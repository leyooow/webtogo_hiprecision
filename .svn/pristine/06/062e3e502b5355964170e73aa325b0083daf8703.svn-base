<%@include file="includes/header.jsp"  %>

 
<body>

	 <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />		  	
			  	<h3>
				
<%--				<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
					<a href="groups.do">
					Group : ${group.name}
					</a>
					
					>
					
				</c:if>
				<a href="category.do?group_id=${group.id}">Categories</a> >
--%>				
				
				Edit User
				
				</h3>
			  	
				<c:set var="formAction" value="saveuser.do" />
				<%@include file="userform.jsp" %>
				
				<h3>Logs</h3><br />
				<form action="edituser.do" method="get">
					<input type="hidden" name="user_id" value="${user2.id}" />
					<table style="width:50%">
						<tr>
							<td>From date</td>
							<td>
								<input type="text" name="startDate" id="startDate" value="${startDate}"/>
								<button id="startDateTrigger">...</button>
							</td>
							<td>To date</td>
							<td>
								<input type="text" name="endDate" id="endDate" value="${endDate}" />
								<button id="endDateTrigger">...</button> 
								<button type="submit">Go</button> 
							</td>
						</tr>
					</table>
				</form>
				<script type="text/javascript">
				  Calendar.setup(
				    {
				      inputField  : "startDate",         // ID of the input field
				      ifFormat    : "%Y-%m-%d",    // the date format
				      button      : "startDateTrigger"       // ID of the button
				    }
				  );

				  Calendar.setup(
				    {
				      inputField  : "endDate",         // ID of the input field
				      ifFormat    : "%Y-%m-%d",    // the date format
				      button      : "endDateTrigger"       // ID of the button
				    }
				  );
				</script>
								
				<br />
				<c:choose>
					<c:when test="${fn:length(requestScope.startDate) eq 0 or fn:length(requestScope.endDate) eq 0}">
						Please select a date.
					</c:when>
					<c:when test="${not empty logList and fn:length(requestScope.startDate) gt 0 and fn:length(requestScope.endDate) gt 0}">
						<table style="width:50%">  
							<tr class="headbox"> 
								<th>Date</th>
								<th>Description</th>
							</tr>
							<c:forEach items="${logList}" var="cp"  varStatus="counter">
								<tr>
									<td>${cp.createdOn}</td>
									<td>
										<c:set var="i" value="0" />
										<c:forTokens var="curr" delims="~" items="${cp.remarks}">
											<c:set var="i" value="${i + 1}" />
											<c:choose>
												<c:when test="${fn:length(curr) gt 250}">
													<c:out value="${fn:substring(curr,0,250)}" escapeXml="true" /><a href="javascript:void(0);" onmouseover="javascript:showContent(${i});">...</a>
													<div id="showContent_${i}" style="display:none">
														<c:out value="${curr}" escapeXml="true" />
													</div>
													<br />
												</c:when>
												<c:otherwise>
													<c:out value="${curr}" escapeXml="true" /> <br />
												</c:otherwise>
											</c:choose>
										</c:forTokens>
									</td>
								</tr>
							</c:forEach>
						</table>
						<br />
						<%@include file="includes/paging.jsp"  %>
					</c:when>
					<c:otherwise>
						<br /><br /><p>No activities in this date range.</p>
					</c:otherwise>
				</c:choose>
				
				</div><!--//mainContent-->
		
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>