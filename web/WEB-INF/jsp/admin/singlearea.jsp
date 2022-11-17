<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="order" />
<c:set var="submenu" value="area"/>
<c:set var="pagingAction" value="area.do" />
<c:set var="mode" value="edit"/>

<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

<!-- language for the calendar -->
<script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

<!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<script>
  function submitForm(myForm) {
	var areaname= getElement('areaname');
	var error = false;
	var messages = "Problem(s) occured: \n\n";
		
	if(areaname.length == 0) {
	  error = true;
	  messages += " * Area Name is not specified. \n";
	} 

	if(error) {
	  alert(messages);
	}
	else {
	  return true;
	}
		
	return false;
  }
	
  function getElement(elementName) {
	var value = document.getElementById(elementName).value;

	return value;
  }

  function showContent(id) {
	var content = document.getElementById('showContent_'+id).innerHTML;
	try{
	  overlib(content, STICKY, NOCLOSE);
	}catch(e){
	  alert(content);
	}
  }
</script> 
<script language="javascript" src="../javascripts/overlib.js"></script>

<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
    <div class="contentWrapper" id="contentWrapper">
      <div class="content">
	 	<s:actionmessage />
		<s:actionerror />
				
	    <div class="pageTitle">
	      <h1><strong>Area</strong>: Edit Area</h1>
					
		  <div class="clear"></div>
	    </div><!--//pageTitle-->

	    <form method="post" action="savearea.do" onsubmit="return submitForm(this);">
		  <c:if test="${area.id > 0}">
		    <input type="hidden" name="area_id" value="${area.id}">
		  </c:if>
		
		  <table width="100%">
			<tr>
			  <td width="1%" nowrap>Area Name</td>
			  <td width="10px"></td>
			  <td><textarea id="areaname" name="areaname" class="textbox3">${area.areaName}</textarea></td>
			  <td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
			  <td width="1%" nowrap>Description</td>
			  <td width="10px"></td>
			  <td><input type="text" id="description" name="description" class="textbox3" value="${area.description}"></td>
			  <td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
			  <td colspan="2" style="border: 0px;"></td>
			  <td style="border: 0px;">
			    <c:choose>
				  <c:when test="${area.id > 0}">
					<input type="submit" value="Update" class="btnBlue">
					<input type="reset" value="Reset" class="btnBlue">
					<input type="button" value="Cancel" onclick="window.location='area.do'" class="btnBlue">
				  </c:when>
				  <c:otherwise>
					<input type="submit" value="Add" class="btnBlue">
				  </c:otherwise>
				</c:choose>
			  </td>
			  <td width="1%" colspan="3" nowrap></td>
			</tr>
		  </table>
		</form>
	  </div><!--//mainContent-->
    </div>

	<div class="clear"></div>
  </div><!--//container-->
</body>
</html>