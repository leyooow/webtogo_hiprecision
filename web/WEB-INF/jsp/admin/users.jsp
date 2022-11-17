<%@include file="includes/header.jsp"  %>

<body>
<c:set var="menu" value="manage" />
<c:set var="submenu" value="user_listing" />
<c:set var="pagingAction" value="users.do" />


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

<script>
	function submitForm(myForm) {

		var username = document.getElementById('username').value;
		var password = document.getElementById('password').value;
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
		
		if(password.length == 0) {
			messages += "* Password not given\n";
			error = true;
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
		var singlePageId = "";
		var multiPageId = "";
		var groupId = "";
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
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">

	    <h1><strong>User</strong>: View User Listing</h1>
		<c:if test="${user.userType.value != 'Company Administrator'}">	
			<form name="companyFilter" action="users.do" method="post">	
		<c:choose>
		<c:when test="${user.company.name eq 'agian' }">
			<ul style="display:none;">
			<li>
						Filter by:
					
			</li>
			
			
			<li><select id="company_id1" name="company_id1" class="combobox1" style="width: 250px">
						<option value="">Company</option>
						<c:forEach items="${companies}" var="c">
								<option value="${c.id}" <c:if test="${c.id == (param['id'] != null ? param['id'] : param['company_id1'])}">selected</c:if>>${c.nameEditable}</option>
						</c:forEach>
						</select></li>
						<li><input type="submit" value="Go" class="btnBlue"/></li>
			</ul>
			</c:when>
			<c:otherwise>
			<ul>
			<li>
						Filter by:
					
			</li>
			
			
			<li><select id="company_id1" name="company_id1" class="combobox1" style="width: 250px">
						<option value="">Company</option>
						<c:forEach items="${companies}" var="c">
								<option value="${c.id}" <c:if test="${c.id == (param['id'] != null ? param['id'] : param['company_id1'])}">selected</c:if>>${c.nameEditable}</option>
						</c:forEach>
						</select></li>
						<li><input type="submit" value="Go" class="btnBlue"/></li>
			</ul>
		</c:otherwise>
		</c:choose>
	</form>
						</c:if>
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	   <c:if test="${user.userType.value ne 'User Sub Group Administrator' and user.userType.value ne 'User Group Administrator' }">
				<ul class="pagination">

				   <%@include file="includes/pagingnew.jsp"  %>
			
				  </ul>
			</c:if>
			
				  <div class="clear"></div>
<c:if test="${user.userType.value != 'Company Administrator'}">	
	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr>
						<th>Username</th>
						<th>Email</th>
						<th>Name</th>
						<th>Company</th>
						<th>User Type</th>  
						<th>Action</th>
					</tr>
						<c:set var="count" value="0" />
					<c:forEach items="${users}" var="user">
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<td>${user.username}</td>
						<td>${user.email}</td>
						<td>${user.firstname} ${user.lastname}</td>
						<td>${(user.company.name)==null ? 'None' : user.company.name}</td>
						<td>${user.userType.value}</td>
						<td>
			
						<c:if test="${user.userType.value != 'Super User' or user.userType.value == 'WTG Administrator'}">
							<form action="edituser.do" method="post"><input type="hidden" name="user_id" value="${user.id}" /><button>Edit</button></form> |
							<form action="deleteuser.do" method="post" onsubmit="return confirm('Do you really want to delete username ${user.username}?');"><input type="hidden" name="user_id" value="${user.id}" /><button>Delete</button></form>
						</c:if>
						
						</td>
					</tr>
					</c:forEach>
					
				</table> 
				
			</c:if>
				
				
			<c:if test="${user.userType.value == 'Company Administrator'}">	
				  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr>
						<th>Username</th>
						<th>Email</th>
						<th>Name</th>
						<th>User Type</th>  
						<th>Action</th>
					</tr>
							<c:set var="count" value="0" />
					<c:forEach items="${users}" var="user">
				<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<td>${user.username}</td>
						<td>${user.email}</td>
						<td>${user.firstname} ${user.lastname}</td>
						<td>${user.userType.value}</td>
						<td>
						
						<c:if test="${user.userType.value != 'Super User' or user.userType.value == 'WTG Administrator'}">
							<a href="edituser.do?user_id=${user.id}">Edit</a> |
							<a href="deleteuser.do?user_id=${user.id}"  onclick="return confirm('Do you really want to delete username ${user.username}?');">Delete</a>
						</c:if>
						
						</td>
					</tr>
					</c:forEach>
					
					
				</table> 				
			</c:if>
			 <c:if test="${user.userType.value ne 'User Sub Group Administrator' and user.userType.value ne 'User Group Administrator' }">
				<ul class="pagination">

				   <%@include file="includes/pagingnew.jsp"  %>
			
				  </ul>
			</c:if>
	</div><!--//mainContent-->

	<div class="sidenav">
		<form name="newUserForm" method="post" action="saveuser.do" onsubmit="return submitForm(this);">
		<input type="hidden" name="user_id"  value="${user2.id}"/>
	 <h2>New User</h2>
		
	  <ul>
		<c:if test="${user.userType.value != 'Company Administrator'}">
				<c:set var="userCompanyId" value="${(user2.company != null) ? user2.company.id : 0 }"></c:set>
	    <li <c:if test="${company.name eq 'agian'}">style="display:none;"</c:if> }><strong>Company Name</strong><br /><select id="company_id" name="company_id" class="w200">
						<option value="">- Select Company -</option>
						<c:forEach items="${companies}" var="c">
							<option value="${c.id}" <c:if test="${c.id == userCompanyId or c.id eq company.id}">selected</c:if>>${c.nameEditable}</option>
						</c:forEach>
					</select>
	    </li>
	    </c:if>
	    <c:if test="${user.userType.value == 'Company Administrator'}">
				<input type="hidden" id="company_id" name="company_id" value="${user.company.id }">
			</c:if>		
	    <li>
	    <strong>User Type</strong><br /><select id="userType" name="userType" class="w200">
						<option value="">- Select User Type -</option>
						<c:set var="usertype_counter" value="0"/>
						<c:forEach items="${userTypes}" var="utype">
							
							<c:if test="${user.userType.value == 'Company Administrator'}">
								<c:if test="${utype.value == 'Company Staff' or utype.value == 'Company Administrator'}">
									<option value="${utype.value}"> ${utype.value}</option>
								</c:if>
							</c:if>
							
							<c:if test="${user.userType.value != 'Company Administrator'}">
								
									<c:choose>
									<c:when test="${company.name eq 'agian'}">
										<c:set var="usertype_counter" value="${usertype_counter+1 }"/>
										<c:if test="${usertype_counter > 5 }">
											<option value="${utype.value}"> ${utype.value}</option>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:set var="usertype_counter" value="${usertype_counter+1 }"/>
										<c:if test="${usertype_counter < 6 }">
											<option value="${utype.value}"> ${utype.value}</option>
										</c:if>
									</c:otherwise>
									</c:choose>
							</c:if>									
						</c:forEach>
								
				</select>
	    </li>
	    <li><strong>Username</strong><br /><input type="text" id="username" name="user2.username" value="${user2.username}" class="w200" /></li>
	     <li><strong>Password</strong><br /><input type="password" id="password" name="password" value="${user2.password}" class="w200" /></li>
	      <c:if test="${company.name eq 'agian' }">
	      	<input type="hidden" id="info1" name="info1" value="">
		      <li>
		      Company Name<br />
						<select id="reg_company" class="w200" value="${user2.info1 }" required onchange="changeSubCompany();">
						 <c:forEach items="${companiesAgian}" var="catItem">
							<c:if test="${catItem.parent.parentCategory eq null}">
								<option value="${catItem.name}">${catItem.name}</option>
							</c:if>
						</c:forEach>
		                 </select>
		      </li>
		      <li>
		      Sub Company Name<br />
		      			<c:set var="hasChild" value=""/>
						<c:set var="firstComp" value=""/>
						<c:forEach items="${listCategoryItemMap['Companies']}" var="catItem" varStatus="counter">
							<c:if test="${counter.index eq 0}">
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
		      </li>
	      </c:if>
	      <li><strong>Email Address</strong><br /><input type="text" id="email" name="user2.email" value="${user2.email}" class="w200" /></li>
	       <li><strong>First Name</strong><br /><input type="text" id="firstname" name="user2.firstname" value="${user2.firstname}" class="w200"></li>
	        <li><strong>Last Name</strong><br /><input type="text" id="lastname" name="user2.lastname" value="${user2.lastname}" class="w200"></li>
	        <c:choose>
	        <c:when test="${user.company.name eq 'agian' }">
	        <li style="display:none;"><strong>Number of Items Per Page</strong><br /><select id="numberItems" name="user2.itemsPerPage" class="w200">
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
					<option value="25">25</option>
					<option value="40">40</option>
					<option value="50" selected>50</option>
				</select></li>
	        </c:when>
	        <c:otherwise>
	        <li><strong>Number of Items Per Page</strong><br /><select id="numberItems" name="user2.itemsPerPage" class="w200">
					<option value="10" ${(user2.itemsPerPage == 10) ? "selected" : "" }>10</option>
					<option value="15" ${(user2.itemsPerPage == 15) ? "selected" : "" }>15</option>
					<option value="20" ${(user2.itemsPerPage == 20) ? "selected" : "" }>20</option>
					<option value="25" ${(user2.itemsPerPage == 25) ? "selected" : "" }>25</option>
					<option value="40" ${(user2.itemsPerPage == 40) ? "selected" : "" }>40</option>
					<option value="50" ${(user2.itemsPerPage == 50) ? "selected" : "" }>50</option>
				</select></li>
	        </c:otherwise>
	        </c:choose>	
	        <li><c:if test="${empty user2}">
							<input type="submit" name="submit" value="Add New" class="btnBlue">
					</c:if>
					<c:if test="${not empty user2}">
							<input type="submit" name="submit" value="Update" class="btnBlue">
					</c:if>
					&nbsp;
					<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='users.do'">
				</li>
	   </ul>
	   </form>
	</div><!--//sidenav-->
	
</div>

	<div class="clear"></div>

  </div><!--//container-->
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
</body>

</html>