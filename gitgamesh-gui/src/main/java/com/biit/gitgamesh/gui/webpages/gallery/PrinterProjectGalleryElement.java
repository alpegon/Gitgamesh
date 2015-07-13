package com.biit.gitgamesh.gui.webpages.gallery;

import java.sql.Timestamp;

import com.biit.gitgamesh.gui.components.GalleryElement;
import com.biit.gitgamesh.persistence.entity.PrinterProject;

public class PrinterProjectGalleryElement extends GalleryElement<PrinterProject> {
	private static final long serialVersionUID = -1180272951539494732L;

	public PrinterProjectGalleryElement(PrinterProject element) {
		super(element);
	}

	@Override
	protected String getUserName() {
		return getElement().getName();
	}
	
	@Override
	protected Timestamp getUpdateTime() {
		return getElement().getUpdateTime();
	}
	
	@Override
	protected String getName() {
		return getElement().getName();
	}

	@Override
	protected String getElementDescription() {
		return getElement().getDescription();
	}
}