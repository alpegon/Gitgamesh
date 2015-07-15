package com.biit.gitgamesh.persistence.dao;

import java.util.List;

import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectImage;

public interface IProjectImageDao extends IJpaGenericDao<ProjectImage, Long> {

	public List<ProjectImage> getAll(PrinterProject project);
}
