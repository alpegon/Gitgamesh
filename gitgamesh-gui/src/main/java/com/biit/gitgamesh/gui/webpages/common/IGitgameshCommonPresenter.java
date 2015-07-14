package com.biit.gitgamesh.gui.webpages.common;

import com.biit.gitgamesh.gui.authentication.exceptions.ProjectAlreadyExists;
import com.biit.gitgamesh.gui.authentication.exceptions.ProjectNameInvalid;
import com.biit.gitgamesh.gui.mvp.IMVPPresenter;
import com.biit.gitgamesh.gui.mvp.IMVPView;

public interface IGitgameshCommonPresenter<IV extends IMVPView<?>> extends IMVPPresenter<IV> {
	
	void createNewProject(String name, String projectDescription) throws ProjectAlreadyExists, ProjectNameInvalid;
	
}
