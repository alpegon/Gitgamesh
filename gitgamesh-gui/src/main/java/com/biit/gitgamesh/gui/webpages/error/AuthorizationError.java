package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class AuthorizationError extends MVPVaadinView<IErrorPageView, IErrorPagePresenter>{
	private static final long serialVersionUID = 8717765342959748029L;
	
	public AuthorizationError() {
		super();
		init();
	}

	@Override
	protected IErrorPagePresenter instanciatePresenter() {
		return new AuthorizationErrorPresenter();
	}

	@Override
	protected IErrorPageView instanciateView() {
		return new ErrorPageView();
	}
}
