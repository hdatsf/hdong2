<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div id="updateDialog">
	<form id="updateForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="theme" class="col-md-3 control-label">主题色</label>
			<div class="col-md-9">
				<input id="theme" type="color" class="form-control" name="theme" maxlength="50" value="${system.theme}">
			</div>
		</div>
		<div class="form-group">
			<label for="icon" class="col-md-3 control-label">图标</label>
			<div class="col-md-9">
				<input id="icon" type="text" class="form-control" name="icon" maxlength="20" value="${system.icon}">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-md-3 control-label">标题</label>
			<div class="col-md-9">
				<input id="title" type="text" class="form-control" name="title" maxlength="20" value="${system.title}"  required="true">
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-md-3 control-label">名称</label>
			<div class="col-md-9">
				<input id="name" type="text" class="form-control" name="name" maxlength="20" value="${system.name}" required="true">
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-md-3 control-label">描述</label>
			<div class="col-md-9">
				<input id="description" type="text" class="form-control" name="description" maxlength="300" value="${system.description}">
			</div>
		</div>
		<div class="form-group">
			<label for="basepath" class="col-md-3 control-label">根目录</label>
			<div class="col-md-9">
				<input id="basepath" type="text" class="form-control" name="basepath" maxlength="100" value="${system.basepath}">
			</div>
		</div>
		<div class="form-group">
			<label for="status" class="col-md-3 control-label">状态</label>
			<div class="col-md-9">
				<label class="radio-inline">
					<input type="radio" name="status" id="status_1" value="NORMAL" <c:if test="${system.status=='NORMAL'}">checked</c:if>>正常
				</label>
				<label class="radio-inline">
					<input type="radio" name="status" id="status_2" value="ABNORMAL" <c:if test="${system.status=='ABNORMAL'}">checked</c:if>>锁定
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
	        url: '${basePath}/manage/system/update/${system.systemId}',
	        data: $('#updateForm').serialize(),
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
					$.hdConfirm({
						content: result.data.errorMsg,
						buttons: {
							confirm: {text: '确认'}
						}
					});
				} else {
					$.hdConfirm({
						type:'blue',
						content: '修改成功!',
						autoClose: 'confirm|3000',
						buttons: {
							confirm: {
								text:'确认',
								action:function(){HdDialog.close(true);}
							}
						}
					});
				}
	        }
	    });
	});
	$("#btn_cancel").click(function(){
		HdDialog.close(false);
	});
})
</script>