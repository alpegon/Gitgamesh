package com.biit.gitgamesh.gui.webpages.project;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.core.git.ssh.GitClient;
import com.biit.gitgamesh.gui.authentication.IUser;
import com.biit.gitgamesh.gui.components.GalleryElement;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonPresenter;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.dao.jpa.PrinterProjectDao;
import com.biit.gitgamesh.persistence.dao.jpa.ProjectFileDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.persistence.entity.exceptions.PreviewTooLongException;
import com.biit.gitgamesh.utils.IActivity;
import com.biit.gitgamesh.utils.ImageTools;
import com.biit.gitgamesh.utils.exceptions.InvalidImageExtensionException;
import com.jcraft.jsch.JSchException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ProjectPresenter extends GitgameshCommonPresenter<IProjectView, IProjectPresenter> implements
		IProjectPresenter {

	@Autowired
	private ProjectFileDao projectImageDao;

	@Autowired
	private PrinterProjectDao printerProjectDao;

	@Override
	public void init() {

	}

	@Override
	public IActivity[] getRequiredActivities() {
		return new IActivity[] {};
	}

	/**
	 * Create a copy of a project for the selected user.
	 * 
	 * @param project
	 * @param user
	 * @throws JSchException
	 */
	@Override
	public PrinterProject createForkProject(PrinterProject project, IUser user) throws JSchException {
		if (!project.getCreatedBy().equals(user.getScreenName())) {

			// Clone GIT repo
			GitClient.cloneRepository(user.getScreenName(), project);

			// Clone project.
			PrinterProject newProject = new PrinterProject();
			newProject.copyData(project);
			newProject.setCreatedBy(user.getScreenName());
			newProject.resetIds();
			newProject.setCreationTime();
			newProject.setUpdateTime();
			newProject.setClonnedFromProject(project);

			printerProjectDao.makePersistent(newProject);

			// Clone images.
			List<ProjectFile> images = projectImageDao.getAll(project);
			for (ProjectFile image : images) {
				ProjectFile newImage = new ProjectFile();
				newImage.copyData(image);
				newImage.resetIds();
				newImage.setPrinterProject(newProject);
				projectImageDao.makePersistent(newImage);
			}

			return newProject;
		}
		return null;
	}

	@Override
	public ProjectFile storeImage(PrinterProject project, String path) throws IOException,
			InvalidImageExtensionException {
		ProjectFile uploadedImage = new ProjectFile();
		BufferedImage imageBuffer = ImageTools.loadFromFile(path);
		String extension = ImageTools.getExtension(path);
		byte[] image = ImageTools.getBytes(imageBuffer, extension);
		uploadedImage.setFile(image);
		uploadedImage.setPrinterProject(project);
		projectImageDao.makePersistent(uploadedImage);

		// Create preview if not exists.
		if (project.getPreview() == null) {
			try {
				project.setPreview(ImageTools.getBytes(ImageTools.resizeImage(imageBuffer,
						GalleryElement.THUMBNAIL_WIDTH, GalleryElement.THUMBNAIL_HEIGHT, ImageTools.preserveAlpha(extension)), extension));
			} catch (PreviewTooLongException e) {
				GitgameshLogger.errorMessage(this.getClass().getName(), e);
			}
		}

		return uploadedImage;
	}

}
