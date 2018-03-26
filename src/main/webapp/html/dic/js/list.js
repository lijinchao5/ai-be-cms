
layui.use(['table','form', 'layedit', 'laydate'], function(){
	//调用父窗layer
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit
	  ,laydate = layui.laydate ,table = layui.table;
	
	var where={};
	var table = layui.table;
	table.render({
		elem: '#test'
	    ,url: $.PROJECT_URL + "dic/finddic.do"
	    ,where: where
	    ,cols: [[
	    	{field:'typeName',  title: '名称'},
	    	{field:'type',  title: '编码'}
	    	,{title: '操作', fixed: 'right', align:'center', toolbar: '#barDemo'}
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
	
	table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象
		 
		if(layEvent === 'adddetail'){ //添加子项
			window.location.href=$.PROJECT_URL +'html/dic/listdetail.html?dic_id='+data.id;
		} else if(layEvent === 'del'){ //删除
			layer.confirm('确定删除吗？', function(index){
				$.ajaxs({
					url:'dic/delDic.do',
					param:data,
					success : function(data) {
						queryreload(table);
					},
					  error:function(data){
						  layer.alert(data.message);
					  }
				});
				layer.close(index);
		    });
		} else if(layEvent === 'edit'){ //编辑
			 layer.open({
				  type: 2,
				  title: '修改',
				  shadeClose: false,
				  shade: 0.8,
				  area: ['43%', '50%'],
				  content: $.PROJECT_URL +'html/user/update.html?id='+data.id+'&type='+type+'&relationid='+relationid,
				  end:function(){
					  queryreload(table);
				  }
			}); 
		}
	});
	  
});

function queryreload(table){
	table.reload('test', {
		where: $.formData().field
	});
}

function addschool(){
	layer.open({
		  type: 2,
		  title: '新增',
		  shadeClose: false,
		  shade: 0.8,
		  area: ['43%', '50%'],
		  content: $.PROJECT_URL +'html/user/update.html?type='+type+'&relationid='+relationid,
		  end:function(){
			  //queryreload(layui.table);
		  }
	}); 
}

function dateFormats(fmt,date) {
	if(date==null){
		return "";
	}
    var o = {
        "M+": new Date(date).getMonth() + 1, //月份 
        "d+": new Date(date).getDate(), //日 
        "h+": new Date(date).getHours(), //小时 
        "m+": new Date(date).getMinutes(), //分 
        "s+": new Date(date).getSeconds(), //秒 
        "q+": Math.floor((new Date(date).getMonth() + 3) / 3), //季度 
        "S": new Date(date).getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (new Date(date).getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

