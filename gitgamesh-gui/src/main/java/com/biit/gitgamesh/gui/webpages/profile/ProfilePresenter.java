package com.biit.gitgamesh.gui.webpages.profile;

import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonPresenter;
import com.biit.gitgamesh.utils.IActivity;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ProfilePresenter extends GitgameshCommonPresenter<IProfileView, IProfilePresenter> implements IProfilePresenter {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public IActivity[] getRequiredActivities() {
		return new IActivity[] {};
	}

}
