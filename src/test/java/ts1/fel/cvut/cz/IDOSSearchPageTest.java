package ts1.fel.cvut.cz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ts1.fel.cvut.cz.pom.IDDOSCartPage;
import ts1.fel.cvut.cz.pom.IDOSSearchPage;
import ts1.fel.cvut.cz.utils.DateTimeStringUtility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class IDOSSearchPageTest {
    private WebDriver driver;
    private IDOSSearchPage searchPage;

    @BeforeEach
    void driverSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        searchPage = new IDOSSearchPage(driver, "https://idos.idnes.cz/vlakyautobusymhdvse/spojeni/vysledky/?f=Praha&fc=1&t=Brno&tc=1")
                .openCurrentSearchPage()
                .clickAcceptCookies();
    }

    @AfterEach
    void driverDestruct() {
        driver.quit();
    }

    @ParameterizedTest(name = "Should choose age - {0} and save. Displays afterwards - {1}.")
    @CsvFileSource(resources = {"/testingdata/age_and_info_statuses.csv"})
    void editAndSaveAge_parametrized_test(String ageEC, String expText) {
        String foundInfo = searchPage
                .clickEditTravelerInfoButton()
                .selectAgeEC(ageEC)
                .clickSavePassengerButton()
                .getPassengerInfo();

        assertEquals(expText, foundInfo);
    }

    @Test
    void findTimeCorrectly_from20h12m_test() {
        String limitTime = "20:12";
        for (String time : searchPage.fillInTimeField(limitTime)
                .clickSearch()
                .getFoundDepartureTimes()) {
            assertTrue(DateTimeStringUtility.timeIsGreaterOrEquals(time, limitTime));
        }
    }

    @ParameterizedTest(name = "{0} should be shifted to the next day. Results are only that day.")
    @CsvFileSource(resources = {"/testingdata/dates.csv"})
    void nextDayRoutesSearch_allFoundDatesOnlyThatDay_parametrized_test(String date) {
        String[] specificDate = date.split("\\.");
        LocalDate testDate = LocalDate.of(Integer.parseInt(specificDate[2]), Integer.parseInt(specificDate[1]), Integer.parseInt(specificDate[0]) + 1);
        String expDate = testDate.format(DateTimeFormatter.ofPattern("dd.MM."));

        for (String foundDate : searchPage.fillInDateField(date)
                .clickSearch()
                .clickNextDayButton()
                .clickSearch()
                .getFoundDates()) {
            assertEquals(expDate, DateTimeStringUtility.normalizeDate(foundDate));
        }
    }

    @Test
    void ticketDiscountISIC_priceLesserThan50percentOff_from18to25Age_test() {
        String ageEC = "18-25";
        String name = "Vojtech";
        String surname = "Marek";
        double expDiscount = 50;

        IDDOSCartPage cartPage = searchPage.clickNextDayButton()
                .clickSearch()
                .findAndClickAddToShoppingCart()
                .clickTicketBackNoButton();

        double originalPrice = cartPage.getPriceValue();

        cartPage = cartPage.clickEditTravelerInfoButton()
                .clickAgeECSelector()
                .selectAgeEC(ageEC)
                .fillNameField(name)
                .fillSurnameField(surname)
                .clickDiscountCardButton()
                .selectISICDiscountCard()
                .clickSaveDiscountCardButton()
                .clickSavePassengerButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double discountPrice = cartPage.getPriceValue();

        double actualDiscount = 100 - (discountPrice * 100) / originalPrice;
        assertTrue(actualDiscount >= expDiscount);
    }

    @Test
    void addingSeveralTickets_totalTripleValue_threePassengers_test() {
        String ageEC = "18-25";
        String name = "Vojtech";
        String surname = "Marek";

        IDDOSCartPage cartPage = searchPage.clickNextDayButton()
                .clickSearch()
                .findAndClickAddToShoppingCart()
                .clickTicketBackNoButton();

        double originalPrice = cartPage.getPriceValue();
        int numOfPassengers = 3;

        double expTotal = originalPrice * numOfPassengers;

        for (int i = 0; i < numOfPassengers; i++) {
            if (i == 0) cartPage = cartPage.clickEditTravelerInfoButton();
            else cartPage = cartPage.clickAddNewPassenger();
            cartPage = cartPage.clickAgeECSelector(i)
                    .selectAgeEC(ageEC, i)
                    .fillNameField(name, i)
                    .fillSurnameField(surname, i);
        }
        cartPage = cartPage.clickSavePassengerButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double totalPrice = cartPage.getPriceValue();

        assertEquals(expTotal, totalPrice);
    }
}
