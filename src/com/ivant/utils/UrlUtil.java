package com.ivant.utils;

public final class UrlUtil {

//	public static String generateImageUploadUrl(Company company) {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		ServletContext servletContext = ServletActionContext.getServletContext();
//		String servletContextName = servletContext.getServletContextName();
//		User user = (User) request.getAttribute(UserInterceptor.USER_REQUEST_KEY);
//		
//		boolean isLocal = (request.getRequestURI().startsWith("/" + servletContextName)) ? true : false;
//		
//		request.setAttribute("local", isLocal);
//		String serverName = "";
//		if(request.getServerName().startsWith("www.") || request.getServerName().equals("localhost")) {
//			serverName = request.getServerName();
//		}
//		else {
//			serverName = "www." + request.getServerName();
//		}
//		
//		StringBuffer uploadedImageUrl = new StringBuffer(); // used string buffer instead of string to speed up the performance
//		if(isLocal) {
////			uploadedImageUrl.append("http://");
////			uploadedImageUrl.append(request.getServerName() + ":");
////			uploadedImageUrl.append(request.getServerPort() + "/");
////			uploadedImageUrl.append("/" + servletContext.getServletContextName() + "/");
//			uploadedImageUrl.append("companies/");
//			uploadedImageUrl.append(company.getName() + "/");
//			uploadedImageUrl.append("images/");
//		}
//		else {
////			if(serverName.startsWith("www.webtogo.com.ph")) {
////				uploadedImageUrl.append("http://");
////				uploadedImageUrl.append(request.getServerName() + "/");\
////				uploadedImageUrl.append("/companies/");
////				uploadedImageUrl.append(company.getName() + "/");
////				uploadedImageUrl.append("images/");
////			}
////			else {
////				uploadedImageUrl.append("http://");
////				uploadedImageUrl.append(request.getServerName() + ":");
////				uploadedImageUrl.append("/companies/");
////				uploadedImageUrl.append(company.getName() + "/");
////				uploadedImageUrl.append("images/");
////			}
//			uploadedImageUrl.append("/companies/");
//			uploadedImageUrl.append(company.getName() + "/");
//			uploadedImageUrl.append("images/");
//		}
//	
//		return uploadedImageUrl.toString();
//	}
}
