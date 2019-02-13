package org.openqa.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Ejercicio 2
 * 
 * Abre el sitio http://www.google.com y realiza la consulta de todos los
 * elementos de la página By.xpath Relativo
 * 
 * @author Ricardo Romero
 *
 */
public class Selenium1Example8 {

	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://google.com.mx");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.xpath("//input[@name='q']"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());

		// Getting Selenium to pause for X miliseconds
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Close the browser
		driver.quit();
	}
}