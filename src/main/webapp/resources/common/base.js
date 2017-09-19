//div加载页面
function loadPage(url, container) {
	if (!container)
	container = "#mainDiv";
	$(container).load(url, function(response, status, xhr) {
		if (status == "success") {
			if (response) {
				try {
					var result = jQuery.parseJSON(response);
					if (result.code == 100) {
					jQuery(container).html("");
					alert(result.data);
					}
				} catch (e) {
					return response;
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
	this.lastDialog = null;
	this.value = null;
};
__HdDialog.prototype.push = function(dialog){
	this.lastDialog = dialog;
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
	if(this.lastDialog !=null){
		this.lastDialog.close();
	}
};
__HdDialog.prototype.getValue = function(){
	return this.value;
};
var HdDialog = new __HdDialog();