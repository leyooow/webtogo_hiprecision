<%@include file="includes/header.jsp"  %>
<link href="css/loginCss.css" rel="stylesheet" type="text/css">


<body onload="document.getElementById('username').focus();">

	<div class="top"></div>

	<div class="container">

    
		
        <p class="text">Forgot your Password</p>

			<s:actionerror/>			
		
		<form method="post" action="forgotPassword.do">
				
				<input type="hidden" name="submitted" value="true" />
				<input type="hidden" name="companyId" value="${company.id}" />
				  <table class="tblLogin">
				  <tr><td>
				Enter your email address: 
				 </td><td>
				
				<input type="text" name="email" value="" style="width: 220px; padding: 3px;"/>
				</td></tr>
				
				<tr><td colspan="2">
				<input type="submit" value="Submit" class="btnBlue" />  
				</td></tr>
				</table>
				</form>
    </div>
    <div class="bottom"></div>



</body>

</html>
