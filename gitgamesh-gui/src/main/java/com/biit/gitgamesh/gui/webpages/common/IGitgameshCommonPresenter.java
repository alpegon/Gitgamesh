package com.biit.gitgamesh.gui.webpages.common;

import com.biit.gitgamesh.gui.authentication.exceptions.ProjectAlreadyExists;
import com.biit.gitgamesh.gui.authentication.exceptions.ProjectNameInvalid;
import com.biit.gitgamesh.gui.mvp.IMVPPresenter;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;

public interface IGitgameshCommonPresenter<IV extends IMVPView<?>> extends IMVPPresenter<IV> {
	
	IPrinterProjectDao getPrinterProjectDao();
	
	PrinterProject createNewProject(String name, String projectDescription) throws ProjectAlreadyExists, ProjectNameInvalid;
	
	PrinterProject getProject(Long id);
	
}
