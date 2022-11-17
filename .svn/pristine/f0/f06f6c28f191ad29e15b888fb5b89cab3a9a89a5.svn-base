<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRVariationAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<style>

.variationEditorTable {
	background-color: #FFCC99;
	padding: 10px;
}

.variationEditorTable td {
	border: 0px; 
}

.variationEditorTableRight {
	background-color: #ffffff;
	padding: 10px;
	border: 1px solid #000000;
}

.variationEditorTableRight th {
	background-color: #000000;
	font-weight: bold;
	border: 1px solid #000000;
}

</style>

<script>

function addVariation(myForm) {
	var name = myForm.name.value;
	var sku = myForm.sku.value;
	var price = myForm.price.value;
	var weight = myForm.weight.value;
	
	if(name.length > 0) {
		DWRVariationAction.addVariation(${item.id}, name, sku, price, weight, function() {
			refreshList();
			clearFields();
		});  
	}
	else {
		alert("Variation name should be supplied");
	}
	
	return false;
}

function refreshList() {
	DWRVariationAction.findAllVariations(${item.id}, function(variations) {
		
		var html = '<table class="variationEditorTableRight">';
		
		if(variations.length > 0) {
			html += '<tr>';
			html += '<th>Variation</th>';
			html += '<th>SKU</th>';
			html += '<th>Price</th>';
			html += '<th>Weight</th>';
			html += '<th>Action</th>';
			html += '<tr>';
			
			for(var i=0; i <variations.length; i++) {
				variation = variations[i];
				
				html += '<tr>';
				html += '<td>'+ variation.name +'</td>';
				html += '<td>'+ variation.sku +'</td>';
				html += '<td>'+ variation.price +'</td>';
				html += '<td>'+ variation.weight +'</td>';
				html += '<td><a href="javascript:;"  onclick="showEditForm('+variation.id+');">edit</a> | <a href="javascript:;" onclick="deleteVariation('+variation.id+');">delete</a></td>';
				html += '<tr>';
				
			}
		}
		else {
			html += '<tr><th>No variation defined</th></tr>';
		} 
		
		html += '</table>';
		
		var variationDiv = document.getElementById('variationListing');
		variationDiv.innerHTML = html;
	});

}

function deleteVariation(id) {
	if(confirm("Do you really want to delete this?")) {
		DWRVariationAction.deleteVariation(id, function() {
			refreshList();
		});
	}
}

function clearFields() {
	var myForm = document.getElementById('variationForm');
	myForm.reset();
} 

 
function showEditForm(id) {
	document.getElementById('mainForm').innerHTML = document.getElementById('editForm').innerHTML;
	
	DWRVariationAction.find(id, function(itemVariation) {
		var myForm = document.getElementById('variationEditForm');
		myForm.id.value = itemVariation.id;
		myForm.name.value = itemVariation.name;
		myForm.sku.value = itemVariation.sku;
		myForm.price.value = itemVariation.price;
		myForm.weight.value = itemVariation.weight;
	});
} 

function showAddForm() {
	document.getElementById('mainForm').innerHTML = document.getElementById('addForm').innerHTML;
}

function updateVariation(myForm) {
	var id = myForm.id.value;
	var name = myForm.name.value;
	var sku = myForm.sku.value;
	var price = myForm.price.value;
	var weight = myForm.weight.value;

	DWRVariationAction.update(id, name, sku, price, weight, function() {
		refreshList();
		showAddForm();
		clearFields();
	});
	
	return false;
}

</script>

<table class="variationEditorTable">
	<tr> 
		<td style="border: 0px;">
		
		
		<table class="variationEditorTable" cellpadding="2">
			<tr>
				<td width="30%" valign="top">
					
					<div id="mainForm">
						
					</div>
					
					<div id="addForm" style="display: none;">
						<form id="variationForm" onsubmit="return addVariation(this);" style="font-size: 12px;">
						
						<h3>Add New Variation</h3>
						
						<b>Variation Name:</b><br>
						<input type="text" name="name" class="textbox3" /><br>
						
						<b>SKU:</b><br>
						<input type="text" name="sku" /><br>
						
						<b>Price:</b><br>
						<input type="text" name="price" /><br>
						
						<b>Weight:</b><br>  
						<input type="text" name="weight" /><br>
						
						<br>
						<input type="submit" value="add variation" />
						
						</form>
					</div>
					
					<div id="editForm" style="display: none;">
						<form id="variationEditForm" onsubmit="return updateVariation(this);" style="font-size: 12px;">
						
						<h3>Edit Variation</h3> 
						
						<b>ID:</b><br>
						<input type="text" name="id" readonly="readonly" disabled="disabled"/><br>
													
						<b>Variation Name:</b><br>
						<input type="text" name="name" class="textbox3" /><br>
						
						<b>SKU:</b><br>
						<input type="text" name="sku" /><br>
						
						<b>Price:</b><br>
						<input type="text" name="price" /><br>
						
						<b>Weight:</b><br>  
						<input type="text" name="weight" /><br>
							
						<input type="submit" value="update" />
						<input type="button" value="cancel" onclick="showAddForm();" />
								
						</form>
					</div>
							 			
				</td>
				<td width="20px">
					
				</td>
				<td valign="top">
				
				<div id="variationListing"></div>
				
				</td>
			</tr>
		</table>
		
		</td> 
	</tr>
</table>

<script>
	showAddForm();
	refreshList();
</script>