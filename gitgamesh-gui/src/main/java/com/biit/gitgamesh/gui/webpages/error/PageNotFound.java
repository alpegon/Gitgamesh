package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class PageNotFound extends MVPVaadinView<IErrorPageView, IErrorPagePresenter>{
	private static final long serialVersionUID = -8594381202397446291L;

	public PageNotFound() {
		super();
		init();
	}
	
	@Override
	protected IErrorPagePresenter instanciatePresenter() {
		return new PagePresenterNotFound();
	}

	@Override
	protected IErrorPageView instanciateView() {
		return new ErrorPageView();
	}

}
