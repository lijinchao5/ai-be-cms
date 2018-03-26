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
	
	//下拉框初始化
	$.loaddic("jcnj","select[name='gradeLevelId']",form);
	$.loaddic("kslx","select[name='questionType']",form);
	$.loaddic("sjxtx","select[name='paperType']",form);
	
	var provlist = [];
	var loadtip = $.layload();
	$.ajaxs({
		url:'area/findByParentid.do',
		param:{'parentid':0},
		success : function(data) {
			
			provlist = data.result
			//表格初始化
			table.render({
				elem: '#table'
			    ,url: $.PROJECT_URL + "paperManager/findPapers.do"
			    ,cols: [[
			    	{field:'name', title: '试卷名称', width: '25%'}
//			    	,{field:'paperProvince', title: '省份'}
			    	,{field:'paperProvince',  title: '省份', width: '25%', templet: function(d){
			    		var paperProvince = d['paperProvince'];
			    		if(paperProvince && paperProvince!=''){
			    			var arr = paperProvince.split(",");
			    			var str = [];
			    			for (var kk = 0; kk < arr.length; kk++) {
			    				var type = arr[kk];
				    			for (var i = 0; i < provlist.length; i++) {
				    				if(provlist[i] && provlist[i]['code']==type) {
				    					str.push (provlist[i]['texts']);
				    					break;
				    				}
				    			}
			    			}
			    			return str.join(",");
			    		}
			    		return '';
			        }}
			    	,{field:'questionTypeName', title: '试卷类型', width: '10%'}
			    	,{field:'paperTypeName', title: '试卷题型', width: '10%'}
			    	,{field:'gradeName', title: '年级', width: '10%'}
			    	,{field:'term', title: '学期', width: '10%', templet: function(d){
			    		if(d.term == '1') return '上学期';
			    		else if(d.term == '2') return '下学期';
			    		return'不限';
			        }}
			    	,{title: '操作', width: '10%', fixed: 'right', align:'center', toolbar: '#barDemo'}
			    ]]
				,page: true
			});
			$.loadSelect({
					dom : "#paperProvince",
					data : data.result,
					form : form,
					text : "texts",
					value : "code",
					isClear:true
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
						content: $.PROJECT_URL +'html/paper/edit.html?id='+data.id+"&paperProvince="+data.paperProvince,
						end: function(){
							table.reload('table', {
								where: $.formData().field
							});
						}
					}); 
				}
			});
			
			layer.close(loadtip);
		},
		error:function (){
			layer.close(loadtip);
		}
	});
	
	
	//监听提交
	form.on('submit(submit)', function(data){
		//
		table.reload('table', {
			where: data.field
		});
		return false;
	});
	//监听新增按钮
	$("#add").on('click', function(){
		layer.open({
			type: 2,
			title: '导入试卷',
			shadeClose: false,
			shade: 0.8,
			area: ['60%', '60%'],
			content: $.PROJECT_URL +'html/paper/import.html',
			end: function(){
				table.reload('table', {
					where: $.formData().field
				});
			}
		}); 
	});
	
	
	  
});