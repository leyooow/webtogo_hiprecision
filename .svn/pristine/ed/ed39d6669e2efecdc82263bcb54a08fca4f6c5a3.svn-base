<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">	
	<div style="font:bold 20px arial;color:#fff;padding: 0 10px 10px;clear:both">
		${user.company.nameEditable}&nbsp; 
		<span class="companySetting">[ <a class="editCompanySettings" href="editcompany.do?company_id=${company.id}">Edit Company Settings</a> ]</span>
	</div>
	<ul id="nav">		
						
		<li><a href="dashboard.do" ${(menu == "dashboard") ? 'class="active"' : ''}>Dashboard</a></li>
		
		<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">		
<%--		<li><a href="companies.do" ${(menu == "companies") ? 'class="active"' : ''}>Companies</a></li>   --%>
		<%-- <li><a href="users.do" ${(menu == "users") ? 'class="active"' : ''}>Users</a></li>  --%>		
		</c:if>
		
		<c:if test="${user.company != null}">
			<%-- <c:if test="${user.userType.value == 'Super User'  or user.userType.value =='WTG Administrator'}">  --%>
			<c:if test="${company.companySettings.hasProducts}">
			<li><a href="groups.do" ${(menu == "groups") ? 'class="active"' : ''}>Groups</a></li>
			</c:if>
			<%-- </c:if>  --%>
			<li><a href="${menulist[0].url}" ${(menu == "pages") ? 'class="active"' : ''}>Pages</a></li>
			
			<c:choose>
				<c:when test="${user.userType.value == 'Super User'  or user.userType.value =='WTG Administrator'}">
					<li><a href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}>Forms</a></li>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${fn:length(formPageList) > 0}">
							<li><a href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}>Forms</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}>Forms</a></li>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<%--
			<c:if test="${user.userType.value != 'Super User'  or user.userType.value =='WTG Administrator'}">
				<c:if test="${(groupList != null)}">
					<c:forEach items="${groupList}" var="g">
						<c:choose>
							<c:when test="${menu == 'groupItem'}">
								<li><a href="items.do?group_id=${g.id}" ${(groupName eq g.name) ? 'class="active"' : ''}>${g.name}</a></li>
							</c:when>
							<c:otherwise> 
								<li><a href="items.do?group_id=${g.id}">${g.name}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
			</c:if>
			--%>
			<%-- <li><a href="billing.do" ${(menu == "account") ? 'class="active"' : ''}>Account</a></li>  --%>
			
			<c:if test="${company.companySettings.hasEventCalendar}">	 
			<li><a href="events.do" ${(menu == "events") ? 'class="active"' : ''}>Events</a></li>	
			</c:if>
			
			<!-- 
			<li><a href="variationGroup.do" ${(menu == "variations") ? 'class="active"' : ''}>Variations</a></li>	
			-->
			
		</c:if>


		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
			<li><a href="billing.do" ${(menu == "account") ? 'class="active"' : ''}>Account</a></li>
		</c:if>

		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
			<li><a href="activities.do" ${(menu == "activities") ? 'class="active"' : ''}>Activities</a></li>
		</c:if>
		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
			<li><a href="eventgroups.do" ${(menu == "eventgroup") ? 'class="active"' : ''}>EventGroups</a></li>
		</c:if>

<%--
		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
			<li><a href="eventgroups.do" ${(menu == "eventgroups") ? 'class="active"' : ''}>Event Groups</a></li>
		</c:if>
--%>
		<c:if test="${user.company != null}">
			<c:if test="${user.company.companySettings.hasMemberFeature == true}">
				<li><a href="members.do" ${(menu == "members") ? 'class="active"' : ''}>Members</a></li>
			</c:if>
		</c:if>


<%--		<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
		<li><a href="users.do" ${(menu == "user_listing") ? 'class="active"' : ''}>Users</a></li>
		</c:if>
--%>

<%--
		<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
		<li><a href="companies.do" ${(menu == "manage") ? 'class="active"' : ''}>Manage</a></li>
		</c:if>
--%>
	</ul> 
</div>

<div id="catmenu">
	
	<c:choose>
	
		<c:when test="${menu == 'companies'}">
			<div id="submenu">
				<a href="<s:url action="companies" />" ${(submenu == "company_listing") ? 'class="active"' : ''}> Company Listing</a>	
			</</div>			
		</c:when>
		
		<c:when test="${menu == 'users'}">
			<div id="submenu">
				<a href="<s:url action="users.do" />" ${(submenu == "user_listing") ? 'class="active"' : ''}> User Listing</a>	
			</</div>			
		</c:when>
		
		<c:when test="${menu == 'pages'}">
			<c:if test="${not empty menulist}">
				<div id="submenu">
					<c:forEach items="${menulist}" var="menu">
						<a href="${menu.url}" ${(submenu == menu.name) ? 'class="active"' : ''}>${menu.name}</a> |
					</c:forEach>
				</</div>			
			</c:if>
		</c:when>
		
		<c:when test="${menu == 'items'}">
			<div id="submenu">&nbsp;</div>
		</c:when>
		
		<c:when test="${menu == 'groups'}">
			<div id="submenu">&nbsp;</div>
		</c:when>
		
		<c:when test="${menu == 'forms'}">
			<div id="submenu">
				<a href="formsubmissions.do" ${(submenu == 'formsubmissions') ? 'class="active"' : ''}>Submissions</a> | 
				<c:choose>
					<c:when test="${fn:length(formPageList) > 0}">
						<c:forEach items="${formPageList}" var="form">
							<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
								<a href="${form.url}" ${(submenu == form.name) ? 'class="active"' : ''}>${form.name}</a> | 
							</c:if>
							<c:if test="${user.userType.value ne 'Super User' and user.userType.value ne 'WTG Administrator' and (form.name eq 'Submissions' or form.name eq 'thank you' or form.name eq 'contact us' or form.name eq 'acknowledgement' or form.name eq 'Thank You' or form.name eq 'Contact Us' or form.name eq 'Acknowledgement')}">
								<a href="${form.url}" ${(submenu == form.name) ? 'class="active"' : ''}>${form.name}</a> | 
							</c:if>


						</c:forEach> 
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose> 
				
			</div>
		</c:when>
		
		<c:when test="${menu == 'account'}">
			<div id="submenu">
			<a href="billing.do" ${(submenu == 'billing') ? 'class="active"' : ''}>Billing</a>
			&nbsp; 
			<a href="payment.do" ${(submenu == 'payment') ? 'class="active"' : ''}>Payment</a>
			</div>
		</c:when> 
		
		<c:when test="${menu == 'groupItem'}">
			<div id="submenu">
			<a href="items.do?group_id=${group.id}" ${(submenu == 'items') ? 'class="active"' : ''}>Items</a>
			&nbsp; 
			
			<a href="category.do?group_id=${group.id}" ${(submenu == 'categories') ? 'class="active"' : ''}>Categories</a>
			
			<c:if test="${group.hasBrands}">
				&nbsp; 
				<a href="brands.do?group_id=${group.id}" ${(submenu == 'brands') ? 'class="active"' : ''}>Brands</a>
			</c:if>
			 
			</div>
		</c:when>
		
		<c:when test="${menu == 'variations'}">
			<div id="submenu">
			
			<a href="variationGroup.do" ${(submenu == 'variation_group') ? 'class="active"' : ''}>Variation Group</a>
			<a href="variation.do" ${(submenu == 'variation_group_items') ? 'class="active"' : ''}>Variation Group Items</a>
			
			</div>
		</c:when>
		
<%--
		<c:when test="${menu == 'manage'}">
			<div id="submenu">
			<a href="companies.do" ${(submenu == 'company_listing') ? 'class="active"' : ''}>Companies</a>
			&nbsp; 
			<a href="users.do" ${(submenu == 'user_listing') ? 'class="active"' : ''}>Users</a>
			</div>
		</c:when> 
--%>



		<c:otherwise>
			<div id="submenu">&nbsp;</div>
		</c:otherwise>
				
		
	</c:choose>

	
</div>
	