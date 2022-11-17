<%@include file="includes/header.jsp"  %>

<c:choose>
	<c:when test="${company.id eq 66 }">
		<script type="text/javascript" src="/dwr/interface/DWRItemAction.js"></script>
		<script type="text/javascript" src="/dwr/interface/DWRCategoryAction.js"></script>
		<script type="text/javascript" src="/dwr/engine.js"></script>
	</c:when>
	<c:otherwise>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRItemAction.js'></script>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRCategoryAction.js'></script>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
	</c:otherwise>
</c:choose>
   
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
		<c:if test="${not empty category.childrenCategory}">
		dragsort.makeListSortable(document.getElementById("phoneticlong"),
					verticalOnly, saveOrder);
		</c:if>
		<c:if test="${not empty items}">
		dragsort.makeListSortable(document.getElementById("phoneticlong-items"),
				verticalOnly, saveItemOrder);
		</c:if>
	}
	
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
	
	function saveItemOrder(item) {
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
		var items = ToolMan.junkdrawer().serializeList(document.getElementById('phoneticlong-items'));
		items = items.split("|");
		DWRItemAction.saveNewOrder(items, back);
	}

	function save2() {
		var items = ToolMan.junkdrawer().serializeList(document.getElementById('phoneticlong'));
		items = items.split("|");
		DWRCategoryAction.saveNewOrder(items, back);
	}
	
	function back() {
		window.location = 'sortItems.do?group_id=${group.id}&category_id=${category.id}&evt=sortUpdated';
	}

</script>

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="items" />
<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
</script>

<div class="contentWrapper" id="contentWrapper">
    <div class="sortItemContent">
	 	<s:actionmessage />
			<s:actionerror />
				<c:if test="${param['evt'] != null}">
					<ul>
						<c:if test="${param['evt'] == 'sortUpdated'}">
							<li>&bull; Ordering was successfully updated.</li>
						</c:if>
					</ul>
				</c:if>
				
	  <div class="pageTitle">
	    <h1><strong>Sort Sub-Categories and Items (Drag and Arrange)</strong> - ${category.name } Category</h1>
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			<br /><br />
		<div class="clear"></div>
		<c:if test="${not empty category.childrenCategory}">
			<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
				<tr>
					<th>Sub-Category Name</th>
				</tr> 
			</table>
			<ul id="phoneticlong">			
				<c:forEach items="${category.childrenCategory}" var="i">
					<li itemID="${i.id}" style="cursor: url(https://mail.google.com/mail/images/2/openhand.cur), default !important;" >${i.name}</li>
				</c:forEach>
			</ul>
			<ul>
				<li style="padding-left: 300px;">
					<input type="button" value="Save" onclick="save2();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='sortItems.do?group_id=${group.id}&category_id=${category.id}'" />
				</li>
			</ul>
			<br /><br />
		</c:if>
		
		<c:if test="${not empty items}">
			<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
				<tr>
					<th>Item Name - SKU - Price</th>
				</tr> 
			</table>
			<ul id="phoneticlong-items">			
				<c:forEach items="${items}" var="i">
					<li itemID="${i.id}" style="cursor: url(https://mail.google.com/mail/images/2/openhand.cur), default !important;" >${i.name} - ${i.sku } - ${i.price }</li>
				</c:forEach>
			</ul>
			<ul>
				<li style="padding-left: 300px;">
					<input type="button" value="Save" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='sortItems.do?group_id=${group.id}&category_id=${category.id}'" />
				</li>
			</ul>
		</c:if>
	 
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>	
	</div><!--//content-->
</div>

	<div class="clear"></div>

  </div><!--//container-->
</body>

</html>