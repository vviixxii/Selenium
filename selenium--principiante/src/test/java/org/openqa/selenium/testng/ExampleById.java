package org.openqa.selenium.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Locator -> By.id
 * 
 * @author Ricardo Romero
 *
 */
public class ExampleById extends BaseTest {
	
	@Test
	public void Test1() {
		WebDriver driver = getDriver();
		List<WebElement> elements = driver.findElements(By.id("tsf"));
		System.out.println("Elements: " + elements.size());
		System.out.println("");
		int index = 1;
		for(WebElement el : elements) {
			try {
			System.out.println("******************* " + index + " ************************");
			//if(el.getText().trim().length() > 0)
				System.out.println("Action: " + el.getAttribute("action"));
			System.out.println("");
			} catch (StaleElementReferenceException e) {
				System.out.println("--> StaleElementReferenceException");
			}
			index++;
		}
	}
}
