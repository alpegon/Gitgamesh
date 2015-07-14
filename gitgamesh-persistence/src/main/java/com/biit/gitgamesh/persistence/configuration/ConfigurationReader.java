package com.biit.gitgamesh.persistence.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.persistence.configuration.exception.PropertyNotFoundException;

public class ConfigurationReader {

	private final HashMap<Class<?>, IValueConverter<?>> converter;
	private final HashMap<String, String> propertiesDefault;
	private final HashMap<String, String> properties;
	private final List<IPropertiesSource> propertiesSources;

	public ConfigurationReader() {
		converter = new HashMap<Class<?>, IValueConverter<?>>();
		propertiesDefault = new HashMap<String, String>();
		properties = new HashMap<String, String>();
		propertiesSources = new ArrayList<IPropertiesSource>();

		addConverter(Boolean.class, new BooleanValueConverter());
		addConverter(Integer.class, new IntegerValueConverter());
		addConverter(Double.class, new DoubleValueConverter());
	}

	public <T> void addConverter(Class<T> clazz, IValueConverter<T> valueConverter) {
		converter.put(clazz, valueConverter);
	}

	@SuppressWarnings("unchecked")
	public <T> IValueConverter<T> getConverter(Class<T> clazz) {
		return (IValueConverter<T>) converter.get(clazz);
	}

	public void addPropertiesSource(IPropertiesSource propertiesSource) {
		propertiesSources.add(propertiesSource);
	}

	/**
	 * Restarts all properties to their default values and then reads all the
	 * configuration files again.
	 */
	public void readConfigurations() {
		properties.clear();
		properties.putAll(propertiesDefault);

		for (IPropertiesSource propertiesSource : propertiesSources) {
			Properties propertyFile;
			propertyFile = propertiesSource.loadFile();
			if (propertyFile != null) {
				readAllProperties(propertyFile);
			}
		}
	}

	/**
	 * Reads all properties configured in this configuration reader from
	 * propertyFile. If they doesn't exist, then the current value is mantained
	 * as default value.
	 * 
	 * @param propertyFile
	 */
	private void readAllProperties(Properties propertyFile) {
		Set<String> propertyIds = new HashSet<String>(properties.keySet());
		for (String propertyId : propertyIds) {
			String value = propertyFile.getProperty(propertyId, properties.get(propertyId));
			properties.put(propertyId, value);
			GitgameshLogger.debug(this.getClass().getName(), "Property '" + propertyId + "' set as value '" + value
					+ "'.");
		}
	}

	/**
	 * Adds a property
	 * 
	 * @param propertyName
	 * @param defaultValue
	 */
	public <T> void addProperty(String propertyName, T defaultValue) {
		if (defaultValue == null) {
			propertiesDefault.put(propertyName, null);
			properties.put(propertyName, null);
		} else if (defaultValue instanceof String) {
			propertiesDefault.put(propertyName, new String((String) defaultValue).trim());
			properties.put(propertyName, new String((String) defaultValue).trim());
		} else {
			propertiesDefault.put(propertyName, getConverter(defaultValue.getClass()).convertToString(defaultValue));
			properties.put(propertyName, getConverter(defaultValue.getClass()).convertToString(defaultValue));
		}
	}

	public String getProperty(String propertyName) throws PropertyNotFoundException {
		if (properties.containsKey(propertyName)) {
			if (properties.get(propertyName) != null) {
				return new String(properties.get(propertyName).trim());
			} else {
				return null;
			}
		} else {
			throw new PropertyNotFoundException("Property not defined in the configuration reader");
		}
	}

	public <T> T getProperty(String propertyName, Class<? extends T> type) throws PropertyNotFoundException {
		String stringValue = getProperty(propertyName);
		if (stringValue != null) {
			return getConverter(type).convertFromString(stringValue);
		} else {
			return null;
		}
	}

	public List<IPropertiesSource> getPropertiesSources() {
		return propertiesSources;
	}
}
