package ch.testing.selenium.weekenddiscount.pageobjects;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends Page {

	private static final Log LOG = LogFactory.getLog(HomePage.class);
	

	@FindBy(id="cart_products")
	private WebElement productsInCartTable;

	public CartPage(WebDriver driver) {
		super(driver);

		if (!(driver.getPageSource().contains("checkout"))) {
			throw new IllegalStateException("This is not the cart page");
		}
		LOG.debug("CartPage created successfully");
	}
	
	public String getSubtotal() {
		WebElement subtotal = driver.findElement(By.id("subtotal"));
		
		return subtotal.getText();
	}
	
	
	public int getNofProductsInCart() {
		List<WebElement> rows = productsInCartTable.findElements(By.xpath("tbody/tr"));
		return rows.size();
		
	}
	
	

}
