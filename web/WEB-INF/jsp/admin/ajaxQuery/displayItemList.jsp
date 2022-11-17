<%
//no items should be displayed at this moment
if(request.getParameter("displayNone")!=null&&request.getParameter("allItemsList")!=null){
	%>
	<option></option>
	<%
	return;
}
if(request.getParameter("allItemsList")!=null){// indicating whether all items should be displayed
	%>
	<c:choose>
	<c:when test="${fn:length(group.categories) > 0}">
	<c:forEach items="${items}" var="item">
		<option value="${item.id}" id="${item.id}" >${item.name} </option>
		<option id="__${item.id}" name="__${item.id}" value="${item.sku}" hidden>${item.sku}</option>
	</c:forEach>
	</c:when>
	</c:choose>
	<%
	return;
	}	
%>