package com.sineshore.charts.ibapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeManager {

	private static final SimpleDateFormat TWS_FORMAT = new SimpleDateFormat("yyyyMMdd  HH:mm:ss");
	private static final SimpleDateFormat MIN_SEC = new SimpleDateFormat("mm:ss");
	private static final SimpleDateFormat HOUR_MIN_SEC = new SimpleDateFormat("HH:mm:ss");

	public static long seconds(String date) {
		try {
			return TWS_FORMAT.parse(date).getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String minSec(long date) {
		return MIN_SEC.format(new Date(date * 1000));
	}

	public static String hourMinSec(long date) {
		return HOUR_MIN_SEC.format(new Date(date * 1000));
	}

}
