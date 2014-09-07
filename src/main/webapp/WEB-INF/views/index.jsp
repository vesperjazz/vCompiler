<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

	<head>
	    <!-- Favicon -->
	    <link rel="shortcut icon" href="<c:url value="/resources/img/logo.ico"/>">			
		<meta charset="utf-8">
		<title>VHDL Code Editor</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
		<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources/font-awesome/css/font-awesome.css" />">		
    <link rel="stylesheet" href="<c:url value="/resources/css/carousel.css" />">	    
	</head> 
	<body>


<body>

    <nav class="navbar navbar-inverse navbar-default">
    <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
                <a class="navbar-brand logo-nav" href="">
                    <img src="<c:url value="/resources/img/logo.png" />">
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#about">About</a>
                    </li>
                    <li><a href="#services">Services</a>
                    </li>
                    <li><a href="#contact">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
            </div>
    </nav>

    <div class="container">

        <div class="row">
            <div class="col-lg-8">
				<img class="img-responsive img-rounded" src="http://placehold.it/900x350">
            </div>
            <div class="col-lg-4">        
				<div class="col-md-12">
 				<c:choose>
        		<c:when  test="${empty username}">		
				    <form class="form-signin" name='f' action="<c:url value='j_spring_security_check' />"
						method='POST'>
				         <h2 class="form-signin-heading">Please sign in</h2>
						<div class="input-group">
						  <span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
						<input class="form-control" placeholder="username"
										        type="text"  name='j_username' >
						</div>
						<div class="input-group">
						  <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
						   <input class="form-control" placeholder="Password" type="password" name='j_password'>
						</div>
				       <!-- <input class="form-control" placeholder="username"
				        type="text"  name='j_username' >
				        <input class="form-control" placeholder="Password" type="password" name='j_password'> --> 
				
				        <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">Sign in</button>
						<c:if test="${not empty error}">
							<div class="errorblock">
								Your login attempt was not successful, try again.<br /> Caused :
								${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
							</div>
						</c:if>           
				    </form>
				     </c:when>
				     <c:otherwise>
			      <div class="row">
			      <div class="well text-center">
			        	<br>
			         	<h2>Hello, <c:out value="${username}" /> !</h2> 
			         	<br>
			         	<p>Click here to access into vCompiler</p>
			         	<br>
			         	<a href ="./editor"><button class="btn btn-lg btn-primary btn-block">Enter</button></a>
			        	</div>	
			        	</div>			    
				      </c:otherwise>
				</c:choose>
				 
					</div>                
            </div>
        </div>
		</div>
        <hr>
	<div class="container marketing">
        <div class="row">
            <div class="col-lg-12">
                <div class="well text-center">
                	vCompiler is a web-based VHDL compiler that can build, compile and simulate your test benches result without the need in installing any software.
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-4">
            	<i class="fa fa-code fa-5x"></i>
                <h2>Code Anywhere</h2>
                <p>Write the code you want, when and where you want. vCompiler is an online IDE that adapts perfectly to all device resolutions like mobiles, tablets and desktops.</p>
               <!--   <a class="btn btn-default" href="#">More Info</a>-->
            </div>
            <div class="col-lg-4">
            	<i class="fa fa-cloud-download fa-5x"></i>
                <h2>No installation</h2>
                <p>It can be frustrating to have to install volumes of software just to write a little bit of code.</p>
                <!--<a class="btn btn-default" href="#">More Info</a>-->
            </div>
            <div class="col-lg-4">
            	<i class="fa fa-random fa-5x"></i>
                <h2>Preview waveform</h2>
                <p>View testbench waveform in vCompiler have the ability to be run directly in your web browser</p>
               <!-- <a class="btn btn-default" href="#">More Info</a>-->
            </div>
        </div>

  

    </div>
	<div class="container">
		<hr>
	    <footer>
	        <p class="pull-right"><a class="" href="#">Back to top</a>
	        </p>
	 	<p>© Copyright 2014 vCompiler. All Rights Reserved.</p>
	    </footer>
	</div>  
	</body>
	    <!-- External JS -->
	<!-- jQuery library -->
	<!--<script src="<c:url value="/resources/js/jquery-2.js"/>"></script>-->
	<!-- Bootstrap -->
	<!--  <script src="<c:url value="/resources/EPWave/bootstrap.js"/>"></script>-->
	
	<!-- Underscore -->
	<!--<script src="<c:url value="/resources/js/underscore-min.js"/>"></script>-->
	
	<!-- Main JS -->
	<!--<script src="<c:url value="/resources/js/utils.js"/>"></script>-->
	
	<!-- jQuery UI, needed for jQuery UI Layout and multiselect for EDA Playground, and File Upload for EPWave -->
	<!--<script src="<c:url value="/resources/EPWave/jquery-ui.js"/>"></script>-->
</html>
