/**
 * table使用示例
 */

layui.use(['element','table'], function(){
	//调用父窗layer
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	var element = layui.element,table = layui.table;
	
	var loadtip = $.layload();
	$.ajaxs({
		url:'/read/articleInfo.do'
		,data : {articleId : $.urlparam('articleId') }
		,success : function(_data){
			var res = _data.result;
			
			$('#name').text(res['name']);
			$('#wordNum').text(res['wordNum']);
			$('#levelName').text(res['levelName']);
			$('#typeName').text(res['typeName']);
			
			table.render({
				elem: '#table'
			    ,data: res.list
			    ,cols: [[
			    	{field:'orderNum', title: '序号', width: '8%'}
			    	,{field:'sentenceCont', title: '段落内容', width: '50%'}
			    	,{field:'sentenceMean', title: '中文释义', width: '26%'}
			    	,{field:'audioPath', width: '8%', title: '音频', templet: function(d){
			    		var path = d['audioPath'];
			    		if(path && path!=''){
			    		  return '<i class="layui-icon" title="播放" onclick="playaudio(\''+path+'\')" style="color: #33AB9F;cursor: pointer;">&#xe652;</i>';
			    		}
			    		return '';
			        }}
			    	,{field:'wordNum', title: '词数', width: '8%'}
			    ]]
		    	,limit : 10000
			});
			
			layer.close(loadtip);
		}
		,error : function(_data){
			layer.close(loadtip);
			layer.alert(_data.message);
		}
	})
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
