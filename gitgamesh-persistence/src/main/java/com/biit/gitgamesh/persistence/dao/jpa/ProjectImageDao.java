package com.biit.gitgamesh.persistence.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.biit.gitgamesh.persistence.dao.IProjectImageDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;
import com.biit.gitgamesh.persistence.entity.ProjectImage;

@Repository
public class ProjectImageDao extends AnnotatedGenericDao<ProjectImage, Long> implements IProjectImageDao {

	public ProjectImageDao() {
		super(ProjectImage.class);
	}

	@Override
	public List<ProjectImage> getAll(PrinterProject printerProject) {
		if (printerProject == null) {
			return new ArrayList<ProjectImage>();
		}
		// Get the criteria builder instance from entity manager
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		// Create criteria query and pass the value object which needs to be populated as result
		CriteriaQuery<ProjectImage> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		// Tell to criteria query which tables/entities you want to fetch
		Root<ProjectImage> projectImage = criteriaQuery.from(getEntityClass());

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
			return new ArrayList<ProjectImage>();
		}
	}

}
