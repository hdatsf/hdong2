<%@ page contentType="text/html; charset=utf-8"%>
<div id="createDialog">
  <form id="createForm" method="post" class="form-horizontal">
    <div class="form-group">
      <label for="name" class="col-md-3 control-label">组织名称</label>
      <div class="col-md-9">
        <input id="name" type="text" class="form-control" name="name" maxlength="20" placeholder="请输入组织名称">
      </div>
    </div>
    <div class="form-group">
      <label for="description" class="col-md-3 control-label">组织描述</label>
      <div class="col-md-9">
        <textarea class="form-control" id="description" name="description" maxlength="1000" placeholder="请输入组织描述" rows="3"></textarea>
        <!-- <input id="description" type="text" class="form-control" name="description" maxlength="1000" placeholder="请输入组织描述"> -->
      </div>
    </div>
    <div class="form-group">
      <label for="pid" class="col-md-3 control-label">所属上级</label>
      <div class="col-md-9">
        <input id="pid" type="text" class="form-control" name="pid" maxlength="20" placeholder="请输入所属上级">
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
	$("#btn_save").click(function(){
		$.ajax({
			type:'post',
			url:'manage/organization/create',
			data:$('#createForm').serialize(),
			beforeSend:function(){
				if($('#name').val() == ''){
					$('#name').focus();
					return false;
				}
				if($('#description').val() == ''){
					$('#description').focus();
					return false;
				}
			},
			success:function(result){
				if(result.code != 1){
					$.confirm({
						theme:'bootstrap',
						title:false,
						content:result.data.errorMsg,
						buttons:{
							confirm:{text:'确认'}
						}
					});
				} else {
					$.confrim({
						title:false,
						content:'保存成功！',
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