<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>下载页面</title>
<link href="res/images/favorite.ico" rel="shortcut icon" type="image/x-icon" />
<link href="res/css/style.css" rel="stylesheet">
<link href="res/css/bootstrap/bootstrap.css" rel="stylesheet" media="screen">
<link href="res/css/download/download.css" rel="stylesheet" media="screen">
<link href="res/css/download/rocket.css" rel="stylesheet" media="screen">
<link href="res/css/toastr.css" rel="stylesheet">
<script type="text/javascript"  src="res/js/jquery.min.js"></script>
<script type="text/javascript" src="res/js/download/download.js"></script>
<script type="text/javascript"  src="res/js/toastr/toastr.js"></script>
</head>
<body>
<div style="display: none;" id="rocket-to-top">
<div style="opacity:0;display: block;" class="level-2"></div>
<div class="level-3"></div>
</div>
<div class="header-div" >
    <ul>
      <li><span class="glyphicon glyphicon-cd tit-glyphicon" aria-hidden="true"></span> 软件下载中心<span class="small" id="version">Version 1.0</span></li>  
      <li class="home"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span> <a href="#">返回登录页</a></li>
    </ul>
</div>
<div class="download-wrap" id="download-wrap">
</div>
<nav id="pager">
</nav>
<div class="copyright">©2015  Centerm Information Co., LTD All Rights Reserved</div>
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/ecmascript" src="res/js/jquery-migrate-1.2.1.min.js"></script> 
<script type="text/javascript"  src="res/js/bootstrap.min.js"></script>
<script type="text/javascript"  src="res/js/download/rocketscripts.js"></script>
<script type="text/javascript" src="script/util/session_timeout.js"></script>  
</body>
</html>

