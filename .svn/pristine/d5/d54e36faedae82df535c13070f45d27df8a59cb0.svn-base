package com.ivant.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

public class ImageElement extends ITextPdfElement {
	
	public static String FLOAT_LEFT = "left";
	public static String FLOAT_RIGHT = "right";
	
	private String file_path;
	private Float width;
	private Float height;
	private String floatTo;
	
	public ImageElement(String file_path, Float width, Float height, Integer x, Integer y) {
		this.file_path = file_path;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = (int) (y + height);
	}
	
	public ImageElement(String file_path, Float width, Float height, String floatTo, Integer y) {
		this.file_path = file_path;
		this.width = width;
		this.height = height;
		this.floatTo = floatTo;
		this.y = (int) (y + height);
	}
	
	private Boolean isLocalResource() {
		try {
			URL url = new URL(file_path);
		} catch(MalformedURLException e) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Image getImage() {
		Image image = null;
		
		try {
			if(isLocalResource()) {
				image = Image.getInstance(file_path);
			} else {
				image = Image.getInstance(new URL(file_path));
			}

			image.scaleAbsolute(width, height);
			
		} catch(BadElementException a) {
			a.printStackTrace();
		} catch(MalformedURLException b) {
			b.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public String getFloatTo() {
		return floatTo;
	}

	public void setFloatTo(String floatTo) {
		this.floatTo = floatTo;
	}

	public Float getWidth() {
		return width;
	}

	public Float getHeight() {
		return height;
	}
}
