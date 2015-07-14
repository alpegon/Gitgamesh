package com.biit.gitgamesh.gui.windows;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Confirmation window with an accept cancel. If the accept button is pressed
 * the action is done, otherwise, action is discarded.
 *
 */
public abstract class WindowProceedAction extends WindowAcceptCancel implements IWindowAcceptCancel{
	private static final long serialVersionUID = -2111506182459100300L;
	private static final String width = "450px";
	private static final String height = "200px";

	private Label label;

	public WindowProceedAction(ILanguageCode code, final AcceptActionListener listener) {
		super();
		setContent(generateContent(code.translation()));
		setResizable(false);
		setDraggable(false);
		setClosable(false);
		setModal(true);
		setWidth(width);
		setHeight(height);

		addAcceptActionListener(new AcceptActionListener() {

			@Override
			public void acceptAction(IWindowAcceptCancel window) {
				listener.acceptAction(window);
				window.close();
			}
		});
		showCentered();
	}

	public WindowProceedAction(ILanguageCode code, final AcceptActionListener acceptListener, final CancelActionListener cancelListener) {
		super();
		setContent(generateContent(code.translation()));
		setResizable(false);
		setDraggable(false);
		setClosable(false);
		setModal(true);
		setWidth(width);
		setHeight(height);

		addAcceptActionListener(new AcceptActionListener() {

			@Override
			public void acceptAction(IWindowAcceptCancel window) {
				acceptListener.acceptAction(window);
				window.close();
			}
		});
		addCancelActionListener(cancelListener);
		showCentered();
	}

	private Component generateContent(String text) {

		label = new Label("<div style=\"text-align: center;\">" + text + "</div>");
		label.setContentMode(ContentMode.HTML);

		VerticalLayout rootLayout = new VerticalLayout();
		rootLayout.setSizeFull();

		rootLayout.addComponent(label);
		rootLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		return rootLayout;
	}

}
