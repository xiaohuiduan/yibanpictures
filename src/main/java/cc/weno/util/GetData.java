package cc.weno.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohiu
 * 斗图
 * 人脸识别检测
 */
@Component
public class GetData {

    private static CloseableHttpClient httpClient;

    /**
     * 斗图网址
     */
    private static final String DOUTU_URL = "https://www.doutula.com/api/search";

    /**
     * 百度人脸识别网址
     */
    private static final String BAIDU_IMG_URL = "https://aip.baidubce.com/rest/2.0/face/v3/detect";

    /**
     * 百度token
     */
    public static String BAI_TOKEN;

    /**
     * 识别发送的post数据
     */
    public static String BAI_PARAMS;

    private static URIBuilder uriBuilder;

    static {
        httpClient = HttpClients.createDefault();

        //get请求网址拼接
        try {
            uriBuilder = new URIBuilder(DOUTU_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 获得百度token
        BAI_TOKEN = new GetToken().getToken();

        BAI_PARAMS = "image_type=BASE64&max_face_num=1&access_token=" + BAI_TOKEN;
    }

    /**
     * 获得斗图啦网站的数据
     * @param name
     * @param page
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    public String getDataByName(String name, String page) throws UnsupportedEncodingException, URISyntaxException {
        BAI_TOKEN = new GetToken().getToken();
        //构建参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("keyword", name));
        params.add(new BasicNameValuePair("page", page));
        uriBuilder.setParameters(params);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        //设置请求头
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String entityStr = EntityUtils.toString(entity, "UTF-8");
            return entityStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获得人脸识别的数据
     * @param imgBase64 图片的base64编码
     * @return 放回数据
     */
    public String getImageData(String imgBase64) {

        List<NameValuePair> list = new ArrayList<>();
        HttpPost post = new HttpPost(BAIDU_IMG_URL);

        list.add(new BasicNameValuePair("access_token", BAI_TOKEN));
        list.add(new BasicNameValuePair("image", imgBase64));
        list.add(new BasicNameValuePair("image_type", "BASE64"));
        list.add(new BasicNameValuePair("face_field",
                "age,expression,face_shape,gender,glasses,emotion," +
                        "race,beauty"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            HttpEntity resEntity = response.getEntity();
            String entityStr = EntityUtils.toString(resEntity, "UTF-8");
            return entityStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取识别参数，并进行处理
     * @param imgFile 图片位置
     */
    public JSONObject returnJson(String imgFile) {
        JSONObject resultJson = new JSONObject();
        try {
            String msg = ImgBase64Trans.getImgStr(imgFile);
            JSONObject jsonObject = JSONObject.parseObject(getImageData(msg));
            System.out.println(jsonObject);
            switch (jsonObject.getInteger("error_code")) {
                // 成功
                case 0:
                    return jsonObject;
                //QPS超限
                case 18:
                    resultJson.put("error_code", "QPS");
                    break;
                // 110 111token失效，重新获取
                case 110:
                    BAI_TOKEN = new GetToken().getToken();
                    returnJson(imgFile);
                    break;
                case 111:
                    BAI_TOKEN = new GetToken().getToken();
                    returnJson(imgFile);
                    break;
                case 222202:
                    resultJson.put("error_code", "noFace");
                    break;
                case 222304:
                    resultJson.put("error_code", "sizeLarge");
                    break;
                // 未知名的错误
                default:
                    resultJson.put("error_code", "unKnow");
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJson;
    }
}

