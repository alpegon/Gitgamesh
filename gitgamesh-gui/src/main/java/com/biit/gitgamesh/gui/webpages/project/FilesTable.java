package com.biit.gitgamesh.gui.webpages.project;

import java.sql.Timestamp;

import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.persistence.entity.ProjectFile;
import com.biit.gitgamesh.utils.DateManager;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class FilesTable extends Table {
	private static final long serialVersionUID = -7302644510273739920L;

	public enum TableColumn {
		FILE_NAME, FILE_CREATION_TIME, FILE_UPDATE_TIME;
	};

	public FilesTable() {
		initContainerProperties();
	}

	private void initContainerProperties() {
		setSelectable(true);
		setImmediate(true);
		setMultiSelect(false);
		setNullSelectionAllowed(false);
		setSizeFull();

		addContainerProperty(TableColumn.FILE_NAME, String.class, "");
		addContainerProperty(TableColumn.FILE_CREATION_TIME, String.class, "");
		addContainerProperty(TableColumn.FILE_UPDATE_TIME, String.class, "");

		setColumnHeader(TableColumn.FILE_NAME, LanguageCodes.FILE_TABLE_HEADER_NAME.translation());
		setColumnHeader(TableColumn.FILE_CREATION_TIME, LanguageCodes.FILE_TABLE_HEADER_CREATION_TIME.translation());
		setColumnHeader(TableColumn.FILE_UPDATE_TIME, LanguageCodes.FILE_TABLE_HEADER_UPDATE_TIME.translation());

		setColumnCollapsingAllowed(true);
		setColumnCollapsed(TableColumn.FILE_NAME, false);
		setColumnCollapsed(TableColumn.FILE_CREATION_TIME, false);
		setColumnCollapsed(TableColumn.FILE_UPDATE_TIME, false);

		setColumnExpandRatio(TableColumn.FILE_NAME, 1.0f);
		setColumnExpandRatio(TableColumn.FILE_CREATION_TIME, 1.0f);
		setColumnExpandRatio(TableColumn.FILE_UPDATE_TIME, 1.0f);
		
		setVisibleColumns(new Object[]{TableColumn.FILE_NAME});

		setSortContainerPropertyId(TableColumn.FILE_NAME);
	}

	public void addRow(ProjectFile file) {
		if (file != null) {
			Item item = getItem(file);
			if (item == null) {
				item = addItem(file);
			}
			setRow(file, item);
		}
		sort();
	}

	@SuppressWarnings("unchecked")
	private void setRow(ProjectFile file, Item item) {
		if (file != null) {
			// Paint columns.
			item.getItemProperty(TableColumn.FILE_NAME).setValue(file.getFileName());

			try {
				item.getItemProperty(TableColumn.FILE_CREATION_TIME).setValue(
						DateManager.convertDateToStringWithHours(new Timestamp(file.getCreationTime().getTime())));
			} catch (NullPointerException npe) {

			}
			try {
				item.getItemProperty(TableColumn.FILE_UPDATE_TIME).setValue(
						DateManager.convertDateToStringWithHours(new Timestamp(file.getUpdateTime().getTime())));
			} catch (NullPointerException npe) {

			}
		}
	}

	public void removeSelectedRow() {
		Object row = getValue();
		if (row != null) {
			removeItem(row);
		}
	}

	public void updateRow(ProjectFile file) {
		Item item = getItem(file);
		// Update element if exists.
		if (item != null) {
			setRow(file, item);
			sort();
		}
	}

}
