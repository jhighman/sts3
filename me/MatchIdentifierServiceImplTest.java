package me;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import me.MatchIdentifierService.MatchTarget;



public class MatchIdentifierServiceImplTest {

	private MatchIdentifierService match = new MatchIdentifierServiceImpl();
	private int score = 0;
	
	
	@Test
	public void testAddressMatchScenarios() {
		
		Assert.assertEquals(5, match.compare("22 Acacia Avenue, NEW York City 55555", "22 Acacia Ave, NEW York City 55555"));  // I would hope for a higher score
		
	}
	
	
	@Ignore
	@Test
	public void testDateMatchScenarios() {
		Assert.assertEquals(5, match.compare("03/1970", "03/11/1970"));
		Assert.assertEquals(9, match.compare("03-11-1970", "03/11/1970"));
		Assert.assertEquals(9, match.compare("03-11-1970", "11/03/1970")); // note the anomaly
		Assert.assertEquals(5, match.compare("03-1970", "03/11/1970"));
		Assert.assertEquals(3, match.compare("1970", "03/11/1970"));
		Assert.assertEquals(3, match.compare("Mar/70", "03/11/1970"));
		Assert.assertEquals(3, match.compare("Mar 70", "03/11/1970"));
		Assert.assertEquals(3, match.compare("03/11/1970", "Mar 70"));
		Assert.assertEquals(3, match.compare("03/11/1970", "Mar/70"));
		Assert.assertEquals(3, match.compare("03/11/1970", "1970"));
		
		// targeted option:
		Assert.assertEquals(5, match.compare("03/1970", "03/11/1970", MatchTarget.DATE));
		Assert.assertEquals(9, match.compare("03-11-1970", "03/11/1970" , MatchTarget.DATE));
		Assert.assertEquals(9, match.compare("03-11-1970", "11/03/1970" , MatchTarget.DATE)); // note the anomaly
		Assert.assertEquals(5, match.compare("03-1970", "03/11/1970", MatchTarget.DATE));
		Assert.assertEquals(3, match.compare("1970", "03/11/1970", MatchTarget.DATE));
		Assert.assertEquals(3, match.compare("Mar/70", "03/11/1970", MatchTarget.DATE));
		Assert.assertEquals(3, match.compare("Mar 70", "03/11/1970", MatchTarget.DATE));
		Assert.assertEquals(3, match.compare("03/11/1970", "Mar 70", MatchTarget.DATE));
		Assert.assertEquals(3, match.compare("03/11/1970", "Mar/70", MatchTarget.DATE));
		Assert.assertEquals(3, match.compare("03/11/1970", "1970", MatchTarget.DATE));
	}
	
	@Ignore
	@Test
	public void testNameMatchScenarios() {
		
		Assert.assertEquals(10, match.compare("Jeff Highman", "JEFF HIGHMAN"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "JEFFREY HIGHMAN"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman Jeff"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman Jeffrey A"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman, Jeffrey A"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman, Jeffrey Aaron"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Jeffrey A Highman"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "J A Highman"));  // score seems high
		Assert.assertEquals(9, match.compare("Jeff Highman", "Jeff Highman JR"));
		Assert.assertEquals(5, match.compare("Jeff Highman", "Harold Highman"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Jeff Higman"));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Geoff Highman"));
		Assert.assertEquals(9, match.compare("Jeffrey Highman", "Jeffery Highman"));
		Assert.assertEquals(9, match.compare("Jeffrey Highman", "Jeffery Aaron Highman"));
		Assert.assertEquals(5, match.compare("Jeffrey Highman", "Michael Allen Highman"));
		Assert.assertEquals(5, match.compare("Jeffrey Highman", "Jeff Hiemen"));
		//Assert.assertEquals(0, match.compare("Jeffrey Highman", "Michael Allen Highwayman"));  //why??
		//Assert.assertEquals(0, match.compare("Jeffrey Highman", "Billy Hiemen"));  //why??
		
		Assert.assertEquals(5, match.compare("Bill Highman", "William Highman"));  //Doesn't do well with nicknames
		
		// targeted option:
		Assert.assertEquals(10, match.compare("Jeff Highman", "JEFF HIGHMAN", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "JEFFREY HIGHMAN", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman Jeff", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman Jeffrey A", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman, Jeffrey A", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Highman, Jeffrey Aaron", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Jeffrey A Highman", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "J A Highman", MatchTarget.NAME));
		Assert.assertEquals(9, match.compare("Jeff Highman", "Jeff Higman", MatchTarget.NAME));
		Assert.assertEquals(5, match.compare("Jeff Highman", "Harold Highman", MatchTarget.NAME));
	}
	
	@Ignore
	@Test
	public void testSSNMasks() {
		
		
		Assert.assertEquals(5, match.compare("254-49-3260", "XXX-XX-3260", MatchTarget.SSN));
		Assert.assertEquals(0, match.compare("254-49-3260", "254-44-3260", MatchTarget.SSN));
		Assert.assertEquals(5, match.compare("254-49-3260", "254-44-XXXX", MatchTarget.SSN));
		
		// targeted option:
		
		Assert.assertEquals(5, match.compare("254-49-3260", "XXX-XX-3260"));
		Assert.assertEquals(0, match.compare("254-49-3260", "254-44-3260"));
		Assert.assertEquals(5, match.compare("254-49-3260", "254-44-XXXX"));
		
	}
	
	
	@Ignore
	@Test
	public void testNullsAndSptings() {
		Assert.assertEquals(-10, match.compare(null, null));
		Assert.assertEquals(-10, match.compare(null, "x"));
		Assert.assertEquals(-10, match.compare("x", null));
		Assert.assertEquals(-10, match.compare("", ""));
		Assert.assertEquals(-10, match.compare("", "x"));
		Assert.assertEquals(-10, match.compare("x", ""));
	}
	@Ignore
	@Test
	public void testExactMatchScenarios() {
		Assert.assertEquals(10, match.compare("Jeff", "JEFF"));
		Assert.assertEquals(10, match.compare("Jeff", "JEFF  "));
		Assert.assertEquals(10, match.compare("Jeff  ", "JEFF"));
		Assert.assertEquals(10, match.compare("10-01-2001", "10-01-2001"));
		Assert.assertEquals(10, match.compare("254-49-3260", "254-49-3260"));
	}
	
	
}
