package com.biit.gitgamesh.gui.webpages.project;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
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
import com.biit.gitgamesh.persistence.dao.IProjectFileDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.FileReader;
import com.biit.gitgamesh.utils.exceptions.InvalidImageExtensionException;
import com.jcraft.jsch.JSchException;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinResponse;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringComponent
public class ProjectView extends GitgameshCommonView<IProjectView, IProjectPresenter> implements IProjectView {
	private static final long serialVersionUID = 8364085061299494663L;

	private static final String CSS_TABLE_LAYOUT = "gitgamesh-table-layout";

	private PrinterProject project;
	private Label description;
	private FilesMenu filesMenu;
	private FilesTable filesTable;
	private CarouselLayout carouselLayout;

	public ProjectView() {

	}

	@Autowired
	private IProjectFileDao projectImageDao;

	@Override
	public void init() {
		getContentLayout().addComponent(createTitleWithMenuLayout());

		description = new Label();
		description.setStyleName(CSS_PAGE_DESCRIPTION);
		getContentLayout().addComponent(description);

		getContentLayout().addComponent(createMiddleLayout());

		VerticalLayout verticalLayout = createFilesRootLayout();

		try {
			getContentLayout().addComponent(createWebpage());
		} catch (IOException e) {
			// Insert other thing.
			e.printStackTrace();
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
		getContentLayout().addComponent(verticalLayout);
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

	private Component createWebpage() throws IOException {
		String fileName = UUID.randomUUID() + ".stl";
		((GitgameshUi) GitgameshUi.getCurrent()).addDynamicFiles(fileName, new IServeDynamicFile() {
			@Override
			public void serveFileWithResponse(VaadinResponse response) {
				response.setContentType("text/plain");
				File fileStl = FileReader.getResource("slotted_disk.stl");
				try {
					response.getOutputStream().write(Files.readAllBytes(fileStl.toPath()));
				} catch (IOException e) {
					e.printStackTrace();
					GitgameshLogger.errorMessage(this.getClass().getName(), e);
				}
			}
		});

		String viewerHtml = FileReader.getResource("viewer.html", Charset.forName("UTF-8"));
		viewerHtml = viewerHtml.replace("%%FILE_URL%%", "/" + fileName);

		StreamResource resource = new StreamResource(new ViewerStreamSource(viewerHtml), UUID.randomUUID().toString()
				+ ".html");
		BrowserFrame frame = new BrowserFrame(null, resource);
		frame.setWidth("500px");
		frame.setHeight("500px");

		return frame;
	}

	private VerticalLayout createFilesRootLayout() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setStyleName(CSS_TABLE_LAYOUT);

		filesMenu = new FilesMenu();
		filesMenu.getUploadFileButton().addFileUploadedListener(new Plupload.FileUploadedListener() {

			private static final long serialVersionUID = 7155048020018422919L;

			@Override
			public void onFileUploaded(PluploadFile file) {
				try {
					File fileUploaded = new File(file.getUploadedFile().toString());
					try {
						GitClient.uploadRepositoryFile(project, file.getName(), fileUploaded);
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

		verticalLayout.addComponent(filesMenu);
		verticalLayout.addComponent(createFilesTable());

		return verticalLayout;
	}

	private FilesTable createFilesTable() {
		filesTable = new FilesTable();
		return filesTable;
	}

	private void updateFilesTable() {
		try {
			List<ProjectFile> projectFiles = GitClient.getRepositoryFilesInformation(project);
			if (filesTable != null) {
				for (ProjectFile projectFile : projectFiles) {
					filesTable.addRow(projectFile);
				}
			}
		} catch (JSchException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
	}

	/**
	 * Layout between header and file table.
	 * 
	 * @return
	 */
	private Layout createMiddleLayout() {
		HorizontalLayout middleLayout = new HorizontalLayout();
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
		middleLayout.addComponent(carouselLayout);
		return middleLayout;
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
	}
}
