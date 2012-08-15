package controllers;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import play.Play;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Header;
import play.mvc.Router;
import play.mvc.Router.ActionDefinition;


/**
 * Class QRController is resposible for creating, storing and rendering QRCode.
 * @author Kronics_2
 *
 */
public class QRController extends Controller {

	/** 
	 * This function generates the QRcode of an URL based on the poll with a determined polltoken.
	 * @param polltoken - The poll token to use for linking the QRCode image to the poll.
	 * @param size - The size of the image.
	 * @return - Returns the image or null if error.
	 */

	private static BufferedImage generateImage(String token, int size) throws Exception {
		// Generate the url to be displayed.
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("token", token);
		ActionDefinition ad = Router.reverse("JoinPoll.index", args);
		ad.absolute();
		System.out.println(ad.url);
		String url = ad.url;
		
		// Crop at 1000.
		if(size > 1000) {
			size = 1000;
		}
		
		BitMatrix bm;
		Writer writer = new QRCodeWriter();
		bm = writer.encode(url, BarcodeFormat.QR_CODE, size, size);

		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if(bm.get(x,y)) {
					image.setRGB(x, y, 0x000000);
				} else {
					image.setRGB(x, y, 0xFFFFFF);
				}
			}
		}
		return image;
	}

	/**
	 * This function is called from the Routes file, it creates a file in the tmp folder with the image of
	 * the QRCode if it doesn't exist, if it does, uses it. 
	 */
	public static void renderImage(String token, Integer size, String format) throws Exception {
		String path = Play.applicationPath.getPath();
		File tempDirectory = new File(path+"/tmp/qr-cache/");
		if (!tempDirectory.isDirectory()) {
			boolean success = tempDirectory.mkdir();
			if(!success) {
				throw new Exception("Couldn't create the temporary qrcodes directory.");
			}
		}
		
		File cachedFile = new File(tempDirectory, token + "." + size.toString() + "." + format);
		
		if (!cachedFile.exists()) {
			FileOutputStream fos = new FileOutputStream(cachedFile);
			BufferedImage image = generateImage(token, size);
			ImageIO.write(image, format, fos);
			fos.close();
		}
		renderBinary(cachedFile);
	}
}
