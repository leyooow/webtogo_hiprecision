
			
			<tr>
			<td>
			<c:if test="${displayRepeatLabel == null or displayRepeatLabel }">
			Repeats Every:
			</c:if>
			</td>
				<td align="left" colspan="2">
				<br>
				<input type="hidden" name="SinglePage.isRockwellBranch" value="yes">
				<c:forEach begin="0" end="${fn:length(dayArray)-1}" var="i" step="1">
					<input <c:if test="${fn:indexOf(singlePage.daysAvailable,dayArray[i])>=0}"> checked </c:if> type="checkbox" name="SinglePage.checkBoxDays" value="${dayArray[i]}"/>&nbsp;${dayArray[i]}&nbsp;&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
			<td class="label"></td>
			<td  align="left" colspan="2">&nbsp;&nbsp;
			<br><b>Start time:<b>&nbsp;&nbsp;&nbsp;

			&nbsp;&nbsp;&nbsp;<select name="SinglePage.startingTime"  id="startingTime" style="width:4em;">
			<c:forEach var="i" begin="1" end="12" step="1">
				<option value="<c:choose><c:when test="${i ne _startTime or i+12 ne _startTime}">${i}</c:when><c:otherwise>${i}</c:otherwise></c:choose>" <c:if test="${i+12 eq _startTime}"> selected </c:if><c:if test="${i eq _startTime}"> selected </c:if>>
				${i}
				</option>
			</c:forEach>
			</select> &nbsp;
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
			</select>&nbsp;
			<select name="ePmAm" id="ePmAm">
			<c:set var="lastPageNo" value="1"/>
			<<option value="AM" <c:if test="${_startTime le 11}">selected</c:if>>AM</option>
			<option value="PM" <c:if test="${_startTime ge 12}">selected</c:if>>PM</option>
			</select>
			</td>
			<c:if test="${displayRemove ne '' and displayRemove }">
				<td class="label">
					<a href="javascript:void(0)" onclick="return removeRow(this)">remove</a>
				</td>
			</c:if>
			</tr>
			
			
			