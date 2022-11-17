<%@include file="includes/header.jsp"  %>
<script language="JavaScript" src="../javascripts/overlib.js"></script>

<body>


<c:set var="menu" value="raffleentries" />
<c:set var="submenu" value="watsonsusd" />
<c:choose>
	<c:when test="${memSort ne null}" >
		<c:set var="pagingAction" value="members.do?memSort=${memSort}&searchBy=${x}&str=${y}" />
	</c:when>
	<c:otherwise>
		<c:set var="pagingAction" value="members.do?memSort=username" />
	</c:otherwise>
</c:choose>
<c:set var="firstParamGiven" value="${true}" />

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
				document.forms[FormName].submit();
		}	
		return false;	
	}
</script>
<div class="contentWrapper" id="contentWrapper">

	<c:if test="${customFieldDownload != null}">	
	<div id="mainContent">
	<%@include file="member_customFieldDownload.jsp"%>
	</div>
	</c:if>	
	
<%-- 	<c:if test="${customFieldDownload == null}">	 --%>
    <div class="mainContent">
	 	<s:actionmessage />
				<s:actionerror />
				<c:if test="${param['evt'] != null}">
					<ul>
			 			
						<c:if test="${param['evt'] == 'save'}">
							<li><span class="actionMessage">Event was successfully created.</span></li>
						</c:if> 
						
						<c:if test="${param['evt'] == 'deleteFailed'}">
							<li><span class="errorMessage">Event was not successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Entry/s was successfully deleted.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Event was successfully updated.</span></li>
						</c:if>

					</ul>
				</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Entries</strong></h1>
	
						
		<div class="clear"></div>

	  </div><!--//pageTitle-->
	  <div class="downloadsBox"> 
		
		<form action="skechersraffle.do" method="post">
			<table width="100%">
				<tr>
					<td>Date From:</td>
					<td>
						<!--  START DATE -->
						<input type="text" id="start_date" name="startDate" value="${startDate}" size="11" maxlength="10" readonly /> 
						<a href="javascript:;" id="start_date_button"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>
						   
						  
						<script type="text/javascript">
						    Calendar.setup({
						        inputField     :    "start_date",     // id of the input field
						        ifFormat       :    "%m-%d-%Y",      // format of the input field
						        button         :    "start_date_button",  // trigger for the calendar (button ID)
						        align          :    "Tl",           // alignment (defaults to "Bl")
						        singleClick    :    true
						    });
						</script>								
					</td>
								
					<td>Date To:</td>
					<td>
					<!-- END DATE -->
					<input type="text" id="end_date" name="endDate" value="${endDate}" size="11" maxlength="10" readonly /> 
					<a href="javascript:;" id="end_date_button"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>
					   
					  
					<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "end_date",     // id of the input field
					        ifFormat       :    "%m-%d-%Y",      // format of the input field
					        button         :    "end_date_button",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true
					    });
					</script>								
					</td>
					
					<td>
						<input type="submit" value="Filter" class="btnBlue"/>	
					</td>
					
				</tr>
				
				
				
			</table>
		</form>
	
		<table>
			<tr>
				<form action="downloadSkechersRaffleEntryDetails.do" method="post" name="downloadForm" >
					<%@include file="includes/memberDownloadSettings.jsp"  %>
					<c:forEach items="${list}" var="fieldName" >
						<input type="hidden" name="fieldName" value="${fieldName}">
					</c:forEach>	
					<td colspan="3">Download Entry List in Excel Format</td>
					<td>
						<input type="hidden" name="startDate" value="${startDate }" />
						<input type="hidden" name="endDate" value="${endDate }" />
						<input type="hidden" name="watsonWinner" value="${watsonWinner.id }" />
						<input type="submit" value="Download" class="btnBlue"/>
					</td>							
				</form>
				<%-- --%>
				<form action="generateSkechersWinner.do" method="post" onsubmit="return checkDate();">
					<td>
						<input type="hidden" name="startDate" value="${startDate }" />
						<input type="hidden" name="endDate" value="${endDate }" />
						<input type="hidden" name="id" value=3 />
						<input type="submit" value="Generate Winner" class="btnBlue"/>					
					</td>
				</form>
			</tr>			
		</table>	
	
	  </div>	
	  
	  <c:if test="${not empty emailsList && user.userType.value != 'Company Staff'}"> 	
		<c:if test="${user.userType.value != 'Company Staff'}">	
	  		<form name="emailForm" id="emailForm" action="deletemultipleraffleentry.do" method="post">
	  	</c:if>
	  	<ul class="selectionList">
	      <li>Select</li>
		  <li><a onclick="setCheckbox('emailForm', 'emailCheckbox', true )"  href="javascript:void(0);">All</a></li>
		  <li>,</li>
		  <li><a onclick="setCheckbox('emailForm', 'emailCheckbox', false)" href="javascript:void(0);">None</a></li>
		  <li>,</li>
		  <input type="hidden" name="selectedValues" value="">
	      <li><a href="javascript:{}" onclick="confirmDelete('emailForm','emailCheckbox'); return false;" class="delete" >[Delete Selected]</a></li>
	    </ul>
	 </c:if>
<%-- 	  <ul class="pagination"><%@include file="includes/pagingnew.jsp"  %></ul> --%>
	
	  <c:if test="${skechersWinner ne null }">
	  	<div class="downloadsBox">
	  		<table>
	  			<tr>
	  				<td>Date From :</td>
	  				<td><fmt:formatDate type="date" value="${displayStartDate }" /></td>
	  				<td>Date To :</td>
	  				<td><fmt:formatDate type="date" value="${displayEndDate }" /></td>
	  			</tr>
	  			
	  			<tr>
	  				<td colspan="2">Random generated winner is : ${skechersWinner.otherDetailMap['name'] }  </td>
	  			</tr>
	  			
	  			<tr>
	  				<td colspan="2">Contact Details : ${skechersWinner.otherDetailMap['contactNo']}</td>
	  			</tr>
	  		</table>
	  	</div>
	  </c:if>
		
	  <div class="clear"></div>
 	  
 	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
						<tr >
							<th></th>
							<th>Name</th>
							<th>Complete Address</th>
							<th>Email Address</th>
							<th>Contact No.</th>
							<th>Official Receipt#</th>
							<th>Branch</th>
							<th>Raffle Code</th>
							<th>Action</th>
						</tr>
						
						
						<c:set var="count" value="0" />
						<c:forEach items="${emailsList}" var="wr">
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
								<c:set var="count" value="${count+1}" />
								<td><input type="checkbox" name="emailCheckbox" value="${wr.id}"> </td>	
								<td>${wr.otherDetailMap['name']}</td>
								<td>${wr.otherDetailMap['address']}</td>
								<td>${wr.otherDetailMap['email']}</td>
								<td>${wr.otherDetailMap['contactNo']}</td>
								<td>${wr.otherDetailMap['officialReceipt']}</td>
								<td>${wr.otherDetailMap['branch']}</td>
								<td>${wr.otherDetailMap['raffleCode']}</td>
								<td><a onclick="return confirm('Do you really want to delete this entry?');" href="deleteraffleentry.do?submissionId=${wr.id}">Delete</a></td>
	 	  					</tr>
 	  					</c:forEach>
		</table>
				
	</form>
	
<%-- 	  <ul class="pagination"><%@include file="includes/pagingnew.jsp"  %></ul> --%>
	</div><!--//mainContent-->
	
<%-- 	</c:if> --%>

</div>

	<div class="clear"></div>

  </div><!--//container-->

<script type="text/javascript">
	function checkDate() {
		var startDate = document.getElementById("start_date");
		var endDate = document.getElementById("end_date");

		if(startDate.value.length == 0 || endDate.value.length == 0) {
			alert("Select Date from and Date to");
			return false;
		}

		return true;
	}
</script>
</body>

</html>