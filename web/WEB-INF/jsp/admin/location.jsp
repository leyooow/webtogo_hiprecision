<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="order" />
<c:set var="submenu" value="arealocation"/>
<c:set var="pagingAction" value="location.do" />

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
	var areaID = document.getElementById('areaID').value;
	var locationname = document.getElementById('locationname').value;
	var rate = document.getElementsByName('rate');
	var hasblankrate = false;
	var error = false;
	var messages = "Problem(s) occured: \n\n";

	if(areaID.length == 0) {
	  error = true;
	  messages += " * Area Name is not specified. \n";
	} 

	if(locationname.length == 0) {
	  error = true;
	  messages += " * Location Name is not specified. \n";
	}

	for(var i=0; i<rate.length; i++)
	{
	  if(rate[i].value == "" || isNaN(rate[i].value))
	  {
	    error = true;
	    hasblankrate = true;
	  }
	}

	if(hasblankrate)
	{
	  messages += " * One of the Rates Per Unit is not specified or invalid.";
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
	      <h1><strong>Location</strong>: View Locations</h1>
		  
		  <div class="clear"></div>
	    </div><!--//pageTitle-->

	    <ul class="pagination">
	      <%@include file="includes/pagingnew.jsp"  %>
	    </ul>
			
		<div class="clear"></div>

	    <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
		  <tr class="headbox"> 
		    <th>Area Name</th>
		    <th>Location Name</th>
		    <th>Description</th>  
		    <th>Action</th>  
		  </tr>
		  <c:set var="count" value="0" />
		  <c:forEach items="${locations}" var="cp"  varStatus="counter">
		    <tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
			  <c:set var="count" value="${count+1}" />
			  <td>${cp.ossShippingArea.areaName}</td>
			  <td>${cp.locationName}</td>
			  <td>${cp.description}</td>
  			  <td>					
				<a href="editlocation.do?location_id=${cp.id}">Edit</a>
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator'}">
				  |
				  <a href="deletelocation.do?location_id=${cp.id}"  onclick="return confirm('Do you really want to delete location ${member.username}?');">Delete</a>
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
    
    <form method="post" action="savelocation.do" onsubmit="return submitForm(this);">
      <div class="sidenav">
        <h2>New Location</h2>
        <table border="0">
          <tr>
            <td>Area Name:</td>
            <td>
              <select id="areaID" name="areaID">
                <option value="">-Select Area-</option>
                <c:forEach items="${areas}" var="a">
                  <option value="${a.id}">${a.areaName}</option>
                </c:forEach>
              </select>
			</td>
          </tr>
          <tr>
            <td>Location Name:</td><td><input id="locationname" name="locationname" type="text"/></td>
          </tr>
          <tr>
            <td>Description:</td><td><input id="description" name="description" type="text"/></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <c:if test="${company.name ne 'mraircon'}">
	          <tr>
	            <td><strong>Volume Weight</strong></td><td><strong>Rates Per Unit</strong></td>
	          </tr>                    
	          <tr>
	            <td>1 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="1"/></td>
	          </tr>
	          <tr>
	            <td>2 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="2"/></td>
	          </tr>
	          <tr>
	            <td>3 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="3"/></td>
	          </tr>
	          <tr>
	            <td>4 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="4"/></td>
	          </tr>
	          <tr>
	            <td>5 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="5"/></td>
	          </tr>
	          <tr>
	            <td>6 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="6"/></td>
	          </tr>
	          <tr>
	            <td>16 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="16"/></td>
	          </tr>
	          <tr>
	            <td>27 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="27"/></td>
	          </tr>
	          <tr>
	            <td>37 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="37"/></td>
	          </tr>
	          <tr>
	            <td>52 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="52"/></td>
	          </tr>
	          <tr>
	            <td>111 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="111"/></td>
	          </tr>
	          <tr>
	            <td>117 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="117"/></td>
	          </tr>
	          <tr>
	            <td>264 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="264"/></td>
	          </tr>
	          <tr>
	            <td>626 kls:</td><td><input id="rate" name="rate" type="text"/><input type="hidden" name="weight" value="626"/></td>
	          </tr>
          </c:if>          
          <tr>          
	        <td>
		        <c:if test="${company.name eq 'mraircon'}">
	          		<input id="rate" name="rate" type="hidden" value="0"/><input type="hidden" name="weight" value="1"/>
	          	</c:if>
	        	<input type="submit" value="Add" class="btnBlue">
	        </td>
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