package com.appium.ebay.AppiumEbayProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Base {
	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	
	
	@BeforeClass
    public void configureAppium() throws MalformedURLException {
		Map<String , String> env = new HashMap<String , String>(System.getenv());
		env.put("ANDROID_HOME", "C:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk");
		env.put("JAVA_HOME", "C:\\Program Files\\Java\\jdk-11.0.16.1");
		
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\ASUS\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withTimeout(Duration.ofSeconds(300))
                .build();
        
        service.start();
        
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Automation");
        options.setAppPackage("com.ebay.mobile");  // Example app package, replace with actual one if different
        options.setAppActivity("com.ebay.mobile.home.impl.main.MainActivity");  // Example app activity, replace with actual one if different

        options.setCapability("appWaitDuration", 30000);
        // Create object for Android driver
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        if (driver == null) {
            System.out.println("Driver initialization failed.");
        } else {
            System.out.println("Driver initialized successfully: " + driver);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
	 public void scrollToEnd() {
    	 //scroll as long as the app has element
   		boolean canScrollMore;
   		do {			
   			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture",ImmutableMap.of(
   					"left", 100, "top", 100, "width",200, "height", 200,
   					"direction", "down",
   					"percent",3.0
   					));
   			
   		} while(canScrollMore);
   		
       }
	 
	 public void scrollToText(String text) {
	        driver.findElement(AppiumBy.androidUIAutomator(
	            "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + text + "\"));"
	        ));
	    }
	 
	 public void scrollToResourceId(String elementId) {
		 driver.findElement(AppiumBy.androidUIAutomator(
				 "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"" + elementId + "\"));"
				 ));
	 }
	 
	 public void swipeAction(WebElement ele,String swipeDirection,double percent){
		
			((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
				    "elementId", ((RemoteWebElement) ele ).getId(),
					"direction",swipeDirection,
					"percent", percent
					));
	 }
	 public void scrollToTextUsingUiAutomator(String text) {
		    try {
		        driver.findElement(AppiumBy.androidUIAutomator(
		            "new UiScrollable(new UiSelector().scrollable(true).instance(0))"
		            + ".setAsVerticalList().scrollForward().scrollIntoView(new UiSelector().textContains(\"" + text + "\").instance(0));"
		        ));
		        System.out.println("Berhasil menggulir hingga menemukan teks: " + text);
		    } catch (Exception e) {
		        System.out.println("Gagal menemukan elemen dengan teks: " + text);
		        e.printStackTrace();
		    }
		}
public void randomClick() {
	 int screenWidth = driver.manage().window().getSize().getWidth();
     int screenHeight = driver.manage().window().getSize().getHeight();

     // Pilih koordinat acak
     Random rand = new Random();
     int x = 566;
     int y = 232;

     // Klik pada koordinat acak
     // Klik pada koordinat acak menggunakan TouchAction
     driver.executeScript("mobile: clickGesture", ImmutableMap.of("x", x, "y", y));
     System.out.println("Berhasil klik pada koordinat: (" + x + ", " + y + ")");

}

public void swipeUsingW3C() {
    // Mendapatkan ukuran layar perangkat
    int screenWidth = driver.manage().window().getSize().getWidth();
    int screenHeight = driver.manage().window().getSize().getHeight();

    // Mengatur titik awal (startX, startY) dan titik akhir (endX, endY) untuk swipe
    int startX = screenWidth / 2;
    int startY = (int) (screenHeight * 0.8); // 80% dari tinggi layar
    int endY = (int) (screenHeight * 0.2);   // 20% dari tinggi layar

    // Membuat objek PointerInput untuk sentuhan
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    // Membuat sequence untuk swipe gesture
    Sequence swipe = new Sequence(finger, 1)
            .addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), startX, startY)) // Gerak ke titik awal
            .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))           // Sentuhan pada titik awal
            .addAction(finger.createPointerMove(Duration.ofMillis(500), Origin.viewport(), startX, endY)) // Geser ke titik akhir
            .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));            // Lepaskan sentuhan

    // Melakukan aksi swipe
    driver.perform(Arrays.asList(swipe));
    System.out.println("Swipe ke bawah berhasil menggunakan W3C Actions.");
}
     
     @AfterClass
     public void tearDown() {
    		
 		driver.quit();
 		service.stop();
     }

}