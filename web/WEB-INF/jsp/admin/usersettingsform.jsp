<script>

function updateUserInfo(myForm) {
	var pattern = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/;
	var email = myForm.email.value;
	var firstname = myForm.firstname.value;
	var lastname = myForm.lastname.value;
	
	var message = "The following problem(s) were encounter: \n\n";
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

<form action="updateusersetting.do" method="post" onsubmit="return updateUserInfo(this);">

<input type="hidden" name="userId" value="${user.id}" />

<table>
	<tr> 
		<td colspan="3"><h3>User Information</h3></td>
	</tr>
	<tr>
		<td width="1%" nowrap>Username</td>
		<td width="5px;"></td>
		<td>${user.username}</td>
	</tr>
	<tr>
		<td width="1%" nowrap>User Email</td>
		<td width="5px;"></td>
		<td><input type="text" name="email" value="${user.email}" class="textbox4" /></td>
	</tr>
	<tr>
		<td width="1%" nowrap>Firstname</td>
		<td width="5px;"></td>
		<td><input type="text" name="firstname" value="${user.firstname}" class="textbox4" /></td>
	</tr>
	<tr>
		<td width="1%" nowrap>Lastname</td>
		<td width="5px;"></td>
		<td><input type="text" name="lastname" value="${user.lastname}" class="textbox4" /></td>
	</tr>
	<tr>
		<td width="1%" nowrap>Number of Items Per Page</td>
		<td width="5px;"></td>
				<td>
				<select id="itemsPerPage" name="itemsPerPage" class="combobox1">
					<option value="10" ${(user.itemsPerPage == 10) ? "selected" : "" }>10</option>
					<option value="15" ${(user.itemsPerPage == 15) ? "selected" : "" }>15</option>
					<option value="20" ${(user.itemsPerPage == 20) ? "selected" : "" }>20</option>
					<option value="25" ${(user.itemsPerPage == 25) ? "selected" : "" }>25</option>
					<option value="40" ${(user.itemsPerPage == 40) ? "selected" : "" }>40</option>
					<option value="50" ${(user.itemsPerPage == 50) ? "selected" : "" }>50</option>
				</select>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="border: 0px;"></td>
		<td style="border: 0px;"><input type="submit" name="submit" value="Update" class="upload_button2" /></td> 
</table>

</form>

<br><br>

<table>
	<tr>
		<td colspan="3" style="border: 0px;"><b>Change you password here</b></td>
	</tr>
		
	<tr>
		<td colspan="2" style="border: 0px;"></td>
		<td style="border: 0px;">
				
		<div id="passwordDiv">
		
		<form method="post" action="changeuserpassword.do">
		
		<input type="hidden" name="userId" value="${user.id}" />
		
		<table>
			<tr>
				<td style="border: 0px;">Old Password</td>
				<td style="border: 0px;" width="1px;"></td>
				<td style="border: 0px;"><input type="password" name="oldPassword" value=""></td>
			</tr>
			<tr>
				<td style="border: 0px;">New Password</td>
				<td style="border: 0px;" width="1px;"></td>
				<td style="border: 0px;"><input type="password" name="newPassword" value=""></td>
			</tr>
			<tr> 
				<td style="border: 0px;">Confirm Password</td>
				<td style="border: 0px;" width="1px;"></td>
				<td style="border: 0px;"><input type="password" name="confirmPassword" value=""></td>
			</tr>
			<tr>
				<td style="border: 0px;" colspan="2"></td>
				<td style="border: 0px;"><input type="submit" name="submit" value="Change Password" class="upload_button2"></td>
			</tr>
		</table>  
		
		</form>
	
		</div>
		
		</td>
	</tr>
</table>

<br><br>



