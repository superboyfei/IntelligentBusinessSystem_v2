$("#searchUser").bind({
	focus : function(){if($(this).val()=="输入部分或全部用户帐号"){$(this).val("")};
    },
	blur : function(){if($(this).val()==""){};}
	
});
$(document).ready(function(){
	$("input[id='searchUser']").bind('input propertychange', function(){
		jQuery("#user-jqgrid").setGridParam({
			url:'system/user/main/loadUsersByPage.action',
			postData:{matchUid:$(this).val()},
			page:0
		}).trigger("reloadGrid");
	});
});