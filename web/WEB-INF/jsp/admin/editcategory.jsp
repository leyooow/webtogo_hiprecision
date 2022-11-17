<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />


	
				
<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="items" />

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="categories" />

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
	
	function updateFileTitle(id, title) {
		DWRImageAction.updateFileTitle("category", id, title);
	}
	
	function updateFileCaption(id, caption) {
		DWRImageAction.updateFileCaption("category", id, caption);
	}
	
	function updateFileDescription(id, description) {
		DWRImageAction.updateFileDescription("category", id, description);
	}
	
	
</script> 
<body>
  <div class="container">
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
<div class="contentWrapper" id="contentWrapper">
	<div class="mainContent">
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
	  <div class="pageTitle">

	    <h1><strong>Edit Category</strong></h1>


		</ul>
		
					
					
					
		<div class="clear"></div>
		
	  </div><!--//pageTitle-->
	  <c:if test="${not empty languages}">
		 <h4>Language:
						<c:if test="${category.language!= null}"> 
						${category.language.language}
						</c:if>
						<c:if test="${category.language== null}"> 
						English
						</c:if>
					</h4>
		  <div class="metaBox">
		  	<c:if test="${category.language== null}"> 
			English, 
			</c:if>
			<c:if test="${category.language != null}"> 
			<a href="editcategory.do?group_id=${param.group_id}&category_id=${param.category_id}">English</a> 
			</c:if>
		  	<c:forEach items="${languages}" var="lang">
		  		<c:if test="${lang.language ne param.language}">
				, <a href="editcategory.do?group_id=${param.group_id}&category_id=${param.category_id}&language=${lang.language}">${lang.language}</a>
				</c:if>
				<c:if test="${lang.language eq param.language}">
				, ${lang.language}
				</c:if>    
			</c:forEach>
			
		  </div>
	  </c:if>
	  
<c:set var="formAction" value="updatecategory.do" />
<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		<input type="hidden" name="language" value="${param.language}"/>
		<c:if test="${newCategory != true && category.parentCategory != null}">
		<input type="hidden" name="parent_id" value="${category.parentCategory.id}" />
		</c:if>
		
		<input type="hidden" name="group_id" value="${group.id}" />
		
		<c:if test="${newCategory != true && category.id > 0}">
		<input type="hidden" name="category_id" value="${category.id}" />
		</c:if>
				
		<c:if test="${company.companySettings.hasMetaProduct eq true}" >
			<h4>General Tag</h4>
			<div class="metaBox">				
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
				  <tr>
				    <td class="label">HTML Title</td>
					<td><input class="inputMeta" type="text" name="category.meta_title" value="${category.meta_title}" /></td>
					<td>&nbsp;</td>
					<td class="label">Meta Keywords</td>			
					<td><input class="inputMeta" type="text" name="category.meta_keywords" value="${category.meta_keywords}" /></td>
				  </tr><tr valign="top">
				    <td class="label">Meta Description</td>
					<td><textarea class="inputMeta" name="category.meta_description" >${category.meta_description}</textarea></td>			
					<td>&nbsp;</td>
				    <td colspan="5" class="label">		
						<input type="reset" value="Reset" class="btnBlue">
						<input type="submit" value="Update" class="btnBlue">	
					</td>
				  </tr>
				</table>
			</div>
		</c:if>
		
		<table width="100%">
			<c:if test="${category.language== null}"> 		
				<tr>
					<td width="1%" nowrap>Parent Category ${categoryparent_id}</td>
					<td width="10px"></td>			
					<td>
							<select name="categoryparent_id" style="width: 500px;">
									<option value="0"> -- SELECT A CATEGORY -- </option>
									<c:forEach items="${categories}" var="cat">
										<c:if test="${category.id ne cat.id}"><option value="${cat.id}" <c:if test="${cat.id == categoryparent_id}">selected</c:if>>${cat.name} ${cat.descriptor}</option></c:if>
									</c:forEach>
								
							</select>
				
					</td>
				</tr>
			</c:if>
		
			<tr>
				<td width="1%" nowrap>Name</td>
				<td width="10px"></td>
				<td><input type="text" id="category_name" name="category.name" value="${(newCategory == true) ? '' : category.name}" class="textbox3" /></td>
			</tr>
			
			<c:if test="${category.language== null}"> 
				<tr>
					<td width="1%" nowrap>Sort Order</td>
					<td width="10px"></td>
					<td><input type="text" id="category.sortOrder" name="category.sortOrder" value="${(newCategory == true) ? '' : category.sortOrder}" class="textbox3" /></td>
				</tr>
			</c:if>
			
			<c:if test="${company.name eq 'gurkkatest' and group.name eq 'Products'}">
				<tr>
					<td width="1%" nowrap>&nbsp;</td>
					<td width="10px"></td>
					<td>
						<s:checkbox name="category.flag1" value="%{category.flag1}" theme="simple" />&nbsp;<i><b>Show in main products filter</b></i><br>
						<s:checkbox name="category.flag2" value="%{category.flag2}" theme="simple" />&nbsp;<i><b>Show in guest products filter</b></i> <br>
					</td>
				</tr>
			</c:if>
			
			<%--
			<tr>
				<td width="1%" nowrap>Parent</td>
				<td width="10px"></td>
				<td>
				
				<select name="parent" style="width: 255px;">
					<c:if test="${user.userType.value == 'SUPER_USER' or user.userType.value == 'WEBTOGO_ADMINISTRATOR'}">
					<option value="0">-None-</option> 
					</c:if>
					<c:forEach items="${categoryList}" var="cat">
						<c:set var="catName" value="${fn:trim(fn:replace(cat.name, '-', ''))}" />
						<c:choose>
							<c:when test="${ (newCategory != true && category != null && category.parent != null)}">
								<option value="${cat.value}" <c:if test="${category.parent.name eq catName}">selected</c:if>>${cat.name}</option>
							</c:when> 
							<c:otherwise>
								<option value="${cat.value}">${cat.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
								
				</td>
			</tr>
			--%>
			
			<c:if test="${category.language== null}"> 
				<tr>
					<td valign="top" width="1%" nowrap></td>
					<td></td>
					<td>
						
					<s:checkbox name="category.hidden" value="%{category.hidden}" theme="simple" /> <i><b>Check to disable this category</b></i>
					
					</td>
				</tr>
			</c:if>
			
			<tr>
				<td valign="top" width="1%" nowrap>Description</td>
				<td></td>
					<td>
					
						  <textarea id="category.description" name="category.description" >${category.description}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'category.description');
			</script>			
			</tr>
			
			
			
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
				<c:choose>
					<c:when test="${newCategory != true && category.id > 0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type=button value="Cancel" class="btnBlue" onclick="window.location='category.do?group_id=${group.id}'">
					</c:when>
					<c:otherwise>
						<input type="submit" name="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
				
				</td>
			</tr>
					
		</table>
		
		</form>
	</div><!--//mainContent-->

	<div class="sidenav">
		
					
				<form method="post" action="uploadcategoryimage.do" enctype="multipart/form-data">
				
					<input type="hidden" name="parent" value="${category.parentCategory.id}" />
					<input type="hidden" name="category_id" value="${category.id}" />
					<input type="hidden" name="group_id" value="${group.id}" />
				<c:if test="${fn:length(category.images) > 0}">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>

	   <ol

	    
			<c:forEach items="${category.images}" var="img">
		<li>

		  <img src="${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}" />

		  <span><a href="#"><strong>${img.filename}</strong></a> 
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
		  <a onclick="return confirm('Do you really want to delete this image?');" href="deletecategoryimage.do?group_id=${group.id}&category_id=${category.id}&image_id=${img.id}" class="delete">[Delete]</a></span>
				
			<table>
										<tr>
											<td>Title</td>
										</tr>
										<tr>
											<td><input type="text" name="image_title_${img.id}" value="${img.title}" class="textbox4" onblur="updateImageTitle(${img.id}, this.value);"></td>
										</tr>
										<tr>
											<td>Caption</td>
										</tr>
										<tr>											
											<td><input type="text" name="image_caption_${img.id}" value="${img.caption}" class="textbox4" onblur="updateImageCaption(${img.id}, this.value);"></td>
										</tr> 
										<tr>
											<td>Description</td>
										</tr>
										<tr>
											<td>
												<textarea class="textbox4" onblur="updateImageDescription(${img.id}, this.value);">${img.description}</textarea>
											</td>
										</tr>
									</table>
									</c:if>	
		  <div class="clear"></div>

		</li>
		</c:forEach>
	  </ol>
		
	  <p class="imageNote">Click on Edit to Modify Title, Caption and Description of the image</p>
		</c:if>
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>

	  <ul class="uploadImageNote">

		<li>You are only allowed to upload ${companySettings.maxAllowedImages} files.</li>

		<li>500 x 590 is the most advisable size of image that should be uploaded.</li>

	  </ul>

	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>
			
		  <td colspan="2">
								    
								    <input id="file" name="upload" type="file" /></td>

		</tr>

	  </table>

	  <p class="uploadButtons">
	   
				<input type="submit" value="Upload" class="btnBlue">

	  
	</form>
	
	<br/>
	
	<form method="post" action="uploadcategoryfile.do" enctype="multipart/form-data">
				
					<input type="hidden" name="parent" value="${category.parentCategory.id}" />
					<input type="hidden" name="category_id" value="${category.id}" />
					<input type="hidden" name="group_id" value="${group.id}" />
				<c:if test="${fn:length(category.files) > 0}">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> File List</h3>

	  <ol>

	    
			<c:forEach items="${category.files}" var="file">
		<li>

		  <span><a href="#">
		  <c:choose>
									<c:when test="${file.fileType eq 'application/pdf'}">
										<img src="../images/icons/page_white_acrobat.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/msword'}">
										<img src="../images/icons/page_white_word.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-powerpoint'}">
										<img src="../images/icons/page_white_powerpoint.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-excel'}">
										<img src="../images/icons/page_excel.png">
									</c:when>
									<c:when test="${file.fileType eq 'text/plain'}">
										<img src="../images/icons/page_white_text.png">
									</c:when>
									<c:otherwise>
										<img src="../images/icons/page_white.png">
									</c:otherwise>
									</c:choose>
		  <strong>${file.fileName}</strong></a> 
		  <a  href="${contextParams['IMAGE_PATH']}/images/items/${file.filePath}" target="_blank">View </a> | 
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
		  <a onclick="return confirm('Do you really want to delete this file?');" href="deletecategoryfile.do?group_id=${group.id}&category_id=${category.id}&file_id=${file.id}" class="delete">[Delete]</a></span>
				
			<table>
										<tr>
											<td>Title</td>
										</tr>
										<tr>
											<td><input type="text" name="file_title_${file.id}" value="${file.title}" class="textbox4" onblur="updateFileTitle(${file.id}, this.value);"></td>
										</tr>
										<tr>
											<td>Caption</td>
										</tr>
										<tr>											
											<td><input type="text" name="file_caption_${file.id}" value="${file.caption}" class="textbox4" onblur="updateFileCaption(${file.id}, this.value);"></td>
										</tr> 
										<tr>
											<td>Description</td>
										</tr>
										<tr>
											<td>
												<textarea class="textbox4" onblur="updateFileDescription(${file.id}, this.value);">${file.description}</textarea>
											</td>
										</tr>
									</table>
									</c:if>	
		  <div class="clear"></div>

		</li>
		</c:forEach>
	  </ol>
		
	  <p class="imageNote">Click on Edit to Modify Title, Caption and Description of the image</p>
		</c:if>
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Upload File</h3>

	  <ul class="uploadImageNote">

		<li>You are only allowed to upload ${companySettings.maxAllowedImages} files.</li>

		<li>500 x 590 is the most advisable size of image that should be uploaded.</li>

	  </ul>

	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>
			
		  <td colspan="2">
								    
								    <input id="file" name="upload" type="file" /></td>

		</tr>

	  </table>

	  <p class="uploadButtons">
	   
				<input type="submit" value="Upload" class="btnBlue">

	  
	</form>
	

	</div><!--//sidenav-->

	<div class="clear"></div>

  </div><!--//container-->
</div>
</body>

</html>