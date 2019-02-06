package romero.com.ExtendReportExample;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import romero.com.ExtendReportExample.base.TestBase;
import romero.com.ExtendReportExample.pages.BasePage;
import romero.com.ExtendReportExample.pages.CreateAccountPage;
import romero.com.ExtendReportExample.pages.SignInPage;

public class CreateAccountTest extends TestBase {
	private WebDriver driver;
	private SignInPage signInPage;
	private BasePage basePage;
	private CreateAccountPage createAccountPage;

	@BeforeClass
	public void setUp() {
		driver = getDriver();
	}

	@Test
	public void verifyCreateAnAccountPage() {
		System.out.println("Create An Account page test...");
		basePage = new BasePage(driver);
		signInPage = basePage.clickSignInBtn();
		createAccountPage = signInPage.clickonCreateAnAccount();
		Assert.assertTrue(createAccountPage.verifyPageTitle(), "Page title not matching");
		Assert.assertTrue(createAccountPage.verifyCreateAccountPageText(), "Page text not matching");
	}

	@Test
	public void createAccountExample1() {
		System.out.println("Hey im in example1 test");
	}

	@Test
	public void createAccountExample2() {
		System.out.println("Hey im in Example2 test");
	}
}
