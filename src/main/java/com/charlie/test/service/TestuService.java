package com.charlie.test.service;

import java.util.List;

import com.charlie.test.model.Testu;

public interface TestuService {
	int deleteByPrimaryKey(Integer id);

    int insert(Testu record);

    int insertSelective(Testu record);

    Testu selectByPrimaryKey(Integer id);
    
    List<Testu> selectALl() ;

    int updateByPrimaryKeySelective(Testu record);

    int updateByPrimaryKey(Testu record);
}
