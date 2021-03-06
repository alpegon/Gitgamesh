package com.biit.gitgamesh.core.git.ssh;

import java.io.File;
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
		commandExecutor.connectSession();
		// Enables output
		commandExecutor.setCommandOutputEnabled(true);
		String commandOuput = commandExecutor.runCommands(commands);
		commandExecutor.disconnect();
		return commandOuput;
	}

	public static void createNewRepository(PrinterProject project) throws JSchException {
		if (project != null) {
			String userName = project.getCreatedBy();
			String repositoryName = project.getName();

			List<String> commands = setGitFolder();
			// Creates user folder
			commands.add("mkdir -p " + userName);
			commands.add("cd " + userName + "/");
			// Creates the git repo for the user
			commands.add("mkdir -p " + repositoryName);
			commands.add("cd " + repositoryName + "/");
			// Creates the folder where the files are going to be
			commands.add("mkdir -p files");
			// Initilizes the empty git repo
			commands.add("git init");
			executeCommands(commands);
		}
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
		commands.add("mkdir -p " + userName);
		commands.add("cd " + userName + "/");
		// Clone the repository in the user folder
		commands.add("git clone " + repositoryPath);
		executeCommands(commands);
	}

	/**
	 * Returns a String containing all the commit information for the repository.<br>
	 * 
	 * The format of the output string (for each commit) is: "%cd || %H" <br>
	 * To know more about the git output formats -> http://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History
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
		List<ProjectFile> projectFiles = new ArrayList<>();

		try {
			List<String> commands = setGitFolder();
			commands.add("cd " + getFilesFolderPath(userName, repositoryName));
			commands.add("ls -l --time-style=\"+%d-%m-%y %H:%M:%S\" | awk '/^-/ {printf \"%s::%s %s\\n\",$NF,$6,$7}'");
			String commandOutput = executeCommands(commands);
			if (commandOutput != null && commandOutput.length() > 1) {
				String[] outputs = commandOutput.split("\n");
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
						projectFile.setPrinterProject(project);
						projectFiles.add(projectFile);
					} catch (ParseException e) {
						GitgameshLogger.errorMessage(GitClient.class.getName(), e);
					}
				}
			}
		} catch (NullPointerException e) {
			GitgameshLogger.errorMessage(GitClient.class.getName(), e);
		}
		return projectFiles;
	}

	/**
	 * This method allows to retrieve files from the repository.<br>
	 * Returns the file passed with the stl file information inside.
	 * 
	 * @param file
	 * @return
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
	public static byte[] getRepositoryFile(ProjectFile file) throws JSchException, IOException {
		String fileName = file.getFileName();
		String userName = file.getPrinterProject().getCreatedBy();
		String repositoryName = file.getPrinterProject().getName();

		SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER, GIT_KEY_FILE, GIT_URL, SSH_PORT);
		commandExecutor.connectSession();
		byte[] stlFile = commandExecutor.getRemoteFile(GIT_FOLDER + getFilesFolderPath(userName, repositoryName)
				+ fileName);
		GitgameshLogger.debug(GitClient.class.getName(), "File '"+fileName+"' downloaded correctly.");
		commandExecutor.disconnect();
		return stlFile;
	}

	/**
	 * This method allows to put the file in the repository.<br>
	 * 
	 * @param file
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
	public static void uploadRepositoryFile(PrinterProject project, String fileName, File file) throws JSchException,
			IOException {
				String userName = project.getCreatedBy();
		String repositoryName = project.getName();
		SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER, GIT_KEY_FILE, GIT_URL, SSH_PORT);
		commandExecutor.connectSession();
		String filePath = GIT_FOLDER + getFilesFolderPath(userName, repositoryName) + fileName;
		commandExecutor.setRemoteFile(filePath, file, fileName);
		commandExecutor.disconnect();
		// Once the file is uploaded, we have to commit it to the repository to
		// keep the track of the changes
		commitNewFiles(project);
	}

}