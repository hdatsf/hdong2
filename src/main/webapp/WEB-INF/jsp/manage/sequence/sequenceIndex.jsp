<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div class="panel panel-default">
	<div class="panel-heading">序列号管理</div>
	<div class="panel-body" style="padding-left: 0px; padding-right: 15px">
		<form id="resetForm" method="post" class="form-inline">
			<div class="form-group col-md-offset-3">
				<input id="key" type="text" class="form-control" name="key" placeholder="键" required>
			</div>
			<div class="form-group">
				<input id="value" type="text" class="form-control" name="value" placeholder="值" required>
			</div>
			<a id="btn_reset" type="button" role="button" class="btn btn-primary">设置</a>
			<a id="btn_resetAll" type="button" role="button" class="btn btn-primary">全量初始化</a>
		</form>
	</div>
</div>

<script>
	$(function() {
		$('#resetForm').validate();
		$('#btn_resetAll').click(function(){
			$.hdConfirm({
				type : 'red',
				animationSpeed : 100,
				title : false,
				content : '确认全量初始化序列号吗？',
				buttons : {
					confirm : {
						text : '确认',
						btnClass : 'btn btn-danger',
						action : function() {
							HdConfirm.close();
							$.ajax({
								type : 'get',
								url : '${basePath}/manage/sequence/resetAll',
								success : function(result) {
									if (result.code == 1) {
										$.hdConfirm({
											title : false,
											content : '初始化成功!',
											buttons : {
												confirm : {
													text : '确认',
													action : function() {
														HdConfirm.close();
													}
												}
											}
										});
									} else {
										$.hdErrorConfirm(result);
									}
								}
							});
						}
					},
					cancel : {
						text : '取消',
						btnClass : 'btn btn-warning'
					}
				}
			});
		});
		$('#btn_reset').click(function() {
			if (!$('#resetForm').valid()){
				return;
			}
			$.hdConfirm({
				type : 'red',
				animationSpeed : 100,
				title : false,
				content : '确认设置该序列号吗？',
				buttons : {
					confirm : {
						text : '确认',
						btnClass : 'btn btn-danger',
						action : function() {
							HdConfirm.close();
							$.ajax({
								type : 'get',
								url : '${basePath}/manage/sequence/reset/'+$("#key").val()+'/'+$("#value").val(),
								success : function(result) {
									if (result.code == 1) {
										$.hdConfirm({
											title : false,
											content : '设置成功!',
											buttons : {
												confirm : {
													text : '确认',
													action : function() {
														HdConfirm.close();
													}
												}
											}
										});
									} else {
										$.hdErrorConfirm(result);
									}
								}
							});
						}
					},
					cancel : {
						text : '取消',
						btnClass : 'btn btn-warning'
					}
				}
			});
		});
	});
</script>