<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http:‎//www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>WebTogo - Get online</title>
	
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %> 
	<%-- <%@taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>--%>
	<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
	<%@ taglib prefix="FCK" uri="http://java.fckeditor.net" %>
	<%@taglib uri="/struts-tags" prefix="s" %>
	<%@include file="constants.jsp"%>
	
	
	<c:if test="${company.name eq 'gurkkatest'}">
		<%
		session.setMaxInactiveInterval(-1);
		%>
	</c:if>
	
	<SCRIPT LANGUAGE="javascript" type="text/javascript" SRC="../javascripts/FusionCharts/FusionCharts/FusionCharts.js"></SCRIPT>
	<un:useConstants var="CompanyStatus" className="com.ivant.cms.enums.CompanyStatusEnum" />
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<link href="css/css.css" rel="stylesheet" type="text/css" />
	
	
  <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

	<LINK REL="SHORTCUT ICON" HREF="images/webtogo.ico">
	
	<script>
		function loading() {
			document.getElementById("opacityOverlay").style.display = document.getElementById("loadDiv").style.display = 'block';

			return true;
			}
	</script>
	<%if (request.getRequestURL().indexOf("chatboard") == -1 && request.getRequestURL().indexOf("settings") == -1) {%>
		<c:if test="${user.lastLogin ne null}">
			<c:if test="${user.userType ne null and user.userType.value eq 'Normal User'}">
				<%response.sendRedirect("chatboard.do");%>
			</c:if>
		</c:if>
	<%} %>
	<%if (request.getRequestURL().indexOf("login") == -1 && request.getRequestURL().indexOf("terms") == -1) {%>	
		<c:if test="${user.lastLogin eq null}">
			<%response.sendRedirect("terms.do");%>
		</c:if>
		<c:if test="${user.company.companySettings.hasClientChat eq true}">
			<%@include file="../../../../common/chat/admin_include.jsp"%>
		</c:if>
	<%}%>

	
	<c:if test="${company.name eq 'bluechip' and group.name eq 'Products' }">
		<%@include file="bluechiphead.jsp"  %>
	</c:if>

</head>


