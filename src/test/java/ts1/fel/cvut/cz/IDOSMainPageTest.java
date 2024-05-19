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
import ts1.fel.cvut.cz.utils.DateTimeStringUtility;

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

    @ParameterizedTest(name = "Should search for a route: from {0} to {1} and get an Error - \"{2}\".")
    @CsvFileSource(resources = {"/testingdata/non-existing_routes.csv"})
    void nonExistingRouteSearch_parametrized_test(String from, String to, String expErrorMessage) {
        IDOSSearchPage searchPage = mainPage.fillInFromField(from)
                .fillInToField(to)
                .clickSearchButton();

        String foundFromError = searchPage.getFromErrorFieldText();
        String foundToError = searchPage.getToErrorFieldText();

        if (!foundFromError.isEmpty()) {
            assertEquals(expErrorMessage, foundFromError);
        }

        if (!foundToError.isEmpty()) {
            assertEquals(expErrorMessage, foundToError);
        }
    }

    @ParameterizedTest(name = "Should search for a route: from {0} to {1} and find.")
    @CsvFileSource(resources = {"/testingdata/existing_routes.csv"})
    void existingRoutesSearch_parametrized_test(String expFrom, String expTo) {
        IDOSSearchPage searchPage = mainPage.fillInFromField(expFrom)
                .fillInToField(expTo)
                .clickSearchButton();

        assertEquals(expFrom ,searchPage.getFromFieldValue());
        assertEquals(expTo ,searchPage.getToFieldValue());
    }

    @Test
    void arrivalRoutesSearch__allFoundTimesAreEarlier_15h00m_test() {
        String departureTime = "15:00";
        for (String time : mainPage.fillInFromField("Praha")
                .fillInToField("Brno")
                .fillInTimeField(departureTime)
                .checkArrivalBox()
                .clickSearchButton()
                .getFoundArrivalTimes()) {
            assertTrue(DateTimeStringUtility.timeIsGreaterOrEquals(departureTime, time));
        }
    }
}
