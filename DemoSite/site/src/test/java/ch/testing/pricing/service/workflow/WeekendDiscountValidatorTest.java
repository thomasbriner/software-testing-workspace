package ch.testing.pricing.service.workflow;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ch.testing.util.DateFactory;

public class WeekendDiscountValidatorTest {

	private WeekendDiscountValidator validator;

	@Before
	public void setup() {
		validator = new WeekendDiscountValidator();
	}

	@Test(expected = ValidatorNotYetInitializedException.class)
	public void testNotInitialized()
			throws ValidatorNotYetInitializedException {
		validator.isAuthorizedForDiscount(new Date());
	}

	@Test
	public void testWithinFirstWeekend()
			throws ValidatorNotYetInitializedException {
		validator.initializeWithWeekendNumber(1);
//		Date weekendDate = createDate(2014, 6, 7, 23, 49, 3);
		Date weekendDate = DateFactory.createDate(2014, 6, 7, 23, 49, 3);
		boolean actual = validator.isAuthorizedForDiscount(weekendDate);
		Assert.assertTrue("Sat of first Weekend --> should be true", actual);
	}


	@Test
	public void testNegativeWeekendNumber()
			throws ValidatorNotYetInitializedException {
		validator.initializeWithWeekendNumber(-1);
		Date weekendDate = DateFactory.createDate(2014, 6, 7, 23, 49, 3);
		boolean actual = validator.isAuthorizedForDiscount(weekendDate);
		Assert.assertTrue("Sat of first Weekend --> should be true", actual);
	}

	@Test
	public void testMonthStartingWithSaturday()
			throws ValidatorNotYetInitializedException {
		validator.initializeWithWeekendNumber(1);
		Date weekendDate = DateFactory.createDate(2014, 11, 1, 0, 0, 0);
		boolean actual = validator.isAuthorizedForDiscount(weekendDate);
		Assert.assertTrue("Sat of first Weekend --> should be true", actual);
	}
	
	

	@Test
	public void testWithinThirdWeekendSunday()
			throws ValidatorNotYetInitializedException {
		validator.initializeWithWeekendNumber(3);
		Date weekendDate = DateFactory.createDate(2014, 8, 17, 23, 59, 59);
		boolean actual = validator.isAuthorizedForDiscount(weekendDate);
		Assert.assertTrue("Sunday of first Weekend --> should be true", actual);
	}



	@Test
	public void testNotOnWeekend()
			throws ValidatorNotYetInitializedException {
		validator.initializeWithWeekendNumber(1);
		Date weekendDate = DateFactory.createDate(2014, 6, 11, 23, 49, 3);
		boolean actual = validator.isAuthorizedForDiscount(weekendDate);
		Assert.assertFalse("Wednesday --> should be false", actual);
	}

}
