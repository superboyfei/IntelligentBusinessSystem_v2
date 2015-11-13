<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="res/css/style.css" rel="stylesheet">
<link href="res/css/bootstrap/bootstrap.min.css" rel="stylesheet" media="screen">    
<style>
body{background:#e8ebf1;}
#ui-layout{ width: 100%}

/*开始页面*/
.start-info{
  padding:0px;
}
.start-business-chart{
  background-color: #fff;
  border: 1px #E4E4E4 solid;
  margin: 20px 15px;
}
.panel {
  border-radius: 0;
  margin-bottom: 30px;
}
.color-white {
  color: white !important;
}
.panel-light-green,.panel-light-blue,.panel-light-purple {
  position: relative;
}
.panel-light-green img,.panel-light-blue img,.panel-light-purple img{
  position: absolute;
  top:15px;
  left: 15px;
}
.panel-light-green {
  border: 1px solid #dddddd;
}
.bg-light-green {
  background-color: #2ecc71 !important;
}

.panel-light-blue {
  border: 1px solid #dddddd;
}

.bg-light-blue {
  background-color: #3498db !important;
}
.panel-light-purple {
  border: 1px solid #dddddd;
}
.bg-light-purple {
  background-color: #9b59b6 !important;
}    
</style>
</head>
<body>
	<div id="ui-layout">
		<div class="start-info">
            <div class="start-business-chart" id="start-business-chart" style="height:360px"></div>
            <div class="content container-fluid sidebarRight animated fadeInUp"> 
            <div class="row">
                <div class="col-md-4">
                    <div class="panel panel-light-blue bg-light-blue color-white">
                        <img src="res/images/index/1.png" width="92" height="92"/>
                        <div class="panel-body text-right">
                            <h4>网点总数</h4>
                            <h2 id="outlet"></h2>
                        </div>
                    </div>

                </div>
                <div class="col-md-4">
                    <div class="panel panel-light-green bg-light-green color-white">
                      <img src="res/images/index/2.png" width="92" height="92"/>
                        <div class="panel-body text-right">
                            <h4>设备总数</h4>
                            <h2 id="device"></h2>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                   <div class="panel panel-light-purple bg-light-purple color-white">
                        <img src="res/images/index/3.png" width="92" height="92"/>
                        <div class="panel-body text-right">
                            <h4>业务总数</h4>
                            <h2 id="buss"></h2>
                        </div>
                    </div>
                </div>
            </div>                
		</div>
	</div>
    <!--[if lte IE 9]>
    <script src="res/js/respond.min.js"></script>
    <script src="res/js/html5shiv.js"></script>
    <![endif]-->        
    <script type="text/javascript" src="res/js/jquery.min.js"></script>
    <script type="text/javascript" src="res/js/echarts-all.js"></script>
    <script type="text/javascript" src="script/main/main-ui.js"></script>
</body>
</html>
