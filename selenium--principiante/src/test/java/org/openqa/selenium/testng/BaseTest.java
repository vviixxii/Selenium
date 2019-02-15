package org.openqa.selenium.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

	private WebDriver driver = null;
	String url = "http://www.google.com";

	public WebDriver getDriver() {
		driver.get(url);
		return driver;
	}

	@BeforeClass
	public void beforeTest() {
		System.out.println("Método @BeforeClass (BaseTest)");
		driver = new FirefoxDriver();
	}

	@AfterClass
	public void afterTest() {
		if (driver != null) {
			driver.quit();
		}
	}

}
