/**
 * table使用示例
 */

layui.use(['element'], function(){
	//调用父窗layer
	if(window.parent && window.parent.layer){
		layer = window.parent.layer;
	}
	var element = layui.element;
	
	var loadtip = $.layload();
	$.ajaxs({
		url:'/bookManager/bookContent.do'
		,data : {bookId : $.urlparam('bookId') }
		,success : function(_data){
			
			var res = _data.result;
			var units = res.units, sections = res.sections, sectiondetails = res.sectiondetails;
			
			//单元
			var arrs = [];
			for (var i = 0; i < units.length; i++) {
				var _obj = units[i];
				_obj.class = "unit";
				_obj.domid = "unit"+_obj.id;
				_obj.title = _obj.name;
				_obj.content = '<div class="layui-collapse" contid="'+_obj.domid+'"></div>';
				var c = $.formatTemplate(_obj, $("#item").html());
				arrs.push(c);
			}
			$("#maincollapse").html(arrs.join(''));
			
			//小节
			for (var i = 0; i < sections.length; i++) {
				var _obj = sections[i];
				_obj.class = "section";
				_obj.domid = "section"+_obj.id;
				_obj.title = _obj.name;
				_obj.content = $.formatTemplate(_obj, $("#section").html());
				var c = $.formatTemplate(_obj, $("#item").html());
				$("div[contid='unit"+_obj.unitId+"']").append(c);
			}
			
			//单词句子对话
			for (var i = 0; i < sectiondetails.length; i++) {
				var _obj = sectiondetails[i];
				_obj.class = "sectiondetail"+_obj.type;
				_obj.domid = "sectiondetail"+_obj.id;
				
				if(_obj.wordMean && _obj.wordMean != "") {
					_obj.text = _obj.text + ' [ <span style="color:#FF784E;">' + _obj.wordMean + ' </span>] ';
				}
				
				if(_obj.type == 1){
					_obj.content = _obj.text;
				}else if(_obj.type == 3){
					_obj.content = _obj.text;
					_obj.space = '<br />';
				}else if(_obj.type == 4){
					_obj.content = "对话"+_obj.dialogNum+"--"+_obj.name+"："+_obj.text;
					_obj.space = '<br />';
				}
				var c = $.formatTemplate(_obj, $("#detail").html());
				$(".type"+_obj.type+"[domid='section"+_obj.sectionId+"']").append(c);
			}
			
			$(".layui-elem-field").each(function(i,n){
				var l = $(this).find("button").length;
				if(l==0)$(this).hide();
			});
			
			var support = !!document.createElement("audio").canPlayType;  
			if (!support) {  
			    layer.alert("你的浏览器不支持播放");  
			    return;
			}
			
			//播放对象
			var audiodom = document.getElementById("audio");
			//单词点击
			$(".sectiondetail1").on("click",function(){
				
				var mAudioPath = $(this).attr("mAudioPath");
				var wAudioPath = $(this).attr("wAudioPath");
				
				if(mAudioPath && mAudioPath != "" && mAudioPath.indexOf("upload") == -1){
					
					var audiopath = $.PROJECT_URL + "file/download.do?type=mp3&id=" + mAudioPath;
					if(!audiodom.paused) audiodom.pause();  
					$(audiodom).attr("src", audiopath );
				    audiodom.play();
			   	 	console.log(audiopath);
				} else if(wAudioPath && wAudioPath != "" && wAudioPath.indexOf("upload") == -1){
					
					var audiopath = $.PROJECT_URL + "file/download.do?type=mp3&id=" + wAudioPath;
					if(!audiodom.paused) audiodom.pause();  
					$(audiodom).attr("src", audiopath );
				    audiodom.play();
			   	 	console.log(audiopath);
				}else{
					layer.alert("音频不存在！建议重新导入");
				}
			    
			    var picturePath = $(this).attr("picturePath");
			    if(!picturePath || picturePath == "" ||  picturePath.indexOf("upload") != -1) return;
			    picturePath = $.PROJECT_URL + "file/download.do?type=jpg&id=" + picturePath;
			    console.log(picturePath);
			    layer.open({
				  type: 1,
				  title: false,
				  closeBtn: 0,
				  offset: '35%',
				  skin: 'layui-layer-nobg', //没有背景色
				  shadeClose: true,
				  content: '<img style="width:300px;" src="'+picturePath+'"/>'
				});
			});
			
			//句子对话点击
			$(".sectiondetail3,.sectiondetail4").on("click",function(){
				var audioPath = $(this).attr("audioPath");
				if(audioPath && audioPath != ""){
					var audiopath = $.PROJECT_URL + "file/download.do?type=mp3&id=" + audioPath;
					if(!audiodom.paused) audiodom.pause();  
					$(audiodom).attr("src", audiopath );
				    audiodom.play();
				}else{
					layer.alert("音频不存在！建议重新导入");
				}
			});
			
			element.render('collapse');
			layer.close(loadtip);
		}
		,error : function(_data){
			layer.close(loadtip);
			layer.alert(_data.message);
		}
	})
});