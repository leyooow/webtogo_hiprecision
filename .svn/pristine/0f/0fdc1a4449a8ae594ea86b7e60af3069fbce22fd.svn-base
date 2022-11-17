<c:set var="menu" value="manage_pages" />

<script language="javascript" src="../javascripts/multifile.js"></script>
<script language="javascript" src="../javascripts/upload.js"></script>

<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(multiPage.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<script>

	function submitForm(myForm) {
			
		var name = getElement('multipage_name');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Name not given";
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
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
</script> 

<div style="width:90%;">
<table width="80%">
	
	<tr>
		<td style="border:0px;" colspan="2">
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		<c:if test="${multiPage.id > 0}">
		<input type="hidden" name="update" value="1">
		<input type="hidden" name="multipage_id" value="${multiPage.id}">
		<input type="hidden" name="language" value="${param.language}">
		</c:if>
				
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td colspan="3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap class="label">Name</td>
				<td width="10px"></td>
				<td><input class="inputContent"  type="text" id="multipage_name" name="multiPage.name" value="${multiPage.name}" class="textbox4"></td>
			</tr>
			
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
			<c:if test="${multiPage.language== null}"> 
			<tr>
				<td width="1%" nowrap class="label">JSP</td>
				<td width="10px"></td>
				<td><input class="inputContent" type="text" id="multipage_jsp" name="multiPage.jsp" value="${multiPage.jsp}" class="textbox4"></td>
			</tr>
			</c:if>
			</c:if>
				
			<tr>
				<td width="1%" nowrap class="label">Title</td>
				<td width="10px"></td>
				<td><input class="inputContent" type="text" name="multiPage.title" value="${multiPage.title}" class="textbox4"></td>
			</tr>
				 
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
			<tr>
				<td width="1%" nowrap class="label">Items</td>
				<td width="10px"></td>
				<td><input type="text" name="multiPage.itemsPerPage" value="${multiPage.itemsPerPage}" style="width: 100px;"></td>
			</tr>
			</c:if>
			
			
			
			
				 
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td></td>
					<td>
					
					<s:checkbox name="multiPage.featured" value="%{multiPage.featured}" theme="simple" />
					</td>
		  <td>This page is a featured page.</td>

					
				</tr>
				</c:if>
				
				
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td></td>
					<td>
					
					<s:checkbox name="multiPage.hidden" value="%{multiPage.hidden}" theme="simple" />
					</td>
		  <td>Hide this page in the website.</td>

					
				</tr>
				</c:if>
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td></td>
					<td>
					
					<s:checkbox name="multiPage.hidden" value="%{multiPage.hasFile}" theme="simple" />
					</td>
		  <td>This page has files.</td>

					
				</tr>
				</c:if>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td></td>
					<td>
					
					<s:checkbox name="multiPage.loginRequired" value="%{multiPage.loginRequired}" theme="simple" /> 
					
					</td>
		  <td>This page requires member login.</td>

					
				</tr>
				
				<tr>
					<td></td>
					<td>
					<s:checkbox name="multiPage.hasPublicationDate" value="%{multiPage.hasPublicationDate}" theme="simple" /> 
					</td>
		  			<td>This page has publication date.</td>
				</tr>
				
			</c:if>

		
			
			<c:if test="${companySettings.hasMemberFeature == true}">
				<c:if test="${multiPage.loginRequired == false || user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">			
				<tr>
					<td nowrap>&nbsp;</td>
					<td></td>
					<td>
					
					<s:checkbox name="multiPage.loginRequired" value="%{multiPage.loginRequired}" theme="simple" /> <i><b>This page requires member login.</i>
					
					</td>
					
				</tr>
				</c:if>
			</c:if>
			
			<tr>
				<td width="1%" nowrap valign="top" class="label">Description</td>
				<td></td>
				<td>
				
				 <textarea id="multiPage.description" name="multiPage.description" >${multiPage.description}</textarea>
					<script type="text/javascript">
						CKEDITOR.replace( 'multiPage.description');
					</script>
				</td>
			</tr>
			
			<tr id="fileList" style="display:none;">
				<td colspan="3"><br/><h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>
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
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;" align="right">
				
					<c:choose>
						<c:when test="${multiPage.id > 0}">
							<input type="submit" value="Update" class="btnBlue">
							<input type="reset" value="Reset" class="btnBlue">
							<input type="button" value="Cancel" class="btnBlue" onclick="window.location='${cancelUrl}'">
						</c:when>
						<c:otherwise>
							<input type="submit" value="Add" class="btnBlue">
						</c:otherwise>
					</c:choose>
					
				</td>
			</tr>
			
		</table>
		
		
		
		
		</form>
		
		</td>
		
		
		<c:choose>
			<c:when test="${multiPage.id >0}">
			<td width="30%" bgcolor="#efefef" valign="top"> 

				<div id="uploadimageform"> 
				
				<form method="post" action="uploadmultipageimage.do" enctype="multipart/form-data">
				
					<input type="hidden" name="parent" value="${multiPage.parentMultiPage.id}" />
					<input type="hidden" name="multipage_id" value="${multiPage.id}" />
									
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(multiPage.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>File List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
							
							<table>
								<c:forEach items="${multiPage.images}" var="img">
								<tr>
									<td style="border:0px;" colspan="2"><b>${img.filename}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%" valign="top"> 
									<img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" alt="pic"/>
									<a onclick="return confirm('Do you really want to delete this image?');" href="deletemultipageimage.do?multipage_id=${multiPage.id}&image_id=${img.id}">Delete</a>
									
									</td>
									<td style="border:0px;" valign="top">
									
									<table>
										<tr>
											<td>Title</td>
										</tr>
										<tr>
											<td><input type="text" name="image_title_${img.id}" value="${img.title}" class="textbox4" onblur="DWRImageAction.updateImageTitle('multipage', ${img.id}, this.value);"></td>
										</tr>
										<tr>
											<td>Caption</td>
										</tr>
										<tr>											
											<td><input type="text" name="image_caption_${img.id}" value="${img.caption}" class="textbox4" onblur="DWRImageAction.updateImageCaption('multipage', ${img.id}, this.value);"></td>
										</tr> 
										<tr>
											<td>Description</td>
										</tr>
										<tr>
											<td>
												<textarea class="textbox4" name="image_description_${img.id}" onblur="DWRImageAction.updateImageDescription('multipage', ${img.id}, this.value);">${img.description}</textarea>
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
				
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
				
				MULTIPAGE ID: ${multiPage.id }
				<c:if test="${multiPage.id > 0}">
				<input type="hidden" name="update" value="1">
				<input type="hidden" name="multipage_id" value="${multiPage.id}">
				</c:if>
				
				<table class="tblmeta"> 
					<tr class="headbox" align="left">
						<th colspan="2"> HTML META Settings</th>
					</tr>
					<tr >
						<td colspan="2">  </td>
					</tr>	
					
					<tr> 
						<td nowrap>META Title</td>
						<td><textarea name="multiPage.meta_title" cols=40  rows=2>${multiPage.meta_title}</textarea></td>
							
					</tr>
						 		
					<tr>
						<td nowrap>META Description</td>							
						<td><textarea name="multiPage.meta_description" cols=40  rows=2>${multiPage.meta_description}</textarea></td>
						
					</tr>
					
					<tr>
						<td nowrap>META Keywords</td>
						<td><textarea name="multiPage.meta_keywords" cols=40  rows=2>${multiPage.meta_keywords}</textarea></td>
						
					</tr>
					
					<tr> 
						<td nowrap>META Author</td>						
						<td nowrap="nowrap"><input type="text" id="" name="multiPage.meta_author" value="${multiPage.meta_author}" size = "55"/>
						</td>
					</tr>
					
					<tr> 	
						<td nowrap>META Copyright</td>
						<td><input type="text" name="multiPage.meta_copyright" value="${multiPage.meta_copyright}" size = "55"/></td>
					</tr>				
					<tr>
						<td colspan="2" > </td>
					</tr>
					
						<tr>
				<td colspan="2" style="border: 0px;">
					<c:choose>
						<c:when test="${multiPage.id > 0}">
				
							<input type="submit" value="Update" class="upload_button3">					
							<input type="reset" value="Reset" class="upload_button3">											
							<input type="button" value="Cancel" class="upload_button3" onclick="window.location='${cancelUrl}'">							
						</c:when>
						<c:otherwise>
				
							<input type="submit" value="Add" class="upload_button2">
						</c:otherwise>
					</c:choose>					
				</td>
			</tr>
									
				</table>
				</form>
				<div>	
			</td>
			</c:when>
			<c:otherwise>
				<td width="10%" bgcolor="#ffffff" valign="top"></td>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
</table>
</div>

<script type="text/javascript">
		<c:if test="${company.name eq 'neltex'}">
			document.getElementById('fileList').style.display = 'table-row';
		</c:if>
	</script>