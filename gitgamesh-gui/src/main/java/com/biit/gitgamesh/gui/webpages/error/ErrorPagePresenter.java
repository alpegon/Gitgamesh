package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.mvp.MVPPresenter;
import com.biit.gitgamesh.gui.theme.IThemeResource;
import com.biit.gitgamesh.utils.IActivity;

/**
 * Abstract error page presenter. It needs to return the text label for the
 * error and the image that accompanying the error.
 *
 */
public abstract class ErrorPagePresenter extends MVPPresenter<IErrorPageView, IErrorPagePresenter> implements IErrorPagePresenter {

	@Override
	public void init() {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract ILanguageCode getLabel();

	/**
	 * {@inheritDoc}
	 */
	public abstract IThemeResource getImageSource();

	@Override
	public IActivity[] getRequiredActivities() {
		// No activity permission is required to see an error.
		return new IActivity[] {};
	}

}
