package com.biit.gitgamesh.gui.webpages.gallery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.components.GalleryComponent;
import com.biit.gitgamesh.gui.components.IGalleryElementClickListener;
import com.biit.gitgamesh.gui.components.IGalleryProvider;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.webpages.Project;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;
import com.biit.gitgamesh.persistence.dao.jpa.GalleryOrder;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

@UIScope
@SpringComponent
public class GalleryView extends GitgameshCommonView<IGalleryView, IGalleryPresenter> implements IGalleryView {
	private static final long serialVersionUID = -3493109786988382122L;

	private static final String CSS_INSERTED_GALLERY = "inserted-gallery";
	private static final String CSS_GALLEY_CONTENT_AREA = "gallery-content-area";

	@Autowired
	private IPrinterProjectDao projectDao;

	private GalleryComponent gallery;

	public GalleryView() {
		super();
	}

	@Override
	public void init() {
		getContentLayout().addComponent(createTitle(LanguageCodes.GITGAMESH_CAPTION.translation()));
		getContentLayout().setHeight(FULL);
		getContentLayout().addStyleName(CSS_GALLEY_CONTENT_AREA);

		gallery = new GalleryComponent(9, new IGalleryProvider() {

			@Override
			public int getNumberOfElements() {
				return projectDao.getRowCount();
			}

			@Override
			public List<Component> getElements(int startElement, int numberOfElements, GalleryOrder galleryOrder,
					String filterByName) {
				List<PrinterProject> projects = projectDao.get(startElement, numberOfElements, galleryOrder,
						filterByName, filterByName, filterByName, filterByName);
				List<Component> components = new ArrayList<>();
				for (PrinterProject project : projects) {
					PrinterProjectGalleryElement element = new PrinterProjectGalleryElement(project);
					element.addElementClickListener(new IGalleryElementClickListener() {

						@Override
						public void clickedElement(Object element) {
							PrinterProject project = (PrinterProject) element;
							GitgameshUi.navigateTo(Project.NAME + "/" + project.getId());
						}
					});
					components.add(element);

				}

				return components;
			}
		});

		gallery.setWidth(FULL);
		gallery.setHeightUndefined();
		gallery.addStyleName(CSS_INSERTED_GALLERY);
		getContentLayout().addComponent(gallery);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		selectButton(getGallery());
		gallery.clear();
		gallery.actionLoad();
	}

}
