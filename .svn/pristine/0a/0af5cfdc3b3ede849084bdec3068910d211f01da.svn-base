<%@include file="includes/header.jsp"  %>
<link href="../admin/css/modal.css" rel="stylesheet" type="text/css" />
<body  onload="document.getElementById('company_id').focus();">
	<div class="container">
	<c:set var="menu" value="dashboard" />
	<%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	<div class="contentWrapper" id="contentWrapper">
	    <div class="mainContent">
			<%@include file="../../../../common/chat/chat_admin_v2.jsp"%>
		</div><!--//mainContent-->
		<div class="sidenav">
		<h1>
		  	<c:if test="${user.company != null && user.company.logo != null}">		
				<img src="${contextParams['IMAGE_PATH']}/images/${user.company.logo}" width="229" height="119"/>
			</c:if>
		</h1>
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
	<c:if test="${latestSubmissionsCount > 0}" >
			<li>
	
			  <img src="images/iInquiries.gif" />
	
			  
				<c:if test="${user.userType.value ne 'Company Staff'}">
									
					<span><a href="formsubmissions.do?filterDays=3"><strong>Inquiries</strong> Last 72 Hours(${latestSubmissionsCount})</a></span>
									
				</c:if>
			  <div class="clear"></div>
	
			</li>
	</c:if>
	<c:if test="${fn:length(logList) > 0}" > 
		    <li>
	
			  <a href="/"><img src="images/iLogs.gif" /></a>
	
			  <span><a href="logs.do"><strong> Logs</strong> 
		
					Last 7 days(${fn:length(logList)})
					
			
			  </a>
			  </span>
	
			  <div class="clear"></div>
	
			</li>
	</c:if>
			<li>
	
			  <img src="images/iStats.gif" />
	
			  <span><a href="http://72.249.183.248/awstats/awstats.pl?config=${company.domainName}" target="Statistics"><strong>Statistics</strong> View Site Statistics</a></span>
	
			  <div class="clear"></div>
	
			</li>
				
								<c:if test="${user.userType.value ne 'Company Staff' and latest72Comment > 0}">
			<li>
	
			  <img src="images/iComments.gif" />
	
			  
			
							
									
										<span><a href="comment.do?filterDays=3"><strong>Comments</strong> Last 72 Hours(${latest72Comment})</a></span>
									
							
							
			  <div class="clear"></div>
	
			</li>
			
								</c:if>
	
		  </ul>
	
		</div><!--//sidenav-->
	</div>
	
		
  </div><!--//container-->

</body>

</html>