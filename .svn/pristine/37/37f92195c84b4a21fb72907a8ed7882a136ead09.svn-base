<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="pages" />
<c:set var="submenu" value="${multiPage.name}" />
<c:set var="pagingAction" value="editmultipagechildlist.do?multipage_id=${multiPage.id}" />

<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>

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

<c:choose>
	<c:when test="${company.name eq 'hiprecision' or company.name eq 'hiprecisiononlinestore'}">
		<script type='text/javascript' src='https://www.hi-precision.com.ph/dwr/interface/DWRImageAction.js'></script>
		<script type='text/javascript' src='https://www.hi-precision.com.ph/dwr/engine.js'></script>
	</c:when>
	<c:otherwise>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
</c:otherwise>
</c:choose>

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
			if(document.getElementById('announce').checked == true){
				document.getElementById('singlepage_subtitle').value = "announcement";
			}else{
				document.getElementById('singlepage_subtitle').value = "";
			}
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
<style type="text/css">
	
	#phoneticlong li, #buttons li {
		margin-bottom: 0px;
		margin-top: 4px;
		margin-left: 10px;
		font-size: 12px;
		cursor: hand;
		cursor: pointer;
	}

</style>

<c:choose>
	<c:when test="${company.name eq 'hiprecision' or company.name eq 'hiprecisiononlinestore'}">
		<script type='text/javascript' src='https://www.hi-precision.com.ph/dwr/interface/DWRMultiPageAction.js'></script>
		<script type='text/javascript' src='https://www.hi-precision.com.ph/dwr/engine.js'></script>
	</c:when>
	<c:otherwise>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRMultiPageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
</c:otherwise>
</c:choose>

<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/core.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/events.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/css.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/coordinates.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/drag.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/dragsort.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/cookies.js"></script>

<script language="JavaScript" type="text/javascript">
	var dragsort = ToolMan.dragsort()
	var junkdrawer = ToolMan.junkdrawer()
	<c:if test="${company.name ne 'gurkkatest'}">
		window.onload = function() {
			dragsort.makeListSortable(document.getElementById("phoneticlong"),
						verticalOnly, saveOrder)
		}
	</c:if>
	function verticalOnly(item) {
		item.toolManDragGroup.verticalOnly()
	}
	
	function saveOrder(item) {
		var group = item.toolManDragGroup
		var list = group.element.parentNode
		var id = list.getAttribute("id")
		if (id == null) return
		group.register('dragend', function() {
			ToolMan.cookies().set("list-" + id, 
					junkdrawer.serializeList(list), 365)
		})
	}
	
	function save() {
		var items = ToolMan.junkdrawer().serializeList(document.getElementById('phoneticlong'));
		items = items.split("|");
		
		DWRMultiPageAction.saveNewOrder(items, back);
	}
	
	function back() {
		window.location = 'editmultipagechildlist.do?multipage_id=${multiPage.id}&evt=sortUpdated';
	}

</script>
<body>

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
    <div class="mainContent">
	  	<s:actionmessage />
				<s:actionerror />
			<c:if test="${param['evt'] != null}">
  						<ul>
  						
  						<c:if test="${param['evt'] == 'update'}">
  							<li><span class="actionMessage">Page was successfully updated.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'updatechild'}">
  							<li><span class="actionMessage">Page item was successfully updated.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'deletechild'}">
  							<li><span class="actionMessage">Page item was successfully deleted.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'save'}">
  							<li><span class="actionMessage">Page item was successfully created.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'addfailed'}">
  							<li><span class="actionMessage">Page cannot be created becase a similar page already exist.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'sortUpdated'}">
  							<li><span class="actionMessage">The item ordering have been updated.</span></li>
  						</c:if>
  						
  						</ul>
  					</c:if>
  					
  					<div class="pageTitle">

	    <h1><strong>Edit Multipages</strong></h1>

		<ul>

		  <li>
		 
					
					
					
					
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
						<a href="multipage.do">Multi Pages</a>
					
					</c:if>

					
		  
		  </li>
			<li>&raquo;</li>
		 

		 
		  
		  <li><a href="editmultipage2.do?multipage_id=${multiPage.id}">Edit ${multiPage.name} page</a></li>|
		  <li><a href="#addPage">Add New Page</a></li>
		</ul>
			<div class="clear"></div>
			</div>
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
			
			
							<tr >
								<th >Name</th>
								<th >Hidden</th>
								
								<c:choose>
									<c:when  test="${(company.name eq 'hbc' and multiPage.id eq 892) or (company.name eq 'mraircon' and multiPage.id eq 1732) }">
										<th>Publication Date</th>
									</c:when>
									<c:otherwise>
										<th>Valid Until</th>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${(company.name eq 'boysen' and multiPage.id eq 224) 
								or (company.name eq 'stockbridge' and (multiPage.name eq 'Events' or multiPage.name eq 'Blog'))
								or (company.name eq 'altair' and multiPage.id eq 2029) 
								or ((company.name eq 'rizalacademy') and (multiPage.id eq 2045 or multiPage.id eq 2046))
								or (multiPage.hasPublicationDate)}">
									<th>Publication Date</th>
								</c:if>
								
								<th >Updated On</th>
								<th >Created On</th>
							
								<th >Action</th>
							</tr>
							<c:set var="count" value="0" />
							
							<c:forEach items="${multiPageItems}" var="i">
							<c:choose>
								<c:when test="${company.id eq 1200000}">
									<c:set var="editUrl" value="editsinglepage.do?singlepage_id=${i.id}"/>
								</c:when>
								<c:otherwise>
									<c:set var="editUrl" value="editmultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${i.id}"/>
								</c:otherwise>
							</c:choose>
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
								<td><a id="edit" href="${editUrl }" onclick="return submitToEdit(this);">${i.name}</a></td>
								<td>${i.hidden}</td>
								<c:choose>
									<c:when  test="${(company.name eq 'hbc' and multiPage.id eq 892) or (company.name eq 'uniorientagents')
										or (company.name eq 'mraircon' and multiPage.id eq 1732)}">
										<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.itemDate}" /></td>
									</c:when>
									<c:otherwise>
										<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.validTo}" /></td>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${(company.name eq 'boysen' and multiPage.id eq 224) 
								or (company.name eq 'stockbridge' and (multiPage.name eq 'Events' or multiPage.name eq 'Blog'))
								or (company.name eq 'altair' and multiPage.id eq 2029)
								or ((company.name eq 'rizalacademy') and (multiPage.id eq 2045 or multiPage.id eq 2046))
								or (multiPage.hasPublicationDate)}">
									<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.datePublished}" /></td>
								</c:if>
								
								
								<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.updatedOn}" /></td>
								<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.createdOn}" /></td>
															
								<td>
									<a id="edit" href="${editUrl }" onclick="return submitToEdit(this);">Edit</a>
									<!-- <a id="edit" href="editmultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${i.id}" onclick="return submitToEdit(this);"> | Edit as a child</a> -->
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
										  |
										<a id="delete" href="deletemultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${i.id}" onclick="return confirm('Do you really want to delete this item?');">Delete</a>
									</c:if>	
									
								<%-- 
									<c:if test="${imageCount > 0}" >
										<img src="images/imageIcon.png" title="${imageCount}" />
									</c:if>
								
								<c:if test="${i.fileCount > 0}"	>
										<img src="images/folderIcon.png" title="${i.fileCount}" />
								</c:if>
								--%>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						
					</table>	
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>		
	  </div><!--//mainContent-->
	  <div class="sidenav">
		<h2>Sort Pages <br /> - Drag and Arrange -</h2>
		<ul id="phoneticlong">
			
												<c:forEach items="${multiPageItems}" var="i">
													<li itemID="${i.id}">${i.name}</li>
												</c:forEach>
												
		</ul>
		<ul>
			<li>									<input type="button" value="Save" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='editmultipagechildlist.do?multipage_id=${multiPage.id}'" />
			</li></ul>									
	</div><!--//sidenav-->
	  <div class="mainContent">
	  <div class="pageTitle">

	    <h1><a name="addPage"></a><strong>Add New Page</strong></h1>

		
			<div class="clear"></div>
			</div>
	  <c:set var="formAction" value="addmultipageitem.do" />
				<c:set var="edititableName" value="${true}" />		
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

		  <td class="label" id="nameLabel">
		  Name
		  </td>

		  <td align="left"><input type="text" id="singlepage_name" name="singlePage.name" value="${singlePage.name}" class="inputContent" /></td>

		</tr>
		<c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">
		<tr style="display:none;">

		  <td class="label" id="nameLabel">
		  Posted By
		  </td>

		  <td align="left"><input type="text" id="singlepage_prev" name="singlePage.prev" value="${user.firstname} ${user.lastname}" class="inputContent" /></td>

		</tr>
		</c:if>
		<c:if test="${singlePage.language== null}"> 
				<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">
		<tr>
			
		  <td class="label">JSP</td>

		   <td align="left"><input type="text" id="singlepage_jsp" name="singlePage.jsp" value="${singlePage.jsp}" class="inputContent" /></td>

		</tr>
		</c:if></c:if>
		<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
		
		<%--
		<c:if test="${multiPage.id== 1111  or singlePage.docRoom != null or singlePage.startTime != null}"> 
			<%@include file="hpdskedIncludes/doctorSchedule.jsp" %>
		</c:if>
		--%>
		
		<%-- <c:if test="${multiPage.id== 1111}"> 1097 local ONLY--><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID --%>
		
		<c:if test="${  singlePage.docRoom == null and singlePage.startTime == null  and multiPage.id  != 1111 }"> 
		<tr id="titleRow">

		  <td class="label" id="titleLabel">
		  <c:choose>
			  <c:when test="${company.name eq 'agian' and param.multipage_id eq '2626' }">
			  	Date Posted
			  </c:when>
			  <c:otherwise>
			  	Title
			  </c:otherwise>
		  </c:choose>
		  </td>

		   <td align="left"><input type="text" id="singlepage_title"  name="singlePage.title" value="${singlePage.title}" class="inputContent" /></td>

		</tr>
		
		<tr id="subTitleRow">

		  <td class="label">
		  <c:choose>
		  	<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'news-and-events'}">
		  		Tags
		  	</c:when>
		  	<c:when test="${company.name eq 'agian' and param.multipage_id eq '2626' }">
		  		
		  	</c:when>
		  	<c:when test="${company.name eq 'agian' and param.multipage_id eq '2628' }">
		  		Date
		  	</c:when>
		  	<c:when test="${company.name eq 'bci' }">
		  		Link
			</c:when>
		  	<c:otherwise>
		  		Sub Title
		  	</c:otherwise>
		  </c:choose></td>
		<c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">
			<td align="left"><input type="checkbox" id="announce"/> Announcement</td>
		</c:if>
		<td align="left" <c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">style="display:none;"</c:if>><input type="text" id="singlepage_subtitle"  name="singlePage.subTitle" value="${singlePage.subTitle}" class="inputContent" /></td>
		
		</tr>
		</c:if>
		<c:if test="${singlePage.language== null}"> 
		<%-- <tr id="validUntilDateRow" <c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">style="display:none;"</c:if>> --%>
		<tr id="validUntilDateRow"<c:if test="${company.name eq 'agian' and param.multipage_id ne '2628'}">style="display:none"</c:if>>

		  <td class="label"><c:if test="${company.name eq 'agian'}">Valid Until </c:if>Date</td>

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
		
		<c:if test="${(company.name eq 'stockbridge' and (multiPage.name eq 'Events' or multiPage.name eq 'Blog'))
						or (multiPage.hasPublicationDate)}">
		<tr>

		  <td class="label">Publication Date</td>

		   <td align="left">
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.datePublished}" var="date"/>
									 
					<input type="text" id="date_published" name="datePublished" value="${date}" class="inputContent" /> 
					<a href="javascript:;" id="date_published_button"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>
					   
					  
					<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "date_published",     // id of the input field
					        ifFormat       :    "%m-%d-%Y",      // format of the input field
					        button         :    "date_published_button",  // trigger for the calendar (button ID)
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
					
					<label id="isFeatured">This page is a featured page.</label></td>

					
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

		  <td class="label" id="contentLabel">Content</td>

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
	</div><!--//mainContent-->

	
</div>

	<div class="clear"></div>

  </div><!--//container-->

<script type="text/javascript">
		<c:if test="${company.name eq 'neltex'}">
			document.getElementById('fileList').style.display = 'table-row';
			$('#validUntilDateRow').css('display','none');
			<c:if test="${multiPage.jsp ne 'news-and-events'}">
				$('#subTitleRow').css('display','none');
				$('#fileList').css('display','none');
			</c:if>
		</c:if>
		<c:choose>
		  	<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'news-and-events'}">
		  		$('#nameLabel').html('Article Name');
		  		$('#titleLabel').html('Author');
		  		$('#isFeatured').html('To be featured in homepage');
		  	</c:when>
		  	<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'careers'}">
		  		$('#nameLabel').html('Job Title');
		  		$('#titleRow').css('display','none');
		  	</c:when>
		  	<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'corporate-history'}">
	  			$('#nameLabel').html('Event');
	  			$('#titleLabel').html('Year');
	  			$(document).ready(function(){
	  	  		    $('#singlepage_title').keypress(validateNumber);
	  	  			$('#singlepage_title').attr("maxlength","4");
	  	  			$('#singlepage_title').change(checkYear);
	  	  		});
	  			document.getElementById('fileList').style.display = 'table-row';
	  		</c:when>
	  		<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'about-us'}">
	  			document.getElementById('fileList').style.display = 'table-row';
	  		</c:when>
	  		<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'faqs'}">
	  			$('#nameLabel').html('Question');
	  			$('#titleRow').css('display','none');
	  			$('#contentLabel').html('Answer');
	  		</c:when>
		  	<c:otherwise>
		  	</c:otherwise>
	  	</c:choose>
	  	
	  	function validateNumber(event) {
  		    var key = window.event ? event.keyCode : event.which;
  		    if (event.keyCode == 8 || event.keyCode == 46
  		     || event.keyCode == 37 || event.keyCode == 39) {
  		        return true;
  		    }
  		    else if ( key < 48 || key > 57 ) {
  		        return false;
  		    }
  		    else {
  		    	return true;
  		    }
  		}
  		
  		function checkYear() {
  			if(parseInt($('#singlepage_title').val()) > 2015) {
  				alert("Please input past or current year");
  				$('#singlepage_title').val('')
		    	return false;
		    }
  		}
	</script>
	
</body>

</html>