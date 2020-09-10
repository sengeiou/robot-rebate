package org.jeecg.modules.robot.handler;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.robot.entity.*;
import org.jeecg.modules.robot.utils.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TbkHandler {
    private static final String url = "http://gw.api.taobao.com/router/rest";
    private static final String appkey = "30924975";
    private static final String secret = "a3f0c2dd9e153a7ac743322493f88091";
    public static final Long PUB_ID = 1106390200L;
    public static final Long SITE_ID = 1962050151L;
    private static TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TbkLoginHandler tbkLoginHandler;

    // 匹配出 淘宝分享链接
    private static Pattern findHttpPwdRegs = Pattern.compile("https://m.tb.cn/([A-Za-z0-9_.=\\?]+) ");
    // 匹配出 商品id
    private static List<Pattern> httpPwdRegs = new ArrayList<Pattern>() {{
        add(Pattern.compile("&id=(\\d+)&"));
        add(Pattern.compile("com/i(\\d+).htm"));
    }};

    /**
     * 解析 淘宝分享链接 ，得到商品id
     */
    public Long getItemIdByHttpPwd(String httpPwd) {
        Objects.requireNonNull(httpPwd, "！！！淘宝分享链接不能为空");

        Long itemId = null;
        Matcher findMatcher = findHttpPwdRegs.matcher(httpPwd);
        if (!findMatcher.find()) {
            log.warn("!!!没有从信息【{}】中匹配到http", httpPwd);
            return itemId;
        }
        String http = findMatcher.group().trim();
        log.info("匹配到http: {}", http);
        // 请求http得到文本
        URI url = URI.create(http);
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);

        if (!HttpStatus.OK.equals(resp.getStatusCode())) {
            log.warn("!!!请求http失败");
            return itemId;
        }
        String msg = resp.getBody();
        for (Pattern reg : httpPwdRegs) {
            Matcher matcher = reg.matcher(msg);
            if (matcher.find()) {
                itemId = Long.valueOf(matcher.group(1));
                break;
            }
        }
        log.info("得到商品ID： {}", itemId);
        return itemId;
    }


    /**
     * 查询 商品id，得到推广商品信息
     */
    public TbkDgMaterialOptionalResponse.MapData getPushInfo(Long itemId, Long adzoneId) {
        adzoneId = 110732600234L;

        Objects.requireNonNull(itemId,"！！！商品ID不能为空");
        Objects.requireNonNull(adzoneId,"！！！推广ID不能为空");

        // 淘宝客【推广者】物料搜索 16516
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setPageSize(1L);
        req.setPageNo(1L);
        req.setPlatform(2L); // 2：手机
        req.setQ("https://item.taobao.com/item.htm?id=" + itemId);// 商品id
        req.setAdzoneId(adzoneId); // 注意,这里是推广位id
        TbkDgMaterialOptionalResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (Exception ex) {
            log.error("淘宝客查询商品报错:{}", ex);
            throw new RuntimeException("!!!淘宝客查询商品报错:" + ex.getMessage());
        }
        log.info("淘宝客查询商品结果:{}", rsp.getBody());

        if (CollectionUtils.isEmpty(rsp.getResultList())) {
            return null;
        }

        TbkDgMaterialOptionalResponse.MapData obj = rsp.getResultList().get(0);
        // 注意：特殊处理，目的是让链接跳去低价格介绍页面
        // 将卡券链接中的 tpp_pvid 去掉
        if (StringUtils.isNotEmpty(obj.getCouponShareUrl())) {
            String couponUrl = obj.getCouponShareUrl();
            log.warn("!!!卡券链接转化前：{}", couponUrl);
            String temp = URLUtil.decode(couponUrl)
                    .replaceAll(";tpp_pvid:([0-9_.]+)", ";tpp_pvid:");
            obj.setCouponShareUrl("https:" + temp);
            log.warn("!!!卡券链接转化后：{}", temp);
        }
        // 将链接中的 pvid 去掉
        if (StringUtils.isNotEmpty(obj.getUrl())) {
            String url = obj.getUrl();
            log.warn("!!!详情链接转化前：{}", url);
            String temp = URLUtil.decode(url)
                    .replaceAll("&pvid=([0-9_.]+)", "&pvid=null")
                    .replaceAll(";pvid:([0-9_.]+)", ";pvid:");
            obj.setUrl("https:" + temp);
            log.warn("!!!详情链接转化后：{}", temp);
        }
        return obj;
    }


    /**
     * 生成淘口令
     */
    public String createPushPwd(String sharUrl, String picUrl) {
        Objects.requireNonNull(sharUrl,"！！！推广链接不能为空");
        Objects.requireNonNull(picUrl,"！！！推广图片链接不能为空");

        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        // req.setUserId("1868642953");
        req.setText("淘口令内容");
        // 推广链接，对应上一步骤的 "http: + coupon_share_url字段|url字段 "
        // 注意，有时候会没有 coupon_share_url字段（显示优惠券）
        req.setUrl(sharUrl);
        // 商品图片，对应上一步骤的 "http: + pict_url字段"
        req.setLogo(picUrl);
        req.setExt("{}");
        TbkTpwdCreateResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (Exception ex) {
            log.error("!!!生成淘口令报错:{}", ex);
            throw new RuntimeException("!!!生成淘口令报错:" + ex.getMessage());
        }
        //{"tbk_tpwd_create_response":{"data":{"model":"￥kn1ycdglSD1￥"},"request_id":"78twi4of3xoy"}}
        log.info("生成淘口令结果:{}", rsp.getBody());
        return (null != rsp.getData()) ? rsp.getData().getModel() : null;
    }


    /**
     * 添加推广位
     */
    public TbAdzone addAdzone(String adzoneName) throws Exception{
        Objects.requireNonNull(adzoneName, "！！！推广位名称不能为空");

        // 登录淘宝
        TbSession session = tbkLoginHandler.login();
        if (null == session) {
            throw new RuntimeException("！！！调用淘宝客登录失败");
        }
        String token = session.getToken();
        String cookie = session.getCookie();

        // 请求添加推广位信息
        String url = "https://pub.alimama.com/openapi/param2/1/gateway.unionpub/record.adzone.create.json?t=" + System.currentTimeMillis() + "&_tb_token_=" + token;
        log.info("添加请求路径:{}", url);
        URI uul = URI.create(url);
        MultiValueMap<String, String> header = URLUtil.getHeader();
        header.add(HttpHeaders.COOKIE, cookie);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("tag", "29");
        body.add("recordId", SITE_ID.toString());
        body.add("reqParams", "{\"adzoneName\":\"" + adzoneName + "\"}");
        body.add("sceneCode", "adzone_common");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, header);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uul, httpEntity, String.class);

        // 处理返回信息
        TbResult result = TbResult.throwInvalid(responseEntity);
        TbAdzone obj = result.getData().toJavaObject(TbAdzone.class);
        if (null == obj || null == obj.getResult() || obj.getResult() == false) {
            throw new RuntimeException("！！！添加推广拉失败");
        }
        obj.setSiteId(SITE_ID);
        obj.setPubId(PUB_ID);
        log.info("添加推广位结果:{}", obj);
        return obj;
    }

    /**
     * 批量删除推广位
     */
    public void delAdzone(String adzoneIds) throws Exception{
        Objects.requireNonNull(adzoneIds, "！！！推广位ID不能为空");

        TbSession session = tbkLoginHandler.login();
        if(null == session){
            throw new RuntimeException("！！！调用淘宝客登录失败");
        }
        String token = session.getToken();
        String cookie = session.getCookie();
        String url = "https://pub.alimama.com/openapi/param2/1/gateway.unionpub/record.adzone.delete.json?t=" + System.currentTimeMillis() + "&_tb_token_=" + token;
        log.info("添加请求路径:{}", url);
        URI uul = URI.create(url);
        MultiValueMap<String, String> header = URLUtil.getHeader();
        header.add(HttpHeaders.COOKIE,cookie);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("adzoneIds", adzoneIds);
        body.add("sceneCode","adzone_common");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, header);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uul, httpEntity, String.class);

        TbResult result = TbResult.throwInvalid(responseEntity);
        log.info("批量删除推广位:{}", result);
    }


    /**
     * 查询淘宝订单信息
     */
    public TbOrderPage getTbOrderPage(TbOrderPageSearch search) throws Exception{
        Objects.requireNonNull(search, "！！！搜索条件不能为空");

        TbSession session = tbkLoginHandler.login();
        if(null == session){
            throw new RuntimeException("！！！调用淘宝客登录失败");
        }
        String token = session.getToken();
        String cookie = session.getCookie();
        String http = "https://pub.alimama.com/openapi/param2/1/gateway.unionpub/report.getTbkOrderDetails.json?t=" + System.currentTimeMillis() + "&_tb_token_=" + token + "&jumpType=0&positionIndex=" + search.getParams();
        log.info("http:{}",http);
        URI uul = URI.create(http);
        MultiValueMap<String, String> header = URLUtil.getHeader();
        header.add(HttpHeaders.COOKIE,cookie);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, header);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uul, httpEntity, String.class);

        TbResult result = TbResult.throwInvalid(responseEntity);

        TbOrderPage page = result.getData().toJavaObject(TbOrderPage.class);

        log.info("查询淘宝订单信息结果：{}", page);
        return page;
    }

}
