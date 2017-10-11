<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div id="userRoleDialog">
	<form id="userRoleForm" method="post" class="form-horizontal">
		<div class="form-group" style="height:270px">
			<label for="roleId" class="col-md-4 control-label">拥有角色</label>
			<div class="col-md-8">
				<select id="roleId" name="roleId" multiple="multiple">
					<c:forEach var="upmsRole" items="${upmsRoles}">
						<option value="${upmsRole.id}" ${upmsRole.selected}>${upmsRole.name}</option>
					</c:forEach>
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
		var userId = ${userId};
		$('#userRoleDialog #roleId').multiselect({
			maxHeight: 250,
			enableFiltering: true
		});

		$('#userRoleForm').validate();
		$("#userRoleDialog #btn_save").click(function() {
			if (!$('#userRoleForm').valid()){
				return;
			}
			$.ajax({
				type : 'post',
				url : '${basePath}/manage/user/role/'+userId,
				data : $('#userRoleForm').serialize(),
				success : function(result) {
					if (result.code == 1) {
						$.hdConfirm({
							type : 'blue',
							content : '修改成功！',
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

		$("#userRoleDialog #btn_cancel").click(function() {
			HdDialog.close(false);
		});
	});
</script>