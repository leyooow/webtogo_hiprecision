<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

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

<c:set var="menu" value="pages" />
 
<c:choose>
	<c:when test="${singlePage.parent != null}">
		<c:set var="submenu" value="${singlePage.parent.name}" />
	</c:when>
	<c:otherwise>
		<c:set var="submenu" value="${singlePage.name}" />
	</c:otherwise>
</c:choose>

<c:if test="${singlePage.parent != null}">
	<c:set var="showBackLinks" value="true" />						
</c:if>


<body>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			 	
			  				  	
				<div id="companyhome"> 
													
					<h3>
					
					<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage != null}">
						<a href="multipage.do">Multi Pages</a>
						>
					</c:if>
					
					
					
					<c:choose>
						<c:when test="${multiPage != null}">
							<a href="editmultipagechildlist.do?multipage_id=${multiPage.id}">${multiPage.name}</a> >
						</c:when>
						<c:otherwise>
							<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
								<a href="singlepage.do">Single Pages</a> >
							</c:if>
						</c:otherwise>
					</c:choose>
						
					
											 
					Edit Page
					</h3>
					
					<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Page was successfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'updatefailed'}">
								<li><span class="actionMessage">Failed to update page.</span></li>
							</c:if>
						</ul>
					</c:if>
					
					<c:choose>
						<c:when test="${singlePage.parent != null}">
							<c:set var="formAction" value="updatemultipagechild.do" />
						</c:when>
						<c:otherwise>
							<c:set var="formAction" value="updatesinglepage.do" />
						</c:otherwise>
					</c:choose>
					
					<c:if test="${ (user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator') || (not empty multiPage.id)}">
						<c:set var="edititableName" value="true" />
					</c:if>
					
					<c:if test="${empty multiPage}">					
						<%@include file="singlepageform.jsp" %>
					</c:if>
					<c:if test="${not empty multiPage}">
						<%@include file="singlepagechildform.jsp" %>
					</c:if>
					
					</table>
					<h3>Logs</h3><br />
					<c:choose>
						<c:when test="${not empty logList}">
							<table>  
								<tr class="headbox"> 
									<th>Date</th>
									<th>Description</th>  
									<th>Username</th>
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
														<c:out value="${fn:substring(curr,0,250)}" escapeXml="false" /><a href="javascript:void(0);" onmouseover="javascript:showContent(${i});" onmouseout="return nd();">...</a>
														<div id="showContent_${i}" style="display:none">
															<c:out value="${curr}" escapeXml="false" />
														</div>
														<br />
													</c:when>
													<c:otherwise>
														<c:out value="${curr}" escapeXml="false" /> <br />
													</c:otherwise>
												</c:choose>
											</c:forTokens>
										</td>
										<td>${cp.updatedByUser.username}</td>
									</tr>
								</c:forEach>
							</table>
							<br />
							<%@include file="includes/paging.jsp"  %>
						</c:when>
						<c:otherwise>
							<br /><br /><p>No activities yet.</p>
						</c:otherwise>
					</c:choose>
				</div>
					
				</div> <!--  jobContainer -->
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
