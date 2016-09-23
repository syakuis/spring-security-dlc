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

<body>

<div class="container">

  <h2><i class="fa fa-inbox" aria-hidden="true"></i> 현재 방문자 목록</h2>
  <hr>
  <table class="table">
    <thead>
      <tr>
        <th>Username</th>
        <th>SESSION ID</th>
        <th>Last Request</th>
        <th>Delete</th>
      </tr>
    </thead>
    <thead>
      <c:forEach var="visitor" items="${visitors}">
        <tr>
          <td>${visitor.username}</td>
          <td>${visitor.sessionId}</td>
          <td>${visitor.lastRequest}</td>
          <td><button class="btn btn-default btn-xs" type="button" data-session-id="${visitor.sessionId}"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
        </tr>
      </c:forEach>
    </thead>
  </table>

  <div class="text-center">
    <a class="btn btn-default" href="<c:url value="/member/mypage" />" role="button">마이페이지</a>
    <a class="btn btn-default" href="<c:url value="/member/signout" />" role="button">로그아웃</a>
  </div>

</div>

<script type="application/javascript">
  $("[data-session-id]").click(function() {
    var sessionId = $(this).data('session-id');
    console.log(sessionId);

    $.ajax({
      url : '<c:url value="/member/visitor" />',
      data: '{ "sessionId" : "' + sessionId + '" }',
      type: 'DELETE',
      processData: false,
      contentType: 'application/json'
    }).done(function(responseData) {
      location.reload();
    });

  });
</script>

</body>

</html>