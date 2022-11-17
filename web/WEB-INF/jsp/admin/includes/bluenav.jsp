<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %> 
	<%-- <%@taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>--%>
	<%@ taglib prefix="FCK" uri="http://java.fckeditor.net" %>
	<%@taglib uri="/struts-tags" prefix="s" %>
	 
	<!-- note..!!!
		*Nino Eclarin*
		if the are pages that uses the pagingUtil class.. use request.getParameter("pageNumber")
		
		instead of request.getParameter("page")...
		
		pagingUtil uses "pageNumber" as form variable.
	-->
	
	<div class="navMenu">
		<ul class="nav">
		<c:choose>
		<c:when test="${user.company != null and company.name == 'hiprecisiononlinestore' and user.userType.value == 'Company Staff'}">
			<li><a onclick="loading();" href="order.do" ${(menu == "order") ? 'class="active"' : ''}><strong>Orders</strong></a></li>
		</c:when>
			<c:when test="${user.userType.value == 'User Group Administrator' or user.userType.value == 'User Sub Group Administrator' }">
			<c:if test="${user.company != null}">
				<c:if test="${user.company.companySettings.hasMemberFeature == true}">
					<li><a onclick="loading();" href="members.do" ${(menu == "members") ? 'class="active"' : ''}><strong>Members</strong></a></li>
				</c:if>
			</c:if>
		</c:when>
			<c:when test="${company.name eq 'neltex' && user.userType.value == 'Company Administrator' && fn:containsIgnoreCase(user.username, 'hr')}">
				
				<li><a onclick="loading();" href="dashboard.do" ${(menu == "dashboard") ? 'class="active"' : ''}><strong>Dashboard</strong></a></li>
					
			</c:when>
				
			<c:when test="${user.userType.value != 'Normal User'}">	
			<li><a onclick="loading();" href="dashboard.do" ${(menu == "dashboard") ? 'class="active"' : ''}><strong>Dashboard</strong></a></li>
		
		<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
		
		</c:if>
		
		<c:if test="${user.company != null}">
		
			<c:if test="${company.companySettings.hasProducts}">
			<li><a onclick="loading();" href="groups.do" ${(menu == "groups") ? 'class="active"' : ''}><strong>Groups</strong></a></li>
			</c:if>
			<%-- </c:if>  --%>
			<li <c:if test="${company.name eq 'agian' }">style="display:none;"</c:if>><a onclick="loading();" href="${menulist[0].url}" ${(menu == "pages") ? 'class="active"' : ''}><strong>Pages</strong></a></li>
			
			<c:if test="${company.id ne 127 and (company.id ne 181 and user.userType.value ne 'Company Staff')}">
				<c:choose>
					<c:when test="${user.userType.value == 'Super User'  or user.userType.value =='WTG Administrator'}">
						<li><a onclick="loading();" href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}><strong>Forms</strong></a></li>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${fn:length(formPageList) > 0}">
								<li><a onclick="loading();" href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}><strong>Forms</strong></a></li>
							</c:when>
							<c:otherwise>
								<li><a onclick="loading();" href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}><strong>Forms</strong></a></li>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${company.companySettings.hasComments}">
				<li><a onclick="loading();" href="comment.do" ${(menu == "comments") ? 'class="active"' : ''}><strong>Comments</strong></a></li>
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
						<li><a onclick="loading();" href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}><strong>Forms</strong></a></li>
					</c:when>
					<c:otherwise>
						<c:if test="${user.userType.value ne 'Company Staff' or (user.id eq 178 or user.id eq 179 or user.id eq 180)}">
							<c:choose>
								<c:when test="${fn:length(formPageList) > 0}">
									<li><a onclick="loading();" href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}><strong>Forms</strong></a></li>
								</c:when>
								<c:otherwise>
									<li><a onclick="loading();" href="formsubmissions.do" ${(menu == "forms") ? 'class="active"' : ''}><strong>Forms</strong></a></li>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${company.companySettings.hasEventCalendar}">	 
				<li><a onclick="loading();" href="events.do" ${(menu == "events" or menu=="eventgroups") ? 'class="active"' : ''}><strong>Events</strong></a></li>	
			</c:if>
						
		</c:if>


		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator' && (company.companySettings.doNotGenerateBilling eq false || company.companySettings.doNotGenerateBilling eq null)}">
			<li><a onclick="loading();" href="billing.do" ${(menu == "account") ? 'class="active"' : ''}><strong>Account</strong></a></li>
		</c:if>

		<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
			<li><a onclick="loading();" href="activities.do" ${(menu == "activities") ? 'class="active"' : ''}><strong>Activities</strong></a></li>
		</c:if>
			
		<!-- Bulletin navigation menu item -->
		<c:if test="${company.companySettings.hasBulletinFeature == true}">
			<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
				<li><a onclick="loading();" href="bulletin.do" ${(menu == "bulletin") ? 'class="active"' : ''}><strong>Bulletin</strong></a></li>
			</c:if>
		</c:if>
		
		<!-- Order navigation menu item -->
		<c:if test="${company.companySettings.hasOrder == true}">
			<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator' or user.userType.value == 'Company Administrator'}">
				<li><a onclick="loading();" href="order.do" ${(menu == "order") ? 'class="active"' : ''}><strong>Orders</strong></a></li>
			</c:if>
		</c:if>
		
		<!-- Wishlist navigation menu item -->
		<c:if test="${company.companySettings.hasWishlist == true}">
			<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
				<li><a onclick="loading();" href="wishlist.do" ${(menu == "wishlist") ? 'class="active"' : ''}><strong>Wishlist</strong></a></li>
			</c:if>
		</c:if>
		
		<c:if test="${company.companySettings.hasPackages}">
			<c:if test="${user.company != null and user.userType.value =='Company Administrator' or user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
				<li><a onclick="loading();" href="packages.do" ${(menu == "packages") ? 'class="active"' : ''}><strong>Packages</strong></a></li>
			</c:if>
		</c:if>
		
		<c:if test="${company.companySettings.hasPromoCode == true}">
			<c:if test="${user.company != null and (user.userType.value =='Super User' or user.userType.value =='WTG Administrator' or user.userType.value == 'Company Administrator')}">
				<li><a onclick="loading();" href="promocode.do" ${(menu == "promocode") ? 'class="active"' : ''}><strong>Promo Code</strong></a></li>
			</c:if>
		</c:if>
		
		<c:if test = "${company.id eq 413 }">
				<li><a  onclick="loading();"  href="promo.do" ${menu eq 'promo' ? ' class="active"' : '' }><strong>Promo</strong></a></li>
			</c:if>
		
		<c:if test="${user.company != null && user.userType.value !='Events Administrator'}">
			<c:if test="${user.company.companySettings.hasMemberFeature == true}">
				<li <c:if test="${user.userType.value =='Content Administrator' }">style="display:none;"</c:if>><a onclick="loading();" href="members.do" ${(menu == "members") ? 'class="active"' : ''}><strong>Members</strong></a></li>
			</c:if>
		</c:if>
		
		<c:if test="${user.company != null}">
			<c:if test="${user.company.companySettings.hasPageFileRights == true}">
				<li <c:if test="${company.name eq 'agian' }">style="display:none;"</c:if>><a onclick="loading();" href="message.do" ${(menu == "message") ? 'class="active"' : ''}><strong>Message</strong></a></li>
			</c:if>
		</c:if>
		
		<c:if test="${user.company != null && (user.company.id eq 51 or user.company.id eq 316 or user.company.id eq 355 or user.company.id eq 333 or user.company.id eq 420)}">
				<li><a onclick="loading();" href="components.do" ${(menu == "components") ? 'class="active"' : ''}><strong>Components</strong></a></li>
		</c:if>
		
		<c:if test="${user.company != null}">
			<c:if test="${company.id == 127 and (user.userType.value ne 'Company Staff' or user.id eq 172 or user.id eq 173 or user.id eq 174 or user.id eq 175 or user.id eq 176 or user.id eq 177)}">
				<li>
					<a onclick="loading();" href="trustproductstatistics.do" ${(menu == "productstatistics") ? 'class="active"' : ''}>
						<strong>
						Rates 
						
						</strong>
					</a>
				</li>
			</c:if>
		</c:if>
		</c:when>
		
		</c:choose> <!-- end if not normal user -->
		
		
		<!-- Raffle Entries -->
		<c:if test="${user.company != null}">
			<c:if test="${company.id eq 278 }">
				<li>
					<a onclick="loading();" href="watsonkiddiemembers.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Raffle Entries</strong></a>
				</li>
			</c:if>

			<c:if test="${company.id eq 289 }">
				<li>
					<a onclick="loading();" href="watsonaaamembers.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Raffle Entries</strong></a>
				</li>
			</c:if>
			
			<c:if test="${company.id eq 300 }">
				<li>
					<a onclick="loading();" href="watsonglammembers.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Raffle Entries</strong></a>
				</li>
			</c:if>
			
			<c:if test="${company.id eq 305 }">
				<li>
					<a onclick="loading();" href="watsonstylemembers.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Raffle Entries</strong></a>
				</li>
			</c:if>
			
			<c:if test="${company.id eq 309 }">
				<li>
					<a onclick="loading();" href="watsonsusdmembers.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Raffle Entries</strong></a>
				</li>
			</c:if>
		</c:if>
		
		<c:if test="${company.name eq 'skechers'}">
			<li>
				<a onclick="loading();" href="skechersraffle.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Raffle Entries</strong></a>
			</li>
		</c:if>
		<!-- <c:if test="${company.name eq 'hhasia2'}">
			<li>
				<a onclick="loading();" href="hhasiaraffle.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Raffle Entries</strong></a>
			</li>
		</c:if> -->
		
		<!--  
		
		<c:if test="${company.name eq 'bluewarner'}">
			<li>
				<a onclick="loading();" href="bluewarnerraffle.do" ${(menu == "raffleentries") ? 'class="active"' : ''}><strong>Entries</strong></a>
			</li>
		</c:if>
		
		-->
		
		
		<c:if test="${user.company != null}">
			<c:if test="${user.company.companySettings.hasClientChat == true}">
				<c:if test="${user.userType.value eq 'Company Staff' or user.userType.value eq 'Company Administrator'}">
					<li id="chatnavmenu">
						<a href="#" onclick="JavaScript:openChatWindow()" onMouseOver="window.status='Status Bar Message';return true" onMouseOut="window.status=''; return true">
							<strong>Chat</strong>
						</a>
					</li>
				</c:if>
				<c:if test="${user.userType.value eq 'Normal User'}">
					<li id="chatnavmenu">
						<a href="chatboard.do" onMouseOver="window.status='Status Bar Message';return true" onMouseOut="window.status=''; return true">
							<strong>Chat</strong>
						</a>
					</li>
				</c:if>
			</c:if>
		</c:if>
		
		<c:if test="${user.company != null}">
			<c:if test="${company.id eq 362 }">
				<li><a onclick="loading();" href="kuysenQuotations.do?page=1" ${(menu == "quotation") ? 'class="active"' : ''}><strong>Quotations</strong></a></li>
			</c:if>
		</c:if>
		
		<c:if test="${company.name eq 'gurkkatest'}">
			<li><a onclick="loading();" href="chatMessage.do" ${(menu == "chatmessage") ? 'class="active"' : ''}><strong style="display:inline;">Chat Message</strong>
				<span id="newChatMessageCount" style="
			    display: inline;
			    padding: 5px;
			    background: red;
			    color: white;
			    border-radius: 7px 7px 7px 7px;
			             ">0</span>
			</a></li>
		</c:if>
		<c:if test="${company.name eq 'rockwell'}">
			<li><a onclick="loading();" href="chatMessage.do" ${(menu == "chatmessage") ? 'class="active"' : ''}><strong style="display:inline;">Chat Message</strong>
				
			</a></li>
		</c:if>
		
		</ul> 
	</div>
	<div class="clear"></div>
	<div class="navSubMenu clearfix">
	<c:choose>
	
		<c:when test="${menu == 'companies'}">
			<ul class="subNav">
				<li><a href="<s:url action="companies" />" ${(submenu == "company_listing") ? 'class="active"' : ''}> Company Listing</a></li>	
			</ul>			
		</c:when>
		
		<c:when test="${menu == 'users'}">
			<ul class="subNav">
				<li><a href="<s:url action="users.do" />" ${(submenu == "user_listing") ? 'class="active"' : ''}> User Listing</a>	</li>
			</ul>			
		</c:when>
		
		<c:when test="${menu == 'pages'}">
			<c:if test="${not empty menulist}">
				<ul class="subNav">
				<c:set var="c" value="0" />
					<c:forEach items="${menulist}" var="menu">
					<c:set var="c" value="${c+1}" />
					<c:choose>
	    				<c:when test="${company.name eq 'neltex'}">
	    					<c:choose>
	    						<c:when test="${user.userType.value == 'Company Administrator' && fn:containsIgnoreCase(user.username, 'hr')}">
	    							<c:if test="${menu.name eq 'Careers' or menu.name eq 'Testimonials'}">
	    								<li ${(c == fn:length(menulist)) ? 'class="last"' : ''}><a href="${menu.url}" ${(submenu == fn:escapeXml(menu.name)) ? 'class="active"' : ''}>${fn:escapeXml(menu.name)}<c:if test="${menu.pageType != 'single'}">&#8317;&#8314;&#8318;</c:if>
	    							</c:if>
	    						</c:when>
	    						<c:when test="${!fn:containsIgnoreCase(menu.name, 'Home')}">
								<li ${(c == fn:length(menulist)) ? 'class="last"' : ''}><a href="${menu.url}" ${(submenu == fn:escapeXml(menu.name)) ? 'class="active"' : ''}>${fn:escapeXml(menu.name)}<c:if test="${menu.pageType != 'single'}">&#8317;&#8314;&#8318;</c:if>
								</a></li>
								</c:when>
							</c:choose>
						</c:when>
						<c:otherwise>
							<li ${(c == fn:length(menulist)) ? 'class="last"' : ''}><a href="${menu.url}" ${(submenu == fn:escapeXml(menu.name)) ? 'class="active"' : ''}>${fn:escapeXml(menu.name)}<c:if test="${menu.pageType != 'single'}">&#8317;&#8314;&#8318;</c:if>
							</a></li>
						</c:otherwise>
					</c:choose>
					
					</c:forEach>
					
				</ul>
			</c:if>
		</c:when>
		
		<c:when test="${menu == 'events' or menu == 'eventgroups'}">
			<ul class="subNav">
				<c:if test="${user.company != null and user.userType.value =='Super User' or user.userType.value =='WTG Administrator'}">
					<li><a href="events.do" ${(menu == "events") ? 'class="active"' : ''}>Items</a></li>
					<li><a href="eventgroups.do" ${(menu == "eventgroups") ? 'class="active"' : ''}>Groups</a></li>
				</c:if>
			</ul>
		</c:when>
		
		<c:when test="${menu == 'forms'}">
			<ul class="subNav">
				<c:if test="${company.id ne 181 or user.userType.value ne 'Company Staff'}">
					<li><a href="formsubmissions.do" ${(submenu == 'formsubmissions') ? 'class="active"' : ''}>Submissions</a> </li>
				</c:if>
				<c:if test="${company.id eq 150 || company.id eq 190 || company.id eq 194 || company.id eq 199}">
					<li><a href="registration.do" ${(submenu == 'registrants') ? 'class="active"' : ''}>Registrations</a> </li>| 
				</c:if>
				<c:if test="${company.id eq 413 }">
					<li><a href="registrations.do" ${(submenu == 'registrations') ? 'class="active"' : ''}>Registrations</a> </li>| 
				</c:if>
				<c:if test="${company.name eq 'noelle'}">
					<li><a href="reservations.do" ${(submenu == 'reservations') ? 'class="active"' : ''}>Reservations</a> </li>| 
				</c:if>
				
				<c:choose>
					<c:when test="${fn:length(formPageList) > 0}">
						<c:forEach items="${formPageList}" var="form">
							<c:if test="${user.userType.value =='Super User' or user.userType.value =='WTG Administrator' or user.userType.value == 'Company Administrator'}">
								<li><a href="${form.url}" ${(submenu == form.name) ? 'class="active"' : ''}>${form.name}</a> </li>
							</c:if>
							<c:if test="${user.userType.value ne 'Super User' and user.userType.value ne 'WTG Administrator' and (form.name eq 'Submissions' or form.name eq 'thank you' or form.name eq 'contact us' or form.name eq 'acknowledgement' or form.name eq 'Thank You' or form.name eq 'Contact Us' or form.name eq 'Acknowledgement')}">
								<li><a href="${form.url}" ${(submenu == form.name) ? 'class="active"' : ''}>${form.name}</a> </li>
							</c:if>
						</c:forEach> 
					</c:when>
				</c:choose> 
				
				<c:if test="${company.id eq 256}">
					<li><a href="bullrunregistrations.do" ${(submenu == 'bullrunregistration') ? 'class="active"' : ''}>Bullrun Online Registrations</a></li>
				</c:if>
				
			</ul>
		</c:when>
		
		<c:when test="${menu == 'account'}">
			<ul class="subNav">
			<li><a href="billing.do" ${(submenu == 'billing') ? 'class="active"' : ''}>Billing</a></li>
			
			<li><a href="payment.do" ${(submenu == 'payment') ? 'class="active"' : ''}>Payment</a></li>
			</ul>
		</c:when> 
		
		<c:when test="${menu == 'groupItem'}">
			<ul class="subNav">
			<li><a href="items.do?group_id=${group.id}" ${(submenu == 'items') ? 'class="active"' : ''}>Items</a></li>
			
			<li><a href="category.do?group_id=${group.id}" ${(submenu == 'categories') ? 'class="active"' : ''}>Categories</a></li>
			
			<c:if test="${group.hasBrands}">
				
				<li><a href="brands.do?group_id=${group.id}" ${(submenu == 'brands') ? 'class="active"' : ''}>Brands</a></li>
			</c:if>
			
			<c:if test="${group.hasAttributes}">
				 
				<li><a href="attributes.do?group_id=${group.id}" ${(submenu == 'attributes') ? 'class="active"' : ''}>Attributes</a></li>
			</c:if>
			</ul>
		</c:when>
		
		<c:when test="${menu == 'variations'}">
			<ul class="subNav">
			
			<li><a href="variationGroup.do" ${(submenu == 'variation_group') ? 'class="active"' : ''}>Variation Group</a></li>
			<li><a href="variation.do" ${(submenu == 'variation_group_items') ? 'class="active"' : ''}>Variation Group Items</a></li>
			
			</ul>
		</c:when>
		
		<c:when test="${menu == 'activities'}">
			<c:choose>
			<c:when test="${user.userType.value eq 'Super User' and company.name eq 'agian' }">
				<ul class="subNav">
			
					<li><a href="activities.do" ${(submenu == 'company_activities') ? 'class="active"' : ''}>Company Activities</a></li>
					<li><a href="logs.do" ${(submenu == 'company_logs') ? 'class="active"' : ''}>Company Logs</a></li>
					<c:if test="${company.name ne 'ibpmakati'}">
						<li><a href="memberLogs.do" ${(submenu == 'member_logs') ? 'class="active"' : ''}>Member Logs</a></li>
					</c:if>
					<li><a href="portalactivities.do" ${(submenu == 'agian_portal_activities') ? 'class="active"' : ''}>Portal Activities</a></li>
				</ul>
			</c:when>
			<c:when test="${user.userType.value ne 'Super User' and company.name eq 'agian' }">
				<ul class="subNav">
			
					<li><a href="logs.do" ${(submenu == 'company_logs') ? 'class="active"' : ''}>Company Logs</a></li>
					<c:if test="${company.name ne 'ibpmakati'}">
						<li><a href="memberLogs.do" ${(submenu == 'member_logs') ? 'class="active"' : ''}>Member Logs</a></li>
					</c:if>
					<li><a href="portalactivities.do" ${(submenu == 'agian_portal_activities') ? 'class="active"' : ''}>Portal Activities</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="subNav">
			
				<li><a href="activities.do" ${(submenu == 'company_activities') ? 'class="active"' : ''}>Company Activities</a></li>
				<li><a href="logs.do" ${(submenu == 'company_logs') ? 'class="active"' : ''}>Company Logs</a></li>
				<c:if test="${company.name ne 'ibpmakati'}">
					<li><a href="memberLogs.do" ${(submenu == 'member_logs') ? 'class="active"' : ''}>Member Logs</a></li>
				</c:if>
				<c:if test="${company.name eq 'hpds'}">
					<li><a href="testLogs.do" ${(submenu == 'test_logs') ? 'class="active"' : ''}>Test Logs</a></li>
				</c:if>
				</ul>
			</c:otherwise>
			</c:choose>
		</c:when>
		
		<c:when test="${menu == 'comments'}">
			<ul class="subNav">
				<li><a href="comment.do" ${(submenu == 'comment') ? 'class="active"' : ''}>All Comments</a></li>
				<li><a href="comment.do?commentType=PAGE" ${(submenu == 'pagecomment') ? 'class="active"' : ''}>Pages</a></li>			
				<li><a href="comment.do?commentType=ITEM" ${(submenu == 'itemcomment') ? 'class="active"' : ''}>Items</a></li>
			</ul>
		</c:when>
		
		<c:when test="${(menu == 'order' and company.name eq 'ecommerce') or (menu == 'order' and company.name eq 'drugasia') 
					or (menu == 'order' and company.name eq 'mdt') or (menu == 'order' and company.name eq 'onlinedepot')
		 			or (menu == 'order' and company.name eq 'mraircon')
		 			or (menu == 'order' and company.name eq 'ayrosohardware')}">
			<ul class="subNav">
				<li><a href="order.do" ${(submenu=='order') ? 'class="active"' : ''}>Order</a></li>
				<li><a href="area.do" ${(submenu=='area') ? 'class="active"' : ''}>Area</a></li>
				<li><a href="arealocation.do" ${(submenu=='arealocation') ? 'class="active"' : ''}>Location</a></li>
			</ul>
		</c:when>
		<c:when test="${menu == 'members'}">
			<ul class="subNav">
				<c:if test="${company.id ne 278 }"><!-- WatsonKiddieCare -->
					<li><a href="members.do" ${(submenu == 'member_listing') ? 'class="active"' : ''}>All Members</a></li>
				</c:if>
				<c:if test="${ company.companySettings.hasMemberFiles}">
					<li><a href="memberfiles.do" ${(submenu == 'memberfiles') ? 'class="active"' : ''}>Member Files</a></li>			
				</c:if>
				
				<c:if test="${company.id eq 270 }"> <!--  watsons -->
					<li><a href="watsonmembers.do" ${(submenu == 'watsonmembers') ? 'class="active"' : ''}>Member Details</a></li>
				</c:if>
				
				
				<c:if test="${company.name eq 'WatsonKiddieCare' }"> <!--  watsons kiddie-->
					<li><a href="watsonkiddiemembers.do" ${(submenu == 'watsonkiddiemembers') ? 'class="active"' : ''}>Member Details</a></li>
				</c:if>
				
				<%-- 
				<c:if test="${ company.companySettings.hasReferrals}">
					<li><a onclick="loading();" href="referrals.do" ${(submenu == "referrals") ? 'class="active"' : ''}>Referrals</a></li>
				
				</c:if>
				--%>
				
			</ul>
			
		</c:when>
		
		
	</c:choose>
</div>
	<div class="clear"></div>
	
	<c:if test="${(company.name eq 'gurkkatest') or (company.name eq 'rockwell')}">
		<form name="loadNewChatMessages" id="loadNewChatMessages" class="loadNewChatMessages" method="post" enctype="multipart/form-data" action="loadNewChatMessages.do">
			<!-- 
			<input type="hidden" name="status" id="membermessagestatus" class="membermessagestatus" value="" />
			<input type="hidden" name="remarks" id="membermessageremarks" class="membermessageremarks" value="" />
			<input type="hidden" name="messageType" id="membermessagetype" class="membermessagetype" value="" />
			 -->
			<input type="hidden" name="content" id="content" class="content" value="" />
		</form>
		<script>
			
			window.onload = function() {
				dragsort.makeListSortable(document.getElementById("phoneticlong"),
						verticalOnly, saveOrder)
						
	    			var auto_refresh_count = setInterval(
	    					function() {
	    						loadNewChatMessages();
	    					}
	    					,7000);
	    		
	    	}
		
			/*
			window.onload = function() {
    		<c:if test="${selectedMember ne null}">
    			var auto_refresh = setInterval(
    					function() {
    						loadChatMessages("${selectedMember.id}", "${GurkkaConstants.NEW}");
    					}
    					,7000);
    		</c:if>
    	}
			*/
			
			function loadNewChatMessages(){
				
				$.ajax({
					type: $('#loadNewChatMessages').attr('method'),
					url: $('#loadNewChatMessages').attr('action'),
					cache: false,
					dataType: "json",
					async: true,
					data: $('#loadNewChatMessages').serialize(),
					success: function(data) {
						
						if(data.listNewChatMessages[0].errorMessage != null){
							$('#newChatMessageCount').hide();
						}
						else{
							
							$('#newChatMessageCount').html(data.listNewChatMessages[0].messageCount);
							if(data.listNewChatMessages[0].messageCount == "0" || data.listNewChatMessages[0].messageCount == "" || data.listNewChatMessages[0].messageCount == null){
								$('#newChatMessageCount').hide();
							}
							else{
								$('#newChatMessageCount').show();
							}
							
						}
					},
					complete: function() {
						
					},
					error: function(xhr, textStatus, error){
						
					}
				});
			}
		</script>
	
	</c:if>
	