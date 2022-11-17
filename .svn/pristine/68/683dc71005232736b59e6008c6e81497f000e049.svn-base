<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>
<%@taglib uri="/struts-tags" prefix="s" %>


<style>
h1, h3, h2, strong, p, li, ul {
	font-family: times new roman, verdana, tahoma;
}
</style>

<div>
	<div style="width:95%; margin: auto;">
		<c:if test="${not empty company.logo}">
			<img style="padding:5px;border:0px"  src="${httpServer}/images/${company.logo}" />
		</c:if>
			  	  
		<c:choose> 
		 
		<c:when test="${selectedPage != null}">
		
			<c:choose>
				<c:when test="${selectedPage.class.name == 'com.ivant.cms.entity.MultiPage'}">
					
					<c:choose>
						<c:when test="${param['showallitems'] != null}">
							
							<h1>${selectedPage.name}</h1>
							
							<c:forEach items="${selectedPage.items}" var="p">	
								<h3>${p.name}</h3>  
								<p>${p.content}</p>
							</c:forEach>
	
						</c:when>
						<c:otherwise>
							<h1>${selectedPage.title}</h1> 
							<p>${selectedPage.description}</p>
						</c:otherwise>
					</c:choose>
					
				</c:when>
				<c:when test="${selectedPage.class.name == 'com.ivant.cms.entity.SinglePage'}">
	
					<h1>${selectedPage.name}</h1> 
					<p>${selectedPage.content}</p>
					  
				</c:when>
				<c:when test="${selectedPage.class.name == 'com.ivant.cms.entity.FormPage'}">
	
					<h1>${selectedPage.title}</h1> 
					<p>${selectedPage.topContent}</p> 
					  
				</c:when>
			</c:choose>
			
		</c:when>
		
		<c:when test="${item != null}">
			<c:if test="${not empty item.parent.name}">
				<h3>${item.parent.name}</h3>
			</c:if>
			<c:if test="${not empty item.name}">
				<h2>${item.name}</h2>
			</c:if>
			<c:if test="${not empty item.images}">
				<c:forEach items="${item.images}" var="img">
					<img src="images/items/${img.image2}" alt="${img.caption}"></li>				
				</c:forEach>						
			</c:if>
				
			<c:if test="${not empty item.shortDescription}">	
				<p>${item.shortDescription}</p>
			</c:if>
			
			<c:if test="${not empty item.description}">
				<p>${item.description}</p>
			</c:if>
 
 			<c:if test="${not empty item.otherDetails}">
				<p>${item.otherDetails}</p>
			</c:if>
			 <c:if test="${not empty item.price and item.price>0}">
				<p>Price: ${item.price}</p>
			</c:if>
 
		</c:when>

		</c:choose>
		
		<hr>	
		<small><address>
		<c:if test="${company.address != null and company.address != ''}">
			${company.address}			
		</c:if>
		<c:if test="${company.phone != null and company.phone != ''}">
		<br />	Phone: ${company.phone}			
		</c:if>
		<c:if test="${company.fax != null and company.fax != ''}">
		<br />	Fax: ${company.fax}			
		</c:if>
		<c:if test="${company.email != null and company.email != ''}">
		<br />	Email: ${company.email}			
		</c:if>	
		</address> </small>
	</div> 

</div>	

