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
			url:'/link/findLinkById.do'
			,data : {id : _id}
			,success : function(_data){
				var _result = _data['result'];
				if(_result && _result['length'] && _result['length'] > 0){
					
					var obj = _result[0];
					$("input[name='id']").val(obj['id']);
					$("input[name='name']").val(obj['name']);
					$("input[name='orderby']").val(obj['orderby']);
					$("select[name='type']").val(obj['type']);
					$("textarea[name='desp1']").val(obj['desp1']);
					$("textarea[name='desp2']").val(obj['desp2']);
					$("textarea[name='desp3']").val(obj['desp3']);
					$("input[name='link1']").val(obj['link1']);
					$("input[name='link2']").val(obj['link2']);
					$("input[name='link2']").val(obj['link2']);
					form.render();
					layer.close(loadtip);
				}else{
					layer.close(loadtip);
					layer.alert("数据未找到");
				}
			}
			,error : function(_data){
				layer.close(loadtip);
				layer.alert(_data.message);
			}
		})
	}
	
	//选完文件后不自动上传
	upload.render({
		elem: '#pic1'
		,field: "pic1"
	    ,auto: false
    	,accept: 'img' //只允许上传图片
    	,choose: function(obj){
    		//预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
    		obj.preview(function(index, file, result){
    			$('#pic1').nextAll(".layui-word-aux").text(file.name);
		    });
    	}
	});
	//选完文件后不自动上传
	upload.render({
		elem: '#pic2'
		,field: "pic2"
	    ,auto: false
    	,accept: 'img' //只允许上传图片
    	,choose: function(obj){
    		//预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
    		obj.preview(function(index, file, result){
    			$('#pic2').nextAll(".layui-word-aux").text(file.name);
		    });
    	}
	});
	//选完文件后不自动上传
	upload.render({
		elem: '#pic3'
		,field: "pic3"
	    ,auto: false
    	,accept: 'img' //只允许上传图片
    	,choose: function(obj){
    		//预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
    		obj.preview(function(index, file, result){
    			$('#pic3').nextAll(".layui-word-aux").text(file.name);
		    });
    	}
	});
	
	//监听提交
	form.on('submit(submit)', function(data){
		
		console.log(data.field);
//		return false;
		
		var loadtip = $.layload();
		//ajax提交表单
		$.ajaxSubmits({
			form: data.form,
            type: 'post',
            url: 'link/editLink.do', 
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