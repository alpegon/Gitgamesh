package com.biit.gitgamesh.gui.localization;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.biit.gitgamesh.gui.authentication.UserSessionHandler;
import com.biit.gitgamesh.gui.utils.MessageManager;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.vaadin.server.Page;
@Scope("singleton")
@Component()
public class LocalizationManager {

	@Autowired
	private transient ApplicationContext applicationContext;

	private static Locale getLocale() {
		if (UserSessionHandler.getCurrent().getUser() != null) {
			return UserSessionHandler.getCurrent().getUser().getLocale();
		} else {
			if (Page.getCurrent() != null) {
				return Page.getCurrent().getWebBrowser().getLocale();
			} else {
				return null;
			}
		}
	}
	
	public String translate(ILanguageCode code, String ... args){
		if(code == null){
			return null;
		}
		return translate(code.getCode(),args);
	}
	
	private String translationException (String code, String ... args) {
		return applicationContext.getMessage(code, args, getLocale());
	}

	private String translate(String code, String ... args) {
		try{
			return translationException(code,args);
		}catch (RuntimeException e) {
			GitgameshLogger.errorMessage(LocalizationManager.class.getName(), e);
			try {
				MessageManager.showError(translationException("error.fatal"));
			} catch (RuntimeException e2) {
				MessageManager.showError("Fatal error in the translations.");
			}
			return "No translation.";
		}
	}

}