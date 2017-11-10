package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {

	@SuppressWarnings("unused")
	public static void createThumbnail(String imageFileS, String thumbnailFileS, int maxWidth, int maxHeight) throws IOException {
		File imageFile = new File(imageFileS);   
       File thumbnailFile = new File(thumbnailFileS); 
		
       if (imageFile == null) {
			throw new IllegalArgumentException("imageFile connot be null!");
		}
		if (thumbnailFile == null) {
			throw new IllegalArgumentException("thumbnailFile connot be null!");
		}
		if (maxWidth <= 0) {
			throw new IllegalArgumentException("maxWidth must > 0");
		}
		if (maxHeight <= 0) {
			throw new IllegalArgumentException("maxHeight must > 0");
		}
	
		try {
			BufferedImage image = ImageIO.read(imageFile);
		
			/* 源图宽和高 */
			int imageWidth  = image.getWidth(); 
			int imageHeight = image.getHeight();
			if (maxWidth >= imageWidth && maxHeight >= imageHeight){
				InputStream in = null;
				OutputStream out = null;
				in = new BufferedInputStream(new FileInputStream(imageFileS), 1024);
				out = new BufferedOutputStream(new FileOutputStream(thumbnailFileS), 1024);
				byte[] buffer = new byte[1024];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
				return;
			}
		
			/* 按比例缩放图像 */
			double scaleZ = (double) imageWidth / imageHeight;
			if (scaleZ > 0) {
				imageWidth  = maxWidth;
				imageHeight = (int) (maxWidth  / scaleZ);
				if(imageHeight>maxHeight){
					imageHeight=maxHeight;
					imageWidth =(int)(maxHeight*scaleZ);
				}
			} else {
				imageWidth  = (int) (maxHeight * scaleZ);
				imageHeight = maxHeight;
				if(imageWidth>maxWidth){
					imageWidth=maxWidth;
					imageHeight =(int)(maxWidth / scaleZ);
				}
			}
		
			/* 根据源图和缩略图宽高生成一张图片？ */
			ImageFilter    filter = new java.awt.image.AreaAveragingScaleFilter(imageWidth, imageHeight);
			ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
			Image newImage = Toolkit.getDefaultToolkit().createImage(producer);
			ImageIcon imageIcon = new ImageIcon(newImage);
			Image scaleImage = imageIcon.getImage();
			BufferedImage thumbnail = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = thumbnail.createGraphics();
			g2d.drawImage(scaleImage, 0, 0, null);
			g2d.dispose();
		
			ImageIO.write(thumbnail, "jpeg", thumbnailFile);
			
		} catch (IOException e) {
			throw new IOException("Connot create thumbnail, Please check 'imageFile' or 'thumbnailFile'!");
		}
	}
		
	public static void main(String[] args) {
		try {
				createThumbnail("D:\\187.jpg", "D:\\auth.jpg", 100, 120);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
