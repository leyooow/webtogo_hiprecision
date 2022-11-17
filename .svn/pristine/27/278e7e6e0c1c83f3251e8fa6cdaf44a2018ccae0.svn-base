
/**
 * session_id - the client's unique id
 * staff_id - the client's assigned admin's id
 * chat_name - the client's set name/alias. can be left blank
 * flag - indicates if the system will update the chatlog.
 * 		values are only 1 or 0.
 * flag2 - indicator if the client has been assigned to an admin.
 * 		values are only 1 or 0.
 * chat_log - contains the chat log of the user. it is used
 * 		to check if there are any new messages.
 * chat_timer - is set every 3000 ms with respect to the system's clock.
 * 		indicates the time in minutes of when were new messages.
 * chat_hours - is like the chat_timer except it is in hours.
 * reload - a flag that determines if the user just refreshed the page
 * 		or not. please see check_refresh.js
 * admin_status - indicates whether there is an admin online or none.
 * 		values are only 1 or 0
 * dialog_status - indicates whether the dialog is currently open or not.
 * 		values are only "open" or "close"
 * checkadmininterval - the id returned by the setInterval(). used for
 * 		clearing the interval checkadmin.
 * updateconversation - used for clearing the interval updateconversation
 *  
 */
var session_id = "";
var staff_id = "";
var chat_name = "";
var flag = 0;
var flag2 = 0;
var chat_log = "";//
var chat_timer = 0;
var chat_hours = 0;
var reload = 0;
var admin_status = 0;
var dialogstatus = "";//
var checkadmininterval;
var updateconversationinterval;

window.onload = loadPage;

/**
 * loadPage() first checks if the user just reloaded the page or not.
 * See check_refresh.js
 * From the values obtain on the check_refresh, the system checks if
 * the user was assigned to an admin or not.
 * 
 * When the reload flag is 0, the chat is shown in default and the
 * system will assign a unique id to the client. Otherwise, restores
 * the chat log, updates the chat log every 3000ms, and checks if
 * the chat box is previously opened or not.
 */
function loadPage() {
	/*if(typeof(onclick)!= "function")
		document.getElementById('chatTitle').setAttribute('onclick', 'toggleChatGrowth();');
	else
		document.getElementById('chatTitle').onclick = function(){toggleChatGrowth();};*/
	reload = checkRefresh(); 
	checkAdmin(); 
	prepareForRefresh();
	checkadmininterval = setInterval("checkAdmin()", 6000);

	if (reload == 0) {
		$.post("chatsetsessionid.do", function(data) {
			session_id = $.trim(data.toString());
			});
	} else {
		$.post("chatwriteall.do", {
			name :session_id,
			message :log
		});

		flag = 1;
		updateconversationinterval = setInterval("updateConversation()", 5000);
		$("#client_name").css('display', 'none');
		if (dialogstatus == "open")
			$('#chatdialog').dialog('open');
		else if (dialogstatus == "close")
			$('#chatdialog').dialog('close');
	}
}

window.onbeforeunload = unloadPage; // firefox
//$(window).unload(function () {unloadPage();});

if(window.opera)
{
	window.opera.setOverrideHistoryNavigationMode('compatible');
	window.onunload = unloadPage();
}

/**
 * clears all interval deletes the client's chat log.
 */
function unloadPage() {
	clearInterval(updateconversationinterval);
	clearInterval(checkadmininterval);
	$.post('chatdeletelog.do', {
		u_id :session_id
	});
	prepareForRefresh();
}

/**
 * checks if there are any currently online admins and assigns
 * one of them to the client. chatadminlog.do returns the id
 * of the admin that is assigned to the client. If there are no
 * currently online admin, it returns 0.
 */
function checkAdmin() {
	flag2 = staff_id == "" ? 0 : 1; 
	var chat_status = "";
	$.post("chatadminlog.do",
	{
		admin_status :"user_side",
		user_id :session_id,//
		staff_id :staff_id,
		status :flag2,
		new_user : flag2
	},
	function(data) {
		adminStatus = $.trim(data.toString());
		if (adminStatus == "0") {
			chat_status = "<b class='chatoffline' >chat: offline</b>";
			staff_id = "";
			flag2 = 0;
			$("#chatTitle").attr("href", "contact.do");
			dialogstatus = "close";
			admin_status = 0;
			$("#chatdialog").css('display', 'none');
			$("#chatdialog").dialog('option', 'title',
					"<div class=\"chatHead\" title='Minimize Chat'>"
					+"&nbsp;&nbsp;Chat: OFFLINE</div>"
					+"<img src=\"../../javascripts/chat/image/iClose3.gif\""
						+"border=\"0\""
						+"onclick=\"javascript:toggleChatGrowth()\""
						+"style=\"cursor:pointer;position:relative;right:-175px;\""
						+"title=\"Hide Chat\"/>");
		} else {
			chat_status = "<b class='chatonline' >chat: online</b>";
			admin_status = 1;
			staff_id = adminStatus;
			flag2 = staff_id == "" ? 0 : 1;
			$("#chatTitle").attr("href", "#");
			$("#chatdialog").dialog('option','title',
					"<div class=\"chatHead\" title='Minimize Chat' onclick=\"javascript:toggle();\">"
					+"&nbsp;&nbsp;Chat: ONLINE</div>"
					+"<img src=\"../../javascripts/chat/image/iClose3.gif\""
						+"border=\"0\""
						+"onclick=\"javascript:toggleChatGrowth()\""
						+"style=\"cursor:pointer;position:relative;right:-175px;\""
						+"title=\"Hide Chat\"/>");
		}
		$("#chatTitle").html(chat_status);//
	});
}

/**
 * toggle() is called everytime when the user wants to 
 * hide the chat dialog.
 */
function toggle() {
	if ($("#chatdialog").css('display') == 'none')
		$("#chatdialog").css('display', 'block');
	else
		$("#chatdialog").css('display', 'none');
}

/**
 * Updates the chat log viewed on the interface.
 * First it will check clear the interval for checkAdmin() to
 * avoid duplicate request for checkAdmin(). It then receives
 * the chat log of the user. If it receives 0, it is assumed
 * that the client is a new user and sets a new session id.
 */
function updateConversation() {
	clearInterval(checkadmininterval);
	checkAdmin();
	$.post('chatreceiver.do',
	{
		u_id :session_id
	},
	function(data) {
		if ($.trim(data.toString()) == "0") {
			$.post("chatsetsessionid.do",
					function(data) {
						session_id = $.trim(data.toString());
						$("#conversation").html("<p class='message'><em>*Conversation has been reset.</em></p>");
					});
				
		} else {
			$("#conversation").empty().html(data);
			checkAdminReplied(data);
		}
	});
	$("#conversation").scrollTop($("#conversation")[0].scrollHeight);
	prepareForRefresh();
}

/**
 * appendChat() is called whenever the client sends a 
 * message.
 */
function appendChat() {
	
	var cht = document.getElementById('conversation');
	var message = document.getElementById('chatmsg').value;

	var d = getTimeStamp();

	var nd = document.createElement('div');
	nd.setAttribute('class', 'chat_item');

	var sender = document.createElement('p');
	sender.setAttribute('class', 'sender');
	var time = document.createElement('h6');

	message = message.replace(/<.*(\s*\W*)*\>/, "");
	d = d.toString();

	if (chat_name == "")
		username = session_id;
	else
		username = chat_name;

	sender.innerHTML = username + ": " + message.toString();
	time.innerHTML = d;

	nd.appendChild(sender);
	nd.appendChild(time);

	cht.appendChild(nd);
	document.getElementById('chatmsg').value = "";

	cht.scrollTop = cht.scrollHeight;

	$.post("chatsenduser.do", {
		name :session_id,
		message :message,
		date :d,
		chat_name :chat_name,
		staff_id :staff_id
	});
	//

	// initializes the update of conversation and updates the current
	// conversation between the user and the admin of the system
	if (flag == 0) {
		updateconversationinterval = setInterval("updateConversation()", 5000);
		flag = 1;
	}
	prepareForRefresh();
}

/**
 * toggleChatGrowth() is responsible if the user opens
 * or closes the chat window.
 */
function toggleChatGrowth() {
	if ($('#chatdialog').dialog('isOpen')) {
		$('#chatdialog').dialog('close');
		dialogstatus = "close";
	} else {
		$('#chatdialog').dialog('open');
		dialogstatus = "open";
	}
}

/**
 * Responsible for opening or closing of the chat window.
 * @param xPos - horizontal position of the user click event.
 * @param yPos - vertical position of the user click event.
 */
function showDialog(xPos, yPos){
	if(admin_status == 1) {
		if ($('#chatdialog').dialog('isOpen'))
			$('#chatdialog').dialog('close');
		else {
			$('#chatdialog').dialog('open');
			$('#chatdialog').dialog('option','position',[xPos, yPos]);
		}
	}
}

/**
 * Formats the time to mm-dd-yy hr:mn:sc
 * @returns {String}
 */
function getTimeStamp() {
	var d = new Date();

	return ((d.getMonth().toString().length == 1) ? '0' + d.getMonth()
			.toString() : d.getMonth().toString())
			+ '-'
			+ ((d.getDate().toString().length == 1) ? '0' + d.getDate()
					.toString() : d.getDate().toString())
			+ '-'
			+ ((d.getFullYear().toString().length == 1) ? '0' + d.getFullYear()
					.toString() : d.getFullYear().toString())
			+ ' '
			+ ((d.getHours().toString().length == 1) ? '0' + d.getHours()
					.toString() : d.getHours().toString())
			+ ':'
			+ ((d.getMinutes().toString().length == 1) ? '0' + d.getMinutes()
					.toString() : d.getMinutes().toString())
			+ ':'
			+ ((d.getSeconds().toString().length == 1) ? '0' + d.getSeconds()
					.toString() : d.getSeconds().toString());
}

/**
 * Sets the user alias/nickname
 */
function setNickName() {
	chat_name = document.getElementById('nick_name').value;
	$("#client_name").css('display', 'none');
	$("#conversation").css('display', 'block');
	$("#message_info").css('display', 'block');
}

/**
 * This checks for new messages. If there is a new message,
 * it gets the time of the last message. Otherwise, checks 
 * the time when there was a new message. If the time of 
 * the last message exceeds 10 mins, the chat system suggests
 * that the client may use the ContactUs form of the site.
 * @param data
 */
function checkAdminReplied(data) {
	if ($.trim(data.toString()) != chat_log || chat_log == "") {
		chat_log = $.trim(data.toString());
		var today = new Date();
		chat_timer = today.getUTCMinutes();
		chat_hours = today.getUTCHours();
	} else {
		var now = new Date();
		if (Math.abs(chat_timer - now.getUTCMinutes()) > 10)
			if(Math.abs(chat_hours - now.getUTCHours())!=1)
				$("#conversation").empty().html(data + "<center><p class='message'>10 minutes has passed,<br>you may use the<br>'<a href='contact.do'>Contact Us Form</a>'</p></center>");
	}
}
