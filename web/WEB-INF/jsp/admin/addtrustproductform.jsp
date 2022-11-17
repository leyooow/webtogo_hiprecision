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

		var addbluevalue = getElement('bluestatvalue');
		var addgreenvalue = getElement('greenstatvalue');
		var addbalancedvalue = getElement('balancedstatvalue');
		//var adddate = getElement('statdate');
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(addbluevalue.length == 0) {
			messages += "* Blue Fund value not given\n";
			error = true;
		}

		if(addgreenvalue.length == 0) {
			messages += "* Green Fund value not given\n";
			error = true;
		}
		if(addbalancedvalue.length == 0) {
			messages += "* Balanced Fund value not given\n";
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
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
</script> 

<div style="width:100%;">
<table border="0">
	<tr>
		<td style="border:0px;" valign="top">
	
		<form method="post" action="addtrustproductstatistics.do" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<input type="hidden" name="userID" value="${user.id}">
		<!--  <input type="hidden" name="subject" value="RobinsonsBank Rate Statistics"> -->
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
				<td width="1%" nowrap><b>Blue Fund</td>
				<td width="10px"></td>
				<td>
				<input type="text" id="bluestatvalue" name="bluestatvalue" class="textbox2">
				</td>
			</tr>
			<tr>
				<td width="1%" nowrap><b>Green Fund</td>
				<td width="10px"></td>
				<td>
				<input type="text" id="greenstatvalue" name="greenstatvalue" class="textbox2">
				</td>
			</tr>
			<tr>
				<td width="1%" nowrap><b>Balanced Fund</td>
				<td width="10px"></td>
				<td>
				<input type="text" id="balancedstatvalue" name="balancedstatvalue" class="textbox2">
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
							<input type="submit" value="Add" class="upload_button2">
						</c:otherwise>
					</c:choose>
					
				</td>
			</tr>
		</table>
		</form>
		
		</td>
		<td width="50%" valign="top"></td>
		
		<%-- td width="50%" >
			  <input type="hidden" id="strXML" value="${strXML}"/>
			  <div id="chart1div">
         		FusionCharts
		      </div>
		      <script language="JavaScript">
		         var chart1 = new FusionCharts("../javascripts/FusionCharts/FusionCharts/MSLine.swf", "chart1Id", "600", "450", "0", "1");
		         //chart1.setDataXML("<chart caption='Trust Product' subCaption='Blue Fund' numdivlines='9' lineThickness='2' showValues='0' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'><categories><category label='2007-10-06 '/><category label='2008-10-06 '/><category label='2008-12-06 '/><category label='2009-07-28 '/><category label='2009-08-06 '/><category label='2009-09-06 '/><category label='2009-10-06 '/><category label='2009-10-07 '/><category label='2009-10-09 '/><category label='2009-10-11 '/><category label='2009-10-16 '/><category label='2009-10-26 '/><category label='2009-11-06 '/></categories><dataset seriesName='Blue Fund' color='0080C0' anchorBorderColor='0080C0'><set value='5565.78'/><set value='4.4'/><set value='67.8987'/><set value='120.762498'/><set value='1.1'/><set value='2.2'/><set value='3.3'/><set value='5.5'/><set value='6.6'/><set value='7.7'/><set value='8.8'/><set value='9.92'/><set value='10.1234'/></dataset></chart>");
		         chart1.setDataXML(document.getElementById('strXML').value);
		         chart1.render("chart1div");
		      </script>
		      
		</td --%>
		
	</tr>
</table>
</div>