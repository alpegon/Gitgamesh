package com.biit.gitgamesh.persistence.configuration;

import java.util.Properties;

public interface IPropertiesSource {

	Properties loadFile();

	String getFilePath();

	String getFileName();

}
