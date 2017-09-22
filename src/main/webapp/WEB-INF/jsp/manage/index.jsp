<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<title>盯市系统</title>
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
<!-- jquery confirm -->
<link href="${basePath}/resources/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>
<link href="${basePath}/resources/common/base.css" rel="stylesheet"/>


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="${basePath}/resources/ie/html5shiv.min.js"></script>
  <script src="${basePath}/resources/ie/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<header class="main-header">
			<!-- Logo -->
			<a href="#" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels --> 
				<span class="logo-mini">盯市</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>盯市系统</b></span>
			</a>

			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				<!-- Navbar Right Menu -->
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<!-- Messages: style can be found in dropdown.less-->
						<li class="dropdown messages-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-envelope-o"></i> <span
								class="label label-success">1</span>
						</a>
							<ul class="dropdown-menu">
								<li class="header">你有1条信息</li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<li><a href="#">
												<div class="pull-left">
													<img src="${basePath}/resources/adminlte/img/user2-160x160.jpg" class="img-circle" alt="User Image">
												</div>
												<h4>
													CMRS系统 <small><i class="fa fa-clock-o"></i> 5 mins</small>
												</h4>
												<p>Hi,how are you!</p>
										</a></li>
									</ul>
								</li>
								<li class="footer"><a href="#">查看全部</a></li>
							</ul></li>
						<!-- Notifications: style can be found in dropdown.less -->
						<li class="dropdown notifications-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-bell-o"></i> <span
								class="label label-warning">1</span>
						</a>
							<ul class="dropdown-menu">
								<li class="header">你有1个通知</li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<li><a href="#"> <i class="fa fa-users text-aqua"></i> xxxx领导巡视结果
										</a></li>
									</ul>
								</li>
								<li class="footer"><a href="#">查看全部</a></li>
							</ul></li>
						<!-- Tasks: style can be found in dropdown.less -->
						<li class="dropdown tasks-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-flag-o"></i> <span
								class="label label-danger">1</span>
						</a>
							<ul class="dropdown-menu">
								<li class="header">您有1个待完成任务</li>
								<li>
									<ul class="menu">
										<li><a href="#">
												<h3>
													CMRS设计完成率 <small class="pull-right">20%</small>
												</h3>
												<div class="progress xs">
													<div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0"
														aria-valuemax="100">
														<span class="sr-only">20% 完成</span>
													</div>
												</div>
										</a></li>
									</ul>
								</li>
								<li class="footer"><a href="#">查看全部</a></li>
							</ul></li>
						<!-- User Account: style can be found in dropdown.less -->
						<li class="dropdown user user-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="${basePath}/${upmsUser.avatar}"
								class="user-image" alt="User Image"> <span class="hidden-xs">${upmsUser.realname}</span>
						</a>
							<ul class="dropdown-menu">
								<!-- User image -->
								<li class="user-header"><img src="${basePath}/${upmsUser.avatar}" class="img-circle" alt="User Image">
									<p>
										${upmsUser.realname} <small>${upmsUser.phone}</small><small>${upmsUser.email}</small>
									</p></li>
								<!-- Menu Footer-->
								<li class="user-footer">
									<div class="pull-left">
										<a href="#" class="btn btn-default btn-flat">信息修改</a>
									</div>
									<div class="pull-right">
										<a href="${basePath}/sso/logout" class="btn btn-default btn-flat">退出</a>
									</div>
								</li>
							</ul></li>
					</ul>
				</div>

			</nav>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left image">
						<img src="${basePath}/${upmsUser.avatar}" class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>${upmsUser.realname}</p>
					</div>
				</div>
				<!-- search form -->
				<form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="q" class="form-control" placeholder="Search..."> <span class="input-group-btn">
							<button type="submit" name="search" id="search-btn" class="btn btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>
				<!-- /.search form -->
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu" data-widget="tree">
					<c:forEach var="upmsPermission" items="${upmsPermissions}" varStatus="status">
					<c:if test="${upmsPermission.pid == 0}">
					<li class="treeview">
						<a href="#"><span>${upmsPermission.name}</span></a>
						<ul class="treeview-menu" style="display: none;">
							<c:forEach var="subUpmsPermission" items="${upmsPermissions}">
								<c:if test="${subUpmsPermission.pid == upmsPermission.permissionId}">
									<li>
										<a href="javascript:void(0);" data-url="${subUpmsPermission.uri}"><i class="fa fa-circle-o"></i>${subUpmsPermission.name}</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
					</c:forEach>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content" id="mainDiv" style="padding:1px;">
				<div style="padding:15px;">
					<p><h1>用户权限管理系统</h1></p>
					<p><b>系统简介</b>：本系统基于AdminLTE前台框架、shiro后台权限认证框架、dubbo分布式服务化搭建。通过角色授权和基于用户授权的粗、细粒度权限控制通用平台，并提供单点登录、会话管理和日志管理。接入的系统可自由定义组织、角色、权限、资源等。</p><br/>
					<p><h4>系统功能概述：</h4></p>
					<p><b>系统组织管理</b>：系统和组织增加、删除、修改、查询功能。</p>
					<p><b>用户角色管理</b>：用户和角色增加、删除、修改、查询功能。</p>
					<p><b>资源权限管理</b>：菜单和按钮增加、删除、修改、查询功能。</p>
					<p><b>权限分配管理</b>：提供给角色和用户的权限增加、删除、修改、查询功能。</p>
					<p><b>单点登录(SSO)</b>：提供统一用户单点登录认证、用户鉴权功能。</p>
					<p><b>用户会话管理</b>：提供分布式用户会话管理</p>
					<p><b>操作日志管理</b>：提供记录用户登录、操作等日志。</p><br/>
					<p><h4>对外接口概述：</h4></p>
					<p><b>系统组织数据接口</b>、<b>用户角色数据接口</b>、<b>资源权限数据接口</b>、<b>单点登录(SSO)接口</b>、<b>用户鉴权接口</b></p><br/>
					<p><h4>关于作者</h4></p>
					<p><b>姓　　名</b>：黄栋</p>
					<p><b>电子邮箱</b>：hdatsf@163.com</p>
				</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 1.0.0
			</div>
			<strong>Copyright &copy; 2017-2020.
			</strong> All rights reserved.
		</footer>

	</div>
	<!-- ./wrapper -->

	<!-- jQuery 3 -->
	<script src="${basePath}/resources/jquery/jquery.min.js"></script>
	<script src="${basePath}/resources/jquery-confirm/jquery-confirm.js"></script>
	<script src="${basePath}/resources/jquery-validate/jquery.validate.min.js"></script>
	<script src="${basePath}/resources/jquery-validate/messages_zh.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="${basePath}/resources/bootstrap/js/bootstrap-table.min.js"></script>
	<script src="${basePath}/resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="${basePath}/resources/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${basePath}/resources/adminlte/js/adminlte.min.js"></script>
	<script src="${basePath}/resources/common/base.js"></script>
</body>
</html>
<script>
$(function(){
	HdDict.init();//初始化字典项
    $("ul.treeview-menu li a[data-url]").click(function(){
		loadPage("${basePath}/"+$(this).data("url"));
	    $("ul.treeview-menu li").removeClass("active");
	    $(this).parent().addClass("active");
    });
});

</script>
