<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- <c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, req.contextPath)}" />
<c:url var="myUrl" value="${baseURL}/${MyID}"/>
<c:redirect url="${myUrl}/index.do" /> --%>

<c:redirect url="../../index.do" />