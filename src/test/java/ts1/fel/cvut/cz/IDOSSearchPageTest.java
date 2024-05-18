package ts1.fel.cvut.cz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ts1.fel.cvut.cz.pom.IDOSSearchPage;
import ts1.fel.cvut.cz.utils.DateStringUtility;

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
                .getFoundTimes()) {
            assertTrue(DateStringUtility.timeIsGreaterOrEquals(time, limitTime));
        }
    }

    @ParameterizedTest(name = "{0} should be shifted to the next day and be found.")
    @CsvFileSource(resources = {"/testingdata/dates.csv"})
    void nextDayRoutesSearch_parametrized_test(String date) {
        String[] specificDate = date.split("\\.");
        LocalDate testDate = LocalDate.of(Integer.parseInt(specificDate[2]), Integer.parseInt(specificDate[1]), Integer.parseInt(specificDate[0]) + 1);
        String foundDate = searchPage.fillInDateField(date)
                .clickNextDayButton()
                .clickSearch()
                .getDate();
        foundDate = DateStringUtility.normalizeDate(foundDate.split(" ")[0]);

        String expectedDate = testDate.format(DateTimeFormatter.ofPattern("dd.MM."));
        assertEquals(expectedDate, foundDate);
    }

    @Test
    void ticketDiscountISIC_from18to25Age_test() {
        String ageEC = "18-25";
        String discount = "karta ISIC";
        searchPage.clickEditTravelerInfoButton()
                .selectAgeEC(ageEC)
                .clickDiscountCardButton()
                .selectDiscountCard(discount)
                .clickSaveDiscountCardButton()
                .clickSavePassengerButton()

        searchPage = searchPage
                .fillInDateField(testDate.format(DateTimeFormatter.ofPattern("dd.MM.")))
                .clickSearch();
        int standardPrice = searchPage.getPriceValue();
        int newPrice = searchPage.clickEditTravelerInfoButton()
                .selectAgeEC(ageEC)
                .clickSavePassengerButton()
                .getPriceValue();

        int actualDiscount = 100 - (newPrice * 100) / standardPrice;
        assertTrue(actualDiscount >= expDiscount);
    }
}
