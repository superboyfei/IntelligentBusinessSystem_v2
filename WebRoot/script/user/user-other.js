//非管理员用户登录
var user = 
{
    userGrid    :
    {

        loadUserGrid : function()
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
				colNames	: ['索引', '用户账号', '用户名称', '管理员'],
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
                }],
				rowList 		: [10, 20, 30, 40, 50, 100],
				viewrecords		: true,
				altRows			: true,
				altclass		: 'altRowsColour',
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
					window.parent.hideLoading();
				},
				onPaging	    : function(pgButton)
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
            user.initUIOption.doResize();
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
					user.initUIOption.doResize();
				}
			});
		},
		doResize	: function ()
		{
			var ss = user.initUIOption.getPageSize();
			$("#user-jqgrid").jqGrid('setGridWidth', ss.WinW - 20)
			$("#user-jqgrid").jqGrid('setGridHeight', ss.WinH - 80);
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
		}
	}
}

$(document).ready(function()
{
	//jqgrid默认没有填充屏幕，需要手动进行调整
	user.initUIOption.resizeWindow();
	user.userGrid.loadUserGrid();
});