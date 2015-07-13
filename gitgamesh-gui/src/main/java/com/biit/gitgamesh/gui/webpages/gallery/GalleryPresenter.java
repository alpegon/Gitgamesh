package com.biit.gitgamesh.gui.webpages.gallery;

import com.biit.gitgamesh.gui.mvp.MVPPresenter;
import com.biit.gitgamesh.utils.IActivity;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class GalleryPresenter extends MVPPresenter<IGalleryView,IGalleryPresenter> implements IGalleryPresenter {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IActivity[] getRequiredActivities() {
		return new IActivity[]{};
	}

}
