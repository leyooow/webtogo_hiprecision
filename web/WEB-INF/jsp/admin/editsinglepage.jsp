<%@include file="includes/header.jsp"%>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<c:set var="menu" value="pages" />
<c:choose>
	<c:when test="${singlePage.parent != null}">
		<c:set var="submenu" value="${fn:escapeXml(singlePage.parent.name)}" />
	</c:when>
	<c:otherwise>
		<c:set var="submenu" value="${fn:escapeXml(singlePage.name)}" />
	</c:otherwise>
</c:choose>
<c:if test="${singlePage.parent != null}">
	<c:set var="showBackLinks" value="true" />
</c:if>
<body>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
	
	<!-- JQuery -->
	<link type="text/css" href="javascripts/jquery/jquery-ui-1.8.19.custom.css" rel="Stylesheet" />	
	<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="javascripts/jquery/jquery-ui-1.8.19.custom.min.js"></script>
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
			//document.getElementById("c1").value = document.getElementById('singlePage_isHomeC').checked;
			//alert(document.getElementById('singlePage_hiddenC').checked);
			var isHome = document.getElementById('singlePage_isHomeC');
			if(isHome != null && typeof isHome != 'undefined'){
				document.getElementById("c1").value = document.getElementById('singlePage_isHomeC').checked;
			}
			
			var isFeatured = document.getElementById('singlePage_isSingleFeaturedC');
			if(isFeatured != null && typeof isFeatured != 'undefined'){
				document.getElementById("c2").value = document.getElementById('singlePage_isSingleFeaturedC').checked;
			}
			
			var isHidden = document.getElementById('singlePage_hiddenC');
			if(isHidden != null && typeof isHidden != 'undefined'){
				document.getElementById("c3").value = document.getElementById('singlePage_hiddenC').checked;
			}
			
			var isForm = document.getElementById('singlePage_isFormC');
			if(isForm != null && typeof isForm != 'undefined'){
				document.getElementById("c4").value = document.getElementById('singlePage_isFormC').checked;
			}
			
			if(document.getElementById('announce').checked == true){
				document.getElementById('singlepage_subtitle').value = "announcement";
			}else{
				document.getElementById('singlepage_subtitle').value = "";
			}
			
			/* document.getElementById("c2").value = document.getElementById('singlePage_isSingleFeaturedC').checked;
			document.getElementById("c3").value = document.getElementById('singlePage_hiddenC').checked;
			document.getElementById("c4").value = document.getElementById('singlePage_isFormC').checked; */
			document.getElementById("c5").value = document.getElementById('singlePage_hasFileC').checked;
			document.getElementById("c6").value = document.getElementById('singlePage_loginRequiredC').checked;
			document.getElementById("c7").value = document.getElementById('singlePage_flag1C').checked;
			document.getElementById("c8").value = document.getElementById('singlePage_flag2C').checked;
			
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
	
	function validateImage() {
		if(currentUploads > 0) {
			return true;
		}
		alert("No attached file. Please attached file/s");
		return false;
	}
	
	function validateFile() {
		if(currentDynamicUploads > 0) {
			return true;
		}
		alert("No attached file. Please attached file/s");
		return false;
	}
	
	$(document).ready(function(){
		jQuery('#ImageDialog').dialog({
			autoOpen: false,
			modal:true,
			width:250,			
			draggable:false,
			resizable:true,
			show: "blind",
			hide: "blind",
			buttons:
			{
				"Save":function()
				{
					var title = jQuery('#title').val();
					var caption = jQuery('#caption').val();
					var description = jQuery('#description').val();
					var imageId = document.getElementById("imageId").value;
	
					updateImageTitle(imageId, title);
					updateImageCaption(imageId, caption);
					updateImageDescription(imageId, description);
	
					jQuery('#title'+imageId).val(title);
					jQuery('#caption'+imageId).val(caption);
					jQuery('#description'+imageId).val(description);
	
					if(title != null && title != '')
						jQuery('#tr_title_'+imageId).css("display","");
					else
						jQuery('#tr_title_'+imageId).css("display","none");
	
					if(caption != null && caption != '')
						jQuery('#tr_caption_'+imageId).css("display","");
					else
						jQuery('#tr_caption_'+imageId).css("display","none");
	
					if(description != null && description != '')
						jQuery('#tr_description_'+imageId).css("display","");
					else
						jQuery('#tr_description_'+imageId).css("display","none");
	
					
					<%--//jQuery('#description'+imageId).val(description); --%>
	
					jQuery('#title'+imageId+'_text').html(title);
					jQuery('#caption'+imageId+'_text').html(caption);
					jQuery('#description'+imageId+'_text').html(description);
						
					jQuery('#ImageDialog').dialog('close');	
	
						
				}
				
			}
		});
	});
	
	var openImageDialog = function(id, imageurl, imageName)
	{	
		document.getElementById("imageId").value = id; 		
		var title = jQuery('#title'+id).val();
		var caption = jQuery('#caption'+id).val();
		var description = jQuery('#description'+id).val();
		jQuery('#title').val(title);
		jQuery('#caption').val(caption);
		jQuery('#description').val(description);
		
		jQuery("#dialogimage").attr("src", imageurl);
		jQuery("#imageNameDiv").html(imageName);
		jQuery('#ImageDialog').dialog('open');
		
	}
	
</script>

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

	window.onload = function() {
		var element = document.getElementById("phoneticlong");
		if(element) { 
			dragsort.makeListSortable(document.getElementById("phoneticlong"),
						verticalOnly, saveOrder)
		}
	}
	
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

		DWRImageAction.savePageImagesOrder(items, back);
	}

	function back() {
		<c:choose>
			<c:when test="${multiPage ne null}">
				window.location = 'editmultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${singlePage.id}';
			</c:when>
			<c:otherwise>
				window.location = 'editsinglepage.do?singlepage_id=${singlePage.id}';
			</c:otherwise>
		</c:choose>
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
					<h1>
						<strong>Edit Single Page</strong>
					</h1>
					<ul>
					<li><c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
								<a href="singlepage.do">Single Pages</a>
							</c:if></li>
						<li><c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
								<a href="singlepage.do">Single Pages</a>
							</c:if></li>
						<li>&raquo;</li>
						<li>Edit Single Page</li>
					</ul>
					<c:choose>
						<c:when test="${singlePage.parent != null}">
							<c:set var="formAction" value="updatemultipagechild.do" />
						</c:when>
						<c:otherwise>
							<c:set var="formAction" value="updatesinglepage.do" />
						</c:otherwise>
					</c:choose>
					<div class="clear"></div>
				</div>
				<!--//pageTitle-->
				<c:if test="${not empty languages}">
					<h4>
						Language:
						<c:if test="${singlePage.language!= null}"> 
					${singlePage.language.language}
					</c:if>
						<c:if test="${singlePage.language== null}"> 
					English
					</c:if>
					</h4>
					<div class="metaBox">
						<c:if test="${singlePage.language== null}"> 
		English, 
		</c:if>
						<c:if test="${singlePage.language != null}">
							<a href="editsinglepage.do?singlepage_id=${param.singlepage_id}">English</a>, 
		</c:if>
						<c:forEach items="${languages}" var="lang">
							<c:if test="${lang.language ne param.language}">
			, <a href="editsinglepage.do?singlepage_id=${param.singlepage_id}&language=${lang.language}">${lang.language}</a>
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
							<c:if test="${singlePage.id > 0}">
								<input type="hidden" name="update" value="1">
								<input type="hidden" name="singlepage_id" value="${singlePage.id}">
							</c:if>
							<input type="hidden" name="language" value="${param.language }" />
							<c:if test="${multiPage != null}">
								<input type="hidden" name="multipage_id" value="${multiPage.id}">
							</c:if>
							<table width="98%" border="0" cellspacing="0">
								<tr>
									<td style="width: 130px; font-weight: 600;">HTML Title</td>
									<td><input class="inputMeta" type="text" name="singlePage.meta_title" value="${singlePage.meta_title}" style="width: 100%; font-size: 14px; padding: 3px 3px;"/></td>									
								</tr>
								<tr valign="top">
									<td style="width: 130px; font-weight: 600;">Meta Description</td>
									<td><textarea class="inputMeta" name="singlePage.meta_description" style="width: 100%; font-size: 14px; padding: 3px 3px;">${singlePage.meta_description}</textarea> <!-- input class="inputMeta" type="text" name="singlePage.meta_description" value="${singlePage.meta_description}" /> --></td>
									
								</tr>
								<tr>
									<td style="width: 130px; font-weight: 600;">Meta Keywords</td>
									<td><input class="inputMeta" type="text" name="singlePage.meta_keywords" value="${singlePage.meta_keywords}"  style="width: 100%; font-size: 14px; padding: 3px 3px;"/></td>
									
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td style="text-align: right;" colspan="2"><input type="reset" value="Reset" class="btnBlue"> <input type="submit" value="Update" class="btnBlue"></td>
								</tr>
							</table>
						</form>
					</div>
					<!--//metaBox-->
				</c:if>
				<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
					<c:if test="${singlePage.id > 0}">
						<input type="hidden" name="update" value="1">
						<input type="hidden" name="singlepage_id" value="${singlePage.id}">
						<input type="hidden" name="language" value="${param.language }" />
					</c:if>
					<c:if test="${multiPage != null}">
						<input type="hidden" name="multipage_id" value="${multiPage.id}">
					</c:if>
					<table width="100%" border="0" cellspacing="0" cellpadding="3">
						<tr>
							<td class="label" id="nameLabel">Name</td>
							<td align="left"><input type="text" id="singlepage_name" name="singlePage.name" value="${fn:escapeXml(singlePage.name)}" class="inputContent" /></td>
						</tr>
						<c:if test="${singlePage.language== null}">
							<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">
								<tr>
									<td class="label">JSP</td>
									<td align="left"><input type="text" id="singlepage_jsp" name="singlePage.jsp" value="${fn:escapeXml(singlePage.jsp)}" class="inputContent" /></td>
								</tr>
							</c:if>
						</c:if>
						<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
						<%-- <c:if test="${multiPage.id== 1111}"> 1097 local ONLY--><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID --%>
						<c:if test="${  singlePage.docRoom == null and singlePage.startTime == null  and multiPage.id  != 1111 }">
							<tr id="titleRow">
								<td class="label" id="titleLabel">
								<c:choose>
									<c:when test="${company.name eq 'neltex' and fn:containsIgnoreCase(singlePage.name, 'Facebook Link')}">
										Link
									</c:when>
								  	<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'news-and-events'}">
								  		Author
								  	</c:when>
								  	<c:otherwise>
								  		Title
								  	</c:otherwise>
								  </c:choose>
								</td>
								<td align="left"><input type="text" id="singlepage_title" name="singlePage.title" value="${fn:escapeXml(singlePage.title)}" class="inputContent" /></td>
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
								<td align="left" <c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">style="display:none;"</c:if>><input type="text" id="singlepage_subtitle" name="singlePage.subTitle" value="${fn:escapeXml(singlePage.subTitle)}" class="inputContent" /></td>
							</tr>
						</c:if>
						<c:if test="${company.name eq 'agian' and param.multipage_id eq '2626'}">
							<tr id="titleRow">
								<td class="label" id="titleLabel">
									Date Posted
								</td>
								<td align="left"><input type="text" id="singlepage_title" name="singlePage.title" value="${fn:escapeXml(singlePage.title)}" class="inputContent" /></td>
							</tr>
							<tr id="subTitleRow">
								<td class="label">
									
								</td>
							<c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">
								<td align="left"><input type="checkbox" id="announce" <c:if test="${singlePage.subTitle eq 'announcement' }">checked</c:if>/> Announcement</td>
							</c:if>
								<td align="left" <c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">style="display:none;"</c:if>><input type="text" id="singlepage_subtitle" name="singlePage.subTitle" value="${fn:escapeXml(singlePage.subTitle)}" class="inputContent" /></td>
							</tr>
						</c:if>
						<c:if test="${singlePage.language== null}">
							<%-- <tr id="validUntilDateRow" <c:if test="${company.name eq 'agian' and param.multipage_id eq '2626' }">style="display:none;"</c:if>> --%>
							<tr id="validUntilDateRow" <c:if test="${company.name eq 'agian' and param.multipage_id ne '2628'}">style="display:none;"</c:if>>
								<c:choose>
									<c:when test="${(company.name eq 'hbc' and singlePage.parent.id eq 892) or 
								(company.name eq 'mraircon' and singlePage.parent.id eq 1732)}">
										<td class="label">Publication Date</td>
									</c:when>
									<c:otherwise>
										<td class="label">Valid Until</td>
									</c:otherwise>
								</c:choose>
								<td align="left"><fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate" /> <input type="text" id="item_date" name="itemDate" value="${idate}" class="inputContent" /> <a href="javascript:;" id="item_date_button"><img src="images/iDate.gif"
										style="border: 0px" alt="Open Calendar" align="absmiddle" /></a> <script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "item_date",     // id of the input field
					        ifFormat       :    "%m-%d-%Y",      // format of the input field
					        button         :    "item_date_button",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true
					    });
					</script></td>
							</tr>
							<c:if test="${(company.name eq 'boysen' and singlePage.parent.id eq 224) 
							or (company.name eq 'stockbridge' and (multiPage.name eq 'Events' or multiPage.name eq 'Blog'))
							or (company.name eq 'altair' and singlePage.parent.id eq 2029) 
							or ((company.name eq 'rizalacademy') and (singlePage.parent.id eq 2045 or singlePage.parent.id eq 2046))
							or (multiPage.hasPublicationDate)}">
								<tr>
									<td class="label">Publication Date</td>
									<td align="left"><fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.datePublished}" var="date" /> <input type="text" id="date_published" name="datePublished" value="${date}" class="inputContent" /> <a href="javascript:;" id="date_published_button"><img src="images/iDate.gif"
											style="border: 0px" alt="Open Calendar" align="absmiddle" /></a> <script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "date_published",     // id of the input field
					        ifFormat       :    "%m-%d-%Y",      // format of the input field
					        button         :    "date_published_button",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true
					    });
					</script></td>
								</tr>
							</c:if>
						</c:if>
						<input type="hidden" id="c1" name="singlePage.isHome" />
						<input type="hidden" id="c2" name="singlePage.isSingleFeatured" />
						<input type="hidden" id="c3" name="singlePage.hidden" />
						<input type="hidden" id="c4" name="singlePage.isForm" />
						<input type="hidden" id="c5" name="singlePage.hasFile" />
						<input type="hidden" id="c6" name="singlePage.loginRequired" />
						<input type="hidden" id="c7" name="singlePage.flag1" />
						<input type="hidden" id="c8" name="singlePage.flag2" />
						<tr>
							<c:choose>
								<c:when test="${company.name eq 'cancun' and singlePage.name eq 'Promo Code Setting'}">
									<td class="label"><strong>Promo Code</strong></td>
									<td align="left"><input type="text" id="singlePage.content" name="singlePage.content" value="${singlePage.content}" style="width: 200px" /></td>
								</c:when>
								<c:otherwise>
									<td class="label" valign="top" id="contentLabel">Content</td>
									<td align="left"><textarea id="singlePage.content" name="singlePage.content">${singlePage.content}</textarea> <script type="text/javascript">
						CKEDITOR.replace( 'singlePage.content');
					</script></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td colspan="2" class="label"><c:choose>
									<c:when test="${singlePage.id > 0}">
										<input type="reset" value="Reset" class="btnBlue">
										<input type="submit" value="Update" class="btnBlue">
									</c:when>
									<c:otherwise>
										<input type="submit" value="Update" class="btnBlue">
									</c:otherwise>
								</c:choose></td>
						</tr>
					</table>
				</form>
				<c:set var="maxMultiPageFile" value="${companySettings.maxAllowedFiles}" />
				<c:set var="uploadedFileCount" value="${fn:length(multiPageFiles)}" />
				<c:set var="allowedUploadedFileCount" value="${maxMultiPageFile - uploadedFileCount}" />
				<br />
				<%@include file="uploadpagefile.jsp"%>
			</div>
			<!--//mainContent-->
			<div class="sidenav">
			
				<div id="uploadImageForm">
				<c:choose>
					<c:when test="${multiPage != null}">
						<form method="post" action="uploadmultipageitemimage.do" enctype="multipart/form-data" onsubmit="return validateImage()">
							<input type="hidden" name="multipage_id" value="${multiPage.id}" />
					</c:when>
					<c:otherwise>
						<form method="post" action="uploadsinglepageimage.do" enctype="multipart/form-data" onsubmit="return validateImage()">
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
				<c:if test="${fn:length(singlePage.images) > 0}">
					<h3 aaa>
						<img src="images/iImage.gif" align="absmiddle" /> Image List
					</h3>
					<ol <c:if test="${company.name eq 'uniorientagents' or company.name eq 'life' or company.name eq 'officeman' 
					or company.name eq 'gurkkatest' or company.name eq 'kuysenfurnitures' or company.name eq 'kuysen'}">id="phoneticlong"</c:if>>
						<c:choose>
							<c:when test="${singlePage.id == 2200}">
								<%-- Only this page has this sort by title so far... --%>
								<c:forEach items="${sortedImagesByTitle}" var="img">
									<li><img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" /> <span><a href="#"><strong>${img.filename}</strong></a> <c:if
												test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
												<a onclick="return confirm('Do you really want to delete this image?');" href="deletesinglepageimage.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&image_id=${img.id}" class="delete">[Delete]</a></span>
										<table>
											<tr>
												<td>Title</td>
											</tr>
											<tr>
												<td><input type="text" id="title${img.id}" name="image_title_${img.id}" value="${img.title}" class="textbox4" onblur="updateImageTitle(${img.id}, this.value);"></td>
											</tr>
											<tr>
												<td>Caption</td>
											</tr>
											<tr>
												<td><input type="text" id="caption${img.id}" name="image_caption_${img.id}" value="${img.caption}" class="textbox4" onblur="updateImageCaption(${img.id}, this.value);"></td>
											</tr>
											<tr>
												<td>Description</td>
											</tr>
											<tr>
												<td><textarea class="textbox4" id="description${img.id}" onblur="updateImageDescription(${img.id}, this.value);">${img.description}</textarea></td>
											</tr>
										</table>
				</c:if>
				<div class="clear"></div>
				</li>
				</c:forEach>
				</c:when>
				<c:otherwise>
					<c:set var="images" value="${singlePage.images}"/>
					<c:if test="${company.name eq 'uniorientagents' or company.name eq 'life' or company.name eq 'officeman' 
					or company.name eq 'gurkkatest' or company.name eq 'kuysenfurnitures' or company.name eq 'kuysen'}">	
						<c:set var="images" value="${singlePage.sortedImages}"/>
					</c:if>
					<c:forEach items="${images}" var="img">
						<li itemID="${img.id}">
						<c:choose>
							<c:when test="${company.name eq 'kuysenfurnitures' and fn:containsIgnoreCase(img.filename, 'png') }">
								<img src="${contextParams['IMAGE_PATH']}/images/pages/${img.original}" style="width:120px"/>
								<c:set var="previewImage" value="${img.original}"/>
							</c:when>
							<c:otherwise>
								<img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" />
								<c:set var="previewImage" value="${img.original}"/>
							</c:otherwise>
						</c:choose> 
							<c:if test="${company.name eq 'uniorientagents' or company.name eq 'life' or company.name eq 'officeman' or company.name eq 'gurkkatest'
							or company.name eq 'kuysenfurnitures' or company.name eq 'kuysen'}">
							<input type="button" value="Edit" onclick="openImageDialog(${img.id}, '${contextParams['IMAGE_PATH']}/images/pages/${previewImage}', '${img.filename}');" class="btnBlue">
							</c:if>
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
									<a onclick="return confirm('Do you really want to delete this image?');" href="deletesinglepageimage.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&image_id=${img.id}" class="btnBlue">Delete</a>
									</c:if>
									
							<div class="clear"></div>
		  <a href="#"><strong>${img.filename}</strong></a> 
		  
		  <c:if test="${company.name eq 'uniorientagents' or company.name eq 'life' or company.name eq 'officeman' 
		  or company.name eq 'gurkkatest' or company.name eq 'kuysenfurnitures' or company.name eq 'kuysen'}">
		  					<table>
				 <c:set var="trimTitle" value="${fn:trim(img.title)}"/>
				 <c:set var="trimCaption" value="${fn:trim(img.caption)}"/>
				 <c:set var="trimDescription" value="${fn:trim(img.description)}"/>
				<tr id="tr_title_${img.id}" <c:if test="${fn:length(trimTitle) eq 0}">style="display:none"</c:if>>
					<td><strong>Title&nbsp;&nbsp;</strong></td>
				
					<td id="title${img.id}_text">
						(${img.title})
					</td>
				</tr>
				
				<tr id="tr_caption_${img.id}" <c:if test="${fn:length(trimCaption) eq 0}">style="display:none"</c:if>>
					<c:choose>
						<c:when test="${singlePage.parent ne null}">
							<c:choose>
								<c:when test="${((singlePage.parent.name eq 'Side Ads' and singlePage.name eq 'Side Advertisement Maximized Images') or singlePage.parent.name eq 'Home Carousel') and company.name eq 'gurkkatest'}">
									<td><strong>Link&nbsp;&nbsp;</strong></td>
								</c:when>
								<c:otherwise>
									<td><strong>Caption&nbsp;&nbsp;</strong></td>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<td><strong>Caption&nbsp;&nbsp;</strong></td>
						</c:otherwise>
					</c:choose>
					
					
					<%-- <td><strong>Caption&nbsp;&nbsp;</strong></td> --%>
															
					<td id="caption${img.id}_text">
						(${img.caption}) 
					</td>
				</tr> 
				<tr id="tr_description_${img.id}" <c:if test="${fn:length(trimDescription) eq 0}">style="display:none"</c:if>>
					<td><strong>Description&nbsp;&nbsp;</strong></td>
				
					<td id="description${img.id}_text">
						(${img.description})
					</td>
				</tr>
			</table>
			</c:if>
			
							<table <c:if test="${company.name eq 'uniorientagents' or company.name eq 'life' or company.name eq 'officeman' 
							or company.name eq 'gurkkatest' or company.name eq 'kuysenfurnitures' or company.name eq 'kuysen'}">style="display:none;"</c:if>>
								<tr>
									<td>Title</td>
								</tr>
								<tr>
									<td><input type="text" id="title${img.id}" name="image_title_${img.id}" value="${img.title}" class="textbox4" onblur="updateImageTitle(${img.id}, this.value);"></td>
								</tr>
								<tr>
									<td>Caption</td>
								</tr>
								<tr>
									<td><input type="text" id="caption${img.id}" name="image_caption_${img.id}" value="${img.caption}" class="textbox4" onblur="updateImageCaption(${img.id}, this.value);"></td>
								</tr>
								<tr>
									<td>Description</td>
								</tr>
								<tr>
									<td><textarea class="textbox4" id="description${img.id}" onblur="updateImageDescription(${img.id}, this.value);">${img.description}</textarea></td>
								</tr>
							</table>
							
							<div class="clear"></div></li>
					</c:forEach>
				</c:otherwise>
				</c:choose>
				</ol>
				<ul>
				
				<c:if test="${company.name eq 'uniorientagents' or company.name eq 'life' or company.name eq 'officeman' 
				or company.name eq 'gurkkatest' or company.name eq 'kuysenfurnitures' or company.name eq 'kuysen'}">
		  	<li>
		  		<input type="button" value="Update Image Order" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" 
		  			<c:choose>
						<c:when test="${multiPage ne null}">
							onclick="window.location='editmultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${singlePage.id}'"
						</c:when>
						<c:otherwise>
							onclick="window.location='editsinglepage.do?singlepage_id=${singlePage.id}'"
						</c:otherwise>
					</c:choose> 
		  		/>
		  	</li>
		  	</c:if>
		</ul>
				<div id="uploadImageInstruction">
				<p class="imageNote">Click on Edit to Modify Title, Caption and Description of the image</p>
				</div>
				</c:if>
				
				<div id="uploadImageReminder">
				<h3>
					<img src="images/iImage.gif" align="absmiddle" /> Upload Image
				</h3>
				<ul class="uploadImageNote">
					<li>You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> files.
					</li>
					<li>500 x 590 is the most advisable size of image that should be uploaded.</li>
				</ul>
				</div>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td colspan="2">
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
					</tr>
				</table>
				<p class="uploadButtons">
					<input type="submit" value="Upload" class="btnBlue">
				</form>
				<%@include file="includes/uploadfilesform.jsp"%>
				
				</div><!-- uploadImageForm -->
				
				<h3>
					<img src="images/iPage.gif" align="absmiddle" /> Page Setup
				</h3>
				<table border="0" cellspacing="0" cellpadding="3">
					<c:if test="${(singlePage.id > 0 && multiPage == null && (user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'))}">
						<tr>
							<td><s:checkbox name="singlePage.isHomeC" value="%{singlePage.isHome}" theme="simple" /></td>
							<td>This page is the default homepage.</td>
						</tr>
					</c:if>
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
						
						<c:choose>
							<c:when test="${company.name eq 'rockwell' && multiPage.name eq 'News and Events' }">
								<tr>
									<td><s:checkbox name="singlePage.isSingleFeaturedC" value="%{singlePage.isSingleFeatured}" theme="simple" /></td>
									<td id="isFeatured">Mark as Popular Post</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td><s:checkbox name="singlePage.isSingleFeaturedC" value="%{singlePage.isSingleFeatured}" theme="simple" /></td>
									<td id="isFeatured">This page is a featured page.</td>
								</tr>
							</c:otherwise>
						</c:choose>
						
					</c:if>
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
						<tr>
							<td><s:checkbox name="singlePage.isFormC" value="%{singlePage.isForm}" theme="simple" /></td>
							<td>This is a form page.</td>
						</tr>
					</c:if>
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
						<tr>
							<td><s:checkbox name="singlePage.hiddenC" value="%{singlePage.hidden}" theme="simple" /></td>
							<td>Hide this page in the website.</td>
						</tr>
					</c:if>
					<c:if test="${user.userType.value== 'Super User' or user.userType.value== 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
						<tr>
							<td><s:checkbox name="singlePage.hasFileC" value="%{singlePage.hasFile}" theme="simple" /></td>
							<td>This page has files.</td>
						</tr>
					</c:if>
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
						<tr>
							<td><s:checkbox name="singlePage.loginRequiredC" value="%{singlePage.loginRequired}" theme="simple" /></td>
							<td>This page requires member login.</td>
						</tr>
					</c:if>
					<c:if test="${company.id eq 319 and multiPage.id eq 1667}"> <%-- gurkka --%>
						<tr>
							<td><s:checkbox name="singlePage.flag1C" value="%{singlePage.flag1}" theme="simple" /></td>
							<td>Show in news box.</td>
						</tr>
						<tr>
							<td><s:checkbox name="singlePage.flag2C" value="%{singlePage.flag2}" theme="simple" /></td>
							<td>Show in news archive/page.</td>
						</tr>
					</c:if>
				</table>
			</div>
			<!--//sidenav-->
			<div class="clear"></div>
		</div>
		<!--//container-->
	</div>
	<script>
		<c:if test="${company.name eq 'neltex'}">
			<c:if test="${singlePage.jsp ne 'news-and-events'}">
				$('#uploadImageForm').css('display','none');
				$('#subTitleRow').css('display','none');
				$('#validUntilDateRow').css('display','none');
				$('[name="uploadFileForm"]').css('display','none');
			</c:if>
			<c:if test="${multiPage.name eq 'News and Events' or multiPage.name eq 'Corporate History' or multiPage.jsp eq 'about-us'}">
				$('#uploadImageInstruction').css('display','none');
				$('#uploadImageReminder').css('display','none');
				$('#uploadImageForm').css('display','block');
				<c:if test="${multiPage.name eq 'News and Events'}">
					$('#subTitleRow').css('display','');
				</c:if>
			</c:if>
		</c:if>
		<c:choose>
		  	<c:when test="${company.name eq 'neltex' and multiPage.jsp eq 'news-and-events'}">
		  		$('#nameLabel').html('Article Name');
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
	  			//document.getElementById('fileList').style.display = 'table-row';
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
	
	<div id="ImageDialog" title="Update Image Information" style="float: left; display: none;">
		<table style="margin:10px 0 0 0;">
			<tr>
				<td>
					<img src="" id="dialogimage" style="width:120px"/>
					<br/>
					<div id="imageNameDiv"></div>
				</td>
			</tr>
			<tr>
				<td>Title</td>
			</tr>
			<tr>
				<td><input type="text" id="title" name="image_title_${img.id}"  class="textbox4" ></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${singlePage.parent ne null}">
						<c:choose>
							<c:when test="${((singlePage.parent.name eq 'Side Ads' and singlePage.name eq 'Side Advertisement Maximized Images') or singlePage.parent.name eq 'Home Carousel') and company.name eq 'gurkkatest'}">
								<td>Link</td>
							</c:when>
							<c:otherwise>
								<td>Caption</td>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<td def>Caption</td>
					</c:otherwise>
				</c:choose>
				
			</tr>
			<tr>											
				<td>
				<c:if test="${company.name eq 'boysen'}">
					<select id="caption" name="image_caption_${img.id}"  class="textbox4" style="width:125px;">
						<option value=""></option>
						<option value="thumbnail">Thumbnail</option>
						<option value="brochure">Brochure</option>
					</select>
				</c:if>
				<c:if test="${company.name ne 'boysen'}">
					<input type="text" id="caption" name="image_caption_${img.id}"  class="textbox4">
				</c:if>
				</td>
			</tr> 
			<tr>
				<td>Description</td>
			</tr>
			<tr>
				<td>
					<textarea class="textbox4"  id="description"></textarea>
				</td>
			</tr>
		</table>
	</div>
	
	<input type="hidden" id="imageId" value=""/>
	
</body>
</html>