package com.biit.gitgamesh.persistence.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.utils.PropertiesFile;

public class PropertiesSourceFile implements IPropertiesSource {

	private String filePath;
	private final String fileName;

	public PropertiesSourceFile(String fileName) {
		this.filePath = null;
		this.fileName = fileName;
	}

	public PropertiesSourceFile(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;
	}

	@Override
	public Properties loadFile() {
		try {
			if (filePath == null) {
				return PropertiesFile.load(fileName);
			} else {
				return PropertiesFile.load(filePath, fileName);
			}
		} catch (FileNotFoundException e) {
			GitgameshLogger.warning(this.getClass().getName(), e.getMessage());
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass(), e);
		} catch (NullPointerException e) {
			GitgameshLogger.info(this.getClass().getName(), e.getMessage());
		}
		return null;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String toString() {
		return (getFilePath() != null ? getFilePath() + "/" : "") + getFileName();
	}
}
