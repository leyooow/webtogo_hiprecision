<%@include file="includes/taglibs.jsp" %>
<c:set var="navMenu" value="specializations" />
<%@include file="includes/header.jsp" %>
<%@include file="includes/nav.jsp" %>

<div class="clear"></div>
<c:choose>
	<c:when test="${empty item.id}">
		<div class="content2">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr valign="top">
					<td>	        
			        	<div class="mainContent3">
							<h1>${page.title}</h1>
					  		<div class="welcomeNote">
								<img src="images/grayOvalTl.jpg" class="itl" />
						        <img src="images/grayOvalTr.jpg" class="itr" />
						        <img src="images/grayOvalBl.jpg" class="ibl2" />
						        <img src="images/grayOvalBr.jpg" class="ibr" />
							 	<table>
							 		<c:forEach items="${group.brands}" var="brand" varStatus="counter">
							 			<th align="left" style="padding-bottom:5px;font-size:14px;">
							 				${brand.name}
							 			</th>
							 			<tr>
							 				<c:forEach items="${brand.itemsbyname}" var="categoryItem" varStatus="counter">
							 					<td width="33%"><a href="${menu['specializations'].url}?item_id=${categoryItem.id}">${categoryItem.name} (${categoryItem.parent.name})</a></td>
						  						<c:if test="${counter.count mod 3 eq 0}"></tr><tr></c:if>
							 				</c:forEach>	
							 			</tr>
							 			<tr><td>&nbsp;</td></tr>
							 		</c:forEach>							 									 	
					  			</table>			  
					  		</div>			  		  
						</div><!--//mainContent-->		
				  	</td>
				</tr>
			</table>
		</div><!--//content-->
	</c:when>
	<c:otherwise>
		<div class="content2">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr valign="top">
					<td>	        
			        	<div class="mainContent3">
							<h1>${page.title}</h1>
					  		<div class="welcomeNote">
								<img src="images/grayOvalTl.jpg" class="itl" />
						        <img src="images/grayOvalTr.jpg" class="itr" />
						        <img src="images/grayOvalBl.jpg" class="ibl2" />
						        <img src="images/grayOvalBr.jpg" class="ibr" />
							 	<table>
							 		<tr>
							 			<td><strong>Clinician:</strong></td>
							 			<td>${item.name}</td>
							 		</tr>
							 		<tr>
							 			<td><strong>Specialization:</strong></td>
							 			<td>${item.brand.name}</td>
							 		</tr>
							 		<tr>
							 			<td><strong>Branch:</strong></td>
							 			<td>${item.parent.name}</td>
							 		</tr>
							 	</table>
							 	<br/>
							 	<table>							 							 	
							 		<tr>
							 			<td>${item.shortDescription}</td>							 			
							 		</tr>							 										  				
					  			</table>			  
					  		</div>			  		  
						</div><!--//mainContent-->		
				  	</td>
				</tr>
			</table>
		</div><!--//content-->
	</c:otherwise>
</c:choose>
<%@include file="includes/footer.jsp" %>