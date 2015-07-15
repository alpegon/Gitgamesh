package com.biit.gitgamesh.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "printerProject")
	private PrinterProject printerProject;

	public ProjectImage() {
		super();
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public PrinterProject getPrinterProject() {
		return printerProject;
	}

	public void setPrinterProject(PrinterProject printerProject) {
		this.printerProject = printerProject;
	}

}
