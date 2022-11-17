<%@include file="includes/header.jsp"  %>

<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<c:set var="menu" value="promocode" />
<c:set var="pagingAction" value="promocode.do" />
<c:set var="mode" value="add"/>

<link rel="stylesheet" href="css/chosen.css">

<script language="javascript" src="../javascripts/overlib.js"></script>
<!-- main calendar program -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
<!-- language for the calendar -->
<script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
<!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>
<script language="javascript" src="../javascripts/jquery-1.6.2.min.js"></script>
<script language="javascript" src="../javascripts/jquery-latest.js"></script>
<script language="javascript" src="../javascripts/jquery.jplayer.min.js"></script>
<script language="javascript" src="../javascripts/jquery-ui.min.js"></script>
<script language="javascript" src="../javascripts/jquery.numeric.js"></script>

<script>
	$(document).ready(function(){ 
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		
		<c:if test="${company.name eq 'gurkkatest'}">
			$('[data-rel=chosen]').chosen();
		</c:if>
	}); 

	function showMessage(id) {
		var content = document.getElementById('promocode_'+id).innerHTML;
		overlib(content, STICKY, NOCLOSE)
	}
	
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
	
	function submitForm(myForm) {
		var code = getElement('code');
		var discount = getElement('discount');
		var fromDate = getElement('fromDate');
		var toDate = getElement('toDate');
		
		var type = getElement('type');
		var error = false;
		var messages = 'Problem(s) occured: \n\n';
		
		document.getElementById('note').value = type;
		
		var note = getElement('note');
		
		if(code.length == 0) {
			messages += '* Promo Code is not given\n';
			error = true;
		}
		
		if(discount.length == 0) {
			messages += '* Discount is not given\n';
			error = true;
		}
		
		if(fromDate.length == 0) {
			messages += '* From Date is not given\n';
			error = true;
		}
		
		if(toDate.length == 0) {
			messages += '* To Date is not given\n';
			error = true;
		}
		
		if(note.length == 0) {
			messages += '* Note is not given\n';
			error = true;
		}
		
		if(new Date(fromDate) < new Date(toDate)){
			//alert("fromDate less than toDate");
		}else if(new Date(fromDate) > new Date(toDate)){
			//alert("To Date should be later than From Date");
			messages += '* To Date should be later than From Date';
			error = true;
		}else{
			//alert("fromDate equals toDate");
		}
		
		if(error) {
			alert(messages);
		}
		else {
			
			var content = "";
			$('.child-promo').each(function(){
				if($(this).is(':checked'))
					content += (content == '' ? $(this).val() : "=="+$(this).val());
			});
			$('#items').val(content);
			
			return true;
		}
		
		return false;
	}
	
	function addItem(id, value) {
		var items = $('#items').val();
		var exists = false;
		
		if(id == "") {
			alert("Please select item.");
			return false;
		}
		
		if (~items.indexOf(id)) {
			exists = true;
			if(value == "All Items") {
				alert("You already selected All items");
				return false;
			}
		}
		
		if(items == "AllItems") {
			alert("You already selected All items");
			return false;
		}
		
		if(!exists) {
			var html = "";
			html += "<tr id=\"item-"+id+"\">";
			if(id == "AllItems")
				id = "'AllItems'";
			html+="<td>"+value+"</td>"
					+"<td>"
					+"<div style=\"display:none\"><input type=\"checkbox\" class=\"child-promo\" checked value=\""+id+"\"></div>"
					+"<input type=\"button\" value=\"Remove\" class=\"btnBlue\" onclick=\"removeItem("+id+")\"></td></tr>";
			$('#items-container').append(html);
			var content = "";
			$('.child-promo').each(function(){
				if($(this).is(':checked'))
					content += (content == '' ? $(this).val() : "=="+$(this).val());
			});
			$('#items').val(content);
		} else {
			alert(""+value+ " already in the exist");
		}
	}
	
	function removeItem(id) {
		if(id == "AllItems") {
			$("#item-AllItems").remove();
		} else {
			$('#item-'+id).remove();
		}
		var content = "";
		$('.child-promo').each(function(){
			if($(this).is(':checked'))
				content += (content == '' ? $(this).val() : "=="+$(this).val());
		});
		$('#items').val(content);
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
</script>

<body>
<div class="container">
<%@include file="includes/bluetop.jsp"%>
<%@include file="includes/bluenav.jsp"%>

<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent" ${company.name eq 'hiprecisiononlinestore' ? 'style="float:none"' : '' }>
    	<c:if test="${company.name eq 'gurkkatest' }">
    		<style>
	 			div.tab {
				    overflow: hidden;
				    border-bottom: 1px solid #ccc;
				}
				
				div.tab a {
				    float: left;
				    display: block;
				    color: black;
				    text-align: center;
				    padding: 14px 16px;
				    text-decoration: none;
				    transition: 0.3s;
				    font-size: 15px;
				}
				
				div.tab a:hover {
				    background-color: #ddd;
				}
				
				div.tab a:focus, .active {
				    background-color: #ccc;
				}
				
				.tabcontent {
				    display: none;
				    padding: 5px 10px;
				    border: 1px solid #ccc;
				    border-top: none;
				}
	 		</style>
	 		
	 		<div class="tab">
		 		<a href="javascript:void(0)" id="btnForProduct" onclick="changePromoCodeFor('forProduct')" class="tabLinks active">For Product</a>
		 		<a href="javascript:void(0)" id="btnForShippingFee" onclick="changePromoCodeFor('forShippingFee')" class="tabLinks">For Shipping Fee</a>
		 	</div>
    		<br><br>
    	</c:if>
    
	 	<s:actionmessage />
		<s:actionerror />
		<c:if test="${param['evt'] != null}">
			<ul>
				<c:if test="${param['evt'] == 'duplicate'}">
					<li><span class="actionMessage">Duplicate Promo Code.</span></li>
				</c:if>
			</ul>
		</c:if>
		
		
	  	<div class="pageTitle">
	    	<h1><strong>Promo Code</strong>: View Promo Codes</h1>
			<div class="clear"></div>
	  	</div><!--//pageTitle-->

	  	<ul class="pagination">
	   		<%@include file="includes/pagingnew.jsp"  %>
	  	</ul>
			
		<div class="clear"></div>
 		
 		<c:choose>
 		<c:when test="${company.name eq 'gurkkatest' }">
 		<!-- for product -->
 		<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable" id="forProduct" style=""> 
			<tr>
				<th>Promo Code</th>
				<th>Discount</th>
				<th>From Date</th>
				<th>To Date</th>
				<th>Disabled?</th>
				<c:choose>
				<c:when test="${company.name eq 'gurkkatest' }">
					<th>Minimum</th>
					<th>Usage</th>
				</c:when>
				<c:otherwise>
					<th>Note</th>
				</c:otherwise>
				</c:choose>
				<th>Action</th>
			</tr>
			
			<c:set var="count" value="0" />
			<c:set var="yes" value="Yes" />
			<c:set var="no" value="No" />
			<c:forEach items="${promoCodes}" var="promo">
				<c:if test="${promo.promoFor eq null or promo.promoFor eq 'forproduct'}">
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
						<c:set var="count" value="${count+1}" />
						<td>${promo.code}</td>
						<td align="right"><c:if test="${promo.promoType eq 'cash' }">&#8369;&nbsp;</c:if><fmt:formatNumber value="${promo.discount}" maxFractionDigits="2"/><c:if test="${promo.promoType ne 'cash' }">%</c:if></td>
	                    <td><joda:format pattern="MM-dd-yyyy" value="${promo.fromDate}" /></td>
	                    <td><joda:format pattern="MM-dd-yyyy" value="${promo.toDate}" /></td>
						<td>${promo.isDisabled ? yes : no}</td>
						<c:choose>
						<c:when test="${company.name eq 'gurkkatest' }">
							<td><fmt:formatNumber value="${promo.minimumRequirement}" pattern="#,###.##" /> ${ promo.minimumRequirementUnit}</td>
							<td align="right">${promo.currentUsage} of ${promo.maxUsage } used</td>
						</c:when>
						<c:otherwise>
							<td>
								<c:if test="${not empty promo.note }">
									<div align="center"><img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage(${promo.id});"></div>
									<div id="promocode_${promo.id}" style="display: none;">${promo.note}</div>
								</c:if>	
							</td>
						</c:otherwise>
						</c:choose>
						<td>
							<a href="editpromocode.do?promoCodeId=${promo.id}">Edit</a> |
							<a href="deletepromocode.do?promoCodeId=${promo.id}" onclick="return confirm('Do you really want to delete this promo code?');">Delete</a>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table> 
		<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable" id="forShippingFee" style="display:none;"> 
			<tr>
				<th>Promo Code</th>
				<th>Discount</th>
				<th>From Date</th>
				<th>To Date</th>
				<th>Disabled?</th>
				<c:choose>
				<c:when test="${company.name eq 'gurkkatest' }">
					<th>Minimum</th>
					<th>Usage</th>
				</c:when>
				<c:otherwise>
					<th>Note</th>
				</c:otherwise>
				</c:choose>
				<th>Action</th>
			</tr>
			
			<c:set var="count" value="0" />
			<c:set var="yes" value="Yes" />
			<c:set var="no" value="No" />
			<c:forEach items="${promoCodes}" var="promo">
				<c:if test="${promo.promoFor eq 'forshippingfee' }">
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
						<c:set var="count" value="${count+1}" />
						<td>${promo.code}</td>
						<td><c:if test="${promo.note eq 'cash' }">&#8369;</c:if>${promo.discount}<c:if test="${promo.note ne 'cash' }">%</c:if></td>
	                    <td><joda:format pattern="MM-dd-yyyy" value="${promo.fromDate}" /></td>
	                    <td><joda:format pattern="MM-dd-yyyy" value="${promo.toDate}" /></td>
						<td>${promo.isDisabled ? yes : no}</td>
						<c:choose>
						<c:when test="${company.name eq 'gurkkatest' }">
							<td><fmt:formatNumber value="${promo.minimumRequirement}" pattern="#,###.##" /> ${ promo.minimumRequirementUnit}</td>
							<td>${promo.currentUsage} of ${promo.maxUsage } used</td>
						</c:when>
						<c:otherwise>
							<td>
								<c:if test="${not empty promo.note }">
									<div align="center"><img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage(${promo.id});"></div>
									<div id="promocode_${promo.id}" style="display: none;">${promo.note}</div>
								</c:if>	
							</td>
						</c:otherwise>
						</c:choose>
						<td>
							<a href="editpromocode.do?promoCodeId=${promo.id}">Edit</a> |
							<a href="deletepromocode.do?promoCodeId=${promo.id}" onclick="return confirm('Do you really want to delete this promo code?');">Delete</a>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table> 
 		</c:when>
 		<c:otherwise>
 		<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
			<tr>
				<th>Promo Code</th>
				<th>Discount</th>
				<th>From Date</th>
				<th>To Date</th>
				<th>Disabled?</th>
				<c:choose>
				<c:when test="${company.name eq 'gurkkatest' }">
					<th>Minimum</th>
					<th>Usage</th>
				</c:when>
				<c:otherwise>
					<th>Note</th>
				</c:otherwise>
				</c:choose>
				<th>Action</th>
			</tr>
			
			<c:set var="count" value="0" />
			<c:set var="yes" value="Yes" />
			<c:set var="no" value="No" />
			<c:forEach items="${promoCodes}" var="promo">
				<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
					<td>${promo.code}</td>
					<td><c:if test="${promo.note eq 'cash' }">&#8369;</c:if>${promo.discount}<c:if test="${promo.note ne 'cash' }">%</c:if></td>
                    <td><joda:format pattern="MM-dd-yyyy" value="${promo.fromDate}" /></td>
                    <td><joda:format pattern="MM-dd-yyyy" value="${promo.toDate}" /></td>
					<td>${promo.isDisabled ? yes : no}</td>
					<c:choose>
					<c:when test="${company.name eq 'gurkkatest' }">
						<td><fmt:formatNumber value="${promo.minimumRequirement}" pattern="#,###.##" /> ${ promo.minimumRequirementUnit}</td>
						<td>${promo.currentUsage} of ${promo.maxUsage } used</td>
					</c:when>
					<c:otherwise>
						<td>
							<c:if test="${not empty promo.note }">
								<div align="center"><img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage(${promo.id});"></div>
								<div id="promocode_${promo.id}" style="display: none;">${promo.note}</div>
							</c:if>	
						</td>
					</c:otherwise>
					</c:choose>
					<td>
						<a href="editpromocode.do?promoCodeId=${promo.id}">Edit</a> |
						<a href="deletepromocode.do?promoCodeId=${promo.id}" onclick="return confirm('Do you really want to delete this promo code?');">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table> 
 		</c:otherwise>
 		</c:choose>
			
		<ul class="pagination">
	   		<%@include file="includes/pagingnew.jsp"  %>
	  	</ul>
	</div><!--//mainContent-->

	<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
		<div class="sidenav" ${company.name eq 'hiprecisiononlinestore' ? 'style="float:none; width: 530px;"' : '' }>
	 		<h2>New Promo Code</h2>
			
			<c:if  test="${mode eq 'add'}">
				<form name="newPromoCodeForm" method="post" action="savepromocode.do" onsubmit="return submitForm(this);">
			</c:if>
			<c:if  test="${mode eq 'edit'}">
				<form name="savePromoCodeForm" method="post" action="savepromocodeform.do" onsubmit="return submitForm(this);">
				<input type="hidden" name="promo_code_id" value="${promoCode.id }"/>
			</c:if>
			<input type="hidden" name="promoCode.promoFor" id="promo_for" value="forproduct">
			<table width="100%">
				<tr>
					<td>
						Promo Code<br /><input type="text" id="code" name="promoCode.code" value="" class="w200">
					</td>
				</tr>	
				<c:if test="${company.name eq 'gurkkatest'}">
				<tr>
						<td>
							Brands <br>
							<select name="promoCode.brand"  data-rel="chosen"  style="width: 100%" multiple>
								<!-- <option value ="Main,Guest">Main and Guest</option>
								<option value ="Main">Main Only</option>
								<option value ="Guest">Guest Only</option> -->
								
								<c:forEach items="${gurkkaProducts }" var="i">
									<option value="${i.id}">${i.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				<tr>
					<td>
						Promo Type<br /><select id="type" name="type" class="w200" data-rel="chosen">
											<option value="percentage">Percentage Discount (%)</option>
											<option value="cash">Cash Discount (&#8369;)</option>
										</select>
					</td>
				</tr>	
				
				</c:if>		
				<c:if test="${company.name eq 'hiprecisiononlinestore'}">
				<tr>
					<td>
						Promo Name<br /><input type="text" id="code" name="promoCode.promoName" value="" class="w200">
					</td>
				</tr>	
				</c:if>		
				<tr>
					<td>
						Discount<br /><input type="text" id="discount" name="promoCode.discount" value="" class="w200 positive"><c:if test="${company.name ne 'gurkkatest' }">%</c:if>
					</td>
				</tr>	
				<c:if test="${company.name eq 'gurkkatest'}">
					
					<tr>
						<td>
							Apply <br>
							<select name="promoCode.promoSpecs"  data-rel="chosen"  style="width: 100%">
								<option value ="BY_BRAND">BY BRAND</option>
								<option value ="BY_TOTAL">BY TOTAL</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>
							Minimum Order Requirement <br>
						
							<input id="promo_req" name="promoCode.minimumRequirement" value="" class="w100 positive" type="number" min="0" step="1" pattern="[0-9]">
							<select name="promoCode.minimumRequirementUnit" data-rel="chosen">
								<option value="PESOS">PESOS</option>
								<option value="BOTTLES">BOTTLES/BUNDLES</option>
							</select>
						</td>
					</tr>	
					<tr>
						<td>
							Membership Level<br>
							<select name="promoCode.membershipLevel" class="w200" data-rel="chosen">
								<option value="ALL">ALL</option>
								<option value="ORDINARY">ORDINARY</option>
								<option value="BRONZE">BRONZE</option>
								<option value="SILVER">SILVER</option>
								<option value="GOLD">GOLD</option>
								<option value="TITANIUM">TITANIUM</option>
								<option value="PLATINUM">PLATINUM</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Maximum number of times to use<br>
							<input  name="promoCode.maxUsage" type="number" min="1" step="1" pattern="[0-9]" class="w200 positive"/>
						</td>
					</tr>
					
					
				</c:if>			
				<tr>
					<td>
						From Date<br />
						<fmt:formatDate pattern="MM-dd-yyyy" value="${promoCode.fromDate}" var="ed"/>
						<input type="text" id="fromDate" name="fromDate" value="${ed}" class="w200" readonly="readonly"/> 
						<a href="javascript:;" id="fromDateButton"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
						<script type="text/javascript">
						    Calendar.setup({
					        	inputField     :    "fromDate",     // id of the input field
				        		ifFormat       :    "%m-%d-%Y",      // format of the input field
				        		button         :    "fromDateButton",  // trigger for the calendar (button ID)
				        		align          :    "Tl",           // alignment (defaults to "Bl")
				        		singleClick    :    true
					    	});
						</script>
					</td>
				</tr>	
				<tr>
					<td>
						To Date<br />
						<fmt:formatDate pattern="MM-dd-yyyy" value="${promoCode.toDate}" var="ed"/>
						<input type="text" id="toDate" name="toDate" value="${ed}" class="w200" readonly="readonly"/> 
						<a href="javascript:;" id="toDateButton"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
						<script type="text/javascript">
				    		Calendar.setup({
				        		inputField     :    "toDate",     // id of the input field
				        		ifFormat       :    "%m-%d-%Y",      // format of the input field
				        		button         :    "toDateButton",  // trigger for the calendar (button ID)
				        		align          :    "Tl",           // alignment (defaults to "Bl")
				        		singleClick    :    true
				    		});
						</script>
					</td>
				</tr>
				
				<tr>
					<input type="hidden" name="promoCode.isDisabled" value="false"/>
					<td <c:if test="${company.name eq 'gurkkatest'}">style="display:none;"</c:if>>Note<br /><textarea id="note" name="promoCode.note" cols="41" rows="5" class="w200">${promoCode.note}</textarea></td>
				</tr>
				
				<c:if test="${company.name eq 'hiprecisiononlinestore' }">
				<tr>
					<td colspan="2">Items &nbsp;
					<input type="hidden" id="items" name="promoCode.items" class="w200" value="${promoCode.items}">
					<div>
						<select id="item-select" class="chosen-select">
							<option value="">-- Please Select --</option>
							<option value="AllItems">All Items</option>
							<c:forEach items="${categoryItems }" var="item">
								<option value="${item.id }">${item.name }</option>
							</c:forEach>
						</select><input type="button" value="Add" class="btnBlue" onclick="addItem($('select#item-select option').filter(':selected').val(),$('select#item-select option').filter(':selected').text());">
					</div>
					<div>
						<table>
						<tbody id="items-container">
						</tbody>
					</table>
					</td>
				</tr>
				</c:if>
				<tr>
					<td>
						<c:choose>
							<c:when test="${company.name eq 'hiprecisiononlinestore' }">
								<br/><input type="submit" name="submit" value="Create" class="btnBlue">
							</c:when>
							<c:otherwise>
								<input type="submit" name="submit" value="Add" class="btnBlue">
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</form>
		
		</div><!--//sidenav-->
	</c:if>
</div>
<script src="javascripts/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".chosen-select").chosen({width: "40%"});
});
</script>	
<div class="clear"></div>
</div><!--//container-->

				<script>
			 		function changePromoCodeFor(forName) {
			 		    if(forName == 'forProduct'){
			 		    	$('#btnForProduct').addClass('active');
			 		    	$('#btnForShippingFee').removeClass('active');
			 		    	$('#forProduct').css('display','');
			 		    	$('#forShippingFee').css('display','none');
			 		    	$('#promo_for').val('forproduct');
			 		    }
			 		   if(forName == 'forShippingFee'){
			 		    	$('#btnForProduct').removeClass('active');
			 		    	$('#btnForShippingFee').addClass('active');
			 		    	$('#forProduct').css('display','none');
			 		    	$('#forShippingFee').css('display','');
			 		    	$('#promo_for').val('forshippingfee');
			 		    }
			 		}
		 		</script>
</body>
</html>