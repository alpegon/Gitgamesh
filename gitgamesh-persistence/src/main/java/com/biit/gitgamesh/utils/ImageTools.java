package com.biit.gitgamesh.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ImageTools {

	/**
	 * Loads an image from a specific path
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage loadFromFile(String path) throws IOException {
		return ImageIO.read(new File(path));
	}

	public static byte[] loadImageFromAbsolutePath(String pathToImage) throws IOException {
		Path path = Paths.get(pathToImage);
		byte[] data = Files.readAllBytes(path);
		return data;
	}

	public static byte[] loadImageFromResource(String resourceName) throws IOException {
		URL url = FileReader.class.getClassLoader().getResource(resourceName);
		Path path = Paths.get(FileReader.convert2OsPath(url));
		byte[] data = Files.readAllBytes(path);
		return data;
	}

	/**
	 * Converts a bufferedImage to byte array.
	 * 
	 * @param image
	 *            the image to convert.
	 * @param format
	 *            the final image format. Can be "jpg", "png", "gif", "bmp", "wbmp".
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(BufferedImage image, String format) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, format, baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	/**
	 * Convert a byte array in a BufferedImage.
	 * 
	 * @param imageInBytes
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getImage(byte[] imageInBytes) throws IOException {
		InputStream in = new ByteArrayInputStream(imageInBytes);
		BufferedImage bufferedImage = ImageIO.read(in);
		return bufferedImage;
	}

	/**
	 * Saves a buffered image in a file.
	 * 
	 * @param image
	 *            the image to save.
	 * @param format
	 *            the final image format. Can be "jpg", "png", "gif", "bmp", "wbmp".
	 * @param path
	 *            the path where the image will be saved.
	 * @throws IOException
	 */
	public static void saveInFile(BufferedImage image, String format, String path) throws IOException {
		ImageIO.write(image, "jpg", new File(path));
	}

	/**
	 * Resizes an image to the specified width and height. The relation between high and width will be preserved.
	 * 
	 * @param originalImage
	 * @param scaledWidth
	 *            width in pixels.
	 * @param scaledHeigh
	 *            height in pixels.
	 * @return
	 */
	public static BufferedImage resizeImage(Image originalImage, int scaledWidth, int scaledHeigh) {
		return resizeImage(toBufferedImage(originalImage), scaledWidth, scaledHeigh, true);
	}

	/**
	 * Resizes an image to the specified width and height. The relation between high and width will be preserved.
	 * 
	 * @param originalImage
	 * @param scaledWidth
	 *            width in pixels.
	 * @param scaledHeigh
	 *            height in pixels.
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int scaledWidth, int scaledHeigh) {
		return resizeImage(originalImage, scaledWidth, scaledHeigh, true);
	}

	/**
	 * Resizes an image to the specified width and height. The relation between high and width will be preserved.
	 * 
	 * @param originalImage
	 * @param scaledWidth
	 *            width in pixels.
	 * @param scaledHeigh
	 *            height in pixels.
	 * @param preserveAlpha
	 *            preserve alpha channel.
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int scaledWidth, int scaledHeigh,
			boolean preserveAlpha) {
		BufferedImage resizedImage;
		int finalHeigh, finalWidth;
		if (((double) originalImage.getHeight()) / scaledHeigh < ((double) originalImage.getWidth()) / scaledWidth) {
			finalWidth = scaledWidth;
			finalHeigh = (int) (originalImage.getHeight() * scaledWidth / (double) originalImage.getWidth());
		} else {
			finalWidth = (int) (originalImage.getWidth() * scaledHeigh / (double) originalImage.getHeight());
			finalHeigh = scaledHeigh;
		}
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
		resizedImage = new BufferedImage(finalWidth, finalHeigh, imageType);
		Graphics2D graphic = resizedImage.createGraphics();
		graphic.setComposite(AlphaComposite.Src);
		graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphic.drawImage(originalImage, 0, 0, finalWidth, finalHeigh, null);
		graphic.dispose();

		return resizedImage;
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param image
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// Create a buffered image with transparency
		BufferedImage bufferedimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D graphic = bufferedimage.createGraphics();
		graphic.drawImage(image, 0, 0, null);
		graphic.dispose();

		// Return the buffered image
		return bufferedimage;
	}

	/**
	 * Return the extension of a file.
	 * 
	 * @param path
	 * @return
	 */
	public static String getExtension(String path) {
		String extension = "";

		int i = path.lastIndexOf('.');
		int p = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

		if (i > p) {
			extension = path.substring(i + 1);
		}
		return extension;
	}

}
