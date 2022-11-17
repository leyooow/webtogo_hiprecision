<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Hi-Precision Diagnostics Login</title>
<link href="css/css.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="document.getElementById('username').focus()">
  <div class="loginContainer">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td class="roundedCorner"><img src="images/a.gif" /></td>
		<td class="topShadow"></td>
	    <td class="roundedCorner"><img src="images/a2.gif" /></td>
	  </tr><tr valign="top">
	    <td><img src="images/b3.jpg" /></td>
		<td class="loginWrapper">
		  <h1><img src="images/hi-precision.jpg" alt="Hi-Precision Diagnostics" title="Hi-Precision Diagnostics" /></h1>
		  <div class="loginForm">
		    <h2><img src="images/iLogin.jpg" align="absmiddle" />Call Center Database Search Program.</h2>
			<table width="100%" border="0" cellspacing="0" cellpadding="10">
			  <tr valign="top">
			    <td><img src="images/iLogin2.jpg" /></td>
				<td>
				<form action="login.do" method="POST">
		          <div id="errorMessage">
					<s:actionerror/>
					<s:actionmessage/>
					<font color="red">${param.notificationMessage}</font>
				  </div>
				  <table border="0" cellspacing="0" cellpadding="5" align="center">
				    <tr>
					  <td>Username:</td>
					  <td><input name="username" id="username" key="username" type="text" class="inputField" /></td>
					</tr><tr>
					  <td>Password:</td>
					  <td><input name="password" id="password" key="password" type="password" class="inputField" /></td>
					</tr><tr>
					  <td colspan="2" align="right"><input name="submit" type="submit" value="login" class="loginButton" /></td>
					</tr>
				  </table>
				  </form>
				</td>
			  </tr>
			</table>
		  </div><!--//loginForm-->
		</td>
		<td><img src="images/b4.jpg" /></td>
	  </tr><tr>
	    <td class="roundedCorner"><img src="images/a4.gif" /></td>
		<td class="bottomShadow"></td>
	    <td class="roundedCorner"><img src="images/a3.gif" /></td>
	  </tr><tr>
	  </tr>
	</table>
  </div><!--loginContainer-->
</body>
</html>