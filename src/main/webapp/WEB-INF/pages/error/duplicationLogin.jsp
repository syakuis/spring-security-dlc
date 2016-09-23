<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Spring security</title>

	<script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>

	<link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
	<!--[if lt IE 9]>
	<script src="<c:url value="/resources/bower_components/html5shiv/dist/html5shiv.min.js" />"></script>
	<script src="<c:url value="/resources/bower_components/respond/dest/respond.min.js" />"></script>
	<![endif]-->
	<link rel="stylesheet" href="<c:url value="/resources/bower_components/font-awesome/css/font-awesome.min.css" />">
</head>
<div class="container" style="width: 600px;">
	<div class="alert alert-danger" style="margin-top:100px; padding: 50px;">
		<div class="row">
			<div class="col-xs-2 text-center">
				<i class="fa fa-5x fa-ban"></i>
			</div>
			<div class="col-xs-10">
				<h4>[ 오류발생 ]</h4>
				<p>새로운 사용자가 로그인하였거나, 로그아웃되었습니다.</p>
			</div>
		</div>

		<div class="text-center">
			<a class="btn btn-default" href="<c:url value="/member/login" />" role="button">로그인</a>
		</div>
	</div>
</div>
</body>
</html>