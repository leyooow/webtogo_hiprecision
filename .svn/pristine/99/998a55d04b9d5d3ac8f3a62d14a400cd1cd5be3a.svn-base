<%@include file="includes/header.jsp"  %>
<c:set var="menu" value="manage" />
<c:set var="submenu" value="report_listing" />
<c:set var="pagingAction" value="companies.do" />
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
    <div class="content">
     
				<s:actionmessage />
				<s:actionerror />
				<form action="downloadCompaniesDirectoryXLS.do" method="post" name="downloadForm" >
	<div class="pageTitle">
	    <h1><strong>Reports</strong>: Politiker Data Reports</h1>
		<div class="clear"></div>
	</div><!--//pageTitle-->
</form>
		<div class="clear"></div>
		 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr>
						<th>REPORTS</th>
						<th>DOWNLOAD</th>
					</tr>
					<tr>
						<td><strong>POLITICIANS REPORTS</strong></td>
						<td>&nbsp;</td>
					</tr>
					
					<tr class="oddRow">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*Politician(s) Ratings 
						</td>
						<td style = "text-align:center;">
							<%--<a href="downloadBillingLastPayment.do">PDF</a>
							| --%>
							<a href="politiciansDetailsXLS.do">Download as Excel File</a>
						</td>
					</tr>
					
					<tr>
						<td><strong>BILLS REPORTS</strong></td>
						<td>&nbsp;</td>
					</tr>
					<tr class="oddRow">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*Bill(s) Vote Percentage 
						</td>
						<td style = "text-align:center;">
							<%--<a href="downloadBillingLastPayment.do">PDF</a>
							|--%>
							<a href="billsDetailsXLS.do">Download as Excel File</a>
						</td>
					</tr>
					<tr>
						<td><strong>POLLS REPORTS</strong></td>
						<td>&nbsp;</td>
					</tr>
					<tr class="oddRow">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*Poll(s) Vote Percentage 
						</td>
						<td style = "text-align:center;">
							<%--<a href="downloadBillingLastPayment.do">PDF</a>
							|--%>
							<a href="pollsDetailsXLS.do">Download as Excel File</a>
						</td>
					</tr>
					<tr>
						<td><strong>SPEAKUP REPORTS</strong></td>
						<td>&nbsp;</td>
					</tr>
					
					<tr class="oddRow">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*Speakup Supporter(s) 
						</td>
						<td style = "text-align:center;">
							<%--<a href="downloadBillingLastPayment.do">PDF</a>
							|--%>
							<a href="speakupDetailsXLS.do">Download as Excel File</a>
						</td>
					</tr>				
				</table>
	  
	</div><!--//content-->
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>