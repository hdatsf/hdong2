<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div id="createDialog">
	<form id="createForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="username" class="col-md-3 control-label">账号</label>
			<div class="col-md-9">
				<input id="username" type="text" class="form-control" name="username" maxlength="20" required>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-md-3 control-label">密码</label>
			<div class="col-md-9">
				<input id="password" type="password" class="form-control" name="password" maxlength="32" required>
			</div>
		</div>
		<div class="form-group">
			<label for="realname" class="col-md-3 control-label">姓名</label>
			<div class="col-md-9">
				<input id="realname" type="text" class="form-control" name="realname" maxlength="20" required>
			</div>
		</div>
		<div class="form-group">
			<label for="avatar" class="col-md-3 control-label">头像</label>
			<div class="col-md-7">
				<input id="avatar" type="text" class="form-control icon-picker" name="avatar" required readonly>
			</div>
		</div>
		<div class="form-group">
			<label for="organizationName" class="col-md-3 control-label">所属机构</label>
			<div class="col-md-7">
				<div class="input-group">
					<input id="organizationName" type="text" class="form-control" name="organizationName" value="${organizationName}" readonly> 
					<a id="btn_choose" type="button" role="button" class="btn btn-primary input-group-addon">选择</a>
				</div>
				<input id="organizationId" type="hidden" name="organizationId" value="${organizationId}">
			</div>
		</div>
		<div class="form-group">
			<label for="phone" class="col-md-3 control-label">电话</label>
			<div class="col-md-9">
				<input id="phone" type="text" class="form-control" name="phone" maxlength="20">
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="col-md-3 control-label">邮箱</label>
			<div class="col-md-9">
				<input id="email" type="text" class="form-control" name="email" maxlength="50">
			</div>
		</div>
		<div class="form-group">
			<label for="sex" class="col-md-3 control-label">性别</label>
			<div class="col-md-9">
				<select id="sex" name="sex" class="form-control">
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="locked" class="col-md-3 control-label">状态</label>
			<div class="col-md-9">
				<select id="locked" name="locked" class="form-control">
				</select>
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
		$("#avatar").imgPicker(null,"${basePath}/resources/icon-picker/img/");
		
		HdDict.initSelect("UPMS","USER_SEX",$("#createDialog #sex"),"MALE");
		HdDict.initSelect("UPMS","USER_LOCKED",$("#createDialog #locked"),"NORMAL");
		$('#createDialog #sex').multiselect();
		$('#createDialog #locked').multiselect();
		
		$('#createForm').validate();
		$("#createDialog #btn_save").click(function() {
			if (!$('#createForm').valid()){
				return;
			}
			$.ajax({
				type : 'post',
				url : '${basePath}/manage/user/create',
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
				content:'url:${basePath}/manage/organization/tree?id='+$("#createDialog #organizationId").val(),
				onClose:function(){
					var retValue = HdDialog.getValue();
					if(retValue){
						$("#createDialog #organizationId").val(retValue.id);
						$("#createDialog #organizationName").val(retValue.name);
					}
				}
			}); 
		});
	});
</script>