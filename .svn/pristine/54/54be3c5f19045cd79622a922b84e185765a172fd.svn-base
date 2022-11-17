/*
 * Refresh Checker
 * Credits:http://www.tedpavlic.com/post_detect_refresh_with_javascript.php 
 * 
 */

function checkRefresh() {
	// Get the time now and convert to UTC seconds
	var today = new Date();
	var now = today.getUTCSeconds();

	// Get the cookie
	var cookie = document.cookie;
	var cookieArray = cookie.split('; ');

	var reload_flag = 0;
	
	// Parse the cookies: get the stored time
	for ( var loop = 0; loop < cookieArray.length; loop++) {
		var nameValue = cookieArray[loop].split('=');
		// Get the cookie time stamp
		if (nameValue[0].toString() == 'SHTS') {
			var cookieTime = parseInt(nameValue[1]);
		}
		// Get the cookie page
		else if (nameValue[0].toString() == 'SHTSP') {
			var cookieName = nameValue[1];
		}
		else if (nameValue[0].toString() == 'ID') {
			//session_id = nameValue[1];
			session_id = unescape(nameValue[1]);
		}
		else if (nameValue[0].toString() == 'STAFF'){
			staff_id = unescape(nameValue[1]);
		}
		else if (nameValue[0].toString() == 'LOG') {
			log = unescape(nameValue[1]);
		}
		else if (nameValue[0].toString() == 'USERNAME') {
			chat_name = unescape(nameValue[1]);
		}
		else if (nameValue[0].toString() == 'DIALOG'){//
			dialogstatus = unescape(nameValue[1]);//
		}//
	}
	//checks if the difference in time is less than 10sec
	//if it less than 10sec it is assumed that the
	//user just refresh the page or navigated on the site
	if (cookieName && cookieTime /*&& cookieName == escape(chatcompanyname)*/
			&& Math.abs(now - cookieTime) < 10) {
		// Refresh detected
		reload_flag = 1;
		flag2 = 1;
	}
	
	return reload_flag;

	// You may want to add code in an else here special
	// for fresh page loads
}

//sets the cookies of the current state of variable needed by
//the system.
function prepareForRefresh() {
	if (refresh_prepare > 0) {
		// Turn refresh detection on so that if this
		// page gets quickly loaded, we know it's a refresh
		var today = new Date();
		var now = today.getUTCSeconds();
		var lg = "";
		// load data here
		
		$.post('chatreceiver.do', {
			u_id :session_id,
			company: chatcompanyname
		}, function(data) {
			lg = data;
			document.cookie = 'SHTS=' + now + ';';
			document.cookie = 'SHTSP=' + escape(location.href) + ';';
			document.cookie = 'ID=' + escape(session_id) + ';';
			document.cookie = 'STAFF='+escape(staff_id)+';';
			document.cookie = 'LOG=' + escape(lg) + ';';
			document.cookie = 'USERNAME=' + escape(chat_name) + ';';
			document.cookie = 'DIALOG='+escape(dialogstatus)+';';//
			//alert("wooo");
		});

	} else {
		// Refresh detection has been disabled
		document.cookie = 'SHTS=;';
		document.cookie = 'SHTSP=;';
		document.cookie = 'ID=;';
		document.cookie = 'LOG=;';
		document.cookie = 'USERNAME=;';
		document.cookie = 'DIALOG=;';//
		document.cookie = 'STAFF=;';
	}
}


var refresh_prepare = 1;
