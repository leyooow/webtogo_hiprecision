<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 


<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="forms" />
<c:set var="submenu" value="registrants" />

<c:set var="pagingAction" value="registration.do" />

<script>

/*	function showMessage(id) 
	{
		var content = document.getElementById('emailContent_'+id).innerHTML;
		Tip(content);
	}
*/
	function setCheckbox(FormName, FieldName, CheckValue)
	{			

		if(!document.forms[FormName])
			return;
		var objCheckBoxes = document.forms[FormName].elements[FieldName];
		if(!objCheckBoxes)
			return;
		var countCheckBoxes = objCheckBoxes.length;
		if(!countCheckBoxes)
			objCheckBoxes.checked = CheckValue;
		else
			// set the check value for all check boxes
			for(var i = 0; i < countCheckBoxes; i++)
				objCheckBoxes[i].checked = CheckValue;
	}	

	function confirmDelete(FormName,FieldName){
		var error 				= false;
		var message	 			= "No submission is selected";
		var i					= 0;
		var selected			= 0;
		var objCheckBoxes 		= document.forms[FormName].elements[FieldName];
		document.forms[FormName].selectedValues.value="";

		while(i != (objCheckBoxes.length)){
			if (objCheckBoxes[i].checked == true){
				document.forms[FormName].selectedValues.value = document.forms[FormName].selectedValues.value + objCheckBoxes[i].value + "_";
				selected++;
			}
			i++;
		}
		
		if (!selected)
			error = true;
						
		if(error) {
			alert(message);
		}
		else {			
			message="Do you really want to delete "+ selected +" submission?";
			if (confirm(message))
				return true;
		}	
		return false;	
		
	}
</script>


<script language="JavaScript" src="../javascripts/overlib.js"></script>

<script>
function showMessage(id) {
	var content = document.getElementById('emailContent_'+id).innerHTML;
	overlib(content, STICKY, NOCLOSE)}
</script>
  
<body>

<!-- 
	<script type='text/javascript' src='../javascripts/wz_tooltip.js'></script>
 -->
	
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
			  	<div id="companyhome"> 
				
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
						          			<input type="file" name="${e.otherField.name}" alt="File Name" size="30"/>&nbsp;
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
												 	<c:when test="${e.otherField.name=='Date of Birth'}">
															<input type="text" name="Date of Birth" id="endDate" value="${e.content}" />
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
				  <input type="hidden" name="allowedFields" value="Current School"/>
		          <input type="hidden" name="allowedFields" value="VCAL Number"/>
		          <input type="hidden" name="allowedFields" value="VCAL PIN"/>
		          <input type="hidden" name="allowedFields" value="Referred by"/>
		          <input type="hidden" name="allowedFields" value="Family Name"/>
		          <input type="hidden" name="allowedFields" value="Given Name"/>
		          <input type="hidden" name="allowedFields" value="Preferred Name"/>
		          <input type="hidden" name="allowedFields" value="Date of Birth"/>
		          <input type="hidden" name="allowedFields" value="Gender"/>
		          <input type="hidden" name="allowedFields" value="Nationality"/>
		          <input type="hidden" name="allowedFields" value="E-mail Address"/>
		          <input type="hidden" name="allowedFields" value="Relationship Status"/>
		          <input type="hidden" name="allowedFields" value="Home Address"/>
		          <input type="hidden" name="allowedFields" value="Home Telephone"/>
		          <input type="hidden" name="allowedFields" value="Home Mobile"/>
		          <input type="hidden" name="allowedFields" value="Mother Family Name"/>
		          <input type="hidden" name="allowedFields" value="Mother Given Names"/>
		          <input type="hidden" name="allowedFields" value="Mother Home Mobile"/>
		          <input type="hidden" name="allowedFields" value="Father Family Name"/>
		          <input type="hidden" name="allowedFields" value="Father Given Name"/>
		          <input type="hidden" name="allowedFields" value="Father Home Mobile"/>
		          <input type="hidden" name="allowedFields" value="Address in Australia"/>
		          <input type="hidden" name="allowedFields" value="Home Telephone in Australia"/>
		          <input type="hidden" name="allowedFields" value="Mobile in Australia"/>
		          <input type="hidden" name="allowedFiles" value="digital"/>
		          <input type="hidden" name="allowedFiles" value="passport"/>
		          <input type="hidden" name="allowedFiles" value="visa"/>
		          <input type="hidden" name="allowedFiles" value="birth"/>
		          <input type="hidden" name="allowedFiles" value="financial"/>
		          <input type="hidden" name="allowedFiles" value="academic"/>
		          <input type="hidden" name="allowedFiles" value="ecoe"/>
		          <input type="hidden" name="submissionId" value="${param.submissionId}"/>		 
</c:if>
<c:if test="${company.id==190}">		
		  <input type="hidden" name="allowedFields" value="Username"/>
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
		  <input type="hidden" name="allowedFields" value="Other Programs"/>
		  <input type="hidden" name="allowedFields" value="Recruitment and Selection of volunteers"/>  
		  <input type="hidden" name="allowedFields" value="Monitoring and Evaluation"/>
		  <input type="hidden" name="allowedFields" value="Orientation, Formation and Training of volunteers"/>
		  <input type="hidden" name="allowedFields" value="Volunteer Recognition"/>
		  <input type="hidden" name="allowedFields" value="Volunteer Nurturance / Retention"/>
		  <input type="hidden" name="allowedFields" value="Other Volunteer Management Training"/>
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

				 </div>
				 
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
