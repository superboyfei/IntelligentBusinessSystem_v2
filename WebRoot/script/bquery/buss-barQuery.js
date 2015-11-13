$("#barBtnByTime").click(function(){	
	if($(".ui-layout-toggler").attr("title")=="Open"){
		$(".center-tool-bar").css("width","1145px");
		$("#table-contains").css("width",1130);	
		$("#table-contains2").css("width",1130);
	}
	else{
		$(".center-tool-bar").css("width","915px");	
		$("#table-contains").css("width",900);	
		$("#table-contains2").css("width",900);
	}
	barTime();
});
$("#barBtnByDeal").click(function(){
	if($(".ui-layout-toggler").attr("title")=="Open"){
		$(".center-tool-bar").css("width","1145px");
		$("#table-contains").css("width",1130);	
		$("#table-contains2").css("width",1130);	
	}
	else{
		$(".center-tool-bar").css("width","915px");	
		$("#table-contains").css("width",900);	
		$("#table-contains2").css("width",900);	
	}
	barDeal();
});
$("#barBtnByOutlets").click(function(){
	if($(".ui-layout-toggler").attr("title")=="Open"){
		$(".center-tool-bar").css("width","1145px");
		$("#table-contains").css("width",1130);	
		$("#table-contains2").css("width",1130);
	}
	else{
		$(".center-tool-bar").css("width","915px");	
		$("#table-contains").css("width",900);	
		$("#table-contains2").css("width",900);
	}
	barOutlets();
});
function barTime(){
	$(".ui-layout-center").css("overflow","auto");
	$("#table-contain").css("display","none");
	$("#table-contains2").css("display","none");
	$("#table-contains").css("display","none");
	$("#table-contains").css("height",530);	
	var myDate=[];
	var myCount=[];
	var length;
	if($("#selectTime").val() == "0"){
		length=DateDiff(timeQuery.data.startDate,timeQuery.data.endDate)+1;	
	}
	if($("#selectTime").val() == "1"){
		length=MouthDiff(timeQuery.data.startDate,timeQuery.data.endDate)+1;	
	}
	if($("#selectTime").val() == "2"){
		length=YearDiff(timeQuery.data.startDate,timeQuery.data.endDate)+1;	
	}
	if($("#selectTime").val() == "3"){
		length=QuarterDiff()+1;	
	}
	if(length<=120){
		if(length>20){
			var i=length-20;
			$("#table-contains").css("height",(530+20*i));
		}
	var param={
		queryType : timeQuery.data.queryType,
        bussType  : timeQuery.data.bussType,
        outlet    : timeQuery.data.outlet,
        startDate : timeQuery.data.startDate,
        endDate   : timeQuery.data.endDate		
	};
	$.ajax({
		url     :"business/statistics/querybytime/main/loadAllBusinessStatistics.action",
		type    :"POST",
		dataType: "json",
        data    : param,
        async   :false,
        beforeSend: function(){
        	window.parent.showLoading();
        	setTimeout(function(){
        		window.parent.hideLoading();
        	},8000);
        },
        success :function(data){
        	if(data.result==true){
        		for(var i=0;i<data.queryByTimePoList.length;i++){
        			myDate.push([data.queryByTimePoList[i].date]);
        			myCount.push([data.queryByTimePoList[i].count]);
        		}
        	}
        	else{
        	}
        },
        error   :function(data){
        	toastr.error(data.errorMsg);
        }
	}); 
	var myChart = echarts.init(document.getElementById("table-contains")); 
	var option = {
		    title : {
		    	text: '时间和交易数量',
			    x:'center'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    grid : {
		    	
		    },
		    legend: {
		    	orient : 'vertical',
			    x : 'left',
			    data:['交易数量']
		    },
		    toolbox: {
		        show : false,
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'value',
		            boundaryGap : [0, 0.01]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            data : myDate
		        }
		    ],
		    series : [
		        {
		            name:'交易数量',
		            type:'bar',
		            data:myCount
		        }
		    ]
		};
	if($("#selectTime").val() == "3"){
    	option.grid.x=90
    }
	window.parent.hideLoading(); 
	$("#table-contains").css("display","block");
	myChart.setOption(option);
	}
	else{
		window.parent.hideLoading();
		$("#table-contains2").css("display","block");
	    $("#largeBarData").css("display","block"); 
	    $("#largeBarData").html("数据量大于120条，无法正常呈现条形图效果"); 
	}
}
function barDeal(){
	$(".ui-layout-center").css("overflow","auto");
	$("#table-contain").css("display","none"); 
	$("#table-contains2").css("display","none");
	$("#table-contains").css("display","none");
	$("#table-contains").css("height",530);
	var myDate=[];
	var myCount=[];
	var bussData=dealQuery.data.bussType.split(",");
	var length=bussData.length;
	if(length<=120){
		if(length>20){
			var i=length-20;
			$("#table-contains").css("height",(530+20*i));
		}
	var param={
        bussType  : dealQuery.data.bussType,
        outlet    : dealQuery.data.outlet,
        startDate : dealQuery.data.startDate,
        endDate   : dealQuery.data.endDate		
	};
	$.ajax({
		url     :"business/statistics/querybydeal/main/loadAllBusinessStatistics.action",
		type    :"POST",
		dataType: "json",
        data    : param,
        beforeSend: function(){
        	window.parent.showLoading();
        	setTimeout(function(){
        		window.parent.hideLoading();
        	},8000);
        },
        async   :false,
        success :function(data){
        	if(data.result==true){
        		for(var i=0;i<data.queryByDealPoList.length;i++){
        			myDate.push([data.queryByDealPoList[i].businessName]);
        			myCount.push([data.queryByDealPoList[i].count]);
        		}        		
        	}
        	else{
        	}
        },
        error   :function(data){
        	toastr.error(data.errorMsg);
        }
	});
	var myChart = echarts.init(document.getElementById("table-contains")); 
	var option = {
		    title : {
		    	text: '业务名称和交易数量',
			    x:'center'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    grid : {
		    	x : 80
		    },
		    legend: {
		    	orient : 'vertical',
			    x : 'left',
			    data:['交易数量']
		    },
		    toolbox: {
		        show : false,
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'value',
		            boundaryGap : [0, 0.01]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            data : myDate
		        }
		    ],
		    series : [
		        {
		            name:'交易数量',
		            type:'bar',
		            data:myCount
		        }
		    ]
		};
	for(var i=0;i<myDate.length;i++){
		$("#textarea").html(myDate[i]);
		if(document.getElementById("textarea").offsetWidth+10>option.grid.x){
			option.grid.x=document.getElementById("textarea").offsetWidth+10;
		}
	}
	window.parent.hideLoading();
	$("#textarea").html("");
	$("#table-contains").css("display","block");
	myChart.setOption(option);
	}
	else{
		window.parent.hideLoading();
		$("#table-contains2").css("display","block");
	    $("#largeBarData").css("display","block"); 	
	    $("#largeBarData").html("数据量大于120条，无法正常呈现条形图效果"); 
	}
}
function barOutlets(){
	$(".ui-layout-center").css("overflow","auto");
	$("#table-contain").css("display","none");
	$("#table-contains2").css("display","none");
	$("#table-contains").css("display","none");
	$("#table-contains").css("height",530);
	var myDate=[];
	var myCount=[];
	var outletsData=outletsQuery.data.outlet.split(",");
	var length=outletsData.length;
	if(length<=120){
		if(length>20){
			var i=length-20;
			$("#table-contains").css("height",(530+20*i));
		}
	var param={
        bussType  : outletsQuery.data.bussType,
        outlet    : outletsQuery.data.outlet,
        startDate : outletsQuery.data.startDate,
        endDate   : outletsQuery.data.endDate		
	};
	$.ajax({
		url     :"business/statistics/querybyoutlets/main/loadAllBusinessStatistics.action",
		type    :"POST",
		dataType: "json",
        data    : param,
        async   :false,
        beforeSend: function(){
        	window.parent.showLoading();
        	setTimeout(function(){
        		window.parent.hideLoading();
        	},8000);
        },
        success :function(data){
        	if(data.result==true){
        		for(var i=0;i<data.queryByOutletsPoList.length;i++){
        			myDate.push([data.queryByOutletsPoList[i].outletsName]);
        			myCount.push([data.queryByOutletsPoList[i].count]);
        		}        		
        	}
        	else{
        	}
        },
        error   :function(data){
        	toastr.error(data.errorMsg);
        }
	});
	var myChart = echarts.init(document.getElementById("table-contains")); 
	var option = {
		    title : {
		    	text: '网点名称和交易数量',
			    x:'center'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    grid : {
		    	x : 80
		    },
		    legend: {
		    	orient : 'vertical',
			    x : 'left',
			    data:['交易数量']
		    },
		    toolbox: {
		        show : false,
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'value',
		            boundaryGap : [0, 0.01]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            data : myDate,
		            
		        }
		    ],
		    series : [
		        {
		            name:'交易数量',
		            type:'bar',
		            data:myCount
		        }
		    ]
		};
	for(var i=0;i<myDate.length;i++){
		$("#textarea").html(myDate[i]);
		if(document.getElementById("textarea").offsetWidth+10>option.grid.x){
			option.grid.x=document.getElementById("textarea").offsetWidth+10;
		}
	}
	window.parent.hideLoading();
	$("#textarea").html("");
	$("#table-contains").css("display","block");
	myChart.setOption(option);	
	}
	else{
		window.parent.hideLoading();
		$("#table-contains2").css("display","block");
	    $("#largeBarData").css("display","block"); 	
	    $("#largeBarData").html("数据量大于120条，无法正常呈现条形图效果");
	}
}
//计算两个日期相差多少天
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays  
    aDate  =  sDate1.split("-")  
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
    aDate  =  sDate2.split("-")  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
    return  iDays  
}    
function MouthDiff(sDate1,  sDate2){
	var year1,year2,mouth1,mouth2,aDate;
	aDate=sDate1.split("-");
	year1=aDate[0];
	mouth1=aDate[1];
	aDate=sDate2.split("-");
	year2=aDate[0];
	mouth2=aDate[1];
	return (year2-year1)*12+(mouth2-mouth1);
}
function QuarterDiff(){
	var sDate1,sDate2,sQuarter1,sQuarter2;
	sDate1=$("#startQuarterYear").val();
	sDate2=$("#endQuarterYear").val();
	if($("#startQuarter").val()=="1"){
		sQuarter1=1;
	}
	if($("#startQuarter").val()=="2"){
		sQuarter1=2;
	}
	if($("#startQuarter").val()=="3"){
		sQuarter1=3;
	}
	if($("#startQuarter").val()=="4"){
		sQuarter1=4;
	}
	if($("#endQuarter").val()=="1"){
		sQuarter2=1;
	}
	if($("#endQuarter").val()=="2"){
		sQuarter2=2;
	}
	if($("#endQuarter").val()=="3"){
		sQuarter2=3;
	}
	if($("#endQuarter").val()=="4"){
		sQuarter2=4;
	}
	return (sDate2-sDate1)*4+(sQuarter2-sQuarter1);
}
function YearDiff(sDate1,  sDate2){
	return parseInt(sDate2-sDate1);
}