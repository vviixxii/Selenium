package org.openqa.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Selenium4Example {

	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();

		//driver.get("http://eluniversal.com.mx");
		driver.get("http://google.com.mx");
		
		List<WebElement> elements = driver.findElements(By.xpath("//a"));
		System.out.println("Elements: " + elements.size());
		System.out.println("");
		int index = 1;
		for(WebElement el : elements) {
			try {
			System.out.println("******************* " + index + " ************************");
			//System.out.println(el.getCssValue(propertyName));
			System.out.println("href: " + el.getAttribute("href")); 
			System.out.println("TagName: " + el.getTagName());
			if(el.getText().trim().length() > 0)
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
		driver.quit();
	}
}