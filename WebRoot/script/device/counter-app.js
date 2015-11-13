var counterAppUpdate = {
    data : {
        id : '',
        code : '',
        removeId : '',
        uploadSucc : false,
    },
    action : {
        addApp : function(id, code, name, version, updateDate, description)
        {
            var divStr = "<div class='tellerApps-update-list' data-verid="+ id +" data-code="+ code +"><h4>"
            	+ name +"</h4><div class='rom-version-date'><span><strong><span class='glyphicon glyphicon-cd' aria-hidden='true'></span>应用版本:</strong>"
            	+ version +"</span><span><strong><span class='glyphicon glyphicon-calendar' aria-hidden='true'></span>更新日期: </strong>"
            	+ updateDate +"</span></div><h5>更新说明</h5><div class='rom-infro'>"
            	+ description +"</div><a href='javascript:void(0);'id='detail'>点击查看全部</a><button class='btn btn-default remove-btn'><span class='glyphicon glyphicon-trash' aria-hidden='true' ></span><br/>删除</button></div>";
            
            $(divStr).appendTo("#tellerApps-holder");
            
            //删除按钮监听事件
            $(".remove-btn").on('click',function(event){
            	counterAppUpdate.data.removeId = $(event.target).closest(".tellerApps-update-list").index();
            	counterAppUpdate.data.id = $(event.target).closest(".tellerApps-update-list").attr("data-verid");
                $('#delModal').modal('show');
            });
            
            //监听查看全部
            $('a[id=detail]').each(function(){
                $(this).on('click', function(event) {
                    $("#detailUpdate").empty();
                    counterAppUpdate.data.id = $(event.target).closest(".tellerApps-update-list").attr("data-verid");
                    $('#detailModal').modal('show');
                });
            });
        },
        loadCounterAppInfo : function()
        {
            $.ajax({
                url  : 'device/remotecontrol/counterappupdate/main/loadCounterAppInfo.action',
                type : 'POST',
                dataType : 'json',
                success  : function(data)
                {
                    if (data.result == true) {
                        var jsonData = data.counterAppTypeVoList;
                        $("#tellerApps-holder").empty();
                        for (var i = 0; i < jsonData.length; i++) {
                            var appObj = jsonData[i];
                            var id, description, name, version, updateDate;
                            for (key in appObj)
                            {   
                                if (key == "id") 
                                {
                                    id = appObj[key];
                                }
                                else if (key == "code")
                                {
                                    code = appObj[key];
                                }
                                else if (key == "name")
                                {
                                    name = appObj[key];
                                } else if (key == "version")
                                {
                                    version = appObj[key];
                                } else if (key == "updatetime")
                                {
                                    updateDate = appObj[key];
                                } else if (key == "description")
                                {
                                    description = appObj[key].replace(/#/g, '<br/>');
                                } 
                            }
                            counterAppUpdate.action.addApp(id, code, name, version, updateDate, description);
                        }
                    } else {
                        toastr.error(data.retMsg);
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
    //从后台获取柜员应用版本信息：应用名称，应用版本，更新日期
    counterAppUpdate.action.loadCounterAppInfo();
    
  //监听确定删除按钮
    $('#delBtn').on('click', function() {
        var param = {
        	counterAppTypeId : counterAppUpdate.data.id
        };
        $.ajax({
            url : "device/remotecontrol/counterappupdate/main/deleteCounterAppInfo.action",
            type: "POST",
            dataType: "json",
            data    : param,
            success: function(data)
            {
            	if(data.result == true)
            	{
	                $("#tellerApps-holder > div:eq("+counterAppUpdate.data.removeId +")").remove();
	                $("#delModal").modal('hide');
	                toastr.success("成功删除应用");
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
        multi	   : false,
        swf        : 'res/js/uploadify/uploadify.swf',    //上传使用的Flash
        uploader   : '../upload/uploadCounterAppFile.action;jsessionid=' + sessionID,//上传路径
        fileObjName: 'counterAppFile',
        buttonText : '上传应用',
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
            $("#tellerAppsModal").modal("hide");
            $(".uploadify-queue").html("");
        },
        onUploadSuccess : function(file, data, response) {
            $("#choose-rom").uploadify('disable', false);
            var jsonData = JSON.parse(data);
            if (jsonData.result == false)
            {
            	if(jsonData.errorLevel == "error"){
            		toastr.error(jsonData.errorMsg);
            	} else {
            		toastr.warning(jsonData.errorMsg);
            	}
                $("#tellerAppsModal").modal("hide");
            }
            else
            {
	            $('#firewareName').text(jsonData.counterAppTypeVo.name);
	            $('#firewareVer').text(jsonData.counterAppTypeVo.version);
	            $('#firewareDesc').text(jsonData.counterAppTypeVo.description);
	            counterAppUpdate.data.uploadSucc = true;
	            $(".app-info-text").show();
            }
            $(".uploadify-queue").html("");
        },
        onUploadStart : function() {            
        	$(".close").hide();
        	$("#choose-rom").uploadify("settings", "formData", {'counterAppTypeId'  : counterAppUpdate.data.id});
           	$("#choose-rom").uploadify('disable', true);
        },
        onUploadComplete : function()
        {
            $("#choose-rom").uploadify('disable', false);
            if (counterAppUpdate.data.uploadSucc) {
                $(".form-horizontal").hide();
                counterAppUpdate.data.uploadSucc = false;
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
    $('#detailModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    	
    $('#detailModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });	
    //监听romUpdateModal的右上角关闭按钮的事件
    $('#tellerAppsModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
        $(".form-horizontal").show();
        $(".app-info-text").hide();
        $(".close").show();
    });
    $('#tellerAppsModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });
    //取消保存信息
    $("#app-cancel-btn").on('click', function()
    {
    	//删除固件上传临时文件
    	$.post("device/remotecontrol/counterappupdate/upload/cancelSaveFirmwarInfo.action");
    });
    
    //监听保存按钮
    $("#app-upload-btn").on('click', function() {
        $.ajax({
            url     : "device/remotecontrol/counterappupdate/upload/saveCounterAppInfo.action",
            type    : "POST",
            dataType: "json",
            success : function(data)
            {
                if (data.result == false) {
                    $(".form-horizontal").show();
                    $(".app-info-text").hide();
                    $("#tellerAppsModal").modal("hide"); 
                    toastr.error(data.errorMsg);
                } else {
                    $(".form-horizontal").show();
                    $(".app-info-text").hide();
                    $("#tellerAppsModal").modal("hide"); 
                    toastr.success("成功保存固件信息");
                    //重新加载页面
                    counterAppUpdate.action.loadCounterAppInfo();
                }
            },
            error : function(data) 
            {
                $(".form-horizontal").show();
                $(".app-info-text").hide();                
                $("#tellerAppsModal").modal("hide");
                toastr.error("服务器通讯失败");
            }
        });  
    });
    
    $("#clseDetail").on('click', function() {
        $('#detailModal').modal('hide');
    });
    $('#delModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    	
    $('#delModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });
    $("#detailModal").on('shown.bs.modal', function(){
        //从后台获取详细更新信息
        var param = {
            counterAppTypeId  : counterAppUpdate.data.id
        }
        $.ajax({
            url  : "device/remotecontrol/counterappupdate/main/lookCounterAppUpdateInfo.action",
            type : "POST",
            dataType : "json",
            data     : param,
            success  : function(data)
            {
                if (data.result == true)
                {
                    $("#detailUpdate").text((data.counterAppInfo).replace(/#/g, '\r\n'));
                    $("#detailUpdate").text((data.counterAppInfo).replace(/%/g, '\r\n').replace(/&/g, '----------'));
                } else {
                    toastr.error(data.errorMsg);
                }
            },
            error    : function()
            {
                toastr.error("服务器通讯失败");
            }
        });
    });
});