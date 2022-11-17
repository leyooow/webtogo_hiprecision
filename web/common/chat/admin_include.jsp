<c:if test="${user.userType.value == 'Company Staff' or user.userType.value =='Company Administrator' or user.userType.value == 'Normal User'}">
	<script type="text/javascript">var chatcompanyname="${user.company.id}";var chatcompanyuser="${user.company.name}"; var chatusername="${user.firstname}"; var chatuser="${user.firstname} ${user.lastname}";var staff_id="${user.id}";var staff_type="${user.userType}";</script>
	
	<!-- LOCAL -->
	
	<link rel="stylesheet" href="../javascripts/chat/css/admin.css" type="text/css">
	<script type="text/javascript" src="../javascripts/chat/javascript/jquery-1.7.1.min.js"></script>
	<script type='text/javascript' src="../javascripts/chat/javascript/check_refresh_admin.js"></script>
	
	<%if(request.getRequestURL().indexOf("chat")!= -1){ %>
		<!-- LOCAL -->
		<script type='text/javascript' src="../javascripts/chat/javascript/adminUI.js"></script>
		<script type='text/javascript'>
		$(document).ready(funtion(){
			$("#chatmessage").focus();});
		</script>
	<%} %>
	<%if(request.getRequestURL().indexOf("chat")== -1){ %>
		<!-- LOCAL -->
		<script type='text/javascript' src="../javascripts/chat/javascript/admin_logger.js"></script>
	<%} %>
</c:if>