package com.biit.gitgamesh.gui.mvp;

import com.biit.gitgamesh.utils.IActivity;


/**
 * Interface that any webpage presenter has to implement. If the user wants to
 * inject it in a bean style.
 * 
 * @param <IV>
 */
public interface IMVPPresenter<IV extends IMVPView<?>> {

	/**
	 * Sets the view that can be used inside of the presenter.
	 * @param view
	 */
	public void setView(IMVPView<?> view);

	/**
	 * Gets the view associated to this presenter. Returns a generic
	 * view interface and must be cast to the appropiate type.
	 * @return
	 */
	public IMVPView<?> getView();

	/**
	 * This function will be called to allow any initialization that
	 * requires to be done post constructor. Do not make calls to the view
	 * during this method as the view has not been initialized yet.
	 */
	public void init();

	/**
	 * This function will return all the activity permissions required to view
	 * this webpage. The user must implement it to return at least an empty
	 * list if the webpage doesn't have security 
	 * @return
	 */
	public IActivity[] getRequiredActivities();

}
