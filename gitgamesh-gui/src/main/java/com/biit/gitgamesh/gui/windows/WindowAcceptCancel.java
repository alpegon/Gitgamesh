package com.biit.gitgamesh.gui.windows;

import java.util.HashSet;
import java.util.Set;

import com.biit.gitgamesh.gui.components.IconButton;
import com.biit.gitgamesh.gui.components.IconSize;
import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.theme.IThemeResource;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Basic abstract window with accept cancel buttons.
 *
 */
public abstract class WindowAcceptCancel extends Window implements IWindowAcceptCancel {

	private static final long serialVersionUID = 8796193085149771811L;
	// Shortcut caption act as identifiers they don't appear to the user. If
	// they appear i've never saw them.
	private static final String SHORCUT_ACCEPT_CAPTION = "Enter as Accept";
	private static final String SHORTCUT_CANCEL_CAPTION = "Esc as cancel";
	private Set<AcceptActionListener> acceptListeners;
	private Set<CancelActionListener> cancelListeners;
	protected IconButton acceptButton;
	protected IconButton cancelButton;
	private Component contentComponent;

	public WindowAcceptCancel() {
		super();
		setProperties();
	}

	public WindowAcceptCancel(Component content) {
		super("", content);
		setProperties();
	}

	private void setProperties() {
		setId(this.getClass().getName());
		setModal(true);
		setClosable(true);
		acceptListeners = new HashSet<>();
		cancelListeners = new HashSet<>();
	}

	@Override
	public void setContent(Component content) {
		// NOTE Vaadin. Super initialization will call this function
		// even if no content is passed.
		this.contentComponent = content;
		generateLayout(contentComponent);
	}

	@Override
	public Component getContent() {
		return contentComponent;
	}

	protected abstract ILanguageCode getAcceptCaption();

	protected abstract ILanguageCode getAcceptTooltip();

	protected abstract IThemeResource getAcceptIcon();

	protected abstract ILanguageCode getCancelCaption();

	protected abstract ILanguageCode getCancelTooltip();

	protected abstract IThemeResource getCancelIcon();

	protected void generateAcceptCancelButton() {
		acceptButton = new IconButton(getAcceptCaption(), getAcceptIcon(), getAcceptTooltip(), IconSize.SMALL, new ClickListener() {
			private static final long serialVersionUID = 6785334478985006998L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (acceptAction()) {
					fireAcceptActionListeners();
				}
			}
		});
		cancelButton = new IconButton(getCancelCaption(), getCancelIcon(), getCancelTooltip(), IconSize.SMALL, new ClickListener() {
			private static final long serialVersionUID = -6302237054661116415L;

			@Override
			public void buttonClick(ClickEvent event) {
				fireCancelActionListeners();
				close();
			}
		});
	}

	protected void generateLayout(Component content) {
		generateAcceptCancelButton();

		VerticalLayout rootLayout = new VerticalLayout();
		rootLayout.setSpacing(true);
		rootLayout.setMargin(true);
		rootLayout.setSizeFull();

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidth(null);
		buttonLayout.setSpacing(true);

		buttonLayout.addComponent(acceptButton);
		buttonLayout.addComponent(cancelButton);

		if (content != null) {
			rootLayout.addComponent(content);
			rootLayout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
			rootLayout.setExpandRatio(content, 1.0f);
		}
		rootLayout.addComponent(buttonLayout);
		rootLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		rootLayout.setExpandRatio(buttonLayout, 0.0f);

		addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 2148083623407046384L;

			@Override
			public void windowClose(CloseEvent e) {
				fireCancelActionListeners();
			}
		});

		setKeys(rootLayout);

		super.setContent(rootLayout);
	}

	private void setKeys(VerticalLayout rootLayout) {
		rootLayout.addShortcutListener(new ShortcutListener(SHORCUT_ACCEPT_CAPTION, ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = -9055249857540860785L;

			@Override
			public void handleAction(Object sender, Object target) {
				fireAcceptActionListeners();
			}
		});

		rootLayout.addShortcutListener(new ShortcutListener(SHORTCUT_CANCEL_CAPTION, ShortcutAction.KeyCode.ESCAPE, null) {
			private static final long serialVersionUID = -9055249857540860785L;

			@Override
			public void handleAction(Object sender, Object target) {
				fireCancelActionListeners();
				close();
			}
		});
	}

	public void addAcceptActionListener(AcceptActionListener listener) {
		acceptListeners.add(listener);
	}

	public void removeAcceptActionListener(AcceptActionListener listener) {
		acceptListeners.remove(listener);
	}

	public void addCancelActionListener(CancelActionListener listener) {
		cancelListeners.add(listener);
	}

	public void removeAcceptActionListener(CancelActionListener listener) {
		cancelListeners.remove(listener);
	}

	private void fireAcceptActionListeners() {
		for (AcceptActionListener listener : acceptListeners) {
			listener.acceptAction(this);
		}
	}

	private void fireCancelActionListeners() {
		for (CancelActionListener listener : cancelListeners) {
			listener.cancelAction(this);
		}
	}

	public void showCentered() {
		center();
		UI.getCurrent().addWindow(this);
		focus();
	}

	/**
	 * This function will be called before firing the accept listeners and can
	 * be overriden by child classes. By default we accept the accept action and
	 * fire listener. This can be changed to reject the action if the user has
	 * generated incorrect data returning false.
	 */
	protected boolean acceptAction() {
		// DO nothing
		return true;
	}

	protected IconButton getAcceptButton() {
		return acceptButton;
	}

	protected IconButton getCancelButton() {
		return cancelButton;
	}
}
