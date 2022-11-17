
<%@include file="includes/header.jsp"  %>

<body>

<c:set var="formAction" value="saveevent.do" />
<c:set var="menu" value="events" />
<c:set var="pagingAction" value="events.do" />
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
</script>
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
				<c:if test="${param['evt'] != null}">
					<ul>
			 			
						<c:if test="${param['evt'] == 'save'}">
							<li><span class="actionMessage">Event was successfully created.</span></li>
						</c:if> 
						
						<c:if test="${param['evt'] == 'deleteFailed'}">
							<li><span class="errorMessage">Event was not successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Event was successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Event was successfully updated.</span></li>
						</c:if>

					</ul>
				</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Events</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
				<tr > 
						<th >Event Date</th>
						<th >Event End</th>
						<th >Title</th>
				 	<th >Event Fee</th>
						<th >EventGroup</th>
						<th  >Action</th> 
					</tr> 
					<c:set var="count" value="0" />
					<c:forEach items="${events}" var="e">
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<td nowrap><fmt:formatDate pattern="MMM dd, yyyy hh:mm a" value="${e.eventDate}"/></td> 
						<td nowrap><fmt:formatDate pattern="MMM dd, yyyy hh:mm a" value="${e.eventEnd}"/></td> 
						<td>${e.title}</td>
						<td>${e.fee}</td>
<%-- 
						<c:if test="${company.id eq 217}">
						  <td>${e.fee}</td>
						</c:if>
--%>						
		<%--			<td>${e.eventCategory}</td>	--%> 
						<td nowrap>${e.eventGroup.name}</td>
						  
						<td>
						
						<a href="editevent.do?eventId=${e.id}">Edit</a>
						<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
							 |
							<a href="deleteevent.do?eventId=${e.id}" onclick="return confirm('Do you really want to delete this brand?');">Delete</a>
						</c:if>				
						</td>
					</tr>
					</c:forEach>
										
				</table>		
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
				
	<div class="sidenav">
	
	<script>
  		function submitForm(myForm) {

  			var eventGroup				= myForm["eventGroup"].value;
  			var event_title				= myForm["event_title"].value;
  			var event_date				= myForm["event_date"].value;
  			var event_end 				= myForm["event_end"].value;
  			var event_fee				= myForm["event_fee"].value;  			
  			var detail					= myForm["detail"].value;

			var error = false;
			var messages = "The following error(s) were encountered: \n\n";

			if(eventGroup.length == 0) {
				messages += "* Event Group is not given. \n";
				error = true;
			}
			if(event_title.length == 0) {
				messages += "* Title Address is not given. \n";
				error = true;
			}
			if(event_date.length == 0) {
				messages += "* Start Date is not given. \n";
				error = true;
			}			
			if(event_end.length == 0) {
				messages += "* End Date is not given. \n";
				error = true;
			}
			if(event_fee.length == 0) {
				messages += "* Fee is not given. \n";
				error = true;
			}
			/*if(detail.length == 0) {
				messages += "* Description is not given. \n";
				error = true;
			}*/
									
			if(error) {
				alert(messages);
			}
			else {			
				return true;
			}
			
  			return false;
  		}  	

  		function submitForm2(myForm) {

  			var event_title				= myForm["event_title"].value;
  			var event_date				= myForm["event_date"].value;
  			var event_end 				= myForm["event_end"].value;

			var error = false;
			var messages = "The following error(s) were encountered: \n\n";

			if(event_title.length == 0) {
				messages += "* Title Address is not given. \n";
				error = true;
			}

			if(event_date.length == 0) {
				messages += "* Start Date is not given. \n";
				error = true;
			}			

			if(event_end.length == 0) {
				messages += "* End Date is not given. \n";
				error = true;
			}

			if(error) {
				alert(messages);
			}
			else {			
				return true;
			}
			
  			return false;
  		}  	  			
</script>
			
	 <h2>New Event</h2>
	 
	 <c:if test="${company.id ne 38}">
	 	<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
	 </c:if>
	 
	 <c:if test="${company.id eq 38}">
	 	<form method="post" action="${formAction}" onsubmit="return submitForm2(this);">
	 </c:if>
		
		<c:if test="${event.id > 0}">
			<input type="hidden" name="eventId" value="${event.id}">
		</c:if>
		
		<table width="100%">
		
			<tr>
				<td >Event Group<br />
				<select id="eventGroup" name="eventGroup" class="w200">
					<option value="">- Select Event Group -</option>
					<c:forEach items="${eventGroups}" var="eg">
						<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
						<option value="${eg.id}" ${(eg.id == event.eventGroup.id) ? "selected" : "" }>${eg.name}&nbsp;</option>
					</c:forEach>
				</select>
				</td>
			</tr>			
			<tr>
				<td >Title<br />
			 
				<input type="text" id="event_title" name="title" value="${event.title}" class="w200"/></td>
			</tr>			
			<tr>
				<td >Start Date/Time<br />
				
				<fmt:formatDate pattern="MM-dd-yyyy hh:mm a" value="${event.eventDate}" var="ed"/>
								 
				<input type="text" id="event_date" name="eventDate" value="${ed}" class="w200" readonly="readonly"/> 
				<a href="javascript:;" id="event_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				   
				  
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "event_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y %I:%M %p",      // format of the input field
				        button         :    "event_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true,
				        showsTime	   :	true,
				        timeFormat     :	"12"				        
				    });
				</script>
				
				</td>
			</tr>
			 
			<tr>
				<td >End Date/Time<br />
				
				<fmt:formatDate pattern="MM-dd-yyyy hh:mm a" value="${event.eventEnd}" var="ed"/>
								  
				<input type="text" id="event_end" name="eventEnd" value="${ed}" class="w200" readonly="readonly"/> 
				<a href="javascript:;" id="event_end_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				   
				   
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "event_end",     // id of the input field
				        ifFormat       :    "%m-%d-%Y %I:%M %p",      // format of the input field
				        button         :    "event_end_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true,
				        showsTime	   :	true,
				        timeFormat     :	"12"
				    });
				</script>
				
				</td>
			</tr>
			<tr>
				<td >Fee<br />
				<input type="text" id="event_fee" name="fee" value="${event.fee}" class="w200"/></td>
			</tr>				
			<tr>
				<td >Color Code<br />
				<select id=colorCode name="colorCode" class="w200">
					<option value="">- Select Color -</option>
					<c:forEach items="${colorCodes}" var="ccode">
						<option value="${ccode}" style="background-color: ${ccode}" <c:if test="${event.colorCode.value eq ccode}">selected</c:if> >${ccode}</option>								
					</c:forEach>					
				</select>	 
				
			</tr>	
	
			<tr>
				<td >Description<br />
			 	  <textarea id="detail" name="detail" class="w200" >${event.detail}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace('detail');
			</script></td>
						
			</tr>
			

							
			<tr>
				
				<td >
				<c:choose>
					<c:when test="${event.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='events.do'" class="btnBlue">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
								
				</td>
			</tr>
					
		</table>
		
		</form>
	</div><!--//sidenav-->
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>