package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.mvp.IMVPPresenter;
import com.biit.gitgamesh.gui.theme.IThemeResource;

/**
 * Error page presenter interface.
 *
 */
public interface IErrorPagePresenter extends IMVPPresenter<IErrorPageView> {

	/**
	 * Returns the error message with a {@link ILanguageCode} object.
	 */
	public ILanguageCode getLabel();
	
	/**
	 * Returns the image accompanyng the error with a {@link IThemeResource}
	 */
	public IThemeResource getImageSource();
	
}
