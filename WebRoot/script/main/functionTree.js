function getFont(treeId, node) {
    return node.font ? node.font : {'color' : '#fff'};
}

function getBusinessManageNode(nodes){
	for(var i = 0;i < nodes.length;i ++){
		if(nodes[i].name == "业务管理"){
			return nodes[i];
		}
		else if(nodes[i].children != undefined){
					for(var j = 0;j < nodes[i].children.length;j ++){
						if(nodes[i].children[j].name == "业务管理"){
							return nodes[i].children[j];
						}
					}
				}		
	}
	return;
}

function getDeviceAppNode(nodes){
	for(var i = 0;i < nodes.length;i ++){
		if(nodes[i].name == "应用发布"){
			return nodes[i];
		}
		else if(nodes[i].children != undefined){
					for(var j = 0;j < nodes[i].children.length;j ++){
							for(var k=0;k<nodes[i].children[j].children.length;k++){
								if(nodes[i].children[j].children[k].name == "应用发布"){
									return nodes[i].children[j].children[k];
								}
							}							
					}
				}		
	}
	return;
}

var functionTree =
{
    setting : {
    	callback:{      		
    		onClick:zTreeOnClick
    	},
        view : {
            showLine   : false,
            fontCss    : getFont,
            nameIsHTML : true,
            dblClickExpand: false
        },
        data : {
            simpleData  : {
                enable  : true,
                idKey   : "id",
                pIdKey  : "parentid",
                rootPId : ""
            }
        }        
    },
    tree : {
        loadTree : function()
        {
            $.post("homepage/main/loadUserFunction.action", null, function(data) {
                if (data.result == true)
                {
                    if (data.functionVoList.length == 0) {
                        $('#functionTree').hide();
                        return;
                    }
                    var treeObj = $.fn.zTree.init($("#functionTree"), functionTree.setting, data.functionVoList);
                    treeObj.expandAll(true);
                    //var selectedNode = treeObj.getSelectedNodes();                    
                }
            });
        }
    }
}

function zTreeOnClick(event, treeId, treeNode) {
	//单击父节点收放子目录
	var zTree = $.fn.zTree.getZTreeObj("functionTree");
	zTree.expandNode(treeNode);
	
	$.ajax({
	    url:'checkState.action',
	    type:"POST",
	    dataType : "json",
	    success:function(data){
	    if(data.result==true){
	    }
	    if(data.result==false){
	    	location.href="";
	    }
	    },
	    error:function(data){
	    }
	 }); 	
};

