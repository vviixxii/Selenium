package org.openqa.selenium.testng;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 * Locator -> By.name
 * 
 * @author Ricardo Romero
 *
 */
public class ExampleByName extends BaseTest {
	
	@Test
	public void Test1() {
		try {
		WebDriver driver = getDriver();
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("Cheese!");
		element.submit();
		System.out.println("Page title is: " + driver.getTitle());
		} catch (NoSuchElementException e) {
			System.out.println("********** NoSuchElementException -> " + e.getMessage());
			assertTrue(false);
		}
	}
}
