package com.biit.gitgamesh.gui.components;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public abstract class GalleryElement<T> extends CustomComponent {
	private static final long serialVersionUID = -4466018678230579243L;

	private static final String CSS_GALLERY_ELEMENT = "gallery-element";
	private static final String CSS_GALLERY_ELEMENT_AUTHOR = "gallery-element-author";
	private static final String CSS_GALLERY_ELEMENT_DESCRIPTION = "gallery-element-description";
	private static final String CSS_GALLERY_ELEMENT_TITLE = "gallery-element-title";
	private static final String CSS_GALLERY_ELEMENT_UPDATE = "gallery-element-update";
	private static final String CSS_GALLERY_ELEMENT_CONTENT = "gallery-element-content";
	private static final String CSS_GALLERY_ELEMENT_LEFT_CONTENT = "gallery-element-left-content";
	private static final String CSS_GALLERY_ELEMENT_RIGHT_CONTENT = "gallery-element-right-content";

	private static final String FULL = "100%";
	private static final String LEFT_CONTENT_WIDTH = "80px";
	private static final String THUMBNAIL_WIDTH = "60px";
	private static final String THUMBNAIL_HEIGHT = "60px";

	private final CssLayout rootLayout;
	private final CssLayout contentLayout;
	private final CssLayout leftContent;
	private final CssLayout rightContent;

	private final Label title;
	private final Label description;
	private final Label update;
	private final Label author;

	private final T element;

	private final List<IGalleryElementClickListener> listeners;

	public GalleryElement(T element) {
		super();
		this.element = element;
		this.listeners = new ArrayList<>();

		setStyleName(CSS_GALLERY_ELEMENT);

		rootLayout = new CssLayout();
		rootLayout.setSizeFull();
		rootLayout.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 5444838130947486394L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				fireListeners();
			}
		});

		title = new Label();
		title.setStyleName(CSS_GALLERY_ELEMENT_TITLE);
		title.setValue(getName());
		title.setWidth(FULL);

		rootLayout.addComponent(title);

		contentLayout = new CssLayout();
		contentLayout.setSizeFull();
		contentLayout.setStyleName(CSS_GALLERY_ELEMENT_CONTENT);

		leftContent = new CssLayout();
		leftContent.setStyleName(CSS_GALLERY_ELEMENT_LEFT_CONTENT);
		leftContent.setWidth(LEFT_CONTENT_WIDTH);
		leftContent.setHeight(FULL);

		Resource resource = getPreviewResource();
		Image thumbnail = new Image(null, resource);
		thumbnail.setWidth(THUMBNAIL_WIDTH);
		thumbnail.setWidth(THUMBNAIL_HEIGHT);

		leftContent.addComponent(thumbnail);

		rightContent = new CssLayout();
		rightContent.setStyleName(CSS_GALLERY_ELEMENT_RIGHT_CONTENT);
		rightContent.setWidth(FULL);

		author = new Label();
		author.setValue("by " + getUserName());
		author.setStyleName(CSS_GALLERY_ELEMENT_AUTHOR);

		rightContent.addComponent(author);

		description = new Label();
		description.setValue(getElementDescription());
		description.setStyleName(CSS_GALLERY_ELEMENT_DESCRIPTION);
		rightContent.addComponent(description);

		contentLayout.addComponent(leftContent);
		contentLayout.addComponent(rightContent);

		rootLayout.addComponent(contentLayout);

		update = new Label();
		update.setStyleName(CSS_GALLERY_ELEMENT_UPDATE);
		update.setValue("Updated: " + getUpdateTime());
		update.setWidth(FULL);

		rootLayout.addComponent(update);

		setCompositionRoot(rootLayout);
	}

	protected void fireListeners() {
		for (IGalleryElementClickListener listener : listeners) {
			listener.clickedElement(getElement());
		}
	}

	public void addElementClickListener(IGalleryElementClickListener listener) {
		listeners.add(listener);
	}
	
	public void removeElementClickListener(IGalleryElementClickListener listener) {
		listeners.remove(listener);
	}

	protected abstract Resource getPreviewResource();

	protected abstract String getElementDescription();

	protected abstract Timestamp getUpdateTime();

	protected abstract String getUserName();

	protected abstract String getName();

	public T getElement() {
		return element;
	}
}
