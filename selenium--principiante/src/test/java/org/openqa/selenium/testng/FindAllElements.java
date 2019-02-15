package org.openqa.selenium.testng;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 * Ejercicio FindAllElements
 * 
 * Abre el sitio http://www.google.com y realiza la consulta de todos los elementos de la página
 * 
 * @author Ricardo Romero
 *
 */
public class FindAllElements extends BaseTest {
	
	@Test
	public void Test1() {
		WebDriver driver = getDriver();
		List<WebElement> elements = driver.findElements(By.xpath("//*"));
		System.out.println("Elements: " + elements.size());
		System.out.println("");
		int index = 1;
		for (WebElement el : elements) {
			try {
				System.out.println("******************* " + index + " ************************");
				// System.out.println(el.getCssValue(propertyName));
				System.out.println("TagName: " + el.getTagName());
				if (el.getText().trim().length() > 0)
					System.out.println("Text: " + el.getText());
				System.out.println("isDisplayed: " + el.isDisplayed());
				System.out.println("isEnabled: " + el.isEnabled());
				System.out.println("isSelected: " + el.isSelected());
				System.out.println("Location: " + el.getLocation());
				System.out.println("Size: " + el.getSize());
				System.out.println("");
			} catch (StaleElementReferenceException e) {
				System.out.println("--> StaleElementReferenceException");
			}
			index++;
		}
	}
}