<!-- META tags info are obtained from the CMS -->
<!-- If meta tag is not set, the name of the company will be shown. -->
<c:choose>
	<c:when test="${company.id eq 207}"> <%-- Company Folder : louis --%>
		<meta name="robots" content="index,follow" />
		<meta name="GOOGLEBOT" content="INDEX, FOLLOW" />
		<meta name="Designer" content="WebToGo Philippines" />
		<meta name="revisit-after" content="7 days" />
	</c:when> 
	<c:when test="${company.id eq 345 and page.title eq 'Contact Us'}">
		<meta name="robots" content="noindex,nofollow" />
		<meta name="ROBOTS" content="NOINDEX,NOFOLLOW" />
		<meta name="robots" content="noarchive" />
		<META NAME="ROBOTS" CONTENT="NOARCHIVE">
		<meta name="Author" content="WebToGo Philippines, www.webtogo.com.ph" />
		<meta name="web_author" content="WebToGo Philippines, www.webtogo.com.ph" />
		<meta name="Designer" content="WebToGo Philippines" />
	</c:when>
	<c:otherwise>
		<meta name="robots" content="index,follow" />
		<meta name="ROBOTS" content="INDEX, FOLLOW" />
		<meta name="Author" content="WebToGo Philippines, www.webtogo.com.ph" />
		<meta name="web_author" content="WebToGo Philippines, www.webtogo.com.ph" />
		<meta name="Designer" content="WebToGo Philippines" />
	</c:otherwise>
	
</c:choose>

<%--Dec_14_2012_edited: Changed the order/sequence of <c:when> Display/Check title of root/child/inner Page first(if not empty), then to Parent --%>
<title><c:choose><c:when test="${not empty selectedPage.meta_title}">${selectedPage.meta_title }</c:when><c:when test="${not empty item.name}">${company.title}${' - '}${item.parent.name}${' - '}${item.name }<c:if test="${not empty item.sku }">${' - '}${item.sku }</c:if></c:when><c:when test="${not empty page.meta_title}">${page.meta_title}</c:when><c:otherwise><c:choose><c:when test="${fn:length(company.title) gt 0}">${company.title}</c:when><c:otherwise>${company.nameEditable}</c:otherwise></c:choose><c:choose><c:when test="${not empty item.name}">${company.title}${' - '}${item.parent.name}${' - '}${item.name }<c:if test="${not empty item.sku }">${' - '}${item.sku }</c:if></c:when><c:when test="${not empty category.name}">${' - '}${category.name }</c:when><c:when test="${not empty group.name}">${' - '}${group.name }</c:when><c:when test="${not empty page.title}">${' - '}${page.title} <c:if test='${fn:length(selectedPage.name)>0}'>${' - '} ${selectedPage.name }</c:if></c:when></c:choose></c:otherwise></c:choose></title>

<meta name="title" content="<c:choose><c:when test="${not empty selectedPage.meta_title}">${selectedPage.meta_title }</c:when><c:when test="${not empty page.meta_title}">${page.meta_title}</c:when><c:otherwise><c:choose><c:when test="${fn:length(company.title) gt 0}">${company.title}</c:when><c:otherwise>${company.nameEditable}</c:otherwise></c:choose><c:choose><c:when test="${not empty item.name}">${' - '}${item.name }<c:if test="${not empty item.sku }">${' - '}${item.sku }</c:if></c:when><c:when test="${not empty category.name}">${' - '}${category.name }</c:when><c:when test="${not empty group.name}">${' - '}${group.name }</c:when><c:when test="${not empty page.title}">${' - '}${page.title}</c:when></c:choose></c:otherwise></c:choose>"/>

<c:choose>
<c:when test="${(company.companySettings.hasMetaPage eq true) && (not empty selectedPage.meta_description) }"><meta name="description" content="${selectedPage.meta_description }"/></c:when>
<c:when test="${(company.companySettings.hasMetaPage eq true) && (not empty page.meta_description) }"><meta name="description" content="${page.meta_description }"/></c:when>
<c:when test="${not empty category.description }"><meta name="description" content="${category.metaDescription}"/></c:when>
<c:when test="${not empty item.shortDescription }"><meta name="description" content="${item.metaDescription }"/></c:when>
<c:when test="${company.metaDescription != ''}"><meta name="description" content="${company.metaDescription }"/></c:when>
<c:otherwise><meta name="description" content="${company.nameEditable}"/></c:otherwise>
</c:choose>

<c:if test="${company.id ne 345}">
	<c:choose>
	<c:when test="${(company.companySettings.hasMetaPage eq true) && (not empty selectedPage.meta_keywords) }"><meta name="keywords" content="${selectedPage.meta_keywords }"/></c:when>
	<c:when test="${(company.companySettings.hasMetaPage eq true) && (not empty page.meta_keywords) }"><meta name="keywords" content="${page.meta_keywords }"/></c:when>
	<c:when test="${(not empty item.brand.name) || (not empty item.parent.name) }"><c:set var="categories" scope="session" value="${item.parent.descriptor}"/><c:set var="keyword" value="${item.parent.name}${fn:replace(categories, ' <<', ',')}"/><c:if test="${not empty item.brand.name }"><c:set var="keyword" value="${keyword}${', '}${item.brand.name}"/></c:if><meta name="keywords" content="${keyword}"/></c:when>
	<c:when test="${not empty category.descriptor}"><c:set var="categories" scope="session" value="${category.descriptor}"/><c:set var="keyword" value="${category.name}${fn:replace(categories, ' <<', ',')}"/><meta name="keywords" content="${keyword}"/></c:when>
	<c:when test="${company.keywords != ''}"><meta name="keywords" content="${company.keywords }"/></c:when>
	<c:otherwise><meta name="keywords" content="${company.nameEditable }"/></c:otherwise>
	</c:choose>
</c:if>

<c:choose>
<c:when test="${not empty page.meta_author}"><meta name="author" content="${page.meta_author}"/></c:when>
<c:otherwise><c:choose>
<c:when test="${not empty company.metaAuthor}"><meta name="author" content="${company.metaAuthor}"/></c:when>
<c:otherwise><meta name="author" content="${company.nameEditable }"/></c:otherwise>
</c:choose></c:otherwise>
</c:choose>

<c:choose>
<c:when test="${not empty page.meta_copyright}"><meta name="copyright" content="${page.meta_copyright}"/></c:when>
<c:otherwise><c:choose>
<c:when test="${not empty company.metaCopyright}"><meta name="copyright" content="${company.metaCopyright}"/></c:when>
<c:otherwise><meta name="copyright" content="${company.nameEditable }"/></c:otherwise>
</c:choose></c:otherwise>
</c:choose>
<!-- End of global meta -->