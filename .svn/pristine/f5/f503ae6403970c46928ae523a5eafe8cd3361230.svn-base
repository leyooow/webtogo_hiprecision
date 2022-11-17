<%@include file="../includes/header.jsp"  %>

<c:set var="menu" value="members" />
<c:set var="submenu" value="member_listing" />
<c:set var="pagingAction" value="members.do" />


<body>
  <div class="container">
	
    <%@include file="../includes/bluetop.jsp"%>
	<%@include file="../includes/bluenav.jsp"%>
	
	
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}

	function keyCheck(eventObj, obj)
	{
	    var keyCode
	    if (document.all){
	        keyCode=eventObj.keyCode
	    }
	    else{
	        keyCode=eventObj.which
	    }
	 
	    var str=obj.value
	    
	   if(keyCode == 45 && str == ''){ // '-' must be the first character
		   return true
	   }
	    
	    if(keyCode==46){
	        if (str.indexOf(".")>0){
	            return false
	        }
	    }		    
	 
	    if( keyCode == 8 || keyCode == 0){
	    	return true
		}else if(((keyCode<48 || keyCode >58)   &&   (keyCode != 46))){ // Allow only integers and decimal points
	        return false
	    }		 
	    return true;
	}
</script>

<div class="contentWrapper" id="contentWrapper">
    <div class="content">
				
		<div class="pageTitle">
			<h1><strong>Input Invoice Details</strong></h1>
			<div class="clear"></div>
		</div><!--//pageTitle-->
		
		<br />

		<%@include file="UploadReceipt.jsp" %>

	</div><!--//mainContent-->
	
</div>





	<div class="clear"></div>

  </div><!--//container-->

</body>

</html>