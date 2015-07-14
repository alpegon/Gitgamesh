package com.biit.gitgamesh.gui.webpages.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.authentication.exceptions.ProjectAlreadyExists;
import com.biit.gitgamesh.gui.authentication.exceptions.ProjectNameInvalid;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.biit.gitgamesh.gui.mvp.MVPPresenter;
import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;

public abstract class GitgameshCommonPresenter<IV extends IMVPView<IP>, IP extends IGitgameshCommonPresenter<IV>> extends
		MVPPresenter<IV, IP> {

	@Autowired
	private IPrinterProjectDao printerProjectDao;

	public void createNewProject(String name, String projectDescription) throws ProjectAlreadyExists, ProjectNameInvalid {
		// TODO Auto-generated method stub
		System.out.println("common presenter create new project");
	}

}
