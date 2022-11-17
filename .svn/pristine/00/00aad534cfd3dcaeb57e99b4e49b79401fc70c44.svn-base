<script>
	function populateProvClinic()
	{
		var selectedRegion=document.getElementById("regionClinic").value;
		var singleProvince = listOfAllProvince(selectedRegion);

		document.getElementById("divProvinceClinic").innerHTML = "<select name='provinceClinic' id='provinceClinic' key='provinceClinic' class='textbox3' >" + singleProvince + "</select>";
	}

	function populateProvince()
	{
		var selectedRegion=document.getElementById("region").value;
		var singleProvince = listOfAllProvince(selectedRegion); 

		document.getElementById("divProvince").innerHTML= "<select name='province' id='province' key='province' class='textbox3' >" + singleProvince + "</select>";
	}  	

	function listOfAllProvince(selectedRegion){
		var _regions=['Metro Manila','CAR','Region I','Region II','Region III','Region IV-A','Region IV-B','Region V','Region VI','Region VII','Region VIII','Region IX','Region X','Region XI','Region XII','Region XIII','ARMM'];
		var province=['Caloocan City--Las Piñas City--Makati City--Malabon City--Mandaluyong City--Manila--Marikina City--Muntinlupa City--Navotas City--Parañaque City--Pasay City--Pasig City--Pateros--Quezon City--San Juan City--Taguig City--Valenzuela City','Abra--Apayao--Baguio City--Benguet--Ifugao--Kalinga--Mountain Province','Dagupan City--Ilocos Norte--Ilocos Sur--La Union--Pangasinan','Batanes--Cagayan--Isabela--Nueva Vizcaya--Quirino','Aurora--Bataan--Bulacan--Nueva Ecija--Pampanga--Tarlac--Zambales--Angeles City','Batangas--Cavite--Quezon--Rizal--Laguna--Lucena City','Marinduque--Occidental Mindoro--Oriental Mindoro--Palawan--Romblon','Albay--Camarines Norte--Camarines Sur--Catanduanes--Masbate--Sorsogon','Aklan--Antique--Bacolod City--Capiz--Guimaras--Iloilo--Iloilo City--Negros Occidental','Bohol--Cebu--Cebu City--Lapu-Lapu City--Mandaue City--Negros Oriental--Siquijor','Biliran--Eastern Samar--Leyte--Northern Samar--Ormoc City--Samar--Southern Leyte--Tacloban City','Isabela City--Zamboanga City--Zamboanga del Norte--Zamboanga del Sur--Zamboanga Sibugay','Bukidnon--Cagayan de Oro City--Camiguin--Iligan City--Lanao del Norte--Misamis Occidental--Misamis Oriental','Compostela Valley--Davao City[--Davao del Norte--Davao del Sur--Davao Oriental','Cotabato--Cotabato City--General Santos City--Sarangani--South Cotabato--Sultan Kudarat','Agusan del Norte--Agusan del Sur--Butuan City--Surigao del Norte--Surigao del Sur','Basilan--Lanao del Sur--Maguindanao--Sulu--Tawi-Tawi'];
		var singleProvince ="";
		for(var i=0;i<_regions.length;i++){
				if(selectedRegion==_regions[i]){
				singleProvince=singleProvince+"<option value=''>--Select Province--</option>";
				var pr=province[i].split('--');
					for(var j=0;j<pr.length;j++){
						singleProvince=singleProvince+("<option value='"+pr[j]+"'>"+pr[j]+"</option>");
						}
				}
		}
		return singleProvince;
	}

	function listOfAllCountries()
	{
	    var country = "";
	    var hidCountryClinic = "";
	    var hidCountry = "";
	    var listCountryClinic = "";
	    var listCountry = "";
	    var selCountryClinic = "";
	    var selCountry = "";

  	    hidCountryClinic  = document.getElementById("countryClinic2").value;
	    hidCountry        = document.getElementById("country2").value;
	    selCountryClinic  = "<select onchange='changeCountryClinic()' name='countryClinic' id='countryClinic' key='countryClinic' class='textbox3'>";
	    selCountry        = "<select onchange='changeCountry()' name='country' id='country' key='country' class='textbox3'>"
		    
     	country=country+"<option value=''>--Select a Country--</option>";
		country=country+"<option value='Afghanistan'>Afghanistan</option>";
		country=country+"<option value='Albania'>Albania</option>";
		country=country+"<option value='Algeria'>Algeria</option>";
		country=country+"<option value='American Samoa'>American Samoa</option>";
		country=country+"<option value='Andorra'>Andorra</option>";
		country=country+"<option value='Angola'>Angola</option>";
		country=country+"<option value='Anguilla'>Anguilla</option>";
		country=country+"<option value='Antarctica'>Antarctica</option>";
		country=country+"<option value='Antigua and Barbuda'>Antigua and Barbuda</option>";
		country=country+"<option value='Argentina'>Argentina</option>";
		country=country+"<option value='Armenia'>Armenia</option>";
		country=country+"<option value='Aruba'>Aruba</option>";
		country=country+"<option value='Australia'>Australia</option>";
		country=country+"<option value='Austria'>Austria</option>";
		country=country+"<option value='Azerbaijan'>Azerbaijan</option>";
		country=country+"<option value='Bahamas'>Bahamas</option>";
		country=country+"<option value='Bahrain'>Bahrain</option>";
		country=country+"<option value='Bangladesh'>Bangladesh</option>";
		country=country+"<option value='Barbados'>Barbados</option>";
		country=country+"<option value='Belarus'>Belarus</option>";
		country=country+"<option value='Belgium'>Belgium</option>";
		country=country+"<option value='Belize'>Belize</option>";
		country=country+"<option value='Benin'>Benin</option>";
		country=country+"<option value='Bermuda'>Bermuda</option>";
		country=country+"<option value='Bhutan'>Bhutan</option>";
		country=country+"<option value='Bolivia'>Bolivia</option>";
		country=country+"<option value='Bosnia and Herzegovina'>Bosnia and Herzegovina</option>";
		country=country+"<option value='Botswana'>Botswana</option>";
		country=country+"<option value='Bouvet Island'>Bouvet Island</option>";
		country=country+"<option value='Brazil'>Brazil</option>";
		country=country+"<option value='British Indian Ocean Territory'>British Indian Ocean Territory</option>";
		country=country+"<option value='Brunei Darussalam'>Brunei Darussalam</option>";
		country=country+"<option value='Bulgaria'>Bulgaria</option>";
		country=country+"<option value='Burkina Faso'>Burkina Faso</option>";
		country=country+"<option value='Burundi'>Burundi</option>";
		country=country+"<option value='Cambodia'>Cambodia</option>";
		country=country+"<option value='Cameroon'>Cameroon</option>";
		country=country+"<option value='Canada'>Canada</option>";
		country=country+"<option value='Cape Verde'>Cape Verde</option>";
		country=country+"<option value='Cayman Islands'>Cayman Islands</option>";
		country=country+"<option value='Central African Republic'>Central African Republic</option>";
		country=country+"<option value='Chad'>Chad</option>";
		country=country+"<option value='Chile'>Chile</option>";
		country=country+"<option value='China'>China</option>";
		country=country+"<option value='Christmas Island'>Christmas Island</option>";
		country=country+"<option value='Cocos (Keeling) Islands'>Cocos (Keeling) Islands</option>";
		country=country+"<option value='Colombia'>Colombia</option>";
		country=country+"<option value='Comoros'>Comoros</option>";
		country=country+"<option value='Congo'>Congo</option>";
		country=country+"<option value='Congo, The Democratic Republic of The'>Congo, The Democratic Republic of The</option>";
		country=country+"<option value='Cook Islands'>Cook Islands</option>";
		country=country+"<option value='Costa Rica'>Costa Rica</option>";
		country=country+"<option value='Cote D'ivoire'>Cote D'ivoire</option>";
		country=country+"<option value='Croatia'>Croatia</option>";
		country=country+"<option value='Cuba'>Cuba</option>";
		country=country+"<option value='Cyprus'>Cyprus</option>";
		country=country+"<option value='Czech Republic'>Czech Republic</option>";
		country=country+"<option value='Denmark'>Denmark</option>";
		country=country+"<option value='Djibouti'>Djibouti</option>";
		country=country+"<option value='Dominica'>Dominica</option>";
		country=country+"<option value='Dominican Republic'>Dominican Republic</option>";
		country=country+"<option value='Ecuador'>Ecuador</option>";
		country=country+"<option value='Egypt'>Egypt</option>";
		country=country+"<option value='El Salvador'>El Salvador</option>";
		country=country+"<option value='Equatorial Guinea'>Equatorial Guinea</option>";
		country=country+"<option value='Eritrea'>Eritrea</option>";
		country=country+"<option value='Estonia'>Estonia</option>";
		country=country+"<option value='Ethiopia'>Ethiopia</option>";
		country=country+"<option value='Falkland Islands (Malvinas)'>Falkland Islands (Malvinas)</option>";
		country=country+"<option value='Faroe Islands'>Faroe Islands</option>";
		country=country+"<option value='Fiji'>Fiji</option>";
		country=country+"<option value='Finland'>Finland</option>";
		country=country+"<option value='France'>France</option>";
		country=country+"<option value='French Guiana'>French Guiana</option>";
		country=country+"<option value='French Polynesia'>French Polynesia</option>";
		country=country+"<option value='French Southern Territories'>French Southern Territories</option>";
		country=country+"<option value='Gabon'>Gabon</option>";
		country=country+"<option value='Gambia'>Gambia</option>";
		country=country+"<option value='Georgia'>Georgia</option>";
		country=country+"<option value='Germany'>Germany</option>";
		country=country+"<option value='Ghana'>Ghana</option>";
		country=country+"<option value='Gibraltar'>Gibraltar</option>";
		country=country+"<option value='Greece'>Greece</option>";
		country=country+"<option value='Greenland'>Greenland</option>";
		country=country+"<option value='Grenada'>Grenada</option>";
		country=country+"<option value='Guadeloupe'>Guadeloupe</option>";
		country=country+"<option value='Guam'>Guam</option>";
		country=country+"<option value='Guatemala'>Guatemala</option>";
		country=country+"<option value='Guinea'>Guinea</option>";
		country=country+"<option value='Guinea-bissau'>Guinea-bissau</option>";
		country=country+"<option value='Guyana'>Guyana</option>";
		country=country+"<option value='Haiti'>Haiti</option>";
		country=country+"<option value='Heard Island and Mcdonald Islands'>Heard Island and Mcdonald Islands</option>";
		country=country+"<option value='Holy See (Vatican City State)'>Holy See (Vatican City State)</option>";
		country=country+"<option value='Honduras'>Honduras</option>";
		country=country+"<option value='Hong Kong'>Hong Kong</option>";
		country=country+"<option value='Hungary'>Hungary</option>";
		country=country+"<option value='Iceland'>Iceland</option>";
		country=country+"<option value='India'>India</option>";
		country=country+"<option value='Indonesia'>Indonesia</option>";
		country=country+"<option value='Iran, Islamic Republic of'>Iran, Islamic Republic of</option>";
		country=country+"<option value='Iraq'>Iraq</option>";
		country=country+"<option value='Ireland'>Ireland</option>";
		country=country+"<option value='Israel'>Israel</option>";
		country=country+"<option value='Italy'>Italy</option>";
		country=country+"<option value='Jamaica'>Jamaica</option>";
		country=country+"<option value='Japan'>Japan</option>";
		country=country+"<option value='Jordan'>Jordan</option>";
		country=country+"<option value='Kazakhstan'>Kazakhstan</option>";
		country=country+"<option value='Kenya'>Kenya</option>";
		country=country+"<option value='Kiribati'>Kiribati</option>";
		country=country+"<option value='Korea, Democratic People's Republic of'>Korea, Democratic People's Republic of</option>";
		country=country+"<option value='Korea, Republic of'>Korea, Republic of</option>";
		country=country+"<option value='Kuwait'>Kuwait</option>";
		country=country+"<option value='Kyrgyzstan'>Kyrgyzstan</option>";
		country=country+"<option value='Lao People's Democratic Republic'>Lao People's Democratic Republic</option>";
		country=country+"<option value='Latvia'>Latvia</option>";
		country=country+"<option value='Lebanon'>Lebanon</option>";
		country=country+"<option value='Lesotho'>Lesotho</option>";
		country=country+"<option value='Liberia'>Liberia</option>";
		country=country+"<option value='Libyan Arab Jamahiriya'>Libyan Arab Jamahiriya</option>";
		country=country+"<option value='Liechtenstein'>Liechtenstein</option>";
		country=country+"<option value='Lithuania'>Lithuania</option>";
		country=country+"<option value='Luxembourg'>Luxembourg</option>";
		country=country+"<option value='Macao'>Macao</option>";
		country=country+"<option value='Macedonia, The Former Yugoslav Republic of'>Macedonia, The Former Yugoslav Republic of</option>";
		country=country+"<option value='Madagascar'>Madagascar</option>";
		country=country+"<option value='Malawi'>Malawi</option>";
		country=country+"<option value='Malaysia'>Malaysia</option>";
		country=country+"<option value='Maldives'>Maldives</option>";
		country=country+"<option value='Mali'>Mali</option>";
		country=country+"<option value='Malta'>Malta</option>";
		country=country+"<option value='Marshall Islands'>Marshall Islands</option>";
		country=country+"<option value='Martinique'>Martinique</option>";
		country=country+"<option value='Mauritania'>Mauritania</option>";
		country=country+"<option value='Mauritius'>Mauritius</option>";
		country=country+"<option value='Mayotte'>Mayotte</option>";
		country=country+"<option value='Mexico'>Mexico</option>";
		country=country+"<option value='Micronesia, Federated States of'>Micronesia, Federated States of</option>";
		country=country+"<option value='Moldova, Republic of'>Moldova, Republic of</option>";
		country=country+"<option value='Monaco'>Monaco</option>";
		country=country+"<option value='Mongolia'>Mongolia</option>";
		country=country+"<option value='Montserrat'>Montserrat</option>";
		country=country+"<option value='Morocco'>Morocco</option>";
		country=country+"<option value='Mozambique'>Mozambique</option>";
		country=country+"<option value='Myanmar'>Myanmar</option>";
		country=country+"<option value='Namibia'>Namibia</option>";
		country=country+"<option value='Nauru'>Nauru</option>";
		country=country+"<option value='Nepal'>Nepal</option>";
		country=country+"<option value='Netherlands'>Netherlands</option>";
		country=country+"<option value='Netherlands Antilles'>Netherlands Antilles</option>";
		country=country+"<option value='New Caledonia'>New Caledonia</option>";
		country=country+"<option value='New Zealand'>New Zealand</option>";
		country=country+"<option value='Nicaragua'>Nicaragua</option>";
		country=country+"<option value='Niger'>Niger</option>";
		country=country+"<option value='Nigeria'>Nigeria</option>";
		country=country+"<option value='Niue'>Niue</option>";
		country=country+"<option value='Norfolk Island'>Norfolk Island</option>";
		country=country+"<option value='Northern Mariana Islands'>Northern Mariana Islands</option>";
		country=country+"<option value='Norway'>Norway</option>";
		country=country+"<option value='Oman'>Oman</option>";
		country=country+"<option value='Pakistan'>Pakistan</option>";
		country=country+"<option value='Palau'>Palau</option>";
		country=country+"<option value='Palestinian Territory, Occupied'>Palestinian Territory, Occupied</option>";
		country=country+"<option value='Panama'>Panama</option>";
		country=country+"<option value='Papua New Guinea'>Papua New Guinea</option>";
		country=country+"<option value='Paraguay'>Paraguay</option>";
		country=country+"<option value='Peru'>Peru</option>";
		country=country+"<option value='Philippines'>Philippines</option>";
		country=country+"<option value='Pitcairn'>Pitcairn</option>";
		country=country+"<option value='Poland'>Poland</option>";
		country=country+"<option value='Portugal'>Portugal</option>";
		country=country+"<option value='Puerto Rico'>Puerto Rico</option>";
		country=country+"<option value='Qatar'>Qatar</option>";
		country=country+"<option value='Reunion'>Reunion</option>";
		country=country+"<option value='Romania'>Romania</option>";
		country=country+"<option value='Russian Federation'>Russian Federation</option>";
		country=country+"<option value='Rwanda'>Rwanda</option>";
		country=country+"<option value='Saint Helena'>Saint Helena</option>";
		country=country+"<option value='Saint Kitts and Nevis'>Saint Kitts and Nevis</option>";
		country=country+"<option value='Saint Lucia'>Saint Lucia</option>";
		country=country+"<option value='Saint Pierre and Miquelon'>Saint Pierre and Miquelon</option>";
		country=country+"<option value='Saint Vincent and The Grenadines'>Saint Vincent and The Grenadines</option>";
		country=country+"<option value='Samoa'>Samoa</option>";
		country=country+"<option value='San Marino'>San Marino</option>";
		country=country+"<option value='Sao Tome and Principe'>Sao Tome and Principe</option>";
		country=country+"<option value='Saudi Arabia'>Saudi Arabia</option>";
		country=country+"<option value='Senegal'>Senegal</option>";
		country=country+"<option value='Serbia and Montenegro'>Serbia and Montenegro</option>";
		country=country+"<option value='Seychelles'>Seychelles</option>";
		country=country+"<option value='Sierra Leone'>Sierra Leone</option>";
		country=country+"<option value='Singapore'>Singapore</option>";
		country=country+"<option value='Slovakia'>Slovakia</option>";
		country=country+"<option value='Slovenia'>Slovenia</option>";
		country=country+"<option value='Solomon Islands'>Solomon Islands</option>";
		country=country+"<option value='Somalia'>Somalia</option>";
		country=country+"<option value='South Africa'>South Africa</option>";
		country=country+"<option value='South Georgia and The South Sandwich Islands'>South Georgia and The South Sandwich Islands</option>";
		country=country+"<option value='Spain'>Spain</option>";
		country=country+"<option value='Sri Lanka'>Sri Lanka</option>";
		country=country+"<option value='Sudan'>Sudan</option>";
		country=country+"<option value='Suriname'>Suriname</option>";
		country=country+"<option value='Svalbard and Jan Mayen'>Svalbard and Jan Mayen</option>";
		country=country+"<option value='Swaziland'>Swaziland</option>";
		country=country+"<option value='Sweden'>Sweden</option>";
		country=country+"<option value='Switzerland'>Switzerland</option>";
		country=country+"<option value='Syrian Arab Republic'>Syrian Arab Republic</option>";
		country=country+"<option value='Taiwan, Province of China'>Taiwan, Province of China</option>";
		country=country+"<option value='Tajikistan'>Tajikistan</option>";
		country=country+"<option value='Tanzania, United Republic of'>Tanzania, United Republic of</option>";
		country=country+"<option value='Thailand'>Thailand</option>";
		country=country+"<option value='Timor-leste'>Timor-leste</option>";
		country=country+"<option value='Togo'>Togo</option>";
		country=country+"<option value='Tokelau'>Tokelau</option>";
		country=country+"<option value='Tonga'>Tonga</option>";
		country=country+"<option value='Trinidad and Tobago'>Trinidad and Tobago</option>";
		country=country+"<option value='Tunisia'>Tunisia</option>";
		country=country+"<option value='Turkey'>Turkey</option>";
		country=country+"<option value='Turkmenistan'>Turkmenistan</option>";
		country=country+"<option value='Turks and Caicos Islands'>Turks and Caicos Islands</option>";
		country=country+"<option value='Tuvalu'>Tuvalu</option>";
		country=country+"<option value='Uganda'>Uganda</option>";
		country=country+"<option value='Ukraine'>Ukraine</option>";
		country=country+"<option value='United Arab Emirates'>United Arab Emirates</option>";
		country=country+"<option value='United Kingdom'>United Kingdom</option>";
		country=country+"<option value='United States'>United States</option>";
		country=country+"<option value='United States Minor Outlying Islands'>United States Minor Outlying Islands</option>";
		country=country+"<option value='Uruguay'>Uruguay</option>";
		country=country+"<option value='Uzbekistan'>Uzbekistan</option>";
		country=country+"<option value='Vanuatu'>Vanuatu</option>";
		country=country+"<option value='Venezuela'>Venezuela</option>";
		country=country+"<option value='Viet Nam'>Viet Nam</option>";
		country=country+"<option value='Virgin Islands, British'>Virgin Islands, British</option>";
		country=country+"<option value='Virgin Islands, U.S.'>Virgin Islands, U.S.</option>";
		country=country+"<option value='Wallis and Futuna'>Wallis and Futuna</option>";
		country=country+"<option value='Western Sahara'>Western Sahara</option>";
		country=country+"<option value='Yemen'>Yemen</option>";
		country=country+"<option value='Zambia'>Zambia</option>";
		country=country+"<option value='Zimbabwe'>Zimbabwe</option>";
		country=country+"</select>";		

		document.getElementById("divCountryClinic").innerHTML = selCountryClinic + country;
  		document.getElementById("divCountry").innerHTML       = selCountry + country;

  		for(i=0;i<document.getElementById("countryClinic").options.length;i++)
  		{
  	  		if(document.getElementById("countryClinic").options[i].value == hidCountryClinic)
  	  		{
  	  			document.getElementById("countryClinic").options[i].selected = true;
  	  		}
  		}

  		for(i=0;i<document.getElementById("country").options.length;i++)
  		{
  	  		if(document.getElementById("country").options[i].value == hidCountry)
  	  		{
  	  			document.getElementById("country").options[i].selected = true;
  	  		}
  		}
	}

	function changeCountryClinic()
	{
		var country = document.getElementById("countryClinic").value;

		if(country != 'Philippines')
		{
			document.getElementById("regionClinic").options[0].selected = true;
			document.getElementById("provinceClinic").options[0].selected = true;
			document.getElementById("regionClinic").disabled = true;
			document.getElementById("provinceClinic").disabled = true;
			document.getElementById("regionClinicLabel").innerHTML = "Region";
			document.getElementById("provinceClinicLabel").innerHTML = "Province";
		}
		else
		{
			document.getElementById("regionClinic").disabled = false;
			document.getElementById("provinceClinic").disabled = false;
			document.getElementById("regionClinicLabel").innerHTML = "Region*";
			document.getElementById("provinceClinicLabel").innerHTML = "Province*";
		}
	}

	function changeCountry()
	{
		var country = document.getElementById("country").value;

		if(country != 'Philippines')
		{
			document.getElementById("region").options[0].selected = true;
			document.getElementById("province").options[0].selected = true;
			document.getElementById("region").disabled = true;
			document.getElementById("province").disabled = true;
			document.getElementById("regionLabel").innerHTML = "Region";
			document.getElementById("provinceLabel").innerHTML = "Province";
		}
		else
		{
			document.getElementById("region").disabled = false;
			document.getElementById("province").disabled = false;
			document.getElementById("regionLabel").innerHTML = "Region*";
			document.getElementById("provinceLabel").innerHTML = "Province*";
		}
	}
</script>

<input type="hidden"  value="${member2.email}" name="email"/>
<input type="hidden" id="username" name="username" value="${member2.username}">
<tr>
	<td width="1%" nowrap>Membership<br>
	<select name="membership" id="membership" key="membership" type="select" class="textbox3" >
			 <option value="Fellow" <c:if test="${pcoMember.membership =='Fellow'}"> selected </c:if>>Fellow</option>
			 <option value="Associate Member"  <c:if test="${pcoMember.membership == 'Associate Member'}"> selected </c:if> >Associate Member</option>
			 <option value="Student Member"  <c:if test="${pcoMember.membership == 'Student Member'}"> selected </c:if> >Student Member</option>
			 <option value="Non-member"  <c:if test="${pcoMember.membership == 'Non-member'}"> selected </c:if>>Non-member</option>
		 </select>	
	</td>
</tr>
<tr>
	<td width="1%" nowrap>Suffix<br>
	    <input name="suffix" id="suffix" key="suffix" type="text" value="${pcoMember.suffix}" class="textbox3" /></td>
<%-- 	
		<select name="suffix" id="suffix" key="suffix" type="select" value="" class="textbox3" >
				 	<option value="">--Select--</option>
				      <option value="OD" <c:if test="${pcoMember.suffix eq 'OD'}">selected</c:if>>O.D.</option>
				      <option value="FPCO" <c:if test="${pcoMember.suffix eq 'FPCO'}">selected</c:if>>F.P.C.O.</option>
				      <option value="FAAO" <c:if test="${pcoMember.suffix eq 'FAAO'}">selected</c:if>>F.A.A.O.</option>
				      <option value="PHD" <c:if test="${pcoMember.suffix eq 'PHD'}">selected</c:if>>P.H.D.</option>
				      <option value="MA" <c:if test="${pcoMember.suffix eq 'MA'}">selected</c:if>>M.A.</option>
		 </select>
--%>		 
</td>
</tr>
<tr>
	<td width="1%" nowrap>Gender<br>
		 <select name="sex" id="sex" key="sex" type="select" value="" class="textbox3" >
				 	<option value="">--Male/Female--</option>
				    <option value="male" <c:if test="${pcoMember.sex eq 'male'}">selected</c:if>>Male</option>
				    <option value="female" <c:if test="${pcoMember.sex eq 'female'}">selected</c:if>>Female</option>
		 </select>
	</td>
</tr>
<tr>
	<td width="1%" nowrap>Birth Date<br>
				    <select name="month" id="month" key="month" type="select" width="145" style="width: 145px">
				      <option value="">--Month--</option>
				      <option <c:if test="${pcoMember.month eq '01'}">selected="selected"</c:if>>January</option>
				      <option <c:if test="${pcoMember.month eq '02'}">selected="selected"</c:if>>February</option>
				      <option <c:if test="${pcoMember.month eq '03'}">selected="selected"</c:if>>March</option>
				      <option <c:if test="${pcoMember.month eq '04'}">selected="selected"</c:if>>April</option>
				      <option <c:if test="${pcoMember.month eq '05'}">selected="selected"</c:if>>May</option>
				      <option <c:if test="${pcoMember.month eq '06'}">selected="selected"</c:if>>June</option>
				      <option <c:if test="${pcoMember.month eq '07'}">selected="selected"</c:if>>July</option>
				      <option <c:if test="${pcoMember.month eq '08'}">selected="selected"</c:if>>August</option>
				      <option <c:if test="${pcoMember.month eq '09'}">selected="selected"</c:if>>September</option>
				      <option <c:if test="${pcoMember.month eq '10'}">selected="selected"</c:if>>October</option>
				      <option <c:if test="${pcoMember.month eq '11'}">selected="selected"</c:if>>November</option>
				      <option <c:if test="${pcoMember.month eq '12'}">selected="selected"</c:if>>December</option>
				    </select>
				    <select name="day" id="day" key="day" type="select" class="inputApplication" width="90" style="width: 90px">
				      <option value="">--Day--</option>
				      <c:forEach begin="1" end="31" var="i" step="1">
				        <option <c:if test="${pcoMember.day eq i}">selected="selected"</c:if>>${i}</option>
				      </c:forEach>
				    </select>
				    <select name="year" id="year" key="year" type="select" class="inputApplication" width="110" style="width: 110px">
				      <option value="">--Year--</option>
				      <c:forEach begin="1950" end="2011" var="i" step="1">
				        <option <c:if test="${pcoMember.year eq i}">selected="selected"</c:if>>${i}</option>
				      </c:forEach>
				    </select>
	</td>
</tr>
<tr>
	<td width="1%" nowrap>PRC License<br>
	<input name="prcLicense" id="prcLicense" key="prcLicense" type="text" value="${pcoMember.prclicense}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Type of Practice<br>
				    <select name="typeOfPractice1" id="typeOfPractice1" key="typeOfPractice1" type="select" class="inputApplication" width="210" style="width: 210px" onchange="concatPractice()">
				      <option value="">--Select--</option>
	 		          <option <c:if test="${pcoMember.type1 eq 'Single Proprietorship'}">selected="selected"</c:if> value="Single Proprietorship">Single Proprietorship</option>
	 		          <option <c:if test="${pcoMember.type1 eq 'Partnership'}">selected="selected"</c:if> value="Partnership">Partnership</option>
	 		          <option <c:if test="${pcoMember.type1 eq 'Eye Center'}">selected="selected"</c:if> value="Eye Center">Eye Center</option>
	 		          <option <c:if test="${pcoMember.type1 eq 'Mall Practice'}">selected="selected"</c:if> value="Mall Practice">Mall Practice</option>
				    </select>
	
	                <select name="typeOfPractice2" id="typeOfPractice2" key="typeOfPractice2" type="select" class="inputApplication" width="135" style="width: 135px" onchange="concatPractice()">
	                  <option value="">--Select--</option>
	 		          <option <c:if test="${pcoMember.type2 eq 'Employed'}">selected="selected"</c:if> value="Employed">Employed</option>
	 		          <option <c:if test="${pcoMember.type2 eq 'Owner'}">selected="selected"</c:if> value="Owner">Owner</option>
	 		          <option <c:if test="${pcoMember.type2 eq 'Consultant'}">selected="selected"</c:if> value="Consultant">Consultant</option>
				    </select>	
				    <input name="typeOfPractice" id="typeOfPractice" key="typeOfPractice" type="hidden" value=""/>

		<input name="typeOfPractice" id="typeOfPractice" key="typeOfPractice" type="hidden" value="${pcoMember.typeofpractice}"/>
	</td>
</tr>
<tr>
	<td width="1%" nowrap><STRONG>Clinic Details</STRONG></td>
	<td width="10px"></td>
	<td></td>
</tr>
<tr>
	<td width="1%" nowrap>Name of Clinic<br>
	<input name="nameClinic" id="nameClinic" key="nameClinic" type="text" value="${pcoMember.nameClinic}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Street/Building<br>
	<input name="streetClinic" id="streetClinic" key="streetClinic" type="text" value="${pcoMember.streetClinic}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>City/Municipality<br>
	<input name="cityClinic" id="cityClinic" key="cityClinic" type="text" value="${pcoMember.cityClinic}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Region<br>
				  <select onchange="populateProvClinic()" name="regionClinic" id="regionClinic" key="regionClinic" type="text" class="textbox3" >
				  <option value="">--Select Region--</option>		  
				  <option <c:if test="${pcoMember.regionClinic eq 'Metro Manila'}">selected="selected"</c:if> value="Metro Manila">National Capital Region </option>
				  <option <c:if test="${pcoMember.regionClinic eq 'CAR'}">selected="selected"</c:if> value="CAR">Cordillera Administrative Region</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region I'}">selected="selected"</c:if> value="Region I">Ilocos Region </option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region II'}">selected="selected"</c:if> value="Region II">Cagayan Valley</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region III'}">selected="selected"</c:if> value="Region III">Central Luzon </option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region IV-A'}">selected="selected"</c:if> value="Region IV-A">CALABARZON</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region IV-B'}">selected="selected"</c:if> value="Region IV-B">MIMAROPA </option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region V'}">selected="selected"</c:if> value="Region V">Bicol Region</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region VI'}">selected="selected"</c:if> value="Region VI">Western Visayas</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region VII'}">selected="selected"</c:if> value="Region VII">Central Visayas</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region VIII'}">selected="selected"</c:if> value="Region VIII">Eastern Visayas</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region IX'}">selected="selected"</c:if> value="Region IX">Zamboanga Peninsula</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region X'}">selected="selected"</c:if> value="Region X">Northern Mindanao</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region XI'}">selected="selected"</c:if> value="Region XI">Davao Region</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region XII'}">selected="selected"</c:if> value="Region XII">SOCCSKSARGEN </option>
				  <option <c:if test="${pcoMember.regionClinic eq 'Region XIII'}">selected="selected"</c:if> value="Region XIII">Caraga Region</option>
				  <option <c:if test="${pcoMember.regionClinic eq 'ARMM'}">selected="selected"</c:if> value="ARMM">Autonomous Region in Muslim Mindanao</option>
				  </select>
	</td>
</tr>
<tr>
	<td width="1%" nowrap>Province<br>
				  <input name="provinceClinic2" id="provinceClinic2" type="hidden" value="${pcoMember.provinceClinic}">
				  <div id="divProvinceClinic"><select name="provinceClinic" id="provinceClinic" key="provinceClinic" class="textbox3" >
			 	    <option value="">--Select Province--</option>
			 	  </select>
			 	  </div>
    </td>
</tr>
<tr>
	<td width="1%" nowrap>Country<br>
    <input type="hidden" id="country2" name="country2" value="${pcoMember.country}"/>
    <div id="divCountry"><select onchange="changeCountry()" name="country" id="country" key="country" class="inputApplication" width="1000" style="width: 1000px"></select></div>
    </td>
</tr>
<tr>
	<td width="1%" nowrap><STRONG>Mailing Address</STRONG></td>
	<td width="10px"></td>
	<td></td>
</tr>

<tr>
	<td width="1%" nowrap>Street 1<br>
	<input name="street1" id="street1" key="street1" type="text" value="${pcoMember.street1}" class="textbox3" /></td>
</tr>

<tr>
	<td width="1%" nowrap>Street 2<br>
	<input name="street2" id="street2" key="street2" type="text" value="${pcoMember.street2}" class="textbox3" /></td>
</tr>

<tr>
	<td width="1%" nowrap>City/Municipality<br>
	<input name="city" id="city" key="city" type="text" value="${pcoMember.city}" class="textbox3" /></td>
</tr>

<tr>
	<td width="1%" nowrap>Region<br>
				  <select onchange="populateProvince()" name="region" id="region" key="region" type="text" class="textbox3" >
				  <option value="">--Select Region--</option>		  
				  <option <c:if test="${pcoMember.region eq 'Metro Manila'}">selected="selected"</c:if> value="Metro Manila">National Capital Region </option>
				  <option <c:if test="${pcoMember.region eq 'CAR'}">selected="selected"</c:if> value="CAR">Cordillera Administrative Region</option>
				  <option <c:if test="${pcoMember.region eq 'Region I'}">selected="selected"</c:if> value="Region I">Ilocos Region </option>
				  <option <c:if test="${pcoMember.region eq 'Region II'}">selected="selected"</c:if> value="Region II">Cagayan Valley</option>
				  <option <c:if test="${pcoMember.region eq 'Region III'}">selected="selected"</c:if> value="Region III">Central Luzon </option>
				  <option <c:if test="${pcoMember.region eq 'Region IV-A'}">selected="selected"</c:if> value="Region IV-A">CALABARZON</option>
				  <option <c:if test="${pcoMember.region eq 'Region IV-B'}">selected="selected"</c:if> value="Region IV-B">MIMAROPA </option>
				  <option <c:if test="${pcoMember.region eq 'Region V'}">selected="selected"</c:if> value="Region V">Bicol Region</option>
				  <option <c:if test="${pcoMember.region eq 'Region VI'}">selected="selected"</c:if> value="Region VI">Western Visayas</option>
				  <option <c:if test="${pcoMember.region eq 'Region VII'}">selected="selected"</c:if> value="Region VII">Central Visayas</option>
				  <option <c:if test="${pcoMember.region eq 'Region VIII'}">selected="selected"</c:if> value="Region VIII">Eastern Visayas</option>
				  <option <c:if test="${pcoMember.region eq 'Region IX'}">selected="selected"</c:if> value="Region IX">Zamboanga Peninsula</option>
				  <option <c:if test="${pcoMember.region eq 'Region X'}">selected="selected"</c:if> value="Region X">Northern Mindanao</option>
				  <option <c:if test="${pcoMember.region eq 'Region XI'}">selected="selected"</c:if> value="Region XI">Davao Region</option>
				  <option <c:if test="${pcoMember.region eq 'Region XII'}">selected="selected"</c:if> value="Region XII">SOCCSKSARGEN </option>
				  <option <c:if test="${pcoMember.region eq 'Region XIII'}">selected="selected"</c:if> value="Region XIII">Caraga Region</option>
				  <option <c:if test="${pcoMember.region eq 'ARMM'}">selected="selected"</c:if> value="ARMM">Autonomous Region in Muslim Mindanao</option>
				  </select>
</td>
</tr>

<tr>
	<td width="1%" nowrap>Province<br>
				  <input name="province2" id="province2" type="hidden" value="${pcoMember.province}">
				  <div id="divProvince"><select name="province" id="province" key="province" class="textbox3" width="350" style="width: 350px">
			 	    <option value="">--Select Province--</option>
			 	  </select>
			 	  </div>
</td>
</tr>

<tr>
	<td width="1%" nowrap>Country<br>
	<input type="hidden" id="countryClinic2" name="countryClinic2" value="${pcoMember.countryClinic}"/>
	<div id="divCountryClinic">
	  <select onchange="changeCountryClinic()" name="countryClinic" id="countryClinic" key="countryClinic" class="inputApplication" width="350" style="width: 350px"></select>
	</div>
	
				  <script language="javascript">
				    populateProvClinic();
				    populateProvince();

					var provClinic = document.getElementById("provinceClinic2").value;
					for(var k=0;k<document.getElementById("provinceClinic").options.length;k++)
					{
						if(document.getElementById("provinceClinic").options[k].value == provClinic)
						{
							document.getElementById("provinceClinic").options[k].selected = "selected";
						}
					}

					var prov = document.getElementById("province2").value;
					for(var k=0;k<document.getElementById("province").options.length;k++)
					{
						if(document.getElementById("province").options[k].value == prov)
						{
							document.getElementById("province").options[k].selected = "selected";
						}
					}

				    listOfAllCountries();
				    changeCountryClinic();
				    changeCountry();
				  </script>			
	</td>
</tr>
<tr>
	<td width="1%" nowrap>Landline<br>
	<input name="landline" id="landline" key="landline" type="text" value="${pcoMember.landline}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Mobile Phone 1<br>
	<input name="mobilePhone1" id="mobilePhone1" key="mobilePhone1" type="text" value="${pcoMember.mobilePhone1}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Mobile Phone 2<br>
	<input name="mobilePhone2" id="mobilePhone2" key="mobilePhone2" type="text" value="${pcoMember.mobilePhone2}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Mobile Phone 3<br>
	<input name="mobilePhone2" id="mobilePhone2" key="mobilePhone3" type="text" value="${pcoMember.mobilePhone3}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Fax<br>
	<input name="fax" id="fax" key="fax" type="text" value="${pcoMember.fax}" class="textbox3" /></td>
</tr>
<tr>
	<td width="1%" nowrap>Year of Membership<br>
	<input name="country" id="country" key="country" type="text" value="${pcoMember.yearofmembership}" class="textbox3" /></td>
</tr>
