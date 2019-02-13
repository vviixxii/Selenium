package org.openqa.selenium;

import java.util.List;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Ejercicio 1
 * 
 * Abre el sitio http://www.google.com y realiza la consulta de la palabra Cheese! By.tagName
 * 
 * @author Ricardo Romero
 *
 */
public class Selenium1Example3 {

	public static void main(String[] args) {
		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver();

		// And now use this to visit Google
		driver.get("http://www.google.com");
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		List<WebElement> elements = driver.findElements(By.tagName("a"));
		System.out.println("Elements: " + elements.size());
		System.out.println("");
		int index = 1;
		for(WebElement el : elements) {
			try {
			System.out.println("******************* " + index + " ************************");
			if(el.getText().trim().length() > 0)
				System.out.println("Text: " + el.getText());
			System.out.println("");
			} catch (StaleElementReferenceException e) {
				System.out.println("--> StaleElementReferenceException");
			}
			index++;
		}

		// Close the browser
		driver.quit();
	}
}
