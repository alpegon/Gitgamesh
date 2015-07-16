package com.biit.gitgamesh.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.biit.gitgamesh.utils.exceptions.InvalidImageExtensionException;

@Test(groups = "imageTests")
public class ImageToolsTest {
	private final static String IMAGE_RESOURCE = "ProjectPreview.png";

	@Test
	public void checkImages() throws IOException, InvalidImageExtensionException {
		byte[] imageInBytes = ImageTools.loadImageFromResource(IMAGE_RESOURCE);
		BufferedImage image = ImageTools.getImage(imageInBytes);
		BufferedImage newImage = ImageTools.resizeImage(image, 50, 50,
				ImageTools.preserveAlpha(ImageTools.getExtension(IMAGE_RESOURCE)));
		ImageTools.saveInFile(newImage, "png", System.getProperty("java.io.tmpdir") + "/testImage2.png");
		File finalImage = new File(System.getProperty("java.io.tmpdir") + "/testImage2.png");
		Assert.assertTrue(finalImage.exists());
	}

}
