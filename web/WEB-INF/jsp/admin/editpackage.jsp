<%@include file="includes/header.jsp"%>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRPackageItemAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
<c:set var="menu" value="packages" />
<c:set var="formAction" value="updatepackages.do" />
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
							<li><span class="actionMessage">Package was successfully created.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Package was successfully updated.</span></li>
						</c:if>
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Package was successfully deleted.</span></li>
						</c:if>
					</ul>
				</c:if>
				<div class="pageTitle">
					<h1>
						<strong>Edit Package</strong>
					</h1>
					<div class="clear"></div>
				</div>
				<!--//pageTitle-->
				<div class="clear"></div>
				<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
					<div style="width: 100%;">
						<table>
							<tr>
								<td style="border: 0px;" valign="top">
									<a name="packageForm" />
									<c:set var="formAction" value="updatepackage.do" />
									<%@include file="packageform.jsp"%>
									<br />
									<br />
									<br />
									<c:if test="${not empty packageObj.categoryItemPackages}">
										<table>
											<tr>
												Items
											</tr>
											<c:forEach items="${packageObj.categoryItemPackages}" var="cip">
												<tr>
													<td>
														<tr>
															<td>
																${cip.categoryItem.name}
																<c:if test="${company.id eq 321}">
																	(${cip.categoryItem.descriptionWithoutTags})
																</c:if>																
															</td>
															<td>
																<a href="packages-deleteitem.do?cipId=${cip.id}">delete</a>
															</td>
														</tr>
													</td>
												</tr>
											</c:forEach>
										</table>
									</c:if>
									<br />
									<br />
									<br />
									<c:if test="${not empty allCategoryItems}">
										<form action="packages-additem.do" method="post">
											<table>
												<td>
													<td width="1%" nowrap>Add Item:</td>
													<td width="10px">
														<select name="ciId">
															<option value="">-- Select Item --</option>
															<c:forEach items="${allCategoryItems}" var="ci">
																<option value="${ci.id}">
																${ci.name}
																<c:if test="${company.id eq 321}">
																	(${ci.descriptionWithoutTags})
																</c:if>
																</option>
															</c:forEach>
														</select>
													</td>
													<td>
														<input type="hidden" name="packageId" value="${packageObj.id}" />
														<input type="submit" value="add" />
													<td>
												</td>
											</table>
										</form>
									</c:if>
								</td>
							</tr>
						</table>
					</div>
				</c:if>
			</div>
		</div>
		<!--//sidenav-->
	</div>
	<div class="clear"></div>
	</div>
	<!--//container-->
</body>
</html>
<%--
<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRPackageItemAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<c:set var="pagingAction" value="packages.do" />
<c:set var="menu" value="groupItem" />
<c:set var="submenu" value="packages" />

<script type="text/javascript">
	function addCallback(result){
		if(result=='ok') DWRPackageItemAction.getList(${packageObj.id},getListCallback);
		else alert(result); 
	}
	
	function editCallback(result){
		if(result=='true') DWRPackageItemAction.getList(${packageObj.id},getListCallback);
		else alert("Failed to update the item."); 
	}
	
	function deleteCallback(result){
		if(result=='true') DWRPackageItemAction.getList(${packageObj.id},getListCallback);
		else alert("Failed to delete the item.");
	}
	
	function getListCallback(result){
		var sb = "<table><tr class='headbox'><td>Item</td><td>Regular Price</td><td>Package Price</td><td>Action</td></tr>"
		var resultObj = eval(result);
		var totalPrice = 0 ;
		
		for(var i=0; i < resultObj.length; i++){
			sb += '<tr>';
			sb += '<td>' + resultObj[i].name + '</td>';
			sb += '<td>' + formatCurrency(resultObj[i].regular_price) + '</td>';
			sb += '<td id=listBody_item_package_price_'+resultObj[i].id+'>' + formatCurrency(resultObj[i].price) + '</td>';
			sb += '<td><a href="javascript:editItem('+resultObj[i].id+')">Edit</a> | <a href="javascript:DWRPackageItemAction.deleteItem('+resultObj[i].id+', deleteCallback)" onclick="return confirm(\'Do you really want to delete this item in the package?\');">Delete</a>';
			sb += '</tr>';

			totalPrice += parseFloat(resultObj[i].price);
		}
		
		document.getElementById("listBody").innerHTML = sb + "</table>";
		document.getElementById("total_price").innerHTML = formatCurrency(totalPrice);
	}

	function getPriceCallback(result){
		document.getElementById("regular_price").innerHTML = formatCurrency(result);
		document.getElementById("package_price").value = result;
	}
	
	function addItem(){
		var cat_id = document.getElementById("item_id").value;
		var price = document.getElementById("package_price").value;
		
		if(cat_id.length==0 || price.length==0 || isNaN(price)){
			return;
		}
		
		DWRPackageItemAction.addItem(cat_id, ${packageObj.id}, price, addCallback);
	}

	function changeSelection(){
		var id = document.getElementById("item_id").value;
		if(id.length>0) DWRPackageItemAction.getPrice(id, getPriceCallback);
		else{
			document.getElementById("regular_price").innerHTML = "";
			document.getElementById("package_price").value = "";
		}
	}

	function editItem(itemId){
		var price = prompt("Enter new package price for this item", document.getElementById("listBody_item_package_price_" + itemId).innerHTML);

		price = price+"";
		
		if(price) price = price.replace("," , "");
		
		if(!isNaN(price) && parseFloat(price) >= 0){
			 DWRPackageItemAction.editItem(itemId, price, editCallback);
		}
	}

	function formatCurrency(amount){
		var delimiter = ",";
		amount = parseFloat(amount).toFixed(2);
		amount = amount + "";
		
		var a = amount.split('.',2);
		var d = a[1];
		var i = parseInt(a[0]);
		
		if(isNaN(i)){
			return ''; 
		}
		
		var minus = '';
		if(i < 0) {
			minus = '-';
		}

		i = Math.abs(i);

		var n = new String(i);
		var a = [];

		while(n.length > 3){
			var nn = n.substr(n.length-3);
			a.unshift(nn);
			n = n.substr(0,n.length-3);
		}
		
		if(n.length > 0) {
			a.unshift(n); 
		}
		
		n = a.join(delimiter);

		if(d.length < 1) {
			amount = n;
		}else {
			amount = n + '.' + d; 
		}
		amount = minus + amount;
		return amount;
	}
</script>

<body onload="DWRPackageItemAction.getList(${packageObj.id},getListCallback)">

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
				<div id="companyhome">
 				
 				<h3>Edit Package</h3>
 				
				<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
					<a name="packageForm" />
					<c:set var="formAction" value="updatepackage.do" />
					<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />
					<div style="width:100%;">
						<table>
							<tr>
								<td style="border:0px;" valign="top">
									<form method="post" action="${formAction}">
										<input type="hidden" name="package_id" value="${packageObj.id}" />
										<table>
											<tr>
												<td width="1%" nowrap>Package Name</td>
												<td width="10px"></td>
												<td><input type="text" id="package_name" name="package_name" class="textbox3" value="${packageObj.name}" /></td>
											</tr>
											<tr>
												<td width="1%" nowrap>SKU</td>
												<td width="10px"></td>
												<td><input type="text" id="sku" name="sku" class="textbox3" value="${packageObj.sku}"/></td>
											</tr>
											<tr>
												<td width="1%" nowrap>Total Package Price</td>
												<td width="10px"></td>
												<td id="total_price">0.00</td>
											</tr>
											<tr>
												<td colspan="2" style="border: 0px;"></td>
												<td style="border: 0px;">
													<input type="submit" value="Update" class="upload_button2" />	
												</td>
											</tr>
										</table>
									</form>
								</td>
							</tr>
						</table>
					</div>
					
					<br /><br /><br /><br />
					
					<div id="listBody">
						<table>
							<tr class="headbox">
								<td>Item</td>
								<td>Regular Price</td>
								<td>Package Price</td>
								<td>Action</td>
							</tr>
							<tr>
								<td style="text-align:center" colspan="4">Loading....</td>
							</tr>
						</table>
					</div>
					<br /><br /><br /><br />
					
					<table>
						<tr class="headbox">
							<td>Item</td>
							<td>Regular Price</td>
							<td>Package Price</td>
							<td>Action</td>
						</tr>
						<tr>
							<td>
								<select name="item_id" id="item_id" onchange="changeSelection()">
									<option value=""></option>
									<c:forEach items="${catItems}" var="curr">
										<c:if test="${curr.parentGroup.id eq 160}">
											<option value="${curr.id}">${curr.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td id="regular_price"></td>
							<td>
								<input type="text" name="package_price" id="package_price" />
							</td>
							<td>
								<a href="javascript:addItem();">Add</a>
							</td>
						</tr>
					</table>
				</c:if>			 		
				
 				</div>
						
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
 --%>