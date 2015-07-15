package com.biit.gitgamesh.persistence.dao.jpa;

import java.io.IOException;
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
import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;
import com.biit.gitgamesh.persistence.dao.IProjectImageDao;
import com.biit.gitgamesh.persistence.dao.exceptions.ElementCannotBeRemovedException;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectImage;
import com.biit.gitgamesh.persistence.entity.exceptions.PreviewTooLongException;
import com.biit.gitgamesh.utils.ImageTools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@Test(groups = { "projectImageDao" })
public class ProjectImageDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	private final static String PROJECT_1_NAME = "project1";
	private final static String PROJECT_1_DESCRIPTION = "This is a description.";
	private final static Set<String> PROJECT_1_TAGS = new HashSet<>();
	private final static Set<String> PROJECT_1_CATEGORIES = new HashSet<>();
	private final static String PROJECT_1_PREVIEW_FILE = "ProjectPreview.png";
	private final static String PROJECT_1_USER = "user1";

	private final static String PROJECT_2_NAME = "project2";
	private final static String PROJECT_2_DESCRIPTION = "This is another description.";
	private final static Set<String> PROJECT_2_TAGS = new HashSet<>();
	private final static Set<String> PROJECT_2_CATEGORIES = new HashSet<>();
	private final static String PROJECT_2_PREVIEW_FILE = "ProjectPreview.png";
	private final static String PROJECT_2_USER = "user1";

	private final static String PROJECT_1_IMAGE_1 = "ProjectPreview.png";
	private final static String PROJECT_1_IMAGE_2 = "Image2.png";

	@Autowired
	private IProjectImageDao projectImageDao;

	@Autowired
	private IPrinterProjectDao printerProjectDao;

	private PrinterProject project1, project2;

	@Test
	@Rollback(value = false)
	@Transactional(value = TxType.NEVER)
	public void storeProjectImage() throws IOException, PreviewTooLongException {
		Assert.assertEquals(printerProjectDao.getRowCount(), 0);
		Assert.assertEquals(projectImageDao.getRowCount(), 0);

		// Create project.
		project1 = PrinterProjectDaoTest.createPrinterProject(PROJECT_1_NAME, PROJECT_1_DESCRIPTION, PROJECT_1_TAGS,
				PROJECT_1_CATEGORIES, ImageTools.loadImageFromResource(PROJECT_1_PREVIEW_FILE), PROJECT_1_USER);
		printerProjectDao.makePersistent(project1);
		Assert.assertNotNull(project1.getId());
		Assert.assertEquals(printerProjectDao.getRowCount(), 1);

		// Assign image.
		ProjectImage projectImage1 = new ProjectImage();
		projectImage1.setPrinterProject(project1);
		projectImage1.setImage(ImageTools.loadImageFromResource(PROJECT_1_IMAGE_1));
		projectImageDao.makePersistent(projectImage1);
		Assert.assertNotNull(projectImage1.getId());
		Assert.assertEquals(projectImageDao.getRowCount(), 1);

		ProjectImage projectImage2 = new ProjectImage();
		projectImage2.setPrinterProject(project1);
		projectImage2.setImage(ImageTools.loadImageFromResource(PROJECT_1_IMAGE_2));
		projectImageDao.makePersistent(projectImage2);
		Assert.assertNotNull(projectImage2.getId());
		Assert.assertEquals(projectImageDao.getRowCount(), 2);

		// Same images to project2
		project2 = PrinterProjectDaoTest.createPrinterProject(PROJECT_2_NAME, PROJECT_2_DESCRIPTION, PROJECT_2_TAGS,
				PROJECT_2_CATEGORIES, ImageTools.loadImageFromResource(PROJECT_2_PREVIEW_FILE), PROJECT_2_USER);
		printerProjectDao.makePersistent(project2);
		Assert.assertNotNull(project2.getId());
		Assert.assertEquals(printerProjectDao.getRowCount(), 2);

		// Assign image.
		projectImage1 = new ProjectImage();
		projectImage1.setPrinterProject(project2);
		projectImage1.setImage(ImageTools.loadImageFromResource(PROJECT_1_IMAGE_1));
		projectImageDao.makePersistent(projectImage1);
		Assert.assertNotNull(projectImage1.getId());
		Assert.assertEquals(projectImageDao.getRowCount(), 3);

		projectImage2 = new ProjectImage();
		projectImage2.setPrinterProject(project2);
		projectImage2.setImage(ImageTools.loadImageFromResource(PROJECT_1_IMAGE_2));
		projectImageDao.makePersistent(projectImage2);
		Assert.assertNotNull(projectImage2.getId());
		Assert.assertEquals(projectImageDao.getRowCount(), 4);
	}

	@Test(dependsOnMethods = { "storeProjectImage" })
	@Rollback(value = false)
	@Transactional(value = TxType.NEVER)
	public void getImagesOfProject() throws IOException, PreviewTooLongException {
		List<ProjectImage> projectImages1 = projectImageDao.getAll(project1);
		Assert.assertEquals(projectImages1.size(), 2);

		List<ProjectImage> projectImages2 = projectImageDao.getAll(project2);
		Assert.assertEquals(projectImages2.size(), 2);

		Assert.assertEquals(projectImageDao.getRowCount(), 4);
	}

	@AfterGroups(alwaysRun = true, value = { "projectImageDao" })
	@Rollback(value = false)
	public void clearDatabase() throws ElementCannotBeRemovedException {
		for (ProjectImage projectImage : projectImageDao.getAll()) {
			projectImageDao.makeTransient(projectImage);
		}
		Assert.assertEquals(projectImageDao.getRowCount(), 0);

		for (PrinterProject project : printerProjectDao.getAll()) {
			printerProjectDao.makeTransient(project);
		}
		Assert.assertEquals(printerProjectDao.getRowCount(), 0);
	}

}
