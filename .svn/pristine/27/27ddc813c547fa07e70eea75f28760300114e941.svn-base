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
		} else if (nameValue[0].toString() == 'STAFFID') {
			staff_id = nameValue[1];
		} else if (nameValue[0].toString() == 'LIST') {
			tmp = unescape(nameValue[1]);
			temp_online = tmp.split("/");
		}
	}
	//checks if the difference in time is less than 10sec
	//if it less than 10sec it is assumed that the
	//user just refresh the page or navigated on the site
	if (cookieName && cookieTime /*&& cookieName == escape(chatcompanyname)*/
			&& Math.abs(now - cookieTime) < 10) {
		// Refresh detected
		reload_flag = 1;
	}

	// You may want to add code in an else here special
	// for fresh page loads
	
	return reload_flag;
}

//sets the cookies of the current state of variable needed by
//the system.
function prepareForRefresh() {
	if (refresh_prepare > 0) {
		// Turn refresh detection on so that if this
		// page gets quickly loaded, we know it's a refresh
		var today = new Date();
		var now = today.getUTCSeconds();
		var list = "";
		var keys = new Array();
		// load data here

		for ( var i = 0 ; i < current_online_clients.length ; i++) {
			keys.push(current_online_clients[i]);
		}
		
		list = keys.join("/");
		
		// load end
		
		document.cookie = 'SHTS=' + now + ';';
		document.cookie = 'SHTSP=' + escape(location.href) + ';';
		document.cookie = 'STAFID=' + escape(staff_id) + ';';
		document.cookie = 'LIST=' + escape(list) + ';';

	} else {
		// Refresh detection has been disabled
		document.cookie = 'SHTS=;';
		document.cookie = 'SHTSP=;';
		document.cookie = 'STAFFID=;';
		document.cookie = 'LIST=;';

	}
}

var refresh_prepare = 1;
