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

<tr>
	<td  width="1%" nowrap><b>ID</b>: 
		${memberCode}
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
	
	<c:if test="${not empty member2.info2 and member2.info2 ne ''}">
		<tr>
			<td width="1%" nowrap><b> Birthdate</b>: 
				${member2.info2}
			</td>
		</tr>
	</c:if>
	
</c:if>

<c:if test="${not empty member2.reg_companyAddress}">
	<tr>
		<td width="1%" nowrap><b> Company Name</b>:  
			${member2.reg_companyName} 
		</td>
	</tr>
	<tr>
		<td width="1%" nowrap><b> Contact Person</b>:  
			${member2.info3}" 
		</td>
	</tr>
	<tr>
		<td width="1%" nowrap><b> Company Address</b>:  
			${member2.reg_companyAddress}
		</td>
	</tr>
</c:if>

<tr>
	<td  width="1%" nowrap><b>Area/Province</b>: 
		${member2.province}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>City</b>: 
		${member2.city}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Zip Code</b>: 
		${member2.zipcode}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Mobile No.</b>: 
		${member2.mobile}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Landline No.</b>: 
		${member2.landline}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Fax</b>: 
		${member2.fax}
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Date Registered</b>: 
		${member2.dateJoined}
	</td>
</tr>

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
	<td  width="1%" nowrap><b>Start of Cycle</b>:
		<fmt:formatDate pattern="EEEE, dd MMMM yyyy" value="${currentCycle}" />
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>End of Cycle</b>:
		<%-- 
		<fmt:formatDate pattern="EEEE, dd MMMM yyyy" value="${endCycle}" />
		 --%>
		 <c:set var="today_" value="<%=new java.util.Date()%>" />
		<c:choose>
			<c:when test="${endCycle.time gt today_.time}">
				<fmt:formatDate pattern="EEEE, dd MMMM yyyy" value="${today_}" />
			</c:when>
			<c:otherwise>
				<fmt:formatDate pattern="EEEE, dd MMMM yyyy" value="${endCycle}" />
			</c:otherwise>
		</c:choose>
	</td>
</tr>

<tr>
	<td  width="1%" nowrap><b>Current Points</b>: 
		${member2.info5}
	</td>
</tr>