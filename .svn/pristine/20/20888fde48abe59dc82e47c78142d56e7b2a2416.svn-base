<%@include file="includes/header.jsp"  %>
<c:set var="menu" value="groups" />
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="../admin/javascripts/boxover.js" />"></script> 
<% pageContext.setAttribute("slashquo", "\'"); %>
<body> 
	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
					<div id="companyhome"> 
						<h3>Batch Update for ${group.name}</h3>
						<br />
						<form action="batchupdateitems.do" method="post">
							<input type="hidden" name="group_id" value="${group.id}" />
							<table>
								<tr class="headbox">
									<th>&nbsp;</th>
									<th>SKU</th>
									<th>Name</th>
									<th>Short Description/<br/>
						          	Description/<br/>
						          	Other Details</th>
								
									<c:forEach items="${categoryItemPriceNames}" var="curr">
										<th>${curr.name}</th>
									</c:forEach>
									<c:if test="${empty categoryItemPriceNames}">
										<th>Price</th>
									</c:if>
									<th>&nbsp;</th>
									<th>Action</th>
								</tr>
								<c:forEach items="${itemSKUs}" var="curr" varStatus="status">
									<tr>
										<td>${status.index + 1}</td>
										<td>
											${curr} <input type="hidden" name="id" value="${itemIds[status.index]}" /> <input type="hidden" name="sku" value="${curr}" />
										</td>
										<td>
											${itemNames[status.index]} <input type="hidden" name="productName" value="${itemNames[status.index]}" />
										</td>
										 <td>
										 <c:if test="${not empty itemShortDescription[status.index+1]}">
			          	  						<img src="../admin/images/note.png" border="0" title='fade=[on] cssbody=[tooltipbody] cssheader=[tooltipheader] header=[Short Description] body=[<p class="note" width="150px;"> ${fn:replace(fn:replace(itemShortDescription[status.index+1] ,slashquo ,"&#39;" ),"\"","&#34;")}</p>]' style="cursor: pointer;"  />
			          	  						<input type="hidden" name="itemShortDescription" value="${itemShortDescription[status.index+1]}" />
			          	  				</c:if>
										
					            	  	<c:if test="${not empty itemDescription[status.index+1]}">
						          	  		  	<img src="../admin/images/note.png" border="0" title='fade=[on] cssbody=[tooltipbody] cssheader=[tooltipheader] header=[Description] body=[<p class="note" width="150px;">${fn:replace(fn:replace(itemDescription[status.index+1] ,slashquo ,"&#39;" ),"\"","&#34;")}</p>]' style="cursor: pointer;"  />
						          	  			<input type="hidden" name="itemDescription" value="${itemDescription[status.index+1]}" />
						          	  	</c:if>
					            	  
						          	  	<c:if test="${not empty itemOtherDetails[status.index+1]}">
						          	  			 <img src="../admin/images/note.png" border="0" title='fade=[on] cssbody=[tooltipbody] cssheader=[tooltipheader] header=[Other Details] body=[<p class="note" width="150px;"> ${fn:replace(fn:replace(itemOtherDetails[status.index+1] ,slashquo ,"&#39;" ),"\"","&#34;")}</p>]' style="cursor: pointer;"  />
						          	  			 <input type="hidden" name="itemOtherDetails" value="${itemOtherDetails[status.index+1]}" />
						          	   	</c:if> 
										 
						        		 <%--	${itemShortDescription[status.index+1]}
						        			<input type="hidden" name="itemShortDescription" value="${itemShortDescription[status.index+1]}" />
						          	 		
						          	  <c:if test="${not empty itemShortDescription[status.index+1]}">		
						          	 			<img src="images/note.png" border="0" title="fade=[on] cssbody=[tooltipbody] cssheader=[tooltipheader] header=[Short Description] body=[${itemShortDescription[status.index+1]}]" style="cursor: pointer;"  />
						          	  	 		<input type="hidden" name="itemShortDescription" value="${itemShortDescription[status.index+1]}" />
						          	  	</c:if>	
					            	  	<c:if test="${not empty itemDescription[status.index+1]}">
						          	  		  	<img src="images/note.png" border="0" title="fade=[on] cssbody=[tooltipbody] cssheader=[tooltipheader] header=[Description] body=[${itemDescription[status.index+1]}]" style="cursor: pointer;"  />
						          	  			<input type="hidden" name="itemDescription" value="${itemDescription[status.index+1]}" />
						          	  	</c:if>
					            	  
						          	  	<c:if test="${not empty itemOtherDetails[status.index+1]}">
						          	  			 <img src="images/note.png" border="0" title="fade=[on] cssbody=[tooltipbody] cssheader=[tooltipheader] header=[Other Details] body=[ ${ itemOtherDetails[status.index+1]}]" style="cursor: pointer;"  />
						          	  			 <input type="hidden" name="itemOtherDetails" value="${itemOtherDetails[status.index+1]}" />
						          	   	</c:if> 
						          	   		${itemShortDescription[status.index+1]}
						        			<input type="hidden" name="itemShortDescription" value="${itemShortDescription[status.index+1]}" />
						        			<br>
						        			${itemDescription[status.index+1]}
						        			<input type="hidden" name="itemDescription" value="${itemDescription[status.index+1]}" />
						          	  		<br>
						          	  		${itemOtherDetails[status.index+1]}
						        			<input type="hidden" name="itemOtherDetails" value="${itemOtherDetails[status.index+1]}" /> 
					            	  --%>
					            	  </td> 
										
										<c:forEach items="${itemPrices}" var="currPrice">
											<td>${currPrice[status.index]}</td>
										</c:forEach>
										<td>
											<c:choose>
												<c:when test="${fn:contains(itemActions[status.index], 'No changes.')}">
													<img src="../admin/images/accept.png" alt="ok" title="This item does not have any changes" />
												</c:when>
												<c:when test="${fn:contains(itemActions[status.index], 'Error: ')}">
													<img src="../admin/images/cancel.png" alt="error" title="This item will not be updated because of the error" />
												</c:when>
												<c:otherwise>
													<img src="../admin/images/accept.png" alt="ok" title="This item will be updated" />
												</c:otherwise>
											</c:choose>
										</td>
										<td>${itemActions[status.index]}</td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="6">&nbsp;</td>
								</tr>
							</table>
							<table style="width:100%">
								<tr>
									<td style="text-align:center"><input type="button" value="Cancel" onclick="window.location='items.do?group_id=${group.id}'" /></td>
									<td style="text-align:center"><input type="submit" value="Confirm and Save" /></td>
								</tr>
							</table>
						</form>
					</div>
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
</body>
<%@include file="includes/footer.jsp"  %>