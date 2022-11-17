<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="variations" />
<c:set var="submenu" value="variation_group_items" />

<body>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
				<div id="companyhome">
 				
 				<h3>
				
				Variation Group Items
				
				</h3>
 				
 				
 				<table width="100%" border="0">
					<tr>
						<td>
						
						<div style="float: right;"> 
							[<a href="#variationGroupItemForm">Add Variation Group Item</a>]
						</div>
						 
						</td>
					</tr>
				</table>
 				
 				<table> 
 					<tr class="headbox"> 
						<th>Name</th>
						<th>Variation Group</th>
						<th width="15%">Action</th> 
					</tr> 
					  
					<c:forEach items="${variations}" var="v">
					<tr>
						<td>${v.name}</td> 
						<td>${v.variationGroup.name}</td>
						<td>
						<a href="editVariation.do?variationId=${v.id }">Edit</a> 
						|  
						<a href="deleteVariation.do?variationId=${v.id }" onclick="return confirm('Do you really want to delete this?');">Delete</a>
						</td>
					</tr>
					</c:forEach>															
				</table> 
 				
 				<table width="100%" border="0">
					<tr>
						<td style="border: 0px;">
						
													
						</td>
					</tr>
				</table>
 				
 				<br><br>
 				
 				<a name="variationGroupItemForm"></a>
 				<c:set var="formAction" value="saveVariation.do" />
				<%@include file="variationForm.jsp" %>
 				
 				</div>
						
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>