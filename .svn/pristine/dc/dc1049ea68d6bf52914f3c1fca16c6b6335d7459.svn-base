<%@include file="includes/header.jsp"  %>

<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<c:set var="menu" value="promocode" />

<c:set var="pagingAction" value="promocode.do" />
<c:set var="mode" value="edit"/>

<link rel="stylesheet" href="css/chosen.css">

<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<!-- main calendar program -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
<!-- language for the calendar -->
<script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
<!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>
<script language="javascript" src="../javascripts/overlib.js"></script>
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
		var note = getElement('note');
		
		var error = false;
		var messages = 'Problem(s) occured: \n\n';
		
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
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		return value;
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
		
		if(items == "AllItems" || items == "'AllItems'") {
			alert("You already selected All items");
			return false;
		}
		
		if(value == "All Items") {
			$('#items-container').html("");
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
</script> 

<body>
<div class="container">
<%@include file="includes/bluetop.jsp"%>
<%@include file="includes/bluenav.jsp"%>

<div class="contentWrapper" id="contentWrapper">
	<div class="content">
		<s:actionmessage />
		<s:actionerror />
				
	  	<div class="pageTitle">
	    	<h1><strong>Promo Code</strong>: Edit Promo Code</h1>
					
			<div class="clear"></div>
	  	</div><!--//pageTitle-->
	  	
	 	<form method="post" action="savepromocodeform.do" onsubmit="return submitForm(this);">
			<input type="hidden" name="promoCodeId" value="${promoCode.id}">
			<input type="hidden" name="promoCode.id" value="${promoCode.id}">
			
			<table width="100%">
				<tr>
					<td width="1%" nowrap>Promo Code</td>
					<td width="10px"></td>
					<td><input type="text" id="code" name="promoCode.code" class="textbox3" value="${promoCode.code}"></td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				
				<c:if test="${company.name eq 'gurkkatest'}">
				<tr>
					<td width="1%">Promo For</td>
					<td width="10px"></td>
					<td>
						<select id="promo_for" name="promoCode.promoFor" data-rel="chosen">
							<option value="forproduct" ${promoCode.promoFor eq 'forproduct' or promoCode.promoFor eq null ? 'selected' : ''}>PRODUCT</option>
							<option value="forshippingfee" ${promoCode.promoFor eq 'forshippingfee' ? 'selected' : ''}>SHIPPING FEE</option>
						</select>
					</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>	
				<tr>
					<td>
						Brands 
					</td>
					<td width="10px"></td>
					<td>
						<select name="promoCode.brand" style="width: 200px;" data-rel="chosen" multiple>
							<%-- <option value ="Main,Guest" ${promoCode.brand eq 'Main,Guest' ? 'selected' : ''}>Main and Guest</option>
							<option value ="Main" ${promoCode.brand eq 'Main' ? 'selected' : ''}>Main Only</option>
							<option value ="Guest" ${promoCode.brand eq 'Guest' ? 'selected' : ''}>Guest Only</option> --%>
							<c:forEach items="${gurkkaProducts }" var="i">
								<c:set var="expr" value=",${i.id},"/>
								<option value="${i.id}" ${fn:contains(promoCode.brand,expr) ? 'selected' : ''}>${i.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr>
					<td width="1%">Promo Type</td>
					<td width="10px"></td>
					<td>
						<select id="type" name="type" data-rel="chosen">
							<option value="percentage" ${promoCode.promoType eq 'percentage' ? 'selected' : ''}>Percentage Discount (%)</option>
							<option value="cash" ${promoCode.promoType eq 'cash' ? 'selected' : ''}>Cash Discount (&#8369;)</option>
						</select>
					</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>	
				
				
					
					
				</c:if>		
				
				<c:if test="${company.name eq 'hiprecisiononlinestore' }">
				<tr>
					<td width="1%" nowrap>Promo Name</td>
					<td width="10px"></td>
					<td><input type="text" id="code" name="promoCode.promoName" class="textbox3" value="${promoCode.promoName}"></td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				</c:if>
				<tr>
					<td width="1%" nowrap>Discount</td>
					<td width="10px"></td>
					<td><input type="text" id="discount" name="promoCode.discount" class="textbox3 positive" value="${promoCode.discount}"></td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				
				<c:if test="${company.name eq 'gurkkatest'}">
					
					<tr>
						<td>
							Apply
						</td>
						<td width="10px"></td>
						<td>
							<select name="promoCode.promoSpecs"  data-rel="chosen"  style="width: 200px">
								<option value ="BY_BRAND" ${promoCode.promoSpecs eq 'BY_BRAND' ? 'selected' : ''}>BY BRAND</option>
								<option value ="BY_TOTAL" ${promoCode.promoSpecs eq 'BY_TOTAL' ? 'selected' : ''}>BY TOTAL</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>
							Minimum Order Requirement 
						</td>
						<td width="10px"></td>
						<td>
							<input id="promo_req" name="promoCode.minimumRequirement" value="${ promoCode.minimumRequirement }" class="w100 positive" type="number" min="0" step="1" pattern="[0-9]">
							<select name="promoCode.minimumRequirementUnit" data-rel="chosen">
								<option value="PESOS" ${promoCode.minimumRequirementUnit eq 'PESOS' ? 'selected' : '' }>PESOS</option>
								<option value="BOTTLES" ${promoCode.minimumRequirementUnit eq 'BOTTLES' ? 'selected' : '' }>BOTTLES/BUNDLES</option>
							</select>
						</td>
					</tr>	
					<tr>
						<td>
							Membership Level
						</td>
						<td width="10px"></td>
						<td>
							<select name="promoCode.membershipLevel" class="w200" data-rel="chosen">
								<option value="ALL">ALL</option>
								<c:forEach items="<%= gurkka.cms.utils.GurkkaMemberUtil.MemberType.values() %>" var="i">
									<option ${i.stringValue eq promoCode.membershipLevel ? 'selected' : ''}>${i}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Maximum number of times to use
						</td>
						<td width="10px"></td>
						<td>
							<input  value="${promoCode.maxUsage }" name="promoCode.maxUsage" type="number" min="1" step="1" pattern="[0-9]" class="w200 positive"/>
						</td>
					</tr>
					
					
					
				</c:if>	
				
				<c:if test="${company.name ne 'gurkkatest'}">
				<tr>
					<td width="1%" nowrap>Minimum</td>
					<td width="10px"></td>
					<td><input type="text" id="promoName" name="promoCode.promoName" class="textbox3" value="${promoCode.promoName}"></td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				</c:if>
				
				<tr>
					<td width="1%" nowrap>From Date</td>
					<td width="10px"></td>
					<td nowrap>
						<joda:format pattern="MM-dd-yyyy" value="${promoCode.fromDate}" var="ed"/>
								 	
						<input type="text" id="fromDate" name="fromDate" value="${ed}" class="textbox3" readonly="readonly"/> 
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
					<td width="1%" nowrap>To Date</td>
					<td width="10px"></td>
					<td nowrap>
						<joda:format pattern="MM-dd-yyyy" value="${promoCode.toDate}" var="ed"/>
								 	
						<input type="text" id="toDate" name="toDate" value="${ed}" class="textbox3" readonly="readonly"/> 
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
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				<tr>
					<td width="1%" nowrap>Disabled?</td>
					<td width="10px"></td>
					<td>
						<select id="disabled" name="promoCode.isDisabled">
							<option value="false" <c:if test="${not promoCode.isDisabled}">selected</c:if>>No</option>
							<option value="true" <c:if test="${promoCode.isDisabled}">selected</c:if>>Yes</option>
						</select>
					</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				
				<c:if test="${company.name ne 'gurkkatest' }">
				<tr>
					<td width="1%" nowrap>Note</td>
					<td width="10px"></td>
					<td><textarea id="note" name="promoCode.note" class="textbox3">${promoCode.note}</textarea></td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				</c:if>
				
				<c:if test="${company.name eq 'hiprecisiononlinestore' }">
				<tr>
					<td valign="top" width="1%" nowrap style="padding-top: 12px;">Items</td>
					<td width="10px"></td>
					<td><input type="hidden" id="items" name="promoCode.items" class="textbox3" value="${promoCode.items}">
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
							<c:if test="${fn:containsIgnoreCase(promoCode.items, 'AllItems') }">
							<tr id="item-AllItems">
								<td>All Items</td>
								<td>
									<div style="display:none">
										<input type="checkbox" class="child-promo" checked="" value="'AllItems'">
									</div>
									<input type="button" value="Remove" class="btnBlue" onclick="removeItem('AllItems')">
								</td>
							</tr>
							</c:if>
							<c:forTokens items="${promoCode.items}" delims="==" var="item">
								<c:forEach items="${categoryItems }" var="cI">
									<c:set var="itemId" value="'${cI.id }'"/>
									<c:set var="itemStr" value="'${item }'"/>
									<c:if test="${itemStr eq itemId }">
										<tr id="item-${cI.id }">
											<td>${cI.name }</td>
											<td>
												<div style="display:none"><input type="checkbox" class="child-promo" checked value="${cI.id }"></div>
												<input type="button" value="Remove" class="btnBlue" onclick="removeItem(${cI.id })">
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forTokens>
						</tbody>
						</table>
					</div>
					</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				</c:if>
				<tr>
					<td colspan="2" style="border: 0px;"></td>
					<td style="border: 0px;">
						<br/><input type="submit" value="Update" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='promocode.do'" class="btnBlue">
					</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
		</table>
		</form>
	</div><!--//mainContent-->
</div>
<script src="javascripts/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#pageModuleUsername').focus()
	$('#proceed-gem-form').click(function(){
		$('#gem-intro').hide();
		$('#gem-form').show();
	});
	
	$(".chosen-select").chosen({width: "40%"});
});
</script>
<div class="clear"></div>
</div><!--//container-->
</body>
</html>