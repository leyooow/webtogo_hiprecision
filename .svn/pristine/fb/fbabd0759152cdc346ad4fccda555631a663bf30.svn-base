<%@include file="includes/header.jsp"  %>
<link href="../admin/css/modal.css" rel="stylesheet" type="text/css" />
<body  onload="document.getElementById('company_id').focus();">

  <div class="container">
	<c:set var="menu" value="dashboard" />
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}	

	function submitForm(myForm){
	 	var filename = myForm.logo.value;
	 	var split = new Array();
	 	split = filename.split('\\');
	 	filename = split[split.length-1];
	 	myForm.filename.value = filename;
	}	
</script>
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	  <c:if test="${param['evt'] != null}">
					<ul>
						<c:if test="${param['evt'] == 'companysettingsaved'}">
							<li><span class="actionMessage">Company was successfully updated.</span></li>
							<br />
						</c:if>
						<c:if test="${param['evt'] == 'companycreated'}">
							<li><span class="actionMessage">Company was successfully created.</span></li>
							<br />
						</c:if>
						<c:if test="${param['evt'] == 'companycopied'}">
							<li><span class="actionMessage">Company was successfully copied.</span></li>
							<br />
						</c:if>
					</ul>
				</c:if>			

<c:choose>
			
			<c:when test="${company.name eq 'neltex' && user.userType.value == 'Company Administrator' && fn:containsIgnoreCase(user.username, 'hr')}">
				<h3>
				  	<a href="multipage.do">Manage Pages</a>
				  </h3>
			
				  <ul class="pageList">
			<c:forEach items="${multiPageList}" var="mp"  varStatus="counter">
				<c:if test="${mp.name eq 'Careers' or mp.name eq 'Testimonials'}">
								<li><a href="editmultipagechildlist.do?multipage_id=${mp.id}">${mp.name}</a>&nbsp; - <span> ${fn:length(mp.items)} item(s)</span></li>
								</c:if>
							</c:forEach>
				  </ul>
			</c:when>
			
			<c:otherwise>
	  <h3>
	  	<a href="singlepage.do">Manage Single Page<c:if test="${param.isDeployed != null}">     DEPLOYEEEEEEEEEEEEEEEEED!!!!!!!!!!!!!!!!</c:if></a>
	  	<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
	  		&nbsp;-&nbsp;&nbsp;<a href="addsinglepage.do">add new</a>
	  	</c:if>
	  </h3>

	  <ul class="pageList">

	    <c:forEach items="${singlePageList}" var="sp"  varStatus="counter">
	    	<c:choose>
	    		<c:when test="${company.name eq 'neltex'}">
	    			<c:if test="${!fn:containsIgnoreCase(sp.name, 'Home')}">
	    				<li><a href="editsinglepage.do?singlepage_id=${sp.id}">${sp.name}</a></li>
	    			</c:if>
	    		</c:when>
	    		<c:otherwise>
	    			<li><a href="editsinglepage.do?singlepage_id=${sp.id}">${sp.name}</a></li>
	    		</c:otherwise>
	    	</c:choose>
				</c:forEach>

	  </ul>

	  <h3>
	  	<a href="multipage.do">Manage Multiple Pages</a>
	  	<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
	  		&nbsp;-&nbsp;&nbsp;<a href="addmultipage.do">add new</a>
	  	</c:if>
	  </h3>

	  <ul class="pageList">
<c:forEach items="${multiPageList}" var="mp"  varStatus="counter">
					<li><a href="editmultipagechildlist.do?multipage_id=${mp.id}">${mp.name}</a>&nbsp; - <span>${company.name eq 'kuysenfurnitures' ? fn:length(mp.items) : fn:length(mp.items)} item(s)</span></li>
				</c:forEach>
	  </ul>

	  <h3>
	  	<a href="forms.do">Manage Forms<c:if test="${param.isDeployed != null}">  TRY ULET IF   DEPLOYEEEEEEEEEEEEEEEEED!!!!!!!!!!!!!!!!</c:if></a>
	  	<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
	  		&nbsp;-&nbsp;&nbsp;<a href="addforms.do">add new</a>
	  	</c:if>
	  </h3>

	  <ul class="pageList">
		<c:forEach items="${formPageList}" var="fp"  varStatus="counter">
			<c:choose>
				<c:when test="${company.name eq 'swapcanada' and fp.name ne 'thank you'}">
				
				</c:when>
				<c:otherwise>
					<li><a href="${fp.url }">${fp.name}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>

	  </ul>
		<c:if test="${company.companySettings.hasProducts}">
		
		<h3>
			<a href="groups.do">Manage Groups</a>
			<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
	  		&nbsp;-&nbsp;&nbsp;<a href="addgroups.do">add new</a>
	  	</c:if>
		</h3>

	  <ul class="pageList">
		<c:forEach items="${groupList}" var="gp"  varStatus="counter">
			<c:choose>
				<c:when test="${company.name eq 'swapcanada' and gp.name eq 'Strap'}">
					<li><a href="items.do?group_id=${gp.id}">DIY - Strap</a></li>
				</c:when>
				<c:when test="${company.name eq 'swapcanada' and gp.name eq 'Face'}">
					<li><a href="items.do?group_id=${gp.id}">DIY - Face</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="items.do?group_id=${gp.id}">${gp.name}</a></li>
				</c:otherwise>
			</c:choose>
			
		</c:forEach>

	  </ul>
		
		</c:if>
		
	<div id="manageCategoriesDiv" <c:if test="${company.name eq 'agian' }">style="display:none;"</c:if>>
	  <h3>Manage Categories<c:if test="${param.isDeployed != null}">     DEPLOYEEEEEEEEEEEEEEEEED!!!!!!!!!!!!!!!!</c:if></h3>

	  <ul class="pageList">
		
	    <c:forEach items="${categoryList}" var="cp"  varStatus="counter">
					<li><a href="editcategory.do?group_id=${cp.parentGroup.id}&category_id=${cp.id}">${cp.name}</a>&nbsp; - <span></span></li>
				</c:forEach>

	  </ul>
	  </div>
	  
	  
	  </c:otherwise>
	  
	  </c:choose>
		<!-- 
	  <h3>Recent Logs</h3>
	  
	  <ul class="pageList">

	    <li>

		  

		  <a href="logs.do">View All Logs</a>
		  
		  <c:choose>
				<c:when test="${not empty logList}">
				<c:set var="i" value="1" />
				<c:forEach items="${logList}" var="cp"  varStatus="counter">
				<c:set var="i" value="${i + 1}" />
				</c:forEach>
					&nbsp; - <span>Last seven days (${i})</span></li>
				<!-- </li><li>
				
					<table border="0">
						<tr class="headbox">
							<th>Date</th>
							<th>Description</th>
							<th>Username</th>
						</tr>
						<c:forEach items="${logList}" var="cp"  varStatus="counter">
							<tr>
								<td>${cp.createdOn}</td>
								<td>
									<c:set var="i" value="0" />
									<c:forTokens var="curr" delims="~" items="${cp.remarks}">
										<c:set var="i" value="${i + 1}" />
										<c:choose>
											<c:when test="${fn:length(curr) gt 250}">
												<c:out value="${fn:substring(curr,0,250)}" escapeXml="false" /><a href="javascript:void(0);" onmouseover="javascript:showContent(${i});" onmouseout="return nd();">...</a>
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
								<td>${cp.updatedByUser.username}</td>
							</tr>
						</c:forEach>
					</table></li>
					 
				</c:when>
				
				<c:otherwise>
					
					&nbsp; - <span>No activity for the last seven days.</span></li>
				</c:otherwise>
		   </c:choose>
		   </span>

		  <div class="clear"></div>

		

	  </ul>			
		
		-->
	</div><!--//mainContent-->

	<div class="sidenav" <c:if test="${company.name eq 'agian' and user.userType.value ne 'System Administrator' and user.userType.value ne 'Portal Administrator' and user.userType.value ne 'Super User'}">style="display:none;"</c:if>>
		
	  <h1><c:if test="${user.company != null && user.company.logo != null}">		
							<img src="${contextParams['IMAGE_PATH']}/images/${user.company.logo}" width="229" height="119"/>
							<br />
		</c:if></h1>
	
	  <h2>${user.company.nameEditable}</h2>
		
	  <ul>
	<c:if test="${user.company != null && user.company.lastUpdated != null}">
	    <li>

		  <img src="images/iLastUpdate.gif" />
			<c:if test="${user.userType.value == 'Company Administrator' or user.userType.value == 'Company Staff' and user.lastLogin != null}">
						
								<c:if test="${user.company != null && user.company.lastUpdated != null}">
								
								<span><strong>Last Login</strong><fmt:formatDate pattern="MMMM dd, yyyy - hh:mm:ss a" value="${user.lastLogin}" /></span>
								</c:if>
							
						</c:if>						
						
						
								
								<span><strong>Last Updated:</strong><fmt:formatDate pattern="MMMM dd, yyyy - hh:mm:ss a" value="${user.company.lastUpdated.updatedOn}" /></span>
							
						
		  <div class="clear"></div>
			
		</li>
</c:if>

<c:if test="${company.name ne 'pocketpons' }">
		<li>
		
  		  <img src="images/iInquiries.gif" />
  		  
  		  <c:if test="${user.userType.value ne 'Company Staff'}">
  		  
			<span><a href="formsubmissions.do?filterDays=3"><strong>Inquiries</strong> Last 72 Hours(${latestSubmissionsCount})</a></span>
			
  		  </c:if>
  		  
  		  <div class="clear"></div>
  		
		</li>
</c:if>

		<li>
		
  		  <a href="/"><img src="images/iLogs.gif" /></a>
  		  
  		  <span><a href="logs.do"><strong> Logs</strong> Last 7 days(${fn:length(logList)})</a></span>
  		  
  		  <div class="clear"></div>
  		  
		</li>

<c:if test="${company.name ne 'pocketpons' }">
		<li>

		  <img src="images/iStats.gif" />

		  <span><a href="http://72.249.183.248/awstats/awstats.pl?config=${company.domainName}" target="Statistics"><strong>Statistics</strong> View Site Statistics</a></span>

		  <div class="clear"></div>

		</li>
</c:if>
							<c:if test="${user.userType.value ne 'Company Staff' and latest72Comment > 0}">
		<li>

		  <img src="images/iComments.gif" />

		  
		
						
								
									<span><a href="comment.do?filterDays=3"><strong>Comments</strong> Last 72 Hours(${latest72Comment})</a></span>
								
						
						
		  <div class="clear"></div>

		</li>
		
							</c:if>

	  </ul>
	  
	  					
	 <div>
	  <c:if test="${user.company != null and company.id != null}">
	  	<c:if test="${company.logo == null}">
    		<h2><a id="buttonto" style="cursor: pointer; text-align:center;" >UPLOAD LOGO</a></h2>
	  		 <ul class="uploadImageNote">
	  		 	<li>Click on Upload Logo to Attach Image</li>
	  		 	<li>229 x 119 is the most advisable size of image that should be uploaded.</li>
	  		 </ul>
	 	</c:if>
	  	<c:if test="${company.logo != null}">
	    	<h2><a id="buttonto" style="cursor: pointer; text-align:center;" >EDIT LOGO</a></h2>
	  		 <ul class="uploadImageNote">
	  		 	<li>Click on Edit Logo to Attach Image</li>
	  		 	<li>229 x 119 is the most advisable size of image that should be uploaded.</li>
	  		 </ul>
	 	</c:if>
	  	<div id="showlogo" style="display: none">
		  	
		  	<form action="uploadlogo.do" method="post" enctype="multipart/form-data" onsubmit="return submitForm(this);">
		  		<input type="file" name="logo"/>
		  		<input type="hidden" value="" name="filename"/>
	  			<p class="uploadButtons">
		  			<input type="submit" value="Upload Logo!" class="btnBlue"/>
		  		</p>
		  	</form>
		</div>
	  </c:if>
	  
	  <script>
		$("#buttonto").click(function () {
		$("#showlogo").toggle("slow");
		});    
	  </script>
	 </div>

	</div><!--//sidenav-->
	
	<%--For Click Box Only--%>
	<%--by MEMO--%>
	<c:if test="${user.company != null && user.company.id == 257}">
		
		<div class="sidenav">				
		   	<h2>Shipping Summary</h2>
		   	
		   	<ul>
		   		<li>
					<a href="memberfiles.do?searchStatus=For Quotation"><img src="images/iQuote.jpg" /></a>
			  		<span>
			  			<a href="memberfiles.do?searchStatus=For Quotation"><strong>For Quotation</strong> Total :(${totalForQuotation})	</a>
			  		</span>
			 		 <div class="clear"></div>
				</li>
		   		<li>
					<a href="memberfiles.do?searchStatus=Order On Process"><img src="images/iOrder.jpg" /></a>
			  		<span>
			  			<a href="memberfiles.do?searchStatus=Order On Process"><strong>Order On Process</strong> Total :(${totalOrderOnProcess})	</a>
			  		</span>
			 		 <div class="clear"></div>
				</li>
				<li>
					<a href="memberfiles.do?searchStatus=Quote Sent"><img src="images/iQuoteSent.jpg" /></a>
			  		<span>
			  			<a href="memberfiles.do?searchStatus=Quote Sent"><strong>Quote Sent</strong> Total :(${totalQuoteSent})	</a>
			  		</span>
			 		 <div class="clear"></div>
				</li>
				<li>
					<a href="memberfiles.do?searchStatus=Paid"><img src="images/iPaid.jpg" /></a>
			  		<span>
			  			<a href="memberfiles.do?searchStatus=Paid"><strong>Paid</strong> Total :(${totalPaid})	</a>
			  		</span>
			 		 <div class="clear"></div>
				</li>
				<li>
					<a href="memberfiles.do?searchStatus=For Shipping"><img src="images/iForShipping.jpg" /></a>
			  		<span>
			  			<a href="memberfiles.do?searchStatus=For Shipping"><strong>For Shipping</strong> Total :(${totalForShipping})	</a>
			  		</span>
			 		 <div class="clear"></div>
				</li>
				<li>
					<a href="memberfiles.do?searchStatus=Delivered"><img src="images/iDelivered.jpg" /></a>
			  		<span>
			  			<a href="memberfiles.do?searchStatus=Delivered"><strong>Delivered</strong> Total :(${totalDelivered})	</a>
			  		</span>
			 		 <div class="clear"></div>
				</li>
		   </ul>	   
		</div>
		
	</c:if>	
	
</div>

	
  </div><!--//container-->
<script>
<c:if test="${company.name eq 'neltex'}">
	document.getElementById('manageCategoriesDiv').style.display = 'none';
</c:if>
</script>
</body>

</html>