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
	
	
	$("input[type='file']").on("change",function(){
		$(".layui-upload-choose").text("");
	});
	
	
	var _id = $.urlparam('id');
	if(_id && _id!=""){
		var loadtip = $.layload();
		$.ajaxs({
			url:'/words/findWordsById.do'
			,data : {id : _id}
			,success : function(_data){
				var _result = _data['result'];
				if(_result && _result['length'] && _result['length'] > 0){
					
					var word = _result[0];
					$("input[name='id']").val(word['id']);
					$("input[name='wordSpell']").val(word['wordSpell']);
					$("input[name='wordMean']").val(word['wordMean']);
					
					$("input[name='wordSpell']").attr("readonly","readonly");
					$("input[name='wordSpell']").addClass("readonly");
					
					layer.close(loadtip);
					$.loaddic("dck","select[name='type']",form,word['type'],true);
				}else{
					layer.close(loadtip);
					$.loaddic("dck","select[name='type']",form);
					layer.alert("单词未找到");
				}
			}
			,error : function(_data){
				layer.close(loadtip);
				//下拉框初始化
				$.loaddic("dck","select[name='type']",form);
				layer.alert(_data.message);
			}
		})
	}else{
		
		//下拉框初始化
		$.loaddic("dck","select[name='type']",form);
	}
	
	
	//选完文件后不自动上传
	upload.render({
		elem: '#audioPathM'
		,field: "audioPathMFile"
	    ,auto: false
    	,accept: 'file' //只允许上传mp3
	});
	upload.render({
		elem: '#audioPathW'
		,field: "audioPathWFile"
	    ,auto: false
    	,accept: 'file' //只允许上传mp3
	});
	
	//监听提交
	form.on('submit(submit)', function(data){
		
		var file = $("input[name='audioPathMFile']").val();
		if(file && file!=null && file.toLowerCase().indexOf(".mp3") == -1){
			layern.tips("文件格式错误","#audioPathM");
			return false;
		}
		file = $("input[name='audioPathWFile']").val();
		if(file && file!=null && file.toLowerCase().indexOf(".mp3") == -1){
			layern.tips("文件格式错误","#audioPathW");
			return false;
		}
		console.log(data.field);
//		return false;
		
		var loadtip = $.layload();
		//ajax提交表单
		$.ajaxSubmits({
			form: data.form,
            type: 'post',
            url: 'words/editWords.do', 
            success: function(data) { 
  				layer.close(loadtip);
                layer.alert('操作成功！');
//              console.log(data);
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