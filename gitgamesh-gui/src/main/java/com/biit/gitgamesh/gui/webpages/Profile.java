package com.biit.gitgamesh.gui.webpages;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.biit.gitgamesh.gui.webpages.profile.IProfilePresenter;
import com.biit.gitgamesh.gui.webpages.profile.IProfileView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = Profile.NAME)
public class Profile extends MVPVaadinView<IProfileView, IProfilePresenter>{
	private static final long serialVersionUID = 5651026649682731870L;
	public static final String NAME = "profile";
	
	@Autowired
	private IProfilePresenter presenter;
	
	@Autowired
	private IProfileView view;

	@Override
	protected IProfilePresenter instanciatePresenter() {
		return presenter;
	}

	@Override
	protected IProfileView instanciateView() {
		return view;
	}

}
