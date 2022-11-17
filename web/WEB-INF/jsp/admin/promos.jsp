<%@include file="includes/header.jsp"  %>

<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<c:set var="menu" value="promo" />
<c:set var="pagingAction" value="promo.do" />
<c:set var="mode" value="add"/>

<script language="javascript" src="../javascripts/overlib.js"></script>
<!-- main calendar program -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
<!-- language for the calendar -->
<script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
<!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>
<script language="javascript" src="../javascripts/jquery-1.6.2.min.js"></script>
<script language="javascript" src="../javascripts/jquery-latest.js"></script>
<script language="javascript" src="../javascripts/jquery.jplayer.min.js"></script>
<script language="javascript" src="../javascripts/jquery-ui.min.js"></script>
<script language="javascript" src="../javascripts/jquery.numeric.js"></script>

<script>
	$(document).ready(function(){ 
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
	}); 

	function showMessage(id) {
		var content = document.getElementById('promocode_'+id).innerHTML;
		overlib(content, STICKY, NOCLOSE)
	}
	
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
	
	function submitForm(myForm) {
		var code = getElement('code');
		var discount = getElement('discount');
		var fromDate = getElement('fromDate');
		var toDate = getElement('toDate');
		var note = getElement('note');
		
		var error = false;
		var messages = 'Problem(s) occured: \n\n';
		
		if(code.length == 0) {
			messages += '* Promo Code is not given\n';
			error = true;
		}
		
		if(discount.length == 0) {
			messages += '* Discount is not given\n';
			error = true;
		}
		
		if(fromDate.length == 0) {
			messages += '* From Date is not given\n';
			error = true;
		}
		
		if(toDate.length == 0) {
			messages += '* To Date is not given\n';
			error = true;
		}
		
		if(note.length == 0) {
			messages += '* Note is not given\n';
			error = true;
		}

		if(error) {
			alert(messages);
		}
		else {
			return true;
		}
		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
</script>

<body>
<div class="container">
<%@include file="includes/bluetop.jsp"%>
<%@include file="includes/bluenav.jsp"%>

<div class="contentWrapper" id="contentWrapper">
    <div class="mainContent">
	 	<s:actionmessage />
		<s:actionerror />
		<c:if test="${param['evt'] != null}">
			<ul>
				<c:if test="${param['evt'] == 'duplicate'}">
					<li><span class="actionMessage">Duplicate Promo Code.</span></li>
				</c:if>
			</ul>
		</c:if>
		
		
	  	<div class="pageTitle">
	    	<h1><strong>Promo</strong>: View Promo</h1>
			<div class="clear"></div>
	  	</div><!--//pageTitle-->

	  	<ul class="pagination">
	   		<%@include file="includes/pagingnew.jsp"  %>
	  	</ul>
			
		<div class="clear"></div>
 		
 		<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
			<tr>
				<th>Promo</th>
				<th>Action</th>
			</tr>
			
			<c:set var="count" value="0" />
			<c:set var="yes" value="Yes" />
			<c:set var="no" value="No" />
			<c:forEach items="${promos}" var="promo">
				<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
					<td>${promo.name}</td>
					<td>
						<a href="editpromo.do?promoId=${promo.id}">Edit</a>
					</td>
				</tr>
			</c:forEach>
		</table> 
			
		<ul class="pagination">
	   		<%@include file="includes/pagingnew.jsp"  %>
	  	</ul>
	</div><!--//mainContent-->

</div>
		
<div class="clear"></div>
</div><!--//container-->
</body>
</html>