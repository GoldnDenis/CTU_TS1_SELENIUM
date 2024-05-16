package ts1.fel.cvut.cz.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String url;

    /**
     * wait is set to wait 5 seconds by default
     * @param driver webdriver for the selenium
     */
    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.url = "";

        PageFactory.initElements(driver, this);
    }

    protected AbstractPage(WebDriver driver, String url) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.url = url;

        PageFactory.initElements(driver, this);
    }

    public void setWaitDurationSeconds(int waitDurationSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitDurationSeconds));
    }

    protected void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
