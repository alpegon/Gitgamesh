package com.biit.gitgamesh;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * Defines basic log behavior. Uses log4j.properties.
 */
public class GitgameshLogger {

	private static Logger logger = Logger.getLogger(GitgameshLogger.class);

	/**
	 * Events that have business meaning (i.e. creating category, deleting form, ...). To follow user actions.
	 */
	public static void info(String className, String message) {
		info(logger, className, message);
	}

	/**
	 * Shows not critical errors. I.e. Email address not found, permissions not allowed for this user, ...
	 * 
	 * @param message
	 */
	public static void warning(String className, String message) {
		warning(logger, className, message);
	}

	/**
	 * For following the trace of the execution. I.e. Knowing if the application access to a method, opening database
	 * connection, etc.
	 */
	public static void debug(String className, String message) {
		debug(logger, className, message);
	}

	/**
	 * To log any not expected error that can cause application malfunction.
	 * 
	 * @param message
	 */
	public static void severe(String className, String message) {
		severe(logger, className, message);
	}

	/**
	 * To log java exceptions and log also the stack trace. If enabled, also can send an email to the administrator to
	 * alert of the error.
	 * 
	 * @param className
	 * @param throwable
	 */
	public static void errorMessage(String className, Throwable throwable) {
		errorMessageNotification(logger, className, getStackTrace(throwable));
	}

	/**
	 * To log java exceptions and log also the stack trace. If enabled, also can send an email to the administrator to
	 * alert of the error.
	 * 
	 * @param className
	 * @param error
	 */
	public static void errorMessage(String className, String error) {
		errorMessageNotification(logger, className, error);
	}
	
	/**
	 * Shows not critical errors. I.e. Email address not found, permissions not allowed for this user, ...
	 * 
	 * @param message
	 */
	public static void warning(Logger logger, String message) {
		logger.warn(message);
	}
	
	/**
	 * Shows not critical errors. I.e. Email address not found, permissions not allowed for this user, ...
	 * 
	 * @param message
	 */
	public static void warning(Logger logger, String className, String message) {
		logger.warn(className+": "+message);
	}
	
	/**
	 * Events that have business meaning (i.e. creating category, deleting form, ...). To follow user actions.
	 * 
	 * @param message
	 */
	public static void info(Logger logger, String message){
		logger.info(message);
	}
	
	/**
	 * Events that have business meaning (i.e. creating category, deleting form, ...). To follow user actions.
	 * 
	 * @param message
	 */
	public static void info(Logger logger, String className, String message){
		info(logger,className+": "+message);
	}
	
	/**
	 * For following the trace of the execution. I.e. Knowing if the application access to a method, opening database
	 * connection, etc.
	 * 
	 * @param message
	 */
	public static void debug(Logger logger, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}
	
	/**
	 * For following the trace of the execution. I.e. Knowing if the application access to a method, opening database
	 * connection, etc.
	 * 
	 * @param message
	 */
	public static void debug(Logger logger, String className, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(className+": "+message);
		}
	}
	
	/**
	 * To log any not expected error that can cause application malfunctions. I.e. couldn't open database connection,
	 * etc..
	 * 
	 * @param message
	 */
	protected static void severe(Logger logger, String message) {
		logger.error(message);
	}
	
	/**
	 * To log any not expected error that can cause application malfunctions.
	 * 
	 * @param message
	 */
	public static void severe(Logger logger, String className, String message) {
		severe(logger, className + ": " + message);
	}
	
	/**
	 * Logs an error and send an email to the email configured in settings.conf file.
	 * 
	 * @param className
	 * @param error
	 */
	public static void errorMessageNotification(Logger logger, String className, String error) {
		severe(logger, className, error);
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}
