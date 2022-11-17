<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<center>
<table border="0" cellspacing="0" cellpadding="0">
<tr><td>
<%@include file="../includes/taglibs.jsp" %>

<c:set var="navMenu" value="messageboard" scope="session"/>

<jsp:include page="../../companies/${company.name}/jsp/includes/header_home.jsp"/>

<link href="../../companies/${company.name}/css/css.css" rel="stylesheet" type="text/css" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">   
  <tr valign="top">
    <td class="sLeft"><img src="${httpServer}/images/sLeft.jpg" /></td>    
      
    <td class="contentContainer">
    <div class="masthead"><img src="${httpServer}/images/masthead2.jpg" alt="${company.nameEditable}" title="${company.nameEditable}" /></div>
		<jsp:include page="../../companies/${company.name}/jsp/includes/menulinks.jsp"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">  
		     <tr valign="top">
				<jsp:include page="../../companies/${company.name}/jsp/includes/sidenav.jsp"/>
				<td class="content">  
					<h2>${page.title}</h2>
					<div class="contentWrapper" id="contentWrapper">
						<%@include file="messagelist.jsp" %>  
						<%@include file="composemessage.jsp" %>
					</div><!--contentWrapper end-->
			   </td><!--content end-->
			 </tr>
		</table>
	</td>   
    
    <td class="sRight"><img src="${httpServer}/images/sRight.jpg" /></td>
  </tr>
</table>    
  
<jsp:include page="../../companies/${company.name}/jsp/includes/bottomcontent.jsp"/>
   
<jsp:include page="../../companies/${company.name}/jsp/includes/footer.jsp"/>
</td></tr></table></center>