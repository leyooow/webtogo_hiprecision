
<%@include file="includes/header.jsp"%>
<body>
	<c:set var="menu" value="packages" />
	<c:set var="pagingAction" value="packages.do" />
	<div class="container">
		<%@include file="includes/bluetop.jsp"%>
		<%@include file="includes/bluenav.jsp"%>
		<script language="javascript" src="../javascripts/overlib.js"></script>
		<div class="contentWrapper" id="contentWrapper">
			<div class="mainContent">
				<s:actionmessage />
				<s:actionerror />
				<c:if test="${param['evt'] != null or not empty message}">
					<ul>
						<c:if test="${param['evt'] == 'save'}">
							<li><span class="actionMessage">Package was successfully created.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Package was successfully updated.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Package was successfully deleted.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'sort'}">
							<li><span class="actionMessage">Display order of the packages was successfully updated.</span></li>
						</c:if>
						<c:if test="${not empty message}">
							<li><span class="actionMessage">${message}</span></li>
						</c:if>
					</ul>
				</c:if>
				<div class="pageTitle">
					<h1>
						<strong>Packages</strong>
					</h1>
					</form>
					<div class="clear"></div>
				</div>
				<!--//pageTitle-->
				<ul class="pagination">
					<%@include file="includes/pagingnew.jsp"%>
				</ul>
				<div class="clear"></div>
				<table width="100%" border="0">
					<tr>
						<td>
							<div style="float: right;">
								[<a href="#packageForm">Add Package</a>]
							</div>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr>
						<th>Name</th>
						<th>SKU</th>
						<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'  or user.userType.value == 'Company Staff'}">
							<th align="right">Action</th>
						</c:if>
					</tr>
					<c:set var="count" value="0" />
					<%-- <c:forEach items="${groupList}" var="g">  --%>
					<c:forEach items="${packages}" var="pakeyds">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
							<c:set var="count" value="${count+1}" />
							<td>${pakeyds.name}</td>
							<td>${pakeyds.sku}</td>
							<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Staff'}">
								<td align="right">
									<a href="editpackage.do?package_id=${pakeyds.id}">Edit</a>
									 | 
									<c:if test="${user.userType.value != 'Company Staff'}">
										<a href="deletepackage.do?package_id=${pakeyds.id}" onclick="return confirm('Do you really want to delete this package?');">Delete</a>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<ul class="pagination">
					<%@include file="includes/pagingnew.jsp"%>
				</ul>
				
				<c:if test="${user.userType.value == 'Company Administrator' or user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
					<div>
						<a name="packageForm" />
						<c:if test="${user.company.companySettings.hasPackages}">
							<c:set var="formAction" value="savepackage.do" />
							<%@include file="packageform.jsp"%>
						</c:if>
					</div>
				</c:if>
			</div>
			<!--//mainContent-->
		</div>
		<div class="clear"></div>
	</div>
	<!--//container-->
</body>
</html>
<%--
<%@include file="includes/header.jsp"%>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />
<c:set var="pagingAction" value="packages.do" />
<c:set var="menu" value="packages" />
<c:set var="submenu" value="packages" />
<script>
	function submitComboBox(myCombo) {
		return false;
	}
</script>
<body>
	<div class="container">
		<%@include file="includes/bluetop.jsp"%>
		<%@include file="includes/bluenav.jsp"%>
		<script language="javascript" src="../javascripts/overlib.js"></script>
		<div class="contentWrapper" id="contentWrapper">
			<div class="mainContent">
				<s:actionmessage />
				<s:actionerror />
				<h3>Packages</h3>
				<c:if test="${param['evt'] != null}">
					<ul>
						<c:if test="${param['evt'] == 'save'}">
							<li><span class="actionMessage">Package was successfully created.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Package was successfully updated.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Package was successfully deleted.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'sort'}">
							<li><span class="actionMessage">Display order of the packages was successfully updated.</span></li>
						</c:if>
					</ul>
				</c:if>
				<table width="100%" border="0">
					<tr>
						<td>
							<div style="float: left;"><%@include file="includes/paging.jsp"%></div> <c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
								<div style="float: right;">
									[<a href="#packageForm">Add Package</a>]
								</div>
							</c:if>
						</td>
					</tr>
				</table>
				<table>
					<tr class="headbox">
						<th width="30%">Package Name</th>
						<!--  <th width="5%">Package Code</th>-->
						<th width="5%">SKU</th>
						<th width="5%">Parent Group</th>
						<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'  or user.userType.value == 'Company Staff'}">
							<th width="30%">Action</th>
						</c:if>
					</tr>
					<c:forEach items="${packages}" var="g">
						<tr>
							<td><a href="editpackage.do?package_id=${g.id}">${g.name}</a></td>
							<td>${g.sku}</td>
							<td>${g.parentGroup.name}</td>
							<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Staff'}">
								<td><a href="editpackage.do?package_id=${g.id}">View</a> | <a href="editpackage.do?package_id=${g.id}">Edit</a> | <c:if test="${user.userType.value != 'Company Staff'}">
										<a href="deletepackage.do?package_id=${g.id}" onclick="return confirm('Do you really want to delete this package?');">Delete</a>
									</c:if></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<table width="100%" border="0">
					<tr>
						<td style="border: 0px;">
							<div style="float: left;"><%@include file="includes/paging.jsp"%></div>
						</td>
					</tr>
				</table>
				<br> <br>
				<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
					<a name="packageForm" />
					<c:set var="formAction" value="savepackage.do" />
					<c:if test="${user.company.companySettings.hasPackages}">
						<%@include file="packageform.jsp"%>
					</c:if>
				</c:if>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
<%@include file="includes/footer.jsp"%>

 --%>