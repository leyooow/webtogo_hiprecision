/**
 * 
 */
package com.ivant.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * @author Jayvie Rioflorido
 * @version 1.0 Oct 9, 2015
 * @since 1.0, Oct 9, 2015
 */
public class QRGenerator
{
	
	public File generateQRCode(String textcode, File qrCodeFile)
	{
		try
		{
			int size = 250;
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(textcode, BarcodeFormat.QR_CODE, size, size);
			int width = byteMatrix.getWidth();
			
			BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
			
			Graphics2D graphics = image.createGraphics();
			;
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, width, width);
			graphics.setColor(Color.BLACK);
			
			for(int i = 0; i < width; i++)
			{
				for(int j = 0; j < width; j++)
				{
					if(byteMatrix.get(i, j))
					{
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, "jpg", qrCodeFile);
			
			File newQrCodeFile = new File(qrCodeFile.getAbsolutePath());
			System.out.println("\n\nYou have successfully created QR Code.");
			
			return newQrCodeFile;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
