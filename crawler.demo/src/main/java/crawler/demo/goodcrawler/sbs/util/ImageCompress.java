package crawler.demo.goodcrawler.sbs.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.regexp.recompile;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 *
 * @desc ����ͼ�ࣨͨ�ã� ��java���ܽ�jpg��bmp��png��gifͼƬ�ļ������еȱȻ�ǵȱȵĴ�Сת���� ����ʹ�÷���
 *       compressPic(��ͼƬ·��
 *       ,����СͼƬ·��,��ͼƬ�ļ���,����СͼƬ����,����СͼƬ���,����СͼƬ�߶�,�Ƿ�ȱ�����(Ĭ��Ϊtrue))
 */
public class ImageCompress {

	private File file = null; // �ļ�����
	private String inputDir; // ����ͼ·��
	private String outputDir; // ���ͼ·��
	private String inputFileName; // ����ͼ�ļ���
	private String outputFileName; // ���ͼ�ļ���
	private int outputWidth = 100; // Ĭ�����ͼƬ��
	private int outputHeight = 100; // Ĭ�����ͼƬ��
	private boolean proportion = true; // �Ƿ�ȱ����ű��(Ĭ��Ϊ�ȱ�����)
	
	public ImageCompress() { // ��ʼ������
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		outputWidth = 100;
		outputHeight = 100;
	}
	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setOutputWidth(int outputWidth) {
		this.outputWidth = outputWidth;
	}

	public void setOutputHeight(int outputHeight) {
		this.outputHeight = outputHeight;
	}

	public void setWidthAndHeight(int width, int height) {
		this.outputWidth = width;
		this.outputHeight = height;
	}
	/*
	 * ���ͼƬ��С ������� String path ��ͼƬ·��
	 */
	public long getPicSize(String path) {
		file = new File(path);
		return file.length();
	}
	
	public String compressPic() {
		try {
			file = new File(inputDir + inputFileName);
			if (!file.exists()) {
				return "";
			}
			Image img = ImageIO.read(file);
			if (img.getWidth(null) == -1) {
				System.out.println("Can't read ,retry ! "+ "<BR>");
				return "no";
			}else {
				int newWidth;
				int newHeight;
				if (this.proportion == true) {
					double rate1 = ((double)img.getWidth(null))/(double)outputWidth + 0.1;
					double rate2 = ((double)img.getHeight(null))/(double)outputHeight + 0.1;
					
					double rate = rate1 > rate2? rate1:rate2;
					newWidth = (int)(((double)img.getWidth(null))/rate);
					newHeight = (int)(((double)img.getHeight(null))/rate);
				}else {
					newWidth = outputWidth;
					newHeight = outputHeight;
				}
				BufferedImage  tag = new BufferedImage((int)newWidth,(int)newHeight,BufferedImage.TYPE_INT_RGB);
				
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH),0,0,null);
				
				FileOutputStream outputStream = new FileOutputStream(outputDir + outputFileName);
				
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
				encoder.encode(tag);
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "OK";
	}

	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName) {
		// ����ͼ·��
		this.inputDir = inputDir;
		// ���ͼ·��
		this.outputDir = outputDir;
		// ����ͼ�ļ���
		this.inputFileName = inputFileName;
		// ���ͼ�ļ���
		this.outputFileName = outputFileName;
		return compressPic();
	}
	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			boolean gp) {
		// ����ͼ·��
		this.inputDir = inputDir;
		// ���ͼ·��
		this.outputDir = outputDir;
		// ����ͼ�ļ���
		this.inputFileName = inputFileName;
		// ���ͼ�ļ���
		this.outputFileName = outputFileName;
		// ����ͼƬ����
		setWidthAndHeight(width, height);
		// �Ƿ��ǵȱ����� ���
		this.proportion = gp;
		return compressPic();
	}
	public static final MediaTracker tracker = new MediaTracker(
			new Component() {
				private static final long serialVersionUID = 1234162663955668507L;
			});

	/**
	 * @param originalFile
	 *            ԭͼ��
	 * @param resizedFile
	 *            ѹ�����ͼ��
	 * @param width
	 *            ͼ���
	 * @param height
	 *            ͼ��� Ϊ-1ʱ��ԭ�г������ѹ��������ָ������ѹ��
	 * @param format
	 *            ͼƬ��ʽ jpg, png, gif(�Ƕ���)
	 * @throws IOException
	 */
	public void resize(File originalFile, File resizedFile, int width,
			int height, String format) throws IOException {
		if (format != null && "gif".equals(format.toLowerCase())) {
			resize(originalFile, resizedFile, width, height, 1);
			return;
		}
		FileInputStream fis = new FileInputStream(originalFile);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		int readLength = -1;
		int bufferSize = 1024;
		byte bytes[] = new byte[bufferSize];
		while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
			byteStream.write(bytes, 0, readLength);
		}
		byte[] in = byteStream.toByteArray();
		fis.close();
		byteStream.close();

		Image inputImage = Toolkit.getDefaultToolkit().createImage(in);
		waitForImage(inputImage);
		int imageWidth = inputImage.getWidth(null);
		if (imageWidth < 1)
			throw new IllegalArgumentException("image width " + imageWidth
					+ " is out of range");
		int imageHeight = inputImage.getHeight(null);
		if (imageHeight < 1)
			throw new IllegalArgumentException("image height " + imageHeight
					+ " is out of range");

		// Create output image.
		if (height == -1) {
			double scaleW = (double) imageWidth / (double) width;
			double scaleY = (double) imageHeight / (double) height;
			if (scaleW >= 0 && scaleY >= 0) {
				if (scaleW > scaleY) {
					height = -1;
				} else {
					width = -1;
				}
			}
		}
		Image outputImage = inputImage.getScaledInstance(width, height,
				java.awt.Image.SCALE_DEFAULT);
		checkImage(outputImage);
		encode(new FileOutputStream(resizedFile), outputImage, format);
	}

	/** Checks the given image for valid width and height. */
	private static void checkImage(Image image) {
		waitForImage(image);
		int imageWidth = image.getWidth(null);
		if (imageWidth < 1)
			throw new IllegalArgumentException("image width " + imageWidth
					+ " is out of range");
		int imageHeight = image.getHeight(null);
		if (imageHeight < 1)
			throw new IllegalArgumentException("image height " + imageHeight
					+ " is out of range");
	}

	/**
	 * Waits for given image to load. Use before querying image
	 * height/width/colors.
	 */
	private static void waitForImage(Image image) {
		try {
			tracker.addImage(image, 0);
			tracker.waitForID(0);
			tracker.removeImage(image, 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** Encodes the given image at the given quality to the output stream. */
	private static void encode(OutputStream outputStream, Image outputImage,
			String format) throws java.io.IOException {
		int outputWidth = outputImage.getWidth(null);
		if (outputWidth < 1)
			throw new IllegalArgumentException("output image width "
					+ outputWidth + " is out of range");
		int outputHeight = outputImage.getHeight(null);
		if (outputHeight < 1)
			throw new IllegalArgumentException("output image height "
					+ outputHeight + " is out of range");
		// Get a buffered image from the image.
		BufferedImage bi = new BufferedImage(outputWidth, outputHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D biContext = bi.createGraphics();
		biContext.drawImage(outputImage, 0, 0, null);
		ImageIO.write(bi, format, outputStream);
		outputStream.flush();
	}

	/**
	 * ����gifͼƬ
	 * 
	 * @param originalFile
	 *            ԭͼƬ
	 * @param resizedFile
	 *            ���ź��ͼƬ
	 * @param newWidth
	 *            ���
	 * @param newHeight
	 *            �߶� Ϊ-1ʱ��ԭ�г������ѹ��������ָ������ѹ��
	 * @param quality
	 *            ���ű��� (�ȱ���)
	 * @throws IOException
	 */
	public void resize(File originalFile, File resizedFile,
			int newWidth, int newHeight, float quality) throws IOException {
		if (quality < 0 || quality > 1) {
			throw new IllegalArgumentException(
					"Quality has to be between 0 and 1");
		}
		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image i = ii.getImage();
		Image resizedImage = null;
		int iWidth = i.getWidth(null);
		int iHeight = i.getHeight(null);
		if (newHeight == -1) {
			if (iWidth > iHeight) {
				resizedImage = i.getScaledInstance(newWidth,
						(newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
			} else {
				resizedImage = i.getScaledInstance((newWidth * iWidth)
						/ iHeight, newWidth, Image.SCALE_SMOOTH);
			}
		} else {
			resizedImage = i.getScaledInstance(newWidth, newHeight,
					Image.SCALE_SMOOTH);
		}
		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();
		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();
		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);
		// Write the jpeg to a file.
		FileOutputStream out = FileUtils.openOutputStream(resizedFile);
		// Encodes image as a JPEG data stream
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder
				.getDefaultJPEGEncodeParam(bufferedImage);
		param.setQuality(quality, true);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(bufferedImage);
	}

	/**
	 * �ӿڣ���ָ��Ҫ����ͼ�����ͣ�
	 * 
	 * @param originalFile
	 *            ԭͼƬ
	 * @param resizedFile
	 *            ���ź��ͼƬ
	 * @param newWidth
	 *            ���
	 * @param newHeight
	 *            �߶� Ϊ-1ʱ��ԭ�г������ѹ��������ָ������ѹ��
	 * @throws IOException
	 */
	public void resize(File originalFile, File resizedFile,
			int newWidth, int newHeight) throws IOException {
		// String originalFileName = originalFile.getName();
		// int index = originalFileName.lastIndexOf(".");
		// String originalFileType = originalFileName.substring(index+1);
		// resize(originalFile,resizedFile,newWidth,newHeight,originalFileType);
		try {
			saveImageAsJpg(originalFile, resizedFile, newWidth, newHeight, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ʵ��ͼ��ĵȱ�����
	 * 
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @return
	 */
	public BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {
		// targetW��targetH�ֱ��ʾĿ�곤�Ϳ�
		int desH = 0;
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = sx;
		desH = (int) (sx * source.getHeight());
		if (desH < targetH) {
			desH = targetH;
			sy = (double) 61 / source.getHeight();
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					desH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, desH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * ʵ��ͼ��ĵȱ����ź����ź�Ľ�ȡ
	 * 
	 * @param inFilePath
	 *            Ҫ��ȡ�ļ���·��
	 * @param outFilePath
	 *            ��ȡ�������·��
	 * @param width
	 *            Ҫ��ȡ���
	 * @param hight
	 *            Ҫ��ȡ�ĸ߶�
	 * @param proportion
	 * @throws Exception
	 */

	public void saveImageAsJpg(File inFile, File saveFile, int width,
			int hight, boolean proportion) throws Exception {
		InputStream in = new FileInputStream(inFile);

		BufferedImage srcImage = ImageIO.read(in);
		if (width > 0 || hight > 0) {
			// ԭͼ�Ĵ�С
			int sw = srcImage.getWidth();
			int sh = srcImage.getHeight();
			// ���ԭͼ��Ĵ�СС��Ҫ���ŵ�ͼ���С��ֱ�ӽ�Ҫ���ŵ�ͼ���ƹ�ȥ
			if (sw > width && sh > hight) {
				srcImage = resize(srcImage, width, hight);
			} else {
				String fileName = saveFile.getName();
				String formatName = fileName.substring(fileName
						.lastIndexOf('.') + 1);
				ImageIO.write(srcImage, formatName, saveFile);
				return;
			}
		}
		// ���ź��ͼ��Ŀ�͸�
		// int w = srcImage.getWidth();
		// int h = srcImage.getHeight();
		// ����X������
		int x = 0;
		int y = 0;
		saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);

		in.close();
	}

	/**
	 * ʵ�����ź�Ľ�ͼ
	 * 
	 * @param image
	 *            ���ź��ͼ��
	 * @param subImageBounds
	 *            Ҫ��ȡ����ͼ�ķ�Χ
	 * @param subImageFile
	 *            Ҫ������ļ�
	 * @throws IOException
	 */
	private void saveSubImage(BufferedImage image,
			Rectangle subImageBounds, File subImageFile) throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0
				|| subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			System.out.println("Bad   subimage   bounds");
			return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x,
				subImageBounds.y, subImageBounds.width, subImageBounds.height);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(subImage, formatName, subImageFile);
	}

	// main����
	// compressPic(��ͼƬ·��,����СͼƬ·��,��ͼƬ�ļ���,����СͼƬ����,����СͼƬ���,����СͼƬ�߶�,�Ƿ�ȱ�����(Ĭ��Ϊtrue))
	public static void main(String[] arg) {
		ImageCompress mypic = new ImageCompress();
		System.out.println("�����ͼƬ��С��" + mypic.getPicSize("e:\\1.jpg") / 1024
				+ "KB");
		int count = 0; // ��¼ȫ��ͼƬѹ������ʱ��
		for (int i = 0; i < 1; i++) {
			int start = (int) System.currentTimeMillis(); // ��ʼʱ��
			mypic.compressPic("e:\\", "e:\\", "1.jpg", "r1" + i + ".jpg", 120,
					120, true);
			int end = (int) System.currentTimeMillis(); // ����ʱ��
			int re = end - start; // ��ͼƬ���ɴ���ʱ��
			count += re;
			System.out.println("��" + (i + 1) + "��ͼƬѹ������ʹ����: " + re + "����");
			System.out.println("�����ͼƬ��С��"
					+ mypic.getPicSize("e:\\test\\r1" + i + ".jpg") / 1024
					+ "KB");
		}
		System.out.println("�ܹ����ˣ�" + count + "����");
		
		try {
			mypic.resize(new File("e:\\1.jpg"), new File("e:\\1-1.jpg"), 200, 250, 0.3f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
