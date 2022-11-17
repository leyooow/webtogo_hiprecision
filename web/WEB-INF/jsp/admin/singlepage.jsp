<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="pages" />
<c:set var="pagingAction" value="singlepage.do" />



<c:set var="menu" value="pages" />


<c:if test="${singlePage.parent != null}">
	<c:set var="showBackLinks" value="true" />						
</c:if>


<body>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="../javascripts/wz_tooltip.js"></script>



<c:set var="menu" value="pages" />

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
<div class="contentWrapper" id="contentWrapper">
	<c:if test="${param['evt'] != null}">
						<ul>
							<c:if test="${param['evt'] == 'pageexist'}">
								<li><span class="actionMessage">Page was not created because a similar page already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Page was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Page was successfully deleted.</span></li>
							</c:if>
						</ul>
					</c:if>
	<c:choose>
	<c:when test="${param['evt'] != 'save' && param['evt'] != 'add' && param['evt'] != 'pageexist'}">
    <div class="content">
     <div class="pageTitle">

	    <h1><strong>Single Pages</strong></h1>
	    
	    <ul>
		  <li>[<a href="addsinglepage.do">Add Single Page</a>]</li>
		</ul>
		

		<div class="clear"></div>

	  </div><!--//pageTitle-->
	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>

	  <div class="clear"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
						<tr>
							<th >Name</th>
							
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
								<th>JSP</th>
							</c:if>
							
							<th >Is Home?</th>
							<th ">Featured</th>
							<th >Hidden</th>
							
							<th >Updated On</th>
							<th >Created On</th>
							<th >Action</th>
						</tr>
						<c:set var="count" value="0" />
						<c:forEach items="${singlePages}" var="singlePage">
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
							<td><a href="editsinglepage.do?singlepage_id=${singlePage.id}">${singlePage.name}</a></td>
							
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
								<td>${singlePage.jsp}.jsp</td>
							</c:if>
							
							<td>
								<c:choose>
									<c:when test="${singlePage.isHome}">
										<b>Yes</b>
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							</td>
							
							<td>
								<c:choose>
									<c:when test="${singlePage.isSingleFeatured}">
										<b>Yes</b>
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							</td>
							
							<td>
								<c:choose>
									<c:when test="${singlePage.hidden}">
										<b>Yes</b>
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							</td>
							
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${singlePage.updatedOn}" /></td> 
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${singlePage.createdOn}" /></td>
							<td>
							
							<a href="editsinglepage.do?singlepage_id=${singlePage.id}">Edit</a> 
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
							|
							<a href="deletesinglepage.do?singlepage_id=${singlePage.id}" onclick="return confirm('Do you really want to remove this page?');">Delete</a>
							</c:if>
							
							</td>
						</tr>
						</c:forEach>
						
					</table>
		
	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>

	  <div class="clear"></div>
	

	</div><!--//content-->
	</c:when>
	<c:otherwise>
	
	<div class="content">
	<div class="pageTitle">

	    <h1><a name="addPage"></a><strong>Add New Page</strong></h1>
		<ul>
		  <li>[<a href="singlepage.do">Single Pages</a>]</li>
		</ul>
		
			<div class="clear"></div>
			</div>
	<c:set var="formAction" value="savesinglepage.do" />
						<c:set var="edititableName" value="true" />	
			<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		<c:if test="${singlePage.id > 0}">
		<input type="hidden" name="update" value="1">
		<input type="hidden" name="singlepage_id" value="${singlePage.id}">
		</c:if>
		
		<c:if test="${multiPage != null}">
		<input type="hidden" name="multipage_id" value="${multiPage.id}">
		</c:if>
		
	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>

		  <td class="label">Name</td>

		  <td align="left"><input type="text" id="singlepage_name" name="singlePage.name" value="${singlePage.name}" class="inputContent" /></td>

		</tr>
		<c:if test="${singlePage.language== null}"> 
				<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">
		<tr>
			
		  <td class="label">JSP</td>

		   <td align="left"><input type="text" id="singlepage_jsp" name="singlePage.jsp" value="${singlePage.jsp}" class="inputContent" /></td>

		</tr>
		</c:if></c:if>
		<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
				<%-- <c:if test="${multiPage.id== 1111}"> 1097 local ONLY--><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID --%>
			<c:if test="${  singlePage.docRoom == null and singlePage.startTime == null  and multiPage.id  != 1111 }"> 
		<tr>

		  <td class="label">Title</td>

		   <td align="left"><input type="text" id="singlepage_title"  name="singlePage.title" value="${singlePage.title}" class="inputContent" /></td>

		</tr>
		
		<tr id="">

		  <td class="label">Sub Title</td>

		   <td align="left"><input type="text" id="singlepage_name"  name="singlePage.subTitle" value="${singlePage.subTitle}" class="inputContent" /></td>

		</tr>
		</c:if>
		<c:if test="${singlePage.language== null}"> 
		<tr>

		  <td class="label">Valid Until</td>

		   <td align="left">
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
									 
					<input type="text" id="item_date" name="itemDate" value="${idate}" class="inputContent" /> 
					<a href="javascript:;" id="item_date_button"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>
					   
					  
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

		</tr>
		</c:if>
		

	   	<c:if test="${(singlePage.id > 0 && multiPage == null && (user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'))}">
				<tr>
					 <td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.isHomeC" value="%{singlePage.isHome}" theme="simple" />
					
					This page is the default homepage.</td>
					
				</tr>
				</c:if>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.isSingleFeaturedC" value="%{singlePage.isSingleFeatured}" theme="simple" />
					
					This page is a featured page.</td>

					
				</tr>
				</c:if>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.isFormC" value="%{singlePage.isForm}" theme="simple" />
				This is a form page.</td>

					
				</tr>
				</c:if>
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.hiddenC" value="%{singlePage.hidden}" theme="simple" />
					Hide this page in the website.</td>

					
				</tr>
				</c:if>
				
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.loginRequiredC" value="%{singlePage.loginRequired}" theme="simple" />
					
					This page requires member login.</td>

					
				</tr>
			</c:if>
	 
		
		<tr>

		  <td class="label">Content</td>

		   <td align="left">
		 
		  <textarea id="singlePage.content" name="singlePage.content" >${singlePage.content}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'singlePage.content');
			</script>
		  </td>

		</tr>
		
		<tr id="fileList" style="display:none;">
				<td colspan="2"><br/><h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>
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
			
		  <td colspan="2" class="label">
			<c:choose>
						<c:when test="${singlePage.id > 0}">
						
							<input type="reset" value="Reset" class="btnBlue">
				<input type="submit" value="Update" class="btnBlue">
						</c:when>
						<c:otherwise>
							
						
				<input type="submit" value="Add" class="btnBlue">
						</c:otherwise>
					</c:choose>
		</td>

		</tr>

	  </table>
</form>			

	</div><!--//content-->
	</c:otherwise>
	</c:choose> <!-- if view page or add page -->

	<div class="sidenav">
	
	</div><!--//sidenav-->
</div>

	<div class="clear"></div>

  </div><!--//container-->
  
  <script type="text/javascript">
		<c:if test="${company.name eq 'neltex'}">
			document.getElementById('fileList').style.display = 'table-row';
			
			<c:if test="${singlePage.jsp ne 'news-and-events'}">
				$('#uploadImageForm').css('display','none');
				$('#subTitleRow').css('display','none');
				$('#validUntilDateRow').css('display','none');
				$('[name="uploadFileForm"]').css('display','none');
			</c:if>
			<c:if test="${multiPage.name eq 'News and Events' or multiPage.name eq 'Corporate History'}">
				$('#uploadImageInstruction').css('display','none');
				$('#uploadImageReminder').css('display','none');
				$('#uploadImageForm').css('display','block');
				<c:if test="${multiPage.name eq 'News and Events'}">
					$('#subTitleRow').css('display','');
				</c:if>
			</c:if>
		</c:if>
	</script>
</body>

</html>