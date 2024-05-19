package ts1.fel.cvut.cz.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class IDDOSCartPage extends AbstractPage {
    @FindBy(css = "#ResultYourWay > div > div > p > a")
    private WebElement editPassengerInfoButton;
    @FindBy(css = "#save-passengers")
    private WebElement savePassengerButton;
    @FindBy(css = "a.passengers_reduction1")
    private WebElement discountCardsButton;
    @FindBy(css = "#save-reduction")
    private WebElement saveDiscountCardButton;
    @FindBy(id = "BasketPaymentSum")
    private WebElement totalPriceValue;
    @FindBy(css = "#add-new-passenger")
    private WebElement addNewPassengerButton;

    public IDDOSCartPage(WebDriver driver) {
        super(driver, driver.getCurrentUrl());
    }

    public IDDOSCartPage openShoppingCartPage() {
        driver.get(url);
        return this;
    }

    public IDDOSCartPage clickEditTravelerInfoButton() {
        waitUntilVisible(editPassengerInfoButton);
        waitUntilClickable(editPassengerInfoButton);
        editPassengerInfoButton.click();
        return this;
    }

    public IDDOSCartPage clickAgeECSelector() {
        WebElement selector = driver.findElement(By.cssSelector("#select-1"));
        waitUntilVisible(selector);
        waitUntilClickable(selector);
        selector.click();
        return this;
    }

    public IDDOSCartPage fillNameField(String name) {
        WebElement nameField = driver.findElement(By.cssSelector("#name-1"));
        waitUntilVisible(nameField);
        nameField.sendKeys(name);
        return this;
    }

    public IDDOSCartPage fillSurnameField(String name) {
        WebElement surnameField = driver.findElement(By.cssSelector("#surname-1"));
        waitUntilVisible(surnameField);
        surnameField.sendKeys(name);
        return this;
    }

    public IDDOSCartPage clickAgeECSelector(int passOrdNum) {
        WebElement selector = driver.findElement(By.cssSelector("#select-" + (passOrdNum + 1)));
        waitUntilVisible(selector);
        waitUntilClickable(selector);
        selector.click();
        return this;
    }

    public IDDOSCartPage fillNameField(String name, int passOrdNum) {
        WebElement nameField = driver.findElement(By.cssSelector("#name-" + (passOrdNum + 1)));
        waitUntilVisible(nameField);
        nameField.sendKeys(name);
        return this;
    }

    public IDDOSCartPage fillSurnameField(String name, int passOrdNum) {
        WebElement surnameField = driver.findElement(By.cssSelector("#surname-" +  + (passOrdNum + 1)));
        waitUntilVisible(surnameField);
        surnameField.sendKeys(name);
        return this;
    }

    public IDDOSCartPage selectAgeEC(String ageEC) {
        WebElement option = driver.findElement(By.xpath("//select[@id='select-1']/option[contains(text(), '" + ageEC + "')]"));
        waitUntilVisible(option);
        option.click();
        return this;
    }

    public IDDOSCartPage selectAgeEC(String ageEC, int passOrdNum) {
        WebElement option = driver.findElement(By.xpath("//select[@id='select-" + (passOrdNum + 1) + "']/option[contains(text(), '" + ageEC + "')]"));
        waitUntilVisible(option);
        option.click();
        return this;
    }

    public IDDOSCartPage clickAddNewPassenger() {
        waitUntilClickable(addNewPassengerButton);
        addNewPassengerButton.click();
        return this;
    }

    public IDDOSCartPage clickDiscountCardButton() {
        waitUntilClickable(discountCardsButton);
        discountCardsButton.click();
        return this;
    }

    public IDDOSCartPage clickSaveDiscountCardButton() {
        waitUntilClickable(saveDiscountCardButton);
        saveDiscountCardButton.click();
        return this;
    }

    public double getPriceValue() {
        waitUntilVisible(totalPriceValue);
        return Double.parseDouble(String.join("", totalPriceValue.getText().split(" Kč")[0].split(" ")));
    }

    public IDDOSCartPage selectISICDiscountCard() {
        WebElement label = driver.findElement(By.cssSelector("#recud-options-1 > li:nth-child(2) > label"));
        waitUntilClickable(label);
        label.click();
        return this;
    }

    public IDDOSCartPage clickSavePassengerButton() {
        waitUntilClickable(savePassengerButton);
        savePassengerButton.click();
        return this;
    }
}
