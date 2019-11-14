package com.sineshore.charts.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Utilities {

	public static final char[] WHITESPACE_CHARACTERS = { ' ', '_' };

	// Text

	public static final String join(String seperator, Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0, len = objects.length; i < len; i++) {
			if (i != 0)
				builder.append(seperator);
			builder.append(objects[i]);
		}
		return builder.toString();
	}

	public static final String join(char seperator, Object... objects) {
		return join(String.valueOf(seperator), objects);
	}

	public static final String beautify(String message) {
		char[] characters = message.toLowerCase().replaceAll(" +", " ").toCharArray();
		characters[0] = Character.toUpperCase(characters[0]);
		for (int i = 0, len = characters.length; i < len; i++)
			if (i != 0)
				if (arrayContains(WHITESPACE_CHARACTERS, characters[i - 1]))
					characters[i] = Character.toUpperCase(characters[i]);
		return new String(characters);
	}

	public static final String beautify(Enum<?> enumeration) {
		return beautify(enumeration.name().replace('_', ' '));
	}

	// Number

	public static final boolean isShort(Object object) {
		try {
			Short.parseShort(object.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static final boolean isInt(Object object) {
		try {
			Integer.parseInt(object.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static final boolean isLong(Object object) {
		try {
			Long.parseLong(object.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static final boolean isFloat(Object object) {
		try {
			Float.parseFloat(object.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static final boolean isDouble(Object object) {
		try {
			Double.parseDouble(object.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static final short asShort(Object object) {
		return isShort(object) ? Short.parseShort(object.toString()) : -1;
	}

	public static final int asInt(Object object) {
		return isInt(object) ? Integer.parseInt(object.toString()) : -1;
	}

	public static final long asLong(Object object) {
		return isLong(object) ? Long.parseLong(object.toString()) : -1;
	}

	public static final float asFloat(Object object) {
		return isFloat(object) ? Float.parseFloat(object.toString()) : -1;
	}

	public static final double asDouble(Object object) {
		return isDouble(object) ? Double.parseDouble(object.toString()) : -1;
	}

	public static final double clamp(double min, double max, double value) {
		return Math.min(max, Math.max(min, value));
	}

	public static final boolean inBoundary(double value, double start, double range, boolean equals) {
		if (equals)
			return value >= start && value <= start + range;
		return value > start & value < start + range;
	}

	public static final boolean inBoundary(double value, double start, double range) {
		return inBoundary(value, start, range, false);
	}

	// Array

	public static final Object[] convert(ArrayList<Object> arrayList) {
		return arrayList.toArray();
	}

	public static final <T> ArrayList<T> convert(T[] array) {
		return new ArrayList<>(Arrays.asList(array));
	}

	public static final <T> boolean arrayContains(T[] array, T value) {
		for (T object : array)
			if (object == value || object.equals(value))
				return true;
		return false;
	}

	public static final boolean arrayContains(char[] array, char value) {
		for (int object : array)
			if (object == value)
				return true;
		return false;
	}

	public static final boolean arrayContains(int[] array, int value) {
		for (int object : array)
			if (object == value)
				return true;
		return false;
	}

	public static final boolean arrayContains(float[] array, float value) {
		for (float object : array)
			if (object == value)
				return true;
		return false;
	}

	public static final boolean arrayContains(double[] array, double value) {
		for (double object : array)
			if (object == value)
				return true;
		return false;
	}

	// Random

	public static final Random RANDOM = new Random();

	public static final Color randColor(boolean alpha) {
		return new Color(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat(), alpha ? RANDOM.nextFloat() : 1f);
	}

	// Color

	public static final Color tinge(Color source, float red, float green, float blue, float percentage) {
		float r = asFloat(source.getRed()) / 255;
		float g = asFloat(source.getGreen()) / 255;
		float b = asFloat(source.getBlue()) / 255;

		r += (red / 255 - r) * percentage;
		g += (green / 255 - g) * percentage;
		b += (blue / 255 - b) * percentage;

		r = (float) clamp(0, 1, r);
		g = (float) clamp(0, 1, g);
		b = (float) clamp(0, 1, b);

		return new Color(r, g, b);
	}

	public static final Color tinge(Color source, Color tint, float percentage) {
		return tinge(source, asFloat(tint.getRed()) / 255, asFloat(tint.getGreen()) / 255, asFloat(tint.getBlue()) / 255, percentage);
	}

	public static final Color invert(Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}

}
