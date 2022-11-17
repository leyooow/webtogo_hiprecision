<div id="uploadimageform" name="uploadFileForm"> 

	<c:choose>
		<c:when test="${multiPage != null and singlePage != null}">
			
		
		
		
			<c:if test="${fn:length(multiPageFiles) > 0}"> 
		<!-- @MULTIPAGE -->
		<h3>Files List</h3>
			<table width="100%">
						<form method="post" action="savefileaccesrights.do" enctype="multipart/form-data">
						<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
						<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
						<c:forEach items="${multiPageFiles}" var="file" varStatus= "counter"> 
							<tr>
								<td style="border:0px;border-bottom:1px solid#ececec;">
								
									<c:choose>
									<c:when test="${file.fileType eq 'application/pdf'}">
										<img src="images/icons/page_white_acrobat.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/msword'}">
										<img src="images/icons/page_white_word.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-powerpoint'}">
										<img src="images/icons/page_white_powerpoint.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-excel'}">
										<img src="images/icons/page_excel.png">
									</c:when>
									<c:when test="${file.fileType eq 'text/plain'}">
										<img src="images/icons/page_white_text.png">
									</c:when>
									<c:otherwise>
										<img src="../images/icons/page_white.png">
									</c:otherwise>
									</c:choose>
									
									
									<b>${file.fileName}</b><br>
									
								
									
									
									<c:if test="${company.companySettings.hasPageFileRights eq true}">
											<c:choose>
											<c:when test="${company.name eq 'uniorientagents'}">
												<c:set var="isAllChecked" value ="1" />
												<span id="agentSpan_${counter.count}">
													<c:forEach items="${memberTypes}" var="memberType">
														<input onclick="isAllChecked(${counter.count})" type="checkbox" name="memberTypeIdList" value="${memberType.id}_${file.id }" ${(file.memberTypeAccess[memberType.id] eq true) ? 'CHECKED' : ''}/> ${memberType.name}
														<c:if test="${file.memberTypeAccess[memberType.id] ne true}">
															<c:set var="isAllChecked" value ="0" />
														</c:if>
													</c:forEach>
												</span>													
													<input onclick="selectAllAgents(${counter.count},this)" type="checkbox" id="allAgents_${counter.count}" name="" value="" 
													<c:if test="${isAllChecked eq 1}">
														checked = "checked"
													</c:if>													
													/> All Agents
												
											</c:when>
											<c:otherwise>
												<c:forEach items="${memberTypes}" var="memberType">
													<input type="checkbox" name="memberTypeIdList" value="${memberType.id}_${file.id }" ${(file.memberTypeAccess[memberType.id] eq true) ? 'CHECKED' : ''}/> ${memberType.name}
												</c:forEach>
											</c:otherwise>
										</c:choose>		
									</c:if>	
						</td>
						<td style="border:0px;border-bottom:1px solid#ececec;" align="right">
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'or user.userType.value == 'Portal Administrator'}">
										<a target="_blank" href="${contextParams['FILE_PATH']}/${file.filePath}">View</a> | 
										<a onclick="return confirm('Do you really want to delete this file?');" href="deletemultipagefile.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&mfile_id=${file.id}">Delete</a><br>
									
									</c:if>	
									<c:if test="${company.companySettings.hasPageFileRights eq true}">
									
									</c:if>
						</td>
						</tr>
						</c:forEach>
					</table>
					<c:if test="${company.companySettings.hasPageFileRights eq true and company.name ne 'agian'}">
						<p align="right"><input type="submit" value="Update Access Rights" class="btnBlue"/></p>
					</c:if>
					</form>
					<div class="clear"></div>
			</c:if>
		
			<c:if test="${not empty allowedUploadedFileCount and allowedUploadedFileCount > 0}">
			
		<h3>Upload File</h3>
				
		<form method="post" action="uploadmultipageitemfile.do" enctype="multipart/form-data" onsubmit="return validateFile()">
			<table width="100%" cellpadding="0" border="0">
			<tr > 
					<td style="border:0px;" align="left">
					  
						<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
						<input type="hidden" name="singlepage_id" value="${singlePage.id}" />	
						<div id="attachment_${dynamicType}"style="display: none;"> 
							<div id="dropcap_${dynamicType}" style="display:none">1</div>
						    <input id="file" name="upload" type="file" size="30" />
						    <br/> 
						    <a href="#" onclick="javascript:dynamicRemoveFile(this.parentNode.parentNode,this.parentNode, '${dynamicType}');">Remove</a> 
						</div>  
					</td>
			</tr>
			<tr>	    <td >  
					    <div id="attachments_${dynamicType}"> 
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
					<td style="border:0px;border-bottom:1px solid#ececec;">
						<div align="right">
							<input type="submit" value="Upload Now!" class="btnBlue"/>
						</div> 
					</td>
				</tr>
				</table></form><!-- END OF MAIN TABLE -->
			</c:if>
			<ul class="uploadImageNote" >
					<c:if test="${invalidFiles gt 0}" >
							
								<li class="shortDes"><i> There were ${invalidFiles} invalid files were not uploaded.</i> </li>
								<li class="shortDes"><i> Make sure that the file follows the allowed file type and file size.</i></li> 
						
						</c:if>
				
					

					<li class="shortDes"><i>You are only allowed to upload <b>5</b> files that are not images. </i> </li>
					
					<li class="shortDes"><i>5 MB  is the maximum allowable size of file that should be uploaded.</i></li>
					
					</ul>
		
			
		</c:when>
		<c:otherwise>
		
		
		<!-- @SINGLEPAGE -->
			<h3>${size}</h3>
			<c:if test="${fn:length(singlePageFiles) > 0}"> 

			<h3>Files List</h3>
			<form method="post" action="savefileaccesrights.do" enctype="multipart/form-data" onsubmit="return validateFile()">
				<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
				<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
				<table width="100%" cellspacing="0" cellpadding="10">
					<c:forEach items="${singlePageFiles}" var="file"  varStatus= "counter"> 
						<tr>
								<td colspan="2" style="border-bottom:1px solid#ececec;">
									<c:choose>
									<c:when test="${file.fileType eq 'application/pdf'}">
										<img src="images/icons/page_white_acrobat.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/msword'}">
										<img src="images/icons/page_white_word.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-powerpoint'}">
										<img src="images/icons/page_white_powerpoint.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-excel'}">
										<img src="images/icons/page_excel.png">
									</c:when>
									<c:when test="${file.fileType eq 'text/plain'}">
										<img src="images/icons/page_white_text.png">
									</c:when>
									<c:otherwise>
										<img src="images/icons/page_white.png">
									</c:otherwise>
									</c:choose>
									${file.fileName}<br />
									
									<c:if test="${company.companySettings.hasPageFileRights eq true}">									
										<c:choose>
											<c:when test="${company.name eq 'uniorientagents'}">
												<c:set var="isAllChecked" value ="1" />
												<span id="agentSpan_${counter.count}">
													<c:forEach items="${memberTypes}" var="memberType">
														<input onclick="isAllChecked(${counter.count})" type="checkbox" name="memberTypeIdList" value="${memberType.id}_${file.id }" ${(file.memberTypeAccess[memberType.id] eq true) ? 'CHECKED' : ''}/> ${memberType.name}
														<c:if test="${file.memberTypeAccess[memberType.id] ne true}">
															<c:set var="isAllChecked" value ="0" />
														</c:if>
													</c:forEach>
												</span>													
													<input onclick="selectAllAgents(${counter.count},this)" type="checkbox" id="allAgents_${counter.count}" name="" value="" 
													<c:if test="${isAllChecked eq 1}">
														checked = "checked"
													</c:if>													
													/> All Agents
												
											</c:when>
											<c:otherwise>
												<c:forEach items="${memberTypes}" var="memberType">
													<input type="checkbox" name="memberTypeIdList" value="${memberType.id}_${file.id }" ${(file.memberTypeAccess[memberType.id] eq true) ? 'CHECKED' : ''}/> ${memberType.name}
												</c:forEach>
											</c:otherwise>
										</c:choose>																							
									</c:if>			
									</td>
									<td style="text-align:right; border-bottom:1px solid#ececec;">
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'or user.userType.value == 'Portal Administrator'}">
										
											<a target="_blank" href="${contextParams['FILE_PATH']}/${file.filePath}">View</a> | 
											<c:if test="${company.name eq 'metroquicash' }">
											<a href="${contextParams['FILE_PATH']}/${file.filePath}" type="application/octet-stream" download>Download</a> |
											</c:if>
											<a onclick="return confirm('Do you really want to delete this file?');" href="deletesinglepagefile.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&file_id=${file.id}">Delete</a>
											<br/>
									</c:if>
											
										
										
									
									
									</td>
								
						</tr>
						</c:forEach>
						</table>
						<c:if test="${company.companySettings.hasPageFileRights eq true and company.name ne 'agian'}">
						<p align="right"><input type="submit" value="Update Access Rights" class="btnBlue"/></p>
						</c:if>
						</form>
			<div class="clear"></div>

			</c:if>
		
			<c:if test="${not empty allowedUploadedFileCount and allowedUploadedFileCount > 0}">
				<form method="post" action="uploadsinglepagefile.do" enctype="multipart/form-data" onsubmit="return validateFile()">
				<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
				<input type="hidden" name="singlepage_id" value="${singlePage.id}" />		
				<h3>Upload File</h3>
					
				<table width="100%" cellpadding="10" border="0" style>
				<tr> 
					
					  <td width="100%" colspan="2">
						<div id="attachment_${dynamicType}"style="display: none;"> 
							<div id="dropcap_${dynamicType}" style="display:none">1</div>
						    <input id="file" name="upload" type="file" size="30" />
						    <br/> 
						    <a href="javascript:void(0);" onclick="javascript:dynamicRemoveFile(this.parentNode.parentNode,this.parentNode, '${dynamicType}');">Remove</a> 
						</div>  
						</tr>
						<tr>      
					    <td><div id="attachments_${dynamicType}" align="left"> 
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
							    <input type="submit" value="Upload Now!" class="btnBlue"/>
						</td></div>  
					
					  </td>
				</tr>						
				
				</table><!-- END OF MAIN TABLE -->
				</form>
				<ul class="uploadImageNote" >
					<c:if test="${invalidFiles gt 0}" >
							
								<li class="shortDes"><i> There were ${invalidFiles} invalid files were not uploaded.</i> </li>
								<li class="shortDes"></i> Make sure that the file follows the allowed file type and file size.</i></li> 
						
						</c:if>
				
					

					<li class="shortDes"><i>You are only allowed to upload <b>5</b> files that are not images.</i>  </li>
					
					<li class="shortDes"><i>5 MB  is the maximum allowable size of file that should be uploaded.</i></li>
					
					</ul>
				
			</c:if>
		
		
		</c:otherwise>
	</c:choose>	
 
</div>
<script>
function selectAllAgents(counter,allAgents) {
	var checkBoxes = document.getElementById('agentSpan_'+counter).getElementsByTagName('input');
	if(allAgents.checked){		
	    for (var i = 0; i < checkBoxes.length; i++) {
	    	checkBoxes[i].checked = true;
	    }
	}else{
		var checkBoxes = document.getElementById('agentSpan_'+counter).getElementsByTagName('input');
	    for (var i = 0; i < checkBoxes.length; i++) {
	    	checkBoxes[i].checked = false;
	    }
	}
    
}
function isAllChecked(counter){
	
	var allAgents = document.getElementById('allAgents_'+counter);
	var isAllChecked = true;
	var checkBoxes = document.getElementById('agentSpan_'+counter).getElementsByTagName('input');
    for (var i = 0; i < checkBoxes.length; i++) {
    	if(!checkBoxes[i].checked){
    		isAllChecked = false;
			break;
        }
    }    
    if(isAllChecked){
    	allAgents.checked = true;
    }else{
    	allAgents.checked = false;
    }	
}
</script>