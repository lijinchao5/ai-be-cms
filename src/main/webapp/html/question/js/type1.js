/**
 * 听后回答
 */

layui.use(['table', 'form', 'layedit', 'upload'], function(){
	//调用父窗layer
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit ,table = layui.table,upload = layui.upload;
	
	
	
	/*题组主键生成*/
	var _idxosdtye = 0;
	function id_generate(){
		return _idxosdtye++;
	}
	
	//文件选择初始化
	function filerender(fileDom){
		
		$(fileDom).each(function(i,dom){
			upload.render({
				elem: '#'+$(dom).attr("id")
				,field: $(dom).attr("name")
			    ,auto: false
		    	,exts: 'mp3' //只允许上传mp3
		    	,choose: function(obj){
		    		//预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
		    		obj.preview(function(index, file, result){
		    			$(dom).nextAll(".layui-word-aux").text(file.name);
				    });
		    	}
			});
		})
	}
	/*题组模板新增*/
	function grouptmpl(flag){
		var _obj = {
			id : id_generate()
		}
		,_blank_obj = {
	    	question : ""
	    	,correctResult : ""
	    	,pointResult : ""
	    }
		;
		
		_obj.groupid = "group"+_obj.id;
		_obj.tableid = "table"+_obj.id;
		
		$(".group-list").append($.formatTemplate(_obj, $("#grouptmpl").html()));
		//表格初始化
		table.render({
			elem: '#'+_obj.tableid
			,id: _obj.tableid
		    ,data: [ {
		    	question : ""
		    	,correctResult : ""
		    	,pointResult : ""
		    }]
		    ,cols: [[
		    	{field:'question', title: '题目（不需要填写编号）', width: '35%', edit: 'text'}
		    	,{field:'correctResult', title: '正确答案（多个答案之间用&nbsp;||&nbsp;隔开）', width: '35%', edit: 'text'}
		    	,{field:'pointResult', title: '关键词（多个关键词用&nbsp;||&nbsp;隔开）', width: '21%', edit: 'text'}
		    	,{field:'oper', title: '操作', width: '9%', fixed: 'right', align:'center', toolbar: '#barDemo'}
		    ]]
		    ,limit : 10000
		});
		
		//表格按钮监听
		table.on('tool('+_obj.tableid+')', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			var data = obj.data; //获得当前行数据
			var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
			var _new_data = [];//存放新数据
			
			if(layEvent === 'add'){ //添加
				var _table_data = table.getAllData(_obj.tableid);//获取表格数据
				//添加空行
				_table_data.push( {
			    	question : ""
			    	,correctResult : ""
			    	,pointResult : ""
			    });
				_new_data = _table_data;
				//表格重载
				table.reload(_obj.tableid, {
					data: _new_data
				});
				console.log(_new_data);
				//定位表格位置
				$("#"+_obj.tableid)[0].scrollIntoView(true);
				
			}else if(layEvent === 'del'){ //删除
				
				//删除询问框
				layer.confirm('删除后无法恢复', {
					btn: ['继续删除', '取消'] //按钮
					,title: '删除'
				}, function() {
					
				    obj.del();//删除
					var _table_data = table.getAllData(_obj.tableid);//获取表格数据
					//筛选表格数据
					for (var i = 0; i < _table_data.length; i++) {
						if(_table_data[i].hasOwnProperty('LAY_TABLE_INDEX')) {
							delete _table_data[i].LAY_TABLE_INDEX;
							_new_data.push(_table_data[i]);
						}
					}
					//行数为0则添加默认一行
					if(_new_data.length == 0) _new_data.push( {
				    	question : ""
				    	,correctResult : ""
				    	,pointResult : ""
				    });
					//表格重载
					table.reload(_obj.tableid, {
						data: _new_data
					});
					console.log(_new_data);
					//定位表格位置
					$("#"+_obj.tableid)[0].scrollIntoView(true);
				layer.closeAll();
				});
			}
		});		
		
		//定位到新增的题组并重新编号
		if(flag)$(".question-group[data-id='"+_obj.groupid+"']")[0].scrollIntoView(true);
		$(".groupno").each(function(i,n){
			$(this).text(i+1);
		});
		//初始化文件框
		filerender($(".question-group[data-id='"+_obj.groupid+"']").find(".file-audio"));
		
		form.render();
	}
	
	
	//添加题组
	$("#addgroup").click(function(){
		grouptmpl(true);
	});
	//删除题组
	$("#delgroup").click(function(){
		var num = $(".group-check:checked").length;
		if(num<1) {
			layer.alert("未选中任何题组"); return;
		}
		
		//拼接选中的组名
		var items = []
		$(".group-check:checked").each(function(){
			var text = $(this).nextAll("label").text();
			items.push(text);
		});
		
		//删除询问框
		layer.confirm(
			'删除后无法恢复&emsp;[&nbsp;<span style="color: #FF5722;font-weight: bold;">'
			+items.join('</span>&nbsp;,&nbsp;<span style="color: #FF5722;font-weight: bold;">')+'</span>&nbsp;]'
			, {
			btn: ['继续删除', '取消'] //按钮
			,title: '删除'
		}, function() {
			$(".group-check:checked").each(function(){
				$(this).parents(".question-group").remove();
			});
			if($(".question-group").length == 0) grouptmpl();
			$(".groupno").each(function(i,n){
				$(this).text(i+1);
			});
			layer.closeAll();
		});
	});
	
	
	//下拉框初始化
	$.loaddic("jcnj","select[name='gradeLevelId']",form);
	$.loaddic("xq","select[name='term']",form);
	//初始化一个题组
	grouptmpl();
	//初始化题干音频选择框
	filerender("#audio")
	
	//监听提交
	form.on('submit(submit)', function(data){
		
		
		
		
		return false;
	});
	
	$.keeplive();
});
