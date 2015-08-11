package me;


public class MatchIdentifierDateHelper {

	public static int countDateMatches(String left, String right) {
		int count = 0;
		String leftNumbers[] = null;
		if (MatchIdentifierGeneralizedHelper.containsDashes(left)) {
			int i = MatchIdentifierGeneralizedHelper.countDashes(left);
			leftNumbers = left.split("[-]+");
		} else if (MatchIdentifierGeneralizedHelper.containsSlashes(left)) {
			int i = MatchIdentifierGeneralizedHelper.countSlashes(left);
			leftNumbers = left.split("[/]+");
		} else {
			if (MatchIdentifierGeneralizedHelper.isNumeric(left)) {
				leftNumbers = left.split(" ");
			} else {
				leftNumbers = left.split(" ");
			}
		}
		for (int i = 0; i < leftNumbers.length; i++) {
			if (right.contains(leftNumbers[i])) {
				count++;
			}
		}
		return count;
	}

	public static int compareDates(String left, String right) {
		int result = 0;
		if (isDate(left) && isDate(right)) {
			if (countDateMatches(left, right) > 2) {
				return 9;
			}
			if (countDateMatches(right, left) > 2) {
				return 9;
			}
			if (countDateMatches(left, right) > 1) {
				return 5;
			}
			if (countDateMatches(right, left) > 1) {
				return 5;
			}
			if (countDateMatches(left, right) == 1) {
				return 3;
			}
			if (countDateMatches(right, left) == 1) {
				return 3;
			}
		}
	
		return result;
	
	}

	public static boolean isDate(String string) {
		boolean result = false;
		if (string.trim().length() < 11) {
			if (MatchIdentifierGeneralizedHelper.containsNumeric(string)) {
				return true;
			}
		}
		return result;
	}

}
