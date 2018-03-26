/**
 * 
 */
//动态适应内容窗口大小
$(window).resize(function () {          //当浏览器大小变化时
	$("#contentIframe").height($(".layui-body").height()-3);
});
//iframe跳转方法
function iframeUrl(url){
	if($.get($.CONSTANT.LOGIN_TOKEN) != null){
		if(url && url != "")$('#contentIframe').attr('src',url);
	}else{
		toLogin();
	}
}
//登陆跳转方法
function toLogin(){
	$.set($.CONSTANT.LOGIN_TOKEN,"");
	window.location.href = $.PROJECT_URL + "login.html";
}
var contentIframeDom;
layui.use(['element'], function(){
	var element = layui.element;
	contentIframeDom = $("#contentIframe");
	//初始化窗口大小 site-mobile
	$("#contentIframe").height($(".layui-body").height()-3);
	
	if($.get($.CONSTANT.NAME_TOKEN) && $.get($.CONSTANT.NAME_TOKEN).indexOf("editor") == -1){
		$("#editor").hide();
		$("#admin").show();
	}
	element.render();
	/*响应式菜单*/
	$(".site-tree-mobile").on("click",function(){
		$("body").addClass("site-mobile");
	});
	$(".layui-main-shade,.layui-footer,.layui-header").on("click",function(){
		$("body").removeClass("site-mobile");
	});
	
	if($.get($.CONSTANT.LOGIN_TOKEN) != null){
		$("#denglu").hide();
		$("#user").show();
		$("#username").text($.get($.CONSTANT.NAME_TOKEN));
		$("#tuichu").click(function(event){
			toLogin();
			event.stopPropagation();
		});
		$("#center").click(function(event){
			iframeUrl("html/center.html");
			event.stopPropagation();
		});
	}
	//菜单监听
	element.on('nav(menu)', function(data){
		
		var contenturl =  $(data).find("a").attr('contenturl');
		if(!contenturl || contenturl == "") layer.alert((data).find("a").text()+"模块正在加速开发中 ^_^ ");
    	iframeUrl($(data).find("a").attr('contenturl'));
    	/*响应式菜单*/
		$("body").removeClass("site-mobile");
	});
	
	
});