package com.biit.gitgamesh.gui.webpages.common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.biit.gitgamesh.gui.authentication.UserSessionHandler;
import com.biit.gitgamesh.gui.authentication.exceptions.ProjectAlreadyExists;
import com.biit.gitgamesh.gui.authentication.exceptions.ProjectNameInvalid;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.biit.gitgamesh.gui.mvp.MVPPresenter;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.exceptions.PreviewTooLongException;
import com.biit.gitgamesh.utils.ImageTools;

public abstract class GitgameshCommonPresenter<IV extends IMVPView<IP>, IP extends IGitgameshCommonPresenter<IV>>
		extends MVPPresenter<IV, IP> implements IGitgameshCommonPresenter<IV> {

	@Autowired
	private IPrinterProjectDao printerProjectDao;

	@Override
	public IPrinterProjectDao getPrinterProjectDao() {
		return printerProjectDao;
	}

	@Override
	public PrinterProject createNewProject(String name, String description) throws ProjectAlreadyExists,
			ProjectNameInvalid {
		if (name == null || name.isEmpty()) {
			throw new ProjectNameInvalid();
		}

		PrinterProject project = new PrinterProject();
		project.setName(name);
		project.setDescription(description);

		try {
			project.setPreview(ImageTools.loadImageFromResource("default.image.png"));
		} catch (PreviewTooLongException | IOException e1) {
			// This should not happen.
			GitgameshLogger.errorMessage(this.getClass().getName(), e1);
		}

		project.setCreationTime();
		project.setUpdateTime();

		if (UserSessionHandler.getCurrent().getUser() != null) {
			project.setCreatedBy(UserSessionHandler.getCurrent().getUser().getScreenName());
		}

		try {
			printerProjectDao.makePersistent(project);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new ProjectAlreadyExists();
		}

		return project;
	}

	public PrinterProject getProject(Long id) {
		return printerProjectDao.get(id);
	}
}
