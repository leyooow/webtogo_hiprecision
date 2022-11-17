<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="../javascripts/Ajax.js" type="text/javascript"></script>

<script type="text/javascript" src="../javascripts/wz_tooltip.js"></script>
<script type="text/javascript" src="../javascripts/tip_centerwindow.js"></script>

<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />

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
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Name not given";
			error = true;
		}
		else {
			document.getElementById('singlepage_name').value = document.getElementById('singlepage_name').value;
		}
		 
		if(error) {
			alert(messages);
		}
		else {
			//if(document.getElementById("multipageID").value=="1111")
					{
				//alert("----YOHANTENMA-----"+document.getEelementById("__"+document.getElementById("_itemList").value));
				//		document.getElementById("_1111_room").value=document.getElementById("__"+document.getElementById("_itemList").value).innerHTML;//__itemList
					//	alert(document.getElementById("_1111_room").value);
				//		document.getElementById("_1111_category").value=document.getElementById(document.getElementById("_categoryList").value).innerHTML//categoryList
				//		document.getElementById("_1111_item").value=document.getElementById(document.getElementById("_itemList").value).innerHTML;//itemList
				//		document.getElementById("_categoryList").value=document.getElementById(document.getElementById("_categoryList").value).innerHTML
					//	document.getElementById("_itemLit").value=document.getElementById(document.getElementById("_itemLit").value).innerHTML;//itemLit
						//
				//	return false;
				//	}
			//else
				
			return true;
		}
		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
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
	
	var maximum = 0; // maximum # of attachments allowed
	var currentDynamicUploads = 0; // current # of attachment sections on the web page
	var nameDynamicDesc = ''; // Name property for the Description Input field
	var nameDynamicFile = ''; // Name property for the File Input field
	var inputTypeID = ''; // Name property for the File Input field
	var scrollPosVert = 0; // stores the current scroll position on the form

	//CONSTANTS
	var separatorChar = '_';
	var ADD_UPLOAD = 'addupload' + separatorChar;
	var ATTACHMENT = 'attachment' + separatorChar;
	var ATTACHMENTS = 'attachments' + separatorChar;
	var DROP_CAP = 'dropcap' + separatorChar;
	var ATTACHMENT_MARKER = 'attachmentmarker' + separatorChar;
	var ATTACH_MSG = 'Attach another ';
	
	function addDynamicUpload(maximumUploads, descFieldName, fileFieldName, uniqueFileType) {
		nameDynamicDesc=descFieldName;
		nameDynamicFile=fileFieldName;
		maximum = Number(maximumUploads);

		currentDynamicUploads++;
		if (currentDynamicUploads>maximum){
			return;
		} 

		var addUploadID = ADD_UPLOAD + uniqueFileType; 
		if (currentDynamicUploads>0){
			document.getElementById(addUploadID).childNodes[0].data=(ATTACH_MSG + uniqueFileType);
		}

		if (currentDynamicUploads==maximum){
			document.getElementById(addUploadID).style.visibility='hidden';
		}

		var rootAttachmentFormID = ATTACHMENT + uniqueFileType;
		// First, clone the hidden attachment section
		var newFields = document.getElementById(rootAttachmentFormID).cloneNode(true);

		//Attach a file
		newFields.id = '';
		
		// Make the new attachments section visible
		newFields.style.display = 'block';


		// loop through tags in the new Attachment section
		// and set ID and NAME properties
		//
		// NOTE: the control names for the Description Input
		// field and the file input field are created
		// by appending the currentDynamicUploads variable
		// value to the nameDynamicFile and nameDynamicDesc values
		// respectively
		//
		// In terms of Xaraya, this means you'll need to name your
		// DD properties will need names like the following:
		// "AttachmentDesc1"
		// "AttachmentFile1"
		// "AttachmentDesc2"
		// "AttachmentFile2"
		// "AttachmentDesc3"
		// "AttachmentFile3"
		// et cetera...

		var newField = newFields.childNodes;
		var dropCapID = DROP_CAP + uniqueFileType;
		for (var i=0;i<newField.length;i++) {
			if (newField[i].name==nameDynamicFile) {
				newField[i].id=nameDynamicFile+currentDynamicUploads;
				newField[i].name=nameDynamicFile;
			}

			if (newField[i].name==nameDynamicDesc) {
				newField[i].id=nameDynamicDesc+currentDynamicUploads;
				newField[i].name=nameDynamicDesc
			}
			
			if (newField[i].id==dropCapID) {
				newField[i].id=dropCapID+currentDynamicUploads;
				newField[i].childNodes[0].data=currentDynamicUploads;
			}
		}


		// Insert our new Attachment section into the Attachments Div
		// on the form...
		var attachmentMakerID = ATTACHMENT_MARKER + uniqueFileType;  
		var insertHere = document.getElementById(attachmentMakerID); 
		insertHere.parentNode.insertBefore(newFields,insertHere);
	}
	
</script> 

<div style="width:100%;">

<table border="0">
	<tr>
		<td style="border:0px;" valign="top">
	
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		
		<c:if test="${singlePage.id > 0}">
		<h1>Language:
		<c:if test="${singlePage.language!= null}"> 
		${singlePage.language.language}
		</c:if>
		<c:if test="${singlePage.language== null}"> 
		English
		</c:if>
		</h1>
		
		<input type="hidden" name="update" value="1">
		<input type="hidden" name="singlepage_id" value="${singlePage.id}">
		</c:if>
		
		<c:if test="${multiPage != null}">
		<input type="hidden" name="multipage_id" value="${multiPage.id}">
		</c:if>
		
		<table width="50%">
					
			<tr>
				<td colspan="3">
				Languages:
				<a href="editsinglepage.do?singlepage_id=${param.singlepage_id}">English</a> , 
				<c:forEach items="${languages}" var="lang">
					<a href="editsinglepage.do?singlepage_id=${param.singlepage_id}&language=${lang.language}">${lang.language}</a> , 
				</c:forEach>
				<input type="hidden" name="language" value="${param.language}">
				</td>
			</tr>
		
			<c:if test="${singlePage == null}">
			<tr class="headbox">
				<th colspan="3">
					New Page
				</th>
			</tr>
			</c:if>
			
				<tr>
					<td width="1%" nowrap><b>Name</td>
					<td width="10px"></td>
					<td>
					<input type="text" id="singlepage_name" name="singlePage.name" value="${singlePage.name}" class="textbox4">
					</td>
				</tr>
			<c:if test="${singlePage.language== null}"> 
				<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">
				<tr>
					<td width="1%" nowrap><b>JSP</td>
					<td width="10px"></td>
					<td><input type="text" id="singlepage_jsp" name="singlePage.jsp" value="${singlePage.jsp}" class="textbox4"></td>
				</tr>
				</c:if>
			</c:if>
			<!-- -------------------------------MODIFIED----------------------------------- -->
			
			<!-- IF --><!-- DAPAT NASA COMPANY SETTINGS ITO -->
			<%--<c:if test="${multiPage.id== 1111}"> LOCAL DATA  1097 --><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID  --%>
			<!-- -->	<c:if test="${multiPage.id== 1111  or singlePage.docRoom != null or singlePage.startTime != null}">  <!-- 1097 is the mutiPage of HP Doctors Schedule -->
			
			<tr> 
				<td width="1%" nowrap><b>Category<a href="items.do?group_id=205&listOfAllDoctors=yes"></a></td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.title" value="${singlePage.title}" class="textbox4" id="doctors">
				<!-- 
				<script language="javascript" >
				displayPage("items.do?group_id=205&allCategoryList=yes","_categoryList");

				
					function selectCategory(){
					var cat=document.getElementById("_categoryList").value
					//category_id
					//FILTER THE RESULT BY CATEGORY
					displayPage("items.do?group_id=205&allItemsList=yes&category_id="+cat,"_itemList");
					//alert("THE VALUE IS"+cat);
					}
					function selectItem(){
						var item=document.getElementById("_itemList").value
						//alert("THE VALUE IS"+item);
						}
				</script>
				<select name="singlePage_title" value="${singlePage.title}" class="textbox4" id="_categoryList""> 
				
				
				<script language="javascript" >
								//this ajax query is located at ajaxQuery/displayCategoryList.jsp
				</script>	
				</select>
				 -->
				</td>
			</tr>
			<tr>
				<td width="1%" nowrap><b>Item</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.subTitle" value="${singlePage.subTitle}" class="textbox4">
				<!--  
				<select name="singlePage_subTitle" value="${singlePage.subTitle}" class="textbox4" id="_itemList" onchange="selectItem()"> 
				<script language="javascript" >
				 			//this ajax query is located at ajaxQuery/displayItemList.jsp
					displayPage("items.do?group_id=205&allItemsList=yes&displayNone=true","_itemList");
				</script>
				</select>		
			<!-- -para sa submiiiiiiiiiiiiiiiiiiiiiiiiiiiiiit -->
			<!-- -FOR MULTIPAGE ID 1111 LANG
			<input type="hidden" name="singlePage.title" id="_1111_category" VALUE="">
			<input type="hidden" name="singlePage.subTitle" id="_1111_item" VALUE="">
			 -->
			<input type="hidden" name="singlePage.room" id="_1111_room" VALUE="208">
			<!-- -FOR MULTIPAGE ID 1111 LANG -->
				</td>
			<%
			String dayArray[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
			request.setAttribute("dayArray",dayArray);
			%>
			<tr>
			<td width="1%" nowrap><b>Repeats Every:</b></td>
				<td width="10px"></td>
				
				<td>
				
				<c:forEach begin="0" end="${fn:length(dayArray)-1}" var="i" step="1">
					<input <c:if test="${fn:indexOf(singlePage.daysAvailable,dayArray[i])>=0}"> checked </c:if> type="checkbox" name="SinglePage.checkBoxDays" value="${dayArray[i]}"/>&nbsp;<b>${dayArray[i]}</b>&nbsp;&nbsp;
				</c:forEach></td>
			</tr>
			<tr>
			<td width="1%" nowrap><b></td>
			<td width="10px"></td>
			<td>&nbsp;&nbsp;<b>Start time:<b>&nbsp;&nbsp;&nbsp;
			<input type="hidden" name="SinglePage.hasAdjustableSchedule" value="yes">
			&nbsp;&nbsp;&nbsp;<select name="SinglePage.startingTime"  id="startingTime" style="width:4em;">
			<%
			int counter=1;
			for(int i=1;i<=12;i++){
				out.print("<option  value=\""+i+"\">&nbsp;&nbsp;&nbsp;");
				out.print(counter);
				out.print("</option>");
				counter++;
			}
			%>
			</select> &nbsp;
			<select name="sPmAm" id="sPmAm">
				<option value="AM" selected >AM</option>
				<option value="PM">PM</option>
			</select>
			<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End time:</b> 
			&nbsp;&nbsp;&nbsp;<select name="SinglePage.endingTime" id="endingTime" style="width:4em;">
			<%
			counter=1;
			for(int i=1;i<=12;i++){
				out.print("<option  value=\""+i+"\">&nbsp;&nbsp;&nbsp;");
				out.print(counter);
				out.print("</option>");
				counter++;
			}
			%>
			</select>&nbsp;
			<select name="ePmAm" id="ePmAm">
			<option value="AM" selected default>AM</option>
			<option value="PM">PM</option>
			</select>
			</td>
			</tr>
			</c:if>
			<!-- ELSE -->
			
			<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}">
				<%-- <c:if test="${multiPage.id== 1111}"> 1097 local ONLY--><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID --%>
			<c:if test="${  singlePage.docRoom == null and singlePage.startTime == null  and multiPage.id  != 1111 }"> 
			<tr> 
				<td width="1%" nowrap><b>Title</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.title" value="${singlePage.title}" class="textbox4"></td>
			</tr>
			<tr>
				<td width="1%" nowrap><b>Sub Title</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.subTitle" value="${singlePage.subTitle}" class="textbox4"></td>
			</tr>

			</c:if>
			<!-- END IF -->
		
			<c:if test="${singlePage.language== null}"> 
				<tr>
					<td width="1%" nowrap><b>Date</b></td>
					<td width="10px"></td>
					<td>
					
					<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
									 
					<input type="text" id="item_date" name="itemDate" value="${idate}" class="textbox2" /> 
					<a href="javascript:;" id="item_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
					   
					  
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
				
				<c:if test="${multiPage != null }">
				<tr>
					<td nowrap><b>Valid Until</td> 
					<td width="10px"></td> 
					<td> 
					
					<fmt:formatDate value="${singlePage.validTo}" pattern="MM-dd-yyyy" var="validTo" />
					 				
					<input type="text" id="validTo" name="singlePage.validTo" value="${validTo}" class="textbox2" readonly="readonly">
					<a href="javascript:;" id="event_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar" title="Open Calendar"/></a>
					   
					  
					<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "validTo",     // id of the input field
					        ifFormat       :    "%m-%d-%Y",      // format of the input field
					        button         :    "event_date_button",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true
					    });
					</script>
					
					</td>
				</tr>
				</c:if>
				
				<!-- MARKED! -->
				<c:if test="${(singlePage.id > 0 && multiPage == null && (user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'))}">
				<tr>
					<td nowrap>&nbsp;</td>
					<td></td>
					<td>
					
					<s:checkbox name="singlePage.isHome" value="%{singlePage.isHome}" theme="simple" /> <i><b>This page is the default home page.</i>
					
					</td>
					
				</tr>
				</c:if>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td nowrap>&nbsp;</td>
					<td></td>
					<td>
					
					<s:checkbox name="singlePage.isSingleFeatured" value="%{singlePage.isSingleFeatured}" theme="simple" /> <i><b>This page is a featured page.</i>
					
					</td>
					
				</tr>
				</c:if>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td nowrap>&nbsp;</td>
					<td></td>
					<td>
					
					<s:checkbox name="singlePage.isForm" value="%{singlePage.isForm}" theme="simple" /> <i><b>This is a form page.</i>
					
					</td>
					
				</tr>
				</c:if>
				
				<c:if test="${singlePage.isHome == false || user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
					<tr>
						<td nowrap>&nbsp;</td>
						<td></td>
						<td>
						
						<s:checkbox name="singlePage.hidden" value="%{singlePage.hidden}" theme="simple" /> <i><b>Hide this page in the website.</i>
						
						</td>
						
					</tr>
					
				</c:if>
				
				
				
				<c:if test="${companySettings.hasMemberFeature == true}">
					<c:if test="${singlePage.loginRequired == false || user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
					<tr>
						<td nowrap>&nbsp;</td>
						<td></td>
						<td>
						
						<s:checkbox name="singlePage.loginRequired" value="%{singlePage.loginRequired}" theme="simple" /> <i><b>This page requires member login.</i>
						
						</td>
						
					</tr>
					</c:if>
				</c:if>
			</c:if>
			<tr>
				<td width="1%" nowrap valign="top"><b>Content</td>
				<td width="10px"></td>
				<td>
			 		
			 		<%--<FCK:editor id="singlePage.content" width="98%" height="400" toolbarSet="${(user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator') ? 'Custom_SuperUser' : 'Custom'}">${singlePage.content}</FCK:editor>--%>
			 		
			 				
			 		
			 		
					<div style="float: right; padding: 5px;">NOTE: Press SHIFT+ENTER for a SINGLE SPACED carriage return.</div>
						 
				</td>
			</tr>
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
				
						
					
				</table>
		</form>
		
		</td>
		
		<c:choose>
			<c:when test="${singlePage.id > 0}">
			
			<td width="40%" bgcolor="#efefef" valign="top"> 
			
				<div id="uploadimageform"> 
				
				<c:choose>
					<c:when test="${multiPage != null}">
						<form method="post" action="uploadmultipageitemimage.do" enctype="multipart/form-data">
						<input type="hidden" name="multipage_id" value="${multiPage.id}" />	
					 
					</c:when> 
					<c:otherwise>
						<form method="post" action="uploadsinglepageimage.do" enctype="multipart/form-data">
					</c:otherwise>
				</c:choose>
				
					<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
								
					<table width="100%" cellpadding="0" cellspacing="2" border="0">
						
						<c:if test="${fn:length(singlePage.images) > 0}">
						<tr>
							<td style="border:0px;"><h2>Image List</h2></td>
						</tr>
						<tr>
							<td style="border:0px;">
														
							<table>
								<c:forEach items="${singlePage.images}" var="img">
								<tr>
									<td style="border:0px;" colspan="2"><b>${img.filename}</b></td>
								</tr>
								<tr bgcolor="#f5f5f5">
									<td style="border:0px;" width="1%" valign="top"> 
																				 
									<img src="${contextParams['IMAGE_PATH']}/images/pages/${img.thumbnail}" />
									
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
									<a onclick="return confirm('Do you really want to delete this image?');" href="deletesinglepageimage.do?singlepage_id=${singlePage.id}&multipage_id=${multiPage.id}&image_id=${img.id}">Delete</a>
									</c:if>										
									</td>
									<td style="border:0px;" valign="top">
									
									<table>
										<tr>
											<td>Title</td>
										</tr>
										<tr>
											<td><input type="text" name="image_title_${img.id}" value="${img.title}" class="textbox4" onblur="updateImageTitle(${img.id}, this.value);"></td>
										</tr>
										<tr>
											<td>Caption</td>
										</tr>
										<tr>											
											<td><input type="text" name="image_caption_${img.id}" value="${img.caption}" class="textbox4" onblur="updateImageCaption(${img.id}, this.value);"></td>
										</tr> 
										<tr>
											<td>Description</td>
										</tr>
										<tr>
											<td>
												<textarea class="textbox4" onblur="updateImageDescription(${img.id}, this.value);">${img.description}</textarea>
											</td>
										</tr>
									</table>
									
									</td>
								</tr>
								<tr bgcolor="#ffffff">
									<td style="border:0px;" colspan="2" height="5px"></td>
								</tr>
								</c:forEach>
							</table>
							 
							</td>
						</tr>
						</c:if>
						
						
						
						<c:if test="${allowedImageCount > 0}">
						<tr>
							<td style="border:0px;">
							<h2>Upload Image</h2>
							<br>
							
							<font color="#ff0000">
							* You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> files.
							<br> 
							* <b>${companySettings.image1Width} x ${companySettings.image1Heigth}</b> is the most advisable size of image that should be uploaded.
							</font>
							
							</td>
						</tr>
						<tr> 
							<td style="border:0px;">
							
								<div id="attachment" class="attachment" style="display:none">
									<div id="dropcap" class="dropcap">1</div>
								    
								    <input id="file" name="upload" type="file" size="30" />
								    <!-- 
								    <input id="desc" name="alt" type="text" size="30"/>
								    &nbsp;
								    --> 
								    <a href="#" onclick="javascript:removeFile(this.parentNode.parentNode,this.parentNode);">Remove</a>
								    </div>
								    
								    <div id="attachments" class="container">
								    <!--
								    **************************************************************************
								    Below is the call to the "addUpload" function which adds an Attachment
								    section to the form. Customize the function parameters as needed:
								    1) Maximum # of attachments allowed in this pub type
								    2) base Field Name for the Description fields in your pub type
								    3) base Field Name for the File Upload field in your pub type
								    **************************************************************************
								    -->
								     <span id="attachmentmarker"></span>
								    <div align="right">
							    	 <a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload'); ">Attach an image</a>
								    
									<input type="submit" value="Upload Image!" class="btnBlue"/>
								</div>
							</div>
							</td>
						</tr>
											
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
						<tr>
							<td style="border:0px;">
							
								<div align="center">
								<input type="submit" value="Upload Now!" class="upload_button2" />
								</div>
							 
							</td>
						</tr>
						</c:if>
						
						<tr>
							<td style="border:0px;" height="30px">&nbsp;</td>
						</tr>
					<%-- tr> 
						<td nowrap>HTML Author</td>						
						<td nowrap="nowrap"><input type="text" id="" name="company2.metaAuthor" value="${company2.metaAuthor}" class="textbox4" />
						</td>
					</tr --%>
					
					<%-- tr> 
						<td nowrap>HTML Copyright</td>
						<td><input type="text" name="company2.metaCopyright" value="${company2.metaCopyright}" class="textbox4" /></td>
					</tr --%>				
					
						
					</table>
				
				</form>


		<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
			
			<c:if test="${singlePage.id > 0}">
			<input type="hidden" name="update" value="1">
			<input type="hidden" name="singlepage_id" value="${singlePage.id}">
			</c:if>
		
		<c:if test="${multiPage != null}">
		<input type="hidden" name="multipage_id" value="${multiPage.id}">
		</c:if>
					<table style> 
					<tr class="headbox" align="left">
						<th colspan="2"> HTML META Settings</th>
					</tr>
					<tr >
						<td colspan="2">  </td>
					</tr>	
					
					<tr> 
						<td nowrap>META Title</td>
						<td><textarea name="singlePage.meta_title" cols=40  rows=2>${singlePage.meta_title}</textarea></td>
							
					</tr>
						 		
					<tr>
						<td nowrap>META Description</td>							
						<td><textarea name="singlePage.meta_description" cols=40  rows=2>${singlePage.meta_description}</textarea></td>
						
					</tr>
					
					<tr>
						<td nowrap>META Keywords</td>
						<td><textarea name="singlePage.meta_keywords" cols=40  rows=2>${singlePage.meta_keywords}</textarea></td>
						
					</tr>
					
					<tr> 
						<td nowrap>META Author</td>						
						<td nowrap="nowrap"><input type="text" id="" name="singlePage.meta_author" value="${singlePage.meta_author}" size = "55"/>
						</td>
					</tr>
					
					<tr> 	
						<td nowrap>META Copyright</td>
						<td><input type="text" name="singlePage.meta_copyright" value="${singlePage.meta_copyright}" size = "55"/></td>
					</tr>				
					<tr>
						<td colspan="2" > </td>
					</tr>
					<tr>
				
			</tr>
			<tr>			
			<td style="border: 0px;">
			
							<input type="submit" value="Update" class="upload_button2">
							<input type="reset" value="Reset" class="upload_button2">
							
			</td>
			</tr>	
			
		
							
				</form>
					<c:if test="${pagecomments != null}">
					<table width="100%" class="tblcommentprod">
					<tr class="headbox" align="left">
						<th colspan="2"> Comments</th>
					</tr>
					<c:if test="${fn:length(pagecomments) == 0}">
						<tr><td>No comments.</td></tr>
					</c:if>
					<c:if test="${fn:length(pagecomments) > 0}">
					<%-- 	<c:forEach items="${pagecomments}" var="pagecomments">
						<tr>
							<td width="50%"><div class="txtname">${pagecomments.firstname} ${pagecomments.lastname} <font style="font-weight:bold">says:</div></font></td>
							<td class="comdate" align="right"><img src="images/calendar.jpg" style="margin-right:5px">${pagecomments.createdOn}</td>
							<td><a href="deletecomment.do?commentid=${pagecomments.id }">Delete</a></td>
						</tr>
						<tr>
							<td class="commenttxt" colspan="2">${pagecomments.content}</td>
						</tr>
						<tr>
							<td></td>
						</tr>
						</c:forEach>  --%>
						 <table width="100%" border="0" cellspacing="0" cellpadding="7" class="payments">

									<tr>
										<td></td>
										<td width="10%">Date</td>	
										<td>User</td>	
										<td>Comment</td>
																		
										<td>Action</td>
										
									</tr>
										<c:forEach items="${pagecomments}" var="comment" varStatus="counter">
											<tr>
												<td ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }>&nbsp;</td>
												<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }><fmt:formatDate pattern="MM-dd-yyyy hh:ss a" value="${comment.createdOn}" /></td>
	
												<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }>${comment.firstname } ${comment.lastname }</td>	
												<c:if test="${fn:length(comment.content) >70}">
												<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }><c:out value="${fn:substring(comment.content, 0, 10)}" />&nbsp;&nbsp;<a href="javascript:void(0);" onmouseover="Tip('${comment.content}')" onmouseout="UnTip()" style="text-decoration:none"><strong>Read More</strong></a></td>
												</c:if>
												<c:if test="${fn:length(comment.content) <= 70}">
												<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }>${comment.content }</td>
												</c:if>
														 								
												<td nowrap align="center" ${(counter.count mod 2 eq 0) ? 'class="even"' : '' }><a href="deletecomment.do?singlepage_id=${singlePage.id}&commentid=${comment.id }" onClick="return confirm('Are you sure you want to delete this comment?');">Delete</a></td>
											</tr>
										</c:forEach>
								</table>
					
					</c:if>
					</table>
				</c:if>
<%--
				<c:if test="${singlePage.hasFile}"> 
				<form method="post" action="uploadsinglepagefile.do" enctype="multipart/form-data">
				 
				<input type="hidden" name="singlepage_id" value="${singlePage.id}" />
				
				<table>
					<c:choose>
						<c:when test="${allowedFileCount > 0}">
					
						<tr>
							<td style="border:0px;"><h2>Upload File Attachment</h2></td>
						</tr>
						<tr>
							<td>
							
							<input type="file" name="upload" />
							<input type="submit" value="Upload" /> 
							
							</td>
						</tr>
						
						</c:when>
						<c:otherwise>
						
						<tr>
							<td style="border:0px;"><h2>Uploaded File Attachment</h2></td>
						</tr>
						<tr>
							<td valign="top">
							
							${singlePage.files[0].fileName}
							(
							<a target="_blank" href="${contextParams['FILE_PATH']}/${singlePage.files[0].filePath}">View</a> | 
							<a onclick="return confirm('Do you really want to delete this image?');" href="deletesinglepagefile.do?singlepage_id=${singlePage.id}&file_id=${singlePage.files[0].id}">Delete</a>
							) 
							
							</td>
						</tr>
						
						</c:otherwise>
					</c:choose> 
				</table>
				
				</form>
				</c:if>
--%>
				<div>	

				<c:if test="${singlePage.hasFile}">
					<!--=================================== UPLOAD ANY FILE SECTION ================================-->
					<!-- DECLARE value of dynamicType variable (any unique name for input type IDs) -->
					<c:set var="dynamicType" value="file" />
					<c:if test="${isMultiPage ne true}">
						<%@include file="includes/uploadfilesform.jsp" %>
					</c:if>
					<c:if test="${isMultiPage eq true}">
						<%@include file="includes/uploadfilesformmultipage.jsp" %>
					</c:if>
						
				</c:if>  
			
			</td>
			
			</c:when>
			<c:otherwise>
				<td width="40%" valign="top"></td>
			</c:otherwise>
		</c:choose>
		
	</tr>
</table>

<script>
	addUpload(${allowedImageCount}, 'alt', 'upload'); 
	addDynamicUpload(${allowedUploadedFileCount}, 'alt', 'upload', '${dynamicType}');  
</script> 

</div>