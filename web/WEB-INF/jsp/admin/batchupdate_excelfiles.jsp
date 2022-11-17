<%@include file="includes/header.jsp"  %>
<c:set var="menu" value="groups" />
<link href="../admin/css/admin_new.css" rel="stylesheet" type="text/css" media="screen" />
<link href="../admin/css/company.css" rel="stylesheet" type="text/css" />
<body>
	<%@include file="includes/greentop.jsp"%>
	<div id="wrapper">
		<%@include file="includes/admintop.jsp"%>
		<div id="page">
			<div id="content">
				<div class="jobContainer">
					<div id="companyhome"> 
						<h3>Uploaded Excel Files for ${group.name}</h3>
						<br />
						<table style="width:50%">
							<tr class="headbox">
								<th>Filename  [<span title="Files uploaded with the same filename are overwritten">?</span>]</th>
								<th>User</th>
								<th>Date Uploaded  [<span title="Latest version">?</span>]</th>
							</tr>
							<c:forEach items="${excelFiles}" var="curr">
								<tr>
									<td>
										<a href="downloadbatchupdateexcelfile.do?excel_id=${curr.id}">${curr.fileName}</a>
									</td>
									<td>${curr.user.firstname} ${curr.user.lastname}</td>
									<td>${curr.updatedOn}</td>
								</tr>
							</c:forEach>
						</table>
						<br /><br /><br />
						<a href="items.do?group_id=${group.id}">Back</a>
					</div>
				</div> <!--  jobContainer -->	
			</div> <!--  content -->
			<div class="clear"></div>
		</div><!-- page -->
	</div><!-- wrapper -->
</body>
<%@include file="includes/footer.jsp"  %>