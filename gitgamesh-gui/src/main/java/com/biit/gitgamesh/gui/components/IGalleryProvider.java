package com.biit.gitgamesh.gui.components;

import java.util.List;

import com.vaadin.ui.Component;

public interface IGalleryProvider {

	int getNumberOfElements();

	List<Component> getElements(int startElement, int numberOfElements, GalleryOrder galleryOrder, String filterByName,
			List<String> tags);

}
