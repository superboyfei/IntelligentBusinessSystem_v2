<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML >
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>智慧营业系统</title>
<link href="res/images/favorite.ico" rel="shortcut icon" type="image/x-icon" />
<link href="res/css/style.css" rel="stylesheet">
<link href="res/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<link href="res/css/bootstrap/bootstrap.css" rel="stylesheet" media="screen">
<link href="res/css/uibootstrap/jquery-ui-1.10.0.bootstrap.css" rel="stylesheet" media="screen"/> 
<link href="res/css/bootstrap/bootstrap-datepicker3.css" rel="stylesheet" media="screen">
<link href="res/css/validation/Validate.css" rel="stylesheet">
<link href="res/css/toastr.css" rel="stylesheet">
    
<style>
    body {
        overflow: hidden;
        padding-right: 0px!important;
        background: url(res/images/Bg.jpg);
        background-size: cover;
        filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='res/images/Bg.jpg',sizingMethod='scale');
        -ms-filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='res/images/Bg.jpg',sizingMethod='scale')";        
    }
    .loadingSpin {
        width:100%;
        height:100%;
        background: #000;
        color: black;
        position: absolute;
        opacity: 0.5;
        filter:alpha(opacity=50);
        z-index: 9999;
      }  
     .errormsg {
     	color: #EC6F6F;
     	font-weight: bold;
     }
     .cover {
        position:absolute;
        top: 0px; 
        width:100%; 
        height:62px;
        filter: alpha(opacity=50); 
        background-color: #000;
        z-index:1000;
        left: 0px; 
        display:none;
        opacity:0.5; 
        -moz-opacity:0.5;
      }
      .cover2 {
        position:absolute;
        top: 62px; 
        width:215px; 
        filter: alpha(opacity=50); 
        background-color: #000;
        z-index:1000;
        left: 0px; 
        display:none;
        opacity:0.5; 
        -moz-opacity:0.5;
      }
</style>
</head>

<body>
    <div id="loadingSpin" class="loadingSpin" style="display:none"></div>
    <!-- updateUserModal -->
    <div class="modal fade" role="dialog" aria-hidden="true" id="updateUserModal" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                <h4 class="modal-title">修改信息</h4>
                </div>
                <form role="form" id="updateUserForm">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label">用户名:</label>
			                <input type="text" class="form-control" id="username" name="username" maxlength="20" required>
			                <input type="text" class="form-control" id="username2" name="username2" maxlength="20" required style="display:none">
                        </div>
                        <div class="form-group">
                            <label>
                                <input type="checkbox" id="checkbox-text"> 修改密码
                                <input type="checkbox" id="checkbox-text2" style="display:none">
                            </label>
			            </div>
                        <div id="oldpass_div" class="form-group">
                            <label class="control-label">第一步，请输入原密码:</label>
			               <!--  <input type="password" class="form-control" id="oldpass" name="oldpass" maxlength="20" disabled> -->
			               <input type="password" class="form-control" id="oldpass2" name="oldpass2"  disabled style="display:none">
			                <input type="password" class="form-control" id="oldpass3" name="oldpass3" disabled style="display:none">
			                <input type="password" class="form-control" id="oldpass" name="oldpass" maxlength="20" aria-describedby="inputSuccessStatus" disabled>			              
			                <span id="oldpassMsg" class="errormsg"></span>
                        </div>
                        <div class="form-group">
                            <label class="control-label">第二步，请输入新密码:</label>
			                <input type="password" class="form-control" id="newpass" name="newpass" maxlength="20" disabled>
			                <input type="password" class="form-control" id="newpass2" name="newpass2" maxlength="20" disabled style="display:none">
			                <span id="newpassMsg" class="errormsg"></span>
                        </div>
                        <div class="form-group">
                            <label class="control-label">第三步，请再次输入新密码:</label>
			                <input type="password" class="form-control" id="confirmpass" name="confirmpass" maxlength="20" disabled>
			                <input type="password" class="form-control" id="confirmpass2" name="confirmpass2" maxlength="20" disabled style="display:none">
                        </div>                        
                    </div>
                    <div class="modal-footer">
			            <button type="button" class="btn btn-default" data-dismiss="modal" id="cancelBtn">取消</button>
			            <button type="submit" class="btn btn-primary" id="saveUserBtn">保存</button>
			        </div>
                </form>
            </div>
        </div>
    </div>
    
    <!-- exportLogModal -->
    <div class="modal fade" role="dialog" aria-hidden="true" id="exportLogModal" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="closeBtn">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">导出日志</h4>
                </div> 
                <form role="form" id="">
                    <div class="modal-body">
 	                    <div class="form-group">
 	                    	<label class="control-label">开始日期:</label>
	                        <div id="start-date" class="input-group date form_date">
	                            <input class="form-control" size="16" type="text" readonly>     
	                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	                    	</div>
	                 	</div>  

 	                    <div class="form-group">
 	                    	<label class="control-label">结束日期:</label>
	                        <div id="end-date" class="input-group date form_date">
	                            <input class="form-control" size="16" type="text" readonly>     
	                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	                    	</div>
	                 	</div>
                    </div>
                    <div class="modal-footer">
			            <button type="button" class="btn btn-default" data-dismiss="modal" id="cancelLogBtn">取消</button>
			            <button type="button" class="btn btn-primary" id="exportLogBtn">导出日志</button>
			        </div>
                </form>
            </div>
        </div>
    </div>
                
    <div id="top-banner">
        <a href="homepage/main/mainInfo.action" target="ui-layout-box" id="logoarea">
        	<img src="res/images/top_logo_img.png">
        	<div class="version"></div>
        </a>
        <div id="top-menu">
            <ul>
                <li id="index">
                    <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                    <a>首页</a>
                </li>
                
                <li data-toggle="modal" data-target="#updateUserModal">
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                    <a id="name" title="${sessionScope.user.name}">${sessionScope.user.name}</a>
                    <a id="uid" hidden>${sessionScope.user.uid}</a>
                    <a id="isadmin" hidden>${sessionScope.user.isadmin}</a>
                </li>
                
                
                <li data-toggle="modal" data-target="#exportLogModal" id="exportLog" hidden style="width:98px;">
                   <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                   <a title="导出日志">导出日志</a>
                   <a title="设置日志" class="setLog" id="setLogs"><span class="glyphicon glyphicon-triangle-bottom" aria-hidden="true"></span></a>
                </li>
              
                <li id="logout">
                    <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                    <a>退出</a>
                </li>
            </ul>
        </div>
        <div class="popover bottom" id="setLogPopoDiv" style="display: none;">
                      <div class="arrow"></div>
                      <h3 class="popover-title">导出日志设置 <button type="button" class="close" aria-label="Close" id="popoverCloseBtn"><span aria-hidden="true">&times;</span></button></h3>
                      <div class="popover-content" style="padding-left:0;">
                        <form class="form-horizontal">                
                             <div class="form-group">
                                <label for="inputEmail3" class="col-sm-6 control-label" style="text-align:left;width:100%;">日志输出级别：</label>
                                   <div class="col-sm-6" style="width:100%">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" name="optionsRadios" style="border:none" value="高">高
                                        </label>
                                        <label>
                                          <input type="radio" name="optionsRadios" style="border:none" value="低">低
                                        </label>      
                                        <label>
                                          <input type="radio" name="optionsRadios" style="border:none" value="关闭">关闭
                                        </label>                                     
                                      </div>
                                   </div>                                
                             </div>
                             <div class="form-group">
                                <div class="col-sm-12">
                                 <button type="button" class="btn btn-default btn-sm btn-block " id="saveLogSet">保存设置</button>
                                </div>
                             </div>                                                        
                        </form>
                      </div>
    </div>
    </div>


    <div id="layout-container">
        <div id="menu-tree">
            <ul id="functionTree" class="ztree"></ul>
        </div>
        <iframe id="ui-layout-box" name="ui-layout-box" frameborder="0" heigth="100%" width="100%" marginheight="0" marginwidth="0" scrolling="auto" style="margin-left: 214px;border: 2px solid rgb(64, 88, 116); border-radius: 2px;" src="homepage/main/mainInfo.action">
        </iframe> 
    </div>
    
    <div id="cover" class="cover"></div>
    <div id="cover2" class="cover2"></div>
<!--[if lte IE 9]>
<script src="res/js/respond.min.js"></script>
<script src="res/js/html5shiv.js"></script>
<![endif]-->  
<script type="text/javascript" src="res/js/jquery.min.js"></script>
<script type="text/javascript" src="res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="res/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="res/js/validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="res/js/validation/jquery.form.js"></script>
<script type="text/javascript" src="res/js/validation/localization/messages_zh.js"></script>
<script type="text/javascript" src="res/js/toastr/toastr.js"></script>   
<script type="text/javascript" src="res/js/spin/spin.min.js"></script>
<script type="text/javascript" src="res/js/spin/jquery.spin.js"></script>
<script type="text/javascript" src="res/js/datepicker/bootstrap-datepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="res/js/datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>    
<script type="text/javascript" src="script/plugin/toastrOption.js"></script>
<script type="text/javascript" src="script/plugin/loading.js"></script>
<script type="text/javascript" src="script/main/functionTree.js"></script> 
<script type="text/javascript" src="script/util/util.js"></script>    
<script type="text/javascript" src="script/main/main.js"></script> 
<script type="text/javascript" src="res/js/respond.min.js"></script>
<script type="text/javascript" src="script/util/session_timeout.js"></script>  
</body>
</html>
