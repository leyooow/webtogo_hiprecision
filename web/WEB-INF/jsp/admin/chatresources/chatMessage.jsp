<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html >
  <head>
    <meta charset="UTF-8">
    <title>WebTogo - Get online</title>
    <link rel="SHORTCUT ICON" HREF="images/webtogo.ico">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %> 
	<%-- <%@taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>--%>
	<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
	<%@ taglib prefix="FCK" uri="http://java.fckeditor.net" %>
	<%@taglib uri="/struts-tags" prefix="s" %>
	<%@include file="../includes/constants.jsp"%>
	
	<c:if test="${company.name eq 'gurkkatest'}">
		<%
		session.setMaxInactiveInterval(-1);
		%>
	</c:if>
    
    <link rel="stylesheet" href="chatresources/css/reset.css">

    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css'>

        <link rel="stylesheet" href="chatresources/css/style.css">

    
    
    
  </head>

  <body>
	<c:choose>
	<c:when test="${company.name eq 'rockwell' }">
		<c:if test="${(param.memberId eq '' or param.memberId eq null) and fn:length(listChatMember) gt 0 }">
		<c:redirect url="chatMessage.do?memberId=${listChatMember[0].id}" />
	</c:if>
      <div class="container clearfix">
    <div class="people-list" id="people-list">
      <div class="search" style="display:none;">
        <input type="text" placeholder="search" />
        <i class="fa fa-search"></i>
      </div>
      
      <ul class="list2" style="height:;border: 1px solid #666666;background: #000;">
      		<li class="clearfix" style="background:#000;">
      			<h1>ADMIN USER(S)</h1>
      		</li>
      		<c:forEach items="${listChatAdminUser}" var="chatAdminItem" varStatus = "c">
      		
	      		<li class="clearfix" style="background:#000;">
		          <a style="color:#fff;">
			          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar">
			          <div class="about">
			            <div class="name">${chatAdminItem.username}</div>
			            <div class="status2">
			              <i class="fa fa-circle offline" id="memberonlinestatusadmin${chatAdminItem.id}"></i> <font id="memberadmin${chatAdminItem.id}">OFFLINE</font>&nbsp;
			            </div>
			          </div>
		          </a>
		        </li>
		        
	        </c:forEach>
	        
	        
	</ul>
      
      <ul class="list">
      	<!-- <li class="clearfix"> -->
        	<h1>CLIENT USER(S)</h1>
        <!-- <li class="clearfix"> -->
        <c:forEach items="${listChatMember}" var="chatMember">
        	
        	<li class="clearfix">
	          <a class="achat" href="chatMessage.do?memberId=${chatMember.id}" style="color:#fff;">
		          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
		          <div class="about">
		            <div class="name">${chatMember.username}</div>
		            <div class="status">
		              <i class="fa fa-circle offline" id="memberonlinestatus${chatMember.id}"></i> <font id="member${chatMember.id}" class="fontchat" reltag="${chatMember.createdOn}">${memberNewMessages[chatMember.id]}</font>&nbsp;New Message(s)
		            </div>
		          </div>
	          </a>
	        </li>
        	
        </c:forEach>
        
        <!-- 
        <li class="clearfix">
          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
          <div class="about">
            <div class="name">Vincent Porter</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
          <div class="about">
            <div class="name">Aiden Chavez</div>
            <div class="status">
              <i class="fa fa-circle offline"></i> left 7 mins ago
            </div>
          </div>
        </li>
         -->
         
      </ul>
    </div>
    
    <div class="chat">
      
      <div class="chat-header clearfix">
        <c:if test="${selectedMember ne null}">
	        <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
	        <div class="chat-about">
	          <c:choose>
		          <c:when test="${company.name eq 'rockwell' }">
		          	<div class="chat-with">Chat with ${selectedMember.email}</div>
		          </c:when>
	          
		          <c:otherwise>
		         	 <div class="chat-with">Chat with ${selectedMember.firstname}&nbsp;${selectedMember.lastname}&nbsp;${selectedMember.reg_companyName}</div>
		          </c:otherwise>
	          </c:choose>
	          
	          <div class="chat-num-messages"><!-- already 1 902 messages --></div>
	        </div>
	        <i class="fa fa-star"></i>
        </c:if>
      </div> <!-- end chat-header -->
      
      <div class="chat-history">
        <ul class="chat-conversation-wrapper">
        	<c:forEach items="${selectedMemberMessage}" var="messageItem" varStatus="c">
		          <c:choose>
		          	<c:when test="${messageItem.senderMember.id eq selectedMember.id}">
		          		<%-- Coming from the client --%>
		          		<li>
				            <div class="message-data">
				              <span class="message-data-name"><i class="fa fa-circle online"></i> ${selectedMember.username}</span>
				              <span class="message-data-time"><fmt:formatDate pattern="MMMM dd, yyyy hh:mm a EEEE"  value="${messageItem.createdOn}" /></span>
				            </div>
				            <div class="message my-message">
				              ${messageItem.content}
				            </div>
				          </li>
		          	</c:when>
		          	<c:otherwise>
		          		
		          		<li class="clearfix">
				            <div class="message-data align-right">
				              <span class="message-data-time" ><fmt:formatDate pattern="MMMM dd, yyyy hh:mm a EEEE"  value="${messageItem.createdOn}" /></span> &nbsp; &nbsp;
				              <span class="message-data-name" >Administrator</span> <i class="fa fa-circle me"></i>
				              
				            </div>
				            <div class="message other-message float-right">
				              ${messageItem.content}
				            </div>
				          </li>
		          		
		          	</c:otherwise>
		          </c:choose>
          </c:forEach>
          <%-- 
          <li class="clearfix">
            <div class="message-data align-right">
              <span class="message-data-time" >10:10 AM, Today</span> &nbsp; &nbsp;
              <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
              
            </div>
            <div class="message other-message float-right">
              Hi Vincent, how are you? How is the project coming along?
            </div>
          </li>
          
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:12 AM, Today</span>
            </div>
            <div class="message my-message">
              Are we meeting today? Project has been already finished and I have results to show you.
            </div>
          </li>
           --%>
          
          <%-- 
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:31 AM, Today</span>
            </div>
            <i class="fa fa-circle online"></i>
            <i class="fa fa-circle online" style="color: #AED2A6"></i>
            <i class="fa fa-circle online" style="color:#DAE9DA"></i>
          </li>
           --%>
        </ul>
        
      </div> <!-- end chat-history -->
      
      <div class="chat-message clearfix">
        <c:if test="${selectedMember ne null}">
	        <form class="input-group" onSubmit="validateSubmitChatMessage(this);return false;">
		        <textarea name="messageContent" id="messageContent" class="messageContent" placeholder ="Type your message" rows="3"></textarea>
		        <!--         
		        <i class="fa fa-file-o"></i> &nbsp;&nbsp;&nbsp;
		        <i class="fa fa-file-image-o"></i>
		         -->
		        <button type="submit">Send</button>
	        </form>
		</c:if>
      </div> <!-- end chat-message -->
      
    </div> <!-- end chat -->
    
  </div> <!-- end container -->

		<form name="loadChatMessages" id="loadChatMessages" method="post" enctype="multipart/form-data" action="loadChatMessages.do">
			<input type="hidden" name="status" id="membermessagestatus" class="membermessagestatus" value="All" />
			<input type="hidden" name="memberId" id="memberId" class="memberId" value="" />
		</form>
		
		<form name="submitChatMessage" id="submitChatMessage" class="submitChatMessage" method="post" enctype="multipart/form-data" action="submitChatMessage.do">
			<!-- 
			<input type="hidden" name="status" id="membermessagestatus" class="membermessagestatus" value="" />
			<input type="hidden" name="remarks" id="membermessageremarks" class="membermessageremarks" value="" />
			<input type="hidden" name="messageType" id="membermessagetype" class="membermessagetype" value="" />
			 -->
			<input type="hidden" name="content" id="content" class="content" value="" />
			<input type="hidden" name="memberId" id="memberId" class="memberId" value="" />
		</form>

<script id="message-template" type="text/x-handlebars-template">
  <li class="clearfix">
    <div class="message-data align-right">
      <span class="message-data-time" >{{time}}, Today</span> &nbsp; &nbsp;
      <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
    </div>
    <div class="message other-message float-right">
      {{messageOutput}}
    </div>
  </li>
</script>

<script id="message-response-template" type="text/x-handlebars-template">
  <li>
    <div class="message-data">
      <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
      <span class="message-data-time">{{time}}, Today</span>
    </div>
    <div class="message my-message">
      {{response}}
    </div>
  </li>
</script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src='http://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js'></script>
	<script src='http://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js'></script>

	<script src="chatresources/js/index.js"></script>
	<div id="dontLoad" style="display: none;">true</div>
	<div id="adminFlag" style="display: none;">1</div>
    <script>
    	var latestErrorMessage="";
		var latestErrorMessagePage="";
		var prevData = "";
	
    	window.onload = function() {
    		<c:if test="${selectedMember ne null}">
    			var auto_refresh = setInterval(
    					function() {
    						if($('#adminFlag').html() == '0'){
    							$('#dontLoad').html('false');
    						}
    						loadChatMessages("${selectedMember.id}", "${GurkkaConstants.NEW}");
    						$('#adminFlag').html('0');
    					}
    					,7000);
    		</c:if>
    	}
    	
    	function loadChatMessages(strid_, status_) {
    		
    		if(status_ == "${GurkkaConstants.NEW}"){
    			$('#loadChatMessages #membermessagestatus').attr('value','${GurkkaConstants.NEW}');
    			$('#loadChatMessages #memberId').attr('value','${selectedMember.id}');
    		}
    		else{
    			$('#loadChatMessages #membermessagestatus').attr('value','All');
    			$('#loadChatMessages #memberId').attr('value','0');
    		}
    		$.ajax({
    			type: $('#loadChatMessages').attr('method'),
    			url: $('#loadChatMessages').attr('action'),
    			cache: false,
    			dataType: "json",
    			async: true,
    			data: $('#loadChatMessages').serialize(),
    			success: function(data) {
    				if(prevData != data.listNewMemberMessageCount.length){
    					
    					prevData = data.listNewMemberMessageCount.length;
    					if(data.listNewMemberMessageCount.length > 0) {
        					
        					var messageCount = data.listNewMemberMessageCount.length;
    						for(var i = 0; i < messageCount; i++) {
    							
    							//data.listNewMemberMessageCount[i].memberid
    							//data.listNewMemberMessageCount[i].newmessagecount
    							if(data.listNewMemberMessageCount[i].newmessagecount != "0"){
    								$('#member'+data.listNewMemberMessageCount[i].memberid).html(data.listNewMemberMessageCount[i].newmessagecount);
    								$('#member'+data.listNewMemberMessageCount[i].memberid).attr("reltag",data.listNewMemberMessageCount[i].memberlastmessagedate);
    							}
    							if(data.listNewMemberMessageCount[i].loginstatus == "online"){
    								$('#memberonlinestatus'+data.listNewMemberMessageCount[i].memberid).removeClass("offline").addClass("online");
    							}
    							else{
    								$('#memberonlinestatus'+data.listNewMemberMessageCount[i].memberid).removeClass("online").addClass("offline");
    							}
    							
    						}
    						
        				}
        				
        				if(data.listMemberMessage.length > 0) {
        					if(data.listMemberMessage[0].errorMessage != null && data.listMemberMessage[0].errorMessage != undefined){
        						//if(!(latestErrorMessage == data.listMemberMessage[0].errorMessage) && !(latestErrorMessagePage=="${navMenu}"))
        						//{
        							$.notify(data.listMemberMessage[0].errorMessage, "error");
        							latestErrorMessage = data.listMemberMessage[0].errorMessage;
        							latestErrorMessagePage="${navMenu}";
        						//}
        					}
        					else{
        						var messageCounts = data.listMemberMessage.length;
        						//alert(messageCounts);
        						
        						for(var i = 0; i < messageCounts; i++) {
        							//alert(i +": "+ data.listMemberMessage[i].content);
        							if(data.listMemberMessage[i].createdon != null && data.listMemberMessage[i].createdon != undefined){
    	    							if(data.listMemberMessage[i].senderid == "${selectedMember.id}" && data.listMemberMessage[i].senderid != "") {
    	    								if(i != messageCounts-1){
	    	    								if(data.listMemberMessage[i+1].content == undefined && $('#dontLoad').html() == 'false'){
	    	    									//alert("dontLoad VALUE: "+ $('#dontLoad').html() );
		    	    								$('.chat-conversation-wrapper').append(''
		    	    										+''
		    	    										+''
		    	    										+''
		    	    										+' <li>'
		    	    										+'	<div class="message-data">'
		    	    										+'	  <span class="message-data-name"><i class="fa fa-circle online"></i> '+data.listMemberMessage[i].senderusername+'</span>'
		    	    										+'	  <span class="message-data-time">'+data.listMemberMessage[i].createdon+'</span>'
		    	    										+'	</div>'
		    	    										+'	<div class="message my-message">'
		    	    										+'	  '+data.listMemberMessage[i].content+''
		    	    										+'	</div>'
		    	    										+'  </li>'
		    	    										+''
		    	    										+''
		    	    										+'');
		    	    								$('#dontLoad').html('true');
		    	    								break;
	    	    								}
    	    								}
    	    								else if(dontLoad == 'false'){
    	    									$('.chat-conversation-wrapper').append(''
	    	    										+''
	    	    										+''
	    	    										+''
	    	    										+' <li>'
	    	    										+'	<div class="message-data">'
	    	    										+'	  <span class="message-data-name"><i class="fa fa-circle online"></i> '+data.listMemberMessage[i].senderusername+'</span>'
	    	    										+'	  <span class="message-data-time">'+data.listMemberMessage[i].createdon+'</span>'
	    	    										+'	</div>'
	    	    										+'	<div class="message my-message">'
	    	    										+'	  '+data.listMemberMessage[i].content+''
	    	    										+'	</div>'
	    	    										+'  </li>'
	    	    										+''
	    	    										+''
	    	    										+'');
    	    									$('#dontLoad').html('true');
	    	    								
    	    								}
    	    							}
    	    							else if(data.listMemberMessage[i].senderid != "${selectedMember.id}" && data.listMemberMessage[i].senderid != "") {
    	    								$('.chat-conversation-wrapper').append(''
    	    										+''
    	    										+''
    	    										+''
    	    										+' <li class="clearfix">'
    	    										+'	<div class="message-data align-right">'
    	    										+'	  <span class="message-data-time" >'+data.listMemberMessage[i].createdon+'</span> &nbsp; &nbsp;'
    	    										+'	  <span class="message-data-name" >Administrator</span> <i class="fa fa-circle me"></i>'	  
    	    										+'	</div>'
    	    										+'	<div class="message other-message float-right">'
    	    										+'	  '+data.listMemberMessage[i].content+''
    	    										+'	</div>'
    	    										+'  </li>'
    	    										+''
    	    										+''
    	    										+''
    	    										+''
    	    										+''
    	    										+'');
    	    								$('#dontLoad').html('true');
    	    								$('#adminFlag').html('1');
    	    								break;
    	    							}
        							}
        							
        						}
        						//$(".nano").nanoScroller();
        						//$(".nano").nanoScroller({ scroll: 'bottom' });
        						$(".chat-history").scrollTop(function() { return this.scrollHeight; });
        					}
        				}
    				}
    				
    			},
    			complete: function() {
    				sortDocumentElement();
    			},
    			error: function(xhr, textStatus, error){
    				if(!(latestErrorMessage == "An error was encountered while processing your action!") && !(latestErrorMessagePage=="${navMenu}"))
    				{
    					console.log(error);
    					$.notify("An error was encountered while processing your action!", "error");
    					
    					latestErrorMessage = "An error was encountered while processing your action!";
    					latestErrorMessagePage="${navMenu}";
    				}
    			}
    		});
    	}
    	
    	function submitChatMessage() {
    		$('#submitChatMessage #memberId').attr('value','${selectedMember.id}');
    		$.ajax({
    			type: $('#submitChatMessage').attr('method'),
    			url: $('#submitChatMessage').attr('action'),
    			cache: false,
    			dataType: "json",
    			async: true,
    			data: $('#submitChatMessage').serialize(),
    			success: function(data) {
    				if(data.listSubmitMemberMessage.length > 0) {
    					if(data.listSubmitMemberMessage[0].errorMessage != null){
    						//if(!(latestErrorMessage == data.listMemberMessage[0].errorMessage) && !(latestErrorMessagePage=="${navMenu}"))
    						//{
    							$.notify(data.listMemberMessage[0].errorMessage, "error");
    							latestErrorMessage = data.listMemberMessage[0].errorMessage;
    							latestErrorMessagePage="${navMenu}";
    						//}
    					}
    				}
    			},
    			complete: function() {
    				
    			},
    			error: function(xhr, textStatus, error) {
    				if(!(latestErrorMessage == "An error was encountered while processing your action!") && !(latestErrorMessagePage=="${navMenu}"))
    				{
    					console.log(error);
    					$.notify("An error was encountered while processing your action!", "error");
    					
    					latestErrorMessage = "An error was encountered while processing your action!";
    					latestErrorMessagePage="${navMenu}";
    				}
    			}
    		});
    	}
    	
    	function validateSubmitChatMessage(myForm) {
    		var messages = "* The following error(s) were encountered : \n\n";
    		var error = false;
    		var content = myForm.messageContent.value;
    		
    		if(content.trim().length == 0) {
    			error = true;
    			messages = " * Message Content was not specified.";
    		}
    		if(error){
    			alert(messages);
    			return false;
    		}
    		else{
    			$('#submitChatMessage #content').val(content);
    			myForm.messageContent.value = "";
    		}
    		submitChatMessage();
    		return false;
    	}
    	
    	<%--------------------ADDED CODE FOR SORTING---------------------------- --%>
    	
    	
		function sortDocumentElement() {
			var sort_by_name = function(a, b) {
				var firstItem = a.getElementsByClassName("achat")[0];
				firstItem = firstItem.getElementsByClassName("about")[0];
				firstItem = firstItem.getElementsByClassName("status")[0];
				firstItem = firstItem.getElementsByClassName("fontchat")[0];
				var secondItem = b.getElementsByClassName("achat")[0];
				secondItem = secondItem.getElementsByClassName("about")[0];
				secondItem = secondItem.getElementsByClassName("status")[0];
				secondItem = secondItem.getElementsByClassName("fontchat")[0];
				
				return secondItem.getAttribute("reltag").toLowerCase().localeCompare(firstItem.getAttribute("reltag").toLowerCase());
			}
			var list = jQuery(".list > .clearfix").get();
			list.sort(sort_by_name);
			for (var i = 0; i < list.length; i++) {
				list[i].parentNode.appendChild(list[i]);
			}
		}
    	
    </script>
	</c:when>
	<c:otherwise>
	
    <c:if test="${(param.memberId eq '' or param.memberId eq null) and fn:length(listChatMember) gt 0 }">
		<c:redirect url="chatMessage.do?memberId=${listChatMember[0].id}" />
	</c:if>
      <div class="container clearfix">
    <div class="people-list" id="people-list">
      <div class="search" style="display:none;">
        <input type="text" placeholder="search" />
        <i class="fa fa-search"></i>
      </div>
      
      
      <ul class="list2" style="height:;border: 1px solid #666666;background: #000;">
      		<li class="clearfix" style="background:#000;">
      			<h1>ADMIN USER(S)</h1>
      		</li>
      		<c:forEach items="${listChatAdminUser}" var="chatAdminItem" varStatus = "c">
      		
	      		<li class="clearfix" style="background:#000;">
		          <a style="color:#fff;">
			          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar">
			          <div class="about">
			            <div class="name">${chatAdminItem.username}</div>
			            <div class="status2">
			              <i class="fa fa-circle offline" id="memberonlinestatusadmin${chatAdminItem.id}"></i> <font id="memberadmin${chatAdminItem.id}">OFFLINE</font>&nbsp;
			            </div>
			          </div>
		          </a>
		        </li>
		        
	        </c:forEach>
	        
	        
	</ul>
      
      <ul class="list">
      	<!-- <li class="clearfix"> -->
        	<h1>CLIENT USER(S)</h1>
        <!-- <li class="clearfix"> -->
        <c:forEach items="${listChatMember}" var="chatMember">
        	
        	<li class="clearfix">
	          <a class="achat" href="chatMessage.do?memberId=${chatMember.id}" style="color:#fff;">
		          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
		          <div class="about">
		            <div class="name">${chatMember.username}</div>
		            <div class="status">
		              <i class="fa fa-circle offline" id="memberonlinestatus${chatMember.id}"></i> <font id="member${chatMember.id}" class="fontchat" reltag="${chatMember.createdOn}">${memberNewMessages[chatMember.id]}</font>&nbsp;New Message(s)
		            </div>
		          </div>
	          </a>
	        </li>
        	
        </c:forEach>
        
        <!-- 
        <li class="clearfix">
          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
          <div class="about">
            <div class="name">Vincent Porter</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
          <div class="about">
            <div class="name">Aiden Chavez</div>
            <div class="status">
              <i class="fa fa-circle offline"></i> left 7 mins ago
            </div>
          </div>
        </li>
         -->
         
      </ul>
    </div>
    
    <div class="chat">
      
      <div class="chat-header clearfix">
        <c:if test="${selectedMember ne null}">
	        <img style="border-radius:50%;width:50px;height:50px;" src="chatresources/images/annonymous.jpg" alt="avatar" />
	        <div class="chat-about">
	          <c:choose>
		          <c:when test="${company.name eq 'rockwell' }">
		          	<div class="chat-with">Chat with ${selectedMember.email}</div>
		          </c:when>
	          
		          <c:otherwise>
		         	 <div class="chat-with">Chat with ${selectedMember.firstname}&nbsp;${selectedMember.lastname}&nbsp;${selectedMember.reg_companyName}</div>
		          </c:otherwise>
	          </c:choose>
	          
	          <div class="chat-num-messages"><!-- already 1 902 messages --></div>
	        </div>
	        <i class="fa fa-star"></i>
        </c:if>
      </div> <!-- end chat-header -->
      
      <div class="chat-history">
        <ul class="chat-conversation-wrapper">
        	<c:forEach items="${selectedMemberMessage}" var="messageItem" varStatus="c">
		          <c:choose>
		          	<c:when test="${messageItem.senderMember.id eq selectedMember.id}">
		          		<%-- Coming from the client --%>
		          		<li>
				            <div class="message-data">
				              <span class="message-data-name"><i class="fa fa-circle online"></i> ${selectedMember.username}</span>
				              <span class="message-data-time"><fmt:formatDate pattern="MMMM dd, yyyy hh:mm a EEEE"  value="${messageItem.createdOn}" /></span>
				            </div>
				            <div class="message my-message">
				              ${messageItem.content}
				            </div>
				          </li>
		          	</c:when>
		          	<c:otherwise>
		          		
		          		<li class="clearfix">
				            <div class="message-data align-right">
				              <span class="message-data-time" ><fmt:formatDate pattern="MMMM dd, yyyy hh:mm a EEEE"  value="${messageItem.createdOn}" /></span> &nbsp; &nbsp;
				              <span class="message-data-name" >Administrator</span> <i class="fa fa-circle me"></i>
				              
				            </div>
				            <div class="message other-message float-right">
				              ${messageItem.content}
				            </div>
				          </li>
		          		
		          	</c:otherwise>
		          </c:choose>
          </c:forEach>
          <%-- 
          <li class="clearfix">
            <div class="message-data align-right">
              <span class="message-data-time" >10:10 AM, Today</span> &nbsp; &nbsp;
              <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
              
            </div>
            <div class="message other-message float-right">
              Hi Vincent, how are you? How is the project coming along?
            </div>
          </li>
          
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:12 AM, Today</span>
            </div>
            <div class="message my-message">
              Are we meeting today? Project has been already finished and I have results to show you.
            </div>
          </li>
           --%>
          
          <%-- 
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:31 AM, Today</span>
            </div>
            <i class="fa fa-circle online"></i>
            <i class="fa fa-circle online" style="color: #AED2A6"></i>
            <i class="fa fa-circle online" style="color:#DAE9DA"></i>
          </li>
           --%>
        </ul>
        
      </div> <!-- end chat-history -->
      
      <div class="chat-message clearfix">
        <c:if test="${selectedMember ne null}">
	        <form class="input-group" onSubmit="validateSubmitChatMessage(this);return false;">
		        <textarea name="messageContent" id="messageContent" class="messageContent" placeholder ="Type your message" rows="3"></textarea>
		        <!--         
		        <i class="fa fa-file-o"></i> &nbsp;&nbsp;&nbsp;
		        <i class="fa fa-file-image-o"></i>
		         -->
		        <button type="submit">Send</button>
	        </form>
		</c:if>
      </div> <!-- end chat-message -->
      
    </div> <!-- end chat -->
    
  </div> <!-- end container -->

		<form name="loadChatMessages" id="loadChatMessages" method="post" enctype="multipart/form-data" action="loadChatMessages.do">
			<input type="hidden" name="status" id="membermessagestatus" class="membermessagestatus" value="All" />
			<input type="hidden" name="memberId" id="memberId" class="memberId" value="" />
		</form>
		
		<form name="submitChatMessage" id="submitChatMessage" class="submitChatMessage" method="post" enctype="multipart/form-data" action="submitChatMessage.do">
			<!-- 
			<input type="hidden" name="status" id="membermessagestatus" class="membermessagestatus" value="" />
			<input type="hidden" name="remarks" id="membermessageremarks" class="membermessageremarks" value="" />
			<input type="hidden" name="messageType" id="membermessagetype" class="membermessagetype" value="" />
			 -->
			<input type="hidden" name="content" id="content" class="content" value="" />
			<input type="hidden" name="memberId" id="memberId" class="memberId" value="" />
		</form>

<script id="message-template" type="text/x-handlebars-template">
  <li class="clearfix">
    <div class="message-data align-right">
      <span class="message-data-time" >{{time}}, Today</span> &nbsp; &nbsp;
      <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
    </div>
    <div class="message other-message float-right">
      {{messageOutput}}
    </div>
  </li>
</script>

<script id="message-response-template" type="text/x-handlebars-template">
  <li>
    <div class="message-data">
      <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
      <span class="message-data-time">{{time}}, Today</span>
    </div>
    <div class="message my-message">
      {{response}}
    </div>
  </li>
</script>
     <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js'></script>

	<script src="chatresources/js/index.js"></script>

    <script>
    	var latestErrorMessage="";
		var latestErrorMessagePage="";
	
    	window.onload = function() {
    		<c:if test="${selectedMember ne null}">
    			var auto_refresh = setInterval(
    					function() {
    						loadChatMessages("${selectedMember.id}", "${GurkkaConstants.NEW}");
    					}
    					,7000);
    		</c:if>
    	}
    	
    	function loadChatMessages(strid_, status_) {
    		
    		if(status_ == "${GurkkaConstants.NEW}"){
    			$('#loadChatMessages #membermessagestatus').attr('value','${GurkkaConstants.NEW}');
    			$('#loadChatMessages #memberId').attr('value','${selectedMember.id}');
    		}
    		else{
    			$('#loadChatMessages #membermessagestatus').attr('value','All');
    			$('#loadChatMessages #memberId').attr('value','0');
    		}
    		$.ajax({
    			type: $('#loadChatMessages').attr('method'),
    			url: $('#loadChatMessages').attr('action'),
    			cache: false,
    			dataType: "json",
    			async: true,
    			data: $('#loadChatMessages').serialize(),
    			success: function(data) {
    				
    				if(data.listNewMemberMessageCount.length > 0) {
    					
    					var messageCount = data.listNewMemberMessageCount.length;
						for(var i = 0; i < messageCount; i++) {
							
							//data.listNewMemberMessageCount[i].memberid
							//data.listNewMemberMessageCount[i].newmessagecount
							if(data.listNewMemberMessageCount[i].newmessagecount != "0"){
								$('#member'+data.listNewMemberMessageCount[i].memberid).html(data.listNewMemberMessageCount[i].newmessagecount);
								$('#member'+data.listNewMemberMessageCount[i].memberid).attr("reltag",data.listNewMemberMessageCount[i].memberlastmessagedate);
							}
							if(data.listNewMemberMessageCount[i].loginstatus == "online"){
								$('#memberonlinestatus'+data.listNewMemberMessageCount[i].memberid).removeClass("offline").addClass("online");
							}
							else{
								$('#memberonlinestatus'+data.listNewMemberMessageCount[i].memberid).removeClass("online").addClass("offline");
							}
							
						}
						
    				}
    				
    				if(data.listMemberMessage.length > 0) {
    					if(data.listMemberMessage[0].errorMessage != null && data.listMemberMessage[0].errorMessage != undefined){
    						//if(!(latestErrorMessage == data.listMemberMessage[0].errorMessage) && !(latestErrorMessagePage=="${navMenu}"))
    						//{
    							$.notify(data.listMemberMessage[0].errorMessage, "error");
    							latestErrorMessage = data.listMemberMessage[0].errorMessage;
    							latestErrorMessagePage="${navMenu}";
    						//}
    					}
    					else{
    						var messageCount = data.listMemberMessage.length;
    						for(var i = 0; i < messageCount; i++) {
    							if(data.listMemberMessage[i].createdon != null && data.listMemberMessage[i].createdon != undefined){
	    							if(data.listMemberMessage[i].senderid == "${selectedMember.id}" && data.listMemberMessage[i].senderid != "") {
	    								$('.chat-conversation-wrapper').append(''
	    										+''
	    										+''
	    										+''
	    										+' <li>'
	    										+'	<div class="message-data">'
	    										+'	  <span class="message-data-name"><i class="fa fa-circle online"></i> '+data.listMemberMessage[i].senderusername+'</span>'
	    										+'	  <span class="message-data-time">'+data.listMemberMessage[i].createdon+'</span>'
	    										+'	</div>'
	    										+'	<div class="message my-message">'
	    										+'	  '+data.listMemberMessage[i].content+''
	    										+'	</div>'
	    										+'  </li>'
	    										+''
	    										+''
	    										+'');
	    							}
	    							else if(data.listMemberMessage[i].senderid != "${selectedMember.id}" && data.listMemberMessage[i].senderid != "") {
	    								$('.chat-conversation-wrapper').append(''
	    										+''
	    										+''
	    										+''
	    										+' <li class="clearfix">'
	    										+'	<div class="message-data align-right">'
	    										+'	  <span class="message-data-time" >'+data.listMemberMessage[i].createdon+'</span> &nbsp; &nbsp;'
	    										+'	  <span class="message-data-name" >Administrator</span> <i class="fa fa-circle me"></i>'	  
	    										+'	</div>'
	    										+'	<div class="message other-message float-right">'
	    										+'	  '+data.listMemberMessage[i].content+''
	    										+'	</div>'
	    										+'  </li>'
	    										+''
	    										+''
	    										+''
	    										+''
	    										+''
	    										+'');
	    							}
    							}
    							
    						}
    						//$(".nano").nanoScroller();
    						//$(".nano").nanoScroller({ scroll: 'bottom' });
    						$(".chat-history").scrollTop(function() { return this.scrollHeight; });
    					}
    				}
    			},
    			complete: function() {
    				sortDocumentElement();
    			},
    			error: function(xhr, textStatus, error){
    				if(!(latestErrorMessage == "An error was encountered while processing your action!") && !(latestErrorMessagePage=="${navMenu}"))
    				{
    					console.log(error);
    					$.notify("An error was encountered while processing your action!", "error");
    					
    					latestErrorMessage = "An error was encountered while processing your action!";
    					latestErrorMessagePage="${navMenu}";
    				}
    			}
    		});
    	}
    	
    	function submitChatMessage() {
    		$('#submitChatMessage #memberId').attr('value','${selectedMember.id}');
    		$.ajax({
    			type: $('#submitChatMessage').attr('method'),
    			url: $('#submitChatMessage').attr('action'),
    			cache: false,
    			dataType: "json",
    			async: true,
    			data: $('#submitChatMessage').serialize(),
    			success: function(data) {
    				if(data.listSubmitMemberMessage.length > 0) {
    					if(data.listSubmitMemberMessage[0].errorMessage != null){
    						//if(!(latestErrorMessage == data.listMemberMessage[0].errorMessage) && !(latestErrorMessagePage=="${navMenu}"))
    						//{
    							$.notify(data.listMemberMessage[0].errorMessage, "error");
    							latestErrorMessage = data.listMemberMessage[0].errorMessage;
    							latestErrorMessagePage="${navMenu}";
    						//}
    					}
    				}
    			},
    			complete: function() {
    				
    			},
    			error: function(xhr, textStatus, error) {
    				if(!(latestErrorMessage == "An error was encountered while processing your action!") && !(latestErrorMessagePage=="${navMenu}"))
    				{
    					console.log(error);
    					$.notify("An error was encountered while processing your action!", "error");
    					
    					latestErrorMessage = "An error was encountered while processing your action!";
    					latestErrorMessagePage="${navMenu}";
    				}
    			}
    		});
    	}
    	
    	function validateSubmitChatMessage(myForm) {
    		
    		var messages = "* The following error(s) were encountered : \n\n";
    		var error = false;
    		var content = myForm.messageContent.value;
    		
    		if(content.trim().length == 0) {
    			error = true;
    			messages = " * Message Content was not specified.";
    		}
    		if(error){
    			alert(messages);
    			return false;
    		}
    		else{
    			$('#submitChatMessage #content').val(content);
    			myForm.messageContent.value = "";
    		}
    		submitChatMessage();
    		return false;
    	}
    	
    	<%--------------------ADDED CODE FOR SORTING---------------------------- --%>
    	
    	
		function sortDocumentElement() {
			var sort_by_name = function(a, b) {
				var firstItem = a.getElementsByClassName("achat")[0];
				firstItem = firstItem.getElementsByClassName("about")[0];
				firstItem = firstItem.getElementsByClassName("status")[0];
				firstItem = firstItem.getElementsByClassName("fontchat")[0];
				var secondItem = b.getElementsByClassName("achat")[0];
				secondItem = secondItem.getElementsByClassName("about")[0];
				secondItem = secondItem.getElementsByClassName("status")[0];
				secondItem = secondItem.getElementsByClassName("fontchat")[0];
				
				return secondItem.getAttribute("reltag").toLowerCase().localeCompare(firstItem.getAttribute("reltag").toLowerCase());
			}
			var list = jQuery(".list > .clearfix").get();
			list.sort(sort_by_name);
			for (var i = 0; i < list.length; i++) {
				list[i].parentNode.appendChild(list[i]);
			}
		}
    	
    </script>
    
	</c:otherwise>
	</c:choose>
  </body>
</html>
