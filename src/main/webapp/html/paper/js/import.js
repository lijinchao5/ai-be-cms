/**
 * 教材导入
 */

layui.use(['form', 'layedit', 'upload'], function(){
	//继承父窗layer，当前窗口layer变为layern
	if(window.parent && window.parent.layer){
		layern = layer;
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit ,upload = layui.upload;
	
	var areas = [];
	var loadtip = $.layload();
	//获取省份
	$.ajaxs({
		url:'area/findByParentid.do',
		param:{'parentid':0},
		success : function(data) {
			layer.close(loadtip);
			if(data['result']){
				areas = data['result'];
			}else return ;
			
			var _areastr = [];
			_areastr.push('<input type="checkbox" name="cheakall" lay-filter="cheakall" title="全选">');
			
			for (var i = 0; i < areas.length; i++) {
				_areastr.push('<input type="checkbox" class="area-cb" name="area'+areas[i].code+'" title="'+areas[i].texts+'">');
			}
			$('#checkdiv').html(_areastr.join(""));
			
			form.render();
			form.on('checkbox(cheakall)', function(data){
				console.log(data.elem.checked)
  				$('.area-cb').each(function () {
					this.checked = data.elem.checked;
					form.render();
				});
			}); 
		},
		error:function (){
			layer.close(loadtip);
		}
	});
	
	
	
	//选完文件后不自动上传
	upload.render({
		elem: '#wenjian'
		,field: "bookFile"
	    ,auto: false
	    ,accept: "file"
	});
	
	//监听提交
	form.on('submit(submit)', function(data){
		
		var _grades = [];
		$.each(data.field, function(key, val) {  
			if(key != 'area' && key.indexOf('area') != -1){
				_grades.push(key.replace('area',""));
			}
		});
		if(_grades.length > 0){
			$("input[name='paperProvince']").val(_grades.join(","));
		}else{
			layern.tips("请选择省份","#checkdiv");
			return false;
		}
		
		console.log( $("input[name='paperProvince']").val());
		
		var file = $("input[name='bookFile']").val();
		if(!file || file==null || file.trim()==""){
			layern.tips("请选择zip文件","#wenjiandiv");
			return false;
		}else if(file.toLowerCase().indexOf(".zip")== -1){
			layern.tips("文件格式错误","#wenjiandiv");
			return false;
		}
		
//		console.log( $("input[name='paperProvince']").val());
//		return false;
		
		var loadtip = $.layload();
		//ajax提交表单
		$.ajaxSubmits({
			form: data.form,
            type: 'post',
            url: 'paperManager/import.do', 
            success: function(data) { 
  				layer.close(loadtip);
                layer.alert('导入成功！');
                console.log(data);
                $(data.form).resetForm();
            },
            error: function(data){
  				layer.close(loadtip);
	  			layer.alert(data.message);
            }
        });

  		
		return false;
	});
	
	  
});