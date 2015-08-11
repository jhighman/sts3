package me;


public class MatchIdentifierSSNHelper {

	public static int compareSSNs(String left, String right) {
		int result = 0;
		if (isSSN(left) && isSSN(right)) {
			if (howManyNumericParts4SSN(right) == 1) {
				if (compareLastDigits(left, right)) {
					return 5;
				}
			}
			if (howManyNumericParts4SSN(right) == 2) {
				if (compareFirstTwoDigits(left, right)) {
					return 5;
				}
			}
		}
		return result;
	}

	public static boolean compareLastDigits(String left, String right) {
		boolean result = false;
		if (left != null) {
			String leftNumbers[] = left.split("[0-9]+");
			String lastFourLeft = leftNumbers[leftNumbers.length - 1];
			if (right.contains(lastFourLeft)) {
				return true;
			}
		}
		return result;
	}

	public static boolean compareFirstTwoDigits(String left, String right) {
		boolean result = false;
		if (left != null) {
			String leftNumbers[] = left.split("[-]+");
			String rightNumbers[] = left.split("[-]+");
			// check size of index
	
			if (leftNumbers[0].equals(rightNumbers[0])
					&& leftNumbers[1].equals(rightNumbers[1])) {
				return true;
			}
		}
		return result;
	}

	public static int howManyNumericParts4SSN(String string) {
		String contiguousNumbers[] = string.split("[-]+");
		int count = 0;
		for (int i = 0; i < contiguousNumbers.length; i++) {
			if (MatchIdentifierGeneralizedHelper.isNumeric(contiguousNumbers[i])) {
				count++;
			}
		}
		return count;
	}

	// XXX-XX-XXXX
	public static boolean isSSN(String string) {
		boolean result = false;
		if (MatchIdentifierGeneralizedHelper.containsNumeric(string) && MatchIdentifierGeneralizedHelper.containsDashes(string)) {
			if (string.length() > 10) {
				if (MatchIdentifierGeneralizedHelper.countDashes(string) == 2) {
					return true;
				}
			}
		}
	
		return result;
	}

}
