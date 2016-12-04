package com.charlie.common.utils;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/***
 * Http
 * */
public class HTTPUtil {
	private static Log log = LogFactory.getLog(HTTPUtil.class) ;
	// Post
	public static String doPost(String urlAddress, Map<String, String> parmers) throws Exception{
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(urlAddress);
		NameValuePair[] data = new NameValuePair[parmers.size()];
		int i = 0;
		for (String key : parmers.keySet()) {
			data[i] = new NameValuePair(key, parmers.get(key));
			i++;
		}
		post.setRequestBody(data);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		post.getParams().setSoTimeout(8000);
		
		httpClient.executeMethod(post);
		byte[] responseBody = post.getResponseBody();
		return new String(responseBody, "UTF-8");
	}

	public static String doGet(String urlAddress, String Referer, String cookie) throws Exception{
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(urlAddress);
		get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		get.addRequestHeader("Referer", Referer);
		// post.setRequestHeader("Host", "mp.weixin.qq.com");
		get.addRequestHeader("Cookie", cookie);
		httpClient.executeMethod(get);
		byte[] responseBody = get.getResponseBody();
		return new String(responseBody, "UTF-8");
	}
	
	public static String doPostJson(String urlAddress, String params) throws Exception{
		log.info("调用接口============>"+urlAddress) ;
		log.info("参数============>"+params) ;
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(urlAddress);
		if(StringUtils.isNotEmpty(params)){
			RequestEntity requestEntity = new StringRequestEntity(params,"text/xml","UTF-8");
			post.setRequestEntity(requestEntity);
		}
		post.releaseConnection();
		post.getParams().setSoTimeout(0);//A timeout value of zero is interpreted as an infinite timeout.
		
		httpClient.executeMethod(post);
		byte[] responseBody = post.getResponseBody();
		String result = new String(responseBody, "UTF-8") ;
		log.info("返回结果============>"+result) ;
		return result;
	}
	
}
