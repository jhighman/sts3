package com.infozen.idx.matchmaker;

import static org.junit.Assert.fail;

import org.junit.Test;

import org.junit.Assert;
import org.junit.Ignore;

public class TestMatch {

	private Match match = new Match();
	private int score = 0;
	
	
	@Test
	public void testDateMatchScenarios() {
		Assert.assertEquals(5, match.compare("03/1970", "03/11/1970"));
		Assert.assertEquals(8, match.compare("03-11-1970", "03/11/1970"));
		Assert.assertEquals(8, match.compare("03-11-1970", "11/03/1970")); // note the anomaly
		Assert.assertEquals(5, match.compare("03-1970", "03/11/1970"));
		Assert.assertEquals(3, match.compare("1970", "03/11/1970"));
		Assert.assertEquals(3, match.compare("Mar/70", "03/11/1970"));
		Assert.assertEquals(3, match.compare("Mar 70", "03/11/1970"));
		Assert.assertEquals(3, match.compare("03/11/1970", "Mar 70"));
		Assert.assertEquals(3, match.compare("03/11/1970", "Mar/70"));
		Assert.assertEquals(3, match.compare("03/11/1970", "1970"));
	}
	

	@Test
	public void testNameMatchScenarios() {
		Assert.assertEquals(10, match.compare("Jeff Highman", "JEFF HIGHMAN"));
		Assert.assertEquals(8, match.compare("Jeff Highman", "JEFFREY HIGHMAN"));
		Assert.assertEquals(8, match.compare("Jeff Highman", "Highman Jeff"));
		Assert.assertEquals(8, match.compare("Jeff Highman", "Highman Jeffrey A"));
		Assert.assertEquals(8, match.compare("Jeff Highman", "Highman, Jeffrey A"));
		Assert.assertEquals(8, match.compare("Jeff Highman", "Highman, Jeffrey Aaron"));
		Assert.assertEquals(8, match.compare("Jeff Highman", "Jeffrey A Highman"));
		Assert.assertEquals(8, match.compare("Jeff Highman", "J A Highman"));
		Assert.assertEquals(5, match.compare("Jeff Highman", "Harold Highman"));
		Assert.assertEquals(5, match.compare("Jeff Highman", "Jeff Higman"));
	}
	
	
	

	@Test
	public void testSSNMasks() {
		Assert.assertEquals(5, match.compare("254-49-3260", "XXX-XX-3260"));
		Assert.assertEquals(0, match.compare("254-49-3260", "254-44-3260"));
		Assert.assertEquals(5, match.compare("254-49-3260", "254-44-XXXX"));
	}
	
	

	@Test
	public void testNullsAndSptings() {
		Assert.assertEquals(-10, match.compare(null, null));
		Assert.assertEquals(-10, match.compare(null, "x"));
		Assert.assertEquals(-10, match.compare("x", null));
		Assert.assertEquals(-10, match.compare("", ""));
		Assert.assertEquals(-10, match.compare("", "x"));
		Assert.assertEquals(-10, match.compare("x", ""));
	}

	@Test
	public void testExactMatchScenarios() {
		Assert.assertEquals(10, match.compare("Jeff", "JEFF"));
		Assert.assertEquals(10, match.compare("Jeff", "JEFF  "));
		Assert.assertEquals(10, match.compare("Jeff  ", "JEFF"));
		Assert.assertEquals(10, match.compare("10-01-2001", "10-01-2001"));
		Assert.assertEquals(10, match.compare("254-49-3260", "254-49-3260"));
	}
	
 
	@Test
	public void testContainsNumbers() {
		Assert.assertTrue(match.containsNumeric("254-49-3260"));
		Assert.assertTrue(match.containsNumeric("10-01-2001"));
		Assert.assertFalse(match.containsNumeric(""));
		Assert.assertFalse(match.containsNumeric("JEFF"));
	}
	
}
