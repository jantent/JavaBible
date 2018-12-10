package com.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @author: tangJ
 * @Date: 2018/9/27 10:01
 * @description:
 */
public class HttpClientMainApp {

    static String getUrl = "https://www.hao123.com";
    static String postUrl = "https://127.0.0.1:8443/test";

    private  static String ipUrlStr = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    private static String myIp = "101.231.201.50";

    @Test
    public void testGet() {
        PoolHttpClient httpClient = new PoolHttpClient();
        String resp = httpClient.doGet(getUrl);
//        System.out.println(resp);
        Document document = Jsoup.parse(resp);
        Elements links = document.getElementsByClass("content__list--item");
        for (Element element:links){
            System.out.println(element.toString());;
        }
    }

    @Test
    public void testPost() {
        PoolHttpClient httpClient = new PoolHttpClient();
        for (int i= 0;i<15;i++) {
            String msg = "你好";
            String resp = httpClient.doPost(postUrl, msg);
            System.out.println(resp);
        }
    }

    @Test
    public void getIpAddr(){
        PoolHttpClient httpClient = new PoolHttpClient();
        String url = ipUrlStr+myIp;
        String result = httpClient.doGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println("国家： "+jsonObject.get("country"));
        System.out.println("省份： "+jsonObject.get("region"));
        System.out.println("城市："+jsonObject.get("city"));

    }
}
