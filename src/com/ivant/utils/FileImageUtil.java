package com.ivant.utils;


public class FileImageUtil {
	
	public static String getFileImage(String fileName){
		String fileImage = "";
		if(FileUtil.getExtension(fileName).equalsIgnoreCase("pdf"))
			fileImage ="<img src='images/pdf.gif' border='0'>";
		else if(FileUtil.getExtension(fileName).equalsIgnoreCase("doc"))
			fileImage ="<img src='images/word.gif' border='0'/>";
		else if(FileUtil.getExtension(fileName).equalsIgnoreCase("xls"))
			fileImage ="<img src='images/excel.gif' border='0'/>";
		else if(FileUtil.getExtension(fileName).equalsIgnoreCase("mp3"))
			fileImage ="<img src='images/music.png' border='0'/>";
		else 
			fileImage ="<img src='images/text.gif' border='0'/>";
		return fileImage;
	}

}
