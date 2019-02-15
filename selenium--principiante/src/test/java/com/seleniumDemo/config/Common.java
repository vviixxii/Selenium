package com.seleniumDemo.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Common {

	public WebDriver driver;
	String xpathLoc = "html/body/div[1]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[3]/a/font/b";

	public WebDriver getDriver() {
		if (driver == null) {
			driver = new FirefoxDriver();
		}
		return driver;
	}

	public void navigateTo() {
		getDriver();
		getDriver().get("http://newtours.demoaut.com/");
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void typeInTextBox(LocatorType locatorType, String locator, String textToType) {
		switch (locatorType.toString()) {
		case "Name":
			getDriver().findElement(By.name(locator)).sendKeys(textToType);
			break;
		case "Id":
			getDriver().findElement(By.id(locator)).sendKeys(textToType);
			break;
		}
	}

	public void clickOnLink(LocatorType locatorType, String locator) {
		switch (locatorType.toString()) {
		case "LinkText":
			getDriver().findElement(By.linkText(locator)).click();
			break;
		}
	}

	public String getElementText() {
		return getDriver().findElement(By.xpath(xpathLoc)).getText();
	}

	public void selectFromDropDown(LocatorType locatorType, String locator, String text) {
		switch (locatorType.toString()) {
		case "Name":
			new Select(getDriver().findElement(By.name(locator))).selectByVisibleText(text);
			break;
		case "Id":
			new Select(getDriver().findElement(By.name(locator))).selectByVisibleText(text);
			break;
		}
	}
}
