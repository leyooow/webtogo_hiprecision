<%@include file="includes/taglibs.jsp" %>
<c:set var="navMenu" value="packages" />
<%@include file="includes/header.jsp" %>
<%@include file="includes/nav.jsp" %>

<div class="clear"></div>
<c:choose>
	<c:when test="${empty param.package_id}">
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
							 	<table width="33%" class="floatLeft">		 							 		
					  				<c:forEach items="${packageList1}" var="packageObj" varStatus="counter">
					  					<c:set var="totalPrice" value="0"/>				  								  						
					  					<tr>
					  						<td>
					  							<!-- <a href="${menu['packages'].url}?package_id=${packageObj.id}">${packageObj.name}</a> -->
					  							<a href="javascript:void(0)" onclick="Effect.toggle('brn_${packageObj.id}', 'appear'); return false;"><span class="titlehmo">${packageObj.name}</span></a>
					  							<div id="brn_${packageObj.id}" style="display:none; padding:5px 15px 0 20px; text-align:justify; font-weight:normal; color:#252525;">
					  								Package Code: ${packageObj.sku}<br/>
					  								Package Name: ${packageObj.name}<br/><br/>
					  								Test Procedure(s):<br/>
					  								<c:forEach items="${packageObj.categoryItemPackages}" var="categoryItemPackage" varStatus="counter">
						 									[${categoryItemPackage.categoryItem.sku}] - ${categoryItemPackage.categoryItem.name}<br/>					  														  									
					  									<c:set var="totalPrice" value="${totalPrice + categoryItemPackage.price}"/>						  									  									  									 					    						    		
					  								</c:forEach>
					  								<br/>Total Price: <fmt:formatNumber type="number" minFractionDigits="2" value="${totalPrice}" />
					  							</div>
					  						</td>
					  					</tr>						  										  								  																		  									  									  									 					    						    	
					  				</c:forEach>						  			
					  			</table>
					  								  			
					  			<table width="33%" class="floatLeft">		 							 		
					  				<%-- <c:forEach items="${packageList2}" var="package" varStatus="counter">				  								  						
					  					<tr><td><a href="${menu['packages'].url}?package_id=${packageObj.id}">${packageObj.name}</a></td></tr>						  										  								  																		  									  									  									 					    						    	
					  				</c:forEach> --%>
					  				<c:forEach items="${packageList2}" var="package" varStatus="counter">
					  					<c:set var="totalPrice" value="0"/>				  								  						
					  					<tr>
					  						<td>
					  							<!-- <a href="${menu['packages'].url}?package_id=${packageObj.id}">${packageObj.name}</a> -->
					  							<a href="javascript:void(0)" onclick="Effect.toggle('brn_${packageObj.id}', 'appear'); return false;"><span class="titlehmo">${packageObj.name}</span></a>
					  							<div id="brn_${packageObj.id}" style="display:none; padding:5px 15px 0 20px; text-align:justify; font-weight:normal; color:#252525;">
					  								Package Code: ${packageObj.sku}<br/>
					  								Package Name: ${packageObj.name}<br/><br/>
					  								Test Procedure(s):<br/>
					  								<c:forEach items="${packageObj.categoryItemPackages}" var="categoryItemPackage" varStatus="counter">
						 									[${categoryItemPackage.categoryItem.sku}] - ${categoryItemPackage.categoryItem.name}<br/>					  														  									
					  									<c:set var="totalPrice" value="${totalPrice + categoryItemPackage.price}"/>						  									  									  									 					    						    		
					  								</c:forEach>
					  								<br/>Total Price: <fmt:formatNumber type="number" minFractionDigits="2" value="${totalPrice}" />
					  							</div>
					  						</td>
					  					</tr>						  										  								  																		  									  									  									 					    						    	
					  				</c:forEach>						  			
					  			</table>
					  								  			
					  			<table width="33%" class="floatLeft">		 							 		
					  				<%-- <c:forEach items="${packageList3}" var="package" varStatus="counter">				  								  						
					  					<tr><td><a href="${menu['packages'].url}?package_id=${packageObj.id}">${packageObj.name}</a></td></tr>						  										  								  																		  									  									  									 					    						    	
					  				</c:forEach> --%>
					  				<c:forEach items="${packageList3}" var="package" varStatus="counter">
					  					<c:set var="totalPrice" value="0"/>				  								  						
					  					<tr>
					  						<td>
					  							<!-- <a href="${menu['packages'].url}?package_id=${packageObj.id}">${packageObj.name}</a> -->
					  							<a href="javascript:void(0)" onclick="Effect.toggle('brn_${packageObj.id}', 'appear'); return false;"><span class="titlehmo">${packageObj.name}</span></a>
					  							<div id="brn_${packageObj.id}" style="display:none; padding:5px 15px 0 20px; text-align:justify; font-weight:normal; color:#252525;">
					  								Package Code: ${packageObj.sku}<br/>
					  								Package Name: ${packageObj.name}<br/><br/>
					  								Test Procedure(s):<br/>
					  								<c:forEach items="${packageObj.categoryItemPackages}" var="categoryItemPackage" varStatus="counter">
						 									[${categoryItemPackage.categoryItem.sku}] - ${categoryItemPackage.categoryItem.name}<br/>					  														  									
					  									<c:set var="totalPrice" value="${totalPrice + categoryItemPackage.price}"/>						  									  									  									 					    						    		
					  								</c:forEach>
					  								<br/>Total Price: <fmt:formatNumber type="number" minFractionDigits="2" value="${totalPrice}" />
					  							</div>
					  						</td>
					  					</tr>						  										  								  																		  									  									  									 					    						    	
					  				</c:forEach>						  			
					  			</table>	
					  			<div class="clear"></div>		  
					  		</div>			  		  
						</div><!--//mainContent-->		
						<c:if test="${packagesPagingUtil.totalPages gt 1}">
							<c:set var="pagingAction" value="${menu['packages'].url}"/>													
							<div align="center">
				        		Page ${packagesPagingUtil.currentPageNo} of ${packagesPagingUtil.totalPages} <img src="images/iPage.gif" align="absmiddle" />&nbsp;
				        		<c:if test="${param.pageNumber gt 1}">
					        		<a href="${pagingAction}?pageNumber=1">&laquo; First</a> | &nbsp
					        		<a href="${pagingAction}?pageNumber=${param.pageNumber-1}">&laquo; Previous</a> | &nbsp
				        		</c:if>
				        		
								<c:forEach items="${packagesPagingUtil.pages}" var="pageNum">	 
									<c:set value="${fn:length(packagesPagingUtil.pages)}" var="total" />
									<c:choose>											
										<c:when test="${packagesPagingUtil.currentPageNo!= pageNum }">
											<a href="${pagingAction}?pageNumber=${pageNum}&tpages=${total}">${pageNum}</a>					
										</c:when>  
										<c:otherwise>
											<span style="color: red;">[${pageNum}]</span> 
										</c:otherwise> 
									</c:choose>		 
										<span class="small">-</span>			  
								</c:forEach>
								
								<c:if test="${param.pageNumber ne packagesPagingUtil.totalPages}">	
									| <a href="${pagingAction}?pageNumber=${not empty param.pageNumber ? param.pageNumber+1 : 2}&tpages=${packagesPagingUtil.totalPages}">Next &raquo;</a>
								 	| <a href="${pagingAction}?pageNumber=${packagesPagingUtil.totalPages}&tpages=${packagesPagingUtil.totalPages}">Last &raquo;</a>
								</c:if>	
							</div>
						</c:if>
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
							 			<td><strong>Package Code:</strong></td>
							 			<td>${selectedPackage.sku}</td>
							 		</tr>
							 		<tr>
							 			<td><strong>Package Name:</strong></td>
							 			<td>${selectedPackage.name}</td>
							 		</tr>
							 		<tr><td>&nbsp;</td></tr>					 		
							 		<tr>
							 			<td><strong>Test Procedure(s):</strong></td>
							 			<%-- <td align="right"><strong>Price</strong></td> --%>
							 		</tr>
							 		<c:set var="totalPrice" value="0"/>								 		
						 			<c:forEach items="${selectedPackage.categoryItemPackages}" var="categoryItemPackage" varStatus="counter">
						 				<tr>				  								  						
					  						<td style="padding-left:10px">[${categoryItemPackage.categoryItem.sku}] - ${categoryItemPackage.categoryItem.name}</td>
					  						<%-- <td align="right">${categoryItemPackage.formattedPrice}</td> --%>
					  					</tr>				  								  												
					  					<c:set var="totalPrice" value="${totalPrice + categoryItemPackage.price}"/>						  									  									  									 					    						    		
					  				</c:forEach>
					  				<%-- <tr>					  					
					  					<td colspan="2" align="right"><span style="padding-right:15px">TOTAL:</span><strong><fmt:formatNumber type="number" minFractionDigits="2" value="${totalPrice}" /></strong></td>
					  				</tr> --%>					  				
					  				<tr>
					  					<td><strong>Total Price:</strong></td>
					  					<td><fmt:formatNumber type="number" minFractionDigits="2" value="${totalPrice}" /></td>
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