package com.biit.gitgamesh.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ProjectImages")
public class ProjectImage extends BaseStorableObject {
	private static final long serialVersionUID = -1633999193588469899L;

	// 10 mb
	private static final int MAX_FILE_SIZE = 10 * 1024 * 1024;

	@Lob
	@Column(length = MAX_FILE_SIZE, nullable = false)
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private PrinterProject printerProject;

	public ProjectImage(PrinterProject printerProject) {
		super();
		this.printerProject = printerProject;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
