<%@include file="includes/taglibs.jsp"%>

<c:set var="navMenu" value="online-search" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Health Online</title>
<script type="text/javascript" src="scripts/effects.js"></script>
<script type='text/javascript' src='scripts/prototype.js'></script>
<script type="text/javascript" src="scripts/scriptaculous.js"></script>

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-30272358-6', 'healthonlineasia.com');
  ga('send', 'pageview');

</script>

<link href="css/modal.css" rel="stylesheet" type="text/css" />
<link href="css/cssOnline.css?v=101820210839" rel="stylesheet" type="text/css">

</head>
<body class="inside">
  <div class="container">
  


<div class="content">

<div class="onlineSearchContent">
  	<div class="hi-precision" style="padding-bottom: 10px; padding-top: 25px; padding-left: 25px;">
	  <h1><a href="https://www.healthonlineasia.com/"><img src="images/healthOnline.gif" alt="Health Online" title="Health Online" /></a></h1>
	  <div class="clear"></div>
	</div>
	
	
<script type='text/javascript' src='<c:url value="/dwr/interface/DWRCategoryAction.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script> 

<div class="clear"></div>

<div class="masthead"> 
<img src="images/mastheadOnlineSearch2.jpg" alt="HealthOnline" title="HealthOnline" style="padding-bottom: 20px;">
</div>

<div class="clear"></div>

<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr valign="top">
			
			<%@include file="includes/online-search-side-nav.jsp"%>
			
			
		<td>
		<!-- div><img src="images/c.gif" /></div-->
		<div class="mainContent" id="mainContent"></div>
		<!-- div><img src="images/c2.gif" /></div-->

		<div class="legend"><img src="images/ovalWTl.png" class="itl" />
		<img src="images/ovalWTr.png" class="itr" /> <img
			src="images/ovalWBl.png" class="ibl2" /> <img
			src="images/ovalWBr.png" class="ibr" />


		<ul class="listlegend">
			<!-- <li class="label">Legend:</li> -->
			<li class="moreInfo">For more info, please send us an email at <a href="mailto:labtestinfo@healthonline.com.ph" target="_top">labtestinfo@healthonline.com.ph</a></li>
		</ul>
		<div class="clear"></div>

		</div>
		</td>
	</tr>
</table>
</div>
</div>


</div>
<div class="footerContainer">
<!--<ul class="footerNav">
      <li><a href="${menu['home'].url}">${menu['home'].name}</a></li>
	<li><a href="${menu['about'].url}">${menu['about'].name}</a></li>
	<li><a href="${menu['branches'].url}">${menu['branches'].name}</a></li>
	<li><a href="https://www.healthonlineasia.com/labtestinfo.asp" target="_blank">Lab Test Preparation</a></li>
	 <li><a href="${menu['doctors_clinic'].url}">${menu['doctors_clinic'].name}</a></li> 
	<li><a href="${menu['health_watch'].url}">${menu['health_watch'].name}</a></li>
	<li><a href="${menu['careers'].url}">${menu['careers'].name}</a></li>
	<li><a href="${menu['contact'].url}">${menu['contact'].name}</a></li>
	<li><a href="${menu['customer_testimonials'].url}">${menu['customer_testimonials'].name}</a></li>
	<li><a href="${menu['homeservice'].url}">Home Service</a></li>
	<li><a href="${menu['services'].url}">${menu['services'].name}</a></li> 
	
	<li><a href="http://hiprecision.webtogo.com.ph/home.do">Home</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/about.do">About Us</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/branches.do">Branches</a></li>
	<li><a href="https://www.healthonlineasia.com/labtestinfo.asp" target="_blank">Lab Test Preparation</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/doctors_clinic.do">Doctor's Clinic</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/health_watch.do">Health Watch</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/careers.do">Careers</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/contact.do">Contact Us</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/customer_testimonials.do">Customer Testimonials</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/homeservice.do">Home Service</a></li>
	<li><a href="http://hiprecision.webtogo.com.ph/services.do">Services</a></li>
  </ul>
  -->
  <div class="copyright">
    Copyright &copy; <%--  <script>document.write(theDate.getFullYear())</script> --%> Health Online. Web Design by <a href="http://www.webtogo.com.ph/" target="_blank">WebToGo Philippines</a>
  </div>
  </div>
</div>
</body></html>
