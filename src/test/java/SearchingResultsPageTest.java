import io.qameta.allure.Attachment;
import io.qameta.allure.Story;
import org.junit.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.com.qatestlab.prestatshop.*;

import java.util.concurrent.TimeUnit;

public class SearchingResultsPageTest {

    private WebDriver driver;
    private SearchingResultsPage searchingResultsPage;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        searchingResultsPage = new SearchingResultsPage(driver);
        driver.get("http://prestashop-automation.qatestlab.com.ua/ru/search?controller=search&s=dress");
    }

    @Test
    @Story("Create a report ")
    public void checkCurrencyInProduct(){
        MainPage mainPage = new MainPage(driver);
        mainPage.setUSD();
        Assert.assertEquals("$", searchingResultsPage.checkCurrencyInProduct());
        System.out.println("The price of all the results shown is displayed in dollars. ");
    }

    @Test
    public void checkSorting(){
        searchingResultsPage.setPriceReduction();
        System.out.println("Set sorting from high to low. ");
        searchingResultsPage.waitForPrice();
        System.out.println("Expect the sorted goods. ");
        for(int i = 0; i < searchingResultsPage.checkSorting().length; i++){
            if(i != 0) {
                if (searchingResultsPage.checkSorting()[i-1] >= searchingResultsPage.checkSorting()[i]){
                    if(i == searchingResultsPage.checkSorting().length - 1) {
                        System.out.println("Products are sorted by price from high to low. ");
                    }
                }
            }else{
                continue;
            }
        }
    }

    @Test
    public void checkDiscountedPrices(){
        Assert.assertTrue(searchingResultsPage.checkDiscountedPrices());
        System.out.println("Price before and after corresponds to the discount. ");
    }

    @Attachment
    public byte[] screenshot(){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @After
    public void tearDown(){
        screenshot();

        driver.quit();
    }
}
