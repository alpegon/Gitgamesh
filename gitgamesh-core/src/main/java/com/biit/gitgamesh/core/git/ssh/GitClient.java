package com.biit.gitgamesh.core.git.ssh;

import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSchException;

/**
 * This class connects to Git using SSH.<br>
 * Provides methods to simplify Git management.
 */

public class GitClient {

	private final static String SSH_USER = "gitgamesh";
	private final static String SSH_KEY_FILE = "gitgamesh";
	private final static String SSH_URL = "gitgamesh.biit-solutions.com";
	private final static String DIRECTORY = "./src/main/resources/git";
	private final static String OUTPUT_FILE = "git.out";
	private final static int SSH_PORT = 22;

	private List<String> getGitFolder() {
		List<String> commands = new ArrayList<>();
		commands.add("cd git/");
		return commands;
	}

	public void createNewRepository(String userName) throws JSchException {
		SshCommandExecutor commandExecutor = new SshCommandExecutor(SSH_USER, SSH_KEY_FILE, SSH_URL, SSH_PORT);
		commandExecutor.connect();

		List<String> commands = getGitFolder();
		// Creates the main git repo folder for the user
		commands.add("mkdir " + userName);
		// Initilizes the git repo
		commands.add("cd " + userName + "/");
		commands.add("git init");
		
		// Enables output
		commandExecutor.setCommandOutputEnabled(true);
		commandExecutor.runCommands(commands, DIRECTORY, OUTPUT_FILE);
		commandExecutor.disconnect();
	}
}