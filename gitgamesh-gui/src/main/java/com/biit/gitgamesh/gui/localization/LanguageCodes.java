package com.biit.gitgamesh.gui.localization;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.vaadin.ui.UI;

public enum LanguageCodes implements ILanguageCode{
	
	// Common stuff
	ACCEPT_BUTTON_CAPTION("accept.button.caption"),
	ACCEPT_BUTTON_TOOLTIP("accept.button.tooltip"),
	CANCEL_BUTTON_CAPTION("cancel.button.caption"),
	CANCEL_BUTTON_TOOLTIP("cancel.button.tooltip"),
	CLOSE_BUTTON_CAPTION("close.button.caption"),
	CLOSE_BUTTON_TOOLTIP("close.button.tooltip"),
	EDIT_BUTTON_CAPTION("edit.button.caption"),
	EDIT_BUTTON_TOOLTIP("edit.button.tooltip"),
	DELETE_BUTTON_CAPTION("delete.button.caption"),
	DELETE_BUTTON_TOOLTIP("delete.button.tooltip"),
	SAVE_BUTTON_CAPTION("save.button.caption"),	
	SAVE_BUTTON_TOOLTIP("save.button.tooltip"),
	CREATE_BUTTON_CAPTION("create.button.caption"),
	CREATE_BUTTON_TOOLTIP("create.button.tooltip"),

	// Login
	LOGIN_CAPTION_EMAIL("login.caption.email"),
	LOGIN_CAPTION_PASSWORD("login.caption.password"),
	LOGIN_CAPTION_SIGN_IN("login.caption.signIn"),
	LOGIN_ERROR_EMAIL("login.error.email"),
	LOGIN_ERROR_PASSWORD("login.error.password"),
	LOGIN_ERROR_USER("error.login.user"),
	LOGIN_SCREEN_SYSTEM_NAME("login.screen.system.name"),
	
	// Error messages
	ERROR_UNEXPECTED_ERROR("error.unexpectedError"), 
	
	// Error pages
	PAGE_ERROR("page.error"), 
	PAGE_NOT_FOUND("page.not.found"), 
	AUTHORIZATION_ERROR("authorization.error"), 
	ERROR_PROJECT_ALREADY_EXISTS("error.project.already.exists"),
	INVALID_NAME("invalid.name"),
	
	// Left buttons
	USER_PROFILE_CAPTION("user.profile.caption"), 
	USER_PROFILE_TOOLTIP("user.profile.tooltip"), 
	GITGAMESH_CAPTION("gitgamesh.caption"), 
	GALLERY_CAPTION("gallery.caption"), 
	GALLERY_TOOLTIP("gallery.tooltip"), 
	UPLOAD_CAPTION("upload.caption"), 
	UPLOAD_TOOLTIP("upload.tooltip"), 
	CREATE_PROJECT_CAPTION("create.project.caption"), 
	CREATE_PROJECT_TOOLTIP("create.project.tooltip"), 
	
	//Windows
	NEW_WINDOW_CAPTION("new.window.caption"), 
	NAME_CAPTION("name.caption"),
	NAME_INPUT_PROMPT("name.input.prompt"), 
	DESCRIPTION_CAPTION("description.caption"), 
	DESCRIPTION_INPUT_PROMPT("description.input.prompt"), 
	
	//Project
	PROJECT_CAPTION("project.caption"),
	PROJECT_DOWNLOAD_FILE_BUTTON("project.download.file"),
	PROJECT_DOWNLOAD_FILE_BUTTON_TOOLTIP("project.download.file.tooltip"),
	PROJECT_NEW_FILE_BUTTON("project.new.file"),
	PROJECT_NEW_FILE_BUTTON_TOOLTIP("project.new.file.tooltip"),
	PROJECT_UPDATE_BUTTON("project.update.file"),
	PROJECT_DELETE_BUTTON("project.delete.file"),
	PROJECT_UPDATE_BUTTON_TOOLTIP("project.update.file.tooltip"),
	PROJECT_DELETE_BUTTON_TOOLTIP("project.delete.file.tooltip"),
	FILE_TABLE_HEADER_NAME("project.file.table.name"),
	FILE_TABLE_HEADER_CREATION_TIME("project.file.table.creation.time"),
	FILE_TABLE_HEADER_UPDATE_TIME("project.file.table.update.time"),
	IMAGE_UPLOAD("image.upload"),
	IMAGE_DELETE("image.delete"),
	FILE_UPLOAD_SUCCESS("file.upload.success"),
	FILE_UPLOAD_ERROR("file.upload.error"),
	FILE_DELETE_SUCCESS("file.delete.success"),
	
	PROJECT_FORK_BUTTON("project.fork.button"),
	PROJECT_FORK_BUTTON_TOOLTIP("project.fork.button.tooltip"),
	;

	private String value;
	
	LanguageCodes(String value){
		this.value = value;
	}

	@Override
	public String getCode() {
		return value;
	}

	@Override
	public String translation(String ... args) {
		return ((GitgameshUi) UI.getCurrent()).translate(this, args);
	}
	
}
