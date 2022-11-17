<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />


<body>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
				<div id="companyhome">

				<h3>
				
				Category : ${parent}
				
				</h3>

 				<table width="100%" border="0">
 					

					<tr class="headbox">
						<th width="70%">Category Item Name</th> 
						<th width="30%">Display Order</th>
					</tr>
					 <form action="updateCategoryItemSorting.do" method="post">
					<c:forEach items="${catItems}" var="category">
						<tr>
							<td>${category.name}</td>
						
							<td>
								<input type="hidden" name="catId" value="${category.id}">
								<input type="text" style="width:100px" size="5" name="sortOrder" value="${category.sortOrder}">

							</td>
						
						</tr>
																				
					</c:forEach>
					<tr>
					<c:choose>
						<c:when test="${empty catItems}">
						<td colspan="4">&nbsp;</td>
						</c:when>
						<c:otherwise>
						<td colspan="1">&nbsp;</td><td><input type="submit" value="Update"></td>					
						</c:otherwise>
					</c:choose>					
					</tr>
					</form>
				</table> 	 		
				
				<table width="100%" border="0">
 					

					<tr class="headbox">
						<th width="70%">Childrean Category Name</th> 
						<th width="30%">Display Order</th>
					</tr>
					 <form action="updateChildrenCategorySorting.do" method="post">
					<c:forEach items="${category.childrenCategory}" var="category">
						<tr>
							<td>${category.name}</td>
						
							<td>
								<input type="hidden" name="catId" value="${category.id}">
								<input type="text" style="width:100px" size="5" name="sortOrder" value="${category.sortOrder}">

							</td>
						
						</tr>
																				
					</c:forEach>
					<tr>
					<c:choose>
						<c:when test="${empty category}">
						<td colspan="4">&nbsp;</td>
						</c:when>
						<c:otherwise>
						<td colspan="1">&nbsp;</td><td><input type="submit" value="Update"></td>					
						</c:otherwise>
					</c:choose>					
					</tr>
					</form>
				</table>
 				</div>
						
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>