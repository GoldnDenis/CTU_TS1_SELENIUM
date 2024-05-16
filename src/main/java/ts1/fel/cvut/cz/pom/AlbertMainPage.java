package ts1.fel.cvut.cz.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AlbertMainPage extends AbstractPage {
    @FindBy(css = "#app-root > header > div > div.sc-1s83yj9-0.gokHxp > form > div > input")
    private WebElement searchBar;
    @FindBy(css = "#app-root > header > div > div.sc-1s83yj9-0.gokHxp > form > div > div > button")
    private WebElement searchButton;
    @FindBy(css = "body > div.sc-gp4b9f-0.iaFGlt > div > div:nth-child(4) > div > div > div > div.sc-v8o5b9-9.sc-v8o5b9-10.cntWq.fngKyC > button > div")
    private WebElement acceptCookies;

    public AlbertMainPage(WebDriver driver) {
        super(driver, "https://www.albert.cz/");
    }

    public AlbertMainPage openMainPage() {
        driver.get(url);
        return this;
    }

    public AlbertSearchResultsPage clickSearchButton() {
        searchButton.click();
        return new AlbertSearchResultsPage(driver);
    }

    public AlbertMainPage fillInSearchBar(String text) {
        waitUntilVisible(searchBar);
        searchBar.sendKeys(text);
        return this;
    }

    public AlbertMainPage acceptCookies() {
        waitUntilVisible(acceptCookies);
        acceptCookies.click();
        return this;
    }
}
