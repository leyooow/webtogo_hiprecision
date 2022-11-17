<script type="text/javascript" src="../javascripts/upload.js"></script>
<script type="text/javascript" src="../javascripts/dynamicUploader.js"></script>

<script language="javascript" type="text/javascript">
function ValidateFileUpload()
{
  var fuData = document.getElementById("file")
  var FileUploadPath = fuData.value;
  if(FileUploadPath =='') 
  {
	  alert("Please select an Image File of your Invoice")
	    return false;
  }
  else
  {
    var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
    if (Extension == "jpg" || Extension == "jpeg")
    {
      return true;
    }
    else
    {
      alert("Invalid Image File")
      return false; // Not valid file type
    }
   }
}
</script> 

<script language="javascript">
function validate(){
	var distributor=document.invoiceUpload.distributor	
	var invoiceNumber=document.invoiceUpload.invoiceNumber	
	var companyName_SOLD=document.invoiceUpload.companyName_SOLD	
	var remarks=document.invoiceUpload.remarks	
	var _file=document.invoiceUpload.invoiceNumber	
	var totalPoints=document.invoiceUpload.totalPoints	

	var errorMessage=""
	if ((invoiceNumber.value==null)||(invoiceNumber.value=="")){
			errorMessage=errorMessage+("\nInvoice Number");
		invoiceNumber.focus()
	}

	var companyName_SOLD=document.invoiceUpload.companyName_SOLD	
	if ((companyName_SOLD.value==null)||(companyName_SOLD.value=="")){
			errorMessage=errorMessage+("\nEnd User Company Name");
	}

	if(errorMessage.length>0){
		alert("The Following fields are required:\n"+errorMessage)
		return false
	}

	if(totalPoints===undefined)
	{
		alert("Provide Us your Invoice Details")
		return false
	}
	
	return ValidateFileUpload()
}
</script>

     <div style="width:98%;">
		<form method="post" name="invoiceUpload" action="uploadReceipt.do?byAdmin=true" enctype="multipart/form-data"  onsubmit="return validate()" >
			<input type="hidden" name="member_id" value="${member2.id}">
		
		<!-- test start-->
			<script type="text/javascript">
	
			var itemArray = new Array();
			var priceArray = new Array();
			var qtyArray = new Array();
			var pointsArray = new Array();
			var totalPrice = 0;
			var totalPoints = 0;
			
			function setList(index){
				var list = document.getElementById('items').getElementsByTagName('select');
								
				for(var x=0;x<list.length;x++){
					//list[i].style.display=(i==index)?'block':'none';
					list[x].options[0].selected=true;
					if(x==index) {
						list[x].style.display='block'; 
						list[x].disabled=false;
					}
					else list[x].style.display='none';
				}
	
				if(index<0){
					list[0].disabled=true;
					list[0].options[0].selected=true;
					list[0].style.display='block';
				}
				setAddButton();
			}
			onload = function(){setList(-1)};
	
			function isNumeric(evt){
				var charCode = (evt.which) ? evt.which : event.keyCode
				if (charCode > 31 && (charCode < 48 || charCode > 57))
					return false;
	
				return true;
			}
	
			function renderTable(){
				var msg="";
				subtotal=0;
				subpoints=0;
				totalPrice=0;
				totalPoints=0;
	
				msg += '<table width="100%" cellspacing="0" cellpadding="8" class="companiesTable">';
				msg += '<tr><th width="220px">Product</th><th>Qty</th><th>GC</th><th>Subtotal GC</th><th>&nbsp;</th></tr>';
				if(itemArray.length>0){
					for(var i=0;i<itemArray.length;i++){
						subtotal = priceArray[i] * qtyArray[i];
						subpoints = parseInt(pointsArray[i]) * qtyArray[i];
						totalPrice += subtotal;
						totalPoints += subpoints;
						msg += '<tr>';
						msg += '<td><input type="hidden" name="itemArray" value="' + itemArray[i] + '">' + itemArray[i] + '</td>';
						msg += '<td  align="center"><input type="hidden" name="qtyArray" value="' + qtyArray[i] + '">' + qtyArray[i] + '</td>';
						//msg += '<td  class="right"><input type="hidden" name="priceArray" value="' + priceArray[i] + '">' + formatCurrency(priceArray[i]) + '</td>';
						msg += '<td  align="center"><input type="hidden" name="pointsArray" value="' + pointsArray[i].trim() + '">' + pointsArray[i] + '</td>';					
						//msg += '<td class="right"><input type="hidden" name="subtotal" value="' + subtotal + '">' + formatCurrency(subtotal) + '</td>';
						msg += '<td  align="center"><input type="hidden" name="subpoints" value="' + subpoints + '">' + subpoints + '</td>';
						msg += '<td  align="center"><a href="javascript:removeItem(' + i + ');"><img src="images/cross.png" title="remove" alt="remove"></a></td>';
						msg += '</tr>';
					}
				}		
				msg += '<tr>';
				msg += '<td style="background: #dedede;">Total</td>';
				//msg += '<td>&nbsp;</td><td>&nbsp;</td>';
				msg += '<td style="background: #dedede;">&nbsp;</td>';
				msg += '<td style="background: #dedede;" align="center">&nbsp;</td>';
				msg += '<td style="background: #dedede;" align="center"><input type="hidden" name="totalPoints" value="' + totalPoints + '"><strong>' + totalPoints + ' pts.</strong></td>';
				msg += '<td style="background: #dedede;">&nbsp;</td>';
				msg += '</tr>';	
				msg += '</table>';
				
				document.getElementById('edititems').innerHTML = msg;
				document.getElementById('qty').value=1;
			}
	
			function setAddButton(){
				var list = document.getElementById('items').getElementsByTagName('select');
	
				for(var i=0;i<list.length;i++){
					if(list[i].options[0].selected){
						document.getElementById('btnAdd').disabled=true;
					}
					else {
						document.getElementById('btnAdd').disabled=false;
						break;
					}
				}
			}
	
			function addItem(){
				var list = document.getElementById('items').getElementsByTagName('select');
				var priceList = document.getElementById('price').getElementsByTagName('select');
				var pointsList = document.getElementById('points').getElementsByTagName('select');
				var item = "", price=0, points=0;
	
				for(var i=0;i<list.length;i++){
					if(list[i].style.display=='block'){
						item=list[i].value;
						priceList[i].options[list[i].selectedIndex].selected=true;
						price=priceList[i].value;
						pointsList[i].options[list[i].selectedIndex].selected=true;
						points=pointsList[i].value;
					}
				}
				
	
				itemArray[itemArray.length] = item;
				priceArray[priceArray.length] = price;
				pointsArray[pointsArray.length] = points;
				qtyArray[qtyArray.length] = document.getElementById('qty').value;
				//for(var i=0;i<itemArray.length;i++) alert(itemArray[i]);
		
				renderTable();
			}
	
			function removeItem(index){
				itemArray.splice(index,1);
				priceArray.splice(index,1);
				qtyArray.splice(index,1);
				pointsArray.splice(index,1);
				renderTable();
			}
	
			function formatCurrency(number) {
				var minus = number < 0 ? '-' : '';
				number = parseFloat(Math.abs(number)).toFixed(2);
				var number_arr = String(number).split('');
				var number_cnt = number_arr.length;
				for (var i = 6; i < number_cnt; i+=4) {
					number_arr.splice(-i, 0, ',');
					number_cnt++;
				}
				return minus + number_arr.join('');
			}
			</script>
	
			<table cellspacing="0" cellpadding="4" class="tableDetails">
				<tr>
					<td width="120px" valign="top"><strong>Invoice Number</strong></td>
					<td valign="top">:</td>
					<td valign="top" colspan="3">
					<input type="text" name="invoiceNumber" style="width: 250px;"> <label class="noteGray">(* Please input complete invoice or SI number here)</label>
					</td>
				</tr>
				<tr>
					<td valign="top"><strong>Company Name</strong></td>
					<td valign="top" >:</td>
					<td valign="top" colspan="3">
					<input type="text" name="companyName_SOLD" style="width: 250px;"> <label class="noteGray">(* Sales invoice issued to the client/customer)</label>
					</td>
				</tr>
				<tr valign="top">
					<td valign="top"><strong>Remarks</strong></td>
					<td valign="top">:</td>
					<td valign="top" colspan="3">
					<textarea name="remarks" style="width: 250px;"></textarea> <label class="noteGray">(* Place additional information here if applicable)</label>
					</td>
				</tr>
			</table>
				
			<br />
			<table cellspacing="0" cellpadding="4" class="tableDetails">
				<tr>
					<td width="120px"><strong>Product</strong></td>
					<td>:</td>
					<td>
					<select name="group" id="group" onChange="setList(this.selectedIndex-1)" class="w160">
							<option value="">Select Category</option>
		        		<c:forEach items="${group.categories}" var="cat">
		        			<option value="${cat.name}">${cat.name}</option>
		        		</c:forEach>
					</select>
					</td>
					
					<td>
					<div id="items">
						<c:forEach items="${group.categories}" var="cat">
			        		<select name="${cat.name}" id="${cat.name}" disabled onChange="javascript:setAddButton();" class="w300"/>
			        				<option value="">Select Item</option>
			        			<c:forEach items="${cat.items}" var="item">
			        				<option value="${item.name}">${item.name}</option>
			        			</c:forEach>
			        		</select>
			        	</c:forEach>
					</div>
					<div id="price" style="display:none;">
						<c:forEach items="${group.categories}" var="cat">
			        		<select name="${cat.name}" style="width:10em;" />
			        				<option value="">Select</option>
			        			<c:forEach items="${cat.items}" var="item">
			        				<option value="${item.categoryItemPrices[0].amount}">${item.categoryItemPrices[0].amount}</option>
			        			</c:forEach>
			        		</select>
			        	</c:forEach>
					</div>
					
					<%-- 
					<div id="points" style="display:none;">
						<c:forEach items="${group.categories}" var="cat">
			        		<select name="${cat.name}" style="width:10em;" />
			        				<option value="">Select</option>
			        			<c:forEach items="${cat.items}" var="item">
			        				<option value="${item.price}">${item.price}</option>
			        			</c:forEach>
			        		</select>
			        	</c:forEach>
					</div>
					</td>
					--%>
					<div id="points" style="display:none;">
						<c:forEach items="${group.categories}" var="cat">
			        		<select name="${cat.name}" style="width:10em;" />
			        				<option value="">Select</option>
			        			<c:forEach items="${cat.items}" var="item">
			        				<option value="${item.parent.descriptionWithoutTags}">${item.parent.descriptionWithoutTags}</option>
			        			</c:forEach>
			        		</select>
			        	</c:forEach>
					</div>
					</td>
					
					
					<td><input type="text" name="qty" id="qty" size="3" onKeyPress="return isNumeric(event)" value="1" /></td>
					<td><input type="button" class="btnBlue" value="Add" id="btnAdd" onClick="javascript:addItem();" disabled /></td>
				</tr>
			</table>
			<br/>
	
			<div id="edititems">
			<table width="100%" cellspacing="0" cellpadding="8" class="companiesTable">
				<tr>
					<th width="220px">Product</th>
					<th>Qty</th>
					<th>GC</th>				
					<th>Subtotal GC</th>
					<th>Action</th>
				</tr>
				<tr>
				</tr>
			</table>
			</div>
	
			<!-- test end-->
		<br />
		<strong>Please upload a scanned image of the actual Invoice.</strong><div class="clear"></div>
		
		<table onload="addUpload(5, 'alt', 'upload')" class="tableDetails w585">
			<tr> 
				<td style="border:0px;">
					<a id="addupload" href="javascript:addUpload(4, 'alt', 'upload')"></a>
					    <input id="file" name="upload" type="file" size="30" />
					    </div>
					<div id="attachment" class="attachment" style="display:none">
					    <input id="file" name="upload" type="file" size="30" />
					    <a href="#" onclick="javascript:removeFile(this.parentNode.parentNode,this.parentNode);">Remove</a>
					    </div>
					    <div id="attachments" class="container">
					    <!--
					    **************************************************************************
					    Below is the call to the "addUpload" function which adds an Attachment
					    section to the form. Customize the function parameters as needed:
					    1) Maximum # of attachments allowed in this pub type
					    2) base Field Name for the Description fields in your pub type
					    3) base Field Name for the File Upload field in your pub type
					    **************************************************************************
					    -->
					    <span id="attachmentmarker"></span>
					</div>
				</td>
			</tr>			
			<tr>
				<td>
				
				<p>You may upload files in JPG/JPEG format. Please name the file in this format (companyname_date)</p>
				
				<p>Ex. samplefirsttier_08152011.jpg</p>
				
				<p><strong><em>* No Special Character on Filename</em></strong></p>
				
				</td>
			</tr>	
	
			<tr>
				<td class="right">		
					
				</td>
			</tr>
		</table>
		<div align="left">
			<input class="btnBlue" type="submit" value="Submit"  onclick="LimitAttach(this.form, this.form.upload.value)" />
		</div>
	</form>

</div>