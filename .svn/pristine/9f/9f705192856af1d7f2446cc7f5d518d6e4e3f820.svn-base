<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />

<c:set var="menu" value="groups" />

<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(item.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />


<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<script language="javascript" src="../javascripts/upload.js"></script>
<script language="javascript" src="../javascripts/multifile.js"></script>

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
			var newCell2 = newRow.insertCell(2);
			newCell0.innerHTML = 'Price Name '+priceNameCount;
			newCell2.innerHTML = '<input type="text" name="pn'+newPriceName+'"/>';
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
			var newCell0 = newRow.insertCell(0);
			var newCell1 = newRow.insertCell(1);
			var newCell2 = newRow.insertCell(2);
			newCell0.innerHTML = 'Other Field '+otherFieldCount;
			newCell2.innerHTML = '<input type="text" name="on'+newOtherField+'"/>';
		}	
		else
		{
			alert('You can only add up to ${company.companySettings.maxOtherFields} other fields.');
		}
	}
</script> 

<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;" valign="top">
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		
		<c:if test="${group.id > 0}">
			<input type="hidden" name="group_id" value="${group.id}">
		</c:if>
		
		<table id="table1" width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<c:if test="${group.id == null}">
				<tr class="headbox">
					<th colspan="3">
						New Group
					</th>
				</tr>
			</c:if>
			
			<tr>
				<td width="1%" nowrap>Group Name</td>
				<td width="10px"></td>
				<td><input type="text" id="group_name" name="group.name" value="${group.name}" class="textbox3" /></td>
			</tr>
			
			<tr>
				<td nowrap>Group Description</td>
				<td></td>
				<td><textarea name="group.description" class="textbox3">${group.description}</textarea></td>
			</tr>		
			
			<tr>
				<td nowrap>Max Number of Categories</td> 
				<td></td>
				<td><input type="text" name="group.maxCategories" value="${group.maxCategories}" class="textbox2" /></td>
			</tr>
			 
			<tr>
				<td nowrap>Max Number of Items</td>
				<td></td>
				<td><input type="text" name="group.maxItems" value="${group.maxItems}" class="textbox2" /></td>
			</tr>
			
			<tr>
				<td nowrap>Group Attributes</td>
				<td></td> 
				<td>
				<s:checkbox name="group.featured" value="%{group.featured}" theme="simple" /> <i><b>Has featured items</b></i>
				<br/><br/>
				
				<s:checkbox name="group.hasBrands" value="%{group.hasBrands}" theme="simple" /> <i><b>Has brands</b></i>				
				<br/><br/>
				
				<s:checkbox name="group.hasAttributes" value="%{group.hasAttributes}" theme="simple" /> <i><b>Has attributes</b></i>
				<br/><br/>
			
				<%-- s:checkbox name="group.has2Prices" value="%{group.has2Prices}" theme="simple" /> <i><b>Has 2 Item Prices</b></i>
				<br/><br/ --%>	
				<s:checkbox name="group.isOutOfStock" value="%{group.isOutOfStock}" theme="simple" /> <i><b>Has out of stock items</b></i>
				<br/><br/>
				
				</td>
			</tr>
			
			<c:if test="${company.companySettings.hasMemberFeature == true}">
			<%-- <c:if test="${group.loginRequired == false || user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}"> --%>
				<tr>
					<td nowrap>&nbsp;</td>
					<td></td>
					<td>
					
					<s:checkbox name="group.loginRequired" value="%{group.loginRequired}" theme="simple" /> <i><b>This page requires member login.</i>
					
					</td>
					
				</tr>
			<%-- </c:if> --%>
			</c:if> 
			<tr>
				<td>
					
				</td>
				<td></td> 
				<td> 
					<input type="checkbox" id="item_has_price" name="itemHasPrice" value="true" ${(group.itemHasPrice ne false)? 'CHECKED':'' } onchange="togglePriceCount();"/> <i><b>Items has price</b></i>
				</td>
			</tr>
			<c:if test="${group.itemHasPrice ne false}"> 
			<tr id="itemName1">
				<td>Price Name 1 <font color="red">(The default price name)</font> </td> 
				<td></td>
				<td><input type="text" name="${(not empty group.categoryItemPriceNames)? 'n' : 'pn1' }${group.categoryItemPriceNames[0].id}" value="${(not empty group.categoryItemPriceNames)? group.categoryItemPriceNames[0].name : 'Price Name 1' }"/>&nbsp;&nbsp; <a href="javascript:void(0);" onclick="addRow('table1')">Add another</a></td>
			</tr>	
			
				<c:forEach items="${group.categoryItemPriceNames }" var="itemName" varStatus="counter">
					<c:if test="${counter.count ne 1 }">
						<tr id="itemName${counter.count}">
							<td>Price Name ${counter.count } </td> 
							<td></td>
							<td><input type="text" name="n${itemName.id }" value="${itemName.name }"/></td> 
						</tr>	
					</c:if>
				</c:forEach> 
			</c:if>	
			
			</table>
			<table id="table2" width="50%">
				<c:if test="${company.companySettings.hasOtherFields == true}">
					<tr id="otherField1">
						<td colspan="2">Other Field 1</td> 						
						<td><input type="text" name="${(not empty group.otherFields)? 'o' : 'on1' }${group.otherFields[0].id}" value="${(not empty group.otherFields)? group.otherFields[0].name : 'Other Field 1' }"/>&nbsp;&nbsp; <a href="javascript:void(0);" onclick="addOtherFieldRow('table2')">Add another</a></td>
					</tr>	
				
					<c:forEach items="${group.otherFields }" var="otherField" varStatus="counter">
						<c:if test="${counter.count ne 1 }">
							<tr id="otherField${counter.count}">
								<td colspan="2">Other Field ${counter.count } </td> 								
								<td><input type="text" name="o${otherField.id }" value="${otherField.name }"/></td> 
							</tr>	
						</c:if>
					</c:forEach> 
				</c:if>
			</table>
			
			<table>
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				 
				<c:choose>
					<c:when test="${group.id >0}">
						<input type="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
						<input type="button" value="Cancel" onclick="window.location='groups.do'" class="upload_button2">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="upload_button2">
					</c:otherwise>
				</c:choose>
								
				</td>
			</tr>
					
		</table>
		
		</form>
		
		</td>
		
		<c:choose>
			<c:when test="${group.id > 0}">
			<td width="40%" bgcolor="#efefef" valign="top"> 

				<div id="uploadimageform"> 
				
				<form method="post" action="uploadgroupimage.do" enctype="multipart/form-data">
					
					<%--
					<input type="hidden" name="parent" value="${category.parentCategory.id}" />
					<input type="hidden" name="category_id" value="${category.id}" />
					--%>
					
					<input type="hidden" name="group_id" value="${group.id}" />
				
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(group.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>File List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
							
							<table>
								<c:forEach items="${group.images}" var="img">
								<tr>
									<td style="border:0px;" colspan="2"><b>${img.filename}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%" valign="top"> 
									<img src="${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}" alt="pic"/>
									<a onclick="return confirm('Do you really want to delete this image?');" href="deletegroupimage.do?group_id=${group.id}&image_id=${img.id}">Delete</a>
									
									</td>
									<td style="border:0px;" valign="top">
									
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
									
									</td>
								</tr>
								<tr bgcolor="#ffffff">
									<td style="border:0px;" colspan="2" height="5px"></td>
								</tr>
								</c:forEach>
							</table>
							
							</td>
						</tr>
						</c:if>
						
						<c:if test="${allowedImageCount > 0}">  
						<tr>
							<td style="border:0px;">
							<h2>Upload File</h2>
							
							<br>
							
							<font color="#ff0000">
							* You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> files.
							<br>
							* <b>${companySettings.image1Width} x ${companySettings.image1Heigth}</b> is the most advisable size of image that should be uploaded.
							</font>
							
							
							</td>
						</tr>
						<tr> 
							<td style="border:0px;">
							
								<div id="attachment" class="attachment" style="display:none">
									<div id="dropcap" class="dropcap">1</div>
								    
								    <input id="file" name="upload" type="file" size="30" />
								    <br/> 
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
								    <br/><a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload'); ">Attach a file</a><br/><br/>
								    <span id="attachmentmarker"></span>
								</div>
							
							</td>
						</tr>
						
						
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
						<tr>
							<td style="border:0px;">
							
								<div align="center">
								<input type="submit" value="Upload Now!" class="upload_button2" />
								</div>
							 
							</td>
						</tr>
						</c:if>  
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
					</table>
				
				</form>
				
				<div>	
			</td>
			</c:when>
			<c:otherwise>
				<td width="40%" bgcolor="#ffffff" valign="top"></td>
			</c:otherwise>
		</c:choose>



		
		<td width="40%">
		</td>
		
	</tr>
</table>
</div>