<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="productstatistics" />
<c:set var="submenu" value="productstatistics" />
<c:set var="pagingAction" value="trustproductstatistics.do" />

<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
			<c:if test="${param['evt'] != null}">
						<ul>
							<c:if test="${param['evt'] == 'pageexist'}">
								<li><span class="actionMessage">Rate was not created because a similar page already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Rate was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'add'}">
								<li><span class="actionMessage">Rate was successfully updated.</span></li>
							</c:if>
						</ul>
					</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Rates</strong></h1>
	</form>
			
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>			
					
					<c:if test="${statistics == null}">					
						
						<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
							<tr class="headbox">
								<th>Name</th>
							</tr>
							<c:if test="${(user.id eq 173 or user.id eq 176 or user.id eq 177) or user.userType.value ne 'Company Staff'}">
								<tr>
									<td><a href="trustproductstatistics.do?id=1&page=1">Trust Products</a></td>
								</tr>
							</c:if>
							<c:if test="${(user.id eq 171 or user.id eq 172 or user.id eq 174 or user.id eq 175) or user.userType.value ne 'Company Staff'}">
								<tr class="oddRow">
									<td><a href="trustproductstatistics.do?id=2&page=1">Foreign Exchange Buy and Sell</a></td>
								</tr>
							</c:if>							
						</table>
					</c:if>
						
					<c:if test="${statistics != null}">
				
					<c:set var="formAction" value="addtrustproductstatistics.do" />
					<c:set var="edititableName" value="true" />
					<c:choose>
						<c:when test="${parentStatistics eq 'Trust Products'}">
						
							
						<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
								<tr>
									<th >Blue Fund</th>				
									<th >Green Fund</th>
									<th >Balanced Fund</th>
									<th >Money Market Fund</th>
									
									<%-- th >Date</th --%>
									<th >Updated By</th>
									<th >Created On</th>
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
									<th >Action</th>
									</c:if>
								</tr>
								<c:set var="count" value="0" />
								<c:forEach begin="0" end="${fn:length(statistics)}" var="i" step="4">
									<c:if test="${statistics[i] ne null}">
									<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
									<c:set var="count" value="${count+1}" />
										<td>${statistics[i].value}</td>
										<td>${statistics[i+1].value}</td>
										<td>${statistics[i+2].value}</td>
										<td>${statistics[i+3].value}</td>
										<%-- td>${statistics.date}</td --%>
										<td>${statistics[i].user.firstname} ${statistics[i].user.lastname}</td> 
										<td>${statistics[i].createdOn}</td>
										<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
										<td><a href="deletetrustproductstatistics.do?rate_id=${statistics[i].id}&rate_id2=${statistics[i+1].id}&rate_id3=${statistics[i+2].id}" onclick="return confirm('Are you sure you want to delete this entry?');">Delete</a></td>
										</c:if>
									</tr>
									</c:if>
								</c:forEach>
							</table>
								
						</c:when>					
						<c:otherwise>							
										
							<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
								<tr >
									<th >Buy</th>				
									<th >Sell</th>
									<%-- th >Date</th --%>
									<th >Updated By</th>
									<th >Created On</th>
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
									<th >Action</th>
									</c:if>
								</tr>
																
								<c:set var="count" value="0" />								
								<c:forEach begin="0" end="${fn:length(statistics)}" var="i" step="1">	
									<c:set var="ids" value="" />
									<c:set var="num" value="1" />							
									<c:if test="${statistics[i] ne null}">
										<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
											<c:set var="count" value="${count+1}" />
											<td width="17%">																						
												<c:forEach items="${buyValues[i]}" var="buy">
													${buy.name} = ${buy.value} <br>
													<c:set var="ids" value="${ids}&r${num}=${buy.id}" />
													<c:set var="num" value="${num+1}" />
												</c:forEach>
											</td>
											<td width="17%">
												<c:forEach items="${sellValues[i]}" var="sell">
													${sell.name} = ${sell.value} <br>
													<c:set var="ids" value="${ids}&r${num}=${sell.id}" />
													<c:set var="num" value="${num+1}" />
												</c:forEach>
											</td>
											<td width="25%">${statistics[i].user.firstname} ${statistics[i].user.lastname}</td> 
											<td width="25%">${statistics[i].createdOn}</td>
											<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
												<td width="16%" align="center"><a href="deletetrustproductstatistics.do?${ids}" onclick="return confirm('Are you sure you want to delete this entry?');">Delete</a></td>
											</c:if>
										</tr>
									</c:if>
								</c:forEach>
								
							</table>
						</c:otherwise>
					</c:choose>			
				</c:if>
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
	
	<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
					
		<div class="sidenav">
			<c:if test="${statistics == null}">
				<h2>Choose Rate Category</h2>
			</c:if>
			
			<c:if test="${statistics != null}">	
				<c:choose>
				
					<c:when test="${parentStatistics eq 'Trust Products'}">						
						<script language="javascript" src="../javascripts/upload.js"></script>
						<script language="javascript" src="../javascripts/dynamicUploader.js"></script>
		
						<!-- calendar stylesheet -->
						<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
						
						<!-- main calendar program -->
						<script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
						
						<!-- language for the calendar -->
						<script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
						
						<!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
						<script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>
						
						<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
						<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
						
						<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
						<c:set var="imageCount" value="${fn:length(singlePage.images)}" />
						<c:set var="allowedImageCount" value="${maxImage - imageCount}" />
						<c:set var="allowedFileCount" value="${1 - fn:length(singlePage.files)}" />
						
						<c:set var="maxMultiPageFile" value="${companySettings.maxAllowedFiles}" />
						<c:set var="uploadedFileCount" value="${fn:length(multiPageFiles)}" /> 
						<c:set var="allowedUploadedFileCount" value="${maxMultiPageFile - uploadedFileCount}" />
		
						<script>
							function submitForm(myForm) {
						
								var addbluevalue = getElement('bluestatvalue');
								var addgreenvalue = getElement('greenstatvalue');
								var addbalancedvalue = getElement('balancedstatvalue');
								var moneymarketstatvalue = getElement('moneymarketstatvalue');
								
								//var adddate = getElement('statdate');
								var error = false;
								var messages = "Problem(s) occured: \n\n";
								
								if(addbluevalue.length == 0) {
									messages += "* Blue Fund value not given\n";
									error = true;
								}
						
								if(addgreenvalue.length == 0) {
									messages += "* Green Fund value not given\n";
									error = true;
								}
								if(addbalancedvalue.length == 0) {
									messages += "* Balanced Fund value not given\n";
									error = true;
								}
								if(moneymarketstatvalue.length == 0) {
									messages += "* Money Market Fund value not given\n";
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
								//value = value.replace(/^\s+|\s+$/, '');
								return value;
							}
							
						</script> 
						
						<h2>Update ${parentStatistics}</h2>
								
						<form method="post" action="addtrustproductstatistics.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
						
							<input type="hidden" name="userID" value="${user.id}">
							<!--  <input type="hidden" name="subject" value="RobinsonsBank Rate Statistics"> -->
							<input type="hidden" name="to" value="${company.email}">
							<input type="hidden" name="from" value="noreply@webtogo.com.ph">
							<input type="hidden" name="title" value="Statistics Update">
							
							<table width="100%">
								<tr>
									<td>Blue Fund<br />
									<input type="text" id="bluestatvalue" name="bluestatvalue" class="w200">
									</td>
								</tr>
								<tr>
									<td>Green Fund<br />
									<input type="text" id="greenstatvalue" name="greenstatvalue" class="w200">
									</td>
								</tr>
								<tr>
									<td>Balanced Fund<br />
									<input type="text" id="balancedstatvalue" name="balancedstatvalue" class="w200">
									</td>
								</tr>
								<tr>
									<td>Money Market Fund<br />
									<input type="text" id="moneymarketstatvalue" name="moneymarketstatvalue" class="w200">
									</td>
								</tr>																	 
								<tr>
								
									<td>
									 
												<input type="submit" value="Add" class="btnBlue">
										
									</td>
								</tr>
							</table>
						</form>
					
					</c:when>
					<c:otherwise>
					
						<!-- calendar stylesheet -->
						<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
						
						<!-- main calendar program -->
						<script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
						
						<!-- language for the calendar -->
						<script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
						
						<!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
						<script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>
						
						<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
						<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
						
						<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
						<c:set var="imageCount" value="${fn:length(singlePage.images)}" />
						<c:set var="allowedImageCount" value="${maxImage - imageCount}" />
						<c:set var="allowedFileCount" value="${1 - fn:length(singlePage.files)}" />
						
						<c:set var="maxMultiPageFile" value="${companySettings.maxAllowedFiles}" />
						<c:set var="uploadedFileCount" value="${fn:length(multiPageFiles)}" /> 
						<c:set var="allowedUploadedFileCount" value="${maxMultiPageFile - uploadedFileCount}" />
						
						<script>
		
						function submitForm(myForm) 
						{							
							var error = false;
							var messages = "Problem(s) occured: \n\n";
							
							if(getElement('buyUSD').length == 0) { 
								messages += "* US Buy value not given \n";
								error = true; }							
							if(getElement('sellUSD').length == 0) {
								messages += "* US Sell value not given \n";
								error = true; }							
							if(getElement('buyCNY').length == 0) { 
								messages += "* CNY Buy value not given \n";
								error = true; }							
							if(getElement('sellCNY').length == 0) {
								messages += "* CNY Sell value not given \n";
								error = true; }							
							if(getElement('buyEUR').length == 0) { 
								messages += "* EUR Buy value not given \n";
								error = true; }							
							if(getElement('sellEUR').length == 0) {
								messages += "* EUR Sell value not given \n";
								error = true; }							
							if(getElement('buyGBP').length == 0) { 
								messages += "* GBP Buy value not given \n";
								error = true; }							
							if(getElement('sellGBP').length == 0) {
								messages += "* GBP Sell value not given \n";
								error = true; }							
							if(getElement('buyJPY').length == 0) { 
								messages += "* JPY Buy value not given \n";
								error = true; }							
							if(getElement('sellJPY').length == 0) {
								messages += "* JPY Sell value not given \n";
								error = true; }							
							if(getElement('buySGD').length == 0) { 
								messages += "* SGD Buy value not given \n";
								error = true; }							
							if(getElement('sellSGD').length == 0) {
								messages += "* SGD Sell value not given \n";
								error = true; }							
							if(getElement('buyHKD').length == 0) { 
								messages += "* HKD Buy value not given \n";
								error = true; }							
							if(getElement('sellHKD').length == 0) {
								messages += "* HKD Sell value not given \n";
								error = true; }
														 
							if(error) {
								alert(messages);
							}
							else {
								return true;
							}
							
							return false;
						}
						function getElement(elementName) {
							return document.getElementById(elementName).value;
						}
						
						</script> 
						
						<h2>Update ${parentStatistics} ${latestBuyValues[0]}</h2>
						
						<form method="post" action="addForeignExchangeStatistics.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
							<input type="hidden" name="userID" value="${user.id}">
							<input type="hidden" name="subject" value="RobinsonsBank Rate Statistics">
							<input type="hidden" name="to" value="${company.email}">
							<input type="hidden" name="from" value="noreply@webtogo.com.ph">
							<input type="hidden" name="title" value="STATISTIC UPDATE">
							
							<table width="50%">
								<tr>
									<td><strong>USD</strong><br>Buy<br /><input type="text" id="buyUSD" name="buyUSD" value="${latestBuyValues[0]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td >Sell<br /><input type="text" id="sellUSD" name="sellUSD" value="${latestSellValues[0]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td><strong>CNY</strong><br>Buy<br /><input type="text" id="buyCNY" name="buyCNY" value="${latestBuyValues[1]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td >Sell<br /><input type="text" id="sellCNY" name="sellCNY" value="${latestSellValues[1]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td><strong>EUR</strong><br>Buy<br /><input type="text" id="buyEUR" name="buyEUR" value="${latestBuyValues[2]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td >Sell<br /><input type="text" id="sellEUR" name="sellEUR" value="${latestSellValues[2]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td><strong>GBP</strong><br>Buy<br /><input type="text" id="buyGBP" name="buyGBP" value="${latestBuyValues[3]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td >Sell<br /><input type="text" id="sellGBP" name="sellGBP" value="${latestSellValues[3]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td><strong>JPY</strong><br>Buy<br /><input type="text" id="buyJPY" name="buyJPY" value="${latestBuyValues[4]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td >Sell<br /><input type="text" id="sellJPY" name="sellJPY" value="${latestSellValues[4]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td><strong>SGD</strong><br>Buy<br /><input type="text" id="buySGD" name="buySGD" value="${latestBuyValues[5]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td >Sell<br /><input type="text" id="sellSGD" name="sellSGD" value="${latestSellValues[5]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td><strong>HKD</strong><br>Buy<br /><input type="text" id="buyHKD" name="buyHKD" value="${latestBuyValues[6]}" class="w200" style="text-align: right"></td>
								</tr>
								<tr>
									<td >Sell<br /><input type="text" id="sellHKD" name="sellHKD" value="${latestSellValues[6]}" class="w200" style="text-align: right"></td>
								</tr>																	 
								<tr>
									<td><input type="submit" value="Add" class="btnBlue" ></td>
								</tr>
							</table>
						</form>
					
					</c:otherwise>
				</c:choose>
			</c:if>
		</div><!--//sidenav-->
	</c:if>
</div>
		
	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>