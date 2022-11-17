<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="manage_pages" />

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

<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;" valign="top">
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		
		<c:if test="${newCategory != true && category.parentCategory != null}">
		<input type="hidden" name="parent_id" value="${category.parentCategory.id}" />
		</c:if>
		
		<input type="hidden" name="group_id" value="${group.id}" />
		
		<c:if test="${newCategory != true && category.id > 0}">
		<input type="hidden" name="category_id" value="${category.id}" />
		</c:if> 
		
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr> 
		
			<tr class="headbox">
				<th colspan="3">
					<c:choose>
						<c:when test="${category.id > 0}">
							Edit Category
						</c:when>
						<c:otherwise>
							New Category
						</c:otherwise>
					</c:choose>
				</th>
			</tr>
			
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
			
			
			
			<tr>
				<td width="1%" nowrap>Category Name</td>
				<td width="10px"></td>
				<td><input type="text" id="category_name" name="category.name" value="${(newCategory == true) ? '' : category.name}" class="textbox3" /></td>
			</tr>
			
			<tr>
				<td width="1%" nowrap>Sort Order</td>
				<td width="10px"></td>
				<td><input type="text" id="category.sortOrder" name="category.sortOrder" value="${(newCategory == true) ? '' : category.sortOrder}" class="textbox3" /></td>
			</tr>
			
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
			
			<tr>
				<td valign="top" width="1%" nowrap></td>
				<td></td>
				<td>
					
				<s:checkbox name="category.hidden" value="%{category.hidden}" theme="simple" /> <i><b>Check to disable this category</b></i>
				
				</td>
			</tr>
			
			<tr>
				<td valign="top" width="1%" nowrap>Category Description</td>
				<td></td>
<!--				<td><textarea name="category.description" style="width: 300px; height: 150px;">${(newCategory == true)? '' : category.description}</textarea></td>-->
					<td><FCK:editor basePath="/FCKeditor" instanceName="category.description" width="98%" height="200" toolbarSet="Custom_SuperUser" value="${category.description}"></FCK:editor></td>
			</tr>
			
			
			
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
				<c:choose>
					<c:when test="${newCategory != true && category.id > 0}">
						<input type="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
						<input type=button value="Cancel" class="upload_button2" onclick="window.location='category.do?group_id=${group.id}'">
					</c:when>
					<c:otherwise>
						<input type="submit" name="submit" value="Add" class="upload_button2">
					</c:otherwise>
				</c:choose>
				
				</td>
			</tr>
					
		</table>
		
		</form>
		
		</td>




		<c:choose>
			<c:when test="${category.id >0}">
			<td width="40%" bgcolor="#efefef" valign="top"> 

				<div id="uploadimageform"> 
				
				<form method="post" action="uploadcategoryimage.do" enctype="multipart/form-data">
				
					<input type="hidden" name="parent" value="${category.parentCategory.id}" />
					<input type="hidden" name="category_id" value="${category.id}" />
					<input type="hidden" name="group_id" value="${group.id}" />
				
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(category.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>File List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
							
							<table>
								<c:forEach items="${category.images}" var="img">
								<tr>
									<td style="border:0px;" colspan="2"><b>${img.filename}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%" valign="top"> 
									<img src="${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}" alt="pic"/>
									<a onclick="return confirm('Do you really want to delete this image?');" href="deletecategoryimage.do?group_id=${group.id}&category_id=${category.id}&image_id=${img.id}">Delete</a>
									
									</td>
									<td style="border:0px;" valign="top">
									
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
									
									</td>
								</tr>
								<tr bgcolor="#ffffff">
									<td style="border:0px;" colspan="2" height="5px"></td>
								</tr>
								</c:forEach>
							</table>
							
							</td>
						</tr>
						</c:if>
						
						<c:if test="${allowedImageCount > 0}">  
						<tr>
							<td style="border:0px;">
							<h2>Upload File</h2>
							
							<br>
							
							<font color="#ff0000">
							* You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> files.
							<br>
							* <b>${companySettings.image1Width} x ${companySettings.image1Heigth}</b> is the most advisable size of image that should be uploaded.
							</font>
							
							
							</td>
						</tr>
						<tr> 
							<td style="border:0px;">
							
								<div id="attachment" class="attachment" style="display:none">
									<div id="dropcap" class="dropcap">1</div>
								    
								    <input id="file" name="upload" type="file" size="30" />
								    <br/> 
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
								    <br/><a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload'); ">Attach a file</a><br/><br/>
								    <span id="attachmentmarker"></span>
								</div>
							
							</td>
						</tr>
						
						
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
						<tr>
							<td style="border:0px;">
							
								<div align="center">
								<input type="submit" value="Upload Now!" class="upload_button2" />
								</div>
							 
							</td>
						</tr>
						</c:if>  
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
					</table>
				
				</form>
				
				<div>	
			</td>
			</c:when>
			<c:otherwise>
				<td width="40%" bgcolor="#ffffff" valign="top"></td>
			</c:otherwise>
		</c:choose>



		
		<td width="40%">
		</td>
	</tr>
</table>
</div>