package com.biit.gitgamesh.gui.webpages.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.biit.gitgamesh.persistence.dao.IProjectImageDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectImage;
import com.biit.gitgamesh.utils.ImageTools;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

@UIScope
@SpringComponent
public class ProjectView extends GitgameshCommonView<IProjectView, IProjectPresenter> implements IProjectView {
	private static final long serialVersionUID = 8364085061299494663L;

	private PrinterProject project;
	private Label title;

	@Autowired
	private IProjectImageDao projectImageDao;

	private HorizontalCarousel carousel;

	@Override
	public void init() {
		title = new Label(LanguageCodes.PROJECT_CAPTION.translation());
		title.setStyleName(CSS_PAGE_TITLE);

		getContentLayout().addComponent(title);
		getContentLayout().addComponent(createCarousel());
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
			carousel.addComponent(getImage(image));
		}

		// Add default image if no images.
		if (images.isEmpty()) {
			carousel.addComponent(getImage("noimages.png"));
		}
	}

	private Image getImage(String resourceName) {
		StreamSource imageSource = new DatabaseImageResource(resourceName);

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, "gallery_image.png");

		// Create an image component that gets its contents from the resource.
		return new Image("", resource);
	}

	private Image getImage(ProjectImage image) {
		// Create an instance of our stream source.
		StreamSource imageSource = new DatabaseImageResource(image);

		// Create a resource that uses the stream source
		StreamResource resource = new StreamResource(imageSource, "gallery_image.png");

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
		addImagesToCarousel();
	}

}
