<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

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

<table border="0">
	<tr>
		<td><h3><a class="companyMenu" href="singlepage.do">Manage Single Pages</a></h3></td> 
	</tr>
	<tr>
		<td>
			<table border="0">
				<c:forEach items="${singlePageList}" var="sp"  varStatus="counter">
					<tr><td><a href="editsinglepage.do?singlepage_id=${sp.id}" style="font-size: 7pt; font-weight: none;">${sp.name}</a></td></tr>
				</c:forEach>
			</table>
        </td>
	</tr>


	<tr>
		<td><h3><a class="companyMenu" href="multipage.do">Manage Multi Page</a></h3></td> 
	</tr>
	<tr>
		<td>
			<table border="0">
				<c:forEach items="${multiPageList}" var="mp"  varStatus="counter">
					<tr><td><a href="editmultipagechildlist.do?multipage_id=${mp.id}"  style="font-size: 7pt; font-weight: none;">${mp.name}</a>&nbsp; - ${fn:length(mp.items)} item(s)</td></tr>
				</c:forEach>
			</table>
        </td>
	</tr>


	<tr>
		<td><h3><a class="companyMenu" href="forms.do">Manage Forms</a></h3></td> 
	</tr>
	<tr>
		<td>
			<table border="0">
				<c:forEach items="${formPageList}" var="fp"  varStatus="counter">
					<tr><td><a href="editformpage.do?form_id="  style="font-size: 7pt; font-weight: none;">${fp.name}</a></td></tr>
				</c:forEach>
			</table>
        </td>
	</tr>
	
	<c:if test="${company.companySettings.hasProducts}">
	<tr>
		<td><h3><a class="companyMenu" href="groups.do">Manage Groups</a></h3></td> 
	</tr>
	<tr>
		<td>
			<table border="0">
				<c:forEach items="${groupList}" var="gp"  varStatus="counter">
					<tr><td><a href="items.do?group_id=${gp.id}"  style="font-size: 7pt; font-weight: none;">${gp.name}</a></td></tr>
				</c:forEach>
			</table>
        </td>
	</tr>
	</c:if>
	
	<tr>
		<td><h3>Manage Categories</h3></td> 
	</tr>
	<tr>
		<td>
			<table border="0">
				<c:forEach items="${categoryList}" var="cp"  varStatus="counter">
					<tr><td><a href="editcategory.do?group_id=${cp.parentGroup.id}&category_id=${cp.id}"  style="font-size: 7pt; font-weight: none;">${cp.name}</a> &nbsp;&nbsp;&nbsp;&nbsp; [${fn:length(cp.childrenCategory)}/${cp.parentGroup.maxCategories} ChildrenCategories - ${fn:length(cp.items)} Item(s)]</td></tr>
				</c:forEach>
			</table>
        </td>
	</tr>
	
	
	<tr>
		<td><h3>Logs for the Past 7 Days</h3></td> 
	</tr>
	<tr>
		<td>
			<a href="logs.do" style="font-size: 7pt;">View All Logs</a>
			<c:choose>
				<c:when test="${not empty logList}">
					<table border="0">
						<tr class="headbox">
							<th>Date</th>
							<th>Description</th>
							<th>Username</th>
						</tr>
						<c:forEach items="${logList}" var="cp"  varStatus="counter">
							<tr>
								<td>${cp.createdOn}</td>
								<td>
									<c:set var="i" value="0" />
									<c:forTokens var="curr" delims="~" items="${cp.remarks}">
										<c:set var="i" value="${i + 1}" />
										<c:choose>
											<c:when test="${fn:length(curr) gt 250}">
												<c:out value="${fn:substring(curr,0,250)}" escapeXml="false" /><a href="javascript:void(0);" onmouseover="javascript:showContent(${i});" onmouseout="return nd();">...</a>
												<div id="showContent_${i}" style="display:none">
													<c:out value="${curr}" escapeXml="false" />
												</div>
												<br />
											</c:when>
											<c:otherwise>
												<c:out value="${curr}" escapeXml="false" /> <br />
											</c:otherwise>
										</c:choose>
									</c:forTokens>
								</td>
								<td>${cp.updatedByUser.username}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br />
					<p>No activity for the last seven days.</p>
				</c:otherwise>
			</c:choose>
        </td>
	</tr>
	
	<!--  <tr>
		<td><h3>Manage Activities</h3></td> 
	</tr>
	<tr>
		<td>
			<table border="0">
				<c:forEach items="${activityList}" var="ac"  varStatus="counter">
					<tr><td><a href="editactivity.do?activityId=${ac.id}"  style="font-size: 7pt; font-weight: none;">${ac.description}</a></td></tr>
				</c:forEach>
			</table>
        </td>
	</tr>-->

</table>

