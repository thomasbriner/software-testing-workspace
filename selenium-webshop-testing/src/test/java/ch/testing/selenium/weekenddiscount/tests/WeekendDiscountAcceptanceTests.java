/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 * 
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.testing.selenium.weekenddiscount.tests;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ch.testing.selenium.weekenddiscount.executionrule.ScreenshotOnFailureRule;
import ch.testing.selenium.weekenddiscount.executionrule.WebDriverKeeper;
import ch.testing.selenium.weekenddiscount.pageobjects.CartPage;
import ch.testing.selenium.weekenddiscount.pageobjects.HomePage;
import ch.testing.selenium.weekenddiscount.pageobjects.HotSaucesPage;
import ch.testing.selenium.weekenddiscount.pageobjects.SauceDetailPage;
import ch.testing.selenium.weekenddiscount.util.DBUtil;

/**
 * The Class HeatClinicAcceptanceTests. In this class the acceptance Tests for
 * the weekend discount features are implemented.
 */
public class WeekendDiscountAcceptanceTests {

	private static final Log LOG = LogFactory
			.getLog(WeekendDiscountAcceptanceTests.class);

	@Rule
	public ScreenshotOnFailureRule screenshot = new ScreenshotOnFailureRule();

	private WebDriver driver;

	@BeforeClass
	public static void classSetup() {
		LOG.info("Loading initial data dump...");
		DBUtil.loadDump();
	}

	@Before
	public void setup() throws IOException, InterruptedException {
		driver = new FirefoxDriver();
		WebDriverKeeper.getInstance().setDriver(driver);
	}

	@After
	public void tearDown() {
		driver.close();
	}

	@Test
	public void testWeekendDiscountEnabled() {

		// set date to 4th weekend
		Date weekendDate = createDate(2014, 6, 28, 23, 49, 3);
		DBUtil.setTestTime(weekendDate);

		// get the home page
		HomePage homePage = HomePage.navigateTo(driver);

		// and remember the number of objects in cart
		int nofObjectsInitiallyInCart = homePage.getNofObjectsInCart();
		Assert.assertEquals("Cart should be empty at the beginning", 0,
				nofObjectsInitiallyInCart);

		// go to the sauces
		HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

		// and pick one
		SauceDetailPage saucePage = hotSaucesPage
				.sauceDayOfTheDeadHabaneroDetails();

		// now buy this one
		saucePage.buySauce();

		// and check back on the home page the new number in cart
		HomePage backAgain = HomePage.navigateTo(driver);
		int nofObjectsInCart = backAgain.getNofObjectsInCart();

		Assert.assertTrue(nofObjectsInCart == nofObjectsInitiallyInCart + 1);

		CartPage cart = backAgain.goToCart();
		String subtotal = cart.getSubtotal();

		Assert.assertEquals("Checking subtotal", "$3.49", subtotal);

	}

	@Ignore
	@Test
	public void testWeekendDiscountDisabled() {

		// set date to 4th weekend
		Date weekendDate = createDate(2015, 6, 28, 23, 49, 3);
		DBUtil.setTestTime(weekendDate);

		// get the home page
		HomePage homePage = HomePage.navigateTo(driver);

		// and remember the number of objects in cart
		int nofObjectsInitiallyInCart = homePage.getNofObjectsInCart();
		Assert.assertEquals("Cart should be empty at the beginning", 0,
				nofObjectsInitiallyInCart);

		// go to the sauces
		HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

		// and pick one
		SauceDetailPage saucePage = hotSaucesPage
				.sauceDayOfTheDeadHabaneroDetails();

		// now buy this one
		saucePage.buySauce();

		// and check back on the home page the new number in cart
		HomePage backAgain = HomePage.navigateTo(driver);
		int nofObjectsInCart = backAgain.getNofObjectsInCart();

		Assert.assertTrue(nofObjectsInCart == nofObjectsInitiallyInCart + 1);

		CartPage cart = backAgain.goToCart();
		String subtotal = cart.getSubtotal();

		Assert.assertEquals("Checking subtotal", "$6.99", subtotal);

	}

	private Date createDate(int year, int month, int day, int hour, int minute,
			int second) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);

		return calendar.getTime();

	}

}
