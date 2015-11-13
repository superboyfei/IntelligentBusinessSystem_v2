function zTreeOnClick(event, treeId, treeNode) {
	var bussTreeObj = $.fn.zTree.getZTreeObj("deal-tree");
	bussTreeObj.checkNode(treeNode,null,true,true);
};
var treeObj = null;
var bussTypeTree = 
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
        loadTree : function(domId)
        {
            $.ajax({
                url     : "queryCondition/loadBusiness.action",
                type    : "POST",
                dataType: "json",
                success : function(data) {
                    if (data.result == true)
                    {
                        var newRoot = {name : "所有", id : 0};
                        data.businessVoList.push(newRoot);
                        treeObj = $.fn.zTree.init($(domId), bussTypeTree.setting, data.businessVoList);
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
