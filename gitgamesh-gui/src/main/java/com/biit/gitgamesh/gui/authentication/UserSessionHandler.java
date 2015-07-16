package com.biit.gitgamesh.gui.authentication;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.utils.IdGenerator;
import com.vaadin.ui.UI;

public class UserSessionHandler {

	private IUser user;

	public UserSessionHandler(UI ui) {
		// TODO only for demo!!! must be null.
		setUser(createDefaultUser());

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

	private User createDefaultUser() {
		return new User(new java.sql.Timestamp(new java.util.Date().getTime()), true, "izdubar@biit.com", 0, "Izdubar",
				"en_EN", null, new java.sql.Timestamp(new java.util.Date().getTime()), "127.0.0.1", "Ninsum", false,
				null, new java.sql.Timestamp(new java.util.Date().getTime()), "127.0.0.1", "L.", null, "asd123", false,
				null, false, 1l, "izdubar", "ES", 1l, IdGenerator.createId());
	}

}
