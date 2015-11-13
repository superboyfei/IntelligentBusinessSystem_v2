//获取网点总数
function getOutletCount()
{
    $.ajax({
        type  : 'post',
        async : false,
        url   : 'homepage/main/loadOutletsCount.action',
        dataType : "json",
        success : function (data) {
            if (data.result == true) {
                $("#outlet").html(data.count);
            } else {
            	$('#outlet').html(data.errorMsg);
            }
        },
        error : function(data) {
        	$('#outlet').html(data.errorMsg);
        }
    });
}
//获取设备总数
function getDeviceCount()
{
    $.ajax({
        type  : 'post',
        async : false,
        url   : 'homepage/main/loadDeviceCount.action',
        dataType : "json",
        success : function (data) {
            if (data.result == true) {
                $('#device').html(data.count);
            } else {
            	$('#device').html(data.errorMsg);
            }
        },
        error : function(data) {
        	$('#device').html(data.errorMsg);
        }
    }); 
}
//获取交易总数
function getBussCount()
{
    $.ajax({
        type  : 'post',
        async : false,
        url   : 'homepage/main/loadBusinessCount.action',
        dataType : "json",
        success : function (data) {
            if (data.result == true) {
                $('#buss').html(data.count);
            } else {
            	$('#buss').html(data.errorMsg);
            }
        },
        error : function(data) {
        	$('#buss').html(data.errorMsg);
        }
    }); 
}

//初始化业务统计图表
function getEchart()
{
    var myChart = echarts.init($('#start-business-chart')[0]);
    myChart.showLoading({
    	text : '正在努力加载...'
    });
    var options = 
    {
        title  : {
            text   : '业务量统计'
        },
        backgroundColor:"#fff",
        tooltip : {
            show   : true,
            trigger: 'axis'
        },
        xAxis : [
        {
            type : 'category',
            splitNumber:10, //重要
            boundaryGap:false
        }],
        yAxis : [
            {
                type : 'value',
                splitNumber:10
            }
        ],
        dataZoom  : {
            show  : true,
            start : 92,
            end   : 100
        }, 
        series : [
        {
            name      : '业务',
            type      : 'line',
            smooth    : true,
            itemStyle : {normal: {areaStyle: {type: 'default'}}},
            showAllSymbol : true					
        }]        
    };
    var now = new Date();
    var nowMonth = now.getMonth() + 1;
    var nowDay = now.getDate()-1;
    var past = "";
    if (parseInt(now.getFullYear()) < 2014) {
        past = new Date("2014", "1", "1");
    } else {
        past = new Date(now.getFullYear()-1, nowMonth, nowDay);
    }

    var param =
    {
        startDate : past.getFullYear()+"-"+past.getMonth()+"-"+past.getDate(),
        endDate : now.getFullYear()+"-"+nowMonth+"-"+nowDay
    };

    $.ajax({
        type  : "post",
        data  : param,
        async : false, //同步执行
        url   : "homepage/main/loadBusinessStatistics.action",
        dataType : "json",
        success  : function (data) 
        {
            if (data.result == true) {
                var count = [];
                var date = [];
                for (var i = 0; i < data.businessStatisticsVoList.length; i++)
                {
                    count.push(data.businessStatisticsVoList[i].count);
                    date.push(data.businessStatisticsVoList[i].date);
                }
                options.series[0].data = count;
                options.xAxis[0].data = date;
                myChart.hideLoading();
                myChart.setOption(options, true);
            } else {
                //XXX
            }
        },
        error : function() {
            //XXX
        }
    }); 
}

$(document).ready(function()
{
    getOutletCount();
    getDeviceCount();
    getBussCount();
    getEchart();
    $(window).resize(function(){
    	getEchart();
    });
});