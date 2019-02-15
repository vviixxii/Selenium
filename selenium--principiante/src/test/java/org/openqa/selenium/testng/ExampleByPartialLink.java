package org.openqa.selenium.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Locator -> By.partialLinkText
 * 
 * @author Ricardo Romero
 *
 */
public class ExampleByPartialLink extends BaseTest {

	@Test
	public void Test1() {
		WebDriver driver = getDriver();
		driver.get("http://eluniversal.com.mx/");
		List<WebElement> elements = driver.findElements(By.partialLinkText("amlo"));
		System.out.println("Elements: " + elements.size());
		System.out.println("");
		int index = 1;
		for (WebElement el : elements) {
			try {
				System.out.println("******************* " + index + " ************************");
				if (el.getText().trim().length() > 0)
					System.out.println("Text: " + el.getText());
				System.out.println("");
			} catch (StaleElementReferenceException e) {
				System.out.println("--> StaleElementReferenceException");
			}
			index++;
		}
		driver.findElement(By.partialLinkText("AMLO")).click();
		// Getting Selenium to pause for X miliseconds
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}