<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="activities" />
<c:set var="submenu" value="test_logs"/>
<c:set var="pagingAction" value="testLogs.do" />


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

<div class="contentWrapper" id="contentWrapper">
    <div class="content">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">

	    <h1><strong>Logs</strong>: Test Logs</h1>
		
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->
	  <div style="margin-top:3%">
	  	<input type="text" name="filter" id="filter" />
<!-- 	  	<input type="submit" value="Search" class="btnBlue"> -->
	  	<button class="btnBlue" onclick="createFilter()">Search</button>
	  </div>
	  
	  <script>
	  	function createFilter(){
	  		var filter = document.getElementById('filter').value;
	  		window.location.href = '?filter='+filter;
	  	}
	  </script>

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr class="headbox"> 
										<th>Date</th>
										<th>Description</th>
										<th>Test Name</th>    
										<th>Test Code</th>    
										<th>Username</th>
									</tr>
									<c:set var="count" value="0" />
										<c:forEach items="${categoryItemLogList}" var="cp"  varStatus="counter">
												<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
												<c:set var="count" value="${count+1}" />
												<td>${cp.createdOn}</td>
												<td>
													<c:set var="i" value="0" />
													<c:forTokens var="curr" delims="~" items="${cp.remarks}">
														<c:set var="i" value="${i + 1}" />
														<c:choose>
															<c:when test="${fn:length(curr) gt 250}">
																<c:out value="${fn:substring(curr,0,250)}" escapeXml="true" /><a href="javascript:void(0);" onmouseover="javascript:showContent(${i});">...</a>
																<div id="showContent_${i}" style="display:none">
																	<c:out value="${curr}" escapeXml="true" />
																</div>
																<br />
															</c:when>
															<c:otherwise>
																<c:out value="${curr}" escapeXml="true" /> <br />
															</c:otherwise>
														</c:choose>
													</c:forTokens>
												</td>
												<td>${cp.categoryItem.name}</td>
												<c:if test="${fn:length(cp.categoryItem.categoryItemOtherFields) le 0 }"><td></td></c:if>														
												<c:forEach items="${cp.categoryItem.categoryItemOtherFields}" var="categoryItemOtherFields">
													<c:if test="${categoryItemOtherFields.otherField.name eq 'TEST CODE(S)'}">
														<td>${categoryItemOtherFields.content }</td>
													</c:if>
												</c:forEach>
												<td>${cp.updatedByUser.username}</td>
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

</body>

</html>