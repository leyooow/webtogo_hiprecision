
<%@include file="includes/header.jsp"  %>
<c:set var="menu" value="message"/>
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

	    <h1><strong>Message</strong></h1>
	
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	<div class="clear"></div>
	<ul class="pagination">
		<%@include file="includes/pagingnew.jsp"  %>
	</ul>
	<br>
	<form action="sendmessage.do" method="POST">
		<table width="100%" >
		
			<tr>
				<td>
					Member Type<br/>
					<select name="memberTypeId">
						<c:forEach items="${memberTypes}" var="memberType">
							<option value="${memberType.id }">${memberType.name }</option>
						</c:forEach>
						<c:if test="${company.name eq 'uniorientagents'}">
							<option value="0">All Agents</option>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<td >Subject<br /><input type="text" name="subject" class="w200"></td>
			</tr>
			<tr>
				<td>
					Body<br />
					<textarea name="body" cols="60" rows="10"></textarea>				
				</td>
			</tr>
					
			<tr>
				
				<td >
					<input type="submit" name="submit" value="Send Message" class="btnBlue">
					<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='members.do'">
				
				</td>
			</tr>
		</table>
		</form>
	</div><!--//mainContent-->
	</c:if>
	
		 	  
	</div><!--//sidenav-->

</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>