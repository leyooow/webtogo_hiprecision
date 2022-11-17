<%@include file="includes/taglibs.jsp" %>

<c:set var="navMenu" value="vaccines" />
<%@include file="includes/header.jsp" %>
<%@include file="includes/nav.jsp" %>
<div class="clear"></div>
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
			  <ul class="vaccine">
			  	<c:forEach begin="0" end="${fn:length(allCatItems)-1}" var="i">
				 		<li>
				 			<a href="javascript:void(0)" onclick="Effect.toggle('vac_${allCatItems[i].id}', 'appear'); return false;"><span class="titlevaccine">${allCatItems[i].name}</span></a>
							<%-- <div id="vac_${allCatItems[i].id}" style="display:none; padding:5px 15px 0 20px; text-align:justify; font-weight:normal; color:#252525;">
								${allCatItems[i].shortDescription}
							</div> --%>					  					  
					  </li>
					  
					  <li id="vac_${allCatItems[i].id}" style="display:none; padding:5px 15px 0 20px; text-align:justify; font-weight:normal; color:#252525;">						  				  						  
								<span class="price" style="margin-left:30px">${fn:toUpperCase(allCatItems[i].categoryItemPriceName.name)}: </span><fmt:formatNumber type="number" minFractionDigits="2" value="${allCatItems[i].price}" />						  						  						  		
				  			<c:forEach items="${allCatItems[i].categoryItemPrices}" var="categoryItemPrice" varStatus="counter">
				  				<br/><br/>
				  				<span class="price" style="margin-left:30px">${fn:toUpperCase(categoryItemPrice.categoryItemPriceName.name)}: </span><fmt:formatNumber type="number" minFractionDigits="2" value="${categoryItemPrice.amount}" />  				
				  			</c:forEach>
						  	<c:forEach items="${allCatItems[i].categoryItemOtherFields}" var="categoryItemOtherField" varStatus="counter">							
									<br/><br/>
									<span style="margin-left:30px"><strong>${fn:toUpperCase(categoryItemOtherField.otherField.name)}:</strong> ${categoryItemOtherField.content}</span>
								</c:forEach>
								<br/><br/>
								<c:forEach items="${featuredPages['schedules'].items}" var="schedule">
						  		<c:if test="${schedule.name eq allCatItems[i].otherDetails}">
						  			<span style="margin-left:30px">${schedule.content}</span>
						  		</c:if>
						  	</c:forEach>
								<br/><br/>											 
						</li>
				  </c:forEach>			  				  					  				  					  	
			  </ul>			  
			  </div>
			  <p align="right">For more info, Please send us an email at: <a href="/">info@hi-precision.com</a></p>
			  &nbsp;
		  
			</div><!--//mainContent-->
			
		  </td>
		</tr>
	  </table>
	</div><!--//content-->
<%@include file="includes/footer.jsp" %>