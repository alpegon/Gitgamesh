package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.biit.gitgamesh.gui.theme.IThemeResource;

/**
 * Error page view interface
 *
 */
public interface IErrorPageView extends IMVPView<IErrorPagePresenter> {

	/**
	 * Sets error label with the text specified by language code.
	 * @param content
	 */
	public void setLabelContent(ILanguageCode content);

	/**
	 * Sets the image of the Error Page with the theme resource. 
	 * @param resource
	 */
	public void setImageSource(IThemeResource resource);
	
}
