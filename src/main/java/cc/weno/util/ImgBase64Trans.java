package cc.weno.util;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将图片转为base64
 * @author xiaohiu
 */
public class ImgBase64Trans {

    /**
     * 将图片转换成base64字符串
     * @param imgFile
     * @return
     * @throws IOException
     */
    public static String getImgStr(String imgFile) throws IOException {
        InputStream in = null;

        byte[] imgData = null;

        //读取图片字节数组
        in = new FileInputStream(imgFile);
        imgData = new byte[in.available()];
        in.read(imgData);

        return new String(Base64.encodeBase64(imgData));
    }
}
