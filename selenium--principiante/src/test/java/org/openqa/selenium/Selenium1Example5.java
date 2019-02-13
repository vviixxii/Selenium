package org.openqa.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Ejercicio 4
 * 
 * Abre el sitio http://www.google.com y realiza la consulta de todos los elementos link de la página
 * que parcialmente contengan la palabra AMLO By.partialLinkText
 * 
 * @author Ricardo Romero
 *
 */
public class Selenium1Example5 {

	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://eluniversal.com.mx/");
		
		List<WebElement> elements = driver.findElements(By.partialLinkText("amlo"));
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
		
		driver.findElement(By.partialLinkText("AMLO")).click();
		
		//driver.quit();
	}
}