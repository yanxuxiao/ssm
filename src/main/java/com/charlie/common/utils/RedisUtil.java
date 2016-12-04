package com.charlie.common.utils;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	private static final String url = GetProperties.getPro("jdbc.properties").getProperty("redis.url");
	private static final int port = Integer.valueOf(GetProperties.getPro("jdbc.properties").getProperty("redis.port"));
	private static final int lib = Integer.valueOf(GetProperties.getPro("jdbc.properties").getProperty("redis.lib"));
	public static final int CACHESECONDS= Integer.valueOf(GetProperties.getPro("jdbc.properties").getProperty("redis.cachetime")) ;
	
	public static void saveCache(String cacheKey, String cacheValue) {

		Jedis jd = openConnection();
		jd.set(cacheKey, cacheValue);
		closeConnection(jd);
	}
	/**
	 * 
	 * @param cacheKey 键
	 * @param cacheValue 值
	 * @param cacheSeconds 过期时间  if -1 no limit
	 */
	public static void saveCache(String cacheKey, String cacheValue,int cacheSeconds) {
		Jedis jd = openConnection();
		jd.set(cacheKey, cacheValue);
		if(cacheSeconds!=-1){
			jd.expire(cacheKey, cacheSeconds) ;
		}
		closeConnection(jd);
	}

	public static String getCacheString(String cachekey) {
		Jedis jd = openConnection();
		String cacheValue = jd.get(cachekey);
		closeConnection(jd);
		return cacheValue;
	}
	
	public static void expire(String cachekey,int seconds){
		Jedis jd = openConnection();
		jd.expire(cachekey, seconds) ;
		closeConnection(jd);
	}

	public static Jedis openConnection() {
		Jedis jd = new Jedis(url, port);
		jd.select(lib);
		jd.ping();
		return jd;
	}

	public static boolean check(String key) {
		// RedisUtil
		Jedis jd = openConnection();
		return jd.exists(key);
	}

	public static long delete(String key) {
		Jedis jd = openConnection();
		return jd.del(key);
	}

	public static void closeConnection(Jedis jd) {
		jd.quit();
	}
}
