<%@include file="includes/header.jsp"  %>
<!-- <link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" /> -->

<c:set var="menu" value="events" />
 
<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	<div class="contentWrapper" id="contentWrapper">
	    <div class="mainContent">
				
				<h3>
				
				Edit Event
				</h3> 
				 				  
				<c:set var="formAction" value="updateevent.do" />
				<%@include file="eventsform.jsp" %>
				
		</div><!--//mainContent-->
</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>