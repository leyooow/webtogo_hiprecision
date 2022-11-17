<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
	a.activeMe{
		color: #FFFFFF;
		font-size: 1.1em;
		text-decoration: underline;
		
	}
	a.small{
		font-size: .9em;
	}
</style>
	

<c:url var="pageLinkSub" value="${actionDo}">

	<c:forEach items="${pageContext.request.parameterNames}" var="paramName">
		<c:if test="${paramName ne 'pageNumber'}">
			<c:if test="${not empty param[paramName]}">
				<c:param name="${paramName}" value="${param[paramName]}" />
			</c:if>
		</c:if>
	</c:forEach>
	</c:url>

<c:if test="${pagingUtil.totalPages > 0}">
<c:choose>
		<c:when test="${(menu eq 'wishlist' || menu eq 'order' )&& param.pageNumber!=null && param.pageNumber!=1 }">
			<li>(Page ${pagingUtil.currentPageNo} of ${pagingUtil.totalPages})</li>
		</c:when>
		<c:when test="${param.pageNumber!=null && param.pageNumber!=1 }">
		  <li>(Page ${pagingUtil.currentPageNo+1} of ${pagingUtil.totalPages})</li>
		</c:when>
		<c:otherwise>
			<li>(Page ${pagingUtil.currentPageNo} of ${pagingUtil.totalPages})</li>
		</c:otherwise>
	</c:choose>

</c:if>

<c:if test="${pagingUtil.showFirst}">
  <c:url var="pageLinkFinalFirst" value="${pageLinkSub}">
		<c:param name="pageNumber" value="1"/>
	</c:url>
	<li><a class="small" href='${pageLinkFinalFirst}'>first</a></li>
</c:if>
<c:if test="${param.pageNumber!=null}">
<c:if test="${param.pageNumber>1}">
	<c:url var="pageLinkFinalPrev" value="${pageLinkSub}">
		<c:param name="pageNumber" value="${pagingUtil.currentPageNo}"/>
	</c:url>
	<c:choose>
		<c:when test="${menu eq 'wishlist' || menu eq 'order' }">
			<li><a class="small" href="?pageNumber=${pagingUtil.currentPageNo-1}">prev</a></li>
		</c:when>
		<c:otherwise>
			<li><a class="small" href="${pageLinkFinalPrev}">prev</a></li>
		</c:otherwise>
	</c:choose>
	
</c:if>
</c:if>

<c:if test="${pagingUtil.hasLeftDots}">
	<li>...</li>
</c:if>

<c:forEach items="${pagingUtil.pages}" var="pageNum">
	
	<c:choose>
	
		<c:when test="${param.pageNumber==null && pageNum eq pagingUtil.currentPageNo}">
		  <c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>
			<li><a style="color:#3860B3;font-size: 1.1em;text-decoration: underline;" href='${pageLinkFinal}' >${pageNum}</a></li>
		</c:when>
		
		<c:when test="${param.pageNumber==1 && pageNum eq pagingUtil.currentPageNo}">
		  <c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>
			<li><a style="color:#3860B3;font-size: 1.1em;text-decoration: underline;" href='${pageLinkFinal}' >${pageNum}</a></li>
		</c:when>
		
		<c:when test="${(menu eq 'wishlist' || menu eq 'order') && (param.pageNumber!=null && pageNum eq pagingUtil.currentPageNo && param.pageNumber!=1 ) }">
			<c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>		
			<li><a style="color:#3860B3;font-size: 1.1em;text-decoration: underline;" href='${pageLinkFinal}' >${pagingUtil.currentPageNo}</a></li>
		
		</c:when>
		
		<c:when test="${(menu ne 'wishlist' && menu ne 'order') && (param.pageNumber!=null && pageNum eq pagingUtil.currentPageNo+1 && param.pageNumber!=1 )}">
		  <c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>		
		<li><a style="color:#3860B3;font-size: 1.1em;text-decoration: underline;" href='${pageLinkFinal}' >${pagingUtil.currentPageNo+1}</a></li>
				
		</c:when>
		
		<c:otherwise>
			<c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>
			<li><a class="small"  href='${pageLinkFinal}'>${pageNum}</a></li>
		</c:otherwise>
	</c:choose>
</c:forEach>

<c:if test="${pagingUtil.hasRightDots}">
	<li>...</li>
</c:if>

<c:if test="${pagingUtil.hasNext && param.pageNumber != pagingUtil.totalPages}">
	 <c:url var="pageLinkFinalNext" value="${pageLinkSub}">
	 		<c:if test="${param.pageNumber!=null}">
				<c:param name="pageNumber" value="${param.pageNumber + 1}"/>
			</c:if>
			<c:if test="${param.pageNumber==null}">
				<c:param name="pageNumber" value="${pagingUtil.currentPageNo + 1}"/>
			</c:if>
		</c:url>
	<li><a class="small" href="${pageLinkFinalNext}"'>next</a></li>
</c:if>

<c:if test="${pagingUtil.showLast}">
	 <c:url var="pageLinkFinalLast" value="${pageLinkSub}">
			<c:param name="pageNumber" value="${pagingUtil.totalPages}"/>
		</c:url>
	<li><a class="small" href="${pageLinkFinalLast}">last</a></li>
</c:if>
