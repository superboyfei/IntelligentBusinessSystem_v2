function IdtoString(checkedNodes)
{
    var ids = "";
    
    for (var i = 0; i < checkedNodes.length; i++)
    {
        if (checkedNodes[i].id == '0') {
            continue;
        }
        if (i < checkedNodes.length - 1)
        {
            ids = ids + checkedNodes[i].id + ",";
        }
        else
        {
            ids = ids + checkedNodes[i].id;
        }
    } 
    return ids;
}

function formatDate(year, month, day)
{
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    return year + "-" + month + "-" + day;
}

function getStartDate()
{
	var startDate = $('#start-date .form-control').val();
    
    if (startDate == "") {
        var now = new Date();
        startDate = getFormatDate();
    }
    return startDate;
}

function getEndDate()
{
	var endDate = $('#end-date .form-control').val();
    if (endDate == "") {
        var now = new Date();
        endDate = getFormatDate();
    }
    return endDate;
}

function getFormatDate()
{
    var now = new Date();
    if (now.getDate() == 1) {
        if (now.getMonth() == 0) {
            return formatDate(now.getFullYear()-1, "12", "31");
        } else {
            var day = new Date(now.getTime() - 24*60*60*1000).getDate(); 
            return formatDate(now.getFullYear(), now.getMonth(), day);
        }
    } else {
        return formatDate(now.getFullYear(), now.getMonth() + 1, now.getDate() );
    }    
}

function formatQuarter(){
	var now = new Date(); //当前日期
	var nowMonth = now.getMonth(); //当前月 
	var nowYear = now.getYear(); //当前年 
	nowYear += (nowYear < 2000) ? 1900 : 0;
	var quarterNum = parseInt(nowMonth / 3) + 1;
	var nameOfQuarter = ["","一","二","三","四"];
	
	for(var i=1; i<=quarterNum; i++) {
		$("#startQuarter").append("<option value='" + i + "'>第" + nameOfQuarter[i] + "季度</option>");
	}
	$("#endQuarter").append("<option value='" + quarterNum + "'>第" + nameOfQuarter[quarterNum] + "季度</option>");
	
	$("#startQuarter").val(quarterNum);
	$("#endQuarter").val(quarterNum);
	
	$("#startQuarterYear").val(nowYear);
	$("#endQuarterYear").val(nowYear);
}

function formatQuarterByYear(startYear,endYear){
	$("#startQuarter").empty();
	$("#endQuarter").empty();
	var now = new Date(); //当前日期
	var nowMonth = now.getMonth(); //当前月 
	var nowYear = now.getYear(); //当前年 
	nowYear += (nowYear < 2000) ? 1900 : 0;
	
	var nameOfQuarter = ["","一","二","三","四"];
	var quarterNum = parseInt(nowMonth / 3) + 1;
	
	if(nowYear > endYear){
		// 结束年份小于今年
		for(var i=1; i<=4; i++) {
			$("#startQuarter").append("<option value='" + i + "'>第" + nameOfQuarter[i] + "季度</option>");
			$("#endQuarter").append("<option value='" + i + "'>第" + nameOfQuarter[i] + "季度</option>");
		}
		$("#startQuarter").val(1);
		$("#endQuarter").val(4);
	} 
	
	if(nowYear == endYear){
		if(nowYear == startYear){
			for(var i=1; i<=quarterNum; i++) {
				$("#startQuarter").append("<option value='" + i + "'>第" + nameOfQuarter[i] + "季度</option>");
			}
			$("#endQuarter").append("<option value='" + quarterNum + "'>第" + nameOfQuarter[quarterNum] + "季度</option>");
			$("#startQuarter").val(quarterNum);
			$("#endQuarter").val(quarterNum);
		} else {
			// 开始年份小于今年
			for(var i=1; i<=4; i++) {
				$("#startQuarter").append("<option value='" + i + "'>第" + nameOfQuarter[i] + "季度</option>");
			}
			for(var i=1; i<=quarterNum; i++) {
				$("#endQuarter").append("<option value='" + i + "'>第" + nameOfQuarter[i] + "季度</option>");
			}
			$("#startQuarter").val(1);
			$("#endQuarter").val(quarterNum);
		}
	}
}

function formatMonth(){
	var now = new Date(); //当前日期 
	var nowYear = now.getYear(); //当前年 
	nowYear += (nowYear < 2000) ? 1900 : 0;
	var nowMonth = now.getMonth(); //当前月 
	if(nowMonth=="0"){
		$("#dateStart").val(nowYear+"-01");
		$("#dateEnd").val(nowYear+"-01");		
	}
	if(nowMonth=="1"){
		$("#dateStart").val(nowYear+"-02");
		$("#dateEnd").val(nowYear+"-02");		
	}
	if(nowMonth=="2"){
		$("#dateStart").val(nowYear+"-03");
		$("#dateEnd").val(nowYear+"-03");		
	}
	if(nowMonth=="3"){
		$("#dateStart").val(nowYear+"-04");
		$("#dateEnd").val(nowYear+"-04");		
	}
	if(nowMonth=="4"){
		$("#dateStart").val(nowYear+"-05");
		$("#dateEnd").val(nowYear+"-05");		
	}
	if(nowMonth=="5"){
		$("#dateStart").val(nowYear+"-06");
		$("#dateEnd").val(nowYear+"-06");		
	}
	if(nowMonth=="6"){
		$("#dateStart").val(nowYear+"-07");
		$("#dateEnd").val(nowYear+"-07");		
	}
	if(nowMonth=="7"){
		$("#dateStart").val(nowYear+"-08");
		$("#dateEnd").val(nowYear+"-08");		
	}
	if(nowMonth=="8"){
		$("#dateStart").val(nowYear+"-09");
		$("#dateEnd").val(nowYear+"-09");		
	}
	if(nowMonth=="9"){
		$("#dateStart").val(nowYear+"-10");
		$("#dateEnd").val(nowYear+"-10");		
	}
	if(nowMonth=="10"){
		$("#dateStart").val(nowYear+"-11");
		$("#dateEnd").val(nowYear+"-11");		
	}
	if(nowMonth=="11"){
		$("#dateStart").val(nowYear+"-12");
		$("#dateEnd").val(nowYear+"-12");		
	}
}

function formatYear(){
	var now = new Date(); //当前日期 
	var nowYear = now.getYear(); //当前年 
	nowYear += (nowYear < 2000) ? 1900 : 0;
	$("#dateStart").val(nowYear);
	$("#dateEnd").val(nowYear);
}
