package com.biit.gitgamesh.gui.webpages.project;

import com.biit.gitgamesh.gui.components.HorizontalButtonGroup;
import com.biit.gitgamesh.gui.components.IconButton;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;

public class FilesMenu extends HorizontalButtonGroup {
	private static final long serialVersionUID = 170784848610533314L;
	private final IconButton uploadFile, updateFile;

	public FilesMenu() {
		uploadFile = new IconButton(LanguageCodes.PROJECT_NEW_FILE_BUTTON, ThemeIcon.UPLOAD_FILE,
				LanguageCodes.PROJECT_NEW_FILE_BUTTON_TOOLTIP);
		updateFile = new IconButton(LanguageCodes.PROJECT_UPDATE_BUTTON, ThemeIcon.UPDATE_FILE,
				LanguageCodes.PROJECT_UPDATE_BUTTON_TOOLTIP);

		addIconButton(uploadFile);
		addIconButton(updateFile);

	}

}
