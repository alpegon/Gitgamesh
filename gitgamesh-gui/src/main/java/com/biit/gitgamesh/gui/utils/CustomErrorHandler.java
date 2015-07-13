package com.biit.gitgamesh.gui.utils;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.vaadin.server.DefaultErrorHandler;

/**
 * Class to forward all Vaadin server error (that create a big annoying tooltip) to the logger and show a simple
 * unexpected error message to the user.
 *
 */
public class CustomErrorHandler extends DefaultErrorHandler {
	private static final long serialVersionUID = -5570064834518413901L;

	private final ILanguageCode unexpectedError;

	public CustomErrorHandler(ILanguageCode unexpectedError) {
		this.unexpectedError = unexpectedError;
	}

	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		// Throw the error to the logger.
		GitgameshLogger.errorMessage(CustomErrorHandler.class.getName(), event.getThrowable());
		MessageManager.showError(unexpectedError);
	}
};