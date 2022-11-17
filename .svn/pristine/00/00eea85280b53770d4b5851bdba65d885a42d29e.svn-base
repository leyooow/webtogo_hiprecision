<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="account" />
<c:set var="submenu" value="payment"/>
<c:if test = "${empty billing }">
	<c:set var="pagingAction" value="payment.do" />
</c:if>
<c:if test = "${not empty billing }">
	<c:set var="firstParamGiven" value="true" />
	<c:set var="pagingAction" value="payment.do?billing=${billing.id}"/>
</c:if>


<script language="JavaScript" src="../javascripts/overlib.js"></script>

<script>
function showMessage(id) {
	var content = document.getElementById('payment_'+id).innerHTML;
	overlib(content, STICKY, NOCLOSE)}
</script>

<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../admin/javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../admin/javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../admin/javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../admin/javascripts/jscalendar/calendar-setup.js"></script>


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

	    <h1><strong>Payments</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
					<tr >
						<th>Payment ID</th>
						<th >Billing ID</th>
						<th>type</th>
						<th >Paid Amount</th>
						<th >Date Paid</th>
						<th >Or No.</th>
						<th >Bank</th>
						<th >Check No.</th>
						<th >Check Date</th>
						<th >Rec'd By</th>
						<th >Note</th>
						<th >Action</th>
					</tr>
						<c:set var="count" value="0" />
					<c:forEach items="${payments}" var="payment">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
							<td>${payment.id}</td>
							<td>${payment.billing.id}</td>
							<td>${payment.type}</td>
							<td style="text-align: right;">${payment.paidAmount}</td>
                            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${payment.paymentDate}" /></td>
							<td>${payment.orNumber}</td>
							<td>${payment.bank}</td>
							<td>${payment.checkNumber}</td>
                            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${payment.checkDate}" /></td>
							<td>${payment.receivedBy}</td>							
						<%--  	
							<td>${payment.note}</td>
						--%>
							<td>
								<c:if test="${not empty payment.note }">
								 <%-- <div align="center"><a href="javascript:void(0);" onmouseover="return overlib('text here).', STICKY, MOUSEOFF);" onmouseout="return nd();">here</a></div> --%>
								
								   <div align="center"><a href="javascript:void(0);" onmouseover="showMessage(${payment.id});" onmouseout="return nd();"><img style="cursor:pointer;" src="images/note.jpg"></a> </div> 
								
								<%--	<div align="center"><img style="cursor:pointer;" src="images/note.jpg" onmouseover="return overlib(${payment.note}, STICKY, MOUSEOFF);" onmouseout="return nd();"></div>
								 --%>
								 	<div id="payment_${payment.id}" style="display: none;">${payment.note}</div>
								</c:if>	
							</td>					
							<td>
								<%-- <a href="editpayment.do?billing_id=${payment.id}">Edit</a> |   --%>
								<a href="deletepayment.do?payment_id=${payment.id}">Cancel</a>								 
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
			
	 <h2>New Payment</h2>
	<form name="newPaymentForm" method="post" action="savenewpayment.do" >
		<input type="hidden" name="billing_id" value="${billing.id }"/>
		<table width="100">
			
		
			<tr>
				<td >Payment Type<br />
				<select id="paymenttype" name="type" class="w200">
					<option value="">- Select Payment Type -</option>
					<c:forEach items="${paymentTypes}" var="type">
						<option value="${type.typeCode}">${type.value}</option>
					</c:forEach>
				</select>
				</td>
			</tr>	
			<tr>
				<td>OR No.<br /><input type="text" id="orNumber" name="payment.orNumber" value="${payment.orNumber}" class="w200"></td>
			</tr>
			<tr>
				<td>Bank
<br /><input type="text" id="bank" name="payment.bank" value="${payment.bank}" class="w200"></td>
			</tr>
			<tr>
				<td>Check No.<br /><input type="text" id="checkNumber" name="payment.checkNumber" value="${payment.checkNumber}" class="w200"></td>
			</tr>		
			<tr>
				<td>Check Date<br />
				<fmt:formatDate pattern="MM-dd-yyyy" value="${payment.checkDate}" var="ed"/>
								 
				<input type="text" id="check_date" name="checkDate" value="${ed}" class="w200" readonly="readonly"/> 
				 
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
				<td>Received By<br /><input type="text" id="receivedBy" name="payment.receivedBy" value="${payment.receivedBy}" class="w200"></td>
			</tr>
			<tr>
				<td>Paid Amount<br /><input type="text" id="paidamount" name="payment.paidAmount" value="${payment.paidAmount}" class="w200"></td>
			</tr>
			<tr>
				<td >Payment Date<br />
				<fmt:formatDate pattern="MM-dd-yyyy" value="${payment.paymentDate}" var="ed"/>
								 
				<input type="text" id="payment_date" name="paymentDate" value="${ed}" class="w200" readonly="readonly"/> 
				 
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
				<td >Note<br /><textarea id="note" name="payment.note" cols="41" rows="5" class="w200">${payment.note}</textarea></td>
			</tr>
			<tr>
				
				<td >
				
					<input type="submit" name="submit" value="Add New" class="btnBlue">
				
					<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='payment.do'">
				
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