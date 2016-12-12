package com.charlie.test.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.charlie.test.bean.Shop;
import com.charlie.test.model.Testu;
import com.charlie.test.service.TestuService;
import com.charlie.ylog.annotion.SaveLog;

@Controller  
@RequestMapping(value = "/")  
public class TestuController {
	
	@Resource(name="testuService")
    private TestuService testuService; 
	
	@RequestMapping(value="/helloworld")   
	@SaveLog(note="test log in controller",saveRequests = true)
	@ResponseBody  
    private String getOtherList(){  
		System.out.println("---------");
        return "{\"hello world!\"}";  
    }  
	@RequestMapping(value="/list",method = RequestMethod.GET )
	private @ResponseBody List<Testu> list(){
		List<Testu> list = testuService.selectALl() ;
		return list ;
	}
	@RequestMapping(value="{name}", method = RequestMethod.GET)
	public @ResponseBody Shop getShopInJSON(@PathVariable String name) {
		Shop shop = new Shop();
		shop.setName(name);
		shop.setStaffName(new String[]{"mkyong1", "mkyong2"});
		return shop;
	}
}
