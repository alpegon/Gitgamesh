package com.biit.gitgamesh.gui.webpages.project;

import com.biit.gitgamesh.gui.components.HorizontalButtonGroup;
import com.biit.gitgamesh.gui.components.IconButton;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;

public class FilesMenu extends HorizontalButtonGroup {
	private static final long serialVersionUID = 170784848610533314L;
	private final IconButton downloadFile, uploadFile, updateFile, deleteFile;

	public FilesMenu() {
		downloadFile = new IconButton(LanguageCodes.PROJECT_DOWNLOAD_FILE_BUTTON, ThemeIcon.DOWNLOAD_FILE,
				LanguageCodes.PROJECT_DOWNLOAD_FILE_BUTTON_TOOLTIP);
		uploadFile = new IconButton(LanguageCodes.PROJECT_NEW_FILE_BUTTON, ThemeIcon.UPLOAD_FILE,
				LanguageCodes.PROJECT_NEW_FILE_BUTTON_TOOLTIP);
		updateFile = new IconButton(LanguageCodes.PROJECT_UPDATE_BUTTON, ThemeIcon.UPDATE_FILE,
				LanguageCodes.PROJECT_UPDATE_BUTTON_TOOLTIP);
		deleteFile = new IconButton(LanguageCodes.PROJECT_DELETE_BUTTON, ThemeIcon.DELETE_FILE,
				LanguageCodes.PROJECT_DELETE_BUTTON_TOOLTIP);

		addIconButton(downloadFile);
		addIconButton(uploadFile);
		addIconButton(updateFile);
		addIconButton(deleteFile);

	}

}
