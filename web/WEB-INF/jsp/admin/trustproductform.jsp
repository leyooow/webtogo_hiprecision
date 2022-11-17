<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="productstatistics" />

<script language="javascript" src="../javascripts/upload.js"></script>
<script language="javascript" src="../javascripts/dynamicUploader.js"></script>

<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(singlePage.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />
<c:set var="allowedFileCount" value="${1 - fn:length(singlePage.files)}" />

<c:set var="maxMultiPageFile" value="${companySettings.maxAllowedFiles}" />
<c:set var="uploadedFileCount" value="${fn:length(multiPageFiles)}" /> 
<c:set var="allowedUploadedFileCount" value="${maxMultiPageFile - uploadedFileCount}" />

<script>

	function submitForm(myForm) {
		
		var name = getElement('statname');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Name not given";
			error = true;
		}
		else {
			document.getElementById('statname').value = document.getElementById('statname').value;
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

<table border="0">
	<tr>
		<td style="border:0px;" valign="top">
	
		<form method="post" action="savetrustproductstatistics.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		<input type="hidden" name="value" value="0" />
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<c:if test="${singlePage == null}">
			<tr class="headbox">
				<th colspan="3">
					New Rate
				</th>
			</tr>
			</c:if>
			
			<tr>
				<td width="1%" nowrap><b>Name</td>
				<td width="10px"></td>
				<td>
				<input type="text" id="statname" name="statname" class="textbox4">
				</td>
			</tr>			
			<%-- tr>
				<td width="1%" nowrap><b>Date</b></td>
				<td width="10px"></td>
				<td>
				
				<fmt:formatDate pattern="MM-dd-yyyy" value="${rate.itemDate}" var="idate"/>
								 
				<input type="text" id="item_date" name="itemDate" value="${idate}" class="textbox2" /> 
				<a href="javascript:;" id="item_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				   
				  
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "item_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y",      // format of the input field
				        button         :    "item_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true
				    });
				</script>
				
				</td>			
				
				
			</tr --%>
															 
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
					<input type="submit" value="Add" class="upload_button2">
				</td>
			</tr>
		</table>
		</form>
		
		</td>
		
		<c:choose>
			<c:when test="${singlePage.id > 0}">
			
			<td width="40%" bgcolor="#efefef" valign="top"> 
			
				<div id="uploadimageform"> 
				
				<c:choose>
					<c:when test="${multiPage != null}">
						<form method="post" action="uploadmultipageitemimage.do" enctype="multipart/form-data">
						<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
					 
					</c:when> 
					<c:otherwise>
						<form method="post" action="uploadsinglepageimage.do" enctype="multipart/form-data">
					</c:otherwise>
				</c:choose>
				
					<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
								
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(singlePage.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>Image List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
														
							<table>
								<c:forEach items="${singlePage.images}" var="img">
								<tr>
									<td style="border:0px;" colspan="2"><b>${img.filename}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%" valign="top"> 
																				 
									<img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" />
									
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
									<a onclick="return confirm('Do you really want to delete this image?');" href="deletesinglepageimage.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&image_id=${img.id}">Delete</a>
									</c:if>										
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
							<h2>Upload Image</h2>
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

<%--
				<c:if test="${singlePage.hasFile}"> 
				<form method="post" action="uploadsinglepagefile.do" enctype="multipart/form-data">
				 
				<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
				
				<table>
					<c:choose>
						<c:when test="${allowedFileCount > 0}">
					
						<tr>
							<td style="border:0px;"><h2>Upload File Attachment</h2></td>
						</tr>
						<tr>
							<td>
							
							<input type="file" name="upload" />
							<input type="submit" value="Upload" /> 
							
							</td>
						</tr>
						
						</c:when>
						<c:otherwise>
						
						<tr>
							<td style="border:0px;"><h2>Uploaded File Attachment</h2></td>
						</tr>
						<tr>
							<td valign="top">
							
							${singlePage.files[0].fileName}
							(
							<a target="_blank" href="${contextParams['FILE_PATH']}/${singlePage.files[0].filePath}">View</a> | 
							<a onclick="return confirm('Do you really want to delete this image?');" href="deletesinglepagefile.do?singlepage_id=${singlePage.id}&file_id=${singlePage.files[0].id}">Delete</a>
							) 
							
							</td>
						</tr>
						
						</c:otherwise>
					</c:choose> 
				</table>
				
				</form>
				</c:if>
--%>
				<div>	

				<c:if test="${singlePage.hasFile}">
					<!--=================================== UPLOAD ANY FILE SECTION ================================-->
					<!-- DECLARE value of dynamicType variable (any unique name for input type IDs) -->
					<c:set var="dynamicType" value="file" />
					<%@include file="includes/uploadfilesform.jsp" %>
				</c:if>  
			
			</td>
	
			</c:when>
			<c:otherwise>
				<td width="40%" valign="top"></td>
			</c:otherwise>
		</c:choose>
		
	</tr>
</table>

<script>
	addUpload(${allowedImageCount}, 'alt', 'upload'); 
	addDynamicUpload(${allowedUploadedFileCount}, 'alt', 'upload', '${dynamicType}');  
</script> 

</div>