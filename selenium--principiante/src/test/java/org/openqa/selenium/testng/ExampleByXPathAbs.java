package org.openqa.selenium.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 * Locator ->  By.xpath Absoluto
 * 
 * @author Ricardo Romero
 *
 */
public class ExampleByXPathAbs extends BaseTest {
	
	@Test
	public void Test1() {
		WebDriver driver = getDriver();
		WebElement element = driver.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div/div[1]/div/div[1]/input"));
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