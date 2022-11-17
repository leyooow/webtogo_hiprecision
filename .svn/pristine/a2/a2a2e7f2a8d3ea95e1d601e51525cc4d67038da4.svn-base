
<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="events" />
 <c:set var="formAction" value="updateevent.do" />
 
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

		if(fee.length == 0) {
			error = true;
			messages += " * Event fee was not specified. \n";
		} 		
		
/*		if(detail.length == 0) {
			error = true;
			messages += " * Event detail was not specified. \n";
		} 
		
*/
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
<body>


  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
</script>
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
			<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Group was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'groupexist'}">
								<li><span class="actionMessage">Category was not created because a similar category already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Group was successfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Group was successfully deleted.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'sort'}">
								<li><span class="actionMessage">Display order of the categories was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'sortItems'}">
								<li><span class="actionMessage">Display order of category items was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'noCategory'}">
								<li><span class="actionMessage">Selected group has no category.</span></li>
							</c:if>							
							<c:if test="${param['evt'] == 'sortBrands'}">
								<li><span class="actionMessage">Display order of the brands was successfully updated.</span></li>
							</c:if>
							<c:if test="${param['evt'] == 'noBrand'}">
								<li><span class="actionMessage">Selected group has no brand.</span></li>
							</c:if>															
						</ul>
					</c:if>	
				
	  <div class="pageTitle">

	    <h1><strong>Edit Group</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	 
			
				  <div class="clear"></div>
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
			 		  <textarea id="event.detail" name="event.detail" >${event.detail}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'event.detail');
			</script></td>
						
			</tr>
			

							
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				<c:choose>
					<c:when test="${event.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='events.do'" class="btnBlue">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
								
				</td>
			</tr>
					
		</table>
		
		</form>
		
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
		<form method="post" action="uploadgroupimage.do" enctype="multipart/form-data">
					
					<%--
					<input type="hidden" name="parent" value="${category.parentCategory.id}" />
					<input type="hidden" name="category_id" value="${category.id}" />
					--%>
					
					<input type="hidden" name="group_id" value="${group.id}" />
				
					
					<c:if test="${fn:length(group.images) > 0}">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>

	  <ol>

	    
		<c:forEach items="${group.images}" var="img">
		<li>

		  <img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" />

		  <span><a href="#"><strong>${img.filename}</strong></a> 
			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
		  <a onclick="return confirm('Do you really want to delete this image?');" href="deletesinglepageimage.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&image_id=${img.id}" class="delete">[Delete]</a></span>
			</c:if>	
		  <div class="clear"></div>

		</li>
		</c:forEach>
	  </ol>
		
	  <p class="imageNote">Click on Edit to Modify Title, Caption and Description of the image</p>
		</c:if>
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>

	  <ul class="uploadImageNote">

		<li>You are only allowed to upload 3 files.</li>

		<li>500 x 590 is the most advisable size of image that should be uploaded.</li>

	  </ul>

	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>
			
		  <td colspan="2">
								    
								    <input id="file" name="upload" type="file" /></td>

		</tr>

	  </table>

	  <p class="uploadButtons">
	   
				<input type="submit" value="Upload" class="btnBlue">

	  
	</form>
	
	</div><!--//sidenav-->
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>