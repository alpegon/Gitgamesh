package com.biit.gitgamesh.gui.webpages.project;

import com.biit.gitgamesh.gui.components.HorizontalButtonGroup;
import com.biit.gitgamesh.gui.components.IconButton;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.vaadin.ui.Button.ClickListener;

public class GitMenu extends HorizontalButtonGroup {
	private static final long serialVersionUID = 1224388976091531573L;
	private final IconButton forkProject;

	public GitMenu() {
		forkProject = new IconButton(LanguageCodes.PROJECT_FORK_BUTTON, ThemeIcon.FORK_PROJECT,
				LanguageCodes.PROJECT_FORK_BUTTON_TOOLTIP);

		addIconButton(forkProject);
	}

	public void addForkActionListener(ClickListener listener) {
		forkProject.addClickListener(listener);
	}

}
