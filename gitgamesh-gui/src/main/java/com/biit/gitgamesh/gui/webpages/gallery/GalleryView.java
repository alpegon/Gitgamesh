package com.biit.gitgamesh.gui.webpages.gallery;

import com.biit.gitgamesh.gui.webpages.common.VerticalWebPage;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class GalleryView extends VerticalWebPage<IGalleryView,IGalleryPresenter> implements IGalleryView {
	private static final long serialVersionUID = -3493109786988382122L;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
