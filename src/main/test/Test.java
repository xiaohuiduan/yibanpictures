import cc.weno.util.GetData;

import cc.weno.util.ImgBase64Trans;
import com.alibaba.fastjson.JSONObject;


import java.io.IOException;
import java.net.URISyntaxException;

public class Test {

    @org.junit.Test
   public void test(){
        GetData getData = new GetData();
        System.out.println( getData.returnJson("ss"));
    }
}
