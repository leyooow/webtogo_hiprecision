package com.ivant.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/*
 * Image utilitiy function that makes use of image magick library to process image
 * Author: Rey Bumalay <reyjexter@gmail.com>
 * Software Requirements:
 *    Image Magick - http://www.imagemagick.org
 * 
 * For Image Magick files (WINDOWS): 
 * 64bit - http://webtogo.com.ph/files/ImageMagick-7.0.5-5-portable-Q16-x64.zip
 * 32bit - http://webtogo.com.ph/files/ImageMagick-7.0.5-5-portable-Q16-x86.zip
 * Download and then set it in your classpath
 */
 
public final class ImageUtil {

	private static Logger logger = Logger.getLogger(ImageUtil.class);
	
	public static final int DEFAULT_RESOLUTION = 72;
	public static final int DEFAULT_IMAGE_WIDTH = 400;
	public static final int DEFAULT_IMAGE_HEIGHT = 400;
	public static final int DEFAULT_THUMBNAIL_WIDTH = 120;
	public static final int DEFAULT_THUMBNAIL_HEIGHT = 120;
	
	public static final String COMMAND_DEFAULT = "convert";
	public static final String COMMAND_ALTERNATIVE = "magick";
	
	private static final String OS_NAME = System.getProperty("os.name");

	public static void generateThumbnailImage(long companyId, String input, String output, int width, int height, int resolution, boolean forceResize) {
		if(width <= 0) {
			width = DEFAULT_THUMBNAIL_WIDTH;
		}
		if(height <= 0) {
			height = DEFAULT_THUMBNAIL_HEIGHT;
		}
		if(resolution <= 0) {
			resolution = DEFAULT_RESOLUTION;
		}
		
		output = FileUtil.replaceExtension(output, "jpg");
		
		try {
			if(companyId != 248) {
				String command = "";
				if(forceResize) {
					command = COMMAND_DEFAULT + " -colorspace rgb -thumbnail "+ width + "x" + height + "! \"" + input + "\" \"" + output + "\"";
				}
				else {
					command = COMMAND_DEFAULT + " -colorspace rgb -thumbnail "+ width + "x" + height + " \"" + input + "\" \"" + output + "\"";
				}
				Process process = executeShellCommand(command);
				generateImageIfNotExist(output, command);
				String[] result = readResult(process.getInputStream());
			} else {
				File newFile = new File(output);
				
				File old = new File(input);
				
				FileUtil.copyFile(old, newFile);
			}
			
		} 
		catch(Exception e) {
			logger.fatal("Unable to generate thumbnail image. ", e);
		}
	}
	
	
	
	public static void generateJpegImage(long companyId, String input, String output, int width, int height, int resolution, boolean forceResize) {
		if(width <= 0) {
			width = DEFAULT_IMAGE_WIDTH;
		}
		if(height <= 0) {
			height = DEFAULT_IMAGE_HEIGHT;
		}
		if(resolution <= 0) {
			resolution = DEFAULT_RESOLUTION;
		}

		//System.out.println("generateJpegImage( "+input+" , "+output+" , "+width+" , "+height+" , "+resolution+" , "+forceResize+")");
		
		
		output = FileUtil.replaceExtension(output, "jpg");
		
		try {
			if(companyId != 248) { 
				Map<String, String> fileInfo = identifyFile(input);						
				int inputHeight = height;
				int inputWidth = width;
				try
				{
					inputHeight = Integer.parseInt(fileInfo.get("height"));
					inputWidth = Integer.parseInt(fileInfo.get("width"));
				}
				catch(Exception e)
				{
				}
				
				String command = "";			 
				if(forceResize) {
						//command = COMMAND_DEFAULT + " -colorspace rgb -resample " + resolution + " -resize "+ width + "x" + height + "! \"" + input + "\" \"" + output + "\"";
						command = COMMAND_DEFAULT + " -colorspace rgb -resize "+ width + "x" + height + "! \"" + input + "\" \"" + output + "\"";
				}
				else {
					if(inputWidth > width || ((inputHeight > height) && (inputHeight >= inputWidth) )) {
						//command = COMMAND_DEFAULT + " -colorspace rgb -resample " + resolution + " -resize "+ width + "x" + height + " \"" + input + "\" \"" + output + "\"";
						command = COMMAND_DEFAULT + " -colorspace rgb -resize "+ width + "x" + height + " \"" + input + "\" \"" + output + "\"";
					}
					else {
						 command = COMMAND_DEFAULT + " -colorspace rgb \"" + input + "\" \"" + output + "\"";
					}
	
				} 
				
				Process process = executeShellCommand(command);
				generateImageIfNotExist(output, command);
				String[] result = readResult(process.getInputStream());
			} else {
				File newFile = new File(output);
				
				File old = new File(input);
				
				FileUtil.copyFile(old, newFile);
			}
		}
		catch(Exception e) {
			logger.fatal("Unable to generate jpeg image. ", e);
		}
	}
	
	public static void generateGifImage(long companyId, String input, String output, int width, int height, int resolution, boolean forceResize) {
		if(width <= 0) {
			width = DEFAULT_IMAGE_WIDTH;
		}
		if(height <= 0) {
			height = DEFAULT_IMAGE_HEIGHT;
		}
		if(resolution <= 0) {
			resolution = DEFAULT_RESOLUTION;
		}

				
		output = FileUtil.replaceExtension(output, "gif");
		
		try {
			
			if(companyId != 248) { 
				Map<String, String> fileInfo = identifyFile(input);						
				int inputHeight = height;
				int inputWidth = width;
				try
				{
					inputHeight = Integer.parseInt(fileInfo.get("height"));
					inputWidth = Integer.parseInt(fileInfo.get("width"));
				}
				catch(Exception e)
				{
				}
				
				String command = "";
				if(forceResize) {
						//command = COMMAND_DEFAULT + " -resample " + resolution + " -resize "+ width + "x" + height + "! \"" + input + "\" \"" + output + "\"";
					command = COMMAND_DEFAULT + " -resize "+ width + "x" + height + "! \"" + input + "\" \"" + output + "\"";
				}
				else {
					if(inputWidth > width || ((inputHeight > height) && (inputHeight >= inputWidth) )) {
						//command = COMMAND_DEFAULT + " -resample " + resolution + " -resize "+ width + "x" + height + " \"" + input + "\" \"" + output + "\"";
						command = COMMAND_DEFAULT + " -resize "+ width + "x" + height + " \"" + input + "\" \"" + output + "\"";
					}
					else {
						 command = COMMAND_DEFAULT + "  \"" + input + "\" \"" + output + "\"";
					}
		
				}
				
				Process process = executeShellCommand(command);
				generateImageIfNotExist(output, command);
				String[] result = readResult(process.getInputStream());
			} else {
				File newFile = new File(output);
				
				File old = new File(input);
				
				FileUtil.copyFile(old, newFile);
			}
		}
		catch(Exception e) {
			logger.fatal("Unable to generate gif image. ", e);
		}
	}	
	
	public static void generateJpegImage(long companyId, String input, String output) {
		ImageUtil.generateJpegImage(companyId, input, output, DEFAULT_IMAGE_WIDTH, 
				DEFAULT_IMAGE_HEIGHT, DEFAULT_RESOLUTION, false);
	}
	
	public static void generateGifThumbnailImage(long companyId, String input, String output, int width, int height, int resolution, boolean forceResize) {
		if(width <= 0) {
			width = DEFAULT_THUMBNAIL_WIDTH;
		}
		if(height <= 0) {
			height = DEFAULT_THUMBNAIL_HEIGHT;
		}
		if(resolution <= 0) {
			resolution = DEFAULT_RESOLUTION;
		}
		
		output = FileUtil.replaceExtension(output, "gif");
		
		try {
			
			if(companyId != 248) {
			
				String command = "";
				if(forceResize) {
					command = COMMAND_DEFAULT + " -thumbnail "+ width + "x" + height + "! \"" + input + "\" \"" + output + "\"";
				}
				else {
					command = COMMAND_DEFAULT + " -thumbnail "+ width + "x" + height + " \"" + input + "\" \"" + output + "\"";
				}
				Process process = executeShellCommand(command);
				generateImageIfNotExist(output, command);
				String[] result = readResult(process.getInputStream());
			
			} else {
				File newFile = new File(output);
				
				File old = new File(input);
				
				FileUtil.copyFile(old, newFile);
			}
		} 
		catch(Exception e) {
			logger.fatal("Unable to generate thumbnail image. ", e);
		}
	}
	
	public static void generateGifThumbnailImage(long companyId, String input, String output) {
		generateGifThumbnailImage(companyId, input, output, DEFAULT_THUMBNAIL_WIDTH, 
				DEFAULT_THUMBNAIL_HEIGHT, DEFAULT_RESOLUTION, false);
	}	
	
	
	public static void generateThumbnailImage(long companyId, String input, String output) {
		generateThumbnailImage(companyId, input, output, DEFAULT_THUMBNAIL_WIDTH, 
				DEFAULT_THUMBNAIL_HEIGHT, DEFAULT_RESOLUTION, false);
	}
	
	public static Map<String,String> identifyFile(String input) {	
		Map<String, String> fileInfo = new Hashtable<String, String>();
		
		String command = "identify -format %w/%h \""+ input +"\"";
		
		
		Process process = executeShellCommand(command);
		String[] result = readResult(process.getInputStream());

		if(result.length > 0) {
			
			String[] info = result[0].split("/");
			fileInfo.put("width", info[0]);
			fileInfo.put("height", info[1]);
		}
		
			
		return fileInfo;
	}
	
	private static String[] readResult(InputStream fileSteam){
		List<String> resultList = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileSteam));
			String str = reader.readLine();
			while(str != null) {
				resultList.add(str);
				str = reader.readLine();
			}
		}
		catch(IOException ioe) {
			logger.fatal("Unable to read result. ", ioe);
		}
		String[] result = new String[resultList.size()];
		if(resultList.size()>0)
			resultList.toArray(result);
		return result;
	}
	
	private static void showResult(String[] result) {
		for(String s : result) {
			logger.debug(s);
		}
	}
	
	private static Process executeShellCommand(String command) {
		Process process = null;
				
		logger.info("EXECUTING COMMAND: " + command);
		logger.info("OS_NAME :" + OS_NAME);
		try {
			// if(OS_NAME.equals("Windows XP") || OS_NAME.equals("Windows Vista") || OS_NAME.equals("Windows 7") ) {
			if(OS_NAME.contains("Windows") ) { 
				process = Runtime.getRuntime().exec(
						new String[] { 
								"cmd",
								"/c",
								command
						}
				);
			}
			else {
				process = Runtime.getRuntime().exec(
						new String[] {
								"sh",
								"-c",
								command
						}
				);
			}
		}
		catch(Exception e) {
			logger.fatal("Unable to execute shell command. " + e);
		}
				
		return process;
	}
	
	/**
	 *  uses COMMAND_ALTERNATIVE shell/executable instead of COMMAND_DEFAULT
	 * @param filePath
	 * @param command
	 */
	private static void generateImageIfNotExist(String filePath, String command)
	{
		final File file = new File(filePath);
		if(!file.exists())
		{
			final String newCommand = command.replace(COMMAND_DEFAULT, COMMAND_ALTERNATIVE);
			executeShellCommand(newCommand);
		}
	}
	
	/**
	 * Reads an image in a file and resizes the image. 
	 * Image will only be created if it's larger than the given <code>maxDim</code>
	 * 
	 * @param orig The name of image file.
	 * @param thumb The name of thumbnail file.
	 * @param maxDim The width and height of the thumbnail must be maxDim pixels or less.
	 * 
	 * @author Jay Q.
	 */
	public static void scaleImage(String origImageFile, String thumb, 
			float maxDim) throws FileNotFoundException,	IOException {

		BufferedImage inImage = ImageIO.read(new File(origImageFile));
		int width = inImage.getWidth(null);
		int height = inImage.getHeight(null);

		// we only generate thumbs for wide images, otherwise just copy the image
		if (width <= maxDim) {
			
			if(logger.isDebugEnabled()) logger.debug("image is smaller than or equal to desired size.");
			
			/* COPY FILE IF SMALLER THAN PREFERRED MAX DIMENSION */
			FileUtils.copyFile(new File(origImageFile), new File(thumb));
			return;
			
		}

		// Determine the scale.
		double scale = (double) maxDim / (double) height;

		if (width > height) {
			scale = (double) maxDim / (double) width;
		}

		// Determine size of new image.
		// One of them should equal maxDim.
		int scaledW = width;
		int scaledH = height;

		if (width >= maxDim) {
			scaledW = (int) (scale * width);
			scaledH = (int) (scale * height);
		}

		// Create an image buffer in
		// which to paint on.
		BufferedImage outImage = null;
		Image src = null;
		try {
			outImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
			src = inImage.getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);
		} catch (Exception e) {
			//System.out.println("height: " + height);
			//System.out.println("width: " + width);
			 e.printStackTrace();
			return;
		}

		// Set the scale.
		AffineTransform tx = new AffineTransform();

//		if (scale < 1.0d) {
//			tx.scale(scale, scale);
//			System.out.println("tx here");
//		}
		
		// Paint image.
		Graphics2D g2d = outImage.createGraphics();
		g2d.drawImage(src, tx, null);
		g2d.dispose();

		ImageIO.write(outImage, "jpg", new File(thumb));

		// JPEG-encode the image and write to file.
		/*OutputStream os = new FileOutputStream(thumb);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);

		encoder.encode(outImage);
		os.close();*/
		inImage.flush();
		inImage = null;
		outImage = null;
		//encoder = null;
		g2d = null;
	}
}	
