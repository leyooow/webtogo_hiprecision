
<%@include file="includes/header.jsp"  %>
<c:set var="formAction" value="saveeventgroup.do" />

 <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="events" />

<script>

	function submitForm(myForm) {
		var title =  getElement('eventgroup_name');
		var description =  getElement('eventgroup_description');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		

		
		if(title.length == 0) {
			error = true;
			messages += " * EventGroup title was not specified. \n";
		} 
		
		if(description.length == 0) {
			error = true;
			messages += " * EventGroup description was not specified. \n";
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
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
</script> 
<body>
<c:set var="menu" value="eventgroups" />
<c:set var="pagingAction" value="eventgroups.do" />
<div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
				<c:if test="${param['evt'] != null}">
					<ul>
			 			
						<c:if test="${param['evt'] == 'save'}">
							<li><span class="actionMessage">Event Group was successfully created.</span></li>
						</c:if> 
						
						<c:if test="${param['evt'] == 'deleteFailed'}">
							<li><span class="errorMessage">Event Group was not successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Event Group was successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Event Group was successfully updated.</span></li>
						</c:if>

					</ul>
				</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Event Groups</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
		<tr > 
						<th >Name</th>
						<th >Description</th>
						<th >Items/Events</th>
						<th >Action</th> 
					</tr> 
						<c:set var="count" value="0" />
					<c:forEach items="${eventGroups}" var="e">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<td>${e.name}</td> 
						<td>${e.description}</td> 
 						<td>
 							<c:forEach items="${e.events}" var="event">
	 							${event.title}<br/>
							</c:forEach>
						</td> 
						<td>
							<a href="editeventgroup.do?eventGroupId=${e.id}">Edit</a> |
							<a href="deleteeventgroup.do?eventGroupId=${e.id}" onclick="return confirm('Do you really want to delete this event group?');">Delete</a>
						</td>
					</tr>
					</c:forEach>
					
				</table> 
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
			
	 <h2>New Event Group</h2>
	<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		
		<c:if test="${event.id > 0}">
			<input type="hidden" name="eventId" value="${event.id}">
		</c:if>
		
		<table width="100%">
					
			


			
			<tr>
				<td >Name<br />
			 
				<input type="text" id="eventgroup_name" name="name" value="${eventGroup.name}" class="w200"/></td>
			</tr>
			
			<tr>
				<td >Description<br />
			 
				<input type="text" id="eventgroup_description" name="description" value="${eventGroup.description}" class="w200"/></td>
			</tr>
			
							
			<tr>
			
				<td >
				 
				
				 
				<c:choose>
					<c:when test="${eventGroup.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='eventgroups.do'" class="btnBlue">
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