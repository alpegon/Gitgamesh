package com.biit.gitgamesh.gui.webpages.project;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.entity.ProjectImage;
import com.biit.gitgamesh.utils.ImageTools;
import com.vaadin.server.StreamResource.StreamSource;

public class DatabaseImageResource implements StreamSource {
	private static final long serialVersionUID = 4055558717900112056L;
	private BufferedImage projectImage;

	public DatabaseImageResource(ProjectImage projectImage) {
		try {
			this.projectImage = ImageTools.getImage(projectImage.getImage());
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
	}

	public DatabaseImageResource(ProjectImage projectImage, int width, int high) {
		try {
			this.projectImage = ImageTools.resizeImage(ImageTools.getImage(projectImage.getImage()), width, high);
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
	}

	public DatabaseImageResource(String resource) {
		try {
			this.projectImage = ImageTools.getImage(ImageTools.loadImageFromResource(resource));
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
	}

	@Override
	public InputStream getStream() {
		/* Create an image and draw something on it. */
		try {
			ByteArrayOutputStream imagebuffer = null;
			/* Write the image to a buffer. */
			imagebuffer = new ByteArrayOutputStream();
			ImageIO.write(projectImage, "png", imagebuffer);

			/* Return a stream from the buffer. */
			return new ByteArrayInputStream(imagebuffer.toByteArray());
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
		return null;
	}

}
