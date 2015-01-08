/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 * 
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.testing.selenium.weekenddiscount.badexample;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Class WeekendDiscountTestWithoutPageObjects. This is the bad example how
 * the tests would look like without the page object pattern: a messy bunch of
 * implementation details spread over all tests, impossible to maintain
 */
public class WeekendDiscountTestWithoutPageObjects {

	private WebDriver driver;

	@Before
	public void setup() {
		driver = new FirefoxDriver();
	}

	@Test
	public void testAddToCart() throws InterruptedException {

//		driver.get("http://localhost:8080");
		driver.get("http://188.166.42.91:8080/testing");

		WebElement nofItemsInCart = driver
				.findElement(By
						.xpath("//*[@id='cartLink']//*[@class='headerCartItemsCount']"));
		Assert.assertEquals("Should be 0 items in cart", "0",
				nofItemsInCart.getText());

		WebElement element = driver.findElement(By
				.xpath("//nav//a[contains(@href,'/hot-sauces')]"));

		element.click();

		element = driver
				.findElement(By
						.xpath("//a[contains(@href,'/hot-sauces/day_of_the_dead_habanero_hot_sauce')]"));

		element.click();

		element = driver.findElement(By.id("mainProductToCart"));

		element.click();

		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.pollingEvery(1, TimeUnit.SECONDS);
		WebElement newNofItemsInCart = driver
				.findElement(By
						.xpath("//*[@id='cartLink']//*[@class='headerCartItemsCount']"));
		wait.until(ExpectedConditions.textToBePresentInElement(
				newNofItemsInCart, "1"));

		Assert.assertEquals("Now 1 item in cart", "1",
				newNofItemsInCart.getText());

	}
}
