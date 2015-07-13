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
