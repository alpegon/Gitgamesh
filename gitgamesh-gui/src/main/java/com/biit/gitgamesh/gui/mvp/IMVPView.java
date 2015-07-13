package com.biit.gitgamesh.gui.mvp;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

/**
 * Generic interface that the user has to extends to create its own view
 * interface.
 *
 * @param <IP>
 */
public interface IMVPView<IP extends IMVPPresenter<?>> extends View {

	/**
	 * Sets the presenter that can be used inside of the view.
	 * @param presenter
	 */
	public void setPresenter(IMVPPresenter<?> presenter);

	/**
	 * Gets the presenter associated to this view. Returns a generic
	 * presenter interface and must be cast to the appropiate type.
	 * @return
	 */
	public IMVPPresenter<?> getPresenter();

	/**
	 * This function will be called to allow for any initialization post class
	 * constructor. When this function is called, the presenter will be already
	 * initializated and its safe to make calls to the function
	 * {@link #getPresenter()}
	 */
	public void init();

	/**
	 * Returns the root component of the webpage to be shown.
	 * 
	 * @return
	 */
	public Component getCompositionRoot();
}
