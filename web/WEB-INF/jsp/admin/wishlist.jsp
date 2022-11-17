<%@include file="includes/header.jsp"  %>

<link href="../admin/css/modal.css" rel="stylesheet" type="text/css" />

<script src="../admin/javascripts/prototype.js" type="text/javascript"></script>
<script src="../admin/javascripts/scriptaculous.js" type="text/javascript"></script>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRWishlistAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

<c:set var="menu" value="wishlist" />
<c:set var="pagingAction" value="wishlist.do" />

<head>
	<script type="text/javascript">
		function closeModal() {
			Effect.Fade('OverlayContainer');
		}

		function showWishlist(wishListID) {
			DWRWishlistAction.getWishlist(wishListID,{
				callback:function(messageContent){
					showMessageContent(messageContent);
				}
			}); 
		}
		function showMessageContent(messageContent) {
			currentOrderList = document.getElementById("ModalBox");
			currentOrderList.innerHTML = "<a href='#' onclick='closeModal()'"
				+ " style=\"float:  right;\">" 
				+ "<img src=\"images\/x.gif\"></a>"
				+ messageContent;
			Effect.Appear('OverlayContainer');
		}		
	</script>
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
    <div class="content">
	 	<s:actionmessage />
				<s:actionerror />
				
				
	  <div class="pageTitle">

	    <h1><strong>Wishlist</strong>: View Wish List</h1>
		
					
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>

	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
					<tr >
						<th > Creation Date </th>
						<th > ID </th>
						<th > Member Name </th>												
						<th > Item Name </th>
					</tr>
				<c:set var="count" value="0" />
					<!-- TABLE CONTENTS (wishlist) -->
					<s:iterator value="wishList">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
							<td>
								<s:property value="createdOn"/>
							</td>
							<td>
								<a href="#" onclick=showWishlist(${id});>
									<s:property value="id"/>
								</a>
							</td>
							<td>
								<s:property value="member.lastname"/>, <s:property value="member.firstname"/> 
							</td>							
							<td>
								<s:property value="item.name"/> 
							</td>
						</tr>
					</s:iterator>
	</table>
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->

</div>

	<div class="clear"></div>

  </div><!--//container-->
<!-- TODO populate message-->
	<div id="OverlayContainer" style="float: left; display: none;">
		<div id="ModalBox">
			<a href='#' onclick='closeModal()'>Close Me!</a>
		</div>
	</div>
</body>

</html>