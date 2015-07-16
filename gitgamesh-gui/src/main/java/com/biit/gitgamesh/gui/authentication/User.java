/**
 * user.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.biit.gitgamesh.gui.authentication;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class User implements IUser, Serializable {
	private static final long serialVersionUID = -6134421097495636111L;

	private Timestamp createDate;

	private boolean defaultUser;

	private java.lang.String emailAddress;

	private int failedLoginAttempts;

	private java.lang.String firstName;

	private java.lang.String languageId;

	private Timestamp lastFailedLoginDate;

	private Timestamp lastLoginDate;

	private java.lang.String lastLoginIP;

	private java.lang.String lastName;

	private boolean lockout;

	private Timestamp lockoutDate;

	private Timestamp loginDate;

	private java.lang.String loginIP;

	private java.lang.String middleName;

	private Timestamp modifiedDate;

	private java.lang.String password;

	private boolean passwordEncrypted;

	private Timestamp passwordModifiedDate;

	private boolean passwordReset;

	private long primaryKey;

	private java.lang.String screenName;

	private java.lang.String timeZoneId;

	private long userId;

	private java.lang.String uuid;

	// This information in Liferay is in the Contact object.
	private Date birthday;

	private boolean male;

	public User() {
	}

	public User(Timestamp createDate, boolean defaultUser, java.lang.String emailAddress, int failedLoginAttempts,
			java.lang.String firstName, java.lang.String languageId, Timestamp lastFailedLoginDate,
			Timestamp lastLoginDate, java.lang.String lastLoginIP, java.lang.String lastName, boolean lockout,
			Timestamp lockoutDate, Timestamp loginDate, java.lang.String loginIP, java.lang.String middleName,
			Timestamp modifiedDate, java.lang.String password, boolean passwordEncrypted,
			Timestamp passwordModifiedDate, boolean passwordReset, long primaryKey, java.lang.String screenName,
			java.lang.String timeZoneId, long userId, java.lang.String uuid) {
		this.createDate = createDate;
		this.defaultUser = defaultUser;
		this.emailAddress = emailAddress;
		this.failedLoginAttempts = failedLoginAttempts;
		this.firstName = firstName;
		this.languageId = languageId;
		this.lastFailedLoginDate = lastFailedLoginDate;
		this.lastLoginDate = lastLoginDate;
		this.lastLoginIP = lastLoginIP;
		this.lastName = lastName;
		this.lockout = lockout;
		this.lockoutDate = lockoutDate;
		this.loginDate = loginDate;
		this.loginIP = loginIP;
		this.middleName = middleName;
		this.modifiedDate = modifiedDate;
		this.password = password;
		this.passwordEncrypted = passwordEncrypted;
		this.passwordModifiedDate = passwordModifiedDate;
		this.passwordReset = passwordReset;
		this.primaryKey = primaryKey;
		this.screenName = screenName;
		this.timeZoneId = timeZoneId;
		this.userId = userId;
		this.uuid = uuid;
	}

	/**
	 * Gets the createDate value for this user.
	 * 
	 * @return createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the createDate value for this user.
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the defaultUser value for this user.
	 * 
	 * @return defaultUser
	 */
	public boolean isDefaultUser() {
		return defaultUser;
	}

	/**
	 * Sets the defaultUser value for this user.
	 * 
	 * @param defaultUser
	 */
	public void setDefaultUser(boolean defaultUser) {
		this.defaultUser = defaultUser;
	}

	/**
	 * Gets the emailAddress value for this user.
	 * 
	 * @return emailAddress
	 */
	public java.lang.String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the emailAddress value for this user.
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(java.lang.String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the failedLoginAttempts value for this user.
	 * 
	 * @return failedLoginAttempts
	 */
	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	/**
	 * Sets the failedLoginAttempts value for this user.
	 * 
	 * @param failedLoginAttempts
	 */
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	/**
	 * Gets the firstName value for this user.
	 * 
	 * @return firstName
	 */
	public java.lang.String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName value for this user.
	 * 
	 * @param firstName
	 */
	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the languageId value for this user.
	 * 
	 * @return languageId
	 */
	public java.lang.String getLanguageId() {
		return languageId;
	}

	/**
	 * Sets the languageId value for this user.
	 * 
	 * @param languageId
	 */
	public void setLanguageId(java.lang.String languageId) {
		this.languageId = languageId;
	}

	/**
	 * Gets the lastFailedLoginDate value for this user.
	 * 
	 * @return lastFailedLoginDate
	 */
	public Timestamp getLastFailedLoginDate() {
		return lastFailedLoginDate;
	}

	/**
	 * Sets the lastFailedLoginDate value for this user.
	 * 
	 * @param lastFailedLoginDate
	 */
	public void setLastFailedLoginDate(Timestamp lastFailedLoginDate) {
		this.lastFailedLoginDate = lastFailedLoginDate;
	}

	/**
	 * Gets the lastLoginDate value for this user.
	 * 
	 * @return lastLoginDate
	 */
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * Sets the lastLoginDate value for this user.
	 * 
	 * @param lastLoginDate
	 */
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Gets the lastLoginIP value for this user.
	 * 
	 * @return lastLoginIP
	 */
	public java.lang.String getLastLoginIP() {
		return lastLoginIP;
	}

	/**
	 * Sets the lastLoginIP value for this user.
	 * 
	 * @param lastLoginIP
	 */
	public void setLastLoginIP(java.lang.String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	/**
	 * Gets the lastName value for this user.
	 * 
	 * @return lastName
	 */
	public java.lang.String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName value for this user.
	 * 
	 * @param lastName
	 */
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the lockout value for this user.
	 * 
	 * @return lockout
	 */
	public boolean isLockout() {
		return lockout;
	}

	/**
	 * Sets the lockout value for this user.
	 * 
	 * @param lockout
	 */
	public void setLockout(boolean lockout) {
		this.lockout = lockout;
	}

	/**
	 * Gets the lockoutDate value for this user.
	 * 
	 * @return lockoutDate
	 */
	public Timestamp getLockoutDate() {
		return lockoutDate;
	}

	/**
	 * Sets the lockoutDate value for this user.
	 * 
	 * @param lockoutDate
	 */
	public void setLockoutDate(Timestamp lockoutDate) {
		this.lockoutDate = lockoutDate;
	}

	/**
	 * Gets the loginDate value for this user.
	 * 
	 * @return loginDate
	 */
	public Timestamp getLoginDate() {
		return loginDate;
	}

	/**
	 * Sets the loginDate value for this user.
	 * 
	 * @param loginDate
	 */
	public void setLoginDate(Timestamp loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * Gets the loginIP value for this user.
	 * 
	 * @return loginIP
	 */
	public java.lang.String getLoginIP() {
		return loginIP;
	}

	/**
	 * Sets the loginIP value for this user.
	 * 
	 * @param loginIP
	 */
	public void setLoginIP(java.lang.String loginIP) {
		this.loginIP = loginIP;
	}

	/**
	 * Gets the middleName value for this user.
	 * 
	 * @return middleName
	 */
	public java.lang.String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middleName value for this user.
	 * 
	 * @param middleName
	 */
	public void setMiddleName(java.lang.String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets the modifiedDate value for this user.
	 * 
	 * @return modifiedDate
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * Sets the modifiedDate value for this user.
	 * 
	 * @param modifiedDate
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * Gets the password value for this user.
	 * 
	 * @return password
	 */
	public java.lang.String getPassword() {
		return password;
	}

	/**
	 * Sets the password value for this user.
	 * 
	 * @param password
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	/**
	 * Gets the passwordEncrypted value for this user.
	 * 
	 * @return passwordEncrypted
	 */
	public boolean isPasswordEncrypted() {
		return passwordEncrypted;
	}

	/**
	 * Sets the passwordEncrypted value for this user.
	 * 
	 * @param passwordEncrypted
	 */
	public void setPasswordEncrypted(boolean passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}

	/**
	 * Gets the passwordModifiedDate value for this user.
	 * 
	 * @return passwordModifiedDate
	 */
	public Timestamp getPasswordModifiedDate() {
		return passwordModifiedDate;
	}

	/**
	 * Sets the passwordModifiedDate value for this user.
	 * 
	 * @param passwordModifiedDate
	 */
	public void setPasswordModifiedDate(Timestamp passwordModifiedDate) {
		this.passwordModifiedDate = passwordModifiedDate;
	}

	/**
	 * Gets the passwordReset value for this user.
	 * 
	 * @return passwordReset
	 */
	public boolean isPasswordReset() {
		return passwordReset;
	}

	/**
	 * Sets the passwordReset value for this user.
	 * 
	 * @param passwordReset
	 */
	public void setPasswordReset(boolean passwordReset) {
		this.passwordReset = passwordReset;
	}

	/**
	 * Gets the primaryKey value for this user.
	 * 
	 * @return primaryKey
	 */
	public long getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Sets the primaryKey value for this user.
	 * 
	 * @param primaryKey
	 */
	public void setPrimaryKey(long primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * Gets the screenName value for this user.
	 * 
	 * @return screenName
	 */
	@Override
	public java.lang.String getScreenName() {
		return screenName;
	}

	/**
	 * Sets the screenName value for this user.
	 * 
	 * @param screenName
	 */
	public void setScreenName(java.lang.String screenName) {
		this.screenName = screenName;
	}

	/**
	 * Gets the timeZoneId value for this user.
	 * 
	 * @return timeZoneId
	 */
	public java.lang.String getTimeZoneId() {
		return timeZoneId;
	}

	/**
	 * Sets the timeZoneId value for this user.
	 * 
	 * @param timeZoneId
	 */
	public void setTimeZoneId(java.lang.String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	/**
	 * Gets the userId value for this user.
	 * 
	 * @return userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Sets the userId value for this user.
	 * 
	 * @param userId
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the uuid value for this user.
	 * 
	 * @return uuid
	 */
	public java.lang.String getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid value for this user.
	 * 
	 * @param uuid
	 */
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the user birthday.
	 * 
	 * @return
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Sets the user birthday.
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	/**
	 * Returns the number of years that a user has.
	 * 
	 * @return
	 */
	public int getAge() {
		Calendar dob = Calendar.getInstance();
		dob.setTime(birthday);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
			age--;
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				&& today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
			age--;
		}
		return age;
	}

	public Locale getLocale() {
		return Locale.forLanguageTag(getLanguageId().replace("_", "-"));
	}

	@Override
	public String toString() {
		return screenName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	/**
	 * Equals by id. Necessary to unlock forms.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String getUserName() {
		return this.getFirstName() + " " + this.getLastName();
	}

}
