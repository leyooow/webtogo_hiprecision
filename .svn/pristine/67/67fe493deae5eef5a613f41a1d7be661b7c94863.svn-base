<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" /> 
<c:set var="submenu" value="brands" />

<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
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

<div class="contentWrapper" id="contentWrapper">
    <div class="content">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">

	    <h1><strong>Group</strong>: Sort Brands</h1>
		
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
				
					
					<tr> 
						<th>Brand Name</th>
						<th>Action</th>
					</tr> 
					<c:set var="count" value="0" />
					<c:forEach items="${brands}" var="brand">
					<form action="updateBrandSorting.do" method="post">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<td>${brand.name}</td>
						<td>
							<input type="hidden" name="brandId" value="${brand.id}">
							<input type="text" style="width:100px" size="5" name="sortOrder" value="${brand.sortOrder}">
						</td>						
					</tr>
					</c:forEach>
					<tr>
					<c:choose>
						<c:when test="${empty brands}">
						<td>&nbsp;</td>
						</c:when>
						<c:otherwise>
						<td>&nbsp;</td><td><input type="submit" value="Update"></td>					
						</c:otherwise>
					</c:choose>					
					</tr>
					</form>					
					
				</table> 
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->

</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>