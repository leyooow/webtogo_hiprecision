<%@include file="includes/header.jsp"  %>
<script language="JavaScript" src="../javascripts/overlib.js"></script>
<c:set var="menu" value="members" />
<c:set var="submenu" value="member_listing" />
<script>
	function showNote(id)
{
	var content = document.getElementById('event_'+id).innerHTML;

	if(content.trim() == "")
	{
		document.getElementById('note_'+id).innerHTML = "";
	}
}


function showMessage(id) {
	var content = "Registered for the following:<br/> " + document.getElementById('event_'+id).innerHTML;
	overlib(content, STICKY, NOCLOSE)	
}


function redeemRequest(requestId){

	document.requestedReferral.requestId.value = requestId;

	document.requestedReferral.submit();

	return false;
	
}

	function submitForm(myForm) {

		var username = document.getElementById('username').value;
		var password = document.getElementById('password').value;
		var email = document.getElementById('emailAdd').value;
		var firstname = document.getElementById('firstname').value;
		var lastname = document.getElementById('lastname').value;
		
		var emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(username.length == 0) {
			messages += "* Username name not given\n";
			error = true;
		}
		
		if(password.length == 0) {
			messages += "* Password not given\n";
			error = true;
		}
		
		if(email.length != 0){
			if (email.indexOf(",")== -1)
			 {
				if	(email.search(emailRegEx) == -1 ){
					messages += "* Email not in valid format\n";
		  			error = true;
				}
			 }else{
						
				var emailArray = email.split(",");
				var part_num=0;
				while (part_num < emailArray.length)
	 			{
		 			if	(emailArray[part_num].search(emailRegEx) == -1 ){			 			
						messages += "* One of the email is not in valid format\n";
			  			error = true;
			  			break;
					}
					part_num+=1;
				}
			}
		}
		
		else {
			messages += "* Email not given\n";
  			error = true;
		}
		
		if(firstname.length == 0) {
			messages += "* First name not given\n";
			error = true;
		}
		
		if(lastname.length == 0) {
			messages += "* Last name not given\n";
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
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
</script>
<body>


<c:choose>
	<c:when test="${memSort ne null}" >
		<c:set var="pagingAction" value="members.do?memSort=${memSort}&searchBy=${x}&str=${y}" />
	</c:when>
	<c:otherwise>
		<c:set var="pagingAction" value="members.do?memSort=username" />
	</c:otherwise>
</c:choose>
<c:set var="firstParamGiven" value="${true}" />

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
	<c:if test="${customFieldDownload != null}">	
	<div id="mainContent">
	</div>
	</c:if>	
	
	<c:if test="${customFieldDownload == null}">	
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
				<c:if test="${param['evt'] != null}">
					<ul>
			 			
						<c:if test="${param['evt'] == 'save'}">
							<li><span class="actionMessage">Event was successfully created.</span></li>
						</c:if> 
						
						<c:if test="${param['evt'] == 'deleteFailed'}">
							<li><span class="errorMessage">Event was not successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Event was successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Event was successfully updated.</span></li>
						</c:if>

					</ul>
				</c:if>
				
	  <div class="pageTitle">
		<div style="float:left;width:30%">
		    <h1><strong>Referrals
		    </strong></h1>
	    </div>
	    <div style="float:right;width:25%;text-align:right;">
		    <h1>
		    	<strong  >Promo Code:&nbsp;&nbsp;${fn:trim(promoCode)}
		    	</strong>
			</h1>
		</div>
		
		<c:if test="${actionMode ne null}">
			<div class="clear"></div>
			<div style="float:left;width:60%">
			   	 <h1><strong><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;These ${(fn:length(referralList)==1)?'Referral was':'Referrals were'} successfully ${status.value}</font>
			    </strong></h1>
		    </div>
		 </c:if>
				
		<div class="clear"></div>

	  </div><!--//pageTitle-->
	  <c:if test="${referrerId eq null}">
	  <div style="float:left;width:65%">
	  <form action="referrals.do" method="post">
			<strong>Search By Referrer :&nbsp;</strong>
			
			<strong>Last Name&nbsp;:&nbsp;</strong><input name="lastName"  style="width:70px"  value="${lastName}"/>&nbsp;&nbsp;&nbsp;
			<strong>First Name&nbsp;:&nbsp;</strong><input name="firstName" style="width:70px" value="${firstName}"/>
			<%-- 
			<select name="member" style="width:200px" id="memberSelection"  onchange="findMembersOrder()">
				<option value="0">-members-</option>
				<c:forEach items="${members}" var="member">
					<option value="${member.id}">${member.fullName}</option>
				</c:forEach>
			</select>
			--%>
			<input type="submit" value="Go" class="btnBlue" name="filterByReferrer">
		</form>
		<br>
	</div>
	</c:if>
	<div style="float:right;width:35%;text-align:right">
			<form action="referrals.do" method="post">
				<input type="hidden" name="referrerId" value="${referrerId}">
				<strong>Filter by Status:</strong>
				<select name="filterByReferralStatus">
					<c:if test="${referrerId eq null}">
						<option value="-1">--All-</option>
					</c:if>
					<c:forEach var="status" items="${ReferralStatus.REFERRAL_STATUSES}" varStatus="stat">
						<option value="${stat.count}" <c:if test="${filterByReferralStatus eq stat.count}">selected</c:if>>${status.value}</option>
					</c:forEach>
				</select>
				<input type="submit" value="Go" class="btnBlue" name="filterByReferrer">
			</form>
	</div>
	<div class="clear"></div>

	  
	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	  <c:set var="isSelected" value=""/>
	  
	   <c:if test="${actionMode ne null}">
	   	 	 <c:set var="isSelected" value="checked"/>
	   </c:if>
	   
	   <form action="updateReferralStatus.do" method="post">
	 
				  <div class="clear"></div>

					<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">

						<tr >
							<th >Date Created</th>
							<th >Referred By</th>
							<th >Name</th>
							<th >Contact #</th>
							<th >Email</th>
							<th >Reward</th>
							<th >Status</th>
							<th >Action Date</th>
							<c:if test="${filterByStatusString eq 'Pending'}">
							<th>&nbsp;</th>
							</c:if>
						</tr>
					<c:choose>
					<c:when test="${referralList ne null and fn:length(referralList) ne 0}">
					<c:set var="counter" value="0"/>
					<c:set var="found" value="false"/>
					<c:forEach items="${referralList}" var="referredMember">
					
				  		<c:if test="${counter>0}">
				       		<c:set var="previousReferral" value="${referralList[counter-1]}" />
				       		<c:choose>
				       			<c:when test="${filterByStatusString eq 'Requested' and previousReferral.requestId ne referredMember.requestId or fn:length(referralList) eq counter}">
				       				<tr>
					       				<td width="100%" style="text-align:right" colspan="9">
					       					<c:set var="found" value="true"/>
					       					
					       						<input type="submit" name="actionMode" value="redeemed" class="btnBlue"  onclick="return redeemRequest(${previousReferral.requestId})"/>
					       					
					       				</td>
				       				</tr>
				       			</c:when>
				       			<c:otherwise>
				       				<c:set var="found" value="false"/>
				       			</c:otherwise>
				       		</c:choose>
				       	</c:if>
						
				       	<tr>
				       		<td>${referredMember.createdOn}</td>
				       		<td>
				       		<%-- 
				       		<a href="?referrerId=${referredMember.referredBy.id}&filterByReferralStatus=${(filterByReferralStatus==null)?1:filterByReferralStatus}">
				       		--%>
				       		${referredMember.referredBy.fullName}</td>
				       		<td>${referredMember.fullname}</td>
				       		<td>${referredMember.contactNumber}</td>
				       		<td>${referredMember.email}</td>
				       		<td>
				       			<c:choose>
					       			<c:when test="${referrerId eq null and (referredMember.status.value eq 'Requested' or referredMember.status.value eq 'Redeemed')}">
					       				${referredMember.reward}
					       			</c:when>
						       		<c:otherwise>
							       		<c:if test="${referredMember.status.value eq 'Requested' or referredMember.status.value eq 'Redeemed'}">					       			
							       			<c:choose>
							       				<c:when test="${counter>0 and referralList[counter-1].requestId eq referralList[counter].requestId}">
							       					
							       				</c:when>
							       				<c:otherwise>
							       					${referredMember.reward}
							       				</c:otherwise>
						       				</c:choose>
							       		</c:if>
						       		</c:otherwise>
					       		</c:choose>
				       		</td>
				       		<td align="center">
				       			<c:if test="${referredMember.status.value ne 'Pending'}">
				       				<img src="images/smiles/${referredMember.status.img}" style="height:20px;width:20px;" title="${referredMember.status.value}" alt="${referredMember.status.value}">
				       			</c:if>
				       		</td>
				       		<td>
				       			${referredMember.createdOn}
				       		</td>
				       		<c:if test="${filterByStatusString eq 'Pending'}">
						       		<td style="width:5%;text-align:right">	
						       				<input type="checkbox" name="referralIds" value="${referredMember.id}" ${isSelected}>
						       		</td>
					       	</c:if>
				       	</tr>
				       	
				       	<c:set var="counter" value="${counter+1}"/>
      			 	</c:forEach>
      			 	
      			 	<c:if test="${filterByStatusString eq 'Requested' }">
	  			 		<tr>
		       				<td width="100%" style="text-align:right" colspan="9">
		       					<c:set var="found" value="true"/>
		       					<c:if test="${fn:length(referralList) > 0 and referralList[fn:length(referralList)-1].status.value == 'Requested'}">
		       						<input type="submit" name="actionMode" value="redeemed" class="btnBlue" onclick="return redeemRequest(${referralList[fn:length(referralList)-1].requestId})"/>
		       					</c:if>
		       				</td>
	       				</tr>
       				</c:if>
      			 	
      			 	</c:when>
      			 	<c:otherwise>
      			 			<tr><td colspan="9" style="text-align:center;"><strong>
      			 			No ${filterByStatusString} Referrals</strong></td></tr>
      			 	</c:otherwise>
      			 	</c:choose>
      			 	
      			 	
      			 	<c:if test="${ filterByStatusString eq 'Pending'}">
      			 		<tr >
							<th colspan="9" align="right">
							<c:if test="${actionMode ne null}">
								<a href="referrals.do">view all&nbsp;&nbsp;</a>
							</c:if>
								<input type="hidden" name="referrerId"+ value="${referrerId}"/>
								
								<c:choose>
									<c:when test="${referralList ne null and fn:length(referralList) ne 0}">
								
										<c:if test="${filterByStatusString eq 'Pending'}">
											<input type="submit" name="actionMode" value="approve" class="btnBlue"/>
										</c:if>
										<c:if test="${filterByStatusString eq 'Pending'}">
											<input type="submit" name="actionMode" value="reject" class="btnBlue"/>
										</c:if>
									</c:when>
									<c:otherwise>
									
									</c:otherwise>
								</c:choose>
								
							</th>
						</tr>
					</c:if>
					</table>
					
					  
	  </form>
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>

	  
	  
	</div><!--//mainContent-->
	</c:if>
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
				
	<div class="sidenav">
	<h2>Status Legend:</h2>
	<c:if test="${company.companySettings.hasReferrals}">
		<table width="100%" >
			<c:forEach var="status" items="${ReferralStatus.REFERRAL_STATUSES}">
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="20%">
						
						<c:if test="${status.value ne 'Pending'}">
							<img src="images/smiles/${status.img}"/>
						</c:if>
					</td>
					<td>${status}</td>
				</tr>
					
			</c:forEach>
		</table>
		
	<h2>Reports</h2>
	<form action="downloadReferrals.do" method="post" > 
		<table width="100%" >
				<tr><th colspan="3" ></th></tr>
				<c:if test="${referrerId!=null}">
					<tr><th colspan="3" align="left">Download By Referrer</th></tr>
					<tr>
						<td width="5%">&nbsp;</td>
						<td>
						<input type="hidden" name="referrerId" value="${referrerId}"/>
						<strong>&nbsp;&nbsp;&nbsp;&nbsp;${currentReferrer.fullName}</strong>
						</td>
						<td><input type="submit" value="Download" class="btnBlue"></td>
					</tr>
					<tr><th colspan="3" ></th></tr>
				</c:if>
				<tr><th colspan="3" ></th></tr>
		</table>
	</form>
	<form action="downloadReferrals.do" method="post" > 
		<table width="100%" >
			<tr>
				<td colspan="3" align="left">
					<input type="submit" value="Download All Referrals" class="btnBlue"/>
					<input type="hidden" name="referrerId" value="${referrerId}"/>
				</td>
			</tr>
		</table>
	</form>
	<form action="downloadReferrals.do" method="post" > 
		<table width="100%" >
				<tr><th colspan="3" ></th></tr>
				<tr><th colspan="3" align="right">Filter by Rewards Claim</th></tr>
				<tr>
					<td width="5%">&nbsp;</td>
					<td >
					<select name="reward">
						<%--this part is hardcoded --%>
		       				<option value="Amazon Gift Card">Amazon Gift Card</option>
		       				<option value="Exxon Gas Card">Exxon Gas Card</option>
		       				<option value="Florida 3D2N Package">Florida 3D2N Package</option>
		       				<option value="Macy's Gift Card">Macy's Gift Card</option>
		       			</select>
					</td>
					<td>
					<input type="hidden" name="referrerId" value="${referrerId}"/>
					<input type="submit" value="Download" class="btnBlue"></td>
				</tr>
		</table>
		 </form>
		  
		  
		  
	</c:if>
	
	

	<form name="requestedReferral" method="get" action="updateReferralRequestedStatus.do">	
		<input type="hidden" name="filterByReferralStatus" value="4"/>
		<input type="hidden" name="requestId"/>
		<input type="hidden" name="actionMode" value="redeemed"/>
		<input type="hidden" name="referrerId" value="${referrerId}"/>
	</form>
		
 	
 

 	  </table>
 	 
	</div><!--//sidenav-->
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>