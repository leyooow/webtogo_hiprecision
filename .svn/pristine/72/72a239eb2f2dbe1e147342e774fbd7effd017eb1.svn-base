<%@include file="includes/header.jsp"  %>

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" /> 
<c:set var="submenu" value="attributes" />
<c:set var="firstParamGiven" value="${true}" />
	<c:set var="formAction" value="saveattribute.do" />
				<c:set var="newAttribute" value="${true}" />
				
<c:set var="menu" value="attributes" />

<script src="../admin/javascripts/prototype.js" type="text/javascript"></script>
<script src="../admin/javascripts/scriptaculous.js" type="text/javascript"></script>
<script language="javascript" src="../javascripts/upload.js"></script>

<script>
	
	function submitForm(myForm) {
		
		var name = getElement('attribute_name');
		
		var error = false;
		var messages = "Problem(s) occured: \n\n";
		
		if(name.length == 0) {
			messages += "* Please enter a attribute name\n";
			error = true;
		}
		
		if(error) {
			alert(messages);
		}
		else {
			return true;
		}
		
		return false;
	}
	
	function getElement(elementName) {
		var value = document.getElementById(elementName).value;
		//value = value.replace(/^\s+|\s+$/, '');
		return value;
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
			<c:if test="${param['evt'] != null}">
					<ul>
						<c:if test="${param['evt'] == 'save'}">
							<li><span class="actionMessage">Attribute was successfully created.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'attributeexist'}">
							<li><span class="actionMessage">Attribute was not created because a similar attribute already exist.</span></li>
						</c:if>
													
						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Attribute was sucessfully updated.</span></li>
						</c:if>
						
						<c:if test="${param['evt'] == 'delete'}">
							<li><span class="actionMessage">Attribute was sucessfully deleted.</span></li>
						</c:if>
					</ul>
				</c:if>
				
	  <div class="pageTitle">

	    <h1><strong>Attributes</strong></h1>
	</form>
				
		<div class="clear"></div>

	  </div><!--//pageTitle-->

	  <ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
			
				  <div class="clear"></div>
 <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable"> 
				
					
					<tr> 
						<th>Attribute Name</th>
						<th>Description</th>
						<th></th>
					</tr>
<c:set var="count" value="0" />
					<c:forEach items="${attributes}" var="attribute">
						<tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
					<c:set var="count" value="${count+1}" />
						<td width="25%">${attribute.name}</td>
						<td width="50%">${attribute.description}</td>
						<td width="25%">
						<a href="editattribute.do?attribute_id=${attribute.id}&group_id=${group.id}">Edit</a> |
						<a href="deleteattribute.do?attribute_id=${attribute.id}&group_id=${group.id}" onclick="return confirm('Do you really want to delete this attribute?');">Delete</a>
						</td>
					</tr>
					</c:forEach>
					
					<c:if test="${empty attributes}">
					<tr> 
					<td rowspan=3>*No created attributes.</td>
					</tr>
					</c:if>
				</table> 
			
			<ul class="pagination">

	   <%@include file="includes/pagingnew.jsp"  %>

	  </ul>
	</div><!--//mainContent-->
<c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator'}">
				
	<div class="sidenav">
		
	 <h2>New Attribute</h2>
		<form method="post" action="${formAction}" onsubmit="return submitForm(this);">
				
		<input type="hidden" name="group_id" value="${group.id}" />
		<input type="hidden" id="attribute_id" name="attribute_id" value="${attribute.id}" />
		<table width="100%">
			
			
			<tr>
				<td>Attribute Name<br /><input type="text" id="attribute_name" name="attribute_name" class="w200" value="${attribute.name}"/></td>
			</tr>
			<tr>
				<td >Attribute Description<br />
					<textarea name="attribute_description" class="w200">${attribute.description}</textarea>
				</td>
			</tr>
			<tr>
				<td >Data Type<br />
				<input type="radio" name="datatype" value="string" checked/> String<br>
				<input type="radio" name="datatype" value="numeric" <c:if test="${attribute.dataType ne null}"><c:if test="${attribute.dataType == 'numeric'}">checked</c:if></c:if>/> Numeric
				</td>
			</tr>		
			<tr>
				<td>
				<c:choose>
					<c:when test="${attribute.id > 0}">
						<input type="submit" name="submit" value="Update" class="btnBlue">
						<input type="reset" value="Reset" class="btnBlue"> 
						<input type="button" value="Cancel" class="btnBlue" onclick="window.location='attributes.do?group_id=${group.id}&category_id=${category.id}';"> 
					</c:when>
					<c:otherwise>
						<input type="submit" name="submit" value="Add" class="btnBlue">
					</c:otherwise>
				</c:choose>
				</td>
			</tr> 
			
		</table>
		
		</form>
		
	</div><!--//sidenav-->
	</c:if>
</div>
		
	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>