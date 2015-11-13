var reflectManage = {

	reflectGrid	:
	{
		initEvent		: function()
		{
			$('a[id=reflectCtrl]').each(function() {
				$(this).on('click', function() {
					reflectManage.reflectGrid.initData.call(this);
				});
			});
		},
		initData	: function()
		{
			var $td = $(this).parent();
            var id = $td.siblings("td:eq(0)").text();			
			var city = $td.siblings("td:eq(1)").text();
			var outlets = $td.siblings("td:eq(2)").text();
			var number = $td.siblings("td:eq(3)").text();
            var startIP = $td.siblings("td:eq(4)").text();
            var endIP = $td.siblings("td:eq(5)").text();
            var submask = $td.siblings("td:eq(6)").text();
            reflectManage.data.reflect.id = id;
			reflectManage.data.reflect.city = city;
			reflectManage.data.reflect.outlets = outlets;
			reflectManage.data.reflect.number = number;
            reflectManage.data.reflect.startIP = startIP;
            reflectManage.data.reflect.endIP = endIP;
            reflectManage.data.reflect.submask = submask;
			$("#edit_city").val(reflectManage.data.reflect.city);
			$("#edit_outlets").val(reflectManage.data.reflect.outlets);	
			$("#edit_number").val(reflectManage.data.reflect.number);
			$("#edit_startIP").val(reflectManage.data.reflect.startIP);	
			$("#edit_endIP").val(reflectManage.data.reflect.endIP);
			$("#edit_submask").val(reflectManage.data.reflect.submask);	
		},
		loadReflectGrid	: function()
		{
			$("#reflect-jqgrid").jqGrid(
			{
				url			: 'system/hubei/reflect/main/loadReflectsByPage.action',
				datatype	: 'json',
				colNames	: ['索引', '城市', '网点名称', '网点机构号', '起始ip', '结束ip', '子网掩码', '', ''],
				colModel	: [
				{
					name	: 'id',
					index	: 'id',
					width	: 100,
					hidden	: true,
					align	: 'center',
                    sortable: false
				},				        	   
				{
					name	: 'city',
					index	: 'city',
					width	: 100,
					align	: 'center',
                    sortable: false
				},
				{
					name	: 'outlets',
					index	: 'outlets',
					width	: 100,
					align	: 'center',
                    sortable: false
				},
				{
					name	: 'code',
					index	: 'code',
					width	: 100,
					align	: 'center',
                    sortable: false
				},
				{
					name	: 'start',
					index	: 'start',
					width	: 100,
					align	: 'center',
                    sortable: false
				},
				{
					name	: 'end',
					index	: 'end',
					width	: 100,
					align	: 'center',
                    sortable: false
				},
				{
					name	: 'submask',
					index	: 'submask',
					width	: 100,
					align	: 'center',
                    sortable: false
				},
                {
                    name    : 'edit',
                    index   : 'edit',
                    align   : 'center',
                    width   : 40,
                    sortable: false
                },
                {
                    name    : 'del',
                    index   : 'del',
                    width   : 40,
                    align   : 'center',
                    sortable: false
                }],
				rowList 		: [10, 20, 30, 40, 50, 100],
				viewrecords		: true,
				altRows			: true,
				altclass        : 'ui-widget-content-altclass',
				autowidth	    : true,
				rowNum		    : 20,
				pager		    : "#reflect-jqgrid-page",
				jsonReader	    :
				{
					root		: "tableVo.list",
					total		: "tableVo.totalpages",
					page		: "tableVo.currpage",
					records		: "tableVo.totalrecords",
					repeatitems	: false
				},
				gridComplete    : function() {
		            var ids = $("#reflect-jqgrid").getDataIDs();
		            for (var i = 0; i < ids.length; i++) {
                        ed = "<a id='reflectCtrl' href='javascript:void(0);' class='blue' data-toggle='modal' data-target='#editReflectModal'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span>编辑</a>";  
                        de = "<a id='reflectCtrl' href='javascript:void(0);' class='blue' data-toggle='modal' data-target='#delReflectModal' ><span class='glyphicon glyphicon-trash' aria-hidden='true'></span>删除</a>";
		                jQuery("#reflect-jqgrid").jqGrid('setRowData', ids[i], {edit : ed, del : de});
		            }		
		            reflectManage.reflectGrid.initEvent();
				},
				onPaging	: function(pgButton)
				{
					var lastpage = this.p.lastpage;
    				var pagerId = this.p.pager.substr(1);
    				var $pageInput = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId));
					var newValue = $pageInput.val();
					if (pgButton === "reflect" && newValue > lastpage)
					{ 
						$pageInput.val(lastpage);
					}
				}
			});
			reflectManage.initUIOption.doResize();
		}
	},
	reflectControl	:
	{
		saveReflect	: function()
		{            
			var city = $('#newReflectForm input[name=city]').val();
			var outlets = $('#newReflectForm input[name=outlets]').val();
			var number = $('#newReflectForm input[name=number]').val();
			var startIP = $('#newReflectForm input[name=startIP]').val();
			var endIP = $('#newReflectForm input[name=endIP]').val();
			var submask = $('#newReflectForm input[name=submask]').val();
			var param = {
				city	: city,
				outlets	: outlets,
				number  : number,
				startIP	: startIP,
				endIP	: endIP,
				submask : submask,
			};
            $.ajax({
                url     : "system/hubei/reflect/add/saveReflect.action",
                type    : "POST",
                dataType: "json",
                data    : param,
                success : function(data) {
                    $("#newReflectModal").modal('hide');
                    $('#newReflectForm').validate().resetForm();
                    window.parent.showLoading();
                    if (data.result == true)
                    {
                        toastr.success('新增映射成功');
                        //更新界面数据
                        $("#reflect-jqgrid").trigger("reloadGrid");
                        window.parent.hideLoading();
                        reflectManage.data.reflect.id = data.reflectVo.id;//保存新增映射的id
                        $("#reflect-jqgrid").trigger("reloadGrid");
                    }
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    $("#newReflectModal").modal('hide');
                    $('#newReflectForm').validate().resetForm();
                    window.parent.hideLoading();
                    toastr.error("服务器通讯失败");
                }
            });             
		},
		updateReflect	: function(newpass)
		{
			var city = $('#updateReflectForm input[name=edit_city]').val();
			var outlets = $('#updateReflectForm input[name=edit_outlets]').val();
			var number = $('#updateReflectForm input[name=edit_number]').val();
			var startIP = $('#updateReflectForm input[name=edit_startIP]').val();
			var endIP = $('#updateReflectForm input[name=edit_endIP]').val();
			var submask = $('#updateReflectForm input[name=edit_submask]').val();
			var param = {
				id		  : reflectManage.data.reflect.id,
				city      : city,
				outlets   : outlets,
				number    : number,
				startIP   : startIP,
				endIP     : endIP,
				submask   : submask
			};
            $.ajax({
                url     : "system/hubei/reflect/update/updateReflect.action",
                type    : "POST",
                dataType: "json",
                data    : param,
                success : function(data) {
                    $("#editReflectModal").modal('hide');
                    $('#updateReflectForm').validate().resetForm();
                    window.parent.hideLoading();
                    if (data.result == true)
                    {
                        toastr.success('更新映射成功');
                        //更新界面数据
                        $("#reflect-jqgrid").trigger("reloadGrid");
                    }
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    $("#editReflectModal").modal('hide');
                    $('#updateReflectForm').validate().resetForm();
                    window.parent.hideLoading();
                    toastr.error("服务器通讯失败");
                }
            });            
		},
		deleteReflect	: function()
		{	
			var param = {
				id	: reflectManage.data.reflect.id
			};
            $.ajax({
                url     : "system/hubei/reflect/main/delReflect.action",
                type    : "POST",
                dataType: "json",
                data    : param,
                success : function(data) {
                    $('#delReflectModal').modal('hide');
                    window.parent.hideLoading();               
                    if (data.result == true)
                    {
                        toastr.success('删除映射成功');
                        //更新界面数据
                        $("#reflect-jqgrid").trigger("reloadGrid");
                    }
                    else
                    {
                        toastr.error(data.errorMsg);
                    }
                },
                error   : function(data) {
                    $('#delReflectModal').modal('hide');
                    window.parent.hideLoading();
                    toastr.error("服务器通讯失败");
                }
            });            
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
					reflectManage.initUIOption.doResize();
				}
			});
		},
		doResize	: function ()
		{
			var ss = reflectManage.initUIOption.getPageSize();
			$("#reflect-jqgrid").jqGrid('setGridWidth', ss.WinW - 20)
			$("#reflect-jqgrid").jqGrid('setGridHeight', ss.WinH - 130);
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
		//映射数据封装
		reflect	:
		{
			id      : '',
			city	: '',
			outlets	: '',
			number	: '',
			startIP	: '',
			endIP	: '',
			submask	: ''
		},
	}
}

$(document).ready(function()
{   
	$("#city").val("");
	$("#outlets").val("");
	$("#number").val("");
	$("#startIP").val("");
	$("#endIP").val("");
	$("#submask").val("");
	//jqgrid默认没有填充屏幕，需要手动进行调整
	reflectManage.initUIOption.resizeWindow();
	reflectManage.reflectGrid.loadReflectGrid();
	
	function clearReflectOutlets(){
		$("#reflect_outlets_div").removeClass("has-error has-feedback").removeClass("has-success has-feedback");	
		$("#reflect_outlets_success").remove();
		$("#reflect_outlets_error").remove();
    }
	function clearReflectNumber(){
		$("#reflect_number_div").removeClass("has-error has-feedback").removeClass("has-success has-feedback");	
		$("#reflect_number_success").remove();
		$("#reflect_number_error").remove();
    }
	function clearReflectIP(){
		$("#reflect_ip_div").removeClass("has-error has-feedback").removeClass("has-success has-feedback");	
		$("#reflect_ip_success").remove();
		$("#reflect_ip_error").remove();
    }
	function clearReflectEditOutlets(){
		$("#reflect_edit_outlets_div").removeClass("has-error has-feedback").removeClass("has-success has-feedback");	
		$("#reflect_edit_outlets_success").remove();
		$("#reflect_edit_outlets_error").remove();
    }
//	function clearReflectEditNumber(){
//		$("#reflect_edit_number_div").removeClass("has-error has-feedback").removeClass("has-success has-feedback");	
//		$("#reflect_edit_number_success").remove();
//		$("#reflect_edit_number_error").remove();
//    }
	function clearReflectEditIP(){
		$("#reflect_edit_ip_div").removeClass("has-error has-feedback").removeClass("has-success has-feedback");	
		$("#reflect_edit_ip_success").remove();
		$("#reflect_edit_ip_error").remove();
    }
	
	var nowCity="";
	var nowOutlets="";
	var nowNumber="";
	var nowStartIP="";
	var nowEndIP="";
	$('#editReflectModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
		nowCity=$("#edit_city").val();
        nowOutlets=$("#edit_outlets").val();
        nowNumber=$("#edit_number").val();
        nowStartIP=$("#edit_startIP").val();
        nowEndIP=$("#edit_endIP").val();
	});
	
	//验证新增网点是否重名
	var uniqueOutlets = true;
	$('#outlets,#city').on('blur', function()
	{
		var patrn =/^[\u0391-\uFFE5A-Za-z]+$/;
		var city = $("#city").val();
		var outlets = $("#outlets").val();
		if (outlets == "" || city == "")
		{
			clearReflectOutlets();
			$("#outletsMsg").hide();
			return false;
		}
		for (var i = 0; i < city.length; i++) {
            if (patrn.test(city[i]) == false) {
            	clearReflectOutlets();
    			$("#outletsMsg").hide();
    			return false;
            }
		}
		for (var i = 0; i < outlets.length; i++) {
            if (patrn.test(outlets[i]) == false) {
            	clearReflectOutlets();
    			$("#outletsMsg").hide();
    			return false;
            }
		}
		var param = {
				city   : city,
				outlets: outlets
		};
		$.ajax({
			url     : "system/hubei/reflect/add/validateOutlets.action",
			type    : "POST",
			dataType: "json",
			data    : param,
			async   : false,
			success : function(data) {
				if (data.result == true)
				{
			     	$("#outletsMsg").text(data.message).css("color", "#3C763D").show();
			     	clearReflectOutlets();
			     	$("#reflect_outlets_div").addClass("has-success has-feedback");
			     	$("<span id='reflect_outlets_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#reflect_outlets_div")[0]);
			     	uniqueOutlets = true;
				}
				else
				{
					$("#outletsMsg").text(data.message).css("color", "#EC6F6F").show();
					clearReflectOutlets();
					$("#reflect_outlets_div").addClass("has-error has-feedback");
					$("<span id='reflect_outlets_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#reflect_outlets_div")[0]);
					uniqueOutlets = false;
		   	    }
            },
            error : function(data) {
                toastr.error("服务器通讯失败");
            }
        });
	}); 
	//验证编辑网点是否重名
	var uniqueEditOutlets = true;
	$('#edit_outlets,#edit_city').on('blur', function()
	{
		var patrn =/^[\u0391-\uFFE5A-Za-z]+$/;
		var city = $("#edit_city").val();
		var outlets = $("#edit_outlets").val();
		if (outlets == "" || city == "" )
		{
			clearReflectEditOutlets();
			$("#edit_outletsMsg").hide();
			return false;
		}	
		for (var i = 0; i < city.length; i++) {
            if (patrn.test(city[i]) == false) {
            	clearReflectEditOutlets();
    			$("#edit_outletsMsg").hide();
    			return false;
            }
		}
		for (var i = 0; i < outlets.length; i++) {
            if (patrn.test(outlets[i]) == false) {
            	clearReflectEditOutlets();
    			$("#edit_outletsMsg").hide();
    			return false;
            }
		}
		if(city==nowCity&&outlets==nowOutlets){
			clearReflectEditOutlets();
			$("#edit_outletsMsg").hide();
		}
		else{
			var param = {
					city   : city,
					outlets: outlets
			};
			$.ajax({
				url     : "system/hubei/reflect/add/validateOutlets.action",
				type    : "POST",
				dataType: "json",
				data    : param,
				async   : false,
				success : function(data) {
					if (data.result == true)
					{
				     	$("#edit_outletsMsg").text(data.message).css("color", "#3C763D").show();
				     	clearReflectEditOutlets();
				     	$("#reflect_edit_outlets_div").addClass("has-success has-feedback");
				     	$("<span id='reflect_edit_outlets_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
	                 	.appendTo($("#reflect_edit_outlets_div")[0]);
				     	uniqueEditOutlets = true;
					}
					else
					{
						$("#edit_outletsMsg").text(data.message).css("color", "#EC6F6F").show();
						clearReflectEditOutlets();
						$("#reflect_edit_outlets_div").addClass("has-error has-feedback");
						$("<span id='reflect_edit_outlets_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
	                 	.appendTo($("#reflect_edit_outlets_div")[0]);
						uniqueEditOutlets = false;
			   	    }
	            },
	            error : function(data) {
	                toastr.error("服务器通讯失败");
	            }
	        });
		}
	}); 
	//验证新增网点机构号是否重名
	var uniqueNumber = true, checkNum = true;
	$('#number').on('blur', function()
	{
		var number = $(this).val();
		if (number == "" || checkNum == false)
		{
			clearReflectNumber();
			$("#numberMsg").hide();
			return false;
		}	
		var param = {
				number   : number,
		};
		$.ajax({
			url     : "system/hubei/reflect/add/validateNumber.action",
			type    : "POST",
			dataType: "json",
			data    : param,
			async   : false,
			success : function(data) {
				if (data.result == true)
				{
			     	$("#numberMsg").text(data.message).css("color", "#3C763D").show();
			     	clearReflectNumber();
			     	$("#reflect_number_div").addClass("has-success has-feedback");
			     	$("<span id='reflect_number_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#reflect_number_div")[0]);
			     	uniqueNumber = true;
				}
				else
				{
					$("#numberMsg").text(data.message).css("color", "#EC6F6F").show();
					clearReflectNumber();
					$("#reflect_number_div").addClass("has-error has-feedback");
					$("<span id='reflect_number_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#reflect_number_div")[0]);
					uniqueNumber = false;
		   	    }
            },
            error : function(data) {
                toastr.error("服务器通讯失败");
            }
        });
	}); 
//	//验证编辑网点机构号是否重名
//	var uniqueEditNumber = true, checkNum = true;
//	$('#edit_number').on('blur', function()
//	{
//		var number = $(this).val();
//		if (number == "" || checkNum == false)
//		{
//			clearReflectEditNumber();
//			$("#edit_numberMsg").hide();
//			return false;
//		}	
//		if(number==nowNumber){
//			clearReflectEditNumber();
//			$("#edit_numberMsg").hide();
//		}
//		else{
//			var param = {
//					number   : number,
//			};
//			$.ajax({
//				url     : "system/hubei/reflect/add/validateNumber.action",
//				type    : "POST",
//				dataType: "json",
//				data    : param,
//				async   : false,
//				success : function(data) {
//					if (data.result == true)
//					{
//				     	$("#edit_numberMsg").text(data.message).css("color", "#3C763D").show();
//				     	clearReflectEditNumber();
//				     	$("#reflect_edit_number_div").addClass("has-success has-feedback");
//				     	$("<span id='reflect_edit_number_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
//	                 	.appendTo($("#reflect_edit_number_div")[0]);
//				     	uniqueEditNumber = true;
//					}
//					else
//					{
//						$("#edit_numberMsg").text(data.message).css("color", "#EC6F6F").show();
//						clearReflectEditNumber();
//						$("#reflect_edit_number_div").addClass("has-error has-feedback");
//						$("<span id='reflect_edit_number_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
//	                 	.appendTo($("#reflect_edit_number_div")[0]);
//						uniqueEditNumber = false;
//			   	    }
//	            },
//	            error : function(data) {
//	                toastr.error("服务器通讯失败");
//	            }
//	        });
//		}
//	}); 
	//验证新增ip是否可用
	var uniqueIP = true;
	$('#endIP,#startIP').on('blur', function()
	{
		var patrn =/^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
		var endIP = $("#endIP").val();
		var startIP = $("#startIP").val();
		if (startIP == "" || endIP == "" || patrn.test(startIP) == false || patrn.test(endIP) == false)
		{
			clearReflectIP();
			$("#ipMsg").hide();
			return false;
		}	
		var param = {
				startIP   : startIP,
				endIP     : endIP
		};
		$.ajax({
			url     : "system/hubei/reflect/add/validateNewIp.action",
			type    : "POST",
			dataType: "json",
			data    : param,
			async   : false,
			success : function(data) {
				if (data.result == true)
				{
			     	$("#ipMsg").text(data.message).css("color", "#3C763D").show();
			     	clearReflectIP();
			     	$("#reflect_ip_div").addClass("has-success has-feedback");
			     	$("<span id='reflect_ip_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#reflect_ip_div")[0]);
			     	uniqueIP = true;
				}
				else
				{
					$("#ipMsg").text(data.message).css("color", "#EC6F6F").show();
					clearReflectIP();
					$("#reflect_ip_div").addClass("has-error has-feedback");
					$("<span id='reflect_ip_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
                 	.appendTo($("#reflect_ip_div")[0]);
					uniqueIP = false;
		   	    }
            },
            error : function(data) {
                toastr.error("服务器通讯失败");
            }
        });
	}); 
	//验证编辑ip是否可用
	var uniqueEditIP = true;
	$('#edit_endIP,#edit_startIP').on('blur', function()
	{
		var patrn =/^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
		var number = $("#edit_number").val();
		var endIP = $("#edit_endIP").val();
		var startIP = $("#edit_startIP").val();
		if (startIP == "" || endIP == "" || patrn.test(startIP) == false || patrn.test(endIP) == false)
		{
			clearReflectEditIP();
			$("#edit_ipMsg").hide();
			return false;
		}	
		if(endIP==nowEndIP&&startIP==nowStartIP){
			clearReflectEditIP();
			$("#edit_ipMsg").hide();
		}
		else{
			var param = {
					number    : number,
					startIP   : startIP,
					endIP     : endIP
			};
			$.ajax({
				url     : "system/hubei/reflect/add/validateIp.action",
				type    : "POST",
				dataType: "json",
				data    : param,
				async   : false,
				success : function(data) {
					if (data.result == true)
					{
				     	$("#edit_ipMsg").text(data.message).css("color", "#3C763D").show();
				     	clearReflectEditIP();
				     	$("#reflect_edit_ip_div").addClass("has-success has-feedback");
				     	$("<span id='reflect_edit_ip_success' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>")
	                 	.appendTo($("#reflect_edit_ip_div")[0]);
				     	uniqueEditIP = true;
					}
					else
					{
						$("#edit_ipMsg").text(data.message).css("color", "#EC6F6F").show();
						clearReflectEditIP();
						$("#reflect_edit_ip_div").addClass("has-error has-feedback");
						$("<span id='reflect_edit_ip_error' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>")
	                 	.appendTo($("#reflect_edit_ip_div")[0]);
						uniqueEditIP = false;
			   	    }
	            },
	            error : function(data) {
	                toastr.error("服务器通讯失败");
	            }
	        });
		}
	}); 
	
	//自定义验证规则
    jQuery.validator.addMethod("checkIP", function(value, element) {
        var patrn =/^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
            if (patrn.test(value) == false) {
            	clearReflectIP();
            	$("#ipMsg").hide();
            	clearReflectEditIP();
            	$("#edit_ipMsg").hide();
                checkIP = false;
                return false;
            }
        checkIP = true;
        return true;
    });
    jQuery.validator.addMethod("checkSub", function(value, element) {
        var patrn =/^(254|252|248|240|224|192|128|0)\.0\.0\.0|255\.(254|252|248|240|224|192|128|0)\.0\.0|255\.255\.(254|252|248|240|224|192|128|0)\.0|255\.255\.255\.(254|252|248|240|224|192|128|0)$/;
            if (patrn.test(value) == false) {
                checkSub = false;
                return false;
            }
        checkSub = true;
        return true;
    });
    jQuery.validator.addMethod("checkNum", function(value, element) {
        var patrn =/(^[A-Za-z0-9]$)/;
        for (var i = 0; i < value.length; i++) {
            if (patrn.test(value[i]) == false) {
            	clearReflectNumber();
            	$("#numberMsg").hide();
                checkNum = false;
                return false;
            }
        }
        checkNum = true;
        return true;
    });
    jQuery.validator.addMethod("checkNumLen", function(value, element) {
            if (value.length>20) {
            	clearReflectNumber();
            	$("#numberMsg").hide();
                checkNumLen = false;
                return false;
            }
        checkNumLen = true;
        return true;
    });
    jQuery.validator.addMethod("checkCh", function(value, element) {
    	var patrn =/^[\u0391-\uFFE5A-Za-z]+$/;
        for (var i = 0; i < value.length; i++) {
            if (patrn.test(value[i]) == false) {
            	clearReflectOutlets();
            	$("#outletsMsg").hide();
            	clearReflectEditOutlets();
            	$("#edit_outletsMsg").hide();
                checkCh = false;
                return false;
            }
        }
        checkCh = true;
        return true;
    });
    jQuery.validator.addMethod("checkChLen", function(value, element) {
            if (value.length>20) {
            	clearReflectOutlets();
            	$("#outletsMsg").hide();
            	clearReflectEditOutlets();
            	$("#edit_outletsMsg").hide();
                checkChLen = false;
                return false;
            }
        checkChLen = true;
        return true;
    });
    //新增映射
    var newReflectVal = $("#newReflectForm").validate({
        rules : {
            city : {
                checkCh : true,
                checkChLen : true,
            },
            outlets : {
            	checkCh : true,
                checkChLen : true,
            },
            number : {
                checkNum  : true,
                checkNumLen : true,
            },
            startIP : {
                checkIP : true,
            },
            endIP : {
            	checkIP : true,
            },
            submask : {
            	checkSub : true,
            }
        },
        messages : {
            city  : {
            	required  : '请输入城市',
            	checkCh   : '请用中文或英文来表示城市',
                checkChLen : '最大输入长度不能超过20'
            },
            outlets : {
            	required  : '请输入网点名称',
            	checkCh   : '请用中文或英文来表示网点名称',
                checkChLen : '最大输入长度不能超过20'
            },
            number : {
            	required  : '请输入网点机构号',
            	checkNum  : '请用数字或字母来表示网点机构号',
                checkNumLen : '最大输入长度不能超过20'
            },
            startIP  : {
            	required  : '请输入起始IP',
                checkIP  : 'IP不合法，请重新输入',
            },
            endIP : {
            	required  : '请输入结束IP',
            	checkIP  : 'IP不合法，请重新输入',
            },
            submask : {
            	required  : '请输入子网掩码',
            	checkSub  : '子网掩码不合法，请重新输入',
            }    
        },
        submitHandler : function() {
        	if(uniqueOutlets==false||uniqueNumber==false||uniqueIP==false){
        		return false;
        	}
        	else{
        		window.parent.showLoading();
                reflectManage.reflectControl.saveReflect();	
        	}        
        }
    });   
    //编辑映射
    var updateReflectVal = $("#updateReflectForm").validate({
        rules : {
            edit_city : {
                checkCh : true,
                checkChLen : true,
            },
            edit_outlets : {
            	checkCh : true,
                checkChLen : true,
            },
//            edit_number : {
//                checkNum  : true,
//                checkNumLen : true,
//            },
            edit_startIP : {
                checkIP : true,
            },
            edit_endIP : {
            	checkIP : true,
            },
            edit_submask : {
            	checkSub : true,
            }
        },
        messages : {
        	edit_city  : {
            	required  : '请输入城市',
            	checkCh   : '请用中文或英文来表示城市',
                checkChLen : '最大输入长度不能超过20'
            },
            edit_outlets : {
            	required  : '请输入网点名称',
            	checkCh   : '请用中文或英文来表示网点名称',
                checkChLen : '最大输入长度不能超过20'
            },
//            edit_number : {
//            	required  : '请输入网点机构号',
//            	checkNum  : '请用数字或字母来表示网点机构号',
//                checkNumLen : '最大输入长度不能超过20'
//            },
            edit_startIP  : {
            	required  : '请输入起始IP',
                checkIP  : 'IP不合法，请重新输入',
            },
            edit_endIP : {
            	required  : '请输入结束IP',
            	checkIP  : 'IP不合法，请重新输入',
            },
            edit_submask : {
            	required  : '请输子网掩码',
            	checkSub  : '子网掩码不合法，请重新输入',
            }    
        },
        submitHandler : function() {
        	if(uniqueEditOutlets==false||uniqueEditIP==false){
        		return false;
        	}
        	else{
        		window.parent.showLoading();
                reflectManage.reflectControl.updateReflect();  
        	}                       
        }
    });
    
    $('#newReflectCancelBtn').on('click', function()
	{
        newReflectVal.resetForm();
        clearReflectOutlets();	
        clearReflectNumber();
        clearReflectIP();
        $("#outletsMsg").hide();
        $("#numberMsg").hide();
        $("#ipMsg").hide();
	});
	
	//监听newReflectModal的右上角关闭按钮的事件
	$('#newReflectModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
        newReflectVal.resetForm();
        clearReflectOutlets();	
        clearReflectNumber();
        clearReflectIP();
        $("#outletsMsg").hide();
        $("#numberMsg").hide();
        $("#ipMsg").hide();
	});	
	
	$('#newReflectModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
	});	
	
    $('#updateReflectCancelBtn').on('click', function() {
        updateReflectVal.resetForm();
        clearReflectEditOutlets();	
//        clearReflectEditNumber();
        clearReflectEditIP();
        $("#edit_outletsMsg").hide();
//        $("#edit_numberMsg").hide();
        $("#edit_ipMsg").hide();
	});
    
    //监听editReflectModal的右上角关闭按钮的事件
	$('#editReflectModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
        updateReflectVal.resetForm();
        clearReflectEditOutlets();	
//        clearReflectEditNumber();
        clearReflectEditIP();
        $("#edit_outletsMsg").hide();
//        $("#edit_numberMsg").hide();
        $("#edit_ipMsg").hide();
	});
	
	$('#delReflectModal').on('show.bs.modal', function (e) {
		window.parent.showcover();
	});	
	
	$('#delReflectModal').on('hide.bs.modal', function (e) {
		window.parent.hidecover();
	});	
	
	$('#updateReflectBtn').on('click', function()
	{
		
	});
	
	$('#delReflectBtn').on('click', function()
	{
        window.parent.showLoading();
		reflectManage.reflectControl.deleteReflect();
	});
	
});
