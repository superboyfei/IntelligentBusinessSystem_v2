var deviceStatus =
{
    deviceGrid  : 
    {
        loadDeviceGrid : function(outlet, deviceType)
        {
            $("#deviceState-jqGrid").jqGrid({
                colNames  : ['索引', '设备类型', '设备编号', '所在网点', '设备状态'],
                colModel  : [
                    {name : 'id', hidden: true, width: 75, align : 'center', sortable : false},
                    {name : 'type', width: 75, align : 'center', sortable : false,resizable:false},
                    {name : 'serial', width: 75, align : 'center', sortable : false,resizable:false},
                    {name : 'outlets', width: 75, align : 'center', sortable : false,resizable:false},
                    {name : 'status', width: 75, align : 'center', sortable : false,resizable:false}
                ],
                autowidth   : true,
                rowNum      : 20,
                rowList     : [10, 20, 30, 40, 50, 100],
                viewrecords	: true,
                altRows		: true,
                altclass    : 'ui-widget-content-altclass',
                pager       : "#jqGridPager",
                jsonReader  : {
                    root    : "tableVo.list",
                    page    : "tableVo.currpage",
                    total   : "tableVo.totalpages",
                    records : "tableVo.totalrecords",
                    repeatitems: false
                },
				onPaging	: function(pgButton)
				{
//					window.parent.showLoading();
					var lastpage = this.p.lastpage;
    				var pagerId = this.p.pager.substr(1);
    				var $pageInput = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId));
					var newValue = $pageInput.val();
					if (pgButton === "user" && newValue > lastpage)
					{ 
						$pageInput.val(lastpage);
					}
				},
                gridComplete : function() {
                	var ids = $("#deviceState-jqGrid").getDataIDs();
                	if(ids.length==0){      
                		var width = document.documentElement.clientWidth;
                        var height = document.documentElement.clientHeight;
                        var left=(width-320)/2;
                    	var top=(height-70)/2;
                    	$("#showNoData").css("top",top);
                    	$("#showNoData").css("left",left);
		            	$("#showNoData").css("display","block");
		            }
		            if(ids.length!=0){
		            	$("#showNoData").css("display","none");
		            }
                	window.parent.hideLoading();
                }
            });      
            gridSize.action.doResize("#deviceState-jqGrid");
            var width = document.documentElement.clientWidth;
        	var height = document.documentElement.clientHeight;
            $(window).resize(function()
        	{
            	
        		if (width != document.documentElement.clientWidth
                   || height != document.documentElement.clientHeight)
        		{
        			width = document.documentElement.clientWidth;
                    height = document.documentElement.clientHeight;
                    if($(".ui-layout-toggler").attr("title")=="Close"){
                    	var left=(width-320)/2;
                    	var top=(height-70)/2;
                    	$("#showNoData").css("top",top);
                    	$("#showNoData").css("left",left);
                    }
                    if($(".ui-layout-toggler").attr("title")=="Open"){
                    	var left=(width-90)/2;
                    	var top=(height-70)/2;
                    	$("#showNoData").css("top",top);
                    	$("#showNoData").css("left",left);
                    }
        		}
        	});
        }
    },   
    data :
    {
        device :
        {
            id      : '',
            serial  : '',
            type    : '',
            firmware: '',
            outlets : '',
            status  : ''
        }
    }
}

$(document).ready(function()
{
	$(".ui-layout-center").css("overflow","hidden");
    //初始化
    outletTree.tree.loadTree("#addr-tree");
    deviceTypeTree.tree.loadTree("#device-tree");
    deviceStatus.deviceGrid.loadDeviceGrid();
    //监听查询按钮点击事件
    $("#queryBtn").click(function() {
        //获取用户配置的条件
    	var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
        var nodes = treeObj.getCheckedNodes(true);
        var strOutlet="";
        for(var i=0;i<nodes.length;i++){
        	if(nodes[i].isParent){}
        	else{strOutlet+=nodes[i].id+",";}
        }
        var outlet=strOutlet.substr(0,strOutlet.length-1);
        var deviceType = deviceTypeTree.tree.getCheckedNode("device-tree");
        //检查条件
        if (outlet == "") {
            toastr.error("至少选择一个网点");
            return;
        } else if (outlet == null) {
            toastr.error("获取网点出错");
            return;
        }
        if (deviceType == "") {
            toastr.error("至少选择一个设备类型");
            return;
        } else if (deviceType == null) {
            toastr.error("获取设备类型出错");
            return;
        }
        
        //加载表格
        window.parent.showLoading();
        setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
        $("#deviceState-jqGrid").jqGrid("setGridParam", {
            url       : 'device/devicequery/main/loadDevicesByPage.action',
            datatype  : 'json',
            page:0,
            postData  : {
                outlet    : outlet,
                deviceType: deviceType
            }
        }).trigger('reloadGrid');
    });
    $(".ui-layout-toggler").click(function(){
    	if($(this).attr("title")=="Open"){
    		$("#showNoData").css("left",$("#showNoData").offset().left+109);
    		}
    	if($(this).attr("title")=="Close"){  		
    		$("#showNoData").css("left",$("#showNoData").offset().left-351);
    		}
    });
});