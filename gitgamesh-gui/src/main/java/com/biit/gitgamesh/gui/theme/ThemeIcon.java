package com.biit.gitgamesh.gui.theme;

import com.vaadin.server.ThemeResource;

public enum ThemeIcon implements IThemeResource {

	PAGE_ERROR("page.error.svg"), 
	PAGE_NOT_FOUND("page.not.found.svg"), 
	AUTHORIZATION_ERROR("forbidden.access.svg"),
	
	USER_PROFILE("user.tie.svg"), 
	GALLERY("gallery.svg"),
	UPLOAD("cloud.upload.svg"), 
	
	CANCEL("button.cancel.svg"), 

	;

	// On web the resources are alwais with '/' using the standard OS path
	// breaks this on some browsers.
	private final static String PATH = "img/";

	private String value;

	ThemeIcon(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public ThemeResource getThemeResource() {
		return new ThemeResource(PATH + value);
	}

	public String getFile() {
		return value;
	}

}
