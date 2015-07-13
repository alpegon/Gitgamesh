package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class ServerError extends MVPVaadinView<IErrorPageView, IErrorPagePresenter> {
	private static final long serialVersionUID = -8315232979293877629L;

	public ServerError() {
		super();
		init();
	}
	
	@Override
	protected IErrorPagePresenter instanciatePresenter() {
		return new ServerErrorPresenter();
	}

	@Override
	protected IErrorPageView instanciateView() {
		return new ErrorPageView();
	}

}
