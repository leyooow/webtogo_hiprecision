<%@include file="includes/header.jsp"  %>
<link href="../admin/css/modal.css" rel="stylesheet" type="text/css" />
<body  onload="document.getElementById('company_id').focus();">

  <div class="container">
	<c:set var="menu" value="dashboard" />
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>

<div class="contentWrapper" id="contentWrapper">
    <div class="content">
    		<c:if test="${param['evt'] == 'validatedsuccessfully'}">
				<ul><li><span class="actionMessage">Email validation was successful.</span></li></ul>
			</c:if>
		<div class="pageTitle">
		
			<h1><strong>Validate Email</strong></h1>
		
			<div class="clear"></div>
		
		</div><!--//pageTitle-->
		<ul class="pagination">
	   		<%@include file="includes/pagingnew.jsp"  %>
	  	</ul>
		<div class="clear"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
			<tr style="text-align:left"> 
				<th >Email</th>  
				<th>Date Validated</th>
				<th >isValid</th>		
			</tr>
			<c:set var="count" value="0" />
				<c:forEach items="${emails}" var="g">
					<c:if test="${g.email ne null && fn:length(g.email) gt 0}">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
							<c:set var="count" value="${count+1}" />
							<td>${g.email}</td>
							<td><c:choose><c:when test="${g.emailDateValidated eq null}">NOT YET VALIDATED</c:when><c:otherwise>${g.emailDateValidated }</c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${g.emailValid eq true}"><img src="images/accept.png" title="Email is Valid" alt="Email is Valid" /></c:when><c:when test="${g.emailValid eq false}"><img src="images/cancel.png" title="Email is Not Valid" alt="Email is Not Valid" /></c:when><c:otherwise>NOT YET VALIDATED</c:otherwise></c:choose></td> 
						</tr>
					</c:if>
				</c:forEach>
		</table>
		<br /><br />
		<div style="text-align:center">
			<form method="post" action="emailvalidation.do">
				<input type="submit" value="Validate Now!" class="btnBlue"/>
			</form>
		</div>
	</div><!--//mainContent-->

	

</div>

	
  </div><!--//container-->

</body>

</html>