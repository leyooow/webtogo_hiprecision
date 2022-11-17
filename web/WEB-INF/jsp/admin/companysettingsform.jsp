<c:if test="${company != null}" >

	<form method="post" action="updatecompanysettings.do">
	
	<input type="hidden" name="companyId" value="${company.id}" />
	
	<table>
		<tr>
			<td colspan="3"><h3>Company Information</h3></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Company Name</td>
			<td width="5px;"></td>
			<td><input type="text" name="nameEditable" value="${company.nameEditable}" class="textbox4" /></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Company Email</td>
			<td width="5px;"></td>
			<td><input type="text" name="email" value="${company.email}" class="textbox4" /></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Address</td>
			<td width="5px;"></td>
			<td><input type="text" name="address" value="${company.address}" class="textbox4" /></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Address 2</td>
			<td width="5px;"></td>
			<td><input type="text" name="address2" value="${company.address2}" class="textbox4" /></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Title</td>
			<td width="5px;"></td>
			<td><textarea name="title" cols=40  rows=2>${company.title}</textarea>
				<%--  <input type="text" name="title" value="${company.title}" class="textbox4" /></td>  --%>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Phone</td>
			<td width="5px;"></td>
			<td><input type="text" name="phone" value="${company.phone}" class="textbox4" /></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Cellphone</td>
			<td width="5px;"></td>
			<td><input type="text" name="cellphone" value="${company.cellphone}" class="textbox4" /></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Fax</td>
			<td width="5px;"></td>
			<td><input type="text" name="fax" value="${company.fax}" class="textbox4" /></td>
		</tr>
		
		<tr> 
			<td width="1%" nowrap>Contact Person</td>
			<td width="5px;"></td>
			<td><input type="text" name="contactPerson" value="${company.contactPerson}" class="textbox4" /></td>
		</tr>
		
		<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
		<tr> 
			<td width="1%" nowrap>Domain Name</td>
			<td width="5px;"></td>
			<td><input type="text" name="domainName" value="${company.domainName}" class="textbox4" /></td>
		</tr>
		</c:if>
		
		<tr> 
			<td colspan="2"></td>
			<td>
				<input type="submit" name="submit" value="Update" class="upload_button2">
			</td>
		</tr>
		
	</table>
	
	</form>

</c:if>