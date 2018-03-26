/**
 * 工具类封装
 * 
 * @author WangMeng  
 */
;!(function(_w,undefined){
	_w.jQuery.extend({
		/*常量*/
		CONSTANT : {
			
			SUCCESS_CODE : 0,
			NO_LOGIN_CODE : 99998,
			REPEAT_ERROR_CODE : 10001,
			
			HTTP_FAIL_CODE : -999,
			HTTP_FAIL_MESSAGE : "请求发送失败。",
			
			LOGIN_TOKEN : "_jijidsSJ_jjsOWNS",
			NAME_TOKEN : "QfbFdsSJ"
		},
		/*项目路径*/
		PROJECT_URL : (function () {
            //获取当前网址，如： http://localhost:8083/qqq/share/meun.jsp
            var curWwwPath = window.document.location.href;
            //获取主机地址之后的目录，如： qqq/share/meun.jsp
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            //获取主机地址，如： http://localhost:8083
            var localhostPaht = curWwwPath.substring(0, pos);
            //获取带"/"的项目名，如：/qqq
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            if(projectName != "/oep-be-cms") projectName = "";
            return (localhostPaht+projectName+"/");
        })(),
        layload : function(){
        	return layer.load(1,{shade : [ 0.2, '#000' ]});
        },
        /*参数获取*/
    	urlparam : function (name) {
    		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var s = window.location.search;
            try{ s=decodeURI(s);//url编码解码
            }catch(e){}
            var r = s.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        },
		/*登陆封装*/
		login : function(param, _success, _error){
			if(!_success || typeof _success != "function") _success = function(){};
			if(!_error || typeof _error != "function") _error = function(){};
			$.ajax({
				type : 'POST',// 必须是POST
				data : param,
				dataType : 'json',
				url : $.PROJECT_URL + "login.do",
				success : function(data) {
					if(data.code == $.CONSTANT.SUCCESS_CODE){
						var tokenId = data.result;
						var isset = $.set($.CONSTANT.LOGIN_TOKEN,tokenId);
						$.set($.CONSTANT.NAME_TOKEN, param.username);
						if(!isset) _error({message : "用户信息写入失败"});
						_success(data);
					}else _error(data);
				},
				error : function (){
					_error({
						code : $.CONSTANT.HTTP_FAIL_CODE,
						message : $.CONSTANT.HTTP_FAIL_MESSAGE
					});
				}
			});
		},
		/*ajax封装--加入身份验证*/
		ajaxs : function(obj){
			var _def = {
					type : 'POST',
					url : "",
					param : undefined,
					data : undefined,
					success : function(){},
					error : function(){}
			};
			$.extend( _def, obj );
			
			var _success=_def.success,_error=_def.error
			if(!_success || typeof _success != "function") _success = function(){};
			if(!_error || typeof _error != "function") _error = function(){};
			
			try {
				$.ajax({
					type : _def.type,// 必须是POST
					data : _def.param || _def.data || {},
					dataType : 'json',
					headers : {
						"X-AUTH-TOKEN" : $.get($.CONSTANT.LOGIN_TOKEN)
					},
					timeout:3000000,
					url : $.PROJECT_URL + _def.url,
					success : function(data) {
						if(data.code == $.CONSTANT.SUCCESS_CODE) _success(data);
						else if(data.code == $.CONSTANT.NO_LOGIN_CODE){
							layer.alert('登陆状态失效，请重新登陆。', {
								closeBtn: 0
							}, function(){
								if(window.parent && window.parent.toLogin) window.parent.toLogin();
								else window.location.href = $.PROJECT_URL + "login.html";
							});
						}else _error(data);
					},
					error : function (){
						_error({
							code : $.CONSTANT.HTTP_FAIL_CODE,
							message : $.CONSTANT.HTTP_FAIL_MESSAGE
						});
					}
				});
			} catch (e) {
				console.log(e);
				_error({
					message : "脚本执行异常!"
				});
			}
		},
		/*ajaxSubmits封装--加入身份验证*/
		ajaxSubmits : function(obj){
			var _def = {
					form : undefined,
					type : 'POST',
					url : "",
					param : undefined,
					data : undefined,
					success : function(){},
					error : function(){}
			};
			$.extend( _def, obj );
			
			var _success=_def.success,_error=_def.error
			if(!_success || typeof _success != "function") _success = function(){};
			if(!_error || typeof _error != "function") _error = function(){};
			if(!_def.form) _error({ message : "表单未找到" });
			try {
				$(_def.form).ajaxSubmit({
					type : _def.type,// 必须是POST
					data : _def.param || _def.data || {},
					dataType : 'json',
					headers : {
						"X-AUTH-TOKEN" : $.get($.CONSTANT.LOGIN_TOKEN)
					},
					url : $.PROJECT_URL + _def.url,
					success : function(data) {
						if(data.code == $.CONSTANT.SUCCESS_CODE) _success(data);
						else if(data.code == $.CONSTANT.NO_LOGIN_CODE){
							layer.alert('登陆状态失效，请重新登陆。', {
								closeBtn: 0
							}, function(){
								if(window.parent && window.parent.toLogin) window.parent.toLogin();
								else window.location.href = $.PROJECT_URL + "index.html";
							});
						}else _error(data);
					},
					error : function (){
						_error({
							code : $.CONSTANT.HTTP_FAIL_CODE,
							message : $.CONSTANT.HTTP_FAIL_MESSAGE
						});
					}
				});
			} catch (e) {
				console.log(e);
				_error({
					message : "脚本执行异常!"
				});
			}
		},
		
		/*localStorage存储*/
		set : function(name, obj){
			if(name)
				try { if(typeof obj == "object") obj = JSON.stringify(obj); localStorage.setItem(name, obj); return true; }
				catch (e) { console.log(e); return false; }
			else return false; 
		},
		/*localStorage读取*/
		get : function(name){
			if(name)
				try { var _val = localStorage.getItem(name); if(!_val) return null; else return _val; } 
				catch (e) { console.log(e); return false; }
			else return null; 
		},
		/*localStorage读取-转换对象*/
		getObj : function(name){
			var _val = $.get(name);if(!_val) return null;
			else { try {_val =  JSON.parse(_val);}  catch (e) {console.log(e);} return _val; }
		},
		//数据字典下拉框加载封装，codeType-数据字典类型，$elem-dom选择器，form-表单对象，selval-选中值，isDisabled-是否可用
		loaddic : function (codeType,$elem,form,selval,isDisabled,callback){
			if(!callback || typeof callback != "function") callback = function(){};
	  		var loadtip = $.layload();
	  		$.ajaxs({
	  			url:'/dic/findDicByType.do'
	  			,data : {type : codeType}
	  			,success : function(data){
	  				$.loadSelect({
	  					value : "nameVal",
	  					dom : $elem,
	  					data : data.result,
	  					form : form,
	  					selval : selval,
	  					isDisabled : isDisabled
	  				});
	  				callback(data.result);
	  				layer.close(loadtip);
	  			}
	  			,error : function(data){
	  				layer.close(loadtip);
	  				layer.alert(data.message);
	  			}
	  		})
		},
		//下拉框加载封装，data-数据，text-文本属性名，value-值属性名，dom-选择器，form-表单对象，selval-选中值，isDisabled-是否可用
		loadSelect : function (opt){
			var _option = {
				text : "name",
				value : "id",
				dom : "select:first",
				isDisabled : false,
				data : [],
				form : null,
				selval : undefined,
				isClear : false
			};
			$.extend( _option, opt );
			if(!_option.form) { console.log("form对象不存在"); return;}
	  		$elem = $(_option.dom);
	  		if(_option.isClear)$elem.html("");
			$elem.append("<option value='' >请选择</option>");
			jQuery.each(_option.data || [], function(i,item){
				$elem.append("<option value='"+item[_option.value]+"' "
						+ (_option.selval==item[_option.value]?"selected":"") +" >"+item[_option.text]+"</option>");
            });
			if(_option.isDisabled) $elem.attr("disabled","disabled");
			_option.form.render();//select更改以后，重新渲染页面
			$elem.removeAttr("disabled");
		},
		//获取表单数据
		formData : function(formSel){
		    var field = {}
			    ,formElem = formSel?$(formSel):$('.layui-form') //获取当前所在的form元素，如果存在的话
			    ,fieldElem = formElem.find('input,select,textarea') //获取所有表单域
		    ;
		    var nameIndex = {}; //数组 name 索引
		    layui.each(fieldElem, function(_, item){
		    	item.name = (item.name || '').replace(/^\s*|\s*&/, '');
		    	if(!item.name) return;
		    	//用于支持数组 name
		    	if(/^.*\[\]$/.test(item.name)){
		    		var key = item.name.match(/^(.*)\[\]$/g)[0];
		    		nameIndex[key] = nameIndex[key] | 0;
		    		item.name = item.name.replace(/^(.*)\[\]$/, '$1['+ (nameIndex[key]++) +']');
		    	}
		    	if(/^checkbox|radio$/.test(item.type) && !item.checked) return;      
		    	field[item.name] = item.value;
		    });
	    
		    return {form: formElem
		        ,field: field};
	  	}
		/*模板数据绑定*/
		,formatTemplate : function(data, tmpl) {
			var format = {
				name : function(x) { return x; }
			};
			return tmpl.replace(/{(\w+)}/g, function(m1, m2) {
				if (!m2) return "";
				var _val = (format && format[m2]) ? format[m2](data[m2]) : data[m2];
				return (_val!=undefined && _val!= null) ? _val : "";
			});
		}
		,wordNum : function(value){
			if(!value || value == null) return 0;
			value = value.replace(/[\u4e00-\u9fa5]+/g, " ");
			value = value.replace(/[\.]+/g, " ");
			value = value.replace(/[,]+/g, " ");
			value = value.replace(/[\n]+/g, " ");
			value = value.replace(/\|\|+/g, " ");
			console.log(value)
	        // 将换行符，前后空格不计算为单词数
	        value = value.replace(/\n|\r|^\s+|\s+$/gi,"");
	        // 多个空格替换成一个空格
	        value = value.replace(/\s+/gi," ");
	        // 更新计数
	        var length = 0;
	        var match = value.match(/\s/g);
	        if (match) {
	            length = match.length + 1;
	        } else if (value) {
	            length = 1;
	        }
	        return length;
		}
		/*保持连接，防止状态过期*/
		,keeplive : function (time){
			var _livetime = time || 90*1000;
			_w.setTimeout(_live, _livetime);
	  		function _live(){
		  		$.ajaxs({
		  			url:'/keeplive.do'
		  			,data : {type : "xq"}
		  			,success : function(data){
		  				$.keeplive(_livetime);
		  			}
		  		})
	  		}
		},
	});
})(window,undefined);