<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.contextPath}/">
<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="utf-8">
<title>hdong | Log in</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!--icon-->
<link rel="shortcut icon" type="image/x-icon" href="resources/common/favicon.ico" media="screen" />
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="resources/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="resources/ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="resources/adminlte/css/AdminLTE.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="resources/adminlte/plugins/iCheck/square/blue.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<b>hdong</b> Login
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg">测试用户 test/test</p>
			<form action="" method="post" id="signInForm">
				<div class="form-group has-feedback">
					<input type="text" class="form-control" placeholder="请输入登录邮箱/登录名" id="username"> <span
						class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" class="form-control" placeholder="请输入密码" id="password"> <span
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
							<a href="#">忘记密码</a> <span>&nbsp;/&nbsp;</span> <a href="#" class="text-center">注册</a>
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

	<!-- jQuery 3 -->
	<script src="resources/jquery/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<!-- iCheck -->
	<script src="resources/adminlte/plugins/iCheck/icheck.min.js"></script>
</body>
</html>
<script>
    $(function() {
	$('input').iCheck({
	    checkboxClass : 'icheckbox_square-blue',
	    radioClass : 'iradio_square-blue',
	    increaseArea : '20%'
	});
	$('#signIn').click(function() {
	    $.ajax({
		url : 'sso/login',
		type : 'POST',
		data : {
		    username : $('#username').val(),
		    password : $('#password').val(),
		    rememberMe : $('#rememberMe').is(':checked')
		},
		success : function(json) {
		    if (json.code == 1) {
				location.href = 'manage/index';
		    } else {
				alert(json.data);
				if (10101 == json.code) {
				    $('#username').focus();
				}
				if (10102 == json.code) {
				    $('#password').focus();
				}
		    }
		},
		error : function(error) {
		    console.log(error);
		}
	    });
	});
    });
</script>
