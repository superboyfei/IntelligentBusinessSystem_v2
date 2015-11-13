<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<title>登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<link href="res/images/favorite.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="res/css/login.css" rel="stylesheet">
    <link href="res/css/supersized.css" rel="stylesheet">
    <link href="res/css/bootstrap/bootstrap.min.css" rel="stylesheet" media="screen">

	<body>
		<div class="login_wrap">
			<div class="login" id="loginDiv">
				<div class="login_in">
					<img src="res/images/loginTitle.png" width="340" height="64"/>
					<div class="login_note">
						<s:fielderror><s:param>error</s:param></s:fielderror>
					</div>
					<div class="input_div">
						<form action="login.action" method="post" id="login_form" autocomplete="off">
						<input type="hidden" value="loginPage" name="loginPage"/>
						<ul>
							<li class="login_user">
								<input name="uid" id="uid" type="text" maxlength="40" tabindex="1"  style="position: absolute;z-index:9" autocomplete="off">
								<input name="uid2" id="uid2" type="text" maxlength="40" tabindex="1"  style="position: absolute;z-index:9;display:none">
								<div class="note">请输入用户账号</div>
							</li>
							<li class="login_pass">
							    <input name="password2" id="password2" class="field" type="password" maxlength="20" tabindex="2" style="position: absolute;z-index:9;display:none">
								<input name="password3" id="password3" class="field" type="password" maxlength="20" tabindex="2" style="position: absolute;z-index:9;display:none">
								<input name="password" id="password" class="field" type="password" maxlength="20" tabindex="2" style="position: absolute;z-index:9" autocomplete="off">							
								<div class="note">请输入密码</div>
							</li>
						</ul>
						</form>
					</div>
					<button class="login_btn disabled" id="submit" data-container="body"  data-trigger="focus" disabled>登录</button>
					<div class="download"><a href="toDownload.action"><span class="glyphicon glyphicon-download" aria-hidden="true"></span>点此下载软件</a></div>
				</div>
			</div>
		</div>
		<div class="copyright"><span style="padding-left:80px">© 2002-2015 福建升腾资讯有限公司 版权所有</div>
    <script src="res/js/jquery.min.js"></script>
	<script src="res/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="res/js/bootstrap.min.js"></script>    
    <script type="text/javascript" src="res/js/login/supersized.3.2.7.min.js"></script>
    <script type="text/javascript" src="res/js/login/supersized-init.js"></script>
	<script type="text/javascript">
		//session失效后，跳出iframe
		if (window != top) 
		{
			top.location.href = location.href; 
			window.event.returnValue = false;
		}
	</script>

	<script>
	var cookieName = 'task_uid';
    //登录校验    
	function login()
    {
        var uid = $('#uid'), pass = $('#password');
		var frm = $('form:eq(0)'), uv = frm[0].uid.value, pv = frm[0].password.value, tip = $('h2:eq(0)');
		if ($.trim(uv) == '') {
			$('#loginDiv').effect("shake", { times : 3 }, 100, function() { $('#uid').focus();});
			uid.val('');
			$('.login_user .note').show();
			$('.login_note').html("请输入用户账号");
		} else if ($.trim(pv) == '') {
			$('#loginDiv').effect("shake", { times : 3 }, 100, function() { $('#password').focus();});
			pass.val('');
			$('.login_pass .note').show();	
			$('.login_note').html("请输入密码");
		} else if ($.trim(pv).length < 6) {
            $('.login_note').html("请输入至少6位密码");
        } else if (uidValid() == false) {
            $('.login_note').html("用户账号含有非法字符");
            uid.val('');
            pass.val('');
            $('.login_user .note').show();
            $('.login_pass .note').show();        
        } else if (passValid() == false) {
            $('.login_note').html("密码含有非法字符");  
			uid.val('');
            pass.val('');
			$('.login_user .note').show();
            $('.login_pass .note').show();
        } else {
             $("#login_form").submit();
        }
	}
        
    function passValid()
    {
        var patrn = /[0-9a-zA-Z~!@#%&*-_+]/;
        var pass = $('#password').val();
        for (var i = 0; i < pass.length; i++) {
            if (patrn.test(pass[i]) == false) {
                return false;
            }
        }
        return true;
    }
        
    function uidValid()
    {
        var patrn = /[0-9a-zA-Z\u4e00-\u9fa5]/;
        var uid = $('#uid').val();
        for (var i = 0; i < uid.length; i++) {
            if (patrn.test(uid[i]) == false) {
                return false;
            }
        }
        return true;        
    }
        
        
    function checkSubmit()
    {
        var frm = $('form:eq(0)'), uv = frm[0].uid.value, pv = frm[0].password.value;
        if ($.trim(uv) != '' && $.trim(pv) != '') {
            $('.login_user .note').hide();
            $('.login_pass .note').hide(); 
            $('#submit').removeClass("disabled").removeAttr("disabled");
        } else {
            $('#submit').addClass("disabled").prop({disabled : true});
        }
    }
    //定时器监听输入框
    setInterval("checkSubmit()", 100 ); 
	
	$(document).ready(function()
    { 
        //设置背景图
        $("body").css("background","url(res/images/Bg.jpg)");
	  	var frm = $('form:eq(0)');
        
        //从cookie获取到账号则焦点移动到密码框
		var uid = $.cookie.get(cookieName);
		if (uid) {
			frm[0].uid.value = uid;
			$('.login_user .note').hide();
			$('#password').focus();
		} else {
			$('#uid').focus();
		}
	
        //账号事件监听
		$('#uid')
        .focusin(function() { 
            $('.login_user').addClass('hit1');
            $('#uid').css({
            "height":"40px",
            "margin-top":"1px"
            });
        })
        .focusout(function() { 
            if (this.value == '') {
                $('.login_user .note').show();
            }
            $('.login_user').removeClass('hit1');
         })
        .mouseup(function() { 
            if (this.value != '') {
                $('#userDiv .note').hide();
            }
        })
        .keydown(function() {
            $('.login_user .note').hide();
        });
        
        //密码框事件监听
		$('#password')
        .focusin(function() {
            $('.login_pass').addClass('hit2');
            $('#password').css({
            "height":"40px",
            "margin-top":"1px"
            });
        })
        .focusout(function() {
            if (this.value == '') {
                $('.login_pass .note').show();
            }
            $('.login_pass').removeClass('hit2');
    	})
        .keydown(function() {
            $('.login_pass .note').hide();
        });	
        
		//enter事件监听
        frm.keypress(function (e) {
	        if (e.keyCode == 13) {
	      	 	login();
	         	return false;
	        }            
	    });
        
		//提交
		$('#submit').click(function() {
	        login();
	    });
	    
	    //监听用户帐号不能超过40个字符
	    $("#uid").on('keyup',function(){
    	var length=$("#uid").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < length; i++) {
            if (patrn.test($("#uid").val()[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 40) {
        	$("#uid").val($("#uid").val().substr(0,i));
        	break;
        }
        }
        });
        
      //根据起始窗口初始化元素
      if(document.documentElement.clientHeight>670){
      $("body").eq(0).css("overflow-y","hidden");
      }
      if(document.documentElement.clientHeight<=670){
      $("body").eq(0).css("overflow-y","scroll");
      }
      if(document.documentElement.clientWidth>435){
      $("body").eq(0).css("overflow-x","hidden");
      }
      if(document.documentElement.clientWidth<=435){
      $("body").eq(0).css("overflow-x","scroll");
      }  

      //监听窗口大小
      $(window).resize(function(){
      if(document.documentElement.clientHeight>670){
      window.scrollTo(0,0);
      $("body").eq(0).css("overflow-y","hidden");
      }
      if(document.documentElement.clientHeight<=670){
      $("body").eq(0).css("overflow-y","scroll");
      }
      if(document.documentElement.clientWidth>435){
      $("body").eq(0).css("overflow-x","hidden");
      }
      if(document.documentElement.clientWidth<=435){
      $("body").eq(0).css("overflow-x","scroll");
      }
      });
       
      //防止google浏览器自动填充表单
   	  if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1 ){	 
	    $('#uid').hide();   
        $('#password').hide();         
        $('#uid2').show();   
        $('#password2').show(); 
        $('#password3').show();
        setTimeout(function(){
    	    $('#uid2').hide();   
            $('#password2').hide(); 
            $('#password3').hide();
            $('#uid').show();   
            $('#password').show();
            $('#uid').focus(); 
        },1)
        }    
       });

	//cookie
	$.cookie = {
	    get : function(name) {
	        var a = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	        return a ? unescape(a[2]) : null;
	    },
	    set : function(name, value, seconds, path, domain, secure) {
	        var a = new Date();
	        a.setTime(a.getTime() + seconds);
	        document.cookie =
	            escape(name) + '=' + escape(value)
	            + (a ? '; expires=' + a.toGMTString() : '')
	            + (path ? '; path=' + path : '/')
	            + (domain ? '; domain=' + domain : '')
	            + (secure ? '; secure' : '');
	    },
	    clear : function(name) {
	        var a = new Date();
	        a.setTime(a.getTime() - 1);
	        document.cookie = name + ' = null;expires = ' + a.toGMTString();
	    }
	 };
	</script>        
	</body>
</html>
