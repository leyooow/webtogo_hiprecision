<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />

 <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="events" />

<script>

	function submitForm(myForm) {
		var requestDetails= getElement('requestDetails');
		var remarks= getElement('remarks');
		var actionTaken= getElement('actionTaken');
		var requestDate= getElement('requestDate');
		var description = getElement('description');
		var status =  getElement('status');
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(description.length == 0) {
			error = true;
			messages += " * description was not specified. \n";
		} 
		
		if(status.length == 0) {
			error = true;
			messages += " * status was not specified. \n";
		} 

		if(requestDetails.length == 0) {
			error = true;
			messages += " * request details was not specified. \n";
		} 

		if(remarks.length == 0) {
			error = true;
			messages += " * remarks was not specified. \n";
		} 

		if(actionTaken.length == 0) {
			error = true;
			messages += " * action taken was not specified. \n";
		} 

		if(requestDate.length == 0) {
			error = true;
			messages += " * request date was not specified. \n";
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
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
</script> 

<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;">
		
		
		<form method="post" action="savenewactivity.do" onsubmit="return submitForm(this);">
		
		<c:if test="${activity.id > 0}">
			<input type="hidden" name="activityId" value="${activity.id}">
		</c:if>
		
		<table width="50%">
					
			<tr>
				<td colspan="6"></td>
			</tr>
		
			<c:if test="${activity.id == null}">
				<tr class="headbox">
					<th colspan="3">
						New Activity
					</th>
				</tr>
			</c:if>
			
			 <tr>
				<td width="1%" nowrap>Request Type</td>
				<td width="10px"></td>
				<td>
				<select id="status" name="activity.type" class="combobox1">
						<c:choose>
							<c:when test="${activity.type=='Basic Content Update'}">
								<option value="Basic Content Update" selected="selected">Basic Content Update</option>
							</c:when>
							<c:otherwise>
								<option value="Basic Content Update">Basic Content Update</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${activity.type=='Web Redesign'}">
								<option value="Web Redesign" selected="selected">Web Redesign</option>
							</c:when>
							<c:otherwise>
								<option value="Web Redesign">Web Redesign</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${activity.type=='Combination'}">
								<option value="Combination" selected="selected">Combination</option>
							</c:when>
							<c:otherwise>
								<option value="Combination">Combination</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${activity.type=='Others'}">
								<option value="Others" selected="selected">Others</option>
							</c:when>
							<c:otherwise>
								<option value="Others">Others</option>
							</c:otherwise>
						</c:choose>
				</select>
				</td>
					
			<c:if test="${activity.id > 0}">
				
					<td width="1%" nowrap>Status</td>
					<td width="10px"></td>
					<td>
					<select id="status" name="status" class="combobox1">
						<c:forEach items="${statusTypes}" var="status">
							<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
							<option value="${status.statusCode}" ${(status.statusCode == activity.status.statusCode) ? "selected" : "" }>${status.value}</option>
						</c:forEach>
					</select>
					</td>
				
			</c:if>
			
			<c:if test="${activity.id == null}">
			
			
				
					<div id="test" style="display: none;">
					<select id="status" name="status" class="combobox1">
						<c:forEach items="${statusTypes}" var="status">
								<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
								<option value="${status.statusCode}" ${(status.statusCode == activity.status.statusCode) ? "selected" : "" }>${status.value}</option>
						</c:forEach>
					</select>
					</div>
					</td>
				
			
			
			</c:if>
			</tr>
	
			<tr>
					<td width="1%" nowrap>Request Date</td>
					<td width="10px"></td>
							<td nowrap >
							<c:if test="${activity.id > 0}">
								<fmt:formatDate pattern="MM-dd-yyyy" value="${company2.expiryDate}" var="ed"/>
											 
								<input type="text" id="requestDate" name="activity.requestDate" class="textbox3" value="${activity.requestDate}" readonly="readonly"/> 						
								<a href="javascript:;" id="expiry_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
						 		<script type="text/javascript">
								    Calendar.setup({
								        inputField     :    "requestDate",     // id of the input field
								        ifFormat       :    "%m-%d-%Y",      // format of the input field
								        button         :    "expiry_date_button",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true
								    });
								</script> 
							</c:if>
							<c:if test="${activity.id == null}">
								<fmt:formatDate pattern="MM-dd-yyyy" value="${company2.expiryDate}" var="ed"/>
											 
								<input type="text" id="requestDate" name="activity.requestDate" class="textbox3" value="<%= (new java.text.SimpleDateFormat("MM-dd-yyyy")).format(new java.util.Date()) %>" readonly="readonly"/> 						
								<a href="javascript:;" id="expiry_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
						 		<script type="text/javascript">
								    Calendar.setup({
								        inputField     :    "requestDate",     // id of the input field
								        ifFormat       :    "%m-%d-%Y",      // format of the input field
								        button         :    "expiry_date_button",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true
								    });
								</script> 
							</c:if>
							</td>	
				<td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Request Details</td>
				<td width="10px"></td>
				<td><textarea id="requestDetails" name="activity.requestDetails" class="textbox3">${activity.requestDetails}</textarea></td>
				<td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Requested By</td>
				<td width="10px"></td>
				<td><input type="text" id="description" name="activity.description" class="textbox3" value=${activity.description}></td>
				<td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Remarks</td>
				<td width="10px"></td>
				<td><textarea id="remarks" name="activity.remarks" class="textbox3">${activity.remarks}</textarea></td>
				<td width="1%" colspan="3" nowrap></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Action Taken</td>
				<td width="10px"></td>
				<td><input type="text" id="actionTaken" name="activity.actionTaken" class="textbox3" value=${activity.actionTaken}></td>
				<td width="1%" colspan="3" nowrap></td>
			</tr>
			
			
			<!--<tr>
				<td width="1%" nowrap>Design iteration</td>
				<td width="10px"></td>
				<td><input type="text" id="designIteration" name="activity.designIteration" class="textbox3" value=${activity.designIteration}></td>
			</tr>-->
			
			
			
<%--			
			<tr>
				<td width="1%" nowrap>WTG Admin</td>
				<td width="10px"></td>
				<td>
				<select id="createdBy" name="createdBy" class="combobox1">
					<option value="">- Select WTG Administrator -</option>
					<c:forEach items="${wtgUsers}" var="wtg">
						<option value="${wtg.id}" >${wtg.firstname}&nbsp;${wtg.lastname }</option>
					</c:forEach>
				</select>
				</td>
			</tr>
--%>					
			
							
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				 
				
				 
				<c:choose>
					<c:when test="${activity.id >0}">
						<input type="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
						<input type="button" value="Cancel" onclick="window.location='activities.do'" class="upload_button2">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="upload_button2">
					</c:otherwise>
				</c:choose>
								
				</td>
				<td width="1%" colspan="3" nowrap></td>
			</tr>
					
		</table>
		
		</form>
		
		</td>
	</tr>
</table>
</div>