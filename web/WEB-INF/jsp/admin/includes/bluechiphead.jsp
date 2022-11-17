<script type="text/javascript" src="javascripts/jscolor.min.js"></script>
	<script>
		function bcAddColor(){
			var currentValue = jQuery('#other_field_color').val().trim();
			var name = jQuery('#bluechip_color_name').val().trim();
			var hex = jQuery('#bluechip_color_code').val().replace('#', '').trim();
			if(name != '' && hex != ''){
				currentValue += ',' + name + ':' + hex;
				
				var html = '<div class="bluechip-color" id="color' + hex +'" data-colorpair="' + name +':' + hex + '">' +
					'<span style="background-color: #' + hex +'" class="bc-circle">&nbsp;</span>&nbsp;' +
					'<span style="font-weight: bold;">' + name+'&nbsp;(' + hex +')</span>' +
					'<button onclick="removeColor(\'' + hex + '\'); return false;" type="button">x</button>' +
				'</div>';
				
				jQuery('#bluechipcolors').append(html);
				jQuery('#bluechip_color_name').val('');
				jQuery('#bluechip_color_code').val('');
			}
			jQuery('#other_field_color').val(currentValue);										
		}
	
		function removeColor(hex){
			jQuery('#color' + hex).remove();
			var newValue = '';
			jQuery('.bluechip-color').each(function(){
				var colorpair = jQuery(this).data('colorpair');
				if(colorpair){
					newValue += ',' + colorpair;
				}
			});
			jQuery('#other_field_color').val(newValue);		
			
			return false;
		}
	</script>
	</script>
	<style>
		.bc-circle{
			border-radius: 100%;height: 10px;padding: 5px;width: 10px;margin-left: 10px;display: inline-block;
		}
	</style>