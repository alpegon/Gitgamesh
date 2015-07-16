package com.biit.gitgamesh.persistence.dao;

import java.util.List;

import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;

public interface IProjectFileDao extends IJpaGenericDao<ProjectFile, Long> {

	/**
	 * Get all files for a project stored in the database.
	 * 
	 * @param project
	 * @return
	 */
	public List<ProjectFile> getAll(PrinterProject project);
}
