<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="manage_pages" />
<c:set var="submenu" value="${singlePage.name}" />

<body>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
			  	<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
				<div class="stickit">
					<a href="dashboard.do">Dashboard</a> > <a href="singlepage.do">Single Page Listing</a> > Edit Static Page
				</div>
				</c:if>
			  	
				<div id="companyhome"> 
					<h3>Edit Contact Us</h3><br>
					<c:set var="formAction" value="savesinglepage.do" />
					<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
						<c:set var="edititableName" value="true" />
					</c:if>
					<%@include file="contactuspageform.jsp" %>
				</div>
				

				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
