package com.biit.gitgamesh.gui.webpages.login;

import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public interface ILoginView extends IMVPView<ILoginPresenter>{

	public void setPasswordError(String error);

	public PasswordField getPasswordField();

	public TextField getUsernameField();

	public String getVersion();
	
}
