<br>
<h4>${empty component.id ? 'Add' : 'Edit'} Component</h4>
<form action="savecomponent.do" method="POST">
	<input type="hidden" name="componentId"  value="${component.id}"/>
	<table width="100%" >
		<tr>
			<td>
				Category<br/>
				<select name="componentCategoryId" id="categorySelect">
					<option value="">-- select a category --</option>
					<c:forEach items="${componentCategoryList }" var="category">
						<option value="${category.id }" ${category.id eq component.category.id ? 'SELECTED' : '' }>${category.name }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td >Name<br /><input type="text" id="name" name="component.name" value="${component.name}" class="w200"></td>
		</tr>
		<tr>
			<td >
				<input type="submit" class="btnBlue" value='Save'/>
				<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='component.do'">
			</td>
		</tr>
	</table>
</form>