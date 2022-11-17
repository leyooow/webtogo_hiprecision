
<%@include file="includes/header.jsp"  %>
<script language="JavaScript" src="../javascripts/overlib.js"></script>
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
			var subc = document.getElementById('sub_company').value;
			if('${user.userType.value}'  == 'System Administrator' || '${user.userType.value}'  == 'User Group Administrator'){
				if(document.getElementById('sub_company').value != ''){
					document.getElementById('reg_companyName').value = subc;
					document.getElementById('reg_companyName').value = subc;
					
				}else{
					document.getElementById('reg_companyName').value = document.getElementById('reg_comp').value;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
	function checkDate(dt,id){
		var selectedTimestamp = (new Date(dt).getTime());
		var timestamp = new Date().getTime() - (90 * 24 * 60 * 60 * 1000);
		var currentStamp = new Date().getTime();
		if(currentStamp > selectedTimestamp && selectedTimestamp > timestamp){
		    console.log(currentStamp + " > " + selectedTimestamp + " > " + timestamp);
		    document.getElementById('active').value = parseInt(document.getElementById('active').value)+1;
		    document.getElementById('agianActiveMembers').innerHTML = "Active (" + document.getElementById('active').value + ")";
		    document.getElementById('agianActiveIDS').value = document.getElementById('agianActiveIDS').value + "," + id;
		}else if(dt == ''){
			
		}else{
			document.getElementById('inactive').value = parseInt(document.getElementById('inactive').value)+1;
			document.getElementById('agianInActiveMembers').innerHTML = "Inactive (" + document.getElementById('inactive').value + ")";
		    document.getElementById('agianInActiveIDS').value = document.getElementById('agianInActiveIDS').value + "," + id;
		}
		
		
	}
	window.onload = function() {
		<c:if test="${company.name eq 'agian'}">
			<c:forEach items="${memListCount }" var="m">
			
				if(document.getElementById('utype').value == "User Group Administrator"){
					if(document.getElementById('userInfo1').value == "${m.reg_companyName}" || document.getElementById('userInfo1').value == "${m.info7}"){
						checkDate("${m.lastLogin}","${m.id}");
						document.getElementById('totalmemberss').value = parseInt(document.getElementById('totalmemberss').value)+1;
						document.getElementById('agianTotalMembers').innerHTML = "Total Members (" + document.getElementById('totalmemberss').value + ") - ";
						if("${m.verified}" == "true"){
							document.getElementById('verified').value = parseInt(document.getElementById('verified').value)+1;
							document.getElementById('agianVerifiedMembers').innerHTML = "Verified (" + document.getElementById('verified').value + ")";
							document.getElementById('agianVerifiedIDS').value = document.getElementById('agianVerifiedIDS').value + "," + "${m.id}";
						}else{
							document.getElementById('notverified').value = parseInt(document.getElementById('notverified').value)+1;
							document.getElementById('agianNotVerifiedMembers').innerHTML = "Not Verified (" + document.getElementById('notverified').value + ")";
							document.getElementById('agianNotVerifiedIDS').value = document.getElementById('agianNotVerifiedIDS').value + "," + "${m.id}";
						}
					}
				}else if(document.getElementById('utype').value == "User Sub Group Administrator"){
					if(document.getElementById('userInfo1').value == "${m.reg_companyName}"){
						checkDate("${m.lastLogin}","${m.id}");
						document.getElementById('totalmemberss').value = parseInt(document.getElementById('totalmemberss').value)+1;
						document.getElementById('agianTotalMembers').innerHTML = "Total Members (" + document.getElementById('totalmemberss').value + ") - ";
						if("${m.verified}" == "true"){
							document.getElementById('verified').value = parseInt(document.getElementById('verified').value)+1;
							document.getElementById('agianVerifiedMembers').innerHTML = "Verified (" + document.getElementById('verified').value + ")";
							document.getElementById('agianVerifiedIDS').value = document.getElementById('agianVerifiedIDS').value + "," + "${m.id}";
						}else{
							document.getElementById('notverified').value = parseInt(document.getElementById('notverified').value)+1;
							document.getElementById('agianNotVerifiedMembers').innerHTML = "Not Verified (" + document.getElementById('notverified').value + ")";
							document.getElementById('agianNotVerifiedIDS').value = document.getElementById('agianNotVerifiedIDS').value + "," + "${m.id}";
						}
					}
				}else{
					checkDate("${m.lastLogin}","${m.id}");
					document.getElementById('totalmemberss').value = parseInt(document.getElementById('totalmemberss').value)+1;
					document.getElementById('agianTotalMembers').innerHTML = "Total Members (" + document.getElementById('totalmemberss').value + ") - ";
					if("${m.verified}" == "true"){
						document.getElementById('verified').value = parseInt(document.getElementById('verified').value)+1;
						document.getElementById('agianVerifiedMembers').innerHTML = "Verified (" + document.getElementById('verified').value + ")";
						document.getElementById('agianVerifiedIDS').value = document.getElementById('agianVerifiedIDS').value + "," + "${m.id}";
					}else{
						document.getElementById('notverified').value = parseInt(document.getElementById('notverified').value)+1;
						document.getElementById('agianNotVerifiedMembers').innerHTML = "Not Verified (" + document.getElementById('notverified').value + ")";
						document.getElementById('agianNotVerifiedIDS').value = document.getElementById('agianNotVerifiedIDS').value + "," + "${m.id}";
					}
				}
			</c:forEach>
			
		</c:if>
	}
	
</script>
<body>
<input type="hidden" id="userInfo1" value="${user.info1 }">

<input type="hidden" id="utype" name="utype" value="${user.userType.value }"/>
<c:set var="utype" value="${user.userType.value }"/>
<c:set var="menu" value="members" />
<c:set var="submenu" value="member_listing" />
<input type="hidden" id="agianActiveIDS" value=""/>
<input type="hidden" id="agianInActiveIDS" value=""/>
<input type="hidden" id="agianVerifiedIDS" value=""/>
<input type="hidden" id="agianNotVerifiedIDS" value=""/>
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
	<%@include file="member_customFieldDownload.jsp"%>
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
						
						<c:if test="${param['evt'] == 'invalid'}">
							<c:choose>
								<c:when test="${company.name == 'cancun'}">
									<li><span class="actionMessage"><strong style="color:red">Billing Number already exists</strong></span></li>
								</c:when>
								<c:otherwise>
									<li><span class="actionMessage"><strong style="color:red">Username already exists</strong></span></li>
								</c:otherwise>
							</c:choose>
						</c:if>

					</ul>
				</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Members</strong></h1>
	
						
		<div class="clear"></div>

	  </div>
	  
	  <!-- AGIAN UPLOAD NOTIFICATION -->
	  <c:if test="${company.name eq 'agian' and not empty param.uploadstatus and not empty param.rowRead}"> 
	  	  <%@ include file="agianInclude/memberUploadStatus.jsp" %>
	  </c:if>
	  
	  <!--//pageTitle-->
	  <div class="downloadsBox"> 
	  	<c:set var="formAction" value="" />
	  	<c:choose>
			<c:when test="${company.id eq 319}">
				<c:set var="formAction" value="downloadGurkkaDetails" />
			</c:when>
			<c:when test="${company.id eq 393}"><%-- For Politiker --%>
				<c:set var="formAction" value="downloadPolitikerMembers" />
			</c:when>
			<c:otherwise>
				<c:set var="formAction" value="downloadCustomDetails" />
			</c:otherwise>
		</c:choose>
	  	
	  	<form action="${formAction}.do" method="post" name="downloadForm" >
	  		<c:choose>
		  		<c:when test="${company.id eq 319}"> <%--Gurkka--%>
					<input type="hidden" name="company_id" value="${company.id}"/>
					<table width="100%">
						<tr>
							<td width="90%">Download Members List in Excel Format</td>
							<td><input type="submit" value="Download" class="btnBlue" /></td>
						</tr>
					</table>
				</c:when>
				<c:when test="${company.id eq 393 }"><%-- For Politiker --%>
					<input type="hidden" name="company_id" value="${company.id}" />
					<table width="100%">
						<tr>
							<td width="90%">Download Politiker Member List in Excel Format</td>
							<td><input type="submit" value="Download" class="btnBlue" /></td>
						</tr>
					</table>
				</c:when>
		  		<c:otherwise>
		  			<%@include file="includes/memberDownloadSettings.jsp"  %>
					<c:forEach items="${list}" var="fieldName" >
						<c:if test="${fieldName ne 'Is Activated' and company.name eq 'agian' }">
							<input type="hidden" name="fieldName" value="${fieldName}">
						</c:if>
					</c:forEach>
					<c:if test="${company.name eq 'agian' }">
						<input type="hidden" name="fieldName" value="Active">
						<input type="hidden" name="fieldName" value="Inactive">
						<input type="hidden" name="fieldName" value="Verified">
						<input type="hidden" name="fieldName" value="Not Verified">
						<c:if test="${not empty param.list }">
							<input type="hidden" name="list_name" value="${param.list }">
						</c:if>
						<c:if test="${empty param.list }">
							<input type="hidden" name="list_name" value="all">
						</c:if>
					</c:if>
					<input type="hidden" name="company_id" value="${company.id}"/>
					<table width="100%"><tr><td width="90%">Download Members List in Excel Format</td>
					<td> <input type="submit" value="Download" class="btnBlue"/></td></tr>
					<tr <c:if test="${company.name eq 'agian'}">style="display:none;"</c:if>>
						<td colspan="2">or <a href="members.do?customFieldDownload=show">Download Custom Fields.</a>
						</td>
					</tr>
					</table>
		  		</c:otherwise>
	  		</c:choose>
		</form>
	
	  </div>	
	   <c:if test="${user.userType.value ne 'User Sub Group Administrator' 
	   and user.userType.value ne 'User Group Administrator' 
	   and param.list ne 'verified' 
	   and param.list ne 'notverified'
	   and param.list ne 'active'
	   and param.list ne 'inactive'
	   }">
				<ul class="pagination">

				   <%@include file="includes/pagingnew.jsp"  %>
			
				  </ul>
			</c:if>
	
	<c:if test="${not empty resendActivationMessage}">
		<div>${resendActivationMessage}</div>
	</c:if>
	
	<c:choose>
	<c:when test="${company.name eq 'agian' }">
	
		<c:set var="active" value="0"/>
		<c:set var="inactive" value="0"/>
		<c:set var="verified" value="0"/>
		<c:set var="notverified" value="0"/>
		
		<input type="hidden" id="totalmemberss" value="0"/>
		<input type="hidden" id="active" value="0"/>
		<input type="hidden" id="inactive" value="0"/>
		<input type="hidden" id="verified" value="0"/>
		<input type="hidden" id="notverified" value="0"/>
		
		<ul class="pagination" style="float:left;">
			<li id="agianTotalMembers">Total Members (${fn:length(memListCount) }) - </li>
			<li><a id="agianActiveMembers" href="activemembers.do?list=active">Active (${active}) </a></li>
			<li><a id="agianInActiveMembers" href="inactivemembers.do?list=inactive">Inactive (${inactive })</a></li>
			<li><a id="agianVerifiedMembers" href="verifiedmembers.do?list=verified">Verified (${verified })</a></li>
			<li><a id="agianNotVerifiedMembers" href="notverifiedmembers.do?list=notverified">Not Verified (${notverified })</a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<ul class="pagination" style="float:left;">
			<li>Total Members (${totalMembers }) - </li>
			<li><a href="activemembers.do">Active (${totalActiveMembers }) </a></li>
			<li><a href="inactivemembers.do">Inactive (${totalInactiveMembers })</a></li>
			<li><a href="verifiedmembers.do">Verified (${totalVerifiedMembers })</a></li>
			<li><a href="notverifiedmembers.do">Not Verified (${totalNotVerifiedMembers })</a></li>
		</ul>
	</c:otherwise>
	</c:choose>
		
				  <div class="clear"></div>
 					<c:if test="${not boolNull}">
					<c:if test="${ boolSearch == true and empty memList }">
					
						<ul><li>No matches.</li></ul>
					</c:if>
										

					
					
					<c:if test="${ boolSearch == true and not empty memList }">
				 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable" id="companyMembersTable">
						<tr >
							<c:choose>
							<c:when test="${company.name eq 'agian' }">
								<th >Username</th>
							</c:when>
							<c:otherwise>
								<th >${(useBillingNumberAsUserName)?'Booking Number':'UserName' }</th>
							</c:otherwise>
							</c:choose>
							<c:if test="${company.id == 319}">
								<th >ID</th>
							</c:if>
							<th >Email</th>
							<th >Name</th>
							
							<c:choose>
								<c:when test="${fn:contains(company.name, 'hometherapist')}">
									<%--display nothing--%>
								</c:when>
								<c:otherwise>
									<th >Company</th>
								</c:otherwise>
							</c:choose>
							
							<th >Date Joined</th>
							<th >Action</th>
						</tr>
									<c:set var="count" value="0" />
								<c:forEach items="${memList}" var="member">
									<c:choose>
									<c:when test="${user.userType.value == 'User Group Administrator' or user.userType.value == 'User Sub Group Administrator'}">
										
										
										<c:if test="${user.info1 eq member.reg_companyName }">
										<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
										<c:set var="count" value="${count+1}" />
									 	
										<td>${member.username}</td>
										<c:if test="${company.id == 319}">
											<td>${memberCode[member.id]}</td>
										</c:if>
										<td>${member.email}</td>
										<td>${member.firstname} ${member.lastname}</td>
										
										<c:choose> 
											<c:when test="${fn:contains(company.name, 'hometherapist')}">
												<%--display nothing--%>
											</c:when>
											<c:otherwise>
												<td>
													<c:choose>
														<c:when test="${member.company.name=='apc' or member.company.name =='westerndigital' or member.company.name == 'agian'}">
															${member.reg_companyName}
														</c:when>
														<c:otherwise>
															${(member.company.name)==null ? 'None' : member.company.name}
														</c:otherwise>
													</c:choose>
												</td>
											</c:otherwise>
										</c:choose>
										
										<td>${(member.dateJoined)==null ? 'Null' : member.dateJoined }</td>
										<c:if test="${user.company.nameEditable=='Philippine College of Optometrists'}">
										<td>
									        <div id="note_${member.id}" align="center"><a href="javascript:void(0);" onmouseover="showMessage(${member.id});" onmouseout="return nd();"><img style="cursor:pointer;" src="images/note.jpg"></a> </div>
									        <div id="event_${member.id}" style="display: none;">
			                                  <c:forEach begin="0" end="${(fn:length(events) > 0) ? fn:length(events)-1 : 0}" var="i" step="1">
			                                    <c:set var="found" value="0"/>
			                                    <c:forEach begin="0" end="${(fn:length(pcoEventMember) > 0) ? fn:length(pcoEventMember)-1 : 0}" var="j" step="1">
			                                      <c:if test="${(pcoEventMember[j].eventID eq events[i].id) and member.id eq pcoEventMember[j].memberID}">
			                                        <c:set var="found" value="1"/>
			                                      </c:if>              
			                                     </c:forEach>  
			                                
			                                     <c:if test="${found eq 1}">
			                                       ${events[i].title}<br />
			                                     </c:if>                                
			                                   </c:forEach>		
									        </div>
			                                <script>
			                                  showNote(${member.id});
			                                </script>							
										</td>	
										</c:if>	
										
										
										
										<td>					
											<a href="editmember.do?member_id=${member.id}">Edit</a>
											<c:if test="${utype eq 'System Administrator' or utype eq 'User Group Administrator' or utype eq 'User Sub Group Administrator' or user.userType.value eq 'Super User' or user.userType.value eq 'WTG Administrator' or user.userType.value eq 'Company Administrator'}">
											 |
											<a href="deletemember.do?member_id=${member.id}"  onclick="return confirm('Do you really want to delete member ${member.username}?');">Delete</a>
											</c:if>		
										</td>
									</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										
										<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
										<c:set var="count" value="${count+1}" />
									 	
										<td>${member.username}</td>
										<c:if test="${company.id == 319}">
											<td>${memberCode[member.id]}</td>
										</c:if>
										<td>${member.email}</td>
										<td>${member.firstname} ${member.lastname}</td>
										
										<c:choose> 
											<c:when test="${fn:contains(company.name, 'hometherapist')}">
												<%--display nothing--%>
											</c:when>
											<c:otherwise>
												<td>
													<c:choose>
														<c:when test="${member.company.name=='apc' or member.company.name =='westerndigital' or member.company.name == 'agian'}">
															${member.reg_companyName}
														</c:when>
														<c:otherwise>
															${(member.company.name)==null ? 'None' : member.company.name}
														</c:otherwise>
													</c:choose>
												</td>
											</c:otherwise>
										</c:choose>
										
										<td>${(member.dateJoined)==null ? 'Null' : member.dateJoined }</td>
										<c:if test="${user.company.nameEditable=='Philippine College of Optometrists'}">
										<td>
									        <div id="note_${member.id}" align="center"><a href="javascript:void(0);" onmouseover="showMessage(${member.id});" onmouseout="return nd();"><img style="cursor:pointer;" src="images/note.jpg"></a> </div>
									        <div id="event_${member.id}" style="display: none;">
			                                  <c:forEach begin="0" end="${(fn:length(events) > 0) ? fn:length(events)-1 : 0}" var="i" step="1">
			                                    <c:set var="found" value="0"/>
			                                    <c:forEach begin="0" end="${(fn:length(pcoEventMember) > 0) ? fn:length(pcoEventMember)-1 : 0}" var="j" step="1">
			                                      <c:if test="${(pcoEventMember[j].eventID eq events[i].id) and member.id eq pcoEventMember[j].memberID}">
			                                        <c:set var="found" value="1"/>
			                                      </c:if>              
			                                     </c:forEach>  
			                                
			                                     <c:if test="${found eq 1}">
			                                       ${events[i].title}<br />
			                                     </c:if>                                
			                                   </c:forEach>		
									        </div>
			                                <script>
			                                  showNote(${member.id});
			                                </script>							
										</td>	
										</c:if>	
										
										
										
										<td>					
											<a href="editmember.do?member_id=${member.id}">Edit</a>
											<c:if test="${utype == 'System Administrator' or utype == 'User Group Administrator' or utype == 'User Sub Group Administrator' or user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
											 |
											<a href="deletemember.do?member_id=${member.id}"  onclick="return confirm('Do you really want to delete member ${member.username}?');">Delete</a>
											</c:if>		
										</td>
									</tr>
									</c:otherwise>
									</c:choose>
									
								</c:forEach>
							<%-- </c:otherwise>
							</c:choose> --%>
											
						
						
					</table>
					</c:if>
				</c:if>
				<c:if test="${ boolNull or (not boolNull and boolSearch == false)}">
				
				<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable" id="companyMembersTable">

						<tr >
							<c:choose>
							<c:when test="${company.name eq 'agian' }">
								<th >Username</th>
							</c:when>
							<c:otherwise>
								<th >${(useBillingNumberAsUserName)?'Booking Number':'UserName' }</th>
							</c:otherwise>
							</c:choose>
							<th >Email</th>
							<c:if test="${company.id == 319}">
								<th >ID</th>
							</c:if>
							<th >Name</th>
							
							<c:choose>
								<c:when test="${fn:contains(company.name, 'hometherapist')}">
									<%--display nothing--%>
								</c:when>
								<c:otherwise>
									<th >Company</th>
								</c:otherwise>
							</c:choose>

							<th >Date Joined</th>
							<th >Action</th>
						</tr>
							<c:set var="count" value="0" />
						<c:forEach items="${members}" var="member">
						<c:choose>
						<c:when test="${user.userType.value == 'User Group Administrator'}">
										
								<c:if test="${user.info1 eq member.reg_companyName or user.info1 eq member.info7}">
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
							<c:set var="count" value="${count+1}" />
					
							<td>${member.username}</td>
							<td>${member.email}</td>
							<c:if test="${company.id == 319}">
								<td>${memberCode[member.id]}</td>
							</c:if>
							<td>${member.firstname} ${member.lastname}</td>
							
							<c:choose> 
								<c:when test="${fn:contains(company.name, 'hometherapist')}">
									<%--display nothing--%>
								</c:when>
								<c:otherwise>
									<td>
										<c:choose>
											<c:when test="${member.company.name=='apc' or member.company.name =='westerndigital'}">
												${member.reg_companyName}
											</c:when>
											<c:when test="${member.company.name=='agian'}">
												${member.reg_companyName}
											</c:when>
											<c:otherwise>
												${(member.company.name)==null ? 'None' : member.company.name}
											</c:otherwise>
										</c:choose>
									</td>
								</c:otherwise>
							</c:choose>
							
							<td>${(member.dateJoined)==null ? 'Null' : member.dateJoined }</td>
							<td>	
							<c:if test="${company.companySettings.hasReferrals}">
								<a href="editmember.do?member_id=${member.id}">
								<a href="referrals.do?referrerId=${member.id}&filterByReferralStatus=${(filterByReferralStatus==null)?1:filterByReferralStatus}">
								View Referrals | </a>	
							</c:if>	
								<a href="editmember.do?member_id=${member.id}">
									<c:choose>
										<c:when test="${fn:contains(company.name, 'gurkka')}">
											View
										</c:when>
										<c:otherwise>
											Edit
										</c:otherwise>
									</c:choose>
								</a>
								<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or  utype eq 'User Group Administrator' or  utype eq 'User Sub Group Administrator'}">
								 |
								<a href="deletemember.do?member_id=${member.id}"  onclick="return confirm('Do you really want to delete member ${member.username}?');">Delete</a>
								</c:if>	
								<c:choose>
									<c:when test="${fn:contains(company.name, 'gurkka') or fn:contains(company.name, 'hometherapists')}">
										<c:if test="${not member.activated}">
											| <a href="resendactivation.do?member_id=${member.id}">Resend Activation</a>										
										</c:if>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>	
							</td>
						</tr>
							</c:if>
						</c:when>
						<c:when test="${user.userType.value == 'User Sub Group Administrator'}">
							
								<c:if test="${user.info1 eq member.reg_companyName}">
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
							<c:set var="count" value="${count+1}" />
					
							<td>${member.username}</td>
							<td>${member.email}</td>
							<c:if test="${company.id == 319}">
								<td>${memberCode[member.id]}</td>
							</c:if>
							<td>${member.firstname} ${member.lastname}</td>
							
							<c:choose> 
								<c:when test="${fn:contains(company.name, 'hometherapist')}">
									<%--display nothing--%>
								</c:when>
								<c:otherwise>
									<td>
										<c:choose>
											<c:when test="${member.company.name=='apc' or member.company.name =='westerndigital'}">
												${member.reg_companyName}
											</c:when>
											<c:when test="${member.company.name=='agian'}">
												${member.reg_companyName}
											</c:when>
											<c:otherwise>
												${(member.company.name)==null ? 'None' : member.company.name}
											</c:otherwise>
										</c:choose>
									</td>
								</c:otherwise>
							</c:choose>
							
							<td>${(member.dateJoined)==null ? 'Null' : member.dateJoined }</td>
							<td>	
							<c:if test="${company.companySettings.hasReferrals}">
								<a href="editmember.do?member_id=${member.id}">
								<a href="referrals.do?referrerId=${member.id}&filterByReferralStatus=${(filterByReferralStatus==null)?1:filterByReferralStatus}">
								View Referrals | </a>	
							</c:if>	
								<a href="editmember.do?member_id=${member.id}">
									<c:choose>
										<c:when test="${fn:contains(company.name, 'gurkka')}">
											View
										</c:when>
										<c:otherwise>
											Edit
										</c:otherwise>
									</c:choose>
								</a>
								<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or  utype eq 'User Group Administrator' or  utype eq 'User Sub Group Administrator'}">
								 |
								<a href="deletemember.do?member_id=${member.id}"  onclick="return confirm('Do you really want to delete member ${member.username}?');">Delete</a>
								</c:if>	
								<c:choose>
									<c:when test="${fn:contains(company.name, 'gurkka') or fn:contains(company.name, 'hometherapists')}">
										<c:if test="${not member.activated}">
											| <a href="resendactivation.do?member_id=${member.id}">Resend Activation</a>										
										</c:if>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>	
							</td>
						</tr>
							</c:if>
						</c:when>
						<c:otherwise>
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
							<c:set var="count" value="${count+1}" />
					
							<td>${member.username}</td>
							<td>${member.email}</td>
							<c:if test="${company.id == 319}">
								<td>${memberCode[member.id]}</td>
							</c:if>
							<td>${member.firstname} ${member.lastname}</td>
							
							<c:choose> 
								<c:when test="${fn:contains(company.name, 'hometherapist')}">
									<%--display nothing--%>
								</c:when>
								<c:otherwise>
									<td>
										<c:choose>
											<c:when test="${member.company.name=='apc' or member.company.name =='westerndigital'}">
												${member.reg_companyName}
											</c:when>
											<c:when test="${member.company.name=='agian'}">
												${member.reg_companyName}
											</c:when>
											<c:otherwise>
												${(member.company.name)==null ? 'None' : member.company.name}
											</c:otherwise>
										</c:choose>
									</td>
								</c:otherwise>
							</c:choose>
							
							<td>${(member.dateJoined)==null ? 'Null' : member.dateJoined }</td>
							<td>	
							<c:if test="${company.companySettings.hasReferrals}">
								<a href="editmember.do?member_id=${member.id}">
								<a href="referrals.do?referrerId=${member.id}&filterByReferralStatus=${(filterByReferralStatus==null)?1:filterByReferralStatus}">
								View Referrals | </a>	
							</c:if>	
								<a href="editmember.do?member_id=${member.id}">
									<c:choose>
										<c:when test="${fn:contains(company.name, 'gurkka')}">
											View
										</c:when>
										<c:otherwise>
											Edit
										</c:otherwise>
									</c:choose>
								</a>
								<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator' or user.userType.value == 'User Group Administrator' or user.userType.value == 'User Sub Group Administrator'}">
								 |
								<a href="deletemember.do?member_id=${member.id}"  onclick="return confirm('Do you really want to delete member ${member.username}?');">Delete</a>
								</c:if>	
								<c:choose>
									<c:when test="${fn:contains(company.name, 'gurkka') or fn:contains(company.name, 'hometherapists')}">
										<c:if test="${not member.activated}">
											| <a href="resendactivation.do?member_id=${member.id}">Resend Activation</a>										
										</c:if>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>	
							</td>
						</tr>
						</c:otherwise>
						</c:choose>
						
						</c:forEach>
					</table>
				</c:if> 
			
			 <c:if test="${user.userType.value ne 'User Sub Group Administrator' 
			   and user.userType.value ne 'User Group Administrator' 
			   and param.list ne 'verified' 
			   and param.list ne 'notverified'
			   and param.list ne 'active'
			   and param.list ne 'inactive'
			   }">
				<ul class="pagination">

				   <%@include file="includes/pagingnew.jsp"  %>
			
				  </ul>
			</c:if>
	</div><!--//mainContent-->
	</c:if>
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator' or user.userType.value == 'User Group Administrator' or user.userType.value == 'User Sub Group Administrator'}">
			
			
				
	<div class="sidenav">
	
	<c:if test="${company.companySettings.hasReferrals}">		
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
						<td colspan="3" align="right">
							<input type="submit" value="Download All Referrals" class="btnBlue"/>
							<input type="hidden" name="referrerId" value="${referrerId}"/>
						</td>
					</tr>
				</table>
			</form>
			<form action="downloadReferrals.do" method="post" > 
				<table width="100%" >
						<tr><th colspan="3" ></th></tr>
						<tr><th colspan="3" align="left">Filter by Rewards Claim</th></tr>
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
	
	
	
	<c:choose>
		<c:when test="${company.name == 'cancun'}">
			<form name="newMemberForm" method="post" action="saveAndAllowSameEmail.do" onsubmit="return submitForm(this);">
		</c:when>
		<c:otherwise>
			<form name="newMemberForm" method="post" action="savemember.do" onsubmit="return submitForm(this);">
		</c:otherwise>
	</c:choose>
	
	
	
	<br>
	<h2>Add New Member</h2>
		<input type="hidden" name="member_id"  value="${member2.id}"/>
		<table width="100%" >
		
			<c:set var="memberCompanyId" value="${(member2.company != null) ? member2.company.id : 0 }"></c:set>

			<tr>
				<c:choose>
							<c:when test="${company.name eq 'agian' }">
								<td>Username<br /><input type="text" id="username" name="member2.username" value="${member2.username}" class="w200"></td>
							</c:when>
							<c:otherwise>
								<td >${(useBillingNumberAsUserName)?'Booking Number':'UserName' }<br /><input type="text" id="username" name="member2.username" value="${member2.username}" class="w200"></td>
							</c:otherwise>
							</c:choose>
			
			
				
			</tr>
			<c:choose>
			<c:when test="${company.name eq 'cancun' or company.name eq 'agian'}">
				
					<tr style="display:none;">
						<td ><input type="hidden" id="password" name="password" value="cancun" class="w200"></td>
					</tr>
			
			</c:when>
			<c:otherwise>
					<tr>
						<td >Password<br /><input type="text" id="password" name="password" value="${password}" class="w200"></td>
					</tr>
			</c:otherwise>
			</c:choose>
			<c:if test="${company.companySettings.hasPageFileRights }">
				<tr <c:if test="${company.name eq 'agian' }">style="display:none"</c:if>>
					<td >Member Type<br />
						<select name="memberTypeId" id="memberType">
							<option>-- please select a member type --</option>
							<c:forEach items="${memberTypes}" var="memberType">
								<option value="${memberType.id }" ${(member2.memberType.id eq memberType.id) ? 'SELECTED' : '' }>${memberType.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:if>
			<tr>
				<td >Email Address<br /><input type="text" id="emailAdd" name="member2.email" value="${member2.email}" class="w200"></td>
			</tr>
			<tr>
				<td >First Name<br /><input type="text" id="firstname" name="member2.firstname" value="${member2.firstname}" class="w200"></td>
			</tr>
			<tr>
				<td >Last Name<br /><input type="text" id="lastname" name="member2.lastname" value="${member2.lastname}" class="w200"></td>
			</tr>
			
			<c:if test="${company.name eq 'agian' }">
					<tr>
						<td >Nick Name <br/><input type="text" id="nickname" name="nickname" value="${member2.info5 }" class="w200"></td>
					</tr>
				<tr>
					<td >Member Privilege<br />
						<select name="privilege" id="privilege">
							<option value="User">Limited Access</option>
							<option value="CompanyAdmin">Company Administrator</option>
							<option value="Admin">Administrator</option>
						</select>
					</td>
				</tr>
			</c:if>
			
			<c:if test="${company.name eq 'montero' }">
				<tr>
					<td>Branch<br />
						<select id="branch" name="categoryItem" value="${member2.categoryItem.id}" class="w200">
							<c:forEach items="${listCategoryItemMap['Dealers']}" var="catItem">
								<option value="${catItem.id}">${catItem.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:if>
			
			<c:if test="${company.name eq 'cancun'}">
			<tr>
				<td >Vacation Specialist<br /><input type="text" id="info1" name="member2.info1" value="${member2.info1}" class="w200"></td>
			</tr>
			</c:if>
			
			<c:if test="${company.name eq 'agian'}">
			<tr>
				<td >Company Name<br />
					<c:choose>
					<c:when test="${user.userType.value == 'System Administrator'}">
						<input type="hidden" id="reg_companyName" name="reg_companyName" value="">
						<select class="w200" id="reg_comp" required onchange="changeSubCompany();">
							<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem">
								<c:if test="${catItem.parent.parentCategory eq null}">
									<option value="${catItem.name}" <c:if test="${user.info1 eq catItem.name}">selected</c:if>>
									${catItem.name}</option>
								</c:if>
							</c:forEach>
	                 	</select>
					</c:when>
					<c:when test="${user.userType.value == 'User Group Administrator'}">
						<input id="reg_companyName" name="reg_companyName" class="w200" value="${user.info1 }" readonly>
					</c:when>
					<c:otherwise>
						<c:set var="subComp" value=""/>
						<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem">
								<c:if test="${catItem.name eq user.info1 }">
									<input type="hidden" value="${catItem.parent.name }">
									<c:set var="subComp" value="${catItem.parent.name }"/>
								</c:if>
						</c:forEach>
						<input class="w200" value="${subComp}" readonly>
					</c:otherwise>
					</c:choose>
					 
				</td>
			</tr>
			<tr>
				<td >Sub Company Name<br />
				
				<c:choose>
					<c:when test="${user.userType.value == 'System Administrator'}">
						<c:set var="hasChild" value=""/>
						<c:set var="firstComp" value=""/>
						<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem" varStatus="counter">
							<c:if test="${catItem.name eq user.info1}">
								<c:set var="firstComp" value="${catItem.name }"/>
							</c:if>
							<c:if test="${catItem.parent.name eq firstComp }">
								<c:set var="hasChild" value="true"/>
							</c:if>
						</c:forEach>
						<c:choose>
						<c:when test="${hasChild eq 'true' }">
							<select id="sub_company" name="sub_company" class="w200">
								<option value="${firstComp}">--Select Sub Company--</option>
								<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem">
									<c:if test="${catItem.parent.name eq firstComp}">
										<option value="${catItem.name}">
											${catItem.name}</option>
									</c:if>
								</c:forEach>
	                 		</select>
						</c:when>
						<c:otherwise>
							<select id="sub_company" name="sub_company" class="w200">
								<option value="">--Select Sub Company--</option>
		                 	</select>
						</c:otherwise>
						</c:choose>
						
					</c:when>
					<c:when test="${user.userType.value == 'User Group Administrator'}">
						<select id="sub_company" name="sub_company" class="w200" required>
							<option value="${user.info1 }">--Select Sub Company--</option>
							<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem">
								<c:if test="${catItem.parent.parentCategory ne null}">
									<option value="${catItem.name}" <c:if test="${user.info1 eq catItem.name}">selected</c:if>>
										${catItem.name}</option>
									</c:if>
							</c:forEach>
	                 	</select>
					</c:when>
					<c:otherwise>
						<%-- <select id="reg_companyName" name="reg_companyName" class="w200" required>
							<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem">
								<c:if test="${catItem.parent.parentCategory ne null}">
									<option value="${catItem.name}" <c:if test="${user.info1 eq catItem.name}">selected</c:if>>
										${catItem.name}</option>
									</c:if>
							</c:forEach>
	                 	</select> --%>
	                 	<input id="reg_companyName" name="reg_companyName" class="w200" value="${user.info1}" readonly>
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
			<tr>	
				<td>Birthday<br/>
				<input type="date" id="birthday" name="birthday" value="${member2.info3}" class="w200"></td>	
			</tr>	
			<tr>	
				<td>Position<br/>
				<input type="text" id="reg_companyPosition" name="reg_companyPosition" value="${member2.info4}" class="w200"></td>	
			</tr>
			<tr>	
				<td>Mobile Phone Number<br/>
				<input type="text" id="mobile" name="mobile" value="${member2.mobile}" class="w200"></td>	
			</tr>
			<tr>	
				<td>Telephone Number<br/>
				<input type="text" id="landline" name="landline" value="${member2.landline}" class="w200"></td>	
			</tr>
			<tr>	
				<td>Areas of Expertise <br/>
				<input type="text" id="info4" name="info4" value="${member2.info5}" class="w200"></td>	
			</tr>	
			<tr>	
				<td>Certifications <br/>
				<input type="text" id="value2" name="value2" value="${member2.value2}" class="w200"></td>	
			</tr>	
			<tr>	
				<td>Affiliated Organizations <br/>
				<input type="text" id="value3" name="value3" value="${member2.value3}" class="w200"></td>	
			</tr>		
			</c:if>
			
			<%---------this part is for APC only -------------%>
			<c:if test="${company.name=='apc'}">
				<c:set var="style" > class="w200" </c:set>
					<%@include file="apcIncludes/apcMemberDetails.jsp" %>
			</c:if>
			<%---------this part is for PCO only -------------%>
			<c:if test="${pcoMember ne null}">
					<%@include file="pcoIncludes/pcoMemberDetails.jsp" %>
			</c:if>
			
			<c:if test="${not empty member2 and user.company.nameEditable=='Silver Finance Inc.'}">
			<tr>
				<td ><s:checkbox name="member2.verified" value="%{member2.verified}" theme="simple" /> Verified</td>
			
			</tr>
			</c:if>
			

			
			<tr>
				
				<td >
					<c:if test="${empty member2}">
							<input type="submit" name="submit" value="Add New" class="btnBlue">
					</c:if>
					<c:if test="${not empty member2}">
							<input type="submit" name="submit" value="Update" class="btnBlue">
					</c:if>
					
					<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='members.do'">
				
				</td>
			</tr>
		</table>
		</form>
	  
	  <!--START AGIAN-->
	  <c:if test="${company.name eq 'agian' }">
		  <h2>Add Members By Excel</h2>
		  <table width="100%">
		  	<form method="post" action="uploadMemberByBatch.do" enctype="multipart/form-data">
		  		<tr>
		  			<td><br/>Upload Member by Excel File: <br />
		  				<input type="file" name="members_upload"  />
		  			</td>
		  		</tr>
		  		<tr>
		  			<td>
		  				<input type="submit" name="submit" value="Upload" class="btnBlue"/>
		  			</td>
		  		</tr>
		  	</form>
		  </table>
	  </c:if>
	  <!-- END AGIAN -->
		
 	  <h2>Search List</h2>
 	  <table  width="100%">
 	  <form name="try" method="post" action="members.do" >
 	  <tr>
 	  <td>Search By:<br /><select id="searchBy" name="searchBy" class="w200">
									<option value="u" >${(useBillingNumberAsUserName)?'Booking Number':'UserName' }</option>
									<option value="f">First Name</option>
									<option value="l" >Last Name</option>
									<option value="e" >Email Address</option>
								</select></td></tr><tr>
 	  <td>Keyword:<br /><input type="hidden" id="which" name="which" value="search" /><input type="text" id="str" name="str"  class="w200"/></td>
								</tr><tr><td><input type="submit" value="Go" class="btnBlue" /></td>
 	  </tr>
 	  </form>
 	  </table>
 	   <h2>Sort List</h2>
 	    	  <form name="sort" method="post" action="members.do">
 	  <table  width="100%">

 	  <tr>
							<td>
								Sort Members By:
							<br />	
								<select id="memSort" name="memSort"  class="w200">
									<option value="username" <c:if test="${memSort == 'username'}">selected</c:if>>${(useBillingNumberAsUserName)?'Booking Number':'UserName' }</option>
									<option value="firstname" <c:if test="${memSort == 'firstname'}">selected</c:if>>First Name</option>
									<option value="lastname" <c:if test="${memSort == 'lastname'}">selected</c:if>>Last Name</option>
									<option value="email" <c:if test="${memSort == 'email'}">selected</c:if>>Email Address</option>
									<option value="dateJoined" <c:if test="${memSort == 'dateJoined'}">selected</c:if>>Date Joined </option>
								</select><br />
								<input type="radio" name="memOrder" value="true" checked /> Ascending 
								<input type="radio" name="memOrder" value="false" /> Descending 
								<input type="hidden" id="which" name="which" value="sort" />
							</td></tr><tr>
							<td>
								<input type="submit" value="Go" class="btnBlue">
							</td>
						</tr>
 	 
 	  </table>
 	   </form>
 	  
 	  

 	  
	</div><!--//sidenav-->
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container -->

<script>
	function changeSubCompany(){
		var c = document.getElementById('reg_comp').value;
		var htm = "<option value=''>--Select Sub Company--</option>"
		<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem">
			if(c == '${catItem.parent.name}'){
				htm += "<option value='${catItem.name}'>${catItem.name}</option>";
			}
		</c:forEach>
		
		document.getElementById('sub_company').innerHTML = htm;
	}
</script>

</body>

</html>