package com.biit.gitgamesh.gui.utils;

import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

/**
 * Specialization of custom navigator to avoid double webpage enter on UI
 * initialization.
 *
 */
public class CustomNavigator extends Navigator {
	private static final long serialVersionUID = -7473037204402054370L;

	public CustomNavigator(UI ui, ComponentContainer container) {
		super(ui, container);
	}

	public CustomNavigator(UI ui, SingleComponentContainer container) {
		super(ui, container);
	}

	public CustomNavigator(UI ui, ViewDisplay display) {
		super(ui, display);
	}

	public CustomNavigator(UI ui, NavigationStateManager stateManager, ViewDisplay display) {
		super(ui, stateManager, display);
	}

	public void setState(String navigationState) {
		getStateManager().setState(navigationState);
	}
}