
<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript">
	function submitForm(myForm) {
		var packageName = getElement('package_name');

		var error = false;
		var messages = "Problem(s) occured: \n\n";

		if (packageName.length == 0) {
			error = true;
			messages += " * Package name was not specified. \n";
		}

		if (error) {
			alert(messages);
		} else {
			return true;
		}

		return false;
	}
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
</script>

<div style="width: 100%;">
	<table>
		<tr>
			<td style="border: 0px; padding-top: 20px;" valign="top">
				<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
					<c:if test="${not empty packageObj.id}">
						<input type="hidden" name="package_id" value="${packageObj.id}" />						
					</c:if>
					<table>
						<tr class="headbox">
							<th colspan="3" align="left">${empty packageObj.id ? 'New' : 'Edit'} Package</th>
						</tr>
						<tr>
							<td width="1%" nowrap>Group (Optional)</td>
							<td width="10px"></td>
							<td><select name="parentGroup">
									<option value=""></option>
									<c:forEach items="${groups}" var="g">
										<option value="${g.id}" <c:if test="${g.id eq packageObj.parentGroup.id }">selected</c:if>>${g.name}</option>
									</c:forEach>
							</select></td>
						</tr>
						
						<tr>
							<td width="1%" nowrap>Package Name</td>
							<td width="10px"></td>
							<td><input type="text" id="package_name" name="package_name" class="textbox3" value="${packageObj.name}" /></td>
						</tr>
						<tr>
							<td width="1%" nowrap>SKU</td>
							<td width="10px"></td>
							<td><input type="text" id="sku" name="sku" value="${packageObj.sku}" class="textbox3" /></td>
						</tr>
						<c:if test="${company.name eq 'wilcon' }">
							<tr>
								<td width="1%" nowrap>Price</td>
								<td width="10px"></td>
								<td><input type="text" id="price" name="price" value="${packageObj.price}" class="textbox3" /></td>
							</tr>
							<tr>
								<td width="1%" nowrap>Current Image</td>
								<td width="10px"></td>
								<td><img src="../companies/wilcon/images/packages/original/${packageObj.image}" width="200" height="200"></td>
							</tr>
							<tr>
								<td width="1%" nowrap>Image</td>
								<td width="10px"></td>
								<td><input type="file" id="image" name="image" value="" class="textbox3" /></td>
							</tr>
							<tr>
								<td width="1%" nowrap>Duration (Days)</td>
								<td width="10px"></td>
								<td><input type="number" id="duration" name="duration" value="${packageObj.duration }" class="textbox3" max="999"/></td>
							</tr>
							<tr>
								<td width="1%" nowrap>Stock</td>
								<td width="10px"></td>
								<td><input type="number" id="stock" name="stock" value="${packageObj.stock }" class="textbox3"/></td>
							</tr>
						</c:if>
						<tr>
							<td valign="top" width="1%" nowrap>Description</td>
							<td></td>
							<td><textarea id="description" name="description">${packageObj.description}</textarea> <script type="text/javascript">
								CKEDITOR.replace('description', {
									width : 500
								});
							</script></td>
						</tr>
						<tr>
							<td colspan="2" style="border: 0px;"></td>
							<td style="border: 0px;"><input type="submit" value="${empty packageObj.id ? 'Add' : 'Update'}" class="upload_button2" /></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</div>