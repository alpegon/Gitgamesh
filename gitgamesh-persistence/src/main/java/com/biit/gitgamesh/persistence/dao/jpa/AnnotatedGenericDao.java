package com.biit.gitgamesh.persistence.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.biit.gitgamesh.persistence.dao.IJpaGenericDao;
import com.biit.gitgamesh.persistence.dao.exceptions.ElementCannotBeRemovedException;
import com.biit.gitgamesh.persistence.entity.BaseStorableObject;

public class AnnotatedGenericDao<EntityClass extends BaseStorableObject, PrimaryKeyClass extends Serializable> extends
		GenericDao<EntityClass, PrimaryKeyClass> implements IJpaGenericDao<EntityClass, PrimaryKeyClass> {

	// PersistenceContextType.EXTENDED needed for using Lazy Loading in Vaadin.
	// http://stackoverflow.com/questions/7977547/vaadin-jpa-lazy-loading
	@PersistenceContext(unitName = "defaultPersistenceUnit", type = PersistenceContextType.EXTENDED)
	@Qualifier(value = "gitgameshProjectManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public AnnotatedGenericDao(Class<EntityClass> entityClass) {
		super(entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public EntityClass makePersistent(EntityClass entity) {
		return super.makePersistent(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public void makeTransient(EntityClass entity) throws ElementCannotBeRemovedException {
		super.makeTransient(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public EntityClass get(PrimaryKeyClass id) {
		return super.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
	public int getRowCount() {
		return super.getRowCount();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
	public List<EntityClass> getAll() {
		return super.getAll();
	}

	@Override
	public void evictAllCache() {
		getEntityManager().clear();
		super.evictAllCache();
	}

	@Transactional
	public void clearHibernateCache() {
		SessionFactory sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
		sessionFactory.getCache().evictEntityRegions();
		sessionFactory.getCache().evictCollectionRegions();
		sessionFactory.getCache().evictDefaultQueryRegion();
		sessionFactory.getCache().evictQueryRegions();
	}

}
