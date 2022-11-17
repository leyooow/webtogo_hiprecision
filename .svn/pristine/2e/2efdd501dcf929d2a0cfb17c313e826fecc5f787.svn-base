<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(multiPage.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<c:set var="menu" value="pages" />
<c:set var="submenu" value="${multiPage.name}" /> 
<c:set var="edititableName" value="true" />
 <c:set var="formAction" value="updatemultipage.do" />
				<c:set var="cancelUrl" value="editmultipagechildlist.do?multipage_id=${multiPage.id}" />
				

<c:if test="${singlePage.parent != null}">
	<c:set var="showBackLinks" value="true" />						
</c:if>

<c:set var="imageRange" value=""/>
				<c:if test="${(company.name eq 'neltex') and (multiPage.jsp eq 'careers')}">
					<c:set var="imageRange" value="end='0'"/>
				</c:if>

<body>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="../javascripts/wz_tooltip.js"></script>

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
			//alert(document.getElementById("c1").value);
			document.getElementById("c2").value = document.getElementById('multiPage_isSingleFeaturedC').checked;
			document.getElementById("c3").value = document.getElementById('multiPage_hiddenC').checked;
			document.getElementById("c5").value = document.getElementById('multiPage_hasFileC').checked;
			document.getElementById("c6").value = document.getElementById('multiPage_loginRequiredC').checked;
			document.getElementById("c7").value = document.getElementById('multiPage_hasPublicationDateC').checked;
			//alert(document.getElementById("c1").value);
			
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
		DWRImageAction.updateImageTitle("multipage", id, title);
	}

	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("multipage", id, caption);
	}

	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("multipage", id, description);
	}
	
</script> 
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
</script>
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
			<h1><strong>Edit Multi Page</strong></h1>
			
			<ul>
				<li>
		 			<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
						<a href="multipage.do">Multi Pages</a>  
					</c:if>
		  		</li>
				<li>&raquo;</li>
			 	<li>Edit Multi Page</li>
			</ul>
			
			<div class="clear"></div>
		</div><!--//pageTitle-->
		
		<c:if test="${not empty languages}">
		 	<h4>Language:
				<c:if test="${multiPage.language!= null}"> 
					${singlePage.language.language}
				</c:if>
				<c:if test="${multiPage.language== null}"> 
					English
				</c:if>
			</h4>
		  	<div class="metaBox">
			  	<c:if test="${multiPage.language== null}"> 
				English, 
				</c:if>
				<c:if test="${multiPage.language != null}"> 
				<a href="editmultipage2.do?multipage_id=${param.multipage_id}">English</a>
				</c:if>
			  	<c:forEach items="${languages}" var="lang">
			  		<c:if test="${lang.language ne param.language}">
					, <a href="editmultipage2.do?multipage_id=${param.multipage_id}&language=${lang.language}">${lang.language}</a>
					</c:if>
					<c:if test="${lang.language eq param.language}">
					, ${lang.language}
					</c:if>    
				</c:forEach>
			
			</div>
		</c:if>
		
		<c:if test="${company.companySettings.hasMetaPage eq true}">
			<h4>General Tags</h4>
		
			<div class="metaBox">
				<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		
					<c:if test="${multiPage.id > 0}">
						<input type="hidden" name="update" value="1">
						<input type="hidden" name="multipage_id" value="${multiPage.id}">
						<input type="hidden" name="language" value="${param.language}">
					</c:if>
					<c:if test="${multiPage != null}">
						<input type="hidden" name="multipage_id" value="${multiPage.id}">
					</c:if>
							
					<table width="100%" border="0" cellspacing="0" cellpadding="3">
						<tr>
							<td class="label">HTML Title</td>
							<td><input class="inputMeta" type="text" name="multiPage.meta_title" value="${multiPage.meta_title}" /></td>
							<td>&nbsp;</td>
							<td class="label">Meta Keywords</td>
							<td><input class="inputMeta" type="text" name="multiPage.meta_keywords" value="${multiPage.meta_keywords}" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr valign="top">
							<td class="label">Meta Description</td>
							<td>
								<textarea  class="inputMeta"  name="multiPage.meta_description" >${multiPage.meta_description}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="5" class="label">
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
			</div><!--//metaBox-->
		</c:if>
		
			<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
				
			<c:if test="${multiPage.id > 0}">
			<input type="hidden" name="update" value="1">
			<input type="hidden" name="multipage_id" value="${multiPage.id}">
			<input type="hidden" name="language" value="${param.language}">
			</c:if>
			<c:if test="${multiPage != null}">
			<input type="hidden" name="multipage_id" value="${multiPage.id}">
			</c:if>
			
		  <table width="100%" border="0" cellspacing="0" cellpadding="3">
	
		    <tr>
	
			  <td class="label">Name</td>
	
			  <td align="left"><input type="text" id="multipage_name" name="multiPage.name" value="${multiPage.name}" class="inputContent" /></td>
	
			</tr>
			<tr>
				</tr>
				<c:if test="${empty multiPage.language}"> 
					<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') }">
			<tr>
				
			  <td class="label">JSP</td>
	
			   <td align="left"><input type="text" id="multipage_jsp" name="multiPage.jsp" value="${multiPage.jsp}" class="inputContent" /></td>
	
			</tr>
			
			</c:if></c:if>
			<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
					<%-- <c:if test="${multiPage.id== 1111}"> 1097 local ONLY--><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID --%>
				<tr>
	
			  <td class="label">Title</td>
	
			   <td align="left"><input type="text" name="multiPage.title" value="${multiPage.title}" class="inputContent" /></td>
	
			</tr>
			
			<tr>
	
			  <td class="label">Items</td>
	
			   <td align="left"><input type="text" name="multiPage.itemsPerPage" value="${multiPage.itemsPerPage}" class="inputContent" /></td>
	
			</tr>
			
			<c:if test="${singlePage.language== null}"> 
			<tr>
			</c:if>
			
			<input type="hidden"  id="c2" name="multiPage.featured" />
			<input type="hidden"  id="c3" name="multiPage.hidden" />
			<input type="hidden"  id="c5" name="multiPage.hasFile" />
			<input type="hidden"  id="c6" name="multiPage.loginRequired" />
			<input type="hidden"  id="c7" name="multiPage.hasPublicationDate" />
			
			<tr>
	
			  <td class="label">Content</td>
	
			   <td align="left">
			 
			  <textarea id="multiPage.description" name="multiPage.description" >${multiPage.description}</textarea>
				<script type="text/javascript">
					CKEDITOR.replace( 'multiPage.description');
				</script>
			  </td>
	
			</tr><tr>
				
			  <td colspan="2" class="label">
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
	
			</tr>
	
		  </table>
	</form>
		</div><!--//mainContent-->
	
		<div class="sidenav">
		
						<form method="post" action="uploadmultipageimage.do" enctype="multipart/form-data">
					
						<input type="hidden" name="parent" value="${multiPage.parentMultiPage.id}" />
						<input type="hidden" name="multipage_id" value="${multiPage.id}" />
					
						<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
						<c:if test="${fn:length(multiPage.images) > 0}">
		  <h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>
	
		  <ol>
	
		    
			<c:forEach items="${multiPage.images}" var="img">
			
			<li>
	
			  <img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" />
	
			  <span><a href="#"><strong>${img.filename}</strong></a> 
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
			  <a onclick="return confirm('Do you really want to delete this image?');" href="deletemultipageimage.do?multipage_id=${multiPage.id}&image_id=${img.id}" class="delete">[Delete]</a></span>
					
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
	
			<c:choose>
				<c:when test="${(company.name eq 'neltex') and (multiPage.jsp eq 'careers')}">
					<li>You are only allowed to upload 1 images.</li>
				</c:when>
				<c:otherwise>
					<li>You are only allowed to upload 3 images.</li>
				</c:otherwise>
			</c:choose>
	
			<li>500 x 590 is the most advisable size of image that should be uploaded.</li>
	
		  </ul>
	
			<div id="uploadDiv">
		  <table width="100%" border="0" cellspacing="0" cellpadding="3">
	
		    <tr>
				
			  <td colspan="2">
									    
									    <input id="file" name="upload" type="file" /></td>
	
			</tr>
	
		  </table>
	
		  <p class="uploadButtons">
		   
					<input type="submit" value="Upload" class="btnBlue">
	</div>
		  
		</form>
		
		
		  <h3><img src="images/iPage.gif" align="absmiddle" /> Page Setup</h3>
	
		  <table border="0" cellspacing="0" cellpadding="3">
	
					
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
					<tr>
						
						<td>
						
						<s:checkbox name="multiPage.featuredC" value="%{multiPage.featured}" theme="simple" id="multiPage_isSingleFeaturedC" />
						</td>
			  <td>This page is a featured page.</td>
	
						
					</tr>
					</c:if>
					
					
					
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
					<tr>
						
						<td>
						
						<s:checkbox name="multiPage.hiddenC" value="%{multiPage.hidden}" theme="simple" id="multiPage_hiddenC" />
						</td>
			  <td>Hide this page in the website.</td>
	
						
					</tr>
					</c:if>
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
					<tr>
						
						<td>
						
						<s:checkbox name="multiPage.hasFileC" value="%{multiPage.hasFile}" theme="simple" id="multiPage_hasFileC"/>
						</td>
			  <td>This page has files.</td>
	
						
					</tr>
					</c:if>
					
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
					<tr>
						
						<td>
						
						<s:checkbox name="multiPage.loginRequiredC" value="%{multiPage.loginRequired}" theme="simple" id="multiPage_loginRequiredC"/> 
						
						</td>
			  <td>This page requires member login.</td>
	
						
					</tr>
					
					<tr>
						<td>
						<s:checkbox name="multiPage.hasPublicationDate" value="%{multiPage.hasPublicationDate}" theme="simple" id="multiPage_hasPublicationDateC"/> 
						</td>
			  			<td>This page has publication date.</td>
					</tr>
					
				</c:if>
		  </table>
	
		</div><!--//sidenav-->
	
		<div class="clear"></div>
	
	</div><!--//container-->
	
	<script type="text/javascript">
		<c:if test="${(company.name eq 'neltex') and (multiPage.jsp eq 'careers') and (fn:length(multiPage.images) gt 0)}">
			document.getElementById('uploadDiv').style.display = 'none';
		</c:if>
	</script>
</div>
</body>
</html>