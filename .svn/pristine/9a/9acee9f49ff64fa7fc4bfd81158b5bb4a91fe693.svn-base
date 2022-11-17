
<div class="chatContainer">
	<div class="chatLeft">
		<div class="chatHeader">
			${user.company.nameEditable}
		</div>
		<div class="chatText" id="chatText">
		</div>
    </div>
    
    <div class="chatRight">
		<div class="chatHeaderRight">
			<c:if test="${user.userType.value != 'Normal User'}">
				<p><a href="javascript:self.close();"><img src="../javascripts/chat/image/iSignout.jpg"  align="absmiddle"/> Close Chat Window</a></p>
				<c:if test="${user.userType.value=='Company Administrator'}">
					<p><a href="javascript:signoutresettodefault();"><img src="../javascripts/chat/image/iSignout.jpg" align="absmiddle"/> Reset and Close Window</a></p>
				</c:if>
			</c:if>
			<p><a href="javascript:viewLog('${user.id}-${user.firstname} ${user.lastname}')"><img src="../javascripts/chat/image/iLog.gif"  align="absmiddle"/> View My Logs</a></p>
		</div>
    	<div class="chatOnlineUsers">        	
        	<h1>List of Viewable User Log</h1>
        	<ul id="chatemployees">
        		<c:if test="${user.userType.value =='Company Staff' or user.userType.value=='Normal User'}">
	<!--			<h5 class='log'  >${user.firstname} ${user.lastname}</h5>-->
					<li onclick="viewLog('${user.id}-${user.firstname} ${user.lastname}');" style="cursor:pointer;" title="${user.userType}">
						<img src="../javascripts/chat/image/iChat.gif" align="absmiddle"/>
						${user.firstname} ${user.lastname}
						<img src="../javascripts/chat/image/iLog.gif" align="absmiddle"/>
					</li>
				</c:if>
				<c:if test="${user.userType.value =='Company Administrator'}">
					<script type='text/javascript' >
						loadUserTypes();
					</script>
				</c:if>
            </ul>
        </div>
    </div>
    
    <div class="clear"></div>
    <div class="chatTextarea">
    	<div style="float: right; margin: 0 5px 0 0;">
        	<input type="button" value="SEND" class="chatSend" onclick="javascript:send_reply();"/>
        </div>
        	<textarea id="chatmessage">
            </textarea>
            <script type='text/javascript'>
			$("#chatmessage").keyup(function(e) {
				if(e.keyCode == 13 && e.shiftKey == false)
					send_reply();
			});
		</script>
            <br />
            Clients:
    </div>
	<div class="chatTabs" style="overflow-y:auto">
		<ul id="chatclientlist"></ul>
	</div>
        
        <div class="clear"></div>
</div>
<div id="chatsoundelement"></div>
<div class="chatFooter" align="right">
	<c:if test="${user.userType.value == 'Company Administrator'}">
		<a style="font-size:3px;color:#143476;cursor:pointer;" onclick="javascript:$.post('chatreset.do');">reset all</a>
	</c:if>
	Powered by <a href="http://www.webtogo.com.ph/" target="_blank">WebToGo Philppines</a>
</div>