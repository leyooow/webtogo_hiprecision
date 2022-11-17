<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http:‎//www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>${user.company.nameEditable} - Chat</title>
	
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %> 
	<%-- <%@taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>--%>
	<%@ taglib prefix="FCK" uri="http://java.fckeditor.net" %>
	<%@taglib uri="/struts-tags" prefix="s" %>
	<un:useConstants var="ReferralStatus" className="com.ivant.cms.enums.ReferralStatus" />

	<SCRIPT LANGUAGE="javascript" type="text/javascript" SRC="../javascripts/FusionCharts/FusionCharts/FusionCharts.js"></SCRIPT>
	<un:useConstants var="CompanyStatus" className="com.ivant.cms.enums.CompanyStatusEnum" />
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<link href="css/css.css" rel="stylesheet" type="text/css" />


<script>
		function loading() {
			document.getElementById("opacityOverlay").style.display = document.getElementById("loadDiv").style.display = 'block';

			return true;
			}
	</script>
	<%if(request.getRequestURL().indexOf("login")==-1){ %>
		<%@include file="../../../../common/chat/admin_include.jsp" %>
	<%} %>
</head>


<link href="../admin/css/modal.css" rel="stylesheet" type="text/css" />
<body  onload="document.getElementById('chatmessage').focus();">

  <!--<div class="container">
	<c:set var="menu" value="chat" />
	
<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
		--><%@include file="../../../../common/chat/chat_admin_v2.jsp"%>
	<!--</div>//mainContent

</div>

	
  </div>//container

--></body>

</html>