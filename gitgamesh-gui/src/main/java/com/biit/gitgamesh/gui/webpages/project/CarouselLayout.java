package com.biit.gitgamesh.gui.webpages.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.virkki.carousel.ComponentSelectListener;
import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;

import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.gui.utils.MessageManager;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.dao.IProjectFileDao;
import com.biit.gitgamesh.persistence.dao.exceptions.ElementCannotBeRemovedException;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.IdGenerator;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CarouselLayout extends HorizontalLayout {
	private static final long serialVersionUID = 8137417955779416817L;
	private static final String CSS_IMAGE_IN_CAROUSEL = "image-in-carousel";
	private static final String CSS_BUTTON_LAYOUT = "gitgamesh-image-button-layout";
	private static final String MAX_FILE_SIZE = "5mb";
	private static final String PANEL_WIDTH = "480px";
	private static final String CSS_ROOT_PANEL_LAYOUT_PROJECT_PROPERTIES = "root-panel-layout-project-properties";

	private HorizontalCarousel carousel;
	// private final VerticalLayout propertiesLayout;
	private final CssLayout fixedSizeLayout;
	private CssLayout buttonLayout;

	private Component carouselSelected;
	private Map<Layout, ProjectFile> carouselImages;

	private PrinterProject project;
	private IProjectFileDao projectImageDao;

	private Plupload uploaderButton;
	private Button removeButton;

	public CarouselLayout(PrinterProject project, IProjectFileDao projectImageDao) {
		this.project = project;
		this.projectImageDao = projectImageDao;
		carouselImages = new HashMap<>();
		this.addComponent(createCarousel());
		this.setExpandRatio(carousel, 1.0f);

		Panel rightPanel = new Panel();
		rightPanel.setWidth(PANEL_WIDTH);
		rightPanel.setHeight("100%");

		this.addComponent(rightPanel);
		this.setExpandRatio(rightPanel, 0.0f);
		this.setComponentAlignment(rightPanel, Alignment.MIDDLE_RIGHT);
		this.setSpacing(true);

		VerticalLayout rootPanelLayout = new VerticalLayout();
		rootPanelLayout.setSizeFull();
		rootPanelLayout.setSpacing(true);
		rootPanelLayout.setMargin(true);
		rootPanelLayout.setStyleName(CSS_ROOT_PANEL_LAYOUT_PROJECT_PROPERTIES);

		fixedSizeLayout = new CssLayout();
		fixedSizeLayout.setSizeFull();
		rootPanelLayout.addComponent(fixedSizeLayout);
		rootPanelLayout.setExpandRatio(fixedSizeLayout, 1.0f);

		// Add the properties menu
		fixedSizeLayout.addComponent(createPropertiesLayout(project));

		rootPanelLayout.addComponent(createImageMenu());
		rootPanelLayout.setExpandRatio(buttonLayout, 0.0f);
		rootPanelLayout.setComponentAlignment(buttonLayout, Alignment.BOTTOM_CENTER);

		rightPanel.setContent(rootPanelLayout);
	}

	private AbstractLayout createImageMenu() {
		buttonLayout = new CssLayout();
		buttonLayout.setWidthUndefined();
		buttonLayout.setStyleName(CSS_BUTTON_LAYOUT);

		uploaderButton = new Plupload(LanguageCodes.IMAGE_UPLOAD.translation(),
				ThemeIcon.IMAGE_UPLOAD.getThemeResource());
		uploaderButton.setMaxFileSize(MAX_FILE_SIZE);

		// update upload progress
		uploaderButton.addUploadProgressListener(new Plupload.UploadProgressListener() {
			private static final long serialVersionUID = 7600714848048621928L;

			@Override
			public void onUploadProgress(PluploadFile file) {
				// info.setValue("I'm uploading " + file.getName() +
				// "and I'm at " + file.getPercent() + "%");
			}
		});

		// autostart the uploader after addind files
		uploaderButton.addFilesAddedListener(new Plupload.FilesAddedListener() {
			private static final long serialVersionUID = -2431051065034589988L;

			@Override
			public void onFilesAdded(PluploadFile[] files) {
				uploaderButton.start();
			}
		});

		// notify, when the upload process is completed
		uploaderButton.addUploadCompleteListener(new Plupload.UploadCompleteListener() {
			private static final long serialVersionUID = -4800406390565664123L;

			@Override
			public void onUploadComplete() {
				// label.setValue("upload is completed!");
			}
		});

		// handle errors
		uploaderButton.addErrorListener(new Plupload.ErrorListener() {
			private static final long serialVersionUID = 3061801016052459347L;

			@Override
			public void onError(PluploadError error) {
				MessageManager.showInfo(LanguageCodes.FILE_UPLOAD_ERROR.translation(error.getMessage() + " ("
						+ error.getType() + ")"));
			}
		});

		removeButton = createButton(ThemeIcon.IMAGE_DELETE, LanguageCodes.IMAGE_DELETE, LanguageCodes.IMAGE_DELETE,
				new ClickListener() {
					private static final long serialVersionUID = -3163207753297454630L;

					@Override
					public void buttonClick(ClickEvent event) {
						removeSelectedImage();
					}
				});

		buttonLayout.addComponent(uploaderButton);
		buttonLayout.addComponent(removeButton);

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

	private FormLayout createPropertiesLayout(PrinterProject project) {
		FormLayout layout = new FormLayout();
		if (project != null) {
			layout.setSizeFull();
			layout.setSpacing(true);

			TextField field = new TextField("Downloads", String.valueOf(project.getDownloaded()));
			field.setWidth(100.0f, Unit.PERCENTAGE);
			field.setEnabled(false);
			layout.addComponent(field);

			field = new TextField("Likes", String.valueOf(project.getLikes()));
			field.setWidth(100.0f, Unit.PERCENTAGE);
			field.setEnabled(false);
			layout.addComponent(field);

			field = new TextField("File size", String.valueOf(project.getSize()) + " kB");
			field.setWidth(100.0f, Unit.PERCENTAGE);
			field.setEnabled(false);
			layout.addComponent(field);

			field = new TextField("Materials", String.valueOf(project.getFilamentsColors()));
			field.setWidth(100.0f, Unit.PERCENTAGE);
			field.setEnabled(false);
			layout.addComponent(field);

			field = new TextField("Printing time", String.valueOf(project.getTimeToDo()) + " min");
			field.setWidth(100.0f, Unit.PERCENTAGE);
			field.setEnabled(false);
			layout.addComponent(field);

			field = new TextField("Filament Quantity", String.valueOf(project.getFilamentsQuantity()) + " g");
			field.setWidth(100.0f, Unit.PERCENTAGE);
			field.setEnabled(false);
			layout.addComponent(field);
		}
		return layout;
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
		carousel.setSizeFull();
		// Add the Carousel to a parent layout
		return carousel;
	}

	public void refreshCarousel() {
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

	public void addImageToCarousel(ProjectFile image) {
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
		StreamSource imageSource = new DatabaseImageResource(resourceName, 1200, 600);

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, IdGenerator.createId());
		resource.setCacheTime(0);

		// Create an image component that gets its contents from the resource.
		return new Image(null, resource);
	}

	private Image getImage(ProjectFile image) {
		// Create an instance of our stream source.
		StreamSource imageSource = new DatabaseImageResource(image, 1200, 600);

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, IdGenerator.createId());
		resource.setCacheTime(0);

		// Create an image component that gets its contents from the resource.
		return new Image(null, resource);
	}

	private Button createButton(ThemeIcon icon, LanguageCodes caption, LanguageCodes description,
			ClickListener clickListener) {
		Button button = new Button(icon.getThemeResource());
		button.setCaption(caption.translation());
		button.setDescription(description.translation());
		button.addClickListener(clickListener);

		return button;
	}

	public Plupload getUploaderButton() {
		return uploaderButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public PrinterProject getProject() {
		return project;
	}

	public void setProject(PrinterProject project) {
		this.project = project;
	}

}
