package com.biit.gitgamesh.gui.windows;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.IThemeResource;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class WindowNewProject extends WindowAcceptCancel {
	private static final long serialVersionUID = 4128310838311952427L;
	private static final String WIDTH = "450px";
	private static final String HEIGHT = "500px";
	
	private TextField projectName;
	private TextArea description;

	public WindowNewProject() {
		setWidth(WIDTH);
		setHeight(HEIGHT);
		setClosable(true);
		setDraggable(true);
		setModal(true);

		setCaption(LanguageCodes.NEW_WINDOW_CAPTION.translation());
		
		setContent(generateContent());
	}

	private Component generateContent() {
		VerticalLayout rootLayout = new VerticalLayout();
		rootLayout.setSizeFull();
		rootLayout.setMargin(true);
		rootLayout.setSpacing(true);
		
		projectName = new TextField();
		projectName.setCaption(LanguageCodes.NAME_CAPTION.translation());
		projectName.setInputPrompt(LanguageCodes.NAME_INPUT_PROMPT.translation());
		projectName.setWidth("100%");
		projectName.setHeightUndefined();
		
		description = new TextArea();
		description.setCaption(LanguageCodes.DESCRIPTION_CAPTION.translation());
		description.setInputPrompt(LanguageCodes.DESCRIPTION_INPUT_PROMPT.translation(""+PrinterProject.MAX_DESCRIPTION_LENGTH));
		description.setMaxLength(PrinterProject.MAX_DESCRIPTION_LENGTH);
		description.setSizeFull();
		
		rootLayout.addComponent(projectName);
		rootLayout.setExpandRatio(projectName, 0.0f);
		rootLayout.addComponent(description);
		rootLayout.setExpandRatio(description, 1.0f);
		
		return rootLayout;
	}

	@Override
	protected ILanguageCode getAcceptCaption() {
		return LanguageCodes.CREATE_BUTTON_CAPTION;
	}

	@Override
	protected ILanguageCode getAcceptTooltip() {
		return LanguageCodes.CREATE_BUTTON_TOOLTIP;
	}

	@Override
	protected IThemeResource getAcceptIcon() {
		return ThemeIcon.ACCEPT;
	}

	@Override
	protected ILanguageCode getCancelCaption() {
		return LanguageCodes.CANCEL_BUTTON_CAPTION;
	}

	@Override
	protected ILanguageCode getCancelTooltip() {
		return LanguageCodes.CANCEL_BUTTON_TOOLTIP;
	}

	@Override
	protected IThemeResource getCancelIcon() {
		return ThemeIcon.CANCEL;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProjectDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
