package com.biit.gitgamesh.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.biit.gitgamesh.utils.IdGenerator;

/**
 * Base StorableObject class. This class holds all the basic storable object information doesn't have the copy data
 * methods and thus doesn't require that it's child classes implement it. This base class is used as in the USMO
 * project.
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseStorableObject implements Serializable {
	private static final long serialVersionUID = 1861734314986978986L;
	public static final int MAX_UNIQUE_COLUMN_LENGTH = 190;
	public static final int HASH_CODE_SEED = 31;

	// GenerationType.Table stores into hibernate_sequence the name of the table
	// as a VARCHAR(255) when using
	// "hibernate.id.new_generator_mappings" property. If using utf8mb4, the
	// VARCHAR(255) needs 1000 bytes, that this is
	// forbidden due to MySQL only allows a max of 767 bytes in a unique key. If
	// "hibernate.id.new_generator_mappings"
	// is not set, GenerationType.AUTO causes Cannot use identity column key
	// generation with <union-subclass> error.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(nullable = false)
	private Timestamp creationTime = null;

	protected String createdBy = null;

	private Timestamp updateTime = null;

	// A unique Id created with the object used to compare persisted objects and
	// in memory objects.
	// MySQL unique keys are limited to 767 bytes that in utf8mb4 are ~190.
	@Column(unique = true, nullable = false, updatable = false, length = MAX_UNIQUE_COLUMN_LENGTH)
	private String comparationId;

	public BaseStorableObject() {
		creationTime = new java.sql.Timestamp(new java.util.Date().getTime());
		comparationId = IdGenerator.createId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreationTime() {
		setCreationTime(new java.sql.Timestamp(new java.util.Date().getTime()));
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

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreationTime(Timestamp dateCreated) {
		creationTime = dateCreated;
	}

	public void setUpdateTime(Timestamp dateUpdated) {
		updateTime = dateUpdated;
	}

	@Override
	public int hashCode() {
		final int prime = HASH_CODE_SEED;
		int result = 1;
		result = (prime * result) + ((getComparationId() == null) ? 0 : getComparationId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!this.getClass().isInstance(obj)) {
			return false;
		}

		BaseStorableObject other = (BaseStorableObject) obj;
		if (getComparationId() == null) {
			if (other.getComparationId() != null) {
				return false;
			}
		} else if (!getComparationId().equals(other.getComparationId())) {
			return false;
		}
		return true;
	}

	/**
	 * Reset 'id' and 'comparationId'
	 */
	public void resetIds() {
		setId(null);
		comparationId = IdGenerator.createId();
	}

	/**
	 * This method must not be used directly.
	 * 
	 * @param comparationId
	 */
	public synchronized void setComparationId(String comparationId) {
		this.comparationId = comparationId;
	}

	public synchronized String getComparationId() {
		return comparationId;
	}

	protected void copyData(BaseStorableObject object) {
		setCreatedBy(object.getCreatedBy());
		setId(object.getId());
		setComparationId(object.getComparationId());
		setCreationTime(object.getCreationTime());
		setUpdateTime(object.getUpdateTime());
	}

}
