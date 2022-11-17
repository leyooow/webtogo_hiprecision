
				<h3>Edit Registration</h3>
				<table>
					 	<tr class="headbox"> 
					 		<th width="20%"></th>
					 		<th></th>

						</tr>
				
				
				<c:if test="${user.userType.value != 'Company Staff'}">				 
					<form name="emailForm" action=updateRegistrantStatus.do method="post"  onsubmit="return confirm('This will update status and sent status update email to registrant')">
				</c:if>
				<tr>
					<td>Registrant's Status</td>
					<td>
					<input type="hidden" name="submissionId" value="${param.submissionId}"/>
					<select name="status">
						<option value="Pending" ${member.status eq 'Pending' ? 'selected' : ''}>Pending</option>
						<option value="For Review" ${member.status eq 'For Review' ? 'selected' : ''}>For Review</option>
						<option value="Approved" ${member.status eq 'Approved' ? 'selected' : ''}>Approved</option>
					</select>
					<input type='submit' value="Change and Send Status Email" />
					</td>
				</tr>
				</table>
				
				<c:if test="${user.userType.value != 'Company Staff'}">				 
					</form>
				</c:if>
				
				<br/>
				 <c:if test="${param['evt'] != null}">
						<ul> 
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Submission was successfully deleted.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'error'}">
								<li><span class="actionMessage">An unexpected error occurred during deletion. Deletion failed.</span></li>
							</c:if>
						</ul>
					</c:if>
		
			<br/>
		
		<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Page was successfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'updatefailed'}">
								<li><span class="actionMessage">Failed to update page.</span></li>
							</c:if>
						</ul>
					</c:if>
		
			<br/>

			<c:if test="${user.userType.value != 'Company Staff'}">				 
				<form name="emailForm" action="updateregistrants.do" method="post"  onsubmit="return confirm('This will permanently update registration information.\nEdited upload files will be deleted and replaced with the new one\nIf you didn\'t choose a new file to upload, then old file will remain\nDo you still wish to proceed with update?')" enctype="multipart/form-data">
			</c:if>	
					 <table>
					 	<tr class="headbox"> 
					 		<th width="20%"></th>
					 		<th></th>

						</tr>						
						
					 	<c:forEach items="${items}" var="e">
						 	<tr>	
						 		<td valign="right"> ${e.otherField.name}	</td> 
						 		<td >
						 			<c:choose>
						          		<c:when test="${mapFileName[e.otherField.name]!=null}">
						          			<input type="file" name="uploadedFiles" alt="File Name" size="30"/>&nbsp;
						          			<a href="http://${company.serverName}/attachments/registrations/${e.content}">
									 		<%-- <a href="../companies/${company.name}/attachments/registrations/${e.content}"> --%>
									 			${e.content}
									 		</a>
									 	</c:when>
						          		<c:otherwise>
						          				<c:choose>
									          		<c:when test="${e.otherField.name=='Current School'}">
														<select name="Current School">
															<c:forEach items="${brightSchools}" var="x">
																<option value="${x}" ${e.content eq x ? 'selected' : ''}>${x}</option>
															</c:forEach>
														</select>
												 	</c:when>
												 	<c:when test="${e.otherField.name=='Relationship Status'}">
															<select name="Relationship Status">
																<option value="Single" ${e.content eq 'Single' ? 'selected' : ''}>Single</option>
																<option value="Married" ${e.content eq 'Married' ? 'selected' : ''}>Married</option>
																<option value="De Facto" ${e.content eq 'De Facto' ? 'selected' : ''}>De Facto</option>
																
															</select> 
												 	</c:when>
												 	<c:when test="${e.otherField.name=='Gender'}">
															<select name="Gender">
																<option value="Male" ${e.content eq 'Male' ? 'selected' : ''}>Male</option>
																<option value="Female" ${e.content eq 'Female' ? 'selected' : ''}>Female</option>
															</select> 
												 	</c:when>
												 	<c:when test="${e.otherField.name=='Birthday'}">
															<input type="text" name="Birthday" id="endDate" value="${e.content}" />
															<button id="endDateTrigger" >Click Here</button> 
															<script type="text/javascript">
								
															 Calendar.setup(
																    {
																      inputField  : "endDate",         // ID of the input field
																      ifFormat    : "%Y-%m-%d",    // the date format
																      button      : "endDateTrigger"       // ID of the button
																    }
																  );
															</script>
												 	</c:when>
									          		<c:otherwise>
									          			<input type="text" value= "${e.content}" name="${fn:trim(e.otherField.name)}" size=50/>
									          		</c:otherwise>
						          				</c:choose>
						          		</c:otherwise>
						          	</c:choose>
						 		</td>			 		
						 	</tr>
					 	

					 	</c:forEach>		 	
					 </table>
					 
					 
					 
					 
					 
					 
					 
					 
<c:if test="${company.id==150}">		

		<input type="hidden" name="allowedFields" value="Username"/>
		<input type="hidden" name="allowedFields" value="Password"/>
		<input type="hidden" name="allowedFields" value="Password Confirmation"/>
		<input type="hidden" name="allowedFields" value="First Name"/>
		<input type="hidden" name="allowedFields" value="Middle Name"/>
		<input type="hidden" name="allowedFields" value="Last Name"/>
		<input type="hidden" name="allowedFields" value="Birthday"/>
		<input type="hidden" name="allowedFields" value="Country"/>
		<input type="hidden" name="allowedFields" value="Zip Code"/>
		<input type="hidden" name="allowedFields" value="State/Province"/>
		<input type="hidden" name="allowedFields" value="City/Municipality"/>
		<input type="hidden" name="allowedFields" value="Building"/>
		<input type="hidden" name="allowedFields" value="Street"/>
		<input type="hidden" name="allowedFields" value="District/Subdivision"/>
		<input type="hidden" name="allowedFields" value="Telephone Nos."/>
		<input type="hidden" name="allowedFields" value="Mobile Nos."/>
		<input type="hidden" name="allowedFields" value="Fax Nos"/>
		<input type="hidden" name="allowedFields" value="Email"/>
		<input type="hidden" name="allowedFields" value="Website Address"/>
		          <input type="hidden" name="submissionId" value="${param.submissionId}"/>		 
</c:if>
		 	 
					 
					 
					 
					 
				<c:if test="${user.userType.value != 'Company Staff'}">	
					<br/><br/><br/>
					<div align='center'>
					<input type="submit" value="Update">
					</div>
					<br/>
				 </form>  		 
				</c:if> 
