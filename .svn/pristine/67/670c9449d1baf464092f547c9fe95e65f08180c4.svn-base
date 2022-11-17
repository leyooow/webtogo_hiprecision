window.onload = logStart();
window.onbeforeunload = logEnd();
var popup;
var isFocused = false;

function logStart() {
	adminOnline();
	$(function(){
		$(window).focus(function(){
			if(isFocused)
				return
			adminOnline();
			isFocused = true;
		});
		$(window).blur(function(){
			isFocused = false;
		});
	});
	setInterval("readerChat()", 5000);
	prepareForRefresh();
}


function adminOnline(){
	$.post("chatadminlog.do", {
		admin_status :"online",
		staff_id :staff_id+'-'+chatuser,
		status :"0",
		company :chatcompanyname
	});
}

function logEnd(){
	//$.post("chatredistadmin.do", {staff_id :+'-'+chatuser, status :"offline", boo:"boooo----------------------------------------------"});
}

function readerChat() {
	$.post("initialreader.do", {
		staff_id :staff_id+"-"+chatuser,
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
	popup = window.open(
			'chat.do',
			'',
			'width=740,'
			+'height=660');
	var timer = setInterval( function() {
		if (popup.closed) {
			//alert('popup closed!');
			clearInterval(timer);
			//$.post('chatredistadmin.do');
		}
	}, 500);
}
