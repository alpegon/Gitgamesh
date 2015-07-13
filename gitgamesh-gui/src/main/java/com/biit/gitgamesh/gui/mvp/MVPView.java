package com.biit.gitgamesh.gui.mvp;

import com.vaadin.ui.Component;

/**
 * Abstract class that the user has to implement to create a webpage view.
 *
 * @param <IV>
 * @param <IP>
 */
public abstract class MVPView<IV extends IMVPView<IP>, IP extends IMVPPresenter<IV>>
		implements IMVPView<IP> {
	private static final long serialVersionUID = -8402124318425006825L;

	private IMVPPresenter<?> presenter;

	/**
	 * {@inheritDoc}
	 */
	public void setPresenter(IMVPPresenter<?> presenter) {
		this.presenter = presenter;
	}

	/**
	 * {@inheritDoc}
	 */
	public IMVPPresenter<?> getPresenter() {
		return presenter;
	}

	/**
	 * Returns associated presenter casted to a specific implemented interface.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IP getCastedPresenter() {
		return (IP) presenter;
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract Component getCompositionRoot();

}
