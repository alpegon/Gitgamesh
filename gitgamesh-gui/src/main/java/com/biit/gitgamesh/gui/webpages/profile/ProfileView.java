package com.biit.gitgamesh.gui.webpages.profile;

import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ProfileView extends GitgameshCommonView<IProfileView, IProfilePresenter> implements IProfileView {
	private static final long serialVersionUID = -2538329776726592610L;

	public ProfileView() {
		super();
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		selectButton(getUserProfile());
	}

}
