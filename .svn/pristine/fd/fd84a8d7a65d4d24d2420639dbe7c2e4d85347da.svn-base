<tr>
	<td  width="1%" nowrap><b>Username</b>: 
		${member2.username}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Email Address</b>: 
		${member2.email}
	</td>
</tr>

<c:if test="${not empty member2.address1}">
	<tr>
		<td width="1%" nowrap><b> First Name</b>: 
			${member2.firstname}
		</td>
	</tr>
	<tr>
		<td width="1%" nowrap><b> Last Name</b>: 
			${member2.lastname}
		</td>
	</tr>
	<tr>
		<td width="1%" nowrap><b> Address</b>: 
			${member2.address1}
		</td>
	</tr>
	
</c:if>

<tr>
	<td  width="1%" nowrap><b>Area Serviced</b>: 
		${member2.province}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>City</b>: 
		${member2.city}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Mobile No.</b>: 
		${member2.mobile}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Date Registered</b>: 
		${member2.dateJoined}
	</td>
</tr>

<c:forEach items="${registrationItemOtherFieldsMap}" var="riof">
	<c:if test="${not fn:containsIgnoreCase(riof.key, 'photo')}">
		<tr>
			<td  width="1%" nowrap><b>${riof.key}</b>: 
				${riof.value}
			</td>
		</tr>
	</c:if>
</c:forEach>

<tr>
	<td  width="1%" nowrap><b>Comments:</b>
		 <br>
		<textarea id="member2.info4" name="member2.info4">${member2.info4}</textarea> 
		<script type="text/javascript">
			CKEDITOR.replace( 'member2.info4',
				{
					height:100
				}
			);	
		</script>
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Member Verification</b>: 
	<c:choose>
		<c:when test="${member2.verified}">
			<input type="checkbox" name="verified" checked="checked" /> 
			Verified. Uncheck to unverify.
		</c:when>
		
		<c:otherwise>
			<input type="checkbox" name="verified" /> 
			Not Verified. Check to verify.
		</c:otherwise>
	</c:choose>
	</td>
</tr>

