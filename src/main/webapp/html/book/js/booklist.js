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
					isClear:true,
					form : form
				});
			}
			,error : function(_data){
				layer.close(loadtip);
				layer.alert(_data.message);
			}
		})
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
			title: '导入教材',
			shadeClose: false,
			shade: 0.8,
			area: ['60%', '60%'],
			content: $.PROJECT_URL +'html/book/import.html',
			end: function(){
				table.reload('table', {
					where: $.formData().field
				});
			}
		}); 
	});
	
	//表格初始化
	table.render({
		elem: '#table'
	    ,url: $.PROJECT_URL + "bookManager/findBooks.do"
	    ,cols: [[
//	    	{field:'name', title: '教材名称'},
	    	{field:'bookVersionName', title: '教材版本'}
	    	,{field:'gradeName', title: '年级'}
	    	,{field:'bookVolumeName', title: '教材册别'}
	    	,{title: '操作', fixed: 'right', align:'center', toolbar: '#barDemo'}
	    ]]
		,page: true
	});
	
	
	//表格按钮监听
	table.on('tool(table)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象
		 
		if(layEvent === 'detail'){ //查看
		    //do somehing
			layer.open({
			type: 2,
			title: obj.data['bookVersionName']+obj.data['gradeName']+obj.data['bookVolumeName'],
			shadeClose: false,
			shade: 0.8,
			area: ['80%', '80%'],
			content: $.PROJECT_URL +'html/book/bookinfo.html?bookId='+obj.data['id'],
			end: function(){
				table.reload('table', {
					where: $.formData().field
				});
			}
		}); 
		} 
	});
	  
});