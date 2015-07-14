package com.biit.gitgamesh.core.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

@Test(groups = "jgitTest")
public class GitUserTasksTest {
	private final static String LOCAL_PATH = System.getProperty("java.io.tmpdir") + "testrepo.git";
	private final static String LOCAL_PATH_CLONED = System.getProperty("java.io.tmpdir") + "testrepo_cloned.git";

	private final static String USER = "gittest";
	private final static String PASSWORD = "asd123";
	// private final static String REMOTE_PATH = "http://" + USER + ":" + PASSWORD
	// + "@gitgamesh.biit-solutions.com:29418/test.git";
	private final static String REMOTE_PATH = "https://" + USER + ":" + PASSWORD
			+ "@gitgamesh.biit-solutions.com:8443/gitblit/r/test.git";

	private Repository localRepo;
	private Git git;

	@Before
	public void init() throws IOException, GitAPIException {
		localRepo = new FileRepository(LOCAL_PATH);
		git = new Git(localRepo);
	}

	@Test
	public void testCreate() throws IOException {
		File file = new File(LOCAL_PATH + ".git");
		file.mkdir();
		Assert.assertTrue(file.exists() && file.isDirectory());
		Repository newRepo = new FileRepository(LOCAL_PATH);
//		newRepo.create();
//		newRepo.close();
		git = new Git(newRepo);
	}

	@Test
	public void testClone() throws IOException, GitAPIException {
		Git newGit = Git.cloneRepository().setURI(REMOTE_PATH).setDirectory(new File(LOCAL_PATH)).call();
		Assert.assertNotNull(newGit);
	}

	// @Test(dependsOnMethods = { "testCreate" })
	// public void testAdd() throws IOException, GitAPIException {
	// File myfile = new File(LOCAL_PATH + File.separator + "myfile");
	// myfile.createNewFile();
	// git.add().addFilepattern("myfile").call();
	// }
	//
	// @Test(dependsOnMethods = { "testAdd" })
	// public void testCommit() throws IOException, GitAPIException, JGitInternalException {
	// git.commit().setMessage("Added myfile").call();
	// }
	//
	// @Test(dependsOnMethods = { "testCommit" })
	// public void testPush() throws IOException, JGitInternalException, GitAPIException {
	// git.push().call();
	// }
	//
	// @Test(dependsOnMethods = { "testPush" })
	// public void testTrackMaster() throws IOException, JGitInternalException, GitAPIException {
	// git.branchCreate().setName("master").setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
	// .setStartPoint("origin/master").setForce(true).call();
	// }
	//
	// @Test(dependsOnMethods = { "testTrackMaster" })
	// public void testPull() throws IOException, GitAPIException {
	// git.pull().call();
	// }
	//
	@AfterSuite(alwaysRun = true)
	public void cleanResources() {
		File folder = new File(LOCAL_PATH);
		deleteFolder(folder);
		File clonedFolder = new File(LOCAL_PATH_CLONED);
		deleteFolder(clonedFolder);
	}

	private static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}
}
