package com.charlie.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.charlie.test.dao.TestuMapper;
import com.charlie.test.model.Testu;
import com.charlie.test.service.TestuService;

@Service("testuServiceImpl")
public class TestuServiceImpl implements TestuService{
	@Resource
	private TestuMapper testuMapper ;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return testuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Testu record) {
		// TODO Auto-generated method stub
		return testuMapper.insert(record);
	}

	@Override
	public int insertSelective(Testu record) {
		// TODO Auto-generated method stub
		return testuMapper.insertSelective(record);
	}

	@Override
	public Testu selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return testuMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Testu> selectALl() {
		// TODO Auto-generated method stub
		return testuMapper.selectALl();
	}

	@Override
	public int updateByPrimaryKeySelective(Testu record) {
		// TODO Auto-generated method stub
		return testuMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Testu record) {
		// TODO Auto-generated method stub
		return testuMapper.updateByPrimaryKey(record);
	}
	
}
