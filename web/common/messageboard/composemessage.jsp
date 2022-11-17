<!-- HEADER HERE -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>
<body>
<script language="JavaScript" src="../javascripts/overlib.js">

function showMessage() {
	overlib("try", STICKY, NOCLOSE)
	}

</script>

<script type="text/javascript">
<!--
	function isEmpty(string){
		var empty = (string).replace(/\s+/g,'');
		return (empty == null || empty == '')
	}

	function constructMessage(){  
		var f = document.getElementById("messageform");  
		if(!validateForm(f)){
			return;
		}
		f.action = "postmessage.do";
		f.submit();  
	}
	
	/*Checking here*/
	function validateForm(f) { 
		var auth = f.authorName;
		if(auth.value == null || isEmpty(auth.value)){
			alert("Please input your name.");
			auth.focus();  
			return false;  
		}

		var authEmail = f.authorEmail;
		if(authEmail.value == null || isEmpty(authEmail.value)){
			alert("Please input your email."); 
			authEmail.focus();
			return false;
		}

		var title = f.messageTitle;
		if(title.value == null || isEmpty(title.value)){
			alert("Please input the subject of your message."); 
			title.focus();
			return false;
		} 

		var attachment = f.upload;
		if(attachment.value != null && !isEmpty(attachment.value)){
			 var s = attachment.value.split(".")
			 var ext = s[(s.length)-1];
			 if(!(ext == "gif" || ext == "jpg" || ext == "jpeg")){
				alert("Only image files with gif, jpeg, or jpg extensions may be sent as attachment.");
				return false;
			 }
		}
		
		var messageContent = FCKeditorAPI.GetInstance("message").GetHTML();
		if(messageContent == null || isEmpty(messageContent)){
			return confirm("Are you sure you want to send an empty message?");
		}

		return true;
	}

//-->
</script>
	<div>
	<br/><h2>Compose a new message:</h2><br/>
	<form id="messageform" name="msg" method="post" enctype="multipart/form-data">    
		<input type="hidden" name="successUrl" value="${httpServer}/messageboard.do" /> 
		<div style="line-height:24px">
		
	<table border ='0'>	
	
		<tr>
			<!-- Author Name -->
			<c:choose>		
				<c:when test="${authorName != null}">
				<td>
					* Your Name: 
				</td>
				<td>
					<input type="text" name="authorName" style="width: 282px;" value="${authorName}"/>
				</td>
				</c:when>
				<c:otherwise>
				<td>
					* Your Name: 
				</td>
				<td>
					<input type="text" name="authorName" style="width: 282px;"/>
				</td>
				</c:otherwise>
			</c:choose>
		</tr>
		
		<tr>
			<!-- Author Email -->
			<c:choose>		
				<c:when test="${authorEmail != null}">
				<td>
					* Your Email: 
				</td>
				<td>
					<input type="text" name="authorEmail" style="width: 282px;" value="${authorEmail}"/>
				</td>
				</c:when>
				<c:otherwise>
				<td>
					* Your Email:
				</td>
				<td>
					<input type="text" name="authorEmail" style="width: 282px;"/>	
				</td>
				</c:otherwise>
			</c:choose>
		</tr>
		
	<tr>		
		<!-- Subject -->
		<c:choose>		
			<c:when test="${messageTitle != null}">
			<td>
				* Subject:
			</td>
			<td>
				<input type="text" name="messageTitle" style="width: 282px;" value="${messageTitle}"/>
			</td>
			</c:when>
			<c:otherwise>
			<td>
				* Subject:
			</td>
			<td>
				<input type="text" name="messageTitle" style="width: 282px;"/>
			</td>
			</c:otherwise>
		</c:choose>
	</tr>
	
	<tr>	
		<!-- Recipient Email -->
		<c:choose>		
			<c:when test="${recipientEmail != null}">
				<td>
					Recipient Email:
				</td>
				<td>
					<input type="text" name="recipientEmail" style="width: 282px;" value="${recipientEmail}"/>
				</td>
			</c:when>
			<c:otherwise>
				<td>
					Recipient Email: 
				</td>
				<td>
					<input type="text" name="recipientEmail" style="width: 282px;"/>
				</td>
			</c:otherwise>
		</c:choose>
	</tr>
		
	<tr>	
			<td>
			</td>
			<td>
				<div style="padding-left:0px;width:400px;color:#6d4e0b">(if you want us to contact this person to view your message)</span>
			</td>
	</tr>
		
	
	<tr>
	
		<td>
			Attach Image:
		</td>	
		<td>
			<input id="attachment" type="file" name="upload" size="30" /> <html:textarea cols="20" rows="30" title="Click on Browse to locate the image or picture on your computer, then click Open.  Picture will upload to our Message Board, once you click Post Message below."><img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage();" alt="Instructions" title="Click on Browse to locate the image or picture on your computer, then click Open.  Picture will upload to our Message Board, once you click Post Message below."><span style="color:#666666">Note</span></html:textarea>
		</td>
			</div>
	</tr>
		
	<tr>	
		<td>
		</td>
		<td>
			<div style="padding-left:0px;width:400px;color:#6d4e0b">
			Image should only be in JPEG or GIF format, images size should be less than 1 MB
			</div>
		</td>
	</tr>	
</table>		
		
		<!-- Message -->
		<c:choose>		
			<c:when test="${message != null}">
				Message Text:
 				<FCK:editor id="message"  basePath="../../FCKeditor/" width="100%" height="300" toolbarSet="Custom">
 					${message}
 				</FCK:editor> 				
			</c:when>
			<c:otherwise>
				Message Text:
 				<FCK:editor id="message"  basePath="../../FCKeditor/" width="100%" height="300" toolbarSet="Custom"> 					
 				</FCK:editor> 				
			</c:otherwise>
		</c:choose>
		
		
 		<input id="sendPostEmail" type="button" name="sendPostEmail" value="Post Message" onclick="constructMessage();"/> 
 		<div style="padding-left:5px;width:500px;color:#6d4e0b">Disclaimer: By pressing Post Message, sender agrees that this website has the right to delete any message or uploaded picture or image that the site deems to be vulgar, indecent, hateful or inappropriate.</div>
		<br/><br/>
	</form>
 	</div>
</body>
