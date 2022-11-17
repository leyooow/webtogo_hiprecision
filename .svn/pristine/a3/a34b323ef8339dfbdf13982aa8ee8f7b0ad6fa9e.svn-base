<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="brands" />
 <c:set var="formAction" value="updatebrand.do" />
 
<script language="javascript" src="../javascripts/upload.js"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<c:set var="maxImage" value="1" />
<c:set var="imageCount" value="${fn:length(brand.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />

	<!-- JQuery -->
	<link type="text/css" href="javascripts/jquery/jquery-ui-1.8.19.custom.css" rel="Stylesheet" />	
	<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="javascripts/jquery/jquery-ui-1.8.19.custom.min.js"></script>
	<script>
	
	function submitForm(myForm) {
		
		var name = getElement('brand_name');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Please enter a brand name\n";
			error = true;
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
		DWRImageAction.updateImageTitle("brand", id, title);
	}
	
	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("brand", id, caption);
	}
	
	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("brand", id, description);
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

		DWRImageAction.saveBrandImagesOrder(items, back);
	}

	function back() {
		window.location = 'editbrand.do?group_id=${group.id}&brand_id=${brand.id}';
	}

</script>
 
<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
				<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Category was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'categoryexist'}">
								<li><span class="actionMessage">Category was not created because a similar category already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'maxcategories'}">
								<li><span class="actionMessage">Category was not created because a the maximum number of categories allocated is reached.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Category was sucessfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Category was sucessfully deleted.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'sort'}">
								<li><span class="actionMessage">Display order of the items was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'noItem'}">
								<li><span class="actionMessage">Selected category has no item.</span></li>
							</c:if>							
														
						</ul>
						
					</c:if>

	  <div class="pageTitle">

	    <h1><strong>Edit Brand</strong></h1>
	  
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->
<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
				
		<input type="hidden" name="group_id" value="${group.id}" />
		<input type="hidden" name="brand_id" value="${brand.id}" />
				
		<table width="100%">
			<tr>
				<td>Parent Brand<br />
				<select name="parentBrandId" >
					<option value="0"> -- SELECT A BRAND -- </option>
					<c:forEach items="${allBrands}" var="parentBrand">
						<c:if test="${brand.id ne parentBrand.id}">
							<option value="${parentBrand.id}" ${brand.parentBrand.id eq parentBrand.id?'selected':''} >${parentBrand.name} ${parentBrand.descriptor}</option>
						</c:if>
					</c:forEach>
				</select>
				</td>
			</tr>		
			
			<tr>
				<td>Brand Name<br /><input type="text" id="brand_name" name="brand.name" value="${(newBrand == true) ? '' : brand.name}" class="w200" /></td>
			</tr>
			
			<tr>
				<td>Title<br /><input type="text" id="brand_title" name="brand.title" value="${(newBrand == true) ? '' : brand.title}" class="w200" /></td>
			</tr>
			<tr>
				<td>Tagline<br /><input type="text" id="brand_tagline" name="brand.tagline" value="${(newBrand == true) ? '' : brand.tagline}" class="w200" /></td>
			</tr>
			<tr>
				<td>Url<br /><input type="text" id="brand_url" name="brand.url" value="${(newBrand == true) ? '' : brand.url}" class="w200" /></td>
			</tr>
			<tr>
				<td>Sort Order<br /><input type="text" id="sort_order" name="brand.sortOrder" value="${(newBrand == true) ? '' : brand.sortOrder}" class="w200" /></td>
			</tr>
			<tr>
				<td>Short Description<br /><input type="text" id="brand_short_description" name="brand.shortDescription" value="${(newBrand == true) ? '' : brand.shortDescription}" class="w200" /></td>
			</tr>
			
			<tr>
			
				
				<td>
					<s:checkbox name="brand.featured" value="%{brand.featured}" theme="simple" /> <i><b>This brand is featured</b></i>
					
				</td>
			</tr>
			
			<tr>
				<td>Brand Description<br />
					<textarea name="brand.description" class="w200">${brand.description}</textarea>
					<script type="text/javascript">
						CKEDITOR.replace( 'brand.description',
							{								
								height:100,width: 500								
							}
						);	
					</script>
					
				</td>
			</tr>
			<tr>
				<td>Notes<br />
					<textarea name="brand.notes" class="w200">${brand.notes}</textarea>
					<script type="text/javascript">
						CKEDITOR.replace( 'brand.notes',
							{								
								height:100,width: 500								
							}
						);	
					</script>
					
				</td>
			</tr>
						
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
				<c:choose>
					<c:when test="${brand.id > 0}">
						<input type="submit" name="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue"> 
						<input type="button" value="Cancel" class="btnBlue" onclick="window.location='editbrand.do?group_id=${group.id}&brand_id=${brand.id}';"> 
					</c:when>
					<c:otherwise>
						<input type="submit" name="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
				
				</td>
			</tr> 
			
		</table>
		
		</form>
	 
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' and brand.id > 0}">
				
	<div class="sidenav">
		
	  		<form method="post" action="uploadbrandimage.do" enctype="multipart/form-data">
				
					<input type="hidden" name="group_id" value="${group.id}" />
					<input type="hidden" name="brand_id" value="${brand.id}" />
					<c:if test="${fn:length(brand.images) > 0}">
	   <h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>

	  <ol <c:if test="${company.name eq 'kuysenfurnitures'}">id="phoneticlong"</c:if>>
		<c:set var="images" value="${brand.images}"/>
	    <c:if test="${company.name eq 'kuysenfurnitures'}">	
			<c:set var="images" value="${brand.sortedImages}"/>
		</c:if>
		<c:forEach items="${images}" var="img">
		<li itemID="${img.id}">

		<c:set var="previewImage" value=""/>
		<c:choose>
			<c:when test="${company.name eq 'kuysenfurnitures' and fn:containsIgnoreCase(img.filename, 'png') }">
				<img src="${contextParams['IMAGE_PATH']}/images/brands/${img.original}" style="width:120px"/>
				<c:set var="previewImage" value="${img.original}"/>
			</c:when>
			<c:when test="${fn:containsIgnoreCase(img.filename, 'png') }">
				<img src="${contextParams['IMAGE_PATH']}/images/brands/${img.original}" style="width:120px"/>
				<c:set var="previewImage" value="${img.original}"/>
			</c:when>
			<c:otherwise>
				<img src="${contextParams['IMAGE_PATH']}/images/brands/${img.thumbnail}" />
				<c:set var="previewImage" value="${img.thumbnail}"/>
			</c:otherwise>
		</c:choose>

			<c:if test="${company.name eq 'kuysenfurnitures'}">
			<input type="button" value="Edit" onclick="openImageDialog(${img.id}, '${contextParams['IMAGE_PATH']}/images/brands/${previewImage}', '${img.filename}');" class="btnBlue">
			</c:if>
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
		  		<a onclick="return confirm('Do you really want to delete this image?');" href="deletebrandimage.do?group_id=${group.id}&brand_id=${brand.id}&image_id=${img.id}" class="btnBlue">[Delete]</a>
		  	</c:if>
					
			<div class="clear"></div>
							
		  <a href="#"><strong>${img.filename}</strong></a> 
		  
		  <c:if test="${company.name eq 'kuysenfurnitures'}">
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
			</c:if>
				
			<table <c:if test="${company.name eq 'kuysenfurnitures'}">style="display:none;"</c:if>>
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
		  <div class="clear"></div>

		</li>
		</c:forEach>
	  </ol>
	  
	  	<ul>
			<c:if test="${company.name eq 'kuysenfurnitures'}">
		  	<li>
		  		<input type="button" value="Update Image Order" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='edititem.do?group_id=${group.id}&item_id=${item.id}'" />
		  	</li>
		  	</c:if>
		</ul>
		
	  <p class="imageNote">Click on Edit to Modify Title, Caption and Description of the image</p>
		</c:if>
	 	<h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>

	  <ul class="uploadImageNote">

		<li>You are only allowed to upload ${companySettings.maxAllowedImages} files.</li>

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
			</c:if>
	</div><!--//sidenav-->
	
	
</div>

	<div class="clear"></div>

  </div><!--//container-->
  
  <div id="ImageDialog" title="Update Image Information" style="float: left; display: none;">
		<table style="margin:10px 0 0 0;">
			<tr>
				<td>
					<img src="" id="dialogimage" style="width:120px;"/>
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
					<input type="text" id="caption" name="image_caption_${img.id}"  class="textbox4">
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