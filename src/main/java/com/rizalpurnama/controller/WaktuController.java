package com.rizalpurnama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class WaktuController {

    @GetMapping("/waktu")
    @ResponseBody
    public String sekarang(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEE, dd MM yyyy", new Locale("id", "ID"));
        return dateFormat.format(new Date());
    }

    @GetMapping("/appinfo")
    @ResponseBody
    public Map<String, String> info (@RequestParam String name){
        Map<String, String> data = new LinkedHashMap<>();

        data.put("application.name", "Aplikasi Java Spring");
        data.put("application.version", "1.0.0");
        data.put("last.update", "2021-09-24");
        data.put("author", name);

        return data;
    }
}
