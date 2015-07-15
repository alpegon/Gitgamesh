package com.biit.gitgamesh.gui.webpages.common;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.authentication.exceptions.ProjectAlreadyExists;
import com.biit.gitgamesh.gui.authentication.exceptions.ProjectNameInvalid;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.gui.utils.MessageManager;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.Profile;
import com.biit.gitgamesh.gui.webpages.Project;
import com.biit.gitgamesh.gui.windows.AcceptActionListener;
import com.biit.gitgamesh.gui.windows.IWindowAcceptCancel;
import com.biit.gitgamesh.gui.windows.WindowNewProject;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;

public abstract class GitgameshCommonView<IV extends IMVPView<IP>, IP extends IGitgameshCommonPresenter<IV>> extends
		WebPageComponent<CssLayout, IV, IP> {
	private static final long serialVersionUID = 4313698910834851991L;
	private static final String CSS_LEFT_MENU_LAYOUT = "menu-fixed-left";
	private static final String CSS_CONTENT_AREA = "content-area";
	private static final String CSS_ROOT_GITGAMESH_PAGE = "root-layout-gitgamesh";
	private static final String CSS_BUTTON_LAYOUT = "gitgamesh-button-layout";
	private static final String CSS_BUTTON_SELECTED = "menu-button-selected";
	protected static final String CSS_PAGE_TITLE = "page-title";
	protected static final String CSS_PAGE_DESCRIPTION = "page-description";

	private final CssLayout menuLayout;
	private final CssLayout contentLayout;

	private CssLayout buttonLayout;
	private Button userProfile;
	private Button gallery;
	private Button selectedButton;
	private Button createProject;

	public GitgameshCommonView() {
		super(CssLayout.class);
		setFullScreen(true);

		menuLayout = new CssLayout();
		menuLayout.setStyleName(CSS_LEFT_MENU_LAYOUT);
		menuLayout.setHeight(FULL);

		initializeMenuElements();
		menuLayout.addComponent(buttonLayout);

		contentLayout = new CssLayout();
		contentLayout.setStyleName(CSS_CONTENT_AREA);
		contentLayout.setWidth(FULL);
		contentLayout.setHeightUndefined();

		Responsive.makeResponsive(getRootLayout());
		getRootLayout().setStyleName(CSS_ROOT_GITGAMESH_PAGE);
		getRootLayout().addComponent(menuLayout);
		getRootLayout().addComponent(contentLayout);
	}

	private Button createButton(ThemeIcon icon, LanguageCodes caption, LanguageCodes description, ClickListener clickListener) {
		Button button = new Button(icon.getThemeResource());
		button.setCaption(caption.translation());
		button.setDescription(description.translation());
		button.addClickListener(clickListener);

		return button;
	}

	private void initializeMenuElements() {
		buttonLayout = new CssLayout();
		buttonLayout.setWidth(FULL);
		buttonLayout.setStyleName(CSS_BUTTON_LAYOUT);

		userProfile = createButton(ThemeIcon.USER_PROFILE, LanguageCodes.USER_PROFILE_CAPTION, LanguageCodes.USER_PROFILE_TOOLTIP,
				new ClickListener() {
					private static final long serialVersionUID = 7333928077624084354L;

					@Override
					public void buttonClick(ClickEvent event) {
						selectButton(userProfile);
						GitgameshUi.navigateTo(Profile.NAME);
					}
				});
		gallery = createButton(ThemeIcon.GALLERY, LanguageCodes.GALLERY_CAPTION, LanguageCodes.GALLERY_TOOLTIP, new ClickListener() {
			private static final long serialVersionUID = -1909704965160360694L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectButton(gallery);
				GitgameshUi.navigateTo(Gallery.NAME);
			}
		});
		createProject = createButton(ThemeIcon.CREATE_PROJECT, LanguageCodes.CREATE_PROJECT_CAPTION, LanguageCodes.CREATE_PROJECT_TOOLTIP,
				new ClickListener() {
					private static final long serialVersionUID = -8701022950995423972L;

					@Override
					public void buttonClick(ClickEvent event) {
						selectButton(createProject);

						WindowNewProject window = new WindowNewProject();
						window.addAcceptActionListener(new AcceptActionListener() {

							@Override
							public void acceptAction(IWindowAcceptCancel window) {
								WindowNewProject newProjectWindow = (WindowNewProject) window;
								try {
									PrinterProject project = getCastedPresenter().createNewProject(newProjectWindow.getName(), newProjectWindow.getProjectDescription());
									window.close();
									GitgameshUi.navigateTo(Project.NAME + "/" + project.getId());									
								} catch (ProjectAlreadyExists e) {
									System.out.println("kiwi5");
									MessageManager.showError(LanguageCodes.ERROR_PROJECT_ALREADY_EXISTS);
								} catch (ProjectNameInvalid e) {
									System.out.println("kiwi6");
									MessageManager.showError(LanguageCodes.INVALID_NAME);
								}
							}
						});
						window.showCentered();

					}
				});

		buttonLayout.addComponent(userProfile);
		buttonLayout.addComponent(gallery);
		buttonLayout.addComponent(createProject);
	}

	public void selectButton(Button button) {
		if (buttonLayout.getComponentIndex(button) != -1) {
			if (selectedButton != null) {
				selectedButton.removeStyleName(CSS_BUTTON_SELECTED);
			}
			selectedButton = button;
			selectedButton.addStyleName(CSS_BUTTON_SELECTED);
		}
	}

	public void setFullScreen(boolean value) {
		if (value) {
			getCompositionRoot().setSizeFull();
		} else {
			getCompositionRoot().setHeightUndefined();
		}
	}

	public Button getUserProfile() {
		return userProfile;
	}

	public Button getGallery() {
		return gallery;
	}

	public Button getUpload() {
		return createProject;
	}

	public CssLayout getContentLayout() {
		return contentLayout;
	}
}
