package com.biit.gitgamesh.core.git.ssh;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.configuration.GitgameshConfigurationReader;
import com.jcraft.jsch.JSchException;

/**
 * This class connects to Git using SSH.<br>
 * Provides methods to simplify Git management.
 */

public class GitClient {

	private final static String GIT_FOLDER = GitgameshConfigurationReader.getInstance().getGitFolder();
	private final static String GIT_USER = GitgameshConfigurationReader.getInstance().getGitUser();
	private final static String GIT_KEY_FILE = GitgameshConfigurationReader.getInstance().getGitKeyFile();
	private final static String GIT_URL = GitgameshConfigurationReader.getInstance().getGitUrl();
	private final static String GIT_OUTPUT_DIRECTORY = "./src/main/resources/git";
	private final static String GIT_OUTPUT_FILE = "git.out";
	private final static String STL_FILE_END = ".stl";
	private final static String USER_FILES_FOLDER = "files";
	private final static int SSH_PORT = 22;

	private List<String> getGitFolder() {
		List<String> commands = new ArrayList<>();
		commands.add("cd " + GIT_FOLDER);
		return commands;
	}

	private void executeCommands(List<String> commands) throws JSchException {
		SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER, GIT_KEY_FILE, GIT_URL, SSH_PORT);
		commandExecutor.connect();
		// Enables output
		commandExecutor.setCommandOutputEnabled(true);
		commandExecutor.runCommands(commands, GIT_OUTPUT_DIRECTORY, GIT_OUTPUT_FILE);
		commandExecutor.disconnect();
	}

	public void createNewRepository(String userName) throws JSchException {
		List<String> commands = getGitFolder();
		// Creates the main git repo folder for the user
		commands.add("mkdir " + userName);
		// Initilizes the git repo
		commands.add("cd " + userName + File.separator);
		commands.add("git init");
		executeCommands(commands);
	}

	public void commitNewFiles(String userName, String repositoryName) throws JSchException {
		List<String> commands = getGitFolder();
		commands.add("cd " + userName + File.separator + repositoryName);
		// Clone the repository in the user folder
		commands.add("git add .");
		commands.add("git commit -m \"New files commited\"");
		executeCommands(commands);
	}

	/**
	 * Must receive the complete repository path to clone
	 * 
	 * @param userName
	 * @param repositoryPath
	 * @throws JSchException
	 */
	public void cloneRepository(String userName, String repositoryPath) throws JSchException {
		List<String> commands = getGitFolder();
		commands.add("cd " + userName + File.separator);
		// Clone the repository in the user folder
		commands.add("git clone " + repositoryPath);
		executeCommands(commands);
	}

	/**
	 * We are going to look for an image that has the same name than the file
	 * name passed in the parameter.<br>
	 * This method don't use shell commands, but it's kept here so we have all
	 * the git related methods in the same class.
	 * 
	 * @param userName
	 * @param repositoryName
	 * @param fileName
	 * @throws IOException
	 */
	public BufferedImage getRepositoryImage(String userName, String repositoryName, String fileName) {
		// Remove the .stl if exists
		if (fileName.endsWith(STL_FILE_END)) {
			fileName = fileName.substring(0, fileName.length() - 4);
		}
		String imageName = fileName + ".jpg";
		// Build the complete path used for looking for the image
		String imagePath = GIT_FOLDER + userName + File.separator + repositoryName + File.separator + USER_FILES_FOLDER
				+ File.separator + imageName;
		try {
			URL url = new File(imagePath).toURI().toURL();
			return ImageIO.read(url);
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
			return null;
		}
	}
}