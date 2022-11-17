<%--

<tr>
	<td >Last Name<br /><input type="text" id="lastname" name="member2.lastname" value="${member2.lastname}" class="w200"></td>
</tr>
kk
 ---%>
<c:if test="${style ==null }">
	<c:set var="style" > class="textbox3" </c:set>
</c:if>
<tr>
	<td width="1%" nowrap>Mobile<br>
	<input type="text" id="mobile" name="member2.mobile" value="${member2.mobile}" ${style}>
	</td>
</tr>
<tr>
	<td width="1%" nowrap>Landline<br>
	<input type="text" id="landline" name="member2.landline" value="${member2.landline}"  ${style}>
	</td>
</tr>
<input type="hidden"  value="${member2.email}" name="email"/>
<input type="hidden" id="username" name="username" value="${member2.username}">
<tr>
	<td width="1%" nowrap>Company Name<br><input type="text" id="reg_companyName" name="member2.reg_companyName" value="${member2.reg_companyName}"  ${style}>
	</td>
</tr>
<tr>
	<td width="1%" nowrap>Company Address<br>
	<input type="text" id="reg_companyAddress" name="member2.reg_companyAddress" value="${member2.reg_companyAddress}"  ${style}>
	</td>
</tr>
<tr>
	<td width="1%" nowrap>Position<br>
	<input type="text" id="reg_companyPosition" name="member2.reg_companyPosition" value="${member2.reg_companyPosition}"  ${style}>
	</td>
</tr>
