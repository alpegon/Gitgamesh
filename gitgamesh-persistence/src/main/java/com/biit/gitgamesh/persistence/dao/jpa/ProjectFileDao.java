package com.biit.gitgamesh.persistence.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.biit.gitgamesh.persistence.dao.IProjectFileDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectFile;

@Repository
public class ProjectFileDao extends AnnotatedGenericDao<ProjectFile, Long> implements IProjectFileDao {

	public ProjectFileDao() {
		super(ProjectFile.class);
	}

	@Override
	public List<ProjectFile> getAll(PrinterProject printerProject) {
		if (printerProject == null) {
			return new ArrayList<ProjectFile>();
		}
		// Get the criteria builder instance from entity manager
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		// Create criteria query and pass the value object which needs to be populated as result
		CriteriaQuery<ProjectFile> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		// Tell to criteria query which tables/entities you want to fetch
		Root<ProjectFile> projectImage = criteriaQuery.from(getEntityClass());

		// Apply a filter to the sql.
		List<Predicate> predicates = new ArrayList<Predicate>();
		// Search criteria
		predicates.add(criteriaBuilder.equal(projectImage.get("printerProject"), printerProject));
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));

		// Sort results.
		criteriaQuery.orderBy(criteriaBuilder.asc(projectImage.get("creationTime")));
		try {
			return getEntityManager().createQuery(criteriaQuery).getResultList();
		} catch (NoResultException nre) {
			return new ArrayList<ProjectFile>();
		}
	}

}
