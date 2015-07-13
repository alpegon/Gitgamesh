package com.biit.gitgamesh.gui.webpages.login;

import com.biit.gitgamesh.gui.mvp.MVPPresenter;
import com.biit.gitgamesh.utils.IActivity;

public abstract class LoginPresenter extends MVPPresenter<ILoginView,ILoginPresenter> implements ILoginPresenter {

	@Override
	public IActivity[] getRequiredActivities() {
		return new IActivity[]{};
	}

}
