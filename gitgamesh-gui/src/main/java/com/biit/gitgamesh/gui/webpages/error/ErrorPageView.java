package com.biit.gitgamesh.gui.webpages.error;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.theme.IThemeResource;
import com.biit.gitgamesh.gui.webpages.common.VerticalWebPage;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * View for error pages it is composed of a centered layout with a image and a
 * text to describe the error.
 *
 */
public class ErrorPageView extends VerticalWebPage<IErrorPageView, IErrorPagePresenter> implements IErrorPageView {
	private static final long serialVersionUID = 1367001730512808692L;
	private static final String TEXT_WIDTH = "600px";
	private static final String FULL = "100%";
	private static final String IMAGE_LAYOUT_HEIGHT = "300px";
	private static final String IMAGE_WIDTH = "80px";
	private static final String IMAGE_HEIGHT = "80px";

	private final VerticalLayout messageLayout;
	private final Label label;
	private final Image image;

	public ErrorPageView() {
		super();
		messageLayout = new VerticalLayout();
		label = new Label();
		image = new Image();
	}

	@Override
	public void init() {
		messageLayout.setWidth(TEXT_WIDTH);
		messageLayout.setHeight(null);
		messageLayout.setSpacing(true);

		image.setWidth(IMAGE_WIDTH);
		image.setHeight(IMAGE_HEIGHT);

		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setWidth(FULL);
		imageLayout.setHeight(IMAGE_LAYOUT_HEIGHT);
		imageLayout.addComponent(image);
		imageLayout.setComponentAlignment(image, Alignment.BOTTOM_LEFT);
		imageLayout.setMargin(new MarginInfo(false, false, true, false));

		messageLayout.addComponent(imageLayout);
		messageLayout.addComponent(label);

		getRootLayout().addComponent(messageLayout);
		getRootLayout().setComponentAlignment(messageLayout, Alignment.TOP_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setLabelContent(getCastedPresenter().getLabel());
		setImageSource(getCastedPresenter().getImageSource());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLabelContent(ILanguageCode content) {
		label.setValue(content.translation());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setImageSource(IThemeResource resource) {
		image.setSource(resource.getThemeResource());
	}

}
