<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="account" />
<c:set var="submenu" value="billing"/>
<c:set var="pagingAction" value="billing.do" />
<c:set var="mode" value="add"/>

<script language="JavaScript" src="../javascripts/overlib.js"></script>

<script>
function showMessage(id) {
	var content = document.getElementById('billing_'+id).innerHTML;
	overlib(content, STICKY, NOCLOSE)}
</script>
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
<!-- calendar stylesheet -->
 <!-- calendar stylesheet -->
  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>
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

	    <h1><strong>Billing</strong>: View company billing</h1>
	</form>
		<ul>[
			<li>
							<a href="downloadBillingSummary.do">Download Company Billing(s)</a>
					
						</li>
			]		
		</ul>				
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
				
					
					<tr>
						<th>Ref No</th>
						<th >Status</th>
						<th >Type</th>
						<th >From Date</th>
						<th >To Date</th>				
					<!-- remove "Due Date" column 	
						<th width="8%">Due Date</th>
					  -->		
				  	  				
						<th >Amount</th>
						<th >Note</th>
						<th >Action</th>
					</tr>
					<c:set var="count" value="0" />
					<c:forEach items="${billings}" var="billing">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						
							<td>
								<c:if test="${billing.status.statusName ne 'Paid'}"><a href="editbilling.do?id=${billing.id}">${billing.id }</a></c:if>
								<c:if test="${billing.status.statusName eq 'Paid'}">${billing.id}</c:if>
							</td>
							<td>${billing.status.statusName}</td>
							<td>${billing.type}</td>
                            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${billing.fromDate}" /></td>
                            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${billing.toDate}" /></td>
                       <%-- remove "Due Date" column
                            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${billing.dueDate}" /></td>
						 --%>
							<td style="text-align: right;">${billing.amountDue}</td>
						<%-- 	<td style="text-align: right;">${billing.totalPayments}</td>  --%>
<%--							<c:if test="${!empty billing.payment }">
								<td>
								<c:forEach items="${billing.payment}" var="pay">
									<a href="payment.do?billing_id=${billing.id}">${pay.id}</a>
								</c:forEach>
								</td>
							</c:if>
						    <c:if test="${empty billing.payment }">
								<td>&nbsp;</td>
							</c:if>
--%>							

						  	<%-- 
							<td>${billing.note}</td>
							 --%>
							<td>
								<c:if test="${not empty billing.note }">
									<div align="center"><img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage(${billing.id});"></div>
									<div id="billing_${billing.id}" style="display: none;">${billing.note}</div>
								</c:if>	
							</td>
							<td>
								<c:if test="${!empty billing.payment}">
									<a href="payment.do?billing_id=${billing.id}">View/Add Payment(s)</a> |
								
								
								</c:if>
								
								<c:if test="${empty billing.payment and billing.status.statusName ne 'Cancelled' }">
									<a href="payment.do?billing_id=${billing.id }">Add Payment</a> | 
									<a href="deletebilling.do?billing_id=${billing.id}">Cancel</a> |
								</c:if>
									<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
								<a href="downloadPdf.do?billing_id=${billing.id}">PDF</a> 
									</c:if>									
								<c:if test="${billing.status.statusName eq 'Cancelled'}">
									&nbsp; 
								</c:if>
							</td>
						</tr>
					</c:forEach>
					
				
				</table> 
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
		
	 <h2>New Billing</h2>
		
		<c:if  test="${mode eq 'add'}">
			<form name="newBillingForm" method="post" action="savenewbilling.do" onsubmit="return submitForm(this);">
		</c:if>
		<c:if  test="${mode eq 'edit'}">
			<form name="saveBillingForm" method="post" action="savebillingform.do" onsubmit="return submitForm(this);">
			<input type="hidden" name="billing_id" value="${billing.id }"/>
		</c:if>
		
		<table width="100%">
			
			


			<tr>
				<td >Billing Type<br />
				<select id="billingtype" name="type" class="w200">
					<option value="">- Select Billing Type -</option>
					<c:forEach items="${billingTypes}" var="type">
						<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
						<option value="${type.typeCode}" ${(type.typeCode == billing.type.typeCode) ? "selected" : "" }>${type.value}</option>
					</c:forEach>
				</select>
				
				</td>
			</tr>			

			<tr>
				<td >From Date<br />
				<fmt:formatDate pattern="MM-dd-yyyy" value="${billing.fromDate}" var="ed"/>
								 
				<input type="text" id="from_date" name="fromDate" value="${ed}" class="w200" readonly="readonly"/> 
				 
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
				<td>To Date
				
				<br />
				<fmt:formatDate pattern="MM-dd-yyyy" value="${billing.toDate}" var="ed"/>
								 
				<input type="text" id="to_date" name="toDate" value="${ed}" class="w200" readonly="readonly"/> 
				 
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
				<td >Due Date<br />
				<fmt:formatDate pattern="MM-dd-yyyy" value="${billing.dueDate}" var="ed"/>
								 
				<input type="text" id="due_date" name="dueDate" value="${ed}" class="w200" readonly="readonly"/> 
				 
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
				<td>Amount<br /><input type="text" id="amountdue" name="billing.amountDue" value="${billing.amountDue}" class="w200"></td>
			</tr>
			<tr>
				<td>Note<br /><textarea id="note" name="billing.note" cols="41" rows="5" class="w200">${billing.note}</textarea></td>
			</tr>
			<c:if test="${mode eq 'edit'}">
				<tr>
					<td >Remarks<br /><textarea id="remarks" name="remarks" cols="41" rows="5" class="w200"></textarea></td>
				</tr>
			</c:if>
			<tr>
				
				<td >
				
					<c:if  test="${mode eq 'add'}">
						<input type="submit" name="submit" value="Add New" class="btnBlue">
					</c:if>
					<c:if  test="${mode eq 'edit'}">
						<input type="button" name="button" value="Save" class="btnBlue" onclick ="validate()">
					</c:if>
					
					<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='billing.do'">
				
				</td>
			</tr>
		</table>
		</form>
		
	</div><!--//sidenav-->
	</c:if>
</div>
		
	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>