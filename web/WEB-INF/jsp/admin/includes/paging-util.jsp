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
		<c:when test="${param.pageNumber!=null && param.pageNumber!=1 }">
		  <span style="color: #666666; font-size: .9em;">(Page ${pagingUtil.currentPageNo+1} of ${pagingUtil.totalPages})</span>&nbsp;&nbsp;
		</c:when>
		<c:otherwise>
			<span style="color: #666666; font-size: .9em;">(Page ${pagingUtil.currentPageNo} of ${pagingUtil.totalPages})</span>&nbsp;&nbsp;
		</c:otherwise>
	</c:choose>

</c:if>

<span style="padding-right: 10px">
<c:if test="${pagingUtil.showFirst}">
  <c:url var="pageLinkFinalFirst" value="${pageLinkSub}">
		<c:param name="pageNumber" value="1"/>
	</c:url>
	<a class="small" href='${pageLinkFinalFirst}'>first</a>
</c:if>
<c:if test="${param.pageNumber!=null}">
<c:if test="${param.pageNumber>1}">
	<c:url var="pageLinkFinalPrev" value="${pageLinkSub}">
		<c:param name="pageNumber" value="${pagingUtil.currentPageNo}"/>
	</c:url>
	<a class="small" href="${pageLinkFinalPrev}"'>prev</a>&nbsp;
</c:if>
</c:if>


<c:if test="${pagingUtil.hasLeftDots}">
	<strong>...</strong>
</c:if>

<c:forEach items="${pagingUtil.pages}" var="pageNum">
	<c:choose>
		<c:when test="${param.pageNumber==null && pageNum eq pagingUtil.currentPageNo}">
		  <c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>
			<a class="activeMe" href='${pageLinkFinal}' >${pageNum}</a>
		</c:when>
		<c:when test="${param.pageNumber==1 && pageNum eq pagingUtil.currentPageNo}">
		  <c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>
			<a class="activeMe" href='${pageLinkFinal}' >${pageNum}</a>
		</c:when>
		<c:when test="${param.pageNumber!=null && pageNum eq pagingUtil.currentPageNo+1 && param.pageNumber!=1 }">
		  <c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>
			<a class="activeMe" href='${pageLinkFinal}' >${pageNum}</a>
		</c:when>
		<c:otherwise>
			<c:url var="pageLinkFinal" value="${pageLinkSub}">
				<c:param name="pageNumber" value="${pageNum}"/>
			</c:url>
			<a class="small"  href='${pageLinkFinal}'>${pageNum}</a>
		</c:otherwise>
	</c:choose>
</c:forEach>

<c:if test="${pagingUtil.hasRightDots}">
	<strong>...</strong>
</c:if>

<c:if test="${pagingUtil.hasNext}">
	 <c:url var="pageLinkFinalNext" value="${pageLinkSub}">
	 		<c:if test="${param.pageNumber!=null}">
				<c:param name="pageNumber" value="${param.pageNumber + 1}"/>
			</c:if>
			<c:if test="${pagingUtil.showLast && param.pageNumber==null}">
				<c:param name="pageNumber" value="${pagingUtil.currentPageNo + 1}"/>
			</c:if>
		</c:url>
	<a class="small" href="${pageLinkFinalNext}"'>next</a>
</c:if>

<c:if test="${pagingUtil.showLast}">
	 <c:url var="pageLinkFinalLast" value="${pageLinkSub}">
			<c:param name="pageNumber" value="${pagingUtil.totalPages}"/>
		</c:url>
	&nbsp;<a class="small" href="${pageLinkFinalLast}"'>last</a>
</c:if>
</span>
