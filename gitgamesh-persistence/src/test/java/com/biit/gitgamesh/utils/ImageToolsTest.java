package com.biit.gitgamesh.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.testng.annotations.Test;

@Test(groups = "imageTests")
public class ImageToolsTest {

	@Test
	public void checkImages() throws IOException {
		byte[] imageInBytes = ImageTools.loadImageFromResource("ProjectPreview.png");
		BufferedImage image = ImageTools.getImage(imageInBytes);
		BufferedImage newImage = ImageTools.resizeImage(image, 50, 50);
		ImageTools.saveInFile(newImage, "png", System.getProperty("java.io.tmpdir") + "/testImage2.png");
		File finalImage = new File(System.getProperty("java.io.tmpdir") + "/testImage2.png");
		Assert.assertTrue(finalImage.exists());
	}

}
