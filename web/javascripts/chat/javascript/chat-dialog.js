$(function(){
	$('#chatdialog').dialog({
		
		autoOpen: false,
		closeOnEscape: false,
		//height: 350,
		open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
		title: "<div class=\"chatHead\" title='Minimize Chat' onclick=\"javascript:toggle();\">"
			+"&nbsp;&nbsp;Chat: ......</div>"
			+"<img src=\"../../common/chat/image/iClose3.gif\""
				+"border=\"0\""
				+"onclick=\"javascript:toggleChatGrowth()\""
				+"style=\"cursor:pointer;position:relative;right:-175px;\""
				+"title=\"Hide Chat\"/>",
		width: 265
	});
});
/**
* Initializes the chat window.
*/