package com.biit.gitgamesh.gui.webpages.gallery;

import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonPresenter;
import com.biit.gitgamesh.utils.IActivity;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class GalleryPresenter extends GitgameshCommonPresenter<IGalleryView, IGalleryPresenter> implements IGalleryPresenter {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public IActivity[] getRequiredActivities() {
		return new IActivity[] {};
	}

}
