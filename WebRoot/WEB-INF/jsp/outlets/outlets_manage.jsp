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
        <link href="res/css/bootstrap/bootstrap.css" rel="stylesheet" media="screen">
        <link href="res/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
        <link href="res/css/set-outlets.css" rel="stylesheet" type="text/css">
        <link href="res/css/toastr.css" rel="stylesheet">  
        <link href="res/css/uploadify/uploadify.css" rel="stylesheet"> 
        <link href="res/css/validation/Validate.css" rel="stylesheet">        
    </head>
    <body>
        <!-- 新增或更新网点组modal -->
        <div id="groupModal" class="modal fade bs-example-modal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增网点组</h4>
                    </div>
                    <form id="groupForm" role="form">
                        <div class="modal-body">
                            <div class="form-group">
                                <label class="control-label">网点组名:</label>
                                <input type="text" class="form-control" id="outletsGroupName" name="outletsGroupName" maxlength="50" required>
                            </div>
                            <div class="form-group" style="display:none">
                                <label class="control-label">网点组编号:</label>
                                <input type="text" class="form-control" id="outletsGroupCode" name="outletsGroupCode" maxlength="20" >
                            </div>                            
                            <div class="form-group">
                                <label class="control-label">网点组描述:</label>
                                <input type="text" class="form-control" id="outletsGroupDesc" name="outletsGroupDesc" maxlength="256">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal" id="cancel-Group-Btn">取消</button>
                            <button type="submit" class="btn btn-primary" id="add-Group-Btn">确定</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <!-- 新增或更新网点modal-->
        <div class="modal fade" id="outletsModal" role="dialog" aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增或更新网点</h4>
                    </div>
                    <form id="outletForm" role="form">
                        <div class="modal-body">
                            <div class="outlets-info-text">
                                <div class="form-group">
                                    <label class="control-label">网点编号:</label>
                                    <input type="text" class="form-control" id="outletsCode" name="outletsCode" maxlength="32" required>
                                </div>    
                                <div class="form-group">
                                    <label class="control-label">网点名称:</label>
                                    <input type="text" class="form-control" id="outletsName" name="outletsName" maxlength="50" required>
                                </div>                           
                                <div class="form-group">
                                    <label class="control-label">网点描述:</label>
                                    <input type="text" class="form-control" id="outletsDesc" name="outletsDesc">
                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="outlets-cancel-btn" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" id="outlets-save-btn" class="btn btn-primary">确定</button>
                                </div>   
                            </div>                        
                        </div>
                    </form>
                </div>
            </div>
        </div>   
        
        <div class="main-content-wrap">
           <!-- 左侧网点管理树-->
            <div class="content-wrap">
                <div class="zTreeDemoBackground left">
                    <ul id="tree" class="ztree"></ul>
                </div>
            </div>
            <!-- 右侧网点信息展示-->
            <div class="outlet-infor-detail" id="outlet-infor-detail" style="display:none">
                <dl class="dl-horizontal">
                  <dt>网点编号：</dt>
                  <dd id="curOutletsCode"></dd>
                  <dt>网点名称：</dt>
                  <dd id="curOutletsName"></dd>                  
                  <dt>网点描述：</dt>
                  <dd id="curOutletsDesc"></dd>
                  <dd><br/><button class="btn btn-primary" id="outlets-update-btn" >更新网点信息</button></dd>
                </dl>
            </div>
            <div class="outlet-infor-detail" id="group-infor-detail" style="display:none">
                <dl class="dl-horizontal">
                  <dt>网点组名称：</dt>
                  <dd id="curGroupName"></dd>
                  <dt>网点组描述：</dt>
                  <dd id="curGroupDesc"></dd>
                  <dd><br/><button class="btn btn-primary" id="group-update-btn" >更新网点组信息</button></dd>
                </dl>
            </div>
        </div>
        <div id="rMenu">
            <ul>
                <li id="m_add_group">新增网点组</li>
                <li id="m_update_group">更新网点组</li>
                <li id="m_add_outlets">新增网点信息</li>
                <li id="m_update_outlets">更新网点信息</li>
            </ul>            
        </div> 
        
        <!--[if lte IE 9]>
        <script src="res/js/respond.min.js"></script>
        <script src="res/js/html5shiv.js"></script>
        <![endif]-->        
        <script type="text/javascript" src="res/js/jquery.min.js"></script>
        <script type="text/javascript" src="res/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="res/js/ztree/jquery.ztree.core-3.5.js"></script>
        <script type="text/javascript" src="res/js/ztree/jquery.ztree.excheck-3.5.js"></script>
        <script type="text/javascript" src="res/js/ztree/jquery.ztree.exedit-3.5.js"></script>
        <script type="text/javascript" src="res/js/toastr/toastr.js"></script>
        <script type="text/javascript" src="res/js/validation/jquery.validate.min.js"></script>
        <script type="text/javascript" src="res/js/validation/jquery.form.js"></script>
        <script type="text/javascript" src="res/js/uploadify/jquery.uploadify.js"></script>
        <script type="text/javascript" src="script/plugin/toastrOption.js"></script>
        <script type="text/javascript" src="script/outlets/outlets_manage.js"></script>  
        <script type="text/javascript" src="script/util/session_timeout.js"></script>      
     </body>
</html>