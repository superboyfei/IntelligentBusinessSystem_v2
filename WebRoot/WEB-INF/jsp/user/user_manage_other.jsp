<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		<title></title>
		<link href="res/css/style.css" rel="stylesheet">
		<link href="res/css/bootstrap/bootstrap.css" rel="stylesheet" media="screen">
		<link rel="stylesheet" type="text/css" media="screen" href="res/css/uibootstrap/jquery-ui-1.10.0.bootstrap.css" />
		<!-- The link to the CSS that the grid needs -->
		<link rel="stylesheet" type="text/css" media="screen" href="res/css/trirand/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="res/css/trirand/ui.jqgrid-bootstarp.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="res/css/bootstrapvalidator/bootstrapValidator.min.css" />
    </head>
    <body>
        <div class="ui-layout-center">
            <div class="table-contain">
                <table id="user-jqgrid"></table>
				<div id="user-jqgrid-page"></div>
            </div>
        </div>
        
        <div id="ParentToChildren_other"></div>
        <!--[if lte IE 9]>
        <script src="res/js/respond.min.js"></script>
        <script src="res/js/html5shiv.js"></script>
        <![endif]-->
		<script type="text/javascript" src="res/js/jquery.min.js"></script>
		<script type="text/ecmascript" src="res/js/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript" src="res/js/bootstrap.min.js"></script>
		<!--This is the Javascript file of jqGrid -->
		<script type="text/ecmascript" src="res/js/trirand/jquery.jqGrid.min.js"></script>
		<!-- We support more than 40 localizations -->
		<script type="text/ecmascript" src="res/js/trirand/i18n/grid.locale-cn.js"></script>
        <script type="text/javascript" src="script/user/user-other.js"></script>
        <script type="text/javascript" src="script/util/session_timeout.js"></script>  
    </body>
</html>
