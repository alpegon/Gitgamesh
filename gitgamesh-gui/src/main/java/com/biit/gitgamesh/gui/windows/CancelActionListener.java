package com.biit.gitgamesh.gui.windows;

/**
 * Interface for cancel action callbacks used when a cancel action is performed
 * from a {@link IWindowAcceptCancel}
 *
 */
public interface CancelActionListener {

	/**
	 * Cancel button has been pressed from @param window
	 * @param window
	 */
	public void cancelAction(IWindowAcceptCancel window);

}
