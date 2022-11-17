<%@include file="includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=utf-8" language="java"%>
<c:set var="navMenu" value="search" />
<%@include file="includes/header.jsp"%>
<%@include file="includes/nav.jsp" %>
<script>
	function cart(){
		document.getElementById('addToCart').submit();
	}

	var toggle = false;
	function togglePackage() { 
		togglePackageElement = document.getElementById('togglePackage');
		var content = '<table><tr>';			
		if(!toggle) {
			<c:forEach items="${categoryItemPackages}" var="categoryItemPackage" varStatus="counter">					  							  			
	  			content += '<td style="font-size:10px">${categoryItemPackage.iPackage.htmlName}</td>';
	  			<c:if test="${counter.count mod 3 eq 0}">
	  				content += '</tr><tr>';
	  			</c:if>					  								  		
			</c:forEach>										
			togglePackageElement.innerHTML = 'hide all';
			toggle = true;			
		} else {			
			<c:forEach items="${categoryItemPackages}" var="categoryItemPackage" begin="0" end="8" varStatus="counter">					  							  			
	  			content += '<td style="font-size:10px">${categoryItemPackage.iPackage.htmlName}</td>';
	  			<c:if test="${counter.count mod 3 eq 0}">
	  				content += '</tr><tr>';
	  			</c:if>					  								  		
			</c:forEach>								
			togglePackageElement.innerHTML = 'show all ${fn:length(categoryItemPackages)} packages';
			toggle = false;
		}
		content += '</tr></table>';		  	
		document.getElementById('packages').innerHTML = content;				  	
	}
</script>
<div class="clear"></div>
<div class="content">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">

	    <tr valign="top">
		  <%@include file="includes/sideNavResult.jsp" %>
		  <td>
		  <c:if test="${param.notificationMessage ne null}">
			  <div class="messagebox">
				<img src="images/ovalWTl.png" class="itl" />
			    <img src="images/ovalWTr.png" class="itr" />
			    <img src="images/ovalWBl.png" class="ibl2" />
			    <img src="images/ovalWBr.png" class="ibr" />	        
				${param.notificationMessage}
			  </div>
		  </c:if>
		
		    <div><img src="images/c.gif" /></div>
			<div class="mainContent">
			<c:choose>
				<c:when test="${item!=null}">
					<c:choose>
						<c:when test="${item.parentGroup.id eq 159}">
							<p><strong>Branch Name: </strong>${item.name}</p>
						</c:when>
						<c:when test="${item.parentGroup.id eq 158}">
							<p><strong>Frequently asked questions: </strong>${item.name}</p>
						</c:when>
						<c:when test="${item.parentGroup.id eq 162}">
							<p><strong>Clinician: </strong>${item.name}</p>
							<p><strong>Specialization: </strong>${item.brand.name}</p>
						</c:when>
						<c:when test="${item.parentGroup.id eq 163}">
							<p><strong>Protocol: </strong>${item.name}</p>							
						</c:when>			
						<c:when test="${item.parentGroup.id eq 165}">
							<p><strong>HMO: </strong>${item.name}</p>
							<p>${item.shortDescription}</p>
						</c:when>			
						<c:otherwise>
							<c:choose>
								<c:when test="${item.parentGroup.id ne 164}">
									<p><strong>TEST NAME: </strong>${item.name} <c:if test="${not empty item.unitOfMeasure}">(${item.unitOfMeasure})</c:if></p>
							  	<p><strong>TEST PROCEDURE: </strong>${item.shortDescription}</p>
							  	<p><strong>TEST CODE: </strong>${not empty item.categoryItemOtherFieldMap['TEST CODE(S)'].content ? item.categoryItemOtherFieldMap['TEST CODE(S)'].content : item.sku }</p>
							  	<p><strong>TEST GROUP: </strong>${item.otherDetails}</p>
								  <p>
								  	<strong>PACKAGES: </strong>
								  	<span id="packages">
									  	<table>
									  		<tr>
												<c:forEach items="${categoryItemPackages}" var="categoryItemPackage" begin="0" end="8" varStatus="counter">	
													<c:choose>
														<c:when test="${fn:containsIgnoreCase(categoryItemPackage.iPackage.name, 'PATIENT PREPARATION') or 
																		categoryItemPackage.iPackage.name eq 'SPECIMEN REQUIREMENT' or
																		categoryItemPackage.iPackage.name eq 'SPECIMEN STABILITY 15-25°C' or
																		categoryItemPackage.iPackage.name eq 'SPECIMEN PACKAGING FOR TRANSPORT' or
																		categoryItemPackage.iPackage.name eq 'SPECIMEN STABILITY 2-8°C' or
																		categoryItemPackage.iPackage.name eq 'SPECIMEN STABILITY -15-25°C' or
																		categoryItemPackage.iPackage.name eq 'SPECIMEN REJECTION' or
																		categoryItemPackage.iPackage.name eq 'STORAGE AND TRANSPORT' }">
														</c:when>
														<c:otherwise>
															<td style="font-size:10px">${categoryItemPackage.iPackage.name}</td>
															<c:if test="${counter.count mod 3 eq 0}"></tr><tr></c:if>	
														</c:otherwise>
													</c:choose>
																  							  			
																	  								  		
												</c:forEach> 		
										  	</tr>
									  	</table>			  	
									</span>
									<c:if test="${fn:length(categoryItemPackages) gt 9}">
								  		<span style="float:right;padding-right:50px"><a href="javascript:void(0)" id="togglePackage" onClick="togglePackage()" style="font-size:10px">show all ${fn:length(categoryItemPackages)} packages</a></span>
								  	</c:if>
								  </p>
								</c:when>
								<c:otherwise>
									<p><strong>VACCINE: </strong>${item.name}</p>
								</c:otherwise>
							</c:choose>
						  						  
						  <%-- <c:if test="${item.parentGroup.id !=159 and item.parentGroup.id != 158 and item.parentGroup.id != 163 and item.parentGroup.id != 165}">
						  
						  <p>
								  <span class="price">${fn:toUpperCase(item.categoryItemPriceName.name)}: </span><fmt:formatNumber type="number" minFractionDigits="2" value="${item.price}" />&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<img src="images/iquote.gif" align="absmiddle" /> <a href="addToCart.do?id=${item.id}&itemPrice=${item.price}&keyword=${keyword}&quantity=1">add to quote</a>
						  </p>						  						  		
					  			<c:forEach items="${item.categoryItemPrices}" var="categoryItemPrice" varStatus="counter">
					  				<p><span class="price">${fn:toUpperCase(categoryItemPrice.categoryItemPriceName.name)}: </span><fmt:formatNumber type="number" minFractionDigits="2" value="${categoryItemPrice.amount}" />&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<img src="images/iquote.gif" align="absmiddle" /> <a href="addToCart.do?id=${item.id}&itemPrice=${categoryItemPrice.amount}&keyword=${keyword}&quantity=1&priceName=${categoryItemPrice.categoryItemPriceName.name}">add to quote</a></p>
					  				<input type="hidden" name="itemPrice" id="itemPrice" value="${categoryItemPrice.amount}"/>	
					  			</c:forEach>
					  			<input type="hidden" name="quantity" id="quantity" value="1"/>
					  			<input type="hidden" value="${keyword}" name="keyword"/>				 
						  </c:if> --%>
					  </c:otherwise>
				</c:choose>
					  <div class="box">
					  <br/>
					  <c:choose>
					  	<c:when test="${item.parentGroup.id eq 162 or item.parentGroup.id eq 159}">
					  		${item.shortDescription}
					  		
					  	</c:when>
					  	<c:otherwise>
					  		<c:forEach items="${item.parentGroup.otherFields}" var="of">
					  			<c:if test="${of.sortOrder != null}">
						  			<c:choose>
										<c:when test="${fn:toUpperCase(of.name) eq 'SPECIMEN STABILITY' }">
											<strong>${fn:toUpperCase(of.name)}</strong>
											<p>&nbsp;</p>
										</c:when>
										<c:when test="${fn:toUpperCase(of.name) eq 'RELATED WORDS' }">
											<strong>${fn:toUpperCase(of.name)}/TEST</strong>
											<p>
						  						${empty item.categoryItemOtherFieldMap[of.name].content ? 'Not Applicable' :  item.categoryItemOtherFieldMap[of.name].content}
						  					</p>
										</c:when>
										<c:otherwise>
											<strong>${fn:toUpperCase(of.name)}:</strong>					  
						  					<p>
						  						${empty item.categoryItemOtherFieldMap[of.name].content ? 'Not Applicable' :  item.categoryItemOtherFieldMap[of.name].content}
						  					</p>
										</c:otherwise>
									</c:choose>
					  			</c:if>
					  		</c:forEach>
					  		
					  		
					  		<%-- <c:forEach items="${item.categoryItemOtherFields}" var="categoryItemOtherField" varStatus="counter">
					  			<c:choose>
					  				<c:when test="${fn:containsIgnoreCase(categoryItemOtherField.otherField.name, 'PATIENT PREPARATION') or 
													categoryItemOtherField.otherField.name eq 'SPECIMEN REQUIREMENT' or
													categoryItemOtherField.otherField.name eq 'SPECIMEN STABILITY 15-25°C' or
													categoryItemOtherField.otherField.name eq 'SPECIMEN PACKAGING FOR TRANSPORT' or
													categoryItemOtherField.otherField.name eq 'SPECIMEN STABILITY 2-8°C' or
													categoryItemOtherField.otherField.name eq 'SPECIMEN STABILITY -15-25°C' or
													categoryItemOtherField.otherField.name eq 'SPECIMEN REJECTION' or
													categoryItemOtherField.otherField.name eq 'STORAGE AND TRANSPORT' }">
													
									</c:when>
									<c:otherwise>
									
								  
								  				<strong>${fn:toUpperCase(categoryItemOtherField.otherField.name)}:</strong> ${categoryItemOtherField.content}<br/><br/>
					
									</c:otherwise>
					  			</c:choose>
					  		
					  		
							  </c:forEach> --%>
					  	</c:otherwise>
					  </c:choose>					  
					  	<%--<c:choose>
					  		<c:when test="${item.parentGroup.id eq 158 or item.parentGroup.id eq 159 or item.parentGroup.id eq 162}">
					  			${item.shortDescription }
					  		</c:when>
					  		<c:otherwise>
					    		${item.description}
					    	</c:otherwise>
					    </c:choose>--%> 
					    <br/><br/>
					  </div><!--//box-->
					  	<!--//mapped name of schedule to other details of category item--> 
					  	<c:forEach items="${featuredPages['schedules'].items}" var="schedule">
					  		<c:if test="${schedule.name eq item.otherDetails}">
					  			${schedule.content}
					  		</c:if>
					  	</c:forEach>
			  	</c:when>
			  	<c:when test="${selectedPackage ne null}">			  		
					<p><strong>PACKAGE CODE: </strong>${selectedPackage.sku}</p>
					<p><strong>PACKAGE NAME: </strong>${selectedPackage.name}</p>
					<p>
						<table>
							<tr>
								<th align="left">TEST PROCEDURES</th>
								<%-- <th align="right">PRICE</th> --%>
							</tr>	
							<c:set var="totalPrice" value="0"/>  	
							<c:forEach items="${selectedPackage.categoryItemPackages}" var="categoryItemPackage" varStatus="counter">
								<c:choose>
									<c:when test="${fn:containsIgnoreCase(categoryItemPackage.categoryItem.name, 'PATIENT PREPARATION') or 
													categoryItemPackage.categoryItem.name eq 'SPECIMEN REQUIREMENT' or
													categoryItemPackage.categoryItem.name eq 'SPECIMEN STABILITY 15-25°C' or
													categoryItemPackage.categoryItem.name eq 'SPECIMEN PACKAGING FOR TRANSPORT' or
													categoryItemPackage.categoryItem.name eq 'SPECIMEN STABILITY 2-8°C' or
													categoryItemPackage.categoryItem.name eq 'SPECIMEN STABILITY -15-25°C' or
													categoryItemPackage.categoryItem.name eq 'SPECIMEN REJECTION' or
													categoryItemPackage.categoryItem.name eq 'STORAGE AND TRANSPORT' }">
													
									</c:when>
									<c:otherwise>
									<tr>					  							  			
										<td style="padding-left:10px">[${categoryItemPackage.categoryItem.sku}] - ${categoryItemPackage.categoryItem.name}</td>
										<%-- <td align="right">${categoryItemPackage.formattedPrice}</td> --%>
									</tr>								  						  	
									<c:set var="totalPrice" value="${totalPrice + categoryItemPackage.price}"/>			
									</c:otherwise>
								</c:choose>  	
							</c:forEach>
							<%-- <tr>					  					
						  	<td colspan="2" align="right"><span style="padding-right:15px">TOTAL:</span><strong><fmt:formatNumber type="number" minFractionDigits="2" value="${totalPrice}" /></strong></td>
						  	</tr> --%>		  		
								  							  									  								  				  								
						</table>						
					</p>				
					<p><strong>Total Price: </strong><fmt:formatNumber type="number" minFractionDigits="2" value="${totalPrice}" /></p>		  					  					 				 					  	
			  	</c:when>
			 </c:choose>
			</div><!--//mainContent-->
			<div><img src="images/c2.gif" /></div>
			
			<div class="legend">
				 
				<img src="images/ovalWTl.png" class="itl" />
		        <img src="images/ovalWTr.png" class="itr" />
		        <img src="images/ovalWBl.png" class="ibl2" />
		        <img src="images/ovalWBr.png" class="ibr" /> 
		        
		        
				<ul class="listlegend">
					<li class="label">Legend:</li>
					<li class="icotestprocedure">Test Procedures</li>
					<li class="icopackage">Test Packages</li>									
					<li class="icobranch">Branches</li>
					<li class="icofaq">FAQ's</li>
					<li class="icodoctor">Doctor</li>
				</ul>
				<div class="clear"></div>
				
			</div>
		  </td>
		</tr>

			
	  </table>
	</div><!--//content-->
<%@include file="includes/footer.jsp" %>