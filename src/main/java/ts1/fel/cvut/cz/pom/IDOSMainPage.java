package ts1.fel.cvut.cz.pom;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IDOSMainPage extends AbstractPage {
    @FindBy(css = "#didomi-notice-agree-button")
    private WebElement acceptCookiesButton;
    @FindBy(css = "#From")
    private WebElement fromField;
    @FindBy(css = "#To")
    private WebElement toField;
    @FindBy(css = "#Date")
    private WebElement dateField;
    @FindBy(css = "#Time")
    private WebElement timeField;
    @FindBy(css = "#connection-filter > div.other-params.ca-slide > div > p > label")
    private WebElement directConnectionsOnlyCheckBox;
    @FindBy(css = "#connection-filter > div.other-params.ca-slide > div > ul > li:nth-child(1) > label")
    private WebElement departureCheckBox;
    @FindBy(css = "#connection-filter > div.other-params.ca-slide > div > ul > li:nth-child(2) > label")
    private WebElement arrivalCheckBox;
    @FindBy(css = "#connection-filter > div.submit > button")

    private WebElement searchButton;

    public IDOSMainPage(WebDriver driver) {
        super(driver, "https://idos.idnes.cz/vlakyautobusymhdvse/spojeni/");
    }

    public IDOSMainPage openMainPage() {
        driver.get(url);
        waitUntilVisible(searchButton);
        return this;
    }

    public IDOSMainPage clickAcceptCookies() {
        waitUntilClickable(acceptCookiesButton);
        acceptCookiesButton.click();
        return this;
    }

    public IDOSMainPage fillInFromField(String from) {
        waitUntilVisible(fromField);
        fromField.sendKeys(from);
        return this;
    }

    public IDOSMainPage fillInToField(String to) {
        waitUntilVisible(toField);
        toField.sendKeys(to);
        return this;
    }

    public IDOSMainPage fillInDateField(String date) {
        waitUntilVisible(dateField);
        dateField.sendKeys(date);
        return this;
    }

    public IDOSMainPage fillInTimeField(String time) {
        waitUntilVisible(timeField);
        timeField.sendKeys(time);
        return this;
    }

    public IDOSMainPage checkDepartureBox() {
        waitUntilClickable(departureCheckBox);
        departureCheckBox.click();
        return this;
    }

    public IDOSMainPage checkArrivalBox() {
        waitUntilClickable(arrivalCheckBox);
        arrivalCheckBox.click();
        return this;
    }

    public IDOSMainPage checkDirectConnectionsOnlyBox() {
        waitUntilClickable(directConnectionsOnlyCheckBox);
        directConnectionsOnlyCheckBox.click();
        return this;
    }

    public IDOSSearchPage clickSearchButton() {
        waitUntilClickable(searchButton);
        searchButton.click();
        return new IDOSSearchPage(driver);
    }
}
