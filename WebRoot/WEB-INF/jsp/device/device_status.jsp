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

      <link href="res/css/bootstrap/bootstrap.min.css" rel="stylesheet" media="screen">
      <link href="res/css/uibootstrap/jquery-ui-1.10.0.bootstrap.css" rel="stylesheet" media="screen">
      <link href="res/css/layout-default-latest.css" rel="stylesheet" media="screen">
      <link href="res/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
      <link href="res/css/trirand/ui.jqgrid.css" rel="stylesheet" media="screen"/>
      <link href="res/css/trirand/ui.jqgrid-bootstarp.css" rel="stylesheet" media="screen"/>
      <link href="res/css/style.css" rel="stylesheet">
      <link href="res/css/toastr.css" rel="stylesheet">
      <link href="res/css/custom_togglers.css" rel="stylesheet">
  </head>
  
  <body>
      <!--editModal-->
      <!--
      <div class="modal fade" id="editDeviceModal" role="dialog" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title"></h4>
                </div>
                <from id="">
                    <div class="modal-body">
                        <div class="form-group">
                            <div class="">
                                <ul id="device-outlets-tree" class="ztree"></ul>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="saveDeviceBtn" class="btn btn-primary" >确认</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    </div>                   
                </from>
         
            </div>
          </div>
      </div>
      -->
      <!--delDeviceModal-->
      <!--
      <div class="modal fade" id="delDeviceModal" role="dialog" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title">删除网点</h4>
                </div>
                <div class="modal-body">
                    <p>您是否确认删除该设备信息？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" id="delDeviceBtn" class="btn btn-primary" >确认</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	            </div>            
            </div>
          </div>
      </div>-->
      
      <!-- 中间布局区-->
      <div class="ui-layout-center" id="ui-layout-center">
          <div class="table-contain">
          <span id="showNoData" style="height:30px;width:100px;position:absolute;display:none;">未找到设备</span>
              <table id="deviceState-jqGrid"></table>
              <div id="jqGridPager"></div>
          </div>
      </div>
      
      <!-- 左侧布局区-->
      <div class="ui-layout-west">
          <div class="ui-layout-west-sortMenu" id="ui-layout-west-sortMenu"> 
          <h5 class="west-sort-title expand">请选择网点<span></span></h5>
          <div class="west-sort-div">
            <div id="addr-tree" class="ztree"></div>
          </div>
          
          <h5 class="west-sort-title">请选择设备类型<span></span></h5>
          <div class="west-sort-div" style="display:none">
            <div id="device-tree" class="ztree"></div>
          </div>
          </div>
          
          <div class="west-query-div">
              <p><button type="button" id="queryBtn" class="btn btn-primary btn-block">查 询</button></p>
          </div>  
      </div>
      <!--[if lte IE 9]>
      <script src="res/js/respond.min.js"></script>
      <script src="res/js/html5shiv.js"></script>
      <![endif]-->
      <script type="text/javascript" src="res/js/jquery.min.js"></script>
      <script type="text/javascript" src="res/js/jquery-migrate-1.2.1.min.js"></script>
      <script type="text/javascript" src="res/js/bootstrap.min.js"></script>
      <script type="text/javascript" src="res/js/layout/jquery.layout-latest.js"></script>
      <script type="text/javascript" src="res/js/ztree/jquery.ztree.core-3.5.min.js"></script>
      <script type="text/javascript" src="res/js/ztree/jquery.ztree.excheck-3.5.js"></script>
      <script type="text/ecmascript" src="res/js/trirand/jquery.jqGrid.min.js"></script>
      <script type="text/ecmascript" src="res/js/trirand/i18n/grid.locale-cn.js"></script>
      <script type="text/javascript" src="res/js/toastr/toastr.js"></script>
      <script type="text/javascript" src="script/plugin/toastrOption.js"></script>
      <script type="text/javascript" src="script/device/device-outlettree.js"></script>
      <script type="text/javascript" src="script/main/outletTree.js"></script>
      <script type="text/javascript" src="script/main/deviceTypeTree.js"></script>
      <script type="text/javascript" src="script/main/iframe-layout.js"></script>
      <script type="text/javascript" src="script/util/util.js"></script>
      <script type="text/javascript" src="script/device/device-status.js"></script>
      <script type="text/javascript" src="script/util/session_timeout.js"></script>  
  </body>
</html>
