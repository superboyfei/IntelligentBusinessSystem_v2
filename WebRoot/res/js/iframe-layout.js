//jqgrid自适应窗口
  var t=document.documentElement.clientWidth;  
  window.onresize = function(){  
    if(t!= document.documentElement.clientWidth){ 
        t= document.documentElement.clientWidth; 
         setTimeout("doResize()",700);
      } 
  } 

  function doResize(divStr) { 
    var ss = getPageSize(); 
    var centerWidth=$(".ui-layout-center").width();
    $(divStr).jqGrid('setGridWidth', centerWidth-24).jqGrid('setGridHeight', ss.WinH-130); 
  } 

  function getPageSize() { 
      //http://www.blabla.cn/js_kb/javascript_pagesize_windowsize_scrollbar.html 
      var winW, winH; 
      if(window.innerHeight) {// all except IE 
                winW = window.innerWidth; 
                winH = window.innerHeight; 
        } else if (document.documentElement && document.documentElement.clientHeight) {
                // IE 6 Strict Mode 
                winW = document.documentElement.clientWidth; 
                winH = document.documentElement.clientHeight; 
      } else if (document.body) { // other 
                winW = document.body.clientWidth; 
                winH = document.body.clientHeight; 
      }  // for small pages with total size less then the viewport  
                return {WinW:winW, WinH:winH}; 
  } 

$(document).ready(function () {
        $("body").layout(layoutSettings_Outer);
        $(".west-sort-title").on("click",function(){
                $(this).toggleClass("expand");
                $(this).next().toggle();
         })
});

var layoutSettings_Outer = {
        defaults: {
        },
        west:{
            size:  230,
            onclose_end: function(){
                doResize();
            },
            onopen_end:function(){
                doResize();
            }
        }
};