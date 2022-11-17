<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />
<!-- calendar stylesheet -->

<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
  
<c:set var="menu" value="items" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<script language="javascript" src="../javascripts/upload.js"></script>
<script language="javascript" src="../javascripts/upload2.js"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRItemAttributeAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>


<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(item.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />

<script>
	var isValid = true;
	
	function submitForm(myForm) {
		
		var name = getElement('item_name');
		
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			error = true;
			messages += " * Name was not specified. \n";
		}

		if(!isValid) {
			error = true;
			messages +=" * Attribute value/s not valid. \n";
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
		DWRImageAction.updateImageTitle("item", id, title);
	}
	
	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("item", id, caption);
	}
	
	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("item", id, description);
	}

	
	function updateFileTitle(id, title) {
		DWRImageAction.updateFileTitle("item", id, title);
	}
	
	function updateFileCaption(id, caption) {
		DWRImageAction.updateFileCaption("item", id, caption);
	}
	
	function updateFileDescription(id, description) {
		DWRImageAction.updateFileDescription("item", id, description);
	}	

	function validate(value, type, name){
		DWRItemAttributeAction.getMessage(value, type, name, {
			callback:function(notificationMessage){
			
				if(notificationMessage != null){
					alert(notificationMessage);	
					isValid = false;
				}
				else isValid = true;		
			}
		});
		
	}

</script> 

<div style="width:100%;">
<table>
	<tr valign="top">
		<td style="border:0px;" valign="top">
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<input type="hidden" name="group_id" value="${group.id}" />
		<c:if test="${item.id >0}">
			<input type="hidden" name="item_id" value="${item.id}" />
		</c:if>
		
		<table width="50%" >
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<tr class="headbox">
				<th colspan="3">
					<c:choose>
						<c:when test="${item.id >0}">
							Edit Item 
						</c:when>
						<c:otherwise>
							New Item
						</c:otherwise>
					</c:choose>
				</th>
			</tr>
			
			<tr>
				<td width="1%" nowrap>Parent Category</td>
				<td width="10px"></td>
				<td>
				
				<select name="parent" style="width: 500px;">
					<c:forEach items="${group.categories}" var="cat">
						<option value="${cat.id}" <c:if test="${cat.id == item.parent.id}">selected</c:if>>${cat.name}  ${cat.descriptor}</option>
					</c:forEach>
				</select>
				
				</td>
			</tr> 
			
			<c:if test="${group.hasBrands && fn:length(group.brands)>0}">
				<tr>
					<td width="1%" nowrap>Brand</td>
					<td width="10px"></td>
					<td>
					 
					<select name="brand_id" style="width: 255px;">
						<option value=""></option>
						<c:forEach items="${group.brands}" var="b">
							<option value="${b.id}" <c:if test="${b.id == item.brand.id}">selected</c:if>>${b.name}</option>
						</c:forEach>
					</select>
					
					</td>
				</tr>
			</c:if>
			
			<tr>
				<td width="1%" nowrap>Item Name</td>
				<td width="10px"></td>
				<td><input type="text" id="item_name" name="item.name" value="${item.name}" class="textbox4" /></td>
			</tr>
			
			<tr>
				<td width="1%" nowrap>SKU</td> 
				<td width="10px"></td>
				<td><input type="text" id="item_sku" name="item.sku" value="${item.sku}" class="textbox2" /></td>
			</tr>
			<c:if test="${group.itemHasPrice ne false }"> 
				<tr><td>&nbsp;</td><td></td><td></td></tr>
				<tr>
					<td width="1%" nowrap>${(not empty group.categoryItemPriceNames)? group.categoryItemPriceNames[0].name : 'Price' }</td>
					<td width="10px"></td>
					<td><input type="text" id="item_price" name="item.price" value="${item.price}" class="textbox2" /></td>
				</tr>
				<c:forEach items="${group.categoryItemPriceNames }" var="priceName" varStatus="counter">
					<c:if test="${counter.count ne 1 }">
						<tr>
							<td width="1%" nowrap>${priceName.name }</td>
							<td width="10px"></td>
							<td><input type="text" id="item_price" name="p${priceName.id }" value="${prices[priceName.id] }" class="textbox2" /></td>
						</tr>
					</c:if>
				</c:forEach>
				<tr><td>&nbsp;</td><td></td><td></td></tr>
			</c:if>
			
			<c:if test="${company.companySettings.hasOtherFields ne false }"> 							
				<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
					<c:choose>
						<c:when test="${company.name eq 'hpds'}">
							<tr>
								<td width="1%" nowrap>${otherField.name }</td>
								<td width="10px"></td>
								<td>
									<c:choose>
										<c:when test="${otherField.name eq 'PATIENT PREPARATION' or otherField.name eq 'TEST PACKAGE' or otherField.name eq 'SPECIAL INSTRUCTIONS'}">
											<textarea id="other_field" name="o${otherField.id }" rows="2" cols="60">${otherFields[otherField.id]}</textarea>
										</c:when>										
										<c:otherwise>
											<input type="text" id="other_field" name="o${otherField.id }" value="${otherFields[otherField.id] }" style="width: 350px;" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td width="1%" nowrap>${otherField.name }</td>
								<td width="10px"></td>
								<td><input type="text" id="other_field" name="o${otherField.id }" value="${otherFields[otherField.id] }" style="width: 350px;" /></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<tr><td colspan="3">&nbsp;</td></tr>
			</c:if>
			
				<%-- c:if test="${group.has2Prices}">
			<tr>
				<td width="1%" nowrap>Price 2</td>
				<td width="10px"></td>
				<td><input type="text" id="item_price2" name="item.price2" value="${item.price2}" class="textbox4" /></td>
			</tr>
				</c:if --%>
			<tr>
			<td width="1%" nowrap>Shipping Charge</td>
				<td width="10px"></td>
				<td><input type="text" id="item_shipping_price" name="item.shippingPrice" value="${item.shippingPrice}" class="textbox2" /></td>
			</tr>
			
			<tr>
			<td width="1%" nowrap>Search Tags</td>
			<td width="10px"></td>
			<td><input type="text" id="search_tags" name="search_tags" value="${item.searchTags}" class="textbox4"/></td>
			</tr>
			
			<tr>
				<td width="1%" nowrap>Date</td>
				<td width="10px"></td>
				<td>
				
				<fmt:formatDate pattern="MM-dd-yyyy" value="${item.itemDate}" var="idate"/>
								 
				<input type="text" id="item_date" name="itemDate" value="${idate}" class="textbox2" /> 
				<a href="javascript:;" id="item_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				   
				  
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
<!--			onchange="validate(${grp_att.name}, ${grp_att.datatype})"-->
			<c:if test="${group.hasAttributes && fn:length(group.attributes)>0}">
				<tr bgcolor="#f5f5f5">
				<td colspan="3">
				<table>
				<tr>
				<td align="center"><h4>Attributes</h4></td>
				
				</tr>
				<c:choose>
					<c:when test="${empty item.attributes}">
						<c:forEach items="${group.attributes}" var="att" varStatus="i">
							<tr >
								<td width="1%" nowrap>${att.name}</td>
								<td width="9%"></td>
								<td>
								<c:if test="${not empty att.presetValues}">
										<select name="${att.name}" style="width: 145px;">
											<option value=""></option>
											<c:forEach items="${att.presetValues}" var="presetval">
												<option value="${presetval.value}">${presetval.value}</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${empty att.presetValues}">
										<input type="text" id="1${ i.count }" name="${att.name}" value="${item.attributes[i.count-1].value }" onchange="validate(this.value, '${ att.dataType }', '${att.name}');"/>
									</c:if>

								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:when test="${item.id>0}">
						<c:forEach items="${item.attributes}" var="att" varStatus="i">
							<c:if test="${ not att.disabled }">
								<tr>
									<td width="1%" nowrap>${att.attribute.name}</td>
								<td width="9%"></td>
								<td>
									<c:if test="${not empty att.attribute.presetValues}">
										<option value=""></option>
										<select name="${att.attribute.name}" style="width: 145px;">
											<c:forEach items="${att.attribute.presetValues}" var="presetval">
												<option value="${presetval.value}" <c:if test="${presetval.value == att.value}">selected</c:if>>${presetval.value}</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${empty att.attribute.presetValues}">
										<input type="text" id="2${ i.count }"name="${att.attribute.name}" value="${att.value }" onchange="validate( this.value , '${ att.attribute.dataType }', '${att.attribute.name}');"/>
									</c:if>
									<input type="hidden" name="${att.id }" value="${att.id }"/>
								</td>
								</tr>
							</c:if>
						</c:forEach>
						
						<c:set var="l" value="${0}"/>
						<c:if test="${fn:length(group.attributes) > fn:length(item.attributes)}">
							<c:forEach items="${group.attributes}" var="grp_att" varStatus="j">
								<c:forEach items="${item.attributes}" var="item_att" varStatus="k">
									<c:if test="${grp_att.name eq item_att.attribute.name}">
									<c:set var="l" value="${1}"/>
									</c:if>
								</c:forEach>
							
								<c:if test="${l ne 1 }">
								<tr>
								<td width="1%" nowrap>${grp_att.name}</td>
								<td width="9%"></td>
								<td>
									<c:if test="${not empty grp_att.presetValues}">
										<option value=""></option>
										<select name="${grp_att.name}" style="width: 145px;">
											<c:forEach items="${grp_att.presetValues}" var="presetval">
												<option value="${presetval.value}">${presetval.value}</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${empty grp_att.presetValues}">
										<input type="text" id="3${ i.count }" name="${grp_att.name}" onchange="validate(this.value, '${ grp_att.dataType }', '${grp_att.name}');"/>
									</c:if>
								</td>
								</td>
								</tr>
								</c:if>
								<c:set var="l" value="${0}"/>
							</c:forEach>
						</c:if>
							
					</c:when>
					<c:otherwise>
						<c:forEach items="${group.attributes}" var="att" varStatus="i">
							<tr>
								<td width="1%" nowrap>${att.name}</td>
								<td width="9%"></td>
								<td>
								<c:if test="${not empty att.presetValues}">
									<select name="${att.name}" style="width: 145px;">
										<option value=""></option>
										<c:forEach items="${att.presetValues}" var="presetval">
											<option value="${presetval.value}">${presetval.value}</option>
										</c:forEach>
									</select>
								</c:if>
								<c:if test="${empty att.presetValues}">
									<input type="text" id="4${ i.count }" name="${att.name}" value="${item.attributes[i.count-1].value }" onchange="validate(this.value, '${ att.dataType }', '${att.name}');"/>
								</c:if>

								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				<c:if test="${ item ne null }">
				<tr><td></td><td></td><td></td><td><a href="manageAttributes.do?group_id=${ group.id }&item_id=${ item.id }">Manage Attributes</a></td></tr>
				</c:if>
				</table>
				</td></tr>
			</c:if>
			
			<tr>
				<td nowrap>&nbsp;</td>
				<td></td>
				<td>
				
				<s:checkbox name="item.disabled" value="%{item.disabled}" theme="simple" /> <i><b>This item is disabled.</b></i>	
				
				</td>
				
			</tr>
			
			<tr>
				<td nowrap>&nbsp;</td>
				<td></td>
				<td>
				
				
				<c:choose>
					<c:when test="${company.name eq 'gurkkatest' and group.name eq GurkkaConstants.COCKTAILS_GROUP_NAME}">
						<s:checkbox name="item.featured" value="%{item.featured}" theme="simple" /> <i><b>This item is NEW SUBMITTED.</b></i>
					</c:when>
					<c:otherwise>
						<s:checkbox name="item.featured" value="%{item.featured}" theme="simple" /> <i><b>This item will be featured in the website.</b></i>
					</c:otherwise>
				</c:choose>
				
				
				</td>
				
			</tr>
			<c:if test="${group.isOutOfStock}">
			<tr>
				<td nowrap>&nbsp;</td>
				<td></td>
				<td>
				
				<s:checkbox name="item.isOutOfStock" value="%{item.isOutOfStock}" theme="simple" /> <i><b>This item is out of stock.</b></i>	
				
				</td>
				
			</tr>
			</c:if>
			<tr>
				<td valign="top" width="1%" nowrap>Short Description</td>
				<td></td>
				<td>	
						
							<FCK:editor basePath="/FCKeditor" instanceName="item.shortDescription" width="98%" height="200" toolbarSet="Custom_SuperUser" >
								 <jsp:attribute name="value">
								 	${item.shortDescription}
								 </jsp:attribute>
							</FCK:editor>
						
											
							<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>
					
				</td>
			</tr>			
			
			<tr>
				<td valign="top" width="1%" nowrap>Full Description</td>
				<td></td>
				<td>				
						<FCK:editor basePath="/FCKeditor" instanceName="item.description" width="98%" height="200" toolbarSet="Custom_SuperUser" value="${item.description }"></FCK:editor>
															
						<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>
					
				</td>
			</tr> 
			<tr>
				<td valign="top" width="1%" nowrap>Other Details</td>
				<td></td>
				<td>	
										
							<FCK:editor basePath="/FCKeditor" instanceName="item.otherDetails" width="98%" height="200" toolbarSet="Custom_SuperUser" value="${item.otherDetails}"></FCK:editor>
										
												
								
							<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>
					
				</td>
			</tr>
			
			
			
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				 
				<c:choose>
					<c:when test="${item.id >0}">
						<input type="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
						<input type=button value="Cancel" class="upload_button2" onclick="window.location='items.do?group_id=${group.id}'">
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
			<c:when test="${item.id >0}">
			<td width="40%" bgcolor="#efefef" valign="top"> 
			
				<div id="uploadimageform"> 
				
				<form method="post" action="uploaditemimage.do" enctype="multipart/form-data">
				
					<input type="hidden" name="parent" value="${item.parent.id}" />
					<input type="hidden" name="item_id" value="${item.id}" />
					<input type="hidden" name="group_id" value="${group.id}" />
				
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(item.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>Image List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
							
							<table>
								<c:forEach items="${item.images}" var="img">
								<tr>
									<td style="border:0px;" colspan="2"><b>${img.filename}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%" valign="top"> 
																				 
									<img src="${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}" />
									<a onclick="return confirm('Do you really want to delete this image?');" href="deleteitemimage.do?group_id=${group.id}&item_id=${item.id}&image_id=${img.id}">Delete</a>
									
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
							
							<h2>Upload Image</h2>
							
							<br>
							
							<font color="#ff0000">
							* You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> images.
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
								    <br/><a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload'); ">Attach in image</a><br/><br/>
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
								<input type="submit" value="Upload Image Now!" class="upload_button2" />
								</div>
							 
							</td>
						</tr>
						</c:if>
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
					</form>
					
					<tr>
					<td width="40%" bgcolor="efefef" valign="top"> 
			
				<div id="uploadimageform"> 
				
				<form method="post" action="uploaditemfile.do" enctype="multipart/form-data">
				
					<input type="hidden" name="parent" value="${item.parent.id}" />
					<input type="hidden" name="item_id" value="${item.id}" />
					<input type="hidden" name="group_id" value="${group.id}" />
				
					<table width="100%" cellpadding="0" cellspacing="2" border="0"  style="background-color: #ffffcc;">
						
						<c:if test="${fn:length(item.files) > 0}">
						<tr>
							<td style="border:0px;"><h2>File List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
							
							<table>
								<c:forEach items="${item.files}" var="fil">
								<tr>
									<td style="border:0px;" colspan="2"><b>${fil.fileName}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<!-- <td style="border:0px;" width="1%" valign="top"> --> 
									
									<!--</td>-->
									<td style="border:0px;" valign="top">
									
									<table>
										<tr>
											<td>Title</td>
										</tr>
										<tr>
											<td><input type="text" name="file_title_${fil.id}" value="${fil.title}" class="textbox4" onblur="updateFileTitle(${fil.id}, this.value);"></td>
										</tr>
										<tr>
											<td>Caption</td>
										</tr>
										<tr>											
											<td><input type="text" name="file_caption_${fil.id}" value="${fil.caption}" class="textbox4" onblur="updateFileCaption(${fil.id}, this.value);"></td>
										</tr> 
										<tr>
											<td>Description</td>
										</tr>
										<tr>
											<td>
												<textarea class="textbox4" onblur="updateFileDescription(${fil.id}, this.value);">${fil.description}</textarea>
											</td>
										</tr>
										<tr>
											<td>
												<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">											 
													<a onclick="return confirm('Do you really want to delete this file?');" href="deleteitemfile.do?group_id=${group.id}&item_id=${item.id}&file_id=${fil.id}">Delete</a>
												</c:if>
												|<a  href="downloaditemfile.do?group_id=${group.id}&item_id=${item.id}&file_id=${fil.id}">Download</a>
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
							
							<font color="#000000">
							* You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> files.
							<br>
<%--							* <b>${companySettings.image1Width} x ${companySettings.image1Heigth}</b> is the most advisable size of image that should be uploaded.   --%>
							</font>
							
							
							</td>
						</tr>
						<tr> 
							<td style="border:0px;">
							
								<div id="attachment2" class="attachment" style="display:none">
									<div id="dropcap2" class="dropcap">1</div>
								    
								    <input id="file" name="upload" type="file" size="30" />
								    <br/> 
								    <!-- 
								    <input id="desc" name="alt" type="text" size="30"/>
								    &nbsp;
								    --> 
								    <a href="#" onclick="javascript:removeFile2(this.parentNode.parentNode,this.parentNode);">Remove</a>
								    </div>
								    
								    <div id="attachments2" class="container">
								    <!--
								    **************************************************************************
								    Below is the call to the "addUpload" function which adds an Attachment
								    section to the form. Customize the function parameters as needed:
								    1) Maximum # of attachments allowed in this pub type
								    2) base Field Name for the Description fields in your pub type
								    3) base Field Name for the File Upload field in your pub type
								    **************************************************************************
								    -->
								    <br/><a id="addupload2" href="javascript:addUpload2(${allowedImageCount}, 'alt', 'upload'); ">Attach a file</a><br/><br/>
								    <span id="attachmentmarker2"></span>
								</div>
							
							</td>
						</tr>
						
						
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
						<tr>
							<td style="border:0px;">
							
								<div align="center">
								<input type="submit" value="Upload File Now!" class="upload_button2" />
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
			</td></tr>	
				<c:if test="${not empty item.comments }">
					<tr class="headbox" align="left">
						<th colspan="2"> HTML META Settings</th>
					</tr>
					<tr>
					<td>
						<table width="100%">
							<tr>
								<td></td>
								<td width="10%">Date</td>
								<td>User</td>
								<td>Comment</td>
								<td>Action</td>
							</tr>
							<c:forEach items="${item.comments }" var="comm" varStatus="counter">
								<tr>
									<td ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }>&nbsp;</td>
									<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }><fmt:formatDate pattern="MM-dd-yyyy hh:ss a" value="${comm.createdOn}" /></td>

									<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }>${comm.firstname } ${comm.lastname }</td>	
									<c:if test="${fn:length(comm.content) >70}">
									<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }><c:out value="${fn:substring(comm.content, 0, 10)}" />&nbsp;&nbsp;<a href="javascript:void(0);" onmouseover="Tip('${comm.content}')" onmouseout="UnTip()" style="text-decoration:none"><strong>Read More</strong></a></td>
									</c:if>
									<c:if test="${fn:length(comm.content) <= 70}">
									<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }>${comm.content }</td>
									</c:if>
											 								
									<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }><a href="deleteitemcomment.do?item_id=${item.id}&comment_id=${comm.id }" onClick="return confirm('Are you sure you want to delete this comment?');">Delete</a></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					</tr>
				</c:if>
			</table>
				
				
				
				<div>	
			</td>
</tr>



<%-- -------------  for file upload  --------------------------- --%>
<tr>
<td>
&nbsp;
</td>
			







			</c:when>
			<c:otherwise>
				<td width="40%" bgcolor="#ffffff" valign="top"></td>
			</c:otherwise>
		</c:choose>
	
		
	</tr>
	<!-- 
	<c:if test="${item.id > 0 }">
	<tr>
		<td colspan="2">
		
		<%@include file="includes/variationEditor.jsp" %>
		
		</td>
	</tr>
	</c:if>
	-->
</table>

<script>
	addUpload(${allowedImageCount}, 'alt', 'upload'); 
</script>

</div>

<!-- batch insertion of items via excel file -->
<br /><br />
<script type="text/javascript">
	function checkExcelFileUpload(){
		if(document.getElementById("excelFileUpload").value==""){
			alert("Please select an excel file first");
			return false;
		}

		return true;
	}
</script>
<c:if test="${user.company.companySettings.canBatchUpdateExcelFiles eq true && !(item.id>0)}">
	<div style="100%">
		<form action="previewbatchupdateitems.do" method="post" onsubmit="return checkExcelFileUpload();" enctype="multipart/form-data">
			<input type="hidden" name="groupId" value="${group.id}" />
			<table style="width:50%">
				<tr class="headbox">
					<th colspan="2">Batch Update via Excel File</th>
				</tr>
				<tr>
					<td style="text-align:center;width:50%">File</td>
					<td style="width:50%">
						<input type="file" name="upload" id="excelFileUpload" /><br />
					</td>
				</tr>
				<tr>
					<td>
						<p style="width:260px">
							Please note only a maximum of ${maxItem} row items for each Excel file will be parsed by the System.
						</p>
					</td>
					<td> 
						<input class="upload_button2" type="submit" value="Upload" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<br /><br />
						<p>* Only Microsoft Excel 97-2003 formats are allowed. Click <a href="downloadsampleexcelfile.do?group_id=${group.id}">here</a> for a sample file.</p>
						<p>* Click <a href="batchupdateexcelfiles.do?group_id=${group.id}">here</a> to view all previously uploaded Excel files in ${group.name} group.</p>
					</td>
				</tr>
			</table>
		</form>
	</div>
</c:if>