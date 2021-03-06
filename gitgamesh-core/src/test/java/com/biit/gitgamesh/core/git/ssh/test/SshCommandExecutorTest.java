package com.biit.gitgamesh.core.git.ssh.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@Test(groups = { "sshCommandExecutor" })
public class SshCommandExecutorTest extends AbstractTestNGSpringContextTests {

	// private final static String GIT_USER =
	// GitgameshConfigurationReader.getInstance().getGitUser();
	// private final static String GIT_KEY_FILE =
	// GitgameshConfigurationReader.getInstance().getGitKeyFile();
	// private final static String GIT_URL =
	// GitgameshConfigurationReader.getInstance().getGitUrl();
	// private final static int SSH_PORT = 22;
	//
	// private String createGitTestFolderCommand() {
	// return "mkdir -p " +
	// GitgameshConfigurationReader.getInstance().getGitTestFolder();
	// }
	//
	// private String getGitTestFolderCommand() {
	// return "cd " +
	// GitgameshConfigurationReader.getInstance().getGitTestFolder();
	// }
	//
	// @Test
	// public void checkSshConnection() throws JSchException {
	// SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER,
	// GIT_KEY_FILE, GIT_URL, SSH_PORT);
	// commandExecutor.connect();
	// commandExecutor.disconnect();
	// }
	//
	// @Test
	// public void runBasicCommand() throws JSchException {
	// SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER,
	// GIT_KEY_FILE, GIT_URL, SSH_PORT);
	// commandExecutor.connect();
	// commandExecutor.setCommandOutputEnabled(true);
	// commandExecutor.runCommand("git --version");
	// commandExecutor.disconnect();
	// }
	//
	// @Test
	// public void runCommands() throws JSchException {
	// SshCommandExecutor commandExecutor = new SshCommandExecutor(GIT_USER,
	// GIT_KEY_FILE, GIT_URL, SSH_PORT);
	// commandExecutor.connect();
	// commandExecutor.setCommandOutputEnabled(true);
	// List<String> commands = Arrays.asList(createGitTestFolderCommand(),
	// getGitTestFolderCommand(),
	// "mkdir -p testRepo", "cd testRepo/", "git init", "git status -s");
	// commandExecutor.runCommands(commands);
	// commandExecutor.disconnect();
	// }
//
//	@Test
//	public void testGitClient() throws JSchException {
//		PrinterProject project = new PrinterProject();
//		project.setCreatedBy("tpalmer");
//		project.setName("P100_Cube");
//		List<ProjectFile> files = GitClient.getRepositoryFilesInformation(project);
//		for (ProjectFile file : files) {
//			System.out.println(file.getFileName());
//			System.out.println(file.getCreationTime());
//		}
//	}
//	
//	@Test
//	public void testGitClient() throws JSchException, IOException {
//		PrinterProject project = new PrinterProject();
//		project.setCreatedBy("brickify");
//		project.setName("polytopes");
//		
//		ProjectFile projectFile = new ProjectFile();
//		projectFile.setFileName("cube.ascii.stl");
//		projectFile.setPrinterProject(project);
//		GitClient.getRepositoryFile(projectFile);
//		
//	}
//	
//	@Test
//	public void testGitClient2() throws JSchException, IOException {
//		PrinterProject project = new PrinterProject();
//		project.setCreatedBy("brickify");
//		project.setName("broken");
//		
//		File testFile = FileReader.getResource("test.stl");
//		GitClient.uploadRepositoryFile(project, "test.stl", testFile);
//		GitClient.commitNewFiles(project);
//	}
}
