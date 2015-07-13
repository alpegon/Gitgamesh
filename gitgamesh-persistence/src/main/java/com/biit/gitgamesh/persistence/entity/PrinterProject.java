package com.biit.gitgamesh.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * This is a project definition. Here a user defines tags for classifying and will be shown with the photos on the
 * gallery.
 */
@Entity
@Table(name = "PrinterProject")
public class PrinterProject extends BaseStorableObject {
	private static final long serialVersionUID = -9076295271241701369L;

	// 0.5mb
	private static final int MAX_PREVIEW_FILE_SIZE = 512 * 1024;

	private String name;

	private String description;

	@ElementCollection
	private Set<String> tags;

	@Lob
	@Column(length = MAX_PREVIEW_FILE_SIZE, nullable = false)
	@Basic(fetch = FetchType.LAZY)
	private byte[] preview;

	public PrinterProject() {
		super();
		tags = new HashSet<>();
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

	public void setPreview(byte[] preview) {
		this.preview = preview;
	}

}
