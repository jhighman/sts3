package me;


public class MatchIdentifierGeneralizedHelper {

	public static boolean containsSlashes(String string) {
		boolean result = false;
		if (string != null) {
			if (string.contains("/")) {
				return true;
			}
		}
		return result;
	}

	public static boolean containsDashes(String string) {
		boolean result = false;
		if (string != null) {
			if (string.contains("-")) {
				return true;
			}
		}
		return result;
	}

	public static int countSlashes(String string) {
		return string.length() - string.replace("/", "").length();
	}

	public static int countDashes(String string) {
		return string.length() - string.replace("-", "").length();
	}

	public static boolean containsNumeric(String string) {
		boolean result = false;
		if (string != null) {
			if (string.matches(".*[0-9].*")) {
				return true;
			}
		}
		return result;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean isNullOrSpace(String string) {
		boolean result = true;
		if (string == null) {
		} else {
			if (string.length() < 1) {
			} else {
				result = false;
			}
		}
		return result;
	}

	public static boolean isExactMatch(String left, String right) {
		boolean result = false;
		if (left.toUpperCase().trim().equals(right.toUpperCase().trim())) {
			return true;
		}
		return result;
	}

}
