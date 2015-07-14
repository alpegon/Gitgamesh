package com.biit.gitgamesh.persistence.configuration;

import java.util.Properties;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.utils.PropertiesFile;

public class SystemVariablePropertiesSourceFile extends PropertiesSourceFile {

	private final String environmentVariable;

	public SystemVariablePropertiesSourceFile(String environmentVariable, String fileName) {
		super(fileName);
		this.environmentVariable = environmentVariable;
	}

	public String getEnvironmentVariable() {
		return environmentVariable;
	}

	@Override
	public Properties loadFile() {
		String environmentVariableValue = PropertiesFile.readEnvironmentVariable(getEnvironmentVariable());
		if (environmentVariableValue != null) {
			GitgameshLogger.debug(this.getClass().getName(), "Environmental variable '" + getEnvironmentVariable()
					+ "' values is: '" + environmentVariableValue + "'.");
			setFilePath(environmentVariableValue);
			return super.loadFile();
		} else {
			GitgameshLogger.warning(this.getClass().getName(), "Environmental variable '" + getEnvironmentVariable()
					+ "' is not set on the system.");
			return null;
		}
	}

}
