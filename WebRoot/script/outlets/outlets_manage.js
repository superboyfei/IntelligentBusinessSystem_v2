var outletsManage = 
{
	view	: 
	{
		render	: function()
		{
			outletsManage.outletsTree.loadTree();
			outletsManage.view.initView.resize();
			outletsManage.view.initView.doResize();
			outletsManage.view.initEvent();
            outletsManage.outletsFunction.valid();
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
	            var ss = outletsManage.view.initView.getPageSize();
	            $(".content-wrap").height(ss.WinH - 80);
	            $(".outlet-infor-detail").height(ss.WinH - 80);
	            $(".group-infor-detail").height(ss.WinH - 80);
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
			            outletsManage.view.initView.doResize();
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
	    		outletsManage.data.isUpdate = false;
	    		outletsManage.data.isParent = true;
                $("#outletsGroupName").val("");
                $("#outletsGroupCode").val("");
                $('#groupModal .modal-title').text("新增网点组");
            	$('#groupModal').modal('show');
                $('#outletsGroupName').attr("disabled", false);
	        });
	        $("#m_update_group").on("click", function(){
	        	outletsManage.data.isUpdate = true;
	        	outletsManage.data.isParent = true;
                $("#outletsGroupName").val(outletsManage.outletsTree.treeNode.name);
                $("#outletsGroupDesc").val(outletsManage.outletsTree.treeNode.description);
                $("#outletsGroupCode").val(outletsManage.outletsTree.treeNode.code);
                $('#groupModal .modal-title').text("更新网点组");
                $('#groupModal').modal('show');
                $('#outletsGroupCode').attr("disabled",true);
            });
            //监听新增网点组关闭按钮
            $('#groupModal').on('hide.bs.modal', function() {
            	window.parent.hidecover();
                $('#groupForm').validate().resetForm();
            });
            $('#groupModal').on('show.bs.modal', function (e) {
            	window.parent.showcover();
            });	
            //监听新增网点关闭按钮
            $('#outletsModal').on('hide.bs.modal', function() {
            	window.parent.hidecover();
                $('#outletForm').validate().resetForm();
            });
            $('#outletsModal').on('show.bs.modal', function (e) {
            	window.parent.showcover();
            });
	        $("#m_add_outlets").on("click", function(){
	        	outletsManage.data.isUpdate = false;
	        	outletsManage.data.isParent = false;
	        	$("#outletsName").val("");
                $("#outletsCode").val("");
	        	$('#outletsModal .modal-title').text("新增网点");
	           	$('#outletsModal').modal('show');
	           	$('#outletsName').attr("disabled",false);
	           	$('#outletsCode').attr("disabled",false);
	        });
	        
	        $("#m_update_outlets").on("click", function(){
	        	outletsManage.data.isUpdate = true;
	        	outletsManage.data.isParent = false;
                $("#outletsName").val(outletsManage.outletsTree.treeNode.name);
                $("#outletsDesc").val(outletsManage.outletsTree.treeNode.description);
                $("#outletsCode").val(outletsManage.outletsTree.treeNode.code);
	        	$('#outletsModal .modal-title').text("更新网点");
	           	$('#outletsModal').modal('show');
	           	$('#outletsCode').attr("disabled",true);
	        });
	        
	        //监听右侧更新网点应用按钮
			$("#outlets-update-btn").on("click", function(){
				outletsManage.data.isUpdate = true;
				$("#outletsName").val($("#curOutletsName").text());
	        	$("#outletsDesc").val($("#curOutletsDesc").text());
                $("#outletsCode").val($("#curOutletsCode").text());
				$('#outletsModal .modal-title').text("更新网点");
		       	$('#outletsModal').modal('show');
		       	$('#outletsCode').attr("disabled",true);
			});	
			
			//监听右侧更新网点组应用按钮
			$("#group-update-btn").on("click", function(){
				outletsManage.data.isUpdate = true;
				$("#outletsGroupName").val($("#curGroupName").text());
	        	$("#outletsGroupDesc").val($("#curGroupDesc").text());
				$('#groupModal .modal-title').text("更新网点组");
		       	$('#groupModal').modal('show');
			});	
	    }
	},
	outletsTree	:
	{
		treeObj	: null,
		rMenu	: null,
		treeNode: null,
		setting : 
	    {
	        view: {
	            dblClickExpand: false
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
					outletsManage.outletsTree.treeObj.selectNode(treeNode);
		        	outletsManage.outletsTree.treeNode = treeNode;
		        	if(treeNode.isParent)
					{
		        			outletsManage.outletsTree.controlRMenu.showRMenu({
								x: event.clientX,
								y: event.clientY,
								isGroup:true
							});	
					}
					else
					{
						outletsManage.outletsTree.controlRMenu.showRMenu({
							x: event.clientX,
							y: event.clientY,
							isGroup:false
						});
					}
		        	
		        	if (treeNode.isParent && treeNode.id == 0)//根节点
		            {
		            	$("#outlet-infor-detail").hide();
                		$("#group-infor-detail").hide();
		                return;
		            }
		            else 
		            {
                        var param = {
                            id : treeNode.id
                        };
                        //从服务器获取网点对应的信息
                        $.ajax({
                            url     : "system/outlets/main/loadOutletsInfoById.action",
                            type    : "POST",
                            dataType: "json",
                            data     : param,
                            success : function(data) {
                                if (data.result == true)
                                {
                                	if(data.outletsVo.isParent == true){                                		
                                		$("#outlet-infor-detail").hide();
                                		$("#group-infor-detail").show();
                                        $("#curGroupName").text(data.outletsVo.name);
                                        $("#curGroupDesc").html((data.outletsVo.description).replace(/\n/g, "<br/>").replace(/\s/g,"&nbsp;"));                                       
                                	}
                                	if(data.outletsVo.isParent == false){
                                		$("#group-infor-detail").hide();
                                		$("#outlet-infor-detail").show();
                                        $("#curOutletsName").text(data.outletsVo.name);
                                        $("#curOutletsCode").text(data.outletsVo.code);
                                        $("#curOutletsDesc").html((data.outletsVo.description).replace(/\n/g, "<br/>").replace(/\s/g,"&nbsp;"));
                                	}
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
		            if (treeNode.isParent && treeNode.id == 0)//根节点
		            {
		            	$("#outlet-infor-detail").hide();
                		$("#group-infor-detail").hide();
		                return;
		            }
		            else 
		            {
                        outletsManage.outletsTree.treeNode = treeNode;
                        var param = {
                            id : treeNode.id
                        };
                        //从服务器获取网点对应的信息
                        $.ajax({
                            url     : "system/outlets/main/loadOutletsInfoById.action",
                            type    : "POST",
                            dataType: "json",
                            data     : param,
                            success : function(data) {
                                if (data.result == true)
                                {
                                	if(data.outletsVo.isParent == true){                                		
                                		$("#outlet-infor-detail").hide();
                                		$("#group-infor-detail").show();
                                        $("#curGroupName").text(data.outletsVo.name);
                                        $("#curGroupDesc").html((data.outletsVo.description).replace(/\n/g, "<br/>").replace(/\s/g,"&nbsp;"));                                       
                                	}
                                	if(data.outletsVo.isParent == false){
                                		$("#group-infor-detail").hide();
                                		$("#outlet-infor-detail").show();
                                        $("#curOutletsName").text(data.outletsVo.name);
                                        $("#curOutletsCode").text(data.outletsVo.code);
                                        $("#curOutletsDesc").html((data.outletsVo.description).replace(/\n/g, "<br/>").replace(/\s/g,"&nbsp;"));
                                	}
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
	        }    
	    },
	    loadTree : function()
        {
            $.ajax({
                url     : "system/outlets/main/loadOutlets.action",
                type    : "POST",
                dataType: "json",
                success : function(data)
                {
                    if (data.result == true)
                    {
                        var newRoot = {name : "所有网点", id : 0, isParent : true};
                        data.outletsVoList.push(newRoot);
                        var treeObj = $.fn.zTree.init($("#tree"), outletsManage.outletsTree.setting, data.outletsVoList);
                        var rootNode = treeObj.getNodeByParam("id", 0, null);
                        outletsManage.outletsTree.treeObj = treeObj;
						outletsManage.outletsTree.treeObj.expandNode(rootNode, true, true, true);
						outletsManage.outletsTree.rMenu = $("#rMenu");						
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
	 			outletsManage.outletsTree.rMenu.show();
	 			outletsManage.outletsTree.rMenu.css({"top":rMenuJson.y+"px", "left":rMenuJson.x+"px", "visibility":"visible"});
				if (rMenuJson.isGroup) {
					if(outletsManage.outletsTree.treeNode.id==0){
						$("#m_add_group").show();
						$("#m_update_group").hide();
						$("#m_add_outlets").show();
						$("#m_update_outlets").hide();
					}
					else{
						$("#m_add_group").show();
						$("#m_update_group").show();
						$("#m_add_outlets").show();
						$("#m_update_outlets").hide();
					}
				} else {
					$("#m_add_group").hide();
                    $("#m_update_group").hide();
					$("#m_add_outlets").hide();
					$("#m_update_outlets").show();
				}
	        }
        }
	},
	outletsFunction	:
    {
        addGroupNode	: function()
        {
        	var nodeId = outletsManage.outletsTree.treeNode.id;
        	var groupName = $.trim($("#outletsGroupName").val());
            var groupDesc = $.trim($("#outletsGroupDesc").val());
        	//检测重名
        	$.post("system/outlets/add/existOutletsGroup.action", {isParent: true,parentid: nodeId,name: groupName}, function(data)
        	{
				if (data.result == false)
				{
                    $('#groupModal').modal('hide');
                    $('#groupForm').validate().resetForm();
                    window.parent.hideLoading();
					toastr.error(data.errorMsg);
				}
				else
				{
					if(data.exist == true){
						toastr.warning("已存在相同的网点组");
						$('#groupModal').modal('hide');
						$('#groupForm').validate().resetForm();
						window.parent.hideLoading();
					}
					else{
						var nodeId = outletsManage.outletsTree.treeNode.id;
				        var param = {
				            isParent : outletsManage.data.isParent,
				            name     : groupName,
	                        description : groupDesc,
				            parentid : nodeId
				        };
				
				        $.ajax({
				            url     : "system/outlets/add/addOutletsGroup.action",
				            type    : "POST",
				            dataType: "json",
				            data    : param,
				            success : function(data) {
				                $('#groupModal').modal('hide');
	                            $('#groupForm').validate().resetForm();
	                            window.parent.hideLoading();
				                if (data.result == true)
				                {
				                    var newNode =
				                    {              		
				                    	id		 : data.outletsVo.id,
				                        name     : groupName,
				                        isParent : true,
				                        parentid : nodeId
				                    };
				                    newNode.code="-1";
				                    outletsManage.outletsTree.treeObj.addNodes(outletsManage.outletsTree.treeNode, newNode, false);  
				                    toastr.success("新增网点组成功");
				                } else {
				                    toastr.error(data.errorMsg);
				                }
				            },
				            error   : function(data) {    
				                $('#groupModal').modal('hide');
	                            $('#groupForm').validate().resetForm();
	                            window.parent.hideLoading();
				                toastr.error("服务器通讯失败");
				            }
				        });
					}					
				}
			});
        },
        updateGroupNode	: function()
        {
        	var groupName = $("#outletsGroupName").val();
        	var groupCode = outletsManage.outletsTree.treeNode.code;
        	var groupDesc = $("#outletsGroupDesc").val();
        	var nodeId = outletsManage.outletsTree.treeNode.parentid;
        	if(groupName!=outletsManage.outletsTree.treeNode.name){        		
        		$.post("system/outlets/update/existName.action",{code:groupCode,isParent: true,parentid: nodeId,name: groupName},function(data)
        	        	{
        	        		if(data.result == false)
        					{
        	                $('#groupModal').modal('hide');
        	                $('#groupForm').validate().resetForm();
        	                window.parent.hideLoading();
        					toastr.error(data.errorMsg);
        				    }
        	        		else{
        	        			if(data.exist == true){
        	        				toastr.warning("已存在相同的网点组");
        							$('#groupModal').modal('hide');
        							$('#groupForm').validate().resetForm();
        							window.parent.hideLoading();
        	        			}
        	        			else{
        	        				var nodeId=outletsManage.outletsTree.treeNode.id;
        	        				var param = {
        	        						name : groupName,
        	        						description : groupDesc,
        	        						code : groupCode,
        	        						id : nodeId
        						        };
        						        $.ajax({
        						            url     : "system/outlets/update/updateOutlets.action",
        						            type    : "POST",
        						            data	: param,
        						            dataType: "json",
        						            success : function(data) {
        						                $("#outletsModal").modal("hide"); 
        					                    $('#outletForm').validate().resetForm();
        					                    window.parent.hideLoading();
        						                if (data.result == true)
        						                {
        						                	var updateNode = outletsManage.outletsTree.treeObj.getNodeByParam("id", nodeId, null);
        					                		updateNode.name = groupName;
        					                		updateNode.code = groupCode;
        					                		updateNode.description = groupDesc;
        					                		outletsManage.outletsTree.treeObj.updateNode(updateNode);
        					                		$("#curGroupName").text(groupName);
        					                        $("#curGroupCode").text(groupCode);
        					                        $("#curGroupDesc").html((groupDesc).replace(/#/g, '<br/>').replace(/\s/g,"&nbsp;"));
        						                    toastr.success("更新网点组成功");
        						                    $('#groupModal').modal('hide');
        						                } else {
        						                    toastr.error(data.errorMsg);
        						                }
        						            },
        						            error   : function(data) {
        						                $(".form-horizontal").show();
        						                $(".app-infro-text").hide();
        						                $("#outletsModal").modal("hide");    
        					                    $('#outletForm').validate().resetForm();
        					                    window.parent.hideLoading();
        						                toastr.error("服务器通讯失败");
        						            }
        						        });     
        	        			}
        	        		}
        	        });
        	}
        	else{
        		var nodeId=outletsManage.outletsTree.treeNode.id;
				var param = {
						name : groupName,
						description : groupDesc,
						code : groupCode,
						id : nodeId
			        };
			        $.ajax({
			            url     : "system/outlets/update/updateOutlets.action",
			            type    : "POST",
			            data	: param,
			            dataType: "json",
			            success : function(data) {
			                $("#outletsModal").modal("hide"); 
		                    $('#outletForm').validate().resetForm();
		                    window.parent.hideLoading();
			                if (data.result == true)
			                {
			                	var updateNode = outletsManage.outletsTree.treeObj.getNodeByParam("id", nodeId, null);
		                		updateNode.name = groupName;
		                		updateNode.code = groupCode;
		                		updateNode.description = groupDesc;
		                		outletsManage.outletsTree.treeObj.updateNode(updateNode);
		                		$("#curGroupName").text(groupName);
		                        $("#curGroupCode").text(groupCode);
		                        $("#curGroupDesc").html((groupDesc).replace(/#/g, '<br/>').replace(/\s/g,"&nbsp;"));
			                    toastr.success("更新网点组成功");
			                    $('#groupModal').modal('hide');
			                } else {
			                    toastr.error(data.errorMsg);
			                }
			            },
			            error   : function(data) {
			                $(".form-horizontal").show();
			                $(".app-infro-text").hide();
			                $("#outletsModal").modal("hide");    
		                    $('#outletForm').validate().resetForm();
		                    window.parent.hideLoading();
			                toastr.error("服务器通讯失败");
			            }
			        });
        	}       	
        },
		addOutletsNode	: function()
        {
        	var outletsName = $("#outletsName").val();
        	var outletsDesc = $("#outletsDesc").val();
            var outletsCode = $("#outletsCode").val();
        	var nodeId = outletsManage.outletsTree.treeNode.id;
        	$.post("system/outlets/add/existOutlets.action", {code:outletsCode,isParent: false,parentid: nodeId,name: outletsName}, function(data)
        	{
				if (data.result == false)
				{
                    $('#outletsModal').modal('hide');
                    $('#outletForm').validate().resetForm();
                    window.parent.hideLoading();
					toastr.error(data.errorMsg);
				}
				else
				{
					if(data.exist == true){
						toastr.warning("已存在相同的网点编号");
						$('#groupModal').modal('hide');
						$('#groupForm').validate().resetForm();
						window.parent.hideLoading();
						$('#outletsModal').modal('hide');
					}
					else{
						var param = {
				                name     : outletsName,
		                        code     : outletsCode,
				                description : outletsDesc,
				                isParent : outletsManage.data.isParent,
				                parentid : nodeId
					        };
					        $.ajax({
					            url     : "system/outlets/add/addOutlets.action",
					            type    : "POST",
					            data	: param,
					            dataType: "json",
					            success : function(data) {
					                $("#outletsModal").modal("hide"); 
		                            $('#outletForm').validate().resetForm();
		                            window.parent.hideLoading();
					                if (data.result == true)
					                {
				                        var newNode =
				                        {
				                        	id		 : data.outletsVo.id,
				                            name     : data.outletsVo.name,
				                            isParent : false,
				                            parentid : nodeId
				                        };
				                        outletsManage.outletsTree.treeObj.addNodes(outletsManage.outletsTree.treeNode, newNode, false);
					                    toastr.success("新增网点成功");
					                } 
					                else
					                {
					                    toastr.error(data.errorMsg);
					                }
					            },
					            error   : function(data) {
					                $(".form-horizontal").show();
					                $(".app-infro-text").hide();
					                $("#outletsModal").modal("hide");    
		                            $('#outletForm').validate().resetForm();
		                            window.parent.hideLoading();
					                toastr.error("服务器通讯失败");
					            }
					        }); 
					}		        	
				}
        	});
        },
        updateOutletsNode	: function()
        {
            var outletsCode = $("#outletsCode").val();
        	var outletsName = $("#outletsName").val();
        	var outletsDesc = $("#outletsDesc").val();
        	var nodeId = outletsManage.outletsTree.treeNode.id;    				
        						var param = {
        								name : outletsName,
        								description : outletsDesc,
        								code : outletsCode,
        								id : nodeId
        					        };
        					        $.ajax({
        					            url     : "system/outlets/update/updateOutlets.action",
        					            type    : "POST",
        					            data	: param,
        					            dataType: "json",
        					            success : function(data) {
        					                $("#outletsModal").modal("hide"); 
        				                    $('#outletForm').validate().resetForm();
        				                    window.parent.hideLoading();
        					                if (data.result == true)
        					                {
        				                		var updateNode = outletsManage.outletsTree.treeObj.getNodeByParam("id", nodeId, null);
        				                		updateNode.name = outletsName;
        				                		updateNode.code = outletsCode;
        				                		updateNode.description = outletsDesc;
        				                		outletsManage.outletsTree.treeObj.updateNode(updateNode);
        				                        $("#curOutletsName").text(outletsName);
        					                	$("#curOutletsCode").text(outletsCode);
        				                        $("#curOutletsDesc").html((outletsDesc).replace(/#/g, '<br/>').replace(/\s/g,"&nbsp;"));
        					                    toastr.success("更新网点成功");
        					                } else {
        					                    toastr.error(data.errorMsg);
        					                }
        					            },
        					            error   : function(data) {
        					                $(".form-horizontal").show();
        					                $(".app-infro-text").hide();
        					                $("#outletsModal").modal("hide");    
        				                    $('#outletForm').validate().resetForm();
        				                    window.parent.hideLoading();
        					                toastr.error("服务器通讯失败");
        					            }
        					        });     				               	
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
            jQuery.validator.addMethod("checkCode", function(value, element) {
                var patrn = /[0-9a-zA-Z]/;
                for (var i = 0; i < value.length; i++) {
                    if (patrn.test(value[i]) == false) {
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
            jQuery.validator.addMethod("checkDesc", function(value, element) {
                var patrn = /[0-9a-zA-Z\u4e00-\u9fa5]/;
                for (var i = 0; i < value.length; i++) {
                    if (patrn.test(value[i]) == false) {
                        return false;
                    }
                }
                return true;
            });
            jQuery.validator.addMethod("checkDescLen", function(value, element) {
                var patrn = /[\u4e00-\u9fa5]/;
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    if (patrn.test(value[i])) {
                        count += 2;
                    } else {
                        count += 1;
                    }
                    if (count > 256) {
                        return false;
                    }
                }
                return true;
            });    
            
            $("#groupForm").validate({
                rules : {
                    outletsGroupName : {
                        checkNameLen : true,
                        checkName    : true
                    },
                    outletsGroupDesc : {
                    	checkDescLen : true,
                    	checkDesc    : true
                    }
                },
                messages : {
                    outletsGroupName : {
                        required  : '请输入网点组名',
                        checkNameLen :'最大输入长度不能超过50',
                        checkName    :'网点组名含有非法字符'
                    },
                    outletsGroupDesc : {
                    	checkDescLen : '最大输入长度不能超过256',
                    	checkDesc    : '描述含有非法字符'
                    }
                },
                submitHandler : function() {
                    window.parent.showLoading();
                    setTimeout(function(){
                		window.parent.hideLoading();
                	},8000);
                    if(outletsManage.data.isUpdate == true)
                    {
                    	//更新
	                    outletsManage.outletsFunction.updateGroupNode();
                    }
                    else
                    {
                    	//新增
                    	outletsManage.outletsFunction.addGroupNode();
                    }
                }
            });
            $("#outletForm").validate({
                rules : {
                    outletsName : {
                        checkNameLen : true,
                        checkName    : true
                    },
                    outletsCode : {
                        checkCode : true
                    },
                    outletsDesc : {
                    	checkDescLen : true,
                    	checkDesc    :true
                    }
                },
                messages : {
                    outletsName : {
                        required  : '请输入网点名称',
                        checkNameLen :'最大输入长度不能超过50',
                        checkName    : '网点名含有非法字符'
                    },
                    outletsCode : {
                        required  : '请输入网点编号',
                        checkNameLen :'最大输入长度不能超过20',
                        checkCode : '编号含有非法字符'
                    },
                    outletsDesc : {
                    	checkDescLen : '最大输入长度不能超过256',
                    	checkDesc    : '描述含有非法字符'	
                    }
                },
                submitHandler : function() {
                    window.parent.showLoading();
                    setTimeout(function(){
                		window.parent.hideLoading();
                	},8000);
                    if(outletsManage.data.isUpdate == true)
                    {
                    	//更新
	                    outletsManage.outletsFunction.updateOutletsNode();
                    }
                    else
                    {
                    	//新增
                    	outletsManage.outletsFunction.addOutletsNode();
                    }
                }
            });
        }
    },
	data	:
    {
    	uploadSucc  : false,
    	isUpdate	: false,
    	isParent	: false
    }
};

$(document).ready(function() {
	outletsManage.view.render();
	//监听网点组名不能超过50个字符
    $("#outletsGroupName").on('keyup',function(){
//    	var length=$("#outletsGroupName").val().length;
//    	var patrn2 = /[0-9a-zA-Z\u4e00-\u9fa5]/;
//    	for (var i = 0; i < length; i++) {
//            if (!patrn2.test($("#outletsGroupName").val()[i])) {
//            	$("#outletsGroupName").val($("#outletsGroupName").val().substr(0,i));
//            	break;
//            } 
//        }
    	length=$("#outletsGroupName").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
    	var count = 0;
    	for (var i = 0; i < length; i++) {
    		if (patrn.test($("#outletsGroupName").val()[i])) {
    			count += 2;
    		} else {
    			count += 1;
    		}
    		if (count > 50) {
    			$("#outletsGroupName").val($("#outletsGroupName").val().substr(0,i));
    			break;
    		}
    	}
    });
  //监听网点名不能超过50个字符
    $("#outletsName").on('keyup',function(){
//    	var length=$("#outletsName").val().length;
//    	var patrn2 = /[0-9a-zA-Z\u4e00-\u9fa5]/;
//    	for (var i = 0; i < length; i++) {
//    		if (!patrn2.test($("#outletsName").val()[i])) {
//    			$("#outletsName").val($("#outletsName").val().substr(0,i));
//    			break;
//    		} 
//    	}
    	length=$("#outletsName").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
    	var count = 0;
    	for (var i = 0; i < length; i++) {
    		if (patrn.test($("#outletsName").val()[i])) {
    			count += 2;
    		} else {
    			count += 1;
    		}
    		if (count > 50) {
    			$("#outletsName").val($("#outletsName").val().substr(0,i));
    			break;
    		}
    	}
    });
  //监听网点描述不能超过50个字符
    $("#outletsGroupDesc").on('keyup',function(){
    	var length=$("#outletsGroupDesc").val().length;
//    	var patrn2 = /[0-9a-zA-Z\u4e00-\u9fa5\s\#]/;
//    	for (var i = 0; i < length; i++) {
//    		if (!patrn2.test($("#outletsGroupDesc").val()[i])) {
//    			$("#outletsGroupDesc").val($("#outletsGroupDesc").val().substr(0,i));
//    			break;
//    		} 
//    	}
//    	length=$("#outletsGroupDesc").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
    	var count = 0;
    	for (var i = 0; i < length; i++) {
    		if (patrn.test($("#outletsGroupDesc").val()[i])) {
    			count += 2;
    		} else {
    			count += 1;
    		}
    		if (count > 256) {
    			$("#outletsGroupDesc").val($("#outletsGroupDesc").val().substr(0,i));
    			break;
    		}
    	}
    });
  //监听网点组描述不能超过50个字符
    $("#outletsDesc").on('keyup',function(){
    	var length=$("#outletsDesc").val().length;
//    	var patrn2 = /[0-9a-zA-Z\u4e00-\u9fa5]/;
//    	for (var i = 0; i < length; i++) {
//    		if (!patrn2.test($("#outletsDesc").val()[i])) {
//    			$("#outletsDesc").val($("#outletsDesc").val().substr(0,i));
//    			break;
//    		} 
//    	}
//    	length=$("#outletsDesc").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
    	var count = 0;
    	for (var i = 0; i < length; i++) {
    		if (patrn.test($("#outletsDesc").val()[i])) {
    			count += 2;
    		} else {
    			count += 1;
    		}	
    		if (count > 256) {
    			$("#outletsDesc").val($("#outletsDesc").val().substr(0,i));
    			break;
    		}
    	}
    });
});
