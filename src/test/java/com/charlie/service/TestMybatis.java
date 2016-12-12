package com.charlie.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.charlie.test.model.Testu;
import com.charlie.test.service.TestuService;  
  
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class TestMybatis {  
	
	private static Logger logger = Logger.getLogger(TestMybatis.class); 
	
	@Resource(name="testuService")
    private TestuService service = null;  
  
    @Test
    public void testInsert() {  
    	logger.info("===========start=============");
    	Testu record = new Testu() ;
    	record.setAge(18);
    	record.setUsername("first");
    	record.setPassword("123456");
    	service.insertSelective(record);
    	logger.info("===========end=============");
    } 
    @Test
    @Ignore
    public void testSelectAll(){
    	logger.info("===========start=============");
    	List<Testu> list = service.selectALl() ;
    	System.out.println(list.toString());
    	logger.info("===========end=============");
    }
    @Ignore
    @Test
    public void testFind(){
    	Testu result = service.selectByPrimaryKey(1) ;
    	System.out.println(result.getId()+","+result.getUsername());
    }
      
} 