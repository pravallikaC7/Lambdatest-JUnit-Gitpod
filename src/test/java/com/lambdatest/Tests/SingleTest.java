package com.lambdatest.Tests;

import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SingleTest {

	public static WebDriver driver;
	public static String status = "failed";

	@Before
	public void setUp() throws Exception {

		String browser = Configuration.readConfig("browser");
		String version = Configuration.readConfig("version");
		String os = Configuration.readConfig("os");
		String res = Configuration.readConfig("resolution");

		String username = System.getenv("LT_USERNAME") != null ? System.getenv("LT_USERNAME") : Configuration.readConfig("LambdaTest_UserName");
		String accesskey = System.getenv("LT_ACCESS_KEY") != null ? System.getenv("LT_ACCESS_KEY") : Configuration.readConfig("LambdaTest_AppKey");

		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability(CapabilityType.BROWSER_NAME, browser);
		capability.setCapability(CapabilityType.VERSION, version);
		capability.setCapability(CapabilityType.PLATFORM, os);
		capability.setCapability("build", "Junit Single Test");
		capability.setCapability("name", "JUnit Single");
		capability.setCapability("screen_resolution", res);

		String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";

		driver = new RemoteWebDriver(new URL(gridURL), capability);
	}

	@Test
	public void test() {

		// Launch the app
		//driver.get("https://lambdatest.github.io/sample-todo-app/");
		driver.get("https://www.linkedin.com/");

		/*// Click on First Item
		driver.findElement(By.name("li1")).click();

		// Click on Second Item
		driver.findElement(By.name("li2")).click();

		// Add new item is list
		driver.findElement(By.id("sampletodotext")).clear();
		driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
		driver.findElement(By.id("addbutton")).click();

		// Verify Added item
		String item = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
		Assert.assertTrue(item.contains("Yey, Let's add it to list"));
		status = "passed";*/

		// find web element 'EmailId' TextBox element and give input
		driver.findElement(By.id("session_key")).sendKeys("selenium173@gmail.com");
		// find web element 'Password' TextBox element and give input
		driver.findElement(By.cssSelector("#session_password")).sendKeys("saibaba9");
		// find, Wait until the Web element submit button is enabled
		WebElement submit = new WebDriverWait(driver, 20).until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='sign-in-form__submit-button']")));
		// Click on submit button of Login form
		try {
			submit.click();
		} catch (Exception e) {
		}

		// wait until the Home page is loaded completely
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
		// Find web element 'Me'
		WebElement me = new WebDriverWait(driver, 40).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//img[@class='global-nav__me-photo ghost-person ember-view']")));
		// Click on web element 'ME'
		me.click();
		// Find web element 'View profile' that appears after clicking on 'Me' in Home
		// page
		WebElement viewProfile = new WebDriverWait(driver, 40)
				.until(ExpectedConditions.elementToBeClickable(By.linkText("View Profile")));
		// click on web element 'View profile'
		viewProfile.click();
		// Scroll down to find web element Skills which appears after clicking on web
		// element 'Add profile section'
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,300);");
		// Find web element 'Add profile section' and click on it
		driver.findElement(By.xpath("//li[2]/section/button")).click();
		// find web element 'Skills' and click on it
		driver.findElement(By.xpath("//li[@id='skills-drawer']/button")).click();
		// Find web element link 'Skills' which appears after clicking on 'Skills' and
		// click on it
		driver.findElement(By.linkText("Skills")).click();
		// Find web element text box 'Skill' in Add Skill popup
		WebElement enterSkill = driver.findElement(By.xpath(
				"//div[@id='artdeco-modal-outlet']/div//div[@class='artdeco-modal__content ember-view']/div/div/div[1]//input[@role='combobox']"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		/*
		 * Enter skill in Skill text box and click on submit button in Add Skill popup
		 */
		enterSkill.click();
		enterSkill.sendKeys(" Dev");
		enterSkill.sendKeys(Keys.TAB);
		enterSkill.sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//div[@class='artdeco-modal__actionbar ember-view']/button[1]")).click();

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		String actualTitle = driver
				.findElement(By.xpath("//p[@class='pv-skill-category-entity__name tooltip-container']/span[1]"))
				.getText();
		String expectedTitle = "Dev";
		AssertJUnit.assertEquals(expectedTitle, actualTitle);
		System.out.println("Verified if the added skill is being displayed or not");

	}

	@After
	public void afterTest() {
		((JavascriptExecutor) driver).executeScript("lambda-status=" + status + "");
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File takenSS = scrShot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(takenSS, new File("C:\Users\lenovo\OneDrive\Pictures\Screenshots"));
		} catch (IOException e) {
		} 
		driver.quit();
	}

}
