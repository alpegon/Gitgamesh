package com.biit.gitgamesh.gui.webpages.project;

import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonPresenter;
import com.biit.gitgamesh.utils.IActivity;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ProjectPresenter extends GitgameshCommonPresenter<IProjectView, IProjectPresenter> implements IProjectPresenter {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public IActivity[] getRequiredActivities() {
		return new IActivity[] {};
	}

}
