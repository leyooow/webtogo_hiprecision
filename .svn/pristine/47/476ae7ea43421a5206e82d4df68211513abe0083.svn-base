<%@include file="taglibs.jsp" %>
	<%--
	--works in local but not in live
	--the javascripts and css here is already deleted
	<link href="../../css/cssPaypal.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="css/jquery.ui.all.css">
	<script src="http>/javascripts/jquery/jquery-1.7.2.js"></script>
	<script src="../../javascripts/jquery/jquery.ui.core.js"></script>
	<script src="../../javascripts/jquery/jquery.ui.widget.js"></script>
	<script src="../../javascripts/jquery/jquery.ui.position.js"></script>
	<script src="../../javascripts/jquery/jquery.ui.dialog.js"></script>
	 --%>
	 <%--
	 RESOURCES is in http://sunnysunday.com.ph/scripts/
	 --%>
	<link href="http://sunnysunday.com.ph/css/cssPaypal.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="http://sunnysunday.com.ph/css/jquery.ui.all.css">
	<script src="http://sunnysunday.com.ph/scripts/jquery-1.7.2.js"></script>
	<script src="http://sunnysunday.com.ph/scripts/jquery.ui.core.js"></script>
	<script src="http://sunnysunday.com.ph/scripts/jquery.ui.widget.js"></script>
	<script src="http://sunnysunday.com.ph/scripts/jquery.ui.position.js"></script>
	<script src="http://sunnysunday.com.ph/scripts/jquery.ui.dialog.js"></script>
	
<script>
	$(function() {
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		$( "#dialog-modal" ).dialog({
			height: getHeight(),
			width: 800,
			modal: true
		});
	});

	function getHeight(){
		if ($.browser.msie ) {
			 return 1000;
		}
		else
			return 600;
	}
</script>
<c:set var="navMenu" value="home" />

 	<body onload="pay()">
 	
	<div id="dialog-modal" title="Please wait..." style="display:none">
		<form id="expressCheckoutForm" name="expressCheckoutForm" action="${checkoutUrl}" method="get">
			<input type="hidden" name="cmd" value="_express-checkout" />
			<input type="hidden" name="token" value="${pToken}" />
			<input type="hidden" id="comments" name="comments" value="${ comments }"></input><br/>
			<input type="hidden" id="cart_order_id" name="cart_order_id" value="${ itemPackage.id }"></input><br/>
			 <input type="hidden" name="rm" value="0">
			
				<div class="paypalBox"  id="loading" >
					<h1>Redirecting you to Paypal</h1>
				    <em>Thank you for your patience</em>
				    <br /><br />
				    <img src="http://sunnysunday.com.ph/images/loader.gif" style="margin: 0 0 0 5px;"/>
				</div>
		</form>
	</div>
<script>
	function pay(){
		setInterval(function(){
				var form = document.expressCheckoutForm;
				form.submit();
			},10000);
	}
</script>
</body>