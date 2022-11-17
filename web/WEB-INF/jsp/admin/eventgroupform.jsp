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
		var title =  getElement('eventgroup_name');
		var description =  getElement('eventgroup_description');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		

		
		if(title.length == 0) {
			error = true;
			messages += " * EventGroup title was not specified. \n";
		} 
		
		if(description.length == 0) {
			error = true;
			messages += " * EventGroup description was not specified. \n";
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
		
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		
		<c:if test="${eventGroup.id > 0}">
			<input type="hidden" name="eventGroupId" value="${eventGroup.id}">
		</c:if>
		
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<c:if test="${eventGroup.id == null}">
				<tr class="headbox">
					<th colspan="3">
						New Event Group
					</th>
				</tr>
			</c:if>


			
			<tr>
				<td >Name</td> 
				<td ></td>
				<td>
			 
				<input type="text" id="eventgroup_name" name="name" value="${eventGroup.name}" class="textbox3"/></td>
			</tr>
			
			<tr>
				<td >Description</td> 
				<td ></td>
				<td>
			 
				<input type="text" id="eventgroup_description" name="description" value="${eventGroup.description}" class="textbox3"/></td>
			</tr>
			
							
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				 
				
				 
				<c:choose>
					<c:when test="${eventGroup.id >0}">
						<input type="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
						<input type="button" value="Cancel" onclick="window.location='eventgroups.do'" class="upload_button2">
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