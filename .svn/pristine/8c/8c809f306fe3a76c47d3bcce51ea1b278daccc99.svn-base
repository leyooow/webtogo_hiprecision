<%@include file="includes/header.jsp"  %>
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="variations" />
<c:set var="submenu" value="variation_group" />

<body>

	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
			  	
				<div id="companyhome">
 				
 				<h3>
				
				Variation Group
				
				</h3>
 				
 				<!-- 
 				<c:if test="${param['evt'] != null}">
						<ul>
							
							<c:if test="${param['evt'] == 'save'}">
								<li><span class="actionMessage">Group was successfully created.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'groupexist'}">
								<li><span class="actionMessage">Category was not created because a similar category already exist.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'update'}">
								<li><span class="actionMessage">Group was sucessfully updated.</span></li>
							</c:if>
							
							<c:if test="${param['evt'] == 'delete'}">
								<li><span class="actionMessage">Group was sucessfully deleted.</span></li>
							</c:if>
						</ul>
					</c:if>
 				 -->
 				
 				<table width="100%" border="0">
					<tr>
						<td>
						
						<div style="float: right;"> 
							[<a href="#variationGroupForm">Add Variation Group</a>]
						</div>
						
						</td>
					</tr>
				</table>
 				
 				<table>  
					<tr class="headbox"> 
						<th>Name</th>
						<th>Variations</th>
						<th width="15%">Action</th> 
					</tr> 
					  
					<c:forEach items="${variationGroups}" var="vg">
					<tr>
						<td>${vg.name}</td>
						<td>
						
						<c:forEach items="${vg.variations}" var="v">
							${v.name} <br>
						</c:forEach>
						
						</td> 
						<td>
						<a href="editVariationGroup.do?variationGroupId=${vg.id }">Edit</a> 
						|  
						<a href="deleteVariationGroup.do?variationGroupId=${vg.id }" onclick="return confirm('Do you really want to delete this?');">Delete</a>
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
 				
				<a name="variationGroupForm" />
				<c:set var="formAction" value="saveVariationGroup.do" />
				<%@include file="variationGroupForm.jsp" %>
			 		
 				</div>
						
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
	
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
		
</body>

<%@include file="includes/footer.jsp"  %>