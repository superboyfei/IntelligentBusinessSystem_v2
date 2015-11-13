<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<link href="res/css/bootstrap/bootstrap.min.css" rel="stylesheet" media="screen">
		<link rel="stylesheet" type="text/css" media="screen" href="res/css/uibootstrap/jquery-ui-1.10.0.bootstrap.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="res/css/trirand/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="res/css/trirand/ui.jqgrid-bootstarp.css" />
		<link href="res/css/layout-default-latest.css" rel="stylesheet" media="screen">
		<link href="res/css/bootstrap/bootstrap-datepicker3.css" rel="stylesheet" media="screen">
		<link href="res/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
        <link href="res/css/style.css" rel="stylesheet">
        <link href="res/css/custom_togglers.css" rel="stylesheet">
        <link href="res/css/toastr.css" rel="stylesheet">
	</head>
	<body>
        <div class="ui-layout-center">
            <div class="center-tool-bar">
                <button class="btn btn-default btn-sm" id="exportBtn" disabled>
                    <span class="glyphicon glyphicon-plus" aria-hidden="true" ></span> 导出结果
                </button>
            <div class="btn-group pull-right" data-toggle="buttons">
            <label id="haha"></label>
              <label class="btn btn-default disabled" id="tableBtnByDeal">
                <input type="radio" name="options" autocomplete="off" checked> 表格列表
              </label>
              <label class="btn btn-default disabled" id="barBtnByDeal">
                <input type="radio" name="options" autocomplete="off"> 条形图
              </label>
              <label class="btn btn-default disabled" id="pieBtnByDeal" >
                <input type="radio" name="options"autocomplete="off"> 饼状图
              </label>
            </div>
            </div>
            <div class="table-contain" id="table-contain" style="display:block">
                <table id="dealSort-jqGrid"></table>
                <div id="jqGridPager"></div>
            </div>
            <div class="table-contain" id="table-contains" style="display:none;overflow:auto;width:900px;height:530px;"></div>
            <div class="table-contain" id="table-contains2" style="display:none;overflow:auto;width:900px;height:530px;">
            <span id="largeBarData" style="font-size:14px;text-align:center;line-height:500px;display:none">
            条形图数据不多于120条,饼状图数据不多于30条     
            </span>
            </div>
        </div>
        <div class="ui-layout-west">
            <div class="ui-layout-west-sortMenu" id="ui-layout-west-sortMenu">  
            <h5 class="west-sort-title expand">请选择业务类型<span></span></h5>
            <div class="west-sort-div">
                <div id="deal-tree" class="ztree"></div>
            </div>
                
            <h5 class="west-sort-title">请选择时间条件<span></span></h5>
            <div class="west-sort-div" style="display:none">
                <h6>请选择开始日期</h6>
    			<div class="form-group">                     
                    <div id="start-date" class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy MM dd" >
                        <input class="form-control" size="16" type="text" readonly>                        
    					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                 </div>
                <h6>请选择结束日期</h6>
                <div class="form-group">                     
                    <div id="end-date" class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy MM dd" >
                        <input class="form-control" size="16" type="text" readonly>                        
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                 </div> 
             </div>

            <h5 class="west-sort-title">请选择网点<span></span></h5>
            <div class="west-sort-div" style="display:none">
                <div id="addr-tree" class="ztree"></div>
            </div>
            </div>
            
            <div class="west-query-div">
                <p><button type="button" class="btn btn-primary btn-block" id="query">查 询</button></p>
            </div>  
        </div>
        <span id="textarea" style="font-size:12px"></span>  
<!--[if lte IE 9]>
<script src="res/js/respond.min.js"></script>
<script src="res/js/html5shiv.js"></script>
<![endif]-->
<script type="text/javascript" src="res/js/jquery.min.js"></script>
<script type="text/ecmascript" src="res/js/jquery-migrate-1.2.1.min.js"></script> 
<script type="text/javascript" src="res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="res/js/layout/jquery.layout-latest.js" charset="UTF-8"></script>
<script type="text/javascript" src="res/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="res/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/ecmascript" src="res/js/trirand/jquery.jqGrid.min.js"></script>
<script type="text/ecmascript" src="res/js/trirand/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="res/js/datepicker/bootstrap-datepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="res/js/datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="res/js/toastr/toastr.js"></script>        
<script type="text/javascript" src="script/main/iframe-layout.js"></script>
<script type="text/javascript" src="script/main/outletTree.js"></script>
<script type="text/javascript" src="script/main/deviceTypeTree.js"></script>
<script type="text/javascript" src="script/main/bussTypeTree.js"></script>
<script type="text/javascript" src="script/util/util.js" defer></script>        
<script type="text/javascript" src="script/plugin/toastrOption.js"></script>
<script type="text/javascript" src="script/bquery/buss-dealQuery.js"></script>        
<script type="text/javascript" src="res/js/echarts-all.js"></script> 
<script type="text/javascript" src="script/bquery/buss-barQuery.js"></script>  
<script type="text/javascript" src="script/bquery/buss-pieQuery.js"></script> 
<script type="text/javascript" src="script/util/session_outtime.js"></script>  
<script> 
$(document).ready(function() {
	$('#exportBtn').click(function() {
		var param = {
			"bussType" : oldBussType,
			"startDate": oldStartDate,
			"endDate"  : oldEndDate,
			"outlet"   : oldOutlet
        };
		$.post("business/statistics/querybydeal/export/loadExportData.action", param, function(result){
			location.href = "<%=basePath%>" + "business/statistics/querybydeal/export/exportExcel.action";
		});
	});
});
</script>  
</body>
</html>