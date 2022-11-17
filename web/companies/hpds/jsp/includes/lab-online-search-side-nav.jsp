<%@ page contentType="text/html; charset=utf-8" language="java"%>

<style>
.searchResults li a.active {
    background: #a0a09e !important;
}

</style>

<script>
window.addEventListener( 'load', function( event ) {
	//var param = new URL(location.href).searchParams.get("q");
    //console.log(param);
    //getItemDetails(param,'');
    //document.getElementById('keyword').value = param;
    search(1);
});

</script>


<td class="sidenavContainer">
<div class="sidenav">
	<table>
		
		 <tr>
			<td colspan="3">
			<select name="group" id="group" onchange="search(1)">
				<option value="Analytes">Analytes</option>
				<option value="Lab File">Lab File</option>
			</select>
			</td>
		</tr>
		<tr>
			<td>Search &nbsp;</td>
			<td valign="top"><input name="keyword" id="keyword" type="text"
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
	<%-- <input type="hidden" value="${company.companySettings.productsPerPage}" id="itemPerPage"> --%>
	<input type="hidden" value="20" id="itemPerPage">
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
	var language = 'default';
	var groupName = document.getElementById("group").value;
	var keyword = document.getElementById("keyword").value;
	
	//alert(groupName);
	
	if(groupName == 'Analytes'){
		getAnalytes(pageNumber,keyword,groupName,language);
	}else if(groupName == 'Lab File'){
		getLabFile(pageNumber,keyword,groupName,language);
	}
	
	
}


function getAnalytes(pageNumber,keyword,groupName,language){
	
	var otherFieldName = "ANALYTE";
	var otherFieldValue = "Cobas e602";
	var otherFieldCompator = "ne";
	
	var searchResults = document.getElementById("searchResults");
	var notifNewIsEmpty = true;
	var notifUpdatedIsEmpty = true;
	
	DWRCategoryAction.searchAnalytesByKeywordAndGroup(pageNumber,keyword,groupName,otherFieldName,otherFieldValue,otherFieldCompator,language,function(data){
		searchResults.innerHTML = "";	
		if(data!=null&&data.total>0){
			//method from pagination.js
			//alert(data.total);
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
				
				//console.log("notifDuration: " + notifDuration);
				
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
			    
				a.setAttribute("name","linkResult");
				a.setAttribute("id","linkResult"+index);
				a.href = "javascript:getAnalytesItemDetails('"+result.id+"','linkResult"+index+"')";
				if(index == 0){			
					getAnalytesItemDetails(result.id,"linkResult"+index);
				}
				

				li.appendChild(img1);
				li.appendChild(img2);
				li.appendChild(a);
				searchResults.appendChild(li);
			}
			
		}else{
			var mainContent = document.getElementById("mainContent");
			mainContent.innerHTML = "";
			createPagination(0,0,"");
			searchResults.innerHTML = "No item found";			
		}			
	});	
	
}

function getAnalytesItemDetails(id,linkId){
	//var language = document.getElementById("language").value;
	var language = 'default';

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
		
	
		mainContent.appendChild(table1);

		var analyte = "";
		var analyteMachine = "";
		var sample = "";
		var criticalValue = "";
		var recommendationDilution = "";
		var diluent = "";
		var amr = "";
		var crr = "";
		var repeatTestCriteriaIfBelow = "";
		var repeatTestCriteriaIfAbove = "";
		var acceptableDifference = "";
		
		var groupKey = "ANALYTE- ";
		var analyteKey = "ANALYTE";
		var analyteMachineKey = "MACHINES";
		var sampleKey = "SAMPLE";
		var criticalValueKey = "CRITICAL VALUE";
		var recommendationDilutionKey = "RECOMMENDATION DILUTION";
		var diluentKey = "DILUENT";
		var amrKey = "AMR";
		var crrKey = "CRR";
		var repeatTestCriteriaIfBelowKey = "REPEAT TEST CRITERIA (IF BELOW)";
		var repeatTestCriteriaIfAboveKey = "REPEAT TEST CRITERIA (IF ABOVE)";
		var acceptableDifferenceKey = "ACCEPTABLE DIFFERENCE (%)";
		

		if(data.categoryItemOtherFields.length>0){
			var table2 = document.createElement("table");
			table2.setAttribute("class","table2");

			for(var index = 0;index < data.categoryItemOtherFields.length; index++){
				
				var categoryItemOtherField = data.categoryItemOtherFields[index];
				var fieldName = categoryItemOtherField.otherField.name;
				var content = categoryItemOtherField.content;
				
				if(fieldName == groupKey + analyteKey){
					analyte = content;
				}
				else if(fieldName == groupKey + analyteMachineKey){
					analyteMachine = content;
				}
				else if(fieldName == groupKey + sampleKey){
					sample = content;
				}
				else if(fieldName == groupKey + criticalValueKey){
					criticalValue = content;
				}
				else if(fieldName == groupKey + recommendationDilutionKey){
					recommendationDilution = content;
				}
				else if(fieldName == groupKey + diluentKey){
					diluent = content;
				}
				else if(fieldName == groupKey + amrKey){
					amr = content;
				}
				else if(fieldName == groupKey + crrKey){
					crr = content;
				}
				else if(fieldName == groupKey + repeatTestCriteriaIfBelowKey){
					repeatTestCriteriaIfBelow = content;
				}
				else if(fieldName == groupKey + repeatTestCriteriaIfAboveKey){
					repeatTestCriteriaIfAbove = content;
				}
				else if(fieldName == groupKey + acceptableDifferenceKey){
					acceptableDifference = content;
				}
					
				console.log('id = ' + data.id);
			}
			
			table2.appendChild(createAnalytesTableRow("CATEGORY", data.parent.name ));
			table2.appendChild(createAnalytesTableRow("ANALYTE", data.name ));

			table2.appendChild(createAnalytesTableRow("MACHINE", analyteMachine ));
			table2.appendChild(createAnalytesTableRow("SAMPLE", sample ));
			table2.appendChild(createAnalytesTableRow("CRITICAL VALUE", criticalValue ));
			table2.appendChild(createAnalytesTableRow("RECOMMENDATION DILUTION", recommendationDilution ));
			table2.appendChild(createAnalytesTableRow("DILUENT", diluent ));
			table2.appendChild(createAnalytesTableRow("AMR", amr ));
			table2.appendChild(createAnalytesTableRow("CLINICAL REPORTABLE RANGE (CRR)", crr));
			table2.appendChild(createAnalytesTableRow("REPEAT TEST CRITERIA", ""));
			table2.appendChild(createAnalytesTableRow("IF BELOW", repeatTestCriteriaIfBelow));
			table2.appendChild(createAnalytesTableRow("IF ABOVE", repeatTestCriteriaIfAbove ));
			table2.appendChild(createAnalytesTableRow("ACCEPTABLE DIFFERENCE (%)", acceptableDifference ));


			mainContent.appendChild(table2);
			
			var table3 = document.createElement("table");
			var tr = document.createElement("tr");
			var td = document.createElement("td"); 
			td.setAttribute("colspan","3");
			var p = document.createElement("p");
			var i = document.createElement("i");
			var dis = document.createTextNode("After testing, HPD stores samples at refrigerated temperature for 6 days. Additional tests may only be entertained within 6 days provided sample is still stable for the analyte requested.");
			//i.appendChild(dis);
			
			var noteText = document.createTextNode("");
			var note = document.createElement("i");
			note.style.cssText = "font-weight:bolder";
			note.appendChild(noteText);
			p.appendChild(note);
			p.appendChild(i);
			td.appendChild(p);
			table3.appendChild(td);
			mainContent.appendChild(table3);
			
			history.pushState("", "", "?q="+data.name);
		}		
	});
}

function createAnalytesTableRow(title,content){
	var tr = document.createElement("tr");
	var td1 = document.createElement("td");
	var td2 = document.createElement("td");
	var td3 = document.createElement("td");
	var titleText = title;
	var separator = document.createTextNode( title.toUpperCase() == 'REPEAT TEST CRITERIA' ? '' :  ':');
	var contentText = document.createTextNode(content);
	
	
	var strong = document.createElement("strong");
	var p = document.createElement("p");
	strong.innerHTML = titleText;
	
	var span;
	if(title == "CLINICAL REPORTABLE RANGE (CRR)"){
		span = document.createElement('span');
		span.innerHTML = "<br><b>Note:</b> This is the lowest and highest value that you can report to patient. Please consult your pathologist prior release.";
		span.setAttribute('style','text-transform: none;font-size:0.9em')
	}
	
	if(title == "ACCEPTABLE DIFFERENCE (%)"){
		span = document.createElement('span');
		span.innerHTML = "<br>for repeats and reagent evaluation";
		span.setAttribute('style','text-transform: none;font-size:0.9em')
	}
	
	if(title == "REPEAT TEST CRITERIA"){
		span = document.createElement('span');
		span.innerHTML = "<br>Guideline: Repeat if value fall within the indicated repeat test value/s, and if:" 
						 + "<br>&nbsp;&nbsp;<b>a.</b> It does not correlate with patient clinical diagnosis and sample apperance"
						 + "<br>&nbsp;&nbsp;<b>b.</b> Value is outside the machine’s analytical range (AMR/Reportable Range)"
						 + "<br>&nbsp;&nbsp;<b>c.</b> It falls within or beyond the critical limit and no previous result/s is available in the LIS."
						 + "<br>&nbsp;&nbsp;<b>d.</b> There is a machine flagging in the result."
						 + "<br>&nbsp;&nbsp;<b>e.</b> It does not correlate with previous results"
						 + "<br>&nbsp;&nbsp;<b>f.</b> Bubbles or clots are detected in the sample."						 
						 
		span.setAttribute('style','text-transform: none;font-size:0.9em')
	}
	
	td1.appendChild(strong);
	
	if(title == "CLINICAL REPORTABLE RANGE (CRR)") td1.appendChild(span);
	if(title == "ACCEPTABLE DIFFERENCE (%)") td1.appendChild(span);
	if(title == "REPEAT TEST CRITERIA"){
		td1.setAttribute("colspan","3");
		td1.appendChild(span);
		
	}
		
	
	td1.setAttribute("style","width: 250px; color: #550202;text-transform: uppercase;");
	td1.setAttribute("valign","top");
		

	td2.appendChild(separator);
	td2.setAttribute("style","width: 20px;vertical-align:top");
	td2.setAttribute("valign","top");
	
	
	td3.innerHTML = content; 
	td3.setAttribute("style","width: 250px");
	td3.setAttribute("class","selectable");
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	return tr;
}


function getLabFile(pageNumber,keyword,groupName,language){
	
	var otherFieldName = "TEST";
	var otherFieldValue = "";
	var otherFieldCompator = "ne";
	
	var searchResults = document.getElementById("searchResults");
	var notifNewIsEmpty = true;
	var notifUpdatedIsEmpty = true;
	
	DWRCategoryAction.searchLabFileByKeywordAndGroup(pageNumber,keyword,groupName,otherFieldName,otherFieldValue,otherFieldCompator,language,function(data){
		searchResults.innerHTML = "";	
		if(data!=null&&data.total>0){
			//method from pagination.js
			//alert(data.total);
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
				
				//console.log("notifDuration: " + notifDuration);
				
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
			    
				a.setAttribute("name","linkResult");
				a.setAttribute("id","linkResult"+index);
				a.href = "javascript:getLabFileItemDetails('"+result.id+"','linkResult"+index+"')";
				if(index == 0){			
					getLabFileItemDetails(result.id,"linkResult"+index);
				}
			    
				

				li.appendChild(img1);
				li.appendChild(img2);
				li.appendChild(a);
				searchResults.appendChild(li);
			}
			
		}else{
			var mainContent = document.getElementById("mainContent");
			mainContent.innerHTML = "";
			createPagination(0,0,"");
			searchResults.innerHTML = "No item found";			
		}			
	});	
	
}


function getLabFileItemDetails(id,linkId){
	//var language = document.getElementById("language").value;
	var language = 'default';

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
		
	
		mainContent.appendChild(table1);

		var test = "";
		var purpose = "";
		var testCode = "";
		var runningDay = "";
		var cutOffTime = "";
		var timeOfRelease = "";
		var specimen = "";
		var specimenContainer = "";
		var volumeRequirement = "";
		var patientPreparation = "";
		var specialInstruction = "";
		var limitationsandInterferences = "";
		var methodology = "";
		var measurandDected = "";
		var currentReferenceRange = "";
		var conversionFactor = "";
		var detectionLimit = "";
		var referenceRangeBasedOnInsert = "";
		var sourceOfReferenceRange = "";
		
		//urine and other body fluid
		var machineCode = "";
		var machines = "";
		var preservative ="";
		var specimenDilution = "";
		var control = "";
		var calibrator = "";
		
		
		
		var alertValue = "";
		var sourceOfAlertValue = "";
		
		//urine and other body fluid
		var causeOfRejections = "";
		var limitationsAndInterferences = ""
		
		var reagentStability = "";
		var specimentStability15to25c = "";
		var specimentStability2to8c = "";
		var specimentStabilityNeg15toNeg25c = "";
		var effectivityDate = "";
		var remarks = "";
		
		var groupKey = "LAB FILE- "
		var testKey = "TEST";
		var purposeKey = "PURPOSE";
		var testCodeKey = "TEST CODE";
		var runningDayKey = "RUNNING DAY";
		var cutOffTimeKey = "CUT OFF TIME";
		var timeOfReleaseKey = "TIME OF RELEASE";
		var specimenKey = "SPECIMEN";
		var specimenContainerKey = "SPECIMEN CONTAINER";
		var volumeRequirementKey = "VOLUME REQUIREMENT";
		var patientPreparationKey = "PATIENT PREPARATION";
		var specialInstructionKey = "SPECIAL INSTRUCTION";
		var limitationsandInterferencesKey = "LIMITATIONS AND INTERFERENCES";
		var methodologyKey = "METHODOLOGY";
		var measurandDectedKey = "MEASURAND DETECTED";
		var currentReferenceRangeKey = "CURRENT REFERENCE RANGE";
		var conversionFactorKey = "CONVERSION FACTOR";
		var detectionLimitKey = "DETECTION LIMIT";
		var referenceRangeBasedOnInsertKey = "REFERENCE RANGE BASED ON INSERT";
		var sourceOfReferenceRangeKey = "SOURCE OF REFERENCE RANGE";
		
		//urine and other body fluid
		var machineCodeKey = "MACHINE CODE";
		var machinesKey = "MACHINES";
		var preservativeKey = "PRESERVATIVE";
		var specimenDilutionKey = "SPECIMEN DILUTION";
		var controlKey = "CONTROL";
		var calibratorKey = "CALIBRATOR";
		
		var alertValueKey = "ALERT VALUE";
		var sourceOfAlertValueKey = "SOURCE OF ALERT VALUE";
		
		var causeOfRejectionsKey = "CAUSE OF REJECTIONS";
		var limitationsAndInterferencesKey = "LIMITATIONS AND INTERFERENCES"
		
		var reagentStabilityKey = "REAGENT STABILITY";
		var specimentStability15to25cKey = "SPECIMEN STABILITY 15 to 25°C";
		var specimentStability2to8cKey = "SPECIMEN STABILITY 2 to 8°C";
		var specimentStabilityNeg15toNeg25cKey = "SPECIMEN STABILITY -15 to -25°C";
		var effectivityDateKey = "EFFECTIVITY  DATE"; 
		var remarksKey = "REMARKS";
		

		if(data.categoryItemOtherFields.length>0){
			var table2 = document.createElement("table");
			table2.setAttribute("class","table2");

			for(var index = 0;index < data.categoryItemOtherFields.length; index++){
								
				var categoryItemOtherField = data.categoryItemOtherFields[index];
				var fieldName = categoryItemOtherField.otherField.name;
				var content = categoryItemOtherField.content;
				
				//alert(data.categoryItemOtherFields.id + ">>" + fieldName + " - " + content);
				
				if(fieldName == groupKey + testKey){
					test = content;
				}
				else if(fieldName == groupKey + purposeKey ){
					purpose = content;
				}
				else if(fieldName == groupKey + testCodeKey ){
					testCode = content;
				}
				else if(fieldName == groupKey + runningDayKey ){
					runningDay = content;
					//alert(runningDay);
				}
				else if(fieldName == groupKey + cutOffTimeKey ){
					cutOffTime = content;
					//alert(cutOffTime);
				}
				else if(fieldName == groupKey + timeOfReleaseKey){
					timeOfRelease = content;
				}
				else if(fieldName == groupKey + specimenKey ){
					specimen = content;
				}
				else if(fieldName == groupKey + specimenContainerKey ){
					specimenContainer = content;
				}
				else if(fieldName == groupKey + volumeRequirementKey ){
					volumeRequirement = content;
				}
				else if(fieldName == groupKey + patientPreparationKey ){
					patientPreparation = content;
				}
				else if(fieldName == groupKey + specialInstructionKey ){
					specialInstruction = content;
				}
				else if(fieldName == groupKey + limitationsandInterferencesKey  ){
					limitationsandInterferences = content;
				}
				else if(fieldName == groupKey + methodologyKey  ){
					methodology = content;
					//alert(methodology);
				}
				else if(fieldName == groupKey + measurandDectedKey  ){
					measurandDected = content;
				}
				else if(fieldName == groupKey + currentReferenceRangeKey  ){
					currentReferenceRange = content;
				}
				else if(fieldName == groupKey + conversionFactorKey  ){
					conversionFactor = content;
				}
				else if(fieldName == groupKey + detectionLimitKey  ){
					detectionLimit = content;
				}//urine and other body fluid
				else if(fieldName == groupKey + machineCodeKey  ){
					machineCode = content;
				}
				else if(fieldName == groupKey + machinesKey  ){
					machines = content;
				}
				else if(fieldName == groupKey + preservativeKey  ){
					preservative = content;
				}
				else if(fieldName == groupKey + specimenDilutionKey  ){
					specimenDilution = content;
				}
				else if(fieldName == groupKey + specimenDilutionKey  ){
					specimenDilution = content;
				}
				else if(fieldName == groupKey + controlKey  ){
					control = content;
				}
				else if(fieldName == groupKey + calibratorKey  ){
					calibrator = content;
				}
				else if(fieldName == groupKey + alertValueKey  ){
					alertValue = content;
				}
				else if(fieldName == groupKey + causeOfRejectionsKey  ){ //urine and other body fluid
					causeOfRejections = content;
				}
				else if(fieldName == groupKey + limitationsAndInterferencesKey  ){ //urine and other body fluid
					limitationsAndInterferences = content;
				}
				else if(fieldName == groupKey + referenceRangeBasedOnInsertKey   ){ 
					referenceRangeBasedOnInsert = content;
				}
				else if(fieldName == groupKey + sourceOfReferenceRangeKey   ){
					sourceOfReferenceRange = content;
				}
				else if(fieldName == groupKey + alertValueKey   ){
					alertValue  = content;
				}	
				else if(fieldName == groupKey + sourceOfAlertValueKey    ){
					sourceOfAlertValue  = content;
				}	
				else if(fieldName == groupKey + reagentStabilityKey    ){
					reagentStability  = content;
				}	
				else if(fieldName == groupKey + specimentStability15to25cKey    ){
					specimentStability15to25c  = content;
				}	
				else if(fieldName == groupKey + specimentStability2to8cKey    ){
					specimentStability2to8c  = content;
				}	
				else if(fieldName == groupKey + specimentStabilityNeg15toNeg25cKey    ){
					specimentStabilityNeg15toNeg25c  = content;
				}	
				else if(fieldName == groupKey + effectivityDateKey    ){
					effectivityDate  = content;
				}
				else if(fieldName == groupKey + remarksKey     ){
					remarks   = content;
				}	
				console.log('id = ' + data.id);
			}
			
			table2.appendChild(createLabFileTableRow("CATEGORY", data.parent.name ));
			table2.appendChild(createLabFileTableRow("TEST", data.name ));

			table2.appendChild(createLabFileTableRow(purposeKey, purpose ));
			table2.appendChild(createLabFileTableRow(testCodeKey , testCode ));
			table2.appendChild(createLabFileTableRow(runningDayKey , runningDay ));
			table2.appendChild(createLabFileTableRow(cutOffTimeKey , cutOffTime ));
			table2.appendChild(createLabFileTableRow(timeOfReleaseKey , timeOfRelease ));
			table2.appendChild(createLabFileTableRow(specimenKey, specimen ));
			
			if(data.parent.name != 'Urine and other Body Fluids'){
				table2.appendChild(createLabFileTableRow(specimenContainerKey , specimenContainer));
				table2.appendChild(createLabFileTableRow(volumeRequirementKey , volumeRequirement));
			}
			
			table2.appendChild(createLabFileTableRow(patientPreparationKey , patientPreparation ));
			table2.appendChild(createLabFileTableRow(specialInstructionKey , specialInstruction ));
			table2.appendChild(createLabFileTableRow(limitationsandInterferencesKey , limitationsandInterferences ));

			table2.appendChild(createLabFileTableRow(methodologyKey , methodology ));
			table2.appendChild(createLabFileTableRow(measurandDectedKey , measurandDected ));
			table2.appendChild(createLabFileTableRow(currentReferenceRangeKey , currentReferenceRange ));
			table2.appendChild(createLabFileTableRow(conversionFactorKey , conversionFactor ));
			table2.appendChild(createLabFileTableRow(detectionLimitKey , detectionLimit));
			
			//urine and other body fluids
			if(data.parent.name == 'Urine and other Body Fluids'){
				table2.appendChild(createLabFileTableRow(machineCodeKey , machineCode ));
				table2.appendChild(createLabFileTableRow(machinesKey , machines ));
				table2.appendChild(createLabFileTableRow(preservativeKey , preservativeKey ));
				table2.appendChild(createLabFileTableRow(specimenDilutionKey , specimenDilutionKey ));
				table2.appendChild(createLabFileTableRow(controlKey , controlKey ));
				table2.appendChild(createLabFileTableRow(calibratorKey , calibratorKey ));
			}
			
			table2.appendChild(createLabFileTableRow(referenceRangeBasedOnInsertKey , referenceRangeBasedOnInsert ));
			table2.appendChild(createLabFileTableRow(sourceOfReferenceRangeKey  , sourceOfReferenceRange ));
			table2.appendChild(createLabFileTableRow(alertValueKey  , alertValue ));
			table2.appendChild(createLabFileTableRow(sourceOfAlertValueKey  , sourceOfAlertValue ));
			
			//urine and other body fluids
			if(data.parent.name == 'Urine and other Body Fluids'){
				table2.appendChild(createLabFileTableRow(causeOfRejectionsKey , causeOfRejections ));
			}
			table2.appendChild(createLabFileTableRow(reagentStabilityKey   , reagentStability ));
			table2.appendChild(createLabFileTableRow("HPD SAMPLE STORAGE REQUIREMENT"   , "" ));
			table2.appendChild(createLabFileTableRow(specimentStability15to25cKey  , specimentStability15to25c ));
			table2.appendChild(createLabFileTableRow(specimentStability2to8cKey   , specimentStability2to8c ));
			table2.appendChild(createLabFileTableRow(specimentStabilityNeg15toNeg25cKey   , specimentStabilityNeg15toNeg25c ));
			table2.appendChild(createLabFileTableRow(effectivityDateKey   , effectivityDate ));
			table2.appendChild(createLabFileTableRow(remarksKey   , remarks ));
			
			mainContent.appendChild(table2);
			
			history.pushState("", "", "?q="+data.name);
		}		
	});
}

function createLabFileTableRow(title,content){
	var tr = document.createElement("tr");
	var td1 = document.createElement("td");
	var td2 = document.createElement("td");
	var td3 = document.createElement("td");
	//td3.setAttribute("colspan","3");
	
	
	
	var titleText = title;
	var separator = document.createTextNode( title.toUpperCase() == 'HPD SAMPLE STORAGE REQUIREMENT' ? '' :  ':');
	//var contentText = document.createTextNode(content);
	
	
	var strong = document.createElement("strong");
	strong.innerHTML = titleText;
	
	//var p = document.createElement("p");
	var span;

	if(title.toUpperCase() == "HPD SAMPLE STORAGE REQUIREMENT"){
		span = document.createElement('span');
		span.innerHTML = "<br>(7 days only @ 2 to 8°C)" 					 
		span.setAttribute('style','text-transform: none;font-size:0.9em')
		
	}
	
	td1.appendChild(strong);
	if(title.toUpperCase() == "HPD SAMPLE STORAGE REQUIREMENT") td1.appendChild(span);

	
	td1.setAttribute("style","width: 100px; color: #550202;text-transform: uppercase;");
	td1.setAttribute("valign","top");
		

	td2.appendChild(separator);
	td2.setAttribute("style","width: 20px;vertical-align:top");
	td2.setAttribute("valign","top");
	
	
	td3.innerHTML = content.trim(); 
	td3.setAttribute("style","width: 150px;");
	td3.setAttribute("class","selectable");
	
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	return tr;
}




</script>