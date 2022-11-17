<%@include file="includes/header.jsp"  %>



	<style type="text/css">
		@import "css/domtab.css";
	</style>
    <!--[if gt IE 6]>
        <style type="text/css">
            html>body ul.domtabs a:link,
            html>body ul.domtabs a:visited,
            html>body ul.domtabs a:active,
            html>body ul.domtabs a:hover{
                height:3em;
            }
        </style>
    <![endif]-->
	<script type="text/javascript" src="javascripts/domtab.js"></script>
	
	<!-- JQuery -->
	<link type="text/css" href="javascripts/jquery/jquery-ui-1.8.19.custom.css" rel="Stylesheet" />	
	<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="javascripts/jquery/jquery-ui-1.8.19.custom.min.js"></script>
	<script type="text/javascript">
		
		
	
		function addAnotherSection(parentTableId,hiddenTableId){
			//assignedPersonnelTable , assignedPersonnelHiddenTable
			$("#"+parentTableId).append($("#"+hiddenTableId).html());
			checkCheck();
		}

		function removeRow(obj){
				$(obj).parent().parent().parent().remove();
				checkCheck();
				return false;
		}

		function checkCheck(){

			var weekdays = ["monday","tuesday","wednesday","thursday","friday","saturday","sunday"];
			var scheduleFormHiddenFieldsBasefromCheckbox = "";
			
			for(var i=0;i<weekdays.length;i++){
					
					var dayCheckboxes = $("input[name="+weekdays[i]+"]:checkbox").map(function () 
						{
							var value = "";
							if(this.checked){
								value = weekdays[i];
							}
							return  hiddenField(weekdays[i],value);
						}
					);
					dayCheckboxes.splice(dayCheckboxes.length -1 ,1);
					scheduleFormHiddenFieldsBasefromCheckbox += dayCheckboxes.get().join(",");
					
			}
			$("#scheduleFormHiddenFieldsBasefromCheckbox").html("");
			$("#scheduleFormHiddenFieldsBasefromCheckbox").html(scheduleFormHiddenFieldsBasefromCheckbox);
		}

		function hiddenField(name,value){
			
			return  "<input type='hidden' name='day_"+name+"' value='"+value+"'>";
		//	select  CAST(dos as datetime)  as datesample from jobs  where dos is not null and dos != '' and CAST(dos as datetime) >= '01-01-2001' limit 10
		}

		 
    </script>
	
	<script type="text/javascript">
		document.write('<style type="text/css">');    
		document.write('div.domtab div{display:none;}<');
		document.write('/s'+'tyle>');   
	</script>

    <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<!-- calendar stylesheet -->

  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />



  <!-- main calendar program -->

  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>



  <!-- language for the calendar -->

  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>



  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->

  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>




<%@include file="ajaxQuery/displayCategoryList.jsp"  %>
<%@include file="ajaxQuery/displayItemList.jsp"  %>

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="items" />
<c:choose>
	<c:when test="${not empty catId}" >
		<c:set var="pagingAction" value="items.do?group_id=${group.id}&category_id=${catId}" />
						<c:set var="setVariable" value="${catId}" scope="session" />
	</c:when>
		<c:when test="${not empty category}" >
		<c:set var="pagingAction" value="items.do?group_id=${group.id}&category_id=${category.id}" />
		<c:set var="setVariable" value="${category.id}" scope="session" />
	</c:when>

	<c:otherwise>
		<c:set var="pagingAction" value="items.do?group_id=${group.id}" />
		<c:set var="setVariable" value="" scope="session" />
	</c:otherwise>
</c:choose>
<c:set var="firstParamGiven" value="${true}" />

<script>

function submitComboBox(myCombo) {
	var selectedId = myCombo.value;
	if(selectedId == 'all') {
		window.location = 'items.do';
	}
	else {
		window.location='items.do?category_id=' + selectedId;
	}
}
window.onload = function(){
	<c:if test="${company.id eq 404}"> <%-- Wendys --%>
	$('[name="o689"]').attr("multiple","multiple");
	$('[name="o689"]').attr("size","15");
	
	//$('[name="o690"]').replaceWith('<textarea id = "other_field" name="o690" cols = "35" rows = "5"></textarea>&nbsp;<input type = "button" id = "btn_update" value = "Update List" style = "vertical-align:top;" />')
	$('[name="o690"]').replaceWith('');
	//$('[name="o690"]').attr("readonly","readonly");
	
	/*
	$('[name="o658"]').change(function(){
		var str = "";
		$('[name="o658"] option:selected').each(function(){
			str += $(this).text().trim()+"; ";
		});
		$('[name="o659"]').attr("value",str);
	}).trigger("change");
	*/
	$(document).ready(function() { $('#btn_update').bind("click", showselectedvalue); });
	</c:if>
	
	<c:if test="${company.id eq 448}">
		$(document).ready(function(){
			$('[name="o1036"]').attr("disabled","disabled");
			/* $('[name="o958"]').attr("disabled","disabled"); */
		});
	</c:if>
}

function showselectedvalue()
{
	//alert(1);
	var str = "";
	$('[name="o689"] option:selected').each(function(){
		str += $(this).text().trim()+";";
	});
	$('[name="o690"]').attr("value",str);
}
</script>
<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

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
	
	function getElement(elementName) {
		var element = null;
		if(document.getElementById(elementName) != null)
		element = document.getElementById(elementName).value;
		//value = value.replace(/^\s+|\s+$/, '');
		return element;
	}
	
	function submitForm(myForm) {
				
		
		var name = getElement('item_name');
		var itemDate = getElement('item_date');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		var itemSKU = getElement('item_sku')
		var allowed = /^[a-zA-Z0-9,. ]*$/.test(itemSKU);

// 		if(!allowed){
// 			messages += "Invalid character in SKU field. \n";
// 			alert("Invalid character in SKU field.");
// 			error = true;
// 		}
	
		if(name.length == 0) {
			error = true;
			messages += " * Name was not specified. \n";
		}
		
		<c:if test="${company.name eq 'stockbridge' and group.name eq 'Events'}">
			if(itemDate.length == 0) {
				error = true;
				messages += " * Date was not specified. \n";
			}
		</c:if>
		
		/* var newDate = $('[name="o907"]').val() + " " + $('[name="o908"]').val() + ", " + $('[name="o909"]').val();
		$('[name="o958"]').attr('value', newDate); */
		
		<c:if test="${company.id eq 448}">
		var newDate = $('[name="o1040"]').val() + " " + $('[name="o1041"]').val() + ", " + $('[name="o1042"]').val();
		$('[name="o1036"]').attr('value', newDate);
		//agian_event_date
		$('#agian_event_date').val(newDate);
	</c:if>
		if(!isValid) {
			error = true;
			messages +=" * Attribute value/s not valid. \n";
		}
			
		if(error) {
			alert(messages);
		}
		else {
			<c:if test="${company.name eq 'hiprecision'}">
				checkCheck();
			</c:if>
			return true;
		}
		
		return false;
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
<style type="text/css"><!--
	
	#phoneticlong li, #buttons li {
		margin-bottom: 0px;
		margin-top: 4px;
		margin-left: 10px;
		font-size: 12px;
		cursor: hand;
		cursor: pointer;
	}

	
	//-->
</style>

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
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Item was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Item was successfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'itemexist'}">
								<li><span class="actionMessage">Item was not created because a similiar item already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Item was successfully deleted.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'sort'}">
								<li><span class="actionMessage">Display order of the categories was successfully updated.</span></li>
							</c:if>
	  						<c:if test="${param['evt'] == 'sortUpdated'}">
	  							<li><span class="actionMessage">The item ordering have been updated.</span></li>
	  						</c:if>
						</ul>
					</c:if>
					<c:if test="${param['batchUpdateStatus'] != null}">
						<ul>
		 					<c:if test="${param['batchUpdateStatus'] == 'success'}">
								<li><span class="actionMessage">Batch update successful.</span></li>
							</c:if>
							<c:if test="${param['batchUpdateStatus'] == 'failed'}">
								<li><span class="actionMessage">Batch update failed.</span></li>
							</c:if>
						</ul>
					</c:if>
					<c:if test="${param['previewBatchUpdateStatus'] != null}">
						<ul>
							<c:if test="${param['previewBatchUpdateStatus'] == 'failed'}">
								<li><span class="actionMessage">Preview of the batch update failed.</span></li>
							</c:if>
						</ul>
					</c:if>
					<c:if test="${not empty param['message'] and empty notAddedList }">
						<ul>							
							<li><span class="actionMessage">${param['message']}</span></li>							
						</ul>
					</c:if>
					<c:if test="${not empty notAddedList and not empty param['message'] }">
						<ul>
							<c:forEach items="${notAddedList}" var="notAdded">
								<li><span style="font-size: 120%; color: red;">${notAdded}</span></li>
							</c:forEach>											
						</ul>
					</c:if>
				
	  <div class="pageTitle">
	    <h1><strong>Group</strong>: Items
	    <c:forEach items="${sortedCategoriesList}" var="cat">
				<c:if test="${cat.id == catId}">
					${' - '}${cat.name}${cat.descriptor}					
				</c:if>
		</c:forEach>
		<c:if test="${not empty category}" >
			${' - '}${category.name}${category.descriptor}
		</c:if></h1>
		
	    <c:if test="${fn:length(group.categories) > 0}">
		<ul>
			<li>[<a href="#addItem">Add Item</a>]</li>
		</ul>
					</c:if>
		<div class="clear"></div>

	  </div><!--//pageTitle-->
	
			 <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>

	 
					<c:choose>
 	 				<c:when test="${fn:length(group.categories) gt 0}">
 	 				
	 	 			 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
							
	 	 				
							<tr >
								<th>Item Name</th> 
								<th>SKU</th> 
								<c:if test="${(company.id eq 319 or company.id eq 346 ) and group.name eq 'Products'}">
									<th>QTY</th>
									<th>D. Price</th>
								</c:if>
								<c:if test="${(company.id eq 346 ) and (group.name eq GurkkaConstants.PROMOBASKET or group.name eq GurkkaConstants.BASKETITEM)}">
									<th>QTY</th>
								</c:if>
								<th>Price </th> 
								<th >Brand</th>
								<th >Parent Category</th> 

								<th></th>
								<th></th>
								<c:if test="${group.isOutOfStock}"><th >Out of Stock</th></c:if>
								<c:if test="${company.companySettings.hasProductInventory eq true}"><th >Available Quantity</th></c:if> 
								<th>Action</th>
							</tr>
							<%-- -------------------------------------------------- --%>
							
							
							
							
							<c:set var="count" value="0" />
								<c:forEach items="${items}" var="item">
									<c:choose>
										<c:when test="${(company.id eq 319 or company.id eq 346) and (group.name eq 'Products' or group.name eq GurkkaConstants.PROMOBASKET or group.name eq GurkkaConstants.BASKETITEM)}">
											<%-- Gurkka Stock Level --%>
											<c:choose>
												<c:when test="${item.featured and inventoryQty[item.id] le 5 and inventoryQty[item.id] gt 0}">
													 <tr style="background: #F8FFA8;">
												</c:when>
												<c:when test="${item.featured and inventoryQty[item.id] le 0}">
													<tr style="background: #FFCBCB;">
												</c:when>
												<c:otherwise>
													<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>	
										</c:otherwise>
									</c:choose>
									
										<c:set var="count" value="${count+1}" />
										<td><a href="edititem.do?group_id=${group.id}&item_id=${item.id}&pageNumber=${pageNumber + 1}">${item.name}</a></td>
										<td>${item.sku}</td>
										<c:if test="${(company.id == 319 or company.id == 346) and (group.name eq 'Products' or group.name eq GurkkaConstants.PROMOBASKET or group.name eq GurkkaConstants.BASKETITEM)}">
											<c:forEach items="${item.parentGroup.otherFields}" var="otherField">
							              	 	<c:if test="${fn:contains(otherField.name, 'Inventory')}">
									 		    	<c:choose>
									 			  		<c:when test="${fn:length(inventoryQty[item.id]) gt 0}">
									 			  			<td>
									 			  				${inventoryQty[item.id]}
									 			  				<c:if test="${not empty inventoryQtyNoted[item.id]}">
								 			  						<img src="images/accept.png" title="Item quantity is noted" alt="Item quantity is noted">
									 			  				</c:if>
									 			  			</td>
								 			  			</c:when>
									 			  		<c:otherwise>
									 			  			<td class="empty1"></td>
									 			  		</c:otherwise>
													</c:choose>
							              	 	</c:if>
									 	   </c:forEach>
									 	   <c:if test="${not (company.id eq 346 and (group.name eq GurkkaConstants.PROMOBASKET or group.name eq GurkkaConstants.BASKETITEM))}">
									 	   <c:choose>
							 			  		<c:when test="${fn:length(dealerPrice[item.id]) gt 0}"><td>${dealerPrice[item.id]}</td></c:when>
							 			  		<c:otherwise><td></td></c:otherwise>
											</c:choose>
										  </c:if>
											
										</c:if>
										
										<td>${item.price}</td>
										<%-- c:if test="${group.has2Prices}">
										<td>${item.price2}</td>
										</c:if --%>
										<td>${item.brand.name}</td>
										<td>
											<c:choose>
												<c:when test="${item.parent == null}">
													None
												</c:when>
												<c:otherwise>
													${item.parent.name}
													
													<c:if test="${company.name eq 'kahaya'}">
													- ${item.parent.parentCategory.name}
													</c:if>
													<c:if test="${not empty item.parent.descriptor }">
														<span onmouseover="this.style.cursor='pointer';" title="${item.parent.name}${item.parent.descriptor}">...</span>
													</c:if>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
										
											<c:choose>
												<c:when test="${item.disabled == true}">
													<!-- <b>Yes</b>-->
													<img src="images/cancel.png" title="Item name is Disabled" alt="Item name is Disabled" />
													</c:when>
											</c:choose>
										
										</td>
										<td>
										
											<c:choose>
												<c:when test="${item.featured == true}">
													<!-- <b>Yes</b>-->
													<c:choose>
														<c:when test="${company.name eq 'gurkkatest' and group.name eq GurkkaConstants.COCKTAILS_GROUP_NAME}">
															<img src="images/accept.png" title="New Submitted" alt="New " />
															
														</c:when>
														<c:otherwise>
															<img src="images/accept.png" title="Item name is Featured" alt="Item name is Featured" />
													
														</c:otherwise>
													</c:choose>
													</c:when>
											</c:choose>
										
										</td>
										
										<c:if test="${group.isOutOfStock}">
										<td>
											<c:choose>
												<c:when test="${item.isOutOfStock == true}">
													<b>Yes</b>
												</c:when>
												<c:otherwise>
													No
												</c:otherwise>
											</c:choose>								
										</td>
										</c:if>
										
										<!-- <td>${item.sortOrder}</td>-->
										<c:if test="${company.companySettings.hasProductInventory eq true}"><td>${item.availableQuantity}</td></c:if>
										<td nowrap>
										
										<a href="edititem.do?group_id=${group.id}&item_id=${item.id}&pageNumber=${pageNumber + 1}">Edit</a>
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator' or user.userType.value == 'Events Administrator'}">
										 |
										<a href="deleteitem.do?group_id=${group.id}&item_id=${item.id}&pageNumber=${pageNumber + 1}" onclick="return confirm('Do you really want to delete this item?');">Delete</a>
									</c:if>	
										</td>
										
									</tr>
								</c:forEach>
																						
						</table> 
		 				
					</c:when>
					<c:otherwise>
						
							<c:if test="${not empty keyword}">
								 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
									<tr>
									<th>Name</td>
									<th colspan="2">Actions</td>
								</tr>
								<c:set var="count" value="0" />
								<c:forEach items="${searchList}" var="sl">
								
								<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
										<td>
											<a href="edititem.do?group_id=${sl.parentGroup.id}&item_id=${sl.id}&pageNumber=${pageNumber + 1}">${sl.name }</a>
										</td>
										<td>
											<a href="edititem.do?group_id=${sl.parentGroup.id}&item_id=${sl.id}&pageNumber=${pageNumber + 1}">Edit</a>
										</td>
										<td align="center">
											<a href="deleteitem.do?group_id=${sl.parentGroup.id}&item_id=${sl.id}&pageNumber=${pageNumber + 1}" onclick="return confirm('Do you really want to delete this item?');">Delete</a>
										</td>
									</tr>
								</c:forEach>					 		
								</table>
							</c:if>
					</c:otherwise> 
				</c:choose>
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	    <div class="clear"></div>
	  
		
	</div><!--//mainContent-->
	<div class="sidenav">
	
	<div id="noticeDiv">
	<h2>Notice</h2>
	<ul class="uploadImageNote">
<c:choose>
 	 				<c:when test="${fn:length(group.categories) > 0}">
		<li>Maximum of ${group.maxItems} items</li>
		<li>Maximum of ${maxItem} row items for each Excel file</li>
		<c:if test="${company.id eq 152 and group.id eq 160}">
			<li><a href="downloadgroupasexcel.do?group_id=${group.id}">Download as Excel file</a></li>
		</c:if>
		<li><a href="downloadsampleexcelfile.do?group_id=${group.id}">Sample Excel File</a></li>
						<li><a href="batchupdateexcelfiles.do?group_id=${group.id}">Previously Uploaded Excel Files</a>
					</li>
</c:when>
<c:otherwise>
		<li>To create an item, create a category first.</li>
</c:otherwise>
</c:choose>
	  </ul>
	  </div>
	  
	<h2>Search</h2>
	<ul>
	<form  method="post" action="search.do" >
						<li><input type="text" id="keyword"  name="keyword" class="w200"><input type="hidden" id="group1_id" name="group1_id" value="${group_id}" class="w200"><br /><input type="submit" name="go" value="Search"  class="btnBlue"></li>
					</form></ul>
					<h2>Filter</h2>
	<ul>
					
					<form name="catFilter" action="items.do" method="post">
					
						<input type="hidden" name="group_id" value="${group.id}" />
								<li>	<c:if test="${not empty companies }">
									<select id="catId" name="catId" class="w200">
										<option value=""<c:if test="${param.catId == null}">selected</c:if>>Select a category</option>
									<c:forEach items="${sortedCategoriesList}" var="cat">
										<option value="${cat.id}" <c:if test="${cat.id == catId}">selected</c:if>>${cat.name} ${cat.descriptor}</option>

									</c:forEach>
								</select>
							</c:if> <br />
						<input type="submit" value="Filter" class="btnBlue"></li>
						
					
				</form>
				</ul>
				<c:if test="${fn:length(group.categories) > 0 and company.name ne 'pocketpons' and company.name ne 'hpds'}">
				<h2>Update Via Excel</h2>
				<h2><a href="downloadAllItemsExcel.do?group_id=${group.id}">Download All Items</a></h2>
				<h2><a href="downloadEnabledItemsExcel.do?group_id=${group.id}">Download Enabled Items</a></h2>
				<h2><a href="downloadDisabledItemsExcel.do?group_id=${group.id}">Download Disabled Items</a></h2>
<script type="text/javascript">
	function checkZipFileUpload(){
		if(document.getElementById("zipFileUpload").value==""){
			alert("Please select an zip file first");
			return false;
		}
	
		return true;
	}
	function checkExcelFileUpload(){
		if(document.getElementById("excelFileUpload").value==""){
			alert("Please select an excel file first");
			return false;
		}
		if(loading != null) loading();
		return true;
	}
	function isValidExcelFile(){
		if(document.getElementById("excelFile").value==""){
			alert("Please select an excel file first");
			return false;
		}

		return true;
	}
</script>
<c:if test="${user.company.companySettings.canBatchUpdateExcelFiles eq true && !(item.id>0)}">
	
		<form action="previewbatchupdateitems.do" method="post" onsubmit="return isValidExcelFile();" enctype="multipart/form-data">
			<input type="hidden" name="groupId" value="${group.id}" />
			<table width=100%">
				
				<tr>
					<td>
						<input type="file" name="upload" id="excelFile" />
					</td>
				</tr>
					<td> 
						<input class="btnBlue" type="submit" value="Upload" />
					</td>
				</tr>
				
			</table>
		</form>
	
	</c:if>
	</c:if>

	<c:if test="${
	company.name eq 'hpds'
	||(company.name eq 'kuysen' and group.name eq 'Products')
	||(company.name eq 'sandigan' and group.name eq 'Marines')
	||(company.name eq 'ayrosohardware' and group.name eq 'Products') 
	||(company.name eq 'wilcon' and group.name eq 'Products') }">	
		
		
		<form action="saveuploaditem.do" method="post" onsubmit="return checkExcelFileUpload();" enctype="multipart/form-data">
			<input type="hidden" name="groupId" value="${group.id}" />
			<c:if test="${company.name eq 'hpds'}">
				<input type="hidden" name="parent" value="2383" />
				<input type="hidden" name="group_id" value="160" />
			</c:if>
			<table width=100%">
				
				<tr>
					<td>
						<input type="file" name="upload" id="excelFileUpload" />
					</td>
				</tr>
					<td> 
						<c:choose>
							<c:when test="${company.name eq 'wilcon' }">
								<input class="btnBlue" type="submit" value="Upload Excel or CSV File" />
							</c:when>
							
							<c:otherwise>
								<input class="btnBlue" type="submit" value="Upload Excel" />
							</c:otherwise>
						</c:choose>

					</td>
				</tr>
				
			</table>
		</form>

		
		<c:if test="${company.name ne 'wilcon' and company.name ne 'hpds' }">
			<form action="saveuploaditemimages.do" method="post" onsubmit="return checkZipFileUpload();" enctype="multipart/form-data">
				<input type="hidden" name="groupId" value="${group.id}" />
				<table width=100%">
					
					<tr>
						<td>
							<input type="file" name="upload" id="zipFileUpload" />
						</td>
					</tr>
						<td> 
							<input class="btnBlue" type="submit" value="Upload Images from ZIP" />
						</td>
					</tr>
					
				</table>
			</form>
		</c:if>

		<c:if test="${company.name ne 'hpds'}">
			<form action="saveuploaditemimages.do" method="post" onsubmit="return checkZipFileUpload();" enctype="multipart/form-data">
				<input type="hidden" name="groupId" value="${group.id}" />
				<table width=100%">
					
					<tr>
						<td>
							<input type="file" name="upload" id="zipFileUpload" />
						</td>
					</tr>
						<td> 
							<input class="btnBlue" type="submit" value="Upload Images from ZIP" />
						</td>
					</tr>
					
				</table>
			</form>
		</c:if>
	</c:if>
	</div>
	<c:if test="${fn:length(group.categories) > 0}">
	<div class="mainContent">
	<div class="pageTitle">
<a name="addItem"></a>
	    <h1><strong>Add Item</strong></h1>
		<c:set var="formAction" value="saveitem.do" />
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->
	<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		<input type="hidden" name="agian_event_date" id="agian_event_date" value="">
		<input type="hidden" name="group_id" value="${group.id}" />
		<c:if test="${item.id >0}">
			<input type="hidden" name="item_id" value="${item.id}" />
			<input type="hidden" name="pageNumber" value="${pageNumber}" />
		</c:if>
		
		<table width="100%" ${company.name eq 'hpds' ? 'id="itemFieldsContainer"' : ''}>
			
			
			<tr>
				<td class="label" id="parentCategoryLabel">
				<c:choose>
				<c:when test="${company.name=='hpdsked'}">
					Specialization
				</c:when>
				<c:otherwise>
					Parent Category
					
				</c:otherwise>
				</c:choose>
				</td>
				<td>
				
				<select name="parent" class="w385" 
					<c:if test="${company.name eq 'agian' and group.name eq 'Events and Activities'}"> id="agianParent" onchange="changeParentAgian();"</c:if>
					<c:if test="${company.name eq 'hpds' and group.name eq 'Test Procedures'}"> id="hpdsParent" onchange="changeParentHpds();"</c:if>
					<c:if test="${company.name eq 'hpds' and group.name eq 'Lab File'}"> id="hpdsParent" onchange="changeParentUrine();"</c:if>
				>
					<c:choose>
						<c:when test="${company.name eq 'gurkkatest' and group.name eq GurkkaConstants.COCKTAILS_GROUP_NAME}">
							<c:forEach items="${group.categories}" var="cat">
								<c:if test="${cat.id eq GurkkaConstants.COCKTAILS_CATEGORY_COCKTAILITEM_ID}">
									<option value="${cat.id}" <c:if test="${cat.id == item.parent.id}">selected</c:if>>${cat.name}  ${cat.descriptor}</option>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${categories}" var="cat">													
								<option value="${cat.id}" <c:if test="${cat.id == item.parent.id}">selected</c:if> data-value="${cat.name}">${cat.name}  ${cat.descriptor}</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
				
				</td>
			</tr> 
			
			<c:if test="${group.hasBrands && fn:length(group.brands)>0}">
				<tr>
					<td class="label">
				<c:choose>
				<c:when test="${company.name=='hpdsked'}">
					Branch
				</c:when>
				<c:otherwise>
					Brand
				</c:otherwise>
				</c:choose>
				</td>
					
					<td>
					 
					<select name="brand_id" class="w385">
						<option value=""></option>
						<c:forEach items="${group.brands}" var="b">
							<option value="${b.id}" <c:if test="${b.id == item.brand.id}">selected</c:if>>${b.name}</option>
						</c:forEach>
					</select>
					
					</td>
				</tr>
			</c:if>
			
			<tr>
				<td class="label" id="itemNameLabel">
					<c:choose>
						<c:when test="${company.name=='hpdsked'}">
							${group.name} Name
						</c:when>
						<c:when test="${company.name eq 'montero' and group.name eq 'Dealers'}">
							Dealer Name
						</c:when>
						
						<c:otherwise>
							Item Name
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${company.name eq 'pocketpons' }">
							<input type="text" id="item_name" name="item.name" value="${item.name}" class="w385" maxlength="30"/>
						</c:when>
						<c:otherwise>
							<input type="text" id="item_name" name="item.name" value="${item.name}" class="w385" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<c:if test="${company.name ne 'pocketpons' and company.name ne 'nissanrentacar'}">
				<tr id="skuRow">
					<td class="label">				
					<c:choose>
						<c:when test="${company.name=='hpdsked'}">
							Room
						</c:when>
						<c:when test="${company.name=='montero' and group.name eq 'Dealers'}">
							Dealer Address
						</c:when>
						<c:when test="${company.name eq 'agian' and group.name eq 'Events and Activities'}">
							Category
						</c:when>
						<c:when test="${company.name eq 'bci' and group.name eq 'Portfolio' or group.name eq 'Clients'}">
							Link
						</c:when>
						<c:otherwise>
							SKU
						</c:otherwise>
					</c:choose></td> 
					<c:choose>
					<c:when test="${company.name eq 'agian' and group.name eq 'Events and Activities' }">
										<td><select name="item.sku" id="item_sku" class="w385" disabled>
									 			<option value="" <c:if test="${item.sku eq ''}">selected</c:if>>---Selected Category---</option>
												<%-- <option value="Audit Tools" <c:if test="${item.sku eq 'Audit Tools'}">selected</c:if>>Audit Tools</option>
												<option value="Business Audit" <c:if test="${item.sku eq 'Business Audit'}">selected</c:if>>Business Audit</option>
												<option value="Financial Audit" <c:if test="${item.sku eq 'Financial Audit'}">selected</c:if>>Financial Audit</option>
												<option value="IT Audit" <c:if test="${item.sku eq 'IT Audit'}">selected</c:if>>IT Audit</option> --%>
												<c:forEach items="${agianKDCategories}" var="catItem">
															<option value="${catItem.name}" <c:if test="${item.sku eq catItem.name}">selected</c:if>>${catItem.name}</option>
															
												</c:forEach>
											</select>
										</td>
									</c:when>
					<c:otherwise>
						<td><input type="text" id="item_sku" name="item.sku" value="${item.sku}" class="w385" /></td>
					</c:otherwise>
					</c:choose>
				</tr>
			</c:if>
			<c:if test="${group.itemHasPrice ne false }"> 
				<tr>
					<td class="label">${(not empty group.categoryItemPriceNames)? group.categoryItemPriceNames[0].name : 'Price' }</td>
					
					<td><input type="text" id="item_price" name="item.price" value="${item.price}" class="w385" /></td>
				</tr>
				<c:forEach items="${group.categoryItemPriceNames }" var="priceName" varStatus="counter">
					<c:if test="${counter.count ne 1 }">
						<tr>
							<td class="label">${priceName.name }</td>
							<td><input type="text" id="item_price" name="p${priceName.id }" value="${prices[priceName.id] }" class="w385" /></td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
			
			<c:if test="${company.companySettings.hasOtherFields ne false }"> 							
				<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
					<c:set var="fieldname" value="PATIENT PREPARATION\SPECIAL INSTRUCTION" />
					<c:choose>
						<c:when test="${company.name eq 'pocketpons' and fn:containsIgnoreCase(otherField.name, 'Subtitle Text')}">
							<tr>
								<td width="1%" nowrap>${otherField.name}</td>
								<td><input type="text" id="other_field" name="o${otherField.id}" value="${otherFields[otherField.id] }" class="w385" maxlength="40"/></td>
							</tr>
						</c:when>
						
						<%-- For Bluechip --%>
							<c:when test="${company.name eq 'bluechip' and fn:contains(otherField.name, 'Colors')}">
									
									<input type="hidden" id="other_field_color" name="o${otherField.id}" value="" class="w385"/>
									
									<tr>
										<td class="label">Colors</td>
									
										<td>
											<div id="bluechipcolors"></div>
											<input type="text" placeholder="Color Name" id="bluechip_color_name"> &nbsp;<input class="jscolor" placeholder="Color Code" id="bluechip_color_code"/> <button onclick="bcAddColor(); return false;">Add</button>
										</td>
									</tr>
							</c:when>
						<%-- End of For Bluechip --%>
						
						<%-- For Hi Precision --%>
						
						<c:when test="${company.name eq 'hpds' and 
										( fn:containsIgnoreCase(otherField.name, 'SPECIMEN REQUIREMENT') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN STABILITY 15-25°C') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN PACKAGING FOR TRANSPORT') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN STABILITY 2-8°C') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN STABILITY -15-25°C') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN REJECTION') or
										fn:containsIgnoreCase(otherField.name, 'STORAGE AND TRANSPORT')
										)}">
								
						</c:when>
						
						<c:when test="${company.name eq 'hpds' and (
							otherField.name eq 'STORAGE AND TRANSPORT' or 
							otherField.name eq 'SPECIMEN STABILITY 15-25°C' or
							otherField.name eq 'SPECIMEN STABILITY 2-8°C' or 
							otherField.name eq 'SPECIMEN STABILITY -15-25°C' or 
							otherField.name eq 'SPECIMEN REJECTION' or 
							otherField.name eq 'SPECIMEN REQUIREMENT' or 
							otherField.name eq 'SPECIMEN CONTAINER' or 
							otherField.name eq 'SPECIMEN PACKAGING FOR TRANSPORT'  or
							otherField.name eq 'PURPOSE'  or
							
							
							otherField.name eq 'SPECIAL INSTRUCTIONS'  or
							otherField.name eq 'ALTERNATIVE SPECIMEN(S) & VOLUME REQUIREMENT'  or
							otherField.name eq 'PREFERRED SPECIMEN(S) & VOLUME REQUIREMENT'  or
							otherField.name eq 'INSTRUCTIONS TO ENCODERS'  or
							otherField.name eq 'SPECIMEN STABILITY'  or
							otherField.name eq 'OTHER NAME'  or
							otherField.name eq 'TEST'  or
							otherField.name eq 'ROOM TEMPERATURE'  or
							otherField.name eq 'REFRIGERATED'  or
							otherField.name eq 'FROZEN'  or
							otherField.name eq 'SECTION'  or
							otherField.name eq 'TRANSPORT TEMPERATURE'  or
							otherField.name eq 'REJECTION CRITERIA'  or
							otherField.name eq 'TIME OF RELEASE'  or
							otherField.name eq 'FAQ'  or
							
							otherField.name eq 'OTHER TEST REQUEST NAME' or
							otherField.name eq 'TEST COMPOSITION' or
							otherField.name eq 'INTENDED USE' or
							otherField.name eq 'METHODOLOGY' or
							otherField.name eq 'LABORATORY SECTION' or
							otherField.name eq 'REFERRING CLINICIANS' or
							otherField.name eq 'INSTRUCTIONS TO HP STAFFS' or
							otherField.name eq 'SPECIAL INSTRUCTIONS /PATIENT PREPARATIONS' or
							otherField.name eq 'SPECIAL INSTRUCTIONS/PATIENT PREPARATIONS' or
							
							otherField.name eq 'PATIENT PREPARATIONS' or
							
							otherField.name eq 'COLLECTION/SAMPLE CONTAINER' or
							otherField.name eq 'SPECIMEN AND VOLUME REQUIREMENT' or
							otherField.name eq 'ALTERNATIVE SPECIMEN AND VOLUME REQUIREMENT' or
							otherField.name eq 'SPECIMEN STABILITY' or
							otherField.name eq 'ROOM TEMPERATURE (15-25°C)' or
							otherField.name eq 'REFRIGERATED TEMPERATURE (2-8°C)' or
							otherField.name eq 'FREEZER TEMPERATURE (-20°C)' or
							otherField.name eq 'TRANSPORT TEMPERATURE' or
							otherField.name eq 'REJECTION CRITERIA' or
							otherField.name eq 'RUNNING DAY' or
							otherField.name eq 'CUT OFF TIME' or
							otherField.name eq 'TAT/RELEASING OF RESULTS' or
							otherField.name eq 'REFERENCE INTERVAL/RESULT INTERPRETATION' or
							otherField.name eq 'LIMITATIONS/INTERFERENCES' or
							otherField.name eq 'FREQUENTLY ASKED QUESTIONS (FAQS)' or
							otherField.name eq 'RELATED WORDS' or
							otherField.name eq 'BRANCH'  or
							otherField.name eq 'MACHINE'  or
							otherField.name eq 'SCHEDULE'  or
							
							otherField.name eq 'ANALYTE- ANALYTE' or
							otherField.name eq 'ANALYTE- MACHINES' or
							otherField.name eq 'ANALYTE- SAMPLE' or
							otherField.name eq 'ANALYTE- CRITICAL VALUE' or
							otherField.name eq 'ANALYTE- RECOMMENDATION DILUTION' or
							otherField.name eq 'ANALYTE- DILUENT' or
							otherField.name eq 'ANALYTE- AMR' or
							otherField.name eq 'ANALYTE- CRR' or
							otherField.name eq 'ANALYTE- REPEAT TEST CRITERIA (IF BELOW)' or
							otherField.name eq 'ANALYTE- REPEAT TEST CRITERIA (IF ABOVE)' or
							otherField.name eq 'ANALYTE- ACCEPTABLE DIFFERENCE (%)' or
							
							otherField.name eq 'LAB FILE- TEST' or
							otherField.name eq 'LAB FILE- PURPOSE' or
							otherField.name eq 'LAB FILE- TEST CODE' or
							otherField.name eq 'LAB FILE- RUNNING DAY' or
							otherField.name eq 'LAB FILE- CUT OFF TIME' or
							otherField.name eq 'LAB FILE- SPECIMEN CONTAINER' or
							otherField.name eq 'LAB FILE- METHODOLOGY' or
							otherField.name eq 'LAB FILE- MACHINES' or
							otherField.name eq 'LAB FILE- SOURCE OF ALERT VALUE' or
							otherField.name eq 'LAB FILE- REAGENT STABILITY' or
							otherField.name eq 'LAB FILE- EFFECTIVITY DATE' or
							
							otherField.name eq 'LAB FILE- SPECIMEN' or
							otherField.name eq 'LAB FILE- VOLUME REQUIREMENT' or
							otherField.name eq 'LAB FILE- PATIENT PREPARATION' or
							otherField.name eq 'LAB FILE- SPECIAL INSTRUCTION' or
							otherField.name eq 'LAB FILE- LIMITATIONS AND INTERFERENCES' or
							otherField.name eq 'LAB FILE- MEASURAND DETECTED' or
							otherField.name eq 'LAB FILE- CURRENT REFERENCE RANGE' or
							otherField.name eq 'LAB FILE- CONVERSION FACTOR' or
							otherField.name eq 'LAB FILE- DETECTION LIMIT' or
							
							otherField.name eq 'LAB FILE- REFERENCE RANGE BASED ON INSERT' or
							otherField.name eq 'LAB FILE- SOURCE OF REFERENCE RANGE' or
							otherField.name eq 'LAB FILE- SOURCE OF ALERT VALUE' or
							otherField.name eq 'LAB FILE- REAGENT STABILITY' or
							otherField.name eq 'LAB FILE- SPECIMEN STABILITY 15 to 25°C' or
							otherField.name eq 'LAB FILE- SPECIMEN STABILITY 2 to 8°C' or
							otherField.name eq 'LAB FILE- SPECIMEN STABILITY -15 to -25°C' or
							otherField.name eq 'LAB FILE- EFFECTIVITY DATE' or
							otherField.name eq 'LAB FILE- REMARKS' or
							
							otherField.name eq 'LAB FILE- TIME OF RELEASE' or
							otherField.name eq 'LAB FILE- MACHINE CODE' or
							otherField.name eq 'LAB FILE- PRESERVATIVE' or
							otherField.name eq 'LAB FILE- SPECIMEN DILUTION' or
							otherField.name eq 'LAB FILE- CONTROL' or
							otherField.name eq 'LAB FILE- CALIBRATOR' or
							otherField.name eq 'LAB FILE- ALERT VALUE' or
							otherField.name eq 'LAB FILE- LIMITATIONS AND INTERFERENCE' or
							otherField.name eq 'LAB FILE- CAUSE OF REJECTIONS' or
							
							otherField.name eq fieldname
							
							
							
							)}">
							<c:choose>
								<c:when test="${item.parent.name eq 'Imaging Test Procedures'}">
									<c:choose>
										<c:when test="${company.name eq 'hpds' and (																
											otherField.name eq 'SPECIAL INSTRUCTIONS'  or
											otherField.name eq 'OTHER NAME'  or
											otherField.name eq 'TEST'  or															
											otherField.name eq 'OTHER TEST REQUEST NAME' or
											otherField.name eq 'INTENDED USE' or
											otherField.name eq 'INSTRUCTIONS TO HP STAFFS' or
											otherField.name eq 'SPECIAL INSTRUCTIONS /PATIENT PREPARATIONS' or
											otherField.name eq 'SPECIAL INSTRUCTIONS/PATIENT PREPARATIONS' or
											
											otherField.name eq 'PATIENT PREPARATIONS' or
											
											otherField.name eq 'TAT/RELEASING OF RESULTS' or
											otherField.name eq 'REFERENCE INTERVAL/RESULT INTERPRETATION' or
											otherField.name eq 'FREQUENTLY ASKED QUESTIONS (FAQS)' or
											otherField.name eq 'BRANCH'  or
											otherField.name eq 'MACHINE' or																
											otherField.name eq 'SCHEDULE' 
											
											)}">	
											<c:set var="currentStyle" value="" />
										</c:when>
										<c:otherwise>
											<c:set var="currentStyle" value="display:none" />
										</c:otherwise>	
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${company.name eq 'hpds' and (
											otherField.name eq 'BRANCH'  or
											otherField.name eq 'MACHINE' or
											otherField.name eq 'SCHEDULE'																
											)}">
											<c:set var="currentStyle" value="display:none" />											
										</c:when>
										<c:otherwise>
											<c:set var="currentStyle" value="" />
										</c:otherwise>	
									</c:choose>					
								</c:otherwise>
							</c:choose>
							<tr style="${currentStyle}">
								<td width="1%" nowrap>
									<c:choose>
										<c:when test="${otherField.name eq 'Volume Requirement'}">
											SPECIMEN REQUIREMENT
										</c:when>
										<c:when test="${otherField.name eq 'RELATED WORDS'}">
											RELATED WORDS/TEST
										</c:when>
										<c:otherwise>
											${otherField.name}
										</c:otherwise>
									</c:choose>
								</td>
								<td><textarea type="text" id="other_field${otherField.id}" name="o${otherField.id}" class="w385" maxlength="40">${otherFields[otherField.id] }</textarea></td>
								<script type="text/javascript">
									CKEDITOR.replace( 'other_field${otherField.id}',
											{			
										
										toolbar : [[ 'Source', '-', 'Bold', 'Italic', '-', 'SpecialChar', '-','NumberedList','BulletedList','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock',
											'-','TextColor','BGColor','-','wordcount']],
										height:80,
										width: 500	
																
									}
									);	
								</script>
							</tr>
						</c:when>
						
	
	
						<c:when test="${company.name eq 'hpds'}">
							<tr>
								<td class="label">${otherField.name }</td>
								<td>
									<c:choose>
										<c:when test="${otherField.name eq 'PATIENT PREPARATION' or otherField.name eq 'TEST PACKAGE' or otherField.name eq 'SPECIAL INSTRUCTIONS'}">
											<textarea id="other_field" name="o${otherField.id }" rows="2" cols="60" class="w385">${otherFields[otherField.id]}</textarea>
										</c:when>										
										<c:otherwise>
											<input type="text" id="other_field" name="o${otherField.id }" value="${otherFields[otherField.id] }" class="w385" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="label">${otherField.name }</td>
								<td>
								<c:if test="${fn:length(otherField.otherFieldValueList) gt 0}">
									<select id="other_field" name="o${otherField.id }" class="w385" >
										<c:forEach items="${otherField.otherFieldValueList}" var="option">
											<option value="${option.value}" >
												${option.value}
											</option>
										</c:forEach>
									</select>
								</c:if>
								<c:if test="${fn:length(otherField.otherFieldValueList) eq 0}">																								
									<input type="text" id="other_field" name="o${otherField.id }" value="${otherFields[otherField.id]}" class="w385" />
								</c:if>									
								
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:if>
			
			<tr>
			
			
			<c:if test="${company.name eq 'hiprecision'}">
			</table>
				<table width="100%" id="doctorScheduleFormParentTableId">
					<%@include file="includes/scheduleForm.jsp"  %>
				<table>
				
				<table width="100%" id="doctorScheduleFormParentTableId">
					<tr>
						<td style="width:100%;text-align:right"><input type="button" value="Add Another Schedule" onclick="return addAnotherSection('doctorScheduleFormParentTableId','doctorScheduleFormHiddenTableId')" class="btnBlue"><input type="button" class="btnBlue" value="test" onclick="return checkCheck()"></td>
					</tr>	
				</table>
				<div id="scheduleFormHiddenFieldsBasefromCheckbox" style="display:none">
				
				</div>
			<table width="50%">
			</c:if>
			
			<script>
				function addNewFitting() {
					var tr = "<tr>" +
								"<td>" +
									"<select name=\"fittings\" style=\"width:100%\">" +
										<c:forEach items="${fittingsItem}" var="fitting">
											"<option value=\"${fitting.id}\">${fitting.name}</option>" +
										</c:forEach>		
									"</select>" +
								"</td>" +
								"<td align=\"center\"><input type=\"button\" value=\"Remove\" onclick=\"removeFitting(jQuery(this));\"/></td>" +
							 "</tr>";
					jQuery('#fittings tr:last').after(tr);
				}	
				
				function removeFitting(row) {
					row.closest('tr').remove();
				}
			</script>
			
			<c:if test="${company.id == 321}">
				</table>
				<table width="100%">
					<tr>
						<td width="20%" nowrap valign="top" align="right">Fittings</td>
						<td valign="top" width="80%">
							<table width="100%" id="fittings">
								<tr>
									<th align="center">ITEM NAME</th>
									<th align="center">ACTION</th>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="20%" nowrap valign="top"></td>
						<td valign="top" width="80%"><input type="button" value="Add New" onclick="addNewFitting();" /></td>
					</tr>
				</table>
				<table width="100%">
			</c:if>
			
			<c:if test="${company.companySettings.hasOrder and company.name ne 'hpds'}">
				<td class="label">Shipping Charge</td>				
					<td><input type="text" id="item_shipping_price" name="item.shippingPrice" value="${item.shippingPrice}" class="w385" /></td>
				</tr>
			</c:if>
			
			<c:if test="${company.companySettings.hasProductInventory eq true}">
				<input type="hidden" id="item_availableQuantity" name="item.availableQuantity" value="0" />
			</c:if>
			
			<tr id="searchTagsRow">
				<c:choose>
					<c:when test="${company.name eq 'hpds'}">
						<%-- display none --%>
					</c:when>
					
					<c:when test="${company.name eq 'montero' and group.name eq 'Dealers'}">
						<td class="label">Coordinates</td>
					</c:when>
					<c:otherwise>
						<td class="label">Search Tags</td>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${company.name eq 'hpds'}">
						<%-- display none --%>
					</c:when>
					<c:when test="${company.name eq 'montero' and group.name eq 'Dealers'}">
						<td><input type="text" id="search_tags" name="search_tags" value="${item.searchTags}" class="w385" placeholder="latitude,longitude"/></td>
					</c:when>
					<c:otherwise>
						<td><input type="text" id="search_tags" name="search_tags" value="${item.searchTags}" class="w385"/></td>
					</c:otherwise>
				</c:choose>
				
					
			</tr>
			
			<c:if test="${company.name ne 'hpds'}">
				<tr id="validUntilDateRow">
					<td class="label">${ company.name eq 'pocketpons' ? 'Valid until' : 'Date' }</td>
					
					<td>
					
					<fmt:formatDate pattern="MM-dd-yyyy" value="${item.itemDate}" var="idate"/>
									 
					<input type="text" id="item_date" name="itemDate" value="${idate}" class="textbox2" <c:if test="${company.name eq 'pocketpons' }">onfocus="this.nextElementSibling.click()"</c:if>/> 
					<a href="javascript:;" id="item_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar" /></a>
					   
					  
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
<!--			onchange="validate(${grp_att.name}, ${grp_att.datatype})"-->
			<c:if test="${group.hasAttributes && fn:length(group.attributes)>0}">
				<tr>
				<td colspan="3">
				<table>
				<tr>
				<td align="center"><h4>Attributes</h4></td>
				
				</tr>
				<c:choose>
					<c:when test="${empty item.attributes}">
						<c:forEach items="${group.attributes}" var="att" varStatus="i">
							<tr >
								<td class="label">${att.name}</td>
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
									<td class="label">${att.attribute.name}</td>
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
								<td class="label">${grp_att.name}</td>
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
								<td class="label">${att.name}</td>
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
				<tr><td></td><td><a href="manageAttributes.do?group_id=${ group.id }&item_id=${ item.id }">Manage Attributes</a></td></tr>
				</c:if>
				</table>
				</td></tr>
			</c:if>
			
			<c:if test="${not empty machineList}">
			<tr>
				<td class="label">MACHINE</td>
				<td>
				<hr>
					<table>
						<tr>
							<c:forEach items="${ machineList }" var="machine" varStatus="ctr">
								<c:if test="${ ctr.count mod 3 eq 0 }"></tr><tr></c:if>
								<td>
									<input type="checkbox" name="machineIds" id="machine_${ machine.id }" value="${ machine.id }"/>
										<label for="branch_${ machine.id }">&nbsp;&nbsp;${ machine.name }</label>
								</td>
							 </c:forEach>
							</tr>
						</table>
						<hr>
					</td>
				</tr>
			</c:if>
			
			
			<c:if test="${not empty branchList}">
			<tr>
				<td class="label">BRANCH</td>
				<td>
					<hr>
						<table>
							<tr>
								<c:forEach items="${ branchList }" var="branch" varStatus="ctr">
									<c:if test="${ ctr.count mod 3 eq 0 }"></tr><tr></c:if>
													
										<c:if test="${ ctr.count eq 1 }">
											<td><input type="checkbox" id="branch_0" value="0"/><label for="allBranch">&nbsp;&nbsp;All Branches</label></td>
									</c:if>
											<td>
												<input type="checkbox" name="branchIds" id="branch_${ branch.id }" value="${ branch.id }"/>
												<label for="branch_${ branch.id }">&nbsp;&nbsp;${ branch.name }</label>
											</td>
								</c:forEach>
							</tr>
						</table>
					<hr>	
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="label"></td>
				<td>
					<s:checkbox name="item.disabled" value="%{item.disabled}" theme="simple" /> <i><b>This item is disabled.</b></i>	
				</td>
			</tr>
			<tr>
				<td class="label"></td>
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
				
				<td class="label"></td>
				<td>
				
				<s:checkbox name="item.isOutOfStock" value="%{item.isOutOfStock}" theme="simple" /> <i><b>This item is out of stock.</b></i>	
				
				</td>
				
			</tr>
			</c:if>

		
			<tr id="fileList" style="display:none;">
				<td colspan="2"><br/><h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>
				<table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>
			
			<td width="40%"></td>
		  <td>
								    
			<div id="attachment" class="attachment" style="display:none">
									<div id="dropcap" class="dropcap">1</div>
								    
								    <input id="file" name="upload" type="file" size="30" />
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
								    <span id="attachmentmarker"></span>
								    <div align="right">
							    	 <a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload'); ">Attach an image</a><br/>
								</div>    
			</div>
			</td>

		</tr>

	  </table>
				</td>
			</tr>
			
			
			
			<tr>
				<td colspan="2">
				 
				<c:choose>
					<c:when test="${item.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" class="btnBlue" onclick="window.location='items.do?group_id=${group.id}'">
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
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container-->
  
  <c:if test="${company.name eq 'hiprecision'}">
  	<div style="display:none">
		<c:set var="displayRemove" value="${true}"/>
		<c:set var="displayRepeatLabel" value="${false}"/>
		<table width="50%" id="doctorScheduleFormHiddenTableId">
			<%@include file="includes/scheduleForm.jsp"  %>
		</table>
	</div>
  </c:if>
  
  <script type="text/javascript">
  
	  	$(document).ready(function(){
			//alert('ready function');
			changeParentUrine();
	  	});
  
		<c:if test="${company.name eq 'neltex' and (group.name eq 'Store Locator' or group.name eq 'Products' or group.name eq 'Equote')}">
			$('#noticeDiv').css('display','none');
			document.getElementById('fileList').style.display = 'table-row';
			$('#skuRow').css('display','none');
			$('#searchTagsRow').css('display','none');
			<c:if test="${group.name ne 'Products' and group.name ne 'Equote'}">
			$('#shortDescriptionRow').css('display','none');
			</c:if>
			$('#otherDetailsRow').css('display','none');
			$('#validUntilDateRow').css('display','none');
			<c:choose>
				<c:when test="${group.name eq 'Store Locator'}">
					$('#parentCategoryLabel').html('Province & Region');
					$('#itemNameLabel').html('Branch Name');
				</c:when>
			</c:choose>
		</c:if>
		
		function changeParentAgian(){
			if($('#agianParent').val() != 17478){
				$('#item_sku').attr('disabled','disabled');	
			}else{
				$('#item_sku').removeAttr('disabled');	
			}
		}
		
		function changeParentHpds(){
			var counter = 1;
			if($('#hpdsParent').find(':selected').attr('data-value') == 'Imaging Test Procedures'){
				<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
					<c:choose>															
						<c:when test="${company.name eq 'hpds' and (															

							otherField.name eq 'SPECIAL INSTRUCTIONS'  or
							otherField.name eq 'OTHER NAME'  or
							otherField.name eq 'TEST'  or															
							otherField.name eq 'OTHER TEST REQUEST NAME' or
							otherField.name eq 'INTENDED USE' or
							otherField.name eq 'INSTRUCTIONS TO HP STAFFS' or
							otherField.name eq 'SPECIAL INSTRUCTIONS /PATIENT PREPARATIONS' or
							otherField.name eq 'SPECIAL INSTRUCTIONS/PATIENT PREPARATIONS' or
							
							otherField.name eq 'PATIENT PREPARATIONS' or
							
							otherField.name eq 'TAT/RELEASING OF RESULTS' or
							otherField.name eq 'REFERENCE INTERVAL/RESULT INTERPRETATION' or
							otherField.name eq 'FREQUENTLY ASKED QUESTIONS (FAQS)' or
							otherField.name eq 'BRANCH'  or
							otherField.name eq 'MACHINE' or
							otherField.name eq 'SCHEDULE'
							)}">	
							$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
						</c:when>
						<c:otherwise>
							$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
						</c:otherwise>
					</c:choose>													
				</c:forEach>			
			}else{
				<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
				<c:choose>															
					<c:when test="${company.name eq 'hpds' and (															

						otherField.name eq 'BRANCH'  or
						otherField.name eq 'MACHINE' or 
						otherField.name eq 'SCHEDULE'
						)}">	
						$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
					</c:when>
					<c:otherwise>															
						$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
					</c:otherwise>
				</c:choose>													
			</c:forEach>			
			}																																
		}
		
		function changeParentUrine(){
			var counter = 1;
			var selectedCategory = $('#hpdsParent').find(':selected').attr('data-value');
			if(selectedCategory == 'Urine and other Body Fluids'){
				
				<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
					<c:choose>															
						<c:when test="${company.name eq 'hpds' and group.name eq 'Lab File' and (															
																						
							otherField.name eq 'LAB FILE- SPECIMEN CONTAINER' or
							otherField.name eq 'LAB FILE- VOLUME REEQUIREMENT'
							)}">	
							//alert('${otherField.name}');
							$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
						</c:when>
						<c:otherwise>															
							$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
						</c:otherwise>
					</c:choose>													
				</c:forEach>			
			}else if(selectedCategory == 'IMMUNO' || selectedCategory == 'CHEMISTRY'){
				
				<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
					<c:choose>															
						<c:when test="${company.name eq 'hpds' and group.name eq 'Lab File' and (															
																						
							otherField.name eq 'LAB FILE- MACHINE CODE' or
							otherField.name eq 'LAB FILE- MACHINES' or
							otherField.name eq 'LAB FILE- PRESERVATIVE' or
							otherField.name eq 'LAB FILE- SPECIMEN DILUTION' or
							otherField.name eq 'LAB FILE- CONTROL' or
							otherField.name eq 'LAB FILE- CALIBRATOR' or
							otherField.name eq 'LAB FILE- CAUSE OF REJECTIONS'
							
							)}">	
							//alert('${otherField.name}');
							$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
						</c:when>
						<c:otherwise>															
							$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
						</c:otherwise>
					</c:choose>													
				</c:forEach>			
			}
		}
		
	</script>
  
</body>

</html>