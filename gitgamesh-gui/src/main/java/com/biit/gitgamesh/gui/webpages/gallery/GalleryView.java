package com.biit.gitgamesh.gui.webpages.gallery;

import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class GalleryView extends GitgameshCommonView<IGalleryView,IGalleryPresenter> implements IGalleryView {
	private static final long serialVersionUID = -3493109786988382122L;
	
	public GalleryView() {
		super();
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		selectButton(getGallery());
	}

}
