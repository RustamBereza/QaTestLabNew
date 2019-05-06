
import io.qameta.allure.*;
import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.com.qatestlab.prestatshop.*;

import java.util.concurrent.TimeUnit;

public class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://prestashop-automation.qatestlab.com.ua/ru/");
        mainPage = new MainPage(driver);
    }

    @Test
    @Story("Create a report ")
    public void comparisonCurrency(){
        Assert.assertTrue(mainPage.comparisonCurrency());
        System.out.println("Price of products in the \"Featured Products\" specified in " +
                "in accordance with the established currency in the website header. ");
    }

    @Test
    public void checkPresenceOfInscription(){
        SearchingResultsPage searchingResultsPage =  mainPage.requestDress();
        System.out.println("Set the price display in USD. ");
        System.out.println("Searched by word \"dress\". ");
        Assert.assertEquals("Товаров 7.", searchingResultsPage.checkPresenceOfInscription());
        System.out.println("The page \"Search results\" contains the inscription \"Products: X.\" ");
    }

    @Attachment
    public byte[] screenshot(){
        return((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @After
    public void tearDown(){
        screenshot();

        driver.quit();
    }
}
