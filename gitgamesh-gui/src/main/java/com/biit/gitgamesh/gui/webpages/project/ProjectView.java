package com.biit.gitgamesh.gui.webpages.project;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;

@UIScope
@SpringComponent
public class ProjectView extends GitgameshCommonView<IProjectView, IProjectPresenter> implements IProjectView {
	private static final long serialVersionUID = 8364085061299494663L;

	private PrinterProject project;
	private Label title;

	@Override
	public void init() {
		title = new Label(LanguageCodes.PROJECT_CAPTION.translation());
		title.setStyleName(CSS_PAGE_TITLE);

		getContentLayout().addComponent(title);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		String[] parameters = event.getParameters().split("/");
		if (parameters.length == 0) {
			GitgameshUi.navigateTo(Gallery.NAME);
		} else {
			Long id = Long.parseLong(parameters[0]);
			project = getCastedPresenter().getProject(id);
			if (project == null) {
				GitgameshUi.navigateTo(Gallery.NAME);
			} else {
				updateUi();
			}
		}
	}

	private void updateUi() {
		title.setValue(LanguageCodes.PROJECT_CAPTION.translation()+" "+project.getName());
	}

}
