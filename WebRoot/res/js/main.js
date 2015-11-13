$(document).ready(function () {
        var containerHeight=$(window).height()-62;
        var containerWidth=$(window).width()-214;
        //设置框架高度；
        $("#layout-container").height(containerHeight);
        $("#menu-tree").height(containerHeight);
        $("#ui-layout-box").height(containerHeight);
        $("#ui-layout-box").width(containerWidth);
        //调整大小
        $(window).resize(function(){
            $("#layout-container").height($(window).height()-62);
            $("#menu-tree").height($(window).height()-62);
            $("#ui-layout-box").height($(window).height()-62);
            $("#ui-layout-box").width($(window).width()-214);
            //console.log($(window).width());
        });
       
});