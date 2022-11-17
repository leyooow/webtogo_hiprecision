<%@include file="includes/taglibs.jsp" %>

<c:set var="navMenu" value="branches" />
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
			  <ul class="branch">			  	
			  	<c:forEach begin="0" end="${fn:length(allCatItems)-1}" var="i">
				  		<li>
					  		<a href="javascript:void(0)" onclick="Effect.toggle('brn_${allCatItems[i].id}', 'appear'); return false;"><span class="titlehmo">${allCatItems[i].name}</span></a>
					  		<div id="brn_${allCatItems[i].id}" style="display:none; padding:5px 15px 0 20px; text-align:justify; font-weight:normal; color:#252525;">${allCatItems[i].shortDescription}</div>				  
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