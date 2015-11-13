<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
      <base href="<%=basePath%>">
      <title></title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge">
	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">    
      <link href="res/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
      <link href="res/css/app-update.css" rel="stylesheet" media="screen">
      <link href="res/css/bootstrap/bootstrap.css" rel="stylesheet" media="screen">
      <link href="res/css/layout-default-latest.css" rel="stylesheet" media="screen">
      <link href="res/css/style.css" rel="stylesheet">
      <link href="res/css/toastr.css" rel="stylesheet">
      <link href="res/css/multi-select/multi-select.css" media="screen" rel="stylesheet" type="text/css">      
  </head>
  
  <body>
      <!-- 中间布局区-->
      <div class="ui-layout-center">
          <div class="center-tool-bar">
              <button class="btn btn-primary btn-sm" id="publishApp" disabled>
                  <span class="glyphicon glyphicon-ok-circle" aria-hidden="true" ></span> 发布应用
              </button>
          </div>
          <div class="menu-row">
              <a href="javascript:void(0);" id="reset-btn" class="btn btn-default" disabled>还原</a>
              <div class="apps-lib">
                  <h5>业务库中应用 <small><em>(业务库中应用)</em></small></h5>
                  <button type="button" class="btn btn-link apps-setting-btn" id="appManage-btn">
                  	<span class='glyphicon glyphicon-cog' aria-hidden='true'>配置业务应用</span>
                  </button>
              </div>
              <div class="apps-select">
                  <h5>安装的应用<small><em>(将要安装的应用，移除则为卸载)</em></small></h5>
              </div>
          </div>
          <select multiple="multiple" id="apps-select" disabled="disabled">
          </select>          
      </div>
      
      <!-- 左侧布局区-->
      <div class="ui-layout-west">
          <h5 class="west-sort-title expand">请选择网点<span></span></h5>
          <div class="west-sort-div">
              <div id="addr-tree" class="ztree"></div>
          </div>
          
          <div class="west-query-div">
              <p><button type="button" class="btn btn-primary btn-block" id="queryApp">查 询</button></p>
          </div>  
      </div>
      <!--[if lte IE 9]>
      <script src="res/js/respond.min.js"></script>
      <script src="res/js/html5shiv.js"></script>
      <![endif]-->
      <script type="text/javascript" src="res/js/jquery.min.js"></script>
      <script type="text/javascript" src="res/js/bootstrap.min.js"></script>      
      <script type="text/javascript"  src="res/js/toastr/toastr.js"></script>
      <script type="text/javascript" src="res/js/layout/jquery.layout-latest.js"></script>
      <script type="text/javascript" src="res/js/ztree/jquery.ztree.core-3.5.min.js"></script>
      <script type="text/javascript" src="res/js/ztree/jquery.ztree.excheck-3.5.js"></script> 
      <script type="text/javascript" src="res/js/multi-select/jquery.multi-select.js"></script>
      <script type="text/javascript" src="script/util/util.js"></script>
      <script type="text/javascript" src="script/main/outletTree.js"></script>
      <script type="text/javascript"  src="script/plugin/toastrOption.js"></script>  
      <script type="text/javascript" src="script/main/functionTree.js"></script>      
      <script type="text/javascript" src="script/device/device-app.js"></script> 
      <script type="text/javascript" src="script/util/session_outtime.js"></script>  
      <script> 
	          //监听配置业务应用按钮
	          $("#appManage-btn").on('click', function() {	
             $.ajax({
		      url:'checkState.action',
		      type:"POST",
		      dataType : "json",
		      success:function(data){
		      if(data.result==true){
		      location.href = "<%=basePath%>" + "system/business/main/toUI.action";	  
	          window.parent.SelectBusinessManageNode();	 
		      }
		      if(data.result==false){
		      location.href="";
		      }
		      },
		      error:function(data){
		      }
		      });  		                        	                              
	          }); 
      </script>  
  </body>
</html>
