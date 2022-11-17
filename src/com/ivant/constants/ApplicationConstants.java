package com.ivant.constants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ivant.cms.helper.ClassHelper;

public class ApplicationConstants{
	public static final float VAT = 0.12f;
	public static final String MULTIPAGE_FILES_DIRECTORY_NAME = "multipage_uploads";
	public static final int MAX_BATCH_UPDATE_ITEMS = 10000;
	
	public static List<String> CONTENT_TYPES = new ArrayList<String>();
	
	static{
		final InputStream inputStream = ClassHelper.getResourceAsStream("content_types.txt");
		Scanner scanner = new Scanner(inputStream);
		while(scanner.hasNext()){
			String str = scanner.nextLine();
			if(str != null){
				str = str.trim();
				if(!str.isEmpty()){
					CONTENT_TYPES.add(str);
				}
			}
		}
		scanner.close();
		System.out.println("" + CONTENT_TYPES);
	}	
}
