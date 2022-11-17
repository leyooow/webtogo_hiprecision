<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="activities" />
<c:set var="submenu" value="agian_portal_activities"/>
<c:set var="pagingAction" value="portalactivities.do" />


<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 

<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(e + "\nContent:\n" + content);
		}
	}
</script>

<c:set var="menu" value="activities" />
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

<form action="downloadPortalActivityLogExcel.do" method="post">
	<input type="hidden" name="fieldName" value="Date"/>
  	<input type="hidden" name="fieldName" value="Section"/>
  	<input type="hidden" name="fieldName" value="Topic"/>
  	<input type="hidden" name="fieldName" value="Remarks"/>
  	<input type="hidden" name="fieldName" value="Company"/>
  	<input type="hidden" name="fieldName" value="Sub Company"/>
  	<input type="hidden" name="fieldName" value="Member"/>
  	
<div class="contentWrapper" id="contentWrapper">
    <div class="content">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">
	  <h1><strong>Portal Logs</strong>: View Portal Log List</h1>		
	  <div class="clear"></div>
	</div><!--//pageTitle-->
	  
	<div class="downloadsBox">
	  <input type="hidden" name="company_id" value="${company.id}"/>
	  <input type="hidden" name="member_id_" id="member_id_" class="member_id_" value="0" />
	  <table border="0" cellspacing="0" cellpadding="5">
		<tr>
		  <td>DOWNLOAD IN EXCEL FORMAT&nbsp; &nbsp; </td>
		  <td><input type="radio" name="filter" value="all" checked="checked" /></td>
		  <td>ALL</td>
		  <td><input type="radio" name="filter" value="byDate"  /></td>
		  <td>FROM</td>
		  <td>
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
			<input type="text" id="fromDate" name="fromDate" value="${idate}"/> 
			<a href="javascript:;" id="fromDateButton"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>

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
		  <td>TO</td>
		  <td>
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
			<input type="text" id="toDate" name="toDate" value="${idate}"/> 
			<a href="javascript:;" id="toDateButton"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>

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
		  <td><input type="submit" value="Download" class="btnBlue"></td>
		</tr>
	  </table>
	</div><!--//downloadsBox-->		 

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr class="headbox"> 
										<th>Date</th>
										<th>Section</th>
										<th>Topic</th>
										<th>Remarks</th>
										<th>Company</th>
										<th>Sub Company</th>
										<th>Member</th>
									</tr>
									<c:set var="count" value="0" />
									<c:forEach items="${portalActivityLogList}" var="cp"  varStatus="counter">
											<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
											<c:set var="count" value="${count+1}" />
											<td>${cp.createdOn}</td>
											<td>${cp.section }</td>
											<td>
												<c:set var="i" value="0" />
												<c:forTokens var="curr" delims="~" items="${cp.topic}">
													<c:set var="i" value="${i + 1}" />
													<c:choose>
														<c:when test="${fn:length(curr) gt 250}">
															<c:out value="${fn:substring(curr,0,250)}" escapeXml="false" /><a href="javascript:void(0);" onmouseover="javascript:showContent(${i});">...</a>
															<div id="showContent_${i}" style="display:none">
																<c:out value="${curr}" escapeXml="false" />
															</div>
															<br />
														</c:when>
														<c:otherwise>
															<c:out value="${curr}" escapeXml="false" /> <br />
														</c:otherwise>
													</c:choose>
												</c:forTokens>
											</td>
											<td>${cp.remarks }</td>
											<c:if test="${not empty cp.memberParentCompany }">
												<td>${cp.memberParentCompany }</td>
												<td>${cp.memberCompany }</td>
											</c:if>
											<c:if test="${empty cp.memberParentCompany }">
												<td>${cp.memberCompany }</td>
												<td></td>
											</c:if>
											<td>${cp.member.firstname} ${cp.member.lastname }</td>
										</tr>
									</c:forEach>
								</table>
	
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->

</div>

	<div class="clear"></div>

  </div><!--//container-->
</form>
</body>

</html>