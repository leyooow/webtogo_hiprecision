<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="pages" />
<c:set var="submenu" value="${multiPage.name}" />
<c:set var="pagingAction" value="editmultipagechildlist.do?multipage_id=${multiPage.id}" />

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
	
</script> 
<style type="text/css"><!--
	
	#phoneticlong li, #buttons li {
		margin-bottom: 0px;
		margin-top: 4px;
		margin-left: 10px;
		font-size: 12px;
		cursor: hand;
		cursor: pointer;
	}

	
	//-->
</style>


<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRMultiPageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
   
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/core.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/events.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/css.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/coordinates.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/drag.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/dragsort.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/cookies.js"></script>

<script language="JavaScript" type="text/javascript">
	var dragsort = ToolMan.dragsort()
	var junkdrawer = ToolMan.junkdrawer()

	window.onload = function() {
		dragsort.makeListSortable(document.getElementById("phoneticlong"),
					verticalOnly, saveOrder)
	}
	
	function verticalOnly(item) {
		item.toolManDragGroup.verticalOnly()
	}
	
	function saveOrder(item) {
		var group = item.toolManDragGroup
		var list = group.element.parentNode
		var id = list.getAttribute("id")
		if (id == null) return
		group.register('dragend', function() {
			ToolMan.cookies().set("list-" + id, 
					junkdrawer.serializeList(list), 365)
		})
	}
	
	function save() {
		var items = ToolMan.junkdrawer().serializeList(document.getElementById('phoneticlong'));
		items = items.split("|");
		
		DWRMultiPageAction.saveNewOrder(items, back);
	}
	
	function back() {
		window.location = 'editmultipagechildlist.do?multipage_id=${multiPage.id}&evt=sortUpdated';
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
<body>



  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
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
<div class="contentWrapper" id="contentWrapper">
		  <div class="mainContent">
	  <div class="pageTitle">

	    <h1><a name="addPage"></a><strong>Add New Schedule</strong></h1>

		
			<div class="clear"></div>
			</div>
	  <c:set var="formAction" value="addmultipageitem.do" />
				<c:set var="edititableName" value="${true}" />		
	<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
	
	<c:if test="${company.name == 'hpdsked' and multiPage.name == 'retiroBranch'}">
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
		
	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

			
	    <tr>

		  <td class="label">Name</td>

		  <td align="left"><input type="text" id="singlepage_name" name="singlePage.name" value="${singlePage.name}" class="inputContent" /></td>

		</tr>

			<tr> 
				<td class="label">Specialization<input type='hidden' name='multipage_id' value='${multiPage.id}'></td>
				<td align="left">
				<select name="singlePage.title" onchange="loadDoctors(this)" id="specialization">
					<option value="No selected Specialization" selected  id="doctors">--Select Specialization--</option>
					<c:forEach items="${allCategories}" var="category">
						<option value="${category.name}"  id="doctors" <c:if test="${singlePage.title eq category.name}">selected</c:if>>${category.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			
			<c:set var="container1">
			<c:forEach items="${allItems}" var="item" >
				<c:if test="${multiPage.name=='retiroBranch' and (item.brand.name=='retiro' or item.brand.name=='Retiro') }">
					${item.name}--${item.parent.name} --${item.sku}____
				</c:if>
				<c:if test="${multiPage.name=='events'  and (item.brand.id == 226 or item.brand.name=='HP-Rockwell') }">
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
				<option value="No selected Doctor" selected  id="doctors">--Select Doctor--</option>
				<c:forEach items="${allItems}" var="item">
				<c:if test="${multiPage.name=='retiroBranch' and (item.brand.name=='retiro' or item.brand.name=='Retiro') }">
						<option value="${item.name}"  <c:if test="${fn:indexOf(singlePage.subTitle,item.name)!=-1 or fn:indexOf(item.name,singlePage.subTitle)!=-1}">selected</c:if>>${item.name}</option>
				</c:if>
				<c:if test="${multiPage.name=='events' and (item.brand.id == 226 or item.brand.name=='HP-Rockwell') }">
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



		<c:if test="${singlePage.language== null}"> 
		<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">
	
		</c:if></c:if>
		<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
		
		<c:if test="${company.name == 'hpdsked' and multiPage.name == 'events'}">
			<input type="hidden" name="SinglePage.isRockwellBranch" value="yes">
			<tr>
			<td class="label">Repeats Every:</td>
				<td align="left">
			<%
			String dayArray[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
			request.setAttribute("dayArray",dayArray);
			%>
				<c:forEach begin="0" end="${fn:length(dayArray)-1}" var="i" step="1">
					<input <c:if test="${fn:indexOf(singlePage.daysAvailable,dayArray[i])>=0}"> checked </c:if> type="checkbox" name="SinglePage.checkBoxDays" value="${dayArray[i]}"/>&nbsp;${dayArray[i]}&nbsp;&nbsp;
				</c:forEach>
				
				</td>
			</tr>
			<tr>
			<td class="label"></td>
			<td  align="left">&nbsp;&nbsp;<b>Start time:<b>&nbsp;&nbsp;&nbsp;

			&nbsp;&nbsp;&nbsp;<select name="SinglePage.startingTime"  id="startingTime" style="width:4em;">
			<c:forEach var="i" begin="1" end="12" step="1">
				<option value="<c:choose><c:when test="${i ne _startTime or i+12 ne _startTime}">${i}</c:when><c:otherwise>${i}</c:otherwise></c:choose>" <c:if test="${i+12 eq _startTime}"> selected </c:if><c:if test="${i eq _startTime}"> selected </c:if>>
				${i}
				</option>
			</c:forEach>
			</select> &nbsp;
			<select name="sPmAm" id="sPmAm">
				<option value="AM" <c:if test="${_startTime le 12}">selected</c:if>>AM</option>
				<option value="PM" <c:if test="${_startTime ge 13}">selected</c:if>>PM</option>
			</select>
			<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End time:</b> 
			&nbsp;&nbsp;&nbsp;
			<select name="SinglePage.endingTime" id="endingTime" style="width:4em;">
			<c:forEach var="i" begin="1" end="12" step="1">
				<option value="<c:choose><c:when test="${i ne _endTime or i+12 ne _endTime}">${i}</c:when><c:otherwise>${i}</c:otherwise></c:choose>" <c:if test="${i+12 eq _endTime}"> selected </c:if><c:if test="${i eq _endTime}"> selected </c:if>>

				${i}
				</option>
			</c:forEach>
			</select>&nbsp;
			<select name="ePmAm" id="ePmAm">
			<c:set var="lastPageNo" value="1"/>
			<<option value="AM" <c:if test="${_startTime le 11}">selected</c:if>>AM</option>
			<option value="PM" <c:if test="${_startTime ge 12}">selected</c:if>>PM</option>
			</select>
			</td>
			</tr>
			
		</c:if>
		
		<c:if test="${company.name == 'hpdsked' and multiPage.name == 'retiroBranch'}">
			<%-- 	
			<tr> 
				<td class="label">School</td>
				<td align="left">
				<input type="text" name="singlePage.docSchool" VALUE="${singlePage.docSchool}">
				</td>
			</tr>
			--%>	    
			<tr> 
				<td width="1%" nowrap><input type="hidden" name="SinglePage.isRetiroBranch" value="yes"><b></td>
				<td width="10px"></td>
				<td>
				<a href="/" onclick="addAnotherSchedule();return false;"><strong>Add Another Schedule</strong></a>
				</td>
			</tr>
			</table>
			<table  width="100%" border="0" cellspacing="0" cellpadding="3">
						<script type="text/javascript">
								document.write(document.getElementById("scheduleTable").innerHTML)
						</script>
	        </table>
	        
	        <table id="anotherScheduleContainer"  width="100%" border="0" cellspacing="0" cellpadding="3">
	        </table>
	
		</c:if>
		<c:if test="${singlePage.language== null}"> 
		<table  id="anotherScheduleContainer"  width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr> 
				<td width="1%" nowrap><input type="hidden" name="SinglePage.isRetiroBranch" value="yes"><b></td>
				<td width="10px"></td>
				<td>
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
		<%-- 
		<tr>

		  <td class="label">Description</td>

		   <td align="left">
		 
		  <textarea id="singlePage.content" name="singlePage.content" >${singlePage.content}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'singlePage.content');
			</script>
		  </td>

		</tr>
		--%>
		<tr>
			
		  <td colspan="2" class="label">
			<c:choose>
						<c:when test="${singlePage.id > 0}">
						
							<input type="reset" value="Reset" class="btnBlue">
				<input type="submit" value="Update" class="btnBlue">
						</c:when>
						<c:otherwise>
							
						
				<input type="submit" value="Add" class="btnBlue">
						</c:otherwise>
					</c:choose>
		</td>

		</tr>

	  </table>
</form>			
	</div><!--//mainContent-->


	  <div class="sidenav">
		<h2>Sort Pages <br /> - Drag and Arrange -</h2>
		<ul id="phoneticlong">
			
												<c:forEach items="${multiPageItems}" var="i">
													<li itemID="${i.id}">${i.name}</li>
												</c:forEach>
												
		</ul>
		<ul>
			<li>									<input type="button" value="Save" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='editmultipagechildlist.do?multipage_id=${multiPage.id}'" />
			</li></ul>									
	</div><!--//sidenav-->
    <div class="mainContent">
	  	<s:actionmessage />
				<s:actionerror />
			<c:if test="${param['evt'] != null}">
  						<ul>
  						
  						<c:if test="${param['evt'] == 'update'}">
  							<li><span class="actionMessage">Page was successfully updated.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'updatechild'}">
  							<li><span class="actionMessage">Page item was successfully updated.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'deletechild'}">
  							<li><span class="actionMessage">Page item was successfully deleted.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'save'}">
  							<li><span class="actionMessage">Page item was successfully created.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'addfailed'}">
  							<li><span class="actionMessage">Page cannot be created becase a similar page already exist.</span></li>
  						</c:if>
  						
  						<c:if test="${param['evt'] == 'sortUpdated'}">
  							<li><span class="actionMessage">The item ordering have been updated.</span></li>
  						</c:if>
  						
  						</ul>
  					</c:if>
  					
  					<div class="pageTitle">

	    <h1><strong>Edit Schedules</strong></h1>

		<ul>

		  <li>
		 
					
					
					
					
							<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
						<a href="multipage.do">Multi Pages</a>
					
					</c:if>

					
		  
		  </li>
			<li>&raquo;</li>
		 

		 
		  
		  <li><a href="editmultipage2.do?multipage_id=${multiPage.id}">Edit ${multiPage.name} page</a></li>|
		  <li><a href="#addPage">Add New Page</a></li>
		</ul>
			<div class="clear"></div>
			</div>
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
			
			
							<tr >
								<th >Name</th>
								<th >Hidden</th>
								<th>Valid Until</th>
								<th >Updated On</th>
								<th >Created On</th>
							
								<th >Action</th>
							</tr>
							<c:set var="count" value="0" />
							<c:forEach items="${multiPageItems}" var="i">
							<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
								<td><a id="edit" href="editmultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${i.id}" onclick="return submitToEdit(this);">${i.name}</a></td>
								<td>${i.hidden}</td>
								<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.validTo}" /></td>
								<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.updatedOn}" /></td>
								<td><fmt:formatDate pattern="MMM dd, yyyy" value="${i.createdOn}" /></td>
															
								<td>
									<a id="edit" href="editsinglepage.do?singlepage_id=${i.id}" onclick="return submitToEdit(this);">Edit</a>
									<!-- <a id="edit" href="editmultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${i.id}" onclick="return submitToEdit(this);"> | Edit as a child</a> -->
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
										  |
										<a id="delete" href="deletemultipagechild.do?multipage_id=${multiPage.id}&singlepage_id=${i.id}" onclick="return confirm('Do you really want to delete this item?');">Delete</a>
									</c:if>	
									|
								<%-- 
									<c:if test="${imageCount > 0}" >
										<img src="images/imageIcon.png" title="${imageCount}" />
									</c:if>
								
								<c:if test="${i.fileCount > 0}"	>
										<img src="images/folderIcon.png" title="${i.fileCount}" />
								</c:if>
								--%>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						
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