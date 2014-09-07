/*! Copyright (C) 2013 Victor Lyuboslavsky, All Rights Reserved. */
;

/**function isNumber(a)
{
	return !isNaN(a-0)&&a!==null&&a!==""&&a!==false
	
}**/
/**function isPositive(a){
	return isNumber(a)&&a>0
	
}
function htmlEncode(a){
	return String(a).replace(/&/g,"&amp;").replace(/"/g,"&quot;").replace(/'/g,"&#39;").replace(/</g,"&lt;").replace(/>/g,"&gt;")
	
}
function htmlDecode(a){
	return String(a).replace(/&quot;/g,'"').replace(/&#39;/g,"'").replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&amp;/g,"&")
	
}**/
/**function clearRunButtons(){
	$("#codeRunning").val(0);$("#stopButton").hide();$("#runButton").show();
	$("#runButton").blur()
}
function setRunButtons(){
	$("#codeRunning").val(1);
	$("#stopButton").show();
	$("#runButton").hide()
	
}
function hideCodeButtons(){
	$(".runButton").hide();
	$(".saveButton").hide();
	$(".copyButton").hide()
	
}
function showCodeButtons(){
	clearRunButtons();
	$(".runButton").show();
	$(".saveButton").show();
	$(".copyButton").show()
	
}
function showUserButtons(a){
	if(a)
	{
		$(".userButton").show()
		
	}
	else
	{
		$(".loginButton").show()
		
	}
	
}**/
/**
function getSelectedSimulator()
{
	return $("#simulator option:selected").val()
	
}
function isPandaSelected(a)
{
	return(a==="301")
	
}
function isIcarusVerilogSelected(a){
	return(a==="7"||a==="8"||a==="103")
	
}

function isIcarusVerilog09Selected(a){
	return(a==="8"||a==="103")
	
}
function isVcsSelected(a)
{return(a==="2"||a==="5"||a==="6")
	
}
function isQuestaSelected(a)
{
	return(a==="1"||a==="11"||a==="13")
	
}
function isModelsimSelected(a){
	return(a==="701")
	
}
function isIncisiveSelected(a)
{
	return(a==="12")
	
}
function isSimulatorSupportLibraries(a)
{
	return(a!=="9"&&a!=="10"&&a!=="201")
	
}
function isPythonSelected(a)
{
	return(a==="401")
	
}
function isSynthesisSelected(a)
{
	return(a==="501"||a==="601")
	
}
function isYosysSelected(a)
{
	return(a==="601")
	
}
function isVtrSelected(a)
{
	return(a==="501")
	
}
function isMigenSelected(a)
{
	return(a==="601")
	
}
function isMyHDLSelected(a)
{
	return(a==="501")
	
}
function selectPython()
{
	$("#simulator").val("401")
	
}
function getSelectedSimulatorType(a)
{
	switch(a){
	case"701":
		return"ModelSim";
	
	case"11":case"13":
			
		return"Questa";
			
	case"2":case"5":case"6":
		return"VCS";
		
	case"12":
		return"INCISIV";
		
	case"201":
		return"UXE";
		
	case"8":case"103":case"7":
			
			
		return"Icarus";
		
	case"9":
		
		return"Cver";
		
	case"10":
		return"Veriwell";
	case"301":
		return"Panda";
		
	case"401":
		
		return"Python";
		
	case"501":
		return"VTR";
		
	case"601":
		return"Yosys";
		default:return""
		
	}
	
}**/
/**function createString(b,a)
{
	return Array.prototype.join.call({length:(b||-1)+1},a||"x")
	
}**/
/**function checkBrowserSupport(b)
{
	var c=/MSIE \d/.test(navigator.userAgent),a=c&&(document.documentMode===null||document.documentMode<9);
	if(a)
	{
		window.location.href=b+"/static/browserNotSupported.html"
		
	}
	return true
	
}**/
var sortBy=function(c,a,d)
{
	var b=function(e)
	{
		return d?d(e[c]):e[c]
		
	};
	return function(g,f)
	{
		var e=b(g),h=b(f);
		return((e<h)?-1:(e>h)?+1:0)*[-1,1][+!!a]
		
	}
	};
	function capitalizeFirstLetter(a)
	{
		return a.charAt(0).toUpperCase(0)+a.slice(1)
		
	};