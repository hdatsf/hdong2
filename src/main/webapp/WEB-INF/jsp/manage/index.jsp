<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<script> var basePath = '${basePath}';</script>
<!--icon-->
<link href="${basePath}/resources/favicon.ico" media="screen" rel="shortcut icon" type="image/x-icon" />
<!-- Font Awesome -->
<link href="${basePath}/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet" >
<!-- Ionicons -->
<link href="${basePath}/resources/ionicons/css/ionicons.min.css" rel="stylesheet" >
<!-- Theme style -->
<link href="${basePath}/resources/adminlte/css/AdminLTE.min.css" rel="stylesheet" >
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link href="${basePath}/resources/adminlte/css/skins/_all-skins.min.css" rel="stylesheet" >
<!-- Bootstrap 3.3.7 -->
<link href="${basePath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="${basePath}/resources/bootstrap/css/bootstrap-table.min.css" rel="stylesheet" />
<link href="${basePath}/resources/bootstrap/css/bootstrap-multiselect.css" rel="stylesheet" />
<link href="${basePath}/resources/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
<!-- jquery confirm -->
<link href="${basePath}/resources/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>
<link href="${basePath}/resources/common/base.css" rel="stylesheet"/>
<!-- ztree -->
<link href="${basePath}/resources/ztree/css/ztree_bootstrap.css" rel="stylesheet"/>
<!-- img-picket -->
<link href="${basePath}/resources/icon-picker/css/icon-picker.min.css" rel="stylesheet"/>


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="${basePath}/resources/ie/html5shiv.min.js"></script>
  <script src="${basePath}/resources/ie/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<header class="main-header">
			<a href="#" class="logo">
				<span id="system_name" class="logo-mini" style="font-size:12px"></span> <!-- logo for regular state and mobile devices -->
				<span id="system_title" class="logo-lg" style="font-size:15px"><b></b></span>
			</a>
			<nav class="navbar navbar-static-top">
				<a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
					<span class="sr-only"></span>
				</a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<li class="dropdown messages-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-envelope-o"></i>
								<span class="label label-success">1</span>
							</a>
							<ul class="dropdown-menu">
								<li class="header">你有1条信息</li>
								<li>
									<ul class="menu">
										<li>
											<a href="#">
												<div class="pull-left">
													<img src="${basePath}/resources/icon-picker/img/${upmsUser.avatar}" class="img-circle" alt="User Image">
												</div>
												<h4>
													CMRS系统 <small><i class="fa fa-clock-o"></i> 5 mins</small>
												</h4>
												<p>Hi,how are you!</p>
											</a>
										</li>
									</ul>
								</li>
								<li class="footer"><a href="#">查看全部</a></li>
							</ul>
						</li>
						<li class="dropdown notifications-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-bell-o"></i>
								<span class="label label-warning">1</span>
							</a>
							<ul class="dropdown-menu">
								<li class="header">你有1个通知</li>
								<li>
									<ul class="menu">
										<li><a href="#"> <i class="fa fa-users text-aqua"></i>xxxx领导巡视结果</a></li>
									</ul>
								</li>
								<li class="footer"><a href="#">查看全部</a></li>
							</ul>
						</li>
						<li class="dropdown tasks-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-flag-o"></i>
								<span class="label label-danger">1</span>
							</a>
							<ul class="dropdown-menu">
								<li class="header">您有1个待完成任务</li>
								<li>
									<ul class="menu">
										<li>
											<a href="#">
												<h3>
													CMRS设计完成率 <small class="pull-right">20%</small>
												</h3>
												<div class="progress xs">
													<div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
														<span class="sr-only">20% 完成</span>
													</div>
												</div>
											</a>
										</li>
									</ul>
								</li>
								<li class="footer"><a href="#">查看全部</a></li>
							</ul>
						</li>
						<li class="dropdown notifications-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-stack-exchange"></i>
							</a>
							<ul class="dropdown-menu">
								<li class="header">请选择系统切换</li>
								<li>
									<ul class="menu">
										<c:forEach var="system" items="${upmsSystems}" varStatus="status">
											<li>
												<a href="#" class="switch-systems" data-systemTheme="${system.theme}" data-systemid="${system.systemId}" data-systemname="${system.name}" data-systemtitle="${system.title}">
													<i class="glyphicon glyphicon-${system.icon}"></i>${system.title}
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
							</ul>
						</li>
						<li class="dropdown user user-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="${basePath}/resources/icon-picker/img/${upmsUser.avatar}"
								class="user-image" alt="User Image"> <span class="hidden-xs">${upmsUser.realname}</span>
						</a>
							<ul class="dropdown-menu">
								<!-- User image -->
								<li class="user-header"><img src="${basePath}/resources/icon-picker/img/${upmsUser.avatar}" class="img-circle" alt="User Image">
									<p>
										${upmsUser.realname} <small>${upmsUser.phone}</small><small>${upmsUser.email}</small>
									</p></li>
								<!-- Menu Footer-->
								<li class="user-footer">
									<div class="pull-left">
										<a href="#" id="self-info" class="btn btn-default btn-flat">信息修改</a>
									</div>
									<div class="pull-right">
										<a href="#" id="logout" class="btn btn-default btn-flat">退出</a>
									</div>
								</li>
							</ul></li>
					</ul>
				</div>

			</nav>
		</header>
		<aside class="main-sidebar">
			<section class="sidebar">
				<div class="user-panel">
					<div class="pull-left image">
						<img src="${basePath}/resources/icon-picker/img/${upmsUser.avatar}" class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>${upmsUser.realname}</p>
					</div>
				</div>
				<!-- 
				<form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="q" class="form-control" placeholder="搜索..."> <span class="input-group-btn">
							<button type="submit" name="search" id="search-btn" class="btn btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>
				 -->
				<ul class="sidebar-menu" data-widget="tree">
					<c:forEach var="upmsPermission" items="${upmsPermissions}" varStatus="status">
						<c:if test="${upmsPermission.pid == 0}">
						<li class="treeview system_menus system_${upmsPermission.systemId}" style="display:none;">
							<a href="#"><span>${upmsPermission.name}</span></a>
							<ul class="treeview-menu" style="display: none;">
								<c:forEach var="subUpmsPermission" items="${upmsPermissions}">
									<c:if test="${subUpmsPermission.pid == upmsPermission.permissionId}">
										<li><a href="javascript:void(0);" data-url="${subUpmsPermission.uri}"><i class="fa fa-circle-o"></i>${subUpmsPermission.name}</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</li>
						</c:if>
					</c:forEach>
				</ul>
			</section>
		</aside>

		<div class="content-wrapper">
			<section class="content" id="mainDiv" style="padding:1px;">
			</section>
		</div>

		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 1.0.0
			</div>
			<strong>Copyright &copy; 2017-2020.
			</strong> All rights reserved.
		</footer>

	</div>
</body>
</html>
<!-- jQuery 3 -->
<script src="${basePath}/resources/jquery/jquery.min.js"></script>
<script src="${basePath}/resources/jquery/jquery.cookie.js"></script>
<script src="${basePath}/resources/jquery-confirm/jquery-confirm.js"></script>
<script src="${basePath}/resources/jquery-validate/jquery.validate.min.js"></script>
<script src="${basePath}/resources/jquery-validate/messages_zh.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${basePath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="${basePath}/resources/bootstrap/js/bootstrap-table.js"></script>
<script src="${basePath}/resources/bootstrap/js/bootstrap-multiselect.js"></script>
<script src="${basePath}/resources/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/resources/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script src="${basePath}/resources/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<!-- AdminLTE App -->
<script src="${basePath}/resources/adminlte/js/adminlte.min.js"></script>
<script src="${basePath}/resources/common/base.js"></script>
<script src="${basePath}/resources/ztree/js/jquery.ztree.all.min.js"></script>
<!-- img-picket -->
<script src="${basePath}/resources/icon-picker/js/img-picker.js"></script>
<script src="${basePath}/resources/icon-picker/js/icon-picker.js"></script>
<script>
var HD_CONTENT = {};//内容区域属性
var __oldTheme = '';

$(function(){
	HdDict.init();//初始化字典项
    $("ul.treeview-menu li a[data-url]").click(function(){
		loadPage("${basePath}/"+$(this).data("url"));
	    $("ul.treeview-menu li").removeClass("active");
	    $(this).parent().addClass("active");
    });
	//初始化
    HD_CONTENT.fullHeight = $(".content-wrapper").height();
	HD_CONTENT.title = 41;
	HD_CONTENT.padding = 30;
	HD_CONTENT.height = HD_CONTENT.fullHeight - HD_CONTENT.title - HD_CONTENT.padding -4;
	HD_CONTENT.treeHeight = HD_CONTENT.height -10;
	$("#logout").click(function(){
		location.href = '${basePath}/sso/logout?systemname='+$.cookie('hdong-systemname');
	});
	$("#self-info").click(function(){
		$.hdDialog({
			 title:'用户信息修改',
			 columnClass:'col-md-offset-2 col-md-8',
			 content:'url:${basePath}/manage/user/updateSelf',
			 onClose: function(){
			 }
		});
	});
	
	$('.switch-systems').click(function () {
		var systemid = $(this).attr('data-systemid');
		var systemname = $(this).attr('data-systemname');
		var systemtitle = $(this).attr('data-systemtitle');
		var systemTheme = $(this).attr('data-systemTheme');
		$('.system_menus').hide(0, function () {
			$('.system_' + systemid).show();
		});
		$('body').removeClass(__oldTheme).addClass(systemTheme);
		__oldTheme = systemTheme;
		document.title = systemtitle;
		$('#system_name').text(systemname);
		$('#system_title').text(systemtitle);
		$.ajax({
	        type: 'get',
	        url: '${basePath}/manage/systeminfo/'+systemid,
	        success: function(result) {
				$("#mainDiv").html(result);
	        }
	    });
		var expire = new Date();
		expire.setDate(expire.getDate()+30);
		$.cookie('hdong-systemname', systemname,{expires:expire});
	});
	<c:if test = "${useCookieFlag==false}">
	$(".switch-systems[data-systemname='"+"${systemname}"+"']").click();
	</c:if>
	<c:if test = "${useCookieFlag==true}">
	var systemname = $.cookie('hdong-systemname');
	var chooseObj = null;
	if(systemname){
		chooseObj = $(".switch-systems[data-systemname='"+systemname+"']");
	}
	
	if(chooseObj == null || chooseObj.length==0){
		chooseObj = $(".switch-systems")[0];
	}
	chooseObj.click();
	</c:if>
	
});

</script>
