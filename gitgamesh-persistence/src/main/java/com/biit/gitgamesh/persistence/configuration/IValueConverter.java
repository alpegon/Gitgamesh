package com.biit.gitgamesh.persistence.configuration;

public abstract interface IValueConverter <T>{

	public T convertFromString(String value);
	
	public String convertToString(Object value);
	
}
