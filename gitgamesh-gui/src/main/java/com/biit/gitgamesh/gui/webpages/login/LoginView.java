package com.biit.gitgamesh.gui.webpages.login;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import com.biit.gitgamesh.gui.webpages.common.VerticalWebPage;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalWebPage<ILoginView, ILoginPresenter> implements ILoginView {
	private static final long serialVersionUID = -695053738213036100L;

	private static final String USERNAME_FIELD_ID = "userNameLoginForm";
	private static final String PASSWORD_FIELD_ID = "userPassLoginForm";
	private static final String LOGIN_BUTTON_ID = "loginButton";

	private static String FIELD_SIZE = "160px";

	private TextField usernameField;
	private PasswordField passwordField;

	private VerticalLayout loginVersion;

	@Override
	public void init() {
		setFullScreen(true);

		loginVersion = new VerticalLayout();
		loginVersion.setSizeUndefined();
		loginVersion.setSpacing(true);

		Panel loginPanel = buildLoginForm();
		Component nameVersion = createNameVersion();

		loginVersion.addComponent(loginPanel);
		loginVersion.addComponent(nameVersion);
		loginVersion.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		loginVersion.setComponentAlignment(nameVersion, Alignment.MIDDLE_CENTER);

		getRootLayout().addComponent(loginVersion);
		getRootLayout().setComponentAlignment(loginVersion, Alignment.MIDDLE_CENTER);
	}

	private Panel buildLoginForm() {
		Panel panel = new Panel();
		panel.setSizeUndefined();

		// Create input fields for user name and password
		usernameField = new TextField(getCastedPresenter().getEmailCaption());
		usernameField.setRequired(true);

		usernameField.setWidth(FIELD_SIZE);
		usernameField.focus();
		usernameField.setId(USERNAME_FIELD_ID);

		passwordField = new PasswordField(getCastedPresenter().getPasswordCaption());
		passwordField.setRequired(true);
		passwordField.setWidth(FIELD_SIZE);
		passwordField.setId(PASSWORD_FIELD_ID);

		// If you press enter. Login operation.
		passwordField.addShortcutListener(new ShortcutListener("Shortcut Name", ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = -3780782610097189332L;

			@Override
			public void handleAction(Object sender, Object target) {
				// limit the enters to only from the password field from this form
				if (target == passwordField) {
					getCastedPresenter().checkUserAndPassword(usernameField.getValue(), passwordField.getValue());
				}
				// If write user name and press enter, go to pass field.
				if (target == usernameField) {
					passwordField.focus();
				}
			}
		});

		// Add the login button
		Button loginButton = new Button(getCastedPresenter().getLoginCaption());
		loginButton.setId(LOGIN_BUTTON_ID);
		loginButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1239035599265918788L;

			@Override
			public void buttonClick(ClickEvent event) {
				getCastedPresenter().checkUserAndPassword(usernameField.getValue(), passwordField.getValue());
			}
		});
		loginButton.setWidth(FIELD_SIZE);

		// Alignment and sizes.
		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		layout.setSizeUndefined();
		layout.addComponent(usernameField);
		layout.addComponent(passwordField);
		layout.addComponent(loginButton);
		panel.setContent(layout);
		return panel;
	}

	private Component createNameVersion() {
		Label label = new Label(getCastedPresenter().getNameVersion());
		label.setWidth(null);
		return label;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	@Override
	public void setPasswordError(String error) {
		passwordField.setComponentError(new UserError(error));
	}

	@Override
	public PasswordField getPasswordField() {
		return passwordField;
	}

	@Override
	public TextField getUsernameField() {
		return usernameField;
	}

	@Override
	public String getVersion() {
		ServletContext context = VaadinServlet.getCurrent().getServletContext();
		Manifest manifest;
		String version = null;
		try {
			manifest = new Manifest(context.getResourceAsStream("/META-INF/MANIFEST.MF"));
			Attributes attributes = manifest.getMainAttributes();
			version = attributes.getValue("Implementation-Version");
		} catch (IOException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
		return version;
	}

}
