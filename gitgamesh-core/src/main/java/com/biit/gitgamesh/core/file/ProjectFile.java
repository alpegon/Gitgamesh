package com.biit.gitgamesh.core.file;

import java.sql.Timestamp;

public class ProjectFile {

	private String name;
	private Timestamp creationTime = null;
	private Timestamp updateTime = null;

	public ProjectFile() {
		creationTime = new java.sql.Timestamp(new java.util.Date().getTime());
	}

	public Timestamp getCreationTime() {
		if (creationTime != null) {
			return creationTime;
		} else {
			creationTime = new java.sql.Timestamp(new java.util.Date().getTime());
			return creationTime;
		}
	}

	public void setUpdateTime() {
		setUpdateTime(new java.sql.Timestamp(new java.util.Date().getTime()));
	}

	public Timestamp getUpdateTime() {
		if (updateTime != null) {
			return updateTime;
		} else {
			updateTime = new java.sql.Timestamp(new java.util.Date().getTime());
			return updateTime;
		}
	}

	public void setUpdateTime(Timestamp dateUpdated) {
		updateTime = dateUpdated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
