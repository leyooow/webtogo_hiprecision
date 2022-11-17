<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<body>
<script src="../admin/javascripts/prototype.js" type="text/javascript"></script>
<script src="../admin/javascripts/scriptaculous.js" type="text/javascript"></script>
<c:set var="menu" value="manage" />
<c:set var="submenu" value="company_listing" />
<c:set var="pagingAction" value="companies.do" />
	<style type="text/css">
		@import "css/domtab.css";
	</style>
    <!--[if gt IE 6]>
        <style type="text/css">
            html>body ul.domtabs a:link, 
            html>body ul.domtabs a:visited,
            html>body ul.domtabs a:active,
            html>body ul.domtabs a:hover{
                height:3em;
            }
        </style>
    <![endif]-->
	<script type="text/javascript" src="javascripts/domtab.js"></script>
	<script type="text/javascript">
		document.write('<style type="text/css">');    
		document.write('div.domtab div{display:none;}<');
		document.write('/s'+'tyle>');    
    </script>
    <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<c:set var="menu" value="events" />
 
<!-- calendar stylesheet -->

  <link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />



  <!-- main calendar program -->

  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>



  <!-- language for the calendar -->

  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>



  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->

  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>





 



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

			//var image3Width			= myForm['image3_width'].value;

			//var image3Height		= myForm['image3_height'].value;

			var maxAllowedImages	= myForm['max_allowed_images'].value;

			//webtogo settings			

			var domainName 			= myForm['domain_name'].value;

			var monthlyCharge		= myForm['monthly_charge'];

			var maxFeaturedPages 	= myForm['max_featured_pages'];

			var maxFeaturedProducts	= myForm['max_featured_products'];

			var maxBestSellers		= myForm['max_best_sellers'];
			
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

<body>

  <div class="container">
	<c:set var="menu" value="manage" />
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	 <div class="contentWrapper" id="contentWrapper">
	 <div class="content">
			<s:actionmessage />
				<s:actionerror />
	  <div class="pageTitle">
		
			
				
	    <h1><strong>Company</strong>: View Company Listing</h1>

		<ul>

		

		  <li>[<a href="#addCompany">Add Company</a>]</li>

		</ul>

		<div class="clear"></div>

	  </div><!--//pageTitle-->
	  
	  <c:if test="${user.userType.value eq 'Super User' or user.userType.value eq 'WTG Administrator'}">
		<script type="text/javascript">
		function confirmCopy()
		{
			var source = document.getElementById("source").value;
			var destination = document.getElementById("destination").value;
			if(source != '' && destination != '')
			{
				if(source == destination)
				{
					alert("Don't play with yourself!");
					return false;
				}
				else
				{
					var conf = confirm('Are you sure you want to copy company?');
					if(conf)
					{
						return confirm('Are you really sure? This process cannot be reverted.');
					}				
				}
			}
			else
			{
				alert('Please select a source and a destination.');
			}
			return false;
		}
		</script>
		<div class="pageTitle" style="height: 25px">
			<h1>
				<strong>Copy Company</strong>:
			</h1>
			<form action="copycompany.do" method="post" onsubmit="return confirmCopy();">
					<div style="float:left"><b>FROM:</b></div>
					<div style="float:left">
						<select name="sourceCompany" id="source" style="width: 200px;">
							<option value="">--Select Source--</option>
							<c:forEach items="${activeCompanies}" var="a">
								<option value="${a.id}">${a.nameEditable}</option>
							</c:forEach>
						</select>
					</div>
					<div style="float:left"><b>TO:</b></div>
					<div style="float:left">
						<select name="destinationCompany" id="destination" style="width: 200px;">
							<option value="">--Select Destination--</option>
							<c:forEach items="${ongoingCompanies}" var="o">
								<option value="${o.id}">${o.nameEditable}</option>
							</c:forEach>
						</select>
					</div>
					<div style="float:left">
						<input type="submit" value="GO" />
					</div>
			</form>
			
		</div>
	  </c:if>

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>

	  <div class="clear"></div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">


	    <tr>

		 <th>Company Name</th>
		<th>Contact Person</th>
		<th>Phone</th>
		<th >Email</th>
		<th >Users</th>
		<th >Statistics</th>
		<th >Action</th>

		</tr>
		<c:set var="count" value="0" />
		<c:forEach items="${companiesList}" var="cc">
		
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<c:if test="${cc.companySettings.suspended}">
							<td>${cc.name} - X</td>
						</c:if>
						
						<c:if test="${not cc.companySettings.suspended}">
							<td>${cc.name}</td>
						</c:if>
						<td>${cc.contactPerson}</td>
						<td>${cc.phone}</td>
						<td>
						<c:forEach var="email" items="${fn:split(fn:trim(cc.email), ',')}" varStatus="stat">
					
					${email}<c:if test="${stat.count ne fn:length(fn:split(fn:trim(cc.email), ','))}">,</c:if>
					<c:if test="${(stat.count-1 mod 2) eq 1}"><br /></c:if>
				</c:forEach>
						</td> 
						<td>
						
						<c:forEach items="${cc.users }" var="user">
							${user.username} <br>
						</c:forEach>
						
						</td>
						<td>
								<a target="_blank" href="getstatistics.do?id=${cc.id}">view</a>
						</td>
						<td>
							<a href="editcompany.do?company_id=${cc.id}">Edit</a> |
							<a href="deletecompany.do?company_id=${cc.id}" onclick="return confirm('Are you sure you want to delete ${cc.name}?')">Delete</a>
						</td>
					</tr>
					</c:forEach>

	  </table>

	  <ul class="pagination">

	     <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	  
	  <div class="clear"></div>
		</div><!--//content-->
	  
	<form name="newCompanyForm" method="post" action="createcompany.do" onsubmit="return submitForm(this)">
	 	<input type="hidden" name="user_type"  value="${user.userType.value}"/>
				<input type="hidden" name="company_id"  value="${company2.id}"/>
	
	<div class="mainContent">
	  <div class="pageTitle">
	    <h1><strong>Company Information</strong></h1>
		<ul>
		  <li><a  name="addCompany">Company Setting</a></li>
		  <li>&raquo;</li>
		  <li><a class="active" href="#">Company Information</a></li>
		</ul>
		<div class="clear"></div>
	  </div><!--//pageTitle-->
      
	<!-- domtabs -->
	
   <div class="domtab" style="margin-top:20px">
      <ul class="domtabs">
		<li><a href="#GeneralInfo">General Information</a></li>
		<li><a href="#HTMLMETASettings">HTML META Settings</a></li>
		<li><a href="#WebToGoSettings">WebToGo Settings </a></li>
		<li><a href="#ECommerceOption">E-Commerce Option </a></li>
	</ul>
	<div>
		<h2><a name="GeneralInfo" id="GeneralInfo">General Information</a></h2>
		<table class="tblSettings">
        	<tr>
            	<td class="right">
            	<c:if test="${company2.id == null}">
								<span class="required">*</span>
				</c:if> Company Name  :</td>
                <td><input type="text" id="company_name" name="company2.name"  <c:if test="${company2.id != null}">disabled="disabled" </c:if> value="${company2.name}" class="w385"></td>
            </tr>
            <tr>
            	<td class="right"><span class="required">*</span> Full Company Name  :</td>
                <td><input type="text" id="name_editable" name="company2.nameEditable" value="${company2.nameEditable}" class="w385"></td>
            </tr>
            <tr>
            	<td class="right"><span class="required">*</span> Company Email  :</td>
                <td><textarea cols="65" rows="2" id="company_email" name="company2.email" class="w385">${company2.email}</textarea></td>
            </tr>
            <tr>
            	<td class="right">Company Status  :</td>
                <td><select size="1" id="companyStatus" name="companyStatus">
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
										
									</select> </td>
            </tr>
             <tr>
            	<td class="right"><span class="required">*</span> Address1  :</td>
                <td><input type="text" id="company_address" name="company2.address" value="${company2.address}" class="w385" /></td>
            </tr>
            <tr>
            	<td class="right"><span class="required">*</span> Address2  :</td>
                <td><input type="text" name="company2.address2" value="${company2.address2}" class="w385" /></td>
            </tr>
            <tr>
            	<td class="right">Business Type  :</td>
                <td><select id="company_businessType" name="businessType" class="w385">
								<option value="">- Select Business Type -</option>
								<c:forEach items="${businessTypes}" var="btype">
									<option value="${btype}" <c:if test = "${ btype.value eq company2.businessType.value}"> selected </c:if>>${btype.value}</option>
								</c:forEach>								
							</select></td>
            </tr>
            <tr>
            	<td class="right"><span class="required">*</span> Phone  :</td>
                <td><input type="text" id="company_phone" name="company2.phone" value="${company2.phone}" class="w385"/></td>
            </tr>
             <tr>
            	<td class="right">Cellphone  :</td>
                <td><input type="text" name="company2.cellphone" value="${company2.cellphone}" class="w385" /></td>
            </tr>
            <tr>
            	<td class="right">Fax  :</td>
                <td><input type="text" name="company2.fax" value="${company2.fax}" class="w385"/></td>
            </tr>
              <tr>
            	<td class="right"><span class="required">*</span> Contact Person  :</td>
                <td><input type="text" id="contact_person" name="company2.contactPerson" value="${company2.contactPerson}" class="w385"/></td>
            </tr>
            <tr>
            	<td class="right">Designation  :</td>
                <td><input type="text" id="contact_person_designation" name="company2.contactPersonDesignation" value="${company2.contactPersonDesignation}" class="w385"/></td>
            </tr>
            <tr>
            	<td class="right">Remarks  :</td>
                <td><input type="text" id="remarks" name="company2.remarks" value="${company2.remarks}" class="w385"/></td>
            </tr>
        </table>
	</div>
    
    <div>
		<h2><a name="HTMLMETASettings" id="HTMLMETASettings">HTML META Settings</a></h2>
		<table class="tblSettings">       	
            <tr>
            	<td class="right">HTML Title  :</td>
                <td><textarea name="company2.title" cols=40  rows=2  class="w385">${company2.title}</textarea></td>
            </tr>
             <tr>
            	<td class="right"> HTML Description  :</td>
                <td><textarea name="company2.metaDescription" cols=40  rows=2  class="w385" >${company2.metaDescription}</textarea></td>
            </tr>
             <tr>
            	<td class="right"> HTML Keywords  :</td>
                <td><textarea name="company2.keywords" cols=40  rows=2   class="w385" >${company2.keywords}</textarea></td>
            </tr>            
        </table>	
	</div>
    <c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
    <div>
		<h2><a name="WebToGoSettings" id="WebToGoSettings">WebToGo Settings</a></h2>
		<table class="tblSettings">
        	<tr>
            	<td class="right"><span class="required">*</span> Domain Name  :</td>
                <td><input type="text" id="domain_name" name="company2.domainName" value="${company2.domainName}" class="w385" /></td>
            </tr>
            <tr>
            	<td class="right">Server Name  :</td>
                <td><input type="text" id="server_name" name="company2.serverName" value="${company2.serverName}" class="w385" /></td>
            </tr>           
            <tr>
            	<td class="right">Expiry Date  :</td>
                <td><fmt:formatDate pattern="MM-dd-yyyy" value="${company2.expiryDate}" var="ed"/>
										 
							<input type="text" id="expiry_date" name="expiryDate" value="${ed}" class="w385"/> 						
							<a href="javascript:;" id="expiry_date_button"><img src="images/calendar.png" alt="Open Calendar"/></a>
					 		<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "expiry_date",     // id of the input field
							        ifFormat       :    "%m-%d-%Y",      // format of the input field
							        button         :    "expiry_date_button",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true
							    });
							</script> </td>
            </tr>
             <tr>
            	<td class="right">Website Online Date  :</td>
                <td><fmt:formatDate pattern="MM-dd-yyyy"  value="${company2.companySettings.onlineDate}"  var="od"/>										 
						
							<input type="text"  id="online_date" name="onlineDate" value="${od}"  readonly="readonly"  class="w385"/>													
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
            	<td class="right">Monthly Maintenance Charge  :</td>
                <td><input type="text" id="monthly_charge" name="company2.companySettings.monthlyCharge" value="${company2.companySettings.monthlyCharge}"  class="w385" /></td>
            </tr>            
        </table>
        <br />
        <table width="100%" border="0" cellspacing="0" cellpadding="6" class="companiesTable">
	    <tr>
		  <th width="20px"></th>
		  <th>Sections / Features </th>
		  <th></th>
		  <th></th>		  
		</tr>
        <tr>
		  <td><input type="checkbox" name="hasFeaturedSinglePages" onchange="matchTextfield('newCompanyForm','hasFeaturedSinglePages','company2.companySettings.hasFeaturedSinglePages','company2.companySettings.maxFeaturedSinglePages');" <c:if test="${company2.companySettings.hasFeaturedSinglePages eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasFeaturedSinglePages" <c:if test="${company2.companySettings.hasFeaturedSinglePages eq true}"> value="true"</c:if>  />	</td>
		  <td>Featured Single Pages</td>
		  <td>Maximum</td>
		  <td><input type="text" class="w100" id="max_featured_single_pages" name="company2.companySettings.maxFeaturedSinglePages" value="${company2.companySettings.maxFeaturedSinglePages}" name="tx1"  <c:if test="${company2.companySettings.hasFeaturedSinglePages eq false}"> disabled</c:if> /></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasFeaturedPages" onchange="matchTextfield('newCompanyForm','hasFeaturedPages','company2.companySettings.hasFeaturedPages','company2.companySettings.maxFeaturedPages');" <c:if test="${company2.companySettings.hasFeaturedPages eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasFeaturedPages" <c:if test="${company2.companySettings.hasFeaturedPages eq true}"> value="true"</c:if>  />	</td>
		  <td>Featured Pages</td>
		  <td>Maximum</td>
		  <td><input type="text" id="max_featured_pages" name="company2.companySettings.maxFeaturedPages" value="${company2.companySettings.maxFeaturedPages}" name="tx1"  <c:if test="${company2.companySettings.hasFeaturedPages eq false}"> disabled</c:if> class="w100" /></td>
		</tr>
		
        <tr>
		  <td><input type="checkbox" name="hasFeaturedProducts" onchange="matchTextfield('newCompanyForm','hasFeaturedProducts','company2.companySettings.hasFeaturedProducts','company2.companySettings.maxFeaturedProducts');" <c:if test="${company2.companySettings.hasFeaturedProducts eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasFeaturedProducts" <c:if test="${company2.companySettings.hasFeaturedProducts eq true}"> value="true"</c:if>  /></td>
		  <td>Featured Products</td>
		  <td>Maximum</td>
		  <td><input type="text" id="max_featured_products" name="company2.companySettings.maxFeaturedProducts" value="${company2.companySettings.maxFeaturedProducts}" <c:if test="${company2.companySettings.hasFeaturedProducts eq false}"> disabled</c:if>  class="w100" /></td>	
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasBestSellers" onchange="matchTextfield('newCompanyForm','hasBestSellers','company2.companySettings.hasBestSellers','company2.companySettings.maxBestSellers');" <c:if test="${company2.companySettings.hasBestSellers eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasBestSellers" <c:if test="${company2.companySettings.hasBestSellers eq true}"> value="true"</c:if>  /></td>
		  <td>Best Sellers</td>
		  <td>Maximum</td>
		  <td><input type="text" id="max_best_sellers" name="company2.companySettings.maxBestSellers" value="${company2.companySettings.maxBestSellers}" <c:if test="${company2.companySettings.hasBestSellers eq false}"> disabled</c:if>  class="w100" /></td>	
		</tr>
        <tr>
		  <td><input type="checkbox" name="hasCategoryMenu" onchange="matchTextfield('newCompanyForm','hasCategoryMenu','company2.companySettings.hasCategoryMenu','company2.companySettings.maxCategoryMenu');" <c:if test="${company2.companySettings.hasCategoryMenu eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasCategoryMenu" <c:if test="${company2.companySettings.hasCategoryMenu eq true}"> value="true"</c:if>  />
										</td>
		  <td>Category Menu</td>
		  <td>Maximum</td>
		  <td><input type="text" class="w100" id="max_category_menu" name="company2.companySettings.maxCategoryMenu" value="${company2.companySettings.maxCategoryMenu}" <c:if test="${company2.companySettings.hasCategoryMenu eq false}"> disabled</c:if> /></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasProducts" onchange="matchTextfield('newCompanyForm','hasProducts','company2.companySettings.hasProducts','company2.companySettings.productsPerPage');" <c:if test="${company2.companySettings.hasProducts eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasProducts" <c:if test="${company2.companySettings.hasProducts eq true}"> value="true"</c:if>  />
										</td>
		  <td>Products</td>
		  <td>Products Per Page</td>
		  <td><input type="text" id="products_per_page" name="company2.companySettings.productsPerPage" value="${company2.companySettings.productsPerPage}"  <c:if test="${company2.companySettings.hasProducts eq false}"> disabled</c:if> class="w100" /></td>	
		</tr>
        <tr>
		  <td><input type="checkbox" name="hasPackages" onchange="matchTextfield('newCompanyForm','hasPackages','company2.companySettings.hasPackages','company2.companySettings.packagesPerPage');" <c:if test="${company2.companySettings.hasPackages eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasPackages" <c:if test="${company2.companySettings.hasPackages eq true}"> value="true"</c:if>  />
										</td>
		  <td>Packages</td>
		  <td>Packages Per Page</td>
		  <td><input type="text" id="packages_per_page" name="company2.companySettings.packagesPerPage" value="${company2.companySettings.packagesPerPage}"  <c:if test="${company2.companySettings.hasPackages eq false}"> disabled</c:if> class="w100" /></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasOtherFields" onchange="matchTextfield('newCompanyForm','hasOtherFields','company2.companySettings.hasOtherFields','company2.companySettings.maxOtherFields');" <c:if test="${company2.companySettings.hasOtherFields eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasOtherFields" <c:if test="${company2.companySettings.hasOtherFields eq true}"> value="true"</c:if>  />
										</td>
		  <td>Other Fields</td>
		  <td>Max Other Fields</td>
		  <td><input type="text" class="w100" id="max_other_fields" name="company2.companySettings.maxOtherFields" value="${company2.companySettings.maxOtherFields}"  <c:if test="${company2.companySettings.hasOtherFields eq false}"> disabled</c:if> /> </td>		
		</tr>
        <tr>
		  <td><input type="checkbox" name="showBrands" onchange="matchTextfield('newCompanyForm','showBrands','company2.companySettings.showBrands','');" <c:if test="${company2.companySettings.showBrands eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.showBrands" <c:if test="${company2.companySettings.showBrands eq true}"> value="true"</c:if>  />
										</td>
		  <td>Brands</td>
		  <td></td>
		  <td></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasEventCalendar" onchange="matchTextfield('newCompanyForm','hasEventCalendar','company2.companySettings.hasEventCalendar','');" <c:if test="${company2.companySettings.hasEventCalendar eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasEventCalendar" <c:if test="${company2.companySettings.hasEventCalendar eq true}"> value="true"</c:if>  />
										</td>
		  <td>Event Calendar</td>
		  <td></td>
		  <td></td>
		</tr>
        <tr>
		  <td><input type="checkbox" name="hasRepeatingEvent" onchange="matchTextfield('newCompanyForm','hasRepeatingEvent','company2.companySettings.hasRepeatingEvent','');" <c:if test="${company2.companySettings.hasRepeatingEvent eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasRepeatingEvent" <c:if test="${company2.companySettings.hasRepeatingEvent eq true}"> value="true"</c:if>  />
										</td>
		  <td>Repeating Events</td>
		  <td></td>
		  <td></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasMemberFeature" onchange="matchTextfield('newCompanyForm','hasMemberFeature','company2.companySettings.hasMemberFeature','');"  <c:if test="${company2.companySettings.hasMemberFeature eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasMemberFeature" <c:if test="${company2.companySettings.hasMemberFeature eq true}"> value="1" </c:if> />
										</td>
		  <td>Member</td>
		  <td></td>
		  <td></td>		
		</tr>
        <tr>
		  <td><input type="checkbox" name="hasBulletinFeature" onchange="matchTextfield('newCompanyForm','hasBulletinFeature','company2.companySettings.hasBulletinFeature','');"  <c:if test="${company2.companySettings.hasBulletinFeature eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasBulletinFeature" <c:if test="${company2.companySettings.hasBulletinFeature eq true}"> value="true" </c:if> />
										</td>
		  <td>Message Board</td>
		  <td></td>
		  <td></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasUserRights" onchange="matchTextfield('newCompanyForm','hasUserRights','company2.companySettings.hasUserRights','');"  <c:if test="${company2.companySettings.hasUserRights eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasUserRights" <c:if test="${company2.companySettings.doNotGenerateBilling eq true}"> value="true" </c:if> />
										</td>
		  <td>User Rights</td>
		  <td></td>
		  <td></td>	
		</tr>
        <tr>
		  <td><input type="checkbox" name="canBatchUpdateExcelFiles"  <c:if test="${company2.companySettings.canBatchUpdateExcelFiles eq true}"> checked</c:if>   />
										</td>
		  <td>Can Batch Update Through Excel Files</td>
		  <td></td>
		  <td></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="manyAttachments" onchange="matchTextfield('newCompanyForm','manyAttachments','company2.companySettings.manyAttachments','');"><input type="hidden" name="company2.companySettings.manyAttachments"></td>
		  <td>Many Attachments</td>
		  <td></td>
		  <td></td>	
		</tr>
		<tr>
			<td><input type="checkbox" name="hasTestimonials" onchange="matchTextfield('newCompanyForm','hasTestimonials','company2.companySettings.hasTestimonials','');"
				<c:if test="${company2.companySettings.hasTestimonials eq true}"> checked</c:if> />
				<input type="hidden" name="company2.companySettings.hasTestimonials"
				<c:if test="${company2.companySettings.hasTestimonials eq true}"> value="true" </c:if> />
			</td>
			<td>Has Promo Code</td>
			<td></td>
			<td></td>
		</tr>
        <tr>
			<td><input type="checkbox" name="hasMobileSite" onchange="matchTextfield('newCompanyForm','hasMobileSite','company2.companySettings.hasMobileSite','');"
				<c:if test="${company2.companySettings.hasMobileSite eq true}"> checked</c:if> />
				<input type="hidden" name="company2.companySettings.hasMobileSite"
				<c:if test="${company2.companySettings.hasMobileSite eq true}"> value="true" </c:if> />
			</td>
			<td>Has Mobile Site</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td><input type="checkbox" name="hasPromoCode" onchange="matchTextfield('newCompanyForm','hasPromoCode','company2.companySettings.hasPromoCode','');"
				<c:if test="${company2.companySettings.hasPromoCode eq true}"> checked</c:if> />
				<input type="hidden" name="company2.companySettings.hasPromoCode"
				<c:if test="${company2.companySettings.hasPromoCode eq true}"> value="true" </c:if> />
			</td>
			<td>Has Promo Code</td>
			<td></td>
			<td></td>
		</tr>
	  </table>
        	
	</div>
    
    <div>
		<h2><a name="ECommerceOption" id="ECommerceOption">E-Commerce Option </a></h2>
		 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="companiesTable">
	    <tr>
		  <th width="20px"></th>
		  <th>Sections / Features </th>
		  <th></th>
		  <th></th>		  
		</tr>
        <tr>
		  <td><input type="checkbox" name="hasOrder" onchange="matchTextfield('newCompanyForm','hasOrder','company2.companySettings.hasOrder','');"  <c:if test="${company2.companySettings.hasOrder eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasOrder" <c:if test="${company2.companySettings.hasOrder eq true}"> value="true" </c:if> /></td>
		  <td>Orders</td>
		  <td></td>
		  <td></td>
		</tr>
        <tr class="oddRow">
		  <td><input type="checkbox" name="hasWishlist" onchange="matchTextfield('newCompanyForm','hasWishlist','company2.companySettings.hasWishlist','');"  <c:if test="${company2.companySettings.hasWishlist eq true}"> checked</c:if>   />
											<input type="hidden" name="company2.companySettings.hasWishlist" <c:if test="${company2.companySettings.hasWishlist eq true}"> value="true" </c:if> />
										</td>
		  <td>Wishlist</td>
		  <td></td>
		  <td></td>	
		</tr>
        <tr>
		  <td><input type="checkbox" name="hasPalAccount" value="true" onchange="showToMainFilterField(this); matchTextfield('newCompanyForm','hasPalAccount','company2.companySettings.hasPalAccount','');" ${hasPalAccount? 'checked="checked"' : ""}<c:if test="${company2.companySettings.hasPalAccount eq true}"> checked</c:if>  />
											<input type="hidden" name="company2.companySettings.hasPalAccount" <c:if test="${company2.companySettings.hasPalAccount eq true}"> value="true" </c:if> />
										</td>
		  <td>Paypal Account</td>
		  <td></td>
		  <td></td>
		</tr>              
	  </table>	
	</div>
   </div><!-- //domtab -->  
   <div align="right" style="padding-right:10px; padding-top:10px"><input type="button" class="btnBlue" value="Cancel" onclick="window.location='dashboard.do'" /> &nbsp; 
   <c:if test="${empty company2}">
					<input type="submit" name="submit" value="Add New" class="btnBlue">
				</c:if>
			<c:if test="${not empty company2}">
					<input type="submit" name="submit" value="Update" class="btnBlue">
				</c:if>
		</div>
	</div><!--//mainContent-->
	
	<div class="sidenav">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Image Settings</h3>
	  <table width="100%">
      	<tr>
        	<td rowspan="2" width="40px" class="font33 blue">1</td>
            <td class="blueGreen">Width</td>
            <td><input type="text" id="image1_width" size="4" name="company2.companySettings.image1Width" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image1Width}" </c:if> class="w50"/> &nbsp;<span class="required">*</span></td>
        </tr>
      	<tr>
      	  <td class="blueGreen">Height</td>
      	  <td><input type="text" id="image1_height" size="4" name="company2.companySettings.image1Heigth" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image1Heigth}" </c:if> class="w50"/> &nbsp;<span class="required">*</span> </td>
   	    </tr>
        <tr>
        	<td></td>
            <td colspan="2"><input type="checkbox" name="image1Forced" onchange="matchTextfield('newCompanyForm','image1Forced','company2.companySettings.image1Forced','');" <c:if test="${company2.companySettings.image1Forced eq true}"> checked</c:if>  /><input type="hidden" name="company2.companySettings.image1Forced" <c:if test="${company2.companySettings.image1Forced eq true}"> value="true"</c:if>  />	<span class="font11 green">[Forced Image Resize]</span></td>
        </tr>
        <tr><td colspan="3" class="RightLine"></td></tr>
        <tr>
        	<td rowspan="2" class="font33 blue">2</td>
            <td class="blueGreen">Width</td>
            <td><input type="text" id="image2_width" size="4" name="company2.companySettings.image2Width" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image2Width}" </c:if> class="w50"/> &nbsp;<span class="required">*</span></td>
        </tr>
      	<tr>
      	  <td class="blueGreen">Height</td>
      	  <td><input type="text" id="image2_height" size="4" name="company2.companySettings.image2Heigth" <c:if test="${empty company2}"> value="200" </c:if> <c:if test="${not empty company2}"> value="${company2.companySettings.image2Heigth}" </c:if> class="w50"/> &nbsp;<span class="required">*</span></td>
   	    </tr>
        <tr>
        	<td></td>
            <td colspan="2"><input type="checkbox" name="image2Forced" onchange="matchTextfield('newCompanyForm','image2Forced','company2.companySettings.image2Forced','');" <c:if test="${company2.companySettings.image2Forced eq true}"> checked</c:if>  />										
											<input type="hidden" name="company2.companySettings.image2Forced" <c:if test="${company2.companySettings.image2Forced eq true}"> value="true"</c:if>  />																					
										 	<span class="font11 green">[Forced Image Resize]</span></td>
        </tr>
        <tr><td colspan="3" class="RightLine"></td></tr>
      </table>
	  <p class="imageNote font11">Maximum Images Allowed &nbsp;&nbsp; <input type="text" size="4" width="100px" id="max_allowed_images" name="company2.companySettings.maxAllowedImages" value="${company2.companySettings.maxAllowedImages}" class="w50"/></p>
	  
      <h3><img src="images/package.png" align="absmiddle" /> Shipping Price Control Panel</h3>
	  <div style="padding:8px">
       ShippingPrice Implementation
       <select size="1" id="shippingType" name="shippingType" onchange="toggle()" class="w150">
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
      
            
      </div><br>

	  <h3><img src="images/page_white_put.png" align="absmiddle" /> Downloadable files option</h3>
	  <table border="0" cellspacing="0" cellpadding="3">
	    <tr>
        	<td colspan="3">Will Expire</td>
        </tr>
        <tr>        
		  <td width="18px"><input type="checkbox" name="willExpire" onchange="matchTextfield('newCompanyForm','willExpire','company2.companySettings.willExpire','company2.companySettings.expiryDate');" <c:if test="${company2.companySettings.willExpire eq true}"> checked</c:if>  />
							<input type="hidden" name="company2.companySettings.willExpire" <c:if test="${company2.companySettings.willExpire eq true}"> value="true"</c:if>  />									
						</td>
		  <td width="105px" class="font11 blue01">No. of days</td>
          <td><input type="text" class="w80" id="expiryDate" name="company2.companySettings.expiryDate" value="${company2.companySettings.expiryDate}" name="tx1"  <c:if test="${company2.companySettings.willExpire eq false}"> disabled</c:if> class="w80"/></td>
		</tr>
        <tr><td colspan="3" class="RightLine"></td></tr>
        <tr>
        	<td colspan="3">Limit number of downloads</td>
        </tr>
        <tr>        
		  <td><input type="checkbox" name="limitDownloads" onchange="matchTextfield('newCompanyForm','limitDownloads','company2.companySettings.limitDownloads','company2.companySettings.downloads');" <c:if test="${company2.companySettings.limitDownloads eq true}"> checked</c:if>  />
							<input type="hidden" name="company2.companySettings.limitDownloads" <c:if test="${company2.companySettings.limitDownloads eq true}"> value="true"</c:if>  />									
						</td>
		  <td class="font11 blue01">No. of downloads</td>
          <td><input type="text" id="downloads" name="company2.companySettings.downloads" value="${company2.companySettings.downloads}" name="tx1"  <c:if test="${company2.companySettings.limitDownloads eq false}"> disabled</c:if> class="w80"/></td>
		</tr>          
	  </table>
	</div><!--//sidenav-->
	<div class="clear"></div>
	
    </div><!-- contentWrapper -->
  </div><!--//container-->
  </c:if>
  </form>
</body>
</html>


	


