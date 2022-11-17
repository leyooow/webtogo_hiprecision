<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><c:choose><c:when test="${fn:length(company.title) gt 0}">${company.title}</c:when><c:otherwise>Hi-Precision Diagnostics Results</c:otherwise></c:choose></title>
<script type="text/javascript" src="scripts/effects.js"></script>
<script type='text/javascript' src='scripts/prototype.js'></script>
<script type="text/javascript" src="scripts/scriptaculous.js"></script>

<link href="css/modal.css" rel="stylesheet" type="text/css" />
<link href="css/css.css" rel="stylesheet" type="text/css">

</head>
<body class="inside">
  <div class="container">
  	<div class="hi-precision">
	  <h1><a href="${menu['dashboard'].url}"><img src="images/hi-precision.jpg" alt="Hi-Precision Diagnostics" title="Hi-Precision Diagnostics" /></a></h1>
	  <c:if test="${member!=null}">
	  	<h2>Logged in as Hi-Precision Admin (<a href="logout.do">Log-out</a>) | <a href="http://pdfchkr:8080/calculator.do" target="_blank">Calculator</a> | <a href="${menu['quote'].url}">Quote</a> | <a href="${menu['allQuotes'].url}">All Quotes</a> | <a href="/">Settings</a></h2>
	  </c:if>
	  <c:if test="${member==null}">
	  	<h2>You are not not logged in (<a href="home.do">Log-in</a>) | <a href="/">Settings</a></h2>
	  </c:if>
	  <div class="clear"></div>
	</div><!--//hi-precision-->