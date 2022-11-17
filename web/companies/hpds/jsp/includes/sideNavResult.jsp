<td class="sidenavContainer">
		    <div class="sidenav">
			  <!-- <div class="searchContainer">
			    <div><img src="images/d.gif" /></div>
				<div class="searchForm">
				  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				    <form name="search" id="search" action="search.do">
					  <td>Find &nbsp;</td>
					  <td><input name="keyword" type="text" class="inputSearch" value="${keyword}"/></td>
					  <td><input type="image" src="images/btnGo.gif"/></td>
					  </form>
					</tr>
				  </table>
				</div>
			    <div><img src="images/d3.gif" /></div>
			  </div> -->
			  <%-- <h1><span>(${fn:length(searchList)} items found)</span></h1>
			  <ul class="searchResults">
				<c:forEach items="${searchList}" var="sl">
					 <li><img src="images/oval_top.png" class="itl" /><img src="images/oval_bottom.png" class="ibl" /><a <c:if test="${sl.id eq item.id}">class="active" </c:if> href="/search.do?keyword=${keyword}&item_id=${sl.id}">${sl.name}</a></li>
				</c:forEach>
			  </ul> --%>
			  	<h1>
			  		<c:choose>
			  			<c:when test="${pagingUtilDesc.totalItems gt 0}">
			  				<span>(${pagingUtilDesc.totalItems} items found for "${keyword}")</span>
			  			</c:when>
			  			<c:otherwise>
			  				<c:if test="${keyword ne null and pagingUtilDesc.totalItems eq 0}"><span>Your search did not match any items</span></c:if>
			  				<c:if test="${keyword eq null}"><span>Please enter a Search term to begin.</span></c:if> 
			  			</c:otherwise>
			  		</c:choose>
			  		
			  	</h1>
			  	<br/>
			  	<c:if test="${pagingUtilDesc.totalPages gt 1}">
						<c:set var="pagingAction" value="${menu['search'].url}?keyword=${keyword}"/>													
						<div align="center">
			        		Page ${pagingUtilDesc.currentPageNo} of ${pagingUtilDesc.totalPages} <img src="images/iPage.gif" align="absmiddle" />&nbsp;
			        		<c:if test="${param.pageNumber gt 1}">
				        		<a href="${pagingAction}&pageNumber=1">&laquo; First</a> | &nbsp
				        		<a href="${pagingAction}&pageNumber=${param.pageNumber-1}">&laquo; Previous</a> | &nbsp
			        		</c:if>
			        		
							<c:forEach items="${pagingUtilDesc.pages}" var="pageNum">	 
								<c:set value="${fn:length(pagingUtilDesc.pages)}" var="total" />
								<c:choose>											
									<c:when test="${pagingUtilDesc.currentPageNo!= pageNum }">
										<a href="${pagingAction}&pageNumber=${pageNum}&tpages=${total}">${pageNum}</a>					
									</c:when>  
									<c:otherwise>
										<span style="color: red;">[${pageNum}]</span> 
									</c:otherwise> 
								</c:choose>		 
									<span class="small">-</span>			  
							</c:forEach>
							
							<c:if test="${param.pageNumber ne pagingUtilDesc.totalPages}">	
								| <a href="${pagingAction}&pageNumber=${not empty param.pageNumber ? param.pageNumber+1 : 2}&tpages=${pagingUtilDesc.totalPages}">Next &raquo;</a>
							 	| <a href="${pagingAction}&pageNumber=${pagingUtilDesc.totalPages}&tpages=${pagingUtilDesc.totalPages}">Last &raquo;</a>
							</c:if>	
						</div>
					</c:if>
			  	
			  	<c:choose>
			  		<c:when test="${not empty searchListWithPaging}">
					  	<ul class="searchResults">
							<c:forEach items="${searchListWithPaging}" var="sl">
								<c:choose>
									<c:when test="${sl.parentGroup.id eq 159}">
										<li class="icobranch">
									</c:when>
									<c:when test="${sl.parentGroup.id eq 158}">
									 	<li class="icofaq">
									</c:when>
									<c:when test="${sl.parentGroup.id eq 160}">
										<li class="icotestprocedure">
									</c:when>
									<c:when test="${sl.parentGroup.id eq 162}">
										<li class="icodoctor">
									</c:when>
									<c:when test="${sl.parentGroup.id eq 163}">
										<li class="icofaq">
									</c:when>
									<c:when test="${sl.parentGroup.id eq 164}">
										<li class="icovaccine">
									</c:when>
									<c:when test="${sl.parentGroup.id eq 165}">
										<li class="icohmo">
									</c:when>
									<c:otherwise>
									 	<li class="icopackage">
									</c:otherwise>
								</c:choose>
								<img src="images/oval_top.png" class="itl" /><img src="images/oval_bottom.png" class="ibl" /><a <c:if test="${sl.id eq item.id}">class="active" </c:if> href="${menu['search'].url}?keyword=${keyword}&item_id=${sl.id}&pageNumber=${pagingUtilDesc.currentPageNo}"><c:if test="${sl.parentGroup.id eq 158}">FAQ: </c:if>[${sl.sku}] - ${sl.name}</a></li>								
							</c:forEach>
					  	</ul>					  	
					  </c:when>
					  <c:otherwise>
					  	<ul class="searchResults">
							<c:forEach items="${searchListIncludePackageWithPaging}" var="sl">
								<c:choose>
									<c:when test="${sl.parentGroupId eq 159}">
										<li class="icobranch">
										<c:set var="name" value="${sl.name}"/>
									</c:when>
									<c:when test="${sl.parentGroupId eq 158}">
									 	<li class="icofaq">
									 	<c:set var="name" value="${sl.name}"/>
									</c:when>
									<c:when test="${sl.parentGroupId eq 160}">
										<li class="icotestprocedure">
										<c:set var="name" value="[${sl.sku}] - ${sl.name}"/>
									</c:when>
									<c:when test="${sl.parentGroupId eq 162}">
										<li class="icodoctor">
										<c:set var="name" value="${sl.name}"/>
									</c:when>
									<c:when test="${sl.parentGroupId eq 163}">
										<li class="icofaq">
										<c:set var="name" value="${sl.name}"/>
									</c:when>
									<c:when test="${sl.parentGroupId eq 164}">
										<li class="icovaccine">
										<c:set var="name" value="${sl.name}"/>
									</c:when>
									<c:when test="${sl.parentGroupId eq 165}">
										<li class="icohmo">
										<c:set var="name" value="${sl.name}"/>
									</c:when>
									<c:otherwise>
									 	<li class="icopackage">
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${sl.parentGroupId eq 159 or sl.parentGroupId eq 158 or sl.parentGroupId eq 160 or sl.parentGroupId eq 162 or sl.parentGroupId eq 163 or sl.parentGroupId eq 164 or sl.parentGroupId eq 165}">
										<img src="images/oval_top.png" class="itl" /><img src="images/oval_bottom.png" class="ibl" /><a <c:if test="${sl.id eq item.id}">class="active" </c:if> href="${menu['search'].url}?keyword=${keyword}&item_id=${sl.id}&pageNumber=${pagingUtilDesc.currentPageNo}">${name}</a></li>
									</c:when>
									<c:otherwise>
										<img src="images/oval_top.png" class="itl" /><img src="images/oval_bottom.png" class="ibl" /><a <c:if test="${sl.id eq selectedPackage.id}">class="active" </c:if> href="${menu['search'].url}?keyword=${keyword}&package_id=${sl.id}&pageNumber=${pagingUtilDesc.currentPageNo}">${sl.name}</a></li>		
									</c:otherwise>
								</c:choose>																
							</c:forEach>
					  	</ul>					  	
					  </c:otherwise>
				</c:choose>
				<br/>
				
				<c:if test="${pagingUtilDesc.totalPages gt 1}">
					<c:set var="pagingAction" value="${menu['search'].url}?keyword=${keyword}"/>													
					<div align="center">
		        		Page ${pagingUtilDesc.currentPageNo} of ${pagingUtilDesc.totalPages} <img src="images/iPage.gif" align="absmiddle" />&nbsp;
		        		<c:if test="${param.pageNumber gt 1}">
			        		<a href="${pagingAction}&pageNumber=1">&laquo; First</a> | &nbsp
			        		<a href="${pagingAction}&pageNumber=${param.pageNumber-1}">&laquo; Previous</a> | &nbsp
		        		</c:if>
		        		
						<c:forEach items="${pagingUtilDesc.pages}" var="pageNum">	 
							<c:set value="${fn:length(pagingUtilDesc.pages)}" var="total" />
							<c:choose>											
								<c:when test="${pagingUtilDesc.currentPageNo!= pageNum }">
									<a href="${pagingAction}&pageNumber=${pageNum}&tpages=${total}">${pageNum}</a>					
								</c:when>  
								<c:otherwise>
									<span style="color: red;">[${pageNum}]</span> 
								</c:otherwise> 
							</c:choose>		 
								<span class="small">-</span>			  
						</c:forEach>
						
						<c:if test="${param.pageNumber ne pagingUtilDesc.totalPages}">	
							| <a href="${pagingAction}&pageNumber=${not empty param.pageNumber ? param.pageNumber+1 : 2}&tpages=${pagingUtilDesc.totalPages}">Next &raquo;</a>
						 	| <a href="${pagingAction}&pageNumber=${pagingUtilDesc.totalPages}&tpages=${pagingUtilDesc.totalPages}">Last &raquo;</a>
						</c:if>	
					</div>
				</c:if>
				<c:if test="${shoppingCart.itemCount != 0 and member ne null}">
			  	<div style="padding:0; margin:10px 0 0 0;"><%@include file="quotelisting.jsp" %></div>
			  	</c:if>
			  </div><!--//sidenav-->				  
		  </td><!--//sidenavContainer-->