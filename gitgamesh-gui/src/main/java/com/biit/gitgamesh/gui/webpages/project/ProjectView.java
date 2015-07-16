package com.biit.gitgamesh.gui.webpages.project;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadFile;

import com.biit.gitgamesh.core.git.ssh.GitClient;
import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.IServeDynamicFile;
import com.biit.gitgamesh.gui.authentication.UserSessionHandler;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.utils.MessageManager;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.Project;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.configuration.GitgameshConfigurationReader;
import com.biit.gitgamesh.persistence.dao.IProjectFileDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.FileReader;
import com.biit.gitgamesh.utils.exceptions.InvalidImageExtensionException;
import com.jcraft.jsch.JSchException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinResponse;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringComponent
public class ProjectView extends GitgameshCommonView<IProjectView, IProjectPresenter> implements IProjectView {
	private static final long serialVersionUID = 8364085061299494663L;

	private static final String CSS_COMPONENT_TAB_STYLE = "component-tab";
	private static final String CSS_ROOT_PANEL_LAYOUT_PROJECT_PROPERTIES = "root-panel-layout-project-properties";
	private static final String CSS_CAROUSEL_LAYOUT = "carousel-layout";

	private PrinterProject project;
	private Label description;
	private FilesMenu filesMenu;
	private FilesTable filesTable;
	private CarouselLayout carouselLayout;

	private final HorizontalLayout componentTab;
	private Component renderer = null;

	private Button projectButton;
	private Button componentsButton;
	private CssLayout fixedSizeLayout;

	public ProjectView() {
		componentTab = new HorizontalLayout();
	}

	@Autowired
	private IProjectFileDao projectImageDao;

	@Override
	public void init() {
		getContentLayout().setSizeFull();
		getContentLayout().addComponent(createTitleWithMenuLayout());

		description = new Label();
		description.setStyleName(CSS_PAGE_DESCRIPTION);
		description.setHeight("143px");
		getContentLayout().addComponent(description);

		initTabSheet();
		// getContentLayout().addComponent(tabsheet);

		// VerticalLayout verticalLayout = createFilesRootLayout();
		//
		// try {
		// getContentLayout().addComponent(createWebpage());
		// } catch (IOException e) {
		// // Insert other thing.
		// e.printStackTrace();
		// GitgameshLogger.errorMessage(this.getClass().getName(), e);
		// }
		// getContentLayout().addComponent(verticalLayout);
	}

	private void initTabSheet() {
		carouselLayout = new CarouselLayout(project, projectImageDao);
		carouselLayout.getUploaderButton().addFileUploadedListener(new Plupload.FileUploadedListener() {
			private static final long serialVersionUID = -2689306679308543054L;

			@Override
			public void onFileUploaded(PluploadFile file) {
				try {
					ProjectFile updatedImage = getCastedPresenter().storeImage(project,
							file.getUploadedFile().toString());
					carouselLayout.addImageToCarousel(updatedImage);

					MessageManager.showInfo(LanguageCodes.FILE_UPLOAD_SUCCESS.translation(file.getName()));
				} catch (IOException e) {
					MessageManager.showError(LanguageCodes.FILE_UPLOAD_ERROR.translation(file.getName()));
				} catch (InvalidImageExtensionException e) {
					MessageManager.showError(LanguageCodes.FILE_INVALID);
				}
			}
		});
		carouselLayout.addStyleName(CSS_CAROUSEL_LAYOUT);
		carouselLayout.setSizeFull();

		getContentLayout().addComponent(generateSelectComponent());
		getContentLayout().addComponent(carouselLayout);

		componentTab.addStyleName(CSS_COMPONENT_TAB_STYLE);
		componentTab.setSpacing(true);
		componentTab.setSizeFull();

		filesTable = createFilesTable();
		filesTable.setSizeFull();
		componentTab.addComponent(filesTable);

		try {
			createRenderer(null);
		} catch (IOException e) {
			// DO NOTHING
		}

		Panel propertiesPanel = new Panel();
		propertiesPanel.setSizeFull();
		componentTab.addComponent(propertiesPanel);

		VerticalLayout rootPanelLayout = new VerticalLayout();
		rootPanelLayout.setSizeFull();
		rootPanelLayout.setSpacing(true);
		rootPanelLayout.setMargin(true);
		rootPanelLayout.setStyleName(CSS_ROOT_PANEL_LAYOUT_PROJECT_PROPERTIES);

		fixedSizeLayout = new CssLayout();
		fixedSizeLayout.setSizeFull();
		rootPanelLayout.addComponent(fixedSizeLayout);
		rootPanelLayout.setExpandRatio(fixedSizeLayout, 1.0f);

		rootPanelLayout.addComponent(createFilesMenuLayout());
		filesMenu.setWidth("100%");
		filesMenu.setHeight("32px");
		rootPanelLayout.setExpandRatio(filesMenu, 0.0f);
		rootPanelLayout.setComponentAlignment(filesMenu, Alignment.BOTTOM_CENTER);
		propertiesPanel.setContent(rootPanelLayout);

	}

	/**
	 * Override the renderer with a new one with the selected file.
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void createRenderer(ProjectFile file) throws IOException {
		Component newRenderer = createWebpage(file);
		if (renderer != null) {
			componentTab.addComponent(newRenderer, componentTab.getComponentIndex(renderer));
			componentTab.removeComponent(renderer);
		} else {
			componentTab.addComponent(newRenderer);
		}
		renderer = newRenderer;
	}

	private Component generateSelectComponent() {
		CssLayout buttonLayout = new CssLayout();
		buttonLayout.setWidth(FULL);
		buttonLayout.setHeight("36px");
		buttonLayout.addStyleName("select-component-button-layout");

		CssLayout innerButtonLayout = new CssLayout();
		innerButtonLayout.setWidth(FULL);
		innerButtonLayout.setHeight("36px");
		innerButtonLayout.addStyleName("select-component-inner-button-layout");

		projectButton = new Button("Project");
		componentsButton = new Button("Components");
		innerButtonLayout.addComponent(projectButton);
		innerButtonLayout.addComponent(componentsButton);
		buttonLayout.addComponent(innerButtonLayout);

		projectButton.setWidth("150px");
		projectButton.addStyleName("select-component-button");
		projectButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -766610953224879216L;

			@Override
			public void buttonClick(ClickEvent event) {

				projectButton.removeStyleName("selected");
				componentsButton.removeStyleName("selected");
				getContentLayout().removeComponent(componentTab);
				getContentLayout().removeComponent(carouselLayout);

				projectButton.addStyleName("selected");
				getContentLayout().addComponent(carouselLayout);
			}
		});

		componentsButton.setWidth("150px");
		componentsButton.addStyleName("select-component-button");
		componentsButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6680358683031320521L;

			@Override
			public void buttonClick(ClickEvent event) {

				projectButton.removeStyleName("selected");
				componentsButton.removeStyleName("selected");
				getContentLayout().removeComponent(componentTab);
				getContentLayout().removeComponent(carouselLayout);

				componentsButton.addStyleName("selected");
				getContentLayout().addComponent(componentTab);
			}
		});
		return buttonLayout;
	}

	private HorizontalLayout createTitleWithMenuLayout() {
		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.setMargin(false);
		titleLayout.setWidth("100%");
		Layout title = createHeader(LanguageCodes.PROJECT_CAPTION.translation(), null);
		titleLayout.addComponent(title);
		titleLayout.setExpandRatio(title, 10f);

		GitMenu gitMenu = new GitMenu();
		gitMenu.addForkActionListener(new ClickListener() {
			private static final long serialVersionUID = 7783491340664046542L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					PrinterProject projectClonned = getCastedPresenter().createForkProject(project,
							UserSessionHandler.getCurrent().getUser());
					if (projectClonned != null) {
						GitgameshUi.navigateTo(Project.NAME + "/" + projectClonned.getId());
					}
				} catch (JSchException e) {
					e.printStackTrace();
					MessageManager.showError(LanguageCodes.FORK_FAILED);
					GitgameshLogger.errorMessage(this.getClass().getName(), e);
				}
			}
		});

		titleLayout.addComponent(gitMenu);
		titleLayout.setComponentAlignment(gitMenu, Alignment.MIDDLE_RIGHT);
		titleLayout.setExpandRatio(gitMenu, 1f);

		return titleLayout;
	}

	private Component createWebpage(final ProjectFile file) throws IOException {

		String fileName = UUID.randomUUID() + ".stl";
		((GitgameshUi) GitgameshUi.getCurrent()).addDynamicFiles(fileName, new IServeDynamicFile() {
			@Override
			public void serveFileWithResponse(VaadinResponse response) {
				if (file != null) {
					try {
						byte[] content = GitClient.getRepositoryFile(file);
						// File now has the bytes of git.
						response.setContentType("text/plain");
						try {
							response.getOutputStream().write(content);
						} catch (IOException e) {
							GitgameshLogger.errorMessage(this.getClass().getName(), e);
						}
					} catch (JSchException | IOException e) {
						GitgameshLogger.errorMessage(this.getClass().getName(), e);
					}
				}
			}
		});
		String viewerHtml = FileReader.getResource("viewer.html", Charset.forName("UTF-8"));
		viewerHtml = viewerHtml.replace("%%FILE_URL%%", "/" + fileName).replace("%%JAVASCRIPT_HOME%%",
				GitgameshConfigurationReader.getInstance().getJavascriptHome());

		StreamResource resource = new StreamResource(new ViewerStreamSource(viewerHtml), UUID.randomUUID().toString()
				+ ".html");
		BrowserFrame frame = new BrowserFrame(null, resource);
		frame.setSizeFull();

		return frame;
	}

	private FilesMenu createFilesMenuLayout() {
		filesMenu = new FilesMenu();
		filesMenu.getUploadFileButton().addFileUploadedListener(new Plupload.FileUploadedListener() {
			private static final long serialVersionUID = 7155048020018422919L;

			@Override
			public void onFileUploaded(PluploadFile file) {
				try {
					File fileUploaded = new File(file.getUploadedFile().toString());
					try {
						GitClient.uploadRepositoryFile(project, file.getName(), fileUploaded);
						updateFilesTable();
					} catch (JSchException e) {
						GitgameshLogger.errorMessage(this.getClass().getName(), e);
						MessageManager.showError(LanguageCodes.GIT_FILE_UPLOAD_ERROR.translation(file.getName()));
					}
					MessageManager.showInfo(LanguageCodes.FILE_UPLOAD_SUCCESS.translation(file.getName()));
				} catch (IOException e) {
					MessageManager.showError(LanguageCodes.FILE_UPLOAD_ERROR.translation(file.getName()));
				}
			}
		});

		// update upload progress
		filesMenu.getUploadFileButton().addUploadProgressListener(new Plupload.UploadProgressListener() {
			private static final long serialVersionUID = 789395573657513698L;

			@Override
			public void onUploadProgress(PluploadFile file) {
				GitgameshLogger.debug(this.getClass().getName(), "I'm uploading " + file.getName() + "and I'm at "
						+ file.getPercent() + "%");
			}
		});

		filesMenu.getDownloadFileButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 512397526203911459L;

			@Override
			public void buttonClick(ClickEvent event) {
				ProjectFile projectFile = (ProjectFile) filesTable.getValue();
				try {
					byte[] stlFile = GitClient.getRepositoryFile(projectFile);
					filesMenu.setDownloaderDataSource(stlFile);
					filesMenu.setFileName(projectFile.getFileName());
				} catch (JSchException e) {
					GitgameshLogger.errorMessage(this.getClass().getName(), e);
					MessageManager.showError(LanguageCodes.GIT_FILE_DOWNLOAD_ERROR.translation(projectFile
							.getFileName()));
				} catch (IOException e) {
					MessageManager.showError(LanguageCodes.FILE_DOWNLOAD_ERROR.translation(projectFile.getFileName()));
				}

			}
		});
		return filesMenu;
	}

	private FilesTable createFilesTable() {
		filesTable = new FilesTable();
		filesTable.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 440435700490165193L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// Update 3D render when selecting an element.
				try {
					createRenderer((ProjectFile) filesTable.getValue());
				} catch (IOException e) {
					GitgameshLogger.errorMessage(this.getClass().getName(), e);
				}
			}

		});
		return filesTable;
	}

	private void updateFilesTable() {
		try {
			List<ProjectFile> projectFiles = GitClient.getRepositoryFilesInformation(project);
			if (filesTable != null) {
				filesTable.removeAllItems();
				for (ProjectFile projectFile : projectFiles) {
					if (projectFile.getFileName().endsWith(".stl")) {
						filesTable.addRow(projectFile);
					}
				}
			}
		} catch (JSchException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
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
		getTitleLabel().setValue(LanguageCodes.PROJECT_CAPTION.translation() + " " + project.getName());
		if (project.getClonnedFromProject() == null) {
			getAuthorLabel()
					.setValue(LanguageCodes.PROJECT_AUTHOR_CAPTION.translation() + " " + project.getCreatedBy());
		} else {
			getAuthorLabel().setValue(
					LanguageCodes.PROJECT_AUTHOR_CAPTION.translation() + " " + project.getCreatedBy() + " ("
							+ LanguageCodes.PROJECT_AUTHOR_SOURCE_CAPTION.translation() + " "
							+ project.getClonnedFromProject().getCreatedBy() + ")");
		}
		description.setValue(project.getDescription() != null ? project.getDescription() : "");
		carouselLayout.setProject(project);
		carouselLayout.refreshCarousel();
		updateFilesTable();

		// Update tab status.
		projectButton.removeStyleName("selected");
		componentsButton.removeStyleName("selected");
		getContentLayout().removeComponent(componentTab);
		getContentLayout().removeComponent(carouselLayout);
		projectButton.addStyleName("selected");
		getContentLayout().addComponent(carouselLayout);

	}
}
