package com.biit.gitgamesh.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {
	public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";

	public static String convertDateToString(Date date, String dateFormat) {
		if (date == null) {
			return new SimpleDateFormat(dateFormat).format(new Timestamp(new java.util.Date(0).getTime()));
		}
		return new SimpleDateFormat(dateFormat).format(date);
	}

	public static String convertDateToString(Timestamp timestamp, String dateFormat) {
		if (timestamp == null) {
			return new SimpleDateFormat(dateFormat).format(new Timestamp(0));
		}
		return new SimpleDateFormat(dateFormat).format(timestamp);
	}

	public static String convertDateToStringWithHours(Timestamp time) {
		if (time == null) {
			return "";
		}
		Date date = new Date(time.getTime());
		return convertDateToString(date, DATE_FORMAT);
	}

	public static String convertDateToString(Timestamp time) {
		Date date = new Date(time.getTime());
		return convertDateToString(date, DATE_FORMAT_SIMPLE);
	}

	public static Date incrementDateOneDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1); // number of days to add
		return c.getTime();
	}

	public static Timestamp convertToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

}
