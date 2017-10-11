<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div class="panel panel-default" style="margin-bottom:0px">
	<div class="panel-heading">用户管理</div>
	<div class="panel-body" style="padding-left: 0px; padding-right: 15px">
		<div class="row">
		  	<div class="col-md-3">
		  		<ul id="query_orgTree" class="ztree" style="overflow:auto"></ul>
		  	</div>
		  	<div class="col-md-9">
				<form id="query_form" class="form-horizontal">
					<div class="form-group form-group-sm">
						<label class="control-label col-sm-1" for="query_name">姓名</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="query_name">
						</div>
						<label class="control-label col-sm-1" for="query_organizationName">所属部门</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="query_organizationName" readonly>
							<input type="hidden" id="query_organizationId">
						</div>
					</div>
				</form>
				<div id="toolbar" class="btn-toolbar pull-right" style="margin-bottom: 3px">
					<button id="btn_query" type="button" class="btn btn-primary btn-sm">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
					</button>
					<shiro:hasPermission name="upms:user:create">
						<button id="btn_create" type="button" class="btn btn-primary btn-sm">
							<span class="glyphicon glyphicon-plus" aria-hidden="hidden"></span>新增
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:user:update">
						<button id="btn_update" type="button" class="btn btn-primary btn-sm">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:user:delete">
						<button id="btn_delete" type="button" class="btn btn-primary btn-sm">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:user:organization">
						<button id="btn_updateOrg" type="button" class="btn btn-primary btn-sm">
							用户组织
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:user:permission">
						<button id="btn_addPermission" type="button" class="btn btn-primary btn-sm">
							用户加权限
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:user:permission">
						<button id="btn_subPermission" type="button" class="btn btn-primary btn-sm">
							用户减权限
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="upms:user:role">
						<button id="btn_updateRole" type="button" class="btn btn-primary btn-sm">
							用户角色
						</button>
					</shiro:hasPermission>
					
				</div>
				<table id="tb_users"></table>
			</div>
		</div>
	</div>
</div>

<script>
  $(function(){
	 $('#tb_users').bootstrapTable({
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
        uniqueId: "userId",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        queryParams: function (params) {
            return {
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                name:$("#query_name").val(),
                organizationId:$("#query_organizationId").val(),
            };
        },
        columns: [
        	{field: 'ck', checkbox: true},
			{field: 'userId', title: '编号', sortable: true, align: 'center'},
            {field: 'username', title: '帐号'},
			{field: 'realname', title: '姓名'},
			{field: 'avatar', title: '头像', align: 'center'},
			{field: 'phone', title: '电话'},
			{field: 'email', title: '邮箱'},
			{field: 'sex', title: '性别', formatter:function(name){return HdDict.getDictDesc('UPMS','USER_SEX',name);}},
			{field: 'locked', title: '状态', sortable: true, align: 'center', formatter:function(name){return HdDict.getDictDesc('UPMS','USER_LOCKED',name);}}
		],
		onLoadError : function(status, result){$.hdErrorConfirm(result.responseText);}
	 });
	 
	 //初始化页面上面的按钮事件
	 $("#toolbar #btn_query").click(function(){
		 $('#tb_users').bootstrapTable('refresh',{url:'${basePath}/manage/user/list'});
	 });
	 $("#toolbar #btn_create").click(function(){
		$.hdDialog({
			title:'新增用户信息',
			columnClass:'col-md-offset-2 col-md-8',
			content:'url:${basePath}/manage/user/create?organizationId='+$('#query_organizationId').val()+'&organizationName='+encodeURI($('#query_organizationName').val()),
			onClose:function(){
				if(HdDialog.getValue()){
					$.fn.zTree.init($('#query_orgTree'), setting);
				}
			}
		}); 
	 });
	 $("#toolbar #btn_update").click(function(){
		 var rows = $('#tb_users').bootstrapTable('getSelections');
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
				 title:'用户信息修改',
				 columnClass:'col-md-offset-2 col-md-8',
				 content:'url:${basePath}/manage/user/update/' + rows[0].userId,
				 onClose: function(){
					 if(HdDialog.getValue()){
						 $.fn.zTree.init($('#query_orgTree'), setting);
					 }
				 }
			 });
		 }
	 });
	 
	 $("#toolbar #btn_updateOrg").click(function(){
		 var rows = $('#tb_users').bootstrapTable('getSelections');
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
				 title:'用户组织修改',
				 columnClass:'col-md-offset-2 col-md-8',
				 content:'url:${basePath}/manage/user/organization/' + rows[0].userId,
				 onClose: function(){
					 if(HdDialog.getValue()){
						 $.fn.zTree.init($('#query_orgTree'), setting);
					 }
				 }
			 });
		 }
	 });
	 
	 $("#toolbar #btn_addPermission,#btn_subPermission").click(function(){
		 var rows = $('#tb_users').bootstrapTable('getSelections');
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
			 var myTitle = this.id=="btn_addPermission"?"用户加权限":"用户减权限";
			 var myType = this.id=="btn_addPermission"?"ADD":"SUB";
			 $.hdDialog({
				 title: myTitle,
				 columnClass:'col-md-offset-2 col-md-8',
				 content:'url:${basePath}/manage/user/permission/'+ myType + '/' + rows[0].userId
			 });
		 }
	 });
	 
	 $("#toolbar #btn_updateRole").click(function(){
		 var rows = $('#tb_users').bootstrapTable('getSelections');
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
				 title:'用户角色修改',
				 columnClass:'col-md-offset-4 col-md-4',
				 content:'url:${basePath}/manage/user/role/' + rows[0].userId
			 });
		 }
	 });
	 
	 $("#toolbar #btn_delete").click(function(){
		 var rows = $('#tb_users').bootstrapTable('getSelections');
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
				 content:'确认删除所选用户吗？',
				 buttons:{
					 confirm:{
						 text:'确认',
						 btnClass:'btn btn-danger',
						 action:function(){
							 HdConfirm.close();
							 var ids = new Array();
							 for (var i in rows){
								 ids.push(rows[i].userId);
							 }
							 $.ajax({
								 type:'get',
								 url:'${basePath}/manage/user/delete/' + ids.join('-'),
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
														 $.fn.zTree.init($('#query_orgTree'), setting);
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
	 $("#query_orgTree").height(HD_CONTENT.treeHeight);
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
			onClick: function(e, treeId, node){
				 if($("#query_organizationId").val() == node.id){
					 $("#query_organizationName").val('');
					 $("#query_organizationId").val('');
					 $.fn.zTree.getZTreeObj(treeId).cancelSelectedNode();
				 }else{
					 $("#query_organizationName").val(node.name);
					 $("#query_organizationId").val(node.id);
				 }
				 $("#toolbar #btn_query").click();
			 },
			 onAsyncSuccess:function(e, treeId, node){
				 if($("#query_organizationId").val() == ""){
					 $("#toolbar #btn_query").click();
				 }else{
					 var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
					 var selectNode = zTreeObj.getNodeByTId($("#query_organizationId").val());
					 zTreeObj.selectNode(selectNode);
					 $("#toolbar #btn_query").click();
				 }
			 }
		}
	};
	$.fn.zTree.init($('#query_orgTree'), setting);
});
</script>