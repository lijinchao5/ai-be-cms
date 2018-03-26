
//查询下拉
function qx(Parentid,form,docmentid){
	var loadtip = $.layload();
	$.ajaxs({
		url:'area/findByParentid.do',
		param:{'parentid':Parentid},
		success : function(data) {
			$.loadSelect({
					dom : "#"+docmentid,
					data : data.result,
					form : form,
					text : "texts",
					value : "id",
					isClear:true
				});
			layer.close(loadtip);
		},
		error:function (){
			layer.close(loadtip);
		}
	});
}

//反显下拉
function fxqx(id,form,docmentid){
	var loadtip = $.layload();
	$.ajaxs({
		url:'area/findById.do',
		param:{'id':id},
		success : function(data) {
			$.loadSelect({
					dom : "#"+docmentid,
					data : data.result,
					form : form,
					text : "texts",
					value : "id",
					selval : id,
					isClear:true
				});
			layer.close(loadtip);
		},
		error:function (){
			layer.close(loadtip);
		}
	});
}

//初始化
function initajax(id,from){
	$.ajaxs({
		url:'school/findSchoolById.do',
		param:{'id':id},
		success : function(data) {
			$("#name").val(data.result.name);
			fxqx(data.result.addressArea,from,'qx');
			fxqx(data.result.addressCity,from,'sq');
			fxqx(data.result.addressProvince,from,'sf');
		}
	});
}


layui.use(['form'], function(){
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit
	  ,laydate = layui.laydate ,table = layui.table;
	
	//查询省份
	qx('0',form,'sf');
	initajax($.urlparam('id'),form)
	
	//监听省份，
	form.on('select(sf)', function(data){
		$.loadSelect({
			dom : "#sq",
			data : [],
			isClear:true,
			form : form
		});
		  qx(data.value,form,'sq');
		  $.loadSelect({
				dom : "#qx",
				data : [],
				isClear:true,
				form : form
			});
	});
	
	//监听市区
	form.on('select(sq)', function(data){
		qx(data.value,form,'qx');
	});
	
	//监听提交
	  layui.form.on('submit(updateschool)', function(data){
		  var id=$.urlparam('id');
		  var url="school/saveSchool.do";
		  if(id!=null){
			  url="school/updateSchool.do";
			  data.field.id=id;
		  }
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
