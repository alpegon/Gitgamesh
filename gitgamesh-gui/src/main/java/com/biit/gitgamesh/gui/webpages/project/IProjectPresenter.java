package com.biit.gitgamesh.gui.webpages.project;

import java.io.IOException;

import com.biit.gitgamesh.gui.webpages.common.IGitgameshCommonPresenter;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;

public interface IProjectPresenter extends IGitgameshCommonPresenter<IProjectView> {

	ProjectFile storeImage(PrinterProject project, String path) throws IOException;

	void createForkProject(PrinterProject project, String user);

}
