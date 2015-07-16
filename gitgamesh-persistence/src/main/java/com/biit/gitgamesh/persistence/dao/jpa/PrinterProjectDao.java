package com.biit.gitgamesh.persistence.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
			String filterByName, String tag, String category, String userName) {
		// Get the criteria builder instance from entity manager
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		// Create criteria query and pass the value object which needs to be populated as result
		CriteriaQuery<PrinterProject> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		// Tell to criteria query which tables/entities you want to fetch
		Root<PrinterProject> printerProjectRoot = criteriaQuery.from(getEntityClass());

		// Apply a filter to the sql.
		List<Predicate> predicates = new ArrayList<Predicate>();
		// Search name criteria
		if (filterByName != null && filterByName.length() > 0) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(printerProjectRoot.<String> get("name")), "%"
					+ filterByName.toLowerCase() + "%"));
		}
		// Search by user.
		if (userName != null && userName.length() > 0) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(printerProjectRoot.<String> get("createdBy")),
					"%" + userName.toLowerCase() + "%"));
		}

		// Select tags.
		if (tag != null) {
			// TODO must be use a LIKE not IN!!
			// predicates.add(printerProjectRoot.join("tags").in(tag));
			predicates.add(printerProjectRoot.join("tags", JoinType.LEFT).in(tag));
		}

		// Select categories.
		if (category != null) {
			predicates.add(printerProjectRoot.join("categories", JoinType.LEFT).in(category));
		}

		if (!predicates.isEmpty()) {
			criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[] {})));
		}

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
				criteriaQuery.orderBy(criteriaBuilder.desc(printerProjectRoot.get("updateTime")));
				break;
			}
		}
		
		criteriaQuery.distinct(true);

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
