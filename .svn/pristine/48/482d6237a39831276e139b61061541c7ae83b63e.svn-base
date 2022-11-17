<%@include file="includes/header.jsp"  %>
<c:set var="menu" value="groups" />
<c:set var="menu" value="groups" />

<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(item.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />
<c:set var="formAction" value="updategroup.do" />

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<script language="javascript" src="../javascripts/upload.js"></script>
<script language="javascript" src="../javascripts/multifile.js"></script>

<!-- JQuery -->
	<link type="text/css" href="javascripts/jquery/jquery-ui-1.8.19.custom.css" rel="Stylesheet" />	
	<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="javascripts/jquery/jquery-ui-1.8.19.custom.min.js"></script>

<script>
<c:if test="${empty group.categoryItemPriceNames}">
	var priceNameCount = 1;
	var newPriceName = 1;
</c:if>
<c:if test="${not empty group.categoryItemPriceNames}">
	var priceNameCount = ${group.priceNameCount};
	var newPriceName = 1;
</c:if>

if(priceNameCount == 0)
	priceNameCounte = 1;
	function submitForm(myForm) {
		var groupName = getElement('group_name');
				
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(groupName.length == 0) {
			error = true;
			messages += " * Group name was not specified. \n";
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

	function togglePriceCount()
	{
		var itemHasPrice = document.getElementById('item_has_price');
		if(itemHasPrice.checked == true)
		{
			
		}
		else
		{
			
		}
	}

	function addRow(tableId)
	{ 
		if(priceNameCount < 10) 
		{
			newPriceName++;
			priceNameCount++;
			var tableBody = document.getElementById(tableId).tBodies[0];
			var newRow = tableBody.insertRow(-1);
			newRow.id = 'row'+newPriceName;
			var newCell0 = newRow.insertCell(0);
			var newCell1 = newRow.insertCell(1);
			newCell0.setAttribute('class',"label");
			newCell0.innerHTML = 'Price Name '+priceNameCount;
			newCell1.innerHTML = '<input type="text" name="pn'+newPriceName+'"  class="w385"/>';
		}	
		else
		{
			alert('You can only add up to 10 price names.');
		}
	}

	<c:if test="${empty group.otherFields}">
		var otherFieldCount = 1;
		var newOtherField = 1;
	</c:if>
	<c:if test="${not empty group.otherFields}">
		var otherFieldCount = ${group.otherFieldCount};
		var newOtherField = 1;
	</c:if>

if(otherFieldCount == 0)
	otherFieldCount = 1;
	function submitForm(myForm) {
		var groupName = getElement('group_name');
				
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(groupName.length == 0) {
			error = true;
			messages += " * Group name was not specified. \n";
		}
		
		if(error) {
			alert(messages);
		}
		else {
			return true;
		}
		
		return false;
	}

	function addOtherFieldRow(tableId)
	{ 
		if(otherFieldCount < "${company.companySettings.maxOtherFields}") 
		{
			newOtherField++;
			otherFieldCount++;
			var tableBody = document.getElementById(tableId).tBodies[0];
			var newRow = tableBody.insertRow(-1);
			newRow.id = 'row'+newOtherField;
			newRow.classList.add("bgOtherField");
			var newCell0 = newRow.insertCell(0);
			var newCell1 = newRow.insertCell(1);
			var newCell2 = newRow.insertCell(2);
			newCell0.innerHTML = 'Other Field '+otherFieldCount;
			newCell0.setAttribute('class',"label");
			newCell1.innerHTML = '<input type="text" name="on'+newOtherField+'" class="w200" />';
			newCell2.innerHTML = '<a href="javascript:void(0);" onclick="addOtherFieldValueRow(this.parentNode, '+newOtherField+')">Add Options</a><br/>';
		}	
		else
		{
			alert('You can only add up to ${company.companySettings.maxOtherFields} other fields.');
			
		}
	}

	function addOtherFieldValueRow(cell, newOtherFieldId, isNotNew)
	{
		var prefix = (isNotNew == true) ? "ov" : "onv";
		cell.innerHTML = cell.innerHTML + '<table cellpadding="4" cellspacing="0"><tr><td><input type="text" name="'+prefix+newOtherFieldId+'"/>'+
		'<a href="javascript:void(0)" style="color: red;" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)">x</a></td></tr></table>';
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
	
	function updateImageTitle(id, title) {
		DWRImageAction.updateImageTitle("group", id, title,{ 
	        async: false,
	        callback: function(s){
	        }});
	}
	
	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("group", id, caption,{ 
	        async: false,
	        callback: function(s){
	        }});
	}
	
	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("group", id, description,{ 
	        async: false,
	        callback: function(s){
	        }});
	}

	
	function updateFileTitle(id, title) {
		DWRImageAction.updateFileTitle("group", id, title);
	}
	
	function updateFileCaption(id, caption) {
		DWRImageAction.updateFileCaption("group", id, caption);
	}
	
	function updateFileDescription(id, description) {
		DWRImageAction.updateFileDescription("group", id, description);
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

		DWRImageAction.saveGroupImagesOrder(items, back);
	}

	function back() {
		window.location = 'editgroup.do?group_id=${group.id}';
	}

</script>

<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
			<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Group was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'groupexist'}">
								<li><span class="actionMessage">Category was not created because a similar category already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Group was successfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Group was successfully deleted.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'sort'}">
								<li><span class="actionMessage">Display order of the categories was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'sortItems'}">
								<li><span class="actionMessage">Display order of category items was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'noCategory'}">
								<li><span class="actionMessage">Selected group has no category.</span></li>
							</c:if>							
							<c:if test="${param['evt'] == 'sortBrands'}">
								<li><span class="actionMessage">Display order of the brands was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'noBrand'}">
								<li><span class="actionMessage">Selected group has no brand.</span></li>
							</c:if>															
						</ul>
					</c:if>	
				
	  	<div class="pageTitle">
		<c:if test="${not empty languages}">
			 <h4>Language:
							<c:if test="${group.language!= null}"> 
							${group.language.language}
							</c:if>
							<c:if test="${group.language== null}"> 
							English
							</c:if>
						</h4>
			  <div class="metaBox">
			  	<c:if test="${group.language== null}"> 
				English, 
				</c:if>
				<c:if test="${group.language != null}"> 
				<a href="editgroup.do?group_id=${param.group_id}">English</a>, 
				</c:if>
			  	<c:forEach items="${languages}" var="lang">
			  		<c:if test="${lang.language ne param.language}">
					, <a href="editgroup.do?group_id=${param.group_id}&language=${lang.language}">${lang.language}</a>
					</c:if>
					<c:if test="${lang.language eq param.language}">
					, ${lang.language}
					</c:if>    
				</c:forEach>
				
			  </div>
		</c:if>

	    <h1><strong>Edit Group</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	 
			
				  <div class="clear"></div>
 	<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		<input type="hidden" name="language" value="${param.language }"/>
		<c:if test="${group.id > 0}">
			<input type="hidden" name="group_id" value="${group.id}">
		</c:if>
		
		<table id="table1" width="100%" border="0" cellspacing="0" cellpadding="3">
					
			
			<tr>
				<td class="label">Group Name</td>
			
				<td><input type="text" id="group_name" name="group.name" value="${group.name}" class="w385" /></td>
			</tr>
			
			<tr>
				<td  class="label">Group Description</td>
				
				<td><textarea name="group.description" class="w385">${group.description}</textarea></td>
			</tr>		
			<c:if test="${group.language== null}"> 
				<tr>
					<td  class="label">Max Number of Categories</td> 
					
					<td><input type="text" name="group.maxCategories" value="${group.maxCategories}" class="w385" /></td>
				</tr>
			 
				<tr>
					<td  class="label">Max Number of Items</td>
					
					<td><input type="text" name="group.maxItems" value="${group.maxItems}" class="w385" /></td>
				</tr>
				<tr>
					<td class="label">Sort By</td>
					<td>
						 <input type="radio" name="group.sortType" value="manualSort" <c:if test="${empty group.sortType || group.sortType == 'manualSort'}">checked</c:if>/> Manual Sort<br/>
						 <input type="radio" name="group.sortType" value="alphabeticalSort" <c:if test="${ group.sortType == 'alphabeticalSort'}">checked</c:if>/> Alphabetical Sort<br/>
						 <input type="radio" name="group.sortType" value="dateCreatedSort" <c:if test="${group.sortType == 'dateCreatedSort'}">checked</c:if>/> Date Created Sort<br/>
					</td>					
				 	<c:if test="${group.id > 0}">
						<input type="hidden" name="group_id" value="${group.id}">
					</c:if>			
				</tr>
				<tr>
					<td  class="label">Group Attributes</td>
					 
					<td>
					<s:checkbox name="group.featured" value="%{group.featured}" theme="simple" /> <i><b>Has featured items</b></i>
					<br/>
					<s:checkbox name="group.hasBrands" value="%{group.hasBrands}" theme="simple" /> <i><b>Has brands</b></i>				
					<br/>
					<s:checkbox name="group.hasAttributes" value="%{group.hasAttributes}" theme="simple" /> <i><b>Has attributes</b></i>
					<br/>
					
					<s:checkbox name="group.isOutOfStock" value="%{group.isOutOfStock}" theme="simple" /> <i><b>Has out of stock items</b></i>
					<br/>
					<c:if test="${company.name == 'hiprecision'}">
						<s:checkbox name="group.hasScheduleForm" value="%{group.hasScheduleForm}" theme="simple" /> <i><b>Items of this Group has Schedule.</b></i>	
					
					</c:if>
					</td>
				</tr>
			
				<c:if test="${company.companySettings.hasMemberFeature == true}">
				<%-- <c:if test="${group.loginRequired == false || user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}"> --%>
					<tr>
						
						<td class="label"></td>
						<td>
						
						<s:checkbox name="group.loginRequired" value="%{group.loginRequired}" theme="simple" /> <i><b>This page requires member login.</i>
						
						</td>
						
					</tr>
				<%-- </c:if> --%>
				</c:if> 
				<tr>
					
					<td  class="label"></td> 
					<td> 
						<input type="checkbox" id="item_has_price" name="itemHasPrice" value="true" ${(group.itemHasPrice ne false)? 'CHECKED':'' } onchange="togglePriceCount();"/> <i><b>Items has price</b></i>
					</td>
				</tr>
				<c:if test="${group.itemHasPrice ne false}"> 
				<tr id="itemName1">
					<td  class="label">Price Name 1 <font color="red">(Default)</font> </td> 
					
					<td><input type="text" name="${(not empty group.categoryItemPriceNames)? 'n' : 'pn1' }${group.categoryItemPriceNames[0].id}" value="${(not empty group.categoryItemPriceNames)? group.categoryItemPriceNames[0].name : 'Price Name 1' }" class="w385"/>&nbsp;&nbsp; <a href="javascript:void(0);" onclick="addRow('table1')">Add another</a></td>
				</tr>	
				
					<c:forEach items="${group.categoryItemPriceNames }" var="itemName" varStatus="counter">
						<c:if test="${counter.count ne 1 }">
							<tr id="itemName${counter.count}">
								<td  class="label">Price Name ${counter.count } </td> 
								<td><input type="text" name="n${itemName.id }" value="${itemName.name }" class="w385"/></td> 
							</tr>	
						</c:if>
					</c:forEach> 
				</c:if>	
			</c:if>
			
		</table>
		<c:if test="${group.language== null}"> 
		<table id="table2" width="100%" cellspacing="0" cellpadding="3" border="0">			
			<tr><td>&nbsp;</td></tr>
			<c:if test="${company.companySettings.hasOtherFields == true}">
				<tr id="otherField1" class="bgOtherField">
					<td valign="top" class="label" width="160px">Other Field 1</td> 						
					<td valign="top" nowrap>
						<input class="w200" type="text" name="${(not empty group.otherFields)? 'o' : 'on1' }${group.otherFields[0].id}" value="${(not empty group.otherFields)? group.otherFields[0].name : 'Other Field 1' }"/>&nbsp;&nbsp; 
						<a href="javascript:void(0);" onclick="addOtherFieldRow('table2')">Add another</a>
					</td>
					<td valign="top">							
						<a href="javascript:void(0);" onclick="addOtherFieldValueRow(this.parentNode, ${group.otherFields[0].id}, true)">Add Options</a>
						
						<table cellpadding="4" cellspacing="0">
						<c:forEach items="${group.otherFields[0].otherFieldValueList }" var="value">
							<tr>
								<td><input type="text" name="ov${group.otherFields[0].id }" value="${value.value}"/>
									<a href="javascript:void(0)" style="color: red;" 
									onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)">x</a>
								</td>
							</tr>
						</c:forEach>
						</table>
						
					</td>
				</tr>	
			
				<c:forEach items="${group.otherFields }" var="otherField" varStatus="counter">
					<c:if test="${counter.count ne 1 }">
						<tr id="otherField${counter.count}" class="bgOtherField">
							<td valign="top" class="label">Other Field ${counter.count } </td> 								
							<td valign="top"><input class="w200" type="text" name="o${otherField.id }" value="${otherField.name }"/></td>
							<td>
								<a href="javascript:void(0);" onclick="addOtherFieldValueRow(this.parentNode, ${otherField.id}, true)">Add Options</a>
								
								<table cellpadding="4" cellspacing="0">
								<c:forEach items="${otherField.otherFieldValueList }" var="value">
									<tr>
										<td><input type="text" name="ov${otherField.id }" value="${value.value}"/>
											<a href="javascript:void(0)" style="color: red;" 
											onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)">x</a>
										</td>
									</tr>
								</c:forEach>
								</table>
								
							</td>  
						</tr>	
					</c:if>
				</c:forEach> 
			</c:if>
		</table>
		</c:if>	
				
		<table id="table2" width="100%" cellspacing="0" cellpadding="3" border="0">
			<tr width="100%" border="0" cellspacing="0" cellpadding="3">
				<td colspan="2">
				 
				<c:choose>
					<c:when test="${group.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='groups.do'" class="btnBlue">
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
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
		<form method="post" action="uploadgroupimage.do" enctype="multipart/form-data">
					
					<%--
					<input type="hidden" name="parent" value="${category.parentCategory.id}" />
					<input type="hidden" name="category_id" value="${category.id}" />
					--%>
					
					<input type="hidden" name="group_id" value="${group.id}" />
				
					
					<c:if test="${fn:length(group.images) > 0}">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>

	  <ol id="phoneticlong">

	    
		<c:forEach items="${group.images}" var="img">
		<li itemID="${img.id}">

		  <img src="${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}" />
		<input type="button" value="Edit" onclick="openImageDialog(${img.id}, '${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}', '${img.filename}');" class="btnBlue">
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
			<a onclick="return confirm('Do you really want to delete this image?');" href="deletegroupimage.do?group_id=${group.id}&item_id=${item.id}&image_id=${img.id}" class="btnBlue">Delete</a>
<%-- 		  <a onclick="return confirm('Do you really want to delete this image?');" href="deletegroupimage.do?group_id=${group.id}&image_id=${img.id}" class="delete">[Delete]</a></span> --%>
				
				<div class="clear"></div>
		  <a href="#"><strong>${img.filename}</strong></a> 
			
		  	<div class="clear"></div>
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
					<td><strong>Caption&nbsp;&nbsp;</strong></td>
															
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
			<table style="display:none">
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
											<td>
												<textarea class="textbox4" id="description${img.id}" onblur="updateImageDescription(${img.id}, this.value);">${img.description}</textarea>
											</td>
										</tr>
									</table>
									</c:if>	
		  <div class="clear"></div>

		</li>
		</c:forEach>
	  </ol>
	  <ul>
		  	<li>
		  		<input type="button" value="Update Image Order" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='edititem.do?group_id=${group.id}&item_id=${item.id}'" />
		  	</li>
		</ul>
		
	  <p class="imageNote">Click on Edit to Modify Title, Caption and Description of the image</p>
		</c:if>
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

	  
	</form>
	
	</div><!--//sidenav-->
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container-->
  
  <div id="ImageDialog" title="Update Image Information" style="float: left; display: none;">
		<table style="margin:10px 0 0 0;">
			<tr>
				<td>
					<img src="" id="dialogimage"/>
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
				<td>Caption</td>
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