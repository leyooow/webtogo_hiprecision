 <script>
 	function validate_search(){
 		var keyword = document.getElementById('keyword').value;
 		if(keyword.length < 2)
 		{ 
 	 		alert("keyword must be greater than 1 letter");
 	 		return false;
 		}
 		else{
 	 		return true;
 		}
 	}
 </script>
<div class="searchContainer" align="right"> 
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
		  <td>Search &nbsp;</td>
		  <form name="search" id="search" action="search.do" name="search" onsubmit="return validate_search();">
		  <td valign="top"><input name="keyword" id="keyword" type="text" class="inputSearch" value="${fn:escapeXml(keyword)}" /></td>
		  <td><input type="image" src="images/btnGo.gif" style="margin-left:5px"/></td>
		  </form>
		</tr>
	  </table>	
</div><!--//searchContainer-->
 
<ul class="nav">
  <li><a <c:if test="${navMenu eq 'search'}">class="active"</c:if> href="${menu['search'].url}">Search</a></li>
  <li><a <c:if test="${navMenu eq 'packages'}">class="active"</c:if> href="${menu['packages'].url}">Packages</a></li>
  <li><a <c:if test="${navMenu eq 'branches'}">class="active"</c:if> href="${menu['branches'].url}?category_id=2382">Branches</a></li>
  <li><a <c:if test="${navMenu eq 'faq'}">class="active"</c:if> href="${menu['faq'].url}?category_id=2381">FAQ</a></li>
  <li><a <c:if test="${navMenu eq 'doctors'}">class="active"</c:if> href="${menu['doctors'].url}?group_id=162">Doctors</a></li>
  <li><a <c:if test="${navMenu eq 'specializations'}">class="active"</c:if> href="${menu['specializations'].url}?group_id=162">Specializations</a></li>
  <li><a <c:if test="${navMenu eq 'vaccines'}">class="active"</c:if> href="${menu['vaccines'].url}?category_id=2393">Vaccines</a></li>
  <li><a <c:if test="${navMenu eq 'hmos'}">class="active"</c:if> href="${menu['hmos'].url}?category_id=2394">HMOs</a></li>
</ul>