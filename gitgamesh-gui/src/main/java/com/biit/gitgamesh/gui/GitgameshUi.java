package com.biit.gitgamesh.gui;

import java.io.IOException;
import java.util.HashMap;

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
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinSession;
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
	
	private HashMap<String, IServeDynamicFile> dinamicFiles;
	
	public GitgameshUi() {
		dinamicFiles = new HashMap<>();
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("");
		defineNavigation();
		setErrorHandler(new CustomErrorHandler(LanguageCodes.ERROR_UNEXPECTED_ERROR));
		
		VaadinSession.getCurrent().addRequestHandler(new RequestHandler() {
			private static final long serialVersionUID = -3272419412231462706L;

			@Override
			public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) throws IOException {
				IServeDynamicFile dynamicFile = getDinamicFile(request);
				if(dynamicFile==null){
					return false;
				}else{
					dynamicFile.serveFileWithResponse(response);
					return true;
				}
			}
		});
		
	}
	
	public synchronized void addDynamicFiles(String name, IServeDynamicFile serveFile){
		dinamicFiles.put(name, serveFile);
	}
	
	/**
	 * Restuns null if the file can't be served.
	 * @param name
	 * @return
	 */
	public synchronized IServeDynamicFile getDinamicFile(VaadinRequest request){
		return dinamicFiles.get(request.getPathInfo().substring(1, request.getPathInfo().length()));
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
