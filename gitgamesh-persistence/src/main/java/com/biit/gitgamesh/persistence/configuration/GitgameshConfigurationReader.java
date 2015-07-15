package com.biit.gitgamesh.persistence.configuration;

import net.sf.ehcache.util.FindBugsSuppressWarnings;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.configuration.exception.PropertyNotFoundException;

public class GitgameshConfigurationReader extends ConfigurationReader {

	private static final String CONFIG_FILE = "settings.conf";
	
	// Tags
	private static final String GIT_URL = "git.url";
	private static final String GIT_USER = "git.user";
	private static final String GIT_KEY_FILE = "git.key.file";
	private static final String GIT_FOLDER = "git.folder";
	private static final String GIT_TEST_FOLDER = "git.test.folder";
	
	private static GitgameshConfigurationReader instance;

	private GitgameshConfigurationReader() {
		super();

		addProperty(GIT_URL, "");
		addProperty(GIT_USER, "");
		addProperty(GIT_KEY_FILE, "");
		addProperty(GIT_FOLDER, "");
		addProperty(GIT_TEST_FOLDER, "");

		addPropertiesSource(new PropertiesSourceFile(CONFIG_FILE));

		readConfigurations();
	}

	@FindBugsSuppressWarnings(value = "DC_DOUBLECHECK")
	public static GitgameshConfigurationReader getInstance() {
		if (instance == null) {
			synchronized (GitgameshConfigurationReader.class) {
				if (instance == null) {
					instance = new GitgameshConfigurationReader();
				}
			}
		}
		return instance;
	}

	private String getPropertyLogException(String propertyId) {
		try {
			return getProperty(propertyId);
		} catch (PropertyNotFoundException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
			return null;
		}
	}

	public String getGitUrl() {
		return getPropertyLogException(GIT_URL);
	}

	public String getGitUser() {
		return getPropertyLogException(GIT_USER);
	}

	public String getGitKeyFile() {
		return getPropertyLogException(GIT_KEY_FILE);
	}

	public String getGitFolder() {
		return getPropertyLogException(GIT_FOLDER);
	}
	
	public String getGitTestFolder() {
		return getPropertyLogException(GIT_TEST_FOLDER);
	}
}
