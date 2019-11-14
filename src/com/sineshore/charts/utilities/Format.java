package com.sineshore.charts.utilities;

import java.text.DecimalFormat;

public class Format {

	private static final DecimalFormat DECIMAL = new DecimalFormat("#0.00000");

	public static final String format(double value) {
		return DECIMAL.format(value);
	}

}
