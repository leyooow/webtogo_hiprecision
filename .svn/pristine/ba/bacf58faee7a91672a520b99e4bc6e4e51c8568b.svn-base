<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">	
<div style="font:bold 20px arial;color:#fff;padding: 0 10px 10px;clear:both">
	${user.company.nameEditable}&nbsp; 
	<c:if test="${user.userType.value ne 'Company Staff'}"><span class="companySetting">[ <a class="editCompanySettings" href="editcompany.do?company_id=${company.id}">Edit Company Settings</a> ]</span></c:if>
</div>
	<ul id="nav">		
						
		<li><a href="dashboard.do" ${(menu == "dashboard") ? 'class="active"' : ''}>Dashboard</a></li>
		
		<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
		
		</c:if>
		
		<c:if test="${user.company != null}">
		
			<c:if test="${company.companySettings.hasProducts}">
			<li><a href="groups.do" ${(menu == "groups") ? 'class="active"' : ''}>Groups</a></li>
			</c:if>
			<%-- </c:if>  --%>
			<li><a href="${menulist[0].url}" ${(menu == "pages") ? 'class="active"' : ''}>Pages</a></li>
			
			<c:if test="${company.id ne 127}">
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
			</c:if>
			<c:if test="${company.companySettings.hasComments}">
				<li><a href="comment.do" ${(menu == "comments") ? 'class="active"' : ''}>Comments</a></li>
			</c:if>
			
			<%---------------------------------------------- --%>
			<%-------------- If company is Politiker----------------------- --%>
			<c:if test = "${company.id eq 393 }">
				<li><a  onclick="loading();"  href="politikerreports.do" ${menu eq 'politikerreports' ? ' class="active"' : '' }><strong>Data Reports</strong></a></li>
			</c:if>
			<%---------------------------------------------- --%>
			<%---------------------------------------------- --%>
			
			<c:if test="${company.id eq 127}">
			<c:choose>
				<c:when test="${user.userType.value == 'Super User'  or user.userType.value =='WTG Administrator'}">
					<li><a href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}>Forms</a></li>
				</c:when>
				<c:otherwise>
					<c:if test="${user.userType.value ne 'Company Staff' or (user.id eq 178 or user.id eq 179 or user.id eq 180)}">
						<c:choose>
							<c:when test="${fn:length(formPageList) > 0}">
								<li><a href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}>Forms</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}>Forms</a></li>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:otherwise>
			</c:choose>
			</c:if>
			<c:if test="${company.companySettings.hasEventCalendar}">	 
				<li><a href="events.do" ${(menu == "events" or menu=="eventgroups") ? 'class="active"' : ''}>Events</a></li>	
			</c:if>
						
		</c:if>


		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator' && (company.companySettings.doNotGenerateBilling eq false || company.companySettings.doNotGenerateBilling eq null)}">
			<li><a href="billing.do" ${(menu == "account") ? 'class="active"' : ''}>Account</a></li>
		</c:if>

		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
			<li><a href="activities.do" ${(menu == "activities") ? 'class="active"' : ''}>Activities</a></li>
		</c:if>
			
		<!-- Bulletin navigation menu item -->
		<c:if test="${company.companySettings.hasBulletinFeature == true}">
			<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
				<li><a href="bulletin.do" ${(menu == "bulletin") ? 'class="active"' : ''}>Bulletin</a></li>
			</c:if>
		</c:if>
		
		<!-- Order navigation menu item -->
		<c:if test="${company.companySettings.hasOrder == true}">
			<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator' or user.userType.value == 'Company Administrator'}">
				<li><a href="order.do" ${(menu == "order") ? 'class="active"' : ''}>Orders</a></li>
			</c:if>
		</c:if>
		
		<!-- Wishlist navigation menu item -->
		<c:if test="${company.companySettings.hasWishlist == true}">
			<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
				<li><a href="wishlist.do" ${(menu == "wishlist") ? 'class="active"' : ''}>Wishlist</a></li>
			</c:if>
		</c:if>
		
		<c:if test="${user.company != null}">
			<c:if test="${user.company.companySettings.hasMemberFeature == true}">
				<li><a href="members.do" ${(menu == "members") ? 'class="active"' : ''}>Members</a></li>
			</c:if>
		</c:if>
		
		<c:if test="${user.company != null}">
			<c:if test="${company.id == 127 and (user.userType.value ne 'Company Staff' or user.id eq 172 or user.id eq 173 or user.id eq 174 or user.id eq 175 or user.id eq 176 or user.id eq 177)}">
				<li><a href="trustproductstatistics.do" ${(menu == "productstatistics") ? 'class="active"' : ''}>Rates</a></li>
			</c:if>
		</c:if>

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
		
		<c:when test="${menu == 'events' or menu == 'eventgroups'}">
			<div id="submenu">
				<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
					<a href="events.do" ${(menu == "events") ? 'class="active"' : ''}>Items</a>|
					<a href="eventgroups.do" ${(menu == "eventgroups") ? 'class="active"' : ''}>Groups</a>
				</c:if>
			</div>
		</c:when>
		
		<c:when test="${menu == 'forms'}">
			<div id="submenu">
				<a href="formsubmissions.do" ${(submenu == 'formsubmissions') ? 'class="active"' : ''}>Submissions</a> | 
				<c:if test="${company.id eq 150 || company.id eq 190 || company.id eq 194 || company.id eq 199}">
					<a href="registration.do" ${(submenu == 'registrants') ? 'class="active"' : ''}>Registrations</a> | 
				</c:if>
				
				<c:choose>
					<c:when test="${fn:length(formPageList) > 0}">
						<c:forEach items="${formPageList}" var="form">
							<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator' or user.userType.value == 'Company Administrator'}">
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
			
			<c:if test="${group.hasAttributes}">
				&nbsp; 
				<a href="attributes.do?group_id=${group.id}" ${(submenu == 'attributes') ? 'class="active"' : ''}>Attributes</a>
			</c:if>
			 
			 <c:if test="${company.companySettings.hasPackages}">
				&nbsp; 
				<a href="packages.do" ${(submenu == 'packages') ? 'class="active"' : ''}>Packages</a>
			</c:if>
			 
			</div>
		</c:when>
		
		<c:when test="${menu == 'variations'}">
			<div id="submenu">
			
			<a href="variationGroup.do" ${(submenu == 'variation_group') ? 'class="active"' : ''}>Variation Group</a>
			<a href="variation.do" ${(submenu == 'variation_group_items') ? 'class="active"' : ''}>Variation Group Items</a>
			
			</div>
		</c:when>
		
		<c:when test="${menu == 'activities'}">
			<div id="submenu">
			
			<a href="activities.do" ${(submenu == 'company_activities') ? 'class="active"' : ''}>Company Activities</a>
			<a href="logs.do" ${(submenu == 'company_logs') ? 'class="active"' : ''}>Company Logs</a>
			
			</div>
		</c:when>
		
		<c:when test="${menu == 'comments'}">
			<div id="submenu">
				<a href="comment.do" ${(submenu == 'comment') ? 'class="active"' : ''}>All Comments</a>
				<a href="comment.do?commentType=PAGE" ${(submenu == 'pagecomment') ? 'class="active"' : ''}>Pages</a>				
				<a href="comment.do?commentType=ITEM" ${(submenu == 'itemcomment') ? 'class="active"' : ''}>Items</a>
			</div>
		</c:when>
		<c:otherwise>
			<div id="submenu">&nbsp;</div>
		</c:otherwise>
				
		
	</c:choose>

	
</div>
	