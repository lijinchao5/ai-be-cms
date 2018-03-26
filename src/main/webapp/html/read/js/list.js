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
//		loadtip = $.layload();
		table.reload('table', {
			where: data.field
		});
		return false;
	});
	
	
	//监听新增按钮
	$("#add").on('click', function(){
		layer.open({
			type: 2,
			title: '新增',
			shadeClose: false,
			shade: 0.8,
			area: ['65%', '60%'],
			content: $.PROJECT_URL +'html/read/add.html',
			end: function(){
				table.reload('table', {
					where: $.formData().field
				});
			}
		}); 
	});
	
	
	//下拉框初始化
	$.loaddic("ydjb","select[name='level']",form);
	//下拉框初始化
	$.loaddic("ydfl","select[name='type']",form);
	
	
	//表格初始化
	var loadtip = $.layload();
	table.render({
		elem: '#table'
	    ,url: $.PROJECT_URL + "read/findArticle.do"
	    ,cols: [[
	    	{field:'name', width: '25%', title: '短文名称'}
	    	,{field:'levelName', width: '20%', title: '级别'}
	    	,{field:'typeName', width: '20%', title: '分类'}
	    	,{field:'wordNum', width: '10%', title: '单词数'}
	    	,{title: '操作', fixed: 'right', width: '25%', align:'center', toolbar: '#barDemo'}
	    ]]
		,page: true
		,done: function(res, curr, count){
		    //如果是异步请求数据方式，res即为你接口返回的信息。
		    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		    layer.close(loadtip);
		}
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
				title: '查看',
				shadeClose: false,
				shade: 0.8,
				area: ['60%', '60%'],
				content: $.PROJECT_URL +'html/read/info.html?articleId='+obj.data['id'],
				end: function(){
					table.reload('table', {
						where: $.formData().field
					});
				}
			}); 
		} 
	});		
		
	
});