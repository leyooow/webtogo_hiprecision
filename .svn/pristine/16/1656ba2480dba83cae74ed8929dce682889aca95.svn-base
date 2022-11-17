<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>

<c:set var="menu" value="forms" />
<c:set var="submenu" value="reservations" />
<c:set var="pagingAction" value="formsubmissions.do" />

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
				document.forms[FormName].submit();
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
<div class="container">
<%@include file="includes/bluetop.jsp"%>
<%@include file="includes/bluenav.jsp"%>
<div class="contentWrapper" id="contentWrapper">
	<div class="content">
	  <div class="pageTitle">
		<c:if test="${user.userType.value eq 'Company Staff' and company.id eq 181}">
			<c:if test="${not empty formPageList[0].url}">
				<c:redirect url="${formPageList[0].url}" />
			</c:if>
			
			<c:if test="${empty formPageList[0].url}">
				<c:redirect url="dashboard.do" />
			</c:if>
		</c:if>
		
	    <h1><strong>Form Submissions</strong></h1>
		<div class="clear"></div>
	  </div><!--//pageTitle-->

	  <div class="downloadsBox">
		<form action="downloadsubmission.do" method="post" name="downloadForm" >

		<input type="hidden" name="company_id" value="${company.id}"/>
	    <table border="0" cellspacing="0" cellpadding="5">
		  <tr>
		    <td>DOWNLOAD IN EXCEL FORMAT&nbsp; &nbsp; </td>
			<td><input type="radio" name="filter" value="all" checked="checked" id="all" /></td>
			<td>ALL</td>
			<td><input type="radio" name="filter" value="byMonth"  id="byMonth"/></td>
			<td>BY MONTH</td>
			<td>
			  <select name="byMonth" id="selectmonth" disabled>
				<option value="0">--Select Month--</option>
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
			</td>
			<td>
			  <select name="byYear" id="selectyear" disabled>
			    <option value="0">--Select Year--</option>
				<c:forEach begin="2005" end="${year-1}"  var="date" >
				  <option value=${date}> ${date} </option>
 				</c:forEach>
				<option value="${year}">${year}</option>
			  </select>
			</td>
			<td><input type="submit" id="submitbutton" value="Download" class="btnBlue"></td>
			</form>
		  </tr>
		  <tr>
		  	<td>
		  	<form action="reservations.do" method="get">
		  	<input type="hidden" id="programId" name="programId" />
		  	Filter By&nbsp; &nbsp; 
		  		<c:forEach items="${rootCategories}" var="root">
		  			<c:if test="${root.name eq 'Program'}">
		  				<c:set var="programs" value="${root.enabledItems}"/>
		  			</c:if>
		  		</c:forEach>
		  		<select onchange="document.getElementById('programId').value=this.value;">
		  			<option value="">Please Select</option>
		  			<c:forEach items="${programs}" var="program">
		  				<option value="${program.id}" <c:if test="${program.id eq param.programId}">selected="selected"</c:if>>${program.name}</option>
		  			</c:forEach>
		  		</select>
		  		&nbsp; &nbsp; <input type="submit" value="Go" class="btnBlue">
		  	</form>
		  	</td>
		  </tr>
		</table>
		<script>
			$(function() {
				$("input:radio[@name='filter']").click(function() {
				   if($(this).attr('id') == 'all') {
				      $("#selectmonth").attr("disabled","disabled");
				      $("#selectyear").attr("disabled","disabled");
				   } else {
				      $("#selectmonth").removeAttr("disabled");
				      $("#selectyear").removeAttr("disabled");
				   }    
				});
			});
		</script>
	  </div><!--//downloadsBox-->
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
	  <c:if test="${not empty reservationList && user.userType.value != 'Company Staff'}"> 	
		<c:if test="${user.userType.value != 'Company Staff'}">				 
		  <form name="emailForm" id="emailForm" action="deletemultiplesubmissions.do" method="post">
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
      
      <c:set var="pagingAction" value="reservations.do?programId=${param.programId}" />
      
      <ul class="pagination" style="width: auto;clear: initial;">
	    
	<c:if test="${pagingUtil.totalItems > 0}">
					<li>Page ${pagingUtil.currentPageNo} of ${pagingUtil.totalPages} &nbsp;</li>
					<c:if test="${pagingUtil.totalItems > pagingUtil.itemsPerPage}">
						<c:if test="${param.pageNumber gt 1}">
						     <li><a href="${pagingAction}&pageNumber=1">&laquo; First</a> | &nbsp
						     <a href="${pagingAction}&pageNumber=${param.pageNumber-1}">&laquo; Previous</a> | &nbsp</li>
						</c:if>
						   	<c:forEach items="${pagingUtil.pages}" var="pageNum">	 
								<c:set value="${fn:length(pagingUtil.pages)}" var="total" />
								<c:choose>											
									<c:when test="${pagingUtil.currentPageNo!= pageNum }">
										<li><a href="${pagingAction}&pageNumber=${pageNum}&tpages=${total}">${pageNum}</a></li>					
									</c:when>  
									<c:otherwise>
										<li><span style="color: red;">[${pageNum}]</span></li>
									</c:otherwise> 
								</c:choose>		 
								<li><span class="small">-</span></li>			  
							</c:forEach>
						<c:if test="${param.pageNumber ne pagingUtil.totalPages}">	<li>
							| <a href="${pagingAction}&pageNumber=${(empty param.pageNumber) ? '2' : param.pageNumber+1}&tpages=${pagingUtil.totalPages}">Next &raquo;</a>
							| <a href="${pagingAction}&pageNumber=${pagingUtil.totalPages}&tpages=${pagingUtil.totalPages}">Last &raquo;</a></li>
						</c:if>	
					</c:if>
				</c:if>
				</ul>
				
      <%--
	  <ul class="pagination">
	    <%@include file="includes/pagingnew.jsp"  %>
	  </ul> --%>

	  <div class="clear"></div>
	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
	    <tr>
          <c:if test="${user.userType.value != 'Company Staff'}">	
			<th></th>
		  </c:if>	
		  <th>Name</th>
		  <th>Notes</th>
		  <th>Email</th>
		  <th>Phone</th>
		  <th>Program Name</th>
		  <th>Date Submitted</th>
		  <th>Action</th>
		  <c:set var="count" value="0" />
		</tr>	
				<c:forEach items="${reservationList}" var="e">
						 	<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
							<c:set var="count" value="${count+1}" />
						 	<c:if test="${user.userType.value != 'Company Staff'}">		 	
						 	
						 		<td > 
						 		<c:choose>
							 			<c:when test="${e.formName=='Registration'}">
							 				<input type="checkbox" name="emailCheckbox" value="${e.id}"> 
							 			</c:when>
							 			<c:otherwise>
							 				<input type="checkbox" name="emailCheckbox" value="${e.id}"> 
							 			</c:otherwise>
							 	</c:choose>
						 		
						 		
						 		</td>
						 	</c:if>	
						 		<td> ${e.sender} </td> 
															 		
						 		<td>
						 			<!-- <div align="center"><img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage(${e.id});"></div> -->
						 			<div align="center"><a href="javascript:void(0);" onmouseover="showMessage(${e.id});" onmouseout="return nd();"><img style="cursor:pointer;" src="images/note.jpg"></a> </div>
						 		</td>
								   
						 		<td><a href="mailto:${e.email}"> ${e.email}</a></td>		 
						 		<td>${e.phone}</td>
						 		<td>${e.otherField1}</td>
						 		<td><fmt:formatDate pattern="MMM dd, yyyy - hh:mm:ss a" value="${e.updatedOn}" /></td>
						 		<td>
						 			<c:if test="${user.userType.value != 'Company Staff'}">	
						 			<c:choose>
							 			<c:when test="${e.formName=='Registration'}">
							 					<a onclick="return confirm('Do you really want to delete this submission?');" href="deletesubmission.do?submissionId=${e.id}">Delete</a>
							 			</c:when>
							 			<c:otherwise>
							 					<a onclick="return confirm('Do you really want to delete this submission?');" href="deletesubmission.do?submissionId=${e.id}">Delete</a>
							 			</c:otherwise>
							 		</c:choose>
						 			</c:if>		
						 		</td>
						 		<c:choose>
						 		
						 		
						 		<c:when test="${e.formName=='Registration'}">
						 		<td valign="middle">
						 			<c:if test="${fn:length(e.uploadFileName) gt 0}">
						 				<c:forEach items='${fn:split(e.uploadFileName," ")}' var="p">
						 				<c:set var="item" value="${fn:split(p,'_')}" />
						 						${item[0]} , 
									 		<!--<a href="../companies/${company.name}/attachments/registrations/${p}">
									 			${p}
									 		--><!--</a><br/>-->
							 			</c:forEach>
						 			</c:if>
						 		</td>
						 		</c:when>  
						 		
						 		
						 		
						 		<c:otherwise>
						 		<c:if test="${fn:length(e.uploadFileName) gt 0}">
							 		<td valign="middle">
							 			
							 			<script type="text/javascript">
										function ShowPopup(hoveritem,id)
										{
										hp = document.getElementById("hover_"+id);
										
										// Set position of hover-over popup
										hp.style.top = hoveritem.offsetTop + 0;
										hp.style.left = hoveritem.offsetLeft + 0;
										
										// Set popup to visible
										hp.style.visibility = "Visible";
										}
										
										function HidePopup(id2)
										{
										hp = document.getElementById("hover_"+id2);
										hp.style.visibility = "Hidden";
										}
										</script>
							 			<div>
							 				<div style="width: 10%; float: left;" >
													<%-- 
													Local
													<a href="<c:url value='/companies/${company.name}/message_attachments/${e.uploadFileName}'/>" id="hoverover" style="cursor:pointer;" onMouseOver="ShowPopup(this,${e.id});" onMouseOut="HidePopup(${e.id});"><img src="images/Email_attach.png" align="middle"></a>
													--%>
												<a href="${httpServer}/message_attachments/${e.uploadFileName}" id="hoverover" style="cursor:pointer;" onMouseOver="ShowPopup(this,${e.id});" onMouseOut="HidePopup(${e.id});"><img src="images/Email_attach.png" align="middle"></a>					 				
							 				</div>
							 				<div style="width: 90%; float: right; ">
							 						<table bgcolor="#7da641" border="1" id="hover_${e.id}" style="visibility:hidden;">
														<tr>
														<td bgcolor="#7da641">
														<font color="#FFFFFF">${e.uploadFileName}</font>
														</td>
														</tr>
													</table>
							 				</div>
							 			</div>
	<!--						 			<table border="0">-->
	<!--							 			<tr>-->
	<!--								 			<td>-->
	<!--								 			<a href="<c:url value='/companies/${company.name}/message_attachments/${e.uploadFileName}'/>" id="hoverover" style="cursor:pointer;" onMouseOver="ShowPopup(this,${e.id});" onMouseOut="HidePopup(${e.id});"><img src="images/Email_attach.png"></a>-->
	<!--											</td>-->
	<!--											-->
	<!--											<td>-->
	<!--												<table bgcolor="#7da641" border="1" id="hover_${e.id}" style="visibility:hidden;">-->
	<!--													<tr>-->
	<!--													<td bgcolor="#7da641">-->
	<!--													<font color="#FFFFFF">${e.uploadFileName}</font>-->
	<!--													</td>-->
	<!--													</tr>-->
	<!--												</table>-->
	<!--											</td>-->
	<!--										</tr>-->
	<!--									</table>-->
							 		</td>
							 		</c:if>
							 	</c:otherwise>
						 		</c:choose>
						 	</tr>
					 	
					 		<div id="emailContent_${e.id}" style="display: none;">${e.emailContent}</div> 				 
					 
					 	</c:forEach>
</table>
<c:if test="${not empty reservationList && user.userType.value != 'Company Staff'}"> 
	  <ul class="selectionList">

	    <li>Select</li>

		<li><a onclick="setCheckbox('emailForm', 'emailCheckbox', true )"  href="javascript:void(0);">All</a></li>

		<li>,</li>

		<li><a onclick="setCheckbox('emailForm', 'emailCheckbox', false)" href="javascript:void(0);">None</a></li>

		<li>,</li>
		<li><a href="javascript:{}" onclick="confirmDelete('emailForm','emailCheckbox'); return false;" class="delete" >[Delete Selected]</a></li>
	  </ul>
</c:if></form>

<ul class="pagination" style="width: auto;clear: initial;">
	    
	<c:if test="${pagingUtil.totalItems > 0}">
					<li>Page ${pagingUtil.currentPageNo} of ${pagingUtil.totalPages} &nbsp;</li>
					<c:if test="${pagingUtil.totalItems > pagingUtil.itemsPerPage}">
						<c:if test="${param.pageNumber gt 1}">
						     <li><a href="${pagingAction}&pageNumber=1">&laquo; First</a> | &nbsp
						     <a href="${pagingAction}&pageNumber=${param.pageNumber-1}">&laquo; Previous</a> | &nbsp</li>
						</c:if>
						   	<c:forEach items="${pagingUtil.pages}" var="pageNum">	 
								<c:set value="${fn:length(pagingUtil.pages)}" var="total" />
								<c:choose>											
									<c:when test="${pagingUtil.currentPageNo!= pageNum }">
										<li><a href="${pagingAction}&pageNumber=${pageNum}&tpages=${total}">${pageNum}</a></li>					
									</c:when>  
									<c:otherwise>
										<li><span style="color: red;">[${pageNum}]</span></li>
									</c:otherwise> 
								</c:choose>		 
								<li><span class="small">-</span></li>			  
							</c:forEach>
						<c:if test="${param.pageNumber ne pagingUtil.totalPages}">	<li>
							| <a href="${pagingAction}&pageNumber=${(empty param.pageNumber) ? '2' : param.pageNumber+1}&tpages=${pagingUtil.totalPages}">Next &raquo;</a>
							| <a href="${pagingAction}&pageNumber=${pagingUtil.totalPages}&tpages=${pagingUtil.totalPages}">Last &raquo;</a></li>
						</c:if>	
					</c:if>
				</c:if>
				</ul>
				<%--
	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul> --%>

	  <div class="clear"></div>
	
	</div><!--//content-->
	</div><!-- //contentWrapper -->

  </div><!--//container-->

</body>

</html>