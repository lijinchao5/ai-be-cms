
var initenddate;
function dateFormats(fmt,date) {
	if(date==null){
		return "";
	}
    var o = {
        "M+": new Date(date).getMonth() + 1, //月份 
        "d+": new Date(date).getDate(), //日 
        "h+": new Date(date).getHours(), //小时 
        "m+": new Date(date).getMinutes(), //分 
        "s+": new Date(date).getSeconds(), //秒 
        "q+": Math.floor((new Date(date).getMonth() + 3) / 3), //季度 
        "S": new Date(date).getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (new Date(date).getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


//初始化
function initajax(){
	$.ajaxs({
		url:'school/findSchoolById.do',
		param:{'id':$.urlparam('id')},
		success : function(data) {
			$("#startdate").val(dateFormats('yyyy-MM-dd',data.result.startdate));
			$("#enddate").val(dateFormats('yyyy-MM-dd',data.result.enddate));
			initenddate=dateFormats('yyyy-MM-dd',data.result.enddate);
		}
	});
}

layui.use(['table','form', 'layedit', 'laydate'], function(){
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit
	  ,laydate = layui.laydate ,table = layui.table;
	
	
	//日期初始化
	laydate.render({
		elem: '#startdate'
	});
	laydate.render({
		elem: '#enddate'
	});
	initajax();
	//监听提交
	  layui.form.on('submit(updateschool)', function(data){
		  var id=$.urlparam('id');
		  var url="school/updateSchooldate.do";
		  data.field.id=id;
		  var startdate=data.field.startdate;
		  var enddate=data.field.enddate;
		  if(startdate>enddate){
			  layer.alert("结束时间不能小于开始时间")
			  return false;
		  }
		  /*if(initenddate>enddate){
			  layer.alert("结束时间不能小于上次结束时间");
			  return false;
		  }*/
		  
		  $.ajaxs({
			  url:url,
			  param:data.field,
			  success:function(data){
				  layer.alert("保存成功",function(){
					  layer.closeAll() ;
				  });
			  },
			  error:function(data){
				  layer.alert(data.message);
			  }
		  });
	    return false;
	  });
});
