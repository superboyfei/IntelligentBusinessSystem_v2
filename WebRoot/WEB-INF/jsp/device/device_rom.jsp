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
	  <link href="res/css/style.css" rel="stylesheet">
      <link href="res/css/roms-update.css" rel="stylesheet">
      <link href="res/css/custom_togglers.css" rel="stylesheet">
      <link href="res/css/bootstrap/bootstrap.min.css" rel="stylesheet" media="screen">
      <link href="res/css/toastr.css" rel="stylesheet">
      <link href="res/css/uploadify/uploadify.css" rel="stylesheet">
  </head>
  
  <body>
      <!--romUpdateModal-->
      <div class="modal fade" id="romUpdateModal"  role="dialog" aria-hidden="true" data-backdrop="static">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">上传固件</h4>
              </div>
              <div class="modal-body">
                  <form class="form-horizontal">
                      <div class="form-group">
                          <label class="col-sm-3 control-label">选择固件:</label>
                          <div class="col-sm-9">
                              <input id="choose-rom" type="file" />
                              <input id="sessionID" type="hidden" value="<%=session.getId() %>">
                          </div>
                      </div>                    
                  </form>
                  <div class="rom-info-text" style="display:none">
                      <dl class="dl-horizontal">
                          <dt>固件名称：</dt>
                          <dd id="firewareName"></dd>
                          <dt>固件版本：</dt>
                          <dd id="firewareVer"></dd>
                          <dt>版本更新说明：</dt>
                          <dd id="firewareDesc"></dd>
                      </dl>
                      <div class="modal-footer" >
                          <button type="button" id="rom-cancel-btn" class="btn btn-default" data-dismiss="modal">取消</button>
                          <button type="submit" id="rom-upload-btn" class="btn btn-primary">保存</button>
                      </div>                                              
                  </div>                  
              </div>          
            </div>
          </div>
      </div>
      
      <!--delModal-->
      <div class="modal fade" id="delModal" tabindex="-1" role="dialog"  aria-hidden="true" data-backdrop="static">
          <div class="modal-dialog">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title">删除确认</h4>
                  </div>
                  <div class="modal-body">
                      <p>确认删除该固件？</p>
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
                      <h4 class="modal-title">固件更新说明</h4>
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
              <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true" ></span> 上传固件
          </button>
      </div>    
      
      <!--展示固件信息-->
      <div class="roms-holder" id="roms-holder">
      </div>
          
      <!--[if lte IE 9]>
      <script src="res/js/respond.min.js"></script>
      <script src="res/js/html5shiv.js"></script>
      <![endif]-->
      <script type="text/javascript" src="res/js/jquery.min.js"></script>
      <script type="text/javascript" src="res/js/bootstrap.min.js"></script>
      <script type="text/javascript" src="res/js/toastr/toastr.js"></script>
      <script type="text/javascript" src="res/js/uploadify/jquery.uploadify.js"></script>
      <script type="text/javascript" src="script/plugin/toastrOption.js"></script>
      <script type="text/javascript" src="script/device/device-rom.js"></script>
      <script type="text/javascript" src="script/util/session_timeout.js"></script>      
  </body>
</html>
