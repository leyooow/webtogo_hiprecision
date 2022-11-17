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
			if(document.getElementById("c1") != null)
				document.getElementById("c1").value = document.getElementById('singlePage_isHomeC').checked;

			if(document.getElementById("c2") != null)
				document.getElementById("c2").value = document.getElementById('singlePage_isSingleFeaturedC').checked;

			if(document.getElementById("c3") != null)
				document.getElementById("c3").value = document.getElementById('singlePage_hiddenC').checked;

			if(document.getElementById("c4") != null)
				document.getElementById("c4").value = document.getElementById('singlePage_isFormC').checked;

			if(document.getElementById("c5") != null)
				document.getElementById("c5").value = document.getElementById('singlePage_hasFileC').checked;

			if(document.getElementById("c6") != null)
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

	    <h1><strong>Multiple Pages</strong></h1>

		<ul>
		  <li>[<a href="addmultipage.do">Add Multiple Page</a>]</li>
		</ul>

		<div class="clear"></div>

	  </div><!--//pageTitle-->
	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>

	  <div class="clear"></div>
	<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					
						<tr>
							<th>Name</th>
							
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
								<th>JSP</th>
							</c:if>
							
							<th >Hidden</th>
							<th >Featured</th>
							<th>Updated On</th>
							<th >Created On</th>
							<th>Action</th>
						</tr>
						<c:set var="count" value="0" />
						<c:forEach items="${multiPages}" var="multiPage">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
							<td><a href="editmultipagechildlist.do?multipage_id=${multiPage.id}">${multiPage.name}</a></td>
							<td>${multiPage.jsp}.jsp</td>
							<td>
							
								<c:choose>
									<c:when test="${multiPage.hidden}">
										<b>Yes</b>
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							
							</td>
							
							<td>
							
								<c:choose>
									<c:when test="${multiPage.featured}">
										<b>Yes</b>
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							
							</td>
							
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${multiPage.updatedOn}" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${multiPage.createdOn}" /></td>
							<td>
							
							<a href="editmultipagechildlist.do?multipage_id=${multiPage.id}">Edit Pages</a>
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
							|
							<a href="editmultipage2.do?multipage_id=${multiPage.id}">Edit</a> 
							|
							<a href="deletemultipage.do?multipage_id=${multiPage.id}" onclick="return confirm('Do you want to really delete this?');">Delete</a>
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
		  <li>[<a href="multipage.do">Multiple Pages</a>]</li>
		</ul>
		
			<div class="clear"></div>
			</div>
	<c:set var="formAction" value="savemultipage.do" />
	<c:choose>
		<c:when test="${empty multipage.id}">
			<%@include file="multipageform.jsp"  %>
		</c:when>
		<c:otherwise>
			<%@include file="multipageitemform.jsp"  %>
		</c:otherwise>
	</c:choose>
	
	
	</div><!--//content-->
	</c:otherwise>
	</c:choose>

	<div class="sidenav">
	
	</div><!--//sidenav-->
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>