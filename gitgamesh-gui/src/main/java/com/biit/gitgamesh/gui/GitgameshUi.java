package com.biit.gitgamesh.gui;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.authentication.UserSessionHandler;
import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.localization.LocalizationManager;
import com.biit.gitgamesh.gui.utils.CustomErrorHandler;
import com.biit.gitgamesh.gui.utils.CustomNavigator;
import com.biit.gitgamesh.gui.webpages.LoginPage;
import com.biit.gitgamesh.gui.webpages.error.AuthorizationError;
import com.biit.gitgamesh.gui.webpages.error.PageNotFound;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@PreserveOnRefresh
@Push
@Theme("gitgameshTheme")
@Widgetset("com.biit.gitgamesh.gui.AppWidgetSet")
@SpringUI
public class GitgameshUi extends UI{
	private static final long serialVersionUID = 131764568338199964L;
	
	private CustomNavigator navigator;
	
	@Autowired
	private SpringViewProvider viewProvider;
	
	@Autowired
	private LocalizationManager localization;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("");
		defineNavigation();
		setErrorHandler(new CustomErrorHandler(LanguageCodes.ERROR_UNEXPECTED_ERROR));
	}

	public String translate(ILanguageCode languageCode, String... args) {
		return localization.translate(languageCode, args);
	}
	
	/**
	 * Initialize Spring injected navigator with the views of application.
	 */
	private void defineNavigation() {
		navigator = new CustomNavigator(this, this);
		navigator.addProvider(viewProvider);

		// Set not found page.
		navigator.setErrorView(PageNotFound.class);
		// Set authorization error page.
		viewProvider.setAccessDeniedViewClass(AuthorizationError.class);
		setChangeViewEvents();
	}
	
	/**
	 * Set change view events to store information of current view
	 */
	private void setChangeViewEvents() {
		navigator.addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = -668206181478591694L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				if (event.getViewName().isEmpty()) {
					// If an access to empty url is done go to main page or login screen
					if (UserSessionHandler.getCurrent().getUser() == null) {
						navigator.navigateTo(LoginPage.NAME);
					} else {
						UserSessionHandler.navigateToUserMainView();
					}
					return false;
				}
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				if (UserSessionHandler.getCurrent().getUser() != null) {
					GitgameshLogger.info(this.getClass().getName(), "User '"
							+ UserSessionHandler.getCurrent().getUser().getEmailAddress() + "' has changed view to '"
							+ event.getNewView() + "'.");
				}
			}
		});
	}

	public static void navigateTo(String name) {
		((GitgameshUi) UI.getCurrent()).getNavigator().navigateTo(name);
	}
}
