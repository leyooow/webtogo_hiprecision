<c:if test="${company.companySettings.hasClientChat == true}">
<a id='chatTitle' class='chatTitle' style="cursor:pointer">chat</a>
<script type='text/javascript'>
	$(".chatTitle").click(function(e){showDialog(e.pageX, e.pageY);$("#nick_name").focus();});
</script>
<div id="chatdialog">
	<div class="chatBox" id="conversation">
	</div>
	<div id='client_name'>
		<input type='text' id='nick_name' class='inputName' placeholder='Your Name (Optional)'/>
		<script type='text/javascript'>
			$("#nick_name").keypress(function(e) {if(e.keyCode==13&&e.shiftKey==false){setNickName();}});
		</script>
	</div>
	<div class="chatTextarea2" id="message_info">
		<textarea name="message" id="chatmsg" maxlength="350" placeholder="Your Message"></textarea>
		<script type='text/javascript'>
		$("#chatmsg").keyup(function(e) {
			if(e.keyCode==13&&e.shiftKey==false&&document.getElementById('chatmsg').value.length>0)
				{appendChat();}
		});
		</script>
		<input type="button" value="SEND" class="btnSendGray" onclick="javascript:appendChat();"/>
	</div>
</div>
<div id="chatsoundelement"></div>
</c:if>