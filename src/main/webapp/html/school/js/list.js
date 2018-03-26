
//查询下拉
function qx(Parentid,form,docmentid){
	var loadtip = $.layload();
	$.ajaxs({
		url:'area/findByParentid.do',
		param:{'parentid':Parentid},
		success : function(data) {
			$.loadSelect({
					dom : "#"+docmentid,
					data : data.result,
					form : form,
					text : "texts",
					value : "id",
					isClear:true
				});
			layer.close(loadtip);
		},
		error:function (){
			layer.close(loadtip);
		}
	});
}

layui.use(['table','form', 'layedit', 'laydate'], function(){
	//调用父窗layer
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	
	var form = layui.form ,layedit = layui.layedit
	  ,laydate = layui.laydate ,table = layui.table;
	
	qx('0',form,'sf');
	//监听省份，
	form.on('select(sf)', function(data){
		$.loadSelect({
			dom : "#sq",
			data : [],
			isClear:true,
			form : form
		});
		  qx(data.value,form,'sq');
		  $.loadSelect({
				dom : "#qx",
				data : [],
				isClear:true,
				form : form
			});
	});
	
	//监听市区
	form.on('select(sq)', function(data){
		qx(data.value,form,'qx');
	});
	
	var table = layui.table;
	table.render({
		elem: '#test'
	    ,url: $.PROJECT_URL + "school/findSchool.do"
	    ,cols: [[
	    	{field:'name',  title: '学校名称', width:'15%'}
	    	,{field:'schoolId', title: '学校ID', width:'7.8%'}
//	    	,{field:'addressProvinceName', title: '所属省份'}
//	    	,{field:'addressCityName', title: '所属城市'}
//	    	,{field:'addressAreaName', title: '所属区县'}
	    	,{field:'addressAreaName', title: '所属地区', width:'20.4%', templet: function(d){
	            return d.addressProvinceName + "--" + d.addressCityName + "--" + d.addressAreaName;
	        }}
	    	,{field:'startdate', title: '开始时间', width:'12%', templet: function(d){
	            return dateFormats("yyyy-MM-dd",d.startdate);
	        }},{field:'enddate', title: '结束时间', width:'12%', templet: function(d){
	            return dateFormats("yyyy-MM-dd",d.enddate);
	        }},{field:'sss', title: '状态', width:'6%', templet: function(d){
	        	if(d.enddate>new Date().getTime()){
	        		return "启用";
	        	}else{
	        		return "停用";
	        	}
	        }}
	    	,{title: '操作', fixed: 'right', align:'center', width:'27%', toolbar: '#barDemo'}
	    ]]
		,page: true
	});
	
	form.on('submit(submit)', function(data){
		table.reload('test', {
			where: data.field
		});
		return false;
	});
	
	form.on('button(add)', function(data){
		layer.open({
			  type: 2,
			  title: '新增',
			  shadeClose: false,
			  shade: 0.8,
			  area: ['43%', '50%'],
			  content: $.PROJECT_URL +'html/school/update.html'
		}); 
		return false;
	});
	  
	table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象
		 
		if(layEvent === 'detail'){ //查看
			layer.open({
				  type: 2,
				  title: '续用',
				  shadeClose: false,
				  shade: 0.8,
				  area: ['43%', '450px'],
				  content: $.PROJECT_URL +'html/school/updateTime.html?id='+data.id,
				  end:function(){
					  queryreload(table);
				  }
			});
		} else if(layEvent === 'del'){ //删除
			layer.confirm('确定删除吗？', function(index){
				$.ajaxs({
					url:'school/deleteSchool.do',
					param:data,
					success : function(data) {
						table.reload('test', {
							where: $.formData().field
						});
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
				  content: $.PROJECT_URL +'html/school/update.html?id='+data.id,
				  end:function(){
					  queryreload(table);
				  }
			}); 
		}else if(layEvent ==="user"){
			layer.open({
				  type: 2,
				  title: '用户管理',
				  shadeClose: false,
				  shade: 0.8,
				  area: ['50%', '72%'],
				  content: $.PROJECT_URL +'html/user/list.html?type=8&relationid='+data.id,
				  end:function(){
					  //queryreload(table);
				  }
			}); 
		}else if(layEvent==="adduser"){
			layer.open({
				  type: 2,
				  title: '新增用户',
				  shadeClose: false,
				  shade: 0.8,
				  area: ['43%', '50%'],
				  content: $.PROJECT_URL +'html/user/update.html?type=8&relationid='+data.id,
				  end:function(){
					  //queryreload(layui.table);
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
		  content: $.PROJECT_URL +'html/school/update.html',
		  end:function(){
			  queryreload(layui.table);
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