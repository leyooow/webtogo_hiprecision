<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="order" />
<c:set var="submenu" value="area"/>
<c:set var="pagingAction" value="area.do" />

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRFormSubmissionAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script> 
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
  function showContent(id) {
    var content = document.getElementById('showContent_'+id).innerHTML;
    try{
      overlib(content, STICKY, NOCLOSE);
    }catch(e){
      alert(e + "\nContent:\n" + content);
    }
  }

  function submitForm(myForm) {
	var areaname = document.getElementById('areaname').value;
	var description = document.getElementById('description').value;
	var error = false;
	var messages = "Problem(s) occured: \n\n";
	
	if(areaname.length == 0) {
	  error = true;
	  messages += " * Area Name is not specified. \n";
	} 

	if(error) {
	  alert(messages);
	}
	else {
	  return true;
	}
		
	return false;
  }
</script>

<body>
  <div class="container">
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>
	
    <div class="contentWrapper" id="contentWrapper">
      <div class="mainContent">
	 	<s:actionmessage />
		<s:actionerror />
				
	    <div class="pageTitle">
	      <h1><strong>Area</strong>: View Areas</h1>
		  
		  <div class="clear"></div>
	    </div><!--//pageTitle-->

	    <ul class="pagination">
	      <%@include file="includes/pagingnew.jsp"  %>
	    </ul>
			
		<div class="clear"></div>

	    <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
		  <tr class="headbox"> 
		    <th>Area Name</th>
		    <th>Description</th>  
		    <th>Action</th>
		  </tr>
		  <c:set var="count" value="0" />
		  <c:forEach items="${areas}" var="cp"  varStatus="counter">
		    <tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
			  <c:set var="count" value="${count+1}" />
			  <td>${cp.areaName}</td>
			  <td>${cp.description}</td>
  			  <td>					
				<a href="editarea.do?area_id=${cp.id}">Edit</a>
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
				  |
				  <a href="deletearea.do?area_id=${cp.id}"  onclick="return confirm('Do you really want to delete area ${member.username}?');">Delete</a>
				</c:if>		
			  </td>
			</tr>
		  </c:forEach>
		</table>

		<ul class="pagination">
	      <%@include file="includes/pagingnew.jsp"  %>
	    </ul>
	  </div><!--//mainContent-->
    </div>
    
    <form method="post" action="savearea.do" onsubmit="return submitForm(this);">
      <div class="sidenav">
        <h2>New Area</h2>
        <table border="0">
          <tr>
            <td>Area Name:</td><td><input id="areaname" name="areaname" type="text"/></td>
          </tr>
          <tr>
            <td>Description:</td><td><input id="description" name="description" type="text"/></td>
          </tr>
          <tr>
	        <td><input type="submit" value="Add" class="btnBlue"></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr> 
        </table>
      </div>
    </form>

	<div class="clear"></div>

  </div><!--//container-->
</body>
</html>