<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="activities" />
<c:set var="submenu" value="company_activities"/>

 <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>


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

	    <h1><strong>Payments</strong></h1>
	</form>
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->
<div class="downloadsBox">
		<form action="downloadRequestForm.do" method="post" name="downloadForm" >
						<input type="hidden" name="company_id" value="${company.id}"/>
	    <table border="0" cellspacing="0" cellpadding="5">

		  <tr>

		    <td>DOWNLOAD AS PDF</td>

			<td><input type="radio" name="filter" value="all" checked="checked" /></td>

			<td>ALL</td>

			<td><input type="radio" name="filter" value="byMonth"  /></td>

			<td>BY DATE</td>

			<td>From: 	<br/>													
									<select name="byMonth">
										<option value="01" selected>January</option>
										<option value="02">February</option>
										<option value="03">March</option>
										<option value="04">April</option>
										<option value="05">May</option>
										<option value="06">June</option>
										<option value="07">July</option>
										<option value="08">August</option>
										<option value="09">September</option>
										<option value="10">October</option>
										<option value="11">November</option>
										<option value="12">December</option>														
									</select>		
									<select name="byDay">
										<option value="01" selected>1</option>
										<option value="02">2</option>
										<option value="03">3</option>
										<option value="04">4</option>
										<option value="05">5</option>
										<option value="06">6</option>
										<option value="07">7</option>
										<option value="08">8</option>
										<option value="09">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>		
										<option value="13">13</option>	
										<option value="14">14</option>	
										<option value="15">15</option>	
										<option value="16">16</option>	
										<option value="17">17</option>	
										<option value="18">18</option>	
										<option value="19">19</option>	
										<option value="20">20</option>	
										<option value="21">21</option>	
										<option value="22">22</option>	
										<option value="23">23</option>	
										<option value="24">24</option>	
										<option value="25">25</option>	
										<option value="26">26</option>	
										<option value="27">27</option>	
										<option value="28">28</option>	
										<option value="29">29</option>	
										<option value="30">30</option>	
										<option value="31">31</option>												
									</select>		
									<select name="byYear">
										<option value="2005" selected>2005</option>
										<option value="2006">2006</option>
										<option value="2007">2007</option>
										<option value="2008">2008</option>
										<option value="2009">2009</option>	
										<option value="2009">2010</option>							
									</select>
								</td>
								<td>To: <br/>	
								<select name="endMonth">
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
										<option value="12" selected>December</option>														
								</select>
								<select name="endDay">
										<option value="01">1</option>
										<option value="02">2</option>
										<option value="03">3</option>
										<option value="04">4</option>
										<option value="05">5</option>
										<option value="06">6</option>
										<option value="07">7</option>
										<option value="08">8</option>
										<option value="09">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>		
										<option value="13">13</option>	
										<option value="14">14</option>	
										<option value="15">15</option>	
										<option value="16">16</option>	
										<option value="17">17</option>	
										<option value="18">18</option>	
										<option value="19">19</option>	
										<option value="20">20</option>	
										<option value="21">21</option>	
										<option value="22">22</option>	
										<option value="23">23</option>	
										<option value="24">24</option>	
										<option value="25">25</option>	
										<option value="26">26</option>	
										<option value="27">27</option>	
										<option value="28">28</option>	
										<option value="29">29</option>	
										<option value="30">30</option>	
										<option value="31" selected>31</option>												
								</select>			
								<select name="endYear">
										<option value="2005">2005</option>
										<option value="2006">2006</option>
										<option value="2007">2007</option>
										<option value="2008">2008</option>
										<option value="2009" selected>2009</option>	
										<option value="2008">2010</option>						
								</select>
								
								</td>

			<td><input type="submit" value="Download" class="btnBlue"></td>
			</form>
		  </tr>

		</table>
		
	  </div><!--//downloadsBox-->
	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
					<tr >
						<th>ID</th>
						<th >Request Date</th>
						<th >Request Details</th>
						<th >Requested By</th>
						<th>Remarks</th>
						<th >Action Taken</th>
						<th >Request Type</th>
						<th >Status</th>
						<th >Assigned By</th>
						<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
							<th >Action</th>
						</c:if>	
					</tr>
					<c:set var="count" value="0" />
					<c:forEach items="${activities}" var="activity">
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
							<td><a href="editactivity.do?activityId=${activity.id}">${activity.id }</a></td>
							<td>${activity.requestDate}</td>
							<td>${activity.requestDetails}</td>
							<td>${activity.description}</td>
							<td>${activity.remarks}</td>
							<td>${activity.actionTaken}</td>
							<td>${activity.type}</td>
							<td>${activity.status}</td>
							<td>${activity.createdBy.firstname}&nbsp;${activity.createdBy.lastname}</td>
						<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
							<td>
								<a href="editactivity.do?activityId=${activity.id}">Edit</a> |
								<a href="deleteactivity.do?activityId=${activity.id}"  onclick="return confirm('Do you really want to delete this activity?');">Delete</a>
							</td>
						</c:if>	
						
						</tr>
					</c:forEach>
										
				</table>		
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
			
	 <h2>New Activity</h2>
	<table width="100%">
			<form method="post" action="savenewactivity.do" onsubmit="return submitForm(this);">		
			
		
			
			
			 <tr>
				<td >Request Type<br />
				<select id="status" name="activity.type" class="w200">
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
				<tr>
					<td>Status<br />
					<select id="status" name="status" class="w200">
						<c:forEach items="${statusTypes}" var="status">
							<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
							<option value="${status.statusCode}" ${(status.statusCode == activity.status.statusCode) ? "selected" : "" }>${status.value}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</c:if>
			
			<c:if test="${activity.id == null}">
			
			
				<tr><td>
					<select id="status" name="status" class="w200">
						<c:forEach items="${statusTypes}" var="status">
								<%-- <option value="${type.typeCode}">${type.value}</option>   --%>
								<option value="${status.statusCode}" ${(status.statusCode == activity.status.statusCode) ? "selected" : "" }>${status.value}</option>
						</c:forEach>
					</select>
				
					</td>
				</tr>
			
			
			</c:if>
			</tr>
	
			<tr>
					<td >Request Date<br />
							<c:if test="${activity.id > 0}">
								<fmt:formatDate pattern="MM-dd-yyyy" value="${company2.expiryDate}" var="ed"/>
											 
								<input type="text" id="requestDate" name="activity.requestDate" class="w200" value="${activity.requestDate}" readonly="readonly"/> 						
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
											 
								<input type="text" id="requestDate" name="activity.requestDate" class="w200" value="<%= (new java.text.SimpleDateFormat("MM-dd-yyyy")).format(new java.util.Date()) %>" readonly="readonly"/> 						
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
			</tr>
			<tr>
				<td >Request Details<br /><textarea id="requestDetails" name="activity.requestDetails" class="w200">${activity.requestDetails}</textarea></td>
			
			</tr>
			<tr>
				<td >Requested By<br />
				<input type="text" id="description" name="activity.description" class="w200" value=${activity.description}></td>
			
			</tr>
			<tr>
				<td>Remarks<br /><textarea id="remarks" name="activity.remarks" class="w200">${activity.remarks}</textarea></td>
				
			</tr>
			<tr>
				<td >Action Taken<br /><input type="text" id="actionTaken" name="activity.actionTaken" class="w200" value=${activity.actionTaken}></td>
			
			</tr>
			
			
		
			
							
			<tr>
				
				<td >
				 
				
				 
				<c:choose>
					<c:when test="${activity.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='activities.do'" class="btnBlue">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
								
				</td>
			
			</tr>
					
		</table>
		</form>
	</div><!--//sidenav-->
	</c:if>
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>