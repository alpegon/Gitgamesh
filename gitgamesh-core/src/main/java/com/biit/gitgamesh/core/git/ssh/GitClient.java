package com.biit.gitgamesh.core.git.ssh;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.configuration.GitgameshConfigurationReader;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
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
	private final static String USER_FILES_FOLDER = "files";
	private final static int SSH_PORT = 22;

	private static List<String> setGitFolder() {
		List<String> commands = new ArrayList<>();
		commands.add("cd " + GIT_FOLDER);
		return commands;
	}

	private static String getFilesFolderPath(String userName, String repositoryName) {
		return userName + "/" + repositoryName + "/" + USER_FILES_FOLDER + "/";
	}

	private static String executeCommands(List<String> commands) throws JSchException {
		SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER, GIT_KEY_FILE, GIT_URL, SSH_PORT);
		commandExecutor.connect();
		// Enables output
		commandExecutor.setCommandOutputEnabled(true);
		String commandOuput = commandExecutor.runCommands(commands);
		commandExecutor.disconnect();
		return commandOuput;
	}

	public static void createNewRepository(PrinterProject project) throws JSchException {
		String userName = project.getCreatedBy();
		List<String> commands = setGitFolder();
		// Creates the main git repo folder for the user
		commands.add("mkdir " + userName);
		// Initilizes the git repo
		commands.add("cd " + userName + "/");
		commands.add("git init");
		executeCommands(commands);
	}

	public static void commitNewFiles(PrinterProject project) throws JSchException {
		String userName = project.getCreatedBy();
		String repositoryName = project.getName();
		List<String> commands = setGitFolder();
		commands.add("cd " + getFilesFolderPath(userName, repositoryName));
		// Clone the repository in the user folder
		commands.add("git add . --all");
		commands.add("git commit -m \"New files commited\"");
		executeCommands(commands);
	}

	/**
	 * Clones the passed project to the user's repository
	 * 
	 * @param userName
	 * @param project
	 * @throws JSchException
	 */
	public static void cloneRepository(String userName, PrinterProject project) throws JSchException {
		String repositoryPath = GIT_FOLDER + project.getCreatedBy() + "/" + project.getName();
		List<String> commands = setGitFolder();
		commands.add("cd " + userName + "/");
		// Clone the repository in the user folder
		commands.add("git clone " + repositoryPath);
		executeCommands(commands);
	}

	// /**
	// * We are going to look for an image that has the same name than the file
	// * name passed in the parameter.<br>
	// * This method don't use shell commands, but it's kept here so we have all
	// * the git related methods in the same class.
	// *
	// * @param userName
	// * @param repositoryName
	// * @param fileName
	// * @throws IOException
	// */
	// public BufferedImage getRepositoryImage(String userName, String
	// repositoryName, String fileName) {
	// // Remove the .stl if exists
	// if (fileName.endsWith(STL_FILE_END)) {
	// fileName = fileName.substring(0, fileName.length() - 4);
	// }
	// String imageName = fileName + ".jpg";
	// // Build the complete path used for looking for the image
	// String imagePath = GIT_FOLDER + getFilesFolderPath(userName,
	// repositoryName) + imageName;
	// try {
	// URL url = new File(imagePath).toURI().toURL();
	// return ImageIO.read(url);
	// } catch (IOException e) {
	// GitgameshLogger.errorMessage(this.getClass().getName(), e);
	// return null;
	// }
	// }

	/**
	 * Returns a String containing all the commit information for the
	 * repository.<br>
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
	public static String getRepositoryCommits(PrinterProject project) throws JSchException {
		String userName = project.getCreatedBy();
		String repositoryName = project.getName();
		List<String> commands = setGitFolder();
		commands.add("cd " + getFilesFolderPath(userName, repositoryName));
		commands.add("git log --pretty=format:\"%cd || %H\"");
		return executeCommands(commands);
	}

	// /**
	// * Return a file from the repository based on a commit id.<br>
	// * This method allows to retrieve files from older commits.
	// *
	// * @param userName
	// * @param repositoryName
	// * @param commitId
	// * @return
	// * @throws JSchException
	// */
	// public static byte[] getRepositoryFile(ProjectFile file, String commitId)
	// throws JSchException {
	// String fileName = file.getFileName();
	// String userName = file.getPrinterProject().getCreatedBy();
	// String repositoryName = file.getPrinterProject().getName();
	//
	// Path outFilePath = FileSystems.getDefault().getPath("/tmp/", commitId +
	// file.getFileName());
	//
	// List<String> commands = setGitFolder();
	// commands.add("cd " + getFilesFolderPath(userName, repositoryName));
	// commands.add("git show " + commitId + ":" + fileName + " > " +
	// outFilePath.toString());
	//
	// try {
	// return Files.readAllBytes(outFilePath);
	// } catch (IOException e) {
	// GitgameshLogger.errorMessage(GitClient.class.getName(), e);
	// return null;
	// }
	// }

	/**
	 * Returns a list of the repository files.<br>
	 * Only returns name and creation time, not the file itself.
	 * 
	 * @param userName
	 * @param repositoryName
	 * @return
	 * @throws JSchException
	 */
	public static List<ProjectFile> getRepositoryFilesInformation(PrinterProject project) throws JSchException {
		String userName = project.getCreatedBy();
		String repositoryName = project.getName();

		List<String> commands = setGitFolder();
		commands.add("cd " + getFilesFolderPath(userName, repositoryName));
		commands.add("ls -l --time-style=\"+%d-%m-%y %H:%M:%S\" | awk '/^-/ {printf \"%s::%s %s\\n\",$NF,$6,$7}'");
		String commandOutput = executeCommands(commands);
		String[] outputs = commandOutput.split("\n");
		List<ProjectFile> projectFiles = new ArrayList<>();
		for (String output : outputs) {
			String[] parsedOutput = output.split("::");
			ProjectFile projectFile = new ProjectFile();
			projectFile.setFileName(parsedOutput[0]);
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
				Date dateCreated = format.parse(parsedOutput[1]);
				Timestamp time = new Timestamp(dateCreated.getTime());
				projectFile.setCreationTime(time);
				projectFile.setUpdateTime(time);
				projectFiles.add(projectFile);
			} catch (ParseException e) {
				GitgameshLogger.errorMessage(GitClient.class.getName(), e);
			}
		}
		return projectFiles;
	}

	/**
	 * This method allows to retrieve files from the repository.<br>
	 * Returns the file passed with the stl file information inside.
	 * 
	 * @param file
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
	public static void getRepositoryFile(ProjectFile file) throws JSchException, IOException {
		String fileName = file.getFileName();
		String userName = file.getPrinterProject().getCreatedBy();
		String repositoryName = file.getPrinterProject().getName();

		SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER, GIT_KEY_FILE, GIT_URL, SSH_PORT);
		commandExecutor.connect();
		file.setFile(commandExecutor
				.getRemoteFile(GIT_FOLDER + getFilesFolderPath(userName, repositoryName) + fileName));
	}

	/**
	 * This method allows to retrieve files from the repository.<br>
	 * 
	 * @param file
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
	public static byte[] setRepositoryFile(ProjectFile file) throws JSchException, IOException {
		String fileName = "test" + file.getFileName();
		String userName = file.getPrinterProject().getCreatedBy();
		String repositoryName = file.getPrinterProject().getName();

		SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER, GIT_KEY_FILE, GIT_URL, SSH_PORT);
		commandExecutor.connect();
		return commandExecutor.getRemoteFile(GIT_FOLDER + getFilesFolderPath(userName, repositoryName) + fileName);
	}
}