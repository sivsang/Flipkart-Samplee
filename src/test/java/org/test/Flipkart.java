package org.test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Flipkart {

	WebDriver driver;

	@BeforeMethod
	public void beforeMethod() {
		// Browser Launch
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		// URL Launch
		driver.get("https://www.flipkart.com/");

		// maximize window
		driver.manage().window().maximize();

		// implicit wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);

	}

	@Test()
	public void login() {

		// Switching to Login Pop up
		driver.switchTo().activeElement();

		// Enter UserName
		driver.findElement(By.xpath("//span[contains(text(), 'Enter Email/Mobile number')]/../../input"))
				.sendKeys("9042769619");

		// Enter Password
		driver.findElement(By.xpath("//span[contains(text(), 'Enter Password')]/../../input")).sendKeys("anbaadira");

		// Click login
		driver.findElement(By.xpath("//span[contains(text(), 'Login')]/parent::button")).click();

		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement searchbox = driver
				.findElement(By.xpath("//input[contains(@title, 'Search for products, brands and more')]"));
		WebElement searchIcon = driver.findElement(By.className("L0Z3Pu"));

		wait.until(ExpectedConditions.elementToBeClickable(searchbox));
		// Search HP Laptop
		searchbox.sendKeys("HP Laptop");
		searchIcon.click();

		// Click on Item
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[contains(text(), 'Light Laptop')]/../../parent::a")));
		driver.findElement(By.xpath("//div[contains(text(), 'Light Laptop')]/../../parent::a")).click();

		// Switching to second window
		String firstWin = driver.getWindowHandle();
		Set<String> allWin = driver.getWindowHandles();
		for (String win : allWin) {
			if (!win.equalsIgnoreCase(firstWin))
				driver.switchTo().window(win);
		}

		WebElement addTocart = driver.findElement(By.xpath("(//button)[2]"));

		// Click Add to cart
		wait.until(ExpectedConditions.elementToBeClickable(addTocart));
		addTocart.click();

		// close current window
		driver.close();

		// switch to Parent Window
		driver.switchTo().window(firstWin);

		driver.navigate().refresh();
		wait.until(ExpectedConditions.elementToBeClickable(searchbox));
		// Clear Search
		searchbox.click();
		searchbox.clear();
		searchbox.click();

		// Enter Any mobile in Search
		searchbox.sendKeys("Any Mobile");
		searchIcon.click();

		// click on Item
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Mobile')]")));
		driver.findElement(By.xpath("//a[contains(text(), 'Mobile')]")).click();

		// Switching to second window
		Set<String> allWind = driver.getWindowHandles();
		for (String win : allWind) {
			if (!win.equalsIgnoreCase(firstWin))
				driver.switchTo().window(win);
		}

		// Click Add to cart
		wait.until(ExpectedConditions.elementToBeClickable(addTocart));
		addTocart.click();

		// Clicking on HP Laptop Remove Link
		driver.findElement(By.xpath("//a[contains(text(), 'Light Laptop')]/following::div[contains(text(), 'Remove')]"))
				.click();

		// Switching to Remove Item Popup
		driver.switchTo().activeElement();
		driver.findElement(By.xpath(
				"//div[contains(text(), 'Are you sure you want to remove this item?')]/following::div[contains(text(), 'Remove')]"))
				.click();

		// Verify 2nd added Item in Cart
		boolean flag = driver.findElement(By.xpath("//a[contains(text(), 'Mobile')]")).isDisplayed();
		Assert.assertTrue(flag);

		// Switching to ParentWindow
		driver.switchTo().window(firstWin);

		driver.navigate().refresh();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Ramya')]")));

		// Logout
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//div[contains(text(),'Ramya')]")))
				.click(driver.findElement(By.xpath("//div[contains(text(),'Logout')]"))).build().perform();

		// Verifying the Logout
		boolean result = driver.findElement(By.linkText("Login")).isDisplayed();
		if (result)
			System.out.println("Logged Out successfully");

	}

	@AfterMethod
	public void afterMethod() {
		// Closing all window
		driver.quit();
	}

}
