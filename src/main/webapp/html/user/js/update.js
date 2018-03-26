
var type;
var relationid;
//初始化
function initajax(id){
	$.ajaxs({
		url:'user/findByID.do',
		param:{'id':id},
		success : function(data) {
			$("#name").val(data.result.name);
		}
	});
}


layui.use(['form'], function(){
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	var form = layui.form ,layedit = layui.layedit
	  ,laydate = layui.laydate ,table = layui.table;
	
	type=$.urlparam('type');
	relationid=$.urlparam('relationid');
	
	//initajax($.urlparam('id'))
	
	//监听提交
	  layui.form.on('submit(updateschool)', function(data){
		  data.field.type=type;
		  data.field.relationid=relationid;
		  //console.log(data.field)
		  var id=$.urlparam('id');
		  var url="user/saveUser.do";
		  if(id!=null){
			  url="user/updateUser.do";
			  data.field.id=id;
		  }
		  
		  $.ajaxs({
			  url:"user/findByName.do",
			  param:{type:type,name:data.field.name},
			  success:function(datas){
				  if(datas.result==true){
					  $.ajaxs({
						  url:url,
						  param:data.field,
						  success:function(data){
							  layer.alert("保存成功");
							  var index = parent.layer.getFrameIndex(window.name);  
							  window.parent.layer.close(index); 
						  },
						  error:function(data){
							  layer.alert(data.message);
						  }
					  });
				  }else{
					  layer.alert("用户名重复");
				  }
			  },
			  error:function(data){
				  layer.alert(data.message);
			  }
		  });
		  //alert("ok")
		  //return false;
		  
		  
	    return false;
	  });
});

