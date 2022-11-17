<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />



<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../admin/javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="events" />


<script>
	function validate(){
		var remarks = document.getElementById('remarks').value;

		if(remarks.length == 0)
			alert("Remarks must be given");
		else{
			var myForm = document.saveBillingForm;
			myForm.submit();
		}
			
		
	}
	function submitForm(myForm) {
		
		//var status = getElement('billingstatus');
		var type = getElement('billingtype');
		var fromdate = getElement('fromdate');
		var todate = getElement('todate');
		var duedate = getElement('duedate');
		var amountdue = getElement('amountdue');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
//		if(billingstatus.length == 0) {
//			messages += "* Status not given\n";
//			error = true;
//		}
		
		if(billingtype.length == 0) {
			messages += "* Type not given\n";
			error = true;
		}
		
		if(fromdate.length == 0) {
			messages += "* From Date not given\n";
			error = true;
		}
		
		if(todate.length == 0) {
			messages += "* To Date not given\n";
			error = true;
		}
		
		if(duedate.length == 0) {
			messages += "* Due Date not given\n";
			error = true;
		}
		
		if(amountdue.length == 0) {
			messages += "* Amount Due not given\n";
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
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
</script>

<div style="width:50px;">
<table>
	<tr>
		<td style="border:0px;">
		<c:if  test="${mode eq 'add'}">
			<form name="newBillingForm" method="post" action="savenewbilling.do" onsubmit="return submitForm(this);">
		</c:if>
		<c:if  test="${mode eq 'edit'}">
			<form name="saveBillingForm" method="post" action="savebillingform.do" onsubmit="return submitForm(this);">
			<input type="hidden" name="billing_id" value="${billing.id }"/>
		</c:if>
		<table width="50%">
			<tr>
				<td colspan="3"></td>
			</tr>
			<tr class="headbox">
				<th colspan="3">Billing Info</th>
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
				<td width="1%" nowrap>Billing Type</td>
				<td width="10px"></td>
				<td>
				<select id="billingtype" name="type" class="combobox1">
					<option value="">- Select Billing Type -</option>
					<c:forEach items="${billingTypes}" var="type">
						<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
						<option value="${type.typeCode}" ${(type.typeCode == billing.type.typeCode) ? "selected" : "" }>${type.value}</option>
					</c:forEach>
				</select>
				
				</td>
			</tr>			

<%--			<tr>
				<td width="1%" nowrap>Billing Status</td>
				<td width="10px"></td>
				<td>
				<select id="billingstat" name="stat" class="combobox1">
					<option value="">- Select Billing Status -</option>
					<c:forEach items="${billingStatuses}" var="status">
						<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
<%--						<option value="${status.statusCode}" ${(status.statusCode == billing.status.statusCode) ? "selected" : "" }>${status.value}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
--%>
			<tr>
				<td width="1%" nowrap>From Date</td>
				<td width="10px"></td>
				<td width>
				<fmt:formatDate pattern="MM-dd-yyyy" value="${billing.fromDate}" var="ed"/>
								 
				<input type="text" id="from_date" name="fromDate" value="${ed}" class="textbox3" readonly="readonly"/> 
				  </td>
				 <td> 
				<a href="javascript:;" id="from_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "from_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y",      // format of the input field
				        button         :    "from_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true
				    });
				</script>
				</td>
			</tr>	
			<tr>
				<td width="1%" nowrap>To Date</td>
				<td width="10px"></td>
				<td width>
				<fmt:formatDate pattern="MM-dd-yyyy" value="${billing.toDate}" var="ed"/>
								 
				<input type="text" id="to_date" name="toDate" value="${ed}" class="textbox3" readonly="readonly"/> 
				  </td>
				 <td> 
				<a href="javascript:;" id="to_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "to_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y",      // format of the input field
				        button         :    "to_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true
				    });
				</script>
				</td>
			</tr>			
			<tr>
				<td width="1%" nowrap>Due Date</td>
				<td width="10px"></td>
				<td width>
				<fmt:formatDate pattern="MM-dd-yyyy" value="${billing.dueDate}" var="ed"/>
								 
				<input type="text" id="due_date" name="dueDate" value="${ed}" class="textbox3" readonly="readonly"/> 
				  </td>
				 <td> 
				<a href="javascript:;" id="due_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "due_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y",      // format of the input field
				        button         :    "due_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true
				    });
				</script>
				</td>
			</tr>			
			<tr>
				<td width="1%" nowrap>Amount</td>
				<td width="10px"></td>
				<td><input type="text" id="amountdue" name="billing.amountDue" value="${billing.amountDue}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Note</td>
				<td width="10px"></td>

				<td><textarea id="note" name="billing.note" cols="41" rows="5" class="inputFields">${billing.note}</textarea></td>
			</tr>
			<c:if test="${mode eq 'edit'}">
				<tr>
					<td width="1%" nowrap>Remarks</td>
					<td width="10px"></td>
	
					<td><textarea id="remarks" name="remarks" cols="41" rows="5" class="inputFields"></textarea></td>
				</tr>
			</c:if>
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
					<c:if  test="${mode eq 'add'}">
						<input type="submit" name="submit" value="Add New" class="button1">
					</c:if>
					<c:if  test="${mode eq 'edit'}">
						<input type="button" name="button" value="Save" class="button1" onclick ="validate()">
					</c:if>
					&nbsp;
					<input type="button" name="cancel" value="Cancel" class="button1" onclick="window.location='billing.do'">
				
				</td>
			</tr>
		</table>
		</form>
		
		</td>
	</tr>
</table>
</div>