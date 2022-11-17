/**
 * current_online_clients - an array of currently online client assigned
 * 		to the admin.
 * temp_online - a temporary array containing the list of current clients.
 * current_client - the admin's currently conversing with.
 * reload - a flag if the admin just refreshed the page.
 */
var current_online_clients = new Array();
var temp_online = new Array();
var current_client = "";
var reload = 0;

window.onload = loadPage();

function loadPage() {
	reload = checkRefresh();
	prepareForRefresh();
	setInterval("readChatQueue()", 3000);
	setInterval("updateConversation()", 3000);
	
	$.post("chatadminlog.do", {admin_status :"online", staff_id :staff_id, status :"0"});

	if (reload == 1) {
		for ( var i = 0; i < temp_online.length; i++) {

			var temp2 = new Array();
			temp2 = $.trim(temp_online[i]).split("=");

			if ($.trim(temp_online[i]) != "") {
				current_online_clients[$.trim(temp_online[i])] = $.trim(temp_online[i]);
				addnew(temp2[0], temp2[1]);
			}
		}
	}
}

$(window).unload( function() {
	prepareForRefresh();
	$.post("chatredistadmin.do", {staff_id :staff_id, status :"offline", boo:"boooo----------------------------------------------"});
});

/**
 * Updates the current chat log of the admin with the current client.
 * If the receiver returns 0 it means that the user is offline.
 */
function updateConversation() {
	var cht = document.getElementById('chatText');
	if (current_client != "") {
		$.post("chatreceiver.do",
		{
			u_id :current_client,
			company :chatcompanyname
		},
		function(data) {
			if ($.trim(data.toString()) != "0")
				$("#chatText").html(data);
			else
				$("#chatText").html("<p class='message'><em>*The user is now offline.</em></p>");
			cht.scrollTop = cht.scrollHeight;
		});
	}
}

/**
 * Reads the chat queue of the admin. If there are any new clients the system
 * adds them to the array of currently online clients.
 */
function readChatQueue() {
	// try implementing ng hindi na binubura ung laman ng chat_queueu baka kasi
	// maclose ng admin ung chat ng hindi pa nya nababasa ung message, mahirap
	// backtrack ung message

	$.post("chatqueuereader.do",{staff_id :staff_id,},
	function(data) {
		var temp = new Array();
		temp = data.split("||");

		for (i = 0; i < temp.length - 1; i++) {
			var temp2 = new Array();
			temp2 = $.trim(temp[i]).split("=");
			if (typeof (current_online_clients[$.trim(temp[i])]) == "undefined") {
				current_online_clients[$.trim(temp[i])] = $.trim(temp[i]);
				addnew(temp2[0], temp2[1]);
				// alert(current_online_clients[temp[i]]);
				// single update, try reload everytime
			} else {
				if(current_client != $.trim(temp2[0]))
					$("#" + $.trim(temp2[0])).removeAttr('class');
			}
		}
	});
}

/**
 * addnew() adds the new client to the view of current users of the admin
 * @param id - the id of the client
 * @param name - the alies/nickname of the client
 */
function addnew(id, name) {
	ch_list = document.getElementById('chatclientlist');
	var nd = document.createElement('li');
	var str = $.trim(name);

	if ($.trim(name) == "")
		name = id;

	nd.setAttribute('id', $.trim(id));
	nd.setAttribute('title', $.trim(name));
	nd.innerHTML ="<a href='#' onclick=\"set_conversation('"
			+ $.trim(id) 
			+ "',this) \"/>"
			+ "<img src='../common/chat/image/iChat.gif' align=\"absmiddle\"/>"
			+ $.trim(name) 
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>"
			+ "<img src='../common/chat/image/iClose.gif' onclick='remove(this)' title='"
			+ $.trim(id) + "=" + str
			+ "' style='cursor:pointer;'align=\"absmiddle\"/>";

	//ch_list.insertBefore(nd, ch_list.firstChild);
	ch_list.insertBefore(nd, null);
}

// sends the reply of the admin to the specified user
function send_reply() {
	var cht = document.getElementById('chatText');
	var message = document.getElementById('chatmessage').value;
	var d = getTimeStamp();
	var nd = document.createElement('div');
	nd.setAttribute('class', 'chat_item');

	var sender = document.createElement('p');
	sender.setAttribute('class', 'server');
	var time = document.createElement('h6');

	sender.innerHTML = "<em class='server'>" + chatusername + ":</em> "
			+ message;
	time.innerHTML = d;

	nd.appendChild(sender);
	nd.appendChild(time);

	// try to include an inside function in the post..this will hadle error
	// checking...
	$.post("chatsendadmin.do", {
		user_name :chatusername,
		user_id :current_client,
		message :message,
		date :d,
		staff_id:staff_id
	});
	cht.appendChild(nd);
	cht.scrollTop = cht.scrollHeight;
	document.getElementById('chatmessage').value = "";
	document.getElementById('chatmessage').focus();

}

// sets the current user chatting
function set_conversation(id,handler) {
	movetotop(id, handler);
	$("#" + $.trim(current_client)).attr('class','clicked');
	current_client = id;
	document.getElementById('chatmessage').value = "";
	$("#" + $.trim(id)).attr('class','active');
	$("#chatmessage").focus();
	updateConversation();
}

//move the current user to the top of the view.
function movetotop(id,handler){
	var ch_list = document.getElementById('chatclientlist');
	var user = handler.title;
	handler = handler.parentNode;
	ch_list.removeChild(handler);
	ch_list.insertBefore(handler, ch_list.firstChild);
}

// removes an item in the chat list
function remove(handler) {
	var ch_list = document.getElementById('chatclientlist');
	var user = handler.title;
	handler = handler.parentNode;

	delete (current_online_clients[user]);
	var userValue = user.split('=');
	alert(current_client+":::::::"+userValue[0]);
	if (current_client == userValue[0]) {
		current_client = "";
		$('#chatText').html("");
	}
	ch_list.removeChild(handler);
}

//views the log of the staff the admin wants to see
function viewLog(id) {
	$("#" + $.trim(current_client)).attr('class','clicked');
	
	current_client = "";
	$.post("viewstafflog.do", {staff_id :id}, function(data) {
		document.getElementById('chatText').innerHTML=data;
		
	});
}

//Formatted time.
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
function signoutresettodefault(){
	$.post("logoutchat.do");
	window.close();
}
//loads all the staff of the currentcompany
function loadUserTypes(){
	$.post("getallusers.do",
		function(data){
		var users = $.trim(data).split(",");
		for(i=0;i<users.length;i++){
			var name = users[i].split("=");
			if(chatuser != name[1]){
			var user = document.createElement("li");
			user.setAttribute("onclick","viewLog('"+name[0]+"')");
			user.setAttribute("title", name[2]);
			user.setAttribute("style","cursor:pointer;");
			//if(name[3] == "true")
			//{	
				user.innerHTML = "<img src=\"../common/chat/image/iChat.gif\" align=\"absmiddle\">" 
					+ name[1]
					+ "&nbsp;&nbsp;"
					+ "<img src=\"../common/chat/image/iLog.gif\" align=\"absmiddle\"/>";
		//	}
		//	else
		//	{
		//		user.innerHTML = "<img src=\"../common/chat/image/iChatOff.gif\" align=\"absmiddle\">"
		//			+ name[1]
		//			+ "&nbsp;&nbsp;"
		//			+ "<img src=\"../common/chat/image/iLog.gif\" align=\"absmiddle\"/>";
		//	}
				
			var contain = document.getElementById("chatemployees");
			//contain.insertBefore(user, contain.firstChild);
			contain.insertBefore(user, null);
			}
		}
	});
}



/*function adminClientWrite(){
	var listofclients = document.getElementById('chatclientlist').childNodes;
	var clients = "";
	for(i=0;i<listofclients.length;i++)
	{
		if(typeof(listofclients[i].id)!="undefined")
			clients = clients + listofclients[i].id + "|";
	}
	$.post("chatadminclient.do",{clients:clients,staff_id:staff_id});
}*/