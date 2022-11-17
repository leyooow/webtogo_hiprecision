<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="productstatistics" />

<script language="javascript" src="../javascripts/upload.js"></script>
<script language="javascript" src="../javascripts/dynamicUploader.js"></script>

<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(singlePage.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />
<c:set var="allowedFileCount" value="${1 - fn:length(singlePage.files)}" />

<c:set var="maxMultiPageFile" value="${companySettings.maxAllowedFiles}" />
<c:set var="uploadedFileCount" value="${fn:length(multiPageFiles)}" /> 
<c:set var="allowedUploadedFileCount" value="${maxMultiPageFile - uploadedFileCount}" />

<script>

	function submitForm(myForm) {
		
		var addbuyvalue = getElement('buystatvalue');
		var addsellvalue = getElement('sellstatvalue');
		//var adddate = getElement('statdate');
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(addbuyvalue.length == 0) {
			messages += "* US Buy value not given \n";
			error = true;
		}
		
		if(addsellvalue.length == 0) {
			messages += "* US Sell value not given \n";
			error = true;
		}
		/*if(adddate.length == 0) {
			messages += "* Date not given";
			error = true;
		}
		else {
			document.getElementById('statdate').value = document.getElementById('statdate').value;
		}*/
		 
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
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
</script> 

<div style="width:100%;">
<table border="0">
	<tr>
		<td style="border:0px;" valign="top">
	
		<form method="post" action="addUSBuyAndSellstatistics.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<input type="hidden" name="userID" value="${user.id}">
		<input type="hidden" name="subject" value="RobinsonsBank Rate Statistics">
		<input type="hidden" name="to" value="${company.email}">
		<input type="hidden" name="from" value="noreply@webtogo.com.ph">
		<input type="hidden" name="title" value="Statistics Update">
		
		<table width="50%">
					
			<tr>
				<td colspan="3"></td>
			</tr>
		
			<c:if test="${singlePage == null}">
			<tr class="headbox">
				<th colspan="3">
					Update ${parentStatistics}
				</th>
			</tr>
			</c:if>

			<tr>
				<td width="1%" nowrap><b>Buy</td>
				<td width="10px"></td>
				<td>
				<input type="text" id="buystatvalue" name="buystatvalue" class="textbox2">
				</td>
			</tr>
			<tr>
				<td width="1%" nowrap><b>Sell</td>
				<td width="10px"></td>
				<td>
				<input type="text" id="sellstatvalue" name="sellstatvalue" class="textbox2">
				</td>
			</tr>																		 
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				 
					<c:choose>
						<c:when test="${singlePage.id > 0}">
							<input type="submit" value="Update" class="upload_button2">
							<input type="reset" value="Reset" class="upload_button2">
						</c:when>
						<c:otherwise>
							<input type="submit" value="Add" class="upload_button2" >
						</c:otherwise>
					</c:choose>
					
				</td>
			</tr>
		</table>
		</form>
		
		</td>
		<td width="50%" valign="top"></td>	
	</tr>
</table>
</div>