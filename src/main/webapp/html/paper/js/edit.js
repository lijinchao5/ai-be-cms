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
			
			var provs = [];
			if($.urlparam('paperProvince')) provs = $.urlparam('paperProvince').split(',');
//			console.log(provs);
			var _areastr = [];
			_areastr.push('<input type="checkbox" name="cheakall" lay-filter="cheakall" title="全选">');
			for (var i = 0; i < areas.length; i++) {
				
				var value = areas[i];
				if($.inArray( value.code, provs ) != -1){
					_areastr.push('<input type="checkbox" class="area-cb" name="area'+value.code+'" title="'+value.texts+'" checked="">');
				}else{
					_areastr.push('<input type="checkbox" class="area-cb" name="area'+value.code+'" title="'+value.texts+'">');
				}
//				_areastr.push('<input type="checkbox" name="area'+areas[i].code+'" title="'+areas[i].texts+'">');
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
	
	
	
	var loadtip2 = $.layload();
	//获取省份
	$.ajaxs({
		url:'paperManager/findPaper.do',
		param:{'id':$.urlparam('id')},
		success : function(data) {
			var obj = {}
			if(data['result']){
				obj = data['result'];
			}else {
				layer.alert("信息查询错误！");
				return ;
			}
			$("input[name='name']").val(obj['name']);
			//下拉框初始化
			$.loaddic("jcnj","select[name='gradeLevelId']",form, obj['gradeLevelId']);
			$.loaddic("kslx","select[name='questionType']",form, obj['questionType']);
			$.loaddic("sjxtx","select[name='paperType']",form, obj['paperType']);
			
			layer.close(loadtip2);
		},
		error:function (){
			layer.close(loadtip2);
		}
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
			data.field['paperProvince'] = _grades.join(",");
		}else{
			layern.tips("请选择省份","#checkdiv");
			return false;
		}
		data.field['id'] = $.urlparam('id');
		
		var loadtip = $.layload();
		$.ajaxs({
			url:'/paperManager/updatePaper.do'
			,data : data.field
			,success : function(data){
				layer.alert('编辑成功！');
				layer.close(loadtip);
				layer.closeAll();
			}
			,error : function(data){
				layer.close(loadtip);
				layer.alert(data.message);
			}
		})

  		
		return false;
	});
	
	  
});