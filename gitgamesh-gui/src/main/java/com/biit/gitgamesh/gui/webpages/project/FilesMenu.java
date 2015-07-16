package com.biit.gitgamesh.gui.webpages.project;

import pl.exsio.plupload.Plupload;

import com.biit.gitgamesh.gui.components.HorizontalButtonGroup;
import com.biit.gitgamesh.gui.components.IconButton;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;

public class FilesMenu extends HorizontalButtonGroup {
	private static final long serialVersionUID = 170784848610533314L;
	private final IconButton downloadFile, deleteFile;
	private final Plupload uploadFile, updateFile;
	private static final String MAX_FILE_SIZE = "5mb";

	public FilesMenu() {
		downloadFile = new IconButton(LanguageCodes.PROJECT_DOWNLOAD_FILE_BUTTON, ThemeIcon.DOWNLOAD_FILE,
				LanguageCodes.PROJECT_DOWNLOAD_FILE_BUTTON_TOOLTIP);

//		uploadFile = new IconButton(LanguageCodes.PROJECT_NEW_FILE_BUTTON, ThemeIcon.UPLOAD_FILE,
//				LanguageCodes.PROJECT_NEW_FILE_BUTTON_TOOLTIP);
		
		uploadFile = new Plupload(LanguageCodes.PROJECT_NEW_FILE_BUTTON.translation(),
				ThemeIcon.UPLOAD_FILE.getThemeResource());
		uploadFile.setMaxFileSize(MAX_FILE_SIZE);
		
//		updateFile = new IconButton(LanguageCodes.PROJECT_UPDATE_BUTTON, ThemeIcon.UPDATE_FILE,
//				LanguageCodes.PROJECT_UPDATE_BUTTON_TOOLTIP);
		
		updateFile = new Plupload(LanguageCodes.PROJECT_UPDATE_BUTTON.translation(),
				ThemeIcon.UPDATE_FILE.getThemeResource());
		updateFile.setMaxFileSize(MAX_FILE_SIZE);
		
		
		deleteFile = new IconButton(LanguageCodes.PROJECT_DELETE_BUTTON, ThemeIcon.DELETE_FILE,
				LanguageCodes.PROJECT_DELETE_BUTTON_TOOLTIP);

		addIconButton(downloadFile);
		addButton(uploadFile);
		addButton(updateFile);
		addIconButton(deleteFile);
	}

	
	public IconButton getDownloadFileButton() {
		return downloadFile;
	}

	public Plupload getUploadFileButton() {
		return uploadFile;
	}

	public Plupload getUpdateFileButton() {
		return updateFile;
	}

	public IconButton getDeleteFileButton() {
		return deleteFile;
	}
}
