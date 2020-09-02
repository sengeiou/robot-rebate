package org.jeecg.modules.robot.handler;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.JeecgApplication;
import org.jeecg.modules.robot.utils.URLUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TbkLoginHandler {
    private ChromeDriverService SERVICE = null;
    private ChromeDriver DRIVER = null;
    private boolean isInput = false;

    private final String CHROME_DRIVER_PATH = "chromedriver_win32/chromedriver.exe";
    private final String LOGIN_NAME = "13719412172";
    private final String LOGIN_PWD = "csp.88712150";

    // 啊里妈妈地址
    private final String LOGIN_URL = "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&from=alimama&redirectURL=http%3A%2F%2Flogin.taobao.com%2Fmember%2Ftaobaoke%2Flogin.htm%3Fis_login%3d1&full_redirect=true&disableQuickLogin=true";
    private final String MAIN_PREFIX = "https://www.alimama.com/index.htm";

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct  //初始化方法
    private void postConstruct() throws Exception{
        log.info("初始化TbkLoginUtil!!!");

        URL resource = JeecgApplication.class.getClassLoader().getResource("/");
        String path = resource.getPath()
                .replaceFirst("file:/","")
                .split("lib")[0] + CHROME_DRIVER_PATH;
        log.info("得到驱动路径:{}", path);

        // 配置浏览器驱动路径
        //System.setProperty("webdriver.chrome.driver", path);

        ChromeOptions option = new ChromeOptions();
        //去掉chrome 正受到自动测试软件的控制
        option.addArguments("disable-infobars");
        //option.addArguments("--headless"); // 是否显示浏览器
        option.addArguments("--window-size=1920,1050");
        option.addArguments("user-agent=" + URLUtil.USER_AGENT);

        SERVICE = new ChromeDriverService.Builder().usingDriverExecutable(new File(path)).usingAnyFreePort().build();
        SERVICE.start();

        //设置开发者模式启动
        option.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        DRIVER = new ChromeDriver(SERVICE, option);

        // 设置 webdriver 为null，帮忙绕过验证
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined }); ");
        DRIVER.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", map);
    }

    @PreDestroy  //结束方法
    private void preDestroy() {
        log.info("关闭TbkLoginUtil!!!");
        if(null != DRIVER){
            DRIVER.quit();
            log.info("关闭驱动");
        }
        if (null != SERVICE ) {
            SERVICE.stop();
            log.info("关闭服务");
        }
    }

    /**
     * 登录方法，并得到cookie
     */
    public String login() throws Exception{

        // 加载登录页面
        DRIVER.get(LOGIN_URL);

        // 页面打开到最大化
        //DRIVER.manage().window().maximize();

        Thread.sleep(1500); // 等待页面加载完

        // 设置账号、密码
        Random rand = new Random();
        if(isInput == false){
            WebElement userEl = DRIVER.findElement(By.xpath("//*[@id=\"fm-login-id\"]"));
            for (int i = 0; i < LOGIN_NAME.length(); i++) {
                // 随机睡眠0-1秒
                Thread.sleep(rand.nextInt(300));
                // 逐个输入单个字符
                userEl.sendKeys("" + LOGIN_NAME.charAt(i));
            }

            WebElement pwdEl = DRIVER.findElement(By.xpath("//*[@id=\"fm-login-password\"]"));
            if(StringUtils.isEmpty(pwdEl.getText())){
                for (int j = 0; j < LOGIN_PWD.length(); j++) {
                    // 随机睡眠0-1秒
                    Thread.sleep(rand.nextInt(300));
                    // 逐个输入单个字符
                    pwdEl.sendKeys("" + LOGIN_PWD.charAt(j));
                }
            }

            if (StringUtils.isNotBlank(DRIVER.findElementById("nc_1__scale_text").getText())) {
                //headless模式 会出现滑块
                //滑块控件元素
                WebElement nc_1_n1z = DRIVER.findElementById("nc_1_n1z");
                //整个拖拽框的控件元素
                WebElement nc_1__scale_text = DRIVER.findElementById("nc_1__scale_text");
                //拖拽的宽度即x的距离
                int x = nc_1__scale_text.getSize().getWidth();
                log.info("拖拽的宽度即x的距离:{}",x);
                //拖拽的高度即y的距离
                int y = nc_1__scale_text.getSize().getHeight();
                log.info("拖拽的高度即y的距离:{}",y);
                Actions actions = new Actions(DRIVER);
                actions.dragAndDropBy(nc_1_n1z, x, y).perform();
                Thread.sleep(2000);
            }
            isInput = true;
        }

        // 点击登录
        DRIVER.findElement(By.xpath("//*[@id=\"login-form\"]/div[4]/button")).click();

        // 检测是否登录成功
        Set<Cookie> cookies = null;
        int times = 10; // 检测15次
        while (true) {
            log.info("登录检查：{}", times);
            String current = DRIVER.getCurrentUrl();
            boolean isOk = current.startsWith(MAIN_PREFIX);
            if (isOk) {
                log.info("查询cookie：{}", times);
                cookies = DRIVER.manage().getCookies();
                break;
            }
            if(times <= 1){
                break;
            }
            Thread.sleep(2000);
            times --;
        }
        if(null == cookies){
            log.error("！！！登录失败");
            return null;
        }
        String cookieStr = cookies.stream().map(co -> co.getName() + "=" + co.getValue() + ";").collect(Collectors.joining(""));
        log.info("登录成功:{}", cookieStr);
        return cookieStr;
    }


    /**
     * 获取登录用户信息
     */
    public String getLoginInfo(String cookie) throws Exception{

        String getInfo = "http://pub.alimama.com/common/getUnionPubContextInfo.json";
        URI url = URI.create(getInfo);

        MultiValueMap<String, String> header = URLUtil.getHeader();
        header.add(HttpHeaders.COOKIE,cookie);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body,header);

        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        if (!HttpStatus.OK.equals(resp.getStatusCode())) {
            return null;
        }
        String msg = resp.getBody();
        log.info("得到登录用户信息：{}", msg);
        /*
        HttpGet request = new HttpGet(getInfo);//这里发送get请求

        //添加请求头
        request.addHeader(HttpHeaders.PRAGMA, "no-cache");
        //设置浏览器类型
        request.addHeader(HttpHeaders.USER_AGENT, USER_AGENT);
        request.addHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,en,*");
        request.addHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
        //设置cookie
        request.addHeader(HttpHeaders.COOKIE, cookie);
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.custom().build();

        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        String msg = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            msg = EntityUtils.toString(response.getEntity(),"utf-8");
        }
        log.info("请求到信息:{}",msg);
         */
        return msg;
    }

}
