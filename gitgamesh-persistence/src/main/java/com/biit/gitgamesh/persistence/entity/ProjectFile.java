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
@Table(name = "projectfiles")
public class ProjectFile extends BaseStorableObject {
	private static final long serialVersionUID = -1633999193588469899L;

	// 10 mb
	private static final int MAX_FILE_SIZE = 10 * 1024 * 1024;

	@Lob
	@Column(length = MAX_FILE_SIZE, nullable = false)
	@Basic(fetch = FetchType.LAZY)
	private byte[] file;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "printerProject")
	private PrinterProject printerProject;

	private String fileName;

	public ProjectFile() {
		super();
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public PrinterProject getPrinterProject() {
		return printerProject;
	}

	public void setPrinterProject(PrinterProject printerProject) {
		this.printerProject = printerProject;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void copyData(BaseStorableObject object) {
		if (object instanceof ProjectFile) {
			super.copyData(object);
			setFileName(((ProjectFile) object).getFileName());
			setFile(((ProjectFile) object).getFile().clone());
			setPrinterProject(((ProjectFile) object).getPrinterProject());
		}
	}
}
