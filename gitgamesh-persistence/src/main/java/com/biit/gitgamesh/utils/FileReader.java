package com.biit.gitgamesh.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

import com.biit.gitgamesh.logger.GitgameshLogger;

public class FileReader {
	private final static String ICON_FOLDER = "icons";

	public static File getResource(String filename) throws NullPointerException {
		URL url = FileReader.class.getClassLoader().getResource(filename);
		File file = null;
		// Jetty load resource.
		try {
			file = new File(FileReader.convert2OsPath(url));
			// Apache load resource
			if (!file.exists()) {
				try {
					file = new File(url.toURI());
				} catch (URISyntaxException e) {
					GitgameshLogger.errorMessage(FileReader.class, e);
				} catch (IllegalArgumentException e) {
					GitgameshLogger.errorMessage(FileReader.class, e);
				}
			}
		} catch (NullPointerException npe) {
			throw new NullPointerException("File '" + filename + "' does not exist.");
		}
		return file;
	}

	public static String convert2OsPath(URL string) {
		try {
			if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
				return (string.getPath()).substring(1);
			} else {
				return (string.getPath());
			}
		} catch (NullPointerException npe) {
			return null;
		}
	}

	public static ImageIcon getIcon(String iconFile) {
		return new ImageIcon(FileReader.class.getClassLoader().getResource(
				File.separator + ICON_FOLDER + File.separator + iconFile));
	}

	/**
	 * Reads a resource text file and returns a list of strings (one line per string).
	 * 
	 * @param resourceName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<String> getResouceAsList(String resourceName) throws FileNotFoundException {
		File file = FileReader.getResource(resourceName);
		return readFile(file);
	}

	/**
	 * Reads a resource text file and returns a list of strings (one line per string).
	 * 
	 * @param resourceName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<String> getFileAsList(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		return readFile(file);
	}

	private static List<String> readFile(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		List<String> lines = new ArrayList<String>();
		while (s.hasNext()) {
			lines.add(s.next());
		}
		s.close();
		return lines;
	}

	public static String getResource(String fileName, Charset charset) throws FileNotFoundException {
		StringBuilder result = new StringBuilder("");
		// Get file from resources folder
		try {
			File file = getResource(fileName);
			try {
				for (String line : Files.readAllLines(file.toPath(), charset)) {
					result.append(line).append("\n");
				}
			} catch (IOException e) {
				GitgameshLogger.errorMessage(FileReader.class, e);
			}
		} catch (NullPointerException npe) {
			throw new FileNotFoundException(npe.getMessage());
		}
		return result.toString();
	}

}
