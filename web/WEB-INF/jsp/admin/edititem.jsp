<%@include file="includes/header.jsp"  %>
<link href="css/css.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="items" />

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRCategoryItemAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRSinglePageAction.js'></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 
<script language="javascript" src="../javascripts/overlib.js"></script>

	<!-- JQuery -->
	<link type="text/css" href="javascripts/jquery/jquery-ui-1.8.19.custom.css" rel="Stylesheet" />	
	<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="javascripts/jquery/jquery-ui-1.8.19.custom.min.js"></script>
	
	<%--------------------------- for wendys--------------------------------- --%>
	<script src="included_file/scripts/jquery.js" type="text/javascript"></script>
	<script src="included_file/scripts/jquery-ui.custom.js" type="text/javascript"></script>
	<script src="included_file/scripts/jquery.cookie.js" type="text/javascript"></script>

	<link href="included_file/css/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet">
	<script src="included_file/scripts/jquery.dynatree.js" type="text/javascript"></script>
	<%------------------------------------------------------------------------ --%>
	
	<script type="text/javascript" src="javascripts/ModalPopupsItem2.js"></script>
	<script type="text/javascript" src="javascripts/ModalPopups.js"></script>
	
	
	<c:if test="${company.name eq 'bluechip' and group.name eq 'Products' }">
		<%@include file="includes/bluechiphead.jsp"  %>
	</c:if>
	
	
	
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		//alert('ready function');
		changeParentUrine();
  	});

	function changeTypesAndTags(ofIdNum){
		var of_val = "";
		$('.of_typesandtags'+ofIdNum).each(function(){
			if($(this).prop('checked')){
				of_val += $(this).val() + ";";
			}
		});
		$('.o'+ofIdNum).val(of_val);
	}
	
		function addAnotherSection(parentTableId,hiddenTableId){
			<%--assignedPersonnelTable , assignedPersonnelHiddenTable--%>
			$("#"+parentTableId).append($("#"+hiddenTableId).html());
			checkCheck();
		}

		function removeRow(obj){
				$(obj).parent().parent().parent().remove();
				checkCheck();
				return false;
		}

		function checkCheck(){

			var weekdays = ["monday","tuesday","wednesday","thursday","friday","saturday","sunday"];
			var scheduleFormHiddenFieldsBasefromCheckbox = "";
			
			for(var i=0;i<weekdays.length;i++){
					
					var dayCheckboxes = $("input[name="+weekdays[i]+"]:checkbox").map(function () 
						{
							var value = "";
							if(this.checked){
								value = weekdays[i];
							}
							return  hiddenField(weekdays[i],value);
						}
					);
					dayCheckboxes.splice(dayCheckboxes.length -1 ,1);
					scheduleFormHiddenFieldsBasefromCheckbox += dayCheckboxes.get().join(",");
					
			}
			$("#scheduleFormHiddenFieldsBasefromCheckbox").html("");
			$("#scheduleFormHiddenFieldsBasefromCheckbox").html(scheduleFormHiddenFieldsBasefromCheckbox);
		}

		function hiddenField(name,value){
			
			return  "<input type='hidden' name='day_"+name+"' value='"+value+"'>";
			<%--select  CAST(dos as datetime)  as datesample from jobs  where dos is not null and dos != '' and CAST(dos as datetime) >= '01-01-2001' limit 10--%>
		}

		 
    </script>
 
<body>


<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="../javascripts/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
  
<c:set var="menu" value="items" />

  <!-- main calendar program -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar.js"></script>

  <!-- language for the calendar -->
  <script type="text/javascript" src="../javascripts/jscalendar/lang/calendar-en.js"></script>

  <!-- the following script defines the Calendar.setup helper function, which makes adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="../javascripts/jscalendar/calendar-setup.js"></script>

<script language="javascript" src="../javascripts/upload.js"></script>
<script language="javascript" src="../javascripts/upload2.js"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRImageAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFileAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRItemAttributeAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>


<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(item.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />

<script>

	jQuery(function() {
		jQuery('#QuantityDialog').dialog({
			autoOpen: false,
			modal:true,
			width:250,			
			draggable:false,
			resizable:false,
			show: "blind",
			hide: "blind",
			buttons:
			{
				"Add":function()
				{
					var qty = $('#quantity').val();
					if(qty.length != 0) {
						if(isInteger(qty)) 
							updateAvailableQuantity("ADD");						
						else 
							alert("Invalid Input");
					}
					else
						alert("Quantity is not Given");					
				},
				"Substract":function()
				{
					var qty = jQuery('#quantity').val();
					if(qty.length != 0) {
						if(isInteger(qty)) 
							updateAvailableQuantity("SUBTRACT");						
						else 
							alert("Invalid Input");
					}
					else
						alert("Quantity is not Given");
				}
			}
		});
		
		jQuery('#QuantityLogDialog').dialog({
			autoOpen: false,
			modal:true,
			width:700,			
			draggable:false,
			resizable:true,
			show: "blind",
			hide: "blind"
		});

		jQuery('#ImageDialog').dialog({
			autoOpen: false,
			modal:true,
			width:250,			
			draggable:false,
			resizable:true,
			show: "blind",
			hide: "blind",
			buttons:
			{
				"Save":function()
				{
					var title = jQuery('#title').val();
					var caption = jQuery('#caption').val();
					var description = jQuery('#description').val();
					var imageId = document.getElementById("imageId").value;

					updateImageTitle(imageId, title);
					updateImageCaption(imageId, caption);
					updateImageDescription(imageId, description);

					jQuery('#title'+imageId).val(title);
					jQuery('#caption'+imageId).val(caption);
					jQuery('#description'+imageId).val(description);

					if(title != null && title != '')
						jQuery('#tr_title_'+imageId).css("display","");
					else
						jQuery('#tr_title_'+imageId).css("display","none");

					if(caption != null && caption != '')
						jQuery('#tr_caption_'+imageId).css("display","");
					else
						jQuery('#tr_caption_'+imageId).css("display","none");

					if(description != null && description != '')
						jQuery('#tr_description_'+imageId).css("display","");
					else
						jQuery('#tr_description_'+imageId).css("display","none");

					
					<%--//jQuery('#description'+imageId).val(description); --%>

					jQuery('#title'+imageId+'_text').html(title);
					jQuery('#caption'+imageId+'_text').html(caption);
					jQuery('#description'+imageId+'_text').html(description);
						
					jQuery('#ImageDialog').dialog('close');	

						
				}
				
			}
		});

		jQuery('#FileDialog').dialog({
			autoOpen: false,
			modal:true,
			width:250,			
			draggable:false,
			resizable:true,
			show: "blind",
			hide: "blind",
			buttons:
			{
				"Save":function()
				{
					var title = jQuery('#file_title').val();
					var caption = jQuery('#file_caption').val();
					var description = jQuery('#file_description').val();
					var fileId = document.getElementById("fileId").value;

					updateFileTitle(fileId, title);
					updateFileCaption(fileId, caption);
					updateFileDescription(fileId, description);

					jQuery('#file_title'+fileId).val(title);
					jQuery('#file_caption'+fileId).val(caption);
					jQuery('#file_description'+fileId).val(description);

					if(title != null && title != '')
						jQuery('#tr_file_title_'+fileId).css("display","");
					else
						jQuery('#tr_file_title_'+fileId).css("display","none");

					if(caption != null && caption != '')
						jQuery('#tr_file_caption_'+fileId).css("display","");
					else
						jQuery('#tr_file_caption_'+fileId).css("display","none");

					if(description != null && description != '')
						jQuery('#tr_file_description_'+fileId).css("display","");
					else
						jQuery('#tr_file_description_'+fileId).css("display","none");

					
					<%--//jQuery('#description'+imageId).val(description); --%>

					jQuery('#file_title'+fileId+'_text').html(title);
					jQuery('#file_caption'+fileId+'_text').html(caption);
					jQuery('#file_description'+fileId+'_text').html(description);
						
					jQuery('#FileDialog').dialog('close');	

						
				}
				
			}
		});
		
	});	

	var openQuantityDialog = function()
	{		
		var currQuantity = jQuery('#item_availableQuantity').val();
		jQuery('#currentQuantity').html(currQuantity);
		jQuery('#QuantityDialog').dialog('open');
	}

	var openQuantityLogDialog = function()
	{				
		jQuery('#QuantityLogDialog').dialog('open');
	}

	var openImageDialog = function(id, imageurl, imageName)
	{	
		document.getElementById("imageId").value = id; 		
		var title = jQuery('#title'+id).val();
		var caption = jQuery('#caption'+id).val();
		var description = jQuery('#description'+id).val();
		jQuery('#title').val(title);
		jQuery('#caption').val(caption);
		jQuery('#description').val(description);
		
		jQuery("#dialogimage").attr("src", imageurl);
		jQuery("#imageNameDiv").html(imageName);
		jQuery('#ImageDialog').dialog('open');
		
	}

	var openFileDialog = function(id, fileName)
	{	
		document.getElementById("fileId").value = id; 		
		var title = jQuery('#file_title'+id).val();
		var caption = jQuery('#file_caption'+id).val();
		var description = jQuery('#file_description'+id).val();
		jQuery('#file_title').val(title);
		jQuery('#file_caption').val(caption);
		jQuery('#file_description').val(description);
		
		jQuery("#fileNameDiv").html(fileName);
		jQuery('#FileDialog').dialog('open');
		
	}

	function updateAvailableQuantity(transactionType)
	{
		var item_id  = jQuery('#item_id').val();					
		var quantity = jQuery('#quantity').val();		
		var remarks  = jQuery('#remarks').val();
		
		jQuery.getJSON(
			'updateAvailableQuantity.do',
			{	
				item_id		 	: item_id,
				transactionType : transactionType,
				quantity		: quantity,
				remarks			: remarks
			},
			function(data)
			{
				if(data.updated) {
					jQuery('#item_availableQuantity').val(data.availableQuantity);	
					jQuery('#QuantityDialog').dialog('close');
					jQuery('#quantity').val("");		
					jQuery('#remarks').val("");
					window.location='edititem.do?group_id='+data.groupId+'&item_id='+data.itemId;															
				}
			}
		);	
	}

	function isInteger(str){
		var i;
	    for (i = 0; i < str.length; i++){   
	        // Check that current character is number.
	        var c = str.charAt(i);
	        if (((c < "0") || (c > "9"))) return false;
	        // uncomment the next line of code if you want to detect leading zeros.
	        //if (i==1 && c=="0") return false;
	    }
	    // All characters are numbers.
	    return true;
	}
	
	var isValid = true;
	
	function submitForm(myForm) {
		
		
		var name = getElement('item_name');
		var itemDate = getElement('item_date');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		/* var newDate = $('[name="o907"]').val() + " " + $('[name="o908"]').val() + ", " + $('[name="o909"]').val();
		$('[name="o958"]').attr('value', newDate); */
		
		<c:if test="${company.id eq 448}">
			var newDate = $('[name="o1040"]').val() + " " + $('[name="o1041"]').val() + ", " + $('[name="o1042"]').val();
			$('[name="o1036"]').attr('value', newDate);
			//agian_event_date
			$('#agian_event_date').val(newDate);
		</c:if>
		
		
		
		if($("[name='o756']").prop('checked')){
			$("[name='o756']").attr('value',$("[name='o760']").val());
		}
		
		if($("[name='o757']").prop('checked')){
			$("[name='o757']").attr('value',$("[name='o761']").val());
		}
		
		if($("[name='o758']").prop('checked')){
			$("[name='o758']").attr('value',$("[name='o761']").val());
		}
		
		if($("[name='o759']").prop('checked')){
			$("[name='o759']").attr('value',$("[name='o761']").val());
		}
		
		
		
		
		
		
		
		
		
		
		
		if(name.length == 0) {
			error = true;
			messages += " * Name was not specified. \n";
		}
		
		
		
		<c:if test="${company.name eq 'stockbridge' and group.name eq 'Events'}">
			if(itemDate.length == 0) {
				error = true;
				messages += " * Date was not specified. \n";
			}
		</c:if>
		
		<c:if test="${company.id eq 319}"> <%-- GURKKA --%>
			var gurkkaPriceStr = document.getElementsByName('item.price')[0].value;
			var dealerPriceFreebies = document.getElementsByName('p117').length == 0 ? '' : document.getElementsByName('p117')[0].value;
			var dealerPriceMainStr = document.getElementsByName('p115').length == 0 ? '' : document.getElementsByName('p115')[0].value;
			var dealerPriceStr = dealerPriceFreebies == '' ? dealerPriceMainStr : dealerPriceFreebies;
			
			if(gurkkaPriceStr != '' && dealerPriceStr != '')
			{
				var gurkkaPrice = parseFloat(gurkkaPriceStr);
				var dealerPrice = parseFloat(dealerPriceStr);
				if(gurkkaPrice < dealerPrice)
				{
					error = true;
					messages += " * The Dealer's price should not be greater than Gurkka price. \n";
				}
			}
		</c:if>
	
		<%--/*if(!isValid) {
			error = true;
			messages +=" * Attribute value/s not valid. \n";
		}*/--%>
			
		if(error) {
			alert(messages);
		}
		else {
			<c:if test="${company.name eq 'hiprecision'}">
				checkCheck();
			</c:if>
			
			<c:if test="${company.name eq 'gurkkatest' and group.name eq 'Promo Basket'}">
				validatePromoBasketInputtedItem();
			</c:if>
			
			return true;
		}
		
		
		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		<%--//value = value.replace(/^\s+|\s+$/, '');--%>
		return value;
	}
	
	function updateImageTitle(id, title) {
		DWRImageAction.updateImageTitle("item", id, title,{ 
	        async: false,
	        callback: function(s){
	        }});
	}
	
	function updateImageCaption(id, caption) {
		DWRImageAction.updateImageCaption("item", id, caption,{ 
	        async: false,
	        callback: function(s){
	        }});
	}
	
	function updateImageDescription(id, description) {
		DWRImageAction.updateImageDescription("item", id, description,{ 
	        async: false,
	        callback: function(s){
	        }});
	}

	
	function updateFileTitle(id, title) {
		DWRImageAction.updateFileTitle("item", id, title);
	}
	
	function updateFileCaption(id, caption) {
		DWRImageAction.updateFileCaption("item", id, caption);
	}
	
	function updateFileDescription(id, description) {
		DWRImageAction.updateFileDescription("item", id, description);
	}	

	function validate(value, type, name){
		DWRItemAttributeAction.getMessage(value, type, name, {
			callback:function(notificationMessage){
			
				if(notificationMessage != null){
					alert(notificationMessage);	
					isValid = false;
				}
				else isValid = true;		
			}
		});
		
	}
	
	function validatePromoBasketItem(value, type, name) {
		
	}

	
	var articleNoCount = 0;

	function addArticleNo(tableId,otherFieldName) { 
		articleNoCount++;
		var tableBody = document.getElementById(tableId);
		var articleHTML = $('#articleTd').html();
		var rowId = "article"+articleNoCount;
		articleHTML += "<a href=\"javascript:void(0);\" onclick=\"removeArticleRow('"+rowId+"');\">Remove</a> ";
		var newRow = tableBody.insertRow(-1);
		newRow.id = rowId
		var newCell0 = newRow.insertCell(0);
		var newCell1 = newRow.insertCell(1);
		var newCell2 = newRow.insertCell(2);
		newCell0.setAttribute('width',"1%");
		newCell0.innerHTML = "<label name=\"articleLabel\">Article No. "+articleNoCount+"</label>";
		newCell2.innerHTML = articleHTML;
	}

	function removeArticleRow(id) {
		articleNoCount--;
		$('#'+id).remove();
		var articleLabel = $('[name="articleLabel"]');
		for(var i=0; i<articleLabel.length; i++) {
			var label = articleLabel[i].innerHTML;
			var counter = i+1;
			articleLabel[i].innerHTML = "Article No. "+counter;
		}
	}
	
	function validateImage() {
		if(currentUploads > 0) {
			return true;
		}
		alert("No attached file. Please attached file/s");
		return false;
	}
	
	function validateFile() {
		if(currentUploads2 > 0) {
			return true;
		}
		alert("No attached file. Please attached file/s");
		return false;
	}
	
	
	window.onload = function(){
		
	}
</script> 
   
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/core.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/events.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/css.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/coordinates.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/drag.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/dragsort.js"></script>
<script language="JavaScript" type="text/javascript" src="../javascripts/toolman/org/tool-man/cookies.js"></script>

<script language="JavaScript" type="text/javascript">
	var dragsort = ToolMan.dragsort()
	var junkdrawer = ToolMan.junkdrawer()

	window.onload = function() {

		articleNoCount = $('#articleSize').val();
		
		var element = document.getElementById("phoneticlong");
		if(element) { 
			dragsort.makeListSortable(document.getElementById("phoneticlong"),
						verticalOnly, saveOrder)
		}

		var element = document.getElementById("filesList");
		if(element) { 
			dragsort.makeListSortable(document.getElementById("filesList"),
						verticalOnly, saveOrder)
		}
		
		
		
	}
	<c:if test="${company.id eq 448}">
	$(document).ready(function(){
		$('[name="o1036"]').attr("disabled","disabled");
	});
	</c:if>
	<c:if test="${company.id eq 404}"> <%-- Wendys --%>
		$(document).ready(function(){
				
				var s_val = $('[name="o690"]').val();
				$('[name="o690"]').replaceWith('<textarea  id = "other_field" name="o690" class = "selectedbranches" style = "width:500px;" rows = "5"></textarea>')
				$('[name="o690"]').attr("value",s_val);
				$('[name="o690"]').attr("readonly","readonly");

		});
	</c:if>
		
	function verticalOnly(item) {
		item.toolManDragGroup.verticalOnly()
	}
	
	function saveOrder(item) {
		var group = item.toolManDragGroup
		var list = group.element.parentNode
		var id = list.getAttribute("id")
		if (id == null) return
		group.register('dragend', function() {
			ToolMan.cookies().set("list-" + id, 
					junkdrawer.serializeList(list), 365)
		})
	}
	
	function save() {
		var items = ToolMan.junkdrawer().serializeList(document.getElementById('phoneticlong'));
		items = items.split("|");

		DWRImageAction.saveNewOrder(items, back);
	}

	function saveFileOrder() {
		var items = ToolMan.junkdrawer().serializeList(document.getElementById('filesList'));
		items = items.split("|");

		DWRFileAction.saveNewOrder(items, back);
	}
	
	function back() {
		window.location = 'edititem.do?group_id=${group.id}&item_id=${item.id}';
	}

</script>

  <div class="container">
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
<div class="contentWrapper" id="contentWrapper">
	<div class="mainContent">
	<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Page was successfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'updatefailed'}">
								<li><span class="actionMessage">Failed to update page.</span></li>
							</c:if>
						</ul>
					</c:if>
		<c:if test="${fn:length(gurkkaPromoBasketByCurrentItem) gt 0}">
			<div class="pageTitle" style="background-color:red;">
				<h1 style="color:#fff;"><strong>Please be reminded that this item is Under the following Promo Basket(s) :</strong></h1><br/>
				<c:forEach items="${gurkkaPromoBasketByCurrentItem}" var="gurkkaPromoBasket_Item" varStatus="c" >
					<p ><a target="_blank" style="color:#fff;" href="edititem.do?group_id=${gurkkaPromoBasket_Item.parentGroup.id}&item_id=${gurkkaPromoBasket_Item.id}">- &nbsp;${gurkkaPromoBasket_Item.name}</a></p>
					<c:if test="${not c.last}">
						<br/>
					</c:if>
				</c:forEach>
			</div>
		</c:if>
	  <div class="pageTitle">
	  	
	    <h1><strong>Edit Item</strong></h1>


		</ul>
		
					
					
					
		<div class="clear"></div>
	  </div><!--//pageTitle-->
	  <c:if test="${not empty languages}">
		 <h4>Language:
						<c:if test="${item.language!= null}"> 
						${item.language.language}
						</c:if>
						<c:if test="${item.language== null}"> 
						English
						</c:if>
					</h4>
		  <div class="metaBox">
		  	<c:if test="${item.language== null}"> 
			English, 
			</c:if>
			<c:if test="${item.language != null}"> 
			<a href="edititem.do?group_id=${param.group_id}&item_id=${param.item_id}">English</a> 
			</c:if>
		  	<c:forEach items="${languages}" var="lang">
		  		<c:if test="${lang.language ne param.language}">
				, <a href="edititem.do?group_id=${param.group_id}&item_id=${param.item_id}&language=${lang.language}">${lang.language}</a>
				</c:if>
				<c:if test="${lang.language eq param.language}">
				, ${lang.language}
				</c:if>    
			</c:forEach>
			
		  </div>
	  </c:if>
	  <c:set var="pageNumSuffix" value="" />
	  <c:if test="${param.pageNumber ne null and param.pageNumber ne ''}">
	  	<c:set var="pageNumSuffix" value="?pageNumber=${param.pageNumber}" />
	  </c:if>
	<c:set var="formAction" value="updateitem.do${pageNumSuffix}" />
	<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		<input type="hidden" name="language" value="${param.language}"/>	
		<input type="hidden" name="catId" value="${sessionScope.setVariable }" />
		<input type="hidden" name="group_id" value="${group.id}" />
		<input type="hidden" name="pageNumber" value="${pageNumber}" />
		
		
		<input type="hidden" name="agian_event_date" id="agian_event_date" value=""/>
		
		<c:if test="${item.id >0}">
			<input type="hidden" id="item_id" name="item_id" value="${item.id}" />
		</c:if>
		
		<table width="50%" ${company.name eq 'hpds' ? 'id="itemFieldsContainer"' : ''}>		
			<c:if test="${item.language== null}"> 
				<tr>
					<td width="1%" nowrap>
					<c:choose>
					<c:when test="${company.name=='hpdsked'}">
						Specialization
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${company.name eq 'neltex' and group.name eq 'Store Locator'}">
								Province & Region
							</c:when>
							<c:otherwise>
								Parent Category
							</c:otherwise>
						</c:choose>
					</c:otherwise>
					</c:choose>
	
					</td>
					<td width="10px"></td>
					<td>
					
					<select name="parent" style="width: 500px;" 
						<c:if test="${company.name eq 'agian' and group.name eq 'Events and Activities'}"> id="agianParent" onchange="changeParentAgian();"</c:if>
						<c:if test="${company.name eq 'hpds' and group.name eq 'Test Procedures'}"> id="hpdsParent" onchange="changeParentHpds();"</c:if>
						<c:if test="${company.name eq 'hpds' and group.name eq 'Lab File'}"> id="hpdsParent" onchange="changeParentUrine();"</c:if>
					>
						<c:choose>
							<c:when test="${company.name eq 'gurkkatest' and group.name eq GurkkaConstants.COCKTAILS_GROUP_NAME}">
								<c:forEach items="${group.categories}" var="cat">
									<c:if test="${cat.id eq GurkkaConstants.COCKTAILS_CATEGORY_COCKTAILITEM_ID}">
										<option value="${cat.id}" <c:if test="${cat.id == item.parent.id}">selected</c:if>>${cat.name}  ${cat.descriptor}</option>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${categories}" var="cat">										
									<option value="${cat.id}" <c:if test="${cat.id == item.parent.id}">selected</c:if> data-value="${cat.name}">${cat.name}  ${cat.descriptor}</option>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</select>
					
					</td>
				</tr> 
			</c:if>
			<c:if test="${item.language== null}"> 
				<c:if test="${group.hasBrands && fn:length(group.brands)>0}">
					<tr>
						<td width="1%" nowrap>
							<c:choose>
							<c:when test="${company.name=='hpdsked'}">
						Branch
							</c:when>
							<c:otherwise>
						Brand
							</c:otherwise>
						</c:choose>
						
						
						</td>
						<td width="10px"></td>
						<td>
						 
						<select name="brand_id" style="width: 255px;">
							<option value=""></option>
							<c:forEach items="${group.brands}" var="b">
								<option value="${b.id}" <c:if test="${b.id == item.brand.id}">selected</c:if>>${b.name}</option>
							</c:forEach>
						</select>
						
						</td>
					</tr>
				</c:if>
			</c:if>
			
			
			<tr>
				<td width="1%" nowrap><c:choose>
				<c:when test="${company.name=='hpdsked'}">
					${group.name} Name
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${company.name eq 'neltex' and group.name eq 'Store Locator'}">
							Branch Name
						</c:when>
						<c:when test="${company.name eq 'montero' and group.name eq 'Dealers'}">
							Dealer Name
						</c:when>
						
						<c:otherwise>
							Item Name
						</c:otherwise>
					</c:choose>
				</c:otherwise>
				</c:choose></td>
				<td width="10px"></td>
				<td>
					<c:choose>
						<c:when test="${company.name eq 'pocketpons' }">
							<input type="text" id="item_name" name="item.name" value="${item.name}" class="w385" maxlength="30"/>
						</c:when>
						<c:otherwise>
							<input type="text" id="item_name" name="item.name" value="${item.name}" class="w385" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
			<c:if test="${item.language== null}"> 
				
				<c:if test="${company.name ne 'pocketpons' }">
					<c:choose>

						<c:when test="${company.name eq 'hpds' and group.name eq 'Test Procedures'}">
							<tr id="skuRow">
								<td width="1%" nowrap>SKU</td>
								<td width="10px"></td>

							<td> <input type="text" id="item_sku" name="item.sku" value="${item.sku}" class="w385" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr id="skuRow">
								<c:choose>
									<c:when test="${company.name eq 'agian' and group.name eq 'Events and Activities' }">
										<td width="1%" nowrap>Category</td>
									</c:when>
									<c:when test="${company.name eq 'bci' and group.name eq 'Portfolio' or group.name eq 'Clients'}">
										<td width="1%" nowrap>Link</td>
									</c:when>
									<c:otherwise><td width="1%" nowrap>SKU</td></c:otherwise>
								</c:choose>
										
								<td width="10px"></td>
								<c:choose>
									<c:when test="${company.name eq 'agian' and group.name eq 'Events and Activities' }">
										<td><select name="item.sku" id="item_sku" class="w385" <c:if test="${item.parent.id ne '17478' }">disabled</c:if>>
												<option value="" <c:if test="${item.sku eq ''}">selected</c:if>>---Selected Category---</option>
												
												<c:forEach items="${agianKDCategories}" var="catItem">
															<option value="${catItem.name}" <c:if test="${item.sku eq catItem.name}">selected</c:if>>${catItem.name}</option>
															
												</c:forEach>
											</select>
										</td>
									</c:when>
									<c:otherwise>
										<td><input type="text" id="item_sku" name="item.sku"
											value="${item.sku}" class="w385" />
										</td>
									</c:otherwise>
								</c:choose>
								
							</tr>
						</c:otherwise>
					</c:choose>
					
				</c:if>
				

				
				<c:if test="${group.itemHasPrice ne false }">
					<tr>
						<td>&nbsp;</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td width="1%" nowrap>${(not empty group.categoryItemPriceNames)?
						group.categoryItemPriceNames[0].name : 'Price' }</td>
						<td width="10px"></td>
						<td><input type="text" id="item_price" name="item.price"
							value="<fmt:formatNumber value="${empty item.price ? 0 : item.price}" pattern="0.##" />" class="w385" /></td>
					</tr>
					<c:forEach items="${group.categoryItemPriceNames }" var="priceName"
						varStatus="counter">
						<c:if test="${counter.count ne 1 }">
							<tr>
								<td width="1%" nowrap>${priceName.name }</td>
								<td width="10px"></td>
								<td><input type="text" id="item_price" name="p${priceName.id }"
									value="<fmt:formatNumber value="${empty prices[priceName.id] ? 0 : prices[priceName.id]}" pattern="0.##" />" class="w385" /></td>
							</tr>
						</c:if>
					</c:forEach>
					<tr>
						<td>&nbsp;</td>
						<td></td>
						<td></td>
					</tr>
				</c:if>
				<c:if test="${company.companySettings.hasProductInventory eq true}">
					<tr>
						<td width="1%" nowrap>Available Quantity</td>
						<td width="10px"></td>
						<td><input disabled type="text" id="item_availableQuantity"
							value="${item.availableQuantity}" class="textbox2" /> <img
							src="images/iQty.jpg" onclick="openQuantityDialog();"
							style="padding-top: 5px;" /> <img src="images/iLogs.jpg"
							onclick="openQuantityLogDialog();" style="padding-top: 5px;" /></td>
					</tr>
				</c:if>
				
							
				
			</c:if>				
				<c:if test="${company.companySettings.hasOtherFields ne false }">
					<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
						<c:set var="fieldname" value="PATIENT PREPARATION\SPECIAL INSTRUCTION" />
						<c:choose>
						
						<%-- For Hi Precision --%>
						
						
						<c:when test="${company.name eq 'hpds' and
										( fn:containsIgnoreCase(otherField.name, 'SPECIMEN REQUIREMENT') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN STABILITY 15-25°C') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN PACKAGING FOR TRANSPORT') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN STABILITY 2-8°C') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN STABILITY -15-25°C') or
										fn:containsIgnoreCase(otherField.name, 'SPECIMEN REJECTION') or
										fn:containsIgnoreCase(otherField.name, 'STORAGE AND TRANSPORT')
										)}">
								<div style="display: none">
									<textarea type="text" id="other_field${otherField.id}" name="o${otherField.id}" >${otherFields[otherField.id]}</textarea>
								</div>
						</c:when>
						
						<c:when test="${company.name eq 'hpds' and (
							otherField.name eq 'STORAGE AND TRANSPORT' or 
							otherField.name eq 'SPECIMEN STABILITY 15-25°C' or
							otherField.name eq 'SPECIMEN STABILITY 2-8°C' or 
							otherField.name eq 'SPECIMEN STABILITY -15-25°C' or 
							otherField.name eq 'SPECIMEN REJECTION' or 
							otherField.name eq 'SPECIMEN REQUIREMENT' or 
							otherField.name eq 'SPECIMEN CONTAINER' or 
							otherField.name eq 'SPECIMEN PACKAGING FOR TRANSPORT'  or
							otherField.name eq 'PURPOSE'  or
							
							
							otherField.name eq 'SPECIAL INSTRUCTIONS'  or
							otherField.name eq 'ALTERNATIVE SPECIMEN(S) & VOLUME REQUIREMENT'  or
							otherField.name eq 'PREFERRED SPECIMEN(S) & VOLUME REQUIREMENT'  or
							otherField.name eq 'INSTRUCTIONS TO ENCODERS'  or
							otherField.name eq 'SPECIMEN STABILITY'  or
							otherField.name eq 'OTHER NAME'  or
							otherField.name eq 'TEST'  or
							otherField.name eq 'ROOM TEMPERATURE'  or
							otherField.name eq 'REFRIGERATED'  or
							otherField.name eq 'FROZEN'  or
							otherField.name eq 'SECTION'  or
							otherField.name eq 'TRANSPORT TEMPERATURE'  or
							otherField.name eq 'REJECTION CRITERIA'  or
							otherField.name eq 'TIME OF RELEASE'  or
							otherField.name eq 'FAQ'  or
							
							otherField.name eq 'OTHER TEST REQUEST NAME' or
							otherField.name eq 'TEST COMPOSITION' or
							otherField.name eq 'INTENDED USE' or
							otherField.name eq 'METHODOLOGY' or
							otherField.name eq 'LABORATORY SECTION' or
							otherField.name eq 'REFERRING CLINICIANS' or
							otherField.name eq 'INSTRUCTIONS TO HP STAFFS' or
							otherField.name eq 'SPECIAL INSTRUCTIONS /PATIENT PREPARATIONS' or
							otherField.name eq 'SPECIAL INSTRUCTIONS/PATIENT PREPARATIONS' or
							
							otherField.name eq 'PATIENT PREPARATIONS' or
							
							otherField.name eq 'COLLECTION/SAMPLE CONTAINER' or
							otherField.name eq 'SPECIMEN AND VOLUME REQUIREMENT' or
							otherField.name eq 'ALTERNATIVE SPECIMEN AND VOLUME REQUIREMENT' or
							otherField.name eq 'SPECIMEN STABILITY' or
							otherField.name eq 'ROOM TEMPERATURE (15-25°C)' or
							otherField.name eq 'REFRIGERATED TEMPERATURE (2-8°C)' or
							otherField.name eq 'FREEZER TEMPERATURE (-20°C)' or
							otherField.name eq 'TRANSPORT TEMPERATURE' or
							otherField.name eq 'REJECTION CRITERIA' or
							otherField.name eq 'RUNNING DAY' or
							
							otherField.name eq 'CUT OFF TIME' or
							otherField.name eq 'TAT/RELEASING OF RESULTS' or
							otherField.name eq 'REFERENCE INTERVAL/RESULT INTERPRETATION' or
							otherField.name eq 'LIMITATIONS/INTERFERENCES' or
							otherField.name eq 'FREQUENTLY ASKED QUESTIONS (FAQS)' or
							otherField.name eq 'RELATED WORDS' or
							otherField.name eq 'BRANCH'  or
							otherField.name eq 'MACHINE'  or
							otherField.name eq 'SCHEDULE'  or
							
							otherField.name eq 'ANALYTE- ANALYTE' or
							otherField.name eq 'ANALYTE- MACHINES' or
							otherField.name eq 'ANALYTE- SAMPLE' or
							otherField.name eq 'ANALYTE- CRITICAL VALUE' or
							otherField.name eq 'ANALYTE- RECOMMENDATION DILUTION' or
							otherField.name eq 'ANALYTE- DILUENT' or
							otherField.name eq 'ANALYTE- AMR' or
							otherField.name eq 'ANALYTE- CRR' or
							otherField.name eq 'ANALYTE- REPEAT TEST CRITERIA (IF BELOW)' or
							otherField.name eq 'ANALYTE- REPEAT TEST CRITERIA (IF ABOVE)' or
							otherField.name eq 'ANALYTE- ACCEPTABLE DIFFERENCE (%)' or
							
							otherField.name eq 'LAB FILE- TEST' or
							otherField.name eq 'LAB FILE- PURPOSE' or
							otherField.name eq 'LAB FILE- TEST CODE' or
							otherField.name eq 'LAB FILE- RUNNING DAY' or
							otherField.name eq 'LAB FILE- CUT OFF TIME' or
							otherField.name eq 'LAB FILE- SPECIMEN CONTAINER' or
							otherField.name eq 'LAB FILE- METHODOLOGY' or
							otherField.name eq 'LAB FILE- MACHINES' or
							otherField.name eq 'LAB FILE- SOURCE OF ALERT VALUE' or
							otherField.name eq 'LAB FILE- REAGENT STABILITY' or
							otherField.name eq 'LAB FILE- EFFECTIVITY DATE' or
							
							otherField.name eq 'LAB FILE- SPECIMEN' or
							otherField.name eq 'LAB FILE- VOLUME REQUIREMENT' or
							otherField.name eq 'LAB FILE- PATIENT PREPARATION' or
							otherField.name eq 'LAB FILE- SPECIAL INSTRUCTION' or
							otherField.name eq 'LAB FILE- LIMITATIONS AND INTERFERENCES' or
							otherField.name eq 'LAB FILE- MEASURAND DETECTED' or
							otherField.name eq 'LAB FILE- CURRENT REFERENCE RANGE' or
							otherField.name eq 'LAB FILE- CONVERSION FACTOR' or
							otherField.name eq 'LAB FILE- DETECTION LIMIT' or
							
							otherField.name eq 'LAB FILE- REFERENCE RANGE BASED ON INSERT' or
							otherField.name eq 'LAB FILE- SOURCE OF REFERENCE RANGE' or
							otherField.name eq 'LAB FILE- SOURCE OF ALERT VALUE' or
							otherField.name eq 'LAB FILE- REAGENT STABILITY' or
							otherField.name eq 'LAB FILE- SPECIMEN STABILITY 15 to 25°C' or
							otherField.name eq 'LAB FILE- SPECIMEN STABILITY 2 to 8°C' or
							otherField.name eq 'LAB FILE- SPECIMEN STABILITY -15 to -25°C' or
							otherField.name eq 'LAB FILE- EFFECTIVITY DATE' or
							otherField.name eq 'LAB FILE- REMARKS' or
							
							otherField.name eq 'LAB FILE- TIME OF RELEASE' or
							otherField.name eq 'LAB FILE- MACHINE CODE' or
							otherField.name eq 'LAB FILE- PRESERVATIVE' or
							otherField.name eq 'LAB FILE- SPECIMEN DILUTION' or
							otherField.name eq 'LAB FILE- CONTROL' or
							otherField.name eq 'LAB FILE- CALIBRATOR' or
							otherField.name eq 'LAB FILE- ALERT VALUE' or
							otherField.name eq 'LAB FILE- LIMITATIONS AND INTERFERENCE' or
							otherField.name eq 'LAB FILE- CAUSE OF REJECTIONS' or
							
							otherField.name eq   fieldname
							)}">
							<c:choose>
								<c:when test="${item.parent.name eq 'Imaging Test Procedures'}">
									<c:choose>
										<c:when test="${company.name eq 'hpds' and (																
											otherField.name eq 'SPECIAL INSTRUCTIONS'  or
											otherField.name eq 'OTHER NAME'  or
											otherField.name eq 'TEST'  or															
											otherField.name eq 'OTHER TEST REQUEST NAME' or
											otherField.name eq 'INTENDED USE' or
											otherField.name eq 'INSTRUCTIONS TO HP STAFFS' or
											otherField.name eq 'SPECIAL INSTRUCTIONS /PATIENT PREPARATIONS' or
											otherField.name eq 'SPECIAL INSTRUCTIONS/PATIENT PREPARATIONS' or
											
											otherField.name eq 'PATIENT PREPARATIONS' or
											
											otherField.name eq 'TAT/RELEASING OF RESULTS' or
											otherField.name eq 'REFERENCE INTERVAL/RESULT INTERPRETATION' or
											otherField.name eq 'FREQUENTLY ASKED QUESTIONS (FAQS)' or
											otherField.name eq 'BRANCH'  or
											otherField.name eq 'MACHINE' or
											otherField.name eq 'SCHEDULE'																
											)}">	
											<c:set var="currentStyle" value="" />
										</c:when>
										<c:otherwise>
											<c:set var="currentStyle" value="display:none" />
										</c:otherwise>	
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${company.name eq 'hpds' and (
											otherField.name eq 'BRANCH'  or
											otherField.name eq 'MACHINE' or
											otherField.name eq 'SCHEDULE'																
											)}">
											<c:set var="currentStyle" value="display:none" />											
										</c:when>
										<c:otherwise>
											<c:set var="currentStyle" value="" />
										</c:otherwise>	
									</c:choose>					
								</c:otherwise>
							</c:choose>
							
							<tr style="${currentStyle }">
								<td width="1%" nowrap style="width: 250px">
									<c:choose>
										<c:when test="${otherField.name eq 'Volume Requirement'}">
											SPECIMEN REQUIREMENT
										</c:when>
										<c:when test="${otherField.name eq 'RELATED WORDS'}">
											RELATED WORDS/TEST
										</c:when>
										<c:otherwise>
											${otherField.name}
										</c:otherwise>
									</c:choose>
								</td>
								<td width="10px"></td>
								<td>
								
								<textarea type="text" id="other_field${otherField.id}" name="o${otherField.id}" class="w385" maxlength="40">${otherFields[otherField.id] }</textarea></td>
								<script type="text/javascript">
									CKEDITOR.replace( 'other_field${otherField.id}',
										{			
															
											toolbar : [[ 'Source', '-', 'Bold', 'Italic', '-', 'SpecialChar', '-','NumberedList','BulletedList','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock',
												'-','TextColor','BGColor','-','wordcount']],
											height:80,
											width: 500	
																	
										}
									);	
								</script>
							</tr>							
						</c:when>
						
						
				
							<%---------------------------------------------------------------------------------- --%>
							<c:when test="${company.name eq 'hpds'}">
								<tr>
									<td width="1%" nowrap>${otherField.name }</td>
									<td width="10px"></td>
									<td><c:choose>
										<c:when
											test="${otherField.name eq 'PATIENT PREPARATION' or otherField.name eq 'TEST PACKAGE' or otherField.name eq 'SPECIAL INSTRUCTIONS'}">
											<textarea id="other_field" name="o${otherField.id }" rows="2"
												cols="60">${otherFields[otherField.id]}</textarea>
										</c:when>
										<c:otherwise>
											<input type="text" id="other_field" name="o${otherField.id }"
												value="${otherFields[otherField.id] }" class="w385" />
										</c:otherwise>
									</c:choose></td>
								</tr>
							</c:when>
							
								
							<c:otherwise>
								<c:choose>
									<%-- Gurkka --%>
									<c:when test="${fn:containsIgnoreCase(otherField.name, 'priorit') and (company.id eq 319 or company.id eq 346)}">
										<%-- Do nothing --%>
									</c:when>
				
									<c:otherwise>
										<tr>
											<c:choose>
												<c:when test="${company.id eq 319 or company.id eq 346}">
													<c:choose>
														<c:when test="${fn:containsIgnoreCase(otherField.name, 'noted')}">
															<%-- Display nothing --%>
														</c:when>
														<c:otherwise>
															<td style="vertical-align:top;" width="1%" nowrap>${otherField.name }</td>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<td style="vertical-align:top;" width="1%" nowrap>${otherField.name }</td>
												</c:otherwise>
											</c:choose>
											<td width="10px"></td>
											<td class="td_otherField_${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'producttype' : '' }${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TAG_NAME ? 'producttag' : '' }">
												<c:if test="${fn:length(otherField.otherFieldValueList) gt 0}">
													<c:choose>
														<c:when test="${company.name eq 'gurkkatest' and (group.name eq GurkkaConstants.PROMOBASKET or group.name eq GurkkaConstants.PRODUCTS_GROUP_NAME) and (otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME or otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TAG_NAME)}">
																<input type="hidden" id="other_field" name="o${otherField.id}" value="${otherFields[otherField.id]}" class="o${otherField.id} typetag${otherField.id}" />
																<c:set var="counter_of_select_tag" value="${0}" />
																<c:set var="arrayOfValue" value="${fn:split(otherFields[otherField.id],';')}"/>
																<c:set var="hasNoContent" value="${true}" />
																<c:forEach items="${arrayOfValue}" var="valueItem" varStatus="c">
																	<c:if test="${fn:trim(valueItem) ne '' and valueItem ne null}">
																		<div>
																		
																		
																		<select testonly="3" onchange="updateproducttypetag('${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'type' : 'tag' }','o${otherField.id }');" id="other_field" class="w385 gurkka_${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'producttype' : '' }${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TAG_NAME ? 'producttag' : '' }">
																			<option value="${GurkkaConstants.NOT_APPLICABLE}">${GurkkaConstants.NOT_APPLICABLE}</option>
																			<c:forEach items="${otherField.otherFieldValueList}" var="option">
																				<option value="${option.value}" ${valueItem eq option.value ? ' selected' : ''}>
																					${option.value}
																				</option>
																			</c:forEach>
																		</select>
																		&nbsp;
																		<input type="hidden" name="counterselecttag" value="${counter_of_select_tag}" />
																		<c:choose>
																			<c:when test="${counter_of_select_tag eq 0}">
																				<c:set var="hasNoContent" value="${false}" />
																				<a style="cursor:pointer;" onclick="addnewtypetag('${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'type' : 'tag' }','o${otherField.id }');" class="${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'addProductType' : 'addProductTag' }">Add New</a>
																			</c:when>
																			<c:otherwise>
																				<a style="cursor:pointer;" relneym="${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'type' : 'tag' }" relparent="o${otherField.id }" onclick="removeParentDiv(this);" class="addProductTypeTag">Remove</a>
																			</c:otherwise>
																		</c:choose>
																		
																		<c:set var="counter_of_select_tag" value="${counter_of_select_tag + 1}" />
																		
																		<br/>&nbsp;</div>
																		
																	</c:if>
																</c:forEach>
																
																
																<%-- 
																<c:forEach items="${otherField.otherFieldValueList}" var="option">
																	<label>
																		<input onchange="changeTypesAndTags('${otherField.id}');" type="checkbox" class="of_typesandtags${otherField.id}" name="" id="" value="${option.value}" ${fn:containsIgnoreCase(otherFields[otherField.id], option.value) ? ' checked' : ''} />
																		${option.value}
																	</label>
																</c:forEach>
																 --%>
																 <c:if test="${hasNoContent}">
																	<select testonly="3" onchange="updateproducttypetag('${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'type' : 'tag' }','o${otherField.id }');" id="other_field" class="w385 gurkka_${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'producttype' : '' }${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TAG_NAME ? 'producttag' : '' }">
																		<option value="${GurkkaConstants.NOT_APPLICABLE}">${GurkkaConstants.NOT_APPLICABLE}</option>
																		<c:forEach items="${otherField.otherFieldValueList}" var="option">
																			<option value="${option.value}" ${otherFields[otherField.id] eq option.value ? ' selected' : ''}>
																				${option.value}
																			</option>
																		</c:forEach>
																	</select>
																	&nbsp;<a style="cursor:pointer;" onclick="addnewtypetag('${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'type' : 'tag' }','o${otherField.id }');" class="${otherField.name eq GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME ? 'addProductType' : 'addProductTag' }">Add New</a>
																	<br/><br/>
																</c:if>
																
														</c:when>
														<c:otherwise>
															
															<select testonly="3" id="other_field" name="o${otherField.id }" class="w385">
																<c:forEach items="${otherField.otherFieldValueList}"
																	var="option">
																	<option value="${option.value}"
																		<c:if test="${otherFields[otherField.id] eq option.value}">
																									selected="selected"
																								</c:if>>
																	${option.value}</option>
																</c:forEach>
															</select>
															
														</c:otherwise>
													</c:choose>
												</c:if> 
												
												<c:if test="${fn:length(otherField.otherFieldValueList) eq 0}">										
														<c:choose>
															<c:when test="${company.id eq 319 or company.id eq 346}">
																<c:choose>
																	<c:when test="${fn:containsIgnoreCase(otherField.name, 'promo') and not fn:containsIgnoreCase(otherField.name, 'related')}">
																	
																		<c:choose>
																			<c:when test="${fn:containsIgnoreCase(otherField.name, 'valid')}">
																				<input type="text" id="item_discount_date" name="o${otherField.id }" value="${otherFields[otherField.id]}" class="textbox2" />
																				<a href="javascript:;" id="item_discount_date_button"><img src="images/calendar.png" style="border: 0px" alt="Open Calendar" /></a>
																				<script type="text/javascript">
																				    Calendar.setup({
																				        inputField     :    "item_discount_date",     // id of the input field
																				        ifFormat       :    "%m-%d-%Y",      // format of the input field
																				        button         :    "item_discount_date_button",  // trigger for the calendar (button ID)
																				        align          :    "Tl",           // alignment (defaults to "Bl")
																				        singleClick    :    true
																				    });
																				</script>
																			</c:when>
																			<c:otherwise>
																				<input type="number" id="other_field" step="any" name="o${otherField.id }" value="${otherFields[otherField.id]}" class="w385"
																				<c:if test="${fn:containsIgnoreCase(otherField.name, 'percent')}"> max="99.99" </c:if>
																				/>
																			</c:otherwise>
																		</c:choose>
																	
																		 
																	</c:when>
																	<c:when test="${fn:containsIgnoreCase(otherField.name, 'noted')}">
																		<c:if test="${inventoryQuantity le 5 }">
																			<script type="text/javascript">
																				$(function() {
																					$("td").filter(function (index) {
																						 return $(this).text() == "Inventory Quantity"; 
																					})
																				    .parent()
																				    .after(
																						'<tr>' + 
																							'<td nowrap>&nbsp;</td>' + 
																							'<td></td>' + 
																							'<td><input type="checkbox" id="other_field"' +
																							'name="o${otherField.id }"' + 
																							<c:if test="${otherFields[otherField.id] eq 'on'}">
																								'checked="checked"' +
																							</c:if>
																							'/>' + 
																							'<i><b>Mark As Noted.</b></i></td>' + 
																						'</tr>'
																					);
																				});
																			</script>
																		</c:if>
																	</c:when>
																	<c:otherwise>
																		<input type="text" id="other_field" name="o${otherField.id }" value="${otherFields[otherField.id]}" class="w385" />	
																	</c:otherwise>
																</c:choose>
															</c:when>
															
															<c:otherwise>
																<%-- no specific company --%>
																<input type="text" id="other_field" name="o${otherField.id }" value="${fn:escapeXml(otherFields[otherField.id])}" class="w385" />	
															</c:otherwise>
														</c:choose>												
												</c:if>
											</td>
										</tr>
										<c:if test="${company.id == 321 && fn:containsIgnoreCase(otherField.name, 'Article No.')}">
										</tbody>
										</c:if>
									</c:otherwise>
								</c:choose>
				
							</c:otherwise>
						</c:choose>
					</c:forEach>

				</c:if>
					
				</table>
				<c:if test="${item.language== null}"> 
				<table width="100%" id="doctorScheduleFormParentTableId">
					<c:choose>
						<c:when test="${item ne null}">
							<c:set var="displayRepeatLabel" value="${true}" />
							<c:set var="displayRemove" value="${true}" />
							<c:forEach var="sched" items="${item.schedules}">
								<%@include file="includes/scheduleForm.jsp"%>
							</c:forEach>
							<c:set var="displayRepeatLabel" value="${false}" />
							<c:set var="displayRemove" value="${false}" />
						</c:when>
						<c:otherwise>
							<c:set var="displayRepeatLabel" value="${true}" />
							<%@include file="includes/scheduleForm.jsp"%>
						</c:otherwise>
					</c:choose>
					<table>
				
						<c:if test="${company.name == 'hiprecision'}">
							<table width="100%" id="doctorScheduleFormParentTableId">
								<tr>
									<td style="width: 100%; text-align: right"><input
										type="button" value="Add Another Schedule"
										onclick="return addAnotherSection('doctorScheduleFormParentTableId','doctorScheduleFormHiddenTableId')"
										class="btnBlue"></td>
								</tr>
							</table>
						</c:if>
				
					</table>
					
					<script>
						function addNewFitting() {
							var tr = "<tr>" +
										"<td>" +
											"<select name=\"fittings\" style=\"width:100%\">" +
												<c:forEach items="${fittingsItem}" var="fitting">
													"<option value=\"${fitting.id}\">${fitting.name}</option>" +
												</c:forEach>		
											"</select>" +
										"</td>" +
										"<td><input type=\"button\" value=\"Remove\" onclick=\"removeFitting(jQuery(this));\"/></td>" +
									 "</tr>";
							jQuery('#fittings tr:last').after(tr);
						}	
						
						function removeFitting(row) {
							row.closest('tr').remove();
						}
					</script>
					
					
					<div id="scheduleFormHiddenFieldsBasefromCheckbox"
						style="display: none"></div>
				
					<table width="50%">
						<table width="${company.name eq 'politiker' and group.name eq 'speakup' ? '100' : '100'}%">
					

						<c:if test="${group.hasAttributes && fn:length(group.attributes)>0}">
								<tr bgcolor="#f5f5f5">
									<td colspan="3">
									<table>
										<tr>
											<td align="center">
											<h4>Attributes</h4>
											</td>
				
										</tr>
										<c:choose>
											<c:when test="${empty item.attributes}">
												<c:forEach items="${group.attributes}" var="att" varStatus="i">
													<tr>
														<td width="1%" nowrap>${att.name}</td>
														<td width="9%"></td>
														<td><c:if test="${not empty att.presetValues}">
															<select name="${att.name}" style="width: 145px;">
																<option value=""></option>
																<c:forEach items="${att.presetValues}" var="presetval">
																	<option value="${presetval.value}">${presetval.value}</option>
																</c:forEach>
															</select>
														</c:if> <c:if test="${empty att.presetValues}">
															<input type="text" id="1${ i.count }" name="${att.name}"
																value="${item.attributes[i.count-1].value }"
																onchange="validate(this.value, '${ att.dataType }', '${att.name}');" />
														</c:if></td>
													</tr>
												</c:forEach>
											</c:when>
											<c:when test="${item.id>0}">
												<c:forEach items="${item.attributes}" var="att" varStatus="i">
													<c:if test="${ not att.disabled }">
														<tr>
															<td width="1%" nowrap>${att.attribute.name}</td>
															<td width="9%"></td>
															<td><c:if test="${not empty att.attribute.presetValues}">
																<option value=""></option>
																<select name="${att.attribute.name}" style="width: 145px;">
																	<c:forEach items="${att.attribute.presetValues}"
																		var="presetval">
																		<option value="${presetval.value}"
																			<c:if test="${presetval.value == att.value}">selected</c:if>>${presetval.value}</option>
																	</c:forEach>
																</select>
															</c:if> <c:if test="${empty att.attribute.presetValues}">
																<input type="text" id="2${ i.count }"
																	name="${att.attribute.name}" value="${att.value }"
																	onchange="validate( this.value , '${ att.attribute.dataType }', '${att.attribute.name}');" />
															</c:if> <input type="hidden" name="${att.id }" value="${att.id }" />
															</td>
														</tr>
													</c:if>
												</c:forEach>
				
												<c:set var="l" value="${0}" />
												<c:if
													test="${fn:length(group.attributes) > fn:length(item.attributes)}">
													<c:forEach items="${group.attributes}" var="grp_att"
														varStatus="j">
														<c:forEach items="${item.attributes}" var="item_att"
															varStatus="k">
															<c:if test="${grp_att.name eq item_att.attribute.name}">
																<c:set var="l" value="${1}" />
															</c:if>
														</c:forEach>
				
														<c:if test="${l ne 1 }">
															<tr>
																<td width="1%" nowrap>${grp_att.name}</td>
																<td width="9%"></td>
																<td><c:if test="${not empty grp_att.presetValues}">
																	<option value=""></option>
																	<select name="${grp_att.name}" style="width: 145px;">
																		<c:forEach items="${grp_att.presetValues}" var="presetval">
																			<option value="${presetval.value}">${presetval.value}</option>
																		</c:forEach>
																	</select>
																</c:if> <c:if test="${empty grp_att.presetValues}">
																	<input type="text" id="3${ i.count }"
																		name="${grp_att.name}"
																		onchange="validate(this.value, '${ grp_att.dataType }', '${grp_att.name}');" />
																</c:if></td>
																</td>
															</tr>
														</c:if>
														<c:set var="l" value="${0}" />
													</c:forEach>
												</c:if>
				
											</c:when>
											<c:otherwise>
												<c:forEach items="${group.attributes}" var="att" varStatus="i">
													<tr>
														<td width="1%" nowrap>${att.name}</td>
														<td width="9%"></td>
														<td><c:if test="${not empty att.presetValues}">
															<select name="${att.name}" style="width: 145px;">
																<option value=""></option>
																<c:forEach items="${att.presetValues}" var="presetval">
																	<option value="${presetval.value}">${presetval.value}</option>
																</c:forEach>
															</select>
														</c:if> <c:if test="${empty att.presetValues}">
															<input type="text" id="4${ i.count }" name="${att.name}"
																value="${item.attributes[i.count-1].value }"
																onchange="validate(this.value, '${ att.dataType }', '${att.name}');" />
														</c:if></td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
										<c:if test="${ item ne null }">
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td><a
													href="manageAttributes.do?group_id=${ group.id }&item_id=${ item.id }">Manage
												Attributes</a></td>
											</tr>
										</c:if>
									</table>
									</td>
								</tr>
							</c:if>
				
						<c:if test="${company.name eq 'hpds'}">
								
								<c:if test="${not empty machineList}">
									<tr>
										
										<td nowrap>MACHINE</td>
										<td></td>
										<td>
										<hr>
										<table>
											<tr>
												<c:forEach items="${ machineList }" var="machine" varStatus="ctr">
													<c:if test="${ ctr.count mod 3 eq 0 }"></tr><tr></c:if>
													<td>
														<input type="checkbox" name="machineIds" id="machine_${ machine.id }" value="${ machine.id }"/>
														<label for="branch_${ machine.id }">&nbsp;&nbsp;${ machine.name }</label>
													</td>
												</c:forEach>
											</tr>
										</table>
										<hr>
										</td>
									</tr>
								</c:if>
								
								<c:if test="${not empty branchList}">
									<tr>
										
										<td nowrap>BRANCH</td>
										<td></td>
										<td>
										<hr>
										<table>
											<tr>
												<c:forEach items="${ branchList }" var="branch" varStatus="ctr">
													<c:if test="${ ctr.count mod 3 eq 0 }"></tr><tr></c:if>
													
													<c:if test="${ ctr.count eq 1 }">
														<td><input type="checkbox" id="branch_0" value="true"/><label for="allBranch">&nbsp;&nbsp;All Branches</label></td>
													</c:if>
													
													<td>
														<input type="checkbox" name="branchIds" id="branch_${ branch.id }" value="${ branch.id }"/>
														<label for="branch_${ branch.id }">&nbsp;&nbsp;${ branch.name }</label>
													</td>
												</c:forEach>
											</tr>
										</table>
										<hr>
										</td>
									</tr>
								</c:if>

							<tr>
								<td nowrap>&nbsp;</td>
								<td></td>
								<td><s:checkbox name="item.disabled" value="%{item.disabled}" theme="simple" /> 
									<i><b>This item is disabled.</b></i>
								</td>
							</tr>
				
							<tr>
								<td nowrap>&nbsp;</td>
								<td></td>
								<td>
									<c:choose>
										<c:when test="${company.name eq 'gurkkatest' and group.name eq GurkkaConstants.COCKTAILS_GROUP_NAME}">
											<s:checkbox name="item.featured" value="%{item.featured}"
												theme="simple" /> <i><b>This item is NEW SUBMITTED</b></i>
										</c:when>
										<c:otherwise>
											<s:checkbox name="item.featured" value="%{item.featured}"
												theme="simple" /> <i><b>This item will be featured in the website.</b></i>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							
							
							
						</c:if>
							<c:if test="${company.companySettings.hasBestSellers eq true}">
								<tr>
									<td nowrap>&nbsp;</td>
									<td></td>
									<td><s:checkbox name="item.bestSeller"
										value="%{item.bestSeller}" theme="simple" /> <i><b>This
									item is a best seller.</b></i></td>
								</tr>
							</c:if>
							<c:if test="${group.isOutOfStock}">
								<tr>
									<td nowrap>&nbsp;</td>
									<td></td>
									<td><s:checkbox name="item.isOutOfStock"
										value="%{item.isOutOfStock}" theme="simple" /> <i><b>This
									item is out of stock.</b></i></td>
				
								</tr>
							</c:if>
							<c:if test="${company.companySettings.hasOtherFields ne false and (company.id eq 319 or company.id eq 346)}">
								<c:forEach items="${group.otherFields}" var="otherField"
									varStatus="counter">
									<c:if test="${fn:length(otherField.otherFieldValueList) eq 0 and fn:containsIgnoreCase(otherField.name, 'priorit')}">
										<tr>
											<td nowrap>&nbsp;</td>
											<td></td>
											<td><input type="checkbox" id="other_field"
												name="o${otherField.id }"
												<c:if test="${otherFields[otherField.id] eq 'on'}">
														checked="checked"
													</c:if> />
											<i><b>Prioritized Freebie.</b></i></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${company.name eq 'hbc'}">
								<tr>
									<td nowrap>&nbsp;</td>
									<td></td>
									<td><s:checkbox name="item.validForUSCanadaDelivery"
										value="%{item.validForUSCanadaDelivery}" theme="simple" /> <i><b>This
									item is valid for US and Canada deliveries.</b></i></td>
				
								</tr>
							</c:if>
						</c:if>

						<c:if test="${company.name ne 'pocketpons' and company.name ne 'nissanrentacar' }">
							<tr id="shortDescriptionRow" valign="top">
							
								<td valign="top" width="1%" nowrap><br>Short Description</td>
								<td></td>
								<td>	
									<textarea  class="w385" id="item.shortDescription" name="item.shortDescription" >${item.shortDescription}</textarea>
									<c:choose>
										<c:when test="${company.name eq 'mraircon'}">
											<script type="text/javascript">
												CKEDITOR.replace( 'item.shortDescription',
													{			
														width: 500,					
														toolbar : [[ 'Source', '-', 'Bold', 'Italic', '-','NumberedList','BulletedList','-','Link','Unlink','-','FontSize','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock',
																		'-','Image','-','wordcount']],
														filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
														filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
														filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
														filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
														filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
														filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'							
													}
												);	
											</script>
										</c:when>
										
										<c:when test="${company.name eq 'politiker'}">
											<script type="text/javascript">
												CKEDITOR.replace( 'item.shortDescription',
													{			
																		
														toolbar : [[ 'Source', '-', 'Bold', 'Italic', '-','NumberedList','BulletedList','-','Link','Unlink','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock',
																		'-','wordcount']]
																				
													}
												);	
											</script>
										</c:when>
										
										<c:otherwise>
											<script type="text/javascript">
												CKEDITOR.replace( 'item.shortDescription',
													{								
														height:100,width: 500								
													}
												);	
											</script>
										</c:otherwise>
									</c:choose>
									
												
								</td>
							</tr>
						</c:if>		
				
			<tr>
				<td colspan="3">
					<c:if test="${fn:length(gurkkaPromoBasketByCurrentItem) gt 0}">
						<div class="pageTitle" style="background-color:red;">
							<h1 style="color:#fff;"><strong>Please be reminded that this item is Under the following Promo Basket(s) :</strong></h1><br/>
							<c:forEach items="${gurkkaPromoBasketByCurrentItem}" var="gurkkaPromoBasket_Item" varStatus="c" >
								<p ><a target="_blank" style="color:#fff;" href="edititem.do?group_id=${gurkkaPromoBasket_Item.parentGroup.id}&item_id=${gurkkaPromoBasket_Item.id}">- &nbsp;${gurkkaPromoBasket_Item.name}</a></p>
								<c:if test="${not c.last}">
									<br/>
								</c:if>
							</c:forEach>
						</div>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border: 0px;"></td>
				<td style="border: 0px;">
				 
				<c:choose>
					<c:when test="${item.id >0}">
						<input type="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue">
						<input type=button value="Cancel" class="btnBlue" onclick="window.location='items.do?group_id=${group.id}'">
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
								
				</td>
			</tr>
					
		</table>
		
		</form>
		<c:if test="${user.company != null && (user.company.id eq 51 || user.company.id eq 316 || user.company.id eq 355 || user.company.id eq 333 || user.company.id eq 420)}">
			<div id="uploadimageform">
				<%@include file="includes/itemcomponentform.jsp" %>  	
			</div>
		</c:if>
	</div><!--//mainContent-->

	<div class="sidenav">
		<c:choose>
			<c:when test="${company.name ne 'pocketpons'}">
				<c:set var="pageNumSuffix2" value="" />
			  	<c:if test="${param.pageNumber ne null and param.pageNumber ne ''}">
			  		<c:set var="pageNumSuffix2" value="?pageNumber=${param.pageNumber}" />
			  	</c:if>
				<form method="post" action="uploaditemimage.do${pageNumSuffix2}" enctype="multipart/form-data" onsubmit="return validateImage()">
				
					<input type="hidden" name="parent" value="${item.parent.id}" />
					<input type="hidden" name="item_id" value="${item.id}" />
					<input type="hidden" name="group_id" value="${group.id}" />
					<c:if test="${fn:length(item.images) > 0}">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>
		
	<ul class="uploadImageNote">
	  	<li>Click on Edit to Modify Title, Caption and Description of the image</li>
	  	<li>Drag and Arrange to Sort Images. Click on Update Image Order to Update Order of Images</li>
  	</ul>
	  <hr />
	  <ul id="phoneticlong">

	    
		<c:forEach items="${item.images}" var="img">
		<li itemID="${img.id}" style="cursor: url(https://mail.google.com/mail/images/2/openhand.cur), default !important;">

		  <img src="${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}" />&nbsp;&nbsp;
		  <input type="button" value="Edit" onclick="openImageDialog(${img.id}, '${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}', '${img.filename}');" class="btnBlue">
		  <c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
			<c:set var="pageNumSuffix3" value="" />
		  	<c:if test="${param.pageNumber ne null and param.pageNumber ne ''}">
		  		<c:set var="pageNumSuffix3" value="&pageNumber=${param.pageNumber}" />
		  	</c:if>
			<a onclick="return confirm('Do you really want to delete this image?');" href="deleteitemimage.do?group_id=${group.id}&item_id=${item.id}&image_id=${img.id}${pageNumSuffix3}" class="btnBlue">Delete</a>
			<%--<a onclick="return confirm('Do you really want to delete this image?');" href="deleteitemimage.do?group_id=${group.id}&item_id=${item.id}&image_id=${img.id}" class="delete">[Delete]</a>--%>
		  	<div class="clear"></div>
		  <a href="#"><strong>${img.filename}</strong></a> 
			
		  	<div class="clear"></div>
			<table>
				 <c:set var="trimTitle" value="${fn:trim(img.title)}"/>
				 <c:set var="trimCaption" value="${fn:trim(img.caption)}"/>
				 <c:set var="trimDescription" value="${fn:trim(img.description)}"/>
				<tr id="tr_title_${img.id}" <c:if test="${fn:length(trimTitle) eq 0}">style="display:none"</c:if>>
					<td><strong>Title&nbsp;&nbsp;</strong></td>
				
					<td id="title${img.id }_text">
						(${img.title})
					</td>
				</tr>
				
				<tr id="tr_caption_${img.id}" <c:if test="${fn:length(trimCaption) eq 0}">style="display:none"</c:if>>
					<td><strong>Caption&nbsp;&nbsp;</strong></td>
															
					<td id="caption${img.id }_text">
						(${img.caption}) 
					</td>
				</tr> 
				
				
				<tr id="tr_description_${img.id}" <c:if test="${fn:length(trimDescription) eq 0}">style="display:none"</c:if>>
					<td><strong>Description&nbsp;&nbsp;</strong></td>
				
					<td id="description${img.id }_text">
						(${img.description})
					</td>
				</tr>
			</table>
			<table style="display:none">
										<tr>
											<td>Title</td>
										</tr>
										<tr>
											<td><input disabled type="text" id="title${img.id }" name="image_title_${img.id}" value="${img.title}" class="textbox4" onblur="updateImageTitle(${img.id}, this.value);"></td>
										</tr>
										<tr>
											<td>Caption</td>
										</tr>
										<tr>											
											<td><input disabled type="text" id="caption${img.id }" name="image_caption_${img.id}" value="${img.caption}" class="textbox4" onblur="updateImageCaption(${img.id}, this.value);"></td>
										</tr> 
										<tr>
											<td>Description</td>
										</tr>
										<tr>
											<td>
												<textarea disabled class="textbox4" id="description${img.id }" onblur="updateImageDescription(${img.id}, this.value);">${img.description}</textarea>
											</td>
										</tr>
									</table>
									</c:if>	
									
			<%--<input type="button" value="Edit" onclick="openImageDialog(${img.id}, '${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}', '${img.filename}');" class="btnBlue">--%>
		  <div class="clear"></div>

		</li>
		</c:forEach>
	  </ul>
		<ul>
		  	<li>
		  		<input type="button" value="Update Image Order" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='edititem.do?group_id=${group.id}&item_id=${item.id}'" />
		  	</li>
		</ul>
		</c:if>
		<br /><br />
		
		<div id="uploadImageReminder">
	  <h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>

	  <ul class="uploadImageNote">

		<li>You are only allowed to upload <b>${companySettings.maxAllowedImages}</b> files.</li>

		<li><b>${companySettings.image1Width} x ${companySettings.image1Heigth}</b> is the most advisable size of image that should be uploaded.</li>

	  </ul>
	  </div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>
			
		  <td colspan="2">
								    
			<div id="attachment" class="attachment" style="display:none">
									<div id="dropcap" class="dropcap">1</div>
								    
								    <input id="file" name="upload" type="file" size="30" />
								    <!-- 
								    <input id="desc" name="alt" type="text" size="30"/>
								    &nbsp;
								    --> 
								    <a href="#" onclick="javascript:removeFile(this.parentNode.parentNode,this.parentNode);">Remove</a>
								  </div>
								    
								   <div id="attachments" class="container">
								    <!--
								    **************************************************************************
								    Below is the call to the "addUpload" function which adds an Attachment
								    section to the form. Customize the function parameters as needed:
								    1) Maximum # of attachments allowed in this pub type
								    2) base Field Name for the Description fields in your pub type
								    3) base Field Name for the File Upload field in your pub type
								    **************************************************************************
								    -->
								    <span id="attachmentmarker"></span>
								    <div align="right">
							    	 <a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload'); ">Attach an image</a><br/>
								    
									<input type="submit" value="Upload Image!" class="btnBlue"/>
								</div>    
			</div>
			</td>

		</tr>

	  </table>
	</form>
	</c:when>
	
	<c:otherwise>
		<c:set var="pageNumSuffix2" value="" />
	  	<c:if test="${param.pageNumber ne null and param.pageNumber ne ''}">
	  		<c:set var="pageNumSuffix2" value="?pageNumber=${param.pageNumber}" />
	  	</c:if>
		<form method="post" action="uploaditemimage.do${pageNumSuffix2}" enctype="multipart/form-data">
			<input type="hidden" name="parent" value="${item.parent.id}" />
			<input type="hidden" name="item_id" value="${item.id}" />
			<input type="hidden" name="group_id" value="${group.id}" />
			<c:if test="${fn:length(item.images) > 0}">
	  		<h3><img src="images/iImage.gif" align="absmiddle" /> Image List</h3>
		
			<ul class="uploadImageNote">
<!-- 			  	<li>Click on Edit to Modify Title, Caption and Description of the image</li> -->
<!-- 			  	<li>Drag and Arrange to Sort Images. Click on Update Image Order to Update Order of Images</li> -->
		  	</ul>
	  		<hr />
	  		<ul id="phoneticlong">
				<c:forEach items="${item.images}" var="img" varStatus="stat">
					<li itemID="${img.id}" style="cursor: url(https://mail.google.com/mail/images/2/openhand.cur), default !important;">

		  			<img src="${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}" />&nbsp;&nbsp;
<%-- 	  				<input type="button" value="Edit" onclick="openImageDialog(${img.id}, '${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}', '${img.filename}');" class="btnBlue"> --%>
		  			<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
						<c:set var="pageNumSuffix3" value="" />
					  	<c:if test="${param.pageNumber ne null and param.pageNumber ne ''}">
					  		<c:set var="pageNumSuffix3" value="&pageNumber=${param.pageNumber}" />
					  	</c:if>
						<a onclick="return confirm('Do you really want to delete this image?');" href="deleteitemimage.do?group_id=${group.id}&item_id=${item.id}&image_id=${img.id}${pageNumSuffix3}" class="btnBlue">Delete</a>
						<%--<a onclick="return confirm('Do you really want to delete this image?');" href="deleteitemimage.do?group_id=${group.id}&item_id=${item.id}&image_id=${img.id}" class="delete">[Delete]</a>--%>
		  				<div class="clear"></div>
		  				<a href="#"><strong>${img.filename}
		  				<c:if test="${stat.index eq '0' }"><br>(Coupon Square)</c:if>
		  				<c:if test="${stat.index eq '1' }"><br>(Coupon Horizontal)</c:if>
		  				</strong></a> 
			
		  				<div class="clear"></div>
						<table>
							<c:set var="trimTitle" value="${fn:trim(img.title)}"/>
							<c:set var="trimCaption" value="${fn:trim(img.caption)}"/>
							<c:set var="trimDescription" value="${fn:trim(img.description)}"/>
							<tr id="tr_title_${img.id}" <c:if test="${fn:length(trimTitle) eq 0}">style="display:none"</c:if>>
								<td><strong>Title&nbsp;&nbsp;</strong></td>
				
								<td id="title${img.id }_text">
									(${img.title})
								</td>
							</tr>
				
							<tr id="tr_caption_${img.id}" <c:if test="${fn:length(trimCaption) eq 0}">style="display:none"</c:if>>
								<td><strong>Caption&nbsp;&nbsp;</strong></td>
																		
								<td id="caption${img.id }_text">
									(${img.caption}) 
								</td>
							</tr>
				
							<tr id="tr_description_${img.id}" <c:if test="${fn:length(trimDescription) eq 0}">style="display:none"</c:if>>
								<td><strong>Description&nbsp;&nbsp;</strong></td>
							
								<td id="description${img.id }_text">
									(${img.description})
								</td>
							</tr>
						</table>
						<table style="display:none">
							<tr>
								<td>Title</td>
							</tr>
							<tr>
								<td><input disabled type="text" id="title${img.id }" name="image_title_${img.id}" value="${img.title}" class="textbox4" onblur="updateImageTitle(${img.id}, this.value);"></td>
							</tr>
							<tr>
								<td>Caption</td>
							</tr>
							<tr>											
								<td><input disabled type="text" id="caption${img.id }" name="image_caption_${img.id}" value="${img.caption}" class="textbox4" onblur="updateImageCaption(${img.id}, this.value);"></td>
							</tr> 
							<tr>
								<td>Description</td>
							</tr>
							<tr>
								<td>
									<textarea disabled class="textbox4" id="description${img.id }" onblur="updateImageDescription(${img.id}, this.value);">${img.description}</textarea>
								</td>
							</tr>
						</table>
					</c:if>	
									
					<%--<input type="button" value="Edit" onclick="openImageDialog(${img.id}, '${contextParams['IMAGE_PATH']}/images/items/${img.thumbnail}', '${img.filename}');" class="btnBlue">--%>
		  			<div class="clear"></div>

				</li>
			</c:forEach>
  		</ul>
<!-- 		<ul> -->
<!-- 		  	<li> -->
<%-- 		  		<input type="button" value="Update Image Order" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='edititem.do?group_id=${group.id}&item_id=${item.id}'" /> --%>
<!-- 		  	</li> -->
<!-- 		</ul> -->
		</c:if>
		<br /><br />
	<c:if test="${fn:length(item.images) == 0}">
		<div id="uploadImageReminder">
	  		<h3><img src="images/iImage.gif" align="absmiddle" /> Upload Image</h3>

<!-- 	  		<UL CLASS="UPLOADIMAGENOTE"> -->

<%-- 				<LI>YOU ARE ONLY ALLOWED TO UPLOAD <B>${COMPANYSETTINGS.MAXALLOWEDIMAGES}</B> FILES.</LI> --%>
<%-- 				<LI><B>${COMPANYSETTINGS.IMAGE1WIDTH} X ${COMPANYSETTINGS.IMAGE1HEIGTH}</B> IS THE MOST ADVISABLE SIZE OF IMAGE THAT SHOULD BE UPLOADED.</LI> --%>
<!-- 			</UL> -->
	  </div>
	
	  <table width="100%" border="0" cellspacing="0" cellpadding="3">
	    <tr>
			<td colspan="2">				    
				<div id="attachments" class="container">
					<div id="dropcap1" class="dropcap">Coupon Square</div>
									    
			    	<input id="file" name="upload" id="upload1" type="file" size="30" required/>
									  
<!-- 				    <a href="#" onclick="javascript:removeFile(this.parentNode.parentNode,this.parentNode);">Remove</a> -->
		  		</div>
		  		
		  		</br>
		  		
		  		<div id="attachments" class="container">
					<div id="dropcap2" class="dropcap">Coupon Horizontal</div>
									    
			    	<input id="file" name="upload" id="upload2" type="file" size="30" required/>
			    	<br><br>
					<input type="submit" value="Upload Image!" class="btnBlue"/>
<!-- 				    <a href="#" onclick="javascript:removeFile(this.parentNode.parentNode,this.parentNode);">Remove</a> -->
		  		</div>
								    
<!-- 			   	<div id="attachments" class="container"> -->
<!-- 			   		<span id="attachmentmarker"></span> -->
<!-- 					<div align="right"> -->
<%-- 						<a id="addupload" href="javascript:addUpload(${allowedImageCount}, 'alt', 'upload'); ">Attach an image</a><br/> --%>
							
<!-- 					</div>     -->
<!-- 				</div> -->
			</td>
		</tr>
	  </table>
	</c:if>
	</form>
	</c:otherwise>
</c:choose>

<br/>
<div id="uploadimageform">  	

<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
<c:set var="imageCount" value="${fn:length(item.images)}" />
<c:set var="allowedImageCount" value="${maxImage - imageCount}" />


<c:if test="${company.name ne 'pocketpons' }">
	<form method="post" action="uploaditemfile.do" enctype="multipart/form-data" onsubmit="return validateFile()">
				
					<input type="hidden" name="parent" value="${item.parent.id}" />
					<input type="hidden" name="item_id" value="${item.id}" />
					<input type="hidden" name="group_id" value="${group.id}" />
		

				<c:if test="${fn:length(item.files) > 0}">

			<h3>Files List</h3>
			<ul id="filesList">
						<c:forEach items="${item.files}" var="file"> 
									<li itemID="${file.id}">
									
									<c:choose>
									<c:when test="${file.fileType eq 'application/pdf'}">
										<img src="../images/icons/page_white_acrobat.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/msword'}">
										<img src="../images/icons/page_white_word.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-powerpoint'}">
										<img src="../images/icons/page_white_powerpoint.png">
									</c:when>
									<c:when test="${file.fileType eq 'application/vnd.ms-excel'}">
										<img src="../images/icons/page_excel.png">
									</c:when>
									<c:when test="${file.fileType eq 'text/plain'}">
										<img src="../images/icons/page_white_text.png">
									</c:when>
									<c:otherwise>
										<img src="../images/icons/page_white.png">
									</c:otherwise>
									</c:choose>
									<b>${file.fileName}</b><br />
						
									<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'System Administrator'}">
										<a href="javascript:void(0);" onclick="openFileDialog(${file.id}, '${file.fileName}');">Edit</a> |
										<a  href="downloaditemfile.do?group_id=${group.id}&item_id=${item.id}&file_id=${file.id}">Download</a> | 
										<a onclick="return confirm('Do you really want to delete this file?');" href="deleteitemfile.do?group_id=${group.id}&item_id=${item.id}&file_id=${file.id}">Delete</a>
										
									<div class="clear"></div>
			<table>
				 <c:set var="trimTitle" value="${fn:trim(file.title)}"/>
				 <c:set var="trimCaption" value="${fn:trim(file.caption)}"/>
				 <c:set var="trimDescription" value="${fn:trim(file.description)}"/>
				<tr id="tr_file_title_${file.id}" <c:if test="${fn:length(trimTitle) eq 0}">style="display:none"</c:if>>
					<td><strong>Title&nbsp;&nbsp;</strong></td>
				
					<td id="file_title${file.id }_text">
						(${file.title})
					</td>
				</tr>
				
				<tr id="tr_file_caption_${file.id}" <c:if test="${fn:length(trimCaption) eq 0}">style="display:none"</c:if>>
					<td><strong>Caption&nbsp;&nbsp;</strong></td>
															
					<td id="file_caption${file.id }_text">
						(${file.caption}) 
					</td>
				</tr> 
				
				<tr id="tr_file_description_${file.id}" <c:if test="${fn:length(trimDescription) eq 0}">style="display:none"</c:if>>
					<td><strong>Description&nbsp;&nbsp;</strong></td>
				
					<td id="file_description${file.id }_text">
						(${file.description})
					</td>
				</tr>
			</table>
										<table style="display:none">
											<tr>
												<td>Title</td>
											</tr>
											<tr>
												<td><input type="text" id="file_title${file.id}" name="file_title_${file.id}" value="${file.title}" class="textbox4" onblur="updateFileTitle(${file.id}, this.value);"></td>
											</tr>
											<tr>
												<td>Caption</td>
											</tr>
											<tr>											
												<td><input type="text" id="file_caption${file.id}" name="file_caption_${file.id}" value="${file.caption}" class="textbox4" onblur="updateFileCaption(${file.id}, this.value);"> </td>
											</tr> 
											<tr>
												<td>Description</td>
											</tr>
											<tr>
												<td>
													<textarea class="textbox4" id="file_description${file.id}" onblur="updateFileDescription(${file.id}, this.value);">${file.description}</textarea>
												</td>
											</tr>
										</table>
									</c:if>	
								<div class="clear"></div>
							</li>
						
						</c:forEach>
						</ul>
						<ul>
						  	<li>
						  		<input type="button" value="Update File Order" onclick="saveFileOrder();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='edititem.do?group_id=${group.id}&item_id=${item.id}'" />
						  	</li>
						</ul>
						

			</c:if>
		
			<c:if test="${allowedImageCount > 0}">
			<div id="uploadFileReminder">
				<h3>Upload File</h3>
					<ul class="uploadImageNote">
					<c:if test="${invalidFiles gt 0}" >
							
								<li> There were ${invalidFiles} invalid files were not uploaded. </li>
								<li> Make sure that the file follows the allowed file type and file size.</li> 
						
						</c:if>
				
					

					<li> You are only allowed to upload <b>5</b> files that are not images.  </li>
					
					<li>5 MB  is the maximum allowable size of file that should be uploaded.</li>
					
					</ul>
					</div>
			<table width="100%" cellpadding="0" border="0">



				<tr> 
					
					  <td>
						<div id="attachment2" style="display: none;"> 
							<div id="dropcap2" class="dropcap">1</div>
								    
								    <input id="file" name="upload" type="file" size="30" />
								    <br/> 
								    <!-- 
								    <input id="desc" name="alt" type="text" size="30"/>
								    &nbsp;
								    --> 
								    <a href="#" onclick="javascript:removeFile2(this.parentNode.parentNode,this.parentNode);">Remove</a>
								    </div>  
						      
					    <div id="attachments_${dynamicType}">
						    <!-- 
						    **************************************************************************
						    Below is the call to the "addDynamicUpload" function which adds an Attachment
						    section to the form. Customize the function parameters as needed:
						    1) Maximum # of attachments allowed in this pub type
						    2) base Field Name for the Description fields in your pub type
						    3) base Field Name for the File Upload field in your pub type 
						    3) Unique String preferably the file type 
						    ************************************************************************** 
						    -->
						     <span id="attachmentmarker2"></span>
							    
							    <div align="right">
							    	 <a id="addupload2" href="javascript:addUpload2(${allowedImageCount}, 'alt', 'upload'); ">Attach a file</a><br/>
								    
									<input type="submit" value="Upload Now!" class="btnBlue"/>
								</div> 
						</div>  
						
					</td>  
				</tr>

			
				
						
				
				</table><!-- END OF MAIN TABLE -->
			</c:if>
		
		
		

	</form>
 </c:if>
</div>
<br/>
<br/>


	</div><!--//sidenav-->

	<div class="clear"></div>

  </div><!--//container-->
</div>

	<%-- POPUP DIALOG --%>
	<div id="QuantityDialog" title="Update Available Quantity" style="float: left; display: none;">
		<table style="margin:10px 0 0 0;">
			<tr>
				<td colspan="3">Current Quantity = <strong><span id="currentQuantity">0</span></strong></td>
			</tr>
			<tr>
				<td>Quantity</td>
				<td>:</td>
				<td><input type="text" id="quantity" name="quantity" class="textbox2" style="width: 152px;"/></td>
			</tr>
			<tr valign="top">
				<td>Remarks</td>
				<td>:</td>
				<td><textarea id="remarks" name="remarks" rows="2" cols="20"></textarea></td>
			</tr>
		</table>
	</div>
	
	<div id="QuantityLogDialog" title="Product Quantity Log" style="float: left; display: none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="5" style="margin:10px 0 0 0;" class="companiesTable">	
			<tr>
				<th width="140px">Date</th>
				<th width="80x">Transaction Type</th>
				<th width="80px">Input Quantity</th>
				<th width="80px">Available Quantity</th>
				<th width="150px">Remarks</th>
				<th width="120px">Updated By</th>
			</tr>	
		</table>
		<div style="overflow-y: scroll; overflow-x: hidden; height: 330px; margin:0 0 15px 0;">
			<table width="100%" border="0" cellspacing="0" cellpadding="5" class="companiesTable">	
				<c:set var="count" value="0" />
				<c:forEach items="${listOfLogs}" var="log">
					<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
						<c:set var="count" value="${count+1}" />
						<td width="140px"><fmt:formatDate pattern="MM-dd-yyyy - hh:mm:ss" value="${log.createdOn}"/></td>
						<td width="80x">${log.transactionType}</td>						
						<td width="80px" align="center">${log.quantity}</td>
						<td width="80px" align="center">${log.availableQuantity}</td>
						<td width="150px">${log.remarks}</td>
						<td width="115px">${log.updatedByUser.fullName}</td>
					</tr>
				</c:forEach>			
			</table>
		</div>
	</div>
	
	<div id="ImageDialog" title="Update Image Information" style="float: left; display: none;">
		<table style="margin:10px 0 0 0;">
			<tr>
				<td>
					<img src="" id="dialogimage"/>
					<br/>
					<div id="imageNameDiv"></div>
				</td>
			</tr>
			<tr>
				<td>Title</td>
			</tr>
			<tr>
				<td><input type="text" id="title" name="image_title_${img.id}"  class="textbox4" ></td>
			</tr>
			<tr>
				<td>Caption</td>
			</tr>
			<tr>											
				<td>
				<c:if test="${company.name eq 'boysen'}">
					<select id="caption" name="image_caption_${img.id}"  class="textbox4" style="width:125px;">
						<option value=""></option>
						<option value="thumbnail">Thumbnail</option>
						<option value="brochure">Brochure</option>
					</select>
				</c:if>
				<c:if test="${company.name ne 'boysen'}">
					<input type="text" id="caption" name="image_caption_${img.id}"  class="textbox4">
				</c:if>
				</td>
			</tr> 
			<tr>
				<td>Description</td>
			</tr>
			<tr>
				<td>
					<textarea class="textbox4"  id="description"></textarea>
				</td>
			</tr>
		</table>
	</div>
	
	<input type="hidden" id="imageId" value=""/>
	
	<div id="FileDialog" title="Update File Information" style="float: left; display: none;">
		<table style="margin:10px 0 0 0;">
			<tr>
				<td>
					<img src="" id="dialogfile"/>
					<br/>
					<div id="fileNameDiv"></div>
				</td>
			</tr>
			<tr>
				<td>Title</td>
			</tr>
			<tr>
				<td><input type="text" id="file_title" class="textbox4" ></td>
			</tr>
			<tr>
				<td>Caption</td>
			</tr>
			<tr>
				<td>										
					<input type="text" id="file_caption" class="textbox4">
				</td>
			</tr> 
			<tr>
				<td>Description</td>
			</tr>
			<tr>
				<td>
					<textarea class="textbox4"  id="file_description"></textarea>
				</td>
			</tr>
		</table>
	</div>

	<input type="hidden" id="fileId" value=""/>

	<div style="display:none">
		<c:set var="displayRemove" value="${true}"/>
		<c:set var="displayRepeatLabel" value="${false}"/>
		<table width="50%" id="doctorScheduleFormHiddenTableId">
			<%@include file="includes/scheduleForm.jsp"  %>
		</table>
	</div>
	
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			<c:if test="${ not empty machineListSelected }">
				<c:forEach items="${ machineListSelected }" var="machine">
					checkMachine('${ machine.categoryItemMachine.id }');
				</c:forEach>
			</c:if>
			
			<c:if test="${ not empty branchListSelected }">
				<c:forEach items="${ branchListSelected }" var="branch">
					checkBranch('${ branch.categoryItemBranch.id }');
				</c:forEach>
			</c:if>
			
		});
		
		function checkMachine(id) {
			$('#machine_' + id).prop('checked', true);
		}
		
		function checkBranch(id) {
			$('#branch_' + id).prop('checked', true);
		}
	
		<c:if test="${company.name eq 'neltex' and (group.name eq 'Store Locator' or group.name eq 'Products' or group.name eq 'Equote')}">
			$('#skuRow').css('display','none');
			$('#weightRow').css('display','none');
			$('#searchTagsRow').css('display','none');
			$('#validUntilRow').css('display','none');
			<c:if test="${group.name ne 'Products' and group.name ne 'Equote'}">
			$('#shortDescriptionRow').css('display','none');
			</c:if>
			<c:if test="${group.name eq 'Equote'}">
				$('#shortDescriptionRow').css('display','none');
				$('.sidenav').css('display','none');
			</c:if>
			$('#otherDetailsRow').css('display','none');
			$('#uploadImageReminder').css('display','none');
			$('#uploadFileReminder').css('display','none');
		</c:if>
		
		
		<c:if test="${company.id eq 393 and not empty politicianOtherFieldId}"> <%-- Politiker --%>
			$(document).ready(function(){
				$('#btnUpdatePoliticians').bind("click", function(){
					var str = "";
					$('#politiciansList option:selected').each(function(){
						str += $(this).text().trim()+"${PolitikerConstants.DELIMITER_POLITICIANS}";
					});
					$('[name="o${politicianOtherFieldId}"]').attr("value",str);
				});
			});
		</c:if>
		
	</script>
	
	<script type="text/javascript">
		<c:choose>
			
		
			<c:when test = "${company.name eq 'wendys' and group.name eq 'PROMOS'}">
											var treeData = [
												<c:forEach items = "${groupMap['stores'].categories}" var = "cat_1stlevel" varStatus = "stat1">
													<c:set var = "check_1stlevel" value = "${0}" />
													<c:set var = "check_2ndlevel" value = "${0}" />
													<c:set var = "check_3rdlevel" value = "${0}" />
													<c:set var = "check_4thlevel" value = "${0}" />
													<c:set var = "check_5thlevel" value = "${0}" />
													
													<c:if test = "${cat_1stlevel.parentCategory eq null}">
														{title: "${cat_1stlevel.name}", key:"${cat_1stlevel.name}_All" ,select: <c:choose>
																									<c:when test = "${fn:containsIgnoreCase(item.searchTags,';')}">
																										
																									
																										<c:set var = "tempVar1" value = "; ${cat_1stlevel.id};"/>
																										<c:set var = "tempVar2" value = "; ${cat_1stlevel.id};"/>
																										<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																										<c:choose>
																											<c:when test = "${fn:containsIgnoreCase(item.searchTags,tempVar1) or fn:containsIgnoreCase(item.searchTags,tempVar2) or fn:containsIgnoreCase(item.searchTags,tempVar3)}">
																												true
																												<c:set var = "check_1stlevel" value = "${1}" />
																											</c:when>
																											<c:otherwise>
																												false
																											</c:otherwise>
																										</c:choose>
																									</c:when>
																									<c:otherwise>
																										<c:choose>
																											<c:when test = "${fn:containsIgnoreCase(item.searchTags,cat_1stlevel.name)}">
																												true
																												<c:set var = "check_1stlevel" value = "${1}" />
																											</c:when>
																											<c:otherwise>
																												false
																											</c:otherwise>
																										</c:choose>
																									</c:otherwise>
																									
																								</c:choose>
															<c:choose>
																<c:when test = "${cat_1stlevel.childrenCategory[0] ne null}">
																	,children:
																	[ 
																		<c:forEach items="${cat_1stlevel.childrenCategory}"  var = "cat_2ndlevel" varStatus = "stat2">
																			{title: "${cat_2ndlevel.name}", key:"remove_${cat_2ndlevel.id}", select: <c:choose>
																														<c:when test = "${fn:containsIgnoreCase(item.searchTags,';')}">
																														<c:set var = "tempVar1" value = "; ${cat_2ndlevel.name};"/>
																														<c:set var = "tempVar2" value = "; ${cat_2ndlevel.name}"/>
																														<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																														<c:choose>
																															<c:when test = "${fn:containsIgnoreCase(item.searchTags,tempVar1) or fn:containsIgnoreCase(item.searchTags,tempVar2) or fn:containsIgnoreCase(item.searchTags,tempVar3)}">
																																true
																																<c:set var = "check_2ndlevel" value = "${1}" />
																															</c:when>
																															<c:otherwise>
																																
																																<c:choose>
																																	<c:when test = "${check_1stlevel eq 1}" >
																																		true
																																		<c:set var = "check_2ndlevel" value = "${1}" />
																																	</c:when>
																																	<c:otherwise>
																																		false
																																	</c:otherwise>
																																</c:choose>
																															</c:otherwise>
																														</c:choose>
																													</c:when>
																													<c:otherwise>
																														<c:choose>
																															<c:when test = "${fn:containsIgnoreCase(item.searchTags,cat_2ndlevel.name)}">
																																true
																																<c:set var = "check_2ndlevel" value = "${1}" />
																																	</c:when>
																																	<c:otherwise>
																																		<c:choose>
																																			<c:when test = "${check_1stlevel eq 1}" >
																																				true
																																				<c:set var = "check_2ndlevel" value = "${1}" />
																																			</c:when>
																																			<c:otherwise>
																																				false
																																			</c:otherwise>
																																		</c:choose>
																															</c:otherwise>
																														</c:choose>
																													</c:otherwise>
																													
																												</c:choose>
																	
																	
																	
																	
																					<c:choose>
																						<c:when test = "${cat_2ndlevel.childrenCategory[0] ne null}">
																							,children:
																							[
																								<c:forEach items = "${cat_2ndlevel.childrenCategory}" var = "cat_3rdlevel" varStatus = "stat3">
																									{title: "${cat_3rdlevel.name}", key:"remove_${cat_3rdlevel.id}",select: <c:choose>
																																				<c:when test = "${fn:containsIgnoreCase(item.searchTags,';')}">
																																				<c:set var = "tempVar1" value = "; ${cat_3rdlevel.name};"/>
																																				<c:set var = "tempVar2" value = "; ${cat_3rdlevel.name}"/>
																																				<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																																				<c:choose>
																																					<c:when test = "${fn:containsIgnoreCase(item.searchTags,tempVar1) or fn:containsIgnoreCase(item.searchTags,tempVar2) or fn:containsIgnoreCase(item.searchTags,tempVar3)}">
																																						true
																																						<c:set var = "check_3rdlevel" value = "${1}" />
																																					</c:when>
																																					<c:otherwise>
																																						
																																					<c:choose>
																																						<c:when test = "${check_2ndlevel eq 1}" >
																																							true
																																							<c:set var = "check_3rdlevel" value = "${1}" />
																																						</c:when>
																																						<c:otherwise>
																																							false
																																						</c:otherwise>
																																					</c:choose>
																																						
																																					</c:otherwise>
																																				</c:choose>
																																			</c:when>
																																			<c:otherwise>
																																				<c:choose>
																																					<c:when test = "${fn:containsIgnoreCase(item.searchTags,cat_3rdlevel.name)}">
																																						true
																																						<c:set var = "check_3rdlevel" value = "${1}" />
																																					</c:when>
																																					<c:otherwise>
																																						
																																						<c:choose>
																																							<c:when test = "${check_2ndlevel eq 1}" >
																																								true
																																								<c:set var = "check_3rdlevel" value = "${1}" />
																																							</c:when>
																																							<c:otherwise>
																																								false
																																							</c:otherwise>
																																						</c:choose>
																																						
																																					</c:otherwise>
																																				</c:choose>
																																			</c:otherwise>
																																			
																																		</c:choose>
																							
																							
																							
																							
																							
																										<c:choose>
																											<c:when test = "${cat_3rdlevel.childrenCategory[0] ne null}">
																												,children:
																												[
																													<c:forEach items = "${cat_3rdlevel.childrenCategory}" var = "cat_4thlevel" varStatus = "stat4">
																														{title: "${cat_4thlevel.name}", key:"remove_${cat_4thlevel.id}",select: <c:choose>
																																									<c:when test = "${fn:containsIgnoreCase(item.searchTags,';')}">
																																									<c:set var = "tempVar1" value = "; ${cat_4thlevel.name};"/>
																																									<c:set var = "tempVar2" value = "; ${cat_4thlevel.name}"/>
																																									<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																																									<c:choose>
																																										<c:when test = "${fn:containsIgnoreCase(item.searchTags,tempVar1) or fn:containsIgnoreCase(item.searchTags,tempVar2) or fn:containsIgnoreCase(item.searchTags,tempVar3)}">
																																											true
																																											<c:set var = "check_4thlevel" value = "${1}" />
																																										</c:when>
																																										<c:otherwise>
																																											
																																											<c:choose>
																																												<c:when test = "${check_3rdlevel eq 1}" >
																																													true
																																													<c:set var = "check_4thlevel" value = "${1}" />
																																												</c:when>
																																												<c:otherwise>
																																													false
																																												</c:otherwise>
																																											</c:choose>
																																											
																																										</c:otherwise>
																																									</c:choose>
																																								</c:when>
																																								<c:otherwise>
																																									<c:choose>
																																										<c:when test = "${fn:containsIgnoreCase(item.searchTags,cat_4thlevel.name)}">
																																											true
																																											<c:set var = "check_4thlevel" value = "${1}" />
																																										</c:when>
																																										<c:otherwise>
																																											
																																											<c:choose>
																																												<c:when test = "${check_3rdlevel eq 1}" >
																																													true
																																													<c:set var = "check_4thlevel" value = "${1}" />
																																												</c:when>
																																												<c:otherwise>
																																													false
																																												</c:otherwise>
																																											</c:choose>
																																											
																																										</c:otherwise>
																																									</c:choose>
																																								</c:otherwise>
																																								
																																							</c:choose>
																														
																														}
																														<c:if test = "${not stat4.last}">
																															,
																														</c:if>
																													</c:forEach>
																												]
																											</c:when>
																											<c:when test = "${cat_3rdlevel.enabledItems[0] ne null}">
																												,children:
																												[
																													<c:forEach items="${cat_3rdlevel.enabledItems}"  var = "cat_3rdlevel_item" varStatus = "stat3_item1">
																													{title: "${cat_3rdlevel_item.name} - <em>${cat_3rdlevel_item.sku}</em>", key:"${cat_3rdlevel_item.id}",select: <c:choose>
																																									<c:when test = "${fn:containsIgnoreCase(item.searchTags,';')}">
																																										<c:set var = "tempVar1" value = "; ${cat_3rdlevel_item.id};"/>
																																										<c:set var = "tempVar2" value = "; ${cat_3rdlevel_item.id};"/>
																																										<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																																										<c:choose>
																																											<c:when test = "${fn:containsIgnoreCase(item.searchTags,tempVar1) or fn:containsIgnoreCase(item.searchTags,tempVar2) or fn:containsIgnoreCase(item.searchTags,tempVar3)}">
																																												true
																																											</c:when>
																																											<c:otherwise>
																																												
																																												<c:choose>
																																													<c:when test = "${check_3rdlevel eq 1}" >
																																														true
																																														
																																													</c:when>
																																													<c:otherwise>
																																														false
																																													</c:otherwise>
																																												</c:choose>
																																												
																																											</c:otherwise>
																																										</c:choose>
																																									</c:when>
																																									<c:otherwise>
																																										<c:choose>
																																											<c:when test = "${fn:containsIgnoreCase(item.searchTags,cat_3rdlevel_item.id)}">
																																												true
																																											</c:when>
																																											<c:otherwise>
																																												
																																												<c:choose>
																																													<c:when test = "${check_3rdlevel eq 1}" >
																																														true
																																														
																																													</c:when>
																																													<c:otherwise>
																																														false
																																													</c:otherwise>
																																												</c:choose>
																																												
																																											</c:otherwise>
																																										</c:choose>
																																									</c:otherwise>
																																									
																																								</c:choose>
																													}
																													<c:if test = "${not stat3_item1.last}">
																														,
																													</c:if>
																													</c:forEach>
																													
																												]
																												
																											</c:when>
																											<c:otherwise>
																												
																											</c:otherwise>
																										</c:choose>
																										
																									}
																									<c:if test = "${not stat3.last}">
																										,
																									</c:if>
																								</c:forEach>
																							]
																						</c:when>
																						<c:when test = "${cat_2ndlevel.enabledItems[0] ne null}">
																							,children:
																							[
																								<c:forEach items="${cat_2ndlevel.enabledItems}"  var = "cat_2ndlevel_item" varStatus = "stat2_item1">
																								{title: "${cat_2ndlevel_item.name}", key:"${cat_2ndlevel_item.id}"}
																								<c:if test = "${not stat2_item1.last}">
																									,
																								</c:if>
																								</c:forEach>
																							]
																						</c:when>
																						<c:otherwise>
																							
																						</c:otherwise>
																					</c:choose>
																			}
																			<c:if test = "${not stat2.last}">
																				,
																			</c:if>
																		</c:forEach>
																	]
																</c:when>
																<c:when test = "${cat_1stlevel.enabledItems[0] ne null}">
																	,children:
																	[ 
																		<c:forEach items="${cat_1stlevel.enabledItems}"  var = "cat_1stlevel_item" varStatus = "stat_item1">
																			{title: "${cat_1stlevel_item.name}", key:"${cat_1stlevel_item.id}"}
																			<c:if test = "${not stat_item1.last}">
																				,
																			</c:if>
																		</c:forEach>
																	]
																</c:when>
																<c:otherwise>
																
																</c:otherwise>
															</c:choose>
														}
														<c:if test = "${not stat1.last}">
															,
														</c:if>
													</c:if>
												</c:forEach>
											];
			</c:when>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<c:when test = "${company.name eq 'politiker' and group.name eq 'bills'}">
					var treeData = [
						<c:forEach items = "${groupMap['bills'].categories}" var = "cat_1stlevel" varStatus = "stat1">
							<c:set var = "check_1stlevel" value = "${0}" />
							<c:set var = "check_2ndlevel" value = "${0}" />
							<c:set var = "check_3rdlevel" value = "${0}" />
							<c:set var = "check_4thlevel" value = "${0}" />
							<c:set var = "check_5thlevel" value = "${0}" />
							
							<c:if test = "${cat_1stlevel.parentCategory eq null}">
								{title: "${cat_1stlevel.name}",expand: true, key:"remove_${cat_1stlevel.name}" ,select: <c:choose>
																			<c:when test = "${fn:containsIgnoreCase(otherFields[694],';')}">
																				
																			
																				<c:set var = "tempVar1" value = "; ${cat_1stlevel.id};"/>
																				<c:set var = "tempVar2" value = "; ${cat_1stlevel.id};"/>
																				<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																				<c:choose>
																					<c:when test = "${fn:containsIgnoreCase(otherFields[694],tempVar1) or fn:containsIgnoreCase(otherFields[694],tempVar2) or fn:containsIgnoreCase(otherFields[694],tempVar3)}">
																						true
																						<c:set var = "check_1stlevel" value = "${1}" />
																					</c:when>
																					<c:otherwise>
																						false
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test = "${fn:containsIgnoreCase(otherFields[694],cat_1stlevel.id)}">
																						true
																						<c:set var = "check_1stlevel" value = "${1}" />
																					</c:when>
																					<c:otherwise>
																						false
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																			
																		</c:choose>
									<c:choose>
										<c:when test = "${cat_1stlevel.childrenCategory[0] ne null}">
											,children:
											[ 
												<c:forEach items="${cat_1stlevel.childrenCategory}"  var = "cat_2ndlevel" varStatus = "stat2">
													{title: "${cat_2ndlevel.name}", key:"${cat_2ndlevel.id}", select: <c:choose>
																								<c:when test = "${fn:containsIgnoreCase(otherFields[694],';')}">
																								<c:set var = "tempVar1" value = "; ${cat_2ndlevel.id};"/>
																								<c:set var = "tempVar2" value = "; ${cat_2ndlevel.id}"/>
																								<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																								<c:choose>
																									<c:when test = "${fn:containsIgnoreCase(otherFields[694],tempVar1) or fn:containsIgnoreCase(otherFields[694],tempVar2) or fn:containsIgnoreCase(otherFields[694],tempVar3)}">
																										true
																										<c:set var = "check_2ndlevel" value = "${1}" />
																									</c:when>
																									<c:otherwise>
																										
																										<c:choose>
																											<c:when test = "${check_1stlevel eq 1}" >
																												true
																												<c:set var = "check_2ndlevel" value = "${1}" />
																											</c:when>
																											<c:otherwise>
																												false
																											</c:otherwise>
																										</c:choose>
																									</c:otherwise>
																								</c:choose>
																							</c:when>
																							<c:otherwise>
																								<c:choose>
																									<c:when test = "${fn:containsIgnoreCase(otherFields[694],cat_2ndlevel.id)}">
																										true
																										<c:set var = "check_2ndlevel" value = "${1}" />
																											</c:when>
																											<c:otherwise>
																												<c:choose>
																													<c:when test = "${check_1stlevel eq 1}" >
																														true
																														<c:set var = "check_2ndlevel" value = "${1}" />
																													</c:when>
																													<c:otherwise>
																														false
																													</c:otherwise>
																												</c:choose>
																									</c:otherwise>
																								</c:choose>
																							</c:otherwise>
																							
																						</c:choose>
											
											
											
											
															<c:choose>
																<c:when test = "${cat_2ndlevel.childrenCategory[0] ne null}">
																	,children:
																	[
																		<c:forEach items = "${cat_2ndlevel.childrenCategory}" var = "cat_3rdlevel" varStatus = "stat3">
																			{title: "${cat_3rdlevel.name}", key:"remove_${cat_3rdlevel.id}",select: <c:choose>
																														<c:when test = "${fn:containsIgnoreCase(otherFields[694],';')}">
																														<c:set var = "tempVar1" value = "; ${cat_3rdlevel.id};"/>
																														<c:set var = "tempVar2" value = "; ${cat_3rdlevel.id}"/>
																														<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																														<c:choose>
																															<c:when test = "${fn:containsIgnoreCase(otherFields[694],tempVar1) or fn:containsIgnoreCase(otherFields[694],tempVar2) or fn:containsIgnoreCase(otherFields[694],tempVar3)}">
																																true
																																<c:set var = "check_3rdlevel" value = "${1}" />
																															</c:when>
																															<c:otherwise>
																																
																															<c:choose>
																																<c:when test = "${check_2ndlevel eq 1}" >
																																	true
																																	<c:set var = "check_3rdlevel" value = "${1}" />
																																</c:when>
																																<c:otherwise>
																																	false
																																</c:otherwise>
																															</c:choose>
																																
																															</c:otherwise>
																														</c:choose>
																													</c:when>
																													<c:otherwise>
																														<c:choose>
																															<c:when test = "${fn:containsIgnoreCase(otherFields[694],cat_3rdlevel.id)}">
																																true
																																<c:set var = "check_3rdlevel" value = "${1}" />
																															</c:when>
																															<c:otherwise>
																																
																																<c:choose>
																																	<c:when test = "${check_2ndlevel eq 1}" >
																																		true
																																		<c:set var = "check_3rdlevel" value = "${1}" />
																																	</c:when>
																																	<c:otherwise>
																																		false
																																	</c:otherwise>
																																</c:choose>
																																
																															</c:otherwise>
																														</c:choose>
																													</c:otherwise>
																													
																												</c:choose>
																	
																	
																	
																	
																	
																				<c:choose>
																					<c:when test = "${cat_3rdlevel.childrenCategory[0] ne null}">
																						,children:
																						[
																							<c:forEach items = "${cat_3rdlevel.childrenCategory}" var = "cat_4thlevel" varStatus = "stat4">
																								{title: "${cat_4thlevel.name}", key:"remove_${cat_4thlevel.id}",select: <c:choose>
																																			<c:when test = "${fn:containsIgnoreCase(otherFields[694],';')}">
																																			<c:set var = "tempVar1" value = "; ${cat_4thlevel.id};"/>
																																			<c:set var = "tempVar2" value = "; ${cat_4thlevel.id}"/>
																																			<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																																			<c:choose>
																																				<c:when test = "${fn:containsIgnoreCase(otherFields[694],tempVar1) or fn:containsIgnoreCase(otherFields[694],tempVar2) or fn:containsIgnoreCase(otherFields[694],tempVar3)}">
																																					true
																																					<c:set var = "check_4thlevel" value = "${1}" />
																																				</c:when>
																																				<c:otherwise>
																																					
																																					<c:choose>
																																						<c:when test = "${check_3rdlevel eq 1}" >
																																							true
																																							<c:set var = "check_4thlevel" value = "${1}" />
																																						</c:when>
																																						<c:otherwise>
																																							false
																																						</c:otherwise>
																																					</c:choose>
																																					
																																				</c:otherwise>
																																			</c:choose>
																																		</c:when>
																																		<c:otherwise>
																																			<c:choose>
																																				<c:when test = "${fn:containsIgnoreCase(otherFields[694],cat_4thlevel.id)}">
																																					true
																																					<c:set var = "check_4thlevel" value = "${1}" />
																																				</c:when>
																																				<c:otherwise>
																																					
																																					<c:choose>
																																						<c:when test = "${check_3rdlevel eq 1}" >
																																							true
																																							<c:set var = "check_4thlevel" value = "${1}" />
																																						</c:when>
																																						<c:otherwise>
																																							false
																																						</c:otherwise>
																																					</c:choose>
																																					
																																				</c:otherwise>
																																			</c:choose>
																																		</c:otherwise>
																																		
																																	</c:choose>
																								
																								}
																								<c:if test = "${not stat4.last}">
																									,
																								</c:if>
																							</c:forEach>
																						]
																					</c:when>
																					<%--	
																					<c:when test = "${cat_3rdlevel.enabledItems[0] ne null}">
																						,children:
																						[
																							<c:forEach items="${cat_3rdlevel.enabledItems}"  var = "cat_3rdlevel_item" varStatus = "stat3_item1">
																							{title: "${cat_3rdlevel_item.name} - <em>${cat_3rdlevel_item.sku}</em>", key:"${cat_3rdlevel_item.id}",select: <c:choose>
																																			<c:when test = "${fn:containsIgnoreCase(otherFields[694],';')}">
																																				<c:set var = "tempVar1" value = "; ${cat_3rdlevel_item.id};"/>
																																				<c:set var = "tempVar2" value = "; ${cat_3rdlevel_item.id};"/>
																																				<c:set var = "tempVar3" value = "; ${cat_1stlevel.name}_All;"/>
																																				<c:choose>
																																					<c:when test = "${fn:containsIgnoreCase(otherFields[694],tempVar1) or fn:containsIgnoreCase(otherFields[694],tempVar2) or fn:containsIgnoreCase(otherFields[694],tempVar3)}">
																																						true
																																					</c:when>
																																					<c:otherwise>
																																						
																																						<c:choose>
																																							<c:when test = "${check_3rdlevel eq 1}" >
																																								true
																																								
																																							</c:when>
																																							<c:otherwise>
																																								false
																																							</c:otherwise>
																																						</c:choose>
																																						
																																					</c:otherwise>
																																				</c:choose>
																																			</c:when>
																																			<c:otherwise>
																																				<c:choose>
																																					<c:when test = "${fn:containsIgnoreCase(otherFields[694],cat_3rdlevel_item.id)}">
																																						true
																																					</c:when>
																																					<c:otherwise>
																																						
																																						<c:choose>
																																							<c:when test = "${check_3rdlevel eq 1}" >
																																								true
																																								
																																							</c:when>
																																							<c:otherwise>
																																								false
																																							</c:otherwise>
																																						</c:choose>
																																						
																																					</c:otherwise>
																																				</c:choose>
																																			</c:otherwise>
																																			
																																		</c:choose>
																							}
																							<c:if test = "${not stat3_item1.last}">
																								,
																							</c:if>
																							</c:forEach>
																							
																						]
																						
																					</c:when>
																					--%>
																					<c:otherwise>
																						
																					</c:otherwise>
																				</c:choose>
																				
																			}
																			<c:if test = "${not stat3.last}">
																				,
																			</c:if>
																		</c:forEach>
																	]
																</c:when>
																<%--
																<c:when test = "${cat_2ndlevel.enabledItems[0] ne null}">
																	,children:
																	[
																		<c:forEach items="${cat_2ndlevel.enabledItems}"  var = "cat_2ndlevel_item" varStatus = "stat2_item1">
																		{title: "${cat_2ndlevel_item.name}", key:"${cat_2ndlevel_item.id}"}
																		<c:if test = "${not stat2_item1.last}">
																			,
																		</c:if>
																		</c:forEach>
																	]
																</c:when>
																--%>
																<c:otherwise>
																	
																</c:otherwise>
															</c:choose>
													}
													<c:if test = "${not stat2.last}">
														,
													</c:if>
												</c:forEach>
											]
										</c:when>
										<c:when test = "${cat_1stlevel.enabledItems[0] ne null}">
											,children:
											[ 
												<c:forEach items="${cat_1stlevel.enabledItems}"  var = "cat_1stlevel_item" varStatus = "stat_item1">
													{title: "${cat_1stlevel_item.name}", key:"${cat_1stlevel_item.id}"}
													<c:if test = "${not stat_item1.last}">
														,
													</c:if>
												</c:forEach>
											]
										</c:when>
										<c:otherwise>
										
										</c:otherwise>
									</c:choose>
								}
								<c:if test = "${not stat1.last}">
									,
								</c:if>
							</c:if>
						</c:forEach>
					];
					
					/////////////////////////////////////////////////////////////
					//////////////////////////////////////////////////////////////
					/////////////////////////////////////////////////////////
					/////////////////////////////////////////////////////////////
					//////////////////////////////////////////////////////////////
					var treeData4 = [
						<c:forEach items = "${groupItemsMap['politicians']}" var = "pol" varStatus = "stat1">
							
								{title: "${pol.name}",expand: true, key:"${pol.name}" 
									,select: 
									<c:choose>
										<c:when test = "${fn:containsIgnoreCase(otherFields[696],';')}">
											<c:set var = "tempVar1" value = "${pol.name};"/>
											<c:choose>
												<c:when test = "${fn:containsIgnoreCase(otherFields[696],tempVar1)}">
													true
												</c:when>
												<c:otherwise>
													false
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											false
										</c:otherwise>
									</c:choose>
								}
								<c:if test = "${not stat1.last}">
									,
								</c:if>
						</c:forEach>
					];
		</c:when>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<c:otherwise>
			
			</c:otherwise>
		</c:choose>
											$(document).ready(function(){
										
													
												
												
												
												
													
													var element = document.getElementById("phoneticlong");
													if(element) { 
														dragsort.makeListSortable(document.getElementById("phoneticlong"),
																	verticalOnly, saveOrder)
													}

													var element = document.getElementById("filesList");
													if(element) { 
														dragsort.makeListSortable(document.getElementById("filesList"),
																	verticalOnly, saveOrder)
													}
													
												
												<%--<c:if test="${(company.name eq 'wendys' and fn:containsIgnoreCase(otherField.name, 'branch') and not fn:containsIgnoreCase(otherField.name, 'es')) or (company.name eq 'politiker' and fn:containsIgnoreCase(otherField.name, 'tag issues'))}">--%> 
												<c:if test="${company.name ne 'gurkkatest'}">
												if(treeData != null){
													$("#tree3").dynatree({
														checkbox: true,
														selectMode: 3,
														children: treeData,
														onSelect: function(select, node) {
															// Get a list of all selected nodes, and convert to a key array:
															var selKeys = $.map(node.tree.getSelectedNodes(), function(node){
																return node.data.key;
															});
															$(".selectedbranches").attr('value',"; "+selKeys.join("; ")+"; ");
															<c:if test = "${not fn:containsIgnoreCase(company.name,'politiker')}">
															$("#search_tags").attr('value',"; "+selKeys.join("; ")+"; ");
															</c:if>
															/*----------------------------------------------------------------------------*/
															/*----------------------------------------------------------------------------*/
															var str_keys = $(".selectedbranches").val();
															var arr_keys = str_keys.split('; ');
															var temp_str = "";
															var temp_all = "";
															var isAll = false;
															var JSON_temp_str = "";
															for(var i = 0; i < arr_keys.length; i++)
															{
																if(arr_keys[i]!="" && arr_keys[i].toLowerCase().indexOf("remove_") < 0 )
																{
																	
																	temp_str = temp_str + arr_keys[i].trim()+"; ";
																	
																	if(arr_keys[i].trim().toLowerCase().indexOf('_all')>=0){
																		isAll = true;
																		temp_all = temp_all + arr_keys[i].trim()+"; ";
																	}
																}
																 
															}
															if(temp_str.length>0){
																if(isAll){
																	$(".selectedbranches").attr('value',"; "+temp_all);
																	<c:if test = "${not fn:containsIgnoreCase(company.name,'politiker')}">
																	$("#search_tags").attr('value',"; "+temp_all);
																	</c:if>
																}
																else{
																	$(".selectedbranches").attr('value',"; "+temp_str);
																	<c:if test = "${not fn:containsIgnoreCase(company.name,'politiker')}">
																		$("#search_tags").attr('value',"; "+temp_str);
																	</c:if>
																}
																
															}
															else{
																
																$(".selectedbranches").attr('value',temp_str);
																<c:if test = "${not fn:containsIgnoreCase(company.name,'politiker')}">
																	$("#search_tags").attr('value',temp_str);
																</c:if>
															}
															/*----------------------------------------------------------------------------*/
															/*----------------------------------------------------------------------------*/
															
															// Get a list of all selected TOP nodes
															var selRootNodes = node.tree.getSelectedNodes(true);
															// ... and convert to a key array:
															var selRootKeys = $.map(selRootNodes, function(node){
																return node.data.key;
															});
															$("#echoSelectionRootKeys3").text(selRootKeys.join(", "));
															$("#selectedbranches_old").attr('value',selRootNodes.join("; ")+";");
															
														},
														onDblClick: function(node, event) {
															node.toggleSelect();
														},
														onKeydown: function(node, event) {
															if( event.which == 32 ) {
																node.toggleSelect();
																return false;
															}
														},
														// The following options are only required, if we have more than one tree on one page:
															initId: "treeData",
														cookieId: "dynatree-Cb3",
														idPrefix: "dynatree-Cb3-"
													});
												}
												</c:if>
												<%--</c:if>--%>
												<%--------------------------------------------------------%>
												<%--------------------------------------------------------%>
												<%--------------------------------------------------------%>
												<%--------------------------------------------------------%>
												<c:if test="${company.name ne 'gurkkatest'}">
												$("#tree4").dynatree({
													checkbox: true,
													selectMode: 3,
													children: treeData4,
													onSelect: function(select, node) {
														// Get a list of all selected nodes, and convert to a key array:
														var selKeys = $.map(node.tree.getSelectedNodes(), function(node){
															return node.data.key;
														});
														$(".selectedpoliticians").attr('value',";"+selKeys.join(";")+";");
														
														/*----------------------------------------------------------------------------*/
														/*----------------------------------------------------------------------------*/
														var str_keys = $(".selectedpoliticians").val();
														var arr_keys = str_keys.split(';');
														var temp_str = "";
														var temp_all = "";
														var isAll = false;
														var JSON_temp_str = "";
														for(var i = 0; i < arr_keys.length; i++)
														{
															if(arr_keys[i]!="" && arr_keys[i].toLowerCase().indexOf("remove_") < 0 )
															{
																
																temp_str = temp_str + arr_keys[i].trim()+";";
																
																if(arr_keys[i].trim().toLowerCase().indexOf('_all')>=0){
																	isAll = true;
																	temp_all = temp_all + arr_keys[i].trim()+";";
																}
															}
															 
														}
														if(temp_str.length>0){
															if(isAll){
																$(".selectedpoliticians").attr('value',";"+temp_all);
															}
															else{
																$(".selectedpoliticians").attr('value',";"+temp_str);
															}
															
														}
														else{
															
															$(".selectedpoliticians").attr('value',temp_str);
														}
														/*----------------------------------------------------------------------------*/
														/*----------------------------------------------------------------------------*/
														
														// Get a list of all selected TOP nodes
														var selRootNodes = node.tree.getSelectedNodes(true);
														// ... and convert to a key array:
														var selRootKeys = $.map(selRootNodes, function(node){
															return node.data.key;
														});
														$("#echoSelectionRootKeys3").text(selRootKeys.join(", "));
														
														
													},
													onDblClick: function(node, event) {
														node.toggleSelect();
													},
													onKeydown: function(node, event) {
														if( event.which == 32 ) {
															node.toggleSelect();
															return false;
														}
													},
													// The following options are only required, if we have more than one tree on one page:
														initId: "treeData4",
													cookieId: "dynatree-Cb4",
													idPrefix: "dynatree-Cb4-"
												});
												</c:if>
												<%--------------------------------------------------------%>
												<%--------------------------------------------------------%>
												<%--------------------------------------------------------%>
												<%--------------------------------------------------------%>
												<%--------------------------------------------------------%>
												
										
												$("#btnToggleSelect").click(function(){
													$("#tree2").dynatree("getRoot").visit(function(node){
														node.toggleSelect();
													});
													return false;
												});
												$("#btnDeselectAll").click(function(){
													$("#tree2").dynatree("getRoot").visit(function(node){
														node.select(false);
													});
													return false;
												});
												$("#btnSelectAll").click(function(){
													$("#tree2").dynatree("getRoot").visit(function(node){
														node.select(true);
													});
													return false;
												});
												<!-- Start_Exclude: This block is not part of the sample code -->
												$("#skinCombo")
												.val(0) // set state to prevent caching
												.change(function(){
													var href = "../src/"
														+ $(this).val()
														+ "/ui.dynatree.css"
														+ "?reload=" + new Date().getTime();
													$("#skinSheet").attr("href", href);
												});
												<!-- End_Exclude -->
											});
										
		</script>
										
										<script type = "text/javascript" >
										
											
											
										
											function handleCheckBoxDrinksChanges(cmbBox){
												var price_ = $(cmbBox).attr('title');
												if($(cmbBox).prop('checked')){
													$(cmbBox).attr('value',price_);
												}
												else{
													$(cmbBox).attr('value','-1');
												}
											}
											
											function handleCheckBoxChanges(cmbBox){
												
												if($(cmbBox).prop('checked')){
													$(cmbBox).attr('value','1');
												}
												else{
													$(cmbBox).attr('value','');
												}
											}
											
											function handleComboBoxChanges(cmbBox) {
												var klazneym = $(cmbBox).attr('value');
												klazneym = "." + klazneym;
												$('.all').hide();
												$(klazneym).show();
												$('.selectedbranches').text($('.select_combo').find(':selected').attr('class'));
												uncheckall();
												
												put_remove_check();
												
//////////////////////////////////////////////////////////
												$("input[name=o789]").val('');
												$.each($('.c8469'), function() {
													if($(this).prop('checked')){
														$("input[name=o789]").val($("input[name=o789]").val()+$(this).val()+";");
														//str_temp = str_temp+$(this).attr('name')+", ";
														
													}
													
												});
/////////////////////////////					/////////////////////////////
												$("input[name=o790]").val('');
												$.each($('.c8467'), function() {
													if($(this).prop('checked')){
														$("input[name=o790]").val($("input[name=o790]").val()+$(this).val()+";");
														//str_temp = str_temp+$(this).attr('name')+", ";
														
													}
													
												});
					/////////					////////////////////
												$("input[name=o791]").val('');
												$.each($('.c8466'), function() {
													if($(this).prop('checked')){
														$("input[name=o791]").val($("input[name=o791]").val()+$(this).val()+";");
														//str_temp = str_temp+$(this).attr('name')+", ";
														
													}
													
												});
												//handleCheckBoxChanges(cmbBox);
												//$('.selectedbranches').text('');
											}
											
											function check_uncheck_boxes(check_box) {
												var chkbx = $('.combomeal_boxes');
												//var str_temp = "";
												$('.selectedbranches').text(';');
												$.each(chkbx, function() {
													if($(this).prop('checked')){
														$('.selectedbranches').text($('.selectedbranches').text()+$(this).val()+";");
														//str_temp = str_temp+$(this).attr('name')+", ";
														
													}
													
												});
												
												//////////////////////////////////////////////////////////
												$("input[name=o789]").val('');
												$.each($('.c8469'), function() {
													if($(this).prop('checked')){
														$("input[name=o789]").val($("input[name=o789]").val()+$(this).val()+";");
														//str_temp = str_temp+$(this).attr('name')+", ";
														
													}
													
												});
/////////////////////////////					/////////////////////////////
												$("input[name=o790]").val('');
												$.each($('.c8467'), function() {
													if($(this).prop('checked')){
														$("input[name=o790]").val($("input[name=o790]").val()+$(this).val()+";");
														//str_temp = str_temp+$(this).attr('name')+", ";
														
													}
													
												});
					/////////					////////////////////
												$("input[name=o791]").val('');
												$.each($('.c8466'), function() {
													if($(this).prop('checked')){
														$("input[name=o791]").val($("input[name=o791]").val()+$(this).val()+";");
														//str_temp = str_temp+$(this).attr('name')+", ";
														
													}
													
												});
												//str_temp = str_temp.substring(0,str_temp.length-2);
												//$('.selectedbranches_definition').text('{'+str_temp+'}');
												$('.selectedbranches').text(";"+$('.select_combo').val()+$('.selectedbranches').text());
											}
											
											function uncheckall() {
												var chkbx = $('.combomeal_boxes');
												$.each(chkbx, function() {
													$(this).attr('checked',false);
												});
												$("input[name=o789]").val('');
												$("input[name=o790]").val('');
												$("input[name=o791]").val('');
												
												
											}
											
											function put_remove_check()
											{
												var chkbx = $('.combomeal_boxes');
												$.each(chkbx, function() {
													var selectedbranches = $('.selectedbranches').text();
													if(selectedbranches.indexOf($(this).val()) >= 0){
														$(this).attr('checked',true);
													}
													else
													{
														$(this).attr('checked',false);
													}
												});
											}
											window.onload = function() {
												var klazneym = "."+$('.select_combo').find(':selected').attr('value');
												$('.all').hide();
												
												$(klazneym).show();
											}
											
											function removeParentDiv(page_Object_){
												var relneym_ = $(page_Object_).attr('relneym');
												var relparent_ = $(page_Object_).attr('relparent');
												$(page_Object_).closest("div").remove();
												updateproducttypetag(relneym_, relparent_);
											}
										
											function addNewProductTag(){
												$('.td_otherField_producttag').append(
														'<div><select testonly="3" id="other_field" name="o${otherField.id }" class="w385">'+
															'<c:forEach items="${otherField.otherFieldValueList}" var="option">'+
																'<option value="${option.value}" >'+
																	'${option.value}'+
																'</option>'+
															'</c:forEach>'+
														'</select>'+
														'&nbsp;<a style="cursor:pointer;" onclick="removeParentDiv();" class="addProductTypeTag">Remove</a>'+
														'</div><br/><br/>'
												);
											}
											
											function addNewProductType(){
												$('.td_otherField_producttype').append(
														'<div><select testonly="3" id="other_field" name="o${otherField.id }" class="w385">'+
															'<c:forEach items="${otherField.otherFieldValueList}" var="option">'+
																'<option value="${option.value}" >'+
																	'${option.value}'+
																'</option>'+
															'</c:forEach>'+
														'</select>'+
														'&nbsp;<a style="cursor:pointer;" onclick="removeParentDiv();" class="addProductTypeTag">Remove</a>'+
														'</div><br/><br/>'
												);
											}
											
											function addnewtypetag(neym, parentOF_){
												
												if(neym=="type"){
													var res_ = "";
													$('.gurkka_producttype').each(function(e){
														res_ += $(this).val()+";";
													});
													$('.'+parentOF_).val(res_);
													$('.td_otherField_producttype').append(
															'<div><select testonly="3" onchange="updateproducttypetag(\''+neym+'\', \''+parentOF_+'\');" class="w385 gurkka_producttype">'+
																'<c:forEach items="${item.categoryItemOtherFieldMap[GurkkaConstants.PROMOBASKET_OTHERFIELD_PRODUCT_TYPE_NAME].otherField.otherFieldValueList}" var="ofit">'+
																	'<option value="${ofit.value}" >'+
																		'${ofit.value}'+
																	'</option>'+
																'</c:forEach>'+
															'</select>'+
															'&nbsp;<a style="cursor:pointer;" relneym="'+neym+'" relparent="'+parentOF_+'" onclick="removeParentDiv(this);" class="addProductTypeTag">Remove</a>'+
															'<br/>&nbsp;</div>'
													);
												}
												else{
													var res_ = "";
													$('.gurkka_producttag').each(function(e){
														res_ += $(this).val()+";";
													});
													$('.'+parentOF_).val(res_);
													$('.td_otherField_producttag').append(
															'<div><select testonly="3" onchange="updateproducttypetag(\''+neym+'\', \''+parentOF_+'\');" class="w385 gurkka_producttag">'+
																'<c:forEach items="${item.categoryItemOtherFieldMap[GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TAG_NAME].otherField.otherFieldValueList}" var="ofit">'+
																	'<option value="${ofit.value}" >'+
																		'${ofit.value}'+
																	'</option>'+
																'</c:forEach>'+
															'</select>'+
															'&nbsp;<a style="cursor:pointer;" relneym="'+neym+'" relparent="'+parentOF_+'" onclick="removeParentDiv(this);" class="addProductTypeTag">Remove</a>'+
															'<br/>&nbsp;</div>'
													);
												}
											}
											
											function updateproducttypetag(neym, parentOF_){
												if(neym=="type"){
													var res_ = "";
													$('.gurkka_producttype').each(function(e){
														res_ += $(this).val()+";";
													});
													$('.'+parentOF_).val(res_);
												}
												else{
													var res_ = "";
													$('.gurkka_producttag').each(function(e){
														res_ += $(this).val()+";";
													});
													$('.'+parentOF_).val(res_);
												}
											}
											
											
											
											
											function changeParentAgian(){
												if($('#agianParent').val() != 17478){
													$('#item_sku').attr('disabled','disabled');	
												}else{
													$('#item_sku').removeAttr('disabled');	
												}
											}
											
											function changeParentHpds(){
												var counter = 1;
												if($('#hpdsParent').find(':selected').attr('data-value') == 'Imaging Test Procedures'){
													<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
														<c:choose>															
															<c:when test="${company.name eq 'hpds' and (															
	
																otherField.name eq 'SPECIAL INSTRUCTIONS'  or
																otherField.name eq 'OTHER NAME'  or
																otherField.name eq 'TEST'  or															
																otherField.name eq 'OTHER TEST REQUEST NAME' or
																otherField.name eq 'INTENDED USE' or
																otherField.name eq 'INSTRUCTIONS TO HP STAFFS' or
																otherField.name eq 'SPECIAL INSTRUCTIONS /PATIENT PREPARATIONS' or
																otherField.name eq 'SPECIAL INSTRUCTIONS/PATIENT PREPARATIONS' or
																
																otherField.name eq 'PATIENT PREPARATIONS' or
																
																otherField.name eq 'TAT/RELEASING OF RESULTS' or
																otherField.name eq 'REFERENCE INTERVAL/RESULT INTERPRETATION' or
																otherField.name eq 'FREQUENTLY ASKED QUESTIONS (FAQS)' or
																otherField.name eq 'BRANCH'  or
																otherField.name eq 'MACHINE' or 
																otherField.name eq 'SCHEDULE'
																)}">	
																$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
															</c:when>
															<c:otherwise>
																$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
															</c:otherwise>
														</c:choose>													
													</c:forEach>			
												}else{
													<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
													<c:choose>															
														<c:when test="${company.name eq 'hpds' and (															

															otherField.name eq 'BRANCH'  or
															otherField.name eq 'MACHINE' or
															otherField.name eq 'SCHEDULE'
															)}">	
															$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
														</c:when>
														<c:otherwise>															
															$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
														</c:otherwise>
													</c:choose>													
												</c:forEach>			
												}																																
											}
											
											
											function changeParentUrine(){
												var counter = 1;
												var selectedCategory = $('#hpdsParent').find(':selected').attr('data-value');
												if(selectedCategory == 'Urine and other Body Fluids'){
													
													<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
														<c:choose>															
															<c:when test="${company.name eq 'hpds' and group.name eq 'Lab File' and (															
																															
																otherField.name eq 'LAB FILE- SPECIMEN CONTAINER' or
																otherField.name eq 'LAB FILE- VOLUME REEQUIREMENT'
																)}">	
																//alert('${otherField.name}');
																$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
															</c:when>
															<c:otherwise>															
																$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
															</c:otherwise>
														</c:choose>													
													</c:forEach>			
												}else if(selectedCategory == 'IMMUNO' || selectedCategory == 'CHEMISTRY'){
													
													<c:forEach items="${group.otherFields}" var="otherField" varStatus="counter">
														<c:choose>															
															<c:when test="${company.name eq 'hpds' and group.name eq 'Lab File' and (															
																															
																otherField.name eq 'LAB FILE- MACHINE CODE' or
																otherField.name eq 'LAB FILE- MACHINES' or
																otherField.name eq 'LAB FILE- PRESERVATIVE' or
																otherField.name eq 'LAB FILE- SPECIMEN DILUTION' or
																otherField.name eq 'LAB FILE- CONTROL' or
																otherField.name eq 'LAB FILE- CALIBRATOR' or
																otherField.name eq 'LAB FILE- CAUSE OF REJECTIONS'
																
																)}">	
																//alert('${otherField.name}');
																$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().css('display','none');
															</c:when>
															<c:otherwise>															
																$('#itemFieldsContainer').find('#other_field${otherField.id}').parent().parent().removeAttr('style');
															</c:otherwise>
														</c:choose>													
													</c:forEach>			
												}
											}
										</script>
										
		
</body>
</html>