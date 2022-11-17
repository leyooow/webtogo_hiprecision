<script language="javascript">
	function showLightBox(var light,var len){
		alert("SAMPLE..............")
		return false;
	}
</script>
<script src="../admin/javascripts/tooltip.js" type="text/javascript"></script>

<c:if test="${fn:length(receiptList) ge 1}">

<table width="100%" class="companiesTable">

<tr>
	<td width="70%" valign="left"><h3>These are the list of all uploaded receipts. &nbsp;&nbsp;&nbsp;Total GC: &nbsp;&nbsp;>>&nbsp; 
	<c:choose>
	<c:when test="${membersTotalPoint!=null}">
		${membersTotalPoint}
	</c:when>
	<c:otherwise>
		0.00
	</c:otherwise>
	</c:choose>
	 </h3></td>
	<td valign="right"><h3>
	<c:if test="${company.name =='apc' }">
		Distributor:  &nbsp;&nbsp;  MSI-ESC Philippines Inc.
	</c:if>
	</h3></td>
</tr>
</table>
<table width="100%"  class="companiesTable">
<tr>
<th><b>Title</b></th>
<th><b>Invoice#</b></th>
<th><b>Remarks</b></th>
<th><b>&nbsp</b></th>
<th><b>Notes</b></th>
<th><b> GC </b></th>
<th><b>Created On</b></th>
<th><b>Action By</b></th>
<th><b>Approved Date</b></th>
<th><b>Status</b></th>
<th><b>Action</b></th>
</tr>
<c:forEach items="${receiptList}" var="receiptList">
	<tr>
		<td  valign="top">${receiptList.filename}</td>
		<td>${receiptList.invoiceNumber}</td>
		<td>
		<a href="/" onclick='return false;'
				onmouseout="UnTip()"
				onmouseover='Tip("<h1>${receiptList.remarks}</h1>")' >
				remarks
				</a>
		</td>
		
		<td  valign="top">
		<c:if test="${company.name =='apc' }">
			<a href="http://apc-asap.com/images/ImagesReceipts/${receiptList.original}" target="_blank">view Receipt</a>
		</c:if>
		<c:if test="${company.name =='westerndigital' }">
			<a href="http://wdeals.com.ph//images/ImagesReceipts/${receiptList.original}" target="_blank">view Receipt</a>
		</c:if>
		</td>
		<td td   valign="top">
			<a href="/"  onclick='return false;'
				onmouseout="UnTip()"
				onmouseover='Tip("<h1>Description:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h1>${receiptList.description}")' >
			<strong>Items</strong></a>
			</td>
		<td>
		${receiptList.value}</td>
		<td td  width="10%"  valign="top">${receiptList.createdOn}</td>
		<td style="text-align: center">&nbsp;${receiptList.memberFile.approvedBy}</td>
	<td>&nbsp;${receiptList.memberFile.approvedDate}</td>
		
	<td><B>${receiptList.memberFile.status}</B></td>
	<td>
	<c:choose>
		<c:when  test="${receiptList.memberFile.status == 'Waiting to be Redeemed'}">
		</c:when>
		<c:when  test="${receiptList.memberFile.status == 'REDEEMED'}">
		</c:when>
		<c:when  test="${receiptList.memberFile.status == 'Approved'}">
			<a href='editmember.do?member_id=${member2.id}&cancel_id=${receiptList.id}'><font color="red"  onclick="return confirm('Do you really want to cancel this Receipt?')";>Cancel</font></a>&nbsp;
		</c:when>
		<c:otherwise>
			<a href='editmember.do?member_id=${member2.id}&approve_id=${receiptList.id}'>Approve</a><br>
			<a href='editmember.do?member_id=${member2.id}&reject_id=${receiptList.id}'  onclick="return confirm('Do you really want to Reject this Receipt?');"><font color="red">Reject</font></a>
		</c:otherwise>
	</c:choose>
		</td>
	</tr>
</c:forEach>
</table>
</c:if>
<c:if test="${fn:length(receiptList) eq 0}">
	<h3>No Uploaded Receipt</h3>
	
	<%-- FOR WESTERN DIGITAL ONLY --%>
	<c:if test="${company.id eq 243}">
		<div style="float:left; margin-right: 5px;">
			<form action="uploadMemberReceipt.do" name="" method="post">
				<input type="hidden" name="member_id" value="${(member ne null) ? member.id : member2.id}">
				<input type="hidden" name="group_id" value="221">
				<input type="submit" name="uploadreceipt" class="btnBlue" value="Upload Receipt">
			</form>
		</div>
	</c:if>
	<%-- FOR APC ONLY --%>
	<c:if test="${company.id eq 222}">
		<div style="float:left; margin-right: 5px;">
			<form action="uploadMemberReceipt.do" name="" method="post">
				<input type="hidden" name="member_id" value="${(member ne null) ? member.id : member2.id}">
				<input type="hidden" name="group_id" value="212">
				<input type="submit" name="uploadreceipt" class="btnBlue" value="Upload Receipt">
			</form>
		</div>
	</c:if>
</c:if>
