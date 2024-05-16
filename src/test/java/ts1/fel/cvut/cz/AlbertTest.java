package ts1.fel.cvut.cz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ts1.fel.cvut.cz.pom.AlbertMainPage;

class AlbertTest {
    private WebDriver driver;

    @BeforeEach
    void driverSetup() {
        driver = new ChromeDriver();
    }

//    @AfterEach
//    void driverDestruct() {
//        driver.quit();
//    }

    @ParameterizedTest(name = "Searches for {0} and search query text should include this")
    @CsvSource({"Albert Zmrzlina lesní plody"})
    void testSearchBar(String searchText) {
        new AlbertMainPage(driver)
                .openMainPage()
                .acceptCookies()
                .fillInSearchBar(searchText)
                .clickSearchButton()
                .containsProduct(searchText);
    }
}