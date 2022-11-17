<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />

<c:set var="menu" value="variations" />

<script>

	function submitForm(myForm) {
		var name = myForm.name.value;
		var selectedIndex = myForm.variationGroupId.selectedIndex;
		
		var errorMessage = "The following problems were encountered: \n\n";
		var error = false;
		 
		if(name.length == 0) {
			errorMessage += "* Name given is incorrect\n";
			error = true;
		} 
		 
		if(selectedIndex == 0) {
			errorMessage += "* No variation group was selected\n";
			error = true; 
		}
		
		if(error) {
			alert(errorMessage);
		}
		else {
			return true;
		}
		
		return false;
	}
	
</script> 

<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;">
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		
		<c:choose>
			<c:when test="${variation != null }">
				<input type="hidden" name="update" value="true" />
				<input type="hidden" name="variationGroupId" value="${variation.id}" />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="insert" value="true" />
			</c:otherwise>
		</c:choose>
		
		
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<tr class="headbox">
				<th colspan="3">
					New Variation
				</th>
			</tr>
				
			<tr>
				<td width="1%" nowrap>Name</td>
				<td width="10px"></td>
				<td><input type="text" name="name" value="${variation.name}" class="textbox3" /></td>
			</tr>
			
			<tr> 
				<td width="1%" nowrap>Variation Group</td>
				<td width="10px"></td>
				<td> 
				
				<select class="textbox3" name="variationGroupId">
					<option value="0">Select Variation Group</option>
					 
					<c:forEach items="${variationGroups}" var="vg">
						
							<option value="${vg.id}" <c:if test="${variation != null && variation.variationGroup.id eq vg.id}">selected</c:if>>${vg.name}</option>
						
					</c:forEach>
					
				</select>
				
				</td>
			</tr>
			
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				 
				<c:choose>
					<c:when test="${variation != null }">
						<input type="submit" value="Save" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
						<input type="button" value="Cancel" class="upload_button2" onclick="window.location='variation.do';">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="upload_button2">
					</c:otherwise>
				</c:choose>
						
					
					
				</td>
			</tr>
					
		</table>
		
		</form>
		
		</td>
	</tr>
</table>
</div>