<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %> 
	<%-- <%@taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>--%>
	<%@ taglib prefix="FCK" uri="http://java.fckeditor.net" %>
	<%@taglib uri="/struts-tags" prefix="s" %>
<div class="opacityOverlay" id="opacityOverlay"></div><div class="loadDiv" id="loadDiv" style="position: fixed;"><div class="loadContent" id="loadContent"><img src="images/load.gif" /></div></div>
<div class="webtogo">

			
			
			<%@ page import="java.util.List,com.ivant.cms.delegate.CompanyDelegate,com.ivant.cms.entity.Company,com.ivant.cms.enums.CompanyStatusEnum,javax.servlet.http.*" %>
			<%
				CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
				String status = "active";
				String[] order = { "nameEditable" };
				List<Company> active_companies, active_no_hosting_companies, ongoing_companies, suspended_companies, all_companies, selected_companies;
				active_companies = companyDelegate.findByStatus(
						CompanyStatusEnum.ACTIVE, order).getList();
				active_no_hosting_companies = companyDelegate.findByStatus(
						CompanyStatusEnum.ACTIVE_NO_HOSTING, order).getList();
				ongoing_companies = companyDelegate.findByStatus(
						CompanyStatusEnum.ONGOING, order).getList();
				suspended_companies = companyDelegate.findByStatus(
						CompanyStatusEnum.SUSPENDED, order).getList();
				all_companies = companyDelegate.findAll(order).getList();
				
				if(session.getAttribute("companyStatus")!=null){
					if(session.getAttribute("companyStatus").equals("ACTIVE")){
						selected_companies=active_companies;
						status = "active";
					}
					else if(session.getAttribute("companyStatus").equals("ACTIVE_NO_HOSTING")){
						selected_companies=active_no_hosting_companies;
						status = "active_no_hosting";
					}
					else if(session.getAttribute("companyStatus").equals("ONGOING")){
						selected_companies = ongoing_companies;
						status = "ongoing";
					}
					else if(session.getAttribute("companyStatus").equals("SUSPENDED")){
						selected_companies = suspended_companies;
						status = "suspended";
					}
					else{
						selected_companies = all_companies;
						status = "all";
					}
				}
				else
					selected_companies = active_companies;
			%>
			
			<script type="text/javascript">
			function statusChange(){
				var status = document.getElementById("company_status").value;	
				var companiesId = new Array();
				var companiesName = new Array();
				var max;
				
				document.getElementById("company_id").options.length = 0;

				if(status == "ALL"){<%for (int j = 0, i = 0; j < all_companies.size(); j++) {
					if (all_companies.get(j).getNameEditable() != null) {%>
						companiesId.push("<%=all_companies.get(i).getId()%>");
						companiesName.push("<%=all_companies.get(i).getNameEditable()%>");
						<%i++;
					}
				}%>
					max = "<%=all_companies.size()%>";
				}
				else if(status == "ACTIVE"){<%for (int j = 0; j < active_companies.size(); j++) {%>
					companiesId.push("<%=active_companies.get(j).getId()%>");
					companiesName.push("<%=active_companies.get(j).getNameEditable()%>");
					<%}%>
					max = "<%=active_companies.size()%>";
				}
				else if(status == "ACTIVE_NO_HOSTING"){<%for (int j = 0; j < active_no_hosting_companies.size(); j++) {%>
					companiesId.push("<%=active_no_hosting_companies.get(j).getId()%>");
					companiesName.push("<%=active_no_hosting_companies.get(j).getNameEditable()%>");
					<%}%>
					max = "<%=active_no_hosting_companies.size()%>";
				}
				else if(status == "ONGOING"){<%for (int j = 0; j < ongoing_companies.size(); j++) {%>
					companiesId.push("<%=ongoing_companies.get(j).getId()%>");
					companiesName.push("<%=ongoing_companies.get(j).getNameEditable()%>");
					<%}%>
					max = "<%=ongoing_companies.size()%>";
				}
				else if(status == "SUSPENDED"){<%for (int j = 0; j < suspended_companies.size(); j++) {%>
					companiesId.push("<%=suspended_companies.get(j).getId()%>");
					companiesName.push("<%=suspended_companies.get(j).getNameEditable()%>");
					<%}%>
					max = "<%=suspended_companies.size()%>";
				}
				
					
				
				
				document.getElementById("stat").value =  status;
				var select = document.getElementById("company_id");
				select.options[select.options.length] = new Option('-- SELECT COMPANY --','0');
				for(var i=0; i<max; i++){
					select.options[select.options.length] = new Option(companiesName[i],companiesId[i]);
				}
			}
			
		</script>
		
		<c:set var="userCompanyId" value="${company.id}" />
		<c:set var="test" value="<%=status %>"/>
		<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
		<ul>
		<li><a onclick="loading();" href="companies.do" ${(submenu == "company_listing") ? 'class="active"' : ''}><strong>Companies</strong></a></li>
		<li><a onclick="loading();" href="usertab.do" ${(submenu == "user_listing") ? 'class="active"' : ''}><strong>Users</strong></a></li>
		<li><a onclick="loading();" href="reports.do" ${(submenu == "report_listing") ? 'class="active"' : ''}><strong>Reports</strong></a></li>
		</ul>
		</c:if>
		<c:if test="${company.name eq 'agian' && user.userType.value eq 'System Administrator' }">
			<ul><li><a onclick="loading();" href="usertab.do" ${(submenu == "user_listing") ? 'class="active"' : ''}><strong>Users</strong></a></li></ul>
		</c:if>
      <ol>

        <li><a href="#"><strong>${user.firstname} ${user.lastname}</strong>
		    [${user.userType.value}]</a> |</li>

        <li><a href="settings.do">Profile</a> |</li>
        
        <c:if test="${(user.userType.value ne 'Company Staff' and user.userType.value ne 'Normal User') or company.id eq 181}">
	        <li>
	      		<a class="editCompanySettings" href="editcompany.do?company_id=${company.id}">Company Settings</a> |
	      	</li>
      	</c:if>

        <li><a href="logout.do" title="Log-out and clear the cookie off your machine" onclick="return confirm('Are you sure you want to log out?');">Logout</a> |</li>

        <li><a href="http://webtogo.com.ph/webhelp.do"  style="font-size: 7pt; font-weight: none;"><img src="images/btnHelp.gif" alt="Help" title="Help" align="absmiddle" /></a></li>

      </ol>

      <div class="clear"></div>
			
      <h2>
		<c:if test="${user.userType.value == 'Super User' or user.userType.value =='WTG Administrator'}">
		
		
		
		<form id="selectCompany" name="myForm" method="post" action="dashboard.do">
		<input type="hidden" id="stat" name="company_status" value="ACTIVE"/>
        <table border="0" cellspacing="5" cellpadding="0">
		<tr>
          	<td>Status</td>
          
             <td id="companySelectCell">
              <select id="company_status" name="companyStatus" style="width: 175px;" onchange="statusChange();">
							<option value="ACTIVE" <c:if test="${test == null || test == 'active'}">selected</c:if>>ACTIVE</option>

							<option value="ACTIVE_NO_HOSTING" <c:if test="${  test eq 'active_no_hosting'}">selected</c:if>>ACTIVE_NO_HOSTING</option>

							<option value="ONGOING" <c:if test="${  test eq 'ongoing'}">selected</c:if>>ONGOING</option>

							<option value="SUSPENDED" <c:if test="${  test eq 'suspended'}">selected</c:if>>SUSPENDED</option>
			
							<option value="ALL" <c:if test="${   test eq 'all' }">selected </c:if>>ALL</option>							
							

																						
													
			  </select>
			  
		
		
			  
						<!-- <input border="0" type="submit" value="GO"/> -->
			</td>
			</tr>
		
		
			
		<tr>
			

            <td>Choose Company</td>

            <td id="companySelectCell">
		
		
				
	
              <select id="company_id" name="company_id" style="width: 250px;"  onchange="document.getElementById('selectCompany').submit(); loading();">

							
							<option value="0" <c:if test="${company.id == null || userCompanyId == 0}">selected</c:if>> -- SELECT COMPANY -- </option>

							<c:forEach items="<%=selected_companies%>" var="company">
								<option value="${company.id}" <c:if test="${company.id == userCompanyId}">selected</c:if>>${company.nameEditable}</option>
							</c:forEach>
							
							
							<!-- <c:if test="${empty companies }">
							<c:forEach items="${companies2}" var="company">
								<c:if test="${company.companySettings.companyStatus eq 'ACTIVE_NO_HOSTING'}"><option value="${company.id}" <c:if test="${company.id == userCompanyId}">selected</c:if>>${company.nameEditable}</option></c:if>
							</c:forEach>
							</c:if>  -->
						</select>
						
						<!-- <input border="0" type="submit" value="GO"/> -->
			</td>
          </tr>
          			
					
        </table>
               				
		</form>
				</c:if>
      </h2>
      <h1 style="width: 500px; ">${user.company.nameEditable}&nbsp;
      
		</h1>

    </div><!--//webtogo-->
    <div class="clear" ></div>