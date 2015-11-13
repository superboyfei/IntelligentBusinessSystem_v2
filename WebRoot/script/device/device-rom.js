var deviceRomUpdate = {
    data : {
        id : '',
        code : '',
        removeId : '',
        uploadSucc : false
    },
    action : {
        addDevice : function(id, code, name, version, updateDate, description) 
        {
            var divStr="<div class='roms-update-list' data-verid="+ id +" data-code="+ code +"><h4>"+ name +"</h4><div class='rom-version-date'><span><strong><span class='glyphicon glyphicon-cd' aria-hidden='true'></span>固件版本:</strong>"+ version +"</span><span><strong><span class='glyphicon glyphicon-calendar' aria-hidden='true'></span>更新日期: </strong>"+ updateDate +"</span></div><h5>更新说明</h5><div class='rom-infro'>"+ description +"</div><a href='javascript:void(0);'id='detail'>点击查看全部</a><button class='btn btn-default remove-btn'><span class='glyphicon glyphicon-trash' aria-hidden='true' ></span><br/>删除</button></div>";
            $(divStr).appendTo("#roms-holder");
            
            //删除按钮监听事件
            $(".remove-btn").on('click',function(event){
                deviceRomUpdate.data.removeId = $(event.target).closest(".roms-update-list").index();
                deviceRomUpdate.data.id = $(event.target).closest(".roms-update-list").attr("data-verid");
                $('#delModal').modal('show');
            });
            
            //监听查看全部
            $('a[id=detail]').each(function(){
                $(this).on('click', function(event) {
                    $("#detailUpdate").empty();
                    deviceRomUpdate.data.code = $(event.target).closest(".roms-update-list").attr("data-code");
                    $('#detailModal').modal('show');
                });
            });
        },
        loadFirmwareInfo: function()
        {
            $.ajax({
                url      : "device/remotecontrol/firmwareupdate/main/loadFirmwareInfo.action",
                type     : "POST",
                dataType : "json",     
                success  : function(data)
                {
                    if (data.result == true)
            	    {
                        var jsonData = data.firmwareTypeVoList;
                        $("#roms-holder").empty();
                        for (var i = 0; i < jsonData.length; i++)
                        {
                            var deviceObj = jsonData[i];
                            var id, description, name, version, updateDate;
                            for (key in deviceObj)
                            {   
                                if (key == "id") 
                                {
                                    id = deviceObj[key];
                                }
                                else if (key == "code")
                                {
                                    code = deviceObj[key];
                                }
                                else if (key == "name")
                                {
                                    name = deviceObj[key];
                                } else if (key == "version")
                                {
                                    version = deviceObj[key];
                                } else if (key == "updatetime")
                                {
                                    updateDate = deviceObj[key];
                                } else if (key == "description")
                                {
                                    description = deviceObj[key].replace(/#/g, '<br/>');
                                } 
                            }
                            deviceRomUpdate.action.addDevice(id, code, name, version, updateDate, description);
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
    //从后台获取数据库固件版本信息：固件名称，固件版本，更新日期
    deviceRomUpdate.action.loadFirmwareInfo();
    
    //监听确定删除按钮
    $('#delBtn').on('click', function() {
        var param = {
            id : deviceRomUpdate.data.id
        };
        $.ajax({
            url : "device/remotecontrol/firmwareupdate/main/deleteFirmwareInfo.action",
            type: "POST",
            dataType: "json",
            data    : param,
            success: function(data)
            {
            	if(data.result == true)
            	{
	                $("#roms-holder > div:eq("+deviceRomUpdate.data.removeId +")").remove();
	                $("#delModal").modal('hide');
	                toastr.success("成功删除固件信息");
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
        uploader   : '../upload/uploadFirmwareFile.action;jsessionid=' + sessionID,//上传路径
        fileObjName: 'firmwareFile',
        buttonText : '上传固件',
        removeCompleted : true,
        fileSizeLimit : '300MB',
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
            	if(jsonData.errorLevel == "error"){
            		toastr.error(jsonData.errorMsg);
            	} else {
            		toastr.warning(jsonData.errorMsg);
            	}
                $("#romUpdateModal").modal("hide");
            }
            else
            {
	            deviceRomUpdate.data.id = jsonData.firmwareTypeVo.id;
	            $('#firewareName').text(jsonData.firmwareTypeVo.name);
	            $('#firewareVer').text(jsonData.firmwareTypeVo.version);
	            $('#firewareDesc').text(jsonData.firmwareTypeVo.description);
	            deviceRomUpdate.data.uploadSucc = true;
	            $(".rom-info-text").show();
            }
            $(".uploadify-queue").html("");
        },
        onUploadStart : function() {    
        	$(".close").hide();
           	$("#choose-rom").uploadify('disable', true);
        },
        onUploadComplete : function()
        {
            $("#choose-rom").uploadify('disable', false);
            if (deviceRomUpdate.data.uploadSucc) {
                $(".form-horizontal").hide();
                deviceRomUpdate.data.uploadSucc = false;
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
    
    //监听romUpdateModal的右上角关闭按钮的事件
    $('#romUpdateModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
        $(".form-horizontal").show();
        $(".rom-info-text").hide();
        $(".close").show();
    });
    $('#romUpdateModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();    	
    });	

    $('#detailModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    	
    $('#detailModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });	
    //监听取消按钮
    $('#cancelBtn').on('click', function() {
        $(".form-horizontal").show();
        $(".rom-info-text").hide();            
    }); 
    $('#delModal').on('show.bs.modal', function (e) {
    	window.parent.showcover();
    });	
    	
    $('#delModal').on('hide.bs.modal', function (e) {
    	window.parent.hidecover();
    });	
    //取消保存信息
    $("#rom-cancel-btn").on('click', function()
    {
    	//删除固件上传临时文件
    	$.post("device/remotecontrol/firmwareupdate/upload/cancelSaveFirmwarInfo.action");
    });
    //监听固件保存按钮
    $("#rom-upload-btn").on('click', function() {
        $.ajax({
            url     : "device/remotecontrol/firmwareupdate/upload/saveFirmwareInfo.action",
            type    : "POST",
            dataType: "json",
            success : function(data)
            {
                if (data.result == false) {
                    $(".form-horizontal").show();
                    $(".rom-info-text").hide();
                    $("#romUpdateModal").modal("hide"); 
                    toastr.error(data.errorMsg);
                } else {
                    $(".form-horizontal").show();
                    $(".rom-info-text").hide();
                    $("#romUpdateModal").modal("hide"); 
                    toastr.success("成功保存固件信息");
                    //重新加载页面
                    deviceRomUpdate.action.loadFirmwareInfo();
                }
            },
            error : function(data) 
            {
                $(".form-horizontal").show();
                $(".rom-info-text").hide();                
                $("#romUpdateModal").modal("hide");
                toastr.error("服务器通讯失败");
            }
        });  
    });
    
    $("#clseDetail").on('click', function() {
        $('#detailModal').modal('hide');
    });
    
    $("#detailModal").on('shown.bs.modal', function(){
    	var param = {
    		code : deviceRomUpdate.data.code
    	}
        //从后台获取详细更新信息
        var param = {
            code : deviceRomUpdate.data.code
        }
        $.ajax({
            url  : "device/remotecontrol/firmwareupdate/main/lookFirmwareUpdateInfo.action",
            type : "POST",
            dataType : "json",
            data     : param,
            success  : function(data)
            {
                if (data.result == true)
                {
                    $("#detailUpdate").text((data.firmwareInfo).replace(/#/g, '\r\n'));
                    $("#detailUpdate").text((data.firmwareInfo).replace(/%/g, '\r\n').replace(/&/g, '----------'));
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