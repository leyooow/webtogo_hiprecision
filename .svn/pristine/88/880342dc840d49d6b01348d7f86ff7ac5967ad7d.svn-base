

<%@include file="includes/header.jsp"  %>

<body>

  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>

<div class="contentWrapper" id="contentWrapper">
	
	<c:if test="${customFieldDownload == null}">	
    <div class="mainContent">
	  <s:actionmessage />
	  <s:actionerror />
	  <div class="pageTitle">

	  <h1><strong>Components</strong></h1>
	
						
	  <div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			<div class="clear"></div>
			<c:if test="${empty component.id}">
	 			<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
	
					<tr >
						<th>Category</th>
						<th >Name</th>
						<th width="25%">Action</th>
					</tr>
						<c:set var="count" value="0" />
					<c:forEach items="${componentList}" var="component">
						<tr>
							<td>${component.category.name }</td>
							<td>${component.name }</td>
							<td><a href="components.do?componentId=${component.id }">Edit</a> | <a href="deletecomponent.do?componentId=${component.id }">Delete</a></td>
						</tr>
					</c:forEach>
												
				</table>
			</c:if>
	<ul class="pagination">
		<%@include file="includes/pagingnew.jsp"  %>
	</ul>
	<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
		<div class="clear"></div>
		<%@include file="includes/componentform.jsp"  %>
		<%@include file="includes/componentjavascripts.jsp"  %>
	</c:if>
	</div><!--//mainContent-->
	</c:if>
	<div class="sidenav">	
		<div style="background: #f1f3f5; border: 1px solid #e0e0e0;width:300;">
			
			<h4>Category</h4>
			<ul id="categoryList">
				<c:forEach items="${componentCategoryList }" var="category">
					<li>
						<a href="javascript:void(0);" onclick="jQuery(this).toggle(); jQuery(this).next().toggle();">${category.name }</a>
						<div style="display:none;" >
							<input type="hidden" value="${category.id }"/>
							<input type="text" value="${category.name }"/>
							<a href="javascript:void(0);" onclick="updateCategory(this);">Save</a>
						</div>
					</li>
				</c:forEach>
			</ul>
			<p style="display:none;" id='categoryBox'>
				<input type="hidden" id="companyId" value="${company.id }"/>
				<input type="text" id="categoryName"/>&nbsp;
				<a href="javascript:void(0);" onclick="addCategory();">Save</a>&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="toggleAdd();">Cancel</a>
			</p>
			<p align="center" id="addLink">
				<a href="javascript:void(0);" onclick="toggleAdd();">Add</a>
			</p>&nbsp;
		</div>
	</div>
</div><!--//sidenav-->
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>