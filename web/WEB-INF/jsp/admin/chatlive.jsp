		<!-------------------------------------------
		
						  LIVE CHAT
		
		-------------------------------------------->
		
		<input id="cb-chat" class="hidden" type="checkbox">

		<div class="chat-panel-container panel panel-primary visible-sm visible-md visible-lg">
			<div class="panel-heading">
				<span class="icon-comments2"></span> Customer Service Support Chat
				<label id="lbl-close-chat-panel-container" for="cb-chat"><span class="icon-close3"></span></label>
			</div> <!-- end of panel-heading -->
			
			<%--------------------------------------- ONLINE -------------------------------------------------------------%>
			<%--
			<div class="chat-body panel-body nano">
				<div class="chat-wrapper overthrow nano-content">
					<div id="divChatOnline" class="chat-online-wrapper">
						<div class="user-info">
							<h4 class="oswald text-center"><span class="icon-comments2"></span> Welcome To Our LiveChat!</h4>
							<p class="text-center"><img class="chat-status" src="images/online.png">Admin is now online.</p>
							
							<!-- 
							<form class="chat-form">
								<input class="form-control" type="text" placeholder="Enter your name (optional)">
								<input class="form-control" type="text" placeholder="Enter your email address">
								<input class="form-control" type="text" placeholder="Enter your contact no. (optional)">
								<button class="btn btn-info btn-sm form-control" type="submit">Submit</button>
							</form>
							-->
							
						</div>
					</div> <!-- end of chat-online-wrapper -->
					<!-- 
					<div class="chat-conversation-wrapper">
						<div class="chat-conversation">
							<div class="chat-user-wrapper">
								<img class="user-photo img-circle" src="images/annonymous.jpg">
								<p class="text-center"><small>Annonymous</small></p>
							</div> end of chat-user-wrapper
							<div class="chat-message-wrapper clearfix">
								<span class="bubble customer bg-gradient-white">
									<p>magna aliqua</p>
									<small>March 15, 2015 08:00AM Saturday</small>
								</span>
							</div> end of chat-message-wrapper
						</div> end of chat-conversation
						<div class="chat-conversation">
							<div class="chat-message-wrapper clearfix">
								<span class="bubble admin bg-gradient-green">
									<p>magna aliqua</p>
									<small>March 15, 2015 08:00AM Saturday</small>
								</span>
							</div> end of chat-message-wrapper
							<div class="chat-user-wrapper">
								<img class="user-photo img-circle" src="images/annonymous.jpg">
								<p class="text-center"><small>Administrator</small></p>
							</div> end of chat-user-wrapper									
						</div> end of chat-conversation	
					</div> end of chat-conversation-wrapper
					 -->
					
				</div> <!-- end of nano-content -->				
			</div> <!-- end of panel-body -->
			<div class="panel-footer">
				
				<form class="input-group">
					<input class="form-control" type="text" placeholder="Type your message here...">
					<span class="input-group-btn">
						<button class="btn btn-info" type="submit"><span class="icon-send"></span> Send</button>
					</span>
				</form>
				 
			</div> <!--  end of panel-footer -->
			--%>
			<%------------------------------- END OF ONLINE ---------------------------------------------------%>
		
			<%-- --------------------------------OFFLINE -------------------------------------------------------%>
			
			<div class="chat-body panel-body nano">
				<div class="chat-wrapper overthrow nano-content">
					<div id="divChatOffline" class="chat-offline-wrapper">
						<div class="user-info">
							<h4 class="oswald text-center"><span class="icon-comments2"></span> Welcome To Our LiveChat!</h4>
							<p class="text-center"><img class="chat-status" src="images/offline.png">Admin is offline.</p>
							<form class="chat-form" id="offlinechateform" onsubmit="return validateOffLineChatForm(this)">
								<input type="hidden" name="offline-message-from" value="noreply@gurkka.com" />
								<input type="hidden" name="offline-message-to" value="info@gurkka.com" />
								<input type="hidden" name="offline-message-bcc" value="ariebroncano@yahoo.com,globalnobleintlcorp@gmail.com,customerservice@gurkka.com" />
								<p><small>Dear customers, we are not available at this time, but you can leave us a message.</small></p>
								<input class="form-control" type="text" placeholder="Enter your name (optional)" id="offline-message-name" name="offline-message-name">
								<input class="form-control" type="text" placeholder="Enter your email address" id="offline-message-email" name="offline-message-email">
								<input class="form-control" type="text" placeholder="Enter your contact no. (optional)" id="offline-message-contact" name="offline-message-contact">
								<textarea class="form-control" placeholder="Type your message to us..." id="offline-message-message" name="offline-message-message"></textarea>
								<button class="btn btn-info btn-sm form-control" type="submit" id="offline-message-submit">Submit</button>									
							</form>
						</div>
					</div> <!-- end of chat-offline-wrapper -->
				</div> <!-- end of nano-content -->				
			</div> <!-- end of panel-body -->
			
			<%----------------------------------------- END OF OFFLINE ------------------------------------------------%>
			
		</div> <!-- end of livechat-container -->