package com.biit.gitgamesh.gui.components;

import java.util.List;

import com.vaadin.ui.Component;

public interface IGalleryProvider {

	List<Component> getElements(int startElement, int numberOfElements, GalleryOrder galleryOrder);

	int getNumberOfElements();

}
