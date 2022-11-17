<script language="javascript" type="text/javascript">
		function reloadCaptcha() {
			document.getElementById("image").src=document.getElementById("image").src + "?" + Math.random();;
		}
var validKaptcha = false;
	function checkKaptcha() {

		$.ajax({
			url: "checkKaptcha.do",
			cache: false,
			dataType: "json",
			async: false,
			type: "POST",
			data:{
				kaptcha: $("#kaptcha").val()
			},
			success: function(data){
				if(data.valid){
					$("#kaptchaErrorText").hide();
					validKaptcha = true;
				}
				else {
					$("#kaptchaErrorText").show();
					validKaptcha = false;
				}
			},
			error: function(e, text){
				return false;
			}
		});

	}
</script>

<c:choose>
	<c:when test = "${company.name eq 'wendys' or company.name eq 'pocketpons'}">
		<font style = "font-size:1.3em;">Type the characters you see in this image.</font>
	</c:when>
	<c:otherwise>
		Type the characters you see in this image.
	</c:otherwise>
</c:choose>
 <br> 
<table width="100%" border="0" cellspacing="0" cellpadding="0" ${company.name eq 'wendys' or company.name eq 'pocketpons' ? ' style = "text-align:center;"' : '' }>
	<tr valign="top">
		<td valign="top">
			<img id="image" src="kaptcha.jpg"/>
			<br>
			<!-- <a href="javascript: window.location.reload()"> -->
			<a onclick="javascript:reloadCaptcha()">
				<%-- Copy btnRefresh.jpg to company --%>
				<img src="http://webtogo.com.ph/images/btnRefresh.jpg" style="margin:3px 0;" />
			</a>
		</td>
	</tr>
</table>
<c:choose>
	<c:when test = "${company.name eq 'wendys' or company.name eq 'pocketpons' }">
		<font style = "font-size:1.3em;">Type the characters:</font>
	</c:when>
	<c:otherwise>
		Type the characters:
	</c:otherwise>
</c:choose>
<br>
	<input type="text" name="kaptcha" value="" id = "kaptcha" required />
	<label id="kaptchaErrorText" style="color:red;display:none">* Characters in image not the same.</label>
<br>
<c:choose>
	<c:when test = "${company.name eq 'wendys' or company.name eq 'pocketpons' }">
		<font style = "font-size:1.3em;">Letters are not case sensitive</font>
	</c:when>
	<c:otherwise>
		Letters are not case sensitive
	</c:otherwise>
</c:choose>
