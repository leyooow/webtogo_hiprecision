<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="members" />
<c:set var="submenu" value="member_listing" />
<c:set var="pagingAction" value="members.do" />


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

	function keyCheck(eventObj, obj)
	{
	    var keyCode
	    if (document.all){
	        keyCode=eventObj.keyCode
	    }
	    else{
	        keyCode=eventObj.which
	    }
	 
	    var str=obj.value
	    
	   if(keyCode == 45 && str == ''){ // '-' must be the first character
		   return true
	   }
	    
	    if(keyCode==46){
	        if (str.indexOf(".")>0){
	            return false
	        }
	    }		    
	 
	    if( keyCode == 8 || keyCode == 0){
	    	return true
		}else if(((keyCode<48 || keyCode >58)   &&   (keyCode != 46))){ // Allow only integers and decimal points
	        return false
	    }		 
	    return true;
	}
	function showMessage(id) 
	{
		var notes = document.getElementById('notes_'+id).innerHTML;
		overlib(notes, STICKY, NOCLOSE)
	}
</script>

<div class="contentWrapper" id="contentWrapper">
    <div class="content">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">

		<c:choose>
			<c:when test="${fn:contains(company.name, 'gurkka')}">
				<h1><strong>Member Information</strong></h1>
			</c:when>
			<c:when test="${fn:contains(company.name, 'hometherapist')}">
				<h1><strong>Member Information</strong></h1>
			</c:when>
			<c:otherwise>
				<h1><strong>Members</strong>: Add New Company Member</h1>				
			</c:otherwise>
		</c:choose>
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	  
	
	<div class="clear"></div>
	<%@include file="memberform.jsp"%>
	
	<table width="100%" >
		<tr>
			<td align="right">
				<c:if test="${receiptList != null && fn:length(receiptList) ge 1}">	
					<div style="float:right">
						<form action="downloadmemberfileitems_receipts.do" name="" method="post">
							<input type="hidden" name="fieldName" value="Title">
							<input type="hidden" name="fieldName" value="Invoice Number">
							<input type="hidden" name="fieldName" value="Remarks">
							<input type="hidden" name="fieldName" value="Points">
							<input type="hidden" name="fieldName" value="Status">
							<input type="hidden" name="fieldName" value="Created On">
							<input type="hidden" name="fieldName" value="Action By">
							<input type="hidden" name="fieldName" value="Approved Date">
							<input type="hidden" name="member_id" value="${member2.id}">
							<input type="submit" name="downloadAllMemberFiles" class="btnBlue" value="Download All Receipts Information">
						</form>
					</div>	
								
					<%-- FOR WESTERN DIGITAL ONLY --%>
					<c:if test="${company.id eq 243}">
						<div style="float:right; margin-right: 5px;">
							<form action="uploadMemberReceipt.do" name="" method="post">
								<input type="hidden" name="member_id" value="${(member ne null) ? member.id : member2.id}">
								<input type="hidden" name="group_id" value="221">
								<input type="submit" name="uploadreceipt" class="btnBlue" value="Upload Receipt">
							</form>
						</div>
					</c:if>
					<%-- FOR APC ONLY --%>
					<c:if test="${company.id eq 222}">
						<div style="float:right; margin-right: 5px;">
							<form action="uploadMemberReceipt.do" name="" method="post">
								<input type="hidden" name="member_id" value="${(member ne null) ? member.id : member2.id}">
								<input type="hidden" name="group_id" value="212">
								<input type="submit" name="uploadreceipt" class="btnBlue" value="Upload Receipt">
							</form>
						</div>
					</c:if>
				</c:if>	
			</td>
		</tr>
	</table>
	
	<div class="clear"></div>
	<c:if test="${receiptList != null}">
		<%@include file="apcIncludes/memberReceiptDetails.jsp" %>
	</c:if>
	
	<%-- FOR CLICKBOX ONLY --%>
	<c:if test="${company.id eq 257}">
		<table class="companiesTable" cellspacing="0" cellpadding="4" border="0" width="100%" style="margin: 30px 0 15px 0;">
	        <tr>	        			        
				<th style="width: 130px;" align="center"><strong>Date</strong></th>
				<th style="width: 100px;" align="center"><strong>Invoice Number</strong></th>
				<th style="width: 50px;" align="center"><strong>Notes</strong></th>	
				<th style="width: 250px;" align="center"><strong>File</strong></th>
				<th style="width: 80px;" align="center"><strong>Freight</strong></th>
				<th style="width: 80px;" align="center"><strong>Amount</strong></th>					
				<th style="width: 80px;" align="center"><strong>Status</strong></th>				
				<th style="width: 80px;" align="center"><strong>Action</strong></th>
	        </tr>
	     
	     	<c:set var="temp" value="0"/>	
	      	<c:if test="${fn:length(memberFileItemsList)>0}">
		        
		        <c:forEach begin="0" end="${fn:length(memberFileItemsList)-1}" step="1" var="i">
				  	<form action="updateMemberFile.do?" method="post" onsubmit="return MyForm.validate(this);">
				   		<input type="hidden" name="member_id"  value="${member2.id}"/>
				   						   		
				   		<c:if test="${temp ne memberFileItemsList[i].invoiceNumber}">		
				   			
				   			<div id="notes_${memberFileItemsList[i].id}" style="display: none;">${memberFileItemsList[i].remarks}</div> 	   				
						   	
						   	<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
				  				<c:set var="count" value="${count+1}" />
						   		<td align="center"><fmt:formatDate pattern="MMM dd, yyyy" type="date" value="${memberFileItemsList[i].createdOn}" /> &bull; <fmt:formatDate type="time" value="${memberFileItemsList[i].createdOn}" /></td>
						   		<td align="center">${memberFileItemsList[i].invoiceNumber}</td>
						   		<td>						 			
						 			<div align="center"><a href="javascript:void(0);" onmouseover="showMessage(${memberFileItemsList[i].id});" onmouseout="return nd();"><img style="cursor:pointer;" src="images/note.jpg"></a> </div>
						 		</td>
								<td align="left">																	    
								    <c:choose>
										<c:when test="${fn:endsWith(memberFileItemsList[i].filename, '.xls') || fn:endsWith(memberFileItemsList[i].filename, '.xlsx')}">
											<a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i].original}"><img src="../images/icons/page_excel.png" border="0" align="absmiddle" /> ${memberFileItemsList[i].filename}</a>
											<input type="hidden" name="memberFile_id"  value="${memberFileItemsList[i].memberFile.id}"/>
				   							<input type="hidden" name="memberFileItem_id"  value="${memberFileItemsList[i].id}"/>111
										</c:when>
										<c:otherwise><a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i].original}"><img src="../images/icons/page.png" border="0" align="absmiddle" /> ${memberFileItemsList[i].filename}</a></c:otherwise>
									</c:choose>
									
									<c:if test="${memberFileItemsList[i+1] ne null && memberFileItemsList[i].invoiceNumber == memberFileItemsList[i+1].invoiceNumber}">
										<c:choose>
											<c:when test="${fn:endsWith(memberFileItemsList[i+1].filename, '.xls') || fn:endsWith(memberFileItemsList[i+1].filename, '.xlsx')}">
												<a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i+1].original}"><img src="../images/icons/page_excel.png" border="0" align="absmiddle" /> ${memberFileItemsList[i+1].filename}</a>
												<input type="hidden" name="memberFile_id"  value="${memberFileItemsList[i+1].memberFile.id}"/>
					   							<input type="hidden" name="memberFileItem_id"  value="${memberFileItemsList[i+1].id}"/>222
											</c:when>
											<c:otherwise><a href="http://clickbox.webtogo.com.ph/${memberFileItemsList[i+1].original}"><img src="../images/icons/page.png" border="0" align="absmiddle" /> ${memberFileItemsList[i+1].filename}</a></c:otherwise>
										</c:choose>
									</c:if>									
								</td>	
								<td align="center">${memberFileItemsList[i].freight}</td>						
								<td align="center">								
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
								<td align="center">
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
								<td align="center">
									<input type="submit" name="" value="update" class="btnBlue">
									<a href="deleteMemberFileItem.do?memberFileItem_id=${memberFileItemsList[i].id}">Delete</a>
								</td>
								<c:set var="temp" value="${memberFileItemsList[i].invoiceNumber}"/>
						   	</tr>	
					   	</c:if>
				   	</form>			   
				</c:forEach>	
				
			</c:if>   
	    </table>    	
	</c:if>
	
	<div class="clear"></div>
	
	
	<%--
  
  <div style="float:left">

	  <table width="40%" border="0" cellspacing="0" cellpadding="3">

				
			<c:set var="memberCompanyId" value="${(member2.company != null) ? member2.company.id : 0 }"></c:set>
<!-- 		<tr>
				<td width="1%" nowrap>Company Name </td>
				<td width="10px"></td>
				<td>
				<select id="company_id" name="company_id" class="combobox1">
					<option value="">- Select Company -</option>
					<c:forEach items="${companies}" var="c">
						<option value="${c.id}" <c:if test="${c.id == memberCompanyId}">selected</c:if>>${c.nameEditable}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
 -->
			<tr>
				 <td class="label" width="30%">Username</td>
				<td align="left"  width="70%"><input type="text" id="username" name="member2.username" value="${member2.username}" class="w200"></td>
			</tr>
			<tr>
				<td class="label">Password</td>
			
				<td align="left"><input type="text" id="password" name="password" value="${password}" class="w200"></td>
			</tr>
			<tr>
				<td class="label">Email Address</td>
				
				<td align="left"><input type="text" id="emailAdd" name="member2.email" value="${member2.email}" class="w200"></td>
			</tr>
			<tr>
				<td class="label">First Name</td>
				
				<td align="left"><input type="text" id="firstname" name="member2.firstname" value="${member2.firstname}" class="w200"></td>
			</tr>
			<tr>
				<td class="label">Last Name</td>
				
				<td align="left"><input type="text" id="lastname" name="member2.lastname" value="${member2.lastname}" class="w200"></td>
			</tr>
			<c:if test="${not empty member2 and user.company.nameEditable=='Silver Finance Inc.'}">
			<tr>
				<td class="label"><s:checkbox name="member2.verified" value="%{member2.verified}" theme="simple" /> Verified</td>
				
			</tr>
			</c:if>
			<tr>
<!-- 		<tr>
				<td nowrap>&nbsp;</td>
				<td></td>
				<td>	
					<s:checkbox id="activated" name="member2.activated" value="%{member2.activated}" theme="simple" /> <i><b>This account is activated.</i>	
				</td>
			</tr>
 -->
			<tr>
			<td class="label"></td>
				<td align="left">
						<c:if test="${empty member2}">
							<input type="submit" name="submit" value="Add New" class="btnBlue">
					</c:if>
					<c:if test="${not empty member2}">
							<input type="submit" name="submit" value="Update" class="btnBlue">
					</c:if>
					<input type="button" name="cancel" value="Cancel" class="btnBlue" onclick="window.location='members.do'">
				
				</td>
			</tr>
		</table>
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	  
	  </div>
	  <div style="vertical-align:top;float:right;width:50%">ADSSADSDFAADAS</div>
	  
	  <div class="clear"></div>
	  
	  --%>
	   
	</div><!--//mainContent-->
	
	
</div>





	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>