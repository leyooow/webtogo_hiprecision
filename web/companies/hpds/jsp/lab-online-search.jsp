<%@include file="includes/taglibs.jsp"%>

<c:set var="navMenu" value="online-search" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Laboratory</title>
<script type="text/javascript" src="scripts/effects.js"></script>
<script type='text/javascript' src='scripts/prototype.js'></script>
<script type="text/javascript" src="scripts/scriptaculous.js"></script>

<link href="css/modal.css" rel="stylesheet" type="text/css" />
<link href="css/cssOnline.css?v=101820210839" rel="stylesheet" type="text/css">

</head>
<body class="inside">
  <div class="container">
  


<div class="content">

<div class="onlineSearchContent">
  	<div class="hi-precision" style="padding-bottom: 10px; padding-top: 25px; padding-left: 25px;">
	  <h1><a href="https://labtestpreparation.hpdlab.com/laboratory.do"><img src="images/hipre.jpg" alt="Laboratory" title="Laboratory" /></a></h1>
	  <div class="clear"></div>
	</div>
	
	
<script type='text/javascript' src='<c:url value="/dwr/interface/DWRCategoryAction.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script> 

<div class="clear"></div>

<div class="masthead"> 
</div>

<div class="clear"></div>

<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr valign="top">
		<%@include file="includes/lab-online-search-side-nav.jsp"%>
		<td style="display: inline-block;">

			<div class="mainContent" id="mainContent"></div>
				<div class="legend">
				<ul class="listlegend">
					<!-- <li class="label">Legend:</li> -->
					<li class="moreInfo">For more info, please send us an email at 
						<a href="mailto:laboratorymanagers-dm@hpretiro.hi-precision.com.ph" target="_top">laboratorymanagers-dm@hpretiro.hi-precision.com.ph</a>
					</li>
				</ul>
				</div>
				<div class="clear"></div>
			</div>
		</td>
	</tr>
</table>
</div>
</div>


</div>
<!-- <div class="footerContainer">

  <div class="copyright">
    Copyright &copy; Health Online.
  </div>
  </div>
</div> -->
</body></html>
