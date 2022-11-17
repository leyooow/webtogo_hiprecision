<%@include file="includes/header.jsp"%>
<link href="css/css.css" rel="stylesheet" type="text/css" />

<c:set var="menu" value="groupItem" />
<c:set var="groupName" value="${group.name}" />
<c:set var="submenu" value="items" />

<!-- JQuery -->
<link type="text/css" href="javascripts/jquery/jquery-ui-1.8.19.custom.css" rel="Stylesheet" />
<script type="text/javascript" src="javascripts/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="javascripts/jquery/jquery-ui-1.8.19.custom.min.js"></script>



<body>

	<c:set var="menu" value="items" />


	<c:set var="maxImage" value="${companySettings.maxAllowedImages}" />
	<c:set var="imageCount" value="${fn:length(item.images)}" />
	<c:set var="allowedImageCount" value="${maxImage - imageCount}" />

	<div class="container">
		<%@include file="includes/bluetop.jsp"%>
		<%@include file="includes/bluenav.jsp"%>
		<div class="contentWrapper" id="contentWrapper">
			<div class="mainContent">
				<c:if test="${param['evt'] != null}">
					<ul>

						<c:if test="${param['evt'] == 'update'}">
							<li><span class="actionMessage">Page was successfully
									updated.</span></li>
						</c:if>

						<c:if test="${param['evt'] == 'updatefailed'}">
							<li><span class="actionMessage">Failed to update
									page.</span></li>
						</c:if>
					</ul>
				</c:if>

				<div class="pageTitle">
					<h1>
						<strong>Edit Item</strong>
					</h1>
					<div class="clear"></div>
				</div>
				<!--//pageTitle-->
				
				<c:set var="pageNumSuffix" value="" />
				<c:if test="${param.pageNumber ne null and param.pageNumber ne ''}">
					<c:set var="pageNumSuffix" value="?pageNumber=${param.pageNumber}" />
				</c:if>
				<c:set var="formAction" value="updateitem.do${pageNumSuffix}" />
				
				<form method="post" action="${formAction}" onsubmit="return submitForm(this);" enctype="multipart/form-data">
					<input type="hidden" name="language" value="${param.language}" />
					<input type="hidden" name="catId" value="${sessionScope.setVariable }" />
					<input type="hidden" name="group_id" value="${group.id}" /> 
					<input type="hidden" name="pageNumber" value="${pageNumber}" />
					<input type="hidden" name="agian_event_date" id="agian_event_date" value="" />
					
				<table width="50%" id="itemFieldsContainer">

					<tbody>
						<tr>
							<td width="1%" nowrap="">Parent Category</td>
							<td width="10px"></td>
							<td>
								
								<select name="parent" style="width: 500px;">
									<option value="24002390" selected="" data-value="IMMUNO">IMMUNO</option>
									<option value="24002391" data-value="CHEMISTRY">CHEMISTRY</option>
								</select>
							</td>
						</tr>

						<tr>
							<td width="1%" nowrap="">Item Name</td>
							<td width="10px"></td>
							<td><input type="text" id="item_name" name="item.name" value="ACTH" class="w385"></td>
						</tr>


						<tr id="skuRow">
							<td width="1%" nowrap="">SKU</td>
							<td width="10px"></td>
							<td><input type="text" id="item_sku" name="item.sku" value="ACTH" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">ANALYTE/S</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o69" value="ACTH" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">SAMPLE</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o70" value="PLASMA" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">CRITICAL VALUE</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o71" value="none" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">RECOMMENDATION DILUTION</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o72" value="N/A" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">DILUENT</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o73" value="N/A" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">AMR</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o74" value="1.00-2000 pg/mL" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">CRR</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o75" value="1.00~2000.00 pg/mL" class="w385"></td>
						</tr>
						<tr>
							<td width="1%" nowrap="">REPEAT TEST CRITERIA (IF BELOW)</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o76" value="7.00 pg/mL" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">REPEAT TEST CRITERIA (IF ABOVE)</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o77" value="7.00 pg/mL" class="w385"></td>
						</tr>

						<tr>
							<td width="1%" nowrap="">ACCEPTABLE DIFFERENCE (%)</td>
							<td width="10px"></td>
							<td><input type="text" id="other_field" name="o78" value="5%" class="w385"></td>
						</tr>
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" style="border: 0px;"></td>
							<td style="border: 0px;">
								
									<input type="submit" value="Update" class="btnBlue">
									<input type="reset" value="Reset" class="btnBlue">
									<input type="button" value="Cancel" class="btnBlue" onclick="window.location='items.do?group_id=168'">
											
							</td>
						</tr>
					</tbody>

				</table>
			</form>



					<br />

					<div class="sidenav">
	

					</div>
					<!--//sidenav-->

					<div class="clear"></div>
			</div>
			<!--//container-->
		</div>
</body>
</html>