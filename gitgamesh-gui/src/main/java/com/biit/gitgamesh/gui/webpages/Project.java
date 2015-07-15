package com.biit.gitgamesh.gui.webpages;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.mvp.MVPVaadinView;
import com.biit.gitgamesh.gui.webpages.project.IProjectPresenter;
import com.biit.gitgamesh.gui.webpages.project.IProjectView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = Project.NAME)
public class Project extends MVPVaadinView<IProjectView, IProjectPresenter> {
	private static final long serialVersionUID = -3170418574111723368L;
	public static final String NAME = "project";

	@Autowired
	private IProjectPresenter presenter;

	@Autowired
	private IProjectView view;

	@Override
	protected IProjectPresenter instanciatePresenter() {
		return presenter;
	}

	@Override
	protected IProjectView instanciateView() {
		return view;
	}

}
