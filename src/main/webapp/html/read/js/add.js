/**
 * 
 */

layui.use(['form', 'layedit', 'upload', 'table'], function(){
	//继承父窗layer，当前窗口layer变为layern
	if(window.parent && window.parent.layer){
		layern = layer;
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit ,upload = layui.upload, table = layui.table;
	
	
	$("input[type='file']").on("change",function(){
		$(".layui-upload-choose").text("");
	});
	
	//下拉框初始化
	$.loaddic("ydjb","select[name='level']",form);
	//下拉框初始化
	$.loaddic("ydfl","select[name='type']",form);
	
	//监听文本框
	$("#content").bind("input propertychange change", function(){
		var value = $("#content").val();
		$("input[name='wordNum']").val($.wordNum(value));
	});
	
	//监听分段按钮
	$("#split").on('click', split);
	
	var isSplit = false;
	//分段
	function split(){
		isSplit = true;
		var content = $("#content").val();
		var arr = content.split("||");
		var data = [];
		console.log(arr);
		
		var order = 1;
		for (var i = 0; i < arr.length; i++) {
			var sentenceCont = arr[i];
			if(!sentenceCont || sentenceCont=="" || sentenceCont.trim() == "") continue;
			data.push({
				id : order
				,orderNum : order
				,sentenceCont : sentenceCont.trim()
				,sentenceMean : ""
				,wordNum : $.wordNum(sentenceCont.trim())
			});
			order++;
		}
		
		table.render({
			elem: '#table'
		    ,data: data
		    ,cols: [[
		    	{field:'orderNum', title: '序号', width: '5%'}
		    	,{field:'sentenceCont', title: '段落内容', width: '50%'}
		    	,{field:'sentenceMean', title: '中文释义(选填)', width: '30%', edit: 'text'}
		    	,{field:'audioPath', title: '音频', width: '15%', templet: '#audiotmpl'}
		    	,{field:'id', title: '', style: 'display: none'}
		    	,{field:'wordNum', title: '', style: 'display: none'}
		    ]]
		    ,done: function(res, curr, count){
		    //如果是异步请求数据方式，res即为你接口返回的信息。
		    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		    	for (var i = 0; i < res.data.length; i++) {
		    		(function(_id){     
						upload.render({
							elem: '#audiopath'+_id
							,field: "audiopath"+_id
						    ,auto: false
					    	,exts: 'mp3' //只允许上传mp3
					    	,choose: function(obj){
					    		//预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
					    		obj.preview(function(index, file, result){
					    			$('#audiopath'+_id).text(file.name);		
							    });
					    	}
						});
					})(res.data[i].id)
				}
			}
		    ,limit : 10000
		});
	}
	
	
	//表格数据
	function tableData(elem){
		var table = $(elem).next('.layui-table-view').find('.layui-table-body table');
		var data = [];
		$(table).find('tr').each(function(i, domEle){
			data.push({
				id : $(domEle).find("td[data-field='id'] .layui-table-cell").text()
				,orderNum : $(domEle).find("td[data-field='orderNum'] .layui-table-cell").text()
				,wordNum : $(domEle).find("td[data-field='wordNum'] .layui-table-cell").text()
				,sentenceCont : $(domEle).find("td[data-field='sentenceCont'] .layui-table-cell").text()
				,sentenceMean : $(domEle).find("td[data-field='sentenceMean'] .layui-table-cell").text()
			});
 		});
		return data;
	}
	
	//监听提交
	form.on('submit(submit)', function(data){
		
		/*自动分段*/
		if(!isSplit) split();
		
		/*验证文件*/
		var flag = true;
		$(".layui-upload-file").each(function(i, domEle){
			var file = $(domEle).val();
			if(flag && (!file || file == null || file == "")){
				var td = $(domEle).parents("td");
				td[0].scrollIntoView(true);
				layern.tips("请选择文件", td);
				flag = false;
			}
 		});
		if(!flag) return false;
		
		/*获取分段数据*/
		var sentences = tableData('#table');
		if(sentences.length == 0){
			layer.alert("自动分段错误");
			return false;
		}
		$("input[name='itemjson']").val(JSON.stringify(sentences));
		
//		console.log(sentences);
//		return false;
		
		var loadtip = $.layload();
		//ajax提交表单
		$.ajaxSubmits({
			form: data.form,
            type: 'post',
            url: 'read/add.do', 
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