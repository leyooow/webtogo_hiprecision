<%@ page contentType="text/html; charset=utf-8" language="java"%>
<c:set var="p" value="pv" />
<style>
.searchResults li a.active {
    background: #a0a09e !important;
}

</style>


<script>
window.addEventListener( 'load', function( event ) {
	var param = new URL(location.href).searchParams.get("q");
    console.log(param);
    ///getItemDetails(param,'');
    document.getElementById('keyword').value = param;
    search(1);
});
</script>


<td class="sidenavContainer">
<div class="sidenav">

	<table>
		<tr>
			<td colspan="3">
				<select style="display: none" name="language" id="language" onchange="search(1)">
					<option value="default">English</option>
					<option value="Vietnamese">Tiếng Việt</option >
				</select>
				
				<u><a style="font-size:14px;" href="online-search.do" class="${p eq 'pv' ? 'active' : ''}">Patient Preparation</a></u> | 
				<u><a style="font-size:14px;" href="online-lab-search.do" class="${lab eq 'lv' ? 'active' : ''}">Laboratory Instructions</a></u>
				
						
				<br><br>
			</td>
		</tr>
	</table>


	<table>
	<!-- TR for notifNew and notifUpdated -->
		<!-- <tr>
			<td>
			TEST
			</td>
			<td colspan="3">
				<ul id="notifNew">
					NEW
				</ul>
				<ul id="notifUpdated">
					UPDATED
				</ul>
			</td>
		</tr> -->
	<!-- //////////////////////////////// -->	
		
		<tr>
			<td>Search &nbsp;</td>
			<td><input name="keyword" id="keyword" type="text"
				class="inputSearch" value="${fn:escapeXml(keyword)}" autocomplete="off" /></td>
			<td><input type="image" src="images/btnGo2.gif"
				style="margin-left: 5px" onclick="search(1)"/></td>
		</tr>
	</table>
	<!-- need for pagination.js -->
	
	<ul class="searchResults" id="searchResults">
		
	</ul>
	<script type="text/javascript" src="scripts/pagination.js"></script>
	
	<span id="paginationSpan"></span>
	<input type="hidden" value="${company.companySettings.productsPerPage}" id="itemPerPage">
	<!-- need for pagination.js end-->
</div>
</td>

<script> 
keyword.onkeyup=function(event){		
	if(event.keyCode == 13){
		search(1);
	}
};
function search(pageNumber){
	console.log("invoked... search");
	var language = document.getElementById("language").value;
	var groupName = "Test Procedures";
	// var otherFieldName = "Section";
	var otherFieldName = "TEST";
	var otherFieldValue = "IMAGING";
	var otherFieldCompator = "ne";
	var keyword = document.getElementById("keyword").value;
	var searchResults = document.getElementById("searchResults");
	var notifNewIsEmpty = true;
	var notifUpdatedIsEmpty = true;
	
	DWRCategoryAction.searchByKeywordAndGroup(pageNumber,keyword,groupName,otherFieldName,otherFieldValue,otherFieldCompator,language,function(data){
		searchResults.innerHTML = "";	
		if(data!=null&&data.total>0){
			//method from pagination.js
			createPagination(pageNumber,data.total,"search");
			for(var index = 0;index<data.list.length;index++){
				var result = data.list[index];
				var li = document.createElement('li');
				li.setAttribute('class','icotestprocedure');

				var img1 = document.createElement('img');
				img1.setAttribute("src","images/oval_top.png");
				img1.setAttribute("class","itl");

				var img2 = document.createElement('img');
				img2.setAttribute("src","images/oval_bottom.png");
				img2.setAttribute("class","ibl");
				//---AKO				
				var a = document.createElement('a');
				var notifDuration_orig = "${company.notifDuration}";
				var notifDuration = parseInt(notifDuration_orig);
				
				var today = new Date();
				
				console.log("notifDuration: " + notifDuration);
				
			    var createdOnNotif = new Date(result.createdOn);
			    createdOnNotif.setDate(result.createdOn.getDate() + notifDuration);
			    
			    var updatedOnNotif = new Date(result.updatedOn);
			    updatedOnNotif.setDate(result.updatedOn.getDate() + notifDuration);
			    
			    var addToNotifNew = false;
			    var addToNotifUpdated = false; 
			    if(createdOnNotif.getTime() >= today.getTime()){
			    	a.innerHTML = result.name+" <font style='font-size: 10px; color:red'>new</font>";
			    	addToNotifNew = true;
			    	notifNewIsEmpty = false;
			    }
			    else if (updatedOnNotif.getTime() >= today.getTime()){
			    	a.innerHTML =  result.name+" <font style='font-size: 10px; color:blue'>updated</font>";
			    	addToNotifUpdated = true;
			    	notifUpdatedIsEmpty = false;
			    }
			    else{
			    	a.innerHTML = result.name;
			    }
			    
			   /*  SCRIPT FOR notfNew and notifUpdated ********************/ 
			   
			   /* if(addToNotifNew){
			    	var notifContent = document.getElementById("notifNew");
			    }
			    else if(addToNotifUpdated){
			    	var notifContent = document.getElementById("notifUpdated");
			    }
			    if(addToNotifNew || addToNotifUpdated){
			    	var b = document.createElement('a');
			    	b.innerHTML = "<li>" + result.name + "</li><br>";
			    	
			    	b.setAttribute("name","notifResult");
					b.setAttribute("id","notifResult"+index);
					b.href = "javascript:getItemDetails('"+result.id+"','notifResult"+index+"')";
					if(index == 0){			
						getItemDetails(result.id,"notifResult"+index);
					}
					
					notifContent.appendChild(b);
				   
			    } */
			  /* ****************************************************** */
			    
			    
				a.setAttribute("name","linkResult");
				a.setAttribute("id","linkResult"+index);
				a.href = "javascript:getItemDetails('"+result.id+"','linkResult"+index+"')";
				if(index == 0){			
					getItemDetails(result.id,"linkResult"+index);
				}
				

				li.appendChild(img1);
				li.appendChild(img2);
				li.appendChild(a);
				searchResults.appendChild(li);
			}
			
			/*SCRIPT FOR notfNew and notifUpdated ************************* */
			
			/* if (notifNewIsEmpty){
				var emptyContent = document.getElementById("notifNew");
				emptyContent.innerHTML = "NEW<br><i>-none-</i><br><br>";
			}
			if (notifUpdatedIsEmpty){
				var emptyContent = document.getElementById("notifUpdated");
				emptyContent.innerHTML = "UPDATED<br><i>-none-</i>";
			} */
			/* ************************************************************ */
			
			
		}else{
			var mainContent = document.getElementById("mainContent");
			mainContent.innerHTML = "";
			createPagination(1,1,"");
			searchResults.innerHTML = "No item found" ;			
		}			
	});	
}
function getItemDetails(id,linkId){
	var language = document.getElementById("language").value;
	var linkResult = document.getElementsByName("linkResult");
	for(var index = 0; index<linkResult.length;index++){
		linkResult[index].removeAttribute("class");
	}
	var mainContent = document.getElementById("mainContent");
	
	
	DWRCategoryAction.getCategoryItemById(id,language,function(data){
		mainContent.innerHTML = "";
		document.getElementById(linkId).setAttribute("class","active");	
		
		var table1 = document.createElement("table");
		var tr = document.createElement("tr");
		var td = document.createElement("td"); 
		td.setAttribute("colspan","3");
		var h3 = document.createElement("h3");
		var u = document.createElement("u");
		var itemName = document.createTextNode(data.name!=null?data.name:"");
		u.appendChild(itemName);
		h3.appendChild(u);
		td.appendChild(h3);
		table1.appendChild(td);
		//table1.appendChild(createTableRow("TEST NAME",data.name!=null?data.name:""));
		//table1.appendChild(createTableRow("TEST PROCEDURE",data.shortDescription!=null?data.shortDescription:""));
		//table1.appendChild(createTableRow("TEST CODE",data.sku));
		//table1.appendChild(createTableRow("TEST GROUP",data.otherDetails));
		mainContent.appendChild(table1);

		var otherTest = "";
		var testComposition = "";
		var intendedUse = "";
		var methodology = "";
		var laboratorySection = "";
		
		var specialInstructions = "";
		var patientPreparations2 = ""; //09242021
		
		var collection = "";
		var specimenVolume = "";
		var alternative = "";
		var specimenStability = "";
		var roomTemperature = "";
		var refridgeratedTemperature = "";
		var freezerTemperature = "";
		var transportTemperature = "";
		var rejectionCriteria = "";
		var runningDay = "";
		var cutOffTime = "";
		var tat = "";
		var referenceInterval = "";
		var limitation = "";
		var faq = "";
		var relatedWords = "";
		
		var otherTestKey = "OTHER TEST REQUEST NAME";
		var testCompositionKey = "TEST COMPOSITION";
		var intendedUseKey = "INTENDED USE";
		var methodologyKey = "METHODOLOGY";
		var laboratorySectionKey = "LABORATORY SECTION";
		
		var specialInstructionsKey = "SPECIAL INSTRUCTIONS";
		
		var patientPreparationsKey2 = "PATIENT PREPARATIONS"; //09242021
		
		var collectionKey = "COLLECTION/SAMPLE CONTAINER";
		var specimenVolumeKey = "SPECIMEN AND VOLUME REQUIREMENT";
		var alternativeKey = "ALTERNATIVE SPECIMEN AND VOLUME REQUIREMENT";
		var specimenStabilityKey = "SPECIMEN STABILITY";
		var roomTemperatureKey = "ROOM TEMPERATURE (15-25°C)";
		var refridgeratedTemperatureKey = "REFRIGERATED TEMPERATURE (2-8°C)";
		var freezerTemperatureKey = "FREEZER TEMPERATURE (-20°C)";
		var transportTemperatureKey = "TRANSPORT TEMPERATURE";
		var rejectionCriteriaKey = "REJECTION CRITERIA";
		var runningDayKey = "RUNNING DAY";
		var cutOffTimeKey = "CUT OFF TIME";
		var tatKey = "TAT/RELEASING OF RESULTS";
		var referenceIntervalKey = "REFERENCE INTERVAL/RESULT INTERPRETATION";
		var limitationKey = "LIMITATIONS/INTERFERENCES";
		var faqKey = "FREQUENTLY ASKED QUESTIONS (FAQS)";
		var relatedWordsKey = "RELATED WORDS";

		if(data.categoryItemOtherFields.length>0){
			var table2 = document.createElement("table");
			table2.setAttribute("class","table2");

			for(var index = 0;index<data.categoryItemOtherFields.length;index++){
				var categoryItemOtherField = data.categoryItemOtherFields[index];
				var fieldName = categoryItemOtherField.otherField.name;
				var content = categoryItemOtherField.content;
				
				if(fieldName == otherTestKey){
					otherTest = content;
				}
				else if(fieldName == testCompositionKey){
					testComposition = content;
				}
				else if(fieldName == intendedUseKey){
					intendedUse = content;
				}
				else if(fieldName == methodologyKey){
					methodology = content;
				}
				else if(fieldName == laboratorySectionKey){
					laboratorySection = content;
				}
				
				
				else if(fieldName == specialInstructionsKey){
					specialInstructions = content;
				}
				
				else if(fieldName == patientPreparationsKey2){ //09242021
					patientPreparations2 = content;
				}
				
				
				
				else if(fieldName == collectionKey){
					collection = content;
				}
				else if(fieldName == specimenVolumeKey){
					specimenVolume = content;
				}
				else if(fieldName == alternativeKey){
					alternative = content;
				}
				else if(fieldName == specimenStabilityKey){
					specimenStability = content;
				}
				else if(fieldName == roomTemperatureKey){
					roomTemperature = content;
				}
				else if(fieldName == refridgeratedTemperatureKey){
					refridgeratedTemperature = content;
				}
				else if(fieldName == freezerTemperatureKey){
					freezerTemperature = content;
				}
				else if(fieldName == transportTemperatureKey){
					transportTemperature = content;
				}
				else if(fieldName == rejectionCriteriaKey){
					rejectionCriteria = content;
				}
				else if(fieldName == runningDayKey){
					runningDay = content;
				}
				else if(fieldName == cutOffTimeKey){
					cutOffTime = content;
				}
				else if(fieldName == tatKey){
					tat = content;
				}
				else if(fieldName == referenceIntervalKey){
					referenceInterval = content;
				}
				else if(fieldName == limitationKey){
					limitation = content;
				}
				else if(fieldName == faqKey){
					faq = content;
				}
				else if(fieldName == relatedWordsKey){
					relatedWords = content;
				}
				
				console.log('id = ' + data.id);
			}
						
			table2.appendChild(createTableRow("Test", data.name ));
			table2.appendChild(createTableRow("Other Test Request Name", otherTest ));
			table2.appendChild(createTableRow("Test Composition", testComposition ));
			table2.appendChild(createTableRow("Intended Use", intendedUse ));
			table2.appendChild(createTableRow("Methodology", methodology ));
			//table2.appendChild(createTableRow("Laboratory Section", laboratorySection ));
			//table2.appendChild(createTableRow("Special Instructions", specialInstructions ));
			
			table2.appendChild(createTableRow("Patient Preparations", patientPreparations2 )); //09242021
			
			//table2.appendChild(createTableRow("Collection/Sample Container", collection));
			//table2.appendChild(createTableRow("Specimen and Volume Requirement", specimenVolume));
			//table2.appendChild(createTableRow("Alternative Specimen and Volume Requirement", alternative ));
			//table2.appendChild(createTableRow("Specimen Stability", specimenStability ));
			//table2.appendChild(createTableRow("•&nbsp;&nbsp;&nbsp;Room Temperature (15-25°C)", roomTemperature ));
			//table2.appendChild(createTableRow("•&nbsp;&nbsp;&nbsp;Refrigerated Temperature (2-8°C)", refridgeratedTemperature ));
			//table2.appendChild(createTableRow("•&nbsp;&nbsp;&nbsp;Freezer Temperature (-20°C)", freezerTemperature ));
			//table2.appendChild(createTableRow("Transport Temperature", transportTemperature ));
			//table2.appendChild(createTableRow("Rejection Criteria", rejectionCriteria ));
			table2.appendChild(createTableRow("Running Day", runningDay ));
			table2.appendChild(createTableRow("Cut Off Time", cutOffTime ));
			table2.appendChild(createTableRow("TAT/Releasing of Results", tat ));
			//table2.appendChild(createTableRow("Reference Interval/Result Interpretation", referenceInterval ));
			//table2.appendChild(createTableRow("Limitations/Interferences", limitation ));
			table2.appendChild(createTableRow("Frequently Asked Questions (FAQs)", faq ));
			
			var relatedWordsRow = createTableRow("Related Words/Test", relatedWords );
			relatedWordsRow.style.fontSize = '8px';
			table2.appendChild(relatedWordsRow);

			mainContent.appendChild(table2);
			
			var table3 = document.createElement("table");
			var tr = document.createElement("tr");
			var td = document.createElement("td"); 
			td.setAttribute("colspan","3");
			var p = document.createElement("p");
			var i = document.createElement("i");
// 			var dis = document.createTextNode("\"HPD only store samples at 2°C to 8°C after testing, additional tests will be entertained if stability of the test is within this temperature\"");
			var dis = document.createTextNode("After testing, HPD stores samples at refrigerated temperature for 6 days. Additional tests may only be entertained within 6 days provided sample is still stable for the analyte requested.");
			//i.appendChild(dis);
			
			var noteText = document.createTextNode("NOTE: ");
			var note = document.createElement("i");
			//note.style.cssText = "font-weight:bolder";
			//note.appendChild(noteText);
			p.appendChild(note);
			p.appendChild(i);
			td.appendChild(p);
			table3.appendChild(td);
			mainContent.appendChild(table3);
			
			history.pushState("", "", "?q=" + data.name);
			
		}		
	});
}
function getItemDetailsOld(id,linkId){
	var language = document.getElementById("language").value;
	var linkResult = document.getElementsByName("linkResult");
	for(var index = 0; index<linkResult.length;index++){
		linkResult[index].removeAttribute("class");
	}
	var mainContent = document.getElementById("mainContent");
	
	DWRCategoryAction.getCategoryItemById(id,language,function(data){
		mainContent.innerHTML = "";
		document.getElementById(linkId).setAttribute("class","active");	
		
		var table1 = document.createElement("table");
		var tr = document.createElement("tr");
		var td = document.createElement("td"); 
		td.setAttribute("colspan","3");
		var h3 = document.createElement("h3");
		var u = document.createElement("u");
		var itemName = document.createTextNode(data.name != null ? data.name : "");
		u.appendChild(itemName);
		h3.appendChild(u);
		td.appendChild(h3);
		table1.appendChild(td);
		//table1.appendChild(createTableRow("TEST NAME",data.name!=null?data.name:""));
		//table1.appendChild(createTableRow("TEST PROCEDURE",data.shortDescription!=null?data.shortDescription:""));
		//table1.appendChild(createTableRow("TEST CODE",data.sku));
		//table1.appendChild(createTableRow("TEST GROUP",data.otherDetails));
		
		mainContent.appendChild(table1);
		
		
		var purpose = "";
		//var patientPreparation = "";
		var section = "";
		var methodology = "";
		var volumeReq = "";
		var specimenContainer = "";
		var specimenRejection = "";
		var storageAndTransport = "";
		var runningDay = "";
		var cutOffTime = "";
		var timeOfRelease = "";
		var testComposition = "";
		var intendedUse = "";
		var referringClinicians = "";
		var specimenStability = "";
		var resultInterpretation = "";
		var interferences = "";
		var faq = "";
		var relatedWords = "";
		/*
			new > beth
		*/
		var test = "";
		var rejectionCriteria = "";
		var otherName = "";
		var transportTemperature = "";
		var specimenStability = "";
		var minimumVolume = "";
		var alternativeSpecimen = "";
		var preferredSpecimen = "";
		var specialInstructions = "";
		
		var patientPreparations2 = ""; //09242021
		
		/*
			new > beth
		*/

		var purposeKey = "PURPOSE";
		//var patientPreparationKey = "SPECIAL INSTRUCTION";
		var sectionKey = "SECTION";
		var methodologyKey = "METHODOLOGY";
		var volumeReqKey = "SPECIMEN REQUIREMENT";
		var specimenContainerKey = "SPECIMEN CONTAINER";
		var specimenRejectionKey = "SPECIMEN REJECTION";
		var storageAndTransportKey = "STORAGE AND TRANSPORT";
		var runningDayKey = "RUNNING DAY";
		var cutOffTimeKey = "CUT OFF TIME";
		var timeOfReleaseKey = "TIME OF RELEASE";
		var testCompositionKey = "TEST COMPOSITION";
		var intendedUseKey = "INTENDED USE";
		var referringCliniciansKey = "REFERRING CLINICIANS";
		var specimenStabilityKey = "SPECIMEN STABILITY";
		var resultInterpretationKey = "REFERENCE INTERVAL/RESULT INTERPRETATION";
		var interferencesKey = "LIMITATIONS/INTERFERENCES";
		var faqKey = "FREQUENTLY ASKED QUESTIONS (FAQS)";
		var relatedWordsKey = "RELATED WORDS";
		
		var patientPreparationsKey2 = "PATIENT PREPARATIONS"; //09242021
		
		var roomTemperature = "";
		var refrigerated = "";
		var frozen = "";
		/*
			new > beth
		*/
		var testKey = "TEST";
		var rejectionCriteriaKey = "REJECTION CRITERIA";
		var otherNameKey = "OTHER NAME";
		var transportTemperatureKey = "TRANSPORT TEMPERATURE";
		var specimenStabilityKey = "SPECIMEN STABILITY";
		var minimumVolumeKey = "MINIMUM VOLUME";
		var alternativeSpecimenKey = "ALTERNATIVE SPECIMEN(S) & VOLUME REQUIREMENT";
		var preferredSpecimenKey = "PREFERRED SPECIMEN(S) & VOLUME REQUIREMENT";
		var specialInstructionsKey = "SPECIAL INSTRUCTIONS";
		
		
		var roomTemperatureKey = "ROOM TEMPERATURE";
		var refrigeratedKey = "REFRIGERATED";
		var frozenKey = "FROZEN";
		/*
			new > beth
		*/
		

		if(data.categoryItemOtherFields.length>0){
			var table2 = document.createElement("table");
			table2.setAttribute("class","table2");

			for(var index = 0; index < data.categoryItemOtherFields.length; index++){
				var categoryItemOtherField = data.categoryItemOtherFields[index];
				var fieldName = categoryItemOtherField.otherField.name;
				var content = categoryItemOtherField.content;
				
				if(fieldName == purposeKey){
					purpose = content;
				}
				/* 
				if(fieldName == patientPreparationKey){
					patientPreparation = content;
				}
				*/
				if(fieldName == sectionKey){
					section = content;
				}
				if(fieldName == methodologyKey){
					methodology = content;
				}
				if(fieldName == volumeReqKey){
					volumeReq = content;
				}
				if(fieldName == specimenContainerKey){
					specimenContainer = content;
				}
				if(fieldName == specimenRejectionKey){
					specimenRejection = content;
				}
				if(fieldName == storageAndTransportKey){
					storageAndTransport = content;
				}
				if(fieldName == runningDayKey){
					runningDay = content;
				}
				if(fieldName == cutOffTimeKey){
					cutOffTime = content;
				}
				
				
				if(fieldName == patientPreparationsKey2){ //09242021
					patientPreparations2 = content;
				}
				
				
				if(fieldName == timeOfReleaseKey){
					timeOfRelease = content;
				}	
				if(fieldName == testCompositionKey){
					testComposition = content;
				}
				if(fieldName == intendedUseKey){
					intendedUse = content;
				}
				if(fieldName == referringCliniciansKey){
					referringClinicians = content;
				}
				if(fieldName == specimenStabilityKey){
					specimenStability = content;
				}
				if(fieldName == resultInterpretationKey){
					resultInterpretation = content;
				}
				if(fieldName == interferencesKey){
					interferences = content;
				}
				if(fieldName == faqKey){
					faq = content;
				}
				if(fieldName == relatedWordsKey){
					relatedWords = content;
				}
				if(fieldName == testKey){
					test = content;
				}
				if(fieldName == rejectionCriteriaKey ){
					rejectionCriteria = content;
				}
				if(fieldName == otherNameKey ){
					otherName = content;
				}
				if(fieldName == transportTemperatureKey ){
					transportTemperature = content;
				}
				if(fieldName == specimenStabilityKey ){
					specimenStability = content;
				}
				if(fieldName == minimumVolumeKey ){
					minimumVolume = content;
				}
				if(fieldName == preferredSpecimenKey ){
					preferredSpecimen = content;
				}
				if(fieldName == alternativeSpecimenKey ){
					alternativeSpecimen = content;
				}
				if(fieldName == specialInstructionsKey ){
					specialInstructions = content;
				}
				if(fieldName == roomTemperatureKey ){
					roomTemperature = content;
				}
				if(fieldName == refrigeratedKey ){
					refrigerated = content;
				}
				if(fieldName == frozenKey ){
					frozen = content;
				}
			}
			table2.appendChild(createTableRow(testKey ,data.name));
			table2.appendChild(createTableRow(sectionKey,section));
			table2.appendChild(createTableRow(otherNameKey ,otherName));
			table2.appendChild(createTableRow(methodologyKey,methodology));
			table2.appendChild(createTableRow("Intended Use",purpose));
			table2.appendChild(createTableRow(specimenContainerKey,specimenContainer));
			
			//Preferred Specimen and Volume Requirement = Specimen Requirement
			if(preferredSpecimen != ""){
				table2.appendChild(createTableRow(preferredSpecimenKey ,preferredSpecimen));
			}else{
				table2.appendChild(createTableRow(preferredSpecimenKey ,volumeReq));
			}
			
			table2.appendChild(createTableRow(alternativeSpecimenKey ,alternativeSpecimen));
			table2.appendChild(createTableRow(minimumVolumeKey ,minimumVolume));
			
			table2.appendChild(createTableRow(patientPreparationsKey2 ,patientPreparations2)); //09242021
			
			table2.appendChild(createTableRow(specialInstructionsKey ,specialInstructions));
			
			/*
			//Special Instructions = Patient Preparation
			if(specialInstructions != ""){
				table2.appendChild(createTableRow(specialInstructionsKey ,specialInstructions));
			}else{
				table2.appendChild(createTableRow(specialInstructionsKey ,patientPreparation));
			}
			*/
			
			table2.appendChild(createTableRow(specimenStabilityKey ,""));
			
			table2.appendChild(createTableRow("&nbsp;&nbsp;&nbsp;&nbsp;• Room Temperature",roomTemperature ));
			table2.appendChild(createTableRow("&nbsp;&nbsp;&nbsp;&nbsp;• Refrigerated",refrigerated ));
			table2.appendChild(createTableRow("&nbsp;&nbsp;&nbsp;&nbsp;• Frozen",frozen ));
			
			//Transport Temperature = Storage and Transport OR Specimen Packaging for Transport
			if(transportTemperature != ""){
				table2.appendChild(createTableRow(transportTemperatureKey ,transportTemperature));
			}else{
				table2.appendChild(createTableRow(transportTemperatureKey ,storageAndTransport));
			}
			
			//Rejection Criteria = Specimen Rejection
			if(rejectionCriteria != ""){
				table2.appendChild(createTableRow(rejectionCriteriaKey ,rejectionCriteria));
			}else{
				table2.appendChild(createTableRow(rejectionCriteriaKey ,specimenRejection));
			}
			
			table2.appendChild(createTableRow(runningDayKey,runningDay));
			table2.appendChild(createTableRow("Cut off day/time",cutOffTime));
			table2.appendChild(createTableRow("Turn Around Time",timeOfRelease));
			
			table2.appendChild(createTableRow("TEST COMPOSITION",testComposition));
			table2.appendChild(createTableRow("INTENDED USE",intendedUse));
			table2.appendChild(createTableRow("REFERRING CLINICIANS",referringClinicians));
			table2.appendChild(createTableRow("SPECIMEN STABILITY",specimenStability));
			table2.appendChild(createTableRow("REFERENCE INTERVAL/RESULT INTERPRETATION",resultInterpretation));
			table2.appendChild(createTableRow("LIMITATIONS/INTERFERENCES",interferences));
			table2.appendChild(createTableRow("FREQUENTLY ASKED QUESTIONS (FAQS)",faq));
						
			var rel = createTableRow("RELATED WORDS",relatedWords);
			rel.style.fontSize  = '8px';
			table2.appendChild(rel);
			
			/*
			table2.appendChild(createTableRow(patientPreparationKey,patientPreparation));
			
			
			table2.appendChild(createTableRow("Specimen Requirement",volumeReq));
			 table2.appendChild(createTableRow(volumeReqKey,volumeReq)); 
			
			table2.appendChild(createTableRow(specimenRejectionKey,specimenRejection));
			table2.appendChild(createTableRow(storageAndTransportKey,storageAndTransport));
			
			*/
			//table2.appendChild(createTableRow("","*HPD only store samples at 2oC to 8oC after testing, additional tests will be entertained if stability of the test is within this temperature"))

			mainContent.appendChild(table2);
			
			var table3 = document.createElement("table");
			var tr = document.createElement("tr");
			var td = document.createElement("td"); 
			td.setAttribute("colspan","3");
			var p = document.createElement("p");
			var i = document.createElement("i");
// 			var dis = document.createTextNode("\"HPD only store samples at 2°C to 8°C after testing, additional tests will be entertained if stability of the test is within this temperature\"");
			var dis = document.createTextNode("After testing, HPD stores samples at refrigerated temperature for 6 days. Additional tests may only be entertained within 6 days provided sample is still stable for the analyte requested.");
			//i.appendChild(dis);
			
			var noteText = document.createTextNode("NOTE: ");
			var note = document.createElement("i");
			//note.style.cssText = "font-weight:bolder";
			//note.appendChild(noteText);
			p.appendChild(note);
			p.appendChild(i);
			td.appendChild(p);
			table3.appendChild(td);
			
			mainContent.appendChild(table3);
		}		
	});
}


function createTableRow(title,content){
	var tr = document.createElement("tr");
	var td1 = document.createElement("td");
	var td2 = document.createElement("td");
	var td3 = document.createElement("td");
	var titleText = title; // document.createTextNode(title);
	var separator = document.createTextNode( title.toUpperCase() == 'SPECIMEN STABILITY' ? '' :  ':');
	var contentText = document.createTextNode(content);
	
	
	var strong = document.createElement("strong");
	var p = document.createElement("p");
	strong.innerHTML = titleText;
	
	var span;
	if(title == "Specimen and Volume Requirement"){
		span = document.createElement('span');
		span.innerHTML = "<br><b>Note:</b> Follow tube manufacturer recommendation.";
		span.setAttribute('style','text-transform: none;font-size:0.9em')
	}
	
	td1.appendChild(strong);
	
	if(title == "Specimen and Volume Requirement") td1.appendChild(span);
	
	td1.setAttribute("style","width: 250px; color: #550202;text-transform: uppercase;");
	td1.setAttribute("valign","top");
		

	td2.appendChild(separator);
	td2.setAttribute("style","width: 20px;vertical-align:top");
	td2.setAttribute("valign","top");
	
	if(	content == null || 
		content.replace(/<\/(.*?)>/, "").replace(/<(.*?)>/, "").trim() == '' &&
		title.toUpperCase() != 'SPECIMEN STABILITY'
			){
		content = 'Not Applicable';
	}
	
	td3.innerHTML = content; /* .replace(/<\/(.*?)>/, "").replace(/<(.*?)>/, ""); */
	td3.setAttribute("style","vertical-align:top");
	td3.setAttribute("class","selectable");
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	return tr;
}
//search(1);
</script>