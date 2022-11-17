<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />

<c:set var="menu" value="manage_pages" />

<script language="javascript" src="../javascripts/multifile.js"></script>

<script>

	function submitForm(myForm) {
		
		var name = getElement('contactus.title');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Name not given";
			error = true;
		}
		else {
			document.getElementById('contactus.title').value = document.getElementById('contactus.id').value.replace(" ", "_");
		}
		 
		if(error) {
			alert(messages);
		}
		else {
			return true;
		}
		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
</script> 
<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;">
		
		<form method="post" action="savecontactus.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<c:if test="${contactus.id gt 0}">
		<input type="hidden" name="update" value="1">
		<input type="hidden" name="contactus_id" value="${contactus.id}">
		</c:if>
		
		<table width="50%">
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<c:if test="${contactus == null}">
			<tr class="headbox">
				<th colspan="3">
					New Page
				</th>
			</tr>
			</c:if>
			
			<tr> 
				<td width="1%" nowrap><b>Title</b></td>
				<td width="10px"></td>
				<td><input type="text" name="contactus.title" value="${contactus.title}" class="textbox4"></td>
			</tr>
			<tr>
				<td width="1%" nowrap valign="top"><b>Content</b></td>
				<td width="10px"></td>
				<td>
			 
				<FCK:editor id="contactus.details" width="80%" height="200" toolbarSet="${(user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator') ? 'Custom_SuperUser' : 'Custom'}">${contactus.details}</FCK:editor>
				
				</td>
			</tr>
			
			<c:if test="${not empty contactus.images}">
			<tr>
				<td width="1%" nowrap></td>
				<td width="10px"></td>
				<td>
					
					<c:forEach items="${contactus.images}" var="image">
						${image.filename} <a href="deletecontactusimage.do?contactus_id=${contactus.id}&image_id=${contactus.id}">Delete</a> <br>
					</c:forEach>
						
				</td>
			</tr>
			</c:if>
			
			<tr>
				<td width="1%" nowrap><b>Images</b></td>
				<td width="10px"></td>
				<td>
				
					<table cellpadding="0">
						<tr>
							<td style="border: 0px; padding: 0px;">
							<input id="upload_id" type="file" name="upload" >
							</td>
						</tr>
						<tr>
							<td height="2px" style="border: 0px; padding: 0px;"></td>
						</tr>
						
						<tr>
							<td height="2px" style="border: 0px; padding: 0px;">
							
							<div id="files_list"></div>
							<script>
								<!-- Create an instance of the multiSelector class, pass it the output target and the max number of files -->
								var multi_selector = new MultiSelector( document.getElementById( 'files_list' ), 10 );
								<!-- Pass in the file element -->
								multi_selector.addElement( document.getElementById( 'upload_id' ) );
							</script>
							
							</td>
						</tr>
						
					</table>
				
				</td>
				<tr> 
				<td width="1%" nowrap><b>Email</b></td>
				<td width="10px"></td>
				<td><input type="text" name="contactus.email" value="${contactus.email}" class="textbox4"></td>
			</tr>
			</tr>
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				
					<c:choose>
						<c:when test="${contactus.id > 0}">
							<input type="submit" value="Update" class="button1">
							<input type="reset" value="Reset" class="button1">
						</c:when>
						<c:otherwise>
							<input type="submit" value="Add" class="button1">
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