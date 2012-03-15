package controllers;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import play.mvc.Controller;



public class QRController extends Controller {

	private static BufferedImage generateImage (String URL, int size) {
		BitMatrix bm;
        Writer writer = new QRCodeWriter();
		try {
			bm = writer.encode("hello", BarcodeFormat.QR_CODE, 100, 100);
		
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                int grayValue = (bm.get(x, y) ? 1 : 0) & 0xff;
                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }
        
        /*FileOutputStream fos = new FileOutputStream("D:/image/image.jpg");
		ImageIO.write(image, "RGB", fos);
		fos.close();*/
		
		return image;
		
		} catch (Exception e) {
            e.printStackTrace(System.err);
		}
		return null;
	}
	
	public static void returnImage () {
		try {
			FileOutputStream fos = new FileOutputStream("D:/image/image.jpg");
			BufferedImage image = generateImage("URL",Integer.parseInt("size"));
			ImageIO.write(image, "jpg", fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
         
		 
	}
}
