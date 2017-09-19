<%@ page contentType="text/html; charset=utf-8"%>
<div id="createDialog">
	<form id="createForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="theme" class="col-md-3 control-label">主题色</label>
			<div class="col-md-9">
				<input id="theme" type="color" class="form-control" name="theme" maxlength="50">
			</div>
		</div>
		<div class="form-group">
			<label for="icon" class="col-md-3 control-label">图标</label>
			<div class="col-md-9">
				<input id="icon" type="text" class="form-control" name="icon" maxlength="20">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-md-3 control-label">标题</label>
			<div class="col-md-9">
				<input id="title" type="text" class="form-control" name="title" maxlength="20" required="true">
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-md-3 control-label">名称</label>
			<div class="col-md-9">
				<input id="name" type="text" class="form-control" name="name" maxlength="20" required="true">
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-md-3 control-label">描述</label>
			<div class="col-md-9">
				<input id="description" type="text" class="form-control" name="description" maxlength="300">
			</div>
		</div>
		<div class="form-group">
			<label for="basepath" class="col-md-3 control-label">根目录</label>
			<div class="col-md-9">
				<input id="basepath" type="text" class="form-control" name="basepath" maxlength="100">
			</div>
		</div>
		<div class="form-group">
			<label for="status" class="col-md-3 control-label">状态</label>
			<div class="col-md-9">
				<label class="radio-inline">
					<input type="radio" name="status" id="status_1" value="NORMAL">正常
				</label>
				<label class="radio-inline">
					<input type="radio" name="status" id="status_2" value="ABNORMAL">锁定
				</label>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-1 col-md-offset-5">
				<button id="btn_save" type="button" class="btn btn-primary">保存</button>
			</div>
			<div class="col-md-1">
				<button id="btn_cancel" type="button" class="btn btn-warning">取消</button>
			</div>
		</div>
	</form>
</div>
<script>
$(function () {
	$("#btn_save").click(function(){
		$.ajax({
	        type: 'post',
	        url: 'manage/system/create',
	        data: $('#createForm').serialize(),
	        beforeSend: function() {
	            if ($('#title').val() == '') {
	                $('#title').focus();
	                return false;
	            }
	            if ($('#name').val() == '') {
	                $('#name').focus();
	                return false;
	            }
	        },
	        success: function(result) {
				if (result.code != 1) {
					$.confirm({
						theme: 'bootstrap',
						title: false,
						content: result.data.errorMsg,
						buttons: {
							confirm: {text: '确认'}
						}
					});
				} else {
					$.confirm({
						title:false,
						content: '保存成功!',
						buttons: {
							confirm: {
								text:'确认',
								action:function(){HdDialog.close(true);}
							}
						}
					});
				}
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
				$.confirm({
					title: false,
					content: textStatus,
					buttons: { 
						confirm: { text: '确认'}
					}
				});
	        }
	    });
	});
	$("#btn_cancel").click(function(){
		HdDialog.close(false);
	});
});


</script>