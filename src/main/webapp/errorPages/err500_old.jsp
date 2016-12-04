<%@page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%> 
<%@ page import="com.opensymphony.xwork2.interceptor.ExceptionHolder"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
<meta name='apple-mobile-web-app-capable' content='yes' />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=yes" name="format-detection" />
<title>500</title>
<link href="${ pageContext.request.contextPath }/errorPages/css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ pageContext.request.contextPath }/errorPages/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/errorPages/js/v1.js"></script>
<style type="text/css">
html, body { height: 100%; }
</style>
</head>
<body id="err500">
<div class="mainConWrap">
  <div class="mainCon"><img class="img1" src="${ pageContext.request.contextPath }/errorPages/images/bg4.png">
    <div class="robot2Wrap">
      <div class="robot2">
        <img class="img2" src="${ pageContext.request.contextPath }/errorPages/images/bg5.png">
        <img class="img3" src="${ pageContext.request.contextPath }/errorPages/images/bg6.png"></div>
    </div>
  </div>
</div>
<% 
Log log = LogFactory.getLog(Log.class) ;
try{
	String tipMessage = null;//中文提示信息 
	OgnlValueStack s = (OgnlValueStack)ActionContext.getContext().getValueStack();
	ExceptionHolder e;
	
	for(int i = s.size();i>0;i--){
		Object obj = s.pop();
		if(obj instanceof ExceptionHolder){
			e = (ExceptionHolder)obj;
			Exception o = e.getException();
			if(o instanceof IllegalArgumentException){
				if(o.getMessage().indexOf("is not defined in action")!=-1){
					log.error("非法方式访问系统",o);
					response.sendRedirect("/login!logout.do");
					break;
				}
			}
			if(o instanceof java.sql.SQLException){
				java.sql.SQLException se = (java.sql.SQLException)o;
				se.printStackTrace();
				tipMessage = se.getMessage() ;
				log.error(tipMessage,se);
			}else if(o instanceof Exception){
				tipMessage = "未知错误";
				o.printStackTrace();
				log.error(tipMessage,o);
			}
		}
	}
}catch(Exception e){
	e.printStackTrace();
	log.error(e) ;
}
%>
</body>
</html>
