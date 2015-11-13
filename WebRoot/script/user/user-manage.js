var userManage = {

	userGrid	:
	{
		initEvent		: function()
		{
			//数据加载完成后为编辑，设置权限，删除链接绑定事件
			$('a[id=userCtrl]').each(function() {
				$(this).on('click', function() {
					userManage.userGrid.initData.call(this);
					if ($(this).text() == "编辑")
                    {
                        $("#edit-user-isadmin").attr("disabled", false);
                        if (userManage.data.user.isadmin == 1)
						{
                            $("#edit-user-isadmin").attr("disabled", true);
                        }
                    }
                    
                    if ($(this).text() == "权限设置")
					{
						$("#show-user-name").html(userManage.data.user.name);
						$("#auth-tip").hide();
						$("#saveUserAuthBtn").attr("disabled", false);
						if (userManage.data.user.isadmin == 1)
						{
							$("#auth-tip").show();
							$("#saveUserAuthBtn").attr("disabled", true);
						}
                        window.parent.showLoading();
                        setTimeout(function(){
                    		window.parent.hideLoading();
                    	},8000);
						authority.treeFunction.authTree.loadAuthTree();
					}
				});
			});
		},
		initData	: function()
		{
			var $td = $(this).parent();
			var id = $td.siblings("td:eq(0)").text();
			var uid = $td.siblings("td:eq(1)").text();
			var name = $td.siblings("td:eq(2)").text();
            var isadmin = $td.siblings("td:eq(3)").text();
			userManage.data.user.id = id;
			userManage.data.user.uid = uid;
			userManage.data.user.name = name;
            userManage.data.user.isadmin = isadmin;
			$("#edit-user-id").val(userManage.data.user.uid);
			$("#edit-user-name").val(userManage.data.user.name);			
			if (userManage.data.user.isadmin == 1)
			{
				$("#edit-user-isadmin").attr("checked", true);
			} else {
				$("#edit-user-isadmin").attr("checked", false);
			}
		},
		loadUserGrid	: function()
		{
			window.parent.showLoading();
			setTimeout(function(){
        		window.parent.hideLoading();
        	},8000);
			$("#user-jqgrid").jqGrid(
			{
				url			: 'system/user/main/loadUsersByPage.action',
				datatype	: 'json',
				postData    : {matchUid : ""},
				colNames	: ['索引', '用户账号', '用户名称', '管理员', '', '', ''],
				colModel	: [
				{
					name	: 'id',
					index	: 'id',
					width	: 150,
					hidden	: true,
					align	: 'center',
                    sortable: false
				},
				{
					name	: 'uid',
					index	: 'uid',
					width	: 150,
					align	: 'center',
                    sortable: false,
                    resizable:false
				},
				{
					name	: 'name',
					index	: 'name',
					width	: 150,
					align	: 'center',
                    sortable: false,
                    resizable:false
				},
                {
                    name    : 'isadmin',
                    index   : 'isadmin',
                    width   : 150,
                    hidden  : true,
                    align	: 'center',
                    sortable: false
                },
                {
                    name    : 'edit',
                    index   : 'edit',
                    align   : 'center',
                    width   : 40,
                    sortable: false,
                    resizable:false
                },
                {
                    name    : 'act',
                    index   : 'act',
                    align   : 'center',
                    width   : 40,
                    sortable: false,
                    resizable:false
                },
                {
                    name    : 'del',
                    index   : 'del',
                    width   : 40,
                    align   : 'center',
                    sortable: false,
                    resizable:false
                }],
				rowList 		: [10, 20, 30, 40, 50, 100],
				viewrecords		: true,
				altRows			: true,
				altclass        : 'ui-widget-content-altclass',
				autowidth	    : true,
				rowNum		    : 20,
				pager		    : "#user-jqgrid-page",
				jsonReader	    :
				{
					root		: "tableVo.list",
					total		: "tableVo.totalpages",
					page		: "tableVo.currpage",
					records		: "tableVo.totalrecords",
					repeatitems	: false
				},
				gridComplete    : function() {
		            var ids = $("#user-jqgrid").getDataIDs();
		            for (var i = 0; i < ids.length; i++) {
                        ed = "<a id='userCtrl' href='javascript:void(0);' class='blue' data-toggle='modal' data-target='#editUserModal'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span>编辑</a>"; 
		                col= "<a id='userCtrl' href='javascript:void(0);' class='blue' data-toggle='modal' data-target='#UserSetupModal' ><span class='glyphicon glyphicon-check' aria-hidden='true'></span>权限设置</a>"; 
                        var isadmin = $("#user-jqgrid").getRowData(ids[i]).isadmin;
                        if (isadmin == 1)
                        {
                            de = "<a href='javascript:void(0);' class='linkDisable'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span>删除</a>";
                        } else {
                        	de = "<a id='userCtrl' href='javascript:void(0);' class='blue' data-toggle='modal' data-target='#delUserModal' ><span class='glyphicon glyphicon-trash' aria-hidden='true'></span>删除</a>";
                        }

		                jQuery("#user-jqgrid").jqGrid('setRowData', ids[i], {edit : ed, act : col, del : de});
		            }
		            //需要在数据初始化完成后初始化事件
		            window.parent.hideLoading();
		            if(ids.length==0){		            	
		            	var left=(document.documentElement.clientWidth-60)/2;
		            	var top=(document.documentElement.clientHeight)/2;
		            	$("#showNoData").css("top",top);
		            	$("#showNoData").css("left",left);
		            	$("#showNoData").css("display","block");
		            }
		            if(ids.length!=0){
		            	$("#showNoData").css("display","none");
		            }
		            userManage.userGrid.initEvent();		            
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
				}
			});
			userManage.initUIOption.doResize();
		}
	},
	userControl	:
	{
		saveUser	: function()
		{            
			var uid = $('#newUserForm input[name=uid]').val();
			var name = $('#newUserForm input[name=name]').val();
			var password = $('#newUserForm input[name=password]').val();
			var param = {
				uid		: uid,
				name	: name,
				password: password
			};
            $.ajax({
                url     : "system/user/add/saveUser.action",
                type    : "POST",
                dataType: "json",
                data    : param,
                success : function(data) {
                    $("#newUserModal").modal('hide');
                    $('#newUserForm').validate().resetForm();
                    $('#uidMsg').hide();
                    window.parent.hideLoading();
                    if (data.result == true)
                    {
                        toastr.success('新增用户成功,请设置用户权限');
                        //更新界面数据
                        $("#user-jqgrid").trigger("reloadGrid");
                        $('#UserSetupModal').modal();
                        //显示权限提示框
                        $("#show-user-name").html(name);
						$("#auth-tip").hide();
                        window.parent.showLoading();
                        setTimeout(function(){
                    		window.parent.hideLoading();
                    	},8000);
                        userManage.data.user.id = data.userVo.id;//保存新增用户的id
						authority.treeFunction.authTree.loadAuthTree();
                    }
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    $("#newUserModal").modal('hide');
                    $('#newUserForm').validate().resetForm();
                    $('#uidMsg').hide();
                    window.parent.hideLoading();
                    toastr.error("服务器通讯失败");
                }
            });             
		},
		updateUser	: function(newpass)
		{
			var name = $('#updateUserForm input[name=name]').val();
			var param = {
				id		  : userManage.data.user.id,
				uid		  : userManage.data.user.uid,
				name	  : name,
                newPass   : newpass
			};
            $.ajax({
                url     : "system/user/update/updateUser.action",
                type    : "POST",
                dataType: "json",
                data    : param,
                success : function(data) {
                    $("#editUserModal").modal('hide');
                    $('#updateUserForm').validate().resetForm();
                    window.parent.hideLoading();
                    if (data.result == true)
                    {
                        toastr.success('更新用户成功');
                        //更新界面数据
                        $("#user-jqgrid").trigger("reloadGrid");
                        if(userManage.data.user.uid=="admin"){
                        	parent.document.getElementById("name").innerHTML=name;
                        }                        
                    }
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    $("#editUserModal").modal('hide');
                    $('#updateUserForm').validate().resetForm();
                    window.parent.hideLoading();
                    toastr.error("服务器通讯失败");
                }
            });            
		},
		deleteUser	: function()
		{	
			var param = {
				id	: userManage.data.user.id
			};
            $.ajax({
                url     : "system/user/main/delUser.action",
                type    : "POST",
                dataType: "json",
                data    : param,
                success : function(data) {
                    $('#delUserModal').modal('hide');
                    window.parent.hideLoading();               
                    if (data.result == true)
                    {
                        toastr.success('删除用户成功');
                        //更新界面数据
                        $("#user-jqgrid").trigger("reloadGrid");
                    }
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    $('#delUserModal').modal('hide');
                    window.parent.hideLoading();
                    toastr.error("服务器通讯失败");
                }
            });            
		}
	},
	initUIOption:
	{
		//为窗口绑定自动放缩事件
		resizeWindow	: function()
		{
			var width = document.documentElement.clientWidth;
			var height = document.documentElement.clientHeight;
            $(window).resize(function()
			{
				if (width != document.documentElement.clientWidth
                   || height != document.documentElement.clientHeight)
				{
					width = document.documentElement.clientWidth;
                    height = document.documentElement.clientHeight;
					userManage.initUIOption.doResize();
					var left=(document.documentElement.clientWidth-60)/2;
			    	var top=(document.documentElement.clientHeight)/2;
			    	$("#showNoData").css("top",top);
			    	$("#showNoData").css("left",left);
				}
			});
		},
		doResize	: function ()
		{
			var ss = userManage.initUIOption.getPageSize();
			$("#user-jqgrid").jqGrid('setGridWidth', ss.WinW - 20)
			$("#user-jqgrid").jqGrid('setGridHeight', ss.WinH - 130);
		},
		getPageSize	: function()
		{
			var winW, winH;
			if (window.innerHeight)
			{// all except IE
				winW = window.innerWidth;
				winH = window.innerHeight;
			}
			else if (document.documentElement
					&& document.documentElement.clientHeight)
			{// IE 6 Strict Mode
				winW = document.documentElement.clientWidth;
				winH = document.documentElement.clientHeight;
			}
			else if (document.body)
			{ // other
				winW = document.body.clientWidth;
				winH = document.body.clientHeight;
			} // for small pages with total size less then the viewport
			return {
				WinW	:	winW,
				WinH 	: 	winH
			};
		}
	},
	data		:
	{
		//用户数据封装
		user	:
		{
			id			: '',
			uid			: '',
			name		: '',
            isadmin     : ''
		},
	}
}

$(document).ready(function()
{   
	if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1){
	    $('#user-name').hide();   
	    $('#password-text').hide(); 
	    $('#user-name2').show();   
	    $('#password-text2').show(); 
	    setTimeout(function(){
	    	$('#user-name2').hide();   
		    $('#password-text2').hide();
		    $('#user-name').show();   
		    $('#password-text').show(); 
	    },100)
	    } 

	$("#user-name").val("");
	$("#password-text").val("");
	//jqgrid默认没有填充屏幕，需要手动进行调整
	userManage.initUIOption.resizeWindow();
	userManage.userGrid.loadUserGrid();
	
	function clearUserId(){
		$("#user_id_div").removeClass("has-error has-feedback").removeClass("has-success has-feedback");
		$("#user_id_success").remove();
		$("#user_id_error").remove();
	}
	
	//新增用户：验证用户账号的唯一性
	var uniqueUid = true, checkUid = true, confirmPass = true;
	$('#user-id').on('blur', function()
	{
		var uid = $(this).val();
		if (uid == "" || checkUid == false)
		{
			clearUserId();
			$("#uidMsg").empty();
			return false;
		}
		var param = {"uid" : uid};
		$.ajax({
			url     : "system/user/add/validateUid.action",
			type    : "POST",
			dataType: "json",
			data    : param,
			async   : false,
			success : function(data) {
				if (data.result == true)
				{
			     	$("#uidMsg").text(data.message).css("color", "#3C763D").show();
			     	clearUserId();
			     	$("#user_id_div").addClass("has-success has-feedback");
			     	$("<span id='user_id_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#user_id_div")[0]);
			     	uniqueUid = true;
				}
				else
				{
					$("#uidMsg").text(data.message).css("color", "#EC6F6F").show();
					clearUserId();
					$("#user_id_div").addClass("has-error has-feedback");
					$("<span id='user_id_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#user_id_div")[0]);
					uniqueUid = false;
		   	    }
            },
            error : function(data) {
                toastr.error("服务器通讯失败");
            }
        });
	});    
    
    //自定义验证规则
    jQuery.validator.addMethod("checkUid", function(value, element) {
        var patrn = /[0-9a-zA-Z\u4e00-\u9fa5]/;
        for (var i = 0; i < value.length; i++) {
            if (patrn.test(value[i]) == false) {
                checkUid = false;
                clearUserId();
                $("#uidMsg").empty();
                return false;
            }
        }
        checkUid = true;
        return true;
    });
    jQuery.validator.addMethod("checkUidLen", function(value, element) {
        var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < value.length; i++) {
            if (patrn.test(value[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 40) {
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
    jQuery.validator.addMethod("checkPass", function(value, element) {
        var patrn = /[0-9a-zA-Z~!@#%&*-_+]/;
        for (var i = 0; i < value.length; i++) {
            if (patrn.test(value[i]) == false) {
                return false;
            }
        }
        return true;
    });
    jQuery.validator.addMethod("checkNameLen", function(value, element) {
        var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < value.length; i++) {
            if (patrn.test(value[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 20) {
                return false;
            }
        }
        return true;
    });    
    jQuery.validator.addMethod("confirmPass", function(value, element) {
    	if($("#password-text").val() != $("#password-text-check").val()) {
    		return false;
    	} else {
    		return true;
    	}
    });    
    
	//新增用户
    var newUserVal = $("#newUserForm").validate({
        rules : {
            uid : {
                checkUid : true,
                checkUidLen : true,
            },
            name : {
                checkName : true,
            },
            password : {
                minlength : 6,
                checkPass : true
            },
            passwordCheck : {
            	confirmPass : true
            }
        },
        messages : {
            uid  : {
            	required  : '请输入用户账号',
                checkUid  : '用户账号含有非法字符',
                checkUidLen : '最大输入长度不能超过40'
            },
            name : {
            	required  : '请输入用户名称',
                checkName : '用户名含有非法字符'
            },
            password : {
            	required  : '请输入密码',
                minlength : '请输入至少6位的密码',
                checkPass : '用户密码含有非法字符'
            },
            passwordCheck : {
            	required  : '请重新输入密码',
            	confirmPass: '密码不一致，请重新输入'
            }
        },
        submitHandler : function() {
            if (uniqueUid == false)
            {
                return false;
            }
            if (confirmPass == false)
        	{
            	return false;
        	}
            else
            {
                window.parent.showLoading();
                setTimeout(function(){
            		window.parent.hideLoading();
            	},8000);
                userManage.userControl.saveUser();
            }            
        }
    });   
   
    $('#delUserModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    $("#user-id").on('keyup',function(){
    	var length=$("#user-id").val().length;
    	if(length == 0){
    		clearUserId();
    		$('#uidMsg').hide();
    	}
    	length=$("#user-id").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < length; i++) {
            if (patrn.test($("#user-id").val()[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 40) {
            	$("#user-id").val($("#user-id").val().substr(0,i));
            	break;
            }
        }
    });
    $("#user-name").on('keyup',function(){
    	var length=$("#user-name").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < length; i++) {
            if (patrn.test($("#user-name").val()[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 20) {
            	$("#user-name").val($("#user-name").val().substr(0,i));
            	break;
            }  
        }
    });
    $("#edit-user-name").on('keyup',function(){
    	var length=$("#edit-user-name").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < length; i++) {
            if (patrn.test($("#edit-user-name").val()[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 20) {
            	$("#edit-user-name").val($("#edit-user-name").val().substr(0,i));
            	break;
            }
        }
    });
    $('#delUserModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });
    $('#newUserCancelBtn').on('click', function()
	{
        newUserVal.resetForm();
        clearUserId();
		$('#uidMsg').hide();
	});
    $('#UserSetupModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    	
    $('#UserSetupModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });	
	//监听newUserModal的右上角关闭按钮的事件
	$('#newUserModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
        newUserVal.resetForm();
        clearUserId();
		$('#uidMsg').hide();
	});
	$('#newUserModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
	});	
		
    var resetMsg =  $("<div class='alert alert-warning' role='alert'></div>");
    var newpass = "";
    $('#reset-pass').on('click', function() {
        var resetPass = $('#updateUserForm input[name=resetPass]').is(':checked');
        if (resetPass == true) {
            newpass = "";
            for (var i = 0; i < 6; i++) {
                newpass += Math.round(Math.random() * 9);
            }
            resetMsg.text("*密码将重置为" + newpass);
            resetMsg.appendTo($('#resetForm'));
        } else {
            newpass = "";
            resetMsg.remove();
        }
    });
	
    $('#updateUserCancelBtn').on('click', function() {
        updateUserVal.resetForm();
        resetMsg.remove();
        newpass = "";
	});
    
    //监听editUserModal的右上角关闭按钮的事件
	$('#editUserModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
        updateUserVal.resetForm();
        resetMsg.remove();
        newpass = "";
	});
	

	$('#editUserModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
	});
    
    var updateUserVal = $("#updateUserForm").validate({
        rules : {
            name : {
            	checkName : true,
                checkNameLen : true
            }
        },
        messages : {
            name : {
            	required  : '请输入用户名称',
            	checkName : '用户名含有非法字符',
                checkNameLen :'最大输入长度不能超过20'
            }        
        },
        submitHandler : function() {
            window.parent.showLoading();
            setTimeout(function(){
        		window.parent.hideLoading();
        	},8000);
            userManage.userControl.updateUser(newpass);
            newpass = "";
        }
    }); 
	
	$('#delUserBtn').on('click', function()
	{
        window.parent.showLoading();
        setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
		userManage.userControl.deleteUser();
	});
	
	$('#saveUserAuthBtn').on('click', function()
	{
        window.parent.showLoading();
        setTimeout(function(){
    		window.parent.hideLoading();
    	},8000);
		authority.treeFunction.authTree.saveAuths();
	});
});
