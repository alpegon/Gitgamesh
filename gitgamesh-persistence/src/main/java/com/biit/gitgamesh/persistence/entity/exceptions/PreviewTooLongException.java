package com.biit.gitgamesh.persistence.entity.exceptions;

public class PreviewTooLongException extends Exception {
	private static final long serialVersionUID = -1384794333636398110L;

	public PreviewTooLongException(String message) {
		super(message);
	}
}
