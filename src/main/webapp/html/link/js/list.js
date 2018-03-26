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
			area: ['60%', '60%'],
			content: $.PROJECT_URL +'html/link/edit.html',
			end: function(){
				table.reload('table', {
					where: $.formData().field
				});
			}
		}); 
	});
	
	//表格初始化
	var loadtip = $.layload();
	table.render({
		elem: '#table'
	    ,url: $.PROJECT_URL + "link/findLinks.do"
	    ,cols: [[
	    	{field:'type', width: '20%', title: '分类', templet: function(d){
	    		var type = d['type'];
	    		if(type && type!=''){
	    			if(type == "1") return "专家推荐";
	    			else if(type == "2") return "合作院校";
	    		}
	    		return '';
	        }}
	    	,{field:'name', width: '25%', title: '信息名称'}
	    	,{field:'info', width: '40%', title: '信息摘要', templet: function(d){
	    		
	    		var arr = [];
	    		if(d['desp1'])arr.push(d['desp1']);
	    		if(d['desp2'])arr.push(d['desp2']);
	    		if(d['desp3'])arr.push(d['desp3']);
	    		
	    		return arr.join(" ; ");
	        }}
	    	,{title: '操作', fixed: 'right', width: '15%', align:'center', toolbar: '#barDemo'}
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
				title: '编辑',
				shadeClose: false,
				shade: 0.8,
				area: ['60%', '60%'],
				content: $.PROJECT_URL +'html/link/edit.html?id='+obj.data['id'],
				end: function(){
					table.reload('table', {
						where: $.formData().field
					});
				}
			}); 
		}else if(layEvent === 'T' || layEvent === 'F'){ //查看
			
			var status = layEvent === 'T'?1:0;
			var loadtip = $.layload();
			$.ajaxs({
				url:'/link/delLink.do'
				,data : {status : status, id : obj.data['id']}
				,success : function(data){
					layer.alert('操作成功！');
					layer.close(loadtip);
					layer.closeAll();
					table.reload('table', {
						where: data.field
					});
				}
				,error : function(data){
					layer.close(loadtip);
					layer.alert(data.message);
				}
			})
		}
	});		
		
});

