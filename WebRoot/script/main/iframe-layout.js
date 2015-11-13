var gridSize = {
    data : {
        toolBarHeight   : 0,
        div : "",
    },
    action : {
        getPageSize : function()
        {
            var winW, winH; 
            if (window.innerHeight) {// all except IE 
                winW = window.innerWidth; 
                winH = window.innerHeight; 
            } else if (document.documentElement && document.documentElement.clientHeight) {
                // IE 6 Strict Mode 
                winW = document.documentElement.clientWidth; 
                winH = document.documentElement.clientHeight; 
            } else if (document.body) { // other 
                winW = document.body.clientWidth; 
                winH = document.body.clientHeight; 
            }
            return {WinW : winW, WinH : winH}; 
        },
        doResize : function(divStr)
        {
            gridSize.data.div = divStr;
            var ss = gridSize.action.getPageSize(); 
            $(divStr).jqGrid('setGridWidth', ss.WinW - $("#ui-layout-west-sortMenu").width() -24)
            .jqGrid('setGridHeight', ss.WinH - gridSize.data.toolBarHeight - 80);
            $("#ui-layout-west-sortMenu").height(ss.WinH - 54); 
        },
        closeOrOpenResize : function()
        {
            var ss = gridSize.action.getPageSize(); 
            var centerWidth = $(".ui-layout-center").width();
            if (gridSize.data.div)
            {
                $(gridSize.data.div).jqGrid('setGridWidth', centerWidth - 24)
                .jqGrid('setGridHeight', ss.WinH - gridSize.data.toolBarHeight - 80); 
            }
        }
    }
};

var layoutSettings_Outer = {
    west : {
        size :  230,
        onclose_end : function() {
            gridSize.action.closeOrOpenResize();
        },
        onopen_end : function() {
             gridSize.action.closeOrOpenResize();
        }
    }
};

$(document).ready(function () 
{
    //初始化页面布局
    $("body").layout(layoutSettings_Outer);
    $(".west-sort-title").on("click", function() {
        $(this).toggleClass("expand");
        $(this).next().toggle();
    });
    
    //获取dom高度
    var bar = $('.center-tool-bar');
    if (bar.length != 0) {
        gridSize.data.toolBarHeight = $('.center-tool-bar').outerHeight(true);
    }
    
    //窗口绑定大小变更事件
    var width = document.documentElement.clientWidth; 
    var height = document.documentElement.clientHeight;
    $(window).resize(function() {    
        if (width != document.documentElement.clientWidth
            || height != document.documentElement.clientHeight)
        {
            width = document.documentElement.clientWidth;
            height = document.documentElement.clientHeight;
            gridSize.action.doResize(gridSize.data.div);
        }    
    });
});
