package com.charlie.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.charlie.ylog.annotion.SaveLog;

@Controller  
@RequestMapping(value = "/")  
public class TestuController {
	
	@RequestMapping(value="/helloworld",produces="text/html;charset=UTF-8" )   
	@SaveLog(note="≤‚ ‘Controllerµ˜”√",saveRequests = true)
	@ResponseBody  
    private String getOtherList(){  
		System.out.println("---------");
        return "{\"hello world!\"}";  
    }  
	
}
