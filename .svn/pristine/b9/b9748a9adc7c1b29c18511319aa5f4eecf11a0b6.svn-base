package com.ivant.cms.action.admin;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang3.StringEscapeUtils;
import org.joda.time.DateTime;
import com.ivant.cms.helper.DateHelper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.ivant.utils.HTMLTagStripper;
import com.ivant.utils.TextDiff;

public class LogHelper {
	
	public static <T> String[] getDifference(T oldObject, T newObject){
		
		Field[] fields = oldObject.getClass().getDeclaredFields();			//get all variables
		Method[] methods = oldObject.getClass().getDeclaredMethods();		//get all methods
		BeanComparator bc = new BeanComparator();
		ArrayList<String> ret = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		
		if(!oldObject.getClass().getName().equalsIgnoreCase(newObject.getClass().getName())){
			return null;		//compare the class name
		}
		
		for(int i=0; i<fields.length; i++){							
			for(int j=0; j<methods.length; j++){
				
				if(methods[j].getName().startsWith("get") || methods[j].getName().startsWith("is")){		//for each variable, get the corresponding get/is function
					
					if(
							methods[j].getName().replaceFirst("get", "").compareToIgnoreCase(fields[i].getName())==0 ||
							methods[j].getName().replaceFirst("is", "").compareToIgnoreCase(fields[i].getName())==0
					){
						bc.setProperty(fields[i].getName());
						
						try {
							//invoke the method
							if(
									methods[j].invoke(oldObject, new Object[0]) == null &&
									methods[j].invoke(newObject, new Object[0]) != null
							){
								//if the field switches from null to not null value
								sb = new StringBuilder();
								sb.append("Changed " + fields[i].getName().toLowerCase() + " from null to ");
								
								if(methods[j].getReturnType().equals(DateTime.class)){
									sb.append(DateHelper.formatDate( ((DateTime)methods[j].invoke(newObject, new Object[0])).toDate()));
								}else{
									sb.append(methods[j].invoke(newObject, new Object[0]).toString());	
								}
								ret.add("\"" + sb.toString() + "\"");
							}else if(
									methods[j].invoke(newObject, new Object[0]) == null &&
									methods[j].invoke(oldObject, new Object[0]) != null
							){
								//if the field value switches from not null to null value
								sb = new StringBuilder();
								
								sb.append("Changed " + fields[i].getName().toLowerCase() + " from \"");
								
								if(methods[j].getReturnType().equals(DateTime.class)){
									sb.append(DateHelper.formatDate(((DateTime) methods[j].invoke(oldObject, new Object[0])).toDate()));
								}else{
									sb.append(methods[j].invoke(oldObject, new Object[0]).toString());	
								}
								sb.append("\" to null");
								ret.add(sb.toString());
							}else if(
									methods[j].invoke(newObject, new Object[0]) != null &&
									methods[j].invoke(oldObject, new Object[0]) != null
							){
								//if the field value is both not null in the old and new object
								//compare the values
								
								Object oldRet = methods[j].invoke(oldObject, new Object[0]);
								Object newRet = methods[j].invoke(newObject, new Object[0]);
								String type = oldRet.getClass().getName();
								sb = new StringBuilder();
								String name = fields[i].getName().toLowerCase();
								
								if(type.compareTo("java.lang.String")==0){
									TextDiff td = new TextDiff();
									
									String oldRet2 = HTMLTagStripper.stripTags2((String) oldRet);
									String newRet2 = HTMLTagStripper.stripTags2((String) newRet);
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + ": "+ td.diff_prettyHtml(td.diff_main(oldRet2, newRet2)));
									}
								}else if(type.compareTo("java.lang.Boolean")==0){ 
									boolean oldRet2 = (Boolean) oldRet;
									boolean newRet2 = (Boolean) newRet;
									
									if(oldRet2 != newRet2){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}
								}else if(type.compareTo("java.lang.Character")==0){ 
									Character oldRet2 = (Character) oldRet;
									Character newRet2 = (Character) newRet;
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}
								}else if(type.compareTo("java.lang.Byte")==0){ 
									Byte oldRet2 = (Byte) oldRet;
									Byte newRet2 = (Byte) newRet;
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}
								}else if(type.compareTo("java.lang.Short")==0){ 
									Short oldRet2 = (Short) oldRet;
									Short newRet2 = (Short) newRet;
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}
								}else if(type.compareTo("java.lang.Integer")==0){ 
									Integer oldRet2 = (Integer) oldRet;
									Integer newRet2 = (Integer) newRet;
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}
								}else if(type.compareTo("java.lang.Long")==0){ 
									Long oldRet2 = (Long) oldRet;
									Long newRet2 = (Long) newRet;
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}
								}else if(type.compareTo("java.lang.Float")==0){ 
									Float oldRet2 = (Float) oldRet;
									Float newRet2 = (Float) newRet;
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}
								}else if(type.compareTo("java.lang.Double")==0){ 
									Double oldRet2 = (Double) oldRet;
									Double newRet2 = (Double) newRet;
									
									if(oldRet2.compareTo(newRet2)!=0){
										ret.add("Changed " + name + " from \"" + oldRet2 + "\" to \"" + newRet2 + "\"");
									}	
								}else{
									//complex object
									/*
									if(!oldRet.equals(newRet)){
										ret.add("Changed '"+ name +"'");
									}
									*/
								}
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		String[] ret2 = new String[0];
		ret2 = ret.toArray(ret2);
		return ret2; 
	}
	
	public static String join(String[] arr, String separator){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<arr.length; i++){
			if(i==arr.length-1){
				sb.append(arr[i]);
			}else{
				sb.append(arr[i] + separator);
			}
		}
		
		return sb.toString();
	}
}
