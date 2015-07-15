package com.biit.gitgamesh.persistence.dao;

import java.util.List;

import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;

public interface IProjectImageDao extends IJpaGenericDao<ProjectFile, Long> {

	public List<ProjectFile> getAll(PrinterProject project);
}
