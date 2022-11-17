package com.ivant.cms.helper;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.ivant.cms.interfaces.BaseID;
import com.ivant.utils.ImageUtil;

public class ImageUploadHelper {

	private static Logger log = Logger.getLogger(ImageUploadHelper.class);

	private static int MAX_PICTURE_WIDTH = 400;
	private static int MAX_THUMB_WIDTH = 100;

	String REAL_PATH;
	String THUMB_FOLDER;
	String ORIG_FOLDER;

	public ImageUploadHelper(String orig_folder, String thumb_folder,
			String real_path) {
		this(orig_folder, thumb_folder, real_path, MAX_THUMB_WIDTH,
				MAX_PICTURE_WIDTH);
	}

	public ImageUploadHelper(String orig_folder, String thumb_folder,
			String real_path, Integer max_thumb_width, Integer picture_width) {
		if(max_thumb_width != null){
			MAX_THUMB_WIDTH = max_thumb_width;
		}
		if(picture_width != null){
			MAX_PICTURE_WIDTH = picture_width;
		}
		ORIG_FOLDER = orig_folder;
		REAL_PATH = real_path;
		THUMB_FOLDER = thumb_folder;

		createDirectories(REAL_PATH);
		createDirectories(REAL_PATH + File.separator + THUMB_FOLDER);
		createDirectories(REAL_PATH + File.separator + ORIG_FOLDER);
	}

	public <T extends BaseID<Long>> boolean upload(File fileToUpload,
			String filename, boolean resize) throws FileUploadException {

		if (!fileToUpload.exists()) {
			if (log.isDebugEnabled())
				log.debug("The file does not exists.");
			return false;
		}

		log.debug("filename:" + filename);
		filename = FileHelper.getFilename(filename);
		filename.replace(' ', '_');

		String frontfile = REAL_PATH + File.separator + filename;
		String thumbfile = REAL_PATH + File.separator + THUMB_FOLDER
				+ File.separator + filename;
		String origfile = REAL_PATH + File.separator + ORIG_FOLDER
				+ File.separator + filename;

		File fileOrig = new File(origfile);

		try {
			copyFile(fileToUpload, fileOrig);

			//Image will be resized if exceeded recommended Image Size
			if(resize){
				Image inImage = new ImageIcon(origfile).getImage();
				int width = inImage.getWidth(null);
				if (width > MAX_PICTURE_WIDTH) {
					// create a smaller graphic
					ImageUtil.scaleImage(origfile, frontfile, MAX_PICTURE_WIDTH);
				} else
					ImageUtil.scaleImage(origfile, frontfile, width);
				
				if (width > MAX_THUMB_WIDTH) {
					ImageUtil.scaleImage(origfile, thumbfile, MAX_THUMB_WIDTH);
				} else
					ImageUtil.scaleImage(origfile, thumbfile, width);
			}

			delete(fileToUpload);

		} catch (FileNotFoundException e) {
			log.error("A fileNotFound error occurred while uploading image "
					+ fileOrig.getName() + " : " + e.getMessage());
		} catch (IOException e) {
			log.error("An IO error occurred while uploading image "
					+ fileOrig.getName() + " : " + e.getMessage());
		}

		return true;
	}

	private void copyFile(File source, File dest) throws IOException {
		if (!dest.exists()) {
			dest.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}

	private boolean delete(File resource) throws IOException {
		return resource.delete();
	}

	private static boolean createDirectories(String directory) {
		File dir = new File(directory);
		return dir.mkdirs();
	}
}
