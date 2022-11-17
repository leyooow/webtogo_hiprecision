<%@include file="includes/header.jsp"  %>

<body>

  <div class="container">
	<c:set var="menu" value="settings" />
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

function updateUserInfo(myForm) {
	var pattern = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/;
	var email = myForm.email.value;
	var firstname = myForm.firstname.value;
	var lastname = myForm.lastname.value;
	
	var message = "The following problem(s) were encountered: \n\n";
	var error = false;
	
	if(!pattern.test(email)) {
		message += " * Please enter a valid email address.\n";
		error = true;
	}
	
	if(firstname.length <= 0) {
		message += " * Please enter a valid name.\n";
		error = true;
	}
	
	if(lastname.length <= 0) {
		message += " * Please enter a valid lastname.\n"; 
		error = true;
	}
	
	if(error) {
		alert(message);
	}
	else {
		return true;
	}
	
	return false;
}

</script>

<div class="contentWrapper" id="contentWrapper">
    <div class="content clearfix" style="width: 100%">
 				
 				<c:if test="${param['evt'] != null}">
						<ul>			
							<c:if test="${param['evt'] == 'usersettingsupdated'}">
								<li><span class="actionMessage">User settings was sucessfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'passwordupdated'}">
								<li><span class="actionMessage">Password was sucessfully updated.</span></li>
							</c:if> 
							
							<c:if test="${param['evt'] == 'passwordnotupdated'}">
								<li><span class="actionMessage">Password was not updated.</span></li>
							</c:if>
							
						</ul>
					</c:if>
	
<form action="updateusersetting.do" method="post" onsubmit="return updateUserInfo(this);">

<input type="hidden" name="userId" value="${user.id}" />

	 <table width="49%"  height="100%" border="0" class="companiesTable" style="float: left;">
	<tr> 
		<th colspan="2">User Information</td>
	</tr>
	<tr>
		<td>Username</td>
		
		<td>${user.username}</td>
	</tr>
	<tr class="oddRow">
		<td>User Email</td>
		
		<td><input type="text" name="email" value="${user.email}" class="w200" /></td>
	</tr>
	<tr>
		<td>Firstname</td>
		
		<td><input type="text" name="firstname" value="${user.firstname}" class="w200" /></td>
	</tr>
	<tr class="oddRow">
		<td>Lastname</td>
		
		<td><input type="text" name="lastname" value="${user.lastname}" class="w200" /></td>
	</tr>
	<tr>
		<td>Number of Items Per Page</td>
		
				<td>
				<select id="itemsPerPage" name="itemsPerPage" class="w200">
					<option value="10" ${(user.itemsPerPage == 10) ? "selected" : "" }>10</option>
					<option value="15" ${(user.itemsPerPage == 15) ? "selected" : "" }>15</option>
					<option value="20" ${(user.itemsPerPage == 20) ? "selected" : "" }>20</option>
					<option value="25" ${(user.itemsPerPage == 25) ? "selected" : "" }>25</option>
					<option value="40" ${(user.itemsPerPage == 40) ? "selected" : "" }>40</option>
					<option value="50" ${(user.itemsPerPage == 50) ? "selected" : "" }>50</option>
				</select>
		</td>
	</tr>
	<tr class="oddRow">
		<td colspan="2"><input type="submit" name="submit" value="Update" class="btnBlue"/></td> 
</table>

</form>

<table width="49%" height="100%" border="0" class="companiesTable" style="float: right; ">
	<tr>
		<th colspan="2">Change Password</td>
	</tr>
		
	
		
		
		<form method="post" action="changeuserpassword.do">
		
		<input type="hidden" name="userId" value="${user.id}" />
		
			<tr>
				<td>Old Password</td>
				
				<td><input type="password" name="oldPassword" value=""  class="w200"></td>
			</tr>
			<tr class="oddRow">
				<td>New Password</td>
				
				<td><input type="password" name="newPassword" value=""  class="w200"></td>
			</tr>
			<tr> 
				<td>Confirm New Password</td>
				
				<td><input type="password" name="confirmPassword" value=""  class="w200"></td>
			</tr>
			<tr  class="oddRow">
				<td colspan="2"><input type="submit" name="submit" value="Submit" class="btnBlue"></td>
			</tr>
		</table>  
		
		</form>

	
			
		</div><!-- content -->
	</div><!-- main -->
	</div><!--  container -->
		
</body>
</html>