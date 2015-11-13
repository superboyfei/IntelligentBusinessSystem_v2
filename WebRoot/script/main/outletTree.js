function zTreeOnClick(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("addr-tree");
	treeObj.checkNode(treeNode,null,true,true);
};
var outletTree = 
{
    setting  : 
    {
        data : 
        {
            simpleData  : 
            {
                enable  : true,
                idKey   : 'id',
                pIdKey  : 'parentid',
                rootPId : ''
            }
        },
        check : 
        {
            enable : true
        },
        view :
        {
            showIcon   : false,
            showLine   : false,
            fontCss    : function (treeId, treeNode)
            {
                return treeNode.font ? treeNode.font : {};
            },
            nameIsHTML : true,
            dblClickExpand: false
        },
        callback : 
        {
        	//点击节点改变复选框状态
        	onClick: zTreeOnClick
        }
    },
    tree : 
    {
        loadTree : function(divId)
        {
            $.ajax({
                url     : "system/outlets/main/loadOutlets.action",
                type    : "POST",
                dataType: "json",
                success : function(data) {
                    if (data.result == true)
                    {
                        var newRoot = {name : "所有", id : 0};
                        data.outletsVoList.push(newRoot);
                        var treeObj = $.fn.zTree.init($(divId), outletTree.setting, data.outletsVoList);
                        treeObj.expandAll(true);
                        treeObj.checkAllNodes(true);
                    } else {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    toastr.error("服务器通讯失败");
                }
            });            
        },
        getCheckedNode : function(domId)
        {
            var obj = $.fn.zTree.getZTreeObj(domId);
            if (obj == null)
            {
                return null;
            }
            var nodes = obj.getCheckedNodes(true);
            return IdtoString(nodes);
        }
    }
}