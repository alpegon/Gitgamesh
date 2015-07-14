package com.biit.gitgamesh.gui.components;

import java.util.List;

import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.persistence.dao.jpa.GalleryOrder;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GalleryComponent extends CustomComponent {
	private static final long serialVersionUID = 8021652096779696573L;

	private static final String CSS_GALLERY_CLASS = "gallery-layout";
	private static final String CSS_ORDER_LAYOUT = "order-layout";
	private static final String CSS_GALLERY_LAYOUT = "galleryLayout";

	private static final String FULL = "100%";

	private static final String CSS_CONTROLS = "grouped-controls";

	private static final String CSS_BUTTON_SELECTED = "selected-button";

	private static final String CSS_GALLERY_FILTER_LAYOUT = "gallery-filter-layout";
	private static final String CSS_GALLERY_FILTER_NAME_FIELD = "gallery-filter-name-field";
	private static final String CSS_GALLERY_CANCEL_NAME_FILTER = "gallery-cancel-name-filter";

	private final VerticalLayout rootLayout;
	private final CssLayout filterLayout;
	private final CssLayout orderLayout;
	private final CssLayout galleryLayout;
	private final Button loadMore;

	private final TextField filterField;
	private final Button cancelFilter;

	private final Label resultMessage;

	private final int numberOfElementsToLoad;
	private final IGalleryProvider provider;
	private GalleryOrder orderType;
	private String nameFilter;

	private Button alphabetical, valoration, recent;

	public GalleryComponent(int numberOfElements, IGalleryProvider provider) {
		super();
		numberOfElementsToLoad = numberOfElements;
		this.provider = provider;
		this.orderType = GalleryOrder.ALPHABETIC;

		rootLayout = new VerticalLayout();
		rootLayout.setStyleName(CSS_GALLERY_CLASS);
		rootLayout.setWidth(FULL);
		rootLayout.setHeightUndefined();
		rootLayout.setMargin(false);
		rootLayout.setSpacing(false);

		setWidth(FULL);
		setHeightUndefined();

		setCompositionRoot(rootLayout);

		filterLayout = generateCssLayout(CSS_GALLERY_FILTER_LAYOUT, FULL, null);

		filterField = new TextField();
		filterField.setWidth("100%");
		filterField.setInputPrompt("Search for projects...");
		filterField.setStyleName(CSS_GALLERY_FILTER_NAME_FIELD);
		filterField.setTextChangeTimeout(300);
		filterField.setTextChangeEventMode(TextChangeEventMode.TIMEOUT);
		filterField.addTextChangeListener(new TextChangeListener() {
			private static final long serialVersionUID = -5737194866974599387L;

			@Override
			public void textChange(TextChangeEvent event) {
				nameFilter = event.getText();
				if (nameFilter.isEmpty()) {
					nameFilter = null;
				}
				clear();
				actionLoad();
			}
		});

		cancelFilter = new Button(ThemeIcon.CANCEL.getThemeResource());
		cancelFilter.setStyleName(CSS_GALLERY_CANCEL_NAME_FILTER);

		filterLayout.addComponent(filterField);
		filterLayout.addComponent(cancelFilter);

		orderLayout = generateCssLayout(CSS_ORDER_LAYOUT, FULL, null);
		resultMessage = new Label();
		resultMessage.setWidth(null);
		initializeOrderLayout();

		galleryLayout = generateCssLayout(CSS_GALLERY_LAYOUT, FULL, null);
		Responsive.makeResponsive(galleryLayout);

		rootLayout.addComponent(filterLayout);
		rootLayout.addComponent(orderLayout);
		rootLayout.addComponent(galleryLayout);

		loadMore = new Button("Load More");
		loadMore.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 5395367584567260339L;

			@Override
			public void buttonClick(ClickEvent event) {
				actionLoad(galleryLayout.getComponentCount());
			}
		});

		rootLayout.addComponent(loadMore);

		initializeData();
	}

	private void initializeData() {
		setOrderType(orderType);
		actionLoad();
	}

	private void initializeOrderLayout() {
		CssLayout buttonLayout = generateCssLayout(CSS_CONTROLS, null, null);

		alphabetical = new Button("A-Z", new ClickListener() {
			private static final long serialVersionUID = -1526666748552447534L;

			@Override
			public void buttonClick(ClickEvent event) {
				setOrderType(GalleryOrder.ALPHABETIC);
				clear();
				actionLoad();
			}
		});
		valoration = new Button("Highest rated", new ClickListener() {
			private static final long serialVersionUID = 5283746720819782701L;

			@Override
			public void buttonClick(ClickEvent event) {
				setOrderType(GalleryOrder.VALORATION);
				clear();
				actionLoad();
			}
		});
		recent = new Button("Most Recent", new ClickListener() {
			private static final long serialVersionUID = -1393124313836611741L;

			@Override
			public void buttonClick(ClickEvent event) {
				setOrderType(GalleryOrder.RECENT);
				clear();
				actionLoad();
			}
		});

		buttonLayout.addComponent(alphabetical);
		buttonLayout.addComponent(valoration);
		buttonLayout.addComponent(recent);

		orderLayout.addComponent(buttonLayout);
		orderLayout.addComponent(resultMessage);

	}

	public void clear() {
		galleryLayout.removeAllComponents();
	}

	public void actionLoad() {
		actionLoad(0);
		resultMessage.setValue("Search Results: (" + provider.getNumberOfElements() + ")");
	}

	protected void actionLoad(int startElement) {
		// We ask to load one element more than needed
		List<Component> elements = provider.getElements(startElement, numberOfElementsToLoad + 1, getOrderType(), nameFilter);
		for (int i = 0; i < elements.size() && i < numberOfElementsToLoad; i++) {
			galleryLayout.addComponent(elements.get(i));
		}
		loadMore.setVisible(elements.size() == (numberOfElementsToLoad + 1));
	}

	private CssLayout generateCssLayout(String styleName, String width, String height) {
		CssLayout layout = new CssLayout();
		layout.setStyleName(styleName);
		layout.setWidth(width);
		layout.setHeight(height);
		return layout;
	}

	public GalleryOrder getOrderType() {
		return orderType;
	}

	public void setOrderType(GalleryOrder orderType) {
		getActiveButton().removeStyleName(CSS_BUTTON_SELECTED);
		this.orderType = orderType;
		getActiveButton().addStyleName(CSS_BUTTON_SELECTED);
	}

	private Button getActiveButton() {
		switch (orderType) {
		case ALPHABETIC:
			return alphabetical;
		case RECENT:
			return recent;
		case VALORATION:
			return valoration;
		}
		// Instead of return null if not found return alphabetical (default
		// value)
		return alphabetical;
	}

}
