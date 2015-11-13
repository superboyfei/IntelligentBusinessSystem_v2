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
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<link href="res/css/style.css" rel="stylesheet">
	<link href="res/css/download/download-update.css" rel="stylesheet">
	<link href="res/css/uploadify/uploadify.css" rel="stylesheet">
	<link href="res/css/custom_togglers.css" rel="stylesheet">
    <link href="res/css/bootstrap/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="res/css/toastr.css" rel="stylesheet">
	<link href="res/css/bootstrap/bootstrap.css" rel="stylesheet" media="screen">
	<body>  
	
   <!--appUpdateModal-->
      <div class="modal fade" id="romUpdateModal"  role="dialog" aria-hidden="true" data-backdrop="static">
          <div class="modal-dialog" style="width: 600px;margin: 30px auto;">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增软件</h4>
              </div>
              <div class="modal-body">
                  <form class="form-horizontal">
                      <div class="form-group">
                          <label class="col-sm-3 control-label" style="padding-top: 7px;margin-bottom: 0;vertical-align: middle;text-align: right;float:left;position: relative;min-height: 1px;padding-right: 15px;padding-left: 15px;width: 25%;">选择软件:</label>
                          <div class="col-sm-9" style="float:left;position: relative;min-height: 1px;padding-right: 15px;padding-left: 15px;width: 75%;">
                              <input id="choose-rom" type="file" />
                              <input id="sessionID" type="hidden" value="<%=session.getId() %>">
                          </div>
                      </div>                    
                  </form>
                  <div class="app-info-text" style="display:none">
                      <dl class="dl-horizontal">
                      	  <dt></dt>
                          <dd><image id="softwareIcon" src="res/images/app-default-icon.png" width="32" height="32"/></dd>
                          <dt>软件名称：</dt>
                          <dd id="softwareName"></dd>
                          <dt>软件大小：</dt>
                          <dd id="softwareSize"></dd>
                          <dt>软件更新时间：</dt>
                          <dd id="softwareTime"></dd>
                          <dt>版本更新说明：</dt>
                          <dd>
                          <textarea id="softwareDesc" class="form-control" rows="3"></textarea>
                          </dd>
                          <dd id="errorInfo" style="display:none;float:right;color:red;font-size:12px;">版本更新说明过长，将自动截取512个字符(256个汉字)</dd>
                      </dl>
                      <div class="modal-footer" >
                          <button type="button" id="app-cancel-btn" class="btn btn-default" data-dismiss="modal">取消</button>
                          <button type="submit" id="app-upload-btn" class="btn btn-primary">保存</button>
                      </div>                                              
                  </div>                  
              </div>          
            </div>
          </div>
      </div>     
      
 <!--delModal-->
      <div class="modal fade" id="delModal" tabindex="-1" role="dialog"  aria-hidden="true" data-backdrop="static">
          <div class="modal-dialog"  style="width: 600px;margin: 30px auto;">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title">删除确认</h4>
                  </div>
                  <div class="modal-body">
                      <p>确认删除该软件？</p>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default" id="cancelBtn"data-dismiss="modal">取消</button>
                      <button type="button" class="btn btn-primary" id="delBtn">确认删除</button>
                  </div>
              </div>
          </div>
      </div> 

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
            
	<div class="center-tool-bar">
	    <button class="btn btn-primary btn-sm" data-toggle='modal'  data-target='#romUpdateModal' id="uploadBtn">
	        <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true" ></span> 新增软件
	    </button>
	</div>    
	<div class="roms-holder" id="roms-holder" style="margin-top:50px;">

  	</div>
	<script type="text/javascript" src="res/js/jquery.min.js"></script>
	<script type="text/javascript" src="res/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="res/js/toastr/toastr.js"></script>
	<script type="text/javascript" src="res/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript" src="script/plugin/toastrOption.js"></script>
	<script type="text/javascript" src="res/js/download/download_manage.js"></script>
	<script type="text/javascript" src="script/util/session_timeout.js"></script>  
	</body>
</html>
