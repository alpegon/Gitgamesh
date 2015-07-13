package com.biit.gitgamesh.gui.webpages.common;

import com.biit.gitgamesh.gui.mvp.IMVPPresenter;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.vaadin.ui.VerticalLayout;

public abstract class VerticalWebPage<IV extends IMVPView<IP>, IP extends IMVPPresenter<IV>> extends WebPageComponent<VerticalLayout,IV,IP>{
	private static final long serialVersionUID = -4377565914709502670L;

	public VerticalWebPage() {
		super(VerticalLayout.class);
	}
	
	public void setFullScreen(boolean value) {
		if (value) {
			getCompositionRoot().setHeight(FULL);
		} else {
			getCompositionRoot().setHeightUndefined();
		}
	}
}
