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

var dealQuery = {
	data : {
		array : new Array(),
		bussType : "",
        outlet : "",
        startDate : "",
        endDate : ""
	},
    grid : {
        loadGrid : function(bussType, outlet, startDate, endDate)
        {
            $("#dealSort-jqGrid").jqGrid({
                datatype   : "json",
                colNames   : ["业务类型", "业务名称", "交易数量"],
                colModel   : [
                    { name : 'business', width : 150, align : 'center', sortable : false, hidden: true},
                    { name : 'businessName', width: 150, align : 'center', sortable : false,resizable:false},
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
                gridComplete    : function() {
                	window.parent.hideLoading();
                }
            });

            gridSize.action.doResize("#dealSort-jqGrid"); 
        }
    }
};

function showChildGrid(parentRowID, parentRowKey) {
    //获取要查询的条件
    var bussType  = $("#dealSort-jqGrid").jqGrid("getRowData", parentRowKey).business;    
    var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
    var nodes = treeObj.getCheckedNodes(true);
    var strOutlet="";
    for(var i=0;i<nodes.length;i++){
    	if(nodes[i].isParent){}
    	else{strOutlet+=nodes[i].id+",";}
    }
    var outlet=strOutlet.substr(0,strOutlet.length-1);  
    var startDate = getStartDate();
    var endDate = getEndDate();
    
    //动态创建子表
    var childGridID = parentRowID + "_table";
    var childGridPagerID = parentRowID + "_pager";
    $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');

    $("#" + childGridID).jqGrid({
        url        : "business/statistics/querybydeal/main/loadSubBusinessStatistics.action",
        mtype      : "POST",
        datatype   : "json",
        colNames   : ['时间', '网点', '网点名称', '数量'],
        colModel   : [
            {name: 'date', width: 100, align : 'center', sortable : false,resizable:false},
            {name: 'outlets', width: 100, align : 'center', sortable : false, hidden: true},
            {name: 'outletsName', width: 100, align : 'center', sortable : false,resizable:false},
            {name: 'count', width: 100, align : 'center', sortable : false,resizable:false}
        ],
        viewrecords: true,
        rowNum     : 10,
        autowidth  : true,
        height     : '100%',
        postData   : {
            bussType  : bussType,
            outlet    : outlet,
            startDate : startDate,
            endDate   : endDate
        },        
        jsonReader : {
            root		: "tableVo.list",
			total		: "tableVo.totalpages",
			page		: "tableVo.currpage",
			records		: "tableVo.totalrecords",
            repeatitems : false
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

var oldBussType="";
var oldStartDate="";
var oldEndDate="";
var oldOutlet="";
$(document).ready(function() {
	$("#haha").css({
		"float":"left",
		"margin-left":"140"
	});
    //初始化
    bussTypeTree.tree.loadTree("#deal-tree");
    outletTree.tree.loadTree("#addr-tree");
    dealQuery.grid.loadGrid();   
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
        var startDate = getStartDate();
        var endDate = getEndDate();
        dealQuery.data.bussType=bussType;
        dealQuery.data.outlet=outlet;
        dealQuery.data.startDate=startDate;
        dealQuery.data.endDate=endDate;
        
        oldBussType=bussType;
        oldStartDate=startDate;
        oldEndDate=endDate;
        oldOutlet=outlet;
        //判断配置条件
        if (bussType == "") {
            $('#exportBtn').attr("disabled", true);
            toastr.error("至少选择一种业务类型");
            return;
        } else if (bussType == null) {
            toastr.error("获取业务类型出错");
            return;
        }
        if (outlet == "") {
            $('#exportBtn').attr("disabled", true);
            toastr.error("至少选择一个网点");
            return;
        } else if (outlet == null) {
            toastr.error("获取网点出错");
            return;
        }
        $(".ui-layout-center").css("overflow","hidden");
        $(".center-tool-bar").css("width","100%");
    	$("#table-contains").css("display","none");
    	$("#table-contains2").css("display","none");
    	$("#table-contain").css("display","block");
    	$('#tableBtnByDeal').removeClass('disabled');
        $('#barBtnByDeal').removeClass('disabled');
        $('#pieBtnByDeal').removeClass('disabled');
    	$('#tableBtnByDeal').addClass('active');
        $('#barBtnByDeal').removeClass('active');
        $('#pieBtnByDeal').removeClass('active');        
        $('#exportBtn').attr("disabled", false);
        //加载表格
        window.parent.showLoading();
        setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
        $("#dealSort-jqGrid").jqGrid("setGridParam", {
            url        : 'business/statistics/querybydeal/main/loadBusinessStatistics.action',
            mtype      : "POST",
            page:0,
            postData : {
                bussType  : bussType,
                outlet    : outlet,
                startDate : startDate,
                endDate   : endDate
            }
        }).trigger('reloadGrid');
    });
    $("#tableBtnByDeal").on("click",function(){
    	$(".ui-layout-center").css("overflow","hidden");
    	$(".center-tool-bar").css("width","100%");
    	$("#table-contains").css("display","none");
    	$("#table-contains2").css("display","none");
    	$("#table-contain").css("display","block");
        $('#exportBtn').attr("disabled", true);  
    	$('#tableBtnByDeal').addClass('active');
        $('#barBtnByDeal').removeClass('active');
        $('#pieBtnByDeal').removeClass('active');
        window.parent.showLoading();
        setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
        $("#dealSort-jqGrid").jqGrid("setGridParam", {
            url        : 'business/statistics/querybydeal/main/loadBusinessStatistics.action',
            mtype      : "POST",
            page:0,
            postData : {
                bussType  : dealQuery.data.bussType,
                outlet    : dealQuery.data.outlet,
                startDate : dealQuery.data.startDate,
                endDate   : dealQuery.data.endDate
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
    		if($("#barBtnByDeal").hasClass("active")){
    			barDeal();
    		}
    		if($("#pieBtnByDeal").hasClass("active")){
    			pieDeal();
    		}
    	}
    	if($(this).attr("title")=="Close"){
    		$(".center-tool-bar").width($(".center-tool-bar").width()-230);
    		$("#table-contains").css("width",900);	
    		$("#table-contains2").css("width",900);	
    		if($("#barBtnByDeal").hasClass("active")){
    			barDeal();
    		}
    		if($("#pieBtnByDeal").hasClass("active")){
    			pieDeal();
    		}
    	}
    	}
    });
});
