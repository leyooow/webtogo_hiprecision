<br />
	<div class="downloadsBox"> 
		${param.uploadstatus }
		<c:if test="${param.rowRead gt 0 }">
			<p>Number of member/s uploaded :  ${param.rowRead } <br />
			   Number of member/s inserted : ${param.insertSuccess } <br />	
			   Number of member/s ignored because of existing email address : ${param.ignoreRow } 
			</p>
		</c:if>
		
	</div>
<br />