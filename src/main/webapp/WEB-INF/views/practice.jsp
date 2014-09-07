<%-- 
    Document   : editorVHDL
    Created on : Jan 15, 2014, 4:37:08 PM
    Author     : Yee Tang
--%>

<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>vCompiler - VHDL Code Editor</title>
	    <!-- Favicon -->
	    <link rel="shortcut icon" href="<c:url value="/resources/img/logo.ico"/>">		
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
		<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
		
		<style type="text/css" media="screen">
 

		.ace_editor.fullScreen {
			height: auto;
			width: auto;
			border: 0;
			margin: 0;
			position: fixed !important;
			top: 0;
			bottom: 0;
			left: 0;
			right: 0;
			z-index: 10;
			background: white;
		}
		
		.btn-file {
		    position: relative;
		    overflow: hidden;
		}
		.btn-file input[type=file] {
		    position: absolute;
		    top: 0;
		    right: 0;
		    min-width: 100%;
		    min-height: 100%;
		    font-size: 999px;
		    text-align: right;
		    filter: alpha(opacity=0);
		    opacity: 0;
		    background: blue;
		    cursor: inherit;
		    display: block;
		}		
		</style>                
           
	</head> 
	<body>
	<!--  <div class="container">-->
	    <div class="navbar navbar-inverse navbar-default">
	      <!--  <div class="navbar-header"><a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	                    <span class="glyphicon glyphicon-bar"></span>
	                    <span class="glyphicon glyphicon-bar"></span>
	                    <span class="glyphicon glyphicon-bar"></span>
	                    <span class="glyphicon glyphicon-bar"></span>
	                    <span class="glyphicon glyphicon-bar"></span>
	                    <span class="glyphicon glyphicon-bar"></span>
	                </a>
	        </div> -->
	        <!-- Responsive Navbar -->	
	 	<a class="navbar-brand" href="./">vCompiler</a>
	
	        <!-- Responsive
	        Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse.
	        -->
	        <div class="navbar-collapse collapse navbar-responsive-collapse">
	            <ul class="nav pull-left navbar-nav">
	                <li><a href="./editor">Editor</a>
	                </li>
	                
	            </ul>
	            <ul class="nav pull-left navbar-nav">
	                <li class="active"><a href="./practice">Problem</a>
	                </li>
	                
	            </ul>	            
				<!--<security:authorize ifAnyGranted="ROLE_ADMIN">	   
	            <ul class="nav pull-left navbar-nav">
	                <li><a href="./">test</a>
	                </li>
	                
	            </ul>			
				</security:authorize>     -->	    
	            <ul class="nav pull-right navbar-nav">
	                <li><a href="<c:url value="/j_spring_security_logout" />">Log out</a>
	                </li>
	            </ul>
	        </div>
	        <!--/.navbar-collapse -->
	    </div>
	    <!-- /.navbar -->
	<!--</div>     -->
     <!--  <ul class="nav nav-pills nav-justified">
          <li class="active"><a href="./editor">Code Editor</a></li>
          <li><a  onclick="vhdSimWave()">SimWave</a></li>
		</ul>-->
		

<div class="container">

<div class="row">
   <div class="col-md-12" align=center>
   			<security:authorize ifAnyGranted="ROLE_USER">
            <h3>
                Problem Questions
                
            </h3>   			
            </security:authorize>     			
   			<security:authorize ifAnyGranted="ROLE_ADMIN">
            <h3>
                PRECAUTION: INVALID CHARACTER WILL RESULT IN WRONG ANSWER!!!!<br>
                EXAMPLE: Please use ( " ) instead of ( “ ) <br>
                **Usually happen when you copy paste from MS Word.
                
            </h3>
            <hr>
            
	        <form id="frm" action="GA_VHDL_Servlet" method="post" enctype="multipart/form-data">
	        <div class="table-responsive">
	            <table class = "table table-bordered">
	                <tr>
	                    <td>Problem Number</td> 
	                    <td><input type="text" id="txtProb" name="txtProb"></input></td>
	                </tr>
	                <tr>
	                    <td>Clock duration per clock cycle (Sequential)</td> 
	                    <td><input type="text" id="txtClk" name="txtClk"></input></td>
	                </tr>
	                <tr>
	                    <td>Input File</td> 
	                    <td>
								<div class="input-group">
											<span class="input-group-btn">
												<span class="btn btn-primary btn-file">
													Browse&hellip; <input type="file" multiple>
												</span>
											</span>
											<input type="text" class="form-control" readonly>
										</div>
					     </td>
	                     <!--	<input type="file" id="fileInput" name="fileInput"></input></td>-->
	                    <!--  <td><input type="file" id="fileInput" name="fileInput"></input></td>-->
	                </tr>
	                <tr>
	                    <td>VHD File</td> 
	                    
							<td>	<div class="input-group">
											<span class="input-group-btn">
												<span class="btn btn-primary btn-file">
													Browse&hellip; <input type="file" multiple>
												</span>
											</span>
											<input type="text" class="form-control" readonly>
										</div>	                    
	                    </td>
	                    <!--  <input type="file" id="fileVhd" name="fileVhd"></input></td>-->
	                </tr>
	                <tr>
	                    <td></td>
	                    <td>
	                     <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">Upload</button>
	                    <!--<input type="submit" value="Upload"/>    --></td>
	                </tr>
	            </table>
	            </div>
			<hr>
	        </form>    
	                 
            </security:authorize>  
            <br>
            <div class="table-responsive" align ="center">
             <table class = "table table-bordered"align ="center">
                <tr>
                    <td>
                        Question ID
                    </td>
                    <td align ="center">
                        Description
                    </td>
                    <security:authorize ifAnyGranted="ROLE_ADMIN">	 
                    <td>
                        Answer
                    </td>
                    </security:authorize>  
                </tr>
                <tr>
                    <td>
                        2001
                    </td>
                    <td align ="center">
                        <img src="<c:url value="/resources/VHDL/Images/2001.PNG" />">
                    </td>
                    <security:authorize ifAnyGranted="ROLE_ADMIN">	 
                    <td>
                        <a href="<c:url value="/resources/VHDL/Answers/2001.vhd" />">link</a>
                    </td>
                    </security:authorize>  
                </tr>
                <tr>
                    <td>
                        2002
                    </td>
                    <td align ="center">
                        <img src="<c:url value="/resources/VHDL/Images/2002.PNG" />">
                    </td>
                    <security:authorize ifAnyGranted="ROLE_ADMIN">	 
                    <td>
                        <a href="<c:url value="/resources/VHDL/Answers/2002.vhd" />">link</a>
                    </td>
                    </security:authorize>  
                </tr>
                <tr>
                    <td>
                        2003
                    </td>
                    <td align ="center">
                        <img src="<c:url value="/resources/VHDL/Images/2003.PNG" />">
                    </td>
                    <security:authorize ifAnyGranted="ROLE_ADMIN">	 
                    <td>
                        <a href="<c:url value="/resources/VHDL/Answers/2003.vhd" />">link</a>
                    </td>
                    </security:authorize>  
                </tr>
                <tr>
                    <td>
                        2004
                    </td>
                    <td align ="center">
                        <img src="<c:url value="/resources/VHDL/Images/2004.PNG" />">
                    </td>
                    
                    <security:authorize ifAnyGranted="ROLE_ADMIN">	 
                    <td>
                        <a href="<c:url value="/resources/VHDL/Answers/2004.vhd" />">link</a>
                    </td>
                    </security:authorize>  
                </tr>
                <tr>
                    <td>
                        2005
                    </td>
                    <td align ="center">
                        <img src="<c:url value="/resources/VHDL/Images/2005.PNG" />">
                    </td>
                    <security:authorize ifAnyGranted="ROLE_ADMIN">	 
                    <td>
                        <a href="<c:url value="/resources/VHDL/Answers/2005.vhd" />">link</a>
                    </td>
                    </security:authorize>  
                </tr>
            </table>   
            </div>
</div>
</div>
	<hr>
    <footer>
        <p class="pull-right"><a class="" href="#">Back to top</a>
        </p>
 	<p>© Copyright 2014 vCompiler. All Rights Reserved.</p>
    </footer>
</div>
	
	</body>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>	
       <script>
		$(document)
			.on('change', '.btn-file :file', function() {
				var input = $(this),
				numFiles = input.get(0).files ? input.get(0).files.length : 1,
				label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
				input.trigger('fileselect', [numFiles, label]);
		});
		
		$(document).ready( function() {
			$('.btn-file :file').on('fileselect', function(event, numFiles, label) {
				
				var input = $(this).parents('.input-group').find(':text'),
					log = numFiles > 1 ? numFiles + ' files selected' : label;
				
				if( input.length ) {
					input.val(log);
				} else {
					if( log ) alert(log);
				}
				
			});
		});		
	</script>

</html>
