var authority =
{
    data : 
    {
        ids : ""
    },
	treeFunction:
	{
		setting	:
		{
			data		:
			{
				simpleData	:
				{
					enable	: true,
					idKey	: "id",
					pIdKey	: "parentid",
					rootPId	: ""
				}
			},
			check		:
			{
				enable		: true,
				chkStyle	: "checkbox",
				chkboxType	:
				{
					"Y"	: "ps",
					"N"	: "ps"
				},
                chkDisabledInherit : true
			},
			edit		:
			{
				enable		: true,
                showRemoveBtn: false,
                showRenameBtn: false
			},
            view        : 
            {
                 showIcon   : false
            }
		},
		authTree	: 
		{
			loadAuthTree	: function()
			{
				var param = 
				{
					userid	: userManage.data.user.id
				};
                $.ajax({
                    url     : "system/user/authority/showAuthListByUserid.action",
                    type    : "POST",
                    dataType: "json",
                    data    : param,
                    success : function(data) {
                        window.parent.hideLoading();
                        if (data.result == true)
                        {
                            var treeObj = $.fn.zTree.init($("#user-setup-tree"), authority.treeFunction.setting, data.functionVoList);
                            treeObj.expandAll(true);
                        }
                        else
                        {
                            toastr.error(data.errorMsg);
                        }
                    },
                    error   : function(data) { 
                        window.parent.hideLoading();
                        toastr.error("服务器通讯失败");
                    }
                });                
			},
			saveAuths	: function()
			{
				var checkedNodes = $.fn.zTree.getZTreeObj("user-setup-tree").getCheckedNodes(true);
				var functionids = IdtoString(checkedNodes);
				var param = 
				{
					userid	: userManage.data.user.id,
					functionids : functionids
				};
                $.ajax({
                    url     : "system/user/authority/saveOrUpdateAuthority.action",
                    type    : "POST",
                    dataType: "json",
                    data    : param,
                    success : function(data) {
                        $("#UserSetupModal").modal('hide');
                        window.parent.hideLoading();
                        if (data.result == true)
                        {
                            toastr.success("设置用户权限成功");
                        }
                        else
                        {
                            toastr.error(data.errorMsg);
                        }
                    },
                    error   : function(data) {
                        $("#UserSetupModal").modal('hide');    
                        window.parent.hideLoading();
                        toastr.error("服务器通讯失败");
                    }
                });
			}
		}
	}
};
