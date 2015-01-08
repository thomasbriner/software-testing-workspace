/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 * 
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.testing.selenium.helloworld;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * The Class HelloWorldHeatClinic. This class can be used for first experiments
 * with selenium against the system under test.
 * 
 * Make sure the system under test is running! (Ant View --> site -->
 * jetty-demo-no-db)
 */
public class HelloWorldHeatClinic {

	private WebDriver driver;

	@Before
	public void setup() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

	}

	@Test
	public void testPriceOfSauceGreenGhost() throws InterruptedException {

		driver.get("localhost:8080");

		// check if the home page is loaded
		Assert.assertEquals("Should be home page of heat clinic",
				"Broadleaf Demo - Heat Clinic", driver.getTitle());

		// now go to "Hot Sauces"

		// and check the price of the green ghost sauce: should be $11.99

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testPriceOfSauceGreenGhostComplete() throws InterruptedException {

		driver.get("localhost:8080");

		// check if the home page is loaded
		Assert.assertEquals("Should be home page of heat clinic",
				"Broadleaf Demo - Heat Clinic", driver.getTitle());

		// now go to "Hot Sauces"
		WebElement hotSaucesLink = driver.findElement(By.xpath("//a[contains(@href,'/hot-sauces')]"));
		hotSaucesLink.click();

		// and check the price of the green ghost sauce: should be $11.99
		WebElement price = driver.findElement(By.xpath("//a[contains(@href,'green_ghost')]//div[@class='price']"));
		Assert.assertEquals("$11.99", price.getText());

	}

}
