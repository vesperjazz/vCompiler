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
	                <li class="active"><a href="/editor">Editor</a>
	                </li>
	                
	            </ul>
	            <ul class="nav pull-left navbar-nav">
	                <li><a href="./practice">Problem</a>
	                </li>
	                
	            </ul>	            
				<!--<security:authorize ifAnyGranted="ROLE_ADMIN">	   
	            <ul class="nav pull-left navbar-nav">
	                <li><a href="./">test</a>
	                </li>
	                
	            </ul>				
				</security:authorize>-->         
	            <ul class="nav pull-right navbar-nav">
	                <li><a href="<c:url value="/j_spring_security_logout" />">Log out</a>
	                </li>
	            </ul>
	        </div>
	        <!--/.navbar-collapse -->
	    </div>
	    <!-- /.navbar -->
	<!--</div>     -->
       <ul class="nav nav-pills nav-justified">
          <li class="active"><a href="./editor">Code Editor</a></li>
          <li><a  onclick="vhdSimWave()">SimWave</a></li>
		</ul>
		

<div class="container">

<div class="row">
   <div class="col-md-12" align=center>
	<h1>VHDL Code Editor</h1>
  </div>
</div><!-- end of row-->
<div class="row"><!-- Start of row-->
  <div class="col-md-6">
	<pre id="editor">
<c:out value="${VHDLmessage}" />
	</pre>
                <script src="<c:url value="/resources/editor/ace.js" />"></script>
                <script src="<c:url value="/resources/editor/ext-language_tools.js" />"></script>        
	<script>
		// trigger extension
		ace.require("ace/ext/language_tools");
		var editor = ace.edit("editor");
		editor.getSession().setMode("ace/mode/vhdl");
		editor.setTheme("ace/theme/tomorrow");
		editor.getSession().setTabSize(4);
		editor.getSession().setUseWrapMode(true);

		editor.setOptions({
		enableBasicAutocompletion: true,
		enableSnippets: true,
		minLines: 20,
		maxLines: 35
		});
	</script>	
 </div>
  <div class="col-md-6">
	<pre id="editorTB">
<c:out value="${TBmessage}" />
	</pre>
	<script>
    // trigger extension
    ace.require("ace/ext/language_tools");
    var editorTB = ace.edit("editorTB");
	editorTB.getSession().setMode("ace/mode/vhdl");
    editorTB.setTheme("ace/theme/tomorrow");
	editorTB.getSession().setTabSize(4);
	editorTB.getSession().setUseWrapMode(true);

	editorTB.setOptions({
    enableBasicAutocompletion: true,
    enableSnippets: true,
	minLines: 20,
	maxLines: 35
	});
</script>
  </div>
</div><!-- end of row-->
<br>

<div class="row"><!-- Start of row-->

	<div class="col-md-12" align=center>       
   <!--   <form:form action="" method="POST" commandName="cmd">
             <input type="hidden" value= "123" id="userId" name="userId" />
             <input type="submit" value="Create test bench" id="btnSearch" onclick="vhdSubmit()">

    </form:form>-->            


                
<form class="form-inline">
  <button type="button" class="btn btn-primary" onclick="vhdSubmitTB()">Create TestBench</button>
    <div class="form-group">
    <input  class="form-control" id="problem" placeholder="Question Problem">
  </div>
  <button type="button" class="btn btn-primary" onclick="vhdSubmit()">Submit</button>
</form>
</div>
	
</div><!-- end of row-->
 
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script>
$(function() {
$( "#accordion" ).accordion({
collapsible: true
});
});
</script>
<br>
	<div class="row"><!-- Start of row-->
		<div id="accordion">
		<h3>Console</h3>
			<div>
			<p>Add information here</p>
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

            <script>

            function vhdSubmit()
            {
                var form = document.createElement("form");
                form.setAttribute("method", "post");
                form.setAttribute("action", "submit");
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", "code");
                        hiddenField.setAttribute("value", editor.getValue());
                        form.appendChild(hiddenField);
                        
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", "problem");
                        problem = document.getElementById('problem').value;
                        hiddenField.setAttribute("value", problem);
                        form.appendChild(hiddenField);
                document.body.appendChild(form);
                form.submit();
            }
            function vhdSubmitTB() {
               // method = method || "post"; // Set method to post by default if not specified.

                // The rest of this code assumes you are not using a library.
                // It can be made less wordy if you use one.
                var form = document.createElement("form");
                form.setAttribute("method", "post");
                form.setAttribute("action", "");

                //for(var key in params) {
                  //  if(params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", "code");
                        hiddenField.setAttribute("value", editor.getValue());

                        form.appendChild(hiddenField);
                    // }
                //}

                document.body.appendChild(form);
                form.submit();
            }         
            
            function vhdSimWave() {
                // method = method || "post"; // Set method to post by default if not specified.

                 // The rest of this code assumes you are not using a library.
                 // It can be made less wordy if you use one.
                 var form = document.createElement("form");
                 form.setAttribute("method", "post");
                 form.setAttribute("action", "./SimWave");

                 //for(var key in params) {
                   //  if(params.hasOwnProperty(key)) {
                         var hiddenField = document.createElement("input");
                         hiddenField.setAttribute("type", "hidden");
                         hiddenField.setAttribute("name", "code");
                         hiddenField.setAttribute("value", editor.getValue());
                         form.appendChild(hiddenField);
                         var hiddenField = document.createElement("input");
                         hiddenField.setAttribute("type", "hidden");
                         hiddenField.setAttribute("name", "testBench");
                         hiddenField.setAttribute("value", editorTB.getValue());
                         form.appendChild(hiddenField);                         
                     // }
                 //}

                 document.body.appendChild(form);
                 form.submit();
             }              
            </script>
                
                
	</body>
         
</html>
