/**
 * table使用示例
 */

layui.use(['table', 'form', 'layedit', 'laydate'], function(){
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
	$("#add").on('mouseover', function(){
		$(".add-bar").css("top",$('#add').offset().top+38);
		$(".add-bar").css("left",$('#add').offset().left-30);
		$(".add-bar").show();
	});
	$(".oper-bar").on('mouseleave', function(){
		$(".add-bar").hide();
	});
	
	//监听新增按钮
	var typeurl = [
		''
		,'html/question/type1.html'
		,'html/question/type2.html'
		,'html/question/type3.html'
		,'html/question/type4.html'
		,'html/question/type5.html'
		,'html/question/type6.html'
	];
	$("dd a[type]").each(function(){
		$(this).on('click',function(){
			var type = $(this).attr("type");
			if(window.parent && window.parent.iframeUrl) window.parent.iframeUrl(typeurl[type]);
		});
	});
	
	//下拉框初始化
	$.loaddic("sjdtx","select[name='type']",form);
	$.loaddic("jcnj","select[name='gradeLevelId']",form);
	$.loaddic("xq","select[name='term']",form);
	
	//表格初始化
	table.render({
		elem: '#table'
	    ,url: $.PROJECT_URL + "questionManager/findQuestions.do"
	    ,cols: [[
	    	{field:'questionTypeName', title: '试题题型'}
	    	,{field:'gradeName', title: '年级'}
	    	,{field:'term', title: '学期', templet: function(d){
	    		if(d.term == '1') return '上学期';
	    		else if(d.term == '2') return '下学期';
	    		return '';
	        }}
	    	,{title: '操作', fixed: 'right', width: '8%', align:'center', toolbar: '#barDemo'}
	    ]]
		,page: true
		,done: function(res, curr, count){
		    //如果是异步请求数据方式，res即为你接口返回的信息。
		    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
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
				content: $.PROJECT_URL +'html/words/edit.html?id='+obj.data['id'],
				end: function(){
					table.reload('table', {
						where: $.formData().field
					});
				}
			}); 
		} 
	});		
		
});
