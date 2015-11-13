function showLoading()
{
    $('#loadingSpin').show();
    $('#loadingSpin').spin({color: '#fff'});
    $('.spinner').append("<div style='position:absolute; left:30px;top:-5px; color:#fff;width:120px;'>数据努力处理中...</div>"); 
}

function hideLoading()
{
    $("#loadingSpin").hide().spin(false);
}