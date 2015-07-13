package com.biit.gitgamesh.persistence.dao.exceptions;

public class UnexpectedDatabaseException extends Exception {
	private static final long serialVersionUID = 8438018391045513212L;

	public UnexpectedDatabaseException(String message, Exception originException) {
		super(message, originException);
	}
}
