package com.biit.gitgamesh.gui.mvp;

import com.biit.gitgamesh.utils.IActivity;

/**
 * Abstract class that the user has to implement to create a webpage presenter. 
 *
 * @param <IV>
 * @param <IP>
 */
public abstract class MVPPresenter<IV extends IMVPView<IP>, IP extends IMVPPresenter<IV>>
		implements IMVPPresenter<IV> {

	private IMVPView<?> view;

	/**
	 * {@inheritDoc}
	 */
	public abstract void init();

	/**
	 * {@inheritDoc}
	 */
	public abstract IActivity[] getRequiredActivities();

	/**
	 * Returns associated view casted to a specific implemented interface.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IV getCastedView() {
		return (IV) view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setView(IMVPView<?> view) {
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMVPView<?> getView() {
		return view;
	}

}
