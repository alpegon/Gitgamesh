package com.biit.gitgamesh.gui.webpages.login;

import com.biit.gitgamesh.gui.authentication.UserSessionHandler;
import com.biit.gitgamesh.gui.localization.LanguageCodes;

public class GitgameshLoginPresenter extends LoginPresenter {

	@Override
	public String getLoginCaption() {
		return LanguageCodes.LOGIN_CAPTION_SIGN_IN.translation();
	}

	@Override
	public String getEmailCaption() {
		return LanguageCodes.LOGIN_CAPTION_EMAIL.translation();
	}

	@Override
	public String getPasswordCaption() {
		return LanguageCodes.LOGIN_CAPTION_PASSWORD.translation();
	}

	@Override
	public String getUsernameRequiredErrorCaption() {
		return LanguageCodes.LOGIN_ERROR_EMAIL.translation();
	}

	@Override
	public String getPasswordRequiredErrorCaption() {
		return LanguageCodes.LOGIN_ERROR_PASSWORD.translation();
	}

	@Override
	public String getNameVersion() {
		return LanguageCodes.LOGIN_SCREEN_SYSTEM_NAME.translation(getCastedView().getVersion());
	}

	@Override
	public void checkUserAndPassword(String username, String password) {
		UserSessionHandler.navigateToUserMainView();
	}

	@Override
	public void init() {
		// Do nothing
	}

}