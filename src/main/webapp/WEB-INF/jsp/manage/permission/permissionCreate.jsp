<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div id="createDialog">
	<form id="createForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="pname" class="col-md-3 control-label">所属上级</label>
			<div class="col-md-9">
				<div class="input-group">
					<input id="pname" type="text" class="form-control" name="pname" value="${pname}" required readonly> 
					<a id="btn_choose" type="button" role="button" class="btn btn-primary input-group-addon">选择</a>
				</div>
				<input id="pid" type="hidden" name="pid" value="${pid}">
				<input id="systemId" type="hidden" name="systemId" value="${systemId}">
			</div>
		</div>
		<div class="form-group">
			<label for="type" class="col-md-3 control-label">权限类型</label>
			<div class="col-md-9">
				<select id="type" name="type" class="form-control">
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-md-3 control-label">权限名称</label>
			<div class="col-md-9">
				<input id="name" type="text" class="form-control" name="name" maxlength="20" required>
			</div>
		</div>
		<div class="form-group">
			<label for="permissionValue" class="col-md-3 control-label">权限值</label>
			<div class="col-md-9">
				<input id="permissionValue" type="text" class="form-control" name="permissionValue" maxlength="50">
			</div>
		</div>
		<div class="form-group">
			<label for="uri" class="col-md-3 control-label">路径</label>
			<div class="col-md-9">
				<input id="uri" type="text" class="form-control" name="uri" maxlength="100">
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
		HdDict.initSelect("UPMS","PERMISSION_TYPE",$("#createDialog #type"),"CATALOG");
		$('#createDialog #type').change(function(){
			if($(this).val() == "CATALOG"){
				$("#createDialog #uri").parent().parent().hide();
				$("#createDialog #permissionValue").parent().parent().hide();
			}else if($(this).val() == "MENU"){
				$("#createDialog #uri").parent().parent().show();
				$("#createDialog #permissionValue").parent().parent().show();
			}else if($(this).val() == "BUTTON"){
				$("#createDialog #uri").parent().parent().hide();
				$("#createDialog #permissionValue").parent().parent().show();
			}
		});
		$("#createDialog #type").change();
		$('#createDialog #type').multiselect();
		
		$('#createForm').validate();
		$("#createDialog #btn_save").click(function() {
			if (!$('#createForm').valid()){
				return;
			}
			if($(this).val() == "CATALOG"){
				$("#createDialog #uri").val('');
				$("#createDialog #permissionValue").val('');
			}else if($(this).val() == "BUTTON"){
				$("#createDialog #uri").val('');
			}
			$.ajax({
				type : 'post',
				url : '${basePath}/manage/permission/create',
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
				title:'选择上级权限',
				columnClass:'col-md-offset-4 col-md-4',
				content:'url:${basePath}/manage/permission/tree?id='+$("#createDialog #pid").val(),
				onClose:function(){
					var retValue = HdDialog.getValue();
					if(retValue){
						if(retValue.type=="SYSTEM"){
							$("#createDialog #pid").val(0);
						}else{
							$("#createDialog #pid").val(retValue.id);
						}
						$("#createDialog #pname").val(retValue.name);
						$("#createDialog #systemId").val(retValue.systemId);
					}
				}
			}); 
		});
	});
</script>