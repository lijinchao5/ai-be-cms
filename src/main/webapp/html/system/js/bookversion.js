/**
 * table使用示例
 */

layui.use(['table','form', 'layedit', 'laydate'], function(){
	//调用父窗layer
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit
	  ,laydate = layui.laydate ,table = layui.table;
	
	
	//监听提交
	form.on('submit(submit)', function(data){
		//
		table.reload('table', {
			where: data.field
		});
		return false;
	});
	
	var gradeDic = [];
	
	var loadtip = $.layload();
	$.ajaxs({
		url:'/dic/findDicByType.do'
		,data : {type : 'jcnj'}
		,success : function(data){
			
			if(data['result']){
				gradeDic = data['result'];
			}else return ;
			
			//表格初始化
			table.render({
				elem: '#table'
			    ,url: $.PROJECT_URL + "bookManager/findBookVersions.do"
			    ,cols: [[
			    	{field:'name', width: '40%', title: '教材版本'}
			    	,{field:'grade', width: '45%',  title: '适用年级', templet: function(d){
			    		if(d['grade']){
			    			var grades = d['grade'].split(',');
			    			var _gradestr = [];
			    			for (var i = 0; i < gradeDic.length; i++) {
			    				var grade = gradeDic[i];
			    				if($.inArray( grade.nameVal, grades ) != -1){
			    					_gradestr.push(grade.name);
			    				}
			    				
			    			}
			    			return _gradestr.join(",");
			    		}
			    		else return ''
			        }}
			    	,{title: '操作', fixed: 'right', width: '15%', align:'center', toolbar: '#barDemo'}
			    ]]
				,page: true
			});
			//表格按钮监听
			table.on('tool(table)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				var data = obj.data; //获得当前行数据
				var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
				var tr = obj.tr; //获得当前行 tr 的DOM对象
				 
				if(layEvent === 'edit'){ //编辑
				    layer.open({
						type: 2,
						title: '编辑',
						shadeClose: false,
						shade: 0.8,
						area: ['60%', '60%'],
						content: $.PROJECT_URL +'html/system/bookversionedit.html?grade='+data.grade+'&nameVal='+data.nameVal,
						end: function(){
							table.reload('table', {
								where: $.formData().field
							});
						}
					}); 
				}
			});
			
			layer.close(loadtip);
		}
		,error : function(data){
			layer.close(loadtip);
			layer.alert(data.message);
		}
	})
	  
});