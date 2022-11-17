
<%@include file="includes/header.jsp"  %>
<script language="JavaScript" src="../javascripts/overlib.js"></script>

<c:set var="menu" value="members" />
<c:set var="submenu" value="memberfiles" />

<script>
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	function showMessage(id) 
	{
		var notes = document.getElementById('notes_'+id).innerHTML;
		overlib(notes, STICKY, NOCLOSE)
	}
</script>
<body>

  	<div class="container">
	
	    <%@include file="includes/bluetop.jsp"%>
		<%@include file="includes/bluenav.jsp"%>
		
		<div class="contentWrapper" id="contentWrapper">
			<div class="content">
				<%--
				<div class="downloadsBox" style="margin-bottom:10px;">
					<form action="downloadsubmission.do" method="post" name="downloadForm" >
						<input type="hidden" name="company_id" value="${company.id}"/>
	    
	    				<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td>DOWNLOAD IN EXCEL FORMAT&nbsp; &nbsp; </td>
								<td><input type="radio" name="filter" value="all" checked="checked" /></td>
								<td>ALL</td>
								<td><input type="radio" name="filter" value="byMonth"  /></td>
								<td>BY MONTH</td>
								<td>
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
								</td>
								<td>
			 						<select name="byYear">
										<c:forEach begin="2005" end="${year-1}"  var="date" >
											<option value=${date}> ${date} </option>
										</c:forEach>
										<option value="${year}" selected>${year}</option>
			 						 </select>
								</td>
								<td><input disabled="disabled" type="submit" value="Download" class="btnBlue"></td>			
		 					 </tr>
						</table>
					</form>
				</div>
				--%>
			
				<%-- FOR CLICKBOX ONLY --%>
				<c:if test="${company.id eq 257}">
					<table class="companiesTable" cellspacing="0" cellpadding="4" border="0" width="100%" style="margin: 10px 0 15px 0;">
				        <tr>	        			        
							<th style="width: 110px;" align="center"><strong>Member</strong></th>
							<th style="width: 140px;" align="center"><strong>Date</strong></th>												
							<th style="width: 230px;" align="center"><strong>File</strong></th>										
							<th style="width: 80px;" align="center"><strong>Freight</strong></th>	
							<th style="width: 80px;" align="center"><strong>Invoice #</strong></th>		
							<th style="width: 50px;" align="center"><strong>Notes</strong></th>			
							<th style="width: 80px;" align="center"><strong>Amount</strong></th>
							<th style="width: 80px;" align="center"><strong>Status</strong></th>	
							<th style="width: 120px;" align="center"><strong>Updated By</strong></th>
							<th style="width: 80px;" align="center"><strong>Action</strong></th>
				        </tr>
			     
			     		<c:set var="temp" value="0"/>				      		
			      		<c:if test="${fn:length(memberFileItemsList)>0}">
			      		
				        	<c:forEach begin="0" end="${fn:length(memberFileItemsList)-1}" step="1" var="i">						   	
							   	<form action="updateMemberFileFromList.do?" method="post" onsubmit="return MyForm.validate(this);">
					   				<input type="hidden" name="member_id" value="${memberFileItemsList[i].memberFile.member.id}"/>	
					   				<input type="hidden" name="searchStatus" value="${searchStatus}"/>
					   				<input type="hidden" name="searchStatus" value="${memberFileItemsList[i].id}"/>					   										   				
					   							   						   		
							   		<c:if test="${(temp ne memberFileItemsList[i].invoiceNumber) && (memberFileItemsList[i] ne null)}">		
							   			
							   			<div id="notes_${memberFileItemsList[i].id}" style="display: none;">${memberFileItemsList[i].remarks}</div>
							   			   				
									   	<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
							  				<c:set var="count" value="${count+1}" />
									   		<td align="left">${memberFileItemsList[i].memberFile.member.firstname}&nbsp;${memberFileItemsList[i].memberFile.member.lastname}</td>
									   		<td align="left"><fmt:formatDate pattern="MMM dd, yyyy" type="date" value="${memberFileItemsList[i].createdOn}" /> &bull; <fmt:formatDate type="time" value="${memberFileItemsList[i].createdOn}" /></td>
									   		<td align="left">																				    
											    <c:choose>
													<c:when test="${fn:endsWith(memberFileItemsList[i].filename, '.xls') || fn:endsWith(memberFileItemsList[i].filename, '.xlsx')}">
														<a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i].original}"><img src="../images/icons/page_excel.png" border="0" align="absmiddle" /> ${memberFileItemsList[i].filename}</a>
														<input type="hidden" name="memberFile_id"  value="${memberFileItemsList[i].memberFile.id}"/>
								   						<input type="hidden" name="memberFileItem_id"  value="${memberFileItemsList[i].id}"/>
														<c:set var="tempMemberFileItems" value="${memberFileItemsList[i]}"/>
													</c:when>
													<c:otherwise><a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i].original}"><img src="../images/icons/page.png" border="0" align="absmiddle" /> ${memberFileItemsList[i].filename}</a></c:otherwise>
												</c:choose>
												
												<c:if test="${memberFileItemsList[i+1] ne null && memberFileItemsList[i].invoiceNumber == memberFileItemsList[i+1].invoiceNumber}">
													<br>												
													<c:choose>
														<c:when test="${fn:endsWith(memberFileItemsList[i+1].filename, '.xls') || fn:endsWith(memberFileItemsList[i+1].filename, '.xlsx')}">
															<a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i+1].original}"><img src="../images/icons/page_excel.png" border="0" align="absmiddle" /> ${memberFileItemsList[i+1].filename}</a>
															<input type="hidden" name="memberFile_id"  value="${memberFileItemsList[i+1].memberFile.id}"/>
									   						<input type="hidden" name="memberFileItem_id"  value="${memberFileItemsList[i+1].id}"/>
															<c:set var="tempMemberFileItems" value="${memberFileItemsList[i+1]}"/>
														</c:when>
														<c:otherwise><a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i+1].original}"><img src="../images/icons/page.png" border="0" align="absmiddle" /> ${memberFileItemsList[i+1].filename}</a></c:otherwise>
													</c:choose>
												</c:if>
											</td>	
											<td align="center">${memberFileItemsList[i].freight}</td>	
											<td align="center">${memberFileItemsList[i].invoiceNumber}</td>		
											<td>						 			
									 			<div align="center"><a href="javascript:void(0);" onmouseover="showMessage(${memberFileItemsList[i].id});" onmouseout="return nd();"><img style="cursor:pointer;" src="images/note.jpg"></a> </div>
									 		</td>																				
											<td align="right">										
											<%--<c:choose>
													<c:when test="${fn:endsWith(memberFileItemsList[i].filename, '.xls') || fn:endsWith(memberFileItemsList[i].filename, '.xlsx')}">
														<fmt:formatNumber type="number" pattern="###,###.00" value="${memberFileItemsList[i].amount}" />
													</c:when>
													<c:otherwise>
														<fmt:formatNumber type="number" pattern="###,###.00" value="${memberFileItemsList[i+1].amount}" />													
													</c:otherwise>
												</c:choose>		--%>
												<c:choose>
													<c:when test="${fn:endsWith(memberFileItemsList[i].filename, '.xls') || fn:endsWith(memberFileItemsList[i].filename, '.xlsx')}">
														<input type="text" id="amount" name="amount" value="${memberFileItemsList[i].amount}" onkeypress="return keyCheck(event, this);" style="text-align:right; width:100px">
														<c:set var="tempMemberFileItems" value="${memberFileItemsList[i]}"/>
													</c:when>
													<c:otherwise>
														<input type="text" id="amount" name="amount" value="${memberFileItemsList[i+1].amount}" onkeypress="return keyCheck(event, this);" style="text-align:right; width:100px">
														<c:set var="tempMemberFileItems" value="${memberFileItemsList[i+1]}"/>
													</c:otherwise>
												</c:choose>									
											</td>
											<td align="left">
											<%--<c:choose>
													<c:when test="${tempMemberFileItems ne null}">${tempMemberFileItems.memberFile.status}</c:when>
													<c:otherwise>${memberFileItemsList[i+1].memberFile.status}</c:otherwise>
													</c:choose>  --%>
												<select id="status" name="status" style="width:130px;">
													<option value="" selected>-- Select --</option>
													<option value="For Quotation" <c:if test="${tempMemberFileItems.memberFile.status eq 'For Quotation'}"> selected </c:if>>For Quotation</option>
													<option value="Order On Process" <c:if test="${tempMemberFileItems.memberFile.status eq 'Order On Process'}"> selected </c:if>>Order On Process</option>
													<option value="Quote Sent" <c:if test="${tempMemberFileItems.memberFile.status eq 'Quote Sent'}"> selected </c:if>>Quote Sent</option>
													<option value="Paid" <c:if test="${tempMemberFileItems.memberFile.status eq 'Paid'}"> selected </c:if>>Paid</option>
													<option value="For Shipping" <c:if test="${tempMemberFileItems.memberFile.status eq 'For Shipping'}"> selected </c:if>>For Shipping</option>
													<option value="Delivered" <c:if test="${tempMemberFileItems.memberFile.status eq 'Delivered'}"> selected </c:if>>Delivered</option>
												</select>
											</td>	
											<td align="center">${tempMemberFileItems.memberFile.updatedBy}</td>
											<td align="center">
												<input type="submit" name="" value="update" class="btnBlue">
												<a href="deleteMemberFileItem.do?memberFileItem_id=${memberFileItemsList[i].id}">Delete</a>
											</td>
										
											<c:set var="tempMemberFileItems" value="null"/>
											<c:if test="${memberFileItemsList[i].invoiceNumber ne null}">
												<c:set var="temp" value="${memberFileItemsList[i].invoiceNumber}"/>
											</c:if>										
								   		</tr>	
							   		</c:if>
								</form>						   
							</c:forEach>	
						</c:if>   
			    	</table>		    
			    </c:if><!--//Click Box Only-->
			
			<div class="clear"></div>
			
			</div><!--//mainContent-->
			
		</div><!--//contentWrapper-->
  	</div><!--//container-->

</body>

</html>