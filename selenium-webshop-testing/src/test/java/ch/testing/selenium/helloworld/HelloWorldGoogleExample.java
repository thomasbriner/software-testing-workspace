/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 * 
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.testing.selenium.helloworld;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * The Class HelloWorldGoogleExample. This class is provided as first example on
 * http://code.google.com/p/selenium/wiki/GettingStarted
 */
public class HelloWorldGoogleExample {

	@Test
	public void testGoogleSearchPage() {

		// Create a new instance of the html unit driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver();

		// And now use this to visit Google
		driver.get("http://www.google.com");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the
		// element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Test
//	public void testGoogleChangeLanguage() {
//
//		// Create a new instance of the html unit driver
//		// Notice that the remainder of the code relies on the interface,
//		// not the implementation.
//		WebDriver driver = new FirefoxDriver();
//
//		// And now use this to visit Google
//		driver.get("http://www.google.com");
//
//		// Find the text input element by its name
//		WebElement element = driver.findElement(By.xpath("//div[@id='als']//a[contains(@href,'hl=fr')]"));
//
//		
//		element.click();
//
//
//		Assert.assertTrue("Page should now be in french", driver.getPageSource().contains("Le domaine Google.ch est disponible"));
//		
//	}

	
	
}