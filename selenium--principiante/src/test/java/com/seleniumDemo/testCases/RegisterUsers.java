package com.seleniumDemo.testCases;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.seleniumDemo.config.Business;
import com.seleniumDemo.config.LocatorType;

public class RegisterUsers extends Business {

	Object[][] testObjArray;

	@BeforeTest
	public void setUp() {
		navigateTo();
	}

	@AfterTest
	public void tearDown() {
		getDriver().quit();
	}

	@BeforeMethod
	public void clickRegister() {
		clickOnLink(LocatorType.LinkText, "REGISTER");
	}

	@Test
	public void registerUsers() {
		String userName = "gsanchez";
		String pass = "123456789";

		String[] contactInfo = new String[4];
		contactInfo[0] = "Guadalupe";
		contactInfo[1] = "Sanchez";
		contactInfo[2] = "333-987-9876";
		contactInfo[3] = "gsanchez@loquesea.com";

		String[] mailInfo = new String[5];
		mailInfo[0] = "Insurgentes Sur 408";
		mailInfo[1] = "Guadalajara";
		mailInfo[2] = "Jalisco";
		mailInfo[3] = "20345";
		mailInfo[4] = "MEXICO";

		// Adding Contact Information
		addContactInfo(contactInfo);

		// Adding Mailing Information
		addMailingInfo(mailInfo);

		// Adding User Information
		submitUserInfo(userName, pass);

		// Verify user name is displayed
		Assert.assertTrue(getElementText().contains(userName));
	}
}
