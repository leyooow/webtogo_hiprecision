

	
	<%--		<div style="padding-left:0;float: left;"><img style="padding:5px;" src="${contextParams['IMAGE_PATH']}/images/${user.company.logo}" height="35px"  /></div>&nbsp;<span style="font:bold 20px arial;color:#fff;">${user.company.nameEditable}&nbsp;</span>  --%>
				<table class="green-top"><tr><td>
				<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
					<form method="post" action="dashboard.do">
					<div style="padding-left:10px">
						<span style="color:#fff">Choose Company</span>
												
						&nbsp;&nbsp;
						
						<c:set var="userCompanyId" value="${(user.company != null) ? user.company.id : 0 }"></c:set>
					
						<select name="company_id" style="width: 250px;">
							
							<c:if test="${userCompanyId == 0}">
								<option value="0"> -- SELECT COMPANY -- </option>
							</c:if>
							
							<c:if test="${not empty companies }">
							<c:forEach items="${companies}" var="company">
								<c:if test="${company.companySettings.suspended eq false}"><option value="${company.id}" <c:if test="${company.id == userCompanyId}">selected</c:if>>${company.nameEditable}</option></c:if>
							</c:forEach>
							</c:if>

							<c:if test="${empty companies }">
							<c:forEach items="${companies2}" var="company">
								<c:if test="${company.companySettings.suspended eq false}"><option value="${company.id}" <c:if test="${company.id == userCompanyId}">selected</c:if>>${company.nameEditable}</option></c:if>
							</c:forEach>
							</c:if>
						</select>
						<input border="0" type="submit" value="Go" style="background:url(images/GoButton.gif) bottom no-repeat; width:49px;height:27px;padding: 2px;border: none;color:#fff"/>
					</div>
					</form>
				</c:if></td><td>
	<div class="user-display">
	<h3 class="global">
	 		${user.firstname} ${user.lastname} 
		    <strong>[${user.userType.value}] &nbsp;&nbsp;&nbsp;&nbsp;</strong>
	
		<img src="images/logout.gif" width="16" height="16" style=" margin-bottom: -4px "> 
		<a href="logout.do" title="Log-out and clear the cookie off your machine" onclick="return confirm('Are you sure you want to log out?');">Log-out</a>
		<span class="pipe">&nbsp;&nbsp;<img src="images/settings.gif" width="16" height="16" style=" margin-bottom: -4px "></span><a href="settings.do">Settings </a><span class="pipe"></span>
		<c:if test="${user.userType.value == 'Company Administrator'}">
			<span class="pipe"><img src="images/users.gif" width="16" height="16" style=" margin-bottom: -4px "></span><a href="users.do">Manage Users</a><span class="pipe"></span>			
		</c:if>	
		
	</h3>
			
	</div>
	</td></tr>

		<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
				<tr><td colspan=2 style="background:url(images/greentopMenuBg.gif) top repeat-x #507f07" >		
				<div class="green-top-menu">
				<ul>
			         <li><a href="companies.do" ${(submenu == "company_listing") ? 'class="active"' : ''}>Companies</a></li>
			         <li><a href="users.do" ${(submenu == "user_listing") ? 'class="active"' : ''}>Users</a></li>
			         <li><a href="reports.do" ${(submenu == "report_listing") ? 'class="active"' : ''}>Reports</a></li>
				</ul></div>
			</td></tr>
		</c:if>
 
</table>
