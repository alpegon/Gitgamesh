package com.biit.gitgamesh.gui.webpages;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.biit.gitgamesh.gui.webpages.gallery.IGalleryPresenter;
import com.biit.gitgamesh.gui.webpages.gallery.IGalleryView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = Gallery.NAME)
public class Gallery extends MVPVaadinView<IGalleryView, IGalleryPresenter> {
	private static final long serialVersionUID = -3170418574111723368L;
	public static final String NAME = "gallery";
	
	@Autowired
	private IGalleryPresenter presenter;
	
	@Autowired
	private IGalleryView view;
	
	@Override
	protected IGalleryPresenter instanciatePresenter() {
		return presenter;
	}

	@Override
	protected IGalleryView instanciateView() {
		return view;
	}

}
