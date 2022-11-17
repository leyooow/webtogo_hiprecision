<%@include file="includes/header.jsp"  %>
<link href="css/loginCss.css" rel="stylesheet" type="text/css">

<c:set var="menu" value="forgotpassword" />

<body onload="document.getElementById('username').focus()">

	<div class="top"></div>

	<div class="container">

			<div align="center">
			<c:choose>
	<c:when test="${company != null && company.logo != null}">
		<img src="${contextParams['IMAGE_PATH']}/images/${company.logo}" width="229" height="119" />
	</c:when> 
	<c:otherwise> 
		<img src="http://www.webtogo.com.ph/admin/images/webtogoLOGO.jpg" /> 
	</c:otherwise> 
	
</c:choose><br /></div>
        <p class="text">Login to access the program management and administration tools.</p>
		
		<form name="login" action="login.do" method="post">
		
				<input type="hidden" name="companyId" value="152" /> 
	
  		<input type="hidden" name="login" value="1" />
        <table class="tblLogin">

        	<tr>

            	<td>Username</td>

                <td> <input type="text" name="username" id="username" class="w200"/></td>

            </tr>

            <tr>

            	<td>Password</td>

                <td><input type="password" name="password" id="Password*" class="w200"/></td>

            </tr>
			<tr>
				<td colspan="2">
				<div id="errorMessage" style="color: red; ">
					<s:actionerror/>
					
				</div>
		</td>
			</tr>

            <tr>
				
            	<td colspan="2" class="right"><input type="submit" value="Login" class="btnBlue"/></td>
				
            </tr>
			<tr><td colspan="2" class="right"></td></tr>
          

        </table>

    </div>
	</form>
    <div class="bottom"></div>



</body>

</html>
