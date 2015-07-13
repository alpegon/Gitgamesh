package com.biit.gitgamesh.gui.webpages.login;

import com.biit.gitgamesh.gui.mvp.IMVPPresenter;

public interface ILoginPresenter extends IMVPPresenter<ILoginView>{

	public String getLoginCaption();

	public String getEmailCaption();

	public String getPasswordCaption();

	public String getUsernameRequiredErrorCaption();

	public String getPasswordRequiredErrorCaption();

	public String getNameVersion();
	
	public void checkUserAndPassword(String username, String password);
	
}
