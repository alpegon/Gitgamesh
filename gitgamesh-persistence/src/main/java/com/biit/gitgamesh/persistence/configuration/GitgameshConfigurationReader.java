package com.biit.gitgamesh.persistence.configuration;

import net.sf.ehcache.util.FindBugsSuppressWarnings;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.configuration.exception.PropertyNotFoundException;

public class GitgameshConfigurationReader extends ConfigurationReader {

	private static final String CONFIG_FILE = "settings.conf";
	
	// Tags
	private static final String GIT_BLIT_URL = "git.blit.url";
	private static final String GIT_BLIT_ADMIN_USER = "git.blit.admin.user";
	private static final String GIT_BLIT_ADMIN_PASS = "git.blit.admin.password";
	
	private static GitgameshConfigurationReader instance;

	private GitgameshConfigurationReader() {
		super();

		addProperty(GIT_BLIT_URL, "");
		addProperty(GIT_BLIT_ADMIN_USER, "");
		addProperty(GIT_BLIT_ADMIN_PASS, "");

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

	public String getGitBlitUrl() {
		return getPropertyLogException(GIT_BLIT_URL);
	}

	public String getGitBlitAdminUser() {
		return getPropertyLogException(GIT_BLIT_ADMIN_USER);
	}

	public String getGitBlitAdminPass() {
		return getPropertyLogException(GIT_BLIT_ADMIN_PASS);
	}

}
