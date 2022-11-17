
<%@include file="includes/header.jsp"  %>

<body>
<c:set var="menu" value="groups" />
<c:set var="pagingAction" value="groups.do" />

<script>
function submitComboBox(myCombo) {
	return false;
}
</script>
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
			newCell0.innerHTML = 'Price Name '+priceNameCount+'<br /><input type="text" name="pn'+newPriceName+'"/>';
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
			newCell0.innerHTML = 'Other Field '+otherFieldCount+'<br /><input type="text" name="on'+newOtherField+'"/>';
		}	
		else
		{
			alert('You can only add up to ${company.companySettings.maxOtherFields} other fields.');
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

	    <h1><strong>Groups</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
					<tr > 
						<th >Group Name</th>  
						<c:if test="${company.id != 280}"><th>Max Categories</th></c:if>
						<th >No. of Categories</th>
						<th >Max Items</th>
						<th >Has Featured</th> 
						<th >Has Brands</th>
						<th >Has Attributes</th>
						<!-- th width="5%">Has 2 Prices</th-->
						<th>Out of Stock</th>
						<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'  or user.userType.value == 'Company Staff'}">
							<th>Action</th>
						</c:if>
						
					</tr>
					<c:set var="count" value="0" />
					<%-- <c:forEach items="${groupList}" var="g">  --%>
					<c:forEach items="${groups}" var="g">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
							<td><a href="items.do?group_id=${g.id}">${g.name}</a></td>
							<c:if test="${company.id != 280}"><td>${g.maxCategories}</td></c:if>
							<td>${fn:length(g.categories)}</td> 
							<td>${g.maxItems}</td>
							<td>
							
								<c:choose>
									<c:when test="${g.featured == true}">
										<b>Yes</b> 
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							
							</td>  
							
							<td>
							<c:choose>
								<c:when test="${g.hasBrands}">
									<b>Yes</b> 
								</c:when>
								<c:otherwise> 
									No 
								</c:otherwise>
							</c:choose>
							</td>
							
							<td>
							<c:choose>
								<c:when test="${g.hasAttributes}">
									<b>Yes</b> 
								</c:when>
								<c:otherwise> 
									No 
								</c:otherwise>
							</c:choose>
							</td>
							
							
							<%-- td>
							<c:choose>
								<c:when test="${g.has2Prices}">
									<b>Yes</b> 
								</c:when>
								<c:otherwise> 
									No 
								</c:otherwise>
							</c:choose>
							</td --%>
							
							<td>
							<c:choose>
								<c:when test="${g.isOutOfStock}">
									<b>Yes</b> 
								</c:when>
								<c:otherwise> 
									No 
								</c:otherwise>
							</c:choose>
							</td>

						<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Staff'}">							
							<td>
							
							<a href="items.do?group_id=${g.id}">View</a> |
							<a href="editgroup.do?group_id=${g.id}">Edit</a> |
							<c:if test="${user.userType.value != 'Company Staff'}">
								<a href="deletegroup.do?group_id=${g.id}" onclick="return confirm('Do you really want to delete this group?');">Delete</a> |
							</c:if>
							<a href="sortCategories.do?group_id=${g.id}">Sort Categories</a>
							<c:if test="${g.hasBrands}">
							 |
							<a href="sortBrands.do?group_id=${g.id}">Sort Brands</a>
							</c:if>
							</td>
						</c:if>	
						</tr>									
					</c:forEach>
										
				</table>		
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
			<form method="post" action="savegroup.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
				<c:if test="${group.id > 0}">
			<input type="hidden" name="group_id" value="${group.id}">
		</c:if>
	 <h2>New Group</h2>
		
	  <table id="table1" width="100%">
			<tr>
				<td >Group Name<br /><input type="text" id="group_name" name="group.name" value="${group.name}" class="w200" /></td>
			</tr>
			
			<tr>
				<td >Group Description<br /><textarea name="group.description" class="w200">${group.description}</textarea></td>
			</tr>		
			
			<tr>
				<td >Max Number of Categories<br /><input type="text" name="group.maxCategories" value="${group.maxCategories}" class="w200" /></td>
			</tr>
			 
			<tr>
				<td >Max Number of Items<br /><input type="text" name="group.maxItems" value="${group.maxItems}" class="w200" /></td>
			</tr>
			
			<tr>
				<td >Group Attributes
				<br />
				<s:checkbox name="group.featured" value="%{group.featured}" theme="simple" /> <i><b>Has featured items</b></i>
				<br/>
				
				<s:checkbox name="group.hasBrands" value="%{group.hasBrands}" theme="simple" /> <i><b>Has brands</b></i>				
				<br/>
				
				<s:checkbox name="group.hasAttributes" value="%{group.hasAttributes}" theme="simple" /> <i><b>Has attributes</b></i>
				<br/>
			
				
				<s:checkbox name="group.isOutOfStock" value="%{group.isOutOfStock}" theme="simple" /> <i><b>Has out of stock items</b></i>
				
				<c:if test="${company.name == 'hiprecision'}">
					
					<s:checkbox name="group.hasScheduleForm" value="%{group.hasScheduleForm}" theme="simple" /> <i><b>Items of this Group has Schedule.</b></i>	
				</c:if>
				
				
				</td>
			</tr>
			
			<c:if test="${company.companySettings.hasMemberFeature == true}">
				<tr>	
					<td>
					
					<s:checkbox name="group.loginRequired" value="%{group.loginRequired}" theme="simple" /> <i><b>This page requires member login.</i>
					
					</td>
					
				</tr>
			</c:if> 
			<tr> 
				<td> 
					<input type="checkbox" id="item_has_price" name="itemHasPrice" value="true" ${(group.itemHasPrice ne false)? 'CHECKED':'' } onchange="togglePriceCount();"/> <i><b>Items has price</b></i>
				</td>
			</tr>
			<c:if test="${group.itemHasPrice ne false}">
			<tr><td><a href="javascript:void(0);" onclick="addRow('table1')">Add Price Name</a></td></tr> 
			<tr id="itemName1">
				<td>Price Name 1(Default) <br /><input type="text" name="${(not empty group.categoryItemPriceNames)? 'n' : 'pn1' }${group.categoryItemPriceNames[0].id}" value="${(not empty group.categoryItemPriceNames)? group.categoryItemPriceNames[0].name : 'Price Name 1' }"/></td>
			</tr>	
			
				<c:forEach items="${group.categoryItemPriceNames }" var="itemName" varStatus="counter">
					<c:if test="${counter.count ne 1 }">
						<tr id="itemName${counter.count}">
							<td>Price Name ${counter.count } <br /><input type="text" name="n${itemName.id }" value="${itemName.name }"/></td> 
						</tr>	
					</c:if>
				</c:forEach> 
			</c:if>	
			
			</table>
			
				<c:if test="${company.companySettings.hasOtherFields == true}">
					<table id="table2" width="100%">
					<tr><td><a href="javascript:void(0);" onclick="addOtherFieldRow('table2')">Add Other Field</a></td></tr>
					<tr id="otherField1">
						<td colspan="2">Other Field 1<br /><input type="text" name="${(not empty group.otherFields)? 'o' : 'on1' }${group.otherFields[0].id}" value="${(not empty group.otherFields)? group.otherFields[0].name : 'Other Field 1' }"/></td>
					</tr>	
				
					<c:forEach items="${group.otherFields }" var="otherField" varStatus="counter">
						<c:if test="${counter.count ne 1 }">
							<tr id="otherField${counter.count}">
								<td colspan="2">Other Field ${counter.count } <br /><input type="text" name="o${otherField.id }" value="${otherField.name }"/></td> 
							</tr>	
						</c:if>
					</c:forEach> 
				</c:if>
			</table>
			
			<div id="fileList" style="display:none;">
			<br/>
			<h3>
					<img src="images/iImage.gif" align="absmiddle" /> Upload Image
				</h3>
				<ul class="uploadImageNote">
					<li>You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> files.
					</li>
					<li>500 x 590 is the most advisable size of image that should be uploaded.</li>
				</ul>
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
			</div>
			
			<table width="100%">
			<tr>
				
				<td>
				 
				<c:choose>
					<c:when test="${group.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='groups.do'" class="btnBlue">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add New" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='groups.do'" class="btnBlue">
					</c:otherwise>
				</c:choose>
								
				</td>
			</tr>
					
		</table>
		
		</form>
	</div><!--//sidenav-->
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container-->

 <script type="text/javascript">
		<c:if test="${company.name eq 'neltex'}">
			document.getElementById('fileList').style.display = 'inline';
		</c:if>
	</script>
	
</body>

</html>