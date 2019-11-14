package com.sineshore.charts.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

	private Calendar calendar;
	private SimpleDateFormat format;

	public Date(long milliseconds) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		format = new SimpleDateFormat("y-M-d");
	}

	public void add(Time time) {
		calendar.setTimeInMillis(calendar.getTimeInMillis() + time.milliseconds);
	}

	public void fromString(String text) {
		try {
			calendar.setTime(format.parse(text));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return format.format(new java.util.Date(calendar.getTimeInMillis()));
	}

}
