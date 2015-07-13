package com.biit.gitgamesh.gui.authentication;

import java.util.Locale;

public interface IUser {

	/**
	 * Returns user locale preference. If no preference exists must return a
	 * valid locale.
	 * 
	 * @return
	 */
	Locale getLocale();

	/**
	 * Returs user email address or an empty string. A null value is not allowed
	 * 
	 * @return
	 */
	String getEmailAddress();

}
