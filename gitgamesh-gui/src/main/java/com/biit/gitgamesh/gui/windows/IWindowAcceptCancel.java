package com.biit.gitgamesh.gui.windows;

/**
 * Interface for windows with an accept and a cancel action.
 *
 */
public interface IWindowAcceptCancel {

	/**
	 * Add {@link AcceptActionListener} listener. It will be called when the
	 * accept action is performed.
	 * 
	 * @param listener
	 */
	void addAcceptActionListener(AcceptActionListener listener);

	/**
	 * Removes an {@link AcceptActionListener}
	 * @param listener
	 */
	void removeAcceptActionListener(AcceptActionListener listener);

	/**
	 * Add {@link CancelActionListener} listener. It will be called when the
	 * cancel action is performed.
	 * 
	 * @param listener
	 */
	void addCancelActionListener(CancelActionListener listener);

	/**
	 * Removes an {@link CancelActionListener}
	 * @param listener
	 */
	void removeAcceptActionListener(CancelActionListener listener);

	/**
	 * Closes the window.
	 */
	void close();
}
