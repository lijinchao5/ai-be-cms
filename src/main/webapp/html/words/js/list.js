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
			content: $.PROJECT_URL +'html/words/edit.html',
			end: function(){
				table.reload('table', {
					where: $.formData().field
				});
			}
		}); 
	});
	
	
	var dicdata = [];
	//下拉框初始化
	$.loaddic("dck","select[name='type']",form,undefined,undefined,function(data){
		
	dicdata = data ;
	
	//表格初始化
	var loadtip = $.layload();
	table.render({
		elem: '#table'
	    ,url: $.PROJECT_URL + "words/findWords.do"
	    ,cols: [[
	    	{field:'wordSpell', width: '25%', title: '单词拼写'}
	    	,{field:'wordMean', width: '30%', title: '单词词义'}
//	    	,{field:'typeName', width: '20%', title: '所属单词库'}
	    	,{field:'type', width: '19%', title: '所属单词库', templet: function(d){
	    		var type = d['type'];
	    		if(type && type!=''){
	    			
	    			for (var i = 0; i < dicdata.length; i++) {
	    				if(dicdata[i] && dicdata[i]['nameVal']==type) return dicdata[i]['name'];
	    			}
	    		}
	    		return '';
	        }}
	    	,{field:'audioPathM', width: '9%', title: '男声', templet: function(d){
	    		var path = d['audioPathM'];
	    		if(path && path!=''){
	    		  return '<i class="layui-icon" title="播放" onclick="playaudio(\''+path+'\')" style="color: #33AB9F;cursor: pointer;">&#xe652;</i>';
	    		}
	    		return '';
	        }}
	    	,{field:'audioPathM', width: '9%', title: '女声', templet: function(d){
	    		var path = d['audioPathW'];
	    		if(path && path!=''){
	    		  return '<i class="layui-icon" title="播放" onclick="playaudio(\''+path+'\')" style="color: #33AB9F;cursor: pointer;">&#xe652;</i>';
	    		}
	    		return '';
	        }}
	    	,{title: '操作', fixed: 'right', width: '8%', align:'center', toolbar: '#barDemo'}
	    	/*,{fixed: 'right', align:'center', toolbar: '#barDemo'}*/
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
	
	
	
});

function playaudio(path){

	var support = !!document.createElement("audio").canPlayType;  
	if (!support) {  
	    layer.alert("你的浏览器不支持播放");  
	    return;
	}
	//播放对象
	var audiodom = document.getElementById("audio");
	//单词点击
	if(path && path != ""){
		var audiopath = $.PROJECT_URL + "file/download.do?type=mp3&id=" + path;
		if(!audiodom.paused) audiodom.pause();  
		try{
			$(audiodom).attr("src", audiopath );
		    audiodom.play();
		}catch(e){
			layer.alert("音频播放错误，请检查网络。")
   	 		console.log(e);
		}
	}
}
