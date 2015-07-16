package com.biit.gitgamesh.gui.webpages.project;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.core.git.ssh.GitClient;
import com.biit.gitgamesh.gui.authentication.IUser;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonPresenter;
import com.biit.gitgamesh.persistence.dao.jpa.PrinterProjectDao;
import com.biit.gitgamesh.persistence.dao.jpa.ProjectImageDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.IActivity;
import com.biit.gitgamesh.utils.ImageTools;
import com.jcraft.jsch.JSchException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ProjectPresenter extends GitgameshCommonPresenter<IProjectView, IProjectPresenter> implements
		IProjectPresenter {

	@Autowired
	private ProjectImageDao projectImageDao;

	@Autowired
	private PrinterProjectDao printerProjectDao;

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
	 * @throws JSchException
	 */
	@Override
	public PrinterProject createForkProject(PrinterProject project, IUser user) throws JSchException {
		if (!project.getCreatedBy().equals(user.getScreenName())) {
			GitClient.cloneRepository(user.getScreenName(), project);
			PrinterProject newProject = new PrinterProject();
			newProject.copyData(project);
			newProject.setCreatedBy(user.getScreenName());

			printerProjectDao.makePersistent(newProject);
			return newProject;
		}
		return null;
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
