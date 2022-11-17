<%@include file="includes/header.jsp"  %>


<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRItemCommentAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 

<c:set var="menu" value="comments" />

	<c:choose>
		<c:when test="${commentType eq 'PAGE'}">
			<c:set var="submenu" value="pagecomment" />		
		</c:when>
		<c:when test="${commentType eq 'ITEM'}">
			<c:set var="submenu" value="itemcomment" />		
		</c:when>
		<c:otherwise>
			<c:set var="submenu" value="comment" />
		</c:otherwise>
	</c:choose>


<c:set var="pagingAction" value="comment.do" />

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

	//alert("Values :" + document.forms[FormName].selectedValues.value);
	
	if (!selected)
		error = true;
					
	if(error) {
		alert(message);
	}
	else {			
		message="Do you really want to delete "+ selected +" submission?";
		if (confirm(message)) {
			document.forms[FormName].submit();
		}
	}	
	return false;	
	
}

function updateComment(id, elem) {
	var published = elem.checked;
	DWRItemCommentAction.updateItemComment(id, published, function(data){
		if(data) {
		}
	});
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
    <div class="content">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">

	    <h1><strong>Comments</strong>: View Comments</h1>
		
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->
	  		
	  		<div class="downloadsBox"> 
	  	<c:set var="formAction" value="" />
	  	<c:choose>
			<c:when test="${company.id eq 319}">
				<c:set var="formAction" value="downloadGurkkaDetails" />
			</c:when>
			<c:when test = "${company.id eq 393 }">
				<c:set var = "formAction" value = "downloadPolitikerComments" />
			</c:when>
			<c:otherwise>
				<c:set var="formAction" value="downloadCustomDetails" />
			</c:otherwise>
		</c:choose>
	  	
	  	<form action="${formAction}.do" method="post" name="downloadForm" >
	  		<c:choose>
		  		<c:when test="${company.id eq 319}"> <%--Gurkka--%>
					<input type="hidden" name="company_id" value="${company.id}"/>
					<table width="100%">
						<tr>
							<td width="90%">Download Members List in Excel Format</td>
							<td><input type="submit" value="Download" class="btnBlue" /></td>
						</tr>
					</table>
				</c:when>
				
				<c:when test="${company.id eq 393 }"><%-- For Politiker --%>
					<input type="hidden" name="company_id" value= "${company.id }"/>
					<table width = "100%">
						<tr>
							<td width = "90%">Download Politiker Comments in Excel Format</td>
							<td><input type="submit" value="Download" class="btnBlue" /></td>
						</tr>
					</table>
				</c:when>
				
		  		<c:otherwise>
		  			<%@include file="includes/memberDownloadSettings.jsp"  %>
					<c:forEach items="${list}" var="fieldName" >
						<input type="hidden" name="fieldName" value="${fieldName}">
					</c:forEach>
					<input type="hidden" name="company_id" value="${company.id}"/>
					<table width="100%"><tr><td width="90%">Download Members List in Excel Format</td>
					<td> <input type="submit" value="Download" class="btnBlue"/></td></tr>
					<tr>
						<td colspan="2">or <a href="members.do?customFieldDownload=show">Download Custom Fields.</a>
						</td>
					</tr>
					</table>
		  		</c:otherwise>
	  		</c:choose>
		</form>
	
	  </div>
	  		
	  		<c:if test="${user.userType.value != 'Company Staff'}">				 
				<form name="emailForm" action="deletemultiplecomments.do" method="post" >
					 		<input type="hidden" name="selectedValues" value="">
					 			<c:if test="${not empty commentType}">
					 				<input type="hidden" name="commentType" value="${commentType }">	
					 			</c:if>
			</c:if>	
<c:if test="${not empty itemComments}"> 	
					<c:if test="${user.userType.value != 'Company Staff'}">						 	
					 	  <ul class="selectionList">

	    <li>Select</li>

		<li><a onclick="setCheckbox('emailForm', 'emailCheckbox', true )"  href="javascript:void(0);">All</a></li>

		<li>,</li>

		<li><a onclick="setCheckbox('emailForm', 'emailCheckbox', false)" href="javascript:void(0);">None</a></li>

		<li>,</li>
		
	<li><a href="javascript:{}" onclick="confirmDelete('emailForm','emailCheckbox'); return false;" class="delete" >[Delete Selected]</a></li>
	  </ul>
					 </c:if>		
					 </c:if>			 	
	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
			
					 
					 	<tr > 
					 	<c:if test="${user.userType.value != 'Company Staff'}">	
					 		<th >&nbsp;</th>
					 	</c:if>	
					 		<c:choose>
					 			<c:when test="${company.name eq 'life' }">
					 				<th >Name</th>
					 			</c:when>
					 			<c:when test="${company.name eq 'truecare' }">
					 				<th >Name</th>
					 			</c:when>
					 			<c:otherwise>
					 				<th >Member Name</th>
					 			</c:otherwise>
					 		</c:choose>
					 		<c:choose>
					 			<c:when test="${company.name eq 'truecare' }">
					 				<th >Testimonial</th>
					 			</c:when>
					 			<c:otherwise>
					 				<th >Comment</th>
					 			</c:otherwise>
					 		</c:choose>
					 		<c:choose>
					 			<c:when test="${company.name eq 'truecare' }">
					 			
					 			</c:when>
					 			<c:otherwise>
					 				<th ">Email</th>
					 			</c:otherwise>
					 		</c:choose>
					 		<c:choose>
					 			<c:when test="${company.name eq 'truecare' }">
					 				<th >Rating</th>
					 			</c:when>
					 			<c:otherwise>
					 				<th >Submitted From</th>
					 			</c:otherwise>
					 		</c:choose>
							<th >Date Submitted</th>
							<th >Action</th>
							<c:if test="${company.name eq 'life' }">
								<th>Published</th>
							</c:if>	
							<c:if test="${company.name eq 'truecare' }">
								<th>Display</th>
							</c:if>		
						</tr>		<c:set var="count" value="0" />				
						<c:forEach items="${itemComments}" var="com">
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
								<td width="5px">
									<c:if test="${user.userType.value != 'Company Staff'}">
										<input type="checkbox" name="emailCheckbox" value="${com.id}" />
									</c:if>
								</td>
								<td>
									<c:choose>
										<c:when test="${com.member ne null}">
											<c:choose>
												<c:when test = "${fn:containsIgnoreCase(company.name,'politiker')}">
													${com.member.username }
												</c:when>
												<c:otherwise>
													${com.fullName }
												</c:otherwise>
											</c:choose>
											
										</c:when>
										<c:otherwise>${com.firstname}</c:otherwise>
									</c:choose>
								</td>
								<td>${com.content }</td>
								<c:choose>
									<c:when test="${company.name eq 'truecare' }">
									
									</c:when>
									<c:otherwise>
									<td>
										<c:choose>
											<c:when test="${com.member ne null}"><a href="mailto:${com.member.email }"> ${com.member.email }</a></c:when>
											<c:otherwise><a href="mailto:${com.email }"> ${com.email }</a></c:otherwise>
										</c:choose>
									</td>
									</c:otherwise>
								</c:choose>
								<td>
									<c:choose>
										<c:when test="${company.name eq 'truecare' }">
											${com.lastname }
										</c:when>
										<c:when test="${not empty com.item}">
											${com.item.brand.name } ${com.item.name } (Item)
										</c:when>
										<c:when test="${not empty page}">
											${com.page.name } (Page)
										</c:when>
										<c:otherwise>
											
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<fmt:formatDate pattern="MMM dd, yyyy - hh:mm:ss a" value="${com.createdOn}" />
								</td>
								<td>
									<c:set var="link" value="deletecomment.do?id=${com.id }" />
									<c:if test="${not empty commentType}">
										<c:set var="link" value="${link}&commentType=${commentType }" />
									</c:if>
									
									<a href="${link}" onclick="return confirm('Do you really want to delete this comment?');">Delete</a>
								</td>
								<c:if test="${company.name eq 'life' }">
									<td style="text-align: center;"><input type="checkbox" name="publishedEmail" value="${com.id}" onclick="updateComment(${com.id}, this);" ${com.published ? 'checked' : '' }/></td>
								</c:if>	
								<c:if test="${company.name eq 'truecare' }">
									<td style="text-align: center;"><input type="checkbox" name="publishedEmail" value="${com.id}" onclick="updateComment(${com.id}, this);" ${com.published ? 'checked' : '' }/></td>
								</c:if>	
							</tr>
							
						</c:forEach>
					 	<%--c:forEach items="${emails}" var="e">
						 	<tr>	
						 	<c:if test="${user.userType.value != 'Company Staff'}">		 	
						 	
						 		 
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
						 		<td> ${e.sender}	</td> 
															 		
						 		<td>
						 			<!-- <div align="center"><img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage(${e.id});"></div> -->
						 			<div align="center"><a href="javascript:void(0);" onmouseover="showMessage(${e.id});" onmouseout="return nd();"><img style="cursor:pointer;" src="images/note.jpg"></a> </div>
						 		</td>
								   
						 		<td><a href="mailto:${e.email}"> ${e.email}</a></td>		 
						 		<td>${e.phone}</td>
						 		<td>${e.formName}</td>
						 		<td><fmt:formatDate pattern="MMM dd, yyyy - hh:mm:ss a" value="${e.updatedOn}" /></td>
						 		<td>
						 			<c:if test="${user.userType.value != 'Company Staff'}">	
						 			<c:choose>
							 			<c:when test="${e.formName=='Registration'}">
							 					<a onclick="return confirm('Do you really want to delete this submission');" href="deletesubmission.do?submissionId=${e.id}">Delete</a>
							 			</c:when>
							 			<c:otherwise>
							 					<a onclick="return confirm('Do you really want to delete this submission');" href="deletesubmission.do?submissionId=${e.id}">Delete</a>
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
													<%,-- 
													Local
													<a href="<c:url value='/companies/${company.name}/message_attachments/${e.uploadFileName}'/>" id="hoverover" style="cursor:pointer;" onMouseOver="ShowPopup(this,${e.id});" onMouseOut="HidePopup(${e.id});"><img src="images/Email_attach.png" align="middle"></a>
													--,%>
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
						 		<c:if test="${fn:length(e.uploadFileName) eq 0}">
						 		<td>&nbsp;</td>
						 		</c:if>					 		
						 	</tr>
					 	
					 		<div id="emailContent_${e.id}" style="display: none;">${e.emailContent}</div> 				 
					 
					 	</c:forEach--%>
					</table>
					<c:if test="${not empty itemComments}"> 	
					<c:if test="${user.userType.value != 'Company Staff'}">						 	
					 	  <ul class="selectionList">

	    <li>Select</li>

		<li><a onclick="setCheckbox('emailForm', 'emailCheckbox', true )"  href="javascript:void(0);">All</a></li>

		<li>,</li>

		<li><a onclick="setCheckbox('emailForm', 'emailCheckbox', false)" href="javascript:void(0);">None</a></li>

		<li>,</li>
	<li><a href="javascript:{}" onclick="confirmDelete('emailForm','emailCheckbox'); return false;" class="delete" >[Delete Selected]</a></li>
	  </ul>
					 </c:if>		
					 </c:if>			 	
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	    <div class="clear"></div>
	  
					
				<c:if test="${user.userType.value != 'Company Staff'}">	
				 </form>  		 
				</c:if> 
	</div><!--//mainContent-->

</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>