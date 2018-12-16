package cc.weno.controller;


import cc.weno.util.GetData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xiaohiu
 */

@Controller
public class BaiduController {

    /**
     * 进行图片上传
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public JSONObject upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            // 获得文件原始名字
            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            // 新的名字 UUID可以保证产生的名字不一样
            String newName = UUID.randomUUID() + fileSuffix;
            String filePath = request.getSession().getServletContext().getRealPath("/upload");
            File fileForPath = new File(filePath);

            // 假如文件不存在
            if (!fileForPath.exists()) {
                fileForPath.mkdir();
            }
            File targetFile = new File(filePath, newName);
            try {
                file.transferTo(targetFile);
                GetData getData = new GetData();
                // 獲得數據
                JSONObject jsonObject = getData.returnJson(targetFile.getAbsolutePath());
                return jsonObject;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    @RequestMapping("face")
    public String face(){
        return "face";
    }
}

