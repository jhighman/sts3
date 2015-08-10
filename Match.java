package com.infozen.idx.matchmaker;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

class Match {

	// -10 ignore - a value is null or space
	// 0 - no match - value is null
	// 5 - partial match
	// 8 - two part match
	// 10 - exact match

	public int compare(String left, String right) {
		int result = 0;
		if (isNullOrSpace(left)) {
			return -10;
		}
		if (isNullOrSpace(right)) {
			return -10;
		}
		if (isExactMatch(left, right)) {
			return 10;
		}
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
		if (isName(left) && isName(right)) {
			if (countNameMatches(left.toUpperCase(), right.toUpperCase()) > 1) {
				return 8;
			}
			if (countNameMatches(right.toUpperCase(), left.toUpperCase()) > 1) {
				return 8;
			}
			if (countNameMatches(left.toUpperCase(), right.toUpperCase()) == 1) {
				return 5;
			}
			if (countNameMatches(right.toUpperCase(), left.toUpperCase()) == 1) {
				return 5;
			}
		}
		
		if(isDate(left) && isDate(right)){
			if (countDateMatches(left, right) > 2) {
				return 8;
			}
			if (countDateMatches(right, left) > 2) {
				return 8;
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

	
	public boolean isDate(String string){
		boolean result = false;
		if (string.trim().length() < 11) {
			if (containsNumeric(string)) {
				return true;
			}
		}
		return result;
	}
	
	
	public int countDateMatches(String left, String right) {
		int count = 0;
		String leftNumbers[] = null;
		if(containsDashes(left)){
			int i = countDashes(left);
			leftNumbers = left.split("[-]+");
		} else if (containsSlashes(left)){
			int i = countSlashes(left);
			leftNumbers = left.split("[/]+");
		} else {
			if(isNumeric(left)){
				leftNumbers =left.split(" ");	
			} else {
				leftNumbers =left.split(" ");
			}
		}
		for (int i = 0; i < leftNumbers.length; i++) {
			if (right.contains(leftNumbers[i])) {
				count++;
			}
		}
		return count;
	}
	
	
	public boolean isName(String string) {
		boolean result = false;
		if (!containsNumeric(string)) {
			result = true;
		}
		return result;
	}

	public int countNameMatches(String left, String right) {
		String nameParts[] = left.split("[ ]+");
		int count = 0;
		for (int i = 0; i < nameParts.length; i++) {
			if (right.contains(nameParts[i].toUpperCase())) {
				count++;
			}
		}
		return count;
	}

	public boolean compareLastDigits(String left, String right) {
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

	public boolean compareFirstTwoDigits(String left, String right) {
		boolean result = false;
		if (left != null) {
			String leftNumbers[] = left.split("[-]+");
			String rightNumbers[] = left.split("[-]+");
			// check size of index

			if (leftNumbers[0].equals(rightNumbers[0]) && leftNumbers[1].equals(rightNumbers[1])) {
				return true;
			}
		}
		return result;
	}

	public int howManyNumericParts4SSN(String string) {
		String contiguousNumbers[] = string.split("[-]+");
		int count = 0;
		for (int i = 0; i < contiguousNumbers.length; i++) {
			if (isNumeric(contiguousNumbers[i])) {
				count++;
			}
		}
		return count;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public boolean containsNumeric(String string) {
		boolean result = false;
		if (string != null) {
			if (string.matches(".*[0-9].*")) {
				return true;
			}
		}
		return result;
	}

	public boolean containsDashes(String string) {
		boolean result = false;
		if (string != null) {
			if (string.contains("-")) {
				return true;
			}
		}
		return result;
	}
	
	public boolean containsSlashes(String string) {
		boolean result = false;
		if (string != null) {
			if (string.contains("/")) {
				return true;
			}
		}
		return result;
	}

	private int countDashes(String string) {
		return string.length() - string.replace("-", "").length();
	}

	private int countSlashes(String string) {
		return string.length() - string.replace("/", "").length();
	}
	// XXX-XX-XXXX
	public boolean isSSN(String string) {
		boolean result = false;
		if (containsNumeric(string) && containsDashes(string)) {
			if (string.length() > 10) {
				if (countDashes(string) == 2) {
					return true;
				}
			}
		}

		return result;
	}

	private boolean isExactMatch(String left, String right) {
		boolean result = false;
		if (left.toUpperCase().trim().equals(right.toUpperCase().trim())) {
			return true;
		}
		return result;
	}

	private boolean isNullOrSpace(String string) {
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

}
