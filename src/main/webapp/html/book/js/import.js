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
	
	//下拉框初始化
//	$.loaddic("jcbb","select[name='bookVersion']",form);
	$.loaddic("jcnj","select[name='grade']",form);
	$.loaddic("cb","select[name='bookVolume']",form);
	
	form.on('select(grade)', function(data){
		
		if(!data.value || data.value==""){
			$.loadSelect({
	  			value : "nameVal",
				dom : "#bookVersion",
				data : [],
				isClear:true,
				form : form
			});
			return;
		}
		
		var loadtip = $.layload();
		$.ajaxs({
			url:'/bookManager/findBookVersionsByGrade.do'
			,data : {grade : data.value, }
			,success : function(_data){
				layer.close(loadtip);
				$.loadSelect({
	  				value : "nameVal",
					dom : "#bookVersion",
					data : _data.result,
					isClear : true,
					form : form
				});
			}
			,error : function(_data){
				layer.close(loadtip);
				layer.alert(_data.message);
			}
		})
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
		
		var bookVersion = $("select[name='bookVersion']").next(".layui-form-select").find(".layui-input").val();
		var grade = $("select[name='grade']").next(".layui-form-select").find(".layui-input").val();
		var bookVolume = $("select[name='bookVolume']").next(".layui-form-select").find(".layui-input").val();
		var _name = bookVersion + grade + bookVolume;
		console.log(_name);
		$("input[name='name']").val(_name);
		
		var file = $("input[name='bookFile']").val();
		if(!file || file==null || file.trim()==""){
			layern.tips("请选择zip文件","#wenjiandiv");
			return false;
		}else if(file.toLowerCase().indexOf(".zip")== -1){
			layern.tips("文件格式错误","#wenjiandiv");
			return false;
		}
		var loadtip = $.layload();
		$.ajaxs({
			url:'/bookManager/repeatValid.do'
			,data : data.field
			,success : function(_data){
				layer.close(loadtip);
				loadtip = $.layload();
				//ajax提交表单
				$.ajaxSubmits({
					form: data.form,
		            type: 'post',
		            url: 'bookManager/import.do', 
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
			}
			,error : function(_data){
				layer.close(loadtip);
				
				if(_data.code == $.CONSTANT.REPEAT_ERROR_CODE){
		  			
		  			layer.confirm('当前教材已存在，是否覆盖？', {
				  		btn: ['覆盖','取消'] //按钮
						}
		  				, function(){
		  				loadtip = $.layload();
						//ajax提交表单
						$.ajaxSubmits({
							form: data.form,
				            type: 'post',
				            url: 'bookManager/import.do', 
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
		  					
		  				});
  				}else{
  					layer.alert(_data.message);
  				}
			}
		})
  		
		return false;
	});
	
	  
});