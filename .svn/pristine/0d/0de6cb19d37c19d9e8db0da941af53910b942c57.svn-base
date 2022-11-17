<%@include file="includes/memberDownloadSettings.jsp"  %>
<script>
//Check all radio/check buttons script- by javascriptkit.com
//Visit JavaScript Kit (http://javascriptkit.com) for script
//Credit must stay intact for use  
function checkall(formname,checkname,thestate){
	var el_collection=eval("document.forms."+formname+"."+checkname)
	for (c=0;c<el_collection.length;c++)
		el_collection[c].checked=thestate
}
</script>


<div style="float:left;width:75%;background:white"> 
<c:if test="${company.name == 'apc'||company.name == 'westerndigital'}">
<table width="100%"   class="companiesTable" >
	<tr> 
		<th colspan="2"><h4>Rewards System Reports. </h4></th>
	</tr>
			<tr>
				<td width="90%"><b style="font-size:12px;">Download Members File Items List in Excel Format</b> </td>
				<td> 
					<form action="downloadmemberfileitems.do" method="post">
						<c:forEach items="${listMemberFiles}" var="fieldName" >
								<input type="hidden" name="fieldName" value="${fieldName}">
						</c:forEach>
						<input type="submit" value=" Download " class="btnBlue"/>
					</form>
				</td>
			</tr>
			
			
			
			
			<tr>
				<td width="90%"><b style="font-size:12px;">Download Sales Report.</b> </td>
				<td> 
			
					<form name="fieldSelection2" method="post" action="downloadSalesForRewardsProgram.do">
						<input type="hidden" name="company_id" value="${company.id}">
							<c:forEach var="_listSalesForItem" items="${listSalesForItem}">
								<input type="hidden" name="fieldName" value="${_listSalesForItem}">
							</c:forEach>
						<input type="submit" value=" Download " class="btnBlue">
					</form>
				</td>
			</tr>
			
			

			
			
			<tr>
				<td width="90%"><b style="font-size:12px;">Download Member Information with their Order</b> </td>
				<td> 
<%
List<String> listOfOrders; 
//default settings
listOfOrders= Arrays.asList("Member Name","Order Id" , "Items Total Price","Status", "Quantity" , "Description" , "Order Date");
request.setAttribute("listOfOrders", listOfOrders);
%>
<form action="downloadAllOrderList.do" method="post">


<c:forEach items="${listOfOrders}" var="orderCaption">
	<input type="hidden" name="fieldName" value="${orderCaption}"/>
</c:forEach>
						<input type="submit" value=" Download " class="btnBlue">
					</form>
				</td>
			</tr>
			
			</TABLE>
</c:if>	



<!-- -------------------------------------------------------------- -->
<form name="fieldSelection" method="post" action="downloadCustomDetails.do">
<input type="hidden" name="company_id" value="${company.id}">
<table width="100%"  class="companiesTable" >
	<tr> 
		<th colspan="2"><h4>Select fields to download. </h4></th>
	</tr>
	<tr>
		<td class="center" colspan="2"><a href="javascript:checkall('fieldSelection','fieldName',true)">Check All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:checkall('fieldSelection','fieldName',false)">Uncheck All</a></td>
	</tr>

<c:forEach items="${list}" var="fieldName" >
	<tr>
		<td width="5%"><input type="checkbox" name="fieldName" value="${fieldName}"/></td>
		<td width="50%"><b style="font-size:12px;">${fieldName}</b></td>
	</tr>
</c:forEach>

<tr> 
		<td colspan="2"><div style="float:right;width:10%"><input type="submit" value=" Download " class="btnBlue"></div></td>
</tr>

</table>
</div>
</form>



<c:if test="${company.name =='apc' or company.name=='westerndigital' }">
<!-- MAIN FUNCTION IS to download members, their orders, and their uploaded receipts -->
	<form name="fieldSelection" method="post" action="downloadCustomDetails.do">
		<input type="hidden" name="company_id" value="${company.id}">
	</form>
</c:if>

<%--

<form name="fieldSelection2" method="post" action="downloadSalesForRewardsProgram.do">
<input type="hidden" name="company_id" value="${company.id}">
			<c:forEach var="_listSalesForItem" items="${listSalesForItem}">
				<input type="hidden" name="fieldName" value="${_listSalesForItem}">
			</c:forEach>
<input type="submit" value=" Download " class="btnBlue">
</form>
<!-- -for APC/Western -->
<form name="fieldSelection2" method="post" action="downloadSalesForRewardsProgram.do">
<input type="hidden" name="company_id" value="${company.id}">
<input type="hidden" name="viewSalesReport">
<div style="float:left;width:70%;background:white"> 
<table width="100%"  class="companiesTable" >
	<tr> 
		<th colspan="2"> <h4>Select fields to download. </h4>
			<c:forEach var="_listSalesForItem" items="${listSalesForItem}">
				<input type="hidden" name="fieldName" value="${_listSalesForItem}">
			</c:forEach>
		</th>
	</tr>

	<tr>
		<td class="center" colspan="2"><a href="javascript:checkall('fieldSelection2','itemId','fieldName',true)">Check All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:checkall('fieldSelection2','itemId',false)">Uncheck All</a></td>
	</tr>


		<tr>
			<td width="20%">Select Item Name</td>
			<td>
				<select name="itemSalesToBeDownloaded">
					<c:forEach var="item" items="${listOfItems}">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	

<tr> 
		<td colspan="2"><div style="float:right;width:20%"><input type="submit" value=" Download " class="btnBlue"></div></td>
</tr>

</table>
</div>
</form>
--%>
