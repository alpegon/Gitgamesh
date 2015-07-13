package com.biit.gitgamesh.gui.webpages;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.biit.gitgamesh.gui.webpages.login.GitgameshLoginPresenter;
import com.biit.gitgamesh.gui.webpages.login.ILoginPresenter;
import com.biit.gitgamesh.gui.webpages.login.ILoginView;
import com.biit.gitgamesh.gui.webpages.login.LoginView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = LoginPage.NAME)
public class LoginPage extends MVPVaadinView<ILoginView, ILoginPresenter>{
	private static final long serialVersionUID = -6316907323219765399L;
	public static final String NAME = "login";
	
	@Override
	protected ILoginPresenter instanciatePresenter() {
		return new GitgameshLoginPresenter();
	}
	@Override
	protected ILoginView instanciateView() {
		return new LoginView();
	}
	
	

}
