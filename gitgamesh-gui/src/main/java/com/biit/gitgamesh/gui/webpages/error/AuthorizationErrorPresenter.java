package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.IThemeResource;
import com.biit.gitgamesh.gui.theme.ThemeIcon;

public class AuthorizationErrorPresenter extends ErrorPagePresenter{

	@Override
	public ILanguageCode getLabel() {
		return LanguageCodes.AUTHORIZATION_ERROR;
	}

	@Override
	public IThemeResource getImageSource() {
		return ThemeIcon.AUTHORIZATION_ERROR;
	}

}
