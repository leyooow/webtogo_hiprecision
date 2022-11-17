function displayPage(page,content)
{
var xmlhttp;
if (page.length==0)
  { 
  document.getElementById(content).innerHTML="";
  return;
  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById(content).innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET",page,true);
xmlhttp.send();
}