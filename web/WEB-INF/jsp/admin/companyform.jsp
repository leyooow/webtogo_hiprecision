<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />
<!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="events" />
 

<script  type="text/javascript">
	var counter=-1;

	function function1() {
	//    var n = document.createElement("span");
	//    n.innerHTML = " <tr><td colspan='3'><input type='text' name='fromPrice_"+(counter)+"'>"+"<input type='text' name='toPrice_"+(counter)+"'>"+"<input type='text' name='shippingPrice_"+(counter)+"'></td></tr>";
	//    document.getElementById("myList").appendChild(n); 
	    counter=counter+1;
	} 
	
	function function2() {
		//document.getElementById("myList").removeChild(document.getElementById("myList").lastChild);
		counter=counter-1;
	}

	function toggle(){
		if(document.getElementById("shippingType").value=='fixed'){
		document.getElementById("fixedrate1").style.display = 'block';
		document.getElementById("fixedrate2").style.display = 'block';
		document.getElementById("fixedrate13").style.display = 'none';
		document.getElementById("shiptable100").style.display = 'none';

		}
		else if(document.getElementById("shippingType").value=='ranged'){
		document.getElementById("shiptable100").style.display = 'block';

		document.getElementById("fixedrate13").style.display = 'none';
		document.getElementById("fixedrate1").style.display = 'none';
		document.getElementById("fixedrate2").style.display = 'none';
		}
		else if(document.getElementById("shippingType").value=='individual'){
			document.getElementById("shiptable100").style.display = 'none';

			document.getElementById("fixedrate13").style.display = 'block';
			document.getElementById("fixedrate1").style.display = 'none';
			document.getElementById("fixedrate2").style.display = 'none';
		}
		else{
			document.getElementById("shiptable100").style.display = 'none';

			document.getElementById("fixedrate13").style.display = 'none';
			document.getElementById("fixedrate1").style.display = 'none';
			document.getElementById("fixedrate2").style.display = 'none';
		}
	}


	   			function addRow(tableID,ct) {

		   			if(counter==-1){
			   			counter=ct;
		   			}  
		   			if(counter==20){
		   				alert("You reached the Range table limit");  
		   				return false;
		   			}
		             var table = document.getElementById(tableID);  
		   
		             var rowCount = table.rows.length;  
		             var row = table.insertRow(rowCount-1);  
		   
		             var cell1 = row.insertCell(0);  
		             var element1 = document.createElement("input");  
		             element1.type = "checkbox";  
		             cell1.appendChild(element1);  
		   
		          

		            var cell3 = row.insertCell(1);  
		             var element2 = document.createElement("a");  
		             element2.innerHTML = "<input type='text' class='textbox4' name='toPrice_"+(counter)+"'>";  
		            cell3.appendChild(element2);  

		            var cell3 = row.insertCell(2);  
		             var element2 = document.createElement("a");  
		             element2.innerHTML = "<input type='text' class='textbox4' name='shippingPrice_"+(counter)+"'>";  
		            cell3.appendChild(element2);  
		            counter=counter+1;

		            var cell3 = row.insertCell(3);  
		            var element2 = document.createElement("a");  
		             element2.innerHTML = "<input type='hidden' id='from_price' name='p' value='0' class='textbox4' />";  
		           cell3.appendChild(element2);  
		         }  
		   
		         function deleteRow(tableID) {  
		             try {  
		             var table = document.getElementById(tableID);  
		             var rowCount = table.rows.length;  
		   
		             for(var i=0; i<rowCount; i++) {  
		                 var row = table.rows[i];  
		                 var chkbox = row.cells[0].childNodes[0];  
		                 if(null != chkbox && true == chkbox.checked) {  
		                     table.deleteRow(i);  
		                     rowCount--;  
		                     i--;  
		                 }  
		   
		             }  
		             }catch(e) {  
		                 alert(e);  
		             }  
		             counter=counter-1;
		         }  

	function submitForm(myForm) {
		//general information
		
		var name 			= myForm['company_name'].value;		
		var nameEditable 	= myForm['name_editable'].value;
		var email			= myForm['company_email'].value;		
		var address 		= myForm['company_address'].value;
		var phone 			= myForm['company_phone'].value;
		var contactPerson 	= myForm['contact_person'].value;

		if ((myForm['user_type'].value == 'Super User' ) || (myForm['user_type'].value == 'WTG Administrator')){
			//image settings
			var image1Width			= myForm['image1_width'].value;
			var image1Height		= myForm['image1_height'].value;
			var image2Width			= myForm['image2_width'].value;
			var image2Height		= myForm['image2_height'].value;
			var image3Width			= myForm['image3_width'].value;
			var image3Height		= myForm['image3_height'].value;
			var maxAllowedImages	= myForm['max_allowed_images'].value;
			//webtogo settings			
			var domainName 			= myForm['domain_name'].value;
			var monthlyCharge		= myForm['monthly_charge'];
			var maxFeaturedPages 	= myForm['max_featured_pages'];
			var maxFeaturedProducts	= myForm['max_featured_products'];
			var maxCategoryMenu		= myForm['max_category_menu'];
			var productsPerPage		= myForm['products_per_page'];
			var packagesPerPage		= myForm['packages_per_page'];	
		}
		
		//var businessType = myForm['company_businessType'].value;
		//var email = myForm['company_email'].value;
		//var keywords = myForm['keywords'].value;
		//var title = myForm['title'].value;
		var emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Company name not given\n";
			error = true;
		}
		if(nameEditable.length == 0) {
			messages += "* Full company name not given\n";
			error = true;
		}
		if(address.length == 0) {
			messages += "* Address not given\n";
			error = true;
		}
		if(phone.length == 0) {
			messages += "* Phone not given\n";
			error = true;
		}
		if(contactPerson.length == 0) {
			messages += "* Contact person not given\n";
			error = true;
		}

		if ((myForm['user_type'].value == 'Super User' ) || (myForm['user_type'].value == 'WTG Administrator')){
			if(image1Width.length == 0) {
				messages += "* Image 1 width not given\n";
				error = true;
			}
			if(image1Height.length == 0) {
				messages += "* Image 1 Height not given\n";
				error = true;
			}
			if(image2Width.length == 0) {
				messages += "* Image 2 width not given\n";
				error = true;
			}
			if(image2Height.length == 0) {
				messages += "* Image 2 height not given\n";
				error = true;
			}
			if(image3Width.length == 0) {
				messages += "* Image 3 width not given\n";
				error = true;
			}
			if(image3Height.length == 0) {
				messages += "* Image 3 height not given\n";
				error = true;
			}
			if(domainName.length == 0) {
				messages += "* Domain name not given\n";
				error = true;
			}			
		}
//	if(businessType.length == 0) {
//		messages += "* Business type not given\n";
//		error = true;
//		}
//	alert(messages + " " + error);		
//		if(email.length == 0) {
//			messages += "* Email not given\n";
//			error = true;
//		}

//		if(expiryDate.length == 0) {
//			messages += "* Expiry Date not given\n";
//			error = true;
//		}
//		alert(messages + " " + error);		
//		if(keywords.length == 0) {
//			messages += "* Keywords not given\n";
//			error = true;
//		}
//		alert(messages + " " + error);		
//		if(title.length == 0) {
//			messages += "* Title not given\n";
//			error = true;
//		}
//		alert(messages + " " + error);

		if(email.length != 0){
			if (email.indexOf(",")== -1)
			 {
				if	(email.search(emailRegEx) == -1 ){
					messages += "* Email not in valid format\n";
		  			error = true;
				}
			 }else{
						
				var emailArray = email.split(",");
				var part_num=0;
				while (part_num < emailArray.length)
	 			{
		 			if	(emailArray[part_num].search(emailRegEx) == -1 ){			 			
						messages += "* One of the email is not in valid format\n";
			  			error = true;
			  			break;
					}
					part_num+=1;
				}
			}
		}else{
			messages += "* Email not given\n";
  			error = true;
		}
		if(error) {
			alert(messages);
		}
		else {
			message="Are you sure you want to change the company settings?";
			if (confirm(message)){
				return true;
			}
		}		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		value = value.replace(/^\s+|\s+$/, '');
		return value;
	}
	
	function matchTextfield(FormName, CheckboxName, HiddenName ,TextFieldName)
	{			
		if(!document.forms[FormName])
			return;
		var objCheckBox = document.forms[FormName].elements[CheckboxName];
		if(!objCheckBox)
			return;		

		if (objCheckBox.checked == true) {
			if (TextFieldName.length > 0){
				document.forms[FormName].elements[TextFieldName].disabled=false;
			}
			document.forms[FormName].elements[HiddenName].value=true;
		}
		else{
			if (TextFieldName.length > 0){
				document.forms[FormName].elements[TextFieldName].disabled=true;
				
			}
			document.forms[FormName].elements[HiddenName].value=false;
		}
	}
	function isNumeric(elem){
		var numericExpression = /^[0-9]+$/;
		if(elem.value.match(numericExpression)){
			return true;
		}else{			
			elem.focus();
			return false;
		}
	}

	var showToMainFilterField = function(srcObj) {
		var divObj = document.getElementById("toMainFilterDiv");
		if(srcObj.checked){
			divObj.style.display = "inline";
		} 
		else {
			divObj.style.display = "none";
		}				
	};

	
</script>


<script language="JavaScript" src="../javascripts/overlib.js"></script>

<script>
function showMessage(content) {	
	overlib(content, STICKY, NOCLOSE)}
</script>


<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<form name="newCompanyForm" method="post" action="savecompany.do" onsubmit="return submitForm(this)">
<div style="width:100%;">
	<table border="0" >
	<tr>
			<td colspan="2" ><h3>Company Information</h3></td>
	</tr>

		<tr>
			<td width="60%" valign="top">
				<input type="hidden" name="user_type"  value="${user.userType.value}"/>
				<input type="hidden" name="company_id"  value="${company2.id}"/>
				<table style> 
					<tr class="headbox" align="left">
						<th colspan="2"> General Information </th>
					</tr>
					<tr >
						<td colspan="2">  </td>
					</tr>	
							 		
					<tr>
						<td nowrap>Company Name</td>							
						<td nowrap><input type="text" id="company_name" name="company2.name"  <c:if test="${company2.id != null}">disabled="disabled" </c:if> value="${company2.name}" class="textbox6">
							<c:if test="${company2.id == null}">
								<font color="red">* &nbsp;</font> 
							</c:if>
						</td>
					</tr>
					<tr>
						<td nowrap>Full Company Name</td>							
						<td nowrap><input type="text" id="name_editable" name="company2.nameEditable" value="${company2.nameEditable}" class="textbox6">
							<font color="red">* &nbsp;</font>
						</td>
					</tr>
					<tr> 
						<td nowrap>Company Email <img style="cursor:pointer;" src="images/note.jpg" onmouseover="showMessage('This will be used in all auto emails from the System. Multiple emails can be used if separated by a comma (,).');"> </td></td>							
						<td nowrap>	
							<textarea cols="65" rows="2" id="company_email" name="company2.email">${company2.email}</textarea>							
							<font color="red">* &nbsp;</font>							
						</td>
					</tr>					
					
					<tr>
						<td nowrap>Company Status</td>
						<td nowrap>
							<select size="1" id="companyStatus" name="companyStatus">
										<c:choose>
											<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.ONGOING.num}">
												<option value="${CompanyStatus.ONGOING.num}" selected="selected">  ${CompanyStatus.ONGOING}  </option>
											</c:when>
											<c:otherwise>
												<option value="${CompanyStatus.ONGOING.num}">${CompanyStatus.ONGOING}</option>
											</c:otherwise>
										</c:choose>
				 						<c:choose>
											<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.ACTIVE.num}">
												<option value="${CompanyStatus.ACTIVE.num}" selected="selected">  ${CompanyStatus.ACTIVE}  </option>
											</c:when>
											<c:otherwise>
												<option value="${CompanyStatus.ACTIVE.num}">${CompanyStatus.ACTIVE}</option>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.ACTIVE_NO_HOSTING.num}">
												<option value="${CompanyStatus.ACTIVE_NO_HOSTING.num}" selected="selected"> ${CompanyStatus.ACTIVE_NO_HOSTING} </option>
											</c:when>
											<c:otherwise>
												<option value="${CompanyStatus.ACTIVE_NO_HOSTING.num}">${CompanyStatus.ACTIVE_NO_HOSTING}</option>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${company2.companySettings.companyStatus.num == CompanyStatus.SUSPENDED.num}">
												<option value="${CompanyStatus.SUSPENDED.num}" selected="selected">   ${CompanyStatus.SUSPENDED}   </option>
											</c:when>
											<c:otherwise>
												<option value="${CompanyStatus.SUSPENDED.num}">${CompanyStatus.SUSPENDED}</option>
											</c:otherwise>
										</c:choose>
										
									</select> 
						 </td>
					</tr>
					
					<tr> 
						<td nowrap>Address1</td>						
						<td nowrap="nowrap"><input type="text" id="company_address" name="company2.address" value="${company2.address}" class="textbox6" />
							<font color="red">* &nbsp;</font>
						</td>
					</tr>
					
					<tr> 
						<td nowrap>Address2</td>
						<td><input type="text" name="company2.address2" value="${company2.address2}" class="textbox6" /></td>
					</tr>
					
					<!-- tr> 
						<td nowrap>Title</td>
						<td><textarea name="company2.title" cols=40  rows=2>${company2.title}</textarea>
							
					</tr-->
					<!-- tr>
						<td nowrap>Keywords</td>
						<td><input type="text" id="keywords" maxlength="100" name="company2.keywords" value="${company2.keywords}" class="textbox4"></td>
					</tr--> 	
					<tr>
						<td nowrap>Business Type</td>
						<td>							
							<select id="company_businessType" name="businessType" class="combobox1">
								<option value="">- Select Business Type -</option>
								<c:forEach items="${businessTypes}" var="btype">
									<option value="${btype}" <c:if test = "${ btype.value eq company2.businessType.value}"> selected </c:if>>${btype.value}</option>
								</c:forEach>								
							</select>
						</td>
					</tr>		
										
					<tr> 
						<td nowrap>Phone</td>
						<td nowrap="nowrap"><input type="text" id="company_phone" name="company2.phone" value="${company2.phone}" class="textbox6" />
							<font color="red">* &nbsp;</font>
						</td>
					</tr>
							
					<tr> 
						<td nowrap>Cellphone</td>
						<td><input type="text" name="company2.cellphone" value="${company2.cellphone}" class="textbox6" /></td>
					</tr>
					
					<tr> 
						<td nowrap>Fax</td>
						<td><input type="text" name="company2.fax" value="${company2.fax}" class="textbox6" /></td>
					</tr>
<%-- 				<tr>
						<td nowrap>State</td>
						<td><input type="text" id="state" name="company2.state" value="${company2.state}" class="textbox4"></td>
					</tr>	
--%>						
					<tr> 
						<td nowrap>Contact Person</td>
						<td nowrap="nowrap"><input type="text" id="contact_person" name="company2.contactPerson" value="${company2.contactPerson}" class="textbox6" />
							<font color="red">* &nbsp;</font>
						</td>
					</tr>

					<tr> 
						<td nowrap>Designation</td>
						<td nowrap="nowrap"><input type="text" id="contact_person_designation" name="company2.contactPersonDesignation" value="${company2.contactPersonDesignation}" class="textbox6" />
						</td>
					</tr>
					<tr> 
						<td nowrap>Remarks</td>
						<td nowrap="nowrap"><input type="text" id="remarks" name="company2.remarks" value="${company2.remarks}" class="textbox6" />
						</td>
					</tr>					
						<td colspan="2" > </td>
					</tr>					
				</table>
			
				
			<%-- 	<table style> 
					<tr class="headbox" align="left">
						<td colspan="2"> Paypal Settings</td>
					</tr>
					<tr>
						<td nowrap>&nbsp;</td>
						<td nowrap>&nbsp;</td>
					</tr>	
					
					<tr> 
						<td nowrap>Paypal Username</td>
						<td nowrap="nowrap"><input type="text" id="company2.palUsername" name="company2.palUsername" value="${company2.palUsername}" class="textbox4" />
						</td>						
					</tr>
						 		
					<tr>
						<td nowrap>Paypal Password</td>		
						<td nowrap="nowrap"><input type="text" id="company2.palPassword" name="company2.palPassword" value="${company2.palPassword}" class="textbox4" />					
						
						</td>
					</tr>
					
					<tr>
						<td nowrap>Paypal Signature</td>
						<td nowrap="nowrap"><input type="text" id="company2.palSignature" name="company2.palSignature" value="${company2.palSignature}" class="textbox4" />
						</td>
					</tr>
					<tr>
						<td nowrap>Paypal Success Url</td>
						<td nowrap="nowrap"><input type="text" id="company2.palSuccessUrl" name="company2.palSuccessUrl" value="${company2.palSuccessUrl}" class="textbox4" />
					
						</td>
					</tr>
					<tr>
						<td nowrap>Paypal Cancel Url</td>
						<td nowrap="nowrap"><input type="text" id="company2.palCancelUrl" name="company2.palCancelUrl" value="${company2.palCancelUrl}" class="textbox4" />
						</td>
					</tr>
					
					<%-- tr> 
						<td nowrap>HTML Author</td>						
						<td nowrap="nowrap"><input type="text" id="" name="company2.metaAuthor" value="${company2.metaAuthor}" class="textbox4" />
						</td>
					</tr --%>
					
					<%-- tr> 
						<td nowrap>HTML Copyright</td>
						<td><input type="text" name="company2.metaCopyright" value="${company2.metaCopyright}" class="textbox4" /></td>
					</tr 			
					<tr>
						<td nowrap>&nbsp;</td>
						<td nowrap>&nbsp;</td>
					</tr>					
				</table> 
				--%>	
				<table style> 
					<tr class="headbox" align="left">
						<th colspan="2"> HTML META Settings</th>
					</tr>
					<tr >
						<td colspan="2">  </td>
					</tr>	
					
					<tr> 
						<td nowrap>HTML Title</td>
						<td><textarea name="company2.title" cols=40  rows=2>${company2.title}</textarea>
							
					</tr>
						 		
					<tr>
						<td nowrap>HTML Description</td>							
						<td nowrap><textarea name="company2.metaDescription" cols=40  rows=2 >${company2.metaDescription}</textarea>
						</td>
					</tr>
					
					<tr>
						<td nowrap>HTML Keywords</td>
						<td><textarea name="company2.keywords" cols=40  rows=2 >${company2.keywords}</textarea>
						</td>
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
					<tr>
						<td colspan="2" > </td>
					</tr>					
				</table>
								
			</td>						
			<td width="40%" bgcolor="#efefef" valign="top" rowspan="5" style="border:0px;">
			<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
				<div id="uploadimageform">
					<table width="100%" cellpadding="0" cellspacing="2" >
						<tr>
							<td >
								<h2>Image Settings</h2>							
								<br />								
							</td>
						</tr>	
						<tr>
							<td>
								<table>
		 							<tr>
		 								<td rowspan="3" valign="top" > <div id="dropcap" class="dropcap">1</div></td>
		 								<td>Width </td>
										<td><input type="text" id="image1_width" size="4" name="company2.companySettings.image1Width" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image1Width}" </c:if> />&nbsp; px 
											<font color="red">* &nbsp;</font>
										</td>
										
									</tr>
									<tr>		
										<td >Height</td>
										<td><input type="text" id="image1_height" size="4" name="company2.companySettings.image1Heigth" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image1Heigth}" </c:if> />&nbsp; px 
											<font color="red">* &nbsp;</font>
										</td>							
									</tr>
									<tr >
										<td colspan="2" nowrap class="borderBottom"> 
											<input type="checkbox" name="image1Forced" onchange="matchTextfield('newCompanyForm','image1Forced','company2.companySettings.image1Forced','');" <c:if test="${company2.companySettings.image1Forced eq true}"> checked</c:if>  />
											Forced Image Resize											
											<input type="hidden" name="company2.companySettings.image1Forced" <c:if test="${company2.companySettings.image1Forced eq true}"> value="true"</c:if>  />																					
										</td>																										
									</tr>											
		 							<tr>
		 								<td rowspan="3" valign="top"> <div id="dropcap" class="dropcap">2</div></td>
		 								<td >Width </td>
										<td><input type="text" id="image2_width" size="4" name="company2.companySettings.image2Width" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image2Width}" </c:if>  />&nbsp; px 
											<font color="red">* &nbsp;</font>
										</td>
										
									</tr>
									<tr>		
										<td >Height</td>
										<td><input type="text" id="image2_height" size="4" name="company2.companySettings.image2Heigth" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image2Heigth}" </c:if> />&nbsp; px 
											<font color="red">* &nbsp;</font>
										</td>							
									</tr>
									<tr>
										<td colspan="2" nowrap class="borderBottom">
											<input type="checkbox" name="image2Forced" onchange="matchTextfield('newCompanyForm','image2Forced','company2.companySettings.image2Forced','');" <c:if test="${company2.companySettings.image2Forced eq true}"> checked</c:if>  />										
										 	Forced Image Resize										
											<input type="hidden" name="company2.companySettings.image2Forced" <c:if test="${company2.companySettings.image2Forced eq true}"> value="true"</c:if>  />																					
										</td>																										
									</tr>											
		 							<tr>
		 								<td rowspan="3" valign="top"> <div id="dropcap" class="dropcap">3</div></td>
		 								<td >Width </td>
										<td><input type="text" id="image3_width" size="4" name="company2.companySettings.image3Width" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image3Width}" </c:if>  />&nbsp; px 
											<font color="red">* &nbsp;</font>
										</td>
										
									</tr>
									<tr>		
										<td >Height</td>
										<td><input type="text" id="image3_height" size="4" name="company2.companySettings.image3Heigth" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image3Heigth}" </c:if> />&nbsp; px 
											<font color="red">* &nbsp;</font>
										</td>							
									</tr>
									<tr>
										<td colspan="2" nowrap class="borderBottom">
											 <input type="checkbox" name="image3Forced" onchange="matchTextfield('newCompanyForm','image3Forced','company2.companySettings.image3Forced','');" <c:if test="${company2.companySettings.image3Forced eq true}"> checked</c:if>  />
											 Forced Image Resize										
											<input type="hidden" name="company2.companySettings.image3Forced" <c:if test="${company2.companySettings.image3Forced eq true}"> value="true"</c:if>  />																					
										</td>																										
									</tr>											
								</table>		
									
									<br />							
									 <center>Maximum Images Allowed
									<input type="text" size="4" width="100px" id="max_allowed_images" name="company2.companySettings.maxAllowedImages" value="${company2.companySettings.maxAllowedImages}" />
									<font color="red">* &nbsp;</font>
									</center> 
									<br />	
								</td>
							</tr>										
						</table>	
						<br/><br/><br/>
						

						
						
	
						<table>
									<tr class="headbox" align="left">
										<th colspan="4"> E-Commerce  Option </th>
									</tr>
									<tr>
										<td colspan="4">
											
										</td>
									</tr>
									
									<tr>
									
										<td colspan="4">
											
										</td>
									</tr>
									<tr >
										<td nowrap><u> Sections/Features	</u></td>
										<td nowrap><u> Availability			</u></td>
										<td nowrap colspan="2"><u> 	</u></td>
									</tr>
									<!-- order checkbox -->	
									 																				
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Orders</td>
										<td><input type="checkbox" name="hasOrder" onchange="matchTextfield('newCompanyForm','hasOrder','company2.companySettings.hasOrder','');"  <c:if test="${company2.companySettings.hasOrder eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasOrder" <c:if test="${company2.companySettings.hasOrder eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>			
														
									<!-- wishlist checkbox -->
																																	
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Wishlist</td>
										<td><input type="checkbox" name="hasWishlist" onchange="matchTextfield('newCompanyForm','hasWishlist','company2.companySettings.hasWishlist','');"  <c:if test="${company2.companySettings.hasWishlist eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasWishlist" <c:if test="${company2.companySettings.hasWishlist eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Paypal Account</td>
										<td>
										
											<input type="checkbox" name="hasPalAccount" value="true" onchange="showToMainFilterField(this); matchTextfield('newCompanyForm','hasPalAccount','company2.companySettings.hasPalAccount','');" ${hasPalAccount? 'checked="checked"' : ""}<c:if test="${company2.companySettings.hasPalAccount eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasPalAccount" <c:if test="${company2.companySettings.hasPalAccount eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="4">
											
										</td>
									</tr>
									<tr>
										<td colspan="4" nowrap><u>Order Terms and Condition</u></td>
									</tr>
									<tr> 
										<td colspan="4"><textarea name="company2.statement" cols=50  rows=5>${company2.statement}</textarea>		
									</tr>	
									<tr>
										<td colspan="4">
											
										</td>
									</tr>
									<tr>
										<td colspan="4">
											Note: Company Statement will be printed on the customer`s order PDF last page.
										</td>
									</tr>		
									<tr>
										<td colspan="4">
											
										</td>
									</tr>
									
						</table>	
						
						
						
						
								
					</div>
		
				<div id="toMainFilterDiv" ${ company2.companySettings.hasPalAccount ? 'style="display:block"' : 'style="display:none"' } class="aaaa">
				
				<table style> 
					<tr class="headbox" align="left">
						<th colspan="2"> Paypal Settings </th>
					</tr>
					<tr >
						<td colspan="2">  </td>
					</tr>	
					<tr>
						<td nowrap width="20%">Paypal Currency Type <br />(i.e. PHP or USD)</td>							
						<td nowrap width="80%"><input type="text" id="company2.palCurrencyType" name="company2.palCurrencyType" value="${company2.palCurrencyType}" class="textbox6" />
						
						</td>
					</tr>		 		
					<tr>
						<td nowrap>Paypal Username</td>							
						<td nowrap><input type="text" id="company2.palUsername" name="company2.palUsername" value="${company2.palUsername}" class="textbox6" />
						</td>
					</tr>
					<tr>
						<td nowrap>Paypal Password</td>							
						<td nowrap><input type="text" id="company2.palPassword" name="company2.palPassword" value="${company2.palPassword}" class="textbox6" />
						</td>
					</tr>
					<tr> 
						<td nowrap>Paypal Signature</td>							
						<td nowrap>	<input type="text" id="company2.palSignature" name="company2.palSignature" value="${company2.palSignature}" class="textbox6" />
													
						</td>
					</tr>					
					
					
					<tr> 
						<td nowrap>Paypal Success URL</td>						
						<td nowrap="nowrap"><input type="text" id="company2.palSuccessUrl" name="company2.palSuccessUrl" value="${company2.palSuccessUrl}" class="textbox6" />
						</td>
					</tr>
					
					<tr> 
						<td nowrap>Paypal Cancel URL</td>
						<td><input type="text" id="company2.palCancelUrl" name="company2.palCancelUrl" value="${company2.palCancelUrl}" class="textbox6" /></td>
					</tr>
										
					<tr>
						<td colspan="2" > </td>
					</tr>					
				</table>
				</div>
	
					 <table id="dataTable"> 
								     <tr class="headbox" align="left">
								     <th colspan="4">Shipping Price Control Panel</th>
								     </tr>
									<tr>
										<td colspan="4">
											
										</td>
									</tr>
							<tr>
									<td colspan="2" nowrap>ShippingPrice Implementation</td>
									<td colspan="2">					
				 					<select size="1" id="shippingType" name="shippingType" onchange="toggle()">
				 						<c:choose>
											<c:when test="${company2.companySettings.shippingType == 'none'}">
												<option name="3" value="none" selected="selected">  None  </option>
											</c:when>
											<c:otherwise>
												<option name="3" value="none">None</option>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${company2.companySettings.shippingType == 'fixed'}">
												<option name="1" value="fixed" selected="selected">   Fixed   </option>
											</c:when>
											<c:otherwise>
												<option name="1" value="fixed">Fixed</option>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${company2.companySettings.shippingType == 'ranged'}">
												<option name="2" value="ranged" selected="selected"> By Intervals </option>
											</c:when>
											<c:otherwise>
												<option name="2" value="ranged">By Intervals</option>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${company2.companySettings.shippingType == 'individual'}">
												<option name="3" value="individual" selected="selected">  Per Item  </option>
											</c:when>
											<c:otherwise>
												<option name="3" value="individual">Per Item</option>
											</c:otherwise>
										</c:choose>
										
									</select>
								</td>
							</tr>	

								<tr> 
										<td colspan="2" nowrap><text style="display:none;" id="fixedrate1" >Fixed Price</text></td>
										<td colspan="2" nowrap><input style="display:none;" id="fixedrate2"  type="text" id="flat_rate_shipping_price" name="company2.flatRateShippingPrice" value="${company2.flatRateShippingPrice}" class="textbox4" />
										<font color="red"></font>
										</td>
									</tr>
									<tr> 
										<td colspan="4" nowrap><text style="display:none;" id="fixedrate13" >You can set per item/product shipping price in<br/>the item setting in the "groups" or product page</text></td>
											<font color="red"></font>
										</td>
									</tr>
									
					     </table>  
					  
					     <table    style="display:none;" id="shiptable100">
					     
					     <tr> 
										<td colspan="5" nowrap> Table Interval Price</td>
									</tr>
										<td colspan="1" width="10%"> Delete</td>
										<td colspan="1" width="35%"> Order Total Price</td>
										<td colspan="1" width="35%"> Shipping Price</td>
										<td></td>
									</tr>
									<!--  <c:forEach items="${company2.shippingTable}" var="x" varStatus = "stat">
									 	<tr> 
									 	<td><INPUT type="checkbox" name="chk"/></td>  
										<td nowrap><input type="text" id="from_price" name="company2.shippingTable.get(${stat.count-1}).fromPrice" value="${x.fromPrice}" class="textbox4" />
										</td>
										<td nowrap><input type="text" id="to_price" name="company2.shippingTable.get(${stat.count-1}).toPrice" value="${x.toPrice}" class="textbox4" />
										</td>
										<td nowrap><input type="text" id="shipping_table" name="company2.shippingTable.get(${stat.count-1}).shippingPrice" value="${x.shippingPrice}" class="textbox4" />
										</td>
										</tr>
										<c:set var="ct" value="${stat.count-1}"/>
									</c:forEach>  -->
									<c:set var="ct" value="0"/>
									<c:forEach items="${company2.shippingTable}" var="x" varStatus = "stat">
									 	<tr> 
									 	<td><INPUT type="checkbox" name="chk"/></td>  
									
										<input type="hidden" id="from_price" name="p" value="${x.fromPrice}" class="textbox4" />
									
										<td nowrap><input type="text" id="to_price" name="toPrice_${stat.count-1}" value="${x.toPrice}" class="textbox4" />
										</td>
										<td colspan="1" nowrap><input type="text" id="shipping_table" name="shippingPrice_${stat.count-1}" value="${x.shippingPrice}" class="textbox4" />
										</td>
										<td></td>
										</tr>
										<c:set var="ct" value="${stat.count}"/>
									</c:forEach>  
									
						         <tr>  
						         	<td colspan="1">
						    		 <INPUT type="button" value="Delete Row" onclick="deleteRow('shiptable100')" /> 
					    		 	</td>
						             <td colspan="4">
							         <INPUT type="button" value="Add Row" onclick="addRow('shiptable100',${ct})" /> 
							         </td>
						 			 
					    		 </tr>
					     </table>  
					     <script type="text/javascript" language="JavaScript">
						toggle();
						</script>
				
						<table> 
					<tr class="headbox" align="left">
						<th colspan="4">Downloadable files option</th>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;Will Expire</td>
						<td><input type="checkbox" name="willExpire" onchange="matchTextfield('newCompanyForm','willExpire','company2.companySettings.willExpire','company2.companySettings.expiryDate');" <c:if test="${company2.companySettings.willExpire eq true}"> checked</c:if>  />
							<input type="hidden" name="company2.companySettings.willExpire" <c:if test="${company2.companySettings.willExpire eq true}"> value="true"</c:if>  />									
						</td>
						<td nowrap>No. of days</td>
						<td><input type="text" class="textbox1" id="expiryDate" name="company2.companySettings.expiryDate" value="${company2.companySettings.expiryDate}" name="tx1"  <c:if test="${company2.companySettings.willExpire eq false}"> disabled</c:if> /></td>
										
					</tr> 
					<tr>
					<td>&nbsp;&nbsp;&nbsp;Limit number of downloads </td>
						<td><input type="checkbox" name="limitDownloads" onchange="matchTextfield('newCompanyForm','limitDownloads','company2.companySettings.limitDownloads','company2.companySettings.downloads');" <c:if test="${company2.companySettings.limitDownloads eq true}"> checked</c:if>  />
							<input type="hidden" name="company2.companySettings.limitDownloads" <c:if test="${company2.companySettings.limitDownloads eq true}"> value="true"</c:if>  />									
						</td>
						<td nowrap>No. of downloads</td>
						<td><input type="text" class="textbox1" id="downloads" name="company2.companySettings.downloads" value="${company2.companySettings.downloads}" name="tx1"  <c:if test="${company2.companySettings.limitDownloads eq false}"> disabled</c:if> /></td>
					</tr>
			</table>	
				 		
					<tr>
				</c:if>						
			</td>
		</tr>	
			<td valign="top"> &nbsp;
			
			
				<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">		
 				<table >
					<tr class="headbox" align="left">
						<th colspan="2"> WebToGo Settings </th>
					</tr> 
					<tr >
						<td colspan="2">  </td>
					</tr>		
					<tr> 
						<td nowrap width="25%">Domain Name</td>
						<td nowrap width="75%"><input type="text" id="domain_name" name="company2.domainName" value="${company2.domainName}" class="textbox6" />
						<font color="red">*</font>
						</td>
					</tr>
					<tr> 
						<td nowrap>Server Name</td>
						<td nowrap><input type="text" id="server_name" name="company2.serverName" value="${company2.serverName}" class="textbox6" />
				<!-- 		<font color="red">* &nbsp;</font>    -->
						</td>
					</tr> 	 
					<tr>
						<td nowrap>Expiry Date</td>
						<td nowrap >
							<fmt:formatDate pattern="MM-dd-yyyy" value="${company2.expiryDate}" var="ed"/>
										 
							<input type="text" id="expiry_date" name="expiryDate" value="${ed}" readonly="readonly"/> 						
							<a href="javascript:;" id="expiry_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
					 		<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "expiry_date",     // id of the input field
							        ifFormat       :    "%m-%d-%Y",      // format of the input field
							        button         :    "expiry_date_button",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true
							    });
							</script> 
						</td>						
					</tr>
	 				<tr> 
						<td nowrap>Website Online Date</td>
						<td nowrap>
						<fmt:formatDate pattern="MM-dd-yyyy"  value="${company2.companySettings.onlineDate}"  var="od"/>										 
						
							<input type="text"  id="online_date" name="onlineDate" value="${od}"  readonly="readonly"/>													
							<a href="javascript:;" id="online_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar"/></a>
					 		<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "online_date",     // id of the input field
							        ifFormat       :    "%m-%d-%Y",      // format of the input field
							        button         :    "online_date_button",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true
							    });
							</script>
	 						
						</td>
						</tr> 		 
						<tr> 
							<td nowrap>Monthly Maintenance Charge</td>
						<td><input type="text" id="monthly_charge" name="company2.companySettings.monthlyCharge" value="${company2.companySettings.monthlyCharge}"  class="textbox6" /></td>
						</tr>
						<tr>
							<td colspan="2">
								<table align="center">
									<tr >
										<td nowrap><u> Sections/Features	</u></td>
										<td nowrap><u> Availability			</u></td>
										<td nowrap colspan="2"><u> 	</u></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Featured Single Pages</td>
										<td><input type="checkbox" name="hasFeaturedSinglePages" onchange="matchTextfield('newCompanyForm','hasFeaturedSinglePages','company2.companySettings.hasFeaturedSinglePages','company2.companySettings.maxFeaturedSinglePages');" <c:if test="${company2.companySettings.hasFeaturedSinglePages eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasFeaturedSinglePages" <c:if test="${company2.companySettings.hasFeaturedSinglePages eq true}"> value="true"</c:if>  />									
										</td>
										<td nowrap>Maximum </td>
										<td><input type="text" class="textbox1" id="max_featured_single_pages" name="company2.companySettings.maxFeaturedSinglePages" value="${company2.companySettings.maxFeaturedSinglePages}" name="tx1"  <c:if test="${company2.companySettings.hasFeaturedSinglePages eq false}"> disabled</c:if> /></td>
										
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Featured Products</td>
										<td><input type="checkbox" name="hasFeaturedProducts" onchange="matchTextfield('newCompanyForm','hasFeaturedProducts','company2.companySettings.hasFeaturedProducts','company2.companySettings.maxFeaturedProducts');" <c:if test="${company2.companySettings.hasFeaturedProducts eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasFeaturedProducts" <c:if test="${company2.companySettings.hasFeaturedProducts eq true}"> value="true"</c:if>  />
										</td>
										<td nowrap>Maximum </td>
										<td><input type="text" id="max_featured_products" name="company2.companySettings.maxFeaturedProducts" value="${company2.companySettings.maxFeaturedProducts}" class="textbox1" <c:if test="${company2.companySettings.hasFeaturedProducts eq false}"> disabled</c:if>   /></td>
									</tr>	
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Featured Pages</td>
										<td><input type="checkbox" name="hasFeaturedPages" onchange="matchTextfield('newCompanyForm','hasFeaturedPages','company2.companySettings.hasFeaturedPages','company2.companySettings.maxFeaturedPages');" <c:if test="${company2.companySettings.hasFeaturedPages eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasFeaturedPages" <c:if test="${company2.companySettings.hasFeaturedPages eq true}"> value="true"</c:if>  />									
										</td>
										<td nowrap>Maximum </td>
										<td><input type="text" class="textbox1" id="max_featured_pages" name="company2.companySettings.maxFeaturedPages" value="${company2.companySettings.maxFeaturedPages}" name="tx1"  <c:if test="${company2.companySettings.hasFeaturedPages eq false}"> disabled</c:if> /></td>
										
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Featured Products</td>
										<td><input type="checkbox" name="hasFeaturedProducts" onchange="matchTextfield('newCompanyForm','hasFeaturedProducts','company2.companySettings.hasFeaturedProducts','company2.companySettings.maxFeaturedProducts');" <c:if test="${company2.companySettings.hasFeaturedProducts eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasFeaturedProducts" <c:if test="${company2.companySettings.hasFeaturedProducts eq true}"> value="true"</c:if>  />
										</td>
										<td nowrap>Maximum </td>
										<td><input type="text" id="max_featured_products" name="company2.companySettings.maxFeaturedProducts" value="${company2.companySettings.maxFeaturedProducts}" class="textbox1" <c:if test="${company2.companySettings.hasFeaturedProducts eq false}"> disabled</c:if>   /></td>
									</tr>								
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Category Menu</td>
										<td><input type="checkbox" name="hasCategoryMenu" onchange="matchTextfield('newCompanyForm','hasCategoryMenu','company2.companySettings.hasCategoryMenu','company2.companySettings.maxCategoryMenu');" <c:if test="${company2.companySettings.hasCategoryMenu eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasCategoryMenu" <c:if test="${company2.companySettings.hasCategoryMenu eq true}"> value="true"</c:if>  />
										</td>
										<td nowrap>Maximum </td>
										<td><input type="text" class="textbox1" id="max_category_menu" name="company2.companySettings.maxCategoryMenu" value="${company2.companySettings.maxCategoryMenu}" <c:if test="${company2.companySettings.hasCategoryMenu eq false}"> disabled</c:if> /></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Products</td>
										<td ><input type="checkbox" name="hasProducts" onchange="matchTextfield('newCompanyForm','hasProducts','company2.companySettings.hasProducts','company2.companySettings.productsPerPage');" <c:if test="${company2.companySettings.hasProducts eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasProducts" <c:if test="${company2.companySettings.hasProducts eq true}"> value="true"</c:if>  />
										</td>
										<td nowrap>Products Per Page</td>
										<td><input type="text" class="textbox1" id="products_per_page" name="company2.companySettings.productsPerPage" value="${company2.companySettings.productsPerPage}"  <c:if test="${company2.companySettings.hasProducts eq false}"> disabled</c:if> /> </td>
									</tr>	
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Packages</td>
										<td ><input type="checkbox" name="hasPackages" onchange="matchTextfield('newCompanyForm','hasPackages','company2.companySettings.hasPackages','company2.companySettings.packagesPerPage');" <c:if test="${company2.companySettings.hasPackages eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasPackages" <c:if test="${company2.companySettings.hasPackages eq true}"> value="true"</c:if>  />
										</td>
										<td nowrap>Packages Per Page</td>
										<td><input type="text" class="textbox1" id="packages_per_page" name="company2.companySettings.packagesPerPage" value="${company2.companySettings.packagesPerPage}"  <c:if test="${company2.companySettings.hasPackages eq false}"> disabled</c:if> /> </td>
									</tr>	
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Other Fields</td>
										<td ><input type="checkbox" name="hasOtherFields" onchange="matchTextfield('newCompanyForm','hasOtherFields','company2.companySettings.hasOtherFields','company2.companySettings.maxOtherFields');" <c:if test="${company2.companySettings.hasOtherFields eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasOtherFields" <c:if test="${company2.companySettings.hasOtherFields eq true}"> value="true"</c:if>  />
										</td>
										<td nowrap>Max Other Fields</td>
										<td><input type="text" class="textbox1" id="max_other_fields" name="company2.companySettings.maxOtherFields" value="${company2.companySettings.maxOtherFields}"  <c:if test="${company2.companySettings.hasOtherFields eq false}"> disabled</c:if> /> </td>
									</tr>							
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Brands</td>
										<td><input type="checkbox" name="showBrands" onchange="matchTextfield('newCompanyForm','showBrands','company2.companySettings.showBrands','');" <c:if test="${company2.companySettings.showBrands eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.showBrands" <c:if test="${company2.companySettings.showBrands eq true}"> value="true"</c:if>  />
										</td>
										<td colspan="2">&nbsp;  </td>
									</tr>								
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Event Calendar</td>
									<td><input type="checkbox" name="hasEventCalendar" onchange="matchTextfield('newCompanyForm','hasEventCalendar','company2.companySettings.hasEventCalendar','');" <c:if test="${company2.companySettings.hasEventCalendar eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasEventCalendar" <c:if test="${company2.companySettings.hasEventCalendar eq true}"> value="true"</c:if>  />
										</td>
										<td colspan="2">&nbsp; </td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Repeating Events</td>
									<td><input type="checkbox" name="hasRepeatingEvent" onchange="matchTextfield('newCompanyForm','hasRepeatingEvent','company2.companySettings.hasRepeatingEvent','');" <c:if test="${company2.companySettings.hasRepeatingEvent eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasRepeatingEvent" <c:if test="${company2.companySettings.hasRepeatingEvent eq true}"> value="true"</c:if>  />
										</td>
										<td colspan="2">&nbsp; </td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Member</td>
										<td><input type="checkbox" name="hasMemberFeature" onchange="matchTextfield('newCompanyForm','hasMemberFeature','company2.companySettings.hasMemberFeature','');"  <c:if test="${company2.companySettings.hasMemberFeature eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasMemberFeature" <c:if test="${company2.companySettings.hasMemberFeature eq true}"> value="1" </c:if> />
										</td>
										<td colspan="2">&nbsp; </td>
									</tr>		
									<!-- bulletin checkbox -->																								
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Message Board</td>
										<td><input type="checkbox" name="hasBulletinFeature" onchange="matchTextfield('newCompanyForm','hasBulletinFeature','company2.companySettings.hasBulletinFeature','');"  <c:if test="${company2.companySettings.hasBulletinFeature eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasBulletinFeature" <c:if test="${company2.companySettings.hasBulletinFeature eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>		
									<tr>
										<td>&nbsp;&nbsp;&nbsp;User Rights</td>
										<td><input type="checkbox" name="hasUserRights" onchange="matchTextfield('newCompanyForm','hasUserRights','company2.companySettings.hasUserRights','');"  <c:if test="${company2.companySettings.hasUserRights eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasUserRights" <c:if test="${company2.companySettings.doNotGenerateBilling eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Can Batch Update Through Excel Files</td>
										<td>
											<input type="checkbox" name="canBatchUpdateExcelFiles"  <c:if test="${company2.companySettings.canBatchUpdateExcelFiles eq true}"> checked</c:if>   />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Many Attachments</td>
										<td>
										<%-- 	
										<input type="checkbox" name="manyAttachments" onchange="matchTextfield('newCompanyForm','manyAttachments','company2.companySettings.manyAttachments','');"  <c:if test="${company2.companySettings.manyAttachments eq true}"> checked</c:if>   />
										<input type="hidden" name="company2.companySettings.manyAttachments" <c:if test="${company2.companySettings.manyAttachments eq true}"> value="true" </c:if> />
									 	--%>
									 	<input type="checkbox" name="manyAttachments" onchange="matchTextfield('newCompanyForm','manyAttachments','company2.companySettings.manyAttachments','');"   />
										<input type="hidden" name="company2.companySettings.manyAttachments" />
									
										</td>
										<td colspan="2">&nbsp; </td>
									</tr>
								
								
									<!-- Do Not Generate Billing checkbox -->
									<%-- 																								
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Do Not Generate Billing</td>
										<td><input type="checkbox" name="doNotGenerateBilling" onchange="matchTextfield('newCompanyForm','doNotGenerateBilling','company2.companySettings.doNotGenerateBilling','');"  <c:if test="${company2.companySettings.doNotGenerateBilling eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.doNotGenerateBilling" <c:if test="${company2.companySettings.doNotGenerateBilling eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>
									--%>																						
									<!-- order checkbox -->	
									<!--  																				
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Orders</td>
										<td><input type="checkbox" name="hasOrder" onchange="matchTextfield('newCompanyForm','hasOrder','company2.companySettings.hasOrder','');"  <c:if test="${company2.companySettings.hasOrder eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasOrder" <c:if test="${company2.companySettings.hasOrder eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>			
									-->							
									<!-- wishlist checkbox -->
									<!--																								
									<tr>
										<td>&nbsp;&nbsp;&nbsp;Wishlist</td>
										<td><input type="checkbox" name="hasWishlist" onchange="matchTextfield('newCompanyForm','hasWishlist','company2.companySettings.hasWishlist','');"  <c:if test="${company2.companySettings.hasWishlist eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasWishlist" <c:if test="${company2.companySettings.hasWishlist eq true}"> value="true" </c:if> />
										</td>
										<td colspan="2">&nbsp;</td>
									</tr>
									-->		
								</table>
							</td>
						</tr> 										
						<%-- 
						<tr>
							<td colspan="2" valign="top" align="right">
							
								<div style="float: right; padding-right: 10px;"><INPUT TYPE=CHECKBOX NAME="suspended" onchange="matchTextfield('newCompanyForm','suspended','company2.companySettings.suspended','');" <c:if test="${company2.companySettings.suspended eq true}">checked</c:if>>Suspend this company's site<P></div>
								<input type="hidden" name="company2.companySettings.suspended" <c:if test="${company2.companySettings.suspended eq true}"> value="true"</c:if>  />
							
							</td>
						</tr>
						--%>			
					</table>
					</c:if>						
			</td>
			
		</tr>
		<tr>
			<td valign="top">Fields with asterisk (<font color="red"> * </font>) are required.</td>
			<td valign="top">&nbsp; </td>
		</tr>
		<tr>
			<td style="border: 0px;" valign="top">
				<input type="button" name="cancel" value="Cancel" class="button1" onclick="window.location='dashboard.do'">
				&nbsp;
			<c:if test="${empty company2}">
					<input type="submit" name="submit" value="Add New" class="button1">
				</c:if>
			<c:if test="${not empty company2}">
					<input type="submit" name="submit" value="Update" class="button1">
				</c:if>
				
			</td>
			
		</tr>
		<tr colspan="1" valign="top">
		<td><hr/><br/><br/><br/><br/><br/><br/><br/><br/></td>
		</tr>
	</table>
</div>




<!--  *************************************************************** -->
<%-- 

		<table width="50%" border=0>
			<tr>
				<td colspan="3"></td>
			</tr>
			<tr class="headbox">
					<c:if test="${empty company2}">
							<th colspan="3">New Company=</th>
					</c:if>
					<c:if test="${not empty company2}">
							<th colspan="3">?Edit Company==</th>
					</c:if>
			</tr>
			<tr>
				<td width="1%" nowrap>Company Name</td>
				<td width="10px"></td>
				<td><input type="text" id="company_name" name="company2.name" value="${company2.name}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Full Company Name</td>
				<td width="10px"></td>
				<td><input type="text" id="company_nameEditable" name="company2.nameEditable" value="${company2.nameEditable}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Company Address</td>
				<td width="10px"></td>
				<td><input type="text" id="company_address" name="company2.address" value="${company2.address}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Phone</td>
				<td width="10px"></td>
				<td><input type="text" id="company_phone" name="company2.phone" value="${company2.phone}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Cell Phone</td>
				<td width="10px"></td>
				<td><input type="text" id="company_cellphone" name="company2.cellphone" value="${company2.cellphone}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Fax</td>
				<td width="10px"></td>
				<td><input type="text" id="company_fax" name="company2.fax" value="${company2.fax}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>State</td>
				<td width="10px"></td>
				<td><input type="text" id="company_state" name="company2.state" value="${company2.state}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Contact Person</td>
				<td width="10px"></td>
				<td><input type="text" id="company_contactPerson" name="company2.contactPerson" value="${company2.contactPerson}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Business Type</td>
				<td width="10px"></td>
				<td>
				<select id="company_businessType" name="company2.businessType" class="combobox1">
					<option value="">- Select Business Type -</option>
					<c:forEach items="${businessTypes}" var="btype">
						<option value="${btype}" <c:if test = "${ btype.value eq company2.businessType.value}"> selected </c:if>>${btype.value}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td width="1%" nowrap>Email</td>
				<td width="10px"></td>
				<td><input type="text" id="company_email" name="company2.email" value="${company2.email}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Domain Name</td>
				<td width="10px"></td>
				<td><input type="text" id="domain_name" name="company2.domainName" value="${company2.domainName}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Keywords</td>
				<td width="10px"></td>
				<td><input type="text" id="keywords" name="company2.keywords" value="${company2.keywords}" class="textbox3"></td>
			</tr>
			<tr>
				<td width="1%" nowrap>Title</td>
				<td width="10px"></td>
				<td><input type="text" id="title" name="company2.title" value="${company2.title}" class="textbox3"></td>
			</tr>	
			<tr>
				<td width="1%" nowrap>Expiry Date</td>
				<td width="10px"></td>
				<td width>
				<fmt:formatDate pattern="MM-dd-yyyy" value="${company2.expiryDate}" var="ed"/>
								 
				<input type="text" id="expiry_date" name="expiryDate" value="${ed}" class="textbox3" readonly="readonly"/> 
				  </td>
				 <td> 
				<a href="javascript:;" id="expiry_date_button">Open Calendar</a>
				<script type="text/javascript">
				    Calendar.setup({
				        inputField     :    "expiry_date",     // id of the input field
				        ifFormat       :    "%m-%d-%Y",      // format of the input field
				        button         :    "expiry_date_button",  // trigger for the calendar (button ID)
				        align          :    "Tl",           // alignment (defaults to "Bl")
				        singleClick    :    true
				    });
				</script>
				</td>
			</tr>
			<tr>
				<td colspan=5 valign="top" width="200px" align="right">
					<div style="float: right; padding-right: 78px;"><INPUT TYPE=CHECKBOX NAME="suspended" <c:if test="${company2.companySettings.suspended eq true}">checked</c:if>>Suspend this company's site<P></div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
					<c:if test="${empty company2}">
							<input type="submit" name="submit" value="Add New" class="button1">
					</c:if>
					<c:if test="${not empty company2}">
							<input type="submit" name="submit" value="Update" class="button1">
					</c:if>
					&nbsp;
					<input type="button" name="cancel" value="Cancel" class="button1" onclick="window.location='companies.do'">
				
				</td>
			</tr>
		</table>
 --%>