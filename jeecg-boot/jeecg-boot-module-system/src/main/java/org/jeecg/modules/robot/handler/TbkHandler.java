package org.jeecg.modules.robot.handler;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.robot.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    private static TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);

    @Autowired
    private RestTemplate restTemplate;
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
            String temp = URLUtils.decode(couponUrl)
                    .replaceAll(";tpp_pvid:([0-9_.]+)", ";tpp_pvid:");
            obj.setCouponShareUrl("https:" + temp);
            log.warn("!!!卡券链接转化后：{}", temp);
        }
        // 将链接中的 pvid 去掉
        if (StringUtils.isNotEmpty(obj.getUrl())) {
            String url = obj.getUrl();
            log.warn("!!!详情链接转化前：{}", url);
            String temp = URLUtils.decode(url)
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

    public void test() throws Exception{

        int i = 1;
        while (true){

            URI uul = URI.create("https://pub.alimama.com/openapi/param2/1/gateway.unionpub/report.getTbkOrderDetails.json?t=1597485201179&_tb_token_=537bfee7e33d5&jumpType=0&positionIndex=&pageNo=1&startTime=2020-08-12&endTime=2020-08-15&queryType=2&tkStatus=&memberType=&pageSize=40");
            MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
            header.add(HttpHeaders.CACHE_CONTROL,"no-cache");
            header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            header.add(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
            header.add("cookie", "cna=L4+oFyZaUVYCAXFotwpdGiGu; lgc=%5Cu9648csp%5Cu9E4F; tracknick=%5Cu9648csp%5Cu9E4F; xlly_s=1; t=fec7ba9599c0169383aef18273f1b3be; _samesite_flag_=true; cookie2=1f5eaa2251ac44934aac0b0cd1046d0b; unb=1868642953; _l_g_=Ug%3D%3D; mt=ci=62_1; sgcookie=EBHpYDVtsTqxYurC9cgh1; uc1=existShop=true&cookie15=UIHiLt3xD8xYTw%3D%3D&cookie14=UoTV5O1Pbav2CA%3D%3D&pas=0&cookie16=URm48syIJ1yk0MX2J7mAAEhTuw%3D%3D&cookie21=Vq8l%2BKCLjhS4UhJVbhgU; uc3=nk2=0PJlsMGGBw%3D%3D&vt3=F8dCufXCn6nsqljVL44%3D&lg2=U%2BGCWk%2F75gdr5Q%3D%3D&id2=UondHP0kMwumtQ%3D%3D; csg=860925bb; cookie17=UondHP0kMwumtQ%3D%3D; dnk=%5Cu9648csp%5Cu9E4F; skt=a657068772c76314; existShop=MTU5ODcwMzgzMA%3D%3D; uc4=nk4=0%400njcll7Gx9NSEhcBETye3AX2&id4=0%40UOE3EhUQdsCIwTzXfbMIf9i4c37j; _cc_=URm48syIZQ%3D%3D; sg=%E9%B9%8F34; _nk_=%5Cu9648csp%5Cu9E4F; cookie1=BvAD2stbphTMAOcyeLnf2PSG9JTwRVC3qtbo74KIoa8%3D; _tb_token_=7f6376363b553");
            header.add("referer", "https://pub.alimama.com/manage/effect/overview_orders.htm?spm=a219t.11816980.1998910419.db62784000.239375a5os4qGQ&jumpType=0&positionIndex=&pageNo=1&startTime=2020-08-20&endTime=2020-08-29");
            header.add("x-requested-with", "XMLHttpRequest");
            header.add("authority", "pub.alimama.com");

            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, header);


            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(uul, httpEntity, JSONObject.class);
            log.info("{}=发送结果：{}",i, responseEntity);
            i++;
            Thread.sleep(60000);
        }
    }
}