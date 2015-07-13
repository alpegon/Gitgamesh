package com.biit.gitgamesh.persistence.dao;

import java.util.List;

import com.biit.gitgamesh.persistence.dao.jpa.GalleryOrder;
import com.biit.gitgamesh.persistence.entity.PrinterProject;

public interface IPrinterProjectDao extends IJpaGenericDao<PrinterProject, Long> {

	/**
	 * Get a list of projects that matches some parameters
	 * 
	 * @param startElement
	 *            only retrieve elements from this starting position.
	 * @param numberOfElements
	 *            only retrieve elements until hits position.
	 * @param galleryOrder
	 *            defines the order of the elements.
	 * @param filterByName
	 *            only get elements that the name is like this.
	 * @param tags
	 *            NOT IMPLEMENTED YET:
	 * @return
	 */
	List<PrinterProject> get(int startElement, int numberOfElements, GalleryOrder galleryOrder, String filterByName,
			String tag, String category, String userName);

}
