var downloadApp = {
    data : {
        id : '',
        code : '',
        removeId : '',
        uploadSucc : false,
    },
    action : {
        addApp : function(id,filepath, iconpath, name, uploadtime, size, description)
        {
            var divStr = "<div class=\"download-update-list\" data-verid =\"" + id + "\" id=\""+id+"\">" + 
			            	"<div class=\"downloadapp-icon\"><img src=\"" + iconpath.replace(/\\/g,"/")+"\" width=\"48\" height=\"48\"></div>" + 
			            	"<h4>" + name + "</h4>" +
			            	"<div class=\"rom-version-date\">" + 
				            	"<span><strong><span class=\"glyphicon glyphicon-calendar\" aria-hidden=\"true\"></span>更新日期:</strong>" + uploadtime +"</span>" + 
				            	"<span><strong><span class=\"glyphicon glyphicon-file\" aria-hidden=\"true\"></span>大小:</strong> " + size + " MB </span>" + 
			            	"</div>" + 
			            	"<h5>更新说明</h5>" +
				            "<div class=\"rom-infro\">" + 
				               "<p>" + description + "</p>" + 
				            "</div>" + 
				            "<a href=\"javascript:void(0);\" class=\"detail\" id=\""+id+"\">点击查看全部</a>"+
				            "<p id=\"hideDesc"+id+"\" style=\"display:none;\">"+description+"</p>"+
				            "<button class=\"btn btn-default remove-btn\"   ><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\" ></span><br/>删除</button>" + 
				          "</div>"
            $(divStr).appendTo("#roms-holder");

            //删除按钮监听事件
            $(".remove-btn").on('click',function(event){
            	downloadApp.data.removeId = $(event.target).closest(".download-update-list").index();
            	downloadApp.data.id = $(event.target).closest(".download-update-list").attr("data-verid");
                $('#delModal').modal('show');
            });
            
        },
        loadDownLoadFileInfo : function()
        {
            $.ajax({
                url  : 'system/download/main/loadDownLoadFileInfo.action',
                type : 'POST',
                success  : function(data)
                {
                    if (data.result == true) {
                        var jsonData = data.downloadFileVoList;
                        $("#roms-holder").empty();
                        for (var i = 0; i < jsonData.length; i++) {
                            var appObj = jsonData[i];
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
	                            	iconpath = appObj[key];
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
                            downloadApp.action.addApp(id,filepath, iconpath, name, uploadtime, size, description);
                        }
                        
                      //监听版本更新说明
                        $('a[class=detail]').each(function(){
                            $(this).on('click', function(event) {
                                $("#detailUpdate").empty();
                                $("#detailUpdate").text($("#hideDesc"+$(this).attr("id")).html().replace(/<br>/g,'\r\n'));
                                $('#detailModal').modal('show');
                            });
                        });
                    } else {
                        toastr.error(data.errorMsg);
                    }   
                },
                error : function() 
                {
                    toastr.error("服务器通讯失败");
                }
            });
        }
    }
};

$(document).ready(function()
{
    //从后台获取软件下载中心软件信息：软件名称，软件大小，更新日期
    downloadApp.action.loadDownLoadFileInfo();
    
  //监听确定删除按钮
    $('#delBtn').on('click', function() {
        var param = {
        	id : downloadApp.data.id
        };
        $.ajax({
            url : "system/download/main/deleteDownLoadFileInfo.action",
            type: "POST",
            dataType: "json",
            data    : param,
            success: function(data)
            {
            	if(data.result == true)
            	{
	                $("#roms-holder > div:eq("+downloadApp.data.removeId +")").remove();
	                $("#delModal").modal('hide');
	                toastr.success("成功删除软件");
            	}
            	else
            	{
            		$("#delModal").modal('hide');
            		toastr.error(data.errorMsg);
            	}
            },
            error : function(data) 
            {
                $("#delModal").modal('hide');
                toastr.error("网络异常");
            }
        });
    });
    
    var sessionID = $("#sessionID").val();// jsessionid 为了解决火狐下的问题。
        //上传文件
    $("#choose-rom").uploadify({
        auto       : true, 
        buttonClass:'btn-block',
        swf        : 'res/js/uploadify/uploadify.swf',    //上传使用的Flash
        uploader   : '../upload/uploadDownloadFile.action;jsessionid=' + sessionID,//上传路径
        fileObjName: 'downloadFile',
        buttonText : '点击浏览文件',
        fileSizeLimit : '300MB',
        removeCompleted : true,
        onUploadError : function(file, errorCode, errorMsg, errorString) {
            $("#choose-rom").uploadify('disable', false);
            if(errorCode == -280){
            	toastr.warning("文件上传已被用户取消");
            } else if(errorCode == -200 || errorCode == -220 ) {
            	var p = window;
            	while (p != p.parent) {
            		p = p.parent;
            	}
            	p.location.href = 'welcome.action';
            } else {
            	toastr.error("文件上传失败(" + errorCode + ")，请上传300M以内的文件");
            }
            $("#romUpdateModal").modal("hide");
            $(".uploadify-queue").html("");
        },
        onUploadSuccess : function(file, data, response) {
            $("#choose-rom").uploadify('disable', false);
            var jsonData = JSON.parse(data);
            if (jsonData.result == false)
            {
            	toastr.error("文件上传失败，错误原因：" + jsonData.errorMsg);
                $("#romUpdateModal").modal("hide");
            }
            else
            {
            	$('#softwareIcon').attr("src",jsonData.downloadFileVo.iconpath.replace(/\\/g,"/"));            	
            	$('#softwareName').text(jsonData.downloadFileVo.name);
	            $('#softwareSize').text((jsonData.downloadFileVo.size/1048576).toFixed(2) + " MB");
	            $('#softwareTime').text(jsonData.downloadFileVo.uploadtime);
	            downloadApp.data.uploadSucc = true;
	            $(".app-info-text").show();
            }
            $(".uploadify-queue").html("");
        },
        onUploadStart : function(file) {   
        	$(".close").hide();
           	$("#choose-rom").uploadify('disable', true);
           	$("#choose-rom").uploadify("settings", "formData", {
		    	"downloadFileName":file.name});
		},
        onUploadComplete : function()
        {
            $("#choose-rom").uploadify('disable', false);
            if (downloadApp.data.uploadSucc) {
                //$(".form-horizontal").hide();
                downloadApp.data.uploadSucc = false;
            }
        },
        onFallback : function() {
            toastr.error("未安装flash插件，无法进行文件上传");
            $('#uploadBtn').attr("disabled", true);
        },
        onCancel   : function() {
            $(".close").show();
        }
    });
    
    $("#clseDetail").on('click', function() {
        $('#detailModal').modal('hide');
    });
    
    $('#detailModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    	
    $('#detailModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });
    //监听romUpdateModal的右上角关闭按钮的事件
    $('#romUpdateModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
        $(".form-horizontal").show();
        $(".app-info-text").hide();
        $(".close").show();
        $('#softwareDesc').val("");
    });
    
    $('#romUpdateModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    
    //取消保存信息
    $("#app-cancel-btn").on('click', function()
    {
    	//删除固件上传临时文件
    	$.post("system/download/upload/cancelSaveDownloadFile.action");
    });
    
    $('#delModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    	
    $('#delModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });	
    
  //监听版本更新说明的字数限制
    $("#softwareDesc").on('keyup',function(){    	
    	var length=$("#softwareDesc").val().length;
    	var patrn = /[\u4e00-\u9fa5]/;
        var count = 0;
        for (var i = 0; i < length; i++) {
            if (patrn.test($("#softwareDesc").val()[i])) {
                count += 2;
            } else {
                count += 1;
            }
            if (count > 512) {
            	$("#errorInfo").css("display","block");
            	$("#softwareDesc").val($("#softwareDesc").val().substr(0,i));
            	break;
            }
            else if(count < 512){
            	$("#errorInfo").css("display","none");
            }
        }
    });
    //监听保存按钮
    $("#app-upload-btn").on('click', function() {
    	param={
    		downloadFileDescription : $('#softwareDesc').val()
    	}
        $.ajax({
            url     : "system/download/upload/saveDownloadFileInfo.action",
            type    : "POST",
            dataType: "json",
            data    : param,
            success : function(data)
            {
                if (data.result == false) {
                    $(".form-horizontal").show();
                    $(".app-info-text").hide();
                    $("#romUpdateModal").modal("hide"); 
                    toastr.error(data.errorMsg);
                } else {
                	$("#errorInfo").css("display","none");
                    $(".form-horizontal").show();
                    $(".app-info-text").hide();
                    $("#romUpdateModal").modal("hide"); 
                    toastr.success("成功保存软件信息");
                    //重新加载页面
                    downloadApp.action.loadDownLoadFileInfo();
                }
            },
            error : function(data) 
            {
                $(".form-horizontal").show();
                $(".app-info-text").hide();                
                $("#romUpdateModal").modal("hide");
                toastr.error("服务器通讯失败");
            }
        });  
    });
    function getFileName(path){
    	var pos1 = path.lastIndexOf('/');
    	var pos2 = path.lastIndexOf('\\');
    	var pos  = Math.max(pos1, pos2)
    	if( pos<0 )
    	return path;
    	else
    	return path.substring(pos+1);
    }
});