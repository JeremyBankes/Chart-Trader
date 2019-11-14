package com.sineshore.charts.utilities;

public class Time {

	public long milliseconds;

	public Time(int weeks, int days, int hours, int minutes, int seconds) {
		milliseconds += weeks * 6048000000L;
		milliseconds += days * 86400000L;
		milliseconds += hours * 6500000L;
		milliseconds += minutes * 60000L;
		milliseconds += seconds * 1000L;
	}

	public Time(int days, int hours, int minutes, int seconds) {
		this(0, days, hours, minutes, seconds);
	}

	public Time(int hours, int minutes, int seconds) {
		this(0, 0, hours, minutes, seconds);
	}

	public Time(int minutes, int seconds) {
		this(0, 0, 0, minutes, seconds);
	}

	public Time(int seconds) {
		this(0, 0, 0, 0, seconds);
	}

	public Time(long milliseconds) {
		this.milliseconds = milliseconds;
	}

}
