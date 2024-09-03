package com.appium.ebay.AppiumEbayProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;

public class AddToCart extends Base {
	@Test
	public void SearchTest() throws InterruptedException {
		driver.findElement(By.id("com.ebay.mobile:id/search_box")).click();
		driver.findElement(By.id("com.ebay.mobile:id/search_src_text")).sendKeys("Air Jordan");
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
        Thread.sleep(3000);
        driver.findElement(By.id("com.ebay.mobile:id/text_slot_1")).click();
//        driver.findElement(By.id("com.ebay.mobile:id/button_sort")).click();
        //mengambil product pertama
        WebElement firstProduct = driver.findElement(AppiumBy.xpath("(//android.view.ViewGroup[@resource-id=\"com.ebay.mobile:id/cell_collection_item\"])[1]"));
        firstProduct.click();
        System.out.print("berhasil click product pertama");
        Thread.sleep(3000);
        //masuk pdp dan scroll hingga menemukan add to cart
//        scrollToTextUsingUiAutomator("Add to cart");
        swipeUsingW3C();
        
        
		
	}
}
