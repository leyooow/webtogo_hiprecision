
			
			<tr>
			<td>
			<c:if test="${displayRepeatLabel == null or displayRepeatLabel }">
			Repeats Every:
			</c:if>
			</td>
				<td align="left" colspan="2">
				<c:if test="${sched ne null}">
					<input type="hidden" name="itemScheduleId" value="${sched.id}">
				</c:if>
				<br>
				<c:forEach begin="0" end="${fn:length(dayArray)-1}" var="i" step="1">
					<input type="checkbox" <c:if test="${sched ne null and fn:contains(sched.dailySchedule, fn:toLowerCase(dayArray[i]))}">checked</c:if>   name="${fn:toLowerCase(dayArray[i])}" id="${fn:toLowerCase(dayArray[i])}" value="${dayArray[i]}"/>&nbsp;${dayArray[i]}&nbsp;&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
			<td class="label"></td>
			<td  align="left" colspan="2">&nbsp;&nbsp;
			<br><b>Start time:<b>&nbsp;&nbsp;&nbsp;

			&nbsp;&nbsp;&nbsp;<select name="startTime"  id="startingTime" style="width:4em;">
			<c:forEach var="i" begin="1" end="12" step="1">
				<option value="${i}" <c:if test="${sched ne null and sched.startTime eq i}">selected</c:if>>
				${i}
				</option>
			</c:forEach>
			</select> &nbsp;
			<select name="startTimePost" id="startTimePost">
				<option value="AM" <c:if test="${sched ne null and sched.startTimePost eq 'AM'}">selected</c:if>>AM</option>
				<option value="PM" <c:if test="${sched ne null and sched.startTimePost eq 'PM'}">selected</c:if>>PM</option></select>
			<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End time:</b> 
			&nbsp;&nbsp;&nbsp;
			<select name="endingTime" id="endingTime" style="width:4em;">
			<c:forEach var="i" begin="1" end="12" step="1">
				<option value="${i}" <c:if test="${sched ne null and sched.endTime eq i}">selected</c:if>>
				${i}
				</option>
			</c:forEach>
			</select>&nbsp;
			<select name="endingTimePost" id="endingTimePost">
			<c:set var="lastPageNo" value="1"/>
				<option value="AM" <c:if test="${sched ne null and sched.endTimePost eq 'AM'}">selected</c:if>>AM</option>
				<option value="PM" <c:if test="${sched ne null and sched.endTimePost eq 'PM'}">selected</c:if>>PM</option>
			</select>
			</td>
			<c:if test="${displayRemove ne '' and displayRemove }">
				<td class="label">
					<a href="javascript:void(0)" onclick="return removeRow(this)">remove</a>
				</td>
			</c:if>
			</tr>
			
			
			