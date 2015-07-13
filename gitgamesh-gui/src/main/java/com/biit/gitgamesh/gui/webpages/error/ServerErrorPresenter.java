package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.IThemeResource;
import com.biit.gitgamesh.gui.theme.ThemeIcon;

public class ServerErrorPresenter extends ErrorPagePresenter{

	@Override
	public ILanguageCode getLabel() {
		return LanguageCodes.PAGE_ERROR;
	}

	@Override
	public IThemeResource getImageSource() {
		return ThemeIcon.PAGE_ERROR;
	}

}
