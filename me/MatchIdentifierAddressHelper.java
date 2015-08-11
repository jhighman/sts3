package me;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class MatchIdentifierAddressHelper {
	
	static Pattern zipPattern = Pattern.compile("(\\d{5})");
	
	//we capture the optional street number starting at the first position in the string, trailed by a space we do not capture:
	static Pattern streetNumberPattern = Pattern.compile("^((?<StreetNumber>[0-9]*)(?: ))*");
	
	
	public static int compareAddresses(String left, String right) {
		int result = 0;
		double wt = StringUtils.getJaroWinklerDistance(left, right);
		if (wt < .9) {return 9;}
		if(isAddress(left) && isAddress(right)){
			
			int count = 0;
			if(getZipCode(left).equals(getZipCode(right))){
				count ++;
			}
			if(getStreetNumber(left).equals(getStreetNumber(right))){
				count ++;
			}
			
			if(count == 2){return 5;}
			
			if(count == 2){return 3;}
		}
		
		return result;
	}

	public static boolean isAddress(String string){
		boolean result = false;
		
		if (MatchIdentifierGeneralizedHelper.containsNumeric(string)){
			if(hasStreetNumber(string) && hasZipCode(string)){
				result = true;	
			}
		}	
		return result;
	}
	
	
	public static boolean hasStreetNumber(String string){
		boolean result = false;
		Matcher streetNumberMatcher = streetNumberPattern.matcher(string);
		if (streetNumberMatcher.find()) {
		    result = true;
		}
		return result;
	}
	
	
	public static String getStreetNumber(String string){
		String result = "";
		Matcher streetNumberMatcher = streetNumberPattern.matcher(string);
		if (streetNumberMatcher.find()) {
			result = streetNumberMatcher.group(1).trim();
		}
		return result;
	}
	
	
	public static  boolean hasZipCode(String string){
		boolean result = false;
		Matcher zipMatcher = zipPattern.matcher(string);
		if (zipMatcher.find()) {
		    result = true;
		}
		return result;
	}
	
	public static  String getZipCode(String string){
		String result = "";
		Matcher zipMatcher = zipPattern.matcher(string);
		if (zipMatcher.find()) {
			result = zipMatcher.group(1);
		}
		return result;
	}
	
}
