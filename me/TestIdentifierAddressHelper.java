package me;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TestIdentifierAddressHelper {

	private MatchIdentifierAddressHelper helper = new MatchIdentifierAddressHelper();
	
	
	@Test
	public void test() {
		Assert.assertTrue(helper.hasZipCode("12 Elm Street Jacksonville, FL 32256"));
		Assert.assertTrue(helper.hasZipCode("12 Elm Street Jacksonville, FL 32256-1234"));
		Assert.assertEquals("32256", helper.getZipCode("12 Elm Street Jacksonville, FL 32256"));
		Assert.assertEquals("32256", helper.getZipCode("12 Elm Street Jacksonville, FL 32256-1234"));
		Assert.assertTrue(helper.hasStreetNumber("12 Elm Street Jacksonville, FL 32256"));
		Assert.assertEquals("12", helper.getStreetNumber("12 Elm Street Jacksonville, FL 32256"));
		Assert.assertTrue(helper.isAddress(" 12 Elm Street Jacksonville, FL 32256-1234  USA"));
		Assert.assertFalse(helper.isAddress(" 12 Elm Street Jacksonville"));
		Assert.assertFalse(helper.isAddress(" 12 Elm Street Jacksonville, FL"));
		Assert.assertTrue(helper.isAddress("Elm Street Jacksonville, FL 32256-1234  USA"));
	}

}
