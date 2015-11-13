var layoutSettings_Outer = {
    west : {
        size :  230
    }
};

function windowResize()
{
    var width = document.documentElement.clientWidth; 
    var height = document.documentElement.clientHeight;
    $(window).resize(function() {    
        if (width != document.documentElement.clientWidth
            || height != document.documentElement.clientHeight)
        {
            width = document.documentElement.clientWidth;
            height = document.documentElement.clientHeight;
            $('.ms-container .ms-list').height(height - 180);
        }    
    });
}

function getPageSize() {
    var winW, winH; 
    if (window.innerHeight) {// all except IE 
        winW = window.innerWidth; 
        winH = window.innerHeight; 
    } else if (document.documentElement && document.documentElement.clientHeight) {
        // IE 6 Strict Mode 
        winW = document.documentElement.clientWidth; 
        winH = document.documentElement.clientHeight; 
    } else if (document.body) { //other 
        winW = document.body.clientWidth; 
        winH = document.body.clientHeight; 
    }
    return {WinW : winW, WinH : winH}; 
}

function initOutletTree(){
	$("body").layout(layoutSettings_Outer);
	outletTree.tree.loadTree("#addr-tree");		    
	$(".west-sort-title").on("click", function() {
	$(this).toggleClass("expand");
	$(this).next().toggle();
	});    
}

var deviceApp = {
    data   : {
        outlet : "",
        resetSelectValue: null,//保存查询到的应用，用于还原按钮的操作
        selectValue : null,
        selectDiv : null,
        loadServerAppRet : true,
        value : ""
    },
    action : {
        loadServerApp : function() 
        {
            //查询服务器中的所有应用
            $.ajax({
                url      : "device/remotecontrol/appupdate/main/loadAllAppInfo.action",
                type     : "POST",
                dataType : "json",
                async    : false,
                success  : function(data)
                {
                	if(data.result == true)
                	{
	                    var retJson = data.appInfoVoList;
	                    for (var i = 0; i < retJson.length; i++)
	                    {
	                        var appObj = retJson[i];
	                        var appValue, appIcon, appName;
	                        for (key in appObj)
	                        {   
	                            if (key == "id") 
	                            {
	                                appValue = appObj[key];
	                            }
	                            else if (key == "iconPath")
	                            {
	                                appIcon = "ftp" + appObj[key].replace(/\\/g, "/");
	                            }
	                            else if (key == "name")
	                            {
	                                appName = appObj[key];
	                            } 
	                        }
	                        deviceApp.action.creatOption(appValue, appIcon, appName);
	                    }
                	}
                	else
                	{
                        deviceApp.data.loadServerAppRet = false;
                		toastr.error(data.errorMsg);
                	}
                },
                error    : function(data)
                {
                    deviceApp.data.loadServerAppRet = false;
                    toastr.error("服务器通讯失败");
                }
            });
        },
        creatOption : function(appValue, appIcon, appName)
        {
            var option = $("<option value="+ appValue +" data-icon="+ appIcon +">"+ appName +"</option>");
            option.appendTo("#apps-select");
        }
    }
};

$(document).ready(function()
{
    //加载网点树
	initOutletTree();
    
    //监听窗口大小变化
    windowResize();
    
    //加载服务器中所有应用
    deviceApp.action.loadServerApp();
    //初始化选择应用界面
    $('#apps-select').multiSelect(
    {
        selectableFooter : "<br/><a href='javascript:void(0);' class='btn btn-default btn-block' id='select-all' disabled><span class='glyphicon glyphicon-arrow-left' aria-hidden='true'></span>全部添加</a >",
        selectionFooter  : "<br/><a href='javascript:void(0);' class='btn btn-default btn-block' id='deselect-all' disabled>全部移除<span class='glyphicon glyphicon-arrow-right' aria-hidden='true'></span></a>",
        afterSelect : function(values) {
            $('#deselect-all').attr("disabled", false);
            //添加和全部添加应用时改变selectValue的值
            if (deviceApp.data.selectValue != null && values != null)
            {
            	for(var i=0; i<values.length; i++)
            	{
	            	deviceApp.data.selectValue.push(values[i]);
            	}
            }
        },
        afterDeselect : function(values) {
            $('#select-all').attr("disabled", false);
            //移除和全部移除应用时改变selectValue的值
            if (deviceApp.data.selectValue != null && values != null)
            {
            	for(var i = 0; i < values.length; i++)
            	{
	            	var removeIndex = $.inArray(values[i], deviceApp.data.selectValue);
	                if (removeIndex >= 0)
	                {
	                    deviceApp.data.selectValue.splice(removeIndex, 1);
	                }
            	}
            }
        },
        afterInit : function(ms) {
            deviceApp.data.selectDiv = this.$container.find(".ms-selection .ms-list");
            deviceApp.data.selectDiv.css({background:"#e8e8e8"});
        },
        keepOrder : true
    });
    $('.ms-container .ms-list').height(getPageSize().WinH - 180);
    
    //监听全部添加按钮
    $('#select-all').on("click", function() {
        $(this).attr("disabled", true);
        $('#deselect-all').attr("disabled", false);
        $('#apps-select').multiSelect('select_all');
    });
    
    //监听全部移除按钮
    $('#deselect-all').on("click", function() {
        $(this).attr("disabled", true);
        $('#select-all').attr("disabled", false);
        $('#apps-select').multiSelect('deselect_all');
    });
    
    
    //监听发布应用按钮
    $("#publishApp").on('click', function(){
    	window.parent.showLoading();
    	setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
    	var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
        var nodes = treeObj.getCheckedNodes(true);
        var strOutlet="";
        for(var i=0;i<nodes.length;i++){
        	if(nodes[i].isParent){}
        	else{strOutlet+=nodes[i].id+",";}
        }
        var outlet=strOutlet.substr(0,strOutlet.length-1);
        var param = {
            outlet : outlet,
            selectValue : deviceApp.data.selectValue.join(",")
        };
        $.ajax({
            url      : "device/remotecontrol/appupdate/main/publishApp.action",
            type     : "POST",
            dataType : "json",    
            data     : param,
            success  : function(data)
            {            	 
            	window.parent.hideLoading();
                if(data.result == true)
                {
                	deviceApp.data.resetSelectValue = deviceApp.data.selectValue.concat();
                	var SucOrErr=false;
                	$(".ms-selectable li").each(function(){
                			SucOrErr=true;
                	});
                	if(SucOrErr==true){
                		toastr.success("应用发布成功");
                	}    
                	else{
                		toastr.warning("无待发布应用信息");
                	}
                	queryApp();                	
                }
                else
                {
                	toastr.error(data.errorMsg);
                }
            },
            error    : function(data)
            {
                window.parent.hideLoading();
                toastr.error("服务器通讯失败");
            }
        });
        
    });
    
    //监听还原按钮
    $("#reset-btn").on('click', function(){
    	var resetSelectValue = deviceApp.data.resetSelectValue;
        $('#apps-select').multiSelect('deselect_all');
        $('#apps-select').multiSelect('select2', resetSelectValue);
    });    
    
    //监听查询按钮
    $("#queryApp").click(function(){
    	queryApp("query");
    });   
    
    int;
});

var int=self.setInterval(function() {
	var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
    var nodes = treeObj.getCheckedNodes(true);
    var strOutlet="";
    for(var i=0;i<nodes.length;i++){
    	if(nodes[i].isParent){}
    	else{strOutlet+=nodes[i].id+",";}
    }
    var outlet=strOutlet.substr(0,strOutlet.length-1);  
    deviceApp.data.outlet = outlet;
    if(outlet != null){
    	queryApp("int");
    	window.clearInterval(int);
    }   
}, 100); 

function queryApp(intorquery){
	//从服务器获取查询网点已有的应用
	var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
    var nodes = treeObj.getCheckedNodes(true);
    var strOutlet="";
    for(var i=0;i<nodes.length;i++){
    	if(nodes[i].isParent){}
    	else{strOutlet+=nodes[i].id+",";}
    }
    var outlet=strOutlet.substr(0,strOutlet.length-1);
    deviceApp.data.outlet = outlet;
    if (outlet == "") {
        $('#publishApp').attr("disabled", true);
        toastr.error("至少选择一个网点");
        return;
    } else if (outlet == null) {
        $('#publishApp').attr("disabled", true);
        toastr.error("获取网点出错");
        return;
    }
    window.parent.showLoading();
    setTimeout(function(){
		window.parent.hideLoading();
	},8000);
    var param = {
        outlet : outlet
    };
    $.ajax({
        url      : "device/remotecontrol/appupdate/main/loadDeviceAppByOutlets.action",
        type     : "POST",
        dataType : "json",    
        data     : param,
        success  : function(data)
        {
        	if(data.result == true)
        	{
                window.parent.hideLoading();
                deviceApp.data.selectDiv.css({background:"#fcfcfc"});
                $('#apps-select').multiSelect('deselect_all');
                $('li').each(function() {
                    $(this).removeClass('disabled');
                });
                if (deviceApp.data.loadServerAppRet) {
                    $('#select-all').attr("disabled", false);
                    $('#deselect-all').attr("disabled", false);
                    $('#reset-btn').attr("disabled", false);
                    $('#publishApp').attr("disabled", false);
                } 
                if(intorquery=="int"){
                	deviceApp.data.value=data.deviceAppIdsSet+"";
                	$('#apps-select').multiSelect('select2', data.deviceAppIdsSet); 
                }else{
                	if(data.deviceAppIdsSet+""==deviceApp.data.value){
                		$('#apps-select').multiSelect('select2', data.deviceAppIdsSet); 
                	}else{
                		$('#apps-select').multiSelect('select', data.deviceAppIdsSet); 
                		deviceApp.data.value=data.deviceAppIdsSet+"";
                	}
                }
                $('#ms-apps-select .installed').removeClass("installed");//初始化;
                $('#ms-apps-select .ms-selected').addClass("installed");//初始化 原来已经安装的;
                deviceApp.data.selectValue = data.deviceAppIdsSet.concat();//返回新数组，不复制引用
                deviceApp.data.resetSelectValue = data.deviceAppIdsSet.concat();               
        	}
        	else
        	{
        		window.parent.hideLoading();
           	 	toastr.error(data.errorMsg);
        	}
        },
        error    : function(data)
        {
            window.parent.hideLoading();
            toastr.error("服务器通讯失败");
        }
    });
}