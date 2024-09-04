package com.appium.ebay.AppiumEbayProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;

public class AddToCart extends Base {

    // Metode utilitas untuk mencari dan memilih produk pertama
    private void searchAndSelectProduct(String searchTerm) throws InterruptedException {
        driver.findElement(By.id("com.ebay.mobile:id/search_box")).click();
        driver.findElement(By.id("com.ebay.mobile:id/search_src_text")).sendKeys(searchTerm);
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
        Thread.sleep(3000);
        driver.findElement(By.id("com.ebay.mobile:id/text_slot_1")).click();

        WebElement firstProduct = driver.findElement(AppiumBy.xpath("(//android.view.ViewGroup[@resource-id=\"com.ebay.mobile:id/cell_collection_item\"])[1]"));
        firstProduct.click();
        System.out.print("Berhasil klik produk pertama untuk: " + searchTerm);
        Thread.sleep(3000);
        
        // Scroll atau swipe ke Add to Cart atau bagian lainnya
        swipeUsingW3C();
        	driver.navigate().back();
        	driver.findElement(By.id("com.ebay.mobile:id/search_src_text")).sendKeys(searchTerm);
    }

    @Test(groups = "smoke")
    public void searchAndSelectAirJordan() throws InterruptedException {
        searchAndSelectProduct("Air Jordan");
    }

    @Test(groups = "smoke")
    public void searchAndSelectLaptop() throws InterruptedException {
        searchAndSelectProduct("Laptop");
    }
}
