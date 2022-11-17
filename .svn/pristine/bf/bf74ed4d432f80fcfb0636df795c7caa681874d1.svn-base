/*
 * Created on Sep 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ivant.cms.helper;

public class FileHelper {
	
	public static String getFilename(String filename){
		if(filename.lastIndexOf("/")!=-1){
			return filename.substring(filename.lastIndexOf("/")+1, filename.length());
		}
		else if(filename.lastIndexOf("\\")!=-1){
			return filename.substring(filename.lastIndexOf("\\")+1, filename.length());
		}
		else{
			return filename;
		}
	}
}
