package ts1.fel.cvut.cz.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class IDOSSearchPage extends AbstractPage {
    @FindBy(css = "#didomi-notice-agree-button")
    private WebElement acceptCookiesButton;
    @FindBy(css = "#connection-filter > div.date-time.false > p.reset.inp-datetime.inp-datetime--date > a.inc.ico-right.pikaday-up.btn.btn-blue")
    private WebElement nextDayButton;
    @FindBy(css = "#From")
    private WebElement fromField;
    @FindBy(css = "#To")
    private WebElement toField;
    @FindBy(css = "#Date")
    private WebElement dateField;
    @FindBy(css = "#Time")
    private WebElement timeField;
    @FindBy(css = "#ResultYourWay > div > div > p > a")
    private WebElement editPassengerInfoButton;
    @FindBy(css = "#select-1")
    private WebElement ageECSelector;
    @FindBy(css = "#save-passengers")
    private WebElement savePassengerButton;
    @FindBy(css = "#connection-filter > div.submit > button")
    private WebElement searchButton;
    @FindBy(css = "#ResultYourWay > div > div > ul > li")
    private WebElement passengerInfo;
    @FindBy(css = "#modalContent-1716068661001 > div > div > div > div > ol > li > div > div.row > div.column.reduction-wrapper > span > a")
    private WebElement discountCardsButton;
    @FindBy(css = "#save-reduction")
    private WebElement saveDiscountCardButton;

    public IDOSSearchPage(WebDriver driver) {
        super(driver, driver.getCurrentUrl());
    }

    public IDOSSearchPage(WebDriver driver, String url) {
        super(driver, url);
    }

    public IDOSSearchPage openCurrentSearchPage() {
        driver.get(url);
        return this;
    }

    public IDOSSearchPage clickNextDayButton() {
        waitUntilClickable(nextDayButton);
        nextDayButton.click();
        return this;
    }

    public IDOSSearchPage fillInDateField(String date) {
        waitUntilVisible(dateField);
        dateField.sendKeys(date);
        return this;
    }

    public IDOSSearchPage fillInTimeField(String time) {
        waitUntilVisible(timeField);
        timeField.sendKeys(time);
        return this;
    }

    public String getFromFieldValue() {
        waitUntilVisible(fromField);
        return fromField.getAttribute("value");
    }

    public String getToFieldValue() {
        waitUntilVisible(toField);
        return toField.getAttribute("value");
    }

    public String getFromErrorFieldText() {
        try {
            waitUntilVisible(fromField);
            return driver.findElement(By.cssSelector("label[for='From'] .label-error")).getText();
        } catch (TimeoutException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getToErrorFieldText() {
        try {
            waitUntilVisible(toField);
            return driver.findElement(By.cssSelector("label[for='To'] .label-error")).getText();
        } catch (TimeoutException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getErrorMessagePopUp() {
        try {
            WebElement errorMessagePopup = driver.findElement(By.cssSelector("#errorModalContent"));
            waitUntilVisible(errorMessagePopup);
            return errorMessagePopup.getText();
        } catch (TimeoutException e) {
            e.printStackTrace();
            return "";
        }
    }

    public IDOSSearchPage clickSearch() {
        waitUntilClickable(searchButton);
        searchButton.click();
        return this;
    }

    public int getPriceValue() {
        List<WebElement> priceValueElements = driver.findElements(By.cssSelector("span[class='price-value']"));
        for (WebElement e: priceValueElements) {
            waitUntilClickable(e);
            String price = e.getText();
            if (price.isEmpty()) return -1;
            return Integer.parseInt(price);
        }
        return -1;
    }

    public String getPassengerInfo() {
        waitUntilVisible(passengerInfo);
        return passengerInfo.getText();
    }

    public String getDate() {
        waitUntilVisible(dateField);
        return dateField.getAttribute("value");
    }

    public List<String> getFoundTimes() {
        List<String> times = new ArrayList<>();
        List<WebElement> priceValueElements = driver.findElements(By.cssSelector("h2.reset.date"));
        for (WebElement e: priceValueElements) {
            waitUntilClickable(e);
            String time = e.getText().substring(0, 5);
            times.add(time);
        }
        return times;
    }

    public IDOSSearchPage clickAcceptCookies() {
        waitUntilClickable(acceptCookiesButton);
        acceptCookiesButton.click();
        return this;
    }

    public IDOSSearchPage clickEditTravelerInfoButton() {
        waitUntilClickable(editPassengerInfoButton);
        editPassengerInfoButton.click();
        return this;
    }

    public IDOSSearchPage selectAgeEC(String ageEC) {
        waitUntilClickable(ageECSelector);
        ageECSelector.click();
        WebElement option = driver.findElement(By.xpath("//option[contains(text(), '" + ageEC + "')]"));
        waitUntilVisible(option);
        option.click();
        return this;
    }

    public IDOSSearchPage clickDiscountCardButton() {
        waitUntilClickable(discountCardsButton);
        discountCardsButton.click();
        return this;
    }

    public IDOSSearchPage clickSaveDiscountCardButton() {
        waitUntilClickable(saveDiscountCardButton);
        saveDiscountCardButton.click();
        return this;
    }

    public IDOSSearchPage selectDiscountCard(String card) {
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '" + card + "')]"));
        waitUntilVisible(label);
        WebElement checkOption = driver.findElement(By.id(label.getAttribute("for")));
        waitUntilClickable(checkOption);
        checkOption.click();
        return this;
    }

    public IDOSSearchPage clickSavePassengerButton() {
        waitUntilClickable(savePassengerButton);
        savePassengerButton.click();
        return this;
    }
}
