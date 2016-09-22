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

<style>
  .form-signin .form-signin-heading,
  .form-signin .checkbox {
    margin-bottom: 10px;
  }
  .form-signin .checkbox {
    font-weight: normal;
    padding-left:20px;
  }
  .form-signin .form-control {
    height: auto;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    padding: 10px;
    font-size: 16px;
  }
  .form-signin input[type="text"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }

  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
</style>

<div class="container">

  <div class="row">

    <div class="col-md-4 col-md-offset-4">
      <div id="message" style="display: none;">
        <div class="alert alert-danger" role="alert"></div>
      </div>

      <!-- 로그인 -->
      <div>
        <form class="form-signin" role="form" id="login">
          <input type="hidden" name="ignore" id="ignore">
          <h2 class="form-signin-heading"><i class="fa fa-sign-in"></i> 로그인</h2>
          <input type="text" class="form-control" placeholder="아이디" id="user_id" name="user_id" required>
          <input type="password" class="form-control" placeholder="비밀번호" id="password" name="password" required>
          <div class="checkbox">
            <label>
              <input type="checkbox" name="remember_me"> Remember me
            </label>
          </div>
          <button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
        </form>
      </div>

    </div>

  </div>

</div>

<script type="text/javascript">
  $('#login').submit(function(event) {
    event.preventDefault();

    $.ajax({
      url : '<c:url value="/member/signin" />',
      data: $('#login input').serialize(),
      type: 'POST',
      dataType : 'json',
      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
      }
    }).done(function(responseData) {

      var error = responseData.error;
      var statusCode = responseData.statusCode;

      if (error) {

        if (statusCode === 403) {
          if (confirm("이미 로그인 사용자가 있습니다. 중복로그인 하시겠습니까?")) {
            $('#ignore').val(true);
            $('#login').submit();
          }
        } else {
          $('#message div').text(responseData.message);
          $('#message').show(0, function() {
            $(this).delay(1000).hide(0);
          });
        }
      } else {
        var redirectUrl = responseData.data.redirectUrl;
        var chain = responseData.data.chain;

        if (chain === true) {
          location.reload();
        } else {
          location.href = redirectUrl;
        }

      }
    });
  });
</script>

</body>

</html>