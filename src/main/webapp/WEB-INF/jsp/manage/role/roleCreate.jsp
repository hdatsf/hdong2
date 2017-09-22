<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="createDialog">
  <form id="createForm" method="post" class="form-horizontal">
    <div class="form-group">
      <label for="name" class="col-md-3 control-label">角色名称</label>
      <div class="col-md-9">
        <input id="name" type="text" class="form-control" name="name" maxlength="20" placeholder="请输入角色名称" required>
      </div>
    </div>
    <div class="form-group">
      <label for="title" class="col-md-3 control-label">角色标题</label>
      <div class="col-md-9">
        <input id="title" type="text" class="form-control" name="title" maxlength="20" placeholder="请输入角色标题" required>
      </div>
    </div>
    <div class="form-group">
      <label for="description" class="col-md-3 control-label">角色描述</label>
      <div class="col-md-9">
        <textarea class="form-control" id="description" name="description" maxlength="1000" placeholder="请输入角色描述" rows="3" required></textarea>
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
$(function(){
	$('#createForm').validate();
	$("#btn_save").click(function(){
		if(!$('#createForm').valid()) return;
		$.ajax({
			type:'post',
			url:'${basePath}/manage/role/create',
			data:$('#createForm').serialize(),
			success:function(result){
				debugger;
				if(result.code != 1){
					$.hdConfirm({
						content:result.msg,
						buttons:{
							confirm:{text:'确认'}
						}
					});
				} else {
					$.hdConfirm({
						content:'保存成功！',
						autoClose: 'confirm|3000',
						buttons:{
							confirm:{
								text:'确认',
								action:function(){HdDialog.close(true);}
							}
						}
					});
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				$.confirm({
					title:false,
					content:textStatus,
					buttons:{
						confirm:{text:'确认'}
					}
				});
			}
		});
	});
	
	$("#btn_cancel").click(function(){
		debugger;
		HdDialog.close(false);
	});
});


</script>