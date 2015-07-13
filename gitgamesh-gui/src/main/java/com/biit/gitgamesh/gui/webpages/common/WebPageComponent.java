package com.biit.gitgamesh.gui.webpages.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.biit.gitgamesh.gui.mvp.IMVPPresenter;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.biit.gitgamesh.gui.mvp.MVPView;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;

public abstract class WebPageComponent<L extends AbstractLayout, IV extends IMVPView<IP>, IP extends IMVPPresenter<IV>> extends MVPView<IV, IP> {
	private static final long serialVersionUID = -3755142834851417202L;
	protected static final String FULL = "100%";

	private L contentlayout;

	public WebPageComponent(Class<L> layoutClass) {
		super();
		
		try {
			Constructor<L> constructor = layoutClass.getConstructor();
			contentlayout = constructor.newInstance();
		} catch (NoSuchMethodException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}		
	}

	public L getRootLayout() {
		return contentlayout;
	}

	@Override
	public Component getCompositionRoot() {
		return contentlayout;
	}

}
