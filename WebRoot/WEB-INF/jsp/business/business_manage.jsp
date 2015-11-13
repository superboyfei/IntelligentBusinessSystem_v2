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
        <link href="res/css/set-apps.css" rel="stylesheet" type="text/css">
        <link href="res/css/toastr.css" rel="stylesheet">  
        <link href="res/css/uploadify/uploadify.css" rel="stylesheet">  
        <link href="res/css/validation/Validate.css" rel="stylesheet">       
    </head>
    <body>
    <!--detailModal-->
      <div class="modal fade" id="detailModal" tabindex="-1" role="dialog"  aria-hidden="true" data-backdrop="static">
          <div class="modal-dialog">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">版本更新说明</h4>
                  </div>
                  <div class="modal-body">
                      <textarea class="form-control" rows="18" id="detailUpdate" style="resize:none;cursor:not-allowed;background-color: #eee;opacity: 1;overflow:auto" readonly="readonly"></textarea>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-primary" id="clseDetail">关闭</button>
                  </div>
              </div>
          </div>
      </div>
        <!-- 新增业务组modal -->
        <div id="addGroupModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增业务组</h4>
                    </div>
                    <form id="addGroupForm" role="form">
                        <div class="modal-body">
                            <div class="form-group" style="display:none">
                                <label class="control-label">业务组编号:</label>
                                <input type="text" class="form-control" id="appGroupCode" name="appGroupCode" maxlength="50" required>
                            </div>
                            <div class="form-group">
                                <label class="control-label">业务组名:</label>
                                <input type="text" class="form-control" id="appGroupName" name="appGroupName" maxlength="50" required>
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
        
        <!-- 新增业务应用modal-->
        <div class="modal fade" id="addAppsModal" role="dialog" aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增或更新业务应用</h4>
                    </div>

                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                              <label class="col-sm-3 control-label">业务应用:</label>
                              <div class="col-sm-9" id="addapps">
                                  <input id="choose-app" type="file" />
                                  <input id="sessionID" type="hidden" value="<%=session.getId() %>">
                              </div>
                              <div class="col-sm-9" id="updateapps" style="display:none">
                                  <input id="choose-apps" type="file" />
                                  <input id="sessionID2" type="hidden" value="<%=session.getId() %>">
                              </div>
                            </div>
                        </form>
                        <div class="app-infro-text" style="display:none">
                          <dl class="dl-horizontal">
                          <dt></dt>
                          <dd><img src="" id="appIcon" alt="应用图标" class="img-rounded" width='90' height='90'></dd>
                          <dt>业务名称：</dt>
                          <dd id="bussName"></dd>
                          <dt>业务代码：</dt>
                          <dd id="bussCode"></dd>
                          <dt>应用版本：</dt>
                          <dd id="appVer"></dd>
                          <dt>版本更新说明：</dt>
                          <dd id="appDesc"></dd>
                          </dl>
                          <div class="modal-footer">
                              <button type="button" id="app-cancel-btn" class="btn btn-default" data-dismiss="modal">取消</button>
                              <button type="submit" id="app-save-btn" class="btn btn-primary">确定</button>
                          </div>                           
                        </div>                       
                    </div>
                </div>
            </div>
        </div>   
        
        <div class="main-content-wrap">
           <!-- 左侧业务管理树-->
            <div class="content-wrap">
                <button type="button" class="btn btn-link apps-setting-btn" id="appReleaseBtn">
                  	<span class='glyphicon glyphicon-cog' aria-hidden='true'>应用发布</span>
                </button>
                <div class="zTreeDemoBackground left">
                    <ul id="tree" class="ztree" ></ul>
                </div>
            </div>
            <!-- 右侧业务信息展示-->
            <div class="app-infro-detail" style="display:none">
                <dl class="dl-horizontal">
                  <dt></dt>
                  <dd>
                  <img id="curAppImg" src="" alt="应用图标" class="img-rounded" width='90' height='90'></dd>
                  <dt>业务名称：</dt>
                  <dd id="curBussName"></dd>
                  <dt>业务代码：</dt>
                  <dd id="curBussCode"></dd>
                  <dt>应用名称：</dt>
                  <dd id="curAppName"></dd>
                  <dt>应用版本：</dt>
                  <dd id="curAppVersion"></dd>
                  <dt>版本更新说明：</dt>
                  <dd id="curAppDesc"><a href="javascript:void(0);" id="detail">点击查看</a></dd>
                  <dd><br/><button class="btn btn-primary" id="apps-update-btn" >更新业务应用</button></dd>
                </dl>
            </div>
        </div>
        <div id="rMenu">
            <ul>
                <li id="m_add_group">新增业务组</li>
                <li id="m_add_app">新增业务应用</li>
                <li id="m_update_app">更新业务应用</li>
            </ul>
        </div> 
        
        <!--[if lte IE 9]>
        <script src="res/js/respond.min.js"></script>
        <script src="res/js/html5shiv.js"></script>
        <![endif]-->        
        <script type="text/javascript" src="res/js/jquery.min.js"></script>
        <script type="text/ecmascript" src="res/js/jquery-migrate-1.2.1.min.js"></script>
        <script type="text/javascript" src="res/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="res/js/ztree/jquery.ztree.core-3.5.js"></script>
        <script type="text/javascript" src="res/js/ztree/jquery.ztree.excheck-3.5.js"></script>
        <script type="text/javascript" src="res/js/ztree/jquery.ztree.exedit-3.5.js"></script>
        <script type="text/javascript" src="res/js/validation/jquery.validate.min.js"></script>
        <script type="text/javascript" src="res/js/validation/jquery.form.js"></script>
        <script type="text/jscript"    src="res/js/validation/localization/messages_zh.js"></script>        
        <script type="text/javascript" src="res/js/toastr/toastr.js"></script>
        <script type="text/javascript" src="res/js/uploadify/jquery.uploadify.js"></script>
        <script type="text/javascript" src="script/plugin/toastrOption.js"></script>
        <script type="text/javascript" src="script/main/functionTree.js"></script>      
        <script type="text/javascript" src="script/business/business_manage.js"></script>  
        <script type="text/javascript" src="script/util/session_outtime.js"></script>      
        <script> 
          //监听应用发布按钮
          $("#appReleaseBtn").on('click', function() {  
              $.ajax({
		      url:'checkState.action',
		      type:"POST",
		      dataType : "json",
		      success:function(data){
		      if(data.result==true){
		      location.href = "<%=basePath%>" + "device/remotecontrol/appupdate/main/toUI.action";   
		      window.parent.SelectDeviceAppNode();
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