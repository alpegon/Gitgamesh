package com.biit.gitgamesh.persistence.dao.jpa;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.exceptions.PreviewTooLongException;
import com.biit.gitgamesh.utils.ImageUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@Test(groups = { "printerProjectDao" })
public class PrinterProjectDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	private final static String PROJECT_1_NAME = "project1";
	private final static String PROJECT_1_DESCRIPTION = "This is a description.";
	private final static Set<String> PROJECT_1_DESCRIPTION_TAGS = new HashSet<>();
	private final static String PROJECT_1_PREVIEW_FILE = "ProjectPreview.png";

	static {
		PROJECT_1_DESCRIPTION_TAGS.add("Testing");
	}

	@Autowired
	private IPrinterProjectDao printerProjectDao;

	public static PrinterProject createPrinterProject(String name, String description, Set<String> tags, byte[] preview)
			throws PreviewTooLongException {
		PrinterProject project = new PrinterProject();
		project.setName(name);
		project.setDescription(description);
		project.setTags(tags);
		project.setPreview(preview);
		return project;
	}

	private boolean comparePrinterProjects(PrinterProject project1, PrinterProject project2) {
		if ((project1.getName() != null && project2.getName() == null)
				|| (project1.getName() == null && project2.getName() != null)) {
			return false;
		}
		if (project1.getName() != null && project2.getName() != null) {
			if (!project1.getName().equals(project2.getName())) {
				return false;
			}
		}

		if ((project1.getDescription() != null && project2.getDescription() == null)
				|| (project1.getDescription() == null && project2.getDescription() != null)) {
			return false;
		}
		if (project1.getDescription() != null && project2.getDescription() != null) {
			if (!project1.getDescription().equals(project2.getDescription())) {
				return false;
			}
		}

		if (project1.getTags() != null && project2.getTags() != null) {
			if (!project1.getTags().equals(project2.getTags())) {
				return false;
			}
		}

		if (!Arrays.equals(project1.getPreview(), project2.getPreview())) {
			return false;
		}

		return true;
	}

	@Test
	@Rollback(value = false)
	@Transactional(value = TxType.NEVER)
	public void storeEntity() throws IOException, PreviewTooLongException {
		Assert.assertEquals(printerProjectDao.getRowCount(), 0);
		PrinterProject project = createPrinterProject(PROJECT_1_NAME, PROJECT_1_DESCRIPTION,
				PROJECT_1_DESCRIPTION_TAGS, ImageUtils.getImageFromResource(PROJECT_1_PREVIEW_FILE));
		printerProjectDao.makePersistent(project);
		Assert.assertNotNull(project.getId());
		Assert.assertEquals(printerProjectDao.getRowCount(), 1);

		// Retrieve from database
		PrinterProject projectRetrieved = printerProjectDao.get(project.getId());
		// Check new object.
		Assert.assertTrue(comparePrinterProjects(project, projectRetrieved));
	}
}
