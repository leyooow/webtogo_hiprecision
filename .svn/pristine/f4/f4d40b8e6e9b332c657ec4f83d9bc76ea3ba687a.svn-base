<%@page import="java.util.*"%>
<%--- Business Rules ---%>
<%
	List<String> list; 
	//default settings
	list= Arrays.asList("User Name", "Email Address", "Name", "Company Name","Date Joined", "Is Activated");
	request.setAttribute("list", list);
%>

<c:if test="${company.name == 'hometherapists'}">
	<% 
	list= Arrays.asList("User Name", "Email Address", "Name", "Company Name","Date Joined", "Is Activated" ,"License" ,"License Valid Until"); 
	%>	
</c:if>

<c:if test="${company.name == 'pco'}">
	<% 
	list= Arrays.asList("User Name", "Email Address", "Name", "Gender", "Birthdate", "Registered Events" ,"Suffix",	"Membership",	"PRC Licence",	"Type of Practice",	"Name of Clinic","Country","Region","Province","City","Street 1","Street 2","Clinic(Country)","Clinic(Region)","Clinic(Province)","Clinic(City)","Clinic(Street)","Landline",	"Mobile Phone 1",	"Mobile Phone 2",	"Mobile Phone 3",	"Fax","Company Name", "Date Joined","Is Activated"); 
	%>	
</c:if>

<c:if test="${company.name == 'apc' or company.name == 'westerndigital' }">
	<% 
	list= Arrays.asList("User Name", "Email Address", "First Name", "Last Name", "Mobile", "Landline" ,"Company",	"Company Address",	"Postion");	
	List<String> listMemberFiles; 
	
	listMemberFiles= Arrays.asList("Member Name","Email", "Value","Invoice Number",	"Description",	"Points",	"Distributor","Company Name","Company Address","Position","Status","Action By","Date");
	request.setAttribute("listMemberFiles", listMemberFiles); 
	
	
	
	List<String> listSalesForItem; 
	listSalesForItem= Arrays.asList("Item Name","Invoice Number","Status", "Date Created","Approved Date",	"Points",	"Member Name",	"Company Name");
	request.setAttribute("listSalesForItem", listSalesForItem); 
	
	
	List<String> listOrdersOfSpecificMember;
	listOrdersOfSpecificMember = Arrays.asList("Order Date","Order Id","Quantity","Item","SubTotal","Status","Total Price");
	
	request.setAttribute("listOrdersOfSpecificMember", listOrdersOfSpecificMember);  
	%>	
</c:if>

<c:if test="${company.name == 'aplic'}">
	<% 
	list= Arrays.asList("Salutation", "Name", "Date Created", "Passport No", "Agency", "Insurance Company", "Mailing Address", "Country", "Landline", "Fax", "Mobile", "Email Address", "Member No", "No of Years", "Annual Income", "Age Group"); 
	%>	
</c:if>

<c:if test="${company.name == 'micephilippines'}">
	<% 
	list= Arrays.asList("Name", "Date Created","Designation" ,"Company Name", "Company Address", "Country", "Telephone Numbers", "Fax Number", "Mobile Number", "Email Address", "Accompanying Person"); 
	%>	
</c:if>

 <!--  watson -->
<c:if test="${company.id eq 270 }">
	<% list = Arrays.asList("Name", "Email Address", "Date Joined","Body Mass Index","Fitness Result","Skin Result", "Age", "Height", "Weight", "Mobile", "Landline", "Branch");  %>
</c:if>

 <!--  watson kiddie live_id=278 -->
<c:if test="${company.id eq 271 }">
	<% list = Arrays.asList("Name", "Date Joined", "Age", "Gender", "Contact Number", "Email Address", "Branch");  %>
</c:if>

<c:if test="${company.id eq 300 or company.name == 'watsonglam' }">
	<% list = Arrays.asList("Name", "Date Joined", "Age", "Gender", "Contact Number", "Email Address", "Branch");  %>
</c:if>

<c:if test="${company.id eq 305 or company.name == 'watsonstyle' }">
	<% list = Arrays.asList("Name", "Date Joined", "Age", "Email Address", "Mobile Number", "Contact Number", "Branch");  %>
</c:if>

<c:if test="${company.id eq 309 or company.name == 'watsonsusd' }">
	<% list = Arrays.asList("Name", "Date Joined", "Time", "Email Address", "Mobile Number", "Contact Number", "Points"); %>
</c:if>

<% request.setAttribute("list", list); %>