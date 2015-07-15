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
	private final static String STL_FILE_END = ".stl";
	private final static String USER_FILES_FOLDER = "files";
	private final static int SSH_PORT = 22;

	private List<String> setGitFolder() {
		List<String> commands = new ArrayList<>();
		commands.add("cd " + GIT_FOLDER);
		return commands;
	}

	private String getFilesFolderPath(String userName, String repositoryName) {
		return userName + "/" + repositoryName + "/" + USER_FILES_FOLDER + "/";
	}

	private String executeCommands(List<String> commands) throws JSchException {
		SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER, GIT_KEY_FILE, GIT_URL, SSH_PORT);
		commandExecutor.connect();
		// Enables output
		commandExecutor.setCommandOutputEnabled(true);
		String commandOuput = commandExecutor.runCommands(commands);
		commandExecutor.disconnect();
		return commandOuput;
	}

	public void createNewRepository(String userName) throws JSchException {
		List<String> commands = setGitFolder();
		// Creates the main git repo folder for the user
		commands.add("mkdir " + userName);
		// Initilizes the git repo
		commands.add("cd " + userName + File.separator);
		commands.add("git init");
		executeCommands(commands);
	}

	public void commitNewFiles(String userName, String repositoryName) throws JSchException {
		List<String> commands = setGitFolder();
		commands.add("cd " + getFilesFolderPath(userName, repositoryName));
		// Clone the repository in the user folder
		commands.add("git add . --all");
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
		List<String> commands = setGitFolder();
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
		String imagePath = GIT_FOLDER + getFilesFolderPath(userName, repositoryName) + imageName;
		try {
			URL url = new File(imagePath).toURI().toURL();
			return ImageIO.read(url);
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
			return null;
		}
	}

	/**
	 * Returns a String containing all the commit information for the repository
	 * passed.<br>
	 * 
	 * The format of the output string (for each commit) is: "%cd || %H" <br>
	 * To know more about the git output formats ->
	 * http://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History
	 * 
	 * @param userName
	 * @param repositoryName
	 * @return
	 * @throws JSchException
	 */
	public String getRepositoryCommits(String userName, String repositoryName) throws JSchException {
		List<String> commands = setGitFolder();
		commands.add("cd " + getFilesFolderPath(userName, repositoryName));
		commands.add("git log --pretty=format:\"%cd || %H\"");
		return executeCommands(commands);
	}
}