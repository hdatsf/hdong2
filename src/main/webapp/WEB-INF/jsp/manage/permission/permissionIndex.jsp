<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div class="panel panel-default" style="margin-bottom:0px">
	<div class="panel-heading">权限管理</div>
	<div class="panel-body" style="padding-left: 0px; padding-right: 15px">
		<div class="row">
		  	<div class="col-md-3">
		  		<ul id="query_permissTree" class="ztree" style="overflow:auto"></ul>
		  	</div>
		  	<div class="col-md-9">
				<form id="query_form" class="form-horizontal">
					<div class="form-group form-group-sm">
						<label class="control-label col-sm-1" for="query_name">权限名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="query_name">
						</div>
						<label class="control-label col-sm-1" for="query_pname">所属上级</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="query_pname" readonly>
							<input type="hidden" id="query_pid">
							<input type="hidden" id="query_systemId">
						</div>
					</div>
				</form>
				<div id="toolbar" class="btn-toolbar pull-right" style="margin-bottom: 3px">
					<button id="btn_query" type="button" class="btn btn-primary btn-sm">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
					</button>
					<shiro:hasPermission name="upms:permission:create">
						<button id="btn_create" type="button" class="btn btn-primary btn-sm">
							<span class="glyphicon glyphicon-plus" aria-hidden="hidden"></span>新增
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:permission:update">
						<button id="btn_update" type="button" class="btn btn-primary btn-sm">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:permission:delete">
						<button id="btn_delete" type="button" class="btn btn-primary btn-sm">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
						</button>
					</shiro:hasPermission>
				</div>
				<table id="tb_permissions"></table>
			</div>
		</div>
	</div>
</div>

<script>
  $(function(){
	 $('#tb_permissions').bootstrapTable({
	    url:'',//请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        toolbarAlign:"right",
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        height: 'auto',                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "permissionId",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        queryParams: function (params) {
            return {
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                name:$("#query_name").val(),
                pid:$("#query_pid").val(),
                systemId:$("#query_systemId").val()
            };
        },
        columns: [
			{field: 'ck', checkbox: true},
			{field: 'permissionId', title: '权限编号', sortable: true, align: 'center'},
			{field: 'type', title: '权限类型', sortable: true, align: 'center',formatter:function(name){return HdDict.getDictDesc('UPMS','PERMISSION_TYPE',name);}},
			{field: 'name', title: '权限名称'},
			{field: 'permissionValue', title: '权限值'}
		],
		onLoadError : function(status, result){$.hdErrorConfirm(result.responseText);}
	 });
	 
	 //初始化页面上面的按钮事件
	 $("#toolbar #btn_query").click(function(){
		 $('#tb_permissions').bootstrapTable('refresh',{url:'${basePath}/manage/permission/list'});
	 });
	 $("#toolbar #btn_create").click(function(){
		$.hdDialog({
			title:'新增权限信息',
			columnClass:'col-md-offset-2 col-md-8',
			content:'url:${basePath}/manage/permission/create?pid='+$('#query_pid').val()+'&pname='+encodeURI($('#query_pname').val())+'&systemId='+$("#query_systemId").val(),
			onClose:function(){
				if(HdDialog.getValue()){
					$.fn.zTree.init($('#query_permissTree'), setting);
				}
			}
		}); 
	 });
	 $("#toolbar #btn_update").click(function(){
		 var rows = $('#tb_permissions').bootstrapTable('getSelections');
		 if(rows.length != 1){
			 $.hdConfirm({
				 content:'请选择一条记录！',
				 autoClose:'cancel|3000',
				 backgroundDismiss:true,
				 buttons:{
					 cancel:{
						 text:'取消'
					 }
				 }
			 });
		 } else {
			 $.hdDialog({
				 title:'权限信息修改',
				 columnClass:'col-md-offset-2 col-md-8',
				 content:'url:${basePath}/manage/permission/update/' + rows[0].permissionId,
				 onClose: function(){
					 if(HdDialog.getValue()){
						 $.fn.zTree.init($('#query_permissTree'), setting);
					 }
				 }
			 });
		 }
	 });
	 
	 $("#toolbar #btn_delete").click(function(){
		 var rows = $('#tb_permissions').bootstrapTable('getSelections');
		 if(rows.length == 0){
			 $.hdConfirm({
				 title:false,
				 content:'请至少选择一条记录！',
				 autoClose:'cancel|3000',
				 backgroundDismiss:true,
				 buttons:{
					 cancel:{
						 text:'取消'
					 }
				 }
			 });
		 }else{
			 $.hdConfirm({
				 type:'red',
				 content:'确认删除所选权限吗？',
				 buttons:{
					 confirm:{
						 text:'确认',
						 btnClass:'btn btn-danger',
						 action:function(){
							 HdConfirm.close();
							 var ids = new Array();
							 for (var i in rows){
								 ids.push(rows[i].permissionId);
							 }
							 $.ajax({
								 type:'get',
								 url:'${basePath}/manage/permission/delete/' + ids.join('-'),
								 success: function(result){
									 if(result.code == 1){
										 $.hdConfirm({
											 content:'删除成功!',
											 autoClose:'confirm|3000',
											 buttons:{
												 confirm:{
													 text:'确认',
													 action:function(){
														 HdConfirm.close();
														 $.fn.zTree.init($('#query_permissTree'), setting);
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
					 cancel:{
						 text:'取消',
						 btnClass:'btn btn-warning'
					 }
				 }
			 });
		 }
	 });
	 
	 $("#query_permissTree").height(HD_CONTENT.treeHeight);
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
			onClick: function(e, treeId, node){
				 if($("#query_pid").val() == node.id){
					 $("#query_pname").val('');
					 $("#query_pid").val('');
					 $("#query_systemId").val('');
					 $.fn.zTree.getZTreeObj(treeId).cancelSelectedNode();
				 }else{
					 $("#query_pname").val(node.name);
					 $("#query_systemId").val(node.systemId);
					 if(node.type == "SYSTEM"){
						 $("#query_pid").val(0);
					 }else{
						 $("#query_pid").val(node.id);
					 }
				 }
				 $("#toolbar #btn_query").click();
			 },
			 onAsyncSuccess:function(e, treeId, node){
				 if($("#query_pid").val() == ""){
					 $("#toolbar #btn_query").click();
				 }else{
					 var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
					 var selectNode = zTreeObj.getNodeByTId($("#query_pid").val());
					 zTreeObj.selectNode(selectNode);
					 $("#toolbar #btn_query").click();
				 }
			 }
		}
	};
	$.fn.zTree.init($('#query_permissTree'), setting);
});
</script>