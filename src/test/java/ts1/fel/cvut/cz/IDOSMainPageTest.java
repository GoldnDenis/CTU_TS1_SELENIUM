package ts1.fel.cvut.cz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ts1.fel.cvut.cz.pom.IDOSMainPage;
import ts1.fel.cvut.cz.pom.IDOSSearchPage;

import static org.junit.jupiter.api.Assertions.*;

public class IDOSMainPageTest {
    private WebDriver driver;
    private IDOSMainPage mainPage;

    @BeforeEach
    void driverSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        mainPage = new IDOSMainPage(driver)
                .openMainPage()
                .clickAcceptCookies();
    }

    @AfterEach
    void driverDestruct() {
        driver.quit();
    }

    @Test
    void emptyRouteSearch_nothingWasFilledIn_getsErrorTextsInFieldsFromAndTo_test() {
        String expFromError = "Vyberte místo odkud chcete cestovat";
        String expToError = "Vyberte místo kam chcete cestovat";

        IDOSSearchPage searchPage = mainPage.clickSearchButton();

        assertEquals(expFromError, searchPage.getFromErrorFieldText());
        assertEquals(expToError, searchPage.getToErrorFieldText());
    }

    @Test
    void directRouteSearch_fromDejvickaToDivadloGong_getsAnErrorPopUp_test() {
        String from = "Dejvická";
        String to = "Divadlo Gong";

        String expError = "Spojení nebylo nalezeno.";
        IDOSSearchPage searchPage = mainPage.fillInFromField(from)
                .fillInToField(to)
                .checkDirectConnectionsOnlyBox()
                .clickSearchButton();

        assertTrue(searchPage.getErrorMessagePopUp().contains(expError));
    }

    @ParameterizedTest(name = "Should search for a route: from {0} to {1}, but not find.")
    @CsvFileSource(resources = {"/testingdata/non-existing_routes.csv"})
    void nonExistingRouteSearch_parametrized_test(String from, String to) {
        String expError = "Takové místo neznáme.";

        IDOSSearchPage searchPage = mainPage.fillInFromField(from)
                .fillInToField(to)
                .clickSearchButton();

        String foundFromError = searchPage.getFromErrorFieldText();
        String foundToError = searchPage.getToErrorFieldText();

        if (!foundFromError.isEmpty()) {
            assertEquals(expError, foundFromError);
        }

        if (!foundToError.isEmpty()) {
            assertEquals(expError, foundToError);
        }
    }

    @ParameterizedTest(name = "Should search for a route: from {0} to {1} and find.")
    @CsvFileSource(resources = {"/testingdata/existing_routes.csv"})
    void existingRoutesSearch_parametrized_test(String from, String to) {
        IDOSSearchPage searchPage = mainPage.fillInFromField(from)
                .fillInToField(to)
                .clickSearchButton();

        assertEquals(from ,searchPage.getFromFieldValue());
        assertEquals(to ,searchPage.getToFieldValue());
    }
}
