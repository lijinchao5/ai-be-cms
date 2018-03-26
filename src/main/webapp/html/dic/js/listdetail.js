var dic_id=$.urlparam('dic_id');

layui.use(['table','form', 'layedit', 'laydate'], function(){
	//调用父窗layer
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit
	  ,laydate = layui.laydate ,table = layui.table;
	
	var where={};
	where.dic_id=dic_id;
	var table = layui.table;
	table.render({
		elem: '#test'
	    ,url: $.PROJECT_URL + "dicdetail/finddic.do"
	    ,where: where
	    ,cols: [[
	    	{field:'name',  title: '名称'},
	    	{field:'nameVal',  title: '编码'},
	    	{field:'orderby',  title: '排序'}
//	    	,{fixed: 'right', align:'center', toolbar: '#barDemo'}
	    ]]
		,page: true
	});
	
	form.on('submit(submit)', function(data){
		console.log(data)
		table.reload('test', {
			where: data.field
		});
		return false;
	});
	
	  
});

function queryreload(table){
	table.reload('test', {
		where: $.formData().field
	});
}

function add(){
	layer.open({
		  type: 2,
		  title: '新增',
		  shadeClose: false,
		  shade: 0.8,
		  area: ['43%', '50%'],
		  content: $.PROJECT_URL +'html/user/update.html?dic_id='+dic_id,
		  end:function(){
			  //queryreload(layui.table);
		  }
	}); 
}
