package com.ivant.cart.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
 
public class FileSearch {
 
  private String fileNameToSearch;
  private List<String> result = new ArrayList<String>();
 
  public String getFileNameToSearch() {
	return fileNameToSearch;
  }
 
  public void setFileNameToSearch(String fileNameToSearch) {
	this.fileNameToSearch = fileNameToSearch;
  }
 
  public List<String> getResult() {
	return result;
  }
 
  public void searchDirectory(File directory, String fileNameToSearch) {
 
	setFileNameToSearch(fileNameToSearch);
 
	if (directory.isDirectory()) {
	    search(directory);
	} else {
	    System.out.println(directory.getAbsoluteFile() + " is not a directory!");
	}
 
  }
 
  private boolean search(File file) {
	  File[] temp = null;
	  boolean returnResult = false;
	if (file.isDirectory()) {
 
            //do you have permission to read this directory?	
	    if (file.canRead() && file != null && file.listFiles() != null) {
	    	temp = file.listFiles();
	    	int len = file.listFiles().length;
		for (int ctr = 0; ctr < len; ctr++) {
			
		    if (temp[ctr].isDirectory() && temp[ctr].canRead() && temp != null) {
		    	
			    if(temp[ctr] != null && temp[ctr].canRead())
			    {
			    	returnResult = search(temp[ctr]);
			    	if(returnResult) {
			    		break;
			    	}
			    }
		    } else {
			if (getFileNameToSearch().equalsIgnoreCase(temp[ctr].getName().toLowerCase())) {			
			    result.add(temp[ctr].getAbsoluteFile().toString());
			    returnResult =  true;
			    break;
		    }
		    }
		
	    }
	    	}
 
	 } else {
		System.out.println(file.getAbsoluteFile() + "Permission Denied");
	 }
		return returnResult;
    }
 
  }
 

