<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="attributes" />
 
<body>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  		 	 
			  	<div id="companyhome"> 
			  		 
			  	<h3>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
					<a href="groups.do">
					Group : ${group.name}
					</a>
					
					>
				</c:if>
				<a href="attributes.do?group_id=${group.id}">Attributes</a> >					 
				Edit Attribute
				</h3>
			  		 
				<c:set var="formAction" value="updateattribute.do" />
				<%@include file="attributesform.jsp" %>
				
				</div>
				
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
