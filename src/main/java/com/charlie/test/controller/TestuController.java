package com.charlie.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  
@RequestMapping(value = "/")  
public class TestuController {
	
	@RequestMapping(value="/helloworld",produces="text/html;charset=UTF-8" )   
    @ResponseBody  
    private String getOtherList(){  
        return "{\"hello world!\"}";  
    }  
	
}
