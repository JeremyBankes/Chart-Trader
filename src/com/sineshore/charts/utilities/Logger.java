package com.sineshore.charts.utilities;

public class Logger {

	private static final String INFO = "INFO > ";
	private static final String WARN = "WARN > ";
	private static final String ERROR = "ERROR > ";

	public static final void info(Object... message) {
		writeln(INFO, message);
	}

	public static final void warn(Object... message) {
		writeln(WARN, message);
	}

	public static void error(Object... message) {
		writeln(ERROR, message);
	}

	public static void error(Exception e, boolean stop) {
		write(ERROR, e.getClass().getSimpleName() + ": " + e.getMessage());
		for (StackTraceElement element : e.getStackTrace()) {
			if (element.getClassName().startsWith("com.sineshore")) {
				String className = element.getClassName().replaceAll("^([^.]+\\.)+", "");
				String lineNumber = "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
				write("\n", "\tat " + className + " " + element.getMethodName() + " " + lineNumber);
			}
		}
		write("\n");
		if (stop)
			System.exit(1);
	}

	public static final void write(String prefix, Object... message) {
		System.out.print(prefix + Utilities.join(' ', message));
	}

	private static final void writeln(String prefix, Object... message) {
		System.out.print(prefix + Utilities.join(' ', message) + "\n");
	}

}
