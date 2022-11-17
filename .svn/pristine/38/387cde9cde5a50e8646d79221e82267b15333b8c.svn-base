<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="pages" />
<c:set var="submenu" value="${multiPage.name}" />

<style type="text/css"><!--
	
	#phoneticlong li, #buttons li {
		margin-bottom: 0px;
		margin-top: 4px;
		margin-left: 10px;
		font-size: 12px;
	}

	
	//-->
</style>


<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRMultiPageAction.js'></script>
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
					verticalOnly, saveOrder)
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
		
		DWRMultiPageAction.saveNewOrder(items, back);
	}
	
	function back() {
		window.location = 'editmultipagechildlist.do?multipage_id=${multiPage.id}&evt=sortUpdated';
	}

</script>
<body >

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
	
				<div id="companyhome">
  				 
  					<table>
  						<tr>
  							<td width="50%" style="border: 0px;">
  							
  							<div style="float: left;"><h3>Sorting ${multiPage.name}</h3></div>
		  					<div style="float: right;">[<a href="javascript:;" onclick="save();">Save</a> | <A href="editmultipagechildlist.do?multipage_id=${multiPage.id}">Cancel</a>]</div>
		  					 
		  					<div style="clear: both;"></div>
		  					
		  					<div style="float: left;"><b>Drag and drop the item to set its sorting order</div>
		  					
		  					<div style="clear: both;"></div>
		  					
		  					<div>
		  					
			  					<table>
			  						<tr>
			  							<td style="border: 0px;">
				  							<ul id="phoneticlong" class="boxy">
												<c:forEach items="${multiPageItems}" var="i">
													<li itemID="${i.id}">${i.name}</li>
												</c:forEach>
											</ul>
										</td>
			  						</tr>
			  					</table>
		  					
		  					</div> 
  							
  							
  							</td>
  							<td style="border: 0px;"></td>
  							
  						</tr>
  					</table>
  					  					
				</div> 
				
				
		
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>