<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />


<c:set var="menu" value="pages" />
 
<c:choose>
	<c:when test="${singlePage.parent != null}">
		<c:set var="submenu" value="${singlePage.parent.name}" />
	</c:when>
	<c:otherwise>
		<c:set var="submenu" value="${singlePage.name}" />
	</c:otherwise>
</c:choose>

<c:if test="${singlePage.parent != null}">
	<c:set var="showBackLinks" value="true" />						
</c:if>


<body>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="../javascripts/wz_tooltip.js"></script>



<c:set var="menu" value="pages" />

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
  
 <script type='text/javascript'>
				function loadDoctors(spec){
				var optionDoctor="<option value=\"No selected Doctor\"  id=\"doctors\">--Select Doctor--</option>";
				var specialization=spec.value//document.getElementById("specialization").value
				var docs=document.getElementById("_item_").value
				var pr=docs.split('____')
					for(var j=0;j<pr.length;j++){
						if(pr[j].indexOf(specialization)!=-1){
							var itemIto=pr[j].split('--')
							optionDoctor=optionDoctor+("<option value='"+itemIto[0]+"'>"+itemIto[0]+"</option>");
							}
						}
				document.getElementById("populateWithDocs").innerHTML=optionDoctor
				}
</script>

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
		
		var name = getElement('singlepage_name');
		

		var messages = "Problem(s) occured: \n\n";

		

		
		if(name.length == 0) {
			messages += "* Name not given";
			alert(messages);
			return false;
		}
		else {
			//alert(document.getElementById("c1").value);
			document.getElementById("c1").value = document.getElementById('singlePage_isHomeC').checked;
			document.getElementById("c2").value = document.getElementById('singlePage_isSingleFeaturedC').checked;
			document.getElementById("c3").value = document.getElementById('singlePage_hiddenC').checked;
			document.getElementById("c4").value = document.getElementById('singlePage_isFormC').checked;
			document.getElementById("c5").value = document.getElementById('singlePage_hasFileC').checked;
			document.getElementById("c6").value = document.getElementById('singlePage_loginRequiredC').checked;
			//alert(document.getElementById("c1").value);
			
			
			return true;
			}

		
	}
	
	function getElement(elementName) {
		return document.getElementById(elementName).value;
		
	}
	
	function updateImageTitle(id, title) {
		DWRImageAction.updateImageTitle("page", id, title);
	}
	
	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("page", id, caption);
	}
	
	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("page", id, description);
	}

	function addAnotherSchedule(){
		if(document.getElementById("anotherScheduleContainer").innerHTML=='')
			document.getElementById("anotherScheduleContainer").innerHTML=document.getElementById("scheduleTable").innerHTML
		else
			document.getElementById("anotherScheduleContainer").innerHTML=document.getElementById("anotherScheduleContainer").innerHTML+document.getElementById("scheduleTable").innerHTML
			
			//scheduleTable
		//anotherScheduleContainer
	}
	
</script> 
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
</script>
  <div class="container">
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
<div class="contentWrapper" id="contentWrapper">
	<div class="mainContent">
	<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Page was successfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'updatefailed'}">
								<li><span class="actionMessage">Failed to update page.</span></li>
							</c:if>
						</ul>
					</c:if>
	  <div class="pageTitle">

	    <h1><strong>Edit Single Page</strong></h1>

		<ul>

		  <li>
		 
					
					
					
					
							<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
								<a href="singlepage.do">Single Pages</a>  
							</c:if>
					
		  
		  </li>
			<li>&raquo;</li>
		 

		  <li>Edit Single Page</li>

		</ul>
		
					
					<c:choose>
						<c:when test="${singlePage.parent != null}">
							<c:set var="formAction" value="updatemultipagechild.do" />
						</c:when>
						<c:otherwise>
							<c:set var="formAction" value="updatesinglepage.do" />
						</c:otherwise>
					</c:choose>
					
					
		<div class="clear"></div>
		
	  </div><!--//pageTitle-->
			
	  <div class="metaBox">
	  		<h4>Edit Schedule</h4>
	  </div><!--//metaBox-->
<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
<c:if test="${company.name == 'hpdsked' and singlePage.parent.name == 'retiroBranch'}">
<div id='scheduleTable' style="display:none">
<table>
			
			<tr>
				<td width="1%" nowrap><b>SCHEDULE</td>
			</tr>
			<tr> 
				<td width="1%" nowrap><b>&nbsp;</td>
				<td width="10px"></td>
				<td>
					<b><font color="red">*&nbsp;</font>Days:</b>
					<input type="text" name="singlePage.daysSchedule" VALUE="">
					&nbsp;&nbsp;&nbsp;
					<b><font color="red">*&nbsp;</font>Time:</b>
					<input type="text" name="singlePage.timeSchedule" VALUE="">
				</td>
			</tr>
			
			<tr> 
				<td width="1%" nowrap><b>Room</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.room" VALUE=""></td>
			</tr>
			
			<tr> 
				<td width="1%" nowrap><b>Contact Number</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.contactNumber" VALUE=""></td>
			</tr>
			<tr> 
				<td colspan="3">&nbsp;</td>
			</tr>
			</table>
</div>
</c:if>
		<c:if test="${singlePage.id > 0}">
		<input type="hidden" name="update" value="1">
		<input type="hidden" name="singlepage_id" value="${singlePage.id}">
		</c:if>
		
		<c:if test="${multiPage != null}">
		<input type="hidden" name="multipage_id" value="${multiPage.id}">
		</c:if>
		<c:if test="${singlePage.parent != null}">
		<input type="hidden" name="multipage_id" value="${singlePage.parent.id}">
		</c:if>
		
	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>

		  <td class="label">Schedule Name</td>

		  <td align="left"><input type="text" id="singlepage_name" name="singlePage.name" value="${singlePage.name}" class="inputContent" /></td>

		</tr>
		<c:if test="${singlePage.language== null}"> 
			<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">		
		
				<tr> 
				<td class="label">Specialization<input type='hidden' name='multipage_id' value='${multiPage.id}'></td>
				<td align="left">
				<select name="singlePage.title" onchange="loadDoctors(this)" id="specialization">
					<option value="No selected Specialization"  id="doctors">--Select Specialization--</option>
					<c:forEach items="${allCategories}" var="category">
						<option value="${category.name}"  id="doctors" <c:if test="${singlePage.title eq category.name}">selected</c:if>>${category.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			
			<c:set var="container1">
			<c:forEach items="${allItems}" var="item" >
				<c:if test="${singlePage.parent.name=='retiroBranch' and (item.brand.name=='retiro' or item.brand.name=='Retiro') }">
					${item.name}--${item.parent.name} --${item.sku}____
				</c:if>
				<c:if test="${singlePage.parent.name=='events'  and (item.brand.id == 226 or item.brand.name=='HP-Rockwell') }">
					${item.name}--${item.parent.name} --${item.sku}____
				</c:if>
			</c:forEach>
			</c:set>

			
			<input id="_item_" type="hidden" value="${container1}" name="_item_">
			<c:forEach items="${allItems}" var="item" >
				<c:if test="${singlePage.parent.name=='retiroBranch' and (item.brand.name=='retiro' or item.brand.name=='Retiro') }">
					<label style="visibility:hidden" id="__${item.name}">${item.brand.id}</label>
					<label style="visibility:hidden"  id="____${item.name}">${item.brand.name}</label>
				</c:if>
				<c:if test="${singlePage.parent.name=='events' and (item.brand.id == 226 or item.brand.name=='HP-Rockwell') }">
					<label style="visibility:hidden"  id="__${item.name}">${item.brand.id}</label>
					<label style="visibility:hidden"  id="____${item.name}">${item.brand.name}</label>
				</c:if>
			</c:forEach>
			
			<tr>
				<td class="label">Doctor Name</td>
				<td align="left">
				<select name="singlePage.subTitle" id="populateWithDocs">
				<option value="No selected Doctor"  id="doctors">--Select Doctor--</option>
				<c:forEach items="${allItems}" var="item">
				<c:if test="${singlePage.parent.name=='retiroBranch' and (item.brand.name=='retiro' or item.brand.name=='Retiro') }">
						<option value="${item.name}"  <c:if test="${fn:indexOf(singlePage.subTitle,item.name)!=-1 or fn:indexOf(item.name,singlePage.subTitle)!=-1}">selected</c:if>>${item.name}</option>
				</c:if>
				<c:if test="${singlePage.parent.name=='events'}">
						<option value="${item.name}"  <c:if test="${fn:indexOf(singlePage.subTitle,item.name)!=-1 or fn:indexOf(item.name,singlePage.subTitle)!=-1}">selected</c:if>>${item.name}</option>
				</c:if>
				</c:forEach>
				</select>
				</td>

			</tr>

			<tr> 
				<td class="label">School</td>
				<td align="left">
				<input type="text" name="singlePage.docSchool" VALUE="${singlePage.docSchool}">
				</td>
			</tr>
		
		</c:if></c:if>
		<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
		<c:if test="${company.name == 'hpdsked' and singlePage.parent.name == 'events'}">
				<%@include file="includes/doctorScheduleForm.jsp"  %>
		</c:if>
		
		<c:if test="${company.name == 'hpdsked' and singlePage.parent.name == 'retiroBranch'}">
				
			<tr> 
				<td width="1%" nowrap><input type="hidden" name="SinglePage.isRetiroBranch" value="yes"><b></td>
				<td width="10px"></td>
				<td>
				<a href="/" onclick="addAnotherSchedule();return false;"><strong>Add Another Schedule</strong></a>
				</td>
			</tr>
			</table>
			<table>
				<c:choose>
					<c:when test="${daysAvailable!=null and fn:length(daysAvailable)>0 and hpdskedBranch!=null}">
		<c:forEach begin="0" end="${fn:length(daysAvailable)-1}" var="status" step="1">
			<tr>
				<td width="1%" nowrap><b>SCHEDULE</td>
			</tr>
			<tr> 
				<td width="1%" nowrap><b>&nbsp;</td>
				<td width="10px"></td>
				<td>
					<b><font color="red">*&nbsp;</font>Days:</b>
					<input type="text" name="singlePage.daysSchedule" VALUE="${daysAvailable[status]}">
					&nbsp;&nbsp;&nbsp;
					<b><font color="red">*&nbsp;</font>Time:</b>
					<input type="text" name="singlePage.timeSchedule" VALUE="${timeAvailable[status]}">
				</td>
			</tr>
			
			<tr> 
				<td width="1%" nowrap><b>Room</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.room" VALUE="${docRoom[status]}"></td>
			</tr>
			
			<tr> 
				<td width="1%" nowrap><b>Contact Number</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.contactNumber" VALUE="${contactNumber[status]}"></td>
			</tr>
			<tr> 
				<td colspan="3">&nbsp;</td>
			</tr>
						
			</c:forEach>
					</c:when>
					<c:otherwise>
						<script type="text/javascript">
								document.write(document.getElementById("scheduleTable").innerHTML)
						</script>
					</c:otherwise>
				</c:choose>
				
	        </table>
	        
	        <table id="anotherScheduleContainer">
	        </table>
	
			<table>	
		</c:if>
		
		<c:if test="${singlePage.language== null}"> 
		<tr>

		  <td class="label">Date</td>

		   <td align="left">
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
									 
					<input type="text" id="item_date" name="itemDate" value="${idate}" class="inputContent" /> 
					<a href="javascript:;" id="item_date_button"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>
					   
					  
					<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "item_date",     // id of the input field
					        ifFormat       :    "%m-%d-%Y",      // format of the input field
					        button         :    "item_date_button",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true
					    });
					</script>
		</td>

		</tr>
		</c:if>
		<input type="hidden"  id="c1" name="singlePage.isHome" />
		<input type="hidden"  id="c2" name="singlePage.isSingleFeatured" />	
		<input type="hidden"  id="c3" name="singlePage.hidden" />
		<input type="hidden"  id="c4" name="singlePage.isForm" />
		<input type="hidden"  id="c5" name="singlePage.hasFile" />
		<input type="hidden"  id="c6" name="singlePage.loginRequired" />
		
		<tr>

		  <td class="label">Content</td>

		   <td align="left">
		 
		  <textarea id="singlePage.content" name="singlePage.content" >${singlePage.content}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'singlePage.content');
			</script>
		  </td>

		</tr><tr>
			
		  <td colspan="2" class="label">
			<c:choose>
						<c:when test="${singlePage.id > 0}">
						
							<input type="reset" value="Reset" class="btnBlue">
				<input type="submit" value="Update" class="btnBlue">
						</c:when>
						<c:otherwise>
							
						
				<input type="submit" value="Update" class="btnBlue">
						</c:otherwise>
					</c:choose>
		</td>

		</tr>

	  </table>
</form>
	</div><!--//mainContent-->

	<div class="sidenav">
		<c:if test="${company.name=='hpdsked' and singlePage.parent.name == 'retiroBranch'}">
		<table>	
		<th>
			<td  colspan="3"><h3>Guidelines:</h3></td>	
		</th>
		<tr>
			<td colspan="3">
			<strong style="color:blue">Days</strong>(required)<br>
			-This is the daily Schedule of the Doctors<br>
			e.g. Mon - Tue - Sat <br>
			e.g. Daily
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<strong  style="color:blue"><br>Time</strong>(required)<br>
			-This is the time Schedule of the Doctors<br>
			e.g. 9:00 - 4:00 PM
			</td>
		</tr>
		
		<tr>
			<td colspan="3">
			<strong  style="color:blue"><br>Room/Contact Number</strong>(optional)<br>
			</td>
		</tr>
		
		</table>
		</c:if>
	</div><!--//sidenav-->

	<div class="clear"></div>

  </div><!--//container-->
</div>
</body>

</html>