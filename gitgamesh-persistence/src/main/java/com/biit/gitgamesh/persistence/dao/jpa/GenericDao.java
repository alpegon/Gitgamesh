package com.biit.gitgamesh.persistence.dao.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.biit.gitgamesh.persistence.dao.IJpaGenericDao;
import com.biit.gitgamesh.persistence.dao.exceptions.ElementCannotBeRemovedException;
import com.biit.gitgamesh.persistence.entity.BaseStorableObject;

public abstract class GenericDao<EntityClass extends BaseStorableObject, PrimaryKeyClass extends Serializable>
		implements IJpaGenericDao<EntityClass, PrimaryKeyClass> {

	protected Class<EntityClass> entityClass;

	@Override
	public abstract EntityManager getEntityManager();

	public GenericDao(Class<EntityClass> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public EntityClass makePersistent(EntityClass entity) {
		if (entity == null) {
			throw new NullPointerException();
		}

		getEntityManager().persist(entity);
		// We force a flush due to in some cases a bidirectional relationships needs a @ManyToOne(optional = false) to
		// perform an orphan removals. But without the flush, the optional causes an exception due to the element is set
		// to null.
		// http://stackoverflow.com/questions/3068817/hibernate-triggering-constraint-violations-using-orphanremoval
		getEntityManager().flush();

		return entity;
	}

	@Override
	public EntityClass merge(EntityClass entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		EntityClass managedEntity = getEntityManager().merge(entity);
		getEntityManager().flush();
		return managedEntity;
	}

	@Override
	public void makeTransient(EntityClass entity) throws ElementCannotBeRemovedException {
		getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
	}

	@Override
	public EntityClass get(PrimaryKeyClass id) {
		return getEntityManager().find(getEntityClass(), id);
	}

	@Override
	public int getRowCount() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<EntityClass> root = query.from(getEntityClass());

		query.select(cb.count(root));
		return getEntityManager().createQuery(query).getSingleResult().intValue();
	}

	@Override
	public List<EntityClass> getAll() {
		CriteriaQuery<EntityClass> query = getEntityManager().getCriteriaBuilder().createQuery(getEntityClass());
		query.select(query.from(getEntityClass()));
		try {
			return getEntityManager().createQuery(query).getResultList();
		} catch (NoResultException nre) {
			return new ArrayList<EntityClass>();
		}
	}

	@Override
	public void evictAllCache() {
		getEntityManager().getEntityManagerFactory().getCache().evictAll();
	}

	public void evictCache() {
		EntityManagerFactory factory = getEntityManager().getEntityManagerFactory();
		Cache cache = factory.getCache();
		cache.evict(getEntityClass());
	}

	public Class<EntityClass> getEntityClass() {
		return entityClass;
	}
}
