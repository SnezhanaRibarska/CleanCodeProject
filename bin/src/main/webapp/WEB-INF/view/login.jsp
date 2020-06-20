<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Login</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
<!-- CSS here -->
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/owl.carousel.min.css">
<link rel="stylesheet" href="/css/magnific-popup.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/themify-icons.css">
<link rel="stylesheet" href="/css/nice-select.css">
<link rel="stylesheet" href="/css/flaticon.css">
<link rel="stylesheet" href="/css/animate.css">
<link rel="stylesheet" href="/css/slicknav.css">
<link rel="stylesheet" href="/css/style.css">
<!-- <link rel="stylesheet" href="css/responsive.css"> -->

</head>
<body background="/img/banner/1.png">
	<div class="col-lg-12 col-md-12" align="center">
		<form:form modelAttribute="user" action="home" method="post">
			<div>
				<form:label path="userName">Username:</form:label>
				<form:input path="userName" name="userName" />
			</div>
			<div>
				<form:label path="password">Password:</form:label>
				<form:password path="password" name="password" />
			</div>
			<div>
				<form:button name="login" class="genric-btn info circle">Login</form:button>
			</div>
		</form:form>
		
		If you don't have an account, please register <a class="link" href="register">here</a>
		
		<div align="center" style="font-style: italic; color: red;">${message}</div>
	</div>

	<!-- JS here -->
	<script src="/js/vendor/modernizr-3.5.0.min.js"></script>
	<script src="/js/vendor/jquery-1.12.4.min.js"></script>
	<script src="/js/popper.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/owl.carousel.min.js"></script>
	<script src="/js/isotope.pkgd.min.js"></script>
	<script src="/js/ajax-form.js"></script>
	<script src="/js/waypoints.min.js"></script>
	<script src="/js/jquery.counterup.min.js"></script>
	<script src="/js/imagesloaded.pkgd.min.js"></script>
	<script src="/js/scrollIt.js"></script>
	<script src="/js/jquery.scrollUp.min.js"></script>
	<script src="/js/wow.min.js"></script>
	<script src="/js/nice-select.min.js"></script>
	<script src="/js/jquery.slicknav.min.js"></script>
	<script src="/js/jquery.magnific-popup.min.js"></script>
	<script src="/js/plugins.js"></script>
	<script src="/js/main.js"></script>
</body>
</html>