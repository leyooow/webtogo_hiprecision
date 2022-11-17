<%@include file="includes/header.jsp"  %>
<c:set var="menu" value="quotation" />
<body>

  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
<div class="contentWrapper" id="contentWrapper">
    <div class="content">
		  <div class="pageTitle">
		    	<h1><strong>Download/View Quotations</strong></h1>
	
					<%--<ul>
					  <li>Filter</li>
					  <li>Test</li>
					</ul>--%>
				
				<div class="clear"></div>
		   </div><!--//pageTitle-->
	   
		<div class="clear"></div>
		 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr>
						<th>REF#</th>
						<th>DATE</th>
						<th>CLIENT NAME</th>
						<th>TOTAL NET AMOUNT</th>
						<th>PREPARED BY</th>
						<th>ACTION</th>
					</tr>
					
					<c:forEach items="${transactions}" var="transaction">
						<tr>
							<td>
								${transaction.client.ref}
							</td>
							<td align="center">
								<joda:format value="${transaction.client.date}" var="date" pattern="MM-dd-yyyy" />
								${date}
							</td>
							<td>
								${transaction.client.clientName}
							</td>
							<td align="center">
								${transaction.grandTotal}
							</td>
							<td>
								${transaction.member.firstname}&nbsp;${transaction.member.lastname}
							</td>
							<td align="center">
								<a href="generate-quote.do?id=${transaction.id}">PDF</a>
								&nbsp;|&nbsp;
								<a href="delete-quote.do?id=${transaction.id}">DELETE</a>
							</td>
					</tr>
					</c:forEach>
					
		  </table> 
		  
		  <ul class="pagination">
		  	<%@include file="includes/pagingnew.jsp"  %>
		  </ul>
	  
	</div><!--//content-->
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>