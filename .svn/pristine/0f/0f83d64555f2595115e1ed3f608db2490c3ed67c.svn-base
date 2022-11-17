<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />



<script>
	function submitForm(myForm) {

		var username = document.getElementById('username').value;
		
		<c:if test="${isEdit != 1 && empty user2.id}">
		var password = document.getElementById('password').value;
		</c:if>
		
		var newpassword = document.getElementById('newpassword').value;
		var confirmpassword = document.getElementById('confirmpassword').value;
		var email 	  = document.getElementById('email').value;
		var firstname = document.getElementById('firstname').value;
		var lastname = document.getElementById('lastname').value;
		var usertype = document.getElementById('userType').value;
		
		/* var company = document.getElementById('company').value;  */
		var itemsPerPage = document.getElementById('numberItems').value;

			
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(usertype.length == 0) {
			messages += "* Usertype not given\n";
			error = true;
		}
		
		if(username.length == 0) {
			messages += "* Username name not given\n";
			error = true;
		}
		
		<c:if test="${isEdit != 1 && empty user2.id}">
		if(password.length == 0) {
			messages += "* Password not given\n";
			error = true;
		}
		</c:if>
		
		if(newpassword.length != 0) {
			if(newpassword != confirmpassword) {
				messages += "* Password didn't match\n";
				error = true;
			}
		}
		
		if(email.length == 0) {
			messages += "* Email address not given\n";
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
			if('${user.userType.value}'  == 'System Administrator'){
				if(document.getElementById('sub_company').value != ''){
					document.getElementById('info1').value = subc;
					document.getElementById('info1').value = subc;
					
				}else{
					document.getElementById('info1').value = document.getElementById('reg_company').value;
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

	function submitUncheckedPages(){
		//alert("here!");
		var singlePageBoxes = document.pagesRestrictionForm.singlePages;
		var multiPageBoxes = document.pagesRestrictionForm.multiPages;
		var groupBoxes = document.pagesRestrictionForm.groups;
		var categoryBoxes = document.pagesRestrictionForm.categories;
		var singlePageId = "";
		var multiPageId = "";
		var groupId = "";
		var categoryId = "";
		for(i=0; i<singlePageBoxes.length; i++){
			if(singlePageBoxes[i].checked == false)
				singlePageId += ((singlePageBoxes[i].value+','));
		}

		for(i=0; i<multiPageBoxes.length; i++){
			if(multiPageBoxes[i].checked == false)
				multiPageId += ((multiPageBoxes[i].value+','));
		}

		for(i=0; i<groupBoxes.length; i++){
			if(groupBoxes[i].checked == false)
				groupId += ((groupBoxes[i].value+','));
		}
		
		for(i=0; i<categoryBoxes.length; i++){	
			if(categoryBoxes[i].checked == false)
				categoryId += ((categoryBoxes[i].value+','));
		}

		document.pagesRestrictionForm.forbiddenSinglePageList.value = singlePageId;
		document.pagesRestrictionForm.forbiddenMultiPageList.value = multiPageId;
		document.pagesRestrictionForm.forbiddenGroupList.value = groupId;
		document.pagesRestrictionForm.forbiddenCategoryList.value = categoryId;

		//alert("Multipage id: "+ multiPageId);
				
	}
</script>



<div style=<c:if test="${user2.userType.value eq 'Company Staff'}">"width:900px;"</c:if><c:if test="${user2.userType.value ne 'Company Staff'}">"width:50px;"</c:if>>
<table>
	<tr>
		<td style="border:0px;">
		
		<form name="newUserForm" method="post" action="saveuser.do" onsubmit="return submitForm(this);">
		<input type="hidden" name="user_id"  value="${user2.id}"/>
		<table width="50%">
			<tr>
				<td colspan="3"></td>
			</tr>

			<c:if test="${user.userType.value != 'Company Administrator' and user.company.name ne 'agian'}">
				<c:set var="userCompanyId" value="${(user2.company != null) ? user2.company.id : 0 }"></c:set>
				<tr>
					<td width="1%" nowrap>Company Name </td>
					<td width="10px"></td>
					<td>
					<select id="company_id" name="company_id" class="combobox1">
						<option value="">- Select Company -</option>
						<c:forEach items="${companies}" var="c">
							<option value="${c.id}" <c:if test="${c.id == userCompanyId}">selected</c:if>>${c.nameEditable}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</c:if>	
			<c:set var ="count" value="0"/>
			<c:if test="${user.userType.value == 'Company Administrator'}">
				<input type="hidden" id="company_id" name="company_id" value="${user.company.id }">
			</c:if>		
				<c:set var="usercount" value="0"/>			
			<tr>
				<td width="1%" nowrap>User Type </td>
				<td width="10px"></td>
				<td>				
					<select id="userType" name="userType" class="combobox2">
						<option value="">- Select User Type -</option>
						<c:forEach items="${userTypes}" var="utype" varStatus="counter">
						<c:set var ="count" value="${count+1 }"/>
							<c:choose>
							<c:when test="${user.company.name eq 'agian' }">
								<c:if test="${count > 5}">
										<option value="${utype}" ${utype eq user2.userType ? 'SELECTED' : '' }> ${utype.value}</option>
								</c:if>	
							</c:when>
							<c:otherwise>
								<c:if test="${user.userType.value == 'Company Administrator'}">
									<c:if test="${utype.value == 'Company Staff' or utype.value == 'Company Administrator'}">
										<option value="${utype}" ${utype eq user2.userType ? 'SELECTED' : '' }> ${utype.value}</option>
									</c:if>
								</c:if>
								<c:if test="${user.userType.value != 'Company Administrator'}">
										<option value="${utype}" ${utype eq user2.userType ? 'SELECTED' : '' }> ${utype.value}</option>
								</c:if>	
							</c:otherwise>
							</c:choose>								
						</c:forEach>					
				</select>
				</td>
			</tr>			

			<tr>
				<td width="1%" nowrap>Username</td>
				<td width="10px"></td>
				<td><input type="text" id="username" name="user2.username" value="${user2.username}" class="textbox3"></td>
			</tr>
			<c:if test="${isEdit != 1 && empty user2.id}">
				<tr>
					<td width="1%" nowrap>Password</td>
					<td width="10px"></td>
					<td><input type="text" id="password" name="password" value="${user2.password}" class="textbox3"></td>
				</tr>
			</c:if>
			<tr>
				<td width="1%" nowrap="">New Password</td>
				<td width="10px"></td>
				<td><input type="password" id="newpassword" name="newpassword" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap="">Confirm Password</td>
				<td width="10px"></td>
				<td><input type="password" id="confirmpassword" name="confirmpassword" class="textbox3"></td>
			</tr>
			<c:if test="${company.name eq 'agian' }">
			<input type="hidden" id="info1" name="info1" value="">
			<tr>
			<td width="1%" nowrap="">
		      Company Name</td><td width="10px"></td><td>
		      <c:set var="subComp" value=""/>
		      
		      <c:forEach items="${companiesAgian}" var="catItem">
									<c:if test="${catItem.name eq user2.info1 }">
										<c:if test="${catItem.parent.parentCategory ne null }">
											<c:set var="subComp" value="${catItem.parent.name }"/>
										</c:if>
									</c:if>
							</c:forEach>
						<select id="reg_company" class="w200" value="${user2.info1 }" required onchange="changeSubCompany();">
						 <c:forEach items="${companiesAgian}" var="catItem">
							<c:if test="${catItem.parent.parentCategory eq null }">
									<option value="${catItem.name}" <c:if test="${subComp eq catItem.name or user2.info1 eq catItem.name}">selected</c:if>>
										${catItem.name}</option>
									</c:if>
						</c:forEach>
		                 </select>
		                 </td>
			</tr>
			<c:choose>
			<c:when test="${subComp ne ''}">
				<tr>
				<td width="1%" nowrap="">
			      Sub Company Name</td><td width="10px"></td><td>
			      		<c:set var="hasChild" value=""/>
						<c:set var="firstComp" value=""/>
						<c:forEach items="${companiesAgian}" var="catItem" varStatus="counter">
							<c:if test="${catItem.name eq subComp}">
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
								<c:forEach items="${companiesAgian}" var="catItem">
									<c:if test="${catItem.parent.name eq firstComp}">
										<option value="${catItem.name}" <c:if test="${user2.info1 eq catItem.name }">selected</c:if>>
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
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
			      
							<%-- <select id="sub_company" name="sub_company" class="w200" >
								<option value="">--Select Sub Company--</option>
 								 <c:forEach items="${companiesAgian}" var="catItem">
									<c:if test="${catItem.parent.parentCategory ne null }">
											<option value="${catItem.name}" <c:if test="${user2.info1 eq catItem.name}">selected</c:if>>
												${catItem.name}</option>
											</c:if>
								</c:forEach>
			                 </select> --%>
			                 </td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
				<td width="1%" nowrap="">
			      Sub Company Name</td><td width="10px"></td><td>
							<select id="sub_company" name="sub_company" class="w200" >
								<option value="">--Select Sub Company--</option>
			                 </select>
			                 </td>
				</tr>
			</c:otherwise>
			</c:choose>
	      </c:if>
			
			<tr>
				<td width="1%" nowrap>Email Address</td>
				<td width="10px"></td>
				<td><input type="text" id="email" name="user2.email" value="${user2.email}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>First Name</td>
				<td width="10px"></td>
				<td><input type="text" id="firstname" name="user2.firstname" value="${user2.firstname}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Last Name</td>
				<td width="10px"></td>
				<td><input type="text" id="lastname" name="user2.lastname" value="${user2.lastname}" class="textbox3"></td>
			</tr>
	<c:choose>
	<c:when test="${user.company.name eq 'agian' }">
	<tr style="display:none">
		<td width="1%" nowrap>Number of Items Per Page</td>
		<td width="5px;"></td> 
				<td>
				<select id="numberItems" name="user2.itemsPerPage" class="combobox1">
					<option value="10" >10</option>
					<option value="15" >15</option>
					<option value="20" >20</option>
					<option value="25" >25</option>
					<option value="40" >40</option>
					<option value="50" selected>50</option>
				</select>
				
				
		</td>
	</tr>
	</c:when>
	<c:otherwise>
	<tr>
		<td width="1%" nowrap>Number of Items Per Page</td>
		<td width="5px;"></td> 
				<td>
				<select id="numberItems" name="user2.itemsPerPage" class="combobox1">
					<option value="10" ${(user2.itemsPerPage == 10) ? "selected" : "" }>10</option>
					<option value="15" ${(user2.itemsPerPage == 15) ? "selected" : "" }>15</option>
					<option value="20" ${(user2.itemsPerPage == 20) ? "selected" : "" }>20</option>
					<option value="25" ${(user2.itemsPerPage == 25) ? "selected" : "" }>25</option>
					<option value="40" ${(user2.itemsPerPage == 40) ? "selected" : "" }>40</option>
					<option value="50" ${(user2.itemsPerPage == 50) ? "selected" : "" }>50</option>
				</select>
				
				
		</td>
	</tr>
	</c:otherwise>
	</c:choose>

			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
						<c:if test="${empty user2}">
							<input type="submit" name="submit" value="Add New" class="button1">
					</c:if>
					<c:if test="${not empty user2}">
							<input type="submit" name="submit" value="Update" class="button1">
					</c:if>
					&nbsp;
					<input type="button" name="cancel" value="Cancel" class="button1" onclick="window.location='users.do'">
				
				</td>
			</tr>
		</table>
		</form>
		
		</td>
	</tr>
</table>
<br/>
<br/>
<br/>
<c:if test="${not empty user2 }">
	<c:if test="${user2.company.companySettings.hasUserRights eq true and (user.userType.value eq 'WTG Administrator' or user.userType.value eq 'Super User' or user.userType.value eq 'System Administrator')}">
		<div style="white-space: nowrap;"><h3>Edit User Page Rights</h3></div>
		<br/>
		<form name="pagesRestrictionForm" id="pagesRestrictionForm" method="post" action="saveuserrights.do" onSubmit="submitUncheckedPages();">
			<table width="700" >
				<tr>
					<td valign="top">
						<table>
							<tr class="headbox">
								<th colspan="3">Restrict Single Pages</th>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
										<c:set var="countChecker" value="0"/>
										<c:if test="${not empty singlePages }">
											<c:forEach items="${singlePages }" var="sp" varStatus="counter">
												<td width="33.33%" nowrap><input type="checkbox" id="singlePages" name="singlePages" value="${sp.id }" <c:if test="${forbiddenSinglePages[sp.id] ne false }">CHECKED</c:if>>${sp.name }</td>
												<c:if test="${counter.count mod 3 eq 0 }">
													</tr>
													<tr>
												</c:if>
												
											</c:forEach>
										</c:if>
										<c:if test="${empty singlePages }">
											<input type="hidden" id="singlePages" value="none" />
											</tr>
										</c:if>
										<c:if test="${countChecker eq 1 }">
												<td></td><td></td>
											</c:if>
											<c:if test="${countChecker eq 2 }">
												<td></td>
										</c:if>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td valign="top">
						<table>
							<tr class="headbox">
								<th colspan="3">Restrict Multi Pages</th>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
										<c:set var="countChecker" value="0"/>
										<c:if test="${not empty multiPages }">
											<c:forEach items="${multiPages }" var="mp" varStatus="counter">
												<c:set var="countChecker" value="${countChecker+1}"/>
												<td width="33.33%" nowrap><input type="checkbox" id="multiPages" name="multiPages" value="${mp.id }" <c:if test="${forbiddenMultiPages[mp.id] ne false }">CHECKED</c:if>>${mp.name }</td>
												<c:if test="${counter.count mod 3 eq 0 }">
													</tr>
													<tr>
													<c:set var="countChecker" value="0"/>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty multiPages}">
											<input type="hidden" id="multiPages" value="none" />
											</tr>
										</c:if>
											<c:if test="${countChecker eq 1 }">
												<td></td><td></td>
											</c:if>
											<c:if test="${countChecker eq 2 }">
												<td></td>
											</c:if>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr class="headbox">
								<th colspan="3">Restrict Groups</th>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
										<c:set var="countChecker" value="0"/>
										
										<c:if test="${not empty groups }">
											<c:forEach items="${groups }" var="g" varStatus="counter">
												<c:set var="countChecker" value="${countChecker+1}"/>
												<td width="33.33%" nowrap><input type="checkbox" id="groups" name="groups" value="${g.id }" <c:if test="${forbiddenGroups[g.id] ne false }">CHECKED</c:if>>${g.name }</td>
												<c:if test="${counter.count mod 3 eq 0 }">
													</tr>
													<tr>
													<c:set var="countChecker" value="0"/>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty groups }">
											<input type="hidden" id="groups" value="none" />
											</tr>
										</c:if>
											<c:if test="${countChecker eq 1 }">
												<td></td><td></td>
											</c:if>
											<c:if test="${countChecker eq 2 }">
												<td></td>
											</c:if>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>				
				<tr>
					<td>
						<table>
							<tr class="headbox">
								<th colspan="3">Restrict Category</th>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<c:set var="countChecker" value="0"/>
										
											<c:if test="${not empty groups }">
												<c:forEach items="${groups }" var="g" varStatus="gCounter">
													<c:forEach items="${g.enabledCategories }" var="c" varStatus="cCounter">
														<c:set var="countChecker" value="${countChecker+1}"/>
														<td width="33.33%" nowrap><input type="checkbox" id="categories" name="categories" value="${c.id }" <c:if test="${forbiddenCategories[c.id] ne false }">CHECKED</c:if>>${c.name }</td>
														<c:if test="${cCounter.count mod 3 eq 0 }">
															</tr>
															<tr>
															<c:set var="countChecker" value="0"/>
														</c:if>
													</c:forEach>
												</c:forEach>
											</c:if>
											<c:if test="${empty groups }">
												<input type="hidden" id="groups" value="none" />
												</tr>
											</c:if>
											<c:if test="${countChecker eq 1 }">
												<td></td><td></td>
											</c:if>
											<c:if test="${countChecker eq 2 }">
												<td></td>
											</c:if>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			
			
					
						<div align="center"><input type="submit" value="Update" class="button1"/>&nbsp;&nbsp; <input type="button" name="cancel" value="Cancel" class="button1" onclick="window.location='users.do'"></div>
					
				<input type="hidden" name="user_id"  value="${user2.id}"/>
				<input type="hidden" name="forbiddenSinglePageList" id="forbiddenSinglePageList"/>
				<input type="hidden" name="forbiddenMultiPageList" id="forbiddenMultiPageList"/>
				<input type="hidden" name="forbiddenGroupList" id="forbiddenGroupList"/>
				<input type="hidden" name="forbiddenCategoryList" id="forbiddenCategoryList"/>
		</form>
	</c:if>
</c:if>
</div>

<script>
	function changeSubCompany(){
		var c = document.getElementById('reg_company').value;
		var htm = "<option value=''>--Select Sub Company--</option>"
		<c:forEach items="${companiesAgian}" var="catItem">
			if(c == '${catItem.parent.name}'){
				htm += "<option value='${catItem.name}'>${catItem.name}</option>";
			}
		</c:forEach>
		
		document.getElementById('sub_company').innerHTML = htm;
	}
</script>