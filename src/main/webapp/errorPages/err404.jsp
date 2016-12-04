<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="initial-scale=1.0,user-scalable=no,maximum-scale=1.0,width=device-width" name="viewport" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=yes" name="format-detection" />
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>404错误</title>
<style type="text/css">
    /**/
/*    body{ width:320px;}*/
.errorBody:after{ content:''; position: fixed;  width:100%; height:100%; left: 0px; top: 0px; z-index:-1;
background:-webkit-gradient(linear,0 0,100% 0,from(#b0dbf4 ),color-stop(0.5,#fff),to(#b0dbf4 )); background-size:contain;}
.errorBody,html{  height:100%; }
.errorBody{position:relative;min-height:300px;}
.errorConOut{ position:absolute; height:400px; margin-top:-200px; width:100%; left:0px; top:50%;}
.errorConIn{ text-align:center;}
.errorConIn .tipImg img{ margin-top:-40px; height:90px;}
.errorConIn .tit{ color:#a6dcfc  ; font-style:italic; font-weight:bold; font-size:100px; line-height:100px;}
.errorConIn .tip1{ color:#4ab8f7 ; margin-top:15px; font-size:20px; line-height:1.5em;}
.errorConIn .tip2{color:#4ab8f7 ; margin-top:5px; font-size:14px; line-height:1.5em;}
.errorConIn .tipMenu{ margin-top:20px;}
.errorConIn .tipMenu a{ padding:10px 20px; color:#fff; display:inline-block; background-color:#4ab8f7 ;}
.errorConIn .tipMenu a.cur{ background-color:#ff6600 ;}
</style>
</head>
<body class="errorBody">
    <div class="errorConOut">
        <div class="errorConIn">
            <div class="tit">404</div>
            <div class="tipImg"><img src="${ pageContext.request.contextPath }/images/errorTipbg.png"></div>
            <div class="tip1">SOORY您访问的页面暂时没找到</div>
            <div class="tip2">您可以通过以下方式继续访问……</div>
            <div class="tipMenu">
                <a href="javascript:void(0);">关于我们</a>
                <a class="cur" href="javascript:void(0);">返回首页</a>
            </div>
        </div>
    </div>
</body>
</html>
