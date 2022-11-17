/**
 * 
 */
package com.ivant.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

/**
 * Utility class for File uploads.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class FileUploadUtil {
	//10 mb = 10,000,000 bytes
	private static final Long DEFAULT_FILE_SIZE = 10000000L;
	
	/** Thread safe {@code FileUploadUtil} class instance*/
	private volatile static FileUploadUtil instance;
	
	/**
	 * Creates a new instance of class {@code FileUploadUtil}.
	 */
	private FileUploadUtil(){
		//do nothing
	}
	
	/**
	 * Returns {@code FileUploadUtil} class instance.
	 * 
	 * @return
	 */
	private static FileUploadUtil getInstance(){
		//generate a new instance current instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (FileUploadUtil.class) {
				if(instance == null)
					instance = new FileUploadUtil();
			}
		}
		return instance;
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param filePath
	 * 			- path of the file, must not be null
	 * @param maxFileSize
	 * 			- maximum allowable file size
	 * @return - true if file size is valid, otherwise false
	 */
	public static boolean isFileSizeValid(String filePath, Long maxFileSize){
		return getInstance().isFileSizeValidImpl(filePath, maxFileSize);
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param filePath
	 * 			- path of the file, must not be null
	 * @param maxFileSize
	 * 			- maximum allowable file size
	 * @return - true if file size is valid, otherwise false
	 */
	public static boolean isFileSizeValid(String filePath){
		return getInstance().isFileSizeValidImpl(filePath, DEFAULT_FILE_SIZE);
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param fileLength
	 * 			- file size, must not be null
	 * @param maxFileSize
	 * 			- maximum allowable file size
	 * @return - true if file size is valid, otherwise false
	 */
	public static boolean isFileSizeValid(Long fileLength, Long maxFileSize){
		return getInstance().isFileSizeValidImpl(fileLength, maxFileSize);
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param fileLength
	 * 			- file size, must not be null
	 * @param maxFileSize
	 * 			- maximum allowable file size
	 * @return - true if file size is valid, otherwise false
	 */
	public static boolean isFileSizeValid(Long fileLength){
		return getInstance().isFileSizeValidImpl(fileLength, DEFAULT_FILE_SIZE);
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param propertiesPath - directory containing the upload.properties folder
	 * @param uploaderName - directory containing the uploaded files
	 * @param request - servlet request containing the uploaded file
	 * 
	 * @return - true if file size is valid, otherwise false
	 */
	public static boolean isFileSizeValid(String propertiesPath, String uploaderFieldName, HttpServletRequest request) {
			return getInstance().isFileSizeValidImpl(propertiesPath, uploaderFieldName, request);
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param filePath
	 * 			- path of the file, must not be null
	 * @param maxFileSize
	 * 			- maximum allowable file size
	 * @return - true if file size is valid, otherwise false
	 */
	private boolean isFileSizeValidImpl(String filePath, Long maxFileSize){
		File file = new File(filePath);
	    
		//check if file exists or if file is valid
	    if (!file.exists() || !file.isFile()) {
	    	return false;
	    }
	    
	    //check if file size is valid
	    if(file.length() <= maxFileSize)
	    	return true;
	    
	    //invalid file size
		return false;
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param fileLength
	 * 			- file size, must not be null
	 * @param maxFileSize
	 * 			- maximum allowable file size
	 * @return - true if file size is valid, otherwise false
	 */
	private boolean isFileSizeValidImpl(Long fileLength, Long maxFileSize){		
	    //check if file size is valid
	    if(fileLength <= maxFileSize)
	    	return true;
	    
	    //invalid file size
		return false;
	}
	
	/**
	 * Returns true if file size is valid, otherwise false.
	 * 
	 * @param propertiesPath - directory containing the upload.properties folder
	 * @param uploaderName - directory containing the uploaded files
	 * @param request - servlet request containing the uploaded file
	 * 
	 * @return - true if file size is valid, otherwise false
	 */
	private boolean isFileSizeValidImpl(String propertiesPath, String uploaderName, HttpServletRequest request) {
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
		File[] file = requestWrapper.getFiles(uploaderName);
		String[] filename = requestWrapper.getFileNames(uploaderName);
		
		//check if an attachment exists		
		if((file != null && file.length > 0) && (filename != null && filename.length > 0)){
			Long totalFileSize = getTotalFileSize(file);
			//get maximum upload file size from the properties file
			Long maxFileSize = getMaxUploadSize(propertiesPath);
			
			//delete recently uploaded files if file size is invalid
			if(!isValidFileSize(totalFileSize, maxFileSize)){
				removeAttachment(file);				
				//attachment size is invalid
				return false;				
			}
		}
		//valid attachment size
		return true;
	}
	
	/**
	 * Returns total file size of the specified list of files.
	 * 
	 * @param file
	 * 			- list of files to be checked for size, must not be null
	 * 
	 * @return - total file size of the specified list of files
	 */
	private Long getTotalFileSize(File[] file) {
		Long totalFileSize = 0l;
		
		//get total attachment file size
		for(File currentFile : file){
			totalFileSize += currentFile.length();
		}
		return totalFileSize;
	}
	
	/**
	 * Removes specified file list.
	 *  
	 * @param files - list of files
	 */
	private void removeAttachment(File[] files) {
		//remove uploaded files
		for(File currentFile : files){
			currentFile.delete();
		}
	}

	/**
	 * Returns specified maximum upload file size.
	 * 
	 * @param propertiesPath
	 * 			- directory containing the upload.properties file for current company.
	 * 
	 * @return - specified maximum upload file size
	 */
	private Long getMaxUploadSize(String propertiesPath) {
		Long maxFileSize;			
		Properties uploadProperties = new Properties();
		try {				
			uploadProperties.load(new FileInputStream(propertiesPath + "upload.properties"));
			//valid attachment size must not be more than what is specified in the upload.properties file
			maxFileSize = Long.parseLong(uploadProperties.getProperty("MAX_FILE_SIZE"));
		} catch (Exception e) {
			//set maxFileSize to empty
			maxFileSize = 5L;
			//System.out.println("No MAX_FILE_SIZE property specified.");
		}
		return maxFileSize;
	}
	
	/**
	 * Returns true if totalFileSize is less than or equal to maxFileSize,
	 * otherwise false, if maxFileSize is 0L default maxFileSize is used.
	 * 
	 * @param totalFileSize
	 * 			- file size of the upload file/s
	 * @param maxFileSize
	 * 			- maximum allowable upload file size]
	 * 
	 * @return - true if totalFileSize is less than or equal to maxFileSize,
	 * 		otherwise false, if maxFileSize is 0L default maxFileSize is used.
	 */
	private boolean isValidFileSize(Long totalFileSize, Long maxFileSize) {
		//maxFileSize is empty, check file size with default file size
		if(maxFileSize.equals(0L))
			return isFileSizeValid(totalFileSize);
		return isFileSizeValidImpl(totalFileSize, maxFileSize);
	}
}
