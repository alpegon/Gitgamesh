package com.biit.gitgamesh.persistence.dao.exceptions;

public class ElementCannotBePersistedException extends Exception {
	private static final long serialVersionUID = 5021238847444479860L;

	public ElementCannotBePersistedException(String message) {
		super(message);
	}
}
