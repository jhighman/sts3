package me;


public interface MatchIdentifierService {
	
	public enum MatchTarget {
        SSN,
        DATE,
        NAME,
        ADDRESS
    }
	
	
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
	 * 5  Partial Match High -  For a date this means two elements of the date match.  For a name this means that one name matches
	 * 3  Partial Match - For a date, the year matches
	 * 0  No match
	 */
	int compare(String left, String right);
	
	
	/**
	 * @param left - The first string to be compared
	 * @param right - The second string to be compared
	 * @param matchTarget - The target domain of both left and right (more efficient than a general match)
	 * @return values are:
	 * 
	 * -10 The compare is ignored - This happens when one of the strings is empty or null, or if there is unxpected data
	 * 10 Exact match - This is a perfect match, undisputable
	 * 9  High probability match - This is a match that very close to an exact match
	 * 5  Partial Match High -  For a date this means two elements of the date match.  For a name this means that one name matches
	 * 3  Partial Match - For a date, the year matches
	 * 0  No match
	 */
	int compare(String left, String right, MatchTarget matchTarget);

}
