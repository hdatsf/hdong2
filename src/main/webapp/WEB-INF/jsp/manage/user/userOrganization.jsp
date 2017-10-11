<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div id="userOrgTreeDialog">
	<form id="userOrgForm" method="post" class="form-horizontal">
		<div class="form-group">
			<ul id="userOrgTree" class="ztree" style="overflow: auto;height:400px"></ul>
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
		var setting = {
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }//勾选不影响父节点
			},
			async : {
				enable : true,
				url : '${basePath}/manage/user/organizationTree/' + userId
			},
			data : {
				simpleData : {
					enable : true,
					rootPId : -1
				}
			}
		};
		$.fn.zTree.init($('#userOrgTreeDialog #userOrgTree'), setting);
		
		$("#userOrgTreeDialog #btn_save").click(function(){
			//获取改变的集合
			var zTree = $.fn.zTree.getZTreeObj("userOrgTree")
			var changeNodes = zTree.getChangeCheckedNodes();
			var changeDatas = [];
			for (var i = 0; i < changeNodes.length; i ++) {
				var changeData = {};
				changeData.id = changeNodes[i].id;
				changeData.checked = changeNodes[i].checked;
				changeDatas.push(changeData);
			}
		    $.ajax({
		        type: 'post',
		        url: '${basePath}/manage/user/organization/' + userId,
		        data: {datas: JSON.stringify(changeDatas)},
		        success: function(result) {
		        	if(result.code == 1){
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
					} else {
						$.hdErrorConfirm(result);
					}
		        }
		    });
		});
		

		$('#userOrgTreeDialog #btn_cancel').click(function(){
			HdDialog.close(false);
		});
	});
</script>