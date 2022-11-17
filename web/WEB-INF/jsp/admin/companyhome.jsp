<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />
  
<table>
	<tr>
		<td width="1%"></td>
		<td><a class="companyMenu" href="singlepage.do">Manage Single Pages</a></td> 
		<td>
			<table border="0">
				<tr>
				<c:forEach items="${singlePageList}" var="sp"  varStatus="counter">
					<td><a href="editsinglepage.do?singlepage_id=${sp.id}" style="font-size: 7pt; font-weight: none;">${sp.name}</a></td>
				<c:if test="${counter.count mod 4 eq 0}">
					</tr><tr>
				</c:if>
				</c:forEach>
				</tr>
			</table>
        </td>
	</tr>
	<tr>
		<td width="1%"></td>
		<td><a class="companyMenu" href="multipage.do">Manage Multi Page</a></td> 
		<td>
			<table border="0">
				<tr>
				<c:forEach items="${multiPageList}" var="mp"  varStatus="counter">
					<td><a href="editmultipagechildlist.do?multipage_id=${mp.id}"  style="font-size: 7pt; font-weight: none;">${mp.name}</a></td>
				<c:if test="${counter.count mod 4 eq 0}">
					</tr><tr>
				</c:if>
				</c:forEach>
				</tr>
			</table>
        </td>
	</tr> 
	<tr>
		<td width="1%"></td>
		<td><a class="companyMenu" href="forms.do">Manage Forms</a></td> 
		<td>
			<table border="0">
				<tr>
				<c:forEach items="${formPageList}" var="fp"  varStatus="counter">
					<td><a href=""  style="font-size: 7pt; font-weight: none;">${fp.name}</a></td>
				<c:if test="${counter.count mod 4 eq 0}">
					</tr><tr>
				</c:if>
				</c:forEach>
				</tr>
			</table>
        </td>
	</tr> 	
	<tr>
		<td width="1%"></td>
		<td><a class="companyMenu" href="groups.do">Manage Groups</a></td> 
		<td>
			<table border="0">
				<tr>
				<c:forEach items="${groupList}" var="gp"  varStatus="counter">
					<td><a href="items.do?group_id=${gp.id}"  style="font-size: 7pt; font-weight: none;">${gp.name}</a></td>
				<c:if test="${counter.count mod 4 eq 0}">
					</tr><tr>
				</c:if>
				</c:forEach>
				</tr>
			</table>
        </td>

	</tr>
	<tr>
		<td width="1%"></td>
		<td><a class="companyMenu" href="category.do">Manage Categories </a></td> 
		<td>
			<table border="0">
				<tr>
				<c:forEach items="${categoryList}" var="cp"  varStatus="counter">
					<td><a href="editcategory.do?group_id=${cp.parentGroup.id}&category_id=${cp.id}"  style="font-size: 7pt; font-weight: none;">${cp.name}</a></td>
				<c:if test="${counter.count mod 4 eq 0}">
					</tr><tr>
				</c:if>
				</c:forEach>
				</tr>
			</table>
        </td>
	</tr>
	
</table>

