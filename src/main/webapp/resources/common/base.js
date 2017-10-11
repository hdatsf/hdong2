//验证框架
$.validator.setDefaults({
    showErrors: function(errorMap, errorList) {
        $.each(this.successList, function(index, value) {
        	$(value).tooltip('destroy').removeClass('error');
        });
        $.each(errorList, function(index, value) {
        	if ($(value.element).next("div").hasClass("tooltip")) {
        		$(value.element).attr("data-original-title",value.message).addClass('error').next("div .tooltip").find(".tooltip-inner").text(value.message);
             } else {
                $(value.element).attr("data-original-title",value.message).tooltip("show").addClass('error');
            }
        });
    }
});

//div加载页面
function loadPage(url, container) {
	if (!container){
		container = "#mainDiv";
	}
	$(container).load(url, function(response, status, xhr) {
		if (status == "error") {
			if (response) {
				try {
					var result = jQuery.parseJSON(response);
					if (result.code != 1) {
						$.HdConfirm({
							content: result.data.errorMsg,
							buttons: {
								confirm: {text: '确认'}
							}
						});
					}
				} catch (e) {
					$(container).html(response);
				}
			}
		}
	});
};

// 封装jquery.dialog
if (typeof jQuery === 'undefined') {
	throw new Error('jquery-confirm requires jQuery');
}
if (typeof jQuery.dialog === 'undefined') {
	throw new Error('hd-dialog requires jQuery.dialog');
}
$.hdDialog=function (options, option2) {
	var defaultOpt={
		animationSpeed: 100,
		type: 'blue',
		theme:'bootstrap'
	};
	options = $.extend({},defaultOpt,options);
	var onClose;
	var optionClose = options.onClose;
	if (typeof options.onClose === 'function'){
		onClose = function(){
			HdDialog.pop();
			optionClose();
		};
	}else{
		onClose = function(){
			HdDialog.pop();
		};
	}
	options.onClose = onClose;
	var dialog = $.dialog(options, option2);
	HdDialog.push(dialog);
};
__HdDialog = function(){
	this.dialogs = new Array();
	this.value = null;
};
__HdDialog.prototype.push = function(dialog){
	this.value = null;
	return this.dialogs.push(dialog);
};
__HdDialog.prototype.pop = function(){
	if(this.dialogs.length>0){
		return this.dialogs.pop();
	}
	return null;
};
__HdDialog.prototype.close = function(value){
	this.value = value;
	if(this.dialogs.length>0){
		this.dialogs[this.dialogs.length-1].close();
	}
};
__HdDialog.prototype.getValue = function(){
	return this.value;
};
var HdDialog = new __HdDialog();


//确认框
$.hdConfirm=function (options, option2) {
	var defaultOpt={
		animationSpeed: 100,
		type: 'red',
		title: false,
		theme:'bootstrap'
	};
	options = $.extend({},defaultOpt,options);
	var onClose;
	var optionClose = options.onClose;
	if (typeof options.onClose === 'function'){
		onClose = function(){
			HdConfirm.pop();
			optionClose();
		};
	}else{
		onClose = function(){
			HdConfirm.pop();
		};
	}
	options.onClose = onClose;
	var dialog = $.confirm(options, option2);
	HdConfirm.push(dialog);
};
__HdConfirm = function(){
	this.dialogs = new Array();
	this.value = null;
};
__HdConfirm.prototype.push = function(dialog){
	this.value = null;
	return this.dialogs.push(dialog);
};
__HdConfirm.prototype.pop = function(){
	if(this.dialogs.length>0){
		return this.dialogs.pop();
	}
	return null;
};
__HdConfirm.prototype.close = function(value){
	this.value = value;
	if(this.dialogs.length>0){
		this.dialogs[this.dialogs.length-1].close();
	}
};
__HdConfirm.prototype.getValue = function(){
	return this.value;
};
var HdConfirm = new __HdConfirm();

$.hdErrorConfirm = function(result){
	if(result.code!=undefined){
		$.hdConfirm({
			type: 'red',
			content: result.msg,
			buttons: {confirm: {text: '确认'}}
		});
	}else{
		$.hdConfirm({
			type: 'red',
			columnClass:'col-md-offset-2 col-md-8',
			content: result,
			buttons: {confirm: {text: '确认'}}
		});
	}
};

//获取字典项
__HdDict = function(){
	this.url = basePath + "/sys/dicts";
	this.dictMap = null;//{"app-type-name","desc"}
	this.dictArr = null;//{"app-type",[{name:"name",desc:"desc"}]}
};
//获取描述，一般用于表格
__HdDict.prototype.getDictDesc = function(app, type, name){
	var key = app+"-"+type+"-"+name;
	var my = this;
	if(this.dictMap == null){
		$.ajax({
	        type: 'get',
	        url: this.url,
	        success: function(result) {
	        	my.dictMap = result.dictMap;
	        	my.dictArr = result.dictArr;
	        	return my.dictMap[key];
	        }
	    });
	}else{
		return my.dictMap[key];
	}
};
//获取集合，一般用于下拉框[{name:"name",desc:"desc"}]
__HdDict.prototype.initSelect = function(app, type, selectObj, selectedName){
	var key = app+"-"+type;
	var my = this;
	if(this.dictArr == null){
		$.ajax({
	        type: 'get',
	        url: this.url,
	        success: function(result) {
	        	my.dictMap = result.dictMap;
	        	my.dictArr = result.dictArr;
	        	my.initOption(my.dictArr[key], selectObj, selectedName);
	        }
	    });
	}else{
		my.initOption(my.dictArr[key], selectObj, selectedName);
	}
};
__HdDict.prototype.initOption = function(arr, selectObj, selectedName){
	if(!(selectObj instanceof jQuery)){
		throw new Error('selectObj is not a jquery object');
	}
	var option = [];
	for(var i=0;i<arr.length;i++){
		var item = arr[i];
		if(selectedName!=undefined && selectedName == item.name){
			option.push('<option value="'+item.name+'" selected="selected">'+item.desc+'</option>');
		}else{
			option.push('<option value="'+item.name+'">'+item.desc+'</option>');
		}
	}
	selectObj.append(option.join(""));
};
__HdDict.prototype.init = function(){
	var my = this;
	if(this.dictArr == null){
		$.ajax({
	        type: 'get',
	        url: this.url,
	        success: function(result) {
	        	my.dictMap = result.dictMap;
	        	my.dictArr = result.dictArr;
	        }
	    });
	}
};
var HdDict = new __HdDict();

//下拉框
$.fn.initSelectByDict=function(app, type, selected){
	var options = HdDict.getDictArr(app, type);
	var optionStr = '';
	for(var i=0;i<options.length;i++){
		var selectedStr = options[i].name == selected?"selected":"";
		optionStr += '<option value="'+options[i].name+'" '+selectedStr+'>'+options[i].desc+'</option>' 
	}
	if(optionStr!=''){
		this.html(optionStr);
	}
};
