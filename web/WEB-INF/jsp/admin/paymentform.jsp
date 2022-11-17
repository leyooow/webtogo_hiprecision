<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />

<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../admin/javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../admin/javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../admin/javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../admin/javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="events" />

<script>

	function submitForm(myForm) {
		var eventDate = getElement('event_date');
		var title =  getElement('event_title');
		var detail =  getElement('event_detail');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(eventDate.length == 0) {
			error = true;
			messages += " * Event date was not specified. \n";
		} 
		
		if(title.length == 0) {
			error = true;
			messages += " * Event title was not specified. \n";
		} 
		
		if(detail.length == 0) {
			error = true;
			messages += " * Event detail was not specified. \n";
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
	
</script> 

<div style="width:50px;">
<table>
	<tr>
		<td style="border:0px;">
		
		<form name="newPaymentForm" method="post" action="savenewpayment.do" >
		<input type="hidden" name="billing_id" value="${billing.id }"/>
		<table width="50%">
			<tr>
				<td colspan="3"></td>
			</tr>
			<tr class="headbox">
				<th colspan="3">New Payment for Billing Ref No. ${billing.id}</th>
			</tr>

<%--			<tr>
				<td width="1%" nowrap>Billing Status</td>
				<td width="10px"></td>
				<td>
			
				<select id="billingstatus" name="billing.status.statusName" class="combobox1">
					<option value="">- Select Billing Status -</option>
					<c:forEach items="${billingStatuses}" var="status">
						<option value="${status}">${status.value}</option>
					</c:forEach>
				</select>
				
				</td>
			</tr>
--%>			
			<tr>
				<td width="1%" nowrap>Payment Type</td>
				<td width="10px"></td>
				<td>
				<select id="paymenttype" name="type" class="combobox1">
					<option value="">- Select Payment Type -</option>
					<c:forEach items="${paymentTypes}" var="type">
						<option value="${type.typeCode}">${type.value}</option>
					</c:forEach>
				</select>
				</td>
			</tr>	
			<tr>
				<td width="1%" nowrap>OR No.</td>
				<td width="10px"></td>
				<td><input type="text" id="orNumber" name="payment.orNumber" value="${payment.orNumber}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Bank</td>
				<td width="10px"></td>
				<td><input type="text" id="bank" name="payment.bank" value="${payment.bank}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Check No.</td>
				<td width="10px"></td>
				<td><input type="text" id="checkNumber" name="payment.checkNumber" value="${payment.checkNumber}" class="textbox3"></td>
			</tr>		
			<tr>
				<td width="1%" nowrap>Check Date</td>
				<td width="10px"></td>
				<td width>
				<fmt:formatDate pattern="MM-dd-yyyy" value="${payment.checkDate}" var="ed"/>
								 
				<input type="text" id="check_date" name="checkDate" value="${ed}" class="textbox3" readonly="readonly"/> 
				  </td>
				 <td> 
				<a href="javascript:;" id="check_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "check_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y",      // format of the input field
				        button         :    "check_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true
				    });
				</script>
				</td>
			</tr>			
			<tr>
				<td width="1%" nowrap>Received By</td>
				<td width="10px"></td>
				<td><input type="text" id="receivedBy" name="payment.receivedBy" value="${payment.receivedBy}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Paid Amount</td>
				<td width="10px"></td>
				<td><input type="text" id="paidamount" name="payment.paidAmount" value="${payment.paidAmount}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Payment Date</td>
				<td width="10px"></td>
				<td width>
				<fmt:formatDate pattern="MM-dd-yyyy" value="${payment.paymentDate}" var="ed"/>
								 
				<input type="text" id="payment_date" name="paymentDate" value="${ed}" class="textbox3" readonly="readonly"/> 
				  </td>
				 <td> 
				<a href="javascript:;" id="payment_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "payment_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y",      // format of the input field
				        button         :    "payment_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true
				    });
				</script>
				</td>
			</tr>
			<tr>
				<td width="1%" nowrap>Note</td>
				<td width="10px"></td>
				<td><textarea id="note" name="payment.note" cols="41" rows="5" class="inputFields">${payment.note}</textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
					<input type="submit" name="submit" value="Add New" class="button1">
					&nbsp;
					<input type="button" name="cancel" value="Cancel" class="button1" onclick="window.location='payment.do'">
				
				</td>
			</tr>
		</table>
		</form>
		
		</td>
	</tr>
</table>
</div>