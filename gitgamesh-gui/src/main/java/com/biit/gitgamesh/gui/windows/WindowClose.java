package com.biit.gitgamesh.gui.windows;

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
 * Message window that holds a content and only has a close button.
 *
 */
public abstract class WindowClose extends Window {

	private static final long serialVersionUID = 8796193085149771811L;
	private static final String SHORTCUT_CANCEL_CAPTION = "Esc as close";
	protected IconButton closeButton;
	private Component contentComponent;

	public interface CloseActionListener {
		public void closeAction(WindowClose window);
	}

	public WindowClose() {
		super();
		setProperties();
	}

	public WindowClose(Component content) {
		super("", content);
		setProperties();
	}

	private void setProperties() {
		setId(this.getClass().getName());
		setModal(true);
		setClosable(true);
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

	protected abstract ILanguageCode getCloseCaption();

	protected abstract ILanguageCode getCloseTooltip();

	protected abstract IThemeResource getCloseIcon();

	protected void generateAcceptCancelButton() {
		closeButton = new IconButton(getCloseCaption(), getCloseIcon(), getCloseTooltip(), IconSize.SMALL,
				new ClickListener() {
					private static final long serialVersionUID = 6785334478985006998L;

					@Override
					public void buttonClick(ClickEvent event) {
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

		buttonLayout.addComponent(closeButton);

		if (content != null) {
			rootLayout.addComponent(content);
			rootLayout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
			rootLayout.setExpandRatio(content, 1.0f);
		}
		rootLayout.addComponent(buttonLayout);
		rootLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		rootLayout.setExpandRatio(buttonLayout, 0.0f);

		setKeys(rootLayout);

		super.setContent(rootLayout);
	}

	private void setKeys(VerticalLayout rootLayout) {
		rootLayout.addShortcutListener(new ShortcutListener(SHORTCUT_CANCEL_CAPTION, ShortcutAction.KeyCode.ESCAPE,
				null) {
			private static final long serialVersionUID = -9055249857540860785L;

			@Override
			public void handleAction(Object sender, Object target) {
				close();
			}
		});
	}

	public void showCentered() {
		center();
		UI.getCurrent().addWindow(this);
		focus();
	}

	protected IconButton getAcceptButton() {
		return closeButton;
	}
}
