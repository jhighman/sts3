package me;



class MatchIdentifierServiceImpl implements MatchIdentifierService {

	/**
	 * This method takes any two string values and attempts to match them.  It uses pattern matching to determine the domain type.
	 * 
	 * @param left - The first string to be compared
	 * @param right - The second string to be compared
	 * @return values are:
	 * 
	 * -10 The compare is ignored - This happens when one of the strings is empty or null, or if there is unxpected data
	 * 10 Exact match - This is a perfect match, undisputable
	 * 9  High probability match - This is a match that very close to an exact match
	 * 8  Medium-High probability - This is a match that matches all elements, but has different formats 
	 * 5  Partial Match High -  For a date this means two elements of the date match.  For a name this means that one name matches
	 * 3  Partial Match - For a date, the year matches
	 * 0  No match
	 */
	@Override
	public int compare(String left, String right) {
		int result = 0;
		result = compareGeneralized(left, right);
		if (result > 0) {
			return result;
		}
		
		result = MatchIdentifierSSNHelper.compareSSNs(left, right);
		if (result > 0) {
			return result;
		}

		result = MatchIdentifierNameHelper.compareNames(left, right);
		if (result > 0) {
			return result;
		}

		result = MatchIdentifierDateHelper.compareDates(left, right);
		if (result > 0) {
			return result;
		}
		
		result = MatchIdentifierAddressHelper.compareAddresses(left, right);
		if (result > 0) {
			return result;
		}
		return result;
	}

	/**
	 * @param left - The first string to be compared
	 * @param right - The second string to be compared
	 * @param matchTarget - The target domain of both left and right (more efficient than a general match)
	 * @return values are:
	 * 
	 * -10 The compare is ignored - This happens when one of the strings is empty or null, or if there is unxpected data
	 * 10 Exact match - This is a perfect match, undisputable
	 * 9  High probability match - This is a match that very close to an exact match
	 * 8  Medium-High probability - This is a match that matches all elements, but has different formats 
	 * 5  Partial Match High -  For a date this means two elements of the date match.  For a name this means that one name matches
	 * 3  Partial Match - For a date, the year matches
	 * 0  No match
	 */
	@Override
	public int compare(String left, String right, MatchTarget matchTarget) {

		int result = 0;
		result = compareGeneralized(left, right);
		if (result > 0) {
			return result;
		}

		switch (matchTarget) {
		case SSN:
			result = MatchIdentifierSSNHelper.compareSSNs(left, right);
			break;
		case DATE:
			result = MatchIdentifierDateHelper.compareDates(left, right);
			break;
		case NAME:
			result = MatchIdentifierNameHelper.compareNames(left, right);
			break;
		case ADDRESS:
			result = MatchIdentifierAddressHelper.compareAddresses(left, right);
		}

		return result;
	}

	private int compareGeneralized(String left, String right){
		int result = 0;
		if (MatchIdentifierGeneralizedHelper.isNullOrSpace(left)) {
			return -10;
		}
		if (MatchIdentifierGeneralizedHelper.isNullOrSpace(right)) {
			return -10;
		}
		if (MatchIdentifierGeneralizedHelper.isExactMatch(left, right)) {
			return 10;
		}
		return result;
	}
	
	
}
