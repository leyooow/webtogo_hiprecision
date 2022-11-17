<%@include file="includes/header.jsp"  %>
<link href="../admin/css/modal.css" rel="stylesheet" type="text/css" />
<c:set var="menu" value="forms" />
<c:set var="pagingAction" value="forms.do" />
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
<body  onload="document.getElementById('company_id').focus();">

  <div class="container">
	<c:set var="menu" value="dashboard" />
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
<c:choose>
<c:when test="${param['evt'] != 'save' && param['evt'] != 'add' && param['evt'] != 'pageexist'}">
    <div class="content">
	  	<div class="pageTitle">
		
			
				
	    <h1><strong>Forms</strong>: View Forms</h1>

		<ul>

		

		  <li>[<a href="addforms.do">Add Form</a>]</li>

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
			
			<th>Hidden</th>
			
			<th>Updated On</th>
			<th>Created On</th>
			<th>Action</th>

		</tr>
		<c:set var="count" value="0" />
		<c:forEach items="${formPages}" var="form">
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
						<td>${form.name}</td>
						
						<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
							<td>${form.jsp}.jsp</td>
						</c:if>
						
						<td>
							<c:choose>
								<c:when test="${form.hidden}">
									<b>Yes</b>
								</c:when>
								<c:otherwise>
									No
								</c:otherwise>
							</c:choose>					
						</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${form.updatedOn}" /></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${form.updatedOn}" /></td>
						<td>
						
						<a href="editformpage.do?form_id=${form.id}">Edit</a> |
						<a href="deleteformpage.do?form_id=${form.id}" onclick="return confirm('Do you really want to remove this form?');">Remove</a>
						
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
	<h1><strong>Forms</strong>: New Form</h1>
	<ul>
		  <li>[<a href="forms.do">Forms</a>]</li>
	</ul>
<div class="clear"></div>
		
	  </div><!--//pageTitle-->	
		
	<a name="addForm" />
		<c:set var="formAction" value="saveform.do" />
		<c:set var="edititableName" value="${true}" />
				
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<c:if test="${formPage.id > 0}">
		<input type="hidden" name="form_id" value="${formPage.id}" />
		</c:if>
		
		<table width="50%">
			<tr>
				<td class="label">Name</td>
				
				<td  align="left"><input type="text" id="formPage_name" name="formPage.name" value="${formPage.name}" class="w385"></td>
			</tr>
			
			<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
			<tr>
				<td class="label">JSP</td>
				
				<td align="left"><input type="text" id="formPage_jsp" name="formPage.jsp" value="${formPage.jsp}" class="w385"></td>
			</tr>
			</c:if>
			
			<tr> 
				<td class="label">Title</td>
				
				<td align="left"><input type="text" name="formPage.title" value="${formPage.title}" class="textbox4"></td>
			</tr>

			<tr>
				<td></td>
				
				<td>
				
				<s:checkbox name="formPage.hidden" value="%{formPage.hidden}" theme="simple" /> <i><b>Hide this page in the website.</b></i>
				
				</td>
				
			</tr>
			
			<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
			<tr>
				<td ></td>
				<td>
				
				<s:checkbox name="formPage.fileUploadAllowed" value="%{formPage.fileUploadAllowed}" theme="simple" /> <i><b>This form have an upload field.</b></i>
				 
				</td>
				
			</tr>
			</c:if>

			<tr>
				<td class="label">Top Content</td>
				<td align="left">	 
				<!--<FCK:editor basePath="/FCKeditor" instanceName="formPage.topContent" width="98%" height="400" toolbarSet="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') ? 'Custom_SuperUser' : 'Custom'}" value="${formPage.topContent}"></FCK:editor>
				<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>-->
					<textarea id="formPage.topContent" name="formPage.topContent" >${formPage.topContent}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'formPage.topContent');
			</script>
				
				</td>
			</tr>
			
			<tr>
				<td class="label">Bottom Content</td>
				<td align="left">
				<!--<FCK:editor basePath="/FCKeditor" instanceName="formPage.bottomContent" width="98%" height="400" toolbarSet="${(user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator') ? 'Custom_SuperUser' : 'Custom'}" value="${formPage.bottomContent}"></FCK:editor>
				<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>
					-->
					<textarea id="formPage.bottomContent" name="formPage.bottomContent" >${formPage.bottomContent}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'formPage.bottomContent');
			</script>
				
				</td>
			</tr>
			
	
			<tr>
				<td colspan="2" style="border: 0px;">
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
		</form>
	</div>
	</c:otherwise>
</c:choose>
</div>

	
  </div><!--//container-->

</body>

</html>