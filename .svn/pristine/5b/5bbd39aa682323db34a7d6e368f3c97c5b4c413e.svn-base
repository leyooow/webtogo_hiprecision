<link href="../admin/css/forms.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/upload.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="attributes" />

<script src="../admin/javascripts/prototype.js" type="text/javascript"></script>
<script src="../admin/javascripts/scriptaculous.js" type="text/javascript"></script>
<script language="javascript" src="../javascripts/upload.js"></script>

<script>
	
	function submitForm(myForm) {
		
		var name = getElement('attribute_name');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Please enter a attribute name\n";
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
<table>
	<tr>
		<td style="border:0px;" valign="top" width="35%">
		
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
				
		<input type="hidden" name="group_id" value="${group.id}" />
		<input type="hidden" id="attribute_id" name="attribute_id" value="${attribute.id}" />
		<table width="30%">
					
			<tr>
				<td colspan="3"></td>
			</tr> 
		
			<tr class="headbox">
				<th colspan="3">	
					<c:choose>
						<c:when test="${attribute.id > 0}">
							Edit Attribute
						</c:when>
						<c:otherwise>
							New Attribute 
						</c:otherwise>	
					</c:choose>
				</th>
			</tr>
			
			<tr>
				<td width="1%" nowrap>Attribute Name</td>
				<td width="10px"></td>
				<td><input type="text" id="attribute_name" name="attribute_name" class="textbox4" value="${attribute.name}"/></td>
			</tr>
			<tr>
				<td nowrap>Attribute Description</td>
				<td></td>
				<td>
					<textarea name="attribute_description" style="width: 100%; height: 50px;">${attribute.description}</textarea>
				</td>
			</tr>
			<tr>
				<td nowrap>Data Type</td>
				<td></td>
				<td>
				<input type="radio" name="datatype" value="string" checked/> String<br>
				<input type="radio" name="datatype" value="numeric" <c:if test="${attribute.dataType ne null}"><c:if test="${attribute.dataType == 'numeric'}">checked</c:if></c:if>/> Numeric
				</td>
			</tr>		
			<tr>
				<td colspan="3" style="border: 0px;">
				<c:choose>
					<c:when test="${attribute.id > 0}">
						<input type="submit" name="submit" value="Update" class="upload_button2">
						<input type="reset" value="Reset" class="upload_button2"> 
						<input type="button" value="Cancel" class="upload_button2" onclick="window.location='attributes.do?group_id=${group.id}&category_id=${category.id}';"> 
					</c:when>
					<c:otherwise>
						<input type="submit" name="submit" value="Add" class="upload_button2">
					</c:otherwise>
				</c:choose>
				</td>
			</tr> 
			
		</table>
		
		</form>
		
		</td>
		<td width="5%">
		</td>
		<c:if test="${attribute.id > 0}">
			<td width="30%" bgcolor="#efefef" valign="top">
			<table width="100%" cellpadding="0" cellspacing="2" border="0">
			<tr><td>
		 		<div class="categories">
				<div class="categoriesEdit" id="editPresetValueDiv"><a href="javascript:void(0);" onclick="toggleEditPresetValue();" id="txtTogglePresetValue">Edit</a></div>
				Preset Values
				</div>
				<ul class="categories" id="presetValuesList">
					<c:forEach var="presetValue" items="${presetValues}">
				    <li class="categoriesItem" id="presetValue${presetValue.id}">			      
						   <div class="categoriesEdit" style="display:none"><a href="javascript:void(0);" onclick="editPresetValue('${presetValue.id}', '${presetValue.value}');">Edit</a>|<a href="javascript:void(0);" onclick="deletePresetValue('${presetValue.id}')">Delete</a></div>
						   ${presetValue.value}
					</li>
					</c:forEach>
					<li class="categoriesItem" id="presetValue0">
						  <div id="newPresetValueDiv" style="padding:2px; background-color: #efefef; display:none">
						  add a new value:<br><div class="categoriesEdit" ><a href="javascript:void(0);" onclick="addNewPresetValue();">Save</a></div>
						  <input type="textbox" name="newPresetValue" id="newPresetValue" style="font-size:11px; width:130px;" /> &nbsp;
						  </div>
					</li>
			  </ul>
			</td></tr>
			</table>
			</td>
		</c:if>
		<td width="30%">
		</td>
	</tr>
</table>
</div>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRPresetValueAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/util.js'></script>

<script language="javascript" type="text/javascript"><!--

var isEdit = false;
function toggleEditPresetValue() {
	var objs = document.getElementById("presetValuesList").getElementsByTagName('div');
	var objCount = objs.length;
	
	if(!isEdit) {
		document.getElementById("txtTogglePresetValue").innerHTML = "Done";
		for(var i=0; i<objCount; i++) {
				objs[i].style.display = "";
		}
		document.getElementById('newPresetValueDiv').style.display = "";
		isEdit = true;
	} else {
		document.getElementById('txtTogglePresetValue').innerHTML = 'Edit';
		for(var i=0; i<objCount; i++) {
			objs[i].style.display = "none";
		}
		
		document.getElementById('newPresetValueDiv').value = "";
		document.getElementById('newPresetValueDiv').style.display = "none";
		isEdit = false;
	}
}

function editPresetValue(id, value) {
	var divId =	'presetValue'+id;	
	var msg  = '';
		msg += '<div class="categoriesEdit">';
		msg += '<a href="javascript:void(0);" onclick="savePresetValue(\''+id+'\', \''+value+'\');">Save</a>|<a href="#" onclick="javascript:cancelEditPresetValue(\''+id+'\', \''+value+'\');">Cancel</a></div> &nbsp;';		
		msg += '<input style="font-size:11px; width:130px;" type="text" id="txtPresetValue' +id + '" value="'+value+'" />';
		document.getElementById('presetValue'+id).innerHTML = msg;
}

function cancelEditPresetValue(id, value) {
	var msg = '';
		msg += '<div class="categoriesEdit">';
		msg += '<a href="javascript:void(0);" onclick="editPresetValue(\''+id+'\', \''+value+'\');">Edit</a>|';
		msg += '<a href="javascript:void(0);" onclick="deletePresetValue(\''+id+'\')">Delete</a></div>';
		msg += value;
		document.getElementById('presetValue'+id).innerHTML = msg;		
}

function addNewPresetValue()
{
	
	var value = $('newPresetValue').value;
	var att_id = $('attribute_id').value;
	
	if(value == "") {
		alert("Please enter a value");
		return;
	}
	
	DWRPresetValueAction.addPresetValue(value, att_id, 
		{callback:function(reply) {
		var msg = '';
		
			if(reply)
			{
				msg += '<li class="categoriesItem" id="presetValue'+reply.id+'">';
				msg += '<div class="categoriesEdit">';
				msg += '<a href="javascript:void(0);" onclick="editPresetValue(\''+reply.id+'\', \''+reply.value+'\');">Edit</a>|';
				msg += '<a href="javascript:void(0);" onclick="deletePresetValue(\''+reply.id+'\')">Delete</a></div>';
				msg += reply.value;
				msg += '</li>';
				new Insertion.Before('presetValue0', msg);
				document.getElementById("newPresetValue").value = "";
			}				
	}});
}

function savePresetValue(id, oldvalue) {		

	newValue = $('txtPresetValue'+id).value;
		
	if(newValue == "") {
		alert("Please enter a value.");
		return;
	}
	DWRPresetValueAction.updatePresetValue(id, newValue,{
		callback:function(reply){
			if (reply) {
				cancelEditPresetValue(id, $('txtPresetValue'+id).value);												
			}
		}
	});
	return false;	
}

function deletePresetValue(id){
	
	if (confirm("Are you sure you want to delete this value?")){
		DWRPresetValueAction.deletePresetValue(id,{
			callback:function(isOk){
						if(isOk){
							Element.remove("presetValue"+id);
						}
						else{
							alert('error deleting branch name');
						}
					}
			});
	}
	else{
		return false;
	}
}

--></script>