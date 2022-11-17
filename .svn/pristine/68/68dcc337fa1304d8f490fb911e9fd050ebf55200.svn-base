<% 
if(request.getParameter("allCategoryList")!=null&&request.getParameter("group_id")!=null){
%>
<select name=>
			<option name="noselectedcategory">---select Specialization here---</option>
					<c:forEach items="${group.categories}" var="cat">
						<option value="${cat.id}" id="${cat.id}"<c:if test="${cat.id == item.parent.id}">selected</c:if>>${cat.name}  ${cat.descriptor}</option>
						
					</c:forEach>	
<%			
return;
}				
%>


