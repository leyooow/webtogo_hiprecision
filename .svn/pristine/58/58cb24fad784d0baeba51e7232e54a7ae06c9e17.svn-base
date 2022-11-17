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

	    <h1><strong>Reports</strong>: Download/View Reports</h1>

		<ul>

		

		  <li>Filter</li>
		  <li><select size="1" id="companyStatus" name="filter" >
										<option value="all" selected="selected"> ALL  </option>
												<c:choose>
													<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.ONGOING.num}">
														<option value="${CompanyStatus.ONGOING.num}" selected="selected">  ${CompanyStatus.ONGOING}  </option>
													</c:when>
													<c:otherwise>
														<option value="${CompanyStatus.ONGOING.num}">${CompanyStatus.ONGOING}</option>
													</c:otherwise>
												</c:choose>
						 						<c:choose>
													<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.ACTIVE.num}">
														<option value="${CompanyStatus.ACTIVE.num}" selected="selected">  ${CompanyStatus.ACTIVE}  </option>
													</c:when>
													<c:otherwise>
														<option value="${CompanyStatus.ACTIVE.num}">${CompanyStatus.ACTIVE}</option>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.ACTIVE_NO_HOSTING.num}">
														<option value="${CompanyStatus.ACTIVE_NO_HOSTING.num}" selected="selected"> ${CompanyStatus.ACTIVE_NO_HOSTING} </option>
													</c:when>
													<c:otherwise>
														<option value="${CompanyStatus.ACTIVE_NO_HOSTING.num}">${CompanyStatus.ACTIVE_NO_HOSTING}</option>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.SUSPENDED.num}">
														<option value="${CompanyStatus.SUSPENDED.num}" selected="selected">   ${CompanyStatus.SUSPENDED}   </option>
													</c:when>
													<c:otherwise>
														<option value="${CompanyStatus.SUSPENDED.num}">${CompanyStatus.SUSPENDED}</option>
													</c:otherwise>
												</c:choose>
												
										</select> </li>
										<li><input type="submit" value="Download" class="btnBlue" /></li>

		</ul>
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
						<td>
							*All Companies Latest Payments 
						</td>
						<td>
							<a href="downloadBillingLastPayment.do">PDF</a>
							|
							<a href="downloadBillingLastPaymentXLS.do">Excel</a>
						</td>
					</tr>
					<tr class="oddRow">
						<td>
							*All Companies Pending Bills 
						</td>
						<td>
							<a href="downloadBillingPending.do">PDF</a>
						|
							<a href="downloadBillingPendingXLS.do">Excel</a>
						</td>
					</tr>
					<tr>
						<td>
							*All Companies Cancelled Bills 
						</td>
						<td>
							<a href="downloadBillingCancelled.do">PDF</a>
						|
							<a href="downloadBillingCancelledXLS.do">Excel</a>
						</td>
					</tr>
					<tr class="oddRow">
						<td>
							*All Companies Directory 
						</td>
						<td>
							<a href="downloadCompaniesDirectory.do">PDF</a>
						|
							<a href="downloadCompaniesDirectoryXLS.do">Excel</a>
						</td>
					</tr>				
				</table>
	  
	</div><!--//content-->
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>