<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 


<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="forms" />
<c:set var="submenu" value="registrants" />

<c:set var="pagingAction" value="registration.do" />

<script>

/*	function showMessage(id) 
	{
		var content = document.getElementById('emailContent_'+id).innerHTML;
		Tip(content);
	}
*/
	function setCheckbox(FormName, FieldName, CheckValue)
	{			

		if(!document.forms[FormName])
			return;
		var objCheckBoxes = document.forms[FormName].elements[FieldName];
		if(!objCheckBoxes)
			return;
		var countCheckBoxes = objCheckBoxes.length;
		if(!countCheckBoxes)
			objCheckBoxes.checked = CheckValue;
		else
			// set the check value for all check boxes
			for(var i = 0; i < countCheckBoxes; i++)
				objCheckBoxes[i].checked = CheckValue;
	}	

	function confirmDelete(FormName,FieldName){
		var error 				= false;
		var message	 			= "No submission is selected";
		var i					= 0;
		var selected			= 0;
		var objCheckBoxes 		= document.forms[FormName].elements[FieldName];
		document.forms[FormName].selectedValues.value="";

		while(i != (objCheckBoxes.length)){
			if (objCheckBoxes[i].checked == true){
				document.forms[FormName].selectedValues.value = document.forms[FormName].selectedValues.value + objCheckBoxes[i].value + "_";
				selected++;
			}
			i++;
		}
		
		if (!selected)
			error = true;
						
		if(error) {
			alert(message);
		}
		else {			
			message="Do you really want to delete "+ selected +" submission?";
			if (confirm(message))
				return true;
		}	
		return false;	
		
	}
</script>


<script language="JavaScript" src="../javascripts/overlib.js"></script>

<script>
function showMessage(id) {
	var content = document.getElementById('emailContent_'+id).innerHTML;
	overlib(content, STICKY, NOCLOSE)}
</script>
  
<body>

<!-- 
	<script type='text/javascript' src='../javascripts/wz_tooltip.js'></script>
 -->
	
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
			  	<div id="companyhome"> 
				<c:if test="${ company.id eq 190}"><%-- IAVE --%>
					<%@include file="registrationTemplates/iaveRegistration.jsp"%>
				</c:if>
				<c:if test="${company.id eq 150 }"><%-- BRIGHT --%>
					<%@include file="registrationTemplates/brightRegistration.jsp"%>
				</c:if>
				<c:if test="${company.id eq 194 }"><%-- GamePlace --%>
					<%@include file="registrationTemplates/gameplaceRegistration.jsp"%>
				</c:if>
				<c:if test="${company.id eq 199 }"><%-- Smokefree --%>
					<%@include file="registrationTemplates/smokefreeRegistration.jsp"%>
				</c:if>
				 </div>
				 
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		<div class="clear"></div>
		<%@include file="includes/footer.jsp"  %>
</body>


