package com.biit.gitgamesh.persistence.dao.jpa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import com.biit.gitgamesh.utils.ImageTools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@Test(groups = { "printerProjectDao" })
public class PrinterProjectDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	private final static String PROJECT_1_NAME = "project1";
	private final static String PROJECT_1_DESCRIPTION = "This is a description.";
	private final static Set<String> PROJECT_1_TAGS = new HashSet<>();
	private final static Set<String> PROJECT_1_CATEGORIES = new HashSet<>();
	private final static String PROJECT_1_PREVIEW_FILE = "ProjectPreview.png";

	private final static String PROJECT_2_NAME = "project2";
	private final static String PROJECT_2_DESCRIPTION = "This is another description.";
	private final static Set<String> PROJECT_2_TAGS = new HashSet<>();
	private final static Set<String> PROJECT_2_CATEGORIES = new HashSet<>();
	private final static String PROJECT_2_PREVIEW_FILE = "ProjectPreview.png";

	static {
		PROJECT_1_TAGS.add("tag1");
		PROJECT_1_CATEGORIES.add("cinema");
		PROJECT_1_CATEGORIES.add("sports");

		PROJECT_2_TAGS.add("tag2");
		PROJECT_2_CATEGORIES.add("cinema");
	}

	private PrinterProject project1, project2;

	@Autowired
	private IPrinterProjectDao printerProjectDao;

	public static PrinterProject createPrinterProject(String name, String description, Set<String> tags,
			Set<String> categories, byte[] preview) throws PreviewTooLongException {
		PrinterProject project = new PrinterProject();
		project.setName(name);
		project.setDescription(description);
		project.setTags(tags);
		project.setPreview(preview);
		project.setCategories(categories);
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
		} else if (project1.getTags() == null || project2.getTags() == null) {
			return false;
		}

		if (project1.getCategories() != null && project2.getCategories() != null) {
			if (!project1.getCategories().equals(project2.getCategories())) {
				return false;
			}
		} else if (project1.getCategories() == null || project2.getCategories() == null) {
			return false;
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
		project1 = createPrinterProject(PROJECT_1_NAME, PROJECT_1_DESCRIPTION, PROJECT_1_TAGS, PROJECT_1_CATEGORIES,
				ImageTools.loadImageFromResource(PROJECT_1_PREVIEW_FILE));
		printerProjectDao.makePersistent(project1);
		Assert.assertNotNull(project1.getId());
		Assert.assertEquals(printerProjectDao.getRowCount(), 1);

		// Retrieve from database
		PrinterProject projectRetrieved = printerProjectDao.get(project1.getId());
		// Check new object.
		Assert.assertTrue(comparePrinterProjects(project1, projectRetrieved));

		// Add new Project
		project2 = createPrinterProject(PROJECT_2_NAME, PROJECT_2_DESCRIPTION, PROJECT_2_TAGS, PROJECT_2_CATEGORIES,
				ImageTools.loadImageFromResource(PROJECT_2_PREVIEW_FILE));
		printerProjectDao.makePersistent(project2);
		Assert.assertNotNull(project2.getId());
		Assert.assertEquals(printerProjectDao.getRowCount(), 2);
	}

	@Test(dependsOnMethods = { "storeEntity" })
	@Rollback(value = false)
	@Transactional(value = TxType.NEVER)
	public void getPagination() throws IOException, PreviewTooLongException {
		Assert.assertEquals(printerProjectDao.getRowCount(), 2);
		List<PrinterProject> projects = printerProjectDao.get(0, 1, GalleryOrder.ALPHABETIC, null, null, null, null);
		Assert.assertEquals(projects.size(), 1);

		projects = printerProjectDao.get(100, 1, GalleryOrder.ALPHABETIC, null, null, null, null);
		Assert.assertEquals(projects.size(), 0);
	}

	@Test(dependsOnMethods = { "storeEntity" })
	@Rollback(value = false)
	@Transactional(value = TxType.NEVER)
	public void getByName() throws IOException, PreviewTooLongException {
		Assert.assertEquals(printerProjectDao.getRowCount(), 2);
		List<PrinterProject> projects = printerProjectDao.get(0, 100, GalleryOrder.ALPHABETIC, PROJECT_1_NAME, null,
				null, null);
		Assert.assertEquals(projects.size(), 1);

		projects = printerProjectDao.get(0, 100, GalleryOrder.ALPHABETIC, "Not Valid Name", null, null, null);
		Assert.assertEquals(projects.size(), 0);
	}

	@Test(dependsOnMethods = { "storeEntity" })
	@Rollback(value = false)
	@Transactional(value = TxType.NEVER)
	public void getByDate() throws IOException, PreviewTooLongException {
		Assert.assertEquals(printerProjectDao.getRowCount(), 2);
		List<PrinterProject> projects = printerProjectDao.get(0, 100, GalleryOrder.RECENT, null, null, null, null);
		Assert.assertEquals(projects.size(), 2);
		// First project must be the obtained.
		Assert.assertTrue(comparePrinterProjects(projects.get(0), project1));
	}

	@Test(dependsOnMethods = { "storeEntity" })
	@Rollback(value = false)
	@Transactional(value = TxType.NEVER)
	public void getByCategory() throws IOException, PreviewTooLongException {
		Assert.assertEquals(printerProjectDao.getRowCount(), 2);
		List<PrinterProject> projects = printerProjectDao.get(0, 100, GalleryOrder.RECENT, null, null, "sports", null);
		Assert.assertEquals(projects.size(), 1);
		// First project must be the obtained.
		Assert.assertTrue(comparePrinterProjects(projects.get(0), project1));

		projects = printerProjectDao.get(0, 100, GalleryOrder.RECENT, null, null, "cinema", null);
		Assert.assertEquals(projects.size(), 2);
	}

}
