package com.biit.gitgamesh.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;


public class PropertiesFile {

	public PropertiesFile() {
	}
	
	public static String readEnvironmentVariable(String environmentVariable){
		Map<String, String> env = System.getenv();
		return env.get(environmentVariable);
	}
	
	public static Properties load(String path, String fileName) throws IOException{		
		File file = new File(path,fileName);
		return load(file);
	}

	public static Properties load(String fileName) throws IOException {
		File file = FileReader.getResource(fileName);
		return load(file);
	}

	public static Properties load(File file) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("File is not set.");
		} else {
			return load((InputStream) (new FileInputStream(file)));
		}
	}

	public static Properties load(URL url) throws IOException {
		if (url == null) {
			throw new IllegalArgumentException("Url is not set.");
		} else {
			URLConnection connection = url.openConnection();
			return load(connection.getInputStream());
		}
	}

	public static Properties load(InputStream inputStream) throws IOException {
		Properties properties = new Properties();
		if (inputStream == null) {
			throw new IllegalArgumentException("InputStream is not set.");
		}
		try {
			properties.load(new BufferedInputStream(inputStream));
		} finally {
			inputStream.close();
		}

		return properties;
	}

	public static void store(Properties properties, String fileName) throws IOException {
		if (fileName != null) {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			store(properties, file);
		}
	}

	public static void store(Properties properties, File file) throws IOException {
		store(properties, ((OutputStream) (new FileOutputStream(file))));
	}

	public static void store(Properties properties, URL url) throws IOException {
		if (url == null) {
			throw new IllegalArgumentException("Url is not set.");
		} else {
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			store(properties, connection.getOutputStream());
		}
	}

	public static void store(Properties properties, OutputStream outputStream) throws IOException {
		try {
			if (properties == null) {
				throw new IllegalArgumentException("Properties is not set.");
			}
			if (outputStream == null) {
				throw new IllegalArgumentException("OutputStream is not set.");
			}
			properties.store(new BufferedOutputStream(outputStream), null);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

}
