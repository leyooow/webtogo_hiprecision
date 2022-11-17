			<!-- -------------------------------MODIFIED----------------------------------- -->
			
			<!-- IF --><!-- DAPAT NASA COMPANY SETTINGS ITO -->
			<%--<c:if test="${multiPage.id== 1111}"> LOCAL DATA  1097 --><!-- --1111 IS THE LIVE DATA FOR MULTI_PAGE_ID  --%>
			<!-- -->	
			<tr> 
				<td width="1%" nowrap><b>Category<a href="items.do?group_id=205&listOfAllDoctors=yes"></a></td>
				<input type='hidden' name='multipage_id' value='${multiPage.id}'>
				<td><strong>${singlePage.title}</strong>&nbsp;&nbsp;

				<select name="singlePage.title" onchange="loadDoctors(this)" id="specialization">
					<option value="No selected Specialization"  id="doctors">--Select Specialization--</option>
					<c:forEach items="${allCategories}" var="category">
						<option value="${category.name}"  id="doctors" <c:if test="${singlePage.title eq category.name}">selected</c:if>>${category.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<input id="_item_" type="hidden" value="<c:forEach items="${allItems}" var="item" >${item.name}--${item.parent.name} --${item.sku}____</c:forEach>" name="_item_">
			<c:forEach items="${allItems}" var="item" >
				<label id="branchId__${item.name}" style="visibility:hidden">${item.brand.id}</label>
				<label id="branchName__${item.name}" style="visibility:hidden">${item.brand.name}</label>
			</c:forEach>
			
			<tr>
				<td width="1%" nowrap><b>Item</td>
				<td width="10px"></td>
				<td><strong>${singlePage.subTitle}</strong>&nbsp;&nbsp;
				<select name="singlePage.subTitle" id="populateWithDocs" onchange="displayBranchOfSelectedDoctor()">
				<option value="No selected Doctor"  id="doctors">--Select Doctor--</option>
				<c:forEach items="${allItems}" var="item">
					<option onclick="javascript:alert('Yohantenma ${item.id}');" value="${item.name}"  <c:if test="${fn:indexOf(singlePage.subTitle,item.name)!=-1 or fn:indexOf(item.name,singlePage.subTitle)!=-1}">selected</c:if>>${item.name}</option>
				</c:forEach>
				</select>
				</td>
			<%
			String dayArray[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
			request.setAttribute("dayArray",dayArray);
			%>
			</tr>
			<tr> 
				<td width="1%" nowrap><b>
				Branch of this Doctor
				</td>
				<td width="10px"></td>
				<td>
				<input type="text" id="singlePage.docBranchName" name="singlePage.docBranchName" readOnly>
				<input type="hidden" id="singlePage.docBranch" name="singlePage.docBranch" readOnly>
				</td>
			</tr>
			<tr> 
				<td width="1%" nowrap><b>School</td>
				<td width="10px"></td>
				<td>
				<input type="text" name="singlePage.docSchool" VALUE="${singlePage.docSchool}">
				</td>
			</tr>
			<tr> 
				<td width="1%" nowrap><b>Room</td>
				<td width="10px"></td>
				<td><input type="text" name="singlePage.room" VALUE=""></td>
			</tr>
			<tr>
			<td width="1%" nowrap><b>Repeats Every:</b></td>
				<td width="10px"></td>
				<td>
				<c:forEach begin="0" end="${fn:length(dayArray)-1}" var="i" step="1">
					<input <c:if test="${fn:indexOf(singlePage.daysAvailable,dayArray[i])>=0}"> checked </c:if> type="checkbox" name="SinglePage.checkBoxDays" value="${dayArray[i]}"/>&nbsp;<b>${dayArray[i]}</b>&nbsp;&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
			<td width="1%" nowrap><b></td>
			<td width="10px"></td>
			<td>&nbsp;&nbsp;<b>Start time:<b>&nbsp;&nbsp;&nbsp;
			<input type="hidden" name="SinglePage.hasAdjustableSchedule" value="yes">
			&nbsp;&nbsp;&nbsp;<select name="SinglePage.startingTime"  id="startingTime" style="width:4em;">
			<c:forEach var="i" begin="1" end="12" step="1">
				<option value="<c:choose><c:when test="${i ne _startTime or i+12 ne _startTime}">${i}</c:when><c:otherwise>${i}</c:otherwise></c:choose>" <c:if test="${i+12 eq _startTime}"> selected </c:if><c:if test="${i eq _startTime}"> selected </c:if>>
				${i}
				</option>
			</c:forEach>
			</select> :
			<select name="sPmAmMinutes" id="sPmAmMinutes">
				<option value="00" selected>00</option>
				<option value="30">30</option>
			</select>
			<select name="sPmAm" id="sPmAm">
				<option value="AM" <c:if test="${_startTime le 12}">selected</c:if>>AM</option>
				<option value="PM" <c:if test="${_startTime ge 13}">selected</c:if>>PM</option>
			</select>
			<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End time:</b> 
			&nbsp;&nbsp;&nbsp;
			<select name="SinglePage.endingTime" id="endingTime" style="width:4em;">
			<c:forEach var="i" begin="1" end="12" step="1">
				<option value="<c:choose><c:when test="${i ne _endTime or i+12 ne _endTime}">${i}</c:when><c:otherwise>${i}</c:otherwise></c:choose>" <c:if test="${i+12 eq _endTime}"> selected </c:if><c:if test="${i eq _endTime}"> selected </c:if>>

				${i}
				</option>
			</c:forEach>
			</select>:
			<select name="ePmAmMinutes" id="ePmAmMinutes">
				<option value="00" selected>00</option>
				<option value="30">30</option>
			</select>
			<select name="ePmAm" id="ePmAm">
			<c:set var="lastPageNo" value="1"/>
			<<option value="AM" <c:if test="${_startTime le 11}">selected</c:if>>AM</option>
			<option value="PM" <c:if test="${_startTime ge 12}">selected</c:if>>PM</option>
			</select>
			</td>
			</tr>
		
			<!-- ELSE -->