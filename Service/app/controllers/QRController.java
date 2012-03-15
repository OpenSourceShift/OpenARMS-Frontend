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

	private static BufferedImage generateImage (String polltoken, int size) {
		String URL = ("http://openars.dk/" + polltoken);
		BitMatrix bm;
        Writer writer = new QRCodeWriter();
		try {
			bm = writer.encode(URL, BarcodeFormat.QR_CODE, size, size);
		
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int grayValue = (bm.get(x, y) ? 1 : 0) & 0xff;
                image.setRGB(x, y, (grayValue == 0 ?  0xFFFFFF : 0));
            }
        }

		return image;
		
		} catch (Exception e) {
            e.printStackTrace(System.err);
		}
		return null;
	}
	
	public static void returnImage () {
		try {
			String polltoken = params.get("token");
			int size = params.get("size", Integer.class).intValue();
			FileOutputStream fos = new FileOutputStream("Service/tmp/qrcodes/" +polltoken + "_" + size + ".jpg");
			BufferedImage image = generateImage(polltoken, size);
			ImageIO.write(image, "jpg", fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
         
		 
	}
}
