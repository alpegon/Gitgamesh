package com.biit.gitgamesh.gui.webpages.project;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.IServeDynamicFile;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.dao.IProjectImageDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectImage;
import com.biit.gitgamesh.utils.FileReader;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinResponse;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringComponent
public class ProjectView extends GitgameshCommonView<IProjectView, IProjectPresenter> implements IProjectView {
	private static final long serialVersionUID = 8364085061299494663L;

	private static final String CSS_IMAGE_IN_CAROUSEL = "image-in-carousel";

	private PrinterProject project;
	private Label title, description;

	@Autowired
	private IProjectImageDao projectImageDao;

	private HorizontalCarousel carousel;

	@Override
	public void init() {
		title = new Label(LanguageCodes.PROJECT_CAPTION.translation());
		title.setStyleName(CSS_PAGE_TITLE);
		description = new Label();
		description.setStyleName(CSS_PAGE_DESCRIPTION);

		getContentLayout().addComponent(title);
		getContentLayout().addComponent(description);
		getContentLayout().addComponent(createCarousel());
		try {
			getContentLayout().addComponent(createWebpage());
		} catch (IOException e) {
			// Insert other thing.
			e.printStackTrace();
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
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

	private AbstractComponentContainer createCarousel() {
		carousel = new HorizontalCarousel();
		// Only react to arrow keys when focused
		carousel.setArrowKeysMode(ArrowKeysMode.FOCUS);
		// Fetch children lazily
		carousel.setLoadMode(CarouselLoadMode.LAZY);
		// Transition animations between the children run 500 milliseconds
		carousel.setTransitionDuration(500);
		// Add the Carousel to a parent layout
		return carousel;
	}

	private void addImagesToCarousel() {
		carousel.removeAllComponents();
		// Add images of the project.
		List<ProjectImage> images = projectImageDao.getAll(project);
		for (ProjectImage image : images) {
			carousel.addComponent(imageLayout(getImage(image)));
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
		image.addStyleName(CSS_IMAGE_IN_CAROUSEL);
		imageLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		return imageLayout;
	}

	private Image getImage(String resourceName) {
		StreamSource imageSource = new DatabaseImageResource(resourceName, (int) carousel.getWidth(), (int) carousel.getHeight());

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, "tmp_gallery_image.png");

		// Create an image component that gets its contents from the resource.
		return new Image(null, resource);
	}

	private Image getImage(ProjectImage image) {
		// Create an instance of our stream source.
		StreamSource imageSource = new DatabaseImageResource(image, (int) carousel.getWidth(), (int) carousel.getHeight());

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, "tmp_gallery_image.png");

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
		addImagesToCarousel();
	}

}
