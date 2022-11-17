<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="brands" />

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

<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;">
		
		<c:if test="${!newBrand}">
			<s:actionerror />
		</c:if>
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
				
		<input type="hidden" name="group_id" value="${group.id}" />
		<input type="hidden" name="brand_id" value="${brand.id}" />
				
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr> 
		
			<tr class="headbox">
				<th colspan="3">	
					<c:choose>
						<c:when test="${brand.id > 0}">
							Edit Brand 
						</c:when>
						<c:otherwise>
							New Brand 
						</c:otherwise>	
					</c:choose>
				</th>
			</tr>
			
			<tr>
				<td width="1%" nowrap>Brand Name</td>
				<td width="10px"></td>
				<td><input type="text" id="brand_name" name="brand.name" value="${(newBrand == true) ? '' : brand.name}" class="textbox4" /></td>
			</tr>
			
			<tr>
				<td nowrap></td>
				<td></td>
				<td>
					<%-- 
					<s:checkbox name="brand.disabled" value="%{brand.disabled}" theme="simple" /> <i><b>This brand is disabled</b></i>
					--%>
				</td>
			</tr>
			
			<tr>
				<td nowrap></td>
				<td></td>
				<td>
					<s:checkbox name="brand.featured" value="%{brand.featured}" theme="simple" /> <i><b>This brand is featured</b></i>
					
				</td>
			</tr>
			
			<tr>
				<td nowrap>Brand Description</td>
				<td></td>
				<td>
					<textarea name="brand.description" style="width: 100%; height: 150px;">${brand.description}</textarea>
				</td>
			</tr>
						
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
				<c:choose>
					<c:when test="${brand.id > 0}">
						<input type="submit" name="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2"> 
						<input type="button" value="Cancel" class="upload_button2" onclick="window.location='brands.do?group_id=${group.id}&category_id=${category.id}';"> 
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
			<c:when test="${brand.id > 0}">
			
			<td width="40%" bgcolor="#efefef" valign="top"> 
			
				<div id="uploadimageform"> 
				
				<form method="post" action="uploadbrandimage.do" enctype="multipart/form-data">
				
					<input type="hidden" name="group_id" value="${group.id}" />
					<input type="hidden" name="brand_id" value="${brand.id}" />
								
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(brand.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>File List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
														
							<table>
								<c:forEach items="${brand.images}" var="img">
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%">
									<img src="${contextParams['IMAGE_PATH']}/images/brands/${img.thumbnail}" />
									</td>
									<td style="border:0px;" valign="top">
									<b>${img.filename}</b>
									<br>
									<a onclick="return confirm('Do you really want to delete this image?');" href="deletebrandimage.do?group_id=${group.id}&brand_id=${brand.id}&image_id=${img.id}">Delete</a>
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
							<Br>
							
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
								    <br/><a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload')">Attach a file</a><br/><br/>
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
				<td width="40%" valign="top"></td>
			</c:otherwise>
		</c:choose>
		
	</tr>
</table>
</div>

<script>
	addUpload(${allowedImageCount}, 'alt', 'upload'); 
</script>