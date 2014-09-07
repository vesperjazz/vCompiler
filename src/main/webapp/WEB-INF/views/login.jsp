<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<link href="<c:url value="/resources/css/signin.css" />" rel="stylesheet">
</head>
<div class="container">
<div class="row">
   <div class="col-md-12">
    <form class="form-signin" name='f' action="<c:url value='j_spring_security_check' />"
		method='POST'>
         <h2 class="form-signin-heading">Please sign in</h2>

        <input class="form-control" placeholder="username"
        type="text"  name='j_username' >
        <input class="form-control" placeholder="Password" type="password" name='j_password'>

        <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">Sign in</button>
		<c:if test="${not empty error}">
			<div class="errorblock">
				Your login attempt was not successful, try again.<br /> Caused :
				${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
			</div>
		</c:if>           
    </form>
    
 
	</div>
</div>	
</div>
</body>
</html>
