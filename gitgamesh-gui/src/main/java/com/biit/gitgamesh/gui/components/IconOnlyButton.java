package com.biit.gitgamesh.gui.components;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.BaseTheme;

public class IconOnlyButton extends Button{

	private static final long serialVersionUID = 755173150169409609L;
	private static final String BUTTON_MIN_SIZE = "20px";

	public IconOnlyButton(Resource icon) {
		super();
		setStyleName(BaseTheme.BUTTON_LINK);
		addStyleName("IconOnlyButton");
		setIcon(icon);
		setWidth(BUTTON_MIN_SIZE);
	}
}