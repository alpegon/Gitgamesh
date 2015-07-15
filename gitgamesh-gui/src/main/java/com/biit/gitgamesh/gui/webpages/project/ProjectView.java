package com.biit.gitgamesh.gui.webpages.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.virkki.carousel.ComponentSelectListener;
import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.dao.IProjectImageDao;
import com.biit.gitgamesh.persistence.dao.exceptions.ElementCannotBeRemovedException;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectImage;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HasComponents.ComponentDetachEvent;
import com.vaadin.ui.HasComponents.ComponentDetachListener;
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

	private PrinterProject project;
	private Label title, description;
	private FilesMenu filesMenu;
	private FilesTable filesTable;
	private HorizontalCarousel carousel;
	private Component carouselSelected;
	private Map<Layout, ProjectImage> carouselImages;

	public ProjectView() {
		carouselImages = new HashMap<>();
	}

	@Autowired
	private IProjectImageDao projectImageDao;

	@Override
	public void init() {
		title = new Label(LanguageCodes.PROJECT_CAPTION.translation());
		title.setStyleName(CSS_PAGE_TITLE);
		description = new Label();
		description.setStyleName(CSS_PAGE_DESCRIPTION);
		VerticalLayout verticalLayout = createFilesRootLayout();

		getContentLayout().addComponent(title);
		getContentLayout().addComponent(description);
		getContentLayout().addComponent(createCarouselLayout());
		getContentLayout().addComponent(verticalLayout);
	}

	private VerticalLayout createFilesRootLayout() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setStyleName(CSS_TABLE_LAYOUT);

		verticalLayout.addComponent(createMenu());
		verticalLayout.addComponent(createFilesTable());

		return verticalLayout;
	}

	private FilesTable createFilesTable() {
		filesTable = new FilesTable();
		return filesTable;
	}

	private FilesMenu createMenu() {
		filesMenu = new FilesMenu();
		return filesMenu;
	}

	private Layout createCarouselLayout() {
		HorizontalLayout rootCarousel = new HorizontalLayout();
		AbstractComponentContainer carousel = createCarousel();
		rootCarousel.addComponent(carousel);
		AbstractLayout menu = createImageMenu();
		rootCarousel.addComponent(menu);
		return rootCarousel;
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

		Button uploadImage, deleteImage;

		uploadImage = createButton(ThemeIcon.IMAGE_UPLOAD, LanguageCodes.IMAGE_UPLOAD, LanguageCodes.IMAGE_UPLOAD,
				new ClickListener() {
					private static final long serialVersionUID = 7783491340664046542L;

					@Override
					public void buttonClick(ClickEvent event) {
						// carousel.addComponent();
						refreshCarousel();

					}
				});
		deleteImage = createButton(ThemeIcon.IMAGE_DELETE, LanguageCodes.IMAGE_DELETE, LanguageCodes.IMAGE_DELETE,
				new ClickListener() {
					private static final long serialVersionUID = -3163207753297454630L;

					@Override
					public void buttonClick(ClickEvent event) {
						removeSelectedImage();
						carousel.removeComponent(carouselSelected);
					}
				});

		buttonLayout.addComponent(uploadImage);
		buttonLayout.addComponent(deleteImage);

		return buttonLayout;
	}

	private void removeSelectedImage() {
		ProjectImage projectImage = carouselImages.get(carouselSelected);
		if (projectImage != null) {
			try {
				projectImageDao.makeTransient(projectImage);
			} catch (ElementCannotBeRemovedException e) {
				GitgameshLogger.errorMessage(this.getClass().getName(), e);
			}
		}
	}

	private AbstractComponentContainer createCarousel() {
		carousel = new HorizontalCarousel();
		// Only react to arrow keys when focused
		carousel.setArrowKeysMode(ArrowKeysMode.FOCUS);
		// Fetch children lazily
		carousel.setLoadMode(CarouselLoadMode.LAZY);
		// Transition animations between the children run 500 milliseconds
		carousel.setTransitionDuration(500);
		// Add behaviori
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
		carousel.removeAllComponents();
		// Add images of the project.
		List<ProjectImage> images = projectImageDao.getAll(project);
		for (ProjectImage image : images) {
			Layout imageLayout = imageLayout(getImage(image));
			carouselImages.put(imageLayout, image);
			carousel.addComponent(imageLayout);
		}

		// Add default image if no images.
		if (images.isEmpty()) {
			carousel.addComponent(imageLayout(getImage("no.image.png")));
		}
	}

	private Layout imageLayout(Image image) {
		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setSizeFull();
		imageLayout.setMargin(false);
		imageLayout.setSpacing(false);
		imageLayout.addComponent(image);
		imageLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		return imageLayout;
	}

	private Image getImage(String resourceName) {
		StreamSource imageSource = new DatabaseImageResource(resourceName, (int) carousel.getWidth(),
				(int) carousel.getHeight());

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, "tmp_gallery_image.png");

		// Create an image component that gets its contents from the resource.
		return new Image("", resource);
	}

	private Image getImage(ProjectImage image) {
		// Create an instance of our stream source.
		StreamSource imageSource = new DatabaseImageResource(image, (int) carousel.getWidth(),
				(int) carousel.getHeight());

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, "tmp_gallery_image.png");

		// Create an image component that gets its contents from the resource.
		return new Image("", resource);
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
