<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="updateDialog">
	<form id="updateForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="theme" class="col-md-3 control-label">主题色</label>
			<div class="col-md-9">
				<select id="theme" class="form-control" name="theme" required>
					<option value="skin-black">白暗</option>
					<option value="skin-black-light">白亮</option>
					<option value="skin-blue">蓝暗</option>
					<option value="skin-blue-light">蓝亮</option>
					<option value="skin-green">绿暗</option>
					<option value="skin-green-light">绿亮</option>
					<option value="skin-red">红暗</option>
					<option value="skin-red-light">红亮</option>
					<option value="skin-yellow">黄暗</option>
					<option value="skin-yellow-light">黄亮</option>
					<option value="skin-purple">紫暗</option>
					<option value="skin-purple-light">紫亮</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="icon" class="col-md-3 control-label">图标</label>
			<div class="col-md-7">
				<input id="icon" type="text" class="form-control" name="icon" maxlength="20" readonly required value="${system.icon}">
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-md-3 control-label">名称</label>
			<div class="col-md-9">
				<input id="name" type="text" class="form-control" name="name" maxlength="10" required value="${system.name}">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-md-3 control-label">标题</label>
			<div class="col-md-9">
				<input id="title" type="text" class="form-control" name="title" maxlength="20" required value="${system.title}">
			</div>
		</div>
		<div class="form-group">
			<label for="basepath" class="col-md-3 control-label">根目录</label>
			<div class="col-md-9">
				<input id="basepath" type="text" class="form-control" name="basepath" maxlength="100" value="${system.basepath}">
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-md-3 control-label">描述</label>
			<div class="col-md-9">
				<textarea id="description" type="text" class="form-control" name="description" maxlength="2000" rows="8">${system.description}</textarea>
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
	$("#updateDialog #icon").iconPicker();
	$('#updateDialog #theme').multiselect();
	$('#updateDialog #theme').multiselect('select',"${system.theme}");
	$("#updateForm").validate();
	$("#btn_save").click(function(){
		if(!$("#updateForm").valid())return;
		$.ajax({
	        type: 'post',
	        url: '${basePath}/manage/system/update/${system.systemId}',
	        data: $('#updateForm').serialize(),
	        success: function(result) {
				if(result.code == 1){
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
	        	}else{
	        		$.hdErrorConfirm(result);
	        	}
	        }
	    });
	});
	$("#btn_cancel").click(function(){
		HdDialog.close(false);
	});
})
</script>