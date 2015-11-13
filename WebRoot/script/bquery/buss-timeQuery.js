$('#start-date').datepicker(
{
	language  : 'zh-CN',     
    format    : "yyyy-mm-dd",
    autoclose : true,
    todayHighlight : true,
    endDate : "-1d"
}).on('changeDate', function(ev)
{
	var startDate = $('#start-date .form-control').val();
	if (startDate != "")
	{
		$('#end-date').datepicker(
			"setStartDate", startDate
		);
	}
});

$('#end-date').datepicker(
{
	language  : 'zh-CN',     
    format    : "yyyy-mm-dd",
    autoclose : true,
    todayHighlight : true,
    endDate : "-1d"
}).on('changeDate', function(ev)
{
	var endDate = $('#end-date .form-control').val();
	if (endDate != "")
	{
		$('#start-date').datepicker(
			"setEndDate", endDate
		);
	}
});

$('#year-of-start').datepicker(
{
	language  : 'zh-CN',     
	format    : "yyyy",
	autoclose : true,
	minViewMode : 2,
	maxViewMode : 2
	
}).on('changeDate', function(ev)
{
	var start = $('#year-of-start .form-control').val();
	var end = $('#year-of-end .form-control').val();
    if (start != "") 
    {
        $('#year-of-end').datepicker(
            "setStartDate", start
        );
        formatQuarterByYear(new Number(start),new Number(end));
    }
});

$('#year-of-end').datepicker(
{
	language  : 'zh-CN',     
	format    : "yyyy",
	autoclose : true,
	minViewMode : 2,
	maxViewMode : 2
}).on('changeDate', function(ev)
{
	var start = $('#year-of-start .form-control').val();
	var end = $('#year-of-end .form-control').val();
    if (end != "") 
    {
        $('#year-of-start').datepicker(
            "setEndDate", end
        );
        formatQuarterByYear(new Number(start),new Number(end));
    }
});

var timeQuery = {
    data : {
    	array : new Array(),
    	queryType : "",
        bussType : "",
        outlet : "",
        startDate : "",
        endDate : ""
    },
    grid : {
        loadGrid : function(bussType, outlet, startDate, endDate) 
        {
            $("#timeSort-jqGrid").jqGrid({
                datatype   : "json",
                colNames   : ["时间", "交易数量"],
                colModel   : [
                    { name : 'date', width : 150, align : 'center', sortable : false,resizable:false},
                    { name : 'count', width: 150, align : 'center', sortable : false,resizable:false}
                ],
                viewrecords: true,
                autowidth  : true,
                rowNum     : 20,
                postData   : {
                    bussType  : bussType,
                    outlet    : outlet,
                    startDate : startDate,
                    endDate   : endDate
                },
				jsonReader	    :
				{
					root		: "tableVo.list",
					total		: "tableVo.totalpages",
					page		: "tableVo.currpage",
					records		: "tableVo.totalrecords",
					repeatitems	: false
				},						
                rowList    : [10, 20, 30, 40, 50, 100],
                subGrid    : true, 
                altRows	   : true,
                altclass   : 'ui-widget-content-altclass',
                subGridRowExpanded: showChildGrid,
                pager      : "#jqGridPager",
                onPaging   : function(pgButton)
                {   
//                	window.parent.showLoading();  
                    var lastpage = this.p.lastpage;
                    var pagerId = this.p.pager.substr(1);
                    var $pageInput = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId));
                    var newValue = $pageInput.val();
                    if (pgButton === "user" && newValue > lastpage)
                    { 
                        $pageInput.val(lastpage);
                    }
                },
                loadError: function(xhr,status,error){  
                    window.parent.hideLoading();  
                }, 
                gridComplete    : function() {
                	window.parent.hideLoading();               
                }
            });
            gridSize.action.doResize("#timeSort-jqGrid");               
        }
    }
};

function showChildGrid(parentRowID, parentRowKey) {
    //获取要查询的时间点
    var time  = $("#timeSort-jqGrid").jqGrid("getRowData", parentRowKey).date;
    var treeObj = $.fn.zTree.getZTreeObj("deal-tree");
    var nodes = treeObj.getCheckedNodes(true);
    var strBuss="";
    for(var i=0;i<nodes.length;i++){
    	if(nodes[i].isParent){}
    	else{strBuss+=nodes[i].id+",";}
    }
    var bussType=strBuss.substr(0,strBuss.length-1);  
    var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
    var nodes = treeObj.getCheckedNodes(true);
    var strOutlet="";
    for(var i=0;i<nodes.length;i++){
    	if(nodes[i].isParent){}
    	else{strOutlet+=nodes[i].id+",";}
    }
    var outlet=strOutlet.substr(0,strOutlet.length-1);  
    
    //动态创建子表
    var childGridID = parentRowID + "_table";
    var childGridPagerID = parentRowID + "_pager";
    $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');

    //加载子表数据
    $("#" + childGridID).jqGrid({
        url        : 'business/statistics/querybytime/main/loadSubBusinessStatistics.action',
        mtype      : "POST",
        datatype   : "json",
        colNames   : ['网点', '网点名称', '业务类型', '业务名称', '数量'],
        colModel   : [
            {name : 'outlets', width: 100, align : 'center', sortable : false, hidden: true},
            {name : 'outletName', width: 100, align : 'center', sortable : false,resizable:false},
            {name : 'business', width: 100, align : 'center', sortable : false, hidden: true},
            {name : 'businessName', width: 100, align : 'center', sortable : false,resizable:false},
            {name : 'count', width: 100, align : 'center', sortable : false,resizable:false}
        ],
        autowidth  : true,
        viewrecords: true,
        rowNum     : 10,
        height     : '100%',
        postData   : {
            time     : time,
            bussType : bussType,
            outlet   : outlet
        },
        jsonReader	    :
        {
            root		: "tableVo.list",
            total		: "tableVo.totalpages",
            page		: "tableVo.currpage",
            records		: "tableVo.totalrecords",
            repeatitems	: false
        },
        pager: "#" + childGridPagerID,
        onPaging   : function(pgButton)
        {
            var lastpage = this.p.lastpage;
            var pagerId = this.p.pager.substr(1);
            var $pageInput = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId));
            var newValue = $pageInput.val();
            if (pgButton === "user" && newValue > lastpage)
            { 
                $pageInput.val(lastpage);
            }
        }
    });
    $(window).resize(function() { 
    var ss = gridSize.action.getPageSize(); 
    $("#" + childGridID).jqGrid('setGridWidth',ss.WinW - $("#ui-layout-west-sortMenu").width() -72);
    });
}

var oldQueryType="";
var oldBussType="";
var oldStartDate="";
var oldEndDate="";
var oldOutlet="";
$(document).ready(function() {
    //初始化
	$("#haha").css({
		"float":"left",
		"margin-left":"140"
	});
    outletTree.tree.loadTree("#addr-tree");
    bussTypeTree.tree.loadTree("#deal-tree");
    timeQuery.grid.loadGrid(); 
    $('#start-date .form-control')[0].value = getFormatDate();
    $('#end-date .form-control')[0].value = getFormatDate();
    $('#start-date').datepicker(
		"setEndDate", getFormatDate()
	);
	$('#end-date').datepicker(
		"setStartDate", getFormatDate()
	);
	$('#end-date').datepicker(
    	"setEndDate", getFormatDate()
    );
    
    //监听季度下拉框选项
    $("#startQuarter").on('change', function() {
    	var startYear = new Number($('#year-of-start .form-control').val());
    	var endYear = new Number($('#year-of-end .form-control').val());
    	
    	var now = new Date(); //当前日期
    	var nowMonth = now.getMonth(); //当前月 
    	var quarterNum = parseInt(nowMonth / 3) + 1;
    	var nowYear = now.getYear(); //当前年 
    	nowYear += (nowYear < 2000) ? 1900 : 0;
    	
    	var selectStart = new Number($("#startQuarter").val());
    	var selectEnd = new Number($("#endQuarter").val());
    	
    	var selectMaxIndex = $("#startQuarter option").size();
    	var nameOfQuarter = ["","一","二","三","四"];
    	$("#endQuarter").empty();
    	if((startYear - endYear) != 0){
    		//不同年
    		if((endYear - nowYear) != 0){
    			for(var i = 1; i <= 4; i++){
    	    		$("#endQuarter").append("<option value='"+ i +"'>第" + nameOfQuarter[i] + "季度</option>");
    	    	}
    			$("#endQuarter").val(""+selectEnd);
    		} else {
    			for(var i = 1; i <= quarterNum; i++){
    	    		$("#endQuarter").append("<option value='"+ i +"'>第" + nameOfQuarter[i] + "季度</option>");
    	    	}
    			$("#endQuarter").val(""+selectEnd);
    		}
    	} else {
    		//同一年
    		var nums = 0;
    		if((endYear - nowYear) == 0){
    			//是今年
    			nums = quarterNum - selectStart;
    			for(var i = 0; i <= nums; i++){
            		$("#endQuarter").append("<option value='"+ (selectStart + i)+"'>第" + nameOfQuarter[selectStart + i] + "季度</option>");
            	}
    			if((selectEnd - selectStart) >= 0 && (quarterNum - selectEnd) >= 0){
            		$("#endQuarter").val("" + selectEnd);
            	} else {
            		$("#endQuarter").val(quarterNum);
            	}
    		} else {
    			nums = 4 - selectStart;
    			for(var i = 0; i <= nums; i++){
            		$("#endQuarter").append("<option value='"+ (selectStart + i)+"'>第" + nameOfQuarter[selectStart + i] + "季度</option>");
            	}
            	$("#endQuarter").val("" + selectEnd);
    		}
    		
    	}
    });
    $("#endQuarter").on('change', function() {
    	var startYear = new Number($('#year-of-start .form-control').val());
    	var endYear = new Number($('#year-of-end .form-control').val());
    	
    	var selectStart = new Number($("#startQuarter").val());
    	var selectEnd = new Number($("#endQuarter").val());
    	
    	var nameOfQuarter = ["","一","二","三","四"];
    	$("#startQuarter").empty();
    	if((startYear - endYear) == 0){
    		//同一年
    		var nums = selectEnd;
        	for(var i = 1; i <= nums; i++){
        		$("#startQuarter").append("<option value='"+ i +"'>第" + nameOfQuarter[i] + "季度</option>");
        	}
        	
            if((selectStart - selectEnd) >= 0){
            	$("#startQuarter").val("" + selectEnd);
            } else {
            	$("#startQuarter").val("" + selectStart);
            }
    	} else {
    		//不同年
    		for(var i = 1; i <= 4; i++){
        		$("#startQuarter").append("<option value='"+ i +"'>第" + nameOfQuarter[i] + "季度</option>");
        	}
    		$("#startQuarter").val("" + selectStart);
    	}
    });
    
    //监听时间条件选择事件
    $("#selectTime").on('change', function() {
    	$('#start-date .form-control').val("");
    	$('#end-date .form-control').val("");
        $('#year-of-start').val("");
    	$('#year-of-end').val("");
    	$('#startQuarter').val("");
        $('#endQuarter').val("");
        if($("#selectTime").val() == "0"){
        	$("#startdate-select").html("请选择开始日期");
        	$("#enddate-select").html("请选择结束日期");
        }
        if($("#selectTime").val() == "1"){   
        	$("#startdate-select").html("请选择开始月份");
        	$("#enddate-select").html("请选择结束月份");
        }
        if($("#selectTime").val() == "2"){
        	$("#startdate-select").html("请选择结束年份");
        	$("#enddate-select").html("请选择结束年份");
        } 
    	var mode = parseInt($("#selectTime").val());
    	if (mode == 3) {
    		$(".date-picker-normal").css("display","none");
    		$(".date-picker-quarter").css("display","");
    		formatQuarter();
    		$('#year-of-start').datepicker(
    			"setEndDate" , getFormatDate()
    		);
    		$('#year-of-end').datepicker(
    			"setStartDate" , getFormatDate()
    		);
    		$('#year-of-end').datepicker(
        		"setEndDate" , getFormatDate()
        	);
    	} else {
    		$(".date-picker-normal").css("display","");
    		$(".date-picker-quarter").css("display","none");
    		
    		var minViewMode = mode;
        	var maxViewMode = mode;
        	var format = "yyyy-mm-dd";
        	if (mode == 0) {
        		maxViewMode = mode + 2;
        		$('#start-date .form-control')[0].value = getFormatDate();
        		$('#end-date .form-control')[0].value = getFormatDate();
        	} else if (mode == 1) {
        		maxViewMode = mode + 1;
        		format = "yyyy-mm";
        		formatMonth();
        	} else if (mode == 2) 
        	{
        		format = "yyyy";
        		formatYear();
        	}
        	$('#start-date').datepicker("remove");
            $('#start-date').datepicker({
            	language    : 'zh-CN',     
                format      : format,
                autoclose   : true,        	
            	minViewMode : minViewMode,
            	maxViewMode : maxViewMode
            });
        	$('#end-date').datepicker("remove");
            $('#end-date').datepicker({
            	language    : 'zh-CN',     
                format      : format,
                autoclose   : true,        	
            	minViewMode : minViewMode,
            	maxViewMode : maxViewMode
            });
            $('#start-date').datepicker("remove");
            $('#start-date').datepicker({
            	language    : 'zh-CN',     
                format      : format,
                autoclose   : true,        	
            	minViewMode : minViewMode,
            	maxViewMode : maxViewMode
            });
            
            $('#start-date').datepicker(
    		    "setEndDate", getFormatDate()
    		);
    		$('#end-date').datepicker(
    			"setStartDate", getFormatDate()
    		);
    		$('#end-date').datepicker(
        			"setEndDate", getFormatDate()
        	);
    	}
    });
    
    //监听查询按钮点击事件
    $("#query").on('click', function() {
    	
        //获取用户配置的条件
        var treeObj = $.fn.zTree.getZTreeObj("deal-tree");
        var nodes = treeObj.getCheckedNodes(true);
        var strBuss="";
        for(var i=0;i<nodes.length;i++){
        	if(nodes[i].isParent){}
        	else{strBuss+=nodes[i].id+",";}
        }
        var bussType=strBuss.substr(0,strBuss.length-1);  
        var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
        var nodes = treeObj.getCheckedNodes(true);
        var strOutlet="";
        for(var i=0;i<nodes.length;i++){
        	if(nodes[i].isParent){}
        	else{strOutlet+=nodes[i].id+",";}
        }
        var outlet=strOutlet.substr(0,strOutlet.length-1);  
        var queryType = $("#selectTime").val();
        if (queryType == "3") {
            if ($('#year-of-start .form-control').val() == "") {
                toastr.error("请选择开始年份");
                return;
            } else if ($('#startQuarter').val() == null) {
                toastr.error("请选择开始季度");
                return;
            } else if ($('#year-of-end .form-control').val() == "") {
                toastr.error("请选择结束年份");
                return;
            } else if ($('#endQuarter').val() == null) {
                toastr.error("请选择结束季度");
                return;
            }
            var startDate = $('#year-of-start .form-control').val() + "-" + $('#startQuarter').val();
            var endDate = $('#year-of-end .form-control').val() + "-" + $('#endQuarter').val();
        } else {
            if ($('#start-date .form-control').val() == "") {
                toastr.error("请选择开始日期");
                return;
            } else if ($('#end-date .form-control').val() == "") {
                toastr.error("请选择开始日期");
                return;
            }
            var startDate = getStartDate();
            var endDate = getEndDate();
        }
        oldQueryType = queryType;
        oldBussType = bussType;
        oldOutlet = outlet;
        oldStartDate = startDate;
        oldEndDate = endDate;
        //检查条件
        if (outlet == "") {
            $('#exportBtn').attr("disabled", true);
            toastr.error("至少选择一个网点");
            return;
        } else if (outlet == null) {
            toastr.error("获取网点出错");
            return;
        }
        if (bussType == "") {
            $('#exportBtn').attr("disabled", true);
            toastr.error("至少选择一个业务类型");
            return;
        } else if (bussType == null) {
            toastr.error("获取业务类型出错");
            return;
        }
        if (queryType == "3" 
            && ($('#year-of-start .form-control').val() == $('#year-of-end .form-control').val())) {
            var start = parseInt($('#startQuarter').val());
            var end = parseInt($('#endQuarter').val());
            if (end < start) {
                toastr.error("结束日期季度不能小于开始日期季度");
                return;
            }
        }
        $(".ui-layout-center").css("overflow","hidden");
        $(".center-tool-bar").css("width","100%");
    	$("#table-contains").css("display","none");
    	$("#table-contains2").css("display","none");
    	$("#table-contain").css("display","block");
        $('#exportBtn').attr("disabled", true);  
        $('#tableBtnByTime').removeClass('disabled');
        $('#barBtnByTime').removeClass('disabled');
        $('#pieBtnByTime').removeClass('disabled');
        $('#tableBtnByTime').addClass('active');
        $('#barBtnByTime').removeClass('active');
        $('#pieBtnByTime').removeClass('active');
        timeQuery.data.queryType=queryType;
        timeQuery.data.bussType=bussType;
        timeQuery.data.outlet=outlet;
        timeQuery.data.startDate=startDate;
        timeQuery.data.endDate=endDate;
        
        $('#exportBtn').attr("disabled", false);
        //加载表格
        window.parent.showLoading();
        setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
        $("#timeSort-jqGrid").jqGrid("setGridParam", {
            url        : 'business/statistics/querybytime/main/loadBusinessStatistics.action',
            mtype      : "POST",
            page:0,
            postData : {
            	queryType : queryType,
                bussType  : bussType,
                outlet    : outlet,
                startDate : startDate,
                endDate   : endDate
            }
        }).trigger('reloadGrid');
    });
    $("#tableBtnByTime").on("click",function(){
    	$(".ui-layout-center").css("overflow","hidden");
    	$(".center-tool-bar").css("width","100%");
    	$("#table-contains").css("display","none");
    	$("#table-contains2").css("display","none");
    	$("#table-contain").css("display","block");
        $('#exportBtn').attr("disabled", true);  
    	$('#tableBtnByTime').addClass('active');
        $('#barBtnByTime').removeClass('active');
        $('#pieBtnByTime').removeClass('active');
        window.parent.showLoading();
        setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
        $("#timeSort-jqGrid").jqGrid("setGridParam", {
            url        : 'business/statistics/querybytime/main/loadBusinessStatistics.action',
            mtype      : "POST",
            page:0,
            postData : {
            	queryType : timeQuery.data.queryType,
                bussType  : timeQuery.data.bussType,
                outlet    : timeQuery.data.outlet,
                startDate : timeQuery.data.startDate,
                endDate   : timeQuery.data.endDate
            }
        }).trigger('reloadGrid');
        $('#exportBtn').attr("disabled", false); 
    });
    $(".ui-layout-toggler").click(function(){
    	if($("#table-contain").css("display")=="none"){
    	if($(this).attr("title")=="Open"){
    		$(".center-tool-bar").width($(".center-tool-bar").width()+230);
    		$("#table-contains").css("width",1130);	
    		$("#table-contains2").css("width",1130);	
    		if($("#barBtnByTime").hasClass("active")){
    			barTime();
    		}
    		if($("#pieBtnByTime").hasClass("active")){
    			pieTime();
    		}
    	}
    	if($(this).attr("title")=="Close"){
    		$(".center-tool-bar").width($(".center-tool-bar").width()-230);
    		$("#table-contains").css("width",900);	
    		$("#table-contains2").css("width",900);
    		if($("#barBtnByTime").hasClass("active")){
    			barTime();
    		}
    		if($("#pieBtnByTime").hasClass("active")){
    			pieTime();
    		}
    	}
    	}
    });
});
