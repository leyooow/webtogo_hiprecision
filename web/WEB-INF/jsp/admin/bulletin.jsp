<%@include file="includes/header.jsp"  %>


<script src="../admin/javascripts/prototype.js" type="text/javascript"></script>
<script src="../admin/javascripts/scriptaculous.js" type="text/javascript"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRBulletinAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<c:set var="menu" value="bulletin" />
<c:set var="pagingAction" value="bulletin.do" />

	<script type="text/javascript">
		function closeModal() {
			Effect.Fade('OverlayContainer');
		}

		function showMessage(messageID) {
			DWRBulletinAction.getMessageValue(messageID,{
				callback:function(messageContent){
					showMessageContent(messageContent);
				}
			}); 
		}
		function showMessageContent(messageContent) {
			currentMessage = document.getElementById("ModalBox");
			currentMessage.innerHTML = "<a href='#' onclick='closeModal()'"
				+ " style=\"float:  right;\">" 
				+ "<img src=\"images\/x.gif\"></a>"
				+ messageContent;
			Effect.Appear('OverlayContainer');
		}
		
		function showMessageOnload(){
			//check if "message_id" is specified			
			messageID = <%= request.getParameter("messageID")%>

			//check if messageID is specified
			if(null != messageID)
				//show message context
				showMessage(messageID);
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
    <div class="content">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">

	    <h1><strong>Bulletin</strong>: View Bulletin Board</h1>
		
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
						<tr >
						<th > Date</th>
						<th > Subject </th>
						<th > Author </th>
						<th ></th>
					</tr>
				
					<!-- TABLE CONTENTS (Messages) -->  
					<c:set var="count" value="0" />
					<c:forEach var="msg" items="${messageList}">
								<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />     
							<td><fmt:formatDate pattern="MM/dd/yyyy" value="${msg.createdOn}" /></td>							
							<td>
								<a href="#" onclick=showMessage(${msg.id});>
								  ${msg.subject}
								</a>										
							</td>
							<td>${msg.authorName}</td>
							<td>
								<a href="deleteMessage.do?messageID=${msg.id}"  onclick="return confirm('Do you really want to delete this ${msg.subject} message?');">Delete</a>
							</td>
						</tr>
					</c:forEach>
	</table>
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->

</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>