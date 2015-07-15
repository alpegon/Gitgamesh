package com.biit.gitgamesh.gui.webpages.project;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonPresenter;
import com.biit.gitgamesh.persistence.dao.jpa.ProjectImageDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.IActivity;
import com.biit.gitgamesh.utils.ImageTools;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ProjectPresenter extends GitgameshCommonPresenter<IProjectView, IProjectPresenter> implements
		IProjectPresenter {

	@Autowired
	private ProjectImageDao projectImageDao;

	@Override
	public void init() {
		// TODO Auto-generated method stub

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
	 */
	@Override
	public void createForkProject(PrinterProject project, String user) {
		if (!project.getCreatedBy().equals(user)) {
			// GitClient.cloneRepository(user, project);
		}
	}

	@Override
	public ProjectFile storeImage(PrinterProject project, String path) throws IOException {
		ProjectFile uploadedImage = new ProjectFile();
		uploadedImage.setFile(ImageTools.getBytes(ImageTools.loadFromFile(path), ImageTools.getExtension(path)));
		uploadedImage.setPrinterProject(project);
		projectImageDao.makePersistent(uploadedImage);
		return uploadedImage;
	}

}
