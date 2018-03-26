/**
 * 教材导入
 */

layui.use(['form', 'layedit'], function(){
	//继承父窗layer，当前窗口layer变为layern
	if(window.parent && window.parent.layer){
		layern = layer;
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit ;
	var gradeDic = [];
	
	var loadtip = $.layload();
	$.ajaxs({
		url:'/dic/findDicByType.do'
		,data : {type : 'jcnj'}
		,success : function(data){
			
			layer.close(loadtip);
			if(data['result']){
				gradeDic = data['result'];
			}else return ;
			var grades = [];
			if($.urlparam('grade')) grades = $.urlparam('grade').split(',');
			var _gradestr = [];
			for (var i = 0; i < gradeDic.length; i++) {
				var grade = gradeDic[i];
				if($.inArray( grade.nameVal, grades ) != -1){
					_gradestr.push('<input type="checkbox" name="grade'+grade.nameVal+'" title="'+grade.name+'" checked="">');
				}else{
					_gradestr.push('<input type="checkbox" name="grade'+grade.nameVal+'" title="'+grade.name+'">');
				}
			}
			$('#gradecheck').html(_gradestr.join(""));
			form.render();
		}
		,error : function(data){
			layer.close(loadtip);
			layer.alert(data.message);
		}
	})
	
	//监听提交
	form.on('submit(submit)', function(data){
		
		var _grades = [];
		$.each(data.field, function(key, val) {  
			if(key != 'grade' && key.indexOf('grade') != -1){
				_grades.push(key.replace('grade',""));
			}
		});  
		
		var loadtip = $.layload();
		$.ajaxs({
			url:'/bookManager/updateBookVersions.do'
			,data : {grade : _grades.join(","),version_val : $.urlparam('nameVal')}
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