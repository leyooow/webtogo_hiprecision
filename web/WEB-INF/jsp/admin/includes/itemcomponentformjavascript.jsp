<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRComponentAction.js'></script>
<script type="text/javascript">
function editComponent(element) {
	toggleHide(jQuery(element).parent().parent());
	jQuery(element).hide();
	jQuery(element).next().show();
}

function updateComponent(element) {
	var row = jQuery(element).parent().parent();

	var equation = row.find('[name=equation]').val();
	var componentIdList = row.find('[name=componentIdList]').val();
	var categoryItemComponentId = row.find('[name=categoryItemComponentId]').val();

	DWRComponentAction.update(categoryItemComponentId, componentIdList, equation, {
			callback:function() {
				row.find('[name=equation]').parent().prev().html(equation);
				row.find('[name=componentIdList]').parent().prev().html(row.find('[name=componentIdList] option:selected').text());
				toggleHide(row);
				jQuery(element).hide();
				jQuery(element).prev().show();
			}
	});
}

function toggleHide(row) {
	row.children("td").each(function(index) {
		if(index != 3 && index != 1) {
			jQuery(this).children().each(function() {
				if(jQuery(this).is(':hidden')) {
					jQuery(this).show();
				}else {
					jQuery(this).hide();
				}
			});
		}
	});	
}
</script>