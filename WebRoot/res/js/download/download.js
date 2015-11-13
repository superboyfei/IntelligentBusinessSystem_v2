var downloadApp = {
    data:{
    	id : '',
        loadServerAppRet : true
    },
    action:{
        loadServerApp : function() 
        {
            // 查询服务器中的所有应用
            $.ajax({
                url      : "loadDownLoadFileInfo.action",
                type     : "POST",
                dataType : "json",
                async    : false,
                success  : function(data)
                {
                	if(data.result == true)
                	{
	                    var retJson = data.downloadFileVoList;
	                    for (var i = 0; i < retJson.length; i++)
	                    {
	                        var appObj = retJson[i];
	                        var id, filepath, iconpath, name, uploadtime, size, description;
	                        for (key in appObj)
	                        {   
	                        	if(key == "id")
	                        	{
	                        		id = appObj[key];
	                        	}
	                        	else if (key == "filepath") 
	                            {
	                        		filepath = appObj[key];
	                            }
	                        	else if (key == "iconpath") 
	                            {
	                            	icon = appObj[key];
	                            }
	                            else if (key == "name")
	                            {
	                            	name = appObj[key];
	                            }
	                            else if (key == "uploadtime")
	                            {
	                            	uploadtime = appObj[key];
	                            } 
	                            else if (key == "size")
	                            {
	                            	var Size = appObj[key];
	                            	size=(Size/1048576).toFixed(2);
	                            } 
	                            else if (key == "description")
	                            {
	                            	var Description = appObj[key];
	                            	description=Description.replace(/\%/g,'<br/>');
	                            } 
	                        }
	                        downloadApp.action.creatOption(id,filepath, icon, name,uploadtime, size, description);
	                    }
                	}
                	else
                	{
                		downloadApp.data.loadServerAppRet = false;
                		toastr.error(data.errorMsg);
                	}
                },
                error    : function(data)
                {
                	downloadApp.data.loadServerAppRet = false;
                    toastr.error("获取下载列表异常");
                }
            });
        },
        creatOption : function(id,filepath, iconpath, name, uploadtime, size, description)
        {
        	var option = $("<div class=\"download-list\"><ul><li class=\"app-icon\"><img src=\"" + iconpath.replace(/\\/g,"/") + 
        			"\" width=\"48\"/></li><li class=\"app-name-title\">" + name +
        			"</li><li class=\"app_sub_information\"><span class=\"sub-label\">更新时间：" + uploadtime + "&nbsp&nbsp&nbsp" + 
        			"</span><span class=\"sub-label\">大小：" + size + "MB" +
        			"</span></li><li class=\"app-descript\"><span>" + description + 
        			"</span></li><li class=\"download-btn\"><button id=\"download-file-btn\" type=\"button\" class=\"btn btn-success btn-sm\" download-file-id=\"" + id
        			+ "\"><span class=\"glyphicon glyphicon-download btn-glyphicon\" aria-hidden=\"true\"></span>立即下载</button></li></ul></div>");
            option.appendTo("#download-wrap");
        }
    }
};

var pageDownload = {
	data:{
		obj : null, //获取数据列对象
    	nowPage : 0,// 当前页
    	fileListLength : 0, //列表中下载文件总个数
    	listNum : 10,// 每页显示<ul>数
    	pagesLen : 0,// 总页数
    	pageNum : 4, //显示的链接数目
	},
	action:{
        pageDownloadList : function(){
        	pageDownload.data.obj=$(".download-list");
        	pageDownload.data.fileListLength = pageDownload.data.obj.length;
        	pageDownload.data.pagesLen=Math.ceil(pageDownload.data.fileListLength/pageDownload.data.listNum);
        	pageDownload.action.upPage(0);
        },
        upPage : function(p){       	
        	pageDownload.data.nowPage=p;
        	for (var i=0;i<pageDownload.data.fileListLength;i++){
        		pageDownload.data.obj[i].style.display="none"
        	}
        	for (var i=p*pageDownload.data.listNum;i<(p+1)*pageDownload.data.listNum;i++){
        		if(pageDownload.data.obj[i])
        			pageDownload.data.obj[i].style.display="block";
        	}
        	strS='<ul class="pager"><li style="cursor:pointer"><a onclick="pageDownload.action.upPage(0);">首页</a></li>&nbsp&nbsp';
        	var PageNum_2=pageDownload.data.pageNum%2==0?Math.ceil(pageDownload.data.pageNum/2)+1:Math.ceil(pageDownload.data.pageNum/2);
        	var PageNum_3=pageDownload.data.pageNum%2==0?Math.ceil(pageDownload.data.pageNum/2):Math.ceil(pageDownload.data.pageNum/2)+1;
        	var strC="",startPage,endPage;
        	if (pageDownload.data.pageNum>=pageDownload.data.pagesLen) 
        	{
        		startPage=0;
        		endPage=pageDownload.data.pagesLen-1;
        	}
        	else if (pageDownload.data.nowPage<PageNum_2)
        	{
        		startPage=0;
        		endPage=pageDownload.data.pagesLen-1>pageDownload.data.pageNum?pageDownload.data.pageNum:pageDownload.data.pagesLen-1;
        	} else 
        	{
        		startPage=pageDownload.data.nowPage+PageNum_3>=pageDownload.data.pagesLen?pageDownload.data.pagesLen-pageDownload.data.pageNum-1:pageDownload.data.nowPage-PageNum_2+1;
        		var t=startPage+pageDownload.data.pageNum;
        		endPage=t>pageDownload.data.pagesLen?pageDownload.data.pagesLen-1:t;
        	}
        	for (var i=startPage;i<=endPage;i++){
        		if (i==pageDownload.data.nowPage)
        			strC+='<li class="active" style="cursor:pointer"><a onclick="pageDownload.action.upPage('+i+');">'+(i+1)+'</a></li>&nbsp&nbsp';
        		else 
        			strC+='<li style="cursor:pointer"><a onclick="pageDownload.action.upPage('+i+');">'+(i+1)+'</a></li>&nbsp&nbsp';
        	}
        	strE=' <li style="cursor:pointer"><a onclick="pageDownload.action.upPage('+(pageDownload.data.pagesLen-1)+');">尾页</a></li></ul>&nbsp&nbsp';
        	document.getElementById("pager").innerHTML=strS+strC+strE;
        }
	}
};

function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return (localhostPaht+projectName);
}

function getVersion()
{
    $.ajax({
        url  : 'homepage/version/versionInfo.action',
        type : 'POST',
        dataType : 'json',
        success  : function(data) {
            if (data.result == true) {
            	//设置版本号
            	$('#version').text("Version "+data.systemVersionVo.versionNum);
            }
        }
    });
}

$(document).ready(function(){
	getVersion();
	downloadApp.action.loadServerApp();
	pageDownload.action.pageDownloadList();
	$('button[id=download-file-btn]').each(function(){
        $(this).on('click', function(event) {
        	downloadApp.data.id = $(event.target).closest("#download-file-btn").attr("download-file-id");
    		location.href = getRootPath()+"/" +"downloadFile.action?id=" + downloadApp.data.id;
        });
    });
	if(document.documentElement.clientWidth<800){
		$("body").eq(0).css("overflow-x","scroll");
		$(".header-div").width(800);
	}
	$(window).resize(function(){
		if(document.documentElement.clientWidth<800){
			$("body").eq(0).css("overflow-x","scroll");
			$(".header-div").width(800);
		}
		if(document.documentElement.clientWidth>=800){
			$("body").eq(0).css("overflow-x","hidden");
			$(".header-div").width(document.documentElement.clientWidth);
		}
	});
});
