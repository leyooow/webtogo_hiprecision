<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" /> 
<c:set var="submenu" value="brands" />
<c:set var="pagingAction" value="brands.do?category_id=${category.id}&group_id=${group.id}" />
<c:set var="firstParamGiven" value="${true}" />

<script language="javascript" src="../javascripts/upload.js"></script>

<c:set var="maxImage" value="1" />
<c:set var="imageCount" value="${fn:length(brand.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />

<script>
	
	function submitForm(myForm) {
		
		var name = getElement('brand_name');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Please enter a brand name\n";
			error = true;
		}
		
		if(error) {
			alert(messages);
		}
		else {
			return true;
		}
		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
</script>
<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	

<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
			<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Brand was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'brandexist'}">
								<li><span class="actionMessage">Brand was not created because a similar brand already exist.</span></li>
							</c:if>
														
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Brand was sucessfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Brand was sucessfully deleted.</span></li>
							</c:if>
						</ul>
					</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Brands</strong></h1>
	</form>
				
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
				
			
					<tr> 
						<th>Brand Name</th>
						<th >Disabled</th>
						<th >Is Featured</th>
						<th >Number of Sub - Brand</th>
						<th >Sort Order</th>
						<th>Action</th>
					</tr> 
					<c:set var="count" value="0" />
					<c:forEach items="${brands}" var="brand">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<td>${brand.name}</td>
						<td>
						
						<c:choose>
							<c:when test="${brand.disabled}">
								<b>Yes</b>
							</c:when>
							<c:otherwise>
								No
							</c:otherwise>
						</c:choose>
						
						</td>
						
						<td>
						
						<c:choose>
							<c:when test="${brand.featured}">
								<b>Yes</b>
							</c:when>
							<c:otherwise>
								No
							</c:otherwise>
						</c:choose>
						
						</td>
						<td>
							<c:if test="${not empty brand.childrenBrand}">
								${fn:length(brand.childrenBrand)}
							</c:if>
						</td>
						<td>${brand.sortOrder}</td>
						<td>
						
						<a href="editbrand.do?brand_id=${brand.id}&group_id=${group.id}">Edit</a> |
						<a href="deletebrand.do?brand_id=${brand.id}&group_id=${group.id}" onclick="return confirm('Do you really want to delete this brand?');">Delete</a>
						
						</td>
					</tr>
					</c:forEach>
					
				</table> 
					
				
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	  
	</div><!--//mainContent-->
	<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
				
	<div class="mainContent">
	
	<div class="pageTitle"> <h1><strong>New Brand</strong></h1><div class="clear"></div></div>
	 <c:set var="formAction" value="savebrand.do" />
				<c:set var="newBrand" value="${true}" />
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
				
		<input type="hidden" name="group_id" value="${group.id}" />
		<input type="hidden" name="brand_id" value="${brand.id}" />
				
		<table width="100%">
					
			<tr>
				<td>Parent Brand<br />
				<select name="parentBrandId" >
					<option value="0"> -- SELECT A BRAND -- </option>
					<c:forEach items="${allBrands}" var="parentBrand">
						<option value="${parentBrand.id}" >${parentBrand.name} ${parentBrand.descriptor}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
				
			<tr>
				<td>Brand Name<br /><input type="text" id="brand_name" name="brand.name" value="${(newBrand == true) ? '' : brand.name}" class="w200" /></td>
			</tr>
			
			<tr>
				<td>Title<br /><input type="text" id="brand_title" name="brand.title" value="${(newBrand == true) ? '' : brand.title}" class="w200" /></td>
			</tr>
			<tr>
				<td>Tagline<br /><input type="text" id="brand_tagline" name="brand.tagline" value="${(newBrand == true) ? '' : brand.tagline}" class="w200" /></td>
			</tr>
			<tr>
				<td>Url<br /><input type="text" id="brand_url" name="brand.url" value="${(newBrand == true) ? '' : brand.url}" class="w200" /></td>
			</tr>
			<tr>
				<td>Sort Order<br /><input type="text" id="sort_order" name="brand.sortOrder" value="${(newBrand == true) ? '' : brand.sortOrder}" class="w200" /></td>
			</tr>
			<tr>
				<td>Short Description<br /><input type="text" id="brand_short_description" name="brand.shortDescription" value="${(newBrand == true) ? '' : brand.shortDescription}" class="w200" /></td>
			</tr>
			
			<tr>
			
				
				<td>
					<s:checkbox name="brand.featured" value="%{brand.featured}" theme="simple" /> <i><b>This brand is featured</b></i>
					
				</td>
			</tr>
			
			<tr>
				<td>Brand Description<br />
					<textarea name="brand.description" class="w200">${brand.description}</textarea>
					<script type="text/javascript">
						CKEDITOR.replace( 'brand.description',
							{								
								height:100,width: 500								
							}
						);	
					</script>
					
				</td>
			</tr>
			<tr>
				<td>Notes<br />
					<textarea name="brand.notes" class="w200">${brand.notes}</textarea>
					<script type="text/javascript">
						CKEDITOR.replace( 'brand.notes',
							{								
								height:100,width: 500								
							}
						);	
					</script>
					
				</td>
			</tr>
						
			<tr>
				
				<td>
				
				<c:choose>
					<c:when test="${brand.id > 0}">
						<input type="submit" name="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue"> 
						<input type="button" value="Cancel" class="btnBlue" onclick="window.location='brands.do?group_id=${group.id}&category_id=${category.id}';"> 
					</c:when>
					<c:otherwise>
						<input type="submit" name="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
				
				</td>
			</tr> 
			
		</table>
		
		</form>
		
	</div>
	</c:if>

</div>
		
	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>