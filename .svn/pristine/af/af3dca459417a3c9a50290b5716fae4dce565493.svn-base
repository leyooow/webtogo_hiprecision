<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="order" />
<c:set var="submenu" value="arealocation"/>
<c:set var="pagingAction" value="arealocation.do" />
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
	var areaID = document.getElementById('areaID').value;
	var locationname = document.getElementById('locationname').value;
	var rate = document.getElementsByName('rate');
	var hasblankrate = false;
	var error = false;
	var messages = "Problem(s) occured: \n\n";

	if(areaID.length == 0) {
	  error = true;
	  messages += " * Area Name is not specified. \n";
	} 

	if(locationname.length == 0) {
	  error = true;
	  messages += " * Location Name is not specified. \n";
	}

	for(var i=0; i<rate.length; i++)
	{
	  if(rate[i].value == "" || isNaN(rate[i].value))
	  {
	    error = true;
	    hasblankrate = true;
	  }
	}

	if(hasblankrate)
	{
	    messages += " * One of the Rates Per Unit is not specified or invalid.";
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
	      <h1><strong>Location</strong>: Edit Location</h1>
					
		  <div class="clear"></div>
	    </div><!--//pageTitle-->

	    <form method="post" action="savelocation.do" onsubmit="return submitForm(this);">
		  <c:if test="${location.id > 0}">
		    <input type="hidden" name="location_id" value="${location.id}">
		  </c:if>
		
		  <table width="100%">
			<tr>
			  <td width="1%" nowrap>Area Name</td>
			  <td width="10px"></td>
			  <td>
                <select id="areaID" name="areaID">
                  <option value="">-Select Area-</option>
                  <c:forEach items="${areas}" var="a">
                    <option value="${a.id}" <c:if test="${location.ossShippingArea.areaName eq a.areaName}">selected</c:if>>${a.areaName}</option>
                  </c:forEach>
                </select>
			  </td>
			  <td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
			  <td width="1%" nowrap>Location Name</td>
			  <td width="10px"></td>
			  <td><input type="text" id="locationname" name="locationname" class="textbox3" value="${location.locationName}"></td>
			  <td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
			  <td width="1%" nowrap>Description</td>
			  <td width="10px"></td>
			  <td><input type="text" id="description" name="description" class="textbox3" value="${location.description}"></td>
			  <td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<c:if test="${company.name ne 'mraircon'}">
				<tr>
				  <td width="1%" nowrap>Volume Weight</td>
				  <td width="10px"></td>
				  <td>Rates Per Unit</td>
				  <td width="1%" colspan="3" nowrap></td>
				</tr>
				<c:forEach items="${rates}" var="r">
				  <tr>
				    <td width="1%" nowrap>${r.weight} kls.<input type="hidden" id="weight" name="weight" class="textbox3" value="${r.weight}"></td>
				    <td width="10px"></td>
				    <td><input type="text" id="rate" name="rate" class="textbox3" value="${r.rate}"></td>
				    <td width="1%" colspan="3" nowrap></td>
				  </tr>
				</c:forEach>
			</c:if>
			<c:if test="${company.name eq 'mraircon'}">
			 	<input type="hidden" id="weight" name="weight" class="textbox3" value="1">				    
				<input type="hidden" id="rate" name="rate" class="textbox3" value="0">				    
			</c:if>
			<tr>
			  <td colspan="2" style="border: 0px;"></td>
			  <td style="border: 0px;">
			    <c:choose>
				  <c:when test="${location.id > 0}">
					<input type="submit" value="Update" class="btnBlue">
					<input type="reset" value="Reset" class="btnBlue">
					<input type="button" value="Cancel" onclick="window.location='arealocation.do'" class="btnBlue">
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