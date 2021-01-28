package com.example.demo.controller;
import com.baidu.aip.ocr.AipOcr;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OCRcontroller {
    public static final String APP_ID = "百度智能云APP_ID";
    public static final String API_KEY = "百度智能云API_KEY";
    public static final String SECRET_KEY = "百度智能云SECRET_KEY";


    @RequestMapping(value = "/ocr",method = RequestMethod.POST)
    public Map  ocrimg(MultipartFile file) throws Exception {

        HashMap options = new HashMap();
        options.put("language_type", "CHN_ENG");
        //使用高精度参数，选填
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        AipOcr client=new AipOcr(APP_ID,API_KEY,SECRET_KEY);
        byte [] bite=file.getBytes();
        JSONObject jsonObject=client.accurateGeneral(bite,options);
        String str = "";
        //二进制数组
        byte[] buf = file.getBytes();
        Map map = json2map(jsonObject.toString());
        // 提取结果中的文字并作为map打印出来
        List list = (List) map.get("words_result");
        int len = ((List) map.get("words_result")).size();
        for(int i=0; i<len; i++) {
            int z=i+1;
            str = str +z+" "+((Map) list.get(i)).get("words") + "\n";
        }
        System.out.println(str);
        return map;

    }

    public static <T> Map<String, Object> json2map(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString, Map.class);
    }

}
