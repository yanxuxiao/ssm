package com.charlie.common.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Properties读取工具类。 读取Properties文件用
 * */
public class GetProperties {

	public static Properties getPro(String resourcePath) {
		Properties pro = null;
		try {
			pro = new Properties();
			pro.load(GetProperties.class.getClassLoader().getResourceAsStream(
					resourcePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pro;
	}
	public static void main(String[] args) {
		System.out.println(getPro("common.properties").getProperty("upload.path"));
	}
}
