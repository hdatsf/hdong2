<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div id="createDialog">
	<form id="createForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="pname" class="col-md-3 control-label">所属上级</label>
			<div class="col-md-9">
				<div class="input-group">
					<input id="pname" type="text" class="form-control" name="pname" value="${pname}" readonly> 
					<a id="btn_choose" type="button" role="button" class="btn btn-primary input-group-addon">选择</a>
				</div>
				<input id="pid" type="hidden" name="pid" value="${pid}">
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-md-3 control-label">组织名称</label>
			<div class="col-md-9">
				<input id="name" type="text" class="form-control" name="name" maxlength="20" required>
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-md-3 control-label">组织描述</label>
			<div class="col-md-9">
				<textarea class="form-control" id="description" name="description" maxlength="1000" rows="3" required></textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-2 col-md-offset-4">
				<button id="btn_save" type="button" class="btn btn-primary">保存</button>
			</div>
			<div class="col-md-2">
				<button id="btn_cancel" type="button" class="btn btn-warning">取消</button>
			</div>
		</div>
	</form>
</div>

<script>
	$(function() {
		$('#createForm').validate();
		$("#createDialog #btn_save").click(function() {
			if (!$('#createForm').valid())
				return;
			if ($("#createDialog #pname").val()==""){
				$("#createDialog #pid").val(0);
			}
			$.ajax({
				type : 'post',
				url : '${basePath}/manage/organization/create',
				data : $('#createForm').serialize(),
				success : function(result) {
					if (result.code == 1) {
						$.hdConfirm({
							type : 'blue',
							content : '保存成功！',
							autoClose : 'confirm|3000',
							buttons : {
								confirm : {
									text : '确认',
									action : function() {
										HdDialog.close(true);
									}
								}
							}
						});
					} else {
						$.hdErrorConfirm(result);
					}
				}
			});
		});

		$("#createDialog #btn_cancel").click(function() {
			HdDialog.close(false);
		});
		
		$("#createDialog #btn_choose").click(function() {
			$.hdDialog({
				title:'选择组织',
				columnClass:'col-md-offset-4 col-md-4',
				content:'url:${basePath}/manage/organization/tree?id='+$("#createDialog #pid").val(),
				onClose:function(){
					var retValue = HdDialog.getValue();
					if(retValue){
						$("#createDialog #pid").val(retValue.id);
						$("#createDialog #pname").val(retValue.name);
					}
				}
			}); 
		});
	});
</script>