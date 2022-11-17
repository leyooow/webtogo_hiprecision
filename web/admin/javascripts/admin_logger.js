window.onload = logStart();
window.onbeforeunload = logEnd();
var popup;

function logStart() {
	adminOnline();
	$(function(){
		$(window).bind('focus',function(){adminOnline();});
	});
	setInterval("readerChat()", 2000);
}


function adminOnline(){
	$.post("chatadminlog.do", {
		admin_status :"online",
		staff_id :staff_id,
		status :"0",
		company :chatcompanyname
	});
}

function logEnd(){
	$.post("chatredistadmin.do", {staff_id :staff_id, status :"offline", boo:"boooo----------------------------------------------"});
}

function readerChat() {
	$.post("initialreader.do", {
		staff_id :staff_id,
		company :chatcompanyname
	}, function(data) {

		if ($.trim(data.toString()) != "")
		{
			$("#chatnavmenu").css("background-color","#b0355d");
			setTimeout('$("#chatnavmenu").css("background-color","")', 3000);
		}
	});
}

function openChatWindow() {
	popup = open(
			'chat.do',
			'Admin Chat',
			'toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=740,height=660');
	var timer = setInterval( function() {
		if (popup.closed) {
			//alert('popup closed!');
			clearInterval(timer);
			$.post('logoutchat.do');
		}
	}, 500);
}
