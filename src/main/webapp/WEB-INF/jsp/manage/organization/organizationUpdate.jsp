<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="updateDialog">
  <form id="updateForm" method="post" class="form-horizontal">
    <div class="form-group">
      <label for="name" class="col-md-3 control-label">组织名称</label>
      <div class="col-md-9">
        <input id="name" type="text" class="form-control" name="name" maxlength="20" value="${organization.name}" required>
      </div>
    </div>
    <div class="form-group">
      <label for="description" class="col-md-3 control-label">组织描述</label>
      <div class="col-md-9">
        <textarea class="form-control" id="description" name="description" maxlength="1000" rows="3" required></textarea>
      </div>
    </div>
    <div class="form-group">
      <label for="pid" class="col-md-3 control-label">所属上级</label>
      <div class="col-md-9">
        <input id="pid" type="text" class="form-control" name="pid" maxlength="20" value="${organization.pid}">
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
	document.getElementById("description").value="${organization.description}";
	$("#updateForm").validate();
    $("#btn_save").click(function(){
    	if(!$("#updateForm").valid()) return;
		$.ajax({
			type:'post',
			url:'${basePath}/manage/organization/update/${organization.organizationId}',
			data:$("#updateForm").serialize(),
			success:function(result){
				if(result.code != 1){
					$.hdConfirm({
						content:result.msg,
						buttons:{
							confirm:{text:'确认'}
						}
					});
				} else {
					$.hdConfirm({
						type:'blue',
						content:'修改成功！',
						autoClose:'confirm|3000',
						buttons:{
							confirm : {
								text:'确认',
								action:function(){HdDialog.close(true);}
							}
						}
					});
				}
			}
		});
	});
	
	$('#btn_cancel').click(function(){
		HdDialog.close(false);
	});
})
</script>