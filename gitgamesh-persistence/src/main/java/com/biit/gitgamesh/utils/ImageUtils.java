package com.biit.gitgamesh.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtils {

	public static byte[] getImageFromAbsolutePath(String pathToImage) throws IOException {
		Path path = Paths.get(pathToImage);
		byte[] data = Files.readAllBytes(path);
		return data;
	}

	public static byte[] getImageFromResource(String resourceName) throws IOException {
		URL url = FileReader.class.getClassLoader().getResource(resourceName);
		Path path = Paths.get(FileReader.convert2OsPath(url));
		byte[] data = Files.readAllBytes(path);
		return data;
	}

}
