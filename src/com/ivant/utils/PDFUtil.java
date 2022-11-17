package com.ivant.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class PDFUtil {

	public static File createPDF(JRDataSource data, Map<String, Object> parameters, String templateName, String fileName, String directory)throws Exception{
		JasperReport jr = JasperCompileManager.compileReport(JRLoader.getResourceInputStream(templateName));	

		/* Fill Report */
		JasperPrint print = JasperFillManager.fillReport(jr, parameters, data);
		
		File directoryFile = new File(directory);
		
		if(!directoryFile.exists())
			directoryFile.mkdirs();
		
		File file = new File(directoryFile, fileName);
		FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
		
		byte[] pdfByteArray = JasperExportManager.exportReportToPdf( print );
		
		fos.write(pdfByteArray);
		
		fos.close();
		
		return file;
		
	}
	
	public static byte[] getBytes(File pdf) throws IOException {
		return getBytesFromFile(pdf);
	}
	
	private static byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    // Get the size of the file
	    long length = file.length();

	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    // Close the input stream and return bytes
	    is.close();
	    return bytes;
	}
}
