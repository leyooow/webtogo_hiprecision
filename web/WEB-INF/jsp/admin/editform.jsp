<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />


<c:set var="menu" value="forms" />
<c:set var="submenu" value="${formPage.name}" />
<c:set var="formAction" value="updateform.do" />
 


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
		
		var name = getElement('singlepage_name');
		

		var messages = "Problem(s) occured: \n\n";

		

		
		if(name.length == 0) {
			messages += "* Name not given";
			alert(messages);
			return false;
		}
		else {
			//alert(document.getElementById("c1").value);
			document.getElementById("c1").value = document.getElementById('singlePage_isHomeC').checked;
			document.getElementById("c2").value = document.getElementById('singlePage_isSingleFeaturedC').checked;
			document.getElementById("c3").value = document.getElementById('singlePage_hiddenC').checked;
			document.getElementById("c4").value = document.getElementById('singlePage_isFormC').checked;
			document.getElementById("c5").value = document.getElementById('singlePage_hasFileC').checked;
			document.getElementById("c6").value = document.getElementById('singlePage_loginRequiredC').checked;
			//alert(document.getElementById("c1").value);
			
			
			return true;
			}

		
	}
	
	function getElement(elementName) {
		return document.getElementById(elementName).value;
		
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

	    <h1><strong>Edit Form Page</strong></h1>

	
					
					
		<div class="clear"></div>
		
	  </div><!--//pageTitle-->
	  <c:if test="${not empty languages}">
			 <h4>Language:
							<c:if test="${formPage.language!= null}"> 
							${formPage.language.language}
							</c:if>
							<c:if test="${formPage.language== null}"> 
							English
							</c:if>
						</h4>
			  <div class="metaBox">
			  	<c:if test="${formPage.language== null}"> 
				English, 
				</c:if>
				<c:if test="${formPage.language != null}"> 
				<a href="editformpage.do?form_id=${param.form_id}">English</a>, 
				</c:if>
			  	<c:forEach items="${languages}" var="lang">
			  		<c:if test="${lang.language ne param.language}">
					, <a href="editformpage.do?form_id=${param.form_id}&language=${lang.language}">${lang.language}</a>
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
		  	<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
				<c:if test="${formPage.id > 0}">
					<input type="hidden" name="update" value="1">
					<input type="hidden" name="form_id" value="${formPage.id}">
				</c:if>
				<input type="hidden" name="language" value="${param.language }"/>	
				
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
				    	<td class="label">HTML Title</td>
						<td>
							<input class="inputMeta" type="text" name="formPage.meta_title" value="${formPage.meta_title}" />
						</td>
						
						<td>&nbsp;</td>
					
				    	<td class="label">Meta Keywords</td>
		
						<td>
							<input class="inputMeta" type="text" name="formPage.meta_keywords" value="${formPage.meta_keywords}" />
						</td>
		
						<td>&nbsp;</td>
						
					</tr>
					<tr valign="top">
						<td class="label">Meta Description</td>
						<td>
							<textarea  class="inputMeta"  name="formPage.meta_description" >${formPage.meta_description}</textarea>
							<!-- <input class="inputMeta" type="text" name="formPage.meta_description" value="${formPage.meta_description}" />-->
						</td>
						
						<td colspan="5" class="label">
							<input type="reset" value="Reset" class="btnBlue">
							<input type="submit" value="Update" class="btnBlue">
						</td>
					</tr>
				</table>
			</form>
		</div><!--//metaBox-->
	</c:if>
	
	
	 <form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		<input type="hidden" name="language" value="${param.language }"/>
		<c:if test="${formPage.id > 0}">
		<input type="hidden" name="form_id" value="${formPage.id}" />
		</c:if>
		
		 <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>

		  <td class="label">Name</td>

		  <td align="left"><input type="text" id="formPage_name" name="formPage.name" value="${formPage.name}" class="inputContent" /></td>

		</tr>
		<c:if test="${formPage.language eq null}"> 
			<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">
				<tr>
					
				  <td class="label">JSP</td>
		
				   <td align="left"><input type="text" id="formPage_jsp" name="formPage.jsp" value="${formPage.jsp}" class="inputContent" /></td>
		
				</tr>
			</c:if>
		</c:if>
		<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
				<%-- <c:if test="${multiPage.id== 1111}"> 1097 local ONLY--><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID --%>
			<c:if test="${  singlePage.docRoom == null and singlePage.startTime == null  and multiPage.id  != 1111 }"> 
		<tr>

		  <td class="label">Title</td>

		   <td align="left"><input type="text" name="formPage.title" value="${formPage.title}" class="inputContent" /></td>

		</tr>
		<tr>
		  <td class="label">
		  <c:choose>
		  	<c:when test="${formPage.jsp eq 'contact-us' and company.name eq 'neltex'}">
		  		Contact Details
		  	</c:when>
		  	<c:otherwise>
		  		Top Content
		  	</c:otherwise>
		  </c:choose>
		  </td>

		   <td align="left">
		 
		  <textarea id="formPage.topContent" name="formPage.topContent" >${formPage.topContent}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'formPage.topContent');
			</script>
		  </td>
		  </tr>
		  <tr>
		  <td class="label">
		  <c:choose>
		  	<c:when test="${formPage.jsp eq 'contact-us' and company.name eq 'neltex'}">
		  		Map
		  	</c:when>
		  	<c:otherwise>
		  		Bottom Content
		  	</c:otherwise>
		  </c:choose>
		  </td>

		   <td align="left">
		 
		  <textarea id="formPage.bottomContent" name="formPage.bottomContent" >${formPage.bottomContent}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'formPage.bottomContent');
			</script>
		  </td>
		</tr>
		
			
			
	
			<tr>
				<td colspan="2" class="label">
				
				<c:choose>
					<c:when test="${formPage.id > 0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>
			<input type="hidden"  id="c1" name="singlePage.isHome" />
		<input type="hidden"  id="c2" name="singlePage.isSingleFeatured" />
		
		<input type="hidden"  id="c3" name="singlePage.hidden" />
		<input type="hidden"  id="c4" name="singlePage.isForm" />
		<input type="hidden"  id="c5" name="singlePage.hasFile" />
		<input type="hidden"  id="c6" name="singlePage.loginRequired" />
		</form>

		
		</c:if>
	
		
		
	</div><!--//mainContent-->

	<div class="sidenav">
		<c:choose>
					<c:when test="${multiPage != null}">
						<form method="post" action="uploadmultipageitemimage.do" enctype="multipart/form-data">
						<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
					 
					</c:when> 
					<c:otherwise>
						<form method="post" action="uploadformimage.do" enctype="multipart/form-data">
					</c:otherwise>
				</c:choose>
				
					<input type="hidden" name="form_id" value="${formPage.id}" />
					<c:if test="${fn:length(formPage.images) > 0}">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>

	  <ol>

	    
		<c:forEach items="${formPage.images}" var="img">
		<li>

		  <img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" />

		  <span><a href="#"><strong>${img.filename}</strong></a> 
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
		  <a onclick="return confirm('Do you really want to delete this image?');" href="deleteformpageimage.do?form_id=${formPage.id}&image_id=${img.id}" class="delete">[Delete]</a></span>
				
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
		
		<div id="uploadImageDiv">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>

	  <ul class="uploadImageNote">

		<li>You are only allowed to upload 3 files.</li>

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

	  </div>
	</form>
	  <h3><img src="images/iPage.gif" align="absmiddle" /> Page Setup</h3>

	  <table border="0" cellspacing="0" cellpadding="3">

	   		<tr>
				
				<td>
				
				<s:checkbox name="formPage.hidden" value="%{formPage.hidden}" theme="simple" /> 
				
				</td>
				<td>Hide this page in the website.</td>
			</tr>
			<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
			<tr>
			
				<td>
				
				<s:checkbox name="formPage.fileUploadAllowed" value="%{formPage.fileUploadAllowed}" theme="simple" /> 
				 
				</td>
				<td>This form have an upload field.</td>
			</tr>
			</c:if>
			
	  </table>

	</div><!--//sidenav-->

	<div class="clear"></div>

  </div><!--//container-->
</div>

 <script type="text/javascript">
 <c:if test="${company.name eq 'neltex'}">
 document.getElementById('uploadImageDiv').style.display = 'none';
 </c:if>
 </script>
</body>

</html>