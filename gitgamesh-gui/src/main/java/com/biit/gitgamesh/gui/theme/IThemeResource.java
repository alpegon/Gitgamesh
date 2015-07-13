package com.biit.gitgamesh.gui.theme;

import com.vaadin.server.ThemeResource;

/**
 * Public interface used to encapsulate a vaadin theme resource and use it on
 * several components. Usually is employed in enumerators to use them as a
 * collection of theme resources that can be accessed directly
 *
 */
public interface IThemeResource {

	/**
	 * Return file as a Vaadin {@link ThemeResource}
	 * 
	 * @return
	 */
	public ThemeResource getThemeResource();

	/**
	 * Get path to the theme resource file.
	 * @return
	 */
	public String getFile();
}