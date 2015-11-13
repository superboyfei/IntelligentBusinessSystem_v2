$('#start-date').datepicker(
{
	language  : 'zh-CN',     
    format    : "yyyy-mm-dd",
    autoclose : true
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
    autoclose : true
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

function getVersion()
{
    $.ajax({
        url  : 'homepage/version/versionInfo.action',
        type : 'POST',
        dataType : 'json',
        success  : function(data) {
            if (data.result == true) {
            	//设置版本号
            	$('.version').text("V"+data.systemVersionVo.versionNum);
            }
        }
    });
}

function addExportLog()
{
    var isadmin = $('#isadmin').text();
    if (isadmin == "1") {
        $('#exportLog').show();
    } else {
        $('#exportLog').hide();
    }
}

function addSetLogs()
{
    var isadmin = $('#isadmin').text();
    if (isadmin == "1") {
        $('#setLogs').show();
    } else {
        $('#setLogs').hide();
    }
}

function showcover(){
	setTimeout("$('#cover').show();$('#cover2').show();",400);
}

function hidecover(){
	setTimeout("$('#cover').hide();$('#cover2').hide();",100);
}

function CancelSelected() {
	var treeObj = $.fn.zTree.getZTreeObj("functionTree");
	treeObj.cancelSelectedNode();
}

function SelectDeviceAppNode(){
	var treeObj = $.fn.zTree.getZTreeObj("functionTree");
	treeObj.cancelSelectedNode();
    var nodes = treeObj.getNodes(); 
    var node = getDeviceAppNode(nodes);
    treeObj.selectNode(node,true); 
}

function SelectBusinessManageNode(){
	var treeObj = $.fn.zTree.getZTreeObj("functionTree");
	treeObj.cancelSelectedNode();
    var nodes = treeObj.getNodes(); 
    var node = getBusinessManageNode(nodes);
    treeObj.selectNode(node,true); 
}

$(document).ready(function() {
	//解决google浏览器自动填充表单的问题
    if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1){
    $('#username').hide();   
    $('#oldpass').hide(); 
    $('#newpass').hide();
    $('#comfirmpass').hide();
    $('#checkbox-text').hide();
    $('#username2').show();   
    $('#oldpass2').show(); 
    $('#oldpass3').show(); 
    $('#newpass2').show();
    $('#comfirmpass2').show();
    $('#checkbox-text2').show();
    setTimeout(function(){
    	$('#username2').hide();   
        $('#oldpass2').hide(); 
        $('#oldpass3').hide(); 
        $('#newpass2').hide();
        $('#comfirmpass2').hide();
        $('#checkbox-text2').hide();
        $('#username').show();   
        $('#oldpass').show(); 
        $('#newpass').show();
        $('#comfirmpass').show();
        $('#checkbox-text').show();
    },100)
    } 
    
	$("#username").val("");
	$("#oldpass").val("");
    var topBarHeight = 62;
    //不同大小的窗口初始化元素
   
    	if(document.documentElement.clientWidth<=1177){
    		$("body").eq(0).css("overflow-x","scroll");
    		$("#ui-layout-box").width(963);
    		$("#top-banner").width(1177);
    		$(".cover").width(1180);
    	}
    	if(document.documentElement.clientWidth>1177){
    		var containerWidth = $(window).width() - 214;
    		$("#ui-layout-box").width(containerWidth);
    		$("#top-banner").width("100%");
    		$(".cover").width("100%");
    	}
    	if(document.documentElement.clientHeight<=425){
    		$("body").eq(0).css("overflow-y","scroll");
    		$("#layout-container").height(363);
    	    $("#menu-tree").height(363);
    	    $("#ui-layout-box").height(363);
    	    $(".cover2").height(366);
    	}
    	if(document.documentElement.clientHeight>425){
    		var containerHeight = $(window).height() - topBarHeight;
    		$("#layout-container").height(containerHeight);
    	    $("#menu-tree").height(containerHeight);
    	    $("#ui-layout-box").height(containerHeight);
    	    $(".cover2").height(containerHeight);
    	}	

    //窗口缩放时滚动条的出现
    $(window).resize(function(){
		if(document.documentElement.clientWidth<=1177){
			$("body").eq(0).css("overflow-x","scroll");
			$("#ui-layout-box").width(963);
			$("#top-banner").width(1177);
			$(".cover").width(1180);
		}
		if(document.documentElement.clientWidth>1177){
			$("body").eq(0).css("overflow-x","hidden");
			var containerWidth = $(window).width() - 214;
			$("#ui-layout-box").width(containerWidth);
			$("#top-banner").width("100%");
			$(".cover").width("100%");
		}
		if(document.documentElement.clientHeight<=425){
			$("body").eq(0).css("overflow-y","scroll");
			$("#layout-container").height(363);
		    $("#menu-tree").height(363);
		    $("#ui-layout-box").height(363);
		    $(".cover2").height(366);
		}
		if(document.documentElement.clientHeight>425){
			if($("body").eq(0).css("overflow-y")=="scroll"){
				$("#ui-layout-box").width($("#ui-layout-box").width()+16);
			}
			$("body").eq(0).css("overflow-y","hidden");
			var containerHeight = $(window).height() - topBarHeight;
			$("#layout-container").height(containerHeight);
		    $("#menu-tree").height(containerHeight);
		    $("#ui-layout-box").height(containerHeight);
		    $(".cover2").height(containerHeight);
		}
	});
    
    //加载左侧功能树
    functionTree.tree.loadTree();
    
    //如果登录的是管理员则添加导出日志
    addExportLog();
    
    //如果登录的是管理员则添加日志导出设置
    addSetLogs();
    
    //获取版本号
    getVersion();
    
    $('#logout').on('click', function() {
        window.location.href = "logout.action";
    });
    
    var oldName=$('#name').text();
    //监听updateUserModal的显示
    $('#updateUserModal').on('show.bs.modal', function () {
         var name = $('#name').text();
         $('#username').val(name);
    });
    
    //清除原密码输入框的显示状态
    function clearOldpass(){
  	   	 $("#oldpass_div").removeClass("has-success has-feedback");
         $("#oldpass_div").removeClass("has-error has-feedback")
         $("#oldpass_success").remove();
         $("#oldpass_error").remove();
         $("#newpassMsg").empty().hide();
     }
    
    //监听updateUserModal的右上角关闭按钮的事件
    $('#updateUserModal').on('hide.bs.modal', function (e) {
        updateUser.resetForm();
        $("#oldpassMsg").hide();
        $("#oldpassMsg").html("");
        clearOldpass();
        $('#oldpass').attr({"disabled":true, "required":""});
        $('#newpass').attr({"disabled":true, "required":""});
        $('#confirmpass').attr({"disabled":true, "required":""});
	});
    
    //监听updateUserModal的取消按钮的事件
    $('#cancelBtn').on('click', function(e) {
        updateUser.resetForm();
        $("#oldpassMsg").hide();
        $("#oldpassMsg").html("");
        clearOldpass();
        $('#oldpass').attr({"disabled":true, "required":""});
        $('#newpass').attr({"disabled":true, "required":""});
        $('#confirmpass').attr({"disabled":true, "required":""});  
    });
    
   //监听用户名输入长度
    $("#username").on('keyup',function(){
    	var length=$("#username").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < length; i++) {
            if (patrn.test($("#username").val()[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 20) {
            	$("#username").val($("#username").val().substr(0,i));
            	break;
            }
        }
    });
    
    //原密码错误
    function oldpassError(){
    	$("#oldpass_div").removeClass("has-success has-feedback").addClass("has-error has-feedback");
        $("#oldpass_success").remove();
        $("#oldpass_error").remove();
        $("<span id='oldpass_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
        	.appendTo($("#oldpass_div")[0]);		
    }
    
    $('#oldpass').on('keyup',function(){
    	var oldpassLength = $('#oldpass').val().length;
    	if(oldpassLength==0){
    		if($("#oldpass_success").length>0||$("#oldpass_error").length>0){
    			oldpassErr = true;
                oldpassError();
    		}
    	}
    });
    
    //校验原密码
    var oldpassErr = false;
    $('#oldpass').on('blur', function() {
        var oldpass = $('#oldpass').val();
        if(oldpass == ""){
            if($("#oldpassMsg").html()=="" && $("#oldpass-error").html()!="请输入密码"){            	
            }
            else{
            	oldpassErr = true;
            	oldpassError();
            	$("#oldpassMsg").text("").show();
            }
        } else {
        	$("#oldpassMsg").text("");
        	var uid = $('#uid').text();
            var param = {
                "password" : oldpass,
                "uid" : uid
            };
            $.ajax({
                url  : 'homepage/user/validatePwd.action',
                type : 'POST',
                dataType : 'json',
                async    : false,
                data     : param,
                success  : function(data) {
                    if (data.result == false) {
                        oldpassErr = true;
                        oldpassError();                       
                        $("#oldpassMsg").text("原密码输入错误").show();
                    } else {
                        oldpassErr = false;
                        $("#oldpassMsg").hide();
                        $("#oldpassMsg").html("");
                        $("#oldpass_div").removeClass("has-error has-feedback").addClass("has-success has-feedback");
                        $("#oldpass_success").remove();
                        $("#oldpass_error").remove();
                        $("<span id='oldpass_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
                        	.appendTo($("#oldpass_div")[0]);
                        $('#newpass').attr({"disabled":false, "required":"required"});
                        $('#confirmpass').attr({"disabled":false, "required":"required"}); 
                        $('#newpass').blur();
                    }
                }
            });
        }
    });
    
    var newpasschecker = true;

    $('#newpass').on('blur', function() {
    	//判断新密码是否与旧密码一样
    	var newpass = $('#newpass').val();
    	var oldpass = $('#oldpass').val();

    	if(newpass != "" && oldpass != "" && !oldpassErr) {
    		if(newpass == oldpass){
    			$("#newpassMsg").text("新密码与旧密码一致，请重新输入").show();
    			newpasschecker = false;
    		} else {
    			$("#newpassMsg").empty().hide();
    			newpasschecker = true;
    		}
    	} else {
    		newpasschecker = false;
    	}
    });
    
    //自定义验证规则
    jQuery.validator.addMethod("checkOldPass", function(value, element) {
        var patrn = /[0-9a-zA-Z~!@#%&*-_+]/;
        for (var i = 0; i < value.length; i++) {
            if (patrn.test(value[i]) == false) { 
            	oldpassErr = true;
                oldpassError();  
                return false;
            }
        }
        return true;
    });
    
    jQuery.validator.addMethod("checkOldPassLen", function(value, element) {
    	if(value.length<6){
    		oldpassErr = true;
            oldpassError();  
            return false;
    	}
    	return true;
    });
    
    jQuery.validator.addMethod("checkNewPass", function(value, element) {
        var patrn = /[0-9a-zA-Z~!@#%&*-_+]/;
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
    	if($("#newpass").val() != $("#confirmpass").val()) {
    		return false;
    	} else {
    		return true;
    	}
    });    
    var updateUser = $("#updateUserForm").validate({
        rules : {
            username : {
                checkName : true
            },
            oldpass : {
            	checkOldPassLen : true,
                checkOldPass : true
            },
            newpass : {
                minlength : 6,
                maxlength : 20,
                checkNewPass : true
            },
            confirmpass : {
            	confirmPass : true
            }
        },
        messages : {
            username : {
                required  : '请输入用户名称',
                checkName : '用户名含有非法字符'
            },
            oldpass : {
            	checkOldPassLen : '请输入至少6位的密码',
                required  : '请输入密码',
                checkOldPass : '用户密码含有非法字符'
            },
            newpass : {
                minlength : '请输入至少6位的密码',
                required  : '请输入新密码',
                checkNewPass : '用户密码含有非法字符'
            },
            confirmpass : {
                required  : '请重新输入密码',
                confirmPass   : '密码不一致，请重新输入'
            }
        },
        submitHandler : function() {
        	if ($('#checkbox-text').is(":checked")){
        		if (oldpassErr) {
                    return;
                }
                if (!newpasschecker) {
                	return;
                }	
        	}
            var uid = $('#uid').text();
            var name = $('#updateUserForm input[name=username]').val();
            var newpass = $('#updateUserForm input[name=newpass]').val();
            var param = {
                uid		: uid,
                name	: name,
                password: newpass
            };
            showLoading();
            $.ajax({
                url     : "homepage/user/updateCurrentUser.action",
                type    : "POST",
                dataType: "json",
                data    : param,
                success : function(data) {
                    $("#updateUserModal").modal('hide');
                    $('#updateUserForm').validate().resetForm();
                    hideLoading();
                    if (data.result == true)
                    {
                        $('#name').text(param.name).attr("title", param.name);
                        toastr.success('更新用户信息成功');
                        var child_document=frames["ui-layout-box"].document; 
                        if(child_document.getElementById("ParentToChildren")){
                        	if(child_document.getElementsByTagName("tbody")[0].getElementsByTagName('tr')[1].id=="1"){
                        		child_document.getElementsByTagName("tbody")[0].getElementsByTagName('tr')[1].getElementsByTagName('td')[2].innerHTML=name;
                        	}                        	
                        }
                        if(child_document.getElementById("ParentToChildren_other")){
                        	for(var i=0;i<child_document.getElementsByTagName("tbody")[0].getElementsByTagName('tr').length;i++){
                        		if(child_document.getElementsByTagName("tbody")[0].getElementsByTagName('tr')[i].getElementsByTagName('td')[2].title==oldName){
                        			child_document.getElementsByTagName("tbody")[0].getElementsByTagName('tr')[i].getElementsByTagName('td')[2].innerHTML=name;
                        		}
                        	}                     	                        	
                        }
                    }
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    $("#updateUserModal").modal('hide');
                    $('#updateUserForm').validate().resetForm();
                    hideLoading();
                    toastr.error("服务器通讯失败");
                }
            });            
        }
    });    
    //监听修改用户checkbox
    $('#checkbox-text').on('change', function() {
        var name = $('#updateUserForm input[name=username]').val();
        if ($('#checkbox-text').is(":checked")) {
        	clearOldpass();
            $('#oldpass').attr({"disabled":false, "required":"required"});
            $('#newpass').attr({"disabled": false, "required":"required"});
            $('#confirmpass').attr({"disabled": false, "required":"required"});
        } else {
            updateUser.resetForm();
            $('#updateUserForm input[name=username]').val(name);
            $("#oldpassMsg").hide();
            $("#oldpassMsg").html("");
            clearOldpass();
            $('#oldpass').attr({"disabled":true, "required":""});
            $('#newpass').attr({"disabled":true, "required":""});
            $('#confirmpass').attr({"disabled":true, "required":""});
        }
    });
    
    $('#index').on('click',function(){
    	document.getElementById("ui-layout-box").src="homepage/main/mainInfo.action";
    	CancelSelected();
    });
    
    $('#logoarea').on('click',function(){
    	CancelSelected();
    });
    
    //监听exportLogModal关闭按钮
    $('#closeBtn').on('click', function (e) {
        $('#start-date .form-control').val("");
        $('#end-date .form-control').val("");
    });
    
    //监听exportLogModal取消按钮
    $('#cancelLogBtn').on('click', function(e) {
        $('#start-date .form-control').val("");
        $('#end-date .form-control').val("");
    });
    
    //监听exportLogModal显示事件
    $('#exportLogModal').on('show.bs.modal', function (e) {
            var today = new Date();
            var todayFormat = formatDate(today.getFullYear(), today.getMonth()+1, today.getDate());
            if($('#start-date .form-control').val()==""){
            	$('#start-date .form-control').val(todayFormat);
                $('#end-date .form-control').val(todayFormat);
                $('#start-date').datepicker(
            			"setEndDate", todayFormat
            	);  
            	$('#end-date').datepicker(
            			"setEndDate", todayFormat
            	);  
            }		
    });
    
    $('#exportLogBtn').on('click', function() {
        var start = $('#start-date .form-control').val();
        var end = $('#end-date .form-control').val();
        var param = {
            "startDate" : start,
            "endDate"   : end
        };
        $.post("homepage/log/exportLogPrepare.action", param, function(data) {
        	$('#start-date .form-control').val("");
            $('#end-date .form-control').val("");
            if (data.result == true) {
            	$("#exportLogModal").modal('hide');
                location.href = "homepage/log/exportLog.action";
            } else {
                toastr.error(data.errorMsg);
            }
        });
    });
});
$("#setLogs").on("click",function(){	
	if($("#setLogPopoDiv").is(":visible")) {
		$("#exportLogModal").modal('hide');
		$("#setLogPopoDiv").hide();
		$("#exportLog").removeClass("actived");
		return false;
	}  
	if($("#setLogPopoDiv").is(":hidden")){
		$.post("homepage/log/loadLogConfig.action", function(data) {			
	    	if (data.result == true){	    		
	    		if(data.printLevel==2){
	    			$("input[type='radio']").eq(0).attr("checked",true);
	    		}
	    		if(data.printLevel==1){
	    			$("input:radio[name='optionsRadios']").eq(1).attr("checked",true);
	    		}
	    		if(data.printLevel==0){
	    			$("input:radio[name='optionsRadios']").eq(2).attr("checked",true);
	    		}
	    	}
	    	if (data.result == false){
	    		toastr.error(data.errorMsg);
	    	}
	    	$("#setLogPopoDiv").show(); 
	    }); 
		return false;
	}   
});
$("#popoverCloseBtn").on("click",function(){
   $(this).parent().parent().hide();
   $("#exportLog").removeClass("actived");
});
var spinner;
$("#saveLogSet").on("click",function()
{           
   var opts = {
     lines: 11, // The number of lines to draw
     length: 7, // The length of each line
     width: 4, // The line thickness
     radius: 10, // The radius of the inner circle
     corners: 1, // Corner roundness (0..1)
     rotate: 0, // The rotation offset
     color: '#000', // #rgb or #rrggbb
     speed: 1, // Rounds per second
     trail: 60, // Afterglow percentage
     shadow: false, // Whether to render a shadow
     hwaccel: false, // Whether to use hardware acceleration
     className: 'spinner', // The CSS class to assign to the spinner
     zIndex: 2e9, // The z-index (defaults to 2000000000)
     top: '50%', // Top position relative to parent in px
     left: '50%' // Left position relative to parent in px
   };
   var target = document.getElementById('setLogPopoDiv');
   spinner = new Spinner(opts).spin(target);  
   var value = $("input[type='radio']:checked").val();
   var printlevel;
   if(value=="高"){printlevel=2;}
   if(value=="低"){printlevel=1;}
   if(value=="关闭"){printlevel=0;}
   var param = {
       "printLevel"   : printlevel
   };
   $.post("homepage/log/setLogConfig.action", param, function(data) {
       if (data.result == true) {
    	   spinner.stop();  
    	   toastr.success("配置将在30s之后生效","保存日志配置成功");
    	   $("#setLogPopoDiv").hide();
   		   $("#exportLog").removeClass("actived");
       } else {
           toastr.error(data.errorMsg);
       }
   });
});
