package com.biit.gitgamesh.persistence.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;

@Repository
public class PrinterProjectDao extends AnnotatedGenericDao<PrinterProject, Long> implements IPrinterProjectDao {

	public PrinterProjectDao() {
		super(PrinterProject.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
	public List<PrinterProject> get(int startElement, int numberOfElements, GalleryOrder galleryOrder,
			String filterByName, List<String> tags) {
		// Get the criteria builder instance from entity manager
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		EntityType<PrinterProject> type = getEntityManager().getMetamodel().entity(PrinterProject.class);
		// Create criteria query and pass the value object which needs to be populated as result
		CriteriaQuery<PrinterProject> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		// Tell to criteria query which tables/entities you want to fetch
		Root<PrinterProject> printerProjectRoot = criteriaQuery.from(getEntityClass());

		// Apply a filter to the sql.
		List<Predicate> predicates = new ArrayList<Predicate>();
		// Search criteria
		if (filterByName != null) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(printerProjectRoot.get(type
					.getDeclaredSingularAttribute("name", String.class))), "%" + filterByName.toLowerCase() + "%"));
		}
		if (tags != null && !tags.isEmpty()) {
			// TODO Tags not done yet.
		}

		//
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));

		// Sort results.
		if (galleryOrder != null) {
			switch (galleryOrder) {
			case ALPHABETIC:
				criteriaQuery.orderBy(criteriaBuilder.asc(printerProjectRoot.get("name")));
				break;
			case VALORATION:
				// TODO must be changed to valoration.
				criteriaQuery.orderBy(criteriaBuilder.asc(printerProjectRoot.get("name")));
				break;
			case RECENT:
				criteriaQuery.orderBy(criteriaBuilder.asc(printerProjectRoot.get("updateTime")));
				break;
			}

		}

		try {
			TypedQuery<PrinterProject> query = getEntityManager().createQuery(criteriaQuery);
			query.setFirstResult(startElement);
			query.setMaxResults(numberOfElements);
			return query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
