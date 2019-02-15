package org.openqa.selenium.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 * Locator ->  By.className
 * 
 * @author Ricardo Romero
 *
 */
public class ExampleByClassName extends BaseTest {
	
	@Test
	public void Test1() {
		WebDriver driver = getDriver();
		WebElement element = driver.findElement(By.className("gLFyf"));
		element.sendKeys("Cheese!");
		element.submit();
		System.out.println("Page title is: " + driver.getTitle());
		// Getting Selenium to pause for X miliseconds
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
