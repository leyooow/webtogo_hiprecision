<link type="text/css" href="javascripts/jquery/jquery-ui-1.8.19.custom.css" rel="Stylesheet" />	
<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="javascripts/jquery/jquery-ui-1.8.19.custom.min.js"></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRComponentCategoryAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 
<script language="javascript" src="../javascripts/overlib.js"></script>
<script>
	$.noConflict();
</script>
<script type="text/javascript" language="javascript">
var companyId = ${not empty company.id ? company.id : 0};
function toggleAdd() {
	jQuery('#categoryBox').toggle();
	jQuery('#addLink').toggle();
}

function toggle(element) {
	element.toggle();
}

function addCategory() {
	var container = jQuery('#categoryList');
	var categoryName = jQuery('#categoryName').val();
	DWRComponentCategoryAction.save(companyId, null, categoryName, {
		callback:function(id) {

			var html = '';
			html += '<div style="display:none;">';
			html +=  '<input type="hidden" value="'+id+'"/>';
			html +=  '<input type="text" value="'+categoryName+'"/>';
			html +=  '<a href="javascript:void(0);" onclick="updateCategory(this);">Save</a>';
			html +=  '</div>';

			var row = jQuery('<li></li>');
			row.append('<a href="javascript:void(0);" onclick="jQuery(this).toggleAdd(); jQuery(this).next().toggle();">'+categoryName+'</a>');
			row.append(html);
			
			container.append(row);
	
			jQuery('#categorySelect').append(jQuery('<option></option').text(categoryName).val(id));
			
			jQuery('#categoryName').val('');
			toggleAdd();
		}
	});
	
}

function updateCategory(element) {
	var nameContainer = jQuery(element).prev();
	var idContainer = nameContainer.prev();

	DWRComponentCategoryAction.save(companyId, idContainer.val(), nameContainer.val(), {
		callback:function(id) {
			jQuery('#categorySelect option[value="'+idContainer.val()+'"]').text(nameContainer.val());
		
			jQuery(element).parent().toggle();
			jQuery(element).parent().prev().html(nameContainer.val());
			jQuery(element).parent().prev().toggle();
		}
	});
}
</script>