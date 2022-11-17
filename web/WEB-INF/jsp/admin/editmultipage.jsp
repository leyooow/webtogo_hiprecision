<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../javascripts/upload.js"></script>
<script language="javascript" src="../javascripts/dynamicUploader.js"></script>

<c:set var="menu" value="pages" />
<c:set var="formAction" value="savesinglepage.do" />
<c:set var="edititableName" value="true" />
 
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
			  	
			  	<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
					<a href="dashboard.do">Dashboard</a> > <a href="multipage.do">Multi Page Listing</a> > Edit Multi Page
				</c:if>
				
				</h3>
				
				
				<c:set var="formAction" value="updatemultipage.do" />
				<c:set var="cancelUrl" value="multipage.do" /> 
				
				
				<%@include file="multipageform.jsp" %>
			  	
				</div> 
			
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
