package com.biit.gitgamesh.gui.localization;

/**
 * Language code interface. All vaadin common components use this interface to
 * select the language code and get an appropiate translation depending on the
 * current user or navigator.
 * 
 *
 */
public interface ILanguageCode {

	/**
	 * Returns the text identifier code.
	 * 
	 * @return
	 */
	public abstract String getCode();

	/**
	 * Returns the translation to the current text. If the text has any
	 * parameter it will use the elements passed by args.
	 * 
	 * @param args
	 * @return
	 */
	public abstract String translation(String... args);
}
