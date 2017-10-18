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
<link rel="shortcut icon" type="image/x-icon" href="${basePath}/resources/common/favicon.ico" media="screen" />
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="${basePath}/resources/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="${basePath}/resources/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="${basePath}/resources/ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="${basePath}/resources/adminlte/css/AdminLTE.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="${basePath}/resources/adminlte/plugins/iCheck/square/blue.css">
<!-- jquery confirm -->
<link href="${basePath}/resources/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="${basePath}/resources/ie/html5shiv.min.js"></script>
  <script src="${basePath}/resources/ie/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<b>盯市系统</b> 登录
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg"></p>
			<form action="" method="post" id="signInForm">
				<div class="form-group has-feedback">
					<input type="text" class="form-control" placeholder="请输入登录邮箱/登录名" id="username" maxlength="20" required> <span
						class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" class="form-control" placeholder="请输入密码" id="password" maxlength="20" required> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="checkbox icheck">
							<label> <input type="checkbox" id="rememberMe"> 记住用户
							</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-6">
						<div class="checkbox pull-right">
							<a href="#">忘记密码</a> <span>&nbsp;/&nbsp;</span> <a href="#" id="register" class="text-center">注册</a>
						</div>
					</div>
					<!-- /.col -->
				</div>
				<div class="row">
					<div class="col-xs-12">
						<button type="button" id="signIn" class="btn btn-danger btn-block btn-flat">登录</button>
					</div>
				</div>
			</form>


		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->
	<script src="${basePath}/resources/jquery/jquery.min.js"></script>
	<script src="${basePath}/resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="${basePath}/resources/adminlte/plugins/iCheck/icheck.min.js"></script>
	<script src="${basePath}/resources/jquery-validate/jquery.validate.min.js"></script>
	<script src="${basePath}/resources/jquery-validate/messages_zh.js"></script>
	<script src="${basePath}/resources/jquery-confirm/jquery-confirm.js"></script>
	<script src="${basePath}/resources/common/base.js"></script>
</body>
</html>
<script>
    $(function() {
    	var forceLogout = '${forceLogout}';
    	if(forceLogout == '1'){
    		$(".login-box-msg").text('您已经被系统管理员强退！')
    	}
    	$("#signInForm").validate();
		$('input').iCheck({
		    checkboxClass : 'icheckbox_square-blue',
		    radioClass : 'iradio_square-blue',
		    increaseArea : '20%'
		});
		$('#signIn').click(function() {
			if(!$("#signInForm").valid())return;
		    $.ajax({
				url : '${basePath}/sso/login',
				type : 'POST',
				data : {
				    username : $('#username').val(),
				    password : $('#password').val(),
				    rememberMe : $('#rememberMe').is(':checked')
				},
				success : function(result) {
				    if (result.code == 1) {
						location.href = '${basePath}/manage/index';
				    } else {
				    	$.hdConfirm({
							content: result.msg,
							buttons: {
								confirm: {text: '确认'}
							}
						});
				    }
				},
				error : function(error) {
				    console.log(error);
				}
		    });
		});
		$("#register").click(function(){
			$.hdConfirm({
				content: "不开放注册，请联系管理原开通用户！",
				buttons: {
					confirm: {text: '确认'}
				}
			});
		});
    });
</script>
