package com.biit.gitgamesh.gui.utils;

import com.biit.gitgamesh.GitgameshLogger;
import com.biit.gitgamesh.gui.authentication.IUser;
import com.biit.gitgamesh.gui.authentication.UserSessionHandler;
import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class MessageManager {
	private final static Integer MESSAGE_DURATION_MILISECONDS = 4000;

	public static void showWarning(IUser user, String caption, String description) {
		showMessage(user, caption, description, Notification.Type.WARNING_MESSAGE);
	}

	public static void showWarning(IUser user, ILanguageCode caption, ILanguageCode description) {
		showWarning(user, caption.translation(), description.translation());
	}

	public static void showWarning(IUser user, ILanguageCode caption) {
		showWarning(user, caption.translation(), "");
	}

	protected static void showError(IUser user, String caption, String description) {
		showMessage(user, caption, description, Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER);
	}

	public static void showError(IUser user, ILanguageCode caption, ILanguageCode description) {
		showError(user, caption.translation(), description.translation());
	}

	public static void showError(IUser user, ILanguageCode caption, String description) {
		showError(user, caption.translation(), description);
	}

	protected static void showError(IUser user, String caption) {
		showMessage(user, caption, "", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER);
	}

	public static void showError(IUser user, ILanguageCode caption) {
		showError(user, caption.translation());
	}

	protected static void showInfo(IUser user, String caption, String description) {
		showMessage(user, caption, description, Notification.Type.HUMANIZED_MESSAGE);
	}

	public static void showInfo(IUser user, ILanguageCode caption, ILanguageCode description) {
		showInfo(user, caption.translation(), description.translation());
	}

	protected static void showInfo(IUser user, String caption) {
		showMessage(user, caption, "", Notification.Type.HUMANIZED_MESSAGE);
	}

	public static void showInfo(IUser user, ILanguageCode caption) {
		showInfo(user, caption.translation());
	}

	protected static void showMessage(IUser user, String caption, String description, Notification.Type type) {
		showMessage(user, caption, description, type, Position.TOP_CENTER);
	}

	private static void showMessage(IUser user, String caption, String description, Notification.Type type,
			Position position) {
		// Log it.
		String stringUser = null;
		if (user == null) {
			stringUser = "none";
		} else {
			stringUser = user.getEmailAddress();
		}

		try {
			GitgameshLogger.info(MessageManager.class.getName(), "Message '" + caption + " " + description + "' (" + type
					+ ") displayed to user '" + stringUser + "'.");
		} catch (Exception e) {
			GitgameshLogger.errorMessage(MessageManager.class.getName(), e);
		}

		if (UI.getCurrent() != null) {
			Notification notif = new Notification(caption, description, type);

			// Set the position.
			notif.setPosition(position);

			// Let it stay there until the user clicks it if is error message
			if (type.equals(Notification.Type.ERROR_MESSAGE)) {
				notif.setDelayMsec(-1);
			} else {
				notif.setDelayMsec(MESSAGE_DURATION_MILISECONDS);
			}

			// Show it in the main window.
			notif.show(UI.getCurrent().getPage());
		}
	}
	
	public static void showInfo(ILanguageCode caption) {
		MessageManager.showInfo(UserSessionHandler.getCurrent().getUser(), caption);
	}

	public static void showInfo(String text) {
		MessageManager.showInfo(UserSessionHandler.getCurrent().getUser(), text);
	}

	public static void showError(ILanguageCode caption) {
		MessageManager.showError(UserSessionHandler.getCurrent().getUser(), caption);
	}

	public static void showError(ILanguageCode caption, ILanguageCode description) {
		MessageManager.showError(UserSessionHandler.getCurrent().getUser(), caption, description);
	}

	public static void showError(String caption) {
		MessageManager.showError(UserSessionHandler.getCurrent().getUser(), caption);
	}

	public static void showWarning(ILanguageCode caption) {
		MessageManager.showWarning(UserSessionHandler.getCurrent().getUser(), caption);
	}
}