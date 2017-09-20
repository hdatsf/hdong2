<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div class="panel panel-default">
	<div class="panel-heading">系统管理</div>
	<div class="panel-body" style="padding-left:0px;padding-right:15px">
		<form id="formSearch" class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="control-label col-sm-1" for="txt_system_name">系统名称</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="txt_system_name">
				</div>
			</div>
		</form>
		<div id="toolbar" class="btn-toolbar pull-right" style="margin-bottom:3px">
			<button id="btn_query" type="button" class="btn btn-primary btn-sm">
	            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
	        </button>
	        <shiro:hasPermission name="upms:system:create">
	        <button id="btn_create" type="button" class="btn btn-primary btn-sm">
	            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
	        </button>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="upms:system:update">
	        <button id="btn_update" type="button" class="btn btn-primary btn-sm">
	            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
	        </button>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="upms:system:delete">
	        <button id="btn_delete" type="button" class="btn btn-primary btn-sm">
	            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
	        </button>
	        </shiro:hasPermission>
	    </div>
	    <table id="tb_departments"></table>
	</div>
</div>


<script>
$(function () {
	$('#tb_departments').bootstrapTable({
        url: '${basePath}/manage/system/list',         //请求后台的URL（*）
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
        pageSize: 20,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        height: '500',                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "systemId",               //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        queryParams: function (params) {
            return {
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                systemName: $("#txt_system_name").val()
            };
        },
        columns: [
			{field: 'ck', checkbox: true},
			{field: 'systemId', title: '编号', sortable: true, align: 'center'},
			{field: 'icon', title: '图标', sortable: true, align: 'center'},
	    	{field: 'title', title: '系统标题'},
			{field: 'name', title: '系统名称'},
			{field: 'basepath', title: '根目录'},
			{field: 'status', title: '状态', sortable: true, align: 'center',formatter:function(name){return HdDict.getDictDesc('UPMS','SYSTEM_STATUS',name);}}
		]
    });
	
	//初始化页面上面的按钮事件
    $("#toolbar #btn_query").click(function(){
        $('#tb_departments').bootstrapTable('refresh');
    });
	$("#toolbar #btn_create").click(function(){
	    $.hdDialog({
			title: '新增系统信息',
			columnClass:'col-md-offset-2 col-md-8',//配合col-md-offset-x居中
			//containerFluid:true,//最大化
			content: 'url:${basePath}/manage/system/create',
			onClose: function(){
			    if(HdDialog.getValue()){
			    	$('#tb_departments').bootstrapTable('refresh');
			    }
			}
		});
    });
	$("#toolbar #btn_update").click(function(){
	    var rows = $('#tb_departments').bootstrapTable('getSelections');
		if (rows.length != 1) {
			$.hdConfirm({
				content: '请选择一条记录！',
				autoClose: 'cancel|3000',
				buttons: {
					cancel: {
						text: '取消'
					}
				}
			});
		} else {
		    $.hdDialog({
				title: '系统信息修改',
				columnClass:'col-md-offset-2 col-md-8',//配合col-md-offset-x居中
				//containerFluid:true,//最大化
				content: 'url:${basePath}/manage/system/update/' + rows[0].systemId,
				onClose: function(){
				    if(HdDialog.getValue()){
				    	$('#tb_departments').bootstrapTable('refresh');
				    }
				}
			});
		}
    });
	$("#btn_delete").click(function(){
		var rows = $('#tb_departments').bootstrapTable('getSelections');
		if (rows.length == 0) {
			$.hdConfirm({
				title: false,
				content: '请至少选择一条记录！',
				autoClose: 'cancel|3000',
				backgroundDismiss: true,
				buttons: {
					cancel: {
						text: '取消'
					}
				}
			});
		} else {
			$.hdConfirm({
				type: 'red',
				animationSpeed: 100,
				title: false,
				content: '确认删除所选系统吗？',
				buttons: {
					confirm: {
						text: '确认',
						btnClass: 'btn btn-danger',
						action: function () {
							HdConfirm.close();
							var ids = new Array();
							for (var i in rows) {
								ids.push(rows[i].systemId);
							}
							$.ajax({
								type: 'get',
								url: '${basePath}/manage/system/delete/' + ids.join("-"),
								success: function(result) {
									if (result.code != 1) {
										$.hdConfirm({
											theme: 'red',
											title: false,
											content: result.data.errorMsg,
											buttons: {
												confirm: {text: '确认'}
											}
										});
									} else {
										$.hdConfirm({
											title:false,
											content: '删除成功!',
											buttons: {
												confirm: {
													text:'确认',
													action:function(){
														HdConfirm.close();
														$('#tb_departments').bootstrapTable('refresh');
													}
												}
											}
										});
										
									}
								}
							});
						}
					},
					cancel: {
						text: '取消',
						btnClass: 'btn btn-warning'
					}
				}
			});
		}
	});
});
</script>