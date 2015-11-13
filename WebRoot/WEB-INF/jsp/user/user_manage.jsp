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
		<!--newUserModal-->
		<div class="modal fade" id="newUserModal"  role="dialog" aria-hidden="true" data-backdrop="static">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		        	<span aria-hidden="true">&times;</span>
		        </button>
	        	<h4 class="modal-title">新增用户</h4>
		      </div>
		        <form id="newUserForm" action="#" role="form">
		      	  <div class="modal-body">
			          <div class="form-group" id="user_id_div">
			            <label for="user-id" class="control-label">用户账号:</label>
			            <input type="text" class="form-control" id="user-id" name="uid" maxlength="40" aria-describedby="inputSuccessStatus" required>
			            <span id="uidMsg"></span>
			          </div>
			          <div class="form-group">
			            <label for="user-name" class="control-label">用户名称:</label>
			            <input type="text" class="form-control" id="user-name" name="name" autocomplete="off"  maxlength="20" required>
			            <input type="text" class="form-control" id="user-name2" name="name2" autocomplete="off"  maxlength="20" style="display:none">
			          </div>
			          <div class="form-group">
			            <label for="password-text" class="control-label">密码:</label>
			            <input type="password" class="form-control" id="password-text" name="password" maxlength="20" autocomplete="off" required>
			            <input type="password" class="form-control" id="password-text2" name="password2" maxlength="20" autocomplete="off" style="display:none">
			          </div>
			          <div class="form-group">
			            <label for="password-text" class="control-label">确认密码:</label>
			            <input type="password" class="form-control" id="password-text-check" name="passwordCheck" maxlength="20" autocomplete="off" required>
			          </div>
			      </div>
			      <div class="modal-footer">
			      	
			        <button type="button" class="btn btn-default" data-dismiss="modal" id="newUserCancelBtn">取消</button>
			        <button type="submit" class="btn btn-primary" id="saveUserBtn" data-loading-text="保存中...">保存</button>
			      </div>
		        </form>
		    </div>
		  </div>
		</div>
		<!--editUserModal-->
		<div class="modal fade" id="editUserModal"  role="dialog" aria-hidden="true" data-backdrop="static">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">编辑用户</h4>
		      </div>
	        <form id="updateUserForm" role="form">
		      <div class="modal-body">
		          <div class="form-group">
		            <label for="edit-user-id" class="control-label">用户账号:</label>
		            <input type="text" class="form-control" id="edit-user-id" name="uid" disabled />
		          </div>
		          <div class="form-group">
		            <label for="edit-user-name" class="control-label">用户名称:</label>
		            <input type="text" class="form-control" id="edit-user-name" name="name" maxlength="20" required/>
		          </div>
                  <div class="form-group" id="resetForm">
                    <label>
                        <input type="checkbox" id="reset-pass" value="0" name="resetPass">重置密码
                    </label>
                  </div>                  
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal" id="updateUserCancelBtn">取消</button>
		        <button type="submit" class="btn btn-primary" id="updateUserBtn">保存</button>
		      </div>
	        </form>
		    </div>
		  </div>
		</div>
		<!--UserSetupModal-->
		<div class="modal fade" id="UserSetupModal"  role="dialog" aria-hidden="true" data-backdrop="static">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      	<div class="modal-header">
		        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        	<h4 class="modal-title">用户：<span id="show-user-name"></span></h4>
		      	</div>
	        	<form id="userSetupForm">
			      <div class="modal-body">
			        <div class="form-group">
			           <label for="user-name" class="control-label">权限设置:</label>
			           <div class="user-setup-tree-content">
			              <ul id="user-setup-tree" class="ztree"></ul>
			           </div>
			        </div>
			      </div>
			      <div class="modal-footer">
			      	<span id="auth-tip" style="float:left;color:red;">管理员拥有最高权限，不允许被修改</span>
			        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			        <button type="button" class="btn btn-primary" id="saveUserAuthBtn" data-loading-text="保存中...">保存</button>
			      </div>
	        	</form>
		    </div>
		  </div>
		</div>
		<!--delUserModal-->
		<div class="modal fade" id="delUserModal" tabindex="-1" role="dialog"  aria-hidden="true" data-backdrop="static">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">删除确认</h4>
		      </div>
		      <div class="modal-body">
		        <p>您是否确认删除该用户？</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		        <button type="button" class="btn btn-primary" id="delUserBtn">确认删除</button>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<div class="ui-layout-center">
			<div class="center-tool-bar">
				<button id="newUserBtn" type="submit" class="btn btn-default btn-sm" aria-hidden="true" data-toggle='modal'  data-target='#newUserModal'>
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增用户
				</button>				
			    <input id="searchUser" name="searchUser" type="text" placeholder="输入部分或全部用户帐号" class="pull-right">
			</div>
			<div class="table-contain">
				<table id="user-jqgrid"></table>
				<div id="user-jqgrid-page"></div>
			</div>
		</div>
		
		<div id="ParentToChildren"></div>
		<span id="showNoData" style="height:30px;width:120px;position:absolute;display:none;">未找到匹配的数据</span>
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
		<script type="text/javascript" src="script/user/user-authtree.js"></script>
        <script type="text/javascript" src="script/main/functionTree.js"></script>
		<script type="text/javascript" src="script/user/user-manage.js"></script>
		<script type="text/javascript" src="script/user/user-search.js"></script>
		<script type="text/javascript" src="script/util/session_timeout.js"></script>  
	</body>
</html>