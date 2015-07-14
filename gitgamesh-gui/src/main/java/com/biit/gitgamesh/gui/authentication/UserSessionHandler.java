package com.biit.gitgamesh.gui.authentication;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.vaadin.ui.UI;

public class UserSessionHandler {

	private IUser user;

	public UserSessionHandler(UI ui) {
		setUser(null);

		if (ui == null) {
			throw new IllegalArgumentException("Application Ui can't be null");
		} else {
			ui.getSession().setAttribute(UserSessionHandler.class, this);
		}
	}

	public static UserSessionHandler getCurrent() {
		UserSessionHandler handler = UI.getCurrent().getSession().getAttribute(UserSessionHandler.class);
		if (handler == null) {
			// Session Handler not created or expired. Create a new one.
			synchronized (UserSessionHandler.class) {
				new UserSessionHandler(UI.getCurrent());
				return getCurrent();
			}
		}
		return handler;
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public static void navigateToUserMainView() {
		GitgameshUi.navigateTo(getUserMainViewName());
	}

	public static String getUserMainViewName() {
		return Gallery.NAME;
	}

}
