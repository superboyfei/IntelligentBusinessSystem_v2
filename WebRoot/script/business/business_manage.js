var businessManage = 
{
	view	: 
	{
		render	: function()
		{
			businessManage.businessTree.loadTree();
			businessManage.view.initView.resize();
			businessManage.view.initView.doResize();
			businessManage.view.initEvent();
            businessManage.businessFunction.valid();
		},
		initView	:
		{
			getPageSize : function()
	        {
	            var winW, winH; 
	            if (window.innerHeight) {// all except IE 
	                winW = window.innerWidth; 
	                winH = window.innerHeight; 
	            } else if (document.documentElement && document.documentElement.clientHeight) {
	                // IE 6 Strict Mode 
	                winW = document.documentElement.clientWidth; 
	                winH = document.documentElement.clientHeight; 
	            } else if (document.body) { // other 
	                winW = document.body.clientWidth; 
	                winH = document.body.clientHeight; 
	            }
	            return {WinW : winW, WinH : winH}; 
	        },
	        doResize : function()
	        {
	            var ss = businessManage.view.initView.getPageSize();
	            $(".content-wrap").height(ss.WinH - 80);
	            $(".app-infro-detail").height(ss.WinH - 80);
	        },
	        resize	: function()
	        {
	        	//窗口绑定大小变更事件
			    var width = document.documentElement.clientWidth; 
			    var height = document.documentElement.clientHeight;
			    $(window).resize(function() {    
			        if (width != document.documentElement.clientWidth
			            || height != document.documentElement.clientHeight)
			        {
			            width = document.documentElement.clientWidth;
			            height = document.documentElement.clientHeight;
			            businessManage.view.initView.doResize();
			        }    
			    });
	        }
		},
		initEvent	: function()
	    {
	    	//鼠标移出rMenu时隐藏
	    	$("#rMenu").mouseleave(function()
			{
				$("#rMenu").hide();
			});
			
	    	$("#m_add_group").on("click", function(){
            	$('#addGroupModal').modal('show');
	        });
	    
            //监听新增业务组关闭按钮
            $('#addGroupModal').on('hide.bs.modal', function() {
                $("#appGroupName").val("");
            });
            
            //监听新增业务组取消按钮
            $('#cancel-Group-Btn').on('click', function() {
                $("#appGroupName").val("");
            });
	        
	        $("#m_add_app").on("click", function(){
	        	businessManage.data.isUpdate = false;
	        	$('#addAppsModal .modal-title').text("新增业务应用");
	        	$('#updateapps').css("display","none");
	        	$('#addapps').css("display","block");
	           	$('#addAppsModal').modal('show');
	        });
	        
	        $("#m_update_app").on("click", function(){
	        	businessManage.data.isUpdate = true;
	        	$('#addAppsModal .modal-title').text("更新业务应用");
	        	$('#addapps').css("display","none");
	        	$('#updateapps').css("display","block");
	           	$('#addAppsModal').modal('show');
	        });
	        
	        //监听右侧更新业务应用按钮
			$("#apps-update-btn").on("click", function(){
				businessManage.data.isUpdate = true;
				$('#addAppsModal .modal-title').text("更新业务应用");
				$('#addapps').css("display","none");
	        	$('#updateapps').css("display","block");
		       	$('#addAppsModal').modal('show');
			});
	        
			//监听应用保存按钮
		    $("#app-save-btn").on("click", function() {
		    	if($('#addapps').css("display")=="block"){
		    		businessManage.businessFunction.addBusiness();
		    	}
		    	if($('#updateapps').css("display")=="block"){
		    		businessManage.businessFunction.updateBusiness();	
		    	}
		    });
		    
			//监听应用取消按钮
		    $("#app-cancel-btn").on("click", function() {
		        businessManage.businessFunction.cancelSaveAppInfo();
                $(".form-horizontal").show();
	            $(".app-infro-text").hide();
		    });
            
			//监听应用关闭按钮
		    $("#addAppsModal").on("hide.bs.modal", function() {
                $(".form-horizontal").show();
	            $(".app-infro-text").hide();
	            $(".close").show();
		    });            
		    
		    //应用上传
		    var sessionID = $("#sessionID").val();// jsessionid 为了解决火狐下的问题。
		    $("#choose-app").uploadify({
		        auto       : true, 
		        swf        : 'res/js/uploadify/uploadify.swf',    //上传使用的Flash
		        uploader   : '../add/uploadAppFile.action;jsessionid=' + sessionID,//上传路径
		        fileObjName: 'appFile',
		        buttonText : '上传应用',
		        fileSizeLimit : '300MB',
		        multi	   : false,
		        onUploadStart : function(file) {
		        	$(".close").hide();
		        	$("#choose-app").uploadify('disable', true);
		        	//在上传时附加参数不能使用formData : {'isUpdate' : businessManage.data.isUpdate}
				    $("#choose-app").uploadify("settings", "formData", {
				    	"isParent":businessManage.data.isParent,
				    	"parentid":businessManage.businessTree.treeNode.id});
				},
		        onUploadError : function(file, errorCode, errorMsg, errorString) {
		        	$("#choose-rom").uploadify('disable', false);
		            if(errorCode == -280){
		            	toastr.warning("文件上传已被用户取消");
		            } else if(errorCode == -200 || errorCode == -220 ) {
		            	var p = window;
		            	while (p != p.parent) {
		            		p = p.parent;
		            	}
		            	p.location.href = '/IntelligentBusinessSystem/welcome.action';
		            } else {
		            	toastr.error("文件上传失败(" + errorCode + ")，请上传300M以内的文件");
		            }
                    $("#addAppsModal").modal("hide");
                    $(".uploadify-queue").html("");
		        },
		        onUploadSuccess : function(file, data, response) {
                    $("#choose-app").uploadify('disable', false);
		            var jsonData = JSON.parse(data);
		            if(jsonData.result == false)
		            {
		            	businessManage.businessFunction.cancelSaveAppInfo();
		            	if(jsonData.errorLevel == "error"){
		            		toastr.error(jsonData.errorMsg);
		            	} else {
		            		toastr.warning(jsonData.errorMsg);
		            	}
		            	$("#addAppsModal").modal("hide");
		            }
		            else
		            {
		            	$("#appIcon").attr("src", ("ftp" +jsonData.appUploadInfoVo.iconPath).replace(/\\/g, "/"));
		            	$('#bussName').text(jsonData.appUploadInfoVo.name);
		                $('#bussCode').text(jsonData.appUploadInfoVo.code);
		                $('#appVer').text(jsonData.appUploadInfoVo.version);
		                $('#appDesc').text(jsonData.appUploadInfoVo.description);
			            businessManage.data.uploadSucc = true;
			            $(".app-infro-text").show();
		            }
		            $(".uploadify-queue").html("");
		        },
		        onUploadComplete : function() {
                    $("#choose-app").uploadify('disable', false);
		            if (businessManage.data.uploadSucc) {
		                $(".form-horizontal").hide();
		                businessManage.data.uploadSucc = false;
		            }
		        },
		        onFallback : function() {
		            toastr.error("未安装flash插件，无法进行文件上传");
		        },
                onCancel   : function() {
                    $(".close").show();
                }
		    });
		    var sessionID2 = $("#sessionID2").val();// jsessionid 为了解决火狐下的问题。
		    $("#choose-apps").uploadify({
		        auto       : true, 
		        swf        : 'res/js/uploadify/uploadify.swf',    //上传使用的Flash
		        uploader   : '../update/uploadAppFile.action;jsessionid=' + sessionID2,//上传路径
		        fileObjName: 'appFile',
		        buttonText : '上传应用',
		        fileSizeLimit : '300MB',
		        multi	   : false,
		        onUploadStart : function(file) {
		        	$(".close").hide();
		        	$("#choose-apps").uploadify('disable', true);
		        	//在上传时附加参数不能使用formData : {'isUpdate' : businessManage.data.isUpdate}
				    $("#choose-apps").uploadify("settings", "formData", {"id" : businessManage.businessTree.treeNode.id});
				},
		        onUploadError : function(file, errorCode, errorMsg, errorString) {
                    $("#choose-apps").uploadify('disable', false);
                    if(errorCode == -280){
		            	toastr.warning("文件上传已被用户取消");
		            } else if(errorCode == -200 || errorCode == -220 ) {
		            	var p = window;
		            	while (p != p.parent) {
		            		p = p.parent;
		            	}
		            	p.location.href = '/IntelligentBusinessSystem/welcome.action';
		            } else {
		            	toastr.error("文件上传失败(" + errorCode + ")，请上传300M以内的文件");
		            }
                    $("#addAppsModal").modal("hide");
                    $(".uploadify-queue").html("");
		        },
		        onUploadSuccess : function(file, data, response) {
                    $("#choose-apps").uploadify('disable', false);
		            var jsonData = JSON.parse(data);
		            if(jsonData.result == false)
		            {
		            	businessManage.businessFunction.cancelSaveAppInfo();
		            	if(jsonData.errorLevel == "error") {
		            		toastr.error(jsonData.errorMsg);
		            	} else {
		            		toastr.warning(jsonData.errorMsg);
		            	}
		            	$("#addAppsModal").modal("hide");
		            }
		            else
		            {
		            	$("#appIcon").attr("src", "ftp" +(jsonData.appUploadInfoVo.iconPath).replace(/\\/g, "/"));
		            	$('#bussName').text(jsonData.appUploadInfoVo.name);
		                $('#bussCode').text(jsonData.appUploadInfoVo.code);
		                $('#appVer').text(jsonData.appUploadInfoVo.version);
		                $('#appDesc').text(jsonData.appUploadInfoVo.description);
			            businessManage.data.uploadSucc = true;
			            $(".app-infro-text").show();
		            }
		            $(".uploadify-queue").html("");
		        },
		        onUploadComplete : function() {
                    $("#choose-apps").uploadify('disable', false);
		            if (businessManage.data.uploadSucc) {
		                $(".form-horizontal").hide();
		                businessManage.data.uploadSucc = false;
		            }
		        },
		        onFallback : function() {
		            toastr.error("未安装flash插件，无法进行文件上传");
		        },
                onCancel   : function() {
                    $(".close").show();
                }
		    });
	    }
	},
	businessTree	:
	{
		treeObj	: null,
		rMenu	: null,
		treeNode: null,
		setting : 
	    {
	        view: {
	            dblClickExpand: false,
                showTitle     : false
	        },
	        data		:
			{
				simpleData	:
				{
					enable	: true,
					idKey	: "id",
					pIdKey	: "parentid",
					rootPId	: null
				}
			},
	        check: {
	            enable: false
	        },
	        callback: {
				onRightClick : function(event, treeId, treeNode)
		        {
					businessManage.businessTree.treeObj.selectNode(treeNode);
		        	businessManage.businessTree.treeNode = treeNode;
		        	if (treeNode.isParent) {
		        		businessManage.businessTree.controlRMenu.showRMenu({
							x: event.clientX,
							y: event.clientY,
							isGroup:true
						});
		        		$(".app-infro-detail").hide();
		            }
		        	else
					{
		        		
						businessManage.businessTree.controlRMenu.showRMenu({
							x: event.clientX,
							y: event.clientY,
							isGroup:false
						});
						var nodeId=businessManage.businessTree.treeNode.id;
						var param = {id : nodeId};
				            //从服务器获取业务对应的应用信息
				            $.ajax({
				                url     : "system/business/main/loadAppInfoByBusinessId.action",
				                type    : "POST",
				                data	: param,
				                dataType: "json",				                
				                success : function(data) {
				                    if (data.result == true)
				                    {
				                        //填充右侧展示区
				                        $(".app-infro-detail").show();
				                        $("#curAppImg").attr("src", "ftp" +(data.appVo.iconPath).replace(/\\/g, "/"));
				                        $("#curBussName").text(businessManage.businessTree.treeNode.name);
				                        $("#curBussCode").text(businessManage.businessTree.treeNode.code);
				                        $("#curAppName").text(data.appVo.name);
				                        $("#curAppVersion").text(data.appVo.version);
				                        $("#detail").on("click",function(){
				                        	$("#detailUpdate").empty();				                        	
				                        	$('#detailModal').modal('show')
				                        });
				                    } else {
				                        toastr.error(data.errorMsg);
				                    }
				                },
				                error   : function(data) {				                	
				                    toastr.error("服务器通讯失败");
				                }
				            });
					}
		        },
		        onClick : function(event, treeId, treeNode)
		        {
		        	//单击父节点收放子目录
		        	var obj = $.fn.zTree.getZTreeObj("tree");
		        	obj.expandNode(treeNode);
		        	
		            if (treeNode.isParent) {
		            	$(".app-infro-detail").hide();
		                return;
		            }
		            businessManage.businessTree.treeNode = treeNode;
		            var param = {
		                id : businessManage.businessTree.treeNode.id
		            };
		            //从服务器获取业务对应的应用信息
		            $.ajax({
		                url     : "system/business/main/loadAppInfoByBusinessId.action",
		                type    : "POST",
		                dataType: "json",
		                data    : param,
		                success : function(data) {
		                    if (data.result == true)
		                    {
		                        //填充右侧展示区
		                    	$(".app-infro-detail").show();
		                        $("#curAppImg").attr("src", "ftp" +(data.appVo.iconPath).replace(/\\/g, "/"));
		                        $("#curBussName").text(businessManage.businessTree.treeNode.name);
		                        $("#curBussCode").text(businessManage.businessTree.treeNode.code);
		                        $("#curAppName").text(data.appVo.name);
		                        $("#curAppVersion").text(data.appVo.version);
		                        $("#detail").on("click",function(){
		                        	$("#detailUpdate").empty();				                        	
		                        	$('#detailModal').modal('show')
		                        });
		                    } else {
		                        toastr.error(data.errorMsg);
		                    }
		                },
		                error   : function(data) {
		                    toastr.error("服务器通讯失败");
		                }
		            });
		        }
	        }    
	    },
	    loadTree : function()
        {
            $.ajax({
                url     : "system/business/main/loadBusiness.action",
                type    : "POST",
                dataType: "json",
                success : function(data)
                {
                    if (data.result == true)
                    {
                        var newRoot = {name : "所有业务", id : 0, isParent : true};
                        data.businessVoList.push(newRoot);                
                        var treeObj = $.fn.zTree.init($("#tree"), businessManage.businessTree.setting, data.businessVoList);
                        var rootNode = treeObj.getNodeByParam("id", 0, null);
                        businessManage.businessTree.treeObj = treeObj;
						businessManage.businessTree.treeObj.expandNode(rootNode, true, true, true);
						businessManage.businessTree.rMenu = $("#rMenu");
                    } 
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data)
                {
                    toastr.error("服务器通讯失败");
                }
            });        
        },
        controlRMenu	:
        {
        	showRMenu : function(rMenuJson) 
	        {
	 			businessManage.businessTree.rMenu.show();
	 			businessManage.businessTree.rMenu.css({"top":rMenuJson.y+"px", "left":rMenuJson.x+"px", "visibility":"visible"});
				if (rMenuJson.isGroup) {
					$("#m_add_group").show();
					$("#m_add_app").show();
					$("#m_update_app").hide();
				} else {
					$("#m_add_group").hide();
					$("#m_add_app").hide();
					$("#m_update_app").show();
				}
	        }
        }
	},
	businessFunction	:
    {
        addGroupNode	: function()
        {
        	var nodeId = businessManage.businessTree.treeNode.id;
        	var groupName = $.trim($("#appGroupName").val());
        	//检测重名    
        	$.post("system/business/add/existBusinessGroup.action", {isParent:true , parentid:nodeId , name: groupName}, function(data)
        	{
				if(data.result == false)
				{
                    $('#addGroupModal').modal('hide');
                    $('#addGroupForm').validate().resetForm();
                    window.parent.hideLoading();
					toastr.error(data.errorMsg);
				}
				else
				{
					if(data.exist == true){
						toastr.warning(groupName+"业务组已存在,请重新输入");
						$('#addGroupModal').modal('hide');
						$('#addGroupForm').validate().resetForm();
						window.parent.hideLoading();
						$('#addAppsModal').modal('hide');
					}
					else{
						var nodeId = businessManage.businessTree.treeNode.id;
				        var param = {
				            isParent : true,
				            name     : groupName,
				            parentid : nodeId,
				            name     : groupName
				        };
				
				        $.ajax({
				            url     : "system/business/add/addBusinessGroup.action",
				            type    : "POST",
				            dataType: "json",
				            data    : param,
				            success : function(data) {
				                $('#addGroupModal').modal('hide');
	                            $('#addGroupForm').validate().resetForm();
	                            window.parent.hideLoading();
				                if (data.result == true)
				                {
				                    var newNode =
				                    {
				                    	id		 : data.businessVo.id,
				                        name     : groupName,
				                        isParent : true,
				                        parentid : nodeId
				                    };
				                    businessManage.businessTree.treeObj.addNodes(businessManage.businessTree.treeNode, newNode, false);                   
				                    toastr.success("新增业务组成功");  
				                } else {
				                    toastr.error(data.errorMsg);
				                }
				            },
				            error   : function(data) {   
				                $('#addGroupModal').modal('hide');
	                            $('#addGroupForm').validate().resetForm();
	                            window.parent.hideLoading();
				                toastr.error("服务器通讯失败");
				            }
				        });	
					}					
				}
			});
        },
        addBusiness	: function()
        {
        	var nodeId = businessManage.businessTree.treeNode.id;
        	var param = {
                isParent : false,
                parentid : nodeId
	        };
	        $.ajax({
	            url     : "system/business/add/addBusiness.action",
	            type    : "POST",
	            data	: param,
	            dataType: "json",
	            success : function(data) {
	                $(".form-horizontal").show();
	                $(".app-infro-text").hide();
	                $("#addAppsModal").modal("hide"); 
	                if (data.result == true)
	                {
	                        var newNode =
	                        {
	                        	id		 : data.businessVo.id,
	                            name     : data.businessVo.name,
	                            isParent : false,
	                            parentid : nodeId
	                        };
	                    businessManage.businessTree.treeObj.addNodes(businessManage.businessTree.treeNode, newNode, false);
	                    businessManage.businessTree.treeObj.getNodeByParam("id", data.businessVo.id, null).code=data.businessVo.code;
	                    toastr.success("APK版本文件新增成功");
	                } else {
	                    toastr.error(data.errorMsg);
	                }
	            },
	            error   : function(data) {
	                $(".form-horizontal").show();
	                $(".app-infro-text").hide();
	                $("#addAppsModal").modal("hide");                 
	                toastr.error("服务器通讯失败");
	            }
	        }); 
        },
        updateBusiness	: function()
        {
        	var nodeId = businessManage.businessTree.treeNode.id;
        	var param = {
                id     : nodeId,
	        };
	        $.ajax({
	            url     : "system/business/update/updateBusiness.action",
	            type    : "POST",
	            data	: param,
	            dataType: "json",
	            success : function(data) {
	                $(".form-horizontal").show();
	                $(".app-infro-text").hide();
	                $("#addAppsModal").modal("hide"); 
	                if (data.result == true)
	                {
	                    var updateNode = businessManage.businessTree.treeObj.getNodeByParam("id", businessManage.businessTree.treeNode.id, null);
	                	businessManage.businessTree.treeObj.updateNode(updateNode);
	                    toastr.success("APK版本文件更新成功");
						var param = {id : businessManage.businessTree.treeNode.id};
				            //从服务器获取业务对应的应用信息
				            $.ajax({
				                url     : "system/business/main/loadAppInfoByBusinessId.action",
				                type    : "POST",
				                data	: param,
				                dataType: "json",				                
				                success : function(data) {
				                    if (data.result == true)
				                    {
				                        //填充右侧展示区
				                        $(".app-infro-detail").show();
				                        $("#curAppImg").attr("src", "ftp" + (data.appVo.iconPath).replace(/\\/g, "/"));
				                        $("#curBussName").text(businessManage.businessTree.treeNode.name);
				                        $("#curBussCode").text(businessManage.businessTree.treeNode.code);
				                        $("#curAppName").text(data.appVo.name);
				                        $("#curAppVersion").text(data.appVo.version);
				                        $("#detail").on("click",function(){
				                        	$("#detailUpdate").empty();				                        	
				                        	$('#detailModal').modal('show')
				                        });
				                    } else {
				                        toastr.error(data.errorMsg);
				                    }
				                },
				                error   : function(data) {				                	
				                    toastr.error("服务器通讯失败");
				                }
				            });
	                    
	                } else {
	                    toastr.error(data.errorMsg);
	                }
	            },
	            error   : function(data) {
	                $(".form-horizontal").show();
	                $(".app-infro-text").hide();
	                $("#addAppsModal").modal("hide");                 
	                toastr.error("服务器通讯失败");
	            }
	        }); 
        },
        cancelSaveAppInfo		: function()
        {
        	$.post("system/business/add/cancelSaveAppInfo.action");
        },
        valid : function()
        {
            jQuery.validator.addMethod("checkNameLen", function(value, element) {
                var patrn = /[\u4e00-\u9fa5]/;
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    if (patrn.test(value[i])) {
                        count += 2;
                    } else {
                        count += 1;
                    }
                    if (count > 50) {
                        return false;
                    }
                }
                return true;
            });
            jQuery.validator.addMethod("checkName", function(value, element) {
                var patrn = /[0-9a-zA-Z\u4e00-\u9fa5]/;
                for (var i = 0; i < value.length; i++) {
                    if (patrn.test(value[i]) == false) {
                        return false;
                    }
                }
                return true;
            });
            $("#addGroupForm").validate({
                rules : {
                    appGroupName : {
                        checkNameLen : true,
                        checkName    : true                      
                    }
                },
                messages : {
                    appGroupName : {
                        required  : '请输入业务组名',
                        checkNameLen :'最大输入长度不能超过50',
                        checkName    : '业务组名含有非法字符'
                    }        
                },
                submitHandler : function() {
                    window.parent.showLoading();
                    setTimeout(function(){
                		window.parent.hideLoading();
                	},8000);
                    businessManage.businessFunction.addGroupNode();
                }
            });  
        }
    },
	data	:
    {
    	uploadSucc  : false,
    	isUpdate	: false
    }
};

$(document).ready(function() {
	businessManage.view.render(); 
	$("#detailModal").on('shown.bs.modal', function(){
    	var param = {
    		id : businessManage.businessTree.treeNode.id
    	}
        $.ajax({
            url  : "system/business/main/loadAppUpdateInfo.action",
            type : "POST",
            dataType : "json",
            data     : param,
            success  : function(data)
            {
                if (data.result == true)
                {            
                var updateInfo="";
                var str=data.appUpdateInfo; 
                while(str.indexOf("&")!=-1){
                	updateInfo+=str.substring(0,str.indexOf("&")+1);
                	updateInfo=updateInfo.replace(/&/,'----------');
                	str=str.substring(str.indexOf("&")+1);
                	updateInfo+=str.substring(0,str.indexOf("&")+1);
                	updateInfo=updateInfo.replace(/&/,'----------\r\n');
                	str=str.substring(str.indexOf("&")+1);
                }   
                updateInfo+=str;
                    $("#detailUpdate").text(updateInfo.replace(/#/g, '\r\n'));
                    $("#detailUpdate").text(updateInfo.replace(/%/g, '\r\n'));
                } else {
                    toastr.error(data.errorMsg);
                }
            },
            error    : function()
            {
                toastr.error("服务器通讯失败");
            }
        });
    });
	$('#detailModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
	});	
		
	$('#detailModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
	});
	$('#addGroupModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
	});	
		
	$('#addGroupModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
	});
	$('#addAppsModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
	});	
		
	$('#addAppsModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
	});
	$("#clseDetail").on('click', function() {
        $('#detailModal').modal('hide');
    }); 
	//监听业务组名不能超过50个字符
    $("#appGroupName").on('keyup',function(){
	var length=$("#appGroupName").val().length;
	var patrn = /[\u4e00-\u9fa5]/;
    var count = 0;
    for (var i = 0; i < length; i++) {
        if (patrn.test($("#appGroupName").val()[i])) {
            count += 2;
        } else {
            count += 1;
        }
        if (count > 50) {
        	$("#appGroupName").val($("#appGroupName").val().substr(0,i));
        	break;
        }
    }
    });
});
