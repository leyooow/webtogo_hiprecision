<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="forms" />

<script language="javascript" src="../javascripts/upload.js"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>


<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(formPage.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />

<script>

	function submitForm(myForm) {
		
		var name = getElement('formPage_name');
		var jsp = getElement('formPage_jsp');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			error = true;
			messages += " * Name not given\n";
		}
		
		if(jsp.length == 0) {
			error = true;
			messages += " * Jsp not given\n";
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
	
	function updateImageTitle(id, title) {
		DWRImageAction.updateImageTitle("page", id, title);
	}
	
	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("page", id, caption);
	}
	
	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("page", id, description);
	}
	
</script> 

<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;" valign="top">
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<c:if test="${formPage.id > 0}">
		<input type="hidden" name="form_id" value="${formPage.id}" />
		</c:if>
		
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<c:if test="${formPage == null}">
			<tr class="headbox">
				<th colspan="3">
					New Form Page
				</th>
			</tr>
			</c:if>
			
			<tr>
				<td width="1%" nowrap><b>Name</b></td>
				<td width="10px"></td>
				<td><input type="text" id="formPage_name" name="formPage.name" value="${formPage.name}" class="textbox4"></td>
			</tr>
			
			<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
			<tr>
				<td width="1%" nowrap><b>JSP</b></td>
				<td width="10px"></td>
				<td><input type="text" id="formPage_jsp" name="formPage.jsp" value="${formPage.jsp}" class="textbox4"></td>
			</tr>
			</c:if>
			
			<tr> 
				<td width="1%" nowrap><b>Title</b></td>
				<td width="10px"></td>
				<td><input type="text" name="formPage.title" value="${formPage.title}" class="textbox4"></td>
			</tr>

			<tr>
				<td nowrap>&nbsp;</td>
				<td></td>
				<td>
				
				<s:checkbox name="formPage.hidden" value="%{formPage.hidden}" theme="simple" /> <i><b>Hide this page in the website.</b></i>
				
				</td>
				
			</tr>
			
			<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
			<tr>
				<td nowrap>&nbsp;</td>
				<td></td>
				<td>
				
				<s:checkbox name="formPage.fileUploadAllowed" value="%{formPage.fileUploadAllowed}" theme="simple" /> <i><b>This form have an upload field.</b></i>
				 
				</td>
				
			</tr>
			</c:if>

			<tr>
				<td width="1%" nowrap valign="top"><b>Top Content</b></td>
				<td width="10px"></td>
				<td>	 
				<FCK:editor basePath="/FCKeditor" instanceName="formPage.topContent" width="98%" height="400" toolbarSet="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') ? 'Custom_SuperUser' : 'Custom'}" value="${formPage.topContent}"></FCK:editor>
				<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>
					
				
				</td>
			</tr>
			
			<tr>
				<td width="1%" nowrap valign="top"><b>Bottom Content</b></td>
				<td width="10px"></td>
				<td>	 
				<FCK:editor basePath="/FCKeditor" instanceName="formPage.bottomContent" width="98%" height="400" toolbarSet="${(user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator') ? 'Custom_SuperUser' : 'Custom'}" value="${formPage.bottomContent}"></FCK:editor>
				<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>
					
				
				</td>
			</tr>
			
	
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
				<c:choose>
					<c:when test="${formPage.id > 0}">
						<input type="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="upload_button2">
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>
		</form>
		
		</td>
		
		<c:choose>
			<c:when test="${formPage.id > 0}">
				<td width="40%" bgcolor="#efefef" valign="top"> 
				
				<div id="uploadimageform"> 
				
				<form method="post" action="uploadformimage.do" enctype="multipart/form-data">
				
					<input type="hidden" name="form_id" value="${formPage.id}" />
				
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(formPage.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>File List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
							
							<table>
								<c:forEach items="${formPage.images}" var="img">
								<tr>
									<td style="border:0px;" colspan="2"><b>${img.filename}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%" valign="top"> 
																				 
									<img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" />
									<a onclick="return confirm('Do you really want to delete this image?');" href="deleteformpageimage.do?form_id=${formPage.id}&image_id=${img.id}">Delete</a>
									
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
								    <br/><a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'caption', 'upload')">Attach a file</a><br/><br/>
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
	</tr>
</table>

<script>
	addUpload(${allowedImageCount}, 'alt', 'upload');
</script>

</div>