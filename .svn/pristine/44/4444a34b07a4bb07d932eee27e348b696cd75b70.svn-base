<%@include file="includes/header.jsp"  %>

<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<c:set var="menu" value="promo" />

<c:set var="pagingAction" value="promo.do" />
<c:set var="mode" value="edit"/>

<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<!-- main calendar program -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>
<!-- language for the calendar -->
<script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>
<!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>
<script language="javascript" src="../javascripts/overlib.js"></script>
<script language="javascript" src="../javascripts/jquery-1.6.2.min.js"></script>
<script language="javascript" src="../javascripts/jquery-latest.js"></script>
<script language="javascript" src="../javascripts/jquery.jplayer.min.js"></script>
<script language="javascript" src="../javascripts/jquery-ui.min.js"></script>
<script language="javascript" src="../javascripts/jquery.numeric.js"></script>

<script>
	$(document).ready(function(){ 
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
	}); 

	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}

	function submitForm(myForm) {
		
		if(!$('#all').is(':checked')) {
			var content = "";
			$('.child-promo').each(function(){
				if($(this).is(':checked'))
					content += (content == '' ? $(this).val() : "=="+$(this).val());
			});
			$('#content').val(content);
		}
		return true;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		return value;
	}
</script> 

<body>
<div class="container">
<%@include file="includes/bluetop.jsp"%>
<%@include file="includes/bluenav.jsp"%>

<div class="contentWrapper" id="contentWrapper">
	<div class="content">
		<s:actionmessage />
		<s:actionerror />
				
	  	<div class="pageTitle">
	    	<h1><strong>Promo</strong>: Edit Promo</h1>
					
			<div class="clear"></div>
	  	</div><!--//pageTitle-->
	  	
	 	<form method="post" action="savepromo.do" onsubmit="return submitForm(this);">
			<input type="hidden" name="promoId" value="${promo.id}">
			<input type="hidden" name="promo.id" value="${promo.id}">
			<input type="hidden" id="checked" name="checked" value="${promo.checked }">
			<input type="hidden" id="content" name="content" value="${promo.content }">
			<table width="100%">
				<tr>
					<td width="1%" nowrap>Name : </td>
					<td width="10px"></td>
					<td nowrap>${promo.name }</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				<tr>
					<td width="1%" nowrap></td>
					<td width="10px"></td>
					<td nowrap>
						<input id="all" name="promo.checked" type="checkbox" ${promo.checked ? 'checked' : '' } onclick="enabledField($(this).is(':checked'))">&nbsp;All
						<br/>
						<br/>
						&nbsp;Choose<br/><br/>
						<div style="padding-left:30px">
						<c:if test="${promo.name eq 'Events' }">
							<c:forEach items="${singlePages }" var="sp">
								<c:choose>
									<c:when test="${fn:containsIgnoreCase(promo.content, sp.name) }">
										&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${sp.name }" checked>&nbsp;${sp.name }
										<br/>
									</c:when>
									<c:otherwise>
										&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${sp.name }">&nbsp;${sp.name }
										<br/>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
						
						<c:if test="${promo.name eq 'Packages' }">
							<c:forEach items="${group.enabledCategories }" var="packagesGroup">
								<c:choose>
									<c:when test="${fn:containsIgnoreCase(packagesGroup.name, 'Timer')}" >
										<c:set var="firstTimer" value="${packagesGroup.enabledItems }" />
									</c:when>
									<c:when test="${fn:containsIgnoreCase(packagesGroup.name, 'Card') }">
										<c:set var="classCard" value="${packagesGroup.enabledItems }" />
									</c:when>
									<c:when test="${fn:containsIgnoreCase(packagesGroup.name, 'Monthly') }">
										<c:set var="monthlyUnlimited" value="${packagesGroup.enabledItems }" />
									</c:when>
								</c:choose>
							</c:forEach>
						
							&nbsp;&nbsp;&nbsp;First Timer<br/>
								<c:forEach items="${firstTimer }" var="ft">
									<c:choose>
										<c:when test="${fn:containsIgnoreCase(promo.content, ft.name) }">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${ft.name }" checked>&nbsp;${ft.name }
											<br/>
										</c:when>
										<c:otherwise>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${ft.name }">&nbsp;${ft.name }
											<br/>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<br/>
							&nbsp;&nbsp;&nbsp;Monthly Unlimited<br/>
								<c:forEach items="${monthlyUnlimited }" var="mu">
									<c:choose>
										<c:when test="${fn:containsIgnoreCase(promo.content, mu.name) }">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${mu.name }" checked>&nbsp;${mu.name }
											<br/>
										</c:when>
										<c:otherwise>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${mu.name }">&nbsp;${mu.name }
											<br/>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<br/>
							&nbsp;&nbsp;&nbsp;Class Card<br/>
								<c:forEach items="${classCard }" var="cc">
									<c:choose>
										<c:when test="${fn:containsIgnoreCase(promo.content, cc.name) }">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${cc.name }" checked>&nbsp;${cc.name }
											<br/>
										</c:when>
										<c:otherwise>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="child-promo" type="checkbox" value="${cc.name }">&nbsp;${cc.name }
											<br/>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</c:if>
						</div> 
						<br/>
					</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 0px;"></td>
					<td style="border: 0px;">
						<input type="submit" value="Update" class="btnBlue">
						<input type="button" value="Cancel" onclick="window.location='promo.do'" class="btnBlue">
					</td>
					<td width="1%" colspan="3" nowrap></td>
				</tr>
		</table>
		</form>
	</div><!--//mainContent-->
</div>

<div class="clear"></div>
</div><!--//container-->

<script type="text/javascript">
$(function(){
	var all = $('#all').is(':checked');
	if(all) {
		enabledField(all);
	}
});

function enabledField(all) {
	if(all) {
		$('.child-promo').attr("disabled","disabled");
		$('#checked').val('true');
	} else {
		$('.child-promo').removeAttr("disabled");
		$('#checked').val('false');
	}
}
</script>

</body>
</html>