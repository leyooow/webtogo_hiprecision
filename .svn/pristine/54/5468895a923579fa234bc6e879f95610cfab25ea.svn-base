<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 

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
				
				<h3>Form Submissions</h3>
				 
				<c:if test="${(company.id==150 ||company.id==199|| company.id==194) && hasRegistrants}">
				<div style="position:relative; left:50%; background-color: #efefef; width:400px; border:none; padding: 5px;">
					<form action="downloadRegistrants.do" method="post" name="downloadForm" >
						<input type="hidden" name="company_id" value="${company.id}"/>
						<b>Download in Excel Format</b>
						<table cellpadding="0" cellspacing="0" border="0px" style="background-color: white;">
							<tr>
								<td><input type="radio" name="filter" value="all" checked="checked" /><b>All </b></td>	
								<td nowrap><input type="radio" name="filter" value="byMonth"  /><b>By Month </b>
									&nbsp;																
									<select name="byMonth">
										<option value="1">January</option>
										<option value="2">February</option>
										<option value="3">March</option>
										<option value="4">April</option>
										<option value="5">May</option>
										<option value="6">June</option>
										<option value="7">July</option>
										<option value="8">August</option>
										<option value="9">September</option>
										<option value="10">October</option>
										<option value="11">November</option>
										<option value="12">December</option>														
									</select>			
									<select name="byYear">
										<c:forEach begin="2005" end="${year-1}"  var="date" >
											<option value=${date}> ${date} </option>
										</c:forEach>
										<option value="${year}" selected>${year}</option>
									</select>
								</td>
								<td><input type="submit" value="Download"/></td>
					   		</tr>
					   	</table>
					</form>
				</div>				
				</c:if>
				
				<c:if test="${(company.id==190) && hasRegistrants}">
				<div style="position:relative; left:50%; background-color: #efefef; width:400px; border:none; padding: 5px;">
					<form action="downloadRegistrantsIave.do" method="post" name="downloadForm" >
						<input type="hidden" name="company_id" value="${company.id}"/>
						<b>Download in Excel Format</b>
						<table cellpadding="0" cellspacing="0" border="0px" style="background-color: white;">
							<tr>
								<td><input type="radio" name="filter" value="all" checked="checked" /><b>All </b></td>	
								<td nowrap><!--<input type="radio" name="filter" value="byMonth"  /><b>By Month </b>
									&nbsp;																
									<select name="byMonth">
										<option value="1">January</option>
										<option value="2">February</option>
										<option value="3">March</option>
										<option value="4">April</option>
										<option value="5">May</option>
										<option value="6">June</option>
										<option value="7">July</option>
										<option value="8">August</option>
										<option value="9">September</option>
										<option value="10">October</option>
										<option value="11">November</option>
										<option value="12">December</option>														
									</select>			
									<select name="byYear">
										<c:forEach begin="2005" end="${year-1}"  var="date" >
											<option value=${date}> ${date} </option>
										</c:forEach>
										<option value="${year}" selected>${year}</option>
									</select>
								--></td>
								<td><input type="submit" value="Download"/></td>
					   		</tr>
					   	</table>
					</form>
				</div>
				</c:if>
				<br/>
				
				 <c:if test="${param['evt'] != null}">
						<ul> 
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Submission was successfully deleted.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Submission was successfully update.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'error'}">
								<li><span class="actionMessage">An unexpected error occurred during deletion/update. Deletion/update failed.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'errordownload'}">
								<li><span class="actionMessage">Excel Download: (No item for that month/year)</span></li>
							</c:if>
						</ul>
					</c:if>
				  	<c:if test="${company.id==190}">
						<table cellpadding="0" cellspacing="0" border="0px" style="background-color: white;">
							<tr>
								<td style="width:20px" align="left">
									<input value="Add Registrant" type="button" onClick="document.location.href='editregistrantsTemplate.do';">
								</td>
								<td style="width:10px" align="left">
									<form action="downloadSurveySummaryIave.do" method="post" name="downloadForm" >
										<input type="hidden" name="company_id" value="${company.id}"/>
										<input type="submit" value="Download Surveys Summary"/>
									</form>
								</td>
								<td align="left">
									<form action="downloadRegistrantsSummaryIave.do" method="post" name="downloadForm" >
										<input type="hidden" name="company_id" value="${company.id}"/>
										<input type="submit" value="Download Registrant Summary"/>
									</form>
								</td>
					   		</tr>
					   	</table>
					</c:if>						
				
				 <table width="100%" border="0"> 
						<tr>
							<td>
							
							<c:if test="${param['filterDays'] == null}">
								<div style="float: left;"><%@include file="includes/paging.jsp" %></div>
							</c:if>
													
							</td>
						</tr>
					</table>

			<c:if test="${user.userType.value != 'Company Staff'}">				 
				<form name="emailForm" action="deletemultipleregistrants.do" method="post" onsubmit=" return confirmDelete('emailForm','emailCheckbox')">
			</c:if>	
					 <table>
					 	<tr class="headbox"> 
					 	<c:if test="${user.userType.value != 'Company Staff'}">	
					 		<th width="5px">&nbsp;</th>
					 	</c:if>	
					 		<th width="10%">Name</th>
					 		<th width="5%">Email</th>
					 		<th width="10%">Submitted From</th>
							<th width="15%">Date Submitted</th>
							<th width="10%">Status</th>
							<th width="10%">Action</th>
							<th width="30%">Attachments</th>
						</tr>						
						
					 	<c:forEach items="${members}" var="e">
						 	<tr>	
						 	<c:if test="${user.userType.value != 'Company Staff'}">		 	
						 	
						 		<td width="5px"> 

							 				<input type="checkbox" name="emailCheckbox" value="${e.id}"> 
				
						 		
						 		</td>
						 	</c:if>	
						 		<td> ${e.firstname} ${e.lastname}	</td> 
															 		
						 		<td><a href="mailto:${e.email}"> ${e.email}</a></td>		 
						 		<td>${e.purpose}</td>
						 		<td><fmt:formatDate pattern="MMM dd, yyyy - hh:mm:ss a" value="${e.updatedOn}" /></td>
						 		<td>${e.status}</td>
						 		<td>
						 			<c:if test="${user.userType.value != 'Company Staff'}">
						 			 		<a href="editregistrantsTemplate.do?submissionId=${e.id}">Update</a> 
						 			 | 
							 		 <a onclick="return confirm('Do you really want to delete this submission');" href="deleteregistrants.do?submissionId=${e.id}">Delete</a>		
						 			</c:if>	
						 			
						 				
						 		</td>
						 		<td valign="middle">
						 			<c:forEach items="${e.registrationItemOtherFields}" var="p">
						 				<c:if test="${mapFileName[p.otherField.name]!=null}">
										<a href="http://${company.serverName}/attachments/registrations/${p.content}">
										 		${p.content}
										 </a><br/>
										 </c:if>
									 </c:forEach>
						 		</td>			 		
						 	</tr>
					 	

					 	</c:forEach>
					<c:if test="${not empty members}"> 	
					<c:if test="${user.userType.value != 'Company Staff'}">						 	
					 	<tr>
					 		<td colspan="8"> 
					 			<b>Select:</b>							
					 			<a onclick="setCheckbox('emailForm', 'emailCheckbox', true )"  href="javascript:void(0);">All</a> , 
					 			<a onclick="setCheckbox('emailForm', 'emailCheckbox', false)" href="javascript:void(0);">None</a> &nbsp; | &nbsp;					 							 			  
					 			<!--  <a onclick="submitForm(this)" href="javascript:void(0);">Delete</a> -->
					 			<input type="hidden" name="selectedValues" value="">
					 			<input type="submit" value="Delete Selected" >
					   								 			
					 		</td>
					 	</tr>
					 </c:if>		
					 </c:if>			 	
					 </table>
				<c:if test="${user.userType.value != 'Company Staff'}">	
				 </form>  		 
				</c:if> 
				 
				  <table width="100%" border="0">
				  		<c:if test="${param['filterDays'] != null}">
				  		<tr>
				  			<td align="left"><a href="formsubmissions.do">Show All</a></td>
				  		</tr>
				  		</c:if>
				  		
						<tr>
							<td>
							
							<c:if test="${param['filterDays'] == null}">
								<div style="float: left;"><%@include file="includes/paging.jsp" %></div>
							</c:if>
													
							</td>
						</tr>
					</table>

				 </div>
				 
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>
