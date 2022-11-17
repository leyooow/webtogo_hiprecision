<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="categories" />
<c:set var="pagingAction" value="category.do?group_id=${group.id}" />
<c:set var="firstParamGiven" value="${true}" />


<script>
function submitComboBox(myCombo) {
	var selectedId = myCombo.value;
	if(selectedId == 'all') {
		window.location = 'category.do';
	}
	else {
		window.location='category.do?group_id=${group.id}&category_id=' + selectedId;
	}
}
</script>

<script language="javascript" src="../javascripts/upload.js"></script>

<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(item.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />


<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>


<script language="javascript" src="../javascripts/multifile.js"></script>

<script>

	function submitForm(myForm) {
		
		var name = getElement("category_name");
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			error = true;
			messages += "Category name was not given.";
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
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}

	function updateImageTitle(id, title) {
		DWRImageAction.updateImageTitle("category", id, title);
	}
	
	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("category", id, caption);
	}
	
	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("category", id, description);
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
								<li><span class="actionMessage">Category was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'categoryexist'}">
								<li><span class="actionMessage">Category was not created because a similar category already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'maxcategories'}">
								<li><span class="actionMessage">Category was not created because a the maximum number of categories allocated is reached.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Category was sucessfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Category was sucessfully deleted.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'sort'}">
								<li><span class="actionMessage">Display order of the items was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'noItem'}">
								<li><span class="actionMessage">Selected category has no item.</span></li>
							</c:if>							
														
						</ul>
						
					</c:if>

	  <div class="pageTitle">

	    <h1><strong>Categories</strong></h1>
	    <ul>
	    	<li>[<a href="#addCategory">Add Category</a>]</li>
	    </ul>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
		

					<tr >
						<th>Category Name</th> 
						<th >Parent Category</th>
						<th >Hidden</th>
						<th >Number of Sub - Categories</th> 
						<th >Number of Items</th>
						 <th >Display Order</th>
						<th >Action</th>
					</tr>
					 <c:set var="count" value="0" />
					<c:forEach items="${categories}" var="category">
						<c:choose>
							<c:when test="${company.name eq 'gurkkatest' and group.name eq GurkkaConstants.COCKTAILS_GROUP_NAME}">
								
								<c:if test="${category.id ne GurkkaConstants.COCKTAILS_CATEGORY_COCKTAILITEM_ID}">
									
									<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
									<c:set var="count" value="${count+1}" />
											<td>${category.name}</td>
											<td>${category.parentCategory.name}</td>
											<td>
											<c:choose>
												<c:when test="${ (category.hidden) }">
													<b>Yes</b>
												</c:when>
												<c:otherwise>
													No
												</c:otherwise>
											</c:choose>
											
											</td> 
											<td>
												<c:if test="${not empty category.childrenCategory}">
													${fn:length(category.childrenCategory)}
												</c:if>
											</td>
											<td>
												<c:if test="${not empty category.items}">
													${fn:length(category.items)}
												</c:if>
											</td> 
											<td>${category.sortOrder}</td>
											<td>
										
											<a href="editcategory.do?group_id=${group.id}&category_id=${category.id}">Edit</a>
											<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
											 | <a href="deletecategory.do?group_id=${group.id}&category_id=${category.id}" onclick="return confirm('Do you really want to delete this category?');">Delete</a>
											 <c:if test="${group.sortType=='manualSort' || group.sortType == null}">| <a href="sortItems.do?group_id=${group.id}&category_id=${category.id}">Sort</a></c:if>
											</c:if>
											</td>
											
										</tr>
									
								</c:if>
								
							</c:when>
							<c:otherwise>
								
								<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
								<c:set var="count" value="${count+1}" />
										<td>${category.name}</td>
										<td>${category.parentCategory.name}</td>
										<td>
										<c:choose>
											<c:when test="${ (category.hidden) }">
												<b>Yes</b>
											</c:when>
											<c:otherwise>
												No
											</c:otherwise>
										</c:choose>
										
										</td> 
										<td>
											<c:if test="${not empty category.childrenCategory}">
												${fn:length(category.childrenCategory)}
											</c:if>
										</td>
										<td>
											<c:if test="${not empty category.items}">
												${fn:length(category.items)}
											</c:if>
										</td> 
										<td>${category.sortOrder}</td>
										<td>
									
										<a href="editcategory.do?group_id=${group.id}&category_id=${category.id}">Edit</a>
										<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
										 | <a href="deletecategory.do?group_id=${group.id}&category_id=${category.id}" onclick="return confirm('Do you really want to delete this category?');">Delete</a>
										 <c:if test="${group.sortType=='manualSort' || group.sortType == null}">| <a href="sortItems.do?group_id=${group.id}&category_id=${category.id}">Sort</a></c:if>
										</c:if>
										<c:if test="${user.userType.value == 'System Administrator' or user.userType.value == 'Events Administrator'}">
											 | <a href="deletecategory.do?group_id=${group.id}&category_id=${category.id}" onclick="return confirm('Do you really want to delete this category?');">Delete</a>
											</c:if>
										</td>
										
									</tr>
								
							</c:otherwise>
						</c:choose>
						
						
						
						
						
						
						
																				
					</c:forEach>
					
				</table> 	
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
		<h2>Notice</h2>
	<ul class="uploadImageNote">
			<li> You are only allowed to create ${group.maxCategories} numbers of categories </li>
	  </ul>	
	</c:if>  		
	</div><!--//sidenav-->
	
	<div class="mainContent">
	  <div class="pageTitle">
<a name="addCategory"></a>
	    <h1><strong>New Category</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->
	<c:set var="formAction" value="savecategory.do" />
			<c:set var="newCategory" value="${true}" />
			<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<c:if test="${newCategory != true && category.parentCategory != null}">
		<input type="hidden" name="parent_id" value="${category.parentCategory.id}" />
		</c:if>
		
		<input type="hidden" name="group_id" value="${group.id}" />
		
		<c:if test="${newCategory != true && category.id > 0}">
		<input type="hidden" name="category_id" value="${category.id}" />
		</c:if> 
		
		<table width="50%">
					
			
			<tr>
				<td class="label">Parent Category ${categoryparent_id}</td>
						
			<td>
						<select name="categoryparent_id" class="w385">
								<option value="0"> -- SELECT A CATEGORY -- </option>
								<c:forEach items="${categories}" var="cat">
									<c:if test="${category.id ne cat.id}"><option value="${cat.id}" <c:if test="${cat.id == categoryparent_id}">selected</c:if>>${cat.name} ${cat.descriptor}</option></c:if>
								</c:forEach>
							
						</select>
			
			</td>
			</tr>
			
			
			
			<tr>
				<td class="label">Category Name</td>
				
				<td><input type="text" id="category_name" name="category.name" value="${(newCategory == true) ? '' : category.name}" class="w385" /></td>
			</tr>
			
			<tr>
				<td class="label">Sort Order</td>
				
				<td><input type="text" id="category.sortOrder" name="category.sortOrder" value="${(newCategory == true) ? '' : category.sortOrder}" class="w385" /></td>
			</tr>
			
			<c:if test="${company.name eq 'gurkkatest' and group.name eq 'Products'}">
			<tr>
				<td valign="top" class="label"></td>
			
				<td>
					<s:checkbox name="category.flag1" value="%{category.flag1}" theme="simple" />&nbsp;<i><b>Show in main products filter</b></i><br>
						<s:checkbox name="category.flag2" value="%{category.flag2}" theme="simple" />&nbsp;<i><b>Show in guest products filter</b></i> <br>
				
				</td>
			</tr>
			</c:if>
			
			<tr>
				<td valign="top" class="label"></td>
			
				<td>
					
				<s:checkbox name="category.hidden" value="%{category.hidden}" theme="simple" /> <i><b>Check to disable this category</b></i>
				
				</td>
			</tr>
			
			<tr>
				<td valign="top" class="label">Category Description</td>
				
					<td>
				 <textarea id="category.description" name="category.description" >${category.description}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'category.description');
			</script>
			</td>
			</tr>
			
			<tr id="fileList" style="display:none;">
				<td colspan="2"><br/><h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
		  <td>
							<div id="attachment" class="attachment" style="display: none">
								<div id="dropcap" class="dropcap">1</div>
								<input id="file" name="upload" type="file" size="30"/> <br />
								<!-- 
								    <input id="desc" name="alt" type="text" size="30"/>
								    &nbsp;
								    -->
								<a href="#" onclick="javascript:removeFile(this.parentNode.parentNode,this.parentNode);">Remove</a>
							</div>
							<div id="attachments" class="container">
								<!--
								    **************************************************************************
								    Below is the call to the "addUpload" function which adds an Attachment
								    section to the form. Customize the function parameters as needed:
								    1) Maximum # of attachments allowed in this pub type
								    2) base Field Name for the Description fields in your pub type
								    3) base Field Name for the File Upload field in your pub type
								    **************************************************************************
								    -->
								<br />
								<a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload')">Attach a file</a><br />
								<br /> <span id="attachmentmarker"></span>
							</div>
							</td>
					</tr>
				</table>
				</td>
		</tr>
		
			
			
			<tr>
				<td colspan="2" style="border: 0px;">
				
				
				<c:choose>
					<c:when test="${newCategory != true && category.id > 0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" class="btnBlue" onclick="window.location='category.do?group_id=${group.id}'">
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
</div>

	<div class="clear"></div>

  </div><!--//container-->

 <script type="text/javascript">
		<c:if test="${company.name eq 'neltex'}">
			document.getElementById('fileList').style.display = 'table-row';
		</c:if>
	</script>
	
</body>

</html>