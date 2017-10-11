<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div id="orgTreeDialog">
	<ul id="orgTree" class="ztree" style="overflow:auto;height:350px"></ul>
</div>
<script>
	$(function() {
		var setting = {
			async : {
				enable : true,
				url : '${basePath}/manage/organization/treeData'
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : function(e, treeId, node) {
					var retValue ={};
					if(node.type != "BUTTON"){
						HdDialog.close(node);
					}
				},
				onAsyncSuccess:function(e, treeId, node){
					var nodeId = '${id}';
					if (nodeId != "") {
						var zTreeObj =  $.fn.zTree.getZTreeObj(treeId);
						var node = zTreeObj.getNodeByTId(nodeId);
						zTreeObj.selectNode(node);
					}
				}
			}
		};
		$.fn.zTree.init($('#orgTreeDialog #orgTree'), setting);
	});
</script>