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
	$.loaddic("nj","select[name='interest']",form);
	
	//日期初始化
	laydate.render({
		elem: '#date1'
	});
	
	//监听提交
	form.on('submit(submit)', function(data){
		layer.alert(JSON.stringify(data.field), {
			title: '最终的提交信息'
		})
		//
		table.reload('table', {
			where: data.field
		});
		return false;
	});
	//监听新增按钮
	$("#add").on('click', function(){
		layer.alert(JSON.stringify($.formData().field), {
			title: '新增'
		})
		console.log($.formData());
	});
	
	//表格初始化
	table.render({
		elem: '#table'
	    ,url: $.PROJECT_URL + "dic/finddic.do"
	    ,cols: [[
	    	{field:'id', title: '用户名'}
	    	,{field:'type',  title: '用户名', templet: function(d){
	            return '自定义：'+ d.type +'：哈哈哈哈哈'
	        }}
	    	,{field:'typeName', title: '用户名'}
	    	,{title: '操作', fixed: 'right', align:'center', toolbar: '#barDemo'}
	    ]]
		,page: true
		,where: {
			//TODO url参数
		}
		,method : "get"
	});
	
	
	//表格按钮监听
	table.on('tool(table)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象
		 
		if(layEvent === 'detail'){ //查看
		    //do somehing
			layer.alert("查看");
		} else if(layEvent === 'del'){ //删除
			layer.confirm('真的删除行么', function(index){
				obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
				layer.close(index);
				//向服务端发送删除指令
		    });
		} else if(layEvent === 'edit'){ //编辑
		    //同步更新缓存对应的值
			obj.update({
				typeName: '修改名称'
			});
		}
	});
	  
});