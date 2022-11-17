<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http:‎//www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>webtogo - Admin</title>
	
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>
	<%@taglib uri="/struts-tags" prefix="s" %>

	
</head>
<link href="admin/css/login.css" rel="stylesheet" type="text/css" />

<body>


	<div class="green-top"> 
      
             
</div>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
</p>

<div>

<div class="Login_wrapper" align="center">
<div class="orange-top">
<div class="orange-left"></div></div>

<div class="white">
<div class="logo-incentel"> 

			<img style="padding:5px;border:1px solid #d9d8c8" src="<c:url value="/images/${companySuspended.logo}" />"  />
</div>


<div id="errorMessage">
	<s:actionerror/>
</div>

<span id="loginbox">


</span>
<div class="text"> 
  
  <p style="font:14pt arial">Sorry, ${companySuspended.nameEditable} website is not available.</p>
	This account is currently suspended.  Please contact support@webtogo.com.ph

</div>
</div>
<div class="footer"><div class="footer-right"></div>
<div style="float: left; width: 500px; padding-left: 15px;"></div>
</div>

</div>
<div class="reflection"></div>
<!--wrapper primary-->
</div>
</body>

</html>