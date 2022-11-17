
<%@include file="includes/header.jsp"  %>

<body>
<c:set var="menu" value="chatmessage" />
<c:set var="pagingAction" value="chatMessage.do" />


<div class="container">
		
	<%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
		
	
	<div class="contentWrapper" id="contentWrapper">
		<div class="mainContent">		
			<div class="pageTitle">
				<h1><strong>Chat Message</strong></h1>				
				<div class="clear"></div>
			</div><!--//pageTitle-->
			<div class="clear"></div>
			<br/>
			<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
				<tr > 
					<th >Username</th>  
					<th >Name</th>
					<th >New Message Count</th>
					<th >Action</th> 
				</tr>
				<c:set var="count" value="0" />
				<c:forEach items="${listChatMember}" var="chatMember">
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
						<c:set var="count" value="${count+1}" />
						<td style="text-align:center;"><a href="chatMessage.do?memberId=${chatMember.id}">${chatMember.username}</a></td>
						<td style="text-align:center;"><a href="chatMessage.do?memberId=${chatMember.id}">${chatMember.firstname}&nbsp;${chatMember.lastname}&nbsp;${chatMember.reg_companyName}</a></td>
						<td style="text-align:center;"><a href="chatMessage.do?memberId=${chatMember.id}">${memberNewMessages[chatMember.id]}</a></td>
						<td style="text-align:center;"><a href="chatMessage.do?memberId=${chatMember.id}">View</a></td>
					</tr>									
				</c:forEach>				
			</table>	
		</div><!--//mainContent-->
			
		<div class="sidenav">
			<form method="post" action="savegroup.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		 		<h2>Message Panel</h2>
		  		<table id="table1" width="100%">
					<tr>
						<td >Message<br /><textarea rows="8" name="messageContent" class="w200" placeholder="Type your message here..."></textarea></td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td>
							<input type="submit" value="Send" class="btnBlue">		
						</td>
					</tr>	
				</table>
			</form>
		</div><!--//sidenav-->
	</div>
	<div class="clear"></div>

</div><!--//container-->

</body>
</html>