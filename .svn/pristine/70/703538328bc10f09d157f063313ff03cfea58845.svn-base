
<c:if test="${param.submissionId!=null}">			
				<h3>Edit Registration</h3>
				<table>
					 	<tr class="headbox"> 
					 		<th width="20%"></th>
					 		<th></th>

						</tr>
				
				
				<c:if test="${user.userType.value != 'Company Staff'}">				 
					<form name="emailForm" action=updateRegistrantStatusNoEmail.do method="post" >
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
					<input type='submit' value="Change Status" />
					</td>
				</tr>
				
					<tr class="headbox"> 
					 		<th width="20%"></th>
					 		<th></th>

					</tr>	
					<c:if test="${user.userType.value != 'Company Staff'}">				 
						</form>
					</c:if>		
					<tr>
						<td>Name of Organization</td>
						<td>				
							${(organizationName != null) ? organizationName: mapFieldValues['Organization Name']}"
						</td>
					</tr>
					<tr>
						<td>Address</td>
						<td>
							${(officeAddress != null) ? officeAddress: mapFieldValues['Office Address']}
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<form method="post" name="" action="downloadRegistrantFormsIave.do">
							 	<input type="hidden" name="submissionId" value="${member.id}"/>
								<input type="submit" value="Download All Forms" name="Download Forms">
							</form>
						</td>
					</tr>
				</table>
				
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
		<table>
		<tr> 
					 <td>	
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
			<h4>Choose form to edit</h4><br/>
			<ul>
			<li><a href="editregistrantsTemplate.do?submissionId=${param.submissionId}&formPage=1
					&organizationName=${(mapFieldValues['Organization Name'] ne null) ? mapFieldValues['Organization Name'] : organizationName}
					&officeAddress=${(mapFieldValues['Office Address'] != null) ? mapFieldValues['Office Address']: officeAddress}">Organization Information Form</a></li><br/>
			<c:forEach var="i" begin="1" end="${yearLimit}">
				<li><a href="editregistrantsTemplate.do?submissionId=${param.submissionId}&formPage=${i+1}&year=${2006+i}
				&organizationName=${(mapFieldValues['Organization Name'] ne null) ? mapFieldValues['Organization Name'] : organizationName}
				&officeAddress=${(mapFieldValues['Office Address'] != null) ? mapFieldValues['Office Address']: officeAddress}">${2006+i} Survey Form</a></li><br/>
			</c:forEach>
			</ul>
			
					 		</td>

			</tr>			
		</table>
		<table>
		<tr class="headbox"> 
					 		<th width="20%"></th>
					 		<th></th>

						</tr>
				</table>
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
			
		<c:if test="${param.formPage==1 }">
			<c:if test="${user.userType.value != 'Company Staff'}">				 
				<form name="emailForm" action="updateregistrants.do" method="post"  onsubmit="return confirm('This will permanently update registration information.\nEdited upload files will be deleted and replaced with the new one\nIf you didn\'t choose a new file to upload, then old file will remain\nDo you still wish to proceed with update?')" enctype="multipart/form-data">
			</c:if>	
							 <input type="hidden" name="formPage" value="1"/>
							 <input type="hidden" name="memberId" value="${member.id}"/>
		 
		  			<input type="hidden" name="submissionId" value="${param.submissionId}"/>
		  			 <input type="hidden" name="allowedFields" value="First Name"/>
     						<input type="hidden" name="allowedFields" value="Last Name"/>	
					    <input type="hidden" name="allowedFields" value="E-mail Address"/>
					  <input type="hidden" name="allowedFields" value="Organization Name"/>
					  <input type="hidden" name="allowedFields" value="Central Office"/>
					  <input type="hidden" name="allowedFields" value="Local Chapter"/>
					  <input type="hidden" name="allowedFields" value="Local Chapter Area"/>
					  <input type="hidden" name="allowedFields" value="Year Established"/>
					  <input type="hidden" name="allowedFields" value="Office Address"/>
					  <input type="hidden" name="allowedFields" value="Phone Number"/>
					  <input type="hidden" name="allowedFields" value="Fax Number"/>
					  <input type="hidden" name="allowedFields" value="Mobile Number"/>
					    <input type="hidden" name="allowedFields" value="Network Affiliation1"/>
					  <input type="hidden" name="allowedFields" value="Network Affiliation2"/>
					  <input type="hidden" name="allowedFields" value="Network Affiliation3"/>
					  <input type="hidden" name="allowedFields" value="Network Affiliation4"/>
					  <input type="hidden" name="allowedFields" value="Network Affiliation5"/>
					  <input type="hidden" name="allowedFields" value="Network Affiliation6"/>
					  <input type="hidden" name="allowedFields" value="SEC"/>
					  <input type="hidden" name="allowedFields" value="SEC Year"/>
					  <input type="hidden" name="allowedFields" value="DSWD"/>
					  <input type="hidden" name="allowedFields" value="DSWD Year"/>
					    <input type="hidden" name="allowedFields" value="PCNC"/>
					  <input type="hidden" name="allowedFields" value="PCNC Year"/>
					  <input type="hidden" name="allowedFields" value="CDA"/>
					  <input type="hidden" name="allowedFields" value="CDA Year"/>
					  <input type="hidden" name="allowedFields" value="PNVSCA"/>
					  <input type="hidden" name="allowedFields" value="PNVSCA Year"/>
 
   
		  <input type="hidden" name="allowedFields" value="Government"/>
		  <input type="hidden" name="allowedFields" value="Non-profit Organization"/>
		  <input type="hidden" name="allowedFields" value="Academe-based"/>
		  <input type="hidden" name="allowedFields" value="Corporate"/>
		  <input type="hidden" name="allowedFields" value="Other Types of Org"/>
		  <input type="hidden" name="allowedFields" value="Narrative Description"/>
		  <input type="hidden" name="allowedFields" value="Nationwide"/>
		  <input type="hidden" name="allowedFields" value="Luzon"/>
		  <input type="hidden" name="allowedFields" value="Visayas"/>
		  <input type="hidden" name="allowedFields" value="Mindanao"/>
		  <input type="hidden" name="allowedFields" value="Province"/>
		  <input type="hidden" name="allowedFields" value="City"/>
		  <input type="hidden" name="allowedFields" value="Municipality"/>
		  <input type="hidden" name="allowedFields" value="Barangay"/>  
		  <input type="hidden" name="allowedFields" value="Eradication of extreme proverty and hunger"/>
		  <input type="hidden" name="allowedFields" value="Combating HIV/AIDS, malaria, and other diseases"/>
		  <input type="hidden" name="allowedFields" value="Achievement of universal primary education"/>
		  <input type="hidden" name="allowedFields" value="Ensuring environmental sustainability"/>
		  <input type="hidden" name="allowedFields" value="Promotion of gender and equality and empowerment of women"/>
		  <input type="hidden" name="allowedFields" value="Developing a global partnership for development"/>
		  <input type="hidden" name="allowedFields" value="Reduction of child mortality"/>
		  <input type="hidden" name="allowedFields" value="Other UN Millenium Development Goals"/>
		  <input type="hidden" name="allowedFields" value="Recruitment and Selection of volunteers"/>  
		  <input type="hidden" name="allowedFields" value="Monitoring and Evaluation"/>
		  <input type="hidden" name="allowedFields" value="Orientation, Formation and Training of volunteers"/>
		  <input type="hidden" name="allowedFields" value="Volunteer Recognition"/>
		  <input type="hidden" name="allowedFields" value="Volunteer Nurturance / Retention"/>
		  <input type="hidden" name="allowedFields" value="Other Volunteer Management Training"/>

  

		LOGIN INFORMATION
		<table>
			 <tr>
				 <td colspan="2">	
				<label>*First Name: </label>
				</td>
				<td colspan="2">	
				<input name="First Name" type="text" class="inputText1" value="${mapFieldValues['First Name']}"/>
				<c:if test="${param.message != null}"><font color="#CC0000"><b></b></font></c:if>
				</td>
			</tr>
			 <tr>
				 <td colspan="2">	
				<label>*Last Name: </label>
				</td>
				<td colspan="2">	
				<input name="Last Name" type="text" class="inputText1" value="${mapFieldValues['Last Name']}"/>
				<c:if test="${param.message != null}"><font color="#CC0000"><b></b></font></c:if>
				</td>
			</tr>
			 <tr>
				 <td colspan="2">	
				<label>*Username: </label>
				</td>
				<td colspan="2">	
				${member.username}
				</td>
			</tr>
			<tr>
				<td colspan="2">					
				<label>*Email: </label>
				</td>
				<td colspan="2">	
				 <input name="E-mail Address" value="${mapFieldValues['E-mail Address']}"  type="text" />
				</td>
			</tr>
		</table>



	IDENTIFYING INFORMATION
		<table >
		 
			<tr>
			  	<td colspan="2">				
					<label>*Name of Organization</label><br />
					<input name="Organization Name" type="text" value="${mapFieldValues['Organization Name']}"/><br />
					<input value="checked" name="Central Office" type="checkbox" <c:if test="${mapFieldValues['Central Office']=='checked'}">checked</c:if>/><span > Central Office</span>
					<input value="checked" name="Local Chapter" type="checkbox"  <c:if test="${mapFieldValues['Local Chapter']=='checked'}">checked</c:if>/><span > Local Chapter</span>
					<input name="Local Chapter Area" type="text"  value="${mapFieldValues['Local Chapter Area']}"/><br />
				</td>
				
				<td>				
					<label>*Year Established</label><br />
					<input name="Year Established" type="text"  value="${mapFieldValues['Year Established']}"/><br />
				</td>
				<td width="10px"></td>
			</tr>
			<tr>
			  	<td colspan="4">				
					<label>*Office Address</label><br />
					<input name="Office Address" type="text"  value="${mapFieldValues['Office Address']}"/><br />
				</td>
			</tr>
			<tr>
			  	<td height="65">				
					<label>Phone Number</label><br />
					<input name="Phone Number" type="text"  value="${mapFieldValues['Phone Number']}"/><br />
			 	 </td>
				<td>
					<label>Fax Number</label><br />
					<input name="Fax Number" type="text"  value="${mapFieldValues['Fax Number']}"/><br />
				</td>
				
				<td>				
					<label>*Mobile Number</label><br />
					<input name="Mobile Number" type="text"  value="${mapFieldValues['Mobile Number']}"/><br />
				</td>
				<td width="10px"></td>
				
			</tr>
			<tr>
			  	<td>				
					<label>Network Affiliation</label><br />
					<input name="Network Affiliation1" type="text"  value="${mapFieldValues['Network Affiliation1']}"/><br />
					<input name="Network Affiliation2" type="text"  value="${mapFieldValues['Network Affiliation2']}"/><br />
				</td>
				<td>
					<br />
					<input name="Network Affiliation3" type="text"  value="${mapFieldValues['Network Affiliation3']}"/><br />
					<input name="Network Affiliation4" type="text"  value="${mapFieldValues['Network Affiliation4']}"/><br />
				</td>
				
				<td>				
					<br />
					<input name="Network Affiliation5" type="text"  value="${mapFieldValues['Network Affiliation5']}"/><br />
					<input name="Network Affiliation6" type="text"  value="${mapFieldValues['Network Affiliation6']}"/><br />
				</td>
				<td width="10px"></td>
			</tr>
		
		</table>
		<table> 
			<tr><td colspan="1"><label>AGENCY</label><br /><br /></td>
			<td colspan="1"><label>YEAR</label><br /><br /></td>
			<td colspan="1"><label>AGENCY</label><br /><br /></td>
			<td colspan="1"><label>YEAR</label><br /><br /></td>
			<td colspan="1"><br /><br /></td>
			</tr>
			<tr>
				<td><input value="checked" name="SEC" type="checkbox"   <c:if test="${mapFieldValues['SEC']=='checked'}">checked</c:if>/><span > Securities and Exchange Commission (SEC)</span></td>
				<td><input name="SEC Year" type="text"  value="${mapFieldValues['SEC Year']}"/></td>
				
				<td width="210px">
				<input value="checked" name="DSWD" type="checkbox"   <c:if test="${mapFieldValues['DSWD']=='checked'}">checked</c:if>/><span > Department of Social Welfare and Development</span></td>
				<td><input name="DSWD Year" type="text"  value="${mapFieldValues['DSWD Year']}"/></td>
				<td width="10px"></td>
			</tr>
			<tr>
				<td><input value="checked" name="PCNC" type="checkbox"   <c:if test="${mapFieldValues['PCNC']=='checked'}">checked</c:if>/><span > Philippine Council of NGO Certification (PCNC)</span></td>
				<td><input name="PCNC Year" type="text"  value="${mapFieldValues['PCNC Year']}"/></td>
				
				<td width="210px">
				<input value="checked" name="CDA" type="checkbox"   <c:if test="${mapFieldValues['CDA']=='checked'}">checked</c:if>/><span > Cooperative Development Authority (CDA)</span></td>
				<td><input name="CDA Year" type="text"  value="${mapFieldValues['CDA Year']}"/></td>
				<td width="10px"></td>
			</tr>
			<tr>
				<td><input value="checked" name="PNVSCA" type="checkbox"   <c:if test="${mapFieldValues['PNVSCA']=='checked'}">checked</c:if>/><span > Philippine National Volunteer Service Coordinatinf Agency (PNVSCA)</span></td>
				<td><input name="PNVSCA Year" type="text"  value="${mapFieldValues['PNVSCA Year']}"/></td>				
			</tr>
		</table>


		DESCRIPTION OF ORGANIZATION
		<table> 
			<tr><td colspan="5"><label>A. Type of Organization</label><br /><br /></td></tr>
			<tr>
				<td><input value="checked" name="Government" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Government']=='checked'}">checked</c:if>/><span class="label4"/> Government<br />
				<i>(includes national government agencies and their attached agencies, local government units)</i></span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Non-profit Organization" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Non-profit Organization']=='checked'}">checked</c:if>/><span class="label4"> Non-profit Organization<br />
				<i>(includes social development organizations and registered religious organizations; does not include social action desks of parishes)</i></span></td>
			</tr>
			<tr>
				<td><input value="checked" name="Academe-based" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Academe-based']=='checked'}">checked</c:if>/><span class="label5"> Academe-based<br />
				<i>(includes student organizations, teacher groups etc.)</i></span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Corporate" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Corporate']=='checked'}">checked</c:if>/><span class="label5"> Corporate<br />
				<i>(includes student organizations, teacher groups etc.)</i></span></td>
			</tr>
			<tr>
				<td><input value="checked" name="Other Types of Org" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Other Types of Org']=='checked'}">checked</c:if>/><span class="label5"> Others</span></td>
				<td width="10px"></td>
			</tr>
			<tr>
			  <td colspan="3">
				B. Provide a brief narrative description of your organization (max. 150 words).<br /><br />
				<textarea name="Narrative Description" cols="80" rows="5" class="multiField" id="Message">${mapFieldValues['Narrative Description']}</textarea>
				</td>
			</tr>
			<tr>
			  <td colspan="3">
				<span class="label2"><br /><i>Alternatively, you may send us a brochure of your organization with its vision, mission and goals.</i></span><br />
				
			  </td>
			</tr>
		</table>


		SCOPE AND GEOGRAPHIC INFORMATION
		<table> 
			<tr>
				<td colspan="5">
				<span class="label">Check the scope of your organization's operations. Choose only the widest coverage during 2007 to 2010.</label><br /><br /><br />
				<span class="label">SCOPE</label><br /><br />
				</td>
			</tr>
			<tr>
				<td><input value="checked" name="Nationwide" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Nationwide']=='checked'}">checked</c:if>/><span class="label2"> Nationwide</span></td>
				<td><input name="" type="text" class="inputText2" /></td>
				<td width="10px"></td>
				<td><input value="checked" name="Luzon" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Luzon']=='checked'}">checked</c:if>/><span class="label2"> Luzon</span></td>
				<td><input name="" type="text" class="inputText2"/></td>
			</tr>
			<tr>
				<td><input value="checked" name="Visayas" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Visayas']=='checked'}">checked</c:if>/><span class="label2"> Visayas</span></td>
				<td><input name="" type="text" class="inputText2"/></td>
				<td width="10px"></td>
				<td><input value="checked" name="Mindanao" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Mindanao']=='checked'}">checked</c:if>/><span class="label2"> Mindanao</span></td>
				<td><input name="" type="text" class="inputText2"/></td>
			</tr>
			<tr>
				<td>
				<bR /><span class="label">AREA/S COVERED</label><br /><br />
				</td>
			</tr>
			<tr>
				<td><input value="checked" name="Province" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Province']=='checked'}">checked</c:if>/><span class="label2"> Province</span></td>
				<td><input name="" type="text" class="inputText2"/></td>
				<td width="10px"></td>
				<td><input value="checked" name="City" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['City']=='checked'}">checked</c:if>/><span class="label2"> City</span></td>
				<td><input name="" type="text" class="inputText2"/></td>
			</tr>
			<tr>
				<td><input value="checked" name="Municipality" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Municipality']=='checked'}">checked</c:if>/><span class="label2"> Municipality</span></td>
				<td><input name="" type="text" class="inputText2"/></td>
				<td width="10px"></td>
				<td><input value="checked" name="Barangay" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Barangay']=='checked'}">checked</c:if>/><span class="label2"> Barangay</span></td>
				<td><input name="" type="text" class="inputText2"/></td>
			</tr>
		</table>

	
UNITED NATIONS MILLENIUM DEVELOPMENT GOALS
		<table> 
			<tr>
				<td colspan="5">
				<span class="label">Check the goals which are applicable to the programs of your organization.</label><br /><br /><br />
				</td>
			</tr>
			<tr>
				<td><input value="checked" name="Eradication of extreme proverty and hunger" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Eradication of extreme proverty and hunger']=='checked'}">checked</c:if>/><span class="label6"> Eradication of extreme proverty and hunger</span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Combating HIV/AIDS, malaria, and other diseases" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Combating HIV/AIDS, malaria, and other diseases']=='checked'}">checked</c:if>/><span class="label6"> Combating HIV/AIDS, malaria, and other diseases</span></td>
			</tr>
			<tr>
				<td><input value="checked" name="Achievement of universal primary education" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Achievement of universal primary education']=='checked'}">checked</c:if>/><span class="label6"> Achievement of universal primary education</span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Ensuring environmental sustainability" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Ensuring environmental sustainability']=='checked'}">checked</c:if>/><span class="label6"> Ensuring environmental sustainability</span></td>
			</tr>
			<tr>
				<td><input value="checked" name="Promotion of gender and equality and empowerment of women" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Promotion of gender and equality and empowerment of women']=='checked'}">checked</c:if>/><span class="label6"> Promotion of gender and equality and empowerment of women</span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Developing a global partnership for development" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Developing a global partnership for development']=='checked'}">checked</c:if>/><span class="label6"> Developing a global partnership for development</span></td>
			</tr>
			<tr>
				<td><input value="checked" name="Reduction of child mortality" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Reduction of child mortality']=='checked'}">checked</c:if>/><span class="label6"> Reduction of child mortality</span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Other UN Millenium Development Goals" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Other UN Millenium Development Goals']=='checked'}">checked</c:if>/><span class="label6"> Others</span></td>
			</tr>
		</table>

VOLUNTEER MANAGEMENT TRAININGS
		<table> 
			<tr>
				<td colspan="5">
				<span class="label">Which of the following volunteer management training will be helpful to your organization?</span><br /><br /><br />
				</td>
			</tr>
			<tr>
				<td><input value="checked" name="Recruitment and Selection of volunteers" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Recruitment and Selection of volunteers']=='checked'}">checked</c:if>/><span class="label6"> Recruitment and Selection of volunteers</span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Monitoring and Evaluation" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Monitoring and Evaluation']=='checked'}">checked</c:if>/><span class="label6"> Monitoring and Evaluation</span></td>
			</tr>
			<tr>
				<td><input value="checked" name="Orientation, Formation and Training of volunteers" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Orientation, Formation and Training of volunteers']=='checked'}">checked</c:if>/><span class="label6"> Orientation, Formation and Training of volunteers</span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Volunteer Recognition" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Volunteer Recognition']=='checked'}">checked</c:if>/><span class="label6"> Volunteer Recognition</span></td>
			</tr>
			<tr>
				<td><input value="checked" name="Volunteer Nurturance / Retention" type="checkbox" class="checkBox2" <c:if test="${mapFieldValues['Volunteer Nurturance / Retention']=='checked'}">checked</c:if>/><span class="label6"> Volunteer Nurturance / Retention</span></td>
				<td width="10px"></td>
				<td><input value="checked" name="Other Volunteer Management Training" type="checkbox" class="checkBox1" <c:if test="${mapFieldValues['Other Volunteer Management Training']=='checked'}">checked</c:if>/><span class="label6"> Others</span></td>
			</tr>
		</table>		
					 
				<c:if test="${user.userType.value != 'Company Staff'}">	
					<br/><br/><br/>
					<div align='center'>
					<input type="submit" value="Update">
					</div>
					<br/>
				 </form>  		 
				</c:if> 
</c:if>				
				
				
				
				
				
				
				
				
				
<c:if test="${param.formPage>1 && param.formPage<7 }">

<script type="text/javascript">
		function addMore(){
			//alert("danny");
			document.getElementById("addMoreSpace").innerHTML = document.getElementById("addMoreSpace").innerHTML + '<tr><td><input name="NoOfVolunteers" type="text" class="inputText_no"/></td><td><input type="NoOfDays" class="inputText_no"/></td><td><input type="NoOfHours" class="inputText_no"/></td><td>***</td><td><input type="text" class="inputText_no"/></td></tr>';
		}
		function addRow(tableID) {

			var table = document.getElementById(tableID);

			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);

			var cell1 = row.insertCell(0);
			var element1 = document.createElement("input");
			element1.type = "checkbox";
			element1.name = "chk";
			cell1.appendChild(element1);

			var cell2 = row.insertCell(1);
			var element2 = document.createElement("input");
			element2.type = "text";
			element2.setAttribute("name","No of Volunteers");
			element2.setAttribute("class","inputText_no");
			element2.setAttribute("onkeyup","solveTotalHours()");
			cell2.appendChild(element2);

			var cell3 = row.insertCell(2);
			var element3 = document.createElement("input");
			element3.type = "text";
			element3.setAttribute("name","No of Days");
			element3.setAttribute("class","inputText_no");
			element3.setAttribute("onkeyup","solveTotalHours()");
			cell3.appendChild(element3);

			var cell4 = row.insertCell(3);
			var element4 = document.createElement("input");
			element4.type = "text";
			element4.setAttribute("name","No of Hours");
			element4.setAttribute("class","inputText_no");
			element4.setAttribute("onkeyup","solveTotalHours()");
			cell4.appendChild(element4);

			var cell5 = row.insertCell(4);
			var element5 = document.createElement("input");
			element5.type = "text";
			element5.value = 0;
			element5.setAttribute("name","Total No of Hours");
			element5.setAttribute("class","inputText_no");

			var element7 = document.createElement("span");
			element7.setAttribute("name","Total No of Hours Span");
			element7.setAttribute("style","font-weight:bold");
			element7.innerHTML = "0";
			cell5.appendChild(element5);
			cell5.appendChild(element7);

			var cell6 = row.insertCell(5);
			var element6 = document.createElement("input");
			element6.type = "text";
			element6.setAttribute("name","No of Beneficiaries");
			element6.setAttribute("class","inputText_no");
			element6.setAttribute("onkeyup","solveBeneficiaries()");
			cell6.appendChild(element6);

		}

		function deleteRow(tableID) {
			try {
			var table = document.getElementById(tableID);
			var rowCount = table.rows.length;

			for(var i=0; i<rowCount; i++) {
				var row = table.rows[i];
				var chkbox = row.cells[0].childNodes[0];
				if(null != chkbox && true == chkbox.checked) {
					table.deleteRow(i);
					rowCount--;
					i--;
				}

			}
			}catch(e) {
				alert(e);
			}
		}

		function solveSex(mode){
			var total = document.getElementById("TotalNumberOfVolunteers");
			var x = document.getElementById("SexMale");
			var y = document.getElementById("SexFemale");

			if(total.value >0){
				if(mode){
					y.value = total.value - x.value;
				}else{
					x.value = total.value - y.value;
				}
			}
		}

		function solveTotalHours(){
			 var txtS = new Array();
			 var txtX = new Array();
			 var total = 0;
			 txtS = document.getElementsByName("No of Volunteers");
			 txtX = document.getElementsByName("No of Days");
			 txtY = document.getElementsByName("No of Hours");
			 txtZ = document.getElementsByName("Total No of Hours");
			 txtA = document.getElementsByName("Total No of Hours Span");
			 
			 for(i=0;i<txtS.length;i++){
				 txtZ.item(i).value = txtS.item(i).value * txtX.item(i).value * txtY.item(i).value;
				 txtA.item(i).innerHTML = txtZ.item(i).value;
				 total += parseInt(txtZ.item(i).value);
			 }

			 document.getElementById("grandTotalHours").value = total;
			 document.getElementById("grandTotalHoursSpan").innerHTML = total;
		}

		function solveBeneficiaries(){
			 var txtS = new Array();
			 var total = 0;
			 txtS = document.getElementsByName("No of Beneficiaries");

			 for(i=0;i<txtS.length;i++){
				 if(txtS.item(i).value > 0)
				 	total += parseInt(txtS.item(i).value);
			 }

			document.getElementById("grandTotalBeneficiaries").value = total;
			document.getElementById("grandTotalBeneficiariesSpan").innerHTML = total;
		}

		function resetTotal(){
			document.getElementById("SexMale").value="";
			document.getElementById("SexFemale").value="";
		}

		function updateFiscalYear(){
			var from = document.getElementById("FiscalYearFrom");
			var to = document.getElementById("FiscalYearTo");

			to.disable = true;
		}

		function checkAge(){
			
		}

	</script>



	<form name="submitRegistration" action="updateregistrants.do" method="post"  enctype="multipart/form-data">
   	 	<input type="hidden" name="formPage" value="${param.formPage }"/>
   	 	<input type="hidden" name="memberId" value="${member.id}"/>
   	 	<input type="hidden" name="submissionId" value="${param.submissionId}"/>	
   	 	 <input type="hidden" name="allowedFields" value="Agriculture"/>
   	 	 <input type="hidden" name="allowedFields" value="Culture and Sports"/>
   	 	 <input type="hidden" name="allowedFields" value="Education"/>
   	 	 <input type="hidden" name="allowedFields" value="Electoral Assitance"/>
   	 	 <input type="hidden" name="allowedFields" value="Emergency Relief"/>
   	 	 <input type="hidden" name="allowedFields" value="Environment"/>
   	 	 <input type="hidden" name="allowedFields" value="Nutrition Food"/>
   	 	 <input type="hidden" name="allowedFields" value="Gender"/>
   	 	 <input type="hidden" name="allowedFields" value="Governance"/>
   	 	 <input type="hidden" name="allowedFields" value="HIV / AIDS"/>
   	 	 <input type="hidden" name="allowedFields" value="Health"/>
   	 	 <input type="hidden" name="allowedFields" value="Human Rights"/>
   	 	 <input type="hidden" name="allowedFields" value="Information and Communication Technologies (ICT)"/>
   	 	 <input type="hidden" name="allowedFields" value="Peace and conflict"/>
   	 	 <input type="hidden" name="allowedFields" value="Poverty reduction"/>
   	 	 <input type="hidden" name="allowedFields" value="Other Thematic Areas"/>
   	 	 <input type="hidden" name="allowedFields" value="Children and Youth"/>
   	 	 <input type="hidden" name="allowedFields" value="Person with disabilities"/>
   	 	 <input type="hidden" name="allowedFields" value="Internal Refugees and Displaced Persons"/>
   	 	 <input type="hidden" name="allowedFields" value="Farmers"/>
   	 	 <input type="hidden" name="allowedFields" value="Fisher folks"/>
   	 	 <input type="hidden" name="allowedFields" value="Indigenous Peoples"/>
   	 	 <input type="hidden" name="allowedFields" value="Informal Settlers"/>
   	 	 <input type="hidden" name="allowedFields" value="Women"/>
   	 	 <input type="hidden" name="allowedFields" value="Senior Citizens"/>
   	 	 <input type="hidden" name="allowedFields" value="Private Sector"/>
   	 	 <input type="hidden" name="allowedFields" value="Civil Society"/>
   	 	 <input type="hidden" name="allowedFields" value="Other Sectors"/>
   	 	 <input type="hidden" name="allowedFields" value="Fiscal Year From"/>
   	 	 <input type="hidden" name="allowedFields" value="Fiscal Year To"/>
   	 	 <input type="hidden" name="allowedFields" value="Total Number Of Volunteers"/>
   	 	 <input type="hidden" name="allowedFields" value="Sex Male"/>
   	 	 <input type="hidden" name="allowedFields" value="Sex Female"/>
   	 	 <input type="hidden" name="allowedFields" value="No of Adolescent (13-17)"/>
   	 	 <input type="hidden" name="allowedFields" value="No of Youth (18-35)"/>
   	 	 <input type="hidden" name="allowedFields" value="No of Adult (36-59)"/>
   	 	 <input type="hidden" name="allowedFields" value="No of Senior Citizens (60 and above)"/>
   	 	 <input type="hidden" name="allowedFields" value="Grand Total Number of Hours"/>
   	 	 <input type="hidden" name="allowedFields" value="Grand Total Beneficiaries"/>
   	 	 
   	 	 <input type="hidden" name="allowedFieldsRepeating" value="No of Volunteers"/>
   	 	 <input type="hidden" name="allowedFieldsRepeating" value="No of Days"/>
   	 	 <input type="hidden" name="allowedFieldsRepeating" value="No of Hours"/>
   	 	 <input type="hidden" name="allowedFieldsRepeating" value="Total No of Hours"/>
   	 	 <input type="hidden" name="allowedFieldsRepeating" value="No of Beneficiaries"/>

     <h3 align="left">Page ${param.formPage } of ${yearLimit+2 }</h3>
    <div class="table1">
		
   	 		<input type="hidden" name="year" value="${2005+param.formPage}"/>
   	 		<div class="tableHeader">${2005+param.formPage}</div>
   	
   		
	
   		
		<table cellpadding="0" cellspacing="0" class="subTable2_container">
			<tr><td colspan="2"><label>Check thematic areas and sectors of the volunteer work that apply for the given year. </label></td></tr>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" class="subTable2"> 
					<tr><td colspan="4"><img src="images/bgtop2.jpg" /></td></tr>
					<tr><td colspan="4" align="center"><strong>THEMATIC AREAS</strong><br /><bR /></td></tr>
					<tr>
						<td width="25"></td>
						<td width="100">
							<input name="Agriculture" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Agriculture']=='checked'}">checked</c:if>/><span class="label2"> Agriculture</span><br />
							<input name="Culture and Sports" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Culture and Sports']=='checked'}">checked</c:if>/><span class="label2"> Culture and Sports</span><br />
							<input name="Education" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Education']=='checked'}">checked</c:if>/><span class="label2"> Education</span><br />
							<input name="Electoral Assitance" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Electoral Assitance']=='checked'}">checked</c:if>/><span class="label2"> Electoral Assitance</span><br />
							<input name="Emergency Relief" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Emergency Relief']=='checked'}">checked</c:if>/><span class="label2"> Emergency Relief</span><br />
							<input name="Environment" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Environment']=='checked'}">checked</c:if>/><span class="label2"> Environment</span><br />
							<input name="Nutrition Food" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Nutrition Food']=='checked'}">checked</c:if>/><span class="label2"> Nutrition Food</span><br />
							<input name="Gender" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Gender']=='checked'}">checked</c:if>/><span class="label2"> Gender</span><br />
							<input name="Governance" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Governance']=='checked'}">checked</c:if>/><span class="label2"> Governance</span><br />
						</td>
						<td width="100">
							<input name="HIV / AIDS" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['HIV / AIDS']=='checked'}">checked</c:if>/><span class="label2"> HIV / AIDS</span><br />
							<input name="Health" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Health']=='checked'}">checked</c:if>/><span class="label2"> Health</span><br />
							<input name="Human Rights" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Human Rights']=='checked'}">checked</c:if>/><span class="label2"> Human Rights</span><br />
							<input name="Information and Communication Technologies (ICT)" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Information and Communication Technologies (ICT)']=='checked'}">checked</c:if>/><span class="label2"> Information and Communication Technologies (ICT)</span><br />
							<input name="Peace and conflict" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Peace and conflict']=='checked'}">checked</c:if>/><span class="label2"> Peace and conflict</span><br />
							<input name="Poverty reduction" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Poverty reduction']=='checked'}">checked</c:if>/><span class="label2"> Poverty reduction</span><br />
							<input name="Other Thematic Areas" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Other Thematic Areas']=='checked'}">checked</c:if>/><span class="label2"> Others</span><br />
						</td>
						<td width="25"></td>
					</tr>
					<tr><td colspan="4"><img src="images/bgbottom2.jpg" /></td></tr>
				</table>	
			  </td>
				<td>
			<table cellpadding="0" cellspacing="0" class="subTable2"> 
				<tr><td colspan="4"><img src="images/bgtop2.jpg" /></td></tr>
				<tr><td colspan="4" align="center"><strong>SECTORS</strong><br /><bR /></td></tr>
				<tr>
					<td width="20"></td>
					<td width="125">
						<input name="Children and Youth" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Children and Youth']=='checked'}">checked</c:if>/><span class="label2"> Children and Youth</span><br />
						<input name="Person with disabilities" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Person with disabilities']=='checked'}">checked</c:if>/><span class="label2"> Person with disabilities</span><br />
						<input name="Internal Refugees and Displaced Persons" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Internal Refugees and Displaced Persons']=='checked'}">checked</c:if>/><span class="label2"> Internal Refugees and Displaced Persons</span><br />
						<input name="Farmers" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Farmers']=='checked'}">checked</c:if>/><span class="label2"> Farmers</span><br />
						<input name="Fisher folks" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Fisher folks']=='checked'}">checked</c:if>/><span class="label2"> Fisher folks</span><br />
						<input name="Indigenous Peoples" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Indigenous Peoples']=='checked'}">checked</c:if>/><span class="label2"> Indigenous Peoples</span><br />
						<input name="Informal Settlers" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Informal Settlers']=='checked'}">checked</c:if>/><span class="label2"> Informal Settlers</span><br />					</td>
					<td width="100">
						<input name="Women" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Women']=='checked'}">checked</c:if>/><span class="label2"> Women</span><br />
						<input name="Senior Citizens" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Senior Citizens']=='checked'}">checked</c:if>/><span class="label2"> Senior Citizens</span><br />
						<input name="Private Sector" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Private Sector']=='checked'}">checked</c:if>/><span class="label2"> Private Sector</span><br />
						<input name="Civil Society" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Civil Society']=='checked'}">checked</c:if>/><span class="label2"> Civil Society</span><br />
						<input name="Other Sectors" type="checkbox" class="checkBox1" value="checked" <c:if test="${mapFieldValues['Other Sectors']=='checked'}">checked</c:if>/><span class="label2"> Others</span><br />					</td>
					<td width="5"></td>
				</tr>
				<tr><td colspan="4"><img src="images/bgbottom2.jpg" /></td></tr>
			</table>
			</td>
			</tr>
		</table>
	
		<table cellpadding="0" cellspacing="0" class="mainTable"> 
			<tr>
				<td colspan="4">
					<span class="label">Using the Form below, record the number of volunteer hours for the year 2007 or the equivalent fiscal year of your organization. You may also include the names of the volunteers in a separate annex.</label><br /><br />
				</td>
			</tr>
		</table>
		
		<table class="mainTable"> 
			<tr>
				<td>Fiscal Year from</td>
				<td>
                	<select type="text" name="Fiscal Year From" id="FiscalYearFrom" alt="Fiscal Year From" onchange="updateFiscalYear()">
						<option value="">---</option>
						<c:forEach var="i" begin="2000" end="2010">
		    				<option value="${i}"  ${mapFieldValues['Fiscal Year From'] eq i ? 'selected' : ''}>${i}  </option>
		    			</c:forEach>
		    		</select>
				</td>
				<td>to</td>
				<td>
					<span id="FiscalYearSpace">
						<select type="text" name="Fiscal Year To" id="FiscalYearTo" alt="Fiscal Year To" disable>
							<option value="">---</option>
							<c:forEach var="i" begin="2000" end="2010">
		    					<option value="${i}"  ${mapFieldValues['Fiscal Year To'] eq i ? 'selected' : ''}>${i}  </option>
		    				</c:forEach>
			    		</select>
		    		</span>
					<br />
				</td>
			</tr>
		</table>
		
		<table cellpadding="0" cellspacing="0" class="mainTable">
			<tr>
				<td width="230">
					Volunteers
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td width="150"><span class="label8">Total Number of Volunteers</span></td>	
							<td width="30"><input id="TotalNumberOfVolunteers" name="Total Number Of Volunteers" type="text" class="inputText_no" onkeyup="resetTotal()" value="${mapFieldValues['Total Number Of Volunteers']}"/></td>	
						</tr>
					</table>
				</td>
				<td valign="top">
					Sex
						<table>
							<tr>
								<td><span class="label8">Male</span></td>
								<td>
									<input id="SexMale" name="Sex Male" type="text" class="inputText_no" onkeyup="solveSex(true);" value="${mapFieldValues['Sex Male']}">
								</td>
								<td><span class="label8">Female</span></td>
								<td>
									<input id="SexFemale" name="Sex Female" type="text" class="inputText_no" onkeyup="solveSex(false);" value="${mapFieldValues['Sex Female']}">
								</td>
							</tr>
						</table>
				</td>
			</tr> 
			<tr>
				<td colspan="2">				
							
						Age Range*
						<table>
							<tr>
								<td width="60"><span class="label8">Adolescent<br />13-17 yrs old</span></td>
								<td width="80"><br />
									<input id="age1" name="No of Adolescent (13-17)" type="text" class="inputText_no" value="${mapFieldValues['No of Adolescent (13-17)']}"/>
								</td>
								<td width="60"><span class="label8">Youth<br />18-35 yrs old</span></td>
								<td width="80"><br />
									<input id="age2" name="No of Youth (18-35)" type="text" class="inputText_no" value="${mapFieldValues['No of Youth (18-35)']}"/>
								</td>
							</tr>
							<tr>
								<td width="120"><span class="label8">Adult<br />36-59 yrs old</span></td>
								<td width="80"><br />
									<input id="age3" name="No of Adult (36-59)" type="text" class="inputText_no" value="${mapFieldValues['No of Adult (36-59)']}"/>
								</td>
								<td width="120"><span class="label8">Senior Citizens<br />60 yrs old and above</span></td>
								<td width="80"><br />
									<input id="age4" name="No of Senior Citizens (60 and above)" type="text" class="inputText_no" value="${mapFieldValues['No of Senior Citizens (60 and above)']}"/>
								</td>
							</tr>
						</table>					
					
					
					Computation of Volunteer Hours and Number of Beneficiaries.
					<table id="dataTable" cellpadding="0" cellspacing="0" class="tblVolunteer" >
						<tr>
							<td></td>
							<td>No. of Volunteers</td>
							<td>No. of Days</td>	
							<td>No. of Hours</td>
							<td>Total No. of Hours</td>
							<td>No. of Beneficiaries</td>
						</tr>
						<c:if test="${empty infoRepeating}">
						<tr>
							<td><input name="chk" type="checkbox" /></td>
							<td>
								<input name="No of Volunteers" type="text" class="inputText_no" onkeyup="solveTotalHours()" value="${mapFieldValues['No of Volunteers']}"/>
							</td>
							<td>
								<input name="No of Days" type="text" class="inputText_no" onkeyup="solveTotalHours()" value="${mapFieldValues['No of Days']}"/>
							</td>
							<td>
								<input name="No of Hours"  type="text" class="inputText_no" onkeyup="solveTotalHours()" value="${mapFieldValues['No of Hours']}"/>
							</td>
							<td>
								<input name="Total No of Hours" type="text" class="inputText_no" value="0"/>
								<span name="Total No of Hours Span" style="font-weight:bold">0</span>
							</td>
							<td><input name="No of Beneficiaries" type="text" class="inputText_no" onkeyup="solveBeneficiaries()" value="${mapFieldValues['No of Beneficiaries']}"/></td>
						</tr>
						</c:if>
							<c:if test="${not empty infoRepeating}">
										<c:set var="count" value="${0}" /> 
							      <c:forEach items="${infoRepeating}" var="p">
										<c:set var="count" value="${count+1}" /> 
						          </c:forEach>
								
								  <c:forEach var="i" begin="0" end="${count/5-1}">
								  	
						            <tr>
										<td><input name="chk" type="checkbox" /></td>
										<td>
											<c:set var="ii" value="${'No of Volunteers'}${i}" />
											<input name="No of Volunteers" type="text" class="inputText_no" onkeyup="solveTotalHours()" value="${mapRepeatingFieldValues[ii]}"/>
										</td>
										<td>
											<c:set var="ii" value="${'No of Days'}${i}" /> 
											<input name="No of Days" type="text" class="inputText_no" onkeyup="solveTotalHours()" value="${mapRepeatingFieldValues[ii]}"/>
										</td>
										<td>
											<c:set var="ii" value="${'No of Hours'}${i}" /> 
											<input name="No of Hours"  type="text" class="inputText_no" onkeyup="solveTotalHours()" value="${mapRepeatingFieldValues[ii]}"/>
										</td>
										<td>
											<c:set var="ii" value="${'Total No of Hours'}${i}" /> 
											<input name="Total No of Hours" type="text" class="inputText_no" value="${mapRepeatingFieldValues[ii]}"/>
											<span name="Total No of Hours Span" style="font-weight:bold">${mapRepeatingFieldValues[ii]}</span>
										</td>
										<td>
											<c:set var="ii" value="${'No of Beneficiaries'}${i}" /> 
											<input name="No of Beneficiaries" type="text" class="inputText_no" onkeyup="solveBeneficiaries()" value="${mapRepeatingFieldValues[ii]}"/></td>
									</tr>
						          </c:forEach>
						</c:if>
					</table>
					
					<table cellpadding="0" cellspacing="0" class="tblVolunteer">	
						<tr>
							<td><input type="button" value="Add More" onclick="addRow('dataTable')"></td>
							<td><input type="button" value="Delete Selected" onclick="deleteRow('dataTable')"></td>
						</tr>
						<tr>
							<td colspan="2">
								Grand Total Number of Hours
							</td>
							<td>
								<input type="text" name="Grand Total Number of Hours" id="grandTotalHours" class="inputText_no" value=""/>
								<strong><span id="grandTotalHoursSpan" style="text-align:center">0</span></strong>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								Grand Total Number of Beneficiaries
							</td>
							<td>
								<input type="text" name="Grand Total Beneficiaries" id="grandTotalBeneficiaries" class="inputText_no" value=""/>
								<strong><span id="grandTotalBeneficiariesSpan" style="text-align:center">0</span></strong>
							</td>
						</tr>
						<tr>
						</tr>
					</table>
					
				</td>
			</tr>
			<td colspan="3">
					<input name="" type="submit" value="Submit"/>
			</td>

			</tr>
		</table>
	</div>
<c:if test="${param.formPage!='1'}">
	<script type="text/javascript">
		solveTotalHours();
		solveBeneficiaries();
	</script>
</c:if>	
	</form>
</c:if>	
</c:if>
				
				
<c:if test="${param.submissionId==null}">	
<h3>Add Registrant</h3>
				<table>
					 	<tr class="headbox"> 
					 		<th width="20%"></th>
					 		<th></th>

						</tr>
				
				</table>
 <form name="submitRegistration" action="submitRegistration.do" method="post" enctype="multipart/form-data">
					      <input type="hidden" name="allowedFields" value="E-mail Address"/>
						  <input type="hidden" name="allowedFields" value="Organization Name"/>
						  <input type="hidden" name="allowedFields" value="Central Office"/>
						  <input type="hidden" name="allowedFields" value="Local Chapter"/>
						  <input type="hidden" name="allowedFields" value="Local Chapter Area"/>
						  <input type="hidden" name="allowedFields" value="Year Established"/>
						  <input type="hidden" name="allowedFields" value="Office Address"/>
						  <input type="hidden" name="allowedFields" value="Phone Number"/>
						  <input type="hidden" name="allowedFields" value="Fax Number"/>
						  <input type="hidden" name="allowedFields" value="Mobile Number"/>
						  <input type="hidden" name="allowedFields" value="Network Affiliation1"/>
						  <input type="hidden" name="allowedFields" value="Network Affiliation2"/>
						  <input type="hidden" name="allowedFields" value="Network Affiliation3"/>
						  <input type="hidden" name="allowedFields" value="Network Affiliation4"/>
						  <input type="hidden" name="allowedFields" value="Network Affiliation5"/>
						  <input type="hidden" name="allowedFields" value="Network Affiliation6"/>
						  <input type="hidden" name="allowedFields" value="SEC"/>
						  <input type="hidden" name="allowedFields" value="SEC Year"/>
						  <input type="hidden" name="allowedFields" value="DSWD"/>
						  <input type="hidden" name="allowedFields" value="DSWD Year"/>
						  <input type="hidden" name="allowedFields" value="PCNC"/>
						  <input type="hidden" name="allowedFields" value="PCNC Year"/>
						  <input type="hidden" name="allowedFields" value="CDA"/>
						  <input type="hidden" name="allowedFields" value="CDA Year"/>
						  <input type="hidden" name="allowedFields" value="PNVSCA"/>
						  <input type="hidden" name="allowedFields" value="PNVSCA Year"/>
					 
					
					  
						 <div class="table1">
							<div class="tableHeader">LOGIN INFORMATION</div>
							<table cellpadding="0" cellspacing="0" class="mainTable">
			 <tr>
				 <td colspan="2">	
				<label>*Username: </label>
				</td>
				<td colspan="2">	
				<input name="Username" type="text" class="inputText1"/>
				<c:if test="${param.message != null}"><font color="#CC0000"><b>${param.message}</b></font></c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">	
				<label>*Password: </label>
				</td>
				<td colspan="2">	
				<input name="Password" type="password" class="inputText1"/><br/>
				</td>
			</tr>
			<tr>
				<td colspan="2">	
				<label>*Repeat Password: </label>
				</td>
				<td colspan="2">	
				<input name="Password1" type="password" class="inputText1"/><br/>
				</td>
			</tr>
			<tr>
				<td colspan="2">					
				<label>*Email: </label>
				</td>
				<td colspan="2">	
				 <input name="E-mail Address" type="text" class="inputText4"/>
				</td>
			</tr>
		</table>
	</div><!-- END OF TABLE -->


	<div class="table1">
		<div class="tableHeader">IDENTIFYING INFORMATION</div>
		<table cellpadding="0" cellspacing="0" class="mainTable">
		 
			<tr>
			  	<td colspan="2">				
					<label>*Name of Organization</label><br />
					<input name="Organization Name" type="text" class="inputText1"/><br />
					<input value="checked" name="Central Office" type="checkbox" class="checkBox1"/><span class="label2"> Central Office</span>
					<input value="checked" name="Local Chapter" type="checkbox" class="checkBox1"/><span class="label2"> Local Chapter</span>
					<input name="Local Chapter Area" type="text" class="inputText2"/><br />
				</td>
				<td width="10px"></td>
				<td>				
					<label>*Year Established</label><br />
					<input name="Year Established" type="text" class="inputText2"/><br />
				</td>
			</tr>
			<tr>
			  	<td colspan="4">				
					<label>*Office Address</label><br />
					<input name="Office Address" type="text" class="inputText3"/><br />
				</td>
			</tr>
			<tr>
			  	<td height="65">				
					<label>Phone Number</label><br />
					<input name="Phone Number" type="text" class="inputText4"/><br />
			 	 </td>
				<td>
					<label>Fax Number</label><br />
					<input name="Fax Number" type="text" class="inputText4"/><br />
				</td>
				<td width="10px"></td>
				<td>				
					<label>*Mobile Number</label><br />
					<input name="Mobile Number" type="text" class="inputText4"/><br />
				</td>
				
			</tr>
			<tr>
			  	<td>				
					<label>Network Affiliation</label><br />
					<input name="Network Affiliation1" type="text" class="inputText4"/><br />
					<input name="Network Affiliation2" type="text" class="inputText4"/><br />
				</td>
				<td>
					<br />
					<input name="Network Affiliation3" type="text" class="inputText4"/><br />
					<input name="Network Affiliation4" type="text" class="inputText4"/><br />
				</td>
				<td width="10px"></td>
				<td>				
					<br />
					<input name="Network Affiliation5" type="text" class="inputText4"/><br />
					<input name="Network Affiliation6" type="text" class="inputText4"/><br />
				</td>
			</tr>
		
		</table>
		<table cellpadding="0" cellspacing="0" class="subTable"> 
				<tr><td colspan="1"><label>AGENCY</label><br /><br /></td>
			<td colspan="1"><label>YEAR</label><br /><br /></td>
			<td colspan="1"><br /><br /></td>
			<td colspan="1"><label>AGENCY</label><br /><br /></td>
			<td colspan="1"><label>YEAR</label><br /><br /></td>
			
			</tr>
			<tr>
				<td><input value="checked" name="SEC" type="checkbox" class="checkBox2"/><span class="label3"> Securities and Exchange Commission (SEC)</span></td>
				<td><input name="SEC Year" type="text" class="inputText2"/></td>
				<td width="10px"></td>
				<td width="210px">
				<input value="checked" name="DSWD" type="checkbox" class="checkBox1"/><span class="label3"> Department of Social Welfare and Development</span></td>
				<td><input name="DSWD Year" type="text" class="inputText2"/></td>
			</tr>
			<tr>
				<td><input value="checked" name="PCNC" type="checkbox" class="checkBox2"/><span class="label3"> Philippine Council of NGO Certification (PCNC)</span></td>
				<td><input name="PCNC Year" type="text" class="inputText2"/></td>
				<td width="10px"></td>
				<td width="210px">
				<input value="checked" name="CDA" type="checkbox" class="checkBox1"/><span class="label3"> Cooperative Development Authority (CDA)</span></td>
				<td><input name="CDA Year" type="text" class="inputText2"/></td>
			</tr>
			<tr>
				<td><input value="checked" name="PNVSCA" type="checkbox" class="checkBox2"/><span class="label3"> Philippine National Volunteer Service Coordinatinf Agency (PNVSCA)</span></td>
				<td><input name="PNVSCA Year" type="text" class="inputText2"/></td>				
			</tr>
		</table>
						</div><!-- END OF TABLE -->
						
						
						<div class="table1">
							<div class="tableHeader">CERTIFICATION</div>
							<table cellpadding="0" cellspacing="0" class="mainTable"> 
								<tr>
									<td><input name="agree" value="checked" type="checkbox" class="checkBox2"/>
									<span class="label"> I hereby certify that all information stated in this registration form is true and correct.</span>
									<input name="" type="submit" value="Submit"/></td>
								</tr>
							</table>
						</div><!-- END OF TABLE -->
						
						</form>
						
		
		
		


</c:if>
				
				
				
				
				
				
