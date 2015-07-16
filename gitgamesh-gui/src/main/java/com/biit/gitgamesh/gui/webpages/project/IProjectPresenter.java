package com.biit.gitgamesh.gui.webpages.project;

import java.io.IOException;

import com.biit.gitgamesh.gui.authentication.IUser;
import com.biit.gitgamesh.gui.webpages.common.IGitgameshCommonPresenter;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.exceptions.InvalidImageExtensionException;
import com.jcraft.jsch.JSchException;

public interface IProjectPresenter extends IGitgameshCommonPresenter<IProjectView> {

	ProjectFile storeImage(PrinterProject project, String path) throws IOException, InvalidImageExtensionException;

	PrinterProject createForkProject(PrinterProject project, IUser user) throws JSchException;

}
