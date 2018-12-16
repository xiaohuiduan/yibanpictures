package cc.weno.controller;

import cc.weno.util.GetData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * @author 段小辉
 * 获得前端请求数据
 */
@Controller
public class GetWordController {

    private final static String[] WORDS = {"我好想你", "我喜欢你",
            "喜欢你", "快乐", "皮", "开心", "快乐水", "熬夜", "上课"
    };

    private static final GetData getdata;

    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/springmvc.xml");
        getdata = ctx.getBean("getData", GetData.class);
    }

    @RequestMapping(value = "/doutu", method = RequestMethod.GET)
    public String getWord(@RequestParam(value = "word", required = false, defaultValue = "") String word, @RequestParam(value = "page", defaultValue = "0", required = false
    ) String page, Model model, RedirectAttributes attributes) throws UnsupportedEncodingException, URISyntaxException {

        //用户没有输入信息
        if (word.isEmpty()) {
            int index = (int) (Math.random() * WORDS.length);
            word = WORDS[index];
            attributes.addAttribute("word", word);
            attributes.addAttribute("page", "1");
            //进行重定向
            return "redirect:/doutu";
        }

        String msgResult = getdata.getDataByName(word, page);
        System.out.println(msgResult);
        JSONObject jsonObject = JSONObject.parseObject(msgResult);

        model.addAttribute("msgResult", jsonObject);
        return "doutu";
    }



}
