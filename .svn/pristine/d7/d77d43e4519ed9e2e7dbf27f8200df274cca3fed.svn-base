<%@include file="includes/header.jsp"  %>


<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />


<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
	
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


<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRCategoryAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
   
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
		dragsort.makeListSortable(document.getElementById("phoneticlong"),
					verticalOnly, saveOrder);
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
	
	function save() {
		var items = ToolMan.junkdrawer().serializeList(document.getElementById('phoneticlong'));
		items = items.split("|");
		DWRCategoryAction.saveNewOrder(items, back);
	}
	
	function back() {
		window.location = 'sortCategories.do?group_id=${group.id}&evt=sortUpdated';
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

	    <h1><strong>Group</strong>: Sort categories</h1>
		
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
	
	<c:if test="${not empty categories}">
	<table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
		<tr>
			<th >Category Name</th> 
		</tr>							
	</table> 
	
		<ul id="phoneticlong">			
			<c:forEach items="${categories}" var="category">
				<li itemID="${category.id}" style="cursor: url(https://mail.google.com/mail/images/2/openhand.cur), default !important;" >${category.name}</li>
			</c:forEach>
		</ul>
		<ul>
			<li style="padding-left: 300px;">
				<input type="button" value="Save" onclick="save();" class="btnBlue" />&nbsp; <input type="button" value="Cancel" class="btnBlue" onclick="window.location='sortCategories.do?group_id=${group.id}'" />
			</li>
		</ul>	 		
	</c:if>
				
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->

</div>

	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>