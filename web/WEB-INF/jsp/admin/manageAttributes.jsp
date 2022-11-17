<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />
<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" />



<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="items" />
<body>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
				<div id="companyhome"> 
				
				<h3>
				
				<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
					<a href="groups.do">
					Group : ${group.name}
					</a>
					»
				</c:if>
 				
 					<a href="items.do?group_id=${group.id}">Items</a> »
 					<a href="edititem.do?group_id=${ group.id }&item_id=${ item.id }">Edit Item</a> »
 					Manage Attributes : ${ item.name }
 				</h3>
				
				
<div style="width:30%;">
				<form method="post" action="saveManagedAttributes.do?group_id=${ group.id }&item_id=${ item.id }">
				<div style="width:100%;">
					<table>
						<tr valign="top">
							<td style="border:0px;" valign="top">
							<tr class="headbox">
								<th width="70%" nowrap>Attribute</th>
								
								<th width="30%">Enabled:</th>
							</tr>
							<c:choose>
								<c:when test="${empty item.attributes}">
									<c:forEach items="${group.attributes}" var="att" varStatus="i">
										<c:forEach items="${item.attributes}" var="item_att" varStatus="k">
											<c:if test="${att.name eq item_att.attribute.name}">
											<c:set var="item_instance" value="${item_att}"/>
											</c:if>
										</c:forEach>
										<tr >
											<td width="1%" nowrap>${att.name}</td>
											<td>
												<input type="checkbox" name="cboxes" value="${att.name}" <c:if test="${not item_instance.disabled}">checked</c:if> />
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:when test="${item.id>0}">
									<c:forEach items="${item.attributes}" var="att" varStatus="i">
										
										<tr>
											<td width="1%" nowrap>${att.attribute.name}</td>
											<td>
												<input type="hidden" name="${att.id }" value="${att.id }"/>
												<input type="checkbox" name="cboxes" value="${att.attribute.name}" <c:if test="${not att.disabled}">checked</c:if> />
											</td>
										</tr>
									</c:forEach>
									
									<c:set var="l" value="${0}"/>
									<c:if test="${fn:length(group.attributes) > fn:length(item.attributes)}">
										<c:forEach items="${group.attributes}" var="grp_att" varStatus="j">
											<c:forEach items="${item.attributes}" var="item_att" varStatus="k">
												<c:if test="${grp_att.name eq item_att.attribute.name}">
												<c:set var="l" value="${1}"/>
												<c:set var="item_instance" value="${item_att}"/>
												</c:if>
											</c:forEach>
										
											<c:if test="${l ne 1 }">
											<tr>
											<td width="1%" nowrap>${grp_att.name}</td>
											<td>
												<input type="checkbox" name="cboxes" value="${grp_att.name}" <c:if test="${not item_instance.disabled}">checked</c:if> />
											</td>
											</tr>
											</c:if>
											<c:set var="l" value="${0}"/>
										</c:forEach>
									</c:if>
										
								</c:when>
								<c:otherwise>
									<c:forEach items="${group.attributes}" var="att" varStatus="i">
										<c:forEach items="${item.attributes}" var="item_att" varStatus="k">
											<c:if test="${att.name eq item_att.attribute.name}">
											<c:set var="item_instance" value="${item_att}"/>
											</c:if>
										</c:forEach>
										<tr>
											<td width="1%" nowrap>${att.name}</td>
											<td>
												<input type="checkbox" name="cboxes" value="${att.name}" <c:if test="${not item_instance.disabled}">checked</c:if> />
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="Save" class="upload_button2"/>
								<a href="edititem.do?group_id=${ group.id }&item_id=${ item.id }"><input type="button" value="Cancel" class="upload_button2"/></a>
							</td>
						</tr>
					</table>
				</div>
				</form>
</div>
				</div>
				
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
