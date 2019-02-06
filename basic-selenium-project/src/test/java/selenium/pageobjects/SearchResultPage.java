package selenium.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import selenium.Pages;

public class SearchResultPage extends Pages {

	public SearchResultPage(final WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "q")
	private WebElement searchInput;

	@FindBy(css = "nav.menu a")
	private List<WebElement> sideNavi;

//	@FindBy(css = ".counter")
//	@FindBy(className = "Counter ml-1 mt-1 js-codesearch-count")
	@FindBy(className = "js-codesearch-count")
	private WebElement counter;

	public String getInputValue(){
		return searchInput.getAttribute("value");
	}

	public void clickNaviElement(String naviElement) {
		waitForElement(counter, 10);
		for (WebElement key : sideNavi) {
			if (key.getText().contains(naviElement)){
				key.click();
			}
		}
	}

	public String getExpectedResult(){
		return getTestData("search.result");
	}
	
//	@FindBy(className = "user-list-info em")
	@FindBy(className = "div.user-list-info a em")
	private List<WebElement> userListAccountNames;

	@FindBy(id = "user_search_results")
	private WebElement userList;

	public List<String> getAccountNames() {
		waitForElement(userList);

		List<String> userAccountNames = new ArrayList<>();
		for(WebElement userAccountName : userListAccountNames){
			userAccountNames.add(userAccountName.getText());
		}

		return userAccountNames;
	}
}
