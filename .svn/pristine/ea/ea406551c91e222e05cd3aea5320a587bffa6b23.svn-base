var element = null;
var mode = 0;

function moveByArrowKey(event, previous, next, first, last, key, c) {	
	var unicode = getUnicode(event);		
	if(unicode == '40') { //arrow down		
		if(next != last) {			
			if(event.preventDefault) {				
				event.preventDefault();
				/*if(c > 4 && c % 5 == 0) {
					document.getElementById(key + c).scrollIntoView();
				}*/
			} else {
				event.returnValue = false;
			}
		}
		if(next != '') {
			document.getElementById(next).focus();			
		}
	} else if(unicode == '38') { //arrow up
		if(previous != first) {
			if(event.preventDefault) {
				event.preventDefault();				
			} else {
				event.returnValue = false;
			}
		}
		
		if(previous != '') {
			document.getElementById(previous).focus();
		}
	} else {
		return true;
	}
}

function switchRowColor(c, mode) {
	if(mode == 'selected') {
		document.getElementById('firstcol' + c).style.backgroundColor = '#EEEE00';
		document.getElementById('secondcol' + c).style.backgroundColor = '#EEEE00';
		document.getElementById('thirdcol' + c).style.backgroundColor = '#EEEE00';
		if(document.getElementById('fourthcol' + c) != null) {
			document.getElementById('fourthcol' + c).style.backgroundColor = '#EEEE00';	
		}
	} else {
		if(c % 2 == 0) { //even			
			document.getElementById('firstcol' + c).style.backgroundColor = '#FFF';
			document.getElementById('secondcol' + c).style.backgroundColor = '#FFF';
			document.getElementById('thirdcol' + c).style.backgroundColor = '#FFF';
			if(document.getElementById('fourthcol' + c) != null) {
				document.getElementById('fourthcol' + c).style.backgroundColor = '#FFF';	
			}
		} else { //odd			
			document.getElementById('firstcol' + c).style.backgroundColor = '';
			document.getElementById('secondcol' + c).style.backgroundColor = '';
			document.getElementById('thirdcol' + c).style.backgroundColor = '';
			if(document.getElementById('fourthcol' + c) != null) {
				document.getElementById('fourthcol' + c).style.backgroundColor = '';	
			}
		}
	}
}

function getUnicode(event) {
	return event.keyCode? event.keyCode : event.charCode;   
}

function isIE()
{
	var browser = navigator.appName;
	
	if(browser.search(/microsoft/i) == 0) //ie
	{
		return true;
	}
	else
	{
		return false;
	}
}

function modalPopupsItem(event, elem) {
	element = elem; 

	var unicode = getUnicode(event); 
	
	if(unicode == '9') { //tab
		return true;
	}
	
	if(String.fromCharCode(unicode).match("^[ A-Za-z0-9]$")) { //120 = F9	
		ModalPopups.Custom("modalPopupsItem",   
			"Search Panel",   
			"<div style='padding:25px 5px 25px 25px; overflow-y:auto; position: absolute; right: 20px; overflow-x:hidden; height:200px; width:700px'>" + 
			"Find <input type='text' size='30' id='itemKey' onkeydown='return moveByArrowKey(event, \"\", \"item0\", \"item0\", \"\", \"itm\", " + 1 + ")' onkeyup='modalPopupsItemFind2(event)'/>" +
			"&nbsp;<input type='button' value='Find' tabIndex='1000' onclick='modalPopupsItemFind3()'/><br/>" +    
			"<span id='itemSpan'>" +
			  getItemTableHead() +    	        						                
			  getItemNoContent() + "</table></span>" +					       
			"</div>",    
			{   
				width: 750,  
			    height: 320,
			    buttons: "ok",
			    okButtonText: "Close"					    			      					      					      
			}   
		);   	               
		ModalPopups.GetCustomControl("itemKey").focus();		   					       
	} else {
		return false;
	}
}

function modalPopupsClickCategoryItem() {
	ModalPopups.Custom("modalPopupsItem",   
		"Search Panel",   
		"<div style='padding:25px 5px 25px 25px; overflow-y:auto; position: absolute; right: 20px; overflow-x:hidden; height:200px; width:700px'>" + 
		"Find <input type='text' size='30' id='itemKey' onkeydown='return moveByArrowKey(event, \"\", \"item0\", \"item0\", \"\", \"itm\", " + 1 + ")' onkeyup='modalPopupsItemFind5(event)'/>" +
		"&nbsp;<input type='button' value='Find' tabIndex='1000' onclick='modalPopupsItemFind4()'/><br/>" +    
		"<span id='itemSpan'>" +
		  getItemTableHead() +    	        						                
		  getItemNoContent() + "</table></span>" +					       
		"</div>",    
		{   
			width: 750,  
		    height: 320,
		    buttons: "ok",
		    okButtonText: "Close"					    			      					      					      
		}   
	);   	               
	ModalPopups.GetCustomControl("itemKey").focus();
}

function modalPopupsClickCategoryItemPromoBasket() {
	ModalPopups.Custom("modalPopupsItem",   
		"Search Panel",   
		"<div style='padding:25px 5px 25px 25px; overflow-y:auto; position: absolute; right: 20px; overflow-x:hidden; height:200px; width:700px'>" + 
		"Find <input type='text' size='30' id='itemKey' onkeydown='return moveByArrowKey(event, \"\", \"item0\", \"item0\", \"\", \"itm\", " + 1 + ")' onkeyup='modalPopupsItemFind5PromoBasket(event)'/>" +
		"&nbsp;<input type='button' value='Find' tabIndex='1000' onclick='modalPopupsItemFind4PromoBasket()'/><br/>" +    
		"<span id='itemSpan'>" +
		  getItemTableHead() +    	        						                
		  getItemNoContent() + "</table></span>" +					       
		"</div>",    
		{   
			width: 750,  
		    height: 320,
		    buttons: "ok",
		    okButtonText: "Close"					    			      					      					      
		}   
	);   	               
	ModalPopups.GetCustomControl("itemKey").focus();
}

function modalPopupsClickCategoryItemVideos() {
	ModalPopups.Custom("modalPopupsItem",   
		"Search Panel",   
		"<div style='padding:25px 5px 25px 25px; overflow-y:auto; position: absolute; right: 20px; overflow-x:hidden; height:200px; width:700px'>" + 
		"Find <input type='text' size='30' id='itemKey' onkeydown='return moveByArrowKey(event, \"\", \"item0\", \"item0\", \"\", \"itm\", " + 1 + ")' onkeyup='modalPopupsItemFind5Videos(event)'/>" +
		"&nbsp;<input type='button' value='Find' tabIndex='1000' onclick='modalPopupsItemFind4Videos()'/><br/>" +    
		"<span id='itemSpan'>" +
		  getItemTableHead() +    	        						                
		  getItemNoContent() + "</table></span>" +					       
		"</div>",    
		{   
			width: 750,  
		    height: 320,
		    buttons: "ok",
		    okButtonText: "Close"					    			      					      					      
		}   
	);   	               
	ModalPopups.GetCustomControl("itemKey").focus();
}

function modalPopupsClickCategoryItemTrivias() {
	ModalPopups.Custom("modalPopupsItem",   
		"Search Panel",   
		"<div style='padding:25px 5px 25px 25px; overflow-y:auto; position: absolute; right: 20px; overflow-x:hidden; height:200px; width:700px'>" + 
		"Find <input type='text' size='30' id='itemKey' onkeydown='return moveByArrowKey(event, \"\", \"item0\", \"item0\", \"\", \"itm\", " + 1 + ")' onkeyup='modalPopupsItemFind5Trivias(event)'/>" +
		"&nbsp;<input type='button' value='Find' tabIndex='1000' onclick='modalPopupsItemFind4Trivias()'/><br/>" +    
		"<span id='itemSpan'>" +
		  getItemTableHead() +    	        						                
		  getItemNoContent() + "</table></span>" +					       
		"</div>",    
		{   
			width: 750,  
		    height: 320,
		    buttons: "ok",
		    okButtonText: "Close"					    			      					      					      
		}   
	);   	               
	ModalPopups.GetCustomControl("itemKey").focus();
}

function modalPopupsClickCategoryItemCocktails() {
	ModalPopups.Custom("modalPopupsItem",   
		"Search Panel",   
		"<div style='padding:25px 5px 25px 25px; overflow-y:auto; position: absolute; right: 20px; overflow-x:hidden; height:200px; width:700px'>" + 
		"Find <input type='text' size='30' id='itemKey' onkeydown='return moveByArrowKey(event, \"\", \"item0\", \"item0\", \"\", \"itm\", " + 1 + ")' onkeyup='modalPopupsItemFind5Cocktails(event)'/>" +
		"&nbsp;<input type='button' value='Find' tabIndex='1000' onclick='modalPopupsItemFind4Cocktails()'/><br/>" +    
		"<span id='itemSpan'>" +
		  getItemTableHead() +    	        						                
		  getItemNoContent() + "</table></span>" +					       
		"</div>",    
		{   
			width: 750,  
		    height: 320,
		    buttons: "ok",
		    okButtonText: "Close"					    			      					      					      
		}   
	);   	               
	ModalPopups.GetCustomControl("itemKey").focus();
}

function modalPopupsItem2(event, elem, mod) {
	element = elem; 
	mode = mod;

	var unicode = getUnicode(event); 
	
	if(unicode == '9') { //tab
		return true;
	}
	
	if(String.fromCharCode(unicode).match("^[ A-Za-z0-9]$")) { //120 = F9	
		ModalPopups.Custom("modalPopupsItem",   
			"Search Panel",   
			"<div style='padding:25px 5px 25px 25px; overflow-y:auto; position: absolute; right: 20px; overflow-x:hidden; height:200px; width:700px'>" + 
			"Find <input type='text' size='30' id='itemKey' onkeydown='return moveByArrowKey(event, \"\", \"item0\", \"item0\", \"\", \"itm\", " + 1 + ")' onkeyup='modalPopupsItemFind5(event)'/>" +
			"&nbsp;<input type='button' value='Find' tabIndex='1000' onclick='modalPopupsItemFind4()'/><br/>" +    
			"<span id='itemSpan'>" +
			  getItemTableHead() +    	        						                
			  getItemNoContent() + "</table></span>" +					       
			"</div>",    
			{   
				width: 750,  
			    height: 320,
			    buttons: "ok",
			    okButtonText: "Close"					    			      					      					      
			}   
		);   	               
		ModalPopups.GetCustomControl("itemKey").focus();		   					       
	} else {
		return false;
	}
}

function filterItem(event, unicode) {
	var filter = isIE() ? event.srcElement.value : event.target.value;	
	if(filter.length >= 2) {
		modalPopupsItemFind(filter);
	} else {
		$('itemSpan').innerHTML = getItemTableHead() + getItemNoContent() + "</table>";
	}
}

function getItemContent(item, c, lastIndex) {
	var description = '', content = '', previous = '', next = '', last = '';
	var escapeDescription = '';
	var label = '';		
	
	next = 'item' + (c + 1);
	last = 'item' + lastIndex;	
	
	if(c != 0) {
		previous = 'item' + (c - 1);
	}
	if(c % 2 == 0) {
		content += "<tr class='even' id='itm" + c + "' ";
	} else {
		content += "<tr id='itm" + c + "' ";
	}	

	content += " onclick='modalPopupsItemReturn(\"" + item.id +"\",\"" + item.name + "\")' ";

	content += "style='cursor:pointer;' >";
	
	content += "<td id='secondcol" + c + "' class='left'>&nbsp;" + item.name + "</td>";
	content += "<td id='thirdcol" + c + "' class='left'><a href='javascript:void(0)' id='item" + c + "' onFocus='switchRowColor("+ c + ", \"selected\")' onBlur='switchRowColor("+ c + ", \"\")' onKeyPress='return modalPopupsSuppressBackspace(event, \"itemKey\")' onKeyDown='moveByArrowKey(event, \"" + previous + "\", \"" + next + "\", \"item0\", \"" + last + "\", \"itm\", " + c + ")'";
	content += ">Select</a></td>";

	content += "</tr>";

	return content;
}

function getItemContentPromoBasket(item, c, lastIndex) {
	var description = '', content = '', previous = '', next = '', last = '';
	var escapeDescription = '';
	var label = '';		
	
	next = 'item' + (c + 1);
	last = 'item' + lastIndex;	
	
	if(c != 0) {
		previous = 'item' + (c - 1);
	}
	if(c % 2 == 0) {
		content += "<tr class='even' id='itm" + c + "' ";
	} else {
		content += "<tr id='itm" + c + "' ";
	}	

	content += " onclick='modalPopupsItemReturnPromoBasket(\"" + item.id +"\",\"" + item.name + "\")' ";

	content += "style='cursor:pointer;' >";
	
	content += "<td id='secondcol" + c + "' class='left'>&nbsp;" + item.name + "</td>";
	content += "<td id='thirdcol" + c + "' class='left'><a href='javascript:void(0)' id='item" + c + "' onFocus='switchRowColor("+ c + ", \"selected\")' onBlur='switchRowColor("+ c + ", \"\")' onKeyPress='return modalPopupsSuppressBackspace(event, \"itemKey\")' onKeyDown='moveByArrowKey(event, \"" + previous + "\", \"" + next + "\", \"item0\", \"" + last + "\", \"itm\", " + c + ")'";
	content += ">Select</a></td>";

	content += "</tr>";

	return content;
}

function getItemContentVideos(item, c, lastIndex) {
	var description = '', content = '', previous = '', next = '', last = '';
	var escapeDescription = '';
	var label = '';		
	
	next = 'item' + (c + 1);
	last = 'item' + lastIndex;	
	
	if(c != 0) {
		previous = 'item' + (c - 1);
	}
	if(c % 2 == 0) {
		content += "<tr class='even' id='itm" + c + "' ";
	} else {
		content += "<tr id='itm" + c + "' ";
	}	

	content += " onclick='modalPopupsItemReturnVideos(\"" + item.id +"\",\"" + item.name + "\")' ";

	content += "style='cursor:pointer;' >";
	
	content += "<td id='secondcol" + c + "' class='left'>&nbsp;" + item.name + "</td>";
	content += "<td id='thirdcol" + c + "' class='left'><a href='javascript:void(0)' id='item" + c + "' onFocus='switchRowColor("+ c + ", \"selected\")' onBlur='switchRowColor("+ c + ", \"\")' onKeyPress='return modalPopupsSuppressBackspace(event, \"itemKey\")' onKeyDown='moveByArrowKey(event, \"" + previous + "\", \"" + next + "\", \"item0\", \"" + last + "\", \"itm\", " + c + ")'";
	content += ">Select</a></td>";

	content += "</tr>";

	return content;
}

function getItemContentTrivias(item, c, lastIndex) {
	
	var description = '', content = '', previous = '', next = '', last = '';
	var escapeDescription = '';
	var label = '';		
	
	next = 'item' + (c + 1);
	last = 'item' + lastIndex;	
	
	
	
	
	if(c != 0) {
		previous = 'item' + (c - 1);
	}
	if(item != null){
		if(c % 2 == 0) {
			content += "<tr class='even' id='itm" + c + "' ";
		} else {
			content += "<tr id='itm" + c + "' ";
		}	
	
		content += " onclick='modalPopupsItemReturnTrivias(\"" + item.id +"\",\"" + item.name + "\")' ";
	
		content += "style='cursor:pointer;' >";
		
		content += "<td id='secondcol" + c + "' class='left'>&nbsp;" + item.name + "</td>";
		content += "<td id='thirdcol" + c + "' class='left'><a href='javascript:void(0)' id='item" + c + "' onFocus='switchRowColor("+ c + ", \"selected\")' onBlur='switchRowColor("+ c + ", \"\")' onKeyPress='return modalPopupsSuppressBackspace(event, \"itemKey\")' onKeyDown='moveByArrowKey(event, \"" + previous + "\", \"" + next + "\", \"item0\", \"" + last + "\", \"itm\", " + c + ")'";
		content += ">Select</a></td>";
	
		content += "</tr>";
	}
	return content;
	
}

function getItemContentCocktails(item, c, lastIndex) {
	var description = '', content = '', previous = '', next = '', last = '';
	var escapeDescription = '';
	var label = '';		
	
	next = 'item' + (c + 1);
	last = 'item' + lastIndex;	
	
	if(c != 0) {
		previous = 'item' + (c - 1);
	}
	if(c % 2 == 0) {
		content += "<tr class='even' id='itm" + c + "' ";
	} else {
		content += "<tr id='itm" + c + "' ";
	}	

	content += " onclick='modalPopupsItemReturnCocktails(\"" + item.id +"\",\"" + item.name + "\")' ";

	content += "style='cursor:pointer;' >";
	
	content += "<td id='secondcol" + c + "' class='left'>&nbsp;" + item.name + "</td>";
	content += "<td id='thirdcol" + c + "' class='left'><a href='javascript:void(0)' id='item" + c + "' onFocus='switchRowColor("+ c + ", \"selected\")' onBlur='switchRowColor("+ c + ", \"\")' onKeyPress='return modalPopupsSuppressBackspace(event, \"itemKey\")' onKeyDown='moveByArrowKey(event, \"" + previous + "\", \"" + next + "\", \"item0\", \"" + last + "\", \"itm\", " + c + ")'";
	content += ">Select</a></td>";

	content += "</tr>";

	return content;
}

function getItemNoContent() {
	return "<tr class='even'><td colspan='3' class='center'>No item to display.</td></tr>";	
}

function removeTagProductItem(pageObject_, strId, otherFieldClass) {
	//$(this).closest('.label-primary').remove();$('.br${fn:trim(catItem_)}').remove();
	var contentStr = "";
	$(pageObject_).closest('.label-primary').remove();
	$('.br'+strId).remove();
	
	$('.relatedProducts_').each(function(){
		contentStr += $(this).attr('data-tag') + ";";
	});
	
	$('.'+otherFieldClass).val(contentStr);
}

function removeTagPromoBasketItem(pageObject_, strId, otherFieldClass) {
	//$(this).closest('.label-primary').remove();$('.br${fn:trim(catItem_)}').remove();
	var contentStr = "";
	$(pageObject_).closest('.label-primary').remove();
	$('.br'+strId).remove();
	
	$('.relatedPromoBasket_').each(function(){
		contentStr += $(this).attr('data-tag') + ";";
	});
	
	$('.'+otherFieldClass).val(contentStr);
}

function removeTagCocktailsItem(pageObject_, strId, otherFieldClass) {
	//$(this).closest('.label-primary').remove();$('.br${fn:trim(catItem_)}').remove();
	var contentStr = "";
	$(pageObject_).closest('.label-primary').remove();
	$('.br'+strId).remove();
	
	$('.relatedCocktails_').each(function(){
		contentStr += $(this).attr('data-tag') + ";";
	});
	
	$('.'+otherFieldClass).val(contentStr);
}

function removeTagVideosItem(pageObject_, strId, otherFieldClass) {
	//$(this).closest('.label-primary').remove();$('.br${fn:trim(catItem_)}').remove();
	var contentStr = "";
	$(pageObject_).closest('.label-primary').remove();
	$('.br'+strId).remove();
	
	$('.relatedVideos_').each(function(){
		contentStr += $(this).attr('data-tag') + ";";
	});
	
	$('.'+otherFieldClass).val(contentStr);
}

function removeTagTriviasItem(pageObject_, strId, otherFieldClass) {
	//$(this).closest('.label-primary').remove();$('.br${fn:trim(catItem_)}').remove();
	var contentStr = "";
	$(pageObject_).closest('.label-primary').remove();
	$('.br'+strId).remove();
	
	$('.relatedTrivias_').each(function(){
		contentStr += $(this).attr('data-tag') + ";";
	});
	
	$('.'+otherFieldClass).val(contentStr);
}

function modalPopupsItemReturn(itemId, itemName) {
	//element.value = code;	 			
	$('.relatedProductsId').val($('.relatedProductsId').val() + itemId +";");
	$('.relatedProductsName').append(''
			+''
			+''
			+''
			+''
			+'<span class="label label-primary relatedProducts_" data-tag="'+ itemId +'">'
			+'	'+ itemName +'' 
			+'	<span class="glyphicon1 glyphicon-remove1">'
			+'		<button class="transparentbutton" type="button" tagrel="' + itemId + '"  onclick="removeTagProductItem(this,\'' + itemId + '\',\'relatedProductsId\');">x</button>'
			+'	</span>'
			+'</span>'
			+'<br/>'
			+''
			+''
			+''
			+'');
	
	ModalPopups.Close("modalPopupsItem");   	
	
	if(mode == 1) {
		var form = jQuery(element).closest("form");
		form.submit();
	}
} 

function modalPopupsItemReturnPromoBasket(itemId, itemName) {
	//element.value = code;	 			
	$('.relatedPromoBasketId').val($('.relatedPromoBasketId').val() + itemId +";");
	$('.relatedPromoBasketName').append(''
			+''
			+''
			+''
			+''
			+'<span class="label label-primary relatedPromoBasket_" data-tag="'+ itemId +'">'
			+'	'+ itemName +'' 
			+'	<span class="glyphicon1 glyphicon-remove1" style="float:right;">'
			+'		<button class="transparentbutton" type="button" tagrel="' + itemId + '"  onclick="removeTagPromoBasketItem(this,\'' + itemId + '\',\'relatedPromoBasketId\');">x</button>'
			+'	</span>'
			+'</span>'
			+'<br/>'
			+''
			+''
			+''
			+'');
	
	ModalPopups.Close("modalPopupsItem");   	
	
	if(mode == 1) {
		var form = jQuery(element).closest("form");
		form.submit();
	}
} 

function modalPopupsItemReturnVideos(itemId, itemName) {
	//element.value = code;	 			
	$('.relatedVideosId').val($('.relatedVideosId').val() + itemId +";");
	$('.relatedVideosName').append(''
			+''
			+''
			+''
			+''
			+'<span class="label label-primary relatedVideos_" data-tag="'+ itemId +'">'
			+'	'+ itemName +'' 
			+'	<span class="glyphicon1 glyphicon-remove1" style="float:right;">'
			+'		<button class="transparentbutton" type="button" tagrel="' + itemId + '"  onclick="removeTagVideosItem(this,\'' + itemId + '\',\'relatedVideosId\');">x</button>'
			+'	</span>'
			+'</span>'
			+'<br/>'
			+''
			+''
			+''
			+'');
	
	ModalPopups.Close("modalPopupsItem");   	
	
	if(mode == 1) {
		var form = jQuery(element).closest("form");
		form.submit();
	}
}

function modalPopupsItemReturnTrivias(itemId, itemName) {
	//element.value = code;	 			
	$('.relatedTriviasId').val($('.relatedTriviasId').val() + itemId +";");
	$('.relatedTriviasName').append(''
			+''
			+''
			+''
			+''
			+'<span class="label label-primary relatedTrivias_" data-tag="'+ itemId +'">'
			+'	'+ itemName +'' 
			+'	<span class="glyphicon1 glyphicon-remove1" style="float:right;">'
			+'		<button class="transparentbutton" type="button" tagrel="' + itemId + '"  onclick="removeTagTriviasItem(this,\'' + itemId + '\',\'relatedTriviasId\');">x</button>'
			+'	</span>'
			+'</span>'
			+'<br/>'
			+''
			+''
			+''
			+'');
	
	ModalPopups.Close("modalPopupsItem");   	
	
	if(mode == 1) {
		var form = jQuery(element).closest("form");
		form.submit();
	}
}

function modalPopupsItemReturnCocktails(itemId, itemName) {
	//element.value = code;	 			
	$('.relatedCocktailsId').val($('.relatedCocktailsId').val() + itemId +";");
	$('.relatedCocktailsName').append(''
			+''
			+''
			+''
			+''
			+'<span class="label label-primary relatedCocktails_" data-tag="'+ itemId +'">'
			+'	'+ itemName +'' 
			+'	<span class="glyphicon1 glyphicon-remove1" style="float:right;">'
			+'		<button class="transparentbutton" type="button" tagrel="' + itemId + '"  onclick="removeTagCocktailsItem(this,\'' + itemId + '\',\'relatedCocktailsId\');">x</button>'
			+'	</span>'
			+'</span>'
			+'<br/>'
			+''
			+''
			+''
			+'');
	
	ModalPopups.Close("modalPopupsItem");   	
	
	if(mode == 1) {
		var form = jQuery(element).closest("form");
		form.submit();
	}
} 

function modalPopupsItemFind(itemKey) {
	POSRunningTestStatusDWRAction.findAllByKeyword(itemKey, {
		callback:function(items) {
			setItemSpan(items); 
		},
		errorHandler:function(errorString, exception) {
			alert(errorString);
		}
	});		
}

function modalPopupsItemFind3() {
	modalPopupsItemFind(jQuery('#itemKey').val());	
}

function modalPopupsItemFindProduct(itemKey) {
	DWRCategoryItemAction.loadGurkkaProductItem(itemKey, {
		callback:function(items) {
			setItemSpan(items); 
		},
		errorHandler:function(errorString, exception) {
			alert(errorString);
		}
	});		
}

function modalPopupsItemFindPromoBasket(itemKey) {
	DWRCategoryItemAction.loadGurkkaPromoBasketItem(itemKey, {
		callback:function(items) {
			setItemSpanPromoBasket(items); 
		},
		errorHandler:function(errorString, exception) {
			alert(errorString);
		}
	});		
}

function modalPopupsItemFindVideos(itemKey) {
	DWRCategoryItemAction.loadGurkkaVideosItem(itemKey, {
		callback:function(items) {
			setItemSpanVideos(items); 
		},
		errorHandler:function(errorString, exception) {
			alert(errorString);
		}
	});		
}

function modalPopupsItemFindTrivias(itemKey) {
	DWRSinglePageAction.loadGurkkaTrivia(itemKey, {
		callback:function(items) {
			setItemSpanTrivias(items); 
		},
		errorHandler:function(errorString, exception) {
			alert(errorString);
		}
	});		
}

function modalPopupsItemFindCocktails(itemKey) {
	DWRCategoryItemAction.loadGurkkaCocktailsItem(itemKey, {
		callback:function(items) {
			setItemSpanCocktails(items); 
		},
		errorHandler:function(errorString, exception) {
			alert(errorString);
		}
	});		
}

function modalPopupsItemFind4() {
	modalPopupsItemFindProduct(jQuery('#itemKey').val());	
}

function modalPopupsItemFind4PromoBasket() {
	modalPopupsItemFindPromoBasket(jQuery('#itemKey').val());	
}

function modalPopupsItemFind4Videos() {
	modalPopupsItemFindVideos(jQuery('#itemKey').val());	
}

function modalPopupsItemFind4Trivias() {
	modalPopupsItemFindTrivias(jQuery('#itemKey').val());	
}

function modalPopupsItemFind4Cocktails() {
	modalPopupsItemFindCocktails(jQuery('#itemKey').val());	
}


function modalPopupsItemFind5(event) {
	var unicode = getUnicode(event);			
	if(unicode == 13) {			
		modalPopupsItemFind4(isIE() ? event.srcElement.value : event.target.value);
	} else {		
		filterItem2(event, unicode);
	}
}

function modalPopupsItemFind5PromoBasket(event) {
	var unicode = getUnicode(event);			
	if(unicode == 13) {			
		modalPopupsItemFind4PromoBasket(isIE() ? event.srcElement.value : event.target.value);
	} else {		
		filterItem2PromoBasket(event, unicode);
	}
}

function modalPopupsItemFind5Videos(event) {
	var unicode = getUnicode(event);			
	if(unicode == 13) {			
		modalPopupsItemFind4Videos(isIE() ? event.srcElement.value : event.target.value);
	} else {		
		filterItem2Videos(event, unicode);
	}
}

function modalPopupsItemFind5Trivias(event) {
	var unicode = getUnicode(event);			
	if(unicode == 13) {			
		modalPopupsItemFind4Trivias(isIE() ? event.srcElement.value : event.target.value);
	} else {		
		filterItem2Trivias(event, unicode);
	}
}

function modalPopupsItemFind5Cocktails(event) {
	var unicode = getUnicode(event);			
	if(unicode == 13) {			
		modalPopupsItemFind4Cocktails(isIE() ? event.srcElement.value : event.target.value);
	} else {		
		filterItem2Cocktails(event, unicode);
	}
}

function filterItem2(event, unicode) {
	var filter = isIE() ? event.srcElement.value : event.target.value;	
	if(filter.length >= 2) {
		modalPopupsItemFind4(filter);
	} else {
		$('#itemSpan').html(getItemTableHead() + getItemNoContent() + "</table>");
	}
}

function filterItem2PromoBasket(event, unicode) {
	var filter = isIE() ? event.srcElement.value : event.target.value;	
	if(filter.length >= 2) {
		modalPopupsItemFind4PromoBasket(filter);
	} else {
		$('#itemSpan').html(getItemTableHead() + getItemNoContent() + "</table>");
	}
}

function filterItem2Videos(event, unicode) {
	var filter = isIE() ? event.srcElement.value : event.target.value;	
	if(filter.length >= 2) {
		modalPopupsItemFind4Videos(filter);
	} else {
		$('#itemSpan').html(getItemTableHead() + getItemNoContent() + "</table>");
	}
}

function filterItem2Trivias(event, unicode) {
	var filter = isIE() ? event.srcElement.value : event.target.value;	
	if(filter.length >= 2) {
		modalPopupsItemFind4Trivias(filter);
	} else {
		$('#itemSpan').html(getItemTableHead() + getItemNoContent() + "</table>");
	}
}

function filterItem2Cocktails(event, unicode) {
	var filter = isIE() ? event.srcElement.value : event.target.value;	
	if(filter.length >= 2) {
		modalPopupsItemFind4Cocktails(filter);
	} else {
		$('#itemSpan').html(getItemTableHead() + getItemNoContent() + "</table>");
	}
}

function setItemSpan(items) {
	var content = getItemTableHead();														 
	//alert(items);
	if(items) {	
		
		for(var c = 0; c < items.length; c++) {	
			
			content += getItemContent(items[c], c, items.length - 1);	
			
		}							                  		                
	}
	
	content += "</table>";
	//alert($('itemSpan').innerHTML = content);
	//alert(content);
	//$('itemSpan').addClass("testonlyhere11111")
	//$('itemSpan').innerHTML = "testonlyyyy";//content;
	$('#itemSpan').html(content);
	//document.getElementById("itemSpan").innerHTML(content);
}

function setItemSpanPromoBasket(items) {
	var content = getItemTableHead();														 
	if(items) {	
		for(var c = 0; c < items.length; c++) {	
			content += getItemContentPromoBasket(items[c], c, items.length - 1);	
		}							                  		                
	}
	content += "</table>";
	$('#itemSpan').html(content);
}

function setItemSpanVideos(items) {
	var content = getItemTableHead();														 
	if(items) {	
		for(var c = 0; c < items.length; c++) {	
			content += getItemContentVideos(items[c], c, items.length - 1);	
		}							                  		                
	}
	content += "</table>";
	$('#itemSpan').html(content);
}

function setItemSpanTrivias(items) {
	var content = getItemTableHead();														 
	if(items) {	
		for(var c = 0; c < items.length; c++) {	
			content += getItemContentTrivias(items[c], c, items.length - 1);	
		}							                  		                
	}
	content += "</table>";
	$('#itemSpan').html(content);
}

function setItemSpanCocktails(items) {
	var content = getItemTableHead();														 
	if(items) {	
		for(var c = 0; c < items.length; c++) {	
			content += getItemContentCocktails(items[c], c, items.length - 1);	
		}							                  		                
	}
	content += "</table>";
	$('#itemSpan').html(content);
}

function modalPopupsItemFind2(event) {
	var unicode = getUnicode(event);			
	if(unicode == 13) {			
		modalPopupsItemFind(isIE() ? event.srcElement.value : event.target.value);
	} else {		
		filterItem(event, unicode);
	}
}

function getItemTableHead() {
	var tableHead = "<table width='100%' border='0' cellspacing='0' cellpadding='5' class='items'>";		
	tableHead += "<th width='50%'>Name</th>";
	tableHead += "<th width='50%'>&nbsp;</th>";
	return tableHead;
}

function escapeText(text) {
	return text.replace(/'/g, "&#39;").replace(/"/g, "\\\"");
}