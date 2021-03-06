package com.biit.gitgamesh.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.biit.gitgamesh.persistence.entity.exceptions.PreviewTooLongException;

/**
 * This is a project definition. Here a user defines tags for classifying and will be shown with the photos on the
 * gallery.
 */
@Entity
@Table(name = "printerproject")
public class PrinterProject extends BaseStorableObject {
	private static final long serialVersionUID = -9076295271241701369L;

	// 0.5mb
	private static final int MAX_PREVIEW_FILE_SIZE = 512 * 1024;

	public static final int MAX_DESCRIPTION_LENGTH = 2000;

	private String name;

	@Column(length = MAX_DESCRIPTION_LENGTH)
	private String description;

	@ElementCollection
	@CollectionTable(name = "printerproject_tags")
	private Set<String> tags;

	@ElementCollection
	@CollectionTable(name = "printerproject_categories")
	private Set<String> categories;

	@Lob
	@Column(length = MAX_PREVIEW_FILE_SIZE)
	@Basic(fetch = FetchType.LAZY)
	private byte[] preview;

	// Users that like this project.
	private int likes = 0;
	// Size of the project in bytes.
	private float size = 0;
	// Material composition of the project.
	private int filamentsColors = 1;
	// Cost in material (in grams).
	private int filamentsQuantity = 1;
	// Downloaded times.
	private int downloaded = 0;
	// Time needed to print this model (minutes).
	private int timeToDo = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	private PrinterProject clonnedFromProject = null;

	public PrinterProject() {
		super();
		tags = new HashSet<>();
		categories = new HashSet<>();
		preview = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags.clear();
		this.tags.addAll(tags);
	}

	public byte[] getPreview() {
		return preview;
	}

	public void setPreview(byte[] preview) throws PreviewTooLongException {
		if (preview.length > MAX_PREVIEW_FILE_SIZE) {
			throw new PreviewTooLongException("Image size is '" + preview.length
					+ "' and max allowed size for a preview is '" + MAX_PREVIEW_FILE_SIZE + "'.");
		}
		this.preview = preview;
	}

	@Override
	public String toString() {
		return name;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories.clear();
		this.categories.addAll(categories);
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public int getFilamentsColors() {
		return filamentsColors;
	}

	public void setFilamentsColors(int filamentsColors) {
		this.filamentsColors = filamentsColors;
	}

	public int getFilamentsQuantity() {
		return filamentsQuantity;
	}

	public void setFilamentsQuantity(int filamentsQuatity) {
		this.filamentsQuantity = filamentsQuatity;
	}

	public int getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(int downloaded) {
		this.downloaded = downloaded;
	}

	public int getTimeToDo() {
		return timeToDo;
	}

	public void setTimeToDo(int timetodo) {
		this.timeToDo = timetodo;
	}

	@Override
	public void copyData(BaseStorableObject object) {
		if (object instanceof PrinterProject) {
			super.copyData(object);
			setName(((PrinterProject) object).getName());
			setDescription(((PrinterProject) object).getDescription());
			setTags(((PrinterProject) object).getTags());
			setCategories(((PrinterProject) object).getCategories());
			if (((PrinterProject) object).getPreview() != null) {
				preview = ((PrinterProject) object).getPreview().clone();
			}
			setLikes(((PrinterProject) object).getLikes());
			setSize(((PrinterProject) object).getSize());
			setFilamentsColors(((PrinterProject) object).getFilamentsColors());
			setFilamentsQuantity(((PrinterProject) object).getFilamentsQuantity());
			setDownloaded(((PrinterProject) object).getDownloaded());
			setTimeToDo(((PrinterProject) object).getTimeToDo());
			setClonnedFromProject(((PrinterProject) object).getClonnedFromProject());
		}
	}

	public PrinterProject getClonnedFromProject() {
		return clonnedFromProject;
	}

	public void setClonnedFromProject(PrinterProject clonnedFromProject) {
		this.clonnedFromProject = clonnedFromProject;
	}
}
