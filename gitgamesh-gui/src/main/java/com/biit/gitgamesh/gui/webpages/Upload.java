package com.biit.gitgamesh.gui.webpages;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.biit.gitgamesh.gui.webpages.upload.IUploadPresenter;
import com.biit.gitgamesh.gui.webpages.upload.IUploadView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = Upload.NAME)
public class Upload extends MVPVaadinView<IUploadView, IUploadPresenter>{
	private static final long serialVersionUID = 3317954307223350240L;
	public static final String NAME = "upload";

	@Autowired
	private IUploadPresenter presenter;
	
	@Autowired
	private IUploadView view;
	
	@Override
	protected IUploadPresenter instanciatePresenter() {
		return presenter;
	}

	@Override
	protected IUploadView instanciateView() {
		return view;
	}
}
