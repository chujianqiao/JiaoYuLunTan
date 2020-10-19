package cn.stylefeng.guns.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成二维码的工具类
 * @author wucy
 */
public class QrCodeUtil {

	public static void main(String[] args) {
		//  要生成二维码的链接
		String url = "https://qr.alipay.com/bax04646qt4xyj7zqh7y0036";
		//	指定路径：D:\User\Desktop\testQrcode
		String path = "F:\\testDir";
		//	指定二维码图片名字
		String fileName = "a" + ".jpg";
		createQrCode(url, path, fileName);
	}

	public static String createQrCode(String url, String path, String fileName) {
		String filePath = "";
		try {
			Map<EncodeHintType, String> hints = new HashMap<>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
			File file = new File(path, fileName);
			filePath = file.getAbsolutePath();
			if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
				writeToFile(bitMatrix, "jpg", file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}

	static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

}
