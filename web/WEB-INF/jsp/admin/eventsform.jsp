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
		var eventDate = getElement('event_date');
		var title =  getElement('event_title');
		var fee = getElement('event_fee');
//		var detail =  getElement('event_detail');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(eventDate.length == 0) {
			error = true;
			messages += " * Event date was not specified. \n";
		} 
		
		if(title.length == 0) {
			error = true;
			messages += " * Event title was not specified. \n";
		} 

		/*if(fee.length == 0) {
			error = true;
			messages += " * Event fee was not specified. \n";
		}*/		
		
		if(detail.length == 0) {
			error = true;
			messages += " * Event detail was not specified. \n";
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

	function changeColor(elem){
		combo = document.getElementById(elem)
		combo.style.color = combo.value; 
	}
</script> 

<div style="width:100%;">
<table>
	<tr>
		<td style="border:0px;">
		
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
		
		<c:if test="${event.id > 0}">
			<input type="hidden" name="eventId" value="${event.id}">
		</c:if>
		
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<c:if test="${event.id == null}">
				<tr class="headbox">
					<th colspan="3">
						New Event
					</th>
				</tr>
			</c:if>
			<tr>
				<td width="1%" nowrap>Event Group</td>
				<td width="10px"></td>
				<td>
				<select id="eventGroup" name="eventGroup" class="combobox1">
					<option value="">- Select Event Group -</option>
					<c:forEach items="${eventGroups}" var="eg">
						<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
						<option value="${eg.id}" ${(eg.id == event.eventGroup.id) ? "selected" : "" }>${eg.name}&nbsp;</option>
					</c:forEach>
				</select>
				</td>
			</tr>			
			<tr>
				<td >Title</td> 
				<td ></td>
				<td>
			 
				<input type="text" id="event_title" name="title" value="${event.title}" class="textbox3"/></td>
			</tr>			
			<tr>
				<td width="1%" nowrap>Start Date/Time</td>
				<td width="10px"></td>
				<td>
				
				<fmt:formatDate pattern="MM-dd-yyyy hh:mm a" value="${event.eventDate}" var="ed"/>
								 
				<input type="text" id="event_date" name="eventDate" value="${ed}" class="textbox3" readonly="readonly"/> 
				<a href="javascript:;" id="event_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				   
				  
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "event_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y %I:%M %p",      // format of the input field
				        button         :    "event_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true,
				        showsTime	   :	true,
				        timeFormat     :	"12"				        
				    });
				</script>
				
				</td>
			</tr>
			 
			<tr>
				<td width="1%" nowrap>End Date/Time</td>
				<td width="10px"></td>
				<td>
				
				<fmt:formatDate pattern="MM-dd-yyyy hh:mm a" value="${event.eventEnd}" var="ed"/>
								  
				<input type="text" id="event_end" name="eventEnd" value="${ed}" class="textbox3" readonly="readonly"/> 
				<a href="javascript:;" id="event_end_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
				   
				   
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "event_end",     // id of the input field
				        ifFormat       :    "%m-%d-%Y %I:%M %p",      // format of the input field
				        button         :    "event_end_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true,
				        showsTime	   :	true,
				        timeFormat     :	"12"
				    });
				</script>
				
				</td>
			</tr>
			<tr>
				<td >Fee</td> 
				<td ></td>
				<td>
			 
				<input type="text" id="event_fee" name="fee" value="${event.fee}" class="textbox3"/></td>
			</tr>				
			<tr>
				<td >Color Code</td> 
				<td ></td>
				<td>
				<select id="event_color_code" name="colorCode" onchange="changeColor('event_color_code')" style="width:113px">
					<option value="#000000">- Select Color -</option>
					<c:forEach items="${colorCodes}" var="ccode">
						<option value="${ccode}" style="background-color: ${ccode}" <c:if test="${event.colorCode.value eq ccode}">selected</c:if> >${ccode}</option>								
					</c:forEach>					
				</select>	 
				
			</tr>	
							
<%--		<tr>
				<td >Event Category</td> 
				<td ></td>
				<td>
			 
				<input type="text" id="eventCategory" name="eventCategory" value="${event.eventCategory}" class="textbox3"/></td>
			</tr>
 --%>			
			<tr>
				<td colspan="3">Description</td>
			</tr>
			<tr>		
				<td colspan="2">&nbsp;</td>
				<td nowrap>
			 		<%-- <FCK:editor basePath="/FCKeditor" instanceName="event.detail" width="98%" height="400" toolbarSet="${(user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator') ? 'Custom_SuperUser' : 'Custom_SuperUser'}" value="${event.detail}"/>--%>
					
					<textarea id="event.detail" name="detail" class="w200" >${event.detail}</textarea>
					<script type="text/javascript">CKEDITOR.replace( 'event.detail');</script>
					<span style="padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</span>
				<%-- <textarea id="event_detail" name="detail" class="textbox3">${event.detail}</textarea> --%>				
				</td>
						
			</tr>
			

							
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				<c:choose>
					<c:when test="${event.id >0}">
						<input type="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2">
						<input type="button" value="Cancel" onclick="window.location='events.do'" class="upload_button2">
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