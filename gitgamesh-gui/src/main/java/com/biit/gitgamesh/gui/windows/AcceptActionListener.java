package com.biit.gitgamesh.gui.windows;

/**
 * Interface for listeners called when a Accept action is done from a
 * IWindowAcceptCancel window.
 *
 */
public interface AcceptActionListener {

	/**
	 * Accept button has been pressed from @param window
	 * @param window
	 */
	public void acceptAction(IWindowAcceptCancel window);

}