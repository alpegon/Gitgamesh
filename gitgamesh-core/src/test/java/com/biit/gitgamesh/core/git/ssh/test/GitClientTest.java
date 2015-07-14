package com.biit.gitgamesh.core.git.ssh.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.biit.gitgamesh.core.git.ssh.GitClient;
import com.jcraft.jsch.JSchException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@Test(groups = { "gitClient" })
public class GitClientTest extends AbstractTestNGSpringContextTests {

	private final static String USER_NAME = "John";

	@Test
	public void checkNewRepositoryCreation() throws JSchException {
		GitClient gitClient = new GitClient();
		gitClient.createNewRepository(USER_NAME);
	}

}
