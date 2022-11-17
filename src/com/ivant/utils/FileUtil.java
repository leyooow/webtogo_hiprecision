package com.ivant.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public final class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);
	
	public static final String EXTENSION_DOC = "doc";
	public static final String EXTENSION_DOCX = "docx";
	public static final String EXTENSION_PDF = "pdf";
	public static final String EXTENSION_XLS = "xls";
	public static final String EXTENSION_XLSX = "xlsx";
	public static final String[] EXTENSIONS_EXCEL = { EXTENSION_XLS, EXTENSION_XLSX };
	public static final String[] EXTENSIONS_WORD = { EXTENSION_DOC, EXTENSION_DOCX };
	
	public static void copyFile(File source, File destination) {
		FileInputStream input = null;
		FileOutputStream output = null;
		//System.out.println("destination: " + destination.getAbsolutePath());
		try {
			if(!destination.exists()) {
				destination.createNewFile();
			}
			input = new FileInputStream(source);
			output = new FileOutputStream(destination);
			
			byte[] buf = new byte[1024];
			int len;
			while((len = input.read(buf)) > 0) {
				output.write(buf, 0, len);
			}
			
		}
		catch(Exception e) {
			logger.fatal("failed to copy file " + source.getAbsolutePath() + " to " + 
					destination.getAbsolutePath(), e);
		}
		finally {
			try {
				if(output != null) {
					output.close();
				}
	
				if(input != null) {
					input.close();
				}
			}
			catch(IOException ioe) {}
		}
	}
	
	public static boolean createDirectory(String path) {
		File file = new File(path.replaceAll(" ", "_"));
		return file.mkdirs();
	}	

	public static boolean deleteFile(String filepath) {
		logger.debug("DELETING: " + filepath);
		File file = new File(filepath);
		return file.delete();
	}
	
	public static String insertPostfix(String filename, String postfix) {
		String[] file = filename.split("[.]");
		StringBuffer sb = new StringBuffer();
		sb.append(file[0]);
		sb.append("-");
		sb.append(postfix);
		sb.append(".");
		sb.append(file[1]);
		return sb.toString();
	}
	
	public static String replaceExtension(String filename, String newExtension) {
		String[] filenameSeparated = filename.split("[.]");
		filenameSeparated[filenameSeparated.length-1] = newExtension;
		StringBuffer sb = new StringBuffer();
		for(String s: filenameSeparated) {
			sb.append(s + ".");
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}
	
	public static String getExtension(String filename) {
		String[] filenameSeparated = filename.split("[.]");
		logger.debug("get extension: " + filenameSeparated[filenameSeparated.length-1]);
		return filenameSeparated[filenameSeparated.length-1];

		}
	
	public static String unZip (String inputZip, String destinationDirectory) throws IOException
	{
		int BUFFER = 2048;
	    List zipFiles = new ArrayList();
	    File sourceZipFile = new File(inputZip);
	    File unzipDestinationDirectory = new File(destinationDirectory);
	    unzipDestinationDirectory.mkdir();

	    ZipFile zipFile; 
	    // Open Zip file for reading
	    zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);

	    // Create an enumeration of the entries in the zip file
	    Enumeration zipFileEntries = zipFile.entries();

	    // Process each entry
	    while (zipFileEntries.hasMoreElements()) {
	        // grab a zip file entry
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

	        String currentEntry = entry.getName();

	        File destFile = new File(unzipDestinationDirectory, currentEntry);
	        destFile = new File(unzipDestinationDirectory, destFile.getName());

	        if (currentEntry.endsWith(".zip")) {
	            zipFiles.add(destFile.getAbsolutePath());
	        }
	        
	        //System.out.println("file unzip : "+ destFile.getAbsoluteFile());
	        //System.out.println("file unzip : "+ destFile.getName());

	        // grab file's parent directory structure
	        File destinationParent = destFile.getParentFile();

	        // create the parent directory structure if needed
	        destinationParent.mkdirs();
	        
	        try {
	            // extract file if not a directory
	            if (!entry.isDirectory()) {
	                BufferedInputStream is =
	                        new BufferedInputStream(zipFile.getInputStream(entry));
	                int currentByte;
	                // establish buffer for writing file
	                byte data[] = new byte[BUFFER];
	                
	                // write the current file to disk
	                FileOutputStream fos = new FileOutputStream(destFile);
	                BufferedOutputStream dest =
	                        new BufferedOutputStream(fos, BUFFER);

	                // read and write until last byte is encountered
	                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                    dest.write(data, 0, currentByte);
	                }
	                dest.flush();
	                dest.close();
	                is.close();
	            }
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	    }
	    zipFile.close();

	    for (Iterator iter = zipFiles.iterator(); iter.hasNext();) {
	        String zipName = (String)iter.next();
	        unZip(
	            zipName,
	            destinationDirectory +
	                File.separatorChar +
	                zipName.substring(0,zipName.lastIndexOf(".zip"))
	        );
	    }
	    return "success";

	}
	

	/**
	 * returns true if the file contains the given keyword
	 * @param file
	 * @param keyword
	 * @param ignoreCase
	 * @return
	 */
	public static final boolean searchText(File file, String keyword, boolean ignoreCase)
	{
		try
		{
			if(file.exists() && keyword != null)
			{
				final String extension = FileUtil.getExtension(file.getAbsolutePath());
				if(ArrayUtils.contains(EXTENSIONS_EXCEL, extension))
				{
					return searchTextExcel(file, keyword, ignoreCase);
				}
				else if(ArrayUtils.contains(EXTENSIONS_WORD, extension))
				{
					return searchTextWord(file, keyword, ignoreCase);
				}
				else if(StringUtils.contains(EXTENSION_PDF, extension))
				{
					return searchTextPDF(file, keyword, ignoreCase);
				}
			}
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}/*
		catch(InvalidFormatException ife)
		{
			ife.printStackTrace();
		}*/
		return false;
	}
	
	private static final boolean searchTextExcel(File file, String keyword, boolean ignoreCase) throws IOException, InvalidFormatException
	{
		final FileInputStream fis = new FileInputStream(file);
		
		final Workbook workbook = WorkbookFactory.create(fis);
		
		for(int i = 0; i < workbook.getNumberOfSheets(); i++)
		{
			final Sheet sheet = workbook.getSheetAt(i);
			final Iterator<Row> rows = sheet.rowIterator();
			while(rows.hasNext())
			{
				try
				{
					final Row row = rows.next();
					for(short j = row.getFirstCellNum(); j < row.getLastCellNum(); j++)
					{
						final Cell cell = row.getCell(j);
						
						final String value = getCellStringValue(cell);
						
						if(ignoreCase && StringUtils.containsIgnoreCase(value, keyword))
						{
							return true;
						}
						else if(StringUtils.contains(value, keyword))
						{
							return true;
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	private static final boolean searchTextWord(File file, String keyword, boolean ignoreCase) throws IOException
	{
		final FileInputStream fis = new FileInputStream(file);
		
		String text = null;
		
		if(getExtension(file.getName()).equalsIgnoreCase(EXTENSION_DOC))
		{
			final HWPFDocument doc = new HWPFDocument(fis);
            final WordExtractor we = new WordExtractor(doc);
            text = we.getText();
		}
		else if(getExtension(file.getName()).equalsIgnoreCase(EXTENSION_DOCX))
		{
			final XWPFDocument doc = new XWPFDocument(fis);
			final XWPFWordExtractor we = new XWPFWordExtractor(doc);
			text = we.getText();
		}
		
		return ignoreCase
			? StringUtils.containsIgnoreCase(text, keyword)
			: StringUtils.contains(text, keyword);
	}
	
	private static final boolean searchTextPDF(File file, String keyword, boolean ignoreCase) throws IOException
	{
		final PDDocument doc = PDDocument.load(file);
		final PDFTextStripper stripper = new PDFTextStripper();
		final String text = stripper.getText(doc);
		
		return ignoreCase
			? StringUtils.containsIgnoreCase(text, keyword)
			: StringUtils.contains(text, keyword);
	}
	
	private static final String getCellStringValue(Cell cell/*, boolean isDate*/)
	{
		if(cell == null)
		{
			return null;
		}
		
		try
		{
			/*switch(cell.getCellType())
			{
				case Cell.CELL_TYPE_NUMERIC:
					obj = isDate
						? cell.getDateCellValue()
						: cell.getNumericCellValue();
				case Cell.CELL_TYPE_STRING:
					obj = cell.getStringCellValue();
				case Cell.CELL_TYPE_FORMULA:
					obj = cell.getCellFormula();
				case Cell.CELL_TYPE_BLANK:
					obj = "";
				case Cell.CELL_TYPE_BOOLEAN:
					obj = Boolean.valueOf(cell.getBooleanCellValue());
				case Cell.CELL_TYPE_ERROR:
					obj = Byte.valueOf(cell.getErrorCellValue());
				default:
					break;
			}
			*/
			
			cell.setCellType(Cell.CELL_TYPE_STRING);
			
			return cell.getStringCellValue();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
