package com.biit.gitgamesh.gui.webpages.upload;

import com.biit.gitgamesh.gui.mvp.MVPPresenter;
import com.biit.gitgamesh.utils.IActivity;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class UploadPresenter extends MVPPresenter<IUploadView, IUploadPresenter> implements IUploadPresenter {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public IActivity[] getRequiredActivities() {
		return new IActivity[] {};
	}

}