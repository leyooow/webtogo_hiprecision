<%@include file="itemcomponentformjavascript.jsp" %>
	<c:if test="${fn:length(item.categoryItemComponentList) > 0}">

		<h3>Component List</h3>
		<ol>
			<table width="700">
				<c:forEach items="${item.categoryItemComponentList}" var="component"> 
					<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">					
						<tr>
							<td> 
								<span><b>${component.component.category.name} ${component.component.name}</b></span>
								<span style="display:none">
									<select name="componentIdList" style="width: 300px;">
										<c:forEach items="${componentList }" var="component2"> 
											<option value="${component2.id }" ${component.component.id eq component2.id ? 'SELECTED':''}>${component2.category.name} ${component2.name }</option>
										</c:forEach>
									</select>
									<input type="hidden" name="categoryItemComponentId" value="${component.id }"/>
								</span>
							</td>
							<td> 
							
								<span>${component.variable }</span>
							</td>
							<td>
								<span>${component.equation }</span><span style="display:none"><input type="text" name="equation" value="${component.equation }"/></span>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="editComponent(this);">Edit</a><a href="javascript:void(0);" onclick="updateComponent(this);" style="display:none;">Save</a> | <a href="deleteitemcomponent.do?itemComponentId=${component.id }&groupId=${param.group_id }&itemId=${param.item_id }" onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
							</td>
						</tr>
					</c:if>
					<div class="clear"></div>
				</c:forEach>
			</table>
		</ol>
						

	</c:if>
<form method="post" action="saveitemcomponent.do" enctype="multipart/form-data">
				
	<input type="hidden" name="groupId" value="${group.id}" />
	<input type="hidden" name="itemId" value="${item.id}" />	

		
			
		<table width="100%" cellpadding="0" border="0" id="componentTable">
		</table><!-- END OF MAIN TABLE -->
		
		<p align="right">
			<a href="javascript:void(0);" onclick="jQuery('#componentTable').append(jQuery('#template').html());">Add another</a><br/>
			<input type="submit" value="Update" class="btnBlue">
		</p>
		
		
		

</form>
<table style="display:none" id="template">
	<tr>
		<td>	
			<select name="componentIdList">
				<c:forEach items="${componentList }" var="component">
					<option value="${component.id }">${component.category.name} ${component.name }</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<input type="text" name="equationList"/>
		</td>
		<td>
			<a href="javascript:void(0);" onclick="jQuery(this).parent().parent().remove();">Delete</a>
		</td>
	</tr>
</table>