	
<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
		<c:if test="${singlePage.id > 0}">
		<input type="hidden" name="update" value="1">
		<input type="hidden" name="singlepage_id" value="${singlePage.id}">
		</c:if>
		
		<c:if test="${multiPage != null}">
		<input type="hidden" name="multipage_id" value="${multiPage.id}">
		</c:if>
		
	  <table width="100%" border="0" cellspacing="0" cellpadding="3">

	    <tr>

		  <td class="label">Name</td>

		  <td align="left"><input type="text" id="singlepage_name" name="singlePage.name" value="${singlePage.name}" class="inputContent" /></td>

		</tr>
		<c:if test="${singlePage.language== null}"> 
				<c:if test="${(user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator') && multiPage == null}">
		<tr>
			
		  <td class="label">JSP</td>

		   <td align="left"><input type="text" id="singlepage_jsp" name="singlePage.jsp" value="${singlePage.jsp}" class="inputContent" /></td>

		</tr>
		</c:if></c:if>
		<input type="hidden" name="multipageID" id="multipageID" VALUE="${multiPage.id}" />
				<%-- <c:if test="${multiPage.id== 1111}"> 1097 local ONLY--><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID --%>
			<c:if test="${  singlePage.docRoom == null and singlePage.startTime == null  and multiPage.id  != 1111 }"> 
		<tr>

		  <td class="label">Title</td>

		   <td align="left"><input type="text" id="singlepage_title"  name="singlePage.title" value="${singlePage.title}" class="inputContent" /></td>

		</tr>
		
		<tr>

		  <td class="label">Sub Title</td>

		   <td align="left"><input type="text" id="singlepage_name"  name="singlePage.subTitle" value="${singlePage.subTitle}" class="inputContent" /></td>

		</tr>
		</c:if>
		<c:if test="${singlePage.language== null}"> 
		<tr>

		  <td class="label">Date</td>

		   <td align="left">
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
									 
					<input type="text" id="item_date" name="itemDate" value="${idate}" class="inputContent" /> 
					<a href="javascript:;" id="item_date_button"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>
					   
					  
					<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "item_date",     // id of the input field
					        ifFormat       :    "%m-%d-%Y",      // format of the input field
					        button         :    "item_date_button",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true
					    });
					</script>
		</td>

		</tr>
		</c:if>
		

	   	<c:if test="${(singlePage.id > 0 && multiPage == null && (user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'))}">
				<tr>
					 <td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.isHomeC" value="%{singlePage.isHome}" theme="simple" />
					
					This page is the default homepage.</td>
					
				</tr>
				</c:if>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.isSingleFeaturedC" value="%{singlePage.isSingleFeatured}" theme="simple" />
					
					This page is a featured page.</td>

					
				</tr>
				</c:if>
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.isFormC" value="%{singlePage.isForm}" theme="simple" />
				This is a form page.</td>

					
				</tr>
				</c:if>
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.hiddenC" value="%{singlePage.hidden}" theme="simple" />
					Hide this page in the website.</td>

					
				</tr>
				</c:if>
				
				
				<c:if test="${user.userType.value == 'Super User' or user.userType.value == 'WTG Administrator'}">
				<tr>
					<td class="label"></td>
					<td>
					
					<s:checkbox name="singlePage.loginRequiredC" value="%{singlePage.loginRequired}" theme="simple" />
					
					This page requires member login.</td>

					
				</tr>
			</c:if>
	 
		
		<tr>

		  <td class="label">Content</td>

		   <td align="left">
		 
		  <textarea id="singlePage.content" name="singlePage.content" >${singlePage.content}</textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'singlePage.content');
			</script>
		  </td>

		</tr><tr>
			
		  <td colspan="2" class="label">
			<c:choose>
						<c:when test="${singlePage.id > 0}">
						
							<input type="reset" value="Reset" class="btnBlue">
				<input type="submit" value="Update" class="btnBlue">
						</c:when>
						<c:otherwise>
							
						
				<input type="submit" value="Add" class="btnBlue">
						</c:otherwise>
					</c:choose>
		</td>

		</tr>

	  </table>
</form>			