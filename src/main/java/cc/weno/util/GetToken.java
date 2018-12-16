package cc.weno.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取BaiduAPItoken
 *
 * @author xiaohiu
 */
public class GetToken {

    private static final String API_KEY = "你的APIKEY";

    private static final String SECRET_KEY = "你的Secret_KEY";


    private static final String GRANT_TYPE = "client_credentials";

    private static final String URL = "https://aip.baidubce.com/oauth/2.0/token";


    public String getToken(){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(URL);
        } catch (URISyntaxException e) {
        }

        //构建参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", GRANT_TYPE));
        params.add(new BasicNameValuePair("client_id", API_KEY));
        params.add(new BasicNameValuePair("client_secret", SECRET_KEY));
        uriBuilder.setParameters(params);
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            System.out.println("连接失败");
        }
        //设置请求头
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
            return jsonObject.getString("access_token");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}