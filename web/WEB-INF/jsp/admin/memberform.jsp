<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />

<script>
	function submitForm(myForm) {
		var subc = document.getElementById('sub_company').value;
		if('${user.userType.value}'  == 'System Administrator'){
			if(document.getElementById('sub_company').value != ''){
				document.getElementById('reg_companyName').value = subc;
				document.getElementById('reg_companyName').value = subc;
			}else{
				document.getElementById('reg_companyName').value = document.getElementById('reg_comp').value;
			}
		}
		var username = document.getElementById('username').value;
		var password = document.getElementById('password').value;
		var email = document.getElementById('emailAdd').value;
		var firstname = document.getElementById('firstname').value;
		var lastname = document.getElementById('lastname').value;
		
		var emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(username.length == 0) {
			messages += "* ${(useBillingNumberAsUserName)?'Billing Number':'UserName' } name not given\n";
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
</script>

<div style="width:50px;float:left">
<table  class="companiesTable">
	<tr>
		<td style="border:0px;">

		<c:choose>
			<c:when test="${user.company.nameEditable=='Philippine College of Optometrists'}">
				<form name="newMemberForm" method="post" action="updatePCOMemberDetails.do" onsubmit="return submitForm(this);">
			</c:when>
			<c:otherwise>
				<form name="newMemberForm" method="post" action="savemember.do" onsubmit="return submitForm(this);">
			</c:otherwise>
		</c:choose>
		
		<input type="hidden" name="member_id"  value="${member2.id}"/>
		<table width="50%" border="0"  class="companiesTable">
			<c:set var="memberCompanyId" value="${(member2.company != null) ? member2.company.id : 0 }"></c:set>
<%-- 		<tr>
				<td width="1%" nowrap>Company Name </td>
				<td width="10px"></td>
				<td>
				<select id="company_id" name="company_id" class="combobox1">
					<option value="">- Select Company -</option>
					<c:forEach items="${companies}" var="c">
						<option value="${c.id}" <c:if test="${c.id == memberCompanyId}">selected</c:if>>${c.nameEditable}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
 --%>
			<c:choose>
				<c:when test="${fn:contains(company.name, 'gurkka') or fn:contains(company.name, 'gurkkatest')}">
					<%@include file="companyIncludes/gurkkaMemberDetails.jsp" %>
				</c:when>
				<c:when test="${fn:contains(company.name, 'hometherapist')}">
					<%@include file="companyIncludes/myHTMemberDetails.jsp" %>
				</c:when>
				<c:when test="${company.name eq 'agian' }">
					<%-- <tr>
						<td width="1%" nowrap>Username<br>
						<input type="text" id="username" name="member2.username" value="${member2.username}" class="textbox3">
						</td>
					</tr> --%>
					<tr>
						<td width="1%" nowrap>Email Address<br>
						<input type="text" id="emailAdd" name="member2.email" value="${member2.email}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>First Name<br>
						<input type="text" id="firstname" name="member2.firstname" value="${member2.firstname}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Last Name<br>
						<input type="text" id="lastname" name="member2.lastname" value="${member2.lastname}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Member Privilege<br>
							<select name="privilege" id="privilege">
								<option value="User" <c:if test="${member2.info6 eq 'User' }">selected</c:if>>Limited Access</option>
								<option value="CompanyAdmin" <c:if test="${member2.info6 eq 'CompanyAdmin' }">selected</c:if>>Company Administrator</option>
								<option value="Admin" <c:if test="${member2.info6 eq 'Admin' }">selected</c:if>>Administrator</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Company Name<br />
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						<c:set var="subComp" value=""/>
						<c:choose>
					<c:when test="${user.userType.value == 'System Administrator'}">
						<input type="hidden" id="reg_companyName" name="reg_companyName" value="${member2.reg_companyName }">
						<select class="w200" id="reg_comp" required onchange="changeSubCompany();">
							
							<c:forEach items="${agianCompanies}" var="catItem">
									<c:if test="${catItem.name eq member2.reg_companyName }">
										<c:set var="subComp" value="${catItem.parent.name }"/>
									</c:if>
							</c:forEach>
							<c:forEach items="${agianCompanies}" var="catItem">
								<c:if test="${catItem.parent.parentCategory eq null}">
									<option value="${catItem.name}" <c:if test="${subComp eq catItem.name}">selected</c:if>
									<c:if test="${member2.reg_companyName eq catItem.name}">selected</c:if>>
									${catItem.name}</option>
								</c:if>
							</c:forEach>
	                 	</select>
					</c:when>
					<c:when test="${user.userType.value == 'User Group Administrator'}">
						<input class="w200" value="${user.info1 }" readonly>
					</c:when>
					<c:otherwise>
						
						<c:forEach items="${agianCompanies}" var="catItem">
								<c:if test="${catItem.name eq member2.reg_companyName }">
									<input type="hidden" value="${catItem.parent.name }">
									<c:set var="subComp" value="${catItem.parent.name }"/>
								</c:if>
						</c:forEach>
						<input type="text" class="w200" value="${subComp}" readonly>
					</c:otherwise>
					</c:choose>
						
						
						
						
						
						
						
						
						
						
						
							<%-- <select id="reg_companyName" name="reg_companyName" class="w200" required>
							<c:forEach items="${agianCompanies}" var="comp">
								<option value="${comp.name}" <c:if test="${comp.name eq member2.reg_companyName}"> selected </c:if> >${comp.name}</option>
							</c:forEach>
			                 </select> --%>
						</td>
					</tr>
					<tr>
				<td >Sub Company Name<br />
				<c:choose>
					<c:when test="${user.userType.value == 'System Administrator'}">
					
					<c:forEach items="${agianCompanies}" var="catItem">
									<c:if test="${catItem.name eq member2.reg_companyName }">
										<c:if test="${catItem.parent.parentCategory ne null }">
											<c:set var="subComp" value="${catItem.parent.name }"/>
										</c:if>
									</c:if>
							</c:forEach>
						<select id="sub_company" name="sub_company" class="w200">
							<option value="${subComp }">--Select Sub Company--</option>
							<c:forEach items="${agianCompanies}" var="catItem">
								<c:if test="${catItem.parent.name eq member2.reg_companyName  or catItem.parent.name eq subComp and catItem.parent.name ne 'Ayala' and catItem.parent.name ne 'Others'}">
									<option value="${catItem.name}" <c:if test="${member2.reg_companyName eq catItem.name}">selected</c:if>>
										${catItem.name}</option>
									</c:if>
							</c:forEach>
	                 	</select>
					</c:when>
					<c:when test="${user.userType.value == 'User Group Administrator'}">
						<select id="reg_companyName" name="reg_companyName" class="w200" required>
							<option value="${user.info1 }">--Select Sub Company--</option>
							<c:forEach items="${agianCompanies}" var="catItem">
								<c:if test="${catItem.parent.parentCategory ne null}">
									<option value="${catItem.name}" <c:if test="${member2.reg_companyName eq catItem.name}">selected</c:if>>
										${catItem.name}</option>
									</c:if>
							</c:forEach>
	                 	</select>
					</c:when>
					<c:otherwise>
						<%-- <select id="reg_companyName" name="reg_companyName" class="w200" required>
							<c:forEach items="${agianCompanies}" var="catItem">
								<c:if test="${catItem.parent.parentCategory ne null}">
									<option value="${catItem.name}" <c:if test="${member2.reg_companyName eq catItem.name}">selected</c:if>>
										${catItem.name}</option>
									</c:if>
							</c:forEach>
	                 	</select> --%>
	                 	<input id="reg_companyName" name="reg_companyName" class="w200" value="${member2.reg_companyName}" readonly>
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
					<tr>	
						<td width="1%" nowrap>Birthday<br/>
						<input type="date" id="birthday" name="birthday" value="${member2.info3}" class="w200"></td>	
					</tr>
					<tr>
						<td width="1%" nowrap>Position<br>
						<input type="text" id="reg_companyPosition" name="reg_companyPosition" value="${member2.reg_companyPosition}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Mobile Phone Number<br>
						<input type="text" id="mobile" name="mobile" value="${member2.mobile}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Telephone Number<br>
						<input type="text" id="landline" name="landline" value="${member2.landline}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Areas of Expertise<br>
						<input type="text" id="info4" name="info4" value="${member2.info4}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Certifications<br>
						<input type="text" id="certifications" name="value2" value="${member2.value2}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Affiliated Organizations<br>
						<input type="text" id="org" name="value3" value="${member2.value3}" class="textbox3">
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td width="1%" nowrap>${(useBillingNumberAsUserName)?'Billing Number':'UserName' }<br>
						<input type="text" id="username" name="member2.username" value="${member2.username}" class="textbox3">
						
						</td>
					</tr>
					<tr <c:if test="${company.name=='cancun' or fn:contains(company.name, 'gurkka')}">style="display:none"</c:if>>
						<td width="1%" nowrap>Password<br>
						<input type="text" id="password" name="password" value="${fn:trim(password)}" class="textbox3">
						</td>
					</tr>
					<tr>
						<td width="1%" nowrap>Email Address<br>
						<input type="text" id="emailAdd" name="member2.email" value="${member2.email}" class="textbox3"></td>
					</tr>
					<c:if test="${company.companySettings.hasPageFileRights }">
						<tr>
							<td>
								Member Type<br />
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
						<td width="1%" nowrap>First Name<br>
						<input type="text" id="firstname" name="member2.firstname" value="${member2.firstname}" class="textbox3"></td>
					</tr>
					<tr>
						<td width="1%" nowrap>Last Name<br>
						<input type="text" id="lastname" name="member2.lastname" value="${member2.lastname}" class="textbox3"></td>
					</tr>
				</c:otherwise>
			</c:choose>
			
			<c:if test="${company.name eq 'cancun'}">
			  <tr>
			    <td >Vacation Specialist<br /><input type="text" id="info1" name="member2.info1" value="${member2.info1}" class="textbox3"></td>
			  </tr>
			</c:if>
			<c:if test="${company.name eq 'drugasia'}">
			  	<tr>
					<td width="1%" nowrap><s:checkbox name="member2.seniorCitizen" value="%{member2.seniorCitizen}" theme="simple" /> Senior Citizen</td>
					<td width="10px"></td>
			  	</tr>
			</c:if>
			<%---------this part is for APC only -------------%>
			<c:if test="${company.name=='apc'}">
			  	<%@include file="apcIncludes/apcMemberDetails.jsp" %>
			</c:if>
			<%---------this part is for PCO only -------------%>
			<%-- -  IF THERE IS A PCO MEMBER-- --%>
			<c:if test="${pcoMember ne null}">
			  	<%@include file="pcoIncludes/pcoMemberDetails.jsp" %>
			</c:if>
			<%---------this part is for PCO only -------------%>
			<c:if test="${company.name=='aplic'}">
			  	<%@include file="aplicIncludes/aplicMemberDetails.jsp" %>
			</c:if>				
			<c:if test="${not empty member2 and user.company.nameEditable=='Silver Finance Inc.'}">
			  	<tr>
					<td width="1%" nowrap><s:checkbox name="member2.verified" value="%{member2.verified}" theme="simple" /> Verified</td>
					<td width="10px"></td>
			  	</tr>
			</c:if>
			<tr>
<%-- 		<tr>
				<td nowrap>&nbsp;</td>
				<td></td>
				<td>	
					<s:checkbox id="activated" name="member2.activated" value="%{member2.activated}" theme="simple" /> <i><b>This account is activated.</i>	
				</td>
			</tr>
 --%>
			<tr>
				<td style="border: 0px;">
				<div style="float:right;width:35%">
					<c:if test="${empty member2}">
							<input type="submit" name="submit" value="Add New"  class="btnBlue">
					</c:if>
					<c:if test="${not empty member2}">
							<input type="submit" name="submit" value="Update" class="btnBlue">
					</c:if>
					&nbsp;
					<input type="button"  class="btnBlue" name="cancel" value="Cancel" onclick="window.location='members.do'">
					</div>
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>
</div>

<c:if test="${not empty member2 and user.company.nameEditable=='Philippine College of Optometrists'}">
	<div style="float:right;width:49%">
		<h3>registered in ${fn:length(pcoEventMember)} Event<c:if test="${fn:length(pcoEventMember)>1}">s</c:if></h3>
		<table  class="companiesTable"  width="100%">
			 <tr> 
			    <td colspan="3" width="100%">
			    <table  class="companiesTable"  width="100%">
			    <c:if test="${fn:length(pcoEventMember)>1}">
			    	 <tr  class="headbox">
                  		<th>Title</th>
                  		<th>payment mode</th>
                  		<th>Status</th>
                  		<th>Attended</th>
                  		<th>Action</th>
                  	</tr>
			    </c:if>
                <c:forEach begin="0" end="${(fn:length(events) > 0) ? fn:length(events)-1 : 0}" var="i" step="1">
                  <c:set var="found" value="0"/>
                  
                 	<c:set var="paymentMode" value=""/> 
                 	<c:set var="paymentStatus" value=""/> 
                 	<c:set var="attended" value=""/> 
                 	<c:set var="eventId" value=""/> 
                 	
                  <c:forEach begin="0" end="${(fn:length(pcoEventMember) > 0) ? fn:length(pcoEventMember)-1 : 0}" var="j" step="1">
                    <c:if test="${pcoEventMember[j].eventID eq events[i].id}">
                      <c:set var="found" value="1"/>
                      <c:set var="paymentMode" > ${pcoEventMember[j].modeOfPayment} </c:set>
                      <c:set var="paymentStatus" > ${pcoEventMember[j].status} </c:set>
                      <c:set var="attended" > ${pcoEventMember[j].attended} </c:set>
                      <c:set var="eventId" > ${pcoEventMember[j].id} </c:set>
                    </c:if>
                                  
                  </c:forEach>  
                  <c:if test="${found eq 1}">
                  	<tr>
                  		<td><a href="downloadCertificatePdf.do?id=${member2.id}&event_id=${events[i].id}" ><img src="images/iPdf.png"/> ${events[i].title}</a></td>
                  		<td>
                  		<c:choose>
                  			<c:when test="${paymentMode == '1'}">
                  				Bank
                  			</c:when>
                  			<c:otherwise>
                  				YesPayments
                  			</c:otherwise>
                  		</c:choose>
                  		</td>
                  		<td><c:choose>
                  			<c:when test="${paymentStatus == '0'}">
                  				Unpaid
                  			</c:when>
                  			<c:otherwise>
                  				Paid
                  			</c:otherwise>
                  		</c:choose>
                  		</td>
                  		<td>
                  		
                  		<c:choose>
                  			
                  			<c:when test="${attended == '0'}">
                  				No
                  			</c:when>
                  			<c:otherwise>
                  				Yes
                  			</c:otherwise>
                  			
                  		</c:choose>
                  		
                  		</td>
                  		<td>
                  			<div style="float:right;width:50%"><a href="editmember.do?member_id=${member2.id}&deleteEvent=${eventId}">delete</a></div>
                  			<div style="float:right;width:50%"><a href="editmember.do?member_id=${member2.id}&editEvent=${eventId}">edit</a></div>
                  		</td>
                  	</tr>
                  </c:if>                                
                </c:forEach>	
                </table>
                </td>
			  </tr>
			 
             </table>
          </div>
          
          <c:if test="${editEvent != null}">
          
          <div style="float:right;width:49%">
          <form action="editmember.do?member_id=${member2.id}" METHOD="post">
         		<br><br>
         		
         		<table  class="companiesTable"  width="100%">
         			<tr   class="headbox" >
         				<th colspan="2">Edit Registered event.</th>
         				
         			</tr>
         			<tr>
         				<td width="30%">Event Name</td><td>${eventTitle}</td>
         			</tr>
         			<tr>
         				<td width="30%">Mode Of Payment</td><td>
         					<c:choose>
                  			<c:when test="${paymentMode == '1'}">
                  				Bank
                  			</c:when>
                  			<c:otherwise>
                  				YesPayments
                  			</c:otherwise>
                  		</c:choose>
         				</td>
         			</tr>
         			<tr>
         				<td>Created On</td><td>${_pcoEventMember.createdOn}</td>
         			</tr>
         			<tr>
         				<td>Payment Status</td>
         				<td>
         				<select name="paymentStatus">
         					
         					<option <c:if test="${_pcoEventMember.status=='0'}"> selected </c:if> value="0">Unpaid</option>
         					<option <c:if test="${_pcoEventMember.status=='1'}"> selected </c:if> value="1">Paid</option>
         				</select>
         				</td>
         			</tr>
         			<tr>
         				<td>Attended the Event</td><td>
							<select name="attended">
         					<option <c:if test="${_pcoEventMember.attended=='0'}"> selected </c:if> value="0">No</option>
         					<option <c:if test="${_pcoEventMember.attended=='1'}"> selected </c:if> value="1">Yes</option>
         				</select>
						</td>
         			</tr>
         			<tr>
         				<td>&nbsp;</td>
         				<td>
         					<div style="float:left">
         					<input type="hidden" name="eventId" value="${_pcoEventMember.id}">
         					<input type="submit" name="" value=" update " class="btnBlue">
         					</div>
         				</td>
         			</tr>
         		</table>
         	</form>
          </div>
          
          </c:if>
          
</c:if>

<script>
	function changeSubCompany(){
		var c = document.getElementById('reg_comp').value;
		var htm = "<option value=''>--Select Sub Company--</option>"
		<c:forEach items="${agianCompanies}" var="catItem">
			if(c == '${catItem.parent.name}'){
				htm += "<option value='${catItem.name}'>${catItem.name}</option>";
			}
		</c:forEach>
		
		document.getElementById('sub_company').innerHTML = htm;
	}
</script>
