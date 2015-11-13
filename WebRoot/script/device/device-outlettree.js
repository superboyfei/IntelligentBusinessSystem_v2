var outlet = {
    treeFunction : 
    {
        setting : {
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
			check		    :
			{
				enable		: true,
				chkStyle	: "radio",
                radioType   : 'all'
			},       
        },
        tree : {
            loadTree : function()
            {
                $.post('queryCondition/queryQutlets.action', null, function(data)
                {
                	if (data.result == true)
                	{
                        var treeObj = $.fn.zTree.init($('#device-outlets-tree'), outlet.treeFunction.setting, data.outletsVoList);
                        treeObj.expandAll(true);
                	} else {
                		//xxx
                	}
                });
            },
            saveOutlet : function()
            {
                var checkedNode = $.fn.zTree.getZTreeObj($('#device-outlets-tree')).getCheckedNodes(true);
                var param = {
                   id : deviceStatus.data.device.id
                };
                $.post('', param, function(data){
                    $('#editDeviceModal').modal('hide');
                });
            }
        }
    }
}