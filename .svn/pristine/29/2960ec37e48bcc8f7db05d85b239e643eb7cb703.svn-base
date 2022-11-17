<c:set var="maxMultiPageFile" value="${companySettings.maxAllowedFiles}" />
<c:set var="uploadedFileCount" value="${fn:length(multiPageFiles)}" /> 
<c:set var="allowedUploadedFileCount" value="${maxMultiPageFile - uploadedFileCount}" />
<br/>
<div id="uploadimageform">  		
	<form method="post" action="uploadmultipageitemfile.do" enctype="multipart/form-data">
		<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
		<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
		<table width="100%" cellpadding="0" cellspacing="2" border="0">

			<c:if test="${fn:length(multiPageFiles) > 0}"> 
			<tr>
				<td style="border:0px;"><h2>Files List <%--=request.getContextPath() --%></h2></td>
			</tr>
			
			<tr> 
				<td style="border:0px;">
					<table> 
						<c:forEach items="${multiPageFiles}" var="file"> 
							<tr>
								<td style="border:0px;" colspan="2">
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
									<b>${file.fileName}</b>
								</td> 
							</tr>
							<tr bgcolor="#f5f5f5">
								<td style="border:0px;" width="1%" valign="top"> 
									<c:if test="${user.userType.value == 'Super User' or user.userType.value  == 'WTG Administrator' or user.userType.value  == 'Company Administrator'}">
										<a target="_blank" href="${contextParams['FILE_PATH']}/${file.filePath}">View</a> | 
										<a onclick="return confirm('Do you really want to delete this file?');" href="deletemultipagefile.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&mfile_id=${file.id}">Delete</a>
									</c:if>	
								</td>  
							</tr> 
							<tr bgcolor="#ffffff">
								<td style="border:0px;" colspan="2" height="5px"></td>
							</tr>
						</c:forEach>
						
						<c:if test="${invalidFiles gt 0}" >
							<font color="#ff0000"> 
								* There were ${invalidFiles} invalid files were not uploaded. <br/>
								* Make sure that the file follows the allowed file type and file size. 
							</font>
						</c:if>
					</table> <!-- END OF 1ST INNER TABLE --> 
				</td> 
			</tr>
			</c:if>
		
			<c:if test="${not empty allowedUploadedFileCount and allowedUploadedFileCount > 0}">
				<tr>
					<td style="border:0px;">
					<h2>Upload File</h2>
					<br>
					
					<font color="#ff0000"> 
					* You are only allowed to upload <b>${companySettings.maxAllowedFiles}</b> files that are not images.  
					<br> 
					* <b>${companySettings.maxFileSize} MB </b> is the maximum allowable size of file that should be uploaded.
					</font>
					
					</td> 
				</tr>
			
				<tr> 
					<td style="border:0px;">
					  
						<div id="attachment_${dynamicType}" class="attachment" style="display: none;"> 
							<div id="dropcap_${dynamicType}" class="dropcap">1</div>
						    <input id="file" name="upload" type="file" size="30" />
						    <br/> 
						    <a href="#" onclick="javascript:dynamicRemoveFile(this.parentNode.parentNode,this.parentNode, '${dynamicType}');">Remove</a> 
						</div>  
						      
					    <div id="attachments_${dynamicType}" class="container">
						    <!-- 
						    **************************************************************************
						    Below is the call to the "addDynamicUpload" function which adds an Attachment
						    section to the form. Customize the function parameters as needed:
						    1) Maximum # of attachments allowed in this pub type
						    2) base Field Name for the Description fields in your pub type
						    3) base Field Name for the File Upload field in your pub type 
						    3) Unique String preferably the file type 
						    ************************************************************************** 
						    -->
						    
							    <br/><a id="addupload_${dynamicType}" href="javascript:addDynamicUpload(${allowedUploadedFileCount}, 'alt', 'upload', '${dynamicType}')">Attach another file</a><br/><br/>
							    <span id="attachmentmarker_${dynamicType}"></span> 
						</div>  
						
					</td>  
				</tr>

				<tr>
					<td style="border:0px;" height="30px">&nbsp;</td>
				</tr>
			
				<tr> 
					<td style="border:0px;">
						<div align="center">
							<input type="submit" value="Upload Now!" class="upload_button2"/>
						</div> 
					</td>
				</tr>
			</c:if>
		
			<tr>
				<td style="border:0px;" height="30px">&nbsp;</td>  
			</tr>
		</table><!-- END OF MAIN TABLE -->

	</form>
 
</div>
