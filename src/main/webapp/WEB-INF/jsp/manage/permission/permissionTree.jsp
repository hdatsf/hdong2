<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div id="perTreeDialog">
	<ul id="perTree" class="ztree" style="overflow:auto;height:250px"></ul>
</div>
<script>
	$(function() {
		var setting = {
			async : {
				enable : true,
				url : '${basePath}/manage/permission/treeData'
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
		$.fn.zTree.init($('#perTreeDialog #perTree'), setting);
	});
</script>