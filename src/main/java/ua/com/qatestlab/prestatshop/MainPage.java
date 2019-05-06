package ua.com.qatestlab.prestatshop;

import org.openqa.selenium.*;

import java.util.*;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    private By currencyUahInTheCap = By.xpath("//*[text()='UAH ₴']");
    private By currencyDollInTheCap = By.xpath("//*[text()='USD $']");
    private By currencyInProduct = By.xpath("//*[@class='products']//span[@class='price']");
    private By search = By.xpath("//*[@name='s']");
    private By searchButton = By.xpath("//*[text()='\uE8B6']");

    public boolean comparisonCurrency() {
        String currencyUahInTheCapText = driver.findElement(currencyUahInTheCap).getText();
        List<WebElement> currencyInProducts = driver.findElements(currencyInProduct);
        for(int i = 0; i <= currencyInProducts.size() - 1; i++) {
            String currencyInProductsText = currencyInProducts.get(i).getText();
            if (currencyUahInTheCapText.charAt(currencyUahInTheCapText.length() - 1)
                    != currencyInProductsText.charAt(currencyInProductsText.length() - 1)) {
                return false;
            }
        }
        return true;
    }

    public MainPage setUSD(){
        driver.findElement(currencyUahInTheCap).click();
        driver.findElement(currencyDollInTheCap).click();
        return this;
    }

    public MainPage searchWordDress(){
        driver.findElement(search).sendKeys("dress");
        driver.findElement(searchButton).submit();
        return this;
    }

    public SearchingResultsPage requestDress(){
        this.setUSD();
        this.searchWordDress();
        return new SearchingResultsPage(driver);
    }
}