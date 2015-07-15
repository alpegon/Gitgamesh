package com.biit.gitgamesh.gui.webpages.project;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.virkki.carousel.ComponentSelectListener;
import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.IServeDynamicFile;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.gui.utils.MessageManager;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.dao.IProjectImageDao;
import com.biit.gitgamesh.persistence.dao.exceptions.ElementCannotBeRemovedException;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.FileReader;
import com.biit.gitgamesh.utils.IdGenerator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinResponse;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringComponent
public class ProjectView extends GitgameshCommonView<IProjectView, IProjectPresenter> implements IProjectView {
	private static final long serialVersionUID = 8364085061299494663L;

	private static final String CSS_BUTTON_LAYOUT = "gitgamesh-image-button-layout";
	private static final String CSS_TABLE_LAYOUT = "gitgamesh-table-layout";

	private static final String MAX_FILE_SIZE = "5mb";

	private static final String CSS_IMAGE_IN_CAROUSEL = "image-in-carousel";

	private PrinterProject project;
	private Label title, description;
	private FilesMenu filesMenu;
	private FilesTable filesTable;
	private HorizontalCarousel carousel;
	private Component carouselSelected;
	private Map<Layout, ProjectFile> carouselImages;

	public ProjectView() {
		carouselImages = new HashMap<>();
	}

	@Autowired
	private IProjectImageDao projectImageDao;

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
		title = new Label(LanguageCodes.PROJECT_CAPTION.translation());
		title.setStyleName(CSS_PAGE_TITLE);
		titleLayout.addComponent(title);
		titleLayout.setExpandRatio(title, 10f);

		GitMenu gitMenu = new GitMenu();
		gitMenu.addForkActionListener(new ClickListener() {
			private static final long serialVersionUID = 7783491340664046542L;

			@Override
			public void buttonClick(ClickEvent event) {

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

		StreamResource resource = new StreamResource(new ViewerStreamSource(viewerHtml), UUID.randomUUID().toString() + ".html");
		BrowserFrame frame = new BrowserFrame(null, resource);
		frame.setWidth("500px");
		frame.setHeight("500px");

		return frame;
	}

	private VerticalLayout createFilesRootLayout() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setStyleName(CSS_TABLE_LAYOUT);

		filesMenu = new FilesMenu();
		verticalLayout.addComponent(filesMenu);
		verticalLayout.addComponent(createFilesTable());

		return verticalLayout;
	}

	private FilesTable createFilesTable() {
		filesTable = new FilesTable();
		return filesTable;
	}

	/**
	 * Layout between header and file table.
	 * 
	 * @return
	 */
	private Layout createMiddleLayout() {
		HorizontalLayout middleLayout = new HorizontalLayout();
		AbstractComponentContainer carousel = createCarousel();
		middleLayout.addComponent(carousel);
		AbstractLayout menu = createImageMenu();
		middleLayout.addComponent(menu);

		return middleLayout;
	}

	private Button createButton(ThemeIcon icon, LanguageCodes caption, LanguageCodes description,
			ClickListener clickListener) {
		Button button = new Button(icon.getThemeResource());
		button.setCaption(caption.translation());
		button.setDescription(description.translation());
		button.addClickListener(clickListener);

		return button;
	}

	private AbstractLayout createImageMenu() {
		CssLayout buttonLayout = new CssLayout();
		buttonLayout.setWidth(FULL);
		buttonLayout.setStyleName(CSS_BUTTON_LAYOUT);

		Button deleteImage;

		final Plupload uploader = new Plupload(LanguageCodes.IMAGE_UPLOAD.translation(),
				ThemeIcon.IMAGE_UPLOAD.getThemeResource());
		uploader.setMaxFileSize(MAX_FILE_SIZE);

		// show notification after file is uploaded
		uploader.addFileUploadedListener(new Plupload.FileUploadedListener() {
			private static final long serialVersionUID = -2689306679308543054L;

			@Override
			public void onFileUploaded(PluploadFile file) {
				try {
					ProjectFile updatedImage = getCastedPresenter().storeImage(project,
							file.getUploadedFile().toString());
					addImageToCarousel(updatedImage);

					MessageManager.showInfo(LanguageCodes.FILE_UPLOAD_SUCCESS.translation(file.getName()));
				} catch (IOException e) {
					MessageManager.showInfo(LanguageCodes.FILE_UPLOAD_SUCCESS.translation(file.getName()));
				}
			}
		});

		// update upload progress
		uploader.addUploadProgressListener(new Plupload.UploadProgressListener() {
			private static final long serialVersionUID = 7600714848048621928L;

			@Override
			public void onUploadProgress(PluploadFile file) {
				// info.setValue("I'm uploading " + file.getName() + "and I'm at " + file.getPercent() + "%");
			}
		});

		// autostart the uploader after addind files
		uploader.addFilesAddedListener(new Plupload.FilesAddedListener() {
			private static final long serialVersionUID = -2431051065034589988L;

			@Override
			public void onFilesAdded(PluploadFile[] files) {
				uploader.start();
			}
		});

		// notify, when the upload process is completed
		uploader.addUploadCompleteListener(new Plupload.UploadCompleteListener() {
			private static final long serialVersionUID = -4800406390565664123L;

			@Override
			public void onUploadComplete() {
				// label.setValue("upload is completed!");
			}
		});

		// handle errors
		uploader.addErrorListener(new Plupload.ErrorListener() {
			private static final long serialVersionUID = 3061801016052459347L;

			@Override
			public void onError(PluploadError error) {
				MessageManager.showInfo(LanguageCodes.FILE_UPLOAD_ERROR.translation(error.getMessage() + " ("
						+ error.getType() + ")"));
			}
		});

		deleteImage = createButton(ThemeIcon.IMAGE_DELETE, LanguageCodes.IMAGE_DELETE, LanguageCodes.IMAGE_DELETE,
				new ClickListener() {
					private static final long serialVersionUID = -3163207753297454630L;

					@Override
					public void buttonClick(ClickEvent event) {
						removeSelectedImage();
					}
				});

		buttonLayout.addComponent(uploader);
		buttonLayout.addComponent(deleteImage);

		return buttonLayout;
	}

	private void removeSelectedImage() {
		ProjectFile projectImage = carouselImages.get(carouselSelected);
		if (projectImage != null) {
			try {
				if (carouselSelected != null) {
					projectImageDao.makeTransient(projectImage);
					MessageManager.showInfo(LanguageCodes.FILE_DELETE_SUCCESS.translation());
					carouselImages.remove(carouselSelected);
					carouselSelected = null;
					refreshCarousel();
				}
			} catch (ElementCannotBeRemovedException e) {
				GitgameshLogger.errorMessage(this.getClass().getName(), e);
			}
		}
		refreshCarousel();
	}

	private AbstractComponentContainer createCarousel() {
		carousel = new HorizontalCarousel();
		// Only react to arrow keys when focused
		carousel.setArrowKeysMode(ArrowKeysMode.FOCUS);
		// Fetch children lazily
		carousel.setLoadMode(CarouselLoadMode.LAZY);
		// Transition animations between the children run 500 milliseconds
		carousel.setTransitionDuration(500);
		// Add behavior
		carousel.addComponentSelectListener(new ComponentSelectListener() {
			@Override
			public void componentSelected(Component component) {
				carouselSelected = component;
			}
		});
		// Add the Carousel to a parent layout
		return carousel;
	}

	private void refreshCarousel() {
		carouselImages = new HashMap<>();
		carousel.removeAllComponents();
		// Add images of the project.
		List<ProjectFile> images = projectImageDao.getAll(project);
		for (ProjectFile image : images) {
			addImageToCarousel(image);
		}

		// Add default image if no images.
		// if (images.isEmpty()) {
		// carousel.addComponent(imageLayout(getImage("no.image.png")));
		// carouselSelected = null;
		// }
	}

	private void addImageToCarousel(ProjectFile image) {
		Layout imageLayout = imageLayout(getImage(image));
		carouselImages.put(imageLayout, image);
		carousel.addComponent(imageLayout);
		// Select first image.
		if (carouselSelected == null) {
			carouselSelected = imageLayout;
		}
	}

	private Layout imageLayout(Image image) {
		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setSizeFull();
		imageLayout.setMargin(false);
		imageLayout.setSpacing(false);
		imageLayout.addComponent(image);
		image.addStyleName(CSS_IMAGE_IN_CAROUSEL);
		imageLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		return imageLayout;
	}

	private Image getImage(String resourceName) {
		StreamSource imageSource = new DatabaseImageResource(resourceName, (int) carousel.getWidth(), (int) carousel.getHeight());

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, IdGenerator.createId());
		resource.setCacheTime(0);

		// Create an image component that gets its contents from the resource.
		return new Image(null, resource);
	}

	private Image getImage(ProjectFile image) {
		// Create an instance of our stream source.
		StreamSource imageSource = new DatabaseImageResource(image, (int) carousel.getWidth(), (int) carousel.getHeight());

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, IdGenerator.createId());
		resource.setCacheTime(0);

		// Create an image component that gets its contents from the resource.
		return new Image(null, resource);
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
		title.setValue(LanguageCodes.PROJECT_CAPTION.translation() + " " + project.getName());
		description.setValue(project.getDescription() != null ? project.getDescription() : "");
		refreshCarousel();
	}

}
