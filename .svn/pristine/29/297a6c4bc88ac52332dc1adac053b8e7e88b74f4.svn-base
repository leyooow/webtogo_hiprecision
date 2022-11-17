<span class="actionMsg"><s:actionmessage/></span>
<h3>Message Board:</h3>
<div style="width:100%">
<table cellspacing="0" cellpadding="0" class="messageboard">
	<!-- PAGING -->
	<tr>
		<td colspan="3">
			<div style="float:right">
			  	<c:set var="actionDo" value="messageboard.do"/>
			  	<jsp:include page="../includes/paging-util.jsp" />    
		  	</div>  
  		</td>
  	</tr> 
  
	<!-- TABLE HEADERS -->
	<tr>
		<th width="30%"> Date</th>
		<th width="40%"> Subject </th>
		<th width="30%"> Author </th>
	</tr>

	<!-- TABLE CONTENTS (Messages) -->  
	<c:forEach var="msg" items="${messageList}">
		<tr>  
			<td><font size="+.2"><fmt:formatDate pattern="MM/dd/yyyy" value="${msg.createdOn}" /></font></td>
			<td><a href='<c:url value="messagedetail.do?messageid=${msg.id}"/>'><font size="+.2"> ${msg.subject}</font></a></td>
			<td align="center"><font size="+.2">${msg.authorName}</font></td>
		</tr>
	</c:forEach>
	
	<!-- PAGING -->
	<tr>
		<td colspan="3">
			<div style="float:right">
			  	<c:set var="actionDo" value="messageboard.do"/>
			  	<jsp:include page="../includes/paging-util.jsp" />    
		  	</div>  
  		</td>
  	</tr> 
        
</table></div>