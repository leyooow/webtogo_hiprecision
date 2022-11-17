
<%@include file="includes/header.jsp"  %>

<body>

  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>

<div class="contentWrapper" id="contentWrapper">
	<c:if test="${customFieldDownload != null}">	
	<div id="mainContent">
	<%@include file="member_customFieldDownload.jsp"%>
	</div>
	</c:if>	
	
	<c:if test="${customFieldDownload == null}">	
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
						
						<c:if test="${param['evt'] == 'invalid'}">
							<c:choose>
								<c:when test="${company.name == 'cancun'}">
									<li><span class="actionMessage"><strong style="color:red">Billing Number already exists</strong></span></li>
								</c:when>
								<c:otherwise>
									<li><span class="actionMessage"><strong style="color:red">Username already exists</strong></span></li>
								</c:otherwise>
							</c:choose>
						</c:if>

					</ul>
				</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Member Types</strong></h1>
	
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			<div class="clear"></div>
			<c:if test="${empty memberType.id}">
	 			<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
	
					<tr >
						<th ></th>
						<th >Name</th>
						<th >Action</th>
					</tr>
						<c:set var="count" value="0" />
					<c:forEach items="${memberTypes}" var="memberType">
						<tr>
							<td>&nbsp;</td>
							<td>${memberType.name }</td>
							<td><a href="membertypes.do?memberTypeId=${memberType.id }">Edit</a> | Delete</td>
						</tr>
					</c:forEach>
												
				</table>
			</c:if>
	<ul class="pagination">
		<%@include file="includes/pagingnew.jsp"  %>
	</ul>
	</div><!--//mainContent-->
	</c:if>
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
			
	<br>
	<form action="savemembertype.do" method="POST">
	<h2>Add New Member Type</h2>
		<input type="hidden" name="memberTypeId"  value="${memberType.id}"/>
		<table width="100%" >
		
			<tr>
				<td >Name<br /><input type="text" id="name" name="memberType.name" value="${memberType.name}" class="w200"></td>
			</tr>
					
			<tr>
				
				<td >
					<c:if test="${empty member2}">
							<input type="submit" name="submit" value="Add New" class="btnBlue">
					</c:if>
					<c:if test="${not empty member2}">
							<input type="submit" name="submit" value="Update" class="btnBlue">
					</c:if>
					
					<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='members.do'">
				
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