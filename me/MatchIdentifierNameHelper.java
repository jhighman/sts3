package me;

import org.apache.commons.lang3.StringUtils;

public class MatchIdentifierNameHelper {

	// REGEX to split
	private final static String REG_COMMA_SPACE = "[\\s,;\\n\\t]+";

	public static boolean isName(String string) {
		boolean result = false;
		if (!MatchIdentifierGeneralizedHelper.containsNumeric(string)) {
			result = true;
		}
		return result;
	}

	public static int countNameMatches(String left, String right) {
		String nameParts[] = left.split("[ ]+");
		int count = 0;
		for (int i = 0; i < nameParts.length; i++) {
			if (right.contains(nameParts[i].toUpperCase())) {
				count++;
			}
		}
		return count;
	}

	public static int compareNames(String left, String right) {
		int result = 0;
		if (isName(left) && isName(right)) {
			double score = getSimilarityWeight(left, right);
			if (score > .9) {
				return 9;
			}
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
		return result;

	}

	/**
	 * Compare to given values and get the similarities by weightage. If the
	 * weight is less than 0.9 then try transposing values
	 * 
	 * @see StringUtils#getJaroWinklerDistance(CharSequence, CharSequence)
	 * 
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static double getSimilarityWeight(String arg1, String arg2) {

		double wt = StringUtils.getJaroWinklerDistance(arg1, arg2);
		System.out.println("Wight on first pass:" + wt);
		// if Weight is less than then try transposing
		if (wt < .9) {

			wt = tranposedweight(arg1, arg2);
			System.out.println("Wight on second pass:" + wt);

		}

		return wt;
	}

	public static double tranposedweight(String arg1, String arg2) {

		double wt = 0.0;
		String[] values1 = arg1.split(REG_COMMA_SPACE);
		String[] values2 = arg2.split(REG_COMMA_SPACE);

		if (values1.length > values2.length) {
			String[] tmp = values1;
			values1 = values2;
			values2 = tmp;
		}
		for (int i = 0; i < values1.length; i++) {
			String n = values1[i];
			for (int j = 0; j < values2.length; j++) {
				String sn = values2[j];
				double weight = 0;
				if (sn.length() == 1 || n.length() == 1) {
					if (n.charAt(0) == sn.charAt(0)) {
						weight = 1.0;
					}
				} else {
					weight = StringUtils.getJaroWinklerDistance(n, sn);
				}

				if (weight > .9) {
					wt += weight;
					break;
				}
			}
		}

		wt = wt / values1.length;
		return wt;

	}

}
