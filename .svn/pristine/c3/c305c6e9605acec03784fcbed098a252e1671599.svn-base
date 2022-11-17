<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  
<%@include file="../includes/taglibs.jsp" %>

<c:set var="navMenu" value="messageboard" scope="session"/>

<jsp:include page="../../companies/${company.name}/jsp/includes/header_home.jsp"/>
<link href="../../companies/${company.name}/css/css.css" rel="stylesheet" type="text/css" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">   
  <tr valign="top">
    <td class="sLeft"><img src="${httpServer}/images/sLeft.jpg" /></td>      
      
    <td class="contentContainer">
    <div class="masthead"><img src="${httpServer}/images/masthead2.jpg" alt="${company.nameEditable}" title="${company.nameEditable}" /></div>
		<jsp:include page="../../companies/${company.name}/jsp/includes/menulinks.jsp"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">  
		     <tr valign="top">
				<jsp:include page="../../companies/${company.name}/jsp/includes/sidenav.jsp"/>
				<td class="content">
					<p><a href="messageboard.do">Back to Message Board</a></p>  
					<div class="contentWrapper" id="contentWrapper">  
						<c:set var="msg" value="${messageDetail}"/>
						<font size="+.2">
						Date: <fmt:formatDate pattern="MMM dd, yyyy hh:mm a" value="${msg.createdOn}" /> <br/>  
						Author: ${msg.authorName} <br/>
						Subject: ${msg.subject} <br/>
						<br/>   
						</font>
						<c:if test="${not empty msg.attachmentFile}">
							<!-- LOCAL ONLY
							<img src="<c:url value='/companies/${company.name}/message_attachments/${msg.attachmentFile}'/>" /><br/>
							-->  
							<!-- FOR LIVE 
							-->
							<img src="${httpServer}/message_attachments/${msg.attachmentFile}" /><br/>
						</c:if>  
							
						${msg.content}
					
					</div><!--contentWrapper end-->
					<p><a href="messageboard.do">Back to Message Board</a></p>  
			   </td><!--content end-->   
			 </tr>
		</table>
	</td>
    
    <td class="sRight"><img src="${httpServer}/images/sRight.jpg" /></td>
  </tr>
</table>
   
<jsp:include page="../../companies/${company.name}/jsp/includes/bottomcontent.jsp"/>
   
<jsp:include page="../../companies/${company.name}/jsp/includes/footer.jsp"/>