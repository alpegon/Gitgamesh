package com.biit.gitgamesh.core.git.ssh.test;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.biit.gitgamesh.core.git.ssh.SshCommandExecutor;
import com.jcraft.jsch.JSchException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@Test(groups = { "sshCommandExecutor" })
public class SshCommandExecutorTest extends AbstractTestNGSpringContextTests {

	private final static String SSH_USER = "gitgamesh";
	private final static String SSH_KEY_FILE = "gitgamesh";
	private final static String SSH_URL = "gitgamesh.biit-solutions.com";
	private final static String GIT_COMMAND = "git --version";
	private final static String DIRECTORY = "./src/test/resources/git";
	private final static String OUTPUT_FILE = "git.out";
	private final static int SSH_PORT = 22;

	@Test
	public void checkSshConnection() throws JSchException {
		SshCommandExecutor commandExecutor = new SshCommandExecutor(SSH_USER, SSH_KEY_FILE, SSH_URL, SSH_PORT);
		commandExecutor.connect();
		commandExecutor.disconnect();
	}

	@Test
	public void runBasicCommand() throws JSchException {
		SshCommandExecutor commandExecutor = new SshCommandExecutor(SSH_USER, SSH_KEY_FILE, SSH_URL, SSH_PORT);
		commandExecutor.connect();
		commandExecutor.setCommandOutputEnabled(true);
		commandExecutor.runCommand(GIT_COMMAND, DIRECTORY, OUTPUT_FILE);
		commandExecutor.disconnect();
	}

	@Test
	public void runCommands() throws JSchException {
		SshCommandExecutor commandExecutor = new SshCommandExecutor(SSH_USER, SSH_KEY_FILE, SSH_URL, SSH_PORT);
		commandExecutor.connect();
		commandExecutor.setCommandOutputEnabled(true);
		List<String> commands = Arrays.asList("cd git", "mkdir testRepo", "cd testRepo/", "git init", "git status -s");
		commandExecutor.runCommands(commands, DIRECTORY, OUTPUT_FILE);
		commandExecutor.disconnect();
	}
}
