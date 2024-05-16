package ts1.fel.cvut.cz.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AlbertSearchResultsPage extends AbstractPage {
    @FindBy(css = "#main-content")
    private List<WebElement> resultBox;
    public AlbertSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean containsProduct(String productName) {
        boolean ret = false;
        for (WebElement res: resultBox) {
            String name = res.getAttribute("title");
            if (name.contains(productName)) {
                ret = true;
                break;
            }
        }

        return ret;
    }
}
