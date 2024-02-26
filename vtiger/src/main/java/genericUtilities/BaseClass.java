package genericUtilities;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import pomRepository.HomePage;
import pomRepository.LoginPage;

public class BaseClass {
	public WebDriver driver;
	public FileUtility fUtils=new FileUtility();
	public ExcelUtility eUtils=new ExcelUtility();
	public JavaUtility jUtils=new JavaUtility();
	public WebDriverUtility wUtils=new WebDriverUtility();
	public static WebDriver sDriver;
	
	@BeforeSuite
	public void bsConfig() {
		System.out.println("Data base connection Achieved");
	}
	
	@BeforeClass
	public void bcConfig() throws IOException {
		String browser = fUtils.fetchDataFromPropertyFile("browser");
		String url = fUtils.fetchDataFromPropertyFile("url");
		
		if(browser.equals("chrome")) {
			driver=new ChromeDriver();
		}
		
		else if(browser.equals("edge")) {
			driver=new EdgeDriver();
		}
		
		if(browser.equals("firefox")) {
			driver=new FirefoxDriver();
		}
		
		System.out.println("The browser is launched");
		sDriver = driver;
		wUtils.maximizeTheWindow(driver);
		wUtils.waitForElementsToLoaded(driver);
		driver.get(url);
		System.out.println("The URL is navigated");
	}
	
	@BeforeMethod
	public void bmConfig() throws IOException {
		
		String username = fUtils.fetchDataFromPropertyFile("username");
		String password = fUtils.fetchDataFromPropertyFile("password");

		LoginPage login=new LoginPage(driver);
		login.loginAction(username, password);
		System.out.println("Login in to Application");
	}
	
	
	@AfterMethod
	public void amConfig() {
		
		HomePage home = new HomePage(driver);
		home.signOutAction();
		System.out.println("Sign out from the application");
	}
	
	
	@AfterClass
	public void acConfig() {
		driver.quit();
		System.out.println("The Browser is closed");
	}
	
	@AfterSuite
	public void asConfig() {
		System.out.println("Data base connection is Closed");
	}

}