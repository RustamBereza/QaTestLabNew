package ua.com.qatestlab.prestatshop;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.*;

public class SearchingResultsPage {
    private WebDriver driver;

    public SearchingResultsPage(WebDriver driver){
        this.driver = driver;
    }

    private By inscriptionGoodsX = By.xpath("//*[@id='js-product-list-top']//p");
    private By sortingRelevance = By.xpath("//a[@class='select-title']");
    private By sortingPriceReduction = By.xpath("//*[@id='js-product-list-top']/div[2]//a[5]");
    private By price = By.xpath("//*[@class='product-price-and-shipping']");
    private By discount = By.xpath("//*[@class='discount-percentage']");
    private By beforeDiscount = By.xpath("//*[@class='product-price-and-shipping']/span[@class='regular-price']");
    private By afterDiscount = By.xpath("//*[@class='discount-percentage']/following-sibling::*");

    public String checkPresenceOfInscription(){
        String quantityGoods = driver.findElement(inscriptionGoodsX).getText();
        char number = 0;
        for(int i = 0; i < quantityGoods.length(); i++){
            char quantity = quantityGoods.charAt(i);
            if(quantity > 47 && quantity < 58){
                number = quantity;
            }
        }
        return "Товаров " + (number) + ".";
    }

    public String checkCurrencyInProduct(){
        List<WebElement> currencyInProducts = driver.findElements(price);
        String currencyInProductsText = "";
        for(int i = 0; i < currencyInProducts.size(); i++) {
            currencyInProductsText = currencyInProducts.get(i).getText();
        }
        return "" + currencyInProductsText.charAt(currencyInProductsText.length() - 1);
    }

    public void setPriceReduction(){
        driver.findElement(sortingRelevance).click();
        driver.findElement(sortingPriceReduction).click();
    }

    public void waitForPrice(){
        WebDriverWait wait = (new WebDriverWait(driver, 10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), '\n" +
                "    Цене: от высокой к низкой    ')]")));
    }

    public double[] checkSorting(){
        List<WebElement> element = driver.findElements(price);
        double[] price = new double[element.size()];
        for(int i = 0; i < element.size(); i++) {
            String sortingByPrice = element.get(i).getText();
            price[i] = Double.parseDouble(sortingByPrice.substring(0, 4).replaceAll(",", "."));
        }
        return price;
    }

    public boolean checkDiscountedPrices() {
        List<WebElement> firstElement = driver.findElements(beforeDiscount);
        List<WebElement> secondElement = driver.findElements(afterDiscount);
        List<WebElement> discounts = driver.findElements(discount);
        for (int i = 0; i < firstElement.size(); i++){
            String beforeDiscount = firstElement.get(i).getText();
            String afterDiscount = secondElement.get(i).getText();
            String discountText = discounts.get(i).getText();
            discountText = discountText.substring(1, discountText.length() - 1);
            double firstNum = Double.parseDouble(beforeDiscount.substring(0, 4).replaceAll(",", "."));
            double secondNum = Double.parseDouble(afterDiscount.substring(0, 4).replaceAll(",", "."));
            int discount = Integer.parseInt(discountText);
            if(Math.round(((firstNum - secondNum) / firstNum) * 100) != discount)
                return false;
        }
        return true;
    }
}