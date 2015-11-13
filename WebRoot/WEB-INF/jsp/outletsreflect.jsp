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
        <link href="res/css/validation/Validate.css" rel="stylesheet">
		<link href="res/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		<link href="res/css/toastr.css" rel="stylesheet">
	</head>
	<body>
		<!--newReflectModal-->
		<div class="modal fade" id="newReflectModal"  role="dialog" aria-hidden="true" data-backdrop="static">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		        	<span aria-hidden="true">&times;</span>
		        </button>
	        	<h4 class="modal-title">新增映射</h4>
		      </div>
		        <form id="newReflectForm" action="#" role="form">
		      	  <div class="modal-body">
			          <div class="form-group">
			            <label for="city" class="control-label">地市:</label>
			            <input type="text" class="form-control" id="city" name="city" maxlength="20" aria-describedby="inputSuccessStatus" required>			            
			          </div>
			          <div class="form-group" id="reflect_outlets_div">
			            <label for="outlets" class="control-label">网点名称:</label>
			            <input type="text" class="form-control" id="outlets" name="outlets" autocomplete="off"  maxlength="20" required>
			            <span id="outletsMsg"></span>
			          </div>
			          <div class="form-group" id="reflect_number_div">
			            <label for="number" class="control-label">网点机构号:</label>
			            <input type="text" class="form-control" id="number" name="number" maxlength="20" autocomplete="off" required>
			            <span id="numberMsg"></span>
			          </div>
			          <div class="form-group">
			            <label for="startIP" class="control-label">起始ip:</label>
			            <input type="text" class="form-control" id="startIP" name="startIP" maxlength="20" autocomplete="off" required>
			          </div>
			          <div class="form-group" id="reflect_ip_div">
			            <label for="endIP" class="control-label">结束ip:</label>
			            <input type="text" class="form-control" id="endIP" name="endIP" maxlength="20" autocomplete="off" required>
			            <span id="ipMsg"></span>
			          </div>
			          <div class="form-group">
			            <label for="submask" class="control-label">子关掩码:</label>
			            <input type="text" class="form-control" id="submask" name="submask" maxlength="20" autocomplete="off" required>
			          </div>
			      </div>
			      <div class="modal-footer">			      	
			        <button type="button" class="btn btn-default" data-dismiss="modal" id="newReflectCancelBtn">取消</button>
			        <button type="submit" class="btn btn-primary" id="saveReflectBtn" data-loading-text="保存中...">保存</button>
			      </div>
		        </form>
		    </div>
		  </div>
		</div>		
		<!--editReflectModal-->
		<div class="modal fade" id="editReflectModal"  role="dialog" aria-hidden="true" data-backdrop="static">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		        	<span aria-hidden="true">&times;</span>
		        </button>
	        	<h4 class="modal-title">编辑映射</h4>
		      </div>
		        <form id="updateReflectForm" action="#" role="form">
		      	  <div class="modal-body">
			          <div class="form-group">
			            <label for="edit_city" class="control-label">地市:</label>
			            <input type="text" class="form-control" id="edit_city" name="edit_city" maxlength="20" aria-describedby="inputSuccessStatus" required>
			          </div>
			          <div class="form-group" id="reflect_edit_outlets_div">
			            <label for="edit_outlets" class="control-label">网点名称:</label>
			            <input type="text" class="form-control" id="edit_outlets" name="edit_outlets" autocomplete="off"  maxlength="20" required>
			            <span id="edit_outletsMsg"></span>
			          </div>
			          <div class="form-group"id="reflect_edit_number_div">
			            <label for="edit_number" class="control-label">网点机构号:</label>
			            <input type="text" class="form-control" id="edit_number" name="edit_number" maxlength="20" autocomplete="off" disabled>
			            <span id="edit_numberMsg"></span>
			          </div>
			          <div class="form-group">
			            <label for="edit_startIP" class="control-label">起始ip:</label>
			            <input type="text" class="form-control" id="edit_startIP" name="edit_startIP" maxlength="20" autocomplete="off" required>
			          </div>
			          <div class="form-group"id="reflect_edit_ip_div">
			            <label for="edit_endIP" class="control-label">结束ip:</label>
			            <input type="text" class="form-control" id="edit_endIP" name="edit_endIP" maxlength="20" autocomplete="off" required>
			            <span id="edit_ipMsg"></span>
			          </div>
			          <div class="form-group">
			            <label for="edit_submask" class="control-label">子关掩码:</label>
			            <input type="text" class="form-control" id="edit_submask" name="edit_submask" maxlength="20" autocomplete="off" required>
			          </div>
			      </div>
			      <div class="modal-footer">			      	
			        <button type="button" class="btn btn-default" data-dismiss="modal" id="updateReflectCancelBtn">取消</button>
			        <button type="submit" class="btn btn-primary" id="updateReflectBtn" data-loading-text="保存中...">保存</button>
			      </div>
		        </form>
		    </div>
		  </div>
		</div>
		<!--delReflectModal-->
		<div class="modal fade" id="delReflectModal" tabindex="-1" role="dialog"  aria-hidden="true" data-backdrop="static">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">删除确认</h4>
		      </div>
		      <div class="modal-body">
		        <p>您是否确认删除该映射？</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		        <button type="button" class="btn btn-primary" id="delReflectBtn">确认删除</button>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<div class="ui-layout-center">
			<div class="center-tool-bar">
				<button id="newReflectBtn" type="submit" class="btn btn-default btn-sm" aria-hidden="true" data-toggle='modal'  data-target='#newReflectModal'>
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增映射
				</button>				   
			</div>
			<div class="table-contain">
				<table id="reflect-jqgrid"></table>
				<div id="reflect-jqgrid-page"></div>
			</div>
		</div>
		
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
        <script type="text/javascript" src="res/js/validation/jquery.validate.min.js"></script>
        <script type="text/javascript" src="res/js/validation/jquery.form.js"></script>
        <script type="text/jscript" src="res/js/validation/localization/messages_zh.js"></script>
        <script type="text/javascript" src="res/js/ztree/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="res/js/toastr/toastr.js"></script>
        <script type="text/javascript" src="script/plugin/toastrOption.js"></script>
        <script type="text/javascript" src="script/util/util.js"></script>
        <script type="text/javascript" src="script/main/functionTree.js"></script>
		<script type="text/javascript" src="script/reflect/outletsreflect.js"></script>
		<script type="text/javascript" src="script/util/session_timeout.js"></script>  
	</body>
</html>